<?PHP

	include 'db_config.php';
$con=mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

	$week=$_POST['week'];
	$instructor_id=$_POST['instructor_id'];
	$description=$_POST['description'];
	$type=$_POST['type'];
	$mark=$_POST['mark'];
	$a_choice=$_POST['a_choice'];
	$b_choice=$_POST['b_choice'];
	$c_choice=$_POST['c_choice'];
	$d_choice=$_POST['d_choice'];
	$answer=$_POST['answer'];
	$class_id=$_POST['class_id'];

	$get_query="SELECT quiz_name FROM quiz_post WHERE class_id='$class_id' AND instructor_id='$instructor_id'";

	$stmt = $con->prepare($get_query);
 
	//executing the query 
	$stmt->execute();

	//binding results to the query 
	$stmt->bind_result($temp_quiz_name);

	$quiz_name = ""; 

	//traversing through all the result 
	while($stmt->fetch()){
		$quiz_name = $temp_quiz_name;
		break;
	}
        mysqli_close($con);
$con=mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

	$Sql_Query="INSERT INTO quiz_post (id,quiz_name,week,instructor_id,class_id,description,type,mark,a_choice,b_choice,c_choice,d_choice,answer)VALUES(NULL,'$quiz_name','$week','$instructor_id','$class_id','$description','$type','$mark','$a_choice','$b_choice','$c_choice','$d_choice','$answer')";



	if(mysqli_query($con,$Sql_Query))
        {
		echo 'Data Sent Successfully';
	}
	else{
		echo "Try Again";
	}

	mysqli_close($con);


?>