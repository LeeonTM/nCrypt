package com.n.crypt.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail
import com.n.crypt.R
import com.n.crypt.database.model.Credential
import com.n.crypt.ui.ViewModel.LoginActivityViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var loginActivityViewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        btnLogin.setOnClickListener {
            if (btnLogin.text == "Register") {
                register()
            } else {
                login()
            }
        }

        btnForgotPassword.setOnClickListener {
            sendForgotPasswordEmail()
        }
    }

    private fun login() {
        var loginEmail = txtLoginEmail.text.toString()
        var loginPassword = txtLoginPassword.text.toString()

        if (loginEmail == loginActivityViewModel.credentials.value?.first()?.email &&
            loginPassword == loginActivityViewModel.credentials.value?.first()?.passwordHash) {
            startActivity(
                Intent(
                    this@LoginActivity,
                    OverviewActivity::class.java
                )
            )
        } else {
            Toast.makeText(this, R.string.loginError, Toast.LENGTH_SHORT).show()
        }
    }

    private fun register() {
        var credentials = Credential(
            txtLoginEmail.text.toString(),
            txtLoginPassword.text.toString()
        )

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                loginActivityViewModel.insertCredentials(credentials)
            }
        }
    }

    private fun sendForgotPasswordEmail() {
        BackgroundMail.newBuilder(this)
            .withUsername("ncrypt.noreply@gmail.com")
            .withPassword("")
            .withSenderName("nCrypt")
            .withMailTo(loginActivityViewModel.credentials.value!!.first().email)
            .withType(BackgroundMail.TYPE_PLAIN)
            .withSubject("Your nCrypt password")
            .withBody(loginActivityViewModel.credentials.value!!.first().passwordHash)
            .send()
    }

    private fun initViewModel() {
        loginActivityViewModel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)

        loginActivityViewModel.credentials.observe(this, Observer { credentialsFromDB ->
            if (credentialsFromDB.isNullOrEmpty()) {
                btnLogin.setText(R.string.register)
                btnForgotPassword.visibility = View.GONE
            } else {
                btnLogin.setText(R.string.login)
                btnForgotPassword.visibility = View.VISIBLE
            }
        })
    }
}
