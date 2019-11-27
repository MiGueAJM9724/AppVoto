package com.example.miivoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.miivoto.DataBase.adminDB
import kotlinx.android.synthetic.main.activity_activityresult.*

class Activityresult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activityresult)
resultado()
    }
    fun resultado(){
        val admin = adminDB(this)
        val tupla = admin.Consulta("select sum(numero_voto) as total from voto where id_candidato = 30")
       

    }
}
