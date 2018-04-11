<?php 

include 'db_config.php';

$con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
$sql = "SELECT * FROM users";
$r = mysqli_query($con,$sql);
$result = array();

while($row = mysqli_fetch_array($r))
{
    array_push($result,array(
        'user_id'=>$row['user_id'],
        'name'=>$row['name'],
        'type'=>$row['type'],
        'software_construction'=>$row['software_construction'],
        'probability'=>$row['probability'],
	'computer_engineering'=>$row['computer_engineering']
    ));
}


echo json_encode(array('result'=>$result));

mysqli_close($con);
?>