package net.bartor.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class ListAdapter(private val context: Context, private val data: MutableList<Task>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(index: Int): Any {
        return data[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val data = getItem(position) as Task

        val row = inflater.inflate(R.layout.list_item, parent, false)

        row.findViewById<TextView>(R.id.title_field).text = data.title
        row.findViewById<TextView>(R.id.subtitle_field).text = data.subtitle
        row.findViewById<ImageView>(R.id.type).setImageDrawable(when (data.type) {
            TaskType.SHOPPING -> context.getDrawable(R.drawable.ic_shopping_cart_black_24dp)
            TaskType.PEOPLE -> context.getDrawable(R.drawable.ic_people_black_24dp)
            TaskType.EVENT -> context.getDrawable(R.drawable.ic_more_vert_white_48dp)
        })
        row.findViewById<TextView>(R.id.time).text = "${data.date.get(Calendar.HOUR_OF_DAY)}:${data.date.get(Calendar.MINUTE)} ${data.date.get(Calendar.DAY_OF_MONTH)}/${data.date.get(Calendar.MONTH) + 1}/${data.date.get(Calendar.YEAR)}"

        return row
    }
}