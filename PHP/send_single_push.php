<?php 
include_once("db_connect.php");
include_once ("Firebase.php");
include_once ("Push.php"); 

$response = array(); 
 

 if(isset($_POST['title']) and isset($_POST['message']) and isset($_POST['user_id']))
{
 
 //creating a new push
 $push = null; 
 $user_id=$_POST['user_id'];

 $push = new Push(
 $_POST['title'],
 $_POST['message']
 );
 
 
 //getting the push from push object
 $mPushNotification = $push->getPush(); 
 
 //getting the token from database object 
$query="SELECT token FROM devices WHERE user_id=$user_id";
$result=mysqli_query($con, $query)->fetch_assoc();
$devicetoken = $result['token'];
 
 //creating firebase class object 
 $firebase = new Firebase(); 
 
 //sending push notification and displaying result 
 echo $firebase->send($devicetoken, $mPushNotification);
 }else{
 $response['error']=true;
 $response['message']='Parameters missing';
 }

 
echo json_encode($response);