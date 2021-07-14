package com.smarwey.mvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smarwey.mvvm.data.db.AppDatabase
import com.smarwey.mvvm.data.entities.Quote
import com.smarwey.mvvm.data.network.MyApi
import com.smarwey.mvvm.data.network.SafeApiRequest
import com.smarwey.mvvm.data.preferences.PreferenceProvider
import com.smarwey.mvvm.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private val MINIMUM_INTERVAL = 6

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    /*
    * Whenever there is a change in the quotes
    * its saved to the local database
     */
    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    /*
    * Getting quotes from the API
    * For the very first time
     */
    private suspend fun fetchQuotes() {
        val lastSavedAt = prefs.getLastSavedAt()

        if (lastSavedAt == null || isFetchNeeded(lastSavedAt)) {
            try {
                val response = apiRequest { api.getQuotes() }
                quotes.postValue(response.quotes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun isFetchNeeded(savedAt: String): Boolean {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDateTime  = sdf.format(Date())
        return timeDifferenceInHours(currentDateTime,savedAt) > MINIMUM_INTERVAL
    }

    fun timeDifferenceInHours(current:String, old: String) : Int{
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDateTime=sdf.parse(current)
        val savedAtdDateTime= sdf.parse(old)
        val difference:Long = currentDateTime.time - savedAtdDateTime.time
        val differenceInHours = TimeUnit.MILLISECONDS.toHours(difference)
        return differenceInHours.toInt()
    }


    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
//            prefs.savelastSavedAt(LocalDateTime.now().toString())
            prefs.savelastSavedAt(currentDate)
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

}