package com.neudesic.todo.feature.main.ui.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neudesic.todo.R
import com.neudesic.todo.feature.main.ui.adapter.TodoAdapter
import com.neudesic.todo.feature.main.ui.listener.OnTodoItemClickedListener
import com.neudesic.todo.feature.main.viewmodel.MainViewModel
import com.neudesic.todo.model.Todo


class TodoFragment : Fragment(), OnTodoItemClickedListener {
    companion object {
        const val TAG = "TodoFragment"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var inspirationalQuote: TextView

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TodoAdapter
    private var shouldShowCompletedTasks = false
    private var shouldShowUncompletedTasks = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_todo, container, false)
        inspirationalQuote = view.findViewById(R.id.inspirational_quote)

        recyclerView = view.findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val quote = preferences.getString("inspirationalQuote", "")

        inspirationalQuote.text = quote
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        shouldShowCompletedTasks = preferences.getBoolean("showCompletedTasks", false)
        shouldShowUncompletedTasks = preferences.getBoolean("showUncompletedTasks", false)

        viewModel = ViewModelProvider(this, MainViewModelFactory(requireActivity().application, shouldShowCompletedTasks, shouldShowUncompletedTasks))[MainViewModel::class.java]
        viewModel.todoItems.observe(viewLifecycleOwner, Observer {
            onTodoItemsReceived(it)
        })
    }

    private fun onTodoItemsReceived(todoItems: List<Todo>) {
        if (this::adapter.isInitialized) {
            adapter.todoItems = todoItems
            adapter.notifyDataSetChanged()
        } else {
            adapter = TodoAdapter(todoItems, this)
            recyclerView.adapter = adapter
        }
    }

    override fun onTodoItemCompleted(item: Todo) {
        item.isCompleted = true
        viewModel.update(item)
        Toast.makeText(context, "Todo completed!", Toast.LENGTH_SHORT).show()
    }

    override fun onTodoItemRemoved(item: Todo) {
        viewModel.delete(item)
        Toast.makeText(context, "Todo removed", Toast.LENGTH_SHORT).show()
    }

    class MainViewModelFactory(private val application: Application,
    private val shouldShowCompletedTasks: Boolean,
    private val shouldShowUncompletedTasks: Boolean) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(application, shouldShowCompletedTasks, shouldShowUncompletedTasks) as T
        }

    }
}
