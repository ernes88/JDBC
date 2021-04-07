package aplicacionFinal;

import java.sql.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class AplicacionUniversal {

	public static void main(String[] args) {
		MarcoAplicacion marco=new MarcoAplicacion();
		marco.setVisible(true);
		marco.setTitle("Aplicaci�n Gen�rica");
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setBounds(200,200,500,300);	
	}
}

class MarcoAplicacion extends JFrame{
	
	public MarcoAplicacion(){
		LaminaAplicacion lamina=new LaminaAplicacion();
		add(lamina);
	}
}

class LaminaAplicacion extends JPanel{
	//declaraci�n de los objetos
	private JPanel laminaSup;
	private JPanel laminaCen;
	private JTextArea area;
	private JComboBox combo;
	private JLabel tablas;
	private JLabel baseDatos;
	private String[] rutaBD;
	private String ruta=null;
	private String usuario=null;
	private String pass=null;
	private Connection objConnection=null;
	private DatabaseMetaData objDatabaseMetaData=null;
	private ResultSet objResultSet_BD=null;
	private Statement objStatement=null;
	private ResultSet objResultSet_Tabla=null;
	//private ResultSet descripcionColumnas=null;
	private ResultSetMetaData objResultSetMetaData=null;
	private ArrayList<Character>nombreBD;
	private String nomBD;
	private String rutaCompleta=null;
	
	public LaminaAplicacion(){	
		
		//Inicializaci�n de los objetos
		laminaSup=new JPanel();
		laminaCen=new JPanel();
		area=new JTextArea(30,40);
		combo=new JComboBox();
		combo.setEditable(false);
		tablas=new JLabel("Tablas:");
		
		//Se establece el layout para las laminas 
		setLayout(new BorderLayout());
		
		//se a�aden los componentes a las laminas
		add(laminaSup,BorderLayout.NORTH);
		add(laminaCen,BorderLayout.CENTER);
		
		laminaSup.add(tablas);
		laminaSup.add(combo);	
		laminaCen.add(area);
		
		//se llama al m�todo que adquiere la ruta del archivo que la contiene. 
		rutaCompleta=exploraArchivos("D:/Erne stuffs/Programaci�n/Java","txt",this);
		
		//llamada a este m�todo que carga los nombres de las tablas.Al llamarlo antes del m�too que gestiona el evento click en el combo, pues hace que cuando se ejecuta la aplicaci�n no sale el contenido de la primera tabla del desplegable ya mostrada en el area de texto. 
		obtenerTablas();
		
		baseDatos=new JLabel("Base de Datos: "+buscaNombreBD(ruta));
		laminaSup.add(baseDatos);
		
		//Se gestiona evento de tipo click sobre cualquier elemento del JComboBox utilizando una clase interna an�nima
		manejaEventoClick(combo);
		
		//se llama al m�todo que obtiene el nombre de la base de datos a partir de la String que contiene la ruta de conexi�n
		buscaNombreBD(ruta);	
	}
	
	//M�todo que maneja el evento de tipo click sobre los desplegables del JComboBox, adem�s desde el se llama al m�todo que muestra la informaci�n de las tablas
	public void manejaEventoClick(JComboBox combo){
		combo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String opcionPulsada=(String)combo.getSelectedItem();		//se guarda en una String la opcion escogida por el usuario.
				
				mostrarInfoTablas(opcionPulsada);
			}		
		});
	}
	
	//M�todo que establece la conexi�n co la base de datos, para lo cual llama al m�todo que se encarga de leer el fichero externo que tiene los datos para dicha conexi�n,  
	public void estableceConexion(){
	//se llama al m�todo que lee la informaci�n del .txt externo
		rutaBD=new String[3];
		rutaBD=leeFichero(rutaCompleta);
		ruta=rutaBD[0];
		usuario=rutaBD[1];
		pass=rutaBD[2];
		
		try {
			//se inicia la cadena de conexi�n con la base de datos
			objConnection=DriverManager.getConnection(ruta,usuario,pass);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this,"Error en el metodo estableceConexion");
		}
	}
	
	//m�todo que se encarga de nada mas abra la aplicaci�n cargar los nombres de las tablas en el JComboBox, se llama en el constructor de la l�mina
	public void obtenerTablas() {
		
		estableceConexion();
		try {
			//obtenci�n de los metadatos utilizando el m�todo getMetaData() de la interface Connection.
			objDatabaseMetaData=objConnection.getMetaData();
			
			//Creaci�n de un ResultSet para contener la info de las tablas obtenido con getTables()
			objResultSet_BD=objDatabaseMetaData.getTables(null, null, null, null);
				
			while(objResultSet_BD.next()){
				combo.addItem(objResultSet_BD.getString("TABLE_NAME"));
			}
			
			
		} 
		catch (SQLException e1) {
			JOptionPane.showMessageDialog(this,"Error en el m�todo obtenerTablas");
			e1.printStackTrace();
		}
	}

	//M�todo que se encargar� de leer con un buffer l�nea a l�nea la informaci�n del fichero .txt externo donde se encuentra la ruta, usuario y contrase�a
	public String[] leeFichero(String rutaCompleta){		
		String array[]=new String[3];
		int i=0;
		
		try {																//si se encuentra el archivo y puede leerse del mismo se enttra al try y se abre el flujo de datos, el mismo se cierra al final del try, para liberar los recursos.				
			FileReader objFileReader=new FileReader(rutaCompleta); 
			BufferedReader objBufferedReader=new BufferedReader(objFileReader);	//aca almaceno en un buffer el contenido de ejemplo.txt								
			String linea="";										
			
			array[2]=linea;
			
			while(linea!=null){
				linea=objBufferedReader.readLine();							//el metodo readLine devolvera el contenido de una linea del fichero externo siempre que la encuetre, cuando no encuentre devolvera null, de ahi la condicion del while. Cuando readLine devueleva null, ya para el proxima ciclo no entrara en el while
					
				if(linea!=null){											//este if aca tiene el proposito de que si readLine ya devolvio null significando que ya no leyo linea de texto valida, que no asigne nada al array
					array[i]=linea;
					i++;
				}
				
			}			
			objFileReader.close();											//el metodo close() lanza una excepcion de tipo IOExeption por tanto hay que ponerlo dentro de un try.			
		} 
		catch (IOException e) {												//se captura esta superclase para que abarque las excepcione que lanzan el constructor de FIleReader y el metodo read(), en un solo catch, aunque podrian haberse hecho por separado. 				
			JOptionPane.showMessageDialog(this,"Error en el m�todo lee_Buffer");
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this,"Se ha producido otro tipo de excepci�n");
		}
	return array;
	}
	
	//M�todo que se encarga de elaborar la consulta SQL en base a la informaci�n de la tabla escogida, recupera esa informaci�n y la escribe en el area de texto.
	public void mostrarInfoTablas(String opcionPulsada){
		
		ArrayList<String>campos=new ArrayList<String>();
		String consulta="SELECT * FROM "+opcionPulsada;													//forma de parametrizar la consulta
		
		try{
			estableceConexion();																		//se establece a conexion
			objStatement=objConnection.createStatement();			
			objResultSet_Tabla=objStatement.executeQuery(consulta);										//se guarda en el ResultSet la info de la tabla seleccionada en el combo.
			
			//Se utiliza la interfaz ResultSetMetaData para obtener los metadatos de un ResultSet
			objResultSetMetaData=objResultSet_Tabla.getMetaData();
			
			//via 1.obtenci�n de los metadatos relacionados con la cantidad de columnas de la tabla. Se necesita de la cantidad y nombre de las columnas que estar�n en el ResultSet para asi poder al recorrerlo sacar la info	  
			for(int i=1;i<=objResultSetMetaData.getColumnCount();i++){
				campos.add(objResultSetMetaData.getColumnLabel(i));										//se llena el arrayList con los nombres de las columnas de la tabla 
			}
			
			area.setText("");																			//limpio el area de texto previo a escribirle, esto servir� para una vez se le escriba algo, cuando se vuelva a intertar escribirle se limpie primero el area de texto.
			
			while(objResultSet_Tabla.next()){															//recorro el ResultSet donde estan guardados los registros de la tabla escogida. Cada vez que llegue a esta l�nea est� en un registro diferente.
				for(String nombreCampo:campos){															//recorre las columnas para cada registro
					area.append(objResultSet_Tabla.getString(nombreCampo)+" ");							//anexa al area de texto cada valor de cada casilla, (Registro,columna)
				}
				area.append("\n");																		//agrega un salto de linea para el proximo registro
			}
		}
		catch(Exception e3){
			JOptionPane.showMessageDialog(this,"Error en el m�todo mostrarInfoTablas");
			e3.printStackTrace();
		}
	}
	
	//M�todo que permite obtener el nombre de la base de datos incluida en la ruta
	public String buscaNombreBD(String ruta){
		nombreBD=new ArrayList<Character>();
		
		for(int i=ruta.length()-1;i>=0;i--){			//se recorre la String ruta donde esta guardada la cadena de conexion, de atras hacia adelante.
			if(ruta.charAt(i)!='/'){
				nombreBD.add(ruta.charAt(i));			//se a�aden caracteres al arrayList hasta que se encuentre el caracter '/' 
			}
			if(ruta.charAt(i)=='/'){
				break;
			}
		}
		Collections.reverse(nombreBD);					//se invierte el orden de los caracteres 
		nomBD=nombreBD.toString();
		nomBD=(nomBD.replaceAll(",",""));
		return nomBD=nomBD.substring(1,nomBD.length()-1);
	}
	
	//M�todo que abre una ventana al exlorador de archivos y permite pasarle como par�metro cual va a ser el directorio riz donde va a abrir y que tipo de archivos se van a visualizar en esa ventana.
	public static String exploraArchivos(String ruta,String extension,Component parent){
		String rutaCompleta=null;
			
		//1.Objeto JFileChooser
		JFileChooser objJFileChooser = new JFileChooser(ruta);
			
		//2.Objeto FileNameExtensionFilter que maneja las extensiones a buscar en la ventana que se abra a la PC.
		FileNameExtensionFilter objFileNameExtensionFilter = new FileNameExtensionFilter("Archivos ."+extension,extension);
		
		//3.Aplicar el filtro al objeto JFileChooser. setFileFilter toma como argumento un objeto FileFilter o un objeto de alguna clase que herede de FileFilter, como es el caso de FileNameExtensionFilter
		objJFileChooser.setFileFilter(objFileNameExtensionFilter);
			
		//4.Se llama al m�todo showOpenDialog que es el que se encarga de mostrar el cuadro de di�logo donde se podr� navegar para escoger el archivo. Seg�n la configuraci�n escogida anteriormente la ventana mostrar� una raiz espec�fica en la PC y solo los archivos que fueron escogidos como visibles. Toma como argumento el componente padre, que si tenemos una apk con solo marco ser� el objeto marco, pero si tenemos lamina a�adida al marco ser� la lamina. Devuelve una de tres constantes int de valor 0, 1 y -1 en dependencia de lo que se clickee en la ventana de dialogo. 0(APPROVE_OPTION), 1(CANCEL_OPTION), -1(ERROR_OPTION)  
		int returnVal = objJFileChooser.showOpenDialog(parent);
			
		//5.Comprobaci�n del valor de retorno del m�todo anterior.
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			rutaCompleta=objJFileChooser.getSelectedFile().getAbsolutePath();	     
		}
		return rutaCompleta;
	}
	
}











