<?PHP
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "Klasse";
define('FIREBASE_API_KEY','AAAATOU53Lc:APA91bEdrt5eo0ad6G7Vg7LdSHN-BDqh9I2PGxgAoh3-R7k4TrBWK1ohm7sn-U_fjuagWFzcsbBQNWHstxI39Kdv01i-WMLd1o3cjRfsTlohKpRTNpXIZMUUT_n23-Nwj4ElGBt6Lwtv');
$con = mysqli_connect($servername, $username, $password, $dbname);
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
    } 
?>