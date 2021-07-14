package com.smarwey.mvvm.ui.auth

import com.smarwey.mvvm.data.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message:String)

}