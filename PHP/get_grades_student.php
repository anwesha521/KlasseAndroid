<?php

include_once ('db_connect.php');

$stmt = $con->prepare("SELECT * FROM grades");
 
//executing the query 
$stmt->execute();

//binding results to the query 
$stmt->bind_result($student_id,$instructor_id,$percentage,$class_id,$week,$pid);

$result = array(); 

//traversing through all the result 
while($stmt->fetch()){
$temp = array();
$temp['student_id'] = $student_id;
$temp['percentage'] = $percentage;
$temp['week'] = $week;
array_push($result, $temp);
}


echo json_encode($result);

mysqli_close($con);

?>