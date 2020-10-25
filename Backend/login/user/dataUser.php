<?php 
 	
	$id = $_GET['id'];
	
	require_once('koneksi.php');
	
	$sql = "SELECT * FROM userlog WHERE id=$id";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"id"=>$row['id'],
			"username"=>$row['username'],
			"password"=>$row['password'],
			"login_time"=>$row['login_time'],
			"login_state"=>$row['login_state'],
		));
 
	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>