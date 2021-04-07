package bases_Datos;
import java.sql.*;

public class ProcedimientoAlmacenado {
	
	
	public static void main(String[] args) {
		Connection objConexion=null;
		
		try{
			//1.Creando objeto de la interface Connection e instanciación del mismo con método getConnection de la clase DriverManager
			objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root",""); 	//se crea e inicializa un objeto de la interface Connection con el metodo getConnection(), que toma como argumentos la ruta a la base de datos, un usuario y una contraseña 
			
			//2.Creación de un objeto CalableStatement y utilización del método prepareCall
			CallableStatement procedimiento1=objConexion.prepareCall("{call MuestraAlumnosTop}");
			CallableStatement procedimiento2=objConexion.prepareCall("{call ActualizaNotas}");
			
			//3.Creación del ResultSet
			ResultSet rs=procedimiento1.executeQuery();		
			
			//4.Recorrerlo y obtener su info y sacarla por consola
			while(rs.next()){
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getDouble(4));
			}
			
			procedimiento2.executeQuery();						//se llama a este procedimiento que actualiza las notas en la tabla Alumnos de la base de datos Academia. No adquiero info en este caso en rs porque este procedimiento no involucra a una sentencia SELECT.
			
			System.out.println();
			System.out.println("Se actualizan las notas");
			
			rs=procedimiento1.executeQuery();					//se genera rs de vuelta ya que se utiliza procedimiento1 que es un SELECT, o que permitirá almacenar la BD modificada por procedimiento2
			
			while(rs.next()){
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getDouble(4));
			}
			
			//5.Cerrar el ResultSet.
			rs.close();
		}	
		catch(Exception e){
			System.out.println("Error en clase ConexionBD");
			e.printStackTrace();
		}

	}

}
