package metadatos;
import java.sql.*;

public class Info_Metadatos {

	public static void main(String[] args) {
		mostrar_metadatos();
		System.out.println();
		mostrar_info_tablas();
		System.out.println();
		mostrar_info_columnas();
	}
	
	public static void mostrar_metadatos(){
		Connection objConexion=null;
		try {
			//1.Objeto Connection
			objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root","");
			
			//2.Obtenci�n del objeto de la interface DatabaseMetaData utilizando el m�todo getMetaData() de la interface Connection
			DatabaseMetaData metadatos=objConexion.getMetaData();
			
			//3. Obtenci�n de los metadatos de la base de datos utilizando los m�todos de la interface DatabaseMetaData
			//extrae nombre del driver
			String nombreDriver=metadatos.getDriverName();
			
			//extrae la versi�n del driver
			String versionDriver=metadatos.getDriverVersion();
			
			//extrae el nombre de la base de datos
			String nombreBD=metadatos.getDatabaseProductName();
			
			//extrae la versi�n de la base de datos
			String versionDB=metadatos.getDatabaseProductVersion();
			
			System.out.println("Los datos de la Base de datos son:" 
					+ "\nNombre de la base de datos: "+nombreBD+"\nNombre del Driver: "+nombreDriver
							+"\nVersi�n del Driver: "+versionDriver+ "\nVersi�n de la BD: "+versionDB);
			
		} 
		catch (SQLException e) {
			
			System.out.println("Error en el m�todo mostrar_metadatos()");
		}
		finally{
			try {
				objConexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void mostrar_info_tablas(){
		Connection objConexion=null;
		ResultSet descripcionTablas=null;
		try {
			//1.Objeto Connection
			objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root","");
			
			//2.Obtenci�n del objeto de la interface DatabaseMetaData utilizando el m�todo getMetaData() de la interface Connection
			DatabaseMetaData metadatos=objConexion.getMetaData();
			
			//3. Obtenci�n de los metadatos de la bae de datos utilizando los m�todos de la interface DatabaseMetaData
			System.out.println("Lista de tablas");
			
			//4.Creaci�n de un ResultSet para contener la info de las tablas obtenido con getTables()
			descripcionTablas=metadatos.getTables(null, null, null, null);
			
			//5.Recorrer el ResultSet en busca de los metadatos extraidos.
			while(descripcionTablas.next()){
				System.out.println(descripcionTablas.getString("TABLE_NAME"));
			}
		} 
		catch (SQLException e) {
			
			System.out.println("Error en el m�todo mostrar_metadatos()");
		}
		finally{
			try {
				objConexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void mostrar_info_columnas(){
		Connection objConexion=null;
		ResultSet descripcionColumnas=null;
		try {
			//1.Objeto Connection
			objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root","");
			
			//2.Obtenci�n del objeto de la interface DatabaseMetaData utilizando el m�todo getMetaData() de la interface Connection
			DatabaseMetaData metadatos=objConexion.getMetaData();
			
			//3. Obtenci�n de los metadatos de la bae de datos utilizando los m�todos de la interface DatabaseMetaData
			System.out.println("Lista de Columnas de la tabla alumnos");
			
			//4.Creaci�n de un ResultSet para contener la info de las columnas de a taba alumnos, ya que se restringi� la busqueda dentro de metadatos a solo esta tabla especificandola en el tercer argumento de la llamada a getCOlumns, que es el que especifica un patron en el nombre de la tabla.
			descripcionColumnas=metadatos.getColumns(null, null, "alumnos", null);
			
			//5.Recorrer el ResultSet en busca de os metadatos extraidos.
			while(descripcionColumnas.next()){
				System.out.println(descripcionColumnas.getString("COLUMN_NAME"));
			}
		} 
		catch (SQLException e) {
			
			System.out.println("Error en el m�todo mostrar_metadatos()");
		}
		finally{
			try {
				objConexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
