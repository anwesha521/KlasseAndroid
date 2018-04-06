<?php

include_once ("db_connect.php");
 

if(isset($_GET['week'])&& isset($_GET['class']))
{
$week=$_GET['week'];
$class=$_GET['class'];
$sql = "SELECT * FROM pdfupload WHERE week='$week' AND class='$class'";
 
//getting result on execution the sql query
$result = mysqli_query($con,$sql);
 
//response array
$response = array();
 
$response['error'] = false;
 
$response['message'] = "PDfs fetched successfully.";
 
$response['pdfs'] = array();
 
//traversing through all the rows
 
while($row =mysqli_fetch_array($result)){
    $temp = array();
    $temp['id'] = $row['id'];
    $temp['url'] = $row['url'];
    $temp['name'] = $row['name'];
    $temp['week']=$row['week'];
    array_push($response['pdfs'],$temp);
}
}
 
echo json_encode($response);