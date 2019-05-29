package net.bartor.todolist.db

import androidx.room.TypeConverter
import java.util.*

class TypeConverter {
    @TypeConverter
    fun fromType(type: TaskType) = type.ordinal

    @TypeConverter
    fun fromInt(int: Int) = TaskType.values().find { it.ordinal == int } ?: TaskType.EVENT
}

class CalendarConverter {
    @TypeConverter
    fun fromCalendar(calendar: Calendar) = calendar.toInstant().epochSecond

    @TypeConverter
    fun fromLong(long: Long) = Calendar.getInstance().apply { timeInMillis = long }
}