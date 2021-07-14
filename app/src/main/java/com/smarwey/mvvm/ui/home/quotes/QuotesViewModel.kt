package com.smarwey.mvvm.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.smarwey.mvvm.data.repositories.QuotesRepository
import com.smarwey.mvvm.util.lazyDeferred

class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}
