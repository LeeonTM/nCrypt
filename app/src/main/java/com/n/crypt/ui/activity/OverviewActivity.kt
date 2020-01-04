package com.n.crypt.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.n.crypt.R
import com.n.crypt.database.model.Password
import com.n.crypt.ui.ViewModel.OverviewActivityViewModel
import com.n.crypt.ui.adapter.PasswordAdapter
import kotlinx.android.synthetic.main.activity_overview.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OverviewActivity : AppCompatActivity() {

    private var passwords = arrayListOf<Password>()
    private var passwordAdapter = PasswordAdapter(passwords)

    private lateinit var overviewActivityViewModel: OverviewActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        initViews()
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_passwords -> {
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO) {
                        overviewActivityViewModel.deleteAllPasswords()
                    }
                }
                passwordAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        fabAdd.setOnClickListener{
            startActivity(Intent(this, AddActivity::class.java))
        }

        rvPasswords.layoutManager = LinearLayoutManager(this@OverviewActivity, RecyclerView.VERTICAL, false)
        rvPasswords.adapter = passwordAdapter

        rvPasswords.addItemDecoration(DividerItemDecoration(this@OverviewActivity, DividerItemDecoration.VERTICAL))

        // Add swipe delete
        createItemTouchHelper().attachToRecyclerView(rvPasswords)
    }

    private fun initViewModel() {
        overviewActivityViewModel = ViewModelProviders.of(this).get(OverviewActivityViewModel::class.java)

        overviewActivityViewModel.passwords.observe(this, Observer { passwordsFromDB ->
            this@OverviewActivity.passwords.clear()
            this@OverviewActivity.passwords.addAll(passwordsFromDB)
            passwordAdapter.notifyDataSetChanged()
        })
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        var callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                var passwordToDelete = passwords[position]

                if (direction > 0) {
                    // TODO: Unhash password and copy to clipboard
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.IO) {
                            overviewActivityViewModel.deletePassword(passwordToDelete)
                        }
                    }
                    passwordAdapter.notifyDataSetChanged()
                }
            }
        }

        return ItemTouchHelper(callback)
    }
}
