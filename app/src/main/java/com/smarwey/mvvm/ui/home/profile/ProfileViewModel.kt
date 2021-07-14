package com.smarwey.mvvm.ui.home.profile

import androidx.lifecycle.ViewModel
import com.smarwey.mvvm.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()


}