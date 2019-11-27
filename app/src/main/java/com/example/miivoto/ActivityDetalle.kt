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
import kotlinx.android.synthetic.main.activity_detalle.*
import kotlinx.android.synthetic.main.activity_registro.*
import org.json.JSONObject

class ActivityDetalle : AppCompatActivity() {
    val wsInsertar = address.IP + "Wservice/insertvoto.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        val actividad = intent
        val id = actividad.getIntExtra("id_candidato",0)
        val descrip = actividad.getStringExtra("descripcion")
        val nombre = actividad.getStringExtra("nombre")
        val carrera = actividad.getStringExtra("carrera")
        val nc = actividad.getStringExtra("ncontrol")
        tvDescripcion.text = descrip
        tvnombre.text = nombre
        tvCarrera.text = carrera
        tvncontrol.text = nc
        tvn2.text = nombre
        idcand.text = "$id"

    btnVoto.setOnClickListener {
        val idcandidato = id
        val ncontrol = nc
        var jsonEntrada= JSONObject()
        jsonEntrada.put("id_candidato",idcandidato)
        jsonEntrada.put("ncontrol",ncontrol)
        sendRequest(wsInsertar,jsonEntrada)
        Toast.makeText(this,"Acción exitosa", Toast.LENGTH_SHORT).show()
      //  startActivity(Intent(this,MainActivity::class.java))
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
