import java.util.LinkedList;

/* La utilidad de esta clase es almacenar cada elemento de la expresión regular
 * El elemento puede ser lo que contiene un paréntesis incluso. 
 * Es pór eso que los atributos son arreglos, si sólo es un símbolo se 
 * almacena en un array de tamaño 1
 * 
 * Les dire "celdas" en los comentarios, profe (sólo para identificarlas)
 */


public class ex {

	// El, o los, símbolo de este elemento
	public LinkedList<Character> val;
	public ex[] ins;
	// Dice si tiene cerradura de Kleene
	public boolean[] cerradura; 
	public boolean cer, op;
	
	
	
	// Constructor de 
	public ex(LinkedList<Character> ex) {
		this.val = ex;
		this.cerradura = new boolean[ex.size()];
		this.cer = false;
		this.op = false;
	}
	
	public ex() {
		
	}
	
	// Retorna el valor a buscar
	public char value () {
		return value(0);
	}
	
	// Sobrecarga con index
	public char value (int i) {
		return this.val.get(i);
	}
	
	// Retorna si tiene cerradura
	public boolean rep () {
		return this.cerradura[0];
	}
	
	// Sobrecarga con index
	public boolean rep (int i) {
		return this.cerradura[i];
	}
	
	
	// Invierte todos los valores de kleene
	public void invKleene() {
		for(int i = 0; i < this.cerradura.length; i++) this.cerradura[i] = !this.cerradura[i];
	}
	
	// Sobrecarga con index
	public void invKleene(int i) {
		this.cerradura[i] = !this.cerradura[i];
	}
}
