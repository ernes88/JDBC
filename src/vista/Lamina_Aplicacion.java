//Modulo de la GUI del proyecto PracticaGuiada.
package vista;

import controlador.*;
import java.awt.*;
import javax.swing.*;
import modelo.*;

public class Lamina_Aplicacion extends JPanel{
	private JButton consulta;
	public JButton getConsulta() {
		return consulta;
	}

	private JTextArea area;
	private JLabel nombreP;
	private JComboBox profesores;
	private JPanel laminaAux;
	private JLabel nombreA;
	private JComboBox asignaturas;
	private String[] profes={"Todos"};
	private String[] materias={"Todas"};
	
	public JComboBox getProfesores() {
		return profesores;
	}

	public JComboBox getAsignaturas() {
		return asignaturas;
	}
	
	public JTextArea getArea() {
		return area;
	}
	
	public Lamina_Aplicacion(){
		setLayout(new BorderLayout());
		consulta=new JButton("Consulta");
		add(consulta,BorderLayout.SOUTH);
		
		area=new JTextArea(100,100);
		add(area,BorderLayout.CENTER);
		
		laminaAux=new JPanel();
		add(laminaAux,BorderLayout.NORTH);
	
		nombreP=new JLabel("Nombre Profesor");
		laminaAux.add(nombreP);
	 
		profesores=new JComboBox(profes);
		profesores.setEditable(false);
		laminaAux.add(profesores);
		
		nombreA=new JLabel("Nombre Asignatura");
		laminaAux.add(nombreA);
	
		asignaturas=new JComboBox(materias);
		asignaturas.setEditable(false);
		laminaAux.add(asignaturas);	
		/*
		//se pone al boton consulta a la escucha de un evento de tipo Action donde la accion a generar esta definida dentro de la clase ControlaEventoBotonConsulta.
		consulta.addActionListener(new ControlaEventoBotonConsulta(this));*/
	}
}