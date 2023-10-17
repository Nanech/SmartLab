package com.example.smartlab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartlab.repository.Repository

class ViewModelFactory(private val repository: Repository):ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return com.example.smartlab.ViewModel(repository) as T
    }


}

