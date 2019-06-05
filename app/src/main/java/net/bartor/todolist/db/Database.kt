package net.bartor.todolist.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(entities = [Task::class], version = 1)
@TypeConverters(TypeConverter::class, CalendarConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(Database::class) {
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "db").build()
                }
            }
            return instance
        }
    }
}

