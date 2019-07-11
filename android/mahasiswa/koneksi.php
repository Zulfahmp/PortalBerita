<?php

 define('HOST','localhost');
 define('USER','root');
 define('PASS','root');
 define('DB','db_android');
 
 //membuat koneksi dengan database
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
 
?>