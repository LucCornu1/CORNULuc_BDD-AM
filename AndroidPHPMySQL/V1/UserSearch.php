<?php
	// Main
	include_once(dirname(__FILE__)."\Includes\DbOperation.php");

	$test = new DbOperation();


	if ($_SERVER["REQUEST_METHOD"] == "GET")
	{
		if (isset($_GET["nom"]) || isset($_GET["mail"]) || isset($_GET["localite"]))
		{
			if (isset($_GET["nom"]) && !empty($_GET["nom"]))
			{
				$name = $_GET["nom"];
			}else{
				$name = "%";
			}

			if (isset($_GET["mail"]) && !empty($_GET["mail"]))
			{
				$mail = $_GET["mail"];
			}else{
				$mail= "%";
			}

			if (isset($_GET["localite"]) && !empty($_GET["localite"]))
			{
				$local = $_GET["localite"];
			}else{
				$local = "%";
			}

			$result = $test->getUsersByCriteria($name, $mail, $local);

			$response["status"] = true;
			$response["error"] = false;
			$response["Message"] = "Recherche terminée";
			$response["Resultat"] = $result;

		}else{
			$response = array("status"=>false, "error"=>true, "Message"=>"Erreur dans la saisie !");
		}
	}else{
		$response = array("status"=>false, "error"=>true, "Message"=>"ERREUR !!!");
	}

	echo json_encode($response); // __!! userRegister.php est un web service rest !!__
?>