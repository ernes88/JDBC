package vista;

import javax.swing.*;

import controlador.*;

public class MarcoAplicacion extends JFrame{
	
	public MarcoAplicacion(){
		Lamina_Aplicacion lamina=new Lamina_Aplicacion();
		add(lamina);
		addWindowListener(new CargaCombos(lamina));				//marco ser� la fuente, o sea cuando se abra el marco se desencadenar� el evento de ventana, y el evento ser� el definido en la clase CargaCombos
		
	}	
}
