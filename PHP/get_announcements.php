<?php 

include 'db_config.php';

$con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
$sql = "SELECT * FROM announcements";
$r = mysqli_query($con,$sql);
$result = array();

while($row = mysqli_fetch_array($r))
{
    array_push($result,array(
        'pid'=>$row['pid'],
        'professor'=>$row['professor'],
        'content'=>$row['content'],
        'class'=>$row['class'],
        
    ));
}


echo json_encode($result);

mysqli_close($con);
?>