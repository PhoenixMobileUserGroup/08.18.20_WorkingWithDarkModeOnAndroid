package com.neudesic.todo.feature.main.ui.listener

import com.neudesic.todo.model.Todo

interface OnTodoItemClickedListener {
    fun onTodoItemCompleted(item: Todo)
    fun onTodoItemRemoved(item: Todo)
}