package com.mongodb.ispfieldtechapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.mongodb.ispfieldtechapp.data.LoginRepository
import com.mongodb.ispfieldtechapp.data.Result
import io.realm.mongodb.Credentials
import android.util.Log
import com.mongodb.ispfieldtechapp.ISPFieldTechApplication

import com.mongodb.ispfieldtechapp.R
import io.realm.mongodb.App
import java.util.Arrays.asList

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    /*
    fun loginRealm (realmApp: App, username: String, password: String) : Result {
        realmApp.loginAsync(Credentials.emailPassword(username, password)) {
                if (it.isSuccess) {
                    Log.v("QUICKSTART", "Successfully logged in Email: $username, Password: $password")
/*
                    val action : ActionLoginFragmentToChatRoomFragment = LoginFragmentDirections.actionLoginFragmentToChatRoomFragment()
                    action.email = editTextEmailAddress.text.toString()
                    action.password = editTextPassword.text.toString()
                    Navigation.findNavController(view).navigate(action)
 */
                } else {
                    val eMsg = "Failed to log $eMail. Error: ${it.error.message}"
                    Log.e("QUICKSTART", eMsg)
/*
                    setErrorMsg(eMail)
 */
                }
            }
    }
*/
    fun login(realmApp: App, username: String, password: String) {
        // can be launched in a separate asynchronous job
/*
        val result = loginRepository.login(realmApp, username, password)
        //val result = loginRealm(realmApp, realmApp, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
 */
        realmApp.loginAsync(Credentials.emailPassword(username, password)) {

            if (it.isSuccess) {
                Log.v("QUICKSTART", "Successfully logged in Email: $username, Password: $password")
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = username))
            }
            else {
                val eMsg = "Failed to log $username. Error: ${it.error.message}"
                Log.e("QUICKSTART", eMsg)
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    fun resetPassword(realmApp: App, username: String, password: String) {
        realmApp.emailPassword.callResetPasswordFunctionAsync(username, password, arrayOf("hello")) {
            if (!it.isSuccess) {
                Log.e("QUICKSTART", "Could reset password for user: $username")
                Log.e("QUICKSTART", "Error: ${it.error}")
                _loginResult.value = LoginResult(error = R.string.login_failed)
            } else {
                Log.i("QUICKSTART", "Successfully changed password for user: $username")
                // when the account has been created successfully, log in to the account
                // TODO: need to create login state for password reset or registered user
                _loginResult.value = LoginResult(success = LoggedInUserView(displayName = username))
            }
        }
    }

    fun createUser(realmApp: App, username: String, password: String) {
        realmApp.emailPassword.registerUserAsync(username, password) {
            if (!it.isSuccess) {
                Log.e("QUICKSTART", "Could not register user: $username")
                Log.e("QUICKSTART", "Error: ${it.error}")
                _loginResult.value = LoginResult(error = R.string.login_failed)
            } else {
                Log.i("QUICKSTART", "Successfully registered user: $username")
                // when the account has been created successfully, log in to the account
                // TODO: need to create login state for password reset or registered user
                _loginResult.value = LoginResult(success = LoggedInUserView(displayName = username))
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}