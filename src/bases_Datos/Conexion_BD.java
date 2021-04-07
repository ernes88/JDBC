package bases_Datos;

import java.sql.*;

public class Conexion_BD {

	public static void main(String[] args) {
		try{
			//1.Creando objeto de la interfaceConnection e instanciación del mismo con método de la clase DriverManager
			Connection objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root",""); 	//se crea e inicializa un objeto de la interface Connection con el metodo getConnection(), que toma como argumentos la ruta a la base de datos, un usuario y una contraseña 
			
			//2.Creando objeto de la interface Statement e instanciación del mismo con método de la interface Connection.
			Statement objConsulta=objConexion.createStatement();
			
			//3.Creando objeto de la interface ResultSet e instanciación del mismo con método de la interface Statement.
			ResultSet objResultSet=objConsulta.executeQuery("SELECT * FROM PROFESORES");
			
			//4.Recorrer el objeto objResultSet.
			while(objResultSet.next()){
				System.out.println(objResultSet.getString(1)+ " "+objResultSet.getString(2)+" "+objResultSet.getString("Asignatura"));		//se puede establecer el nombre del campo de la tabla o su indice empezando por el 1. getString se puede usar para adquirir en formato texto cualquier tipo de dato de cualquier campo en la tabla sql, pero os valores numericos serán adquiridos como texto y no podremos realizaar operaciones matemáticas con ellos, para poder hacer esto último tendremos que emplear las otras variantes getDouble, getInt, getShort, etc.
			}
			
			//5.Cerrar el objeto ResultSet
			objResultSet.close();
		}
		catch(Exception e){
			System.out.println("No conecta con la base de datos");
			e.printStackTrace();
		}

	}

}
