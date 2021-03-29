<?php
//echo'debut Users ';
    class Users{

       // Connection
        private $conn;
        private $db_table = "users";

        // Db connection
        public function __construct($db){
            include_once '../config/constants.php';
            $this->conn = $db;
        }

        public function loadParcelByUser($username){
            $conn = $this->conn;
            
            $query = "select id,ST_AsGeoJSON(geom,6) AS geojson, commune,adresse, code_cadastre,numero_cadastre, ST_Area(geom,false) AS surface,nom_proprietaire,montant_achat,nom_locataire,montant_loyer,type, date_autorisation, releve_msa,code_mae, demande_pac from parcelles where nom_locataire='".$username."' ORDER BY id";
            
            $result = $conn->query($query);
            return $result;
    
        }
         
        public function insertEvent($date, $type, $parameter, $id_parcelle){
            $date = $date;
            $type = $type;
            $parameter = $parameter;
            $id_parcelle = $id_parcelle;
            
             $stmt = "
            INSERT INTO evenements
                (date, type, parameter, id_parcelle)
            VALUES
            ('".$date."','" . $type . "','".$parameter."','" . $id_parcelle . "')";
                        
            try {
                
                $stmt = $this->conn->prepare($stmt);
                
                if ($stmt->execute()) {
                return true;
            
            }     
            } catch (PDOException $e) {
             echo "Error : " . $e->getMessage() . "<br/>";
              }
          }
        
        public function login($email, $password){
           // echo 'loginn '
            $getEmail = $email;
            //echo $getEmail ;
           // echo " mot de passe est : " .$password;
            $stmt = "SELECT * FROM users WHERE email='".$getEmail."'";

            
        try{
            $stmt = $this->conn->prepare($stmt);
            $stmt->execute();
            
            $count = $stmt->rowCount();
            
        if ($row = $stmt->fetch()) {
            $dbemail = $row['email'];
            $dbpassword = $row['password'];
            $dbusertype = $row['user_type'];
    
            if ($dbemail == $email && $password == $dbpassword ) {
                //$login = true;
                return $row;
            } else return false;
            
            return $row;
         } else{
            return null;
        }  

        } catch (PDOException $e) {
             echo "Error : " . $e->getMessage() . "<br/>";
        }
       
        }
            
      
}
?>