package net.bartor.todolist.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
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