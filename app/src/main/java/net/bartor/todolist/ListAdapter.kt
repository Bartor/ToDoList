package net.bartor.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import net.bartor.todolist.db.Task
import net.bartor.todolist.db.TaskType
import java.util.*

class TaskListAdapter(private val values: List<Task>, private val listener: (Task) -> Unit) :
    RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.image.setImageResource(
            when (item.type) {
                TaskType.SHOPPING -> R.drawable.ic_shopping_cart_black_24dp
                TaskType.PEOPLE -> R.drawable.ic_people_black_24dp
                TaskType.EVENT -> R.drawable.ic_more_vert_white_48dp
            }
        )
        holder.title.text = item.title
        holder.subtitle.text = item.subtitle
        holder.time.text =
            "${item.date.get(Calendar.MINUTE)}:${item.date.get(Calendar.HOUR)} ${item.date.get(Calendar.DAY_OF_MONTH)}/${item.date.get(
                Calendar.MONTH
            )}/${item.date.get(Calendar.YEAR)}"

        holder.view.setOnLongClickListener {
            listener(item)
            true
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.type_image
        val title: TextView = view.title_field
        val subtitle: TextView = view.subtitle_field
        val time: TextView = view.time
    }
}