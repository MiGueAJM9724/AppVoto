package com.example.miivoto

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.miivoto.DataBase.adminDB
import com.example.miivoto.Volley.VolleySingleton
import com.example.miivoto.Volley.address
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.activity_activityresult.*


class Activityresult : AppCompatActivity() {
    val entries = ArrayList<BarEntry>()//variable para los datos a graficar
    val labels = ArrayList<String>()//estas son las etiquetas de los meses, se podria decir que es lo que se va graficar
    var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activityresult)
        resultado()
        setBarChart()
    }
    fun resultado() {
        val admin = adminDB(this)
        cursor = admin.Consulta("select count(id_voto) as total,nombre from voto GROUP BY id_candidato")
    }
    fun setBarChart(){
        var i=0
        if(cursor!!.moveToFirst()){
            do {
                val vtas = cursor!!.getFloat(0)
                val nom = cursor!!.getString(1)
                entries.add(BarEntry(vtas, i))
                labels.add(nom)
                i++
            }while (cursor!!.moveToNext())
            val barDataSet = BarDataSet(entries, "Votos")
            val data = BarData(labels, barDataSet)
            barChart.data = data
            barChart.setDescription("Votos por candidato")
            barDataSet.color = resources.getColor(R.color.colorAccent)
            barChart.animateY(100)
        }
    }
}