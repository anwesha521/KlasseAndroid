<?php 

include "db_config.php";

$con=mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);



$class_id=$_GET['class_id'];
$class_id='"'.$class_id.'"';

$week_del=$_GET['week_del'];


$stmt = $con->prepare("SELECT * FROM quiz_post WHERE class_id=$class_id");
 
//executing the query 
$stmt->execute();

//binding results to the query 
$stmt->bind_result($id,$quiz_name,$week,$instructor_id,$class_id,$description,$type,$mark,$a_choice,$b_choice,$c_choice,$d_choice,$answer);

$result = array(); 

//traversing through all the result 
while($stmt->fetch())
{
$temp = array();
$temp['week'] = $week;
$temp['quiz_name'] = $quiz_name;
$temp['instructor_id'] = $instructor_id;
$temp['description'] = $description; 
$temp['type'] = $type; 
$temp['mark'] = $mark; 
$temp['a_choice'] = $a_choice; 
$temp['b_choice'] = $b_choice; 
$temp['c_choice'] = $c_choice; 
$temp['d_choice'] = $d_choice;
$temp['answer'] = $answer;
array_push($result, $temp);
}


echo json_encode($result);
mysqli_close($con);

$con=mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);


$delete_ins="DELETE FROM quiz_post WHERE week=$week_del";


mysqli_query($con, $delete_ins);


mysqli_close($con);

?>