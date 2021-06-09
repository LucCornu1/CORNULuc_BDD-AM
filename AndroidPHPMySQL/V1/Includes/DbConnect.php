<?php

Class DbConnect {
	// objet pour la connection
	private $conn;

	public function __construct() {}

	public function connect()
	{
		include_once("Constantes.php");

		$this->conn = new mysqli(SERVER, USER, PASSWORD, BASE); // return mysqli_connect_errno()

		// Check connection
		if (mysqli_connect_errno()) 
		{
			// die("Connection failed: ".mysqli_connect_err());
			// echo "Connection failed: ".mysqli_connect_err();
		}else{
			// echo "Connected successfully <br>";
		}

		return $this->conn;
	}


	public function getConn()
	{
		return $this->conn;
	}
}














/*
define('BASE','ormcomcplh778');
define('SERVER','ormcomcplh778.mysql.db');
define('USER','ormcomcplh778');
define('PASSWD','R8frzgZzN5S8');

//define('BASE','DB_SALON');
//define('SERVER','localhost');
//define('USER','root');
//define('PASSWD','');

function connect_bd()
{
	$dsn = "mysql:dbname=".BASE.";host=".SERVER;
	try
	{
		$connexion = new PDO($dsn, USER, PASSWD);
		echo "<script>console.log('Connexion réussie')</script>";
		return $connexion;
	}
	catch(PDOException $e)
	{
		printf("Echec de la connexion : %s\n", $e->getMessage());
		exit();
	}
}*/
?>