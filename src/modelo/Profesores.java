//Clase que se encargará de manejar los campos de clase de la tabla del mismo nombre en la base de datos.
//En el modelo los datos deben ir encapsulados.

package modelo;

public class Profesores {
	private String id;
	private String nombre;
	private String asignatura;
	private String matricula;
	
	public Profesores(){
		id="";
		nombre="";
		asignatura="";
		matricula="";
	}
	
	//Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
}
