<?php
	// Main
	include_once(dirname(__FILE__)."\Includes\DbOperation.php");

	$test = new DbOperation();

	if ($_SERVER["REQUEST_METHOD"] == "POST")
	{
		if (isset($_POST["nom"]) && isset($_POST["mdp"]) && isset($_POST["mail"]))
		{
			$result = $test->createUser($_POST["nom"], $_POST["mdp"], $_POST["mail"]);

			switch ($result)
			{
				case 0:
					// echo "Utilisateur déjà existant ! <br>";
					$response = array("Status"=>false, "Error"=>false, "Message"=>"Utilisateur deja existant");
					break;
				case 1:
					// echo "Utilisateur créé !<br>";
					$response = array("Status"=>true, "Error"=>false, "Message"=>"Utilisateur cree");
					break;
				case 2:
					// echo "Erreur !<br>";
					$response = array("Status"=>false, "Error"=>true, "Message"=>"Oups... Erreur");
					break;
			}

			echo json_encode($response);
		}
	}
	

	if (isset($_GET["Log_Nom"]) && isset($_GET["Log_Mdp"]))
	{
		if ($test->userLogin($_GET["Log_Nom"], $_GET["Log_Mdp"]))
		{
			echo "Utilisateur trouvé !<br>";

			$test->getUserByUsername($_GET["Log_Nom"]);
		}else{
			echo "Aucun utilisateur correspondant !<br>";
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
            <form method="POST" action="#">
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

	                <input class="btn" type="submit" name="sub" id="sup" value="se connecter">
                </fieldset>
            </form>
        </div>

		<footer>

		</footer>
	</BODY>
</HTML>