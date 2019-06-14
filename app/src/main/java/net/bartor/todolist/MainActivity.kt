package net.bartor.todolist

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
import net.bartor.todolist.notifications.NotificationService
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var taskList: LiveData<List<Task>>
    private val adapterList: ArrayList<Task> = arrayListOf()
    private lateinit var db: AppDatabase
    private var lastService: Intent? = null

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

            if (lastService != null) stopService(lastService)
            lastService = Intent(this@MainActivity, NotificationService::class.java)
            lastService!!.putExtra("tasks", adapterList)
            startService(lastService!!)

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

        editText.addTextChangedListener(object: TextWatcher {
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
