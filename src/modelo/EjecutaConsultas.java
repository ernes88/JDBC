//Clase que se encargará de extraer la info de la base de datos y almacenarla en el ResultSet.
package modelo;
import java.sql.*;

public class EjecutaConsultas {

	private ConexionBD miConexion;
	private ResultSet objResultSet;
	private Statement objConsulta;
	private Connection accesoBD;
	private Profesores misProfesores;
	
	public EjecutaConsultas(){
		miConexion=new ConexionBD();
	}
	
	public ResultSet getObjResultSet() {
		return objResultSet;
	}
	
	public String consultaNombres(){
		misProfesores=null;
		accesoBD=miConexion.dameConexion();
		misProfesores=new Profesores();
		
		try{
			objConsulta=accesoBD.createStatement();
			String consulta1="SELECT DISTINCT Nombre FROM Profesores";
			objResultSet=objConsulta.executeQuery(consulta1);
			misProfesores.setNombre(objResultSet.getString(1));
			//objResultSet.close();
		}
		catch(Exception e){
			System.out.println("Error en el método consultaNombres");
			e.printStackTrace();
		}
		
		return misProfesores.getNombre();
	}
	
	public String consultaAsignaturas(){
		misProfesores=null;
		accesoBD=miConexion.dameConexion();
		misProfesores=new Profesores();
		
		try{
			objConsulta=accesoBD.createStatement();
			String consulta2="SELECT DISTINCT Asignatura FROM Profesores";
			objResultSet=objConsulta.executeQuery(consulta2);
			misProfesores.setAsignatura(objResultSet.getString(1));	
			objResultSet.close();
		}
		catch(Exception e){
			System.out.println("Error en el método consultaAsignaturas");
			e.printStackTrace();
		}
		return misProfesores.getAsignatura();
	}
}
