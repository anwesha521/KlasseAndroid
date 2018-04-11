<?php 

include 'db_config.php';

$con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

$response = array();
if( isset($_GET['pid'] ) ) {

    $pid=$_GET['pid'];
   
   
    $result = mysqli_query($con,"DELETE FROM announcements where pid='$pid'");
   
    $row_count = mysqli_affected_rows($con);

    if($row_count==1){
        $response["success"] = 1;
        $response["message"] = "Deleted Sucessfully.";
       }
    else{
        $response[“success”] = 0;
        $response[“message”] = "Failed To Delete"; 
     } 
  // echoing JSON response
  echo json_encode($response);

 }
?>