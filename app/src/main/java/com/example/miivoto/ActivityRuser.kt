package com.example.miivoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.miivoto.Volley.VolleySingleton
import com.example.miivoto.Volley.address
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.activity_ruser.*
import org.json.JSONObject

class ActivityRuser : AppCompatActivity() {
    val wsInsertar = address.IP + "Wservice/insertalumno.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruser)
        supportActionBar!!.hide()
        btnLogin.setOnClickListener { insertar() }
    }
    fun insertar(){
        if(etcontrol.text!!.isEmpty()){
            Toast.makeText(this,"Complete los campos", Toast.LENGTH_SHORT).show()
            etdescripcion.requestFocus()
        }else{

            val ncontrol = etcontrol.text.toString()
            val nip = etnip2.text.toString()
            var jsonEntrada= JSONObject()
            jsonEntrada.put("ncontrol",ncontrol)
            jsonEntrada.put("nip",nip)
            sendRequest(wsInsertar,jsonEntrada)
            Toast.makeText(this,"Acción exitosa", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun sendRequest(wsUrl:String,jsonEntrada: JSONObject){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsUrl, jsonEntrada,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                Toast.makeText(this, "Success: ${succ} Message:${msg} ", Toast.LENGTH_LONG).show();
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_LONG).show();
                Log.d("ERROR","${error.message}")
            }
        )
        //Es para agregar las peticiones a la cola
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
