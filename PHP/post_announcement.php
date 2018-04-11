<?php
include_once("db_connect.php");

if(isset($_POST['professor']) && isset($_POST['content']) && isset($_POST['class']))
   {
       $professor = $_POST['professor'];
       $content = $_POST['content'];
       $class = $_POST['class'];
       $query = "INSERT INTO announcements(professor,content,class)
       VALUES ('$professor', '$content', '$class')";

       $result = mysqli_query($con, $query);

    if($result > 0){
        echo "success";   
    }
    else{
        echo "failed";   
    }
}


?>