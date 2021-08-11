package com.mongodb.ispfieldtechapp.data

import android.util.Log
import com.mongodb.ispfieldtechapp.data.model.LoggedInUser
import java.io.IOException
import io.realm.mongodb.App
import io.realm.mongodb.Credentials

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(realmApp: App, username: String, password: String): Result<LoggedInUser> {
        try {

            //val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe"
            realmApp.loginAsync(Credentials.emailPassword(username, password)) {


            }
            val user = LoggedInUser(username, username)
            Log.v("QUICKSTART", "Successfully logged in Email: $username, Password: $password")
            return Result.Success(user)
        } catch (e: Throwable) {
            val eMsg = "Failed to log $username. Error: ${e}"
            Log.e("QUICKSTART", eMsg)
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}