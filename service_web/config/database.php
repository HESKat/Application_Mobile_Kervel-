
<?php
//echo'bonjour le jour ';
      class Database {
 
          private $conn = null;
           public function __construct(){ 
            $dbuser = 'postgres';
            $dbpass = 'kerv3L1!?';
            $dbhost = 'localhost';
            $dbname='ayweh';


                try{
                $this->conn = new PDO("pgsql:host=$dbhost;dbname=$dbname", $dbuser, $dbpass);
                  //  echo' sucess ';
                   
            }catch (PDOException $e) {
            echo "Error : " . $e->getMessage() . "<br/>";
            die();
            }
           
      }
          
         public function getConnection(){
             return $this->conn;
         }
    }

$testObject = new Database();

$testObject->getConnection();

?>
