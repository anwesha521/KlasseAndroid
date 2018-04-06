<?PHP
    include_once("db_connect.php"); 

if( isset($_POST['user_id']) && isset($_POST['password']) ) 
{ 
      
        $user_id = $_POST['user_id'];
        $password = $_POST['password'];

        
        $query = "SELECT user_id, password,name,type FROM users WHERE user_id = '$user_id' AND password = '$password'"; 
        
        $result = mysqli_query($con, $query);
       
       if($query)
      {
        if($result->num_rows > 0)
         {
           while($row = mysqli_fetch_assoc($result))
		{
		
	        echo $row['name'].",".$row['type'];
		}
       	 } 

       else
         echo "Failure";
    } 
else
{
echo "QueryFailure";
}
}
else
  echo "Failure";
?>