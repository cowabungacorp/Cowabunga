package br.com.brozoski.cowabunga.adapters

import android.content.Context

class Preferencias(context: Context) {
    val PREFERENCE_NAME =  "br.com.cowabunga.preferencias"
    val LATITUDE =  "latitude"
    val LONGITUDE =  "longitude"

    var preferencia = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)


    fun getLatitude():String{
        return preferencia.getString(LATITUDE, "0")
    }

    fun setLatitude(latitude: String){
        val editor = preferencia.edit()
        editor.putString(LATITUDE, latitude)
        editor.apply()
    }

    fun getLongitude():String{
        return preferencia.getString(LONGITUDE, "0")
    }

    fun setLongitude(longitude: String){
        val editor = preferencia.edit()
        editor.putString(LATITUDE, longitude)
        editor.apply()
    }

}
