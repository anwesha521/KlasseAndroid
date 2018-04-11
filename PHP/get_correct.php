<?php

include_once ('db_connect.php');


$stmt = $con->prepare("SELECT week,answer,mark,type FROM quiz_post");
 
//executing the query 
$stmt->execute();

//binding results to the query 
$stmt->bind_result($week,$answer,$mark,$type);

$result = array(); 

//traversing through all the result 
while($stmt->fetch()){
$temp = array();
$temp['week'] = $week;
$temp['answer'] = $answer;
$temp['mark'] = $mark;
$temp['type'] = $type;
array_push($result, $temp);
}


echo json_encode($result);

mysqli_close($con);

?>