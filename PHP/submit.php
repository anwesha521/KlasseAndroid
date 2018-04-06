<?php

include_once ('db_connect.php');


if(isset($_POST['quizname']) && isset($_POST['student_id'])&&isset($_POST['answers'])&&isset($_POST['week'])&&isset($_POST['correct'])&&isset($_POST['marks'])&&isset($_POST['total'])&&isset($_POST['type']))
{

$quizname=$_POST['quizname'];
$student_id=$_POST['student_id'];
$answers=$_POST['answers'];
$week=$_POST['week'];
$correct=$_POST['correct'];
$marks=$_POST['marks'];
$total=$_POST['total'];
$type=$_POST['type'];
$instructor_id=$_POST['instructor_id'];
$class_id=$_POST['class_id'];


$Sql_Query="insert into quiz_answer (quizname,week,student_id,answers,type,correct,marks,total,instructor_id,class_id) values ('$quizname','$week','$student_id','$answers','$type','$correct','$marks','$total','$instructor_id','$class_id')";


if(mysqli_query($con,$Sql_Query))
{
	echo "Submitted Successfully";
}else{
	echo "Failed to Submit".mysqli_error($con);
}

mysqli_close($con);
}
else 
echo "NOTISSET";

?>