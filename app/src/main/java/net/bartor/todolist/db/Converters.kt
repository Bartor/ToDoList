package net.bartor.todolist.db

import java.util.*

class TypeConverter {
    @android.arch.persistence.room.TypeConverter
    fun fromType(type: TaskType) = type.ordinal

    @android.arch.persistence.room.TypeConverter
    fun fromInt(int: Int) = TaskType.values().find { it.ordinal == int } ?: TaskType.EVENT
}

class CalendarConverter {
    @android.arch.persistence.room.TypeConverter
    fun fromCalendar(calendar: Calendar) = calendar.toInstant().epochSecond

    @android.arch.persistence.room.TypeConverter
    fun fromLong(long: Long) = Calendar.getInstance().apply { timeInMillis = long }
}