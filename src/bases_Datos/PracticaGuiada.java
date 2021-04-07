//Aplicación que permite buscar en la tabla profesores y buscar que profesores imparten que asignatura o viceversa 
package bases_Datos;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PracticaGuiada {

	public static void main(String[] args) {
		MarcoBD marco=new MarcoBD();
		marco.setVisible(true);
		marco.setTitle("Acceso a Bases de Datos");
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setBounds(150,150,450,400);
	}
}

class MarcoBD extends JFrame{
	public MarcoBD(){
		LaminaBD lamina=new LaminaBD();
		add(lamina);		
	}
}

class LaminaBD extends JPanel{
	private JButton consulta;
	private JTextArea area;
	private JLabel nombreP;
	private JComboBox profesores;
	private JPanel laminaAux;
	private JLabel nombreA;
	private JComboBox asignaturas;
	private String[] profes={"Todos"};
	private String[] materias={"Todas"};
	private PreparedStatement objPreparedStatement;
	private ResultSet objResultSet=null;
	private Connection objConexion;
	private Statement objConsulta;
	private final String consultaParametrizada1="SELECT * FROM profesores WHERE Nombre=? AND Asignatura=?";
	private final String consultaParametrizada2="SELECT * FROM profesores WHERE Nombre=?";
	private final String consultaParametrizada3="SELECT * FROM profesores WHERE Asignatura=?";
	private final String consultaParametrizada4="SELECT * FROM profesores";
	private String consultaParametrizada;
	private boolean bandera=false;
	private boolean coincidencia=false;
	private Statement objStatement;
	
	public LaminaBD(){
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
		
		try{
			//1.Creando objeto de la interface Connection e instanciación del mismo con método getConnection de la clase DriverManager
			objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root",""); 	//se crea e inicializa un objeto de la interface Connection con el metodo getConnection(), que toma como argumentos la ruta a la base de datos, un usuario y una contraseña 
			
			//2.Creando objeto de la interface Statement e instanciación del mismo con método de la interface Connection.
			objConsulta=objConexion.createStatement();
			
			//3.Creando objeto de la interface ResultSet e instanciación del mismo con método de la interface Statement.
			//Se extrae info de lo que hay en la columna Nombre de la tabla profesores, para con eso mas adelante rellenar el JComboBox
			String consulta1="SELECT DISTINCT Nombre FROM Profesores";	
			objResultSet=objConsulta.executeQuery(consulta1);
			
			//4.Recorrer el objeto objResultSet.
			while(objResultSet.next()){
				profesores.addItem(objResultSet.getString(1));
			}
			//5.Cierro el objeto, liberando el recurso.
			objResultSet.close();
			
			//6.Se extrae info de lo que hay en la columna Materias de la tabla profesores, para con eso mas adelante rellenar el JComboBox Asignaturas
			String consulta2="SELECT DISTINCT Asignatura FROM Profesores";
			objResultSet=objConsulta.executeQuery(consulta2);
			
			//7.Recorrer el objeto objResultSet.
			while(objResultSet.next()){
				asignaturas.addItem(objResultSet.getString(1));
			}
			//8.Cierro el objeto, liberando el recurso.
			objResultSet.close();
			objConexion.close();
		}
		catch(Exception e1){
			System.out.println("No conecta con la base de datos");
			e1.printStackTrace();
		}
		
		consulta.addActionListener(new ManejaEvento());
		
	}
	
	private class ManejaEvento implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				//1.Creando objeto de la interface Connection e instanciación del mismo con método getConnection de la clase DriverManager
				objConexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root",""); 	//se crea e inicializa un objeto de la interface Connection con el metodo getConnection(), que toma como argumentos la ruta a la base de datos, un usuario y una contraseña 
				
				//2.Chequeando que se marcó en los dos JComboBox
				if(profesores.getSelectedItem().equals("Todos")==true && asignaturas.getSelectedItem().equals("Todas")==false){
					consultaParametrizada=consultaParametrizada3;
				}
				else if(profesores.getSelectedItem().equals("Todos")==false && asignaturas.getSelectedItem().equals("Todas")==true){
					consultaParametrizada=consultaParametrizada2;
				}
				else if(profesores.getSelectedItem().equals("Todos")==false && asignaturas.getSelectedItem().equals("Todas")==false){
					consultaParametrizada=consultaParametrizada1;
				}
				else if(profesores.getSelectedItem().equals("Todos")==true && asignaturas.getSelectedItem().equals("Todas")==true){
					consultaParametrizada=consultaParametrizada4;
				}
				
				//3.Creando objeto de tipo PreparedStatement e instanciarlo con el método prepareStatement de la Interface Connection. 
				objPreparedStatement=objConexion.prepareStatement(consultaParametrizada);
			
				//4.Se llama al método setString de la Interfaz PreparedStatement para establecer el valor de los parámetro en la consulta preparada
				if(consultaParametrizada==consultaParametrizada3){
					objPreparedStatement.setString(1,(String) asignaturas.getSelectedItem());
				}
				else if(consultaParametrizada==consultaParametrizada2){
					objPreparedStatement.setString(1,(String) profesores.getSelectedItem());
				}
				else if(consultaParametrizada==consultaParametrizada4){					//si no se va a filtrar nada en ninguno de los dos campos no se usa una consulta reparada sino una normal y se genera aca mismo el ResultSet.
					objStatement=objConexion.createStatement();
					objResultSet=objStatement.executeQuery(consultaParametrizada4);
					bandera=true;
				}
				else if(consultaParametrizada==consultaParametrizada1){
					objPreparedStatement.setString(1,(String) profesores.getSelectedItem());
					objPreparedStatement.setString(2,(String) asignaturas.getSelectedItem());	
				}
				if(!bandera){		//solo entra aca a generar el ResultSet si se eligio filtrar por alguno de los dos campos.
					//5.Creando objeto de la interface ResultSet e instanciación del mismo con método de la interface PreparedStatement. Cuando la consuulta es preparada se parametriza la misma en el paso 2 que se corresponde con la creación del objeto PreparedStatement, mientras que cuando no se hace como se muestra en Conexion_BD.java es acá en la creación del objeto ResultSet donde se devuelve el resultado de la consulta. 
					objResultSet=objPreparedStatement.executeQuery();
				}
				
				//6.Se limpia el area de texto para que este vacia cada vez que le demos al boton de consulta asi cada nuevo resultset se muestra sobre un area de texto vacia.
				area.setText("");
				
				//7.Recorrer el objeto objResultSet.
				while(objResultSet.next()){
					area.append(objResultSet.getString(1)+" "+objResultSet.getString(2)+" "+objResultSet.getString("Asignatura")+" "+objResultSet.getInt("Matricula"));
					area.append("\n");
					coincidencia=true;
				}
				
				//8.Chequeo si hubo o no coincidencias en la consulta igresada en la aplicación
				if(!coincidencia){
					area.append("No hay coincidencias para los parámetros ingresados");
				}
				
				//8.Limpio las banderas
				bandera=false;
				coincidencia=false;
				
				//9.Cerrar el ResultSet
				objResultSet.close();
			
			}
			catch(Exception e1){
				System.out.println("No conecta con la base de datos");
			}		
		}	
	}
}

