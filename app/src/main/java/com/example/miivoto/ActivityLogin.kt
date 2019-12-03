package com.example.miivoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.miivoto.DataBase.adminDB
import com.example.miivoto.Volley.VolleySingleton
import com.example.miivoto.Volley.address
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class ActivityLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etcontrolr.requestFocus()
        supportActionBar!!.hide()
        btnregistrou.setOnClickListener { startActivity(Intent(this,ActivityRuser::class.java)) }

    }
    fun login(v: View){
        if(etcontrolr.text!!.isEmpty()) etcontrolr.requestFocus()
        else{
            val control = etcontrolr.text.toString()
            var jsonEntrada = JSONObject()
            jsonEntrada.put("ncontrol", etcontrolr.text.toString())
            getuser(jsonEntrada)
            btnLogin.hide()
            btnregistrou.hide()
            btnLogin2.show()
        }
    }
    fun login2(v:View) {
        val nip = etnip.text.toString()
        val contro =etcontrolr.text.toString()
        val admin = adminDB(this)
        val result =  admin.Consulta("Select ncontrol,nip From usuario")
        if(result!!.moveToFirst()) {
            val control = result.getString(0)
            var snip = result.getString(1)
            if (contro == control && nip == snip) {
                val actividad = Intent(this, MainActivity::class.java)
                actividad.putExtra("ncontrol", control)
                startActivity(actividad)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Su numero de control o nip son incoreectos",
                    Toast.LENGTH_SHORT
                ).show()
                etcontrolr.requestFocus()
            }
        }
    }
    fun getuser(jsonEnt:JSONObject){
        val wsURL = address.IP + "Wservice/MostrarAlumno.php"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL, jsonEnt,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                val userJson = response.getJSONArray("usuario")
                    if (userJson.length()>0){
                        val admin = adminDB(this)
                        val ncontrol = userJson.getJSONObject(0).getString("ncontrol")
                        val nip = userJson.getJSONObject(0).getString("nip")
                        val sentencia = "INSERT INTO usuario(ncontrol,nip) Values('$ncontrol','$nip')"
                        var result = admin.Ejecuta(sentencia)
                        Toast.makeText(this,"usuario " + result,Toast.LENGTH_SHORT).show()
                    }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Se necesita una conexión a internet: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}