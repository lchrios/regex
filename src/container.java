import java.util.LinkedList;

// Clase que contiene cada expresión inidividual
public class container {
	
	
	// Opcion de concatenacion con caracter unico
	public String value;
	public boolean kleene = false, plus = false;
	
	
	// Opción de modo suma
	public String[] values;
	public LinkedList<Boolean> moKleene;
	
	
	public container(String c) {
		this.value = c;
		this.plus = false;
	}
	
	public container(String c, boolean hasKleene) {
		this(c);
		if(hasKleene) isKleene();
	}
	
	public void isKleene() {this.kleene = true;}
	
	public container (String[] values, LinkedList<Boolean> moKleene) {
		this.values = values;
		this.moKleene = moKleene;
		this.plus = true;
	}
	
	public String toString() {
		if (plus) {
			String r = "", s = "";
			for (int i = 0; i < values.length; i++) {
				r.concat(values[i]);
			}
			for (int i = 0; i < moKleene.size(); i++) {
				if (moKleene.get(i)) {
					s.concat("t");
				} else {
					s.concat("f");
				}
			}
			return r.concat(s);
			
		} else {
			return String.valueOf(value).concat(" ").concat(kleene ? "t" : "f");
		}
	}
	
}