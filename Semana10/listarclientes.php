<?php 
	$conn = new mysqli("localhost","id20733106_miusuario","Miclave123#","id20733106_tienda");
	$sql = "select idcliente,nombre,dni,telefono,correo,estado from cliente where estado='A'";
	$resultado=mysqli_query($conn,$sql);
	$result = array();
	while($mostrar=mysqli_fetch_array($resultado)){
		$idcliente = $mostrar['idcliente'];
		$nombre = $mostrar['nombre'];
		$dni = $mostrar['dni'];
		$telefono = $mostrar['telefono'];
		$correo = $mostrar['correo'];
		$estado = $mostrar['estado'];
		
		array_push($result,array('idcliente'=>$idcliente,'nombre'=>$nombre,'dni'=>$dni,'telefono'=>$telefono,'correo'=>$correo,'estado'=>$estado));
	}

	echo json_encode($result);
	//echo json_encode(array("result"=>$result));
?>