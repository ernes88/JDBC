package bases_Datos;
import java.sql.*;

public class Transacciones {

	public static void main(String[] args) {
		Connection objConexion=null;
		try{
			//1.Creando objeto de la interface Connection e instanciaci�n del mismo con m�todo getConnection de la clase DriverManager
			objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root",""); 	//se crea e inicializa un objeto de la interface Connection con el metodo getConnection(), que toma como argumentos la ruta a la base de datos, un usuario y una contrase�a 
			
			//2.Habilitando una transacci�n.
			objConexion.setAutoCommit(false);
			
			//3.Creando objeto de la interface Statement e instanciaci�n del mismo con m�todo de la interface Connection.
			Statement objConsulta=objConexion.createStatement();
			
			//4.Insertando valores en una tabla y ejecutando la actualizaci�n.
			//Ac� la idea es que solo se inserte el alumno Daniel (en la tabla de alumnos que estan estudiando), ya que quiere comenzar su curso de Algebra, solo si hay un profesor de Algebra disponible. Por tanto solo si se inserta en la tabla profesores un profesor de Algebra, se insertar�a el estudiante, por tanto estas 4 instrucciones sql deben conformar una transacci�n. 
			String nuevoProfe="INSERT INTO profesores VALUES(8,'Gabriel','Algebra',1)";
			objConsulta.executeUpdate(nuevoProfe);
			
			String nuevoAlumno="INSERT INTO alumnos VALUES(5,'Daniel','Algebra',76)";		//OJO, SI SE EFECTUA UNA MODIFICACION EN LA TABLA COMO POR EJEMPLO A�ADIR UNA NUEVA COLUMNA, PUES ESTE PROGRAMA DARIA ERROR.
			objConsulta.executeUpdate(nuevoAlumno);
			
			//5.Con esto se da el OK al bloque de instrucciones anterior que se comportar�n como una transacci�n.
			objConexion.commit();

			System.out.println("Datos modificados correctamente");
		}
		catch(SQLException e){
			
			try {
				objConexion.rollback();
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Error en la clase Transacciones");
		}
	}

}
