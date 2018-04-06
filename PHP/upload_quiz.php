<?PHP

	include_once ('db_connect.php');

	$quiz_name=$_POST['quiz_name'];
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
	
	$Sql_Query="insert into quiz_post (quiz_name,week,instructor_id,class_id,description,type,mark,a_choice,b_choice,c_choice,d_choice,answer) values ('$quiz_name','$week','$instructor_id','$class_id','$description','$type','$mark','$a_choice','$b_choice','$c_choice','$d_choice','$answer')";

	if(mysqli_query($con,$Sql_Query)){
		echo 'Data Sent Successfully';
	}
	else{
		echo 'Try Again';
	}

	mysqli_close($con);


?>