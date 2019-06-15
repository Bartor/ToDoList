package net.bartor.todolist

import android.app.*
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.bartor.todolist.db.AppDatabase
import net.bartor.todolist.db.Task
import java.util.*
import kotlin.collections.ArrayList

const val CHANNEL_ID = "wololo"

class MainActivity : AppCompatActivity() {
    private lateinit var taskList: LiveData<List<Task>>
    private val adapterList: ArrayList<Task> = arrayListOf()
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstance(this)!!

        recycler.adapter = TaskListAdapter(adapterList) {
            GlobalScope.launch {
                db.taskDao().delete(it)
            }
        }

        recycler.layoutManager = LinearLayoutManager(this)

        taskList = db.taskDao().getAll()
        taskList.observe(this, Observer {
            println(it)
            adapterList.clear()
            adapterList.addAll(it!!)

            val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            for (task in it) {
                println(task)
                val intent = Intent(this, Receiver::class.java)
                    .putExtra("task", task)
                val broadcast = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                manager.setExact(AlarmManager.RTC_WAKEUP, task.date.timeInMillis, broadcast)
            }

            recycler.adapter.notifyDataSetChanged()
        })

        addButton.setOnClickListener {
            val i = Intent(this, NewTaskActivity::class.java)
            startActivity(i)
        }

        byName.setOnClickListener {
            adapterList.sortBy { it.title }
            recycler.adapter!!.notifyDataSetChanged()
        }

        byTime.setOnClickListener {
            adapterList.sortBy { it.date.timeInMillis }
            recycler.adapter!!.notifyDataSetChanged()
        }

        byType.setOnClickListener {
            adapterList.sortBy { it.type }
            recycler.adapter!!.notifyDataSetChanged()
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val res = db.taskDao().findByTitle("%${p0.toString()}%")
                res.observe(this@MainActivity, Observer {
                    adapterList.clear()
                    adapterList.addAll(it!!)
                    recycler.adapter.notifyDataSetChanged()
                })
            }
        })
    }
}
