<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$name = $_POST['name'];
		$nim = $_POST['nim'];
		$alamat = $_POST['alamat'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tb_mahasiswa (nama,nim,alamat) VALUES ('$name','$nim','$alamat')";
		
		//Import File Koneksi database
		require_once('koneksi.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Mahasiswa';
		}else{
			echo 'Gagal Menambahkan Mahasiswa';
		}
		
		mysqli_close($con);
	}
?>