<?php

include_once ('db_connect.php');


$stmt = $con->prepare("SELECT * FROM quiz_answer");

$stmt->execute();

$stmt->bind_result($id,$quizname,$week,$student_id,$answers,$type,$correct,$marks,$total,$instructor_id,$class_id);

$info=array();

while($stmt->fetch()){
	$temp=array();
	$temp['week']=$week;
	$temp['quizname']=$quizname;
	$temp['student_id']=$student_id;
	$temp['answers']=$answers;
	$temp['instructor_id']=$instructor_id;
	$temp['class_id']=$class_id;
	array_push($info, $temp);
}

echo json_encode($info);

mysqli_close($con);


?>