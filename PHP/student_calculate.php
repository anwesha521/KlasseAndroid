<?php 

include 'db_config.php';

$con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

$student_id=$_GET['student_id'];
$sql = "SELECT * FROM quiz_answer WHERE student_id='$student_id' AND id = (SELECT MAX(id) FROM quiz_answer)";
$r = mysqli_query($con,$sql);
$result = array();

while($row = mysqli_fetch_array($r))
{
    
    array_push($result,array(
        'week'=>$row['week'],
        'student_id'=>$row['student_id'],
        'answers'=>$row['answers'],
        'type'=>$row['type'],
	'total'=>$row['total'],
        'correct'=>$row['correct'],
	'marks'=>$row['marks']
    ));
}


echo json_encode($result);

mysqli_close($con);
?>