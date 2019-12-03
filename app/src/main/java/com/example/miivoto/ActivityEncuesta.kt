package com.example.miivoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.miivoto.Volley.VolleySingleton
import com.example.miivoto.Volley.address
import kotlinx.android.synthetic.main.activity_encuesta.*
import org.json.JSONObject

class ActivityEncuesta : AppCompatActivity() {
    val ws = address.IP + "Wservice/insertencuesta.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encuesta)
        btnagregaencuesta.setOnClickListener {
            val pregunta1 = etpregunta1.text.toString()
            val pregunta2 = etpregunta2.text.toString()
            val pregunta3 = etpregunta3.text.toString()
            val pregunta4 = etpregunta4.text.toString()
            var jsonEntrada= JSONObject()
            jsonEntrada.put("pregunta1",pregunta1)
            jsonEntrada.put("pregunta2",pregunta2)
            jsonEntrada.put("pregunta3",pregunta3)
            jsonEntrada.put("pregunta4",pregunta4)

            sendRequest(ws,jsonEntrada)
            Toast.makeText(this,"Gracias por su tiempo", Toast.LENGTH_SHORT).show()
            finish()
            //  startActivity(Intent(this,MainActivity::class.java))
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
