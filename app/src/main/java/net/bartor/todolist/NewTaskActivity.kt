package net.bartor.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import kotlinx.android.synthetic.main.new_task.*
import java.util.*

class NewTaskActivity : AppCompatActivity() {
    private var selected_date: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.new_task)
        super.onCreate(savedInstanceState)

        date_button.text =
            "${selected_date.get(Calendar.HOUR_OF_DAY)}:${selected_date.get(Calendar.MINUTE)} ${selected_date.get(
                Calendar.DAY_OF_MONTH
            )}/${selected_date.get(Calendar.MONTH) + 1}/${selected_date.get(Calendar.YEAR)}"

        date_button.setOnClickListener {
            date(selected_date)
        }

        add_button.setOnClickListener {
            add()
        }
    }

    private fun add() {
        if (title_field.text.trim().isEmpty() || subtitle_field.text.trim().isEmpty() || type.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please choose everything", Toast.LENGTH_LONG).show()
        } else {
            val i = Intent()
            i.putExtra("title", title_field.text.trim().toString())
            i.putExtra("subtitle", subtitle_field.text.trim().toString())
            i.putExtra("date", selected_date)
            i.putExtra("type", when (type.checkedRadioButtonId) {
                R.id.event_button -> TaskType.EVENT
                R.id.people_button -> TaskType.PEOPLE
                R.id.shopping_button -> TaskType.SHOPPING
                else -> TaskType.EVENT
            })
            setResult(Activity.RESULT_OK, i)
            finish()
        }
    }

    private fun date(date: Calendar) {
        SingleDateAndTimePickerDialog.Builder(this)
            .mainColor(getColor(R.color.colorPrimaryDark))
            .titleTextColor(getColor(R.color.colorAccent))
            .title("Choose date")
            .listener {
                date.time = it
                date_button.text =
                    "${date.get(Calendar.HOUR_OF_DAY)}:${date.get(Calendar.MINUTE)} ${date.get(Calendar.DAY_OF_MONTH)}/${date.get(
                        Calendar.MONTH
                    ) + 1}/${date.get(Calendar.YEAR)}"
            }.display()
    }
}
