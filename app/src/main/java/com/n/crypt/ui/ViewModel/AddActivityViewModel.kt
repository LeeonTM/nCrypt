package com.n.crypt.ui.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.n.crypt.database.model.Password
import com.n.crypt.database.repository.PasswordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddActivityViewModel(application: Application): AndroidViewModel(application) {

    private val passwordRepository = PasswordRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.IO)

    val error = MutableLiveData<String?>()
    val success = MutableLiveData<Boolean>()

    fun insertPassword(password: Password) {
        if (passwordIsValid(password)) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    passwordRepository.insertPassword(password)
                }
            }
        }
    }

    private fun passwordIsValid(password: Password): Boolean {
        // TODO: Form validation
        return true
    }
}