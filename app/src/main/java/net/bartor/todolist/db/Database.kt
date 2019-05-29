package net.bartor.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

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

