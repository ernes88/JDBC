package vista;

import javax.swing.*;

import controlador.*;

public class MarcoAplicacion extends JFrame{
	
	public MarcoAplicacion(){
		Lamina_Aplicacion lamina=new Lamina_Aplicacion();
		add(lamina);
		addWindowListener(new CargaCombos(lamina));				//marco será la fuente, o sea cuando se abra el marco se desencadenará el evento de ventana, y el evento será el definido en la clase CargaCombos
		
	}	
}
