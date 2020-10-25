<?php
 	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$username = $_POST['username'];
		$password = $_POST['password'];
		$login_time = $_POST['login_time'];
		$login_state = $_POST['login_state'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO userlogin (username,password,login_time,login_state) VALUES ('$username','$password','$login_time','$login_state')";
		
		//Import File Koneksi database
		require_once('koneksi.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan User';
		}else{
			echo 'Gagal Menambahkan User';
		}
		
		mysqli_close($con);
	}
?>