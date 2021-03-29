<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    
    include_once '../config/database.php';
    include_once '../classe/users.php';

    $database = new Database();
    $db = $database->getConnection();
    echo' hey everyone ';
    $message = array("error" => FALSE);
    $users = new Users($db);
    if (isset($_POST['date']) && isset($_POST['type']) && isset($_POST['parameter']) && isset($_POST['id_parcelle'])){
        
        $date = $_POST['date'];
        $type = $_POST['type'];
        $parameter = $_POST['parameter'];
        $id_parcelle = $_POST['id_parcelle'];
        $type = substr($type,1,strlen($type)-2);
        $parameter = substr($parameter,1,strlen($parameter)-2);
        $date = substr($date,1,strlen($date)-2);
        
    
    $stmt = $users->insertEvent($date, $type, $parameter, $id_parcelle);
       if ($stmt != false){
           // echo ' succés';
           
           $message['error'] = false;
           $message['message'] = 'event created successfully !';
           $message["event"]["date"] = $date;
           $message["event"]["type"] = $type;
           $message["event"]["parameter"] = $parameter;
           $message["event"]["id_parcelle"] = $id_parcelle;
           
           echo json_encode($message);
           return $message;
       }else {
           $message = array();
           $message['error'] = true;
           $message['message'] = 'some error occured';
           echo json_encode($message);
           return $message;
       }
      // if($stmt == EVENT_FAILURE)
 
    }
?>