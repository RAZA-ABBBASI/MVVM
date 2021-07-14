package com.smarwey.mvvm.data.network.responses

import com.smarwey.mvvm.data.entities.User

data class AuthResponse(
    val isSuccessfull: Boolean?,
    val message: String?,
    val user: User?
)