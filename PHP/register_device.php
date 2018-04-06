<?php
include_once("db_connect.php");

if(isset($_POST['token']) && isset($_POST['user_id']))
   {
       $token = $_POST['token'];
       $user_id = $_POST['user_id'];

       $querycheck="SELECT token FROM devices WHERE $user_id=user_id";
       $result1= mysqli_query($con, $querycheck);
       if($result1->num_rows > 0)
          echo "2";
       else {
       $query = "INSERT INTO devices(token,user_id)
       VALUES ('$token','$user_id')";

       $result = mysqli_query($con, $query);

    if($result > 0){
        echo "1";   
    }
    else{
        echo "0";   
    }
}
}

else
 echo "Failed";


?>