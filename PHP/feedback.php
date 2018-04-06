<?php
  

  include_once("db_connect.php");
  if(isset($_POST['feedback']) && isset($_POST['pdfFileName']) && isset($_POST['pgNumber']))
     {
         $feedback = $_POST['feedback'];
         $pdfFileName = $_POST['pdfFileName'];
         $pgNumber = $_POST['pgNumber'];
         $query = "INSERT INTO feedback(feedback,pdfFileName, pgNumber)
         VALUES ('$feedback', '$pdfFileName', '$pgNumber')";
         $result = mysqli_query($con, $query);
      if($result > 0){
          echo "success";
      }
      else{
          echo "failed";
      }
  }
?>
