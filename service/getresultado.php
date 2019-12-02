<?php 
$Cn = mysqli_connect("localhost","root","","eleccion")or die("server no encontrado");
mysqli_set_charset($Cn,"utf8");
    $method=$_SERVER['REQUEST_METHOD'];
    $response = array();
    $result = mysqli_query($Cn,"SELECT id_voto,c.id_candidato,c.nombre from voto as v inner join candidato as c on v.id_candidato = c.id_candidato ORDER BY id_candidato");
    if (!empty($result)) {
        $response["success"] = "202";
        $response["message"] = "encontrados";

        $response["voto"] = array();
        foreach ($result as $tupla)
        {
            array_push($response["voto"], $tupla);
        }
    }
    else{
        $response["success"] = "204";
        $response["message"] = "no encontrados";
        header($_SERVER['SERVER_PROTOCOL'] . " 500  Error interno del servidor ");
    }
    echo json_encode($response);
?>