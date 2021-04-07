//clase que se encarga de establecer la conexi�n con la base de datos
package modelo;
import java.sql.*;

public class ConexionBD {
	
	private Connection objConexion=null;
	
	public ConexionBD(){
		
	}
	public Connection dameConexion(){
		try{
			//1.Creando objeto de la interface Connection e instanciaci�n del mismo con m�todo getConnection de la clase DriverManager
			objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root",""); 	//se crea e inicializa un objeto de la interface Connection con el metodo getConnection(), que toma como argumentos la ruta a la base de datos, un usuario y una contrase�a 
		}
		
		catch(Exception e){
			System.out.println("Error en clase ConexionBD");
		}
		return objConexion;
	}
}
