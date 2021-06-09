<?php

Class DbOperation 
{
	private $conn;

	public function __construct()
	{
		include_once(dirname(__FILE__)."\DbConnect.php");

		$DB = new DbConnect();
		$this->conn = $DB->connect();
	}


	// Fonctions de traitement
	public function userExist($username, $email)
	{
		$req = "SELECT * FROM USERS where USERNAME = ? OR EMAIL = ?";

		//Envoie de la requête à la base
		try
		{
			$stmt = $this->conn->prepare($req);

			$stmt->bind_param("ss", $username, $email);

			$stmt->execute();
			$stmt->store_result();

			return $stmt->num_rows() > 0;
		}
		catch(PDOException $error)
		{
			// echo "<script>console.log('".$error->getMessage()."')</script>";
			exit();
		}
	}

	public function createUser($username, $pass, $email, $localite, $date)
	{	
		if ($this->userExist($username, $email))
		{
			return 0;
		}

		$req = "INSERT INTO USERS(USERNAME, PASSWORD, EMAIL, LOCALITE) VALUES (?,?,?,?);";
		$password = md5($pass);

		//Envoie de la requête à la base
		try
		{
			$stmt = $this->conn->prepare($req);

			$stmt->bind_param("ssss", $username, $password, $email, $localite);

			if ($stmt->execute())
			{
				return 1;
			}
			return 2;
		}
		catch(PDOException $error)
		{
			// echo "<script>console.log('".$error->getMessage()."')</script>";
			exit();
		}
	}

	public function userLogin($username, $pass)
	{
		$req = "SELECT * FROM USERS where USERNAME = ? AND PASSWORD = ?;";
		$password = md5($pass);

		//Envoie de la requête à la base
		try
		{
			$stmt = $this->conn->prepare($req);

			$stmt->bind_param("ss", $username, $password);

			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows() > 0;

		}
		catch(PDOException $error)
		{
			// echo "<script>console.log('".$error->getMessage()."')</script>";
			exit();
		}
	}

	public function getUserByUsername($username)
	{
		$req = "SELECT * FROM USERS where USERNAME = ?;";

		//Envoie de la requête à la base
		try
		{
			$stmt = $this->conn->prepare($req);

			$stmt->bind_param("s", $username);

			$stmt->execute();
			$array = $stmt->get_result()->fetch_assoc();

			// echo "<br>";
			/*foreach ($array as $key => $value)
			{
				// echo $key." : ";
				// echo $value."<br>";
			}*/

			return $array;
		}
		catch(PDOException $error)
		{
			// echo "<script>console.log('".$error->getMessage()."')</script>";
			exit();
		}
	}

	public function getUsersByCriteria($username, $email, $localite)
	{
		$req = "SELECT USERNAME, EMAIL, LOCALITE FROM USERS WHERE (USERNAME LIKE '%".$username."%') AND (EMAIL LIKE '%".$email."%') AND (LOCALITE LIKE '%".$localite."%')";

		// echo $req;

		//Envoie de la requête à la base
		try
		{
			$stmt = $this->conn->prepare($req);

			$stmt->execute();

			$array = array();
			$result = $stmt->get_result();
			
			while ($row = $result->fetch_assoc()) // fetch_assoc() vide le stmt, il faut donc le stocker
			{
				array_push($array, $row);
			}

			return $array;
		}
		catch(PDOException $error)
		{
			// echo "<script>console.log('".$error->getMessage()."')</script>";
			exit();
		}
	}


	public function deleteUserByUsername($username)
	{
		$req = "DELETE FROM USERS WHERE USERNAME = ?;";

		//Envoie de la requête à la base
		try
		{
			$stmt = $this->conn->prepare($req);

			$stmt->bind_param("s", $username);

			$stmt->execute();

			// echo "<br>";
			/*foreach ($array as $key => $value)
			{
				// echo $key." : ";
				// echo $value."<br>";
			}*/

			return true;
		}
		catch(PDOException $error)
		{
			// echo "<script>console.log('".$error->getMessage()."')</script>";
			exit();
		}
	}
}

?>