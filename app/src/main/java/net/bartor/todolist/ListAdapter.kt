package net.bartor.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class ListAdapter(context: Context, var data: ArrayList<Task>) : ArrayAdapter<Task>(context, R.layout.list_item, data) {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val data = getItem(position) as Task

        val row = inflater.inflate(R.layout.list_item, parent, false)

        row.findViewById<TextView>(R.id.title_field).text = data.title
        row.findViewById<TextView>(R.id.subtitle_field).text = data.subtitle
        row.findViewById<ImageView>(R.id.type).setImageDrawable(
            when (data.type) {
                TaskType.SHOPPING -> context.getDrawable(R.drawable.ic_shopping_cart_black_24dp)
                TaskType.PEOPLE -> context.getDrawable(R.drawable.ic_people_black_24dp)
                TaskType.EVENT -> context.getDrawable(R.drawable.ic_more_vert_white_48dp)
            }
        )
        row.findViewById<TextView>(R.id.time).text =
            "${data.date.get(Calendar.HOUR_OF_DAY)}:${data.date.get(Calendar.MINUTE)} ${data.date.get(Calendar.DAY_OF_MONTH)}/${data.date.get(
                Calendar.MONTH
            ) + 1}/${data.date.get(Calendar.YEAR)}"

        return row
    }
}