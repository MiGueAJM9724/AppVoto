package com.example.miivoto.RecyclerView

class candidato(id_candidato:Int,nombre_alumno : String,carrera : String,descripcion: String,ncontrol: String ){
    var id_candidato :Int = 0
    var nombre_alumno :String = ""
    var carrera : String = ""
    var descripcion : String = ""
    var ncontrol : String = ""

    init {
        this.id_candidato = id_candidato
        this.nombre_alumno = nombre_alumno
        this.carrera = carrera
        this.descripcion = descripcion
        this.ncontrol = ncontrol
    }
}