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
import com.example.miivoto.DataBase.adminDB
import com.example.miivoto.Volley.VolleySingleton
import com.example.miivoto.Volley.address
import kotlinx.android.synthetic.main.activity_registro.*
import org.json.JSONObject

class ActivityRegistro : AppCompatActivity() {
    val wsInsertar = address.IP + "Wservice/insertcandidato.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

    }
    fun insertar(v: View){
        if(etnombre_usuario.text!!.isEmpty() || etcontrolr.text!!.isEmpty()){
            Toast.makeText(this,"Complete los campos", Toast.LENGTH_SHORT).show()
            etdescripcion.requestFocus()
        }else{

            val nombre = etnombre_usuario.text.toString()
            val carrera = etcarrera.text.toString()
            val descripcion = etdescripcion.text.toString()
            val ncontrol = etcontrolr.text.toString()
                var jsonEntrada= JSONObject()
                jsonEntrada.put("nombre",nombre)
                jsonEntrada.put("carrera",carrera)
                jsonEntrada.put("descripcion",descripcion)
                jsonEntrada.put("ncontrol",ncontrol)
                sendRequest(wsInsertar,jsonEntrada)
                Toast.makeText(this,"Acción exitosa", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                getvoto()
                finish()
            }
        }
    fun getvoto() { //funcion que carga la informacion de MySQL a SQLite
        val wsURL = address.IP + "Wservice/getresultado.php"
        val admin = adminDB(this)
        admin.Ejecuta("DELETE FROM voto")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL, null,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                val votoJson = response.getJSONArray("voto")//name usuario (webservice)
                for (i in 0 until votoJson.length()) {
                    val idv = votoJson.getJSONObject(i).getString("id_voto")
                    val idc = votoJson.getJSONObject(i).getString("id_candidato")
                    val ncontrol = votoJson.getJSONObject(i).getString("ncontrol")
                    val voto = votoJson.getJSONObject(i).getString("numero_voto")
                    val sentencia = "INSERT INTO voto(id_voto,id_candidato,ncontrol,numero_voto) Values('$idv','$idc','$ncontrol','$voto')"
                    var result = admin.Ejecuta(sentencia)
                    //  Toast.makeText(this, "Información cargada: " + result, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error capa8: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
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
