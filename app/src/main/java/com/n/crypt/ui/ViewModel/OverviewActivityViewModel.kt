package com.n.crypt.ui.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.n.crypt.database.model.Password
import com.n.crypt.database.repository.CredentialRepository
import com.n.crypt.database.repository.PasswordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class OverviewActivityViewModel(application: Application): AndroidViewModel(application) {
    private val passwordRepository = PasswordRepository(application.applicationContext)
    private val credentialRepository = CredentialRepository(application.applicationContext)

    private val mainScope = CoroutineScope(Dispatchers.IO)

    val passwords: LiveData<List<Password>> = passwordRepository.getPasswords()

    suspend fun deletePassword(password: Password) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                passwordRepository.deletePassword(password)
            }
        }
    }

    suspend fun deleteAllPasswords() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                passwordRepository.deleteAllPasswords()
            }
        }

        mainScope.launch {
            withContext(Dispatchers.IO) {
                credentialRepository.deleteAllCredentials()
            }
        }

        exitProcess(1)
    }
}