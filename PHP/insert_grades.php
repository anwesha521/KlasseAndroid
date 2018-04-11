<?php
include_once("db_connect.php");

if(isset($_POST['student_id']))
   {
       $student_id = $_POST['student_id'];
       $instructor_id = $_POST['instructor_id'];
       $percentage = $_POST['percentage'];
       $class_id = $_POST['class_id'];
       $week = $_POST['week'];
       $query = "INSERT INTO grades (student_id,instructor_id,percentage,class_id,week)
       VALUES ('$student_id', '$instructor_id', '$percentage','$class_id','$week')";

       $result = mysqli_query($con, $query);

    if($result > 0){
        echo "success";   
    }
    else{
        echo "failed";   
    }
}


?>