import java.util.LinkedList;

// Clase que contiene cada expresión inidividual
public class container {
	
	
	// Opcion de concatenacion con caracter unico
	public char value;
	public boolean kleene = false, plus;
	
	
	// Opción de modo suma
	public String[] values;
	public LinkedList<Boolean> moKleene;
	
	
	public container(char c) {
		this.value = c;
		this.plus = false;
	}
	
	public container(char c, boolean hasKleene) {
		this(c);
		if(hasKleene) isKleene();
	}
	
	public void isKleene() {this.kleene = true;}
	
	public container (String[] values, LinkedList<Boolean> moKleene) {
		this.values = values;
		this.moKleene = moKleene;
	}
	
}