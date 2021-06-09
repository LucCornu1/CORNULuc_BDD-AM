<?php
	// Main
	include_once(dirname(__FILE__)."\Includes\DbOperation.php");

	$test = new DbOperation();


	if ($_SERVER["REQUEST_METHOD"] == "POST")
	{
		if (isset($_POST["nom"]))
		{
			$test->deleteUserByUsername($_POST["nom"]);
			$response = array("status"=>true, "error"=>false, "Message"=>"Utilisateur supprimé !");

		}else{
			$response = array("status"=>false, "error"=>true, "Message"=>"Erreur dans la saisie !");
		}
	}else{
		$response = array("status"=>false, "error"=>true, "Message"=>"ERREUR !!!");
	}

	echo json_encode($response); // __!! userRegister.php est un web service rest !!__
?>