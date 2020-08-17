package com.neudesic.todo.feature.main.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neudesic.todo.R
import com.neudesic.todo.feature.main.ui.listener.OnTodoItemClickedListener
import com.neudesic.todo.model.Todo
import org.joda.time.format.DateTimeFormat

class TodoAdapter(
    var todoItems: List<Todo>,
    private val listener: OnTodoItemClickedListener
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = itemView.findViewById(R.id.title)
        var description: TextView = itemView.findViewById(R.id.description)
        var dueDate: TextView = itemView.findViewById(R.id.due_date)
        var completeTask: ImageView = itemView.findViewById(R.id.completed)
        var removeTask: ImageView = itemView.findViewById(R.id.remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
        TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_todo, parent, false))

    override fun getItemCount(): Int {
        return todoItems.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todoItems[position]
        if (!item.isCompleted) holder.title.text = item.title else holder.title.text = holder.itemView.context.getString(R.string.completed_task, item.title)

        holder.description.text = item.description
        holder.dueDate.text = holder.itemView.context.getString(R.string.due_date_adapter, item.dueDate.toString(DateTimeFormat.forPattern("MM/dd/YYYY")))

            holder.completeTask.setOnClickListener {
            listener.onTodoItemCompleted(item)
        }

        holder.removeTask.setOnClickListener {
            listener.onTodoItemRemoved(item)
        }
    }
}