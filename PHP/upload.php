<?php


 
//this is our upload folder
$upload_path = 'uploads/';
 
//Getting the server ip
//$server_ip = gethostbyname(gethostname());
$server_ip='10.12.195.1';
//$server_ip='192.168.1.185';


 
//creating the upload url
$upload_url = 'http://'.$server_ip.'/Klasse/'.$upload_path;
 
//response array
$response = array();
include_once("db_connect.php");

    if(isset($_POST['name'])and isset($_FILES['pdf']['name']) and isset($_POST['week'])and isset($_POST['class']))
	{
 
 
        //getting name from the request
        $name = $_POST['name'];
	$week=$_POST['week'];
	$class=$_POST['class'];
 
        //getting file info from the request
        $fileinfo = pathinfo($_FILES['pdf']['name']);
 
        //getting the file extension
        $extension = $fileinfo['extension'];
		
		 $sql = "SELECT max(id) as id FROM pdfupload";
         $result = mysqli_fetch_array(mysqli_query($con,$sql));
		 $filename=0;
		 if($result['id']==null)
			 $filename=1;
		 else
			 $filename=++$result['id'];
		 
		
        //file url to store in the database
        $file_url = $upload_url . $filename . '.' . $extension;
 
        //file path to upload in the server
        $file_path = $upload_path . $filename . '.'. $extension;
	
		
        //trying to save the file in the directory
        try{
            //saving the file
           // move_uploaded_file($_FILES['pdf']['tmp_name'],$file_path);
			move_uploaded_file($_FILES['pdf']['tmp_name'],$file_path);
			
            $sql = "INSERT INTO pdfupload(id, url, name,week,class) VALUES (NULL, '$file_url', '$name','$week','$class')";
			$result= mysqli_query($con,$sql);
			
            //adding the path and name to database
            if($result>0){
 
                //filling response array with values
                $response['error'] = false;
                $response['url'] = $file_url;
                $response['name'] = $name;
				$response ['week']=$week;
				
            }
            //if some error occurred
        }catch(Exception $e){
            $response['error']=true;
            $response['message']=$e->getMessage();
			
        } 
        //closing the connection
        mysqli_close($con);
    }else{
        $response['error']=true;
        $response['message']='Please choose a file';
    }
    
    //displaying the response
    echo json_encode($response);

 
?>