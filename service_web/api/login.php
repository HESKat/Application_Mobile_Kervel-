
<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../config/database.php';
    include_once '../classe/users.php';

    $database = new Database();
    $db = $database->getConnection();
    $users = new Users($db);
    // json response array
    $response = array("error" => FALSE);
    // receiving the post params
    if (isset($_POST['email']) && isset($_POST['password'])) { 
        $email = $_POST['email'];
        $email = substr($email,1,strlen($email)-2);
        $password = $_POST['password'];
        $password = substr($password,1,strlen($password)-2);
        
        //remplacer avec la méthode password_verify($password, $dbpassword) 
        //le $password afin de verifier le bon mot de passe crypté inséré par l'utilisateur
        /*password_verify($password, $dbpassword) */
        
        $user = $users->login($email, $password);
        if ($user != false) {

            $response["error"] = FALSE;
            $response["message"] = "acces avec succée";
            
            $response["user"]["email"] = $user["email"];
            $response["user"]["first_name"] = $user["first_name"];
            $response["user"]["last_name"] = $user["last_name"];
            $response["user"]["user_type"] = $user["user_type"];
            $response["user"]["username"] = $user["username"];

            echo json_encode($response);
            return $response;
        } else {
            // identifiant ou mdp incorrecte
            $response["error"] = TRUE;
            $response["message"] = "identifiant ou mot de passe incorrecte, veuillez réessayer!!";
            echo json_encode($response);
            return $response;
        }
    }
    else {
     // manque des paramétres
        $response["error"] = TRUE;
        $response["message"] = "manque des paramétres!!!";
        echo json_encode($response);
        return $response;
}
?>


