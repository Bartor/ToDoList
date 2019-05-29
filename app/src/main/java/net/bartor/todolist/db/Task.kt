package net.bartor.todolist.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.util.*

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val title: String,
    val subtitle: String,
    @TypeConverters(CalendarConverter::class)
    val date: Calendar,
    @TypeConverters(TypeConverter::class)
    val type: TaskType
) : Serializable

enum class TaskType {
    SHOPPING,
    PEOPLE,
    EVENT
}