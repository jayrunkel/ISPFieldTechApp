package com.mongodb.ispfieldtechapp.data

import android.util.Log
import com.mongodb.ispfieldtechapp.data.model.LoggedInUser
import io.realm.mongodb.App
/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(realmApp: App, username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(realmApp, username, password)

        if (result is Result.Success) {
            Log.v("QUICKSTART", "[LoginRespository:login] Login result was success")
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}