package net.bartor.todolist

import java.util.*

class Task(val title: String, val subtitle: String, val date: Calendar, val type: TaskType)

enum class TaskType {
    SHOPPING,
    PEOPLE,
    EVENT
}