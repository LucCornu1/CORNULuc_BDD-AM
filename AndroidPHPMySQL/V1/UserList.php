<?php
	// Main
	include_once(dirname(__FILE__)."\Includes\DbOperation.php");

	$test = new DbOperation();

	if ($_SERVER["REQUEST_METHOD"] == "GET")
	{
		if (isset($_GET["Log_Nom"]) && isset($_GET["Log_Mdp"]))
		{
			if ($test->userLogin($_GET["Log_Nom"], $_GET["Log_Mdp"]))
			{
				$result = $test->getUserByUsername($_GET["Log_Nom"]);

				$response["status"] = true;
				$response["error"] = false;
				$response["Message"] = "Connexion réussie";
				$response["Nom"] = $result["Username"];
				$response["Mail"] = $result["Email"];
				$response["Localite"] = $result["Localite"];

				// $test->getUserByUsername($_GET["Log_Nom"]);
			}else{
				$response["status"] = false;
				$response["error"] = false;
				$response["Message"] = "Couple nom d'utilisateur mot de passe invalide.";
			}
		}else{
			$response["status"] = false;
			$response["error"] = false;
			$response["Message"] = "Champs incomplets";
		}
	}else{
		$response["status"] = false;
		$response["error"] = false;
		$response["Message"] = "Pas la bonne méthode";
	}

	echo json_encode($response);
?>