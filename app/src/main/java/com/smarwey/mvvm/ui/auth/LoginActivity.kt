package com.smarwey.mvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.smarwey.mvvm.R
import com.smarwey.mvvm.data.entities.User
import com.smarwey.mvvm.databinding.ActivityLoginBinding
import com.smarwey.mvvm.ui.home.HomeActivity
import com.smarwey.mvvm.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.android.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    /*
        * Creating the required instances
         */

//        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
//        val api = MyApi(networkConnectionInterceptor)
//        val db  = AppDatabase(this)
//        val repository = UserRepository(api,db)
//        val factory = AuthViewModelFactory(repository)


    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel =
            ViewModelProvider(this, factory).get(AuthViewModel::class.java)

//        binding.viewmodel=viewModel
//        viewModel.authListener= this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.buttonSignIn.setOnClickListener {

        }

    }

    private fun loginUser() {
        var email = binding.editTextEmail.text.toString().trim()
        var password = binding.editTextPassword.text.toString().trim()

        //@todo Validate usr input

        lifecycleScope.launch {
            val loginResponse = viewModel.userLogin(email, password)
            try {
                val authResponse = viewModel.userLogin(email!!, password!!)
                if (authResponse.user != null) {

                    viewModel.saveLoggedInUser(authResponse.user)
                } else {
                    binding.rootLayout.snackbar(authResponse.message!!)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }

    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        root_layout.snackbar("${user.name} is logged IN")
//           toast("${user.name} is logged IN")

    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
//        toast(message)
    }

}