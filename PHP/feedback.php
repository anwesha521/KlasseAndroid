<?php


  include_once("db_connect.php");
  if(isset($_POST['feedback']) && isset($_POST['pdfFileName']) && isset($_POST['pgNumber'])&& isset($_POST['class']))
     {
         $feedback = $_POST['feedback'];
         $pdfFileName = $_POST['pdfFileName'];
         $pgNumber = $_POST['pgNumber'];
         $class = $_POST['class'];
         $query = "INSERT INTO feedback(feedback,pdfFileName, pgNumber, class)
         VALUES ('$feedback', '$pdfFileName', '$pgNumber', '$class')";
         $result = mysqli_query($con, $query);
      if($result > 0){
          echo "success";
      }
      else{
          echo "failed";
      }
  }
?>
