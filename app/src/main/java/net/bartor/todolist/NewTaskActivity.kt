package net.bartor.todolist

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.new_task.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.bartor.todolist.db.AppDatabase
import net.bartor.todolist.db.Task
import net.bartor.todolist.db.TaskType
import java.util.*

class NewTaskActivity : AppCompatActivity() {
    private var selectedDate: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.new_task)
        super.onCreate(savedInstanceState)

        updateText()

        date_button.setOnClickListener {
            date(selectedDate)
        }

        add_button.setOnClickListener {
            add()
        }
    }

    private fun add() {
        if (title_field.text.trim().isEmpty() || subtitle_field.text.trim().isEmpty() || type_image.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please choose everything", Toast.LENGTH_LONG).show()
        } else {
            GlobalScope.launch {
                AppDatabase.getInstance(this@NewTaskActivity)?.taskDao()?.insert(
                    Task(
                        uid = 0,
                        date = selectedDate,
                        title = title_field.text.trim().toString(),
                        subtitle = subtitle_field.text.trim().toString(),
                        type = when (type_image.checkedRadioButtonId) {
                            R.id.event_button -> TaskType.EVENT
                            R.id.people_button -> TaskType.PEOPLE
                            R.id.shopping_button -> TaskType.SHOPPING
                            else -> TaskType.EVENT
                        }
                    )
                )
            }
            finish()
        }
    }

    private fun updateText() {
        date_button.text =
            "${selectedDate.get(Calendar.HOUR_OF_DAY)}:${selectedDate.get(Calendar.MINUTE)} ${selectedDate.get(
                Calendar.DAY_OF_MONTH
            )}/${selectedDate.get(Calendar.MONTH) + 1}/${selectedDate.get(Calendar.YEAR)}"
    }

    private fun date(date: Calendar) {
        DatePickerDialog(this).apply {
            setOnDateSetListener { _, year, month, day ->
                this@NewTaskActivity.selectedDate.set(Calendar.YEAR, year)
                this@NewTaskActivity.selectedDate.set(Calendar.MONTH, month)
                this@NewTaskActivity.selectedDate.set(Calendar.DAY_OF_MONTH, day)
                updateText()
                this@NewTaskActivity.time(selectedDate)
            }
        }.show()
    }

    private fun time(date: Calendar) {
        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            this@NewTaskActivity.selectedDate.set(Calendar.MINUTE, minute)
            this@NewTaskActivity.selectedDate.set(Calendar.HOUR, hour)
            updateText()
        }, selectedDate.get(Calendar.HOUR), selectedDate.get(Calendar.MINUTE), true).show()
    }
}
