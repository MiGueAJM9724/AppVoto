<?php
try{
        //$Cn = new PDO('pgsql:host=localhost;port=5432;dbname=ProyectoX;user=postgres;password=hola');
        $Cn = new PDO('mysql:host=localhost; dbname=eleccion','root','');
        $Cn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $Cn->exec("SET CHARACTER SET utf8");
        //$Cn->exec("SET CLIENT_ENCODING TO 'UTF8';");
}catch(Exception $e){
    die("Error: " . $e->GetMessage());
}

// Función para ejecutar consultas SELECT
function Consulta($query)
{
    global $Cn;
    try{    
        $result =$Cn->query($query);
        $resultado = $result->fetchAll(PDO::FETCH_ASSOC); 
        $result->closeCursor();
        return $resultado;
    }catch(Exception $e){
        die("Error en la LIN: " . $e->getLine() . ", MSG: " . $e->GetMessage());
    }
}

// Función que recibe un insert y regresa el consecutivo que le genero
function EjecutaConsecutivo($sentencia){
    global $Cn;
    try {
        $result = $Cn->query($sentencia);
        $resultado = $result->fetchAll(PDO::FETCH_ASSOC);
        $result->closeCursor();
        return $resultado[0]['idcurso'];
    } catch (Exception $e) {
        die("Error en la linea: " + $e->getLine() + 
        " MSG: " + $e->GetMessage());
        return 0;
    }
}

function Ejecuta ($sentencia){
    global $Cn;
    try {
        $result = $Cn->query($sentencia);
        $result->closeCursor();
        return 1; // Exito  
    } catch (Exception $e) {
        //die("Error en la linea: " + $e->getLine() + " MSG: " + $e->GetMessage());
        return 0; // Fallo
    }
}

function consultaAlumnos(){
    $query = 'SELECT * FROM usuario ORDER BY nombre_alumno';   
    return Consulta($query);
}

function consultaAlumno($nocon){
    $query = 'SELECT ncontrol,nip FROM usuario WHERE ' . " ncontrol = '$nocon'";
    return Consulta($query);
}
function inserta($post){
    $ncontrol = $post['ncontrol'];
    $nip = $post['nip'];
    $sentencia = 'INSERT INTO usuario(ncontrol,id_carrera,nip)' . 
                 "VALUES('$ncontrol',1,'$nip')";
     
    return Ejecuta($sentencia);
}
function insertCandidato($post){
    $nombre = $post['nombre'];
    $carrera = $post['carrera'];
    $descripcion= $post['descripcion'];
    $ncontrol = $post['ncontrol'];
    $sentencia = 'INSERT INTO candidato(nombre,carrera,descripcion,ncontrol)' . 
                 "VALUES('$nombre','$carrera','$descripcion','$ncontrol')";
     
    return Ejecuta($sentencia);
}
function insertvoto($post){
    $idcandidato = $post['id_candidato'];
    $ncontrol = $post['ncontrol'];
    $sentencia = 'INSERT INTO voto(id_candidato,ncontrol)' . 
                 "VALUES($idcandidato,'$ncontrol')";
     
    return Ejecuta($sentencia);
}
?>  