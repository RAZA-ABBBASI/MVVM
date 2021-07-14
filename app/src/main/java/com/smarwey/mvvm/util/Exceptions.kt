package com.smarwey.mvvm.util

import android.accounts.NetworkErrorException
import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)