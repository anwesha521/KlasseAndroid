
<?php

include_once( "db_connect.php");
$sql = "SELECT * FROM feedback";
$r = mysqli_query($con,$sql);
$result = array();
while($row = mysqli_fetch_array($r))
{
    array_push($result,array(
        'pid'=>$row['pid'],
        'feedback'=>$row['feedback'],
        'pdfFileName'=>$row['pdfFileName'],
        'pgNumber'=>$row['pgNumber']


    ));
	echo $row['feedback'];
}
echo json_encode($result);
mysqli_close($con);
?>
