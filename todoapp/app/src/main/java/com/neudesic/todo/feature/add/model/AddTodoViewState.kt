package com.neudesic.todo.feature.add.model

import androidx.annotation.StringRes

class AddTodoViewState(
    @StringRes val error: Int = 0,
    val errorCode: Int? = null,
    val didSave: Boolean = false
) {
    companion object Constants {
        const val DESCRIPTION = 0
        const val TITLE = 1
        const val DUE_DATE = 2
    }


}