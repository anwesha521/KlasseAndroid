<?php
include_once ('db_connect.php');

$stmt = $con->prepare("SELECT * FROM quiz_post");
 
//executing the query 
$stmt->execute();

//binding results to the query 
$stmt->bind_result($id,$quiz_name,$week,$instructor_id,$class_id,$description,$type,$mark,$a_choice,$b_choice,$c_choice,$d_choice,$answer);

$result = array(); 

//traversing through all the result 
while($stmt->fetch()){
$temp = array();
$temp['week'] = $week;
array_push($result, $temp);
}


echo json_encode($result);

mysqli_close($con);

?>