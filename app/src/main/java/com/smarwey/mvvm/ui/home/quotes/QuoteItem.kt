package com.smarwey.mvvm.ui.home.quotes

import com.smarwey.mvvm.R
import com.smarwey.mvvm.data.entities.Quote
import com.smarwey.mvvm.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quote
) : BindableItem<ItemQuoteBinding>(){

    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }
}