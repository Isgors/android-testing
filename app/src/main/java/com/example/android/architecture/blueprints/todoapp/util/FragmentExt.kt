package com.example.android.architecture.blueprints.todoapp.util

import androidx.fragment.app.Fragment
import com.example.android.architecture.blueprints.todoapp.ViewModelFactory
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = DefaultTasksRepository.getRepository(requireActivity().application)
    return ViewModelFactory(repository, this)
}