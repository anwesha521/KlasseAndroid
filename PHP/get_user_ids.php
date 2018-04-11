<?php 

include ('db_connect.php');
if(isset($_GET['class_id']))
{
$class_id=$_GET['class_id'];
$sql = "SELECT user_id FROM users WHERE software_construction=$class_id OR computer_engineering=$class_id OR probability=$class_id ";
$r = mysqli_query($con,$sql);
$result = array();

while($row = mysqli_fetch_array($r))
{
    array_push($result,array(
        'user_id'=>$row['user_id']
    ));
}


echo json_encode($result);

mysqli_close($con);
}
?>