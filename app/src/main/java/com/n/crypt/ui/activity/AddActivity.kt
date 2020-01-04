package com.n.crypt.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.n.crypt.R
import com.n.crypt.database.model.Password
import com.n.crypt.service.PasswordGenerator
import com.n.crypt.ui.ViewModel.AddActivityViewModel
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    lateinit var addActivityViewModel: AddActivityViewModel

    var passwordGenerator = PasswordGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        supportActionBar?.title = "Add a password"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        btnGeneratePassword.setOnClickListener {
            // TODO: Generate password using service and fill textbox
            txtPasswordPass.setText(
                passwordGenerator.generatePassword(
                    chkUseLowerCase.isChecked,
                    chkUseUpperCase.isChecked,
                    chkUseNumbers.isChecked,
                    chkUseSimpleChars.isChecked,
                    chkUseComplexCharacters.isChecked
                )
            )
        }

        fabCreate.setOnClickListener {
            var password = Password(
                txtPasswordTitle.text.toString(),
                txtPasswordPass.text.toString() // TODO: Hash the password before its saved in the db
            )

            addActivityViewModel.insertPassword(password)
            finish()
        }
    }

    private fun initViewModel() {
        addActivityViewModel = ViewModelProviders.of(this).get(AddActivityViewModel::class.java)

        addActivityViewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        addActivityViewModel.success.observe(this, Observer { success ->
            if (success) finish()
        })
    }
}
