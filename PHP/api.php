<?php

include_once ("db_connect.php");


 //creating a query
 $stmt = $con->prepare("SELECT * FROM feedback;");

 //executing the query
 $stmt->execute();

 //binding results to the query
 $stmt->bind_result($pid, $feedback, $pdfFileName, $pgNumber, $time);

 $feedbackArr = array();

 //traversing through all the result
 while($stmt->fetch()){
 $temp = array();
 $temp['pid'] = $pid;
 $temp['feedback'] = $feedback;
 $temp['pdfFileName'] = $pdfFileName;
 $temp['pgNumber'] = $pgNumber;
 $temp['time'] = $time;
 array_push($feedbackArr, $temp);
 }

 //displaying the result in json format
 echo json_encode($feedbackArr);
?>