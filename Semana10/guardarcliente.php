<?php

	$data = json_decode(file_get_contents('php://input'), true);

	$nombre = $data["nombre"];
	$dni = $data["dni"];
	$telefono = $data["telefono"];
	$correo = $data["correo"];
	$estado = "A";
	
	$conn = new mysqli("localhost","id20733106_miusuario","Miclave123#","id20733106_tienda");
	$sql = "insert into cliente(nombre,dni,telefono,correo,estado) values('$nombre','$dni','$telefono','$correo','$estado')";
	mysqli_query($conn,$sql);
	echo "OK";
?>