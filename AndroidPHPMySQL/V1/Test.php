<?php
function CreateTable($DB)
{
	$req = "CREATE TABLE USERS(Username varchar(25) not null, Password TEXT(15) not null,
	Email varchar(20)not null, primary key(username))Engine=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_cs;";

	try{
		$stmt = $DB->prepare($req);
		$stmt->execute();
	}catch(PDOException $error){
		echo "<script>console.log('".$error->getMessage()."')</script>";
		exit();
	}

	return;
}

function CreateUser($username, $pass, $email, $DB)
{	
	if (UserExist($username, $email, $DB))
	{
		return 0;
	}

	$req = "INSERT INTO USERS(USERNAME, PASSWORD, EMAIL) VALUES (?,?,?);";
	$password = md5($pass);

	//Envoie de la requête à la base
	try
	{
		$stmt = $DB->prepare($req);

		$stmt->bind_param("sss", $username, $password, $email);

		if ($stmt->execute())
		{
			return 1;
		}
		return 2;
	}
	catch(PDOException $error)
	{
		echo "<script>console.log('".$error->getMessage()."')</script>";
		exit();
	}
}

function UserExist($username, $email, $DB)
{
	$req = "SELECT * FROM USERS where USERNAME = ? OR EMAIL = ?";

	//Envoie de la requête à la base
	try
	{
		$stmt = $DB->prepare($req);

		$stmt->bind_param("ss", $username, $email);

		$stmt->execute();
		$stmt->store_result();

		return $stmt->num_rows() > 0;
	}
	catch(PDOException $error)
	{
		echo "<script>console.log('".$error->getMessage()."')</script>";
		exit();
	}
}

function UserLogin($username, $pass, $DB)
{
	$req = "SELECT * FROM USERS where USERNAME = ? AND PASSWORD = ?;";
	$password = md5($pass);

	//Envoie de la requête à la base
	try
	{
		$stmt = $DB->prepare($req);

		$stmt->bind_param("ss", $username, $password);

		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows() > 0;

	}
	catch(PDOException $error)
	{
		echo "<script>console.log('".$error->getMessage()."')</script>";
		exit();
	}
}

function GetUserByUsername($username, $DB)
{
	$req = "SELECT * FROM USERS where USERNAME = ?;";

	//Envoie de la requête à la base
	try
	{
		$stmt = $DB->prepare($req);

		$stmt->bind_param("s", $username);

		$stmt->execute();
		$array = $stmt->get_result()->fetch_assoc();

		echo "<br>";
		foreach ($array as $key => $value)
		{
			echo $key." : ";
			echo $value."<br>";
		}

		return 1;
	}
	catch(PDOException $error)
	{
		echo "<script>console.log('".$error->getMessage()."')</script>";
		exit();
	}
}


	// Main
	require_once("./Includes/DbConnect.php");

	$test = new DbConnect();

	$DB = $test->connect();
	// CreateTable($DB);

	if (isset($_GET["nom"]) && isset($_GET["mdp"]) && isset($_GET["mail"]))
	{
		$return = CreateUser($_GET["nom"], $_GET["mdp"], $_GET["mail"], $DB);

		if ($return == 0)
		{
			echo "Utilisateur déjà existant ! <br>";
		}elseif ($return == 1){
			echo "Utilisateur créé !<br>";
		}else{
			echo "Erreur !";
		}
	}

	if (isset($_GET["Log_Nom"]) && isset($_GET["Log_Mdp"]))
	{
		if (UserLogin($_GET["Log_Nom"], $_GET["Log_Mdp"], $DB))
		{
			echo "Utilisateur trouvé !";

			GetUserByUsername($_GET["Log_Nom"], $DB);
		}else{
			echo "Aucun utilisateur correspondant !";
		}
	}
?>


<!DOCTYPE html>
<HTML>
	<HEAD>
		<meta charset="utf-8"/>
		<title>Inscription</title>
		<lang = fr/>
		<style type="text/css"></style>
		<link rel="stylesheet" type="text/css" href="./CSS/LUCAS_style.css"> 
		<link rel="shortcut icon" type="image/x-icon" href="">     
	</HEAD>
	<BODY>
		<br><br><br>

		<div class="container">
            <form method="GET" action="#">
            	<fieldset>
	            	<legend class="fieldsetLegend">Inscription</legend>
	                <label class="cLabel" for="nom">Pseudo : </label><input class="textInput" type="text"  id="pseudo" name="nom" placeholder="Pseudo" onchange="" required><br>

	                <label class="cLabel" for="mdp"> Mot de Passe : </label><input class="textInput" type="password" id="pass" name="mdp" placeholder="password" required><br>

	                <label class="cLabel" for="mail"> Email : </label><input class="textInput" type="mail" id="Email" name="mail" placeholder="Email" required><br>

	                <input class="btn" type="submit" name="sub" id="sup" value="s'inscrire">
	            </fieldset>
            </form>
        
            <form method="GET" action="#">
            	<fieldset>
	            	<legend class="fieldsetLegend">Login</legend>
	                <label class="cLabel" for="nom">Pseudo : </label><input class="textInput" type="text"  id="pseudo" name="Log_Nom" placeholder="Pseudo" onchange="" required><br>

	                <label class="cLabel" for="mdp"> Mot de Passe : </label><input class="textInput" type="password" id="pass" name="Log_Mdp" placeholder="password" required><br>

	                <input class="btn" type="submit" name="sub" id="sup" value="s'inscrire">
                </fieldset>
            </form>
        </div>

		<footer>

		</footer>
	</BODY>
</HTML>