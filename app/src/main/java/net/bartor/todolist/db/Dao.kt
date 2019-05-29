package net.bartor.todolist.db

import android.arch.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE title LIKE :title")
    fun findByTitle(title: String): LiveData<List<Task>>

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)
}