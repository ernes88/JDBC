//Esta clase servirá para cargar la info resultado de la consulta echa por la clase EjecutaConsulta, que se encontrará en ResultSet en los combos de Marco_Aplicacion.
package controlador;
import vista.*;
import modelo.*;
import java.awt.event.*;
import javax.swing.*;

public class CargaCombos extends WindowAdapter{
	
	private Lamina_Aplicacion lamina2;	
	private EjecutaConsultas consulta=new EjecutaConsultas();
	
	//para poder cargar los JComboBox de la clase Marco_Aplicacion voy a necesitar pasarle a la clase CargaCombos y objeto de la clase Marco_Aplicacion, para lo cual hay que crear un constructor de CargaCombos
	public CargaCombos(Lamina_Aplicacion lamina2){
		this.lamina2=lamina2;
	}

	@Override
	public void windowOpened(WindowEvent e) {		
		consulta.consultaNombres();																//Acá logro cargar la info de la base de datos para el campo Nombres de dicha base de datos en el objeto ResultSet de la clase EjecutaConsultas
		
		try{
			while(consulta.getObjResultSet().next()){											//como objResultSet de EjecutaConsultas es private tengo que emplear el getter correspondiente
				lamina2.getProfesores().addItem(consulta.getObjResultSet().getString(1));		//Acá voy llenando a cada vuelta de bucle el JComboBox profesores. Como es private empleo su getter correspondiente
			}
			consulta.consultaAsignaturas();	
			
			while(consulta.getObjResultSet().next()){											//como objResultSet de EjecutaConsultas es private tengo que emplear el getter correspondiente
				lamina2.getAsignaturas().addItem(consulta.getObjResultSet().getString(1));		//Acá voy llenando a cada vuelta de bucle el JComboBox asignatuuras. Como es private empleo su getter correspondiente
			}	
		}
		catch(Exception e2){
			System.out.println("Error en clase CargaCombos");
		}
	} 
}
