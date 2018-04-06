<?php
include_once("db_connect.php");

if(isset($_GET['student_id']))
{
$student_id = $_GET['student_id'];
$sql = "SELECT percentage, class_id,week FROM grades WHERE student_id=$student_id";
$r = mysqli_query($con,$sql);
$result = array();

while($row = mysqli_fetch_array($r))
{
  array_push($result,array(
        'percentage'=>$row['percentage'],
         'week'=>$row['week'],
        'class_id'=>$row['class_id']
 
  ));

}

echo json_encode($result);

mysqli_close($con);
}
else
echo "error";
?>