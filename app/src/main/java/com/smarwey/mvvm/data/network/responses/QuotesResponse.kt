package com.smarwey.mvvm.data.network.responses

import com.smarwey.mvvm.data.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)