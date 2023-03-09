package com.example.myapplication.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import androidx.room.Room
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.model.data.ApplicationDatabase
import com.example.myapplication.model.data.LoggedEntity
import com.example.myapplication.model.data.UserEntity
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.LoginViewModelFactory


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        val logged = ApplicationDatabase.getAppDatabase(applicationContext!!)
            ?.loginDAO()?.getLogin()

        println("aca")
        println(logged)

        if ( logged != null){
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("user",logged.userName)
            startActivity(intent)
        }

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            //finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun verifyUserAdd(model: LoggedInUserView) {
        val listUsers = ApplicationDatabase.getAppDatabase(applicationContext!!)
            ?.userDAO()?.findUserByUsername(model.displayName)
        listUsers!!.forEach{
            if(it.userName == model.displayName){
                val intent = Intent(this, HomeActivity::class.java)
                val welcome = getString(R.string.welcome)
                val name = model.displayName
                // TODO : initiate successful logged in experience
                Toast.makeText(
                    applicationContext,
                    "$welcome $name",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val intent = Intent(this, HomeActivity::class.java)
        val welcome = getString(R.string.welcome)
        val name = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $name",
            Toast.LENGTH_LONG
        ).show()

        val listUsers = ApplicationDatabase.getAppDatabase(applicationContext!!)
            ?.userDAO()?.findUserByUsername(model.displayName)
        listUsers!!.forEach{
            if(it.userName == model.displayName){
                val usuario = ApplicationDatabase.getAppDatabase(applicationContext!!)
                    ?.userDAO()?.getLastUser()

                if (usuario != null) {
                    ApplicationDatabase.getAppDatabase(applicationContext!!)
                        ?.loginDAO()?.postLogin(LoggedEntity(
                            userId = usuario.userId,
                            userName = name,
                        ))
                }

                intent.putExtra("user", name)
                startActivity(intent)
            }
        }


        var runnable = Runnable {

            ApplicationDatabase.getAppDatabase(applicationContext!!)
                ?.userDAO()?.saveUser(UserEntity(
                    userName = name,
                ))

            val usuario = ApplicationDatabase.getAppDatabase(applicationContext!!)
                ?.userDAO()?.getLastUser()

            if (usuario != null) {
                ApplicationDatabase.getAppDatabase(applicationContext!!)
                    ?.loginDAO()?.postLogin(LoggedEntity(
                        userId = usuario.userId,
                        userName = name,
                    ))
            }

            intent.putExtra("user", name)
            startActivity(intent)
        }

        var handler = Handler()
        handler.postDelayed(runnable,500)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}