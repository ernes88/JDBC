package bases_Datos;
import java.sql.*;

public class Modificando_BD {

	public static void main(String[] args) {
		
		try{
			//1.Creando objeto de la interfaceConnection e instanciación del mismo con método de la clase DriverManager
			Connection objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root",""); 	//se crea e inicializa un objeto de la interface Connection con el metodo getConnection(), que toma como argumentos la ruta a la base de datos, un usuario y una contraseña 
			
			//2.Creando objeto de la interface Statement e instanciación del mismo con método de la interface Connection.
			Statement objConsulta=objConexion.createStatement();
			
			//3.Insertando valores en una tabla
			String nuevoProfe="INSERT INTO profesores VALUES(6,'Pedro','Historia',5)";		//Esta String representará la consulta, los valores a insertar en los campos numericos dentro de la tabla se escriben tal cual, pero los campos de texto irán entre comillas simples. 
			String actualizaID1="UPDATE alumnos SET Notas=95 WHERE id=1";
			String actualizaID2="UPDATE alumnos SET Notas=85 WHERE id=2";
			String borraRegistro="DELETE FROM profesores WHERE id=6";
			
			//objConsulta.executeUpdate(nuevoProfe);			//cada vez que se ejecute esta linea inserta un nuevo registro en la tabla.
			//objConsulta.executeUpdate(actualizaID1);			//aca lo mismo, una vez actualizado se comentan
			//objConsulta.executeUpdate(actualizaID2);
			//objConsulta.executeUpdate(borraRegistro);
			
			//insertando una consulta preparada
			String otroProfe="INSERT INTO profesores VALUES(?,'Juana','Java',65)";
			PreparedStatement objPreparedStatement=objConexion.prepareStatement(otroProfe);
			objPreparedStatement.setInt(1,7);
			ResultSet objResultSet=objPreparedStatement.executeQuery();
			
			while(objResultSet.next()){
				System.out.println(objResultSet.getString(1)+ " "+objResultSet.getString(2)+" "+objResultSet.getString("Asignatura")+" "+objResultSet.getInt("Matricula"));		//se puede especificar el nombre del campo de la tabla o su indice empezando por el 1. getString se puede usar para adquirir en formato texto cualquier tipo de dato de cualquier campo en la tabla sql, pero los valores numericos serán adquiridos como texto y no podremos realizaar operaciones matemáticas con ellos, para poder hacer esto último tendremos que emplear las otras variantes getDouble, getInt, getShort, etc.
			}
			
			System.out.println("Datos modificados correctamente");		
		}
		catch(Exception e){
			System.out.println("No conecta con la base de datos");
			e.printStackTrace();
		}
	}
}
