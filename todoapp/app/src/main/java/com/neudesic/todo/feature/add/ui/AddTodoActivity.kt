package com.neudesic.todo.feature.add.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.neudesic.todo.R
import com.neudesic.todo.feature.add.model.AddTodoViewState
import com.neudesic.todo.feature.add.viewmodel.AddTodoViewModel
import kotlinx.android.synthetic.main.activity_add_todo.*
import org.joda.time.format.DateTimeFormat

class AddTodoActivity : AppCompatActivity() {

    private lateinit var viewModel: AddTodoViewModel
    private lateinit var saveButton: Button
    private lateinit var dueDate: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var title: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        saveButton = findViewById(R.id.save)
        dueDate = findViewById(R.id.due_date)
        description = findViewById(R.id.description)
        title = findViewById(R.id.title)

        save.setOnClickListener {
            onSave()
        }

        title.doAfterTextChanged {
            title.error = null
        }
        description.doAfterTextChanged {
            description.error = null
        }
        dueDate.doAfterTextChanged {
            dueDate.error = null
        }

        viewModel = ViewModelProvider(this)[AddTodoViewModel::class.java]
        viewModel.viewState.observe(this, Observer {
            onViewStateReceived(it)
        })
    }

    private fun onViewStateReceived(viewState: AddTodoViewState) {
        when (viewState.errorCode) {
            AddTodoViewState.TITLE -> title.error = getString(viewState.error)
            AddTodoViewState.DESCRIPTION -> description.error = getString(viewState.error)
            AddTodoViewState.DUE_DATE -> dueDate.error = getString(viewState.error)
            else -> Log.d("AddTodoActivity", "Nothing to set an error for")
        }

        if (viewState.didSave) {
            Toast.makeText(applicationContext, "Todo Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSave() {
        if (dueDate.text.toString().isEmpty()) {
            viewModel.save(title.text.toString(), description.text.toString(), null)
        } else {
            val formatter = DateTimeFormat.forPattern("MM/dd/YYYY")
            viewModel.save(title.text.toString(), description.text.toString(), formatter.parseLocalDate(dueDate.text.toString()))

        }
    }
}