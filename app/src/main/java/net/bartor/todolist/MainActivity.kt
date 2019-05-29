package net.bartor.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import net.bartor.todolist.db.AppDatabase
import net.bartor.todolist.db.Task
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var taskList: LiveData<List<Task>>
    private val adapterList: ArrayList<Task> = arrayListOf()
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstance(this)!!

        recycler.adapter = TaskListAdapter(adapterList) {
            db.taskDao().delete(it)
        }

        taskList = AppDatabase.getInstance(this)?.taskDao()?.getAll()!!
        taskList.observe(this, Observer {
            adapterList.clear()
            adapterList.addAll(it!!)
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
    }
}
