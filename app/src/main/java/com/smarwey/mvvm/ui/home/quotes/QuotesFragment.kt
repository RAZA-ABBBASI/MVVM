package com.smarwey.mvvm.ui.home.quotes

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.smarwey.mvvm.R
import com.smarwey.mvvm.data.entities.Quote
import com.smarwey.mvvm.util.Coroutines
import com.smarwey.mvvm.util.hide
import com.smarwey.mvvm.util.show
import com.smarwey.mvvm.util.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.x.kodein

class QuotesFragment : Fragment(), LifecycleObserver, KodeinAware {

    override val kodein by kodein()
    private val factory: QuotesViewModelFactory by instance()
    private lateinit var viewModel: QuotesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }*/

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onCreated() {
        Log.i("tag", "reached the State.Created")
        viewModel = ViewModelProvider(this, factory).get(QuotesViewModel::class.java)
        Coroutines.main {
            val quotes= viewModel.quotes.await()
            quotes.observe(this, Observer {
                context?.toast(it.size.toString())
            })

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)
    }

    override fun onDetach() {
        super.onDetach()
        lifecycle.removeObserver(this)
    }

    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        viewModel.quotes.await().observe(this, Observer {
            progress_bar.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItem)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }


    private fun List<Quote>.toQuoteItem() : List<QuoteItem>{
        return this.map {
            QuoteItem(it)
        }
    }

}