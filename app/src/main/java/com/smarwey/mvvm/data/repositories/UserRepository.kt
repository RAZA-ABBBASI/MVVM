package com.smarwey.mvvm.data.repositories

import com.smarwey.mvvm.data.db.AppDatabase
import com.smarwey.mvvm.data.entities.User
import com.smarwey.mvvm.data.network.MyApi
import com.smarwey.mvvm.data.network.SafeApiRequest
import com.smarwey.mvvm.data.network.responses.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {


    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest {
            api.userlogin(email, password)
        }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()

    suspend fun userSignup(name: String,email: String, password: String): AuthResponse {
        return apiRequest {
            api.userSignup(name, email, password)
        }
    }

    /*
    * Old code performing the same functionality
    * Optimal code written above
     */


    /*fun userLogin(email: String, password: String) : Response<String>{
        val loginResponse = MutableLiveData<String>()
        MyApi().userlogin(email,password)
            .enqueue(object : Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        loginResponse.value = response.body()?.string()
                    } else {
                        loginResponse.value = response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loginResponse.value=t.message
                }

            })
        return  loginResponse
    }*/

    /*suspend fun userLogin(email: String, password: String): Response<AuthResponse> {
        return MyApi().userlogin(email, password)
    }*/


}