package com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.fragments.FollowersFragment
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataFollowers
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowersViewModel : ViewModel() {

    private val listFollowersNonMutable = ArrayList<DataFollowers>()
    private val listFollowersMutable = MutableLiveData<ArrayList<DataFollowers>>()

    fun getListFollower(): LiveData<ArrayList<DataFollowers>> {
        return listFollowersMutable
    }

    fun getDataUserGit(context: Context, id: String) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token e5b1dd0aa9cdb16974fc1f01aafb6baa2fa14fe8")
        httpClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$id/followers"

        httpClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FollowersFragment.TAG, result)
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

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
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
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FollowersFragment.TAG, result)

                try {
                    val jsonObject = JSONObject(result)
                    val dataFollowers = DataFollowers()
                    dataFollowers.username = jsonObject.getString("login")
                    dataFollowers.name = jsonObject.getString("name")
                    dataFollowers.avatar = jsonObject.getString("avatar_url")
                    dataFollowers.company = jsonObject.getString("company")
                    dataFollowers.location = jsonObject.getString("location")
                    dataFollowers.repository = jsonObject.getString("public_repos")
                    dataFollowers.followers = jsonObject.getString("followers")
                    dataFollowers.following = jsonObject.getString("following")
                    listFollowersNonMutable.add(dataFollowers)
                    listFollowersMutable.postValue(listFollowersNonMutable)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
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