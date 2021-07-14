package com.smarwey.mvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.smarwey.mvvm.data.entities.User
import com.smarwey.mvvm.data.repositories.UserRepository
import com.smarwey.mvvm.ui.home.HomeActivity
import com.smarwey.mvvm.util.ApiException
import com.smarwey.mvvm.util.Coroutines
import com.smarwey.mvvm.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO){repository.userLogin(email, password)}

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ) = withContext(Dispatchers.IO){repository.userSignup(name, email, password)}

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)
}


/*
* Following code is old code for viewmodel, where we have two way binding i.e.,
* in the layout file, and the viewmodel file
 */
/*
class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordConfirm: String? = null
    var authListener: AuthListener? = null

    fun getLoggedInUser() = userRepository.getUser()

    fun onSignupTextClick(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            view.context.startActivity(it)
        }
    }

    fun onSigninTextClick(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            view.context.startActivity(it)
        }
    }

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        Coroutines.main {
            try {
                val authResponse = userRepository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    userRepository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
            */
/*val loginResponse = UserRepository().userLogin(email!!, password!!)
            if (loginResponse.isSuccessful) {
                authListener?.onSuccess(loginResponse?.body()?.user!!)
            } else {
                authListener?.onFailure("Error Code: ${loginResponse.code()}")
            }*//*

        }

    }


    */
/*
    * User Singup
    * @ User name
    * @ Email
    * @ Password
    * @ Password confirmation
     *//*

    fun onSignupButtonClick(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required")
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is required")
            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Password is required")
            return
        }
        if (passwordConfirm.isNullOrEmpty()) {
            authListener?.onFailure("Password Confirmation is required")
            return
        }

        Coroutines.main {
            try {
                val authResponse = userRepository.userSignup(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    userRepository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }

        }

    }

}*/
