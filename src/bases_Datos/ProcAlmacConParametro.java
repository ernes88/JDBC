package bases_Datos;
import java.sql.*;

import javax.swing.JOptionPane;

public class ProcAlmacConParametro {
	private Connection objConexion;
	
	public static void main(String[] args) {
		String alumno=JOptionPane.showInputDialog("Ingrese el alumno a modificar su nota");
		double nuevaNota=Double.parseDouble(JOptionPane.showInputDialog("Ingrese la nueva nota"));
	
		try{
			//1.Creando objeto de la interface Connection e instanciaci�n del mismo con m�todo getConnection de la clase DriverManager
			Connection objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root",""); 	//se crea e inicializa un objeto de la interface Connection con el metodo getConnection(), que toma como argumentos la ruta a la base de datos, un usuario y una contrase�a 
			
			//2.Creaci�n de un objeto CalableStatement y utilizaci�n del m�todo prepareCall
			CallableStatement procedimiento=objConexion.prepareCall("{call ActualizaAlumnos(?,?)}");
			
			//3.Empleo de los m�todo set de CallableStatement
			procedimiento.setString(1, alumno);
			procedimiento.setDouble(2, nuevaNota);
			
			//4.Se ejecuta el objeto CallableStatement que contiene el procedimiento con los par�metros pasados con los m�toos set.
			procedimiento.execute();
			System.out.println("Actualizaci�n OK");
			
			//Se imprime el estado del auto-commit que en este caso es true.
			System.out.println(objConexion.getAutoCommit());
		}	
		catch(Exception e){
			System.out.println("Error en clase ProcAlmacConParametro");
			e.printStackTrace();
		}
	}
}
