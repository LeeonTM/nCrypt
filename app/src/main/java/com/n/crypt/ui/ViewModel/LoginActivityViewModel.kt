package com.n.crypt.ui.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.n.crypt.database.model.Credential
import com.n.crypt.database.repository.CredentialRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivityViewModel(application: Application): AndroidViewModel(application) {
    private val credentialRepository = CredentialRepository(application.applicationContext)

    private val mainScope = CoroutineScope(Dispatchers.IO)

    val credentials: LiveData<List<Credential>> = credentialRepository.getCredentials()

    suspend fun insertCredentials(credential: Credential) {
        if (validCredential(credential)) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    credentialRepository.insertCredential(credential)
                }
            }
        }
    }

    private fun validCredential(credential: Credential): Boolean {
        return true // TODO: Validation
    }
}