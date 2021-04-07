package principal;
import vista.*;
import javax.swing.*;

public class Principal {

	public static void main(String[] args) {
		MarcoAplicacion objMarco=new MarcoAplicacion();
		objMarco.setVisible(true);
		objMarco.setTitle("Accediendo a la Base de datos Academia");
		objMarco.setBounds(150,150,500,450);
		objMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
