package com.example.gs_eazy.Api

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gs_eazy.BuildConfig
import org.json.JSONArray
import org.json.JSONObject

class Api(private val context : Context) {

    private val url = BuildConfig.API_KEY
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun post(path: String, jsonBody: JSONObject, onSuccess: (Any) -> Unit, onError: (String) -> Unit) {

        var urlPath :String = "$url$path";

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            urlPath,
            jsonBody,
            { response ->
                onSuccess(response)
            },
            { error ->
                onError(error.toString())
                Log.e("ApiClient", "Erro: ${error.message}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    fun get(path: String, onSuccess: (Any) -> Unit, onError: (String) -> Unit) {
        val urlPath: String = "$url$path"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            urlPath,
            null,
            { response ->
                onSuccess(response)
            },
            { error ->
                onError(error.toString())
                Log.e("ApiClient", "Erro: ${error.message}")
            }
        )
        requestQueue.add(jsonArrayRequest)
    }

    fun update(path: String, jsonBody: JSONObject, onSuccess: (Any) -> Unit, onError: (String) -> Unit) {
        val urlPath: String = "$url$path"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT,
            urlPath,
            jsonBody,
            { response ->
                onSuccess(response)
            },
            { error ->
                onError(error.toString())
                Log.e("ApiClient", "Erro: ${error.message}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    fun delete(path: String, onSuccess: (Any) -> Unit, onError: (String) -> Unit) {
        val urlPath: String = "$url$path"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.DELETE,
            urlPath,
            null,
            { response ->
                onSuccess(response)
            },
            { error ->
                onError(error.toString())
                Log.e("ApiClient", "Erro: ${error.message}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}