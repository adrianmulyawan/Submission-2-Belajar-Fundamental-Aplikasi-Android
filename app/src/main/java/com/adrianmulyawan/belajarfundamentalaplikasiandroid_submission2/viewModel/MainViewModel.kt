package com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.MainActivity
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataUsers
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val listUsersNonMutable = ArrayList<DataUsers>()
    private val listUsersMutable = MutableLiveData<ArrayList<DataUsers>>()

    fun getListUsers(): LiveData<ArrayList<DataUsers>> {
        return listUsersMutable
    }

    fun getDataUsersGit(context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token e5b1dd0aa9cdb16974fc1f01aafb6baa2fa14fe8")
        httpClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users"

        httpClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val result = responseBody?.let { String(it) }
                Log.d(MainActivity.TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val userLogin = jsonObject.getString("login")
                        getDataGitDetail(userLogin, context)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getDataSearchGit(query: String, context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token e5b1dd0aa9cdb16974fc1f01aafb6baa2fa14fe8")
        httpClient.addHeader("User-Agent", "request")
        var url = "https://api.github.com/search/users?q=$query"

        httpClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val result = responseBody?.let { String(it) }
                Log.d(MainActivity.TAG, result)
                try {
                    listUsersNonMutable.clear()
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (i in 0 until item.length()) {
                        val jsonObject = item.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        getDataGitDetail(username, context)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDataGitDetail(userLogin: String, context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token e5b1dd0aa9cdb16974fc1f01aafb6baa2fa14fe8")
        httpClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$userLogin"

        httpClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val result = responseBody?.let { String(it) }
                Log.d(MainActivity.TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val usersData = DataUsers()
                    usersData.username = jsonObject.getString("login")
                    usersData.name = jsonObject.getString("name")
                    usersData.avatar = jsonObject.getString("avatar_url")
                    usersData.company = jsonObject.getString("company")
                    usersData.location = jsonObject.getString("location")
                    usersData.repository = jsonObject.getString("public_repos")
                    usersData.followers = jsonObject.getString("followers")
                    usersData.following = jsonObject.getString("following")
                    listUsersNonMutable.add(usersData)
                    listUsersMutable.postValue(listUsersNonMutable)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}