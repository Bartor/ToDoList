package net.bartor.todolist.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import net.bartor.todolist.MainActivity
import net.bartor.todolist.R
import net.bartor.todolist.db.Task
import java.util.*
import kotlin.collections.ArrayList

const val CHANNEL_ID = "notify_channel"

class NotificationService : Service() {
    private val threads = mutableListOf<Thread>()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val tasks = intent?.getSerializableExtra("tasks") as ArrayList<Task>
        for (task in tasks.sortedBy { it.date.timeInMillis }.filter { it.date.timeInMillis*1000 > Calendar.getInstance().timeInMillis }) {
            threads.add(Thread {
                try {
                    Thread.sleep(task.date.timeInMillis*1000 - Calendar.getInstance().timeInMillis)
                } catch (e: InterruptedException) {
                    return@Thread
                }
                makeNotification(task)
            })
            threads.last().start()
        }

        val done = Intent()
        done.action = "done"
        sendBroadcast(done)

        return START_STICKY
    }

    private fun makeNotification(task: Task) {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        val builder = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Task: ${task.title}")
            .setContentText("Description: ${task.subtitle}")
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_more_vert_white_48dp)

        val intent = Intent(this, MainActivity::class.java)
        val pending = PendingIntent.getActivity(this, 0, intent, 0)
        builder.setContentIntent(pending)

        manager.notify(2137, builder.build())
    }

    override fun onDestroy() {
        for (thread in threads) thread.interrupt()
        super.onDestroy()
    }
}