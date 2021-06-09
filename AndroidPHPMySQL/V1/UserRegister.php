<?php
	// Main
	include_once(dirname(__FILE__)."\Includes\DbOperation.php");

	$test = new DbOperation();



	if ($_SERVER["REQUEST_METHOD"] == "POST")
	{
		if (isset($_POST["nom"]) && isset($_POST["mdp"]) && isset($_POST["mail"]) && isset($_POST["localite"]) && isset($_POST["date"]))
		{
			$result = $test->createUser($_POST["nom"], $_POST["mdp"], $_POST["mail"], $_POST["localite"], $_POST["date"]);

			switch ($result)
			{
				case 0:
					// echo "Utilisateur déjà existant ! <br>";
					$response = array("Status"=>true, "Error"=>false, "Message"=>"Utilisateur deja existant");
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
		}else{
			$response = array("Status"=>false, "Error"=>true, "Message"=>"Erreur dans la saisie !");
		}
	}else{
		$response = array("Status"=>false, "Error"=>true, "Message"=>"ERREUR !!!");
	}

	echo json_encode($response); // __!! userRegister.php est un web service rest !!__
?>