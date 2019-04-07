package net.bartor.todolist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val taskList = ArrayList<Task>()
    private val NEW_TASK_REQUEST = 2137

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ListAdapter(this, taskList)
        listView.adapter = adapter

        addButton.setOnClickListener {
            val i = Intent(this, NewTaskActivity::class.java)
            startActivityForResult(i, NEW_TASK_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            NEW_TASK_REQUEST -> {
                if (data != null) {
                    val t = Task(
                        data.getStringExtra("title"),
                        data.getStringExtra("subtitle"),
                        data.getSerializableExtra("date") as Calendar,
                        data.getSerializableExtra("type") as TaskType
                    )

                    taskList.add(t)
                    (listView.adapter as ArrayAdapter<Task>).notifyDataSetChanged()
                }
            }
        }
    }
}
