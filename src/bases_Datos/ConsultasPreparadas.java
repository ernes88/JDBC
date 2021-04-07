package bases_Datos;

import java.sql.*;

public class ConsultasPreparadas {

	public static void main(String[] args) {
		try{
			//1.Creando objeto de la interfaceConnection e instanciación del mismo con método getConnection de la clase DriverManager
			Connection objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root",""); 	//se crea e inicializa un objeto de la interface Connection con el metodo getConnection(), que toma como argumentos la ruta a la base de datos, un usuario y una contraseña 
			
			//2.Creando objeto de tipo PreparedStatement e instanciarlo con el método prepareStatement de la Interface Connection. 
			PreparedStatement objPreparedStatement=objConexion.prepareStatement("SELECT * FROM profesores WHERE matricula>?");
		
			//3.Se llama al método setInt de la Interfaz PreparedStatement para establecer el valor del parámetro en la consulta preparada
			objPreparedStatement.setInt(1,25);
			
			//4.Creando objeto de la interface ResultSet e instanciación del mismo con método de la interface PreparedStatement. Cuando la consuulta es preparada se parametriza la misma en el paso 2 que se corresponde con la creación del objeto PreparedStatement, mientras que cuando no se hace como se muestra en Conexion_BD.java es acá en la creación del objeto ResultSet donde se devulve el resultado de la consulta. 
			ResultSet objResultSet=objPreparedStatement.executeQuery();
			
			//5.Recorrer el objeto objResultSet.
			while(objResultSet.next()){
				System.out.println(objResultSet.getString(1)+ " "+objResultSet.getString(2)+" "+objResultSet.getString("Asignatura")+" "+objResultSet.getInt("Matricula"));		//se puede especificar el nombre del campo de la tabla o su indice empezando por el 1. getString se puede usar para adquirir en formato texto cualquier tipo de dato de cualquier campo en la tabla sql, pero los valores numericos serán adquiridos como texto y no podremos realizaar operaciones matemáticas con ellos, para poder hacer esto último tendremos que emplear las otras variantes getDouble, getInt, getShort, etc.
			}
			
			//6.Se cambia el parámetro para cambiar la busqueda.
			System.out.println("Ampliando la búsqueda");
			System.out.println();
			
			objPreparedStatement.setInt(1,5);
			
			objResultSet=objPreparedStatement.executeQuery();
			
			while(objResultSet.next()){
				System.out.println(objResultSet.getString(1)+ " "+objResultSet.getString(2)+" "+objResultSet.getString("Asignatura")+" "+objResultSet.getInt("Matricula"));		//se puede especificar el nombre del campo de la tabla o su indice empezando por el 1. getString se puede usar para adquirir en formato texto cualquier tipo de dato de cualquier campo en la tabla sql, pero los valores numericos serán adquiridos como texto y no podremos realizaar operaciones matemáticas con ellos, para poder hacer esto último tendremos que emplear las otras variantes getDouble, getInt, getShort, etc.
			}
			
			//7.Cerrar el objeto ResultSet
			objResultSet.close();
		}
		catch(Exception e){
			System.out.println("No conecta con la base de datos");
			e.printStackTrace();
		}

	}

}
