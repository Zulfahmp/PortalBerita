<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		//MEndapatkan Nilai Dari Variable 
		$id = $_POST['id'];
		$name = $_POST['name'];
		$nim = $_POST['nim'];
		$alamat = $_POST['alamat'];
		
		//import file koneksi database 
		require_once('koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE tb_mahasiswa SET nama = '$name', nim = '$nim', alamat = '$alamat' WHERE id = $id;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Mahasiswa';
		}else{
			echo 'Gagal Update Data Mahasiswa';
		}
		
		mysqli_close($con);
	}
?>