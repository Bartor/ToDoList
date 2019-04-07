package net.bartor.todolist

import java.io.Serializable
import java.util.*

class Task(val title: String, val subtitle: String, val date: Calendar, val type: TaskType) : Serializable

enum class TaskType {
    SHOPPING,
    PEOPLE,
    EVENT
}