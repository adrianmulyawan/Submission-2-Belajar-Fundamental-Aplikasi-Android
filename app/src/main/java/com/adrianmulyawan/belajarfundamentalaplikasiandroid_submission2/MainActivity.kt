package com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.adapter.ListDataUserAdapter
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataUsers
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listDataUser: ArrayList<DataUsers> = ArrayList()
    private lateinit var listDataUserAdapter: ListDataUserAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private var title: String = "Github User APP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setActionBarTitle(title)

        listDataUserAdapter = ListDataUserAdapter(listDataUser)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        searchUser()
        viewConfig()
        getDataUserGit()
        configureMainViewModel(listDataUserAdapter)
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun viewConfig() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        listDataUserAdapter.notifyDataSetChanged()
        recyclerView.adapter = listDataUserAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            loadingData.visibility = View.VISIBLE
        } else {
            loadingData.visibility = View.INVISIBLE
        }
    }


    private fun getDataUserGit() {
        mainViewModel.getDataUsersGit(applicationContext)
        showLoading(true)
    }

   private fun configureMainViewModel(adapter: ListDataUserAdapter) {
       mainViewModel.getListUsers().observe(this, Observer { listUsers ->
           if (listUsers != null) {
               adapter.setData(listUsers)
               showLoading(true)
           }
       })
   }

    private fun searchUser() {
        sv_user.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    listDataUser.clear()
                    viewConfig()
                    mainViewModel.getDataSearchGit(query, applicationContext)
                    showLoading(true)
                    configureMainViewModel(listDataUserAdapter)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
    }
}