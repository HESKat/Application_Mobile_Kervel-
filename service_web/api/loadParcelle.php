<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    
    include_once '../config/database.php';
    include_once '../classe/users.php';

    

    $database = new Database();
    $db = $database->getConnection();
   
    $users = new Users($db);
    //$stmt = $users->getUsers();

    if (isset($_POST['username']) ){
        //echo 'method in loadParcelle ';
        $username = $_POST['username'];
        $username = substr($username,1,strlen($username)-2);
        $result = $users->loadParcelByUser($username);
    }
   // echo " mahhlaha ";
    $features = [];
    /*if($result == null) {echo ' vide'; } else echo ' non vide';*/

    foreach ($result as $row) {
        //echo ' dans while';
        unset($row['geom']);

    $geometry = $row['geojson'] = json_decode($row['geojson']);
    unset($row['geojson']);
    $feature = ["type" => "Feature", "geometry" => $geometry, "properties" => $row];
    array_push($features,$geometry->coordinates);
    
    //echo var_dump($row);
    }
    $array=[];
    foreach($features as $row){
            array_push($array,$row[0][0]);       
        }
    $featureCollection = ["coordinates" =>$array];
    echo json_encode($featureCollection);
               
             
?>