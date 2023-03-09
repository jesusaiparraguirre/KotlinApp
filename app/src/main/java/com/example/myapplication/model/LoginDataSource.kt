package com.example.myapplication.model

import com.example.myapplication.model.LoggedInUser
import com.example.myapplication.model.Result
import java.io.IOException
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            println(username)
            println(password)
            val user = LoggedInUser(UUID.randomUUID().toString(), username)
            return Result.Success(user)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error al iniciar sesion", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}