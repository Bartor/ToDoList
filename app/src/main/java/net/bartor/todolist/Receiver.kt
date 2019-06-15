package net.bartor.todolist

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import net.bartor.todolist.db.Task

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val task = intent?.getSerializableExtra("task") as Task?

        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT)
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        val builder = Notification.Builder(context, CHANNEL_ID)
            .setContentTitle("Task: ${task?.title}")
            .setContentText("Description: ${task?.subtitle}")
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_more_vert_white_48dp)

        val int = Intent(context, MainActivity::class.java)
        val pending = PendingIntent.getActivity(context, 0, int, 0)
        builder.setContentIntent(pending)

        manager.notify(2137, builder.build())
    }
}