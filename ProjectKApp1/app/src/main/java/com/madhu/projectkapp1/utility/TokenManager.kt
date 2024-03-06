package com.madhu.projectkapp1.utility

import android.content.Context
import android.content.SharedPreferences


class TokenManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        const val TOKEN_KEY = "jwt_token"
        const val CUSTOMER_KEY = "customer_code"
    }


    var authToken: String?
        get() = sharedPreferences.getString(TOKEN_KEY, null)
        set(value) {
            editor.putString(TOKEN_KEY, value)
            editor.apply()
        }

    var customerCode: String?
        get() = sharedPreferences.getString(CUSTOMER_KEY, null)
        set(value) {
            editor.putString(CUSTOMER_KEY, value)
            editor.apply()
        }



    fun clearAuthToken() {
        editor.remove(TOKEN_KEY)
        editor.apply()
    }

    fun clearCustomerCode() {
        editor.remove(CUSTOMER_KEY)
        editor.apply()
    }
}
