import java.util.LinkedList;

public class Automata {

	/*
	 * <|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|>
	 *  Wenas noches profe, el input que maneja mi algoritmo es uno específico pero sencillo, pero se le explica justo abajo.
	 * 	Para que el algoritmo funcione correctamente debe introducir la expresión regular con un formato específico
	 * 
	 *  
	 *   
	 * 	Primero, los paréntesis utilizados para la operación de suma ("+") deben ser sustituidos por coma
	 *  fuera de eso sólo no incluir ningun tipo de espacio
	 *  
	 *  
	 *  Ejemplos:
	 *  
	 *  	- Expresión regular estándar -> input correcto
	 *  
	 *  	- a*(b+cd*) -> a*,b+cd*
	 *  	- ab*d(a*vb+df*+fg)de -> ab*d,a*vb+df*+fg,de 
	 *  
	 * 
	 *  Esto hace que se separen en contenedores que contienen o un valor único o las opciones en el caso de ser una operación suma
	 * 
	 * <|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|>
	 */
	
	
	
	// La expresión regular original en formato String
	public String regex;
	
	// Contiene cada expresión individualmente compilada al construirse 
	// por elemento concatenado a primer nivel (lo que no está dentro de un paréntesis)
	public LinkedList<container> ins;
	
	// Constructor
	public Automata (String regex) {
		this.ins = new LinkedList<container>();
		this.regex = regex;
		this.compile();
	}
	
	
	// Parte la expresión para procesar en contenedores
	public void compile() {
		String[] regS = this.regex.split(",");
		for(int i = 0; i < regS.length; i++) {
			this.ins.add(iterCompile(regS[i]));
		}
	}
	
	//recibe una expresión individual a concatenar con la resultante de la siguiente iteración
	
	public container iterCompile(String regex) {
		String[] op = regex.split("\\+");
		String[] res = new String[op.length];
		String tmp;
		
		LinkedList<Boolean> bs = new LinkedList<Boolean>();
		
		// caso de único caracter
		if (regex.length() == 1) {
			return new container(String.valueOf(regex.charAt(0)));
		// en este caso sólo puede haber 
		} else if (regex.length() == 2 && regex.charAt(1) == '*') {
			return new container(String.valueOf(regex.charAt(0)), true);
		}
		else {
			if (op.length == 1) {
				char c;
				String f = "", temp = op[0];
				for (int j = 0; j < temp.length();j++) {
					c = temp.charAt(j);

					// checa si es Kleene
					if (c == '*') {
						bs.add(true);
					} 
					else {
						// checa si el siguiente es kleene o letra para saber si negar la cerradura del caracter actual
						if (j + 1 < temp.length() && temp.charAt(j+1) != '*') {
							bs.add(false);
						} 
						f.concat(String.valueOf(c));
					}
				}
				String[] toCont = new String[1];
				toCont[0] = f;
				return new container(toCont, bs);
			}
			for (int i = 0; i < op.length; i++) {
				tmp = op[i];
				
				// le pone kleene al último contenedor
				if (tmp.charAt(0) == '*') {
					ins.getLast().kleene = true;
					continue;
				}
				
				// si es único caracter
				if (tmp.length() == 1) {
					res[i] = tmp.substring(0,1); 
				}
				//multiples caracteres
				else {
					char c;
					String f = "";
					for (int j = 0; j < tmp.length();j++) {
						c = tmp.charAt(j);

						// checa si es Kleene
						if (c == '*') {
							bs.add(true);
						} 
						else {
							// checa si el siguiente es kleene o letra para saber si negar la cerradura del caracter actual
							if (j + 1 < tmp.length() && tmp.charAt(j+1) != '*') {
								bs.add(false);
							} 
							f.concat(String.valueOf(c));
						}
					}
				}	
			}
			// regresa con la configuración del proceso de mover las cerraduras a una lista de booleanos 
			return new container(res, bs);
			
		}
	}
	
	public boolean hasAllTrue(LinkedList<Boolean> l) {
		for (int i = 0; i < l.size(); i++) if (!l.get(i)) return false;
		return true;
	}
	
	public boolean hasAnyTrue(LinkedList<Boolean> l) {
		for (int i = 0; i < l.size(); i++) if (l.get(i)) return true;
		return false;
	}
	
	
	public boolean checkInRegex(String str) {
		char[] ch = str.toCharArray();
		// container Number
		int contN = 0;
		char last = ch[0], current;
		container cnt;
		LinkedList<Boolean> finValid = new LinkedList<Boolean>(), tmp = new LinkedList<Boolean>();
		
		for(int i = 0; i < ch.length; i++) {
			if (contN >= ins.size()) {
				return true;
			}
			current = ch[i];
			cnt = ins.get(contN);
			// es modulo de suma
			if (cnt.plus) {
				// operador de suma
				String s;

				for (int j = 0; j < cnt.values.length; j++) {
					tmp.clear();
					// checa para esa opcion
					s = cnt.values[j];

					// checa cada caracter individual de s
					for (int k = 0; k < s.length(); k++) {
						// checa si tiene kleene el caracter 
						if (cnt.moKleene.get(j+k)) {
							// si el actual no es el mismo
							if(current == last) {
								k--;
								last = current;
							}
						} 
						// no es kleene
						else {
							// no es caracter
							if (current != s.charAt(k)) {
								tmp.add(false);
							}
						}
					}
				}
				finValid.add(hasAnyTrue(tmp));
				contN++;
			}
			// no es modulo de suma
			else {
				// checa kleene
				if (cnt.kleene) {
					// si es diferente va a la siguiente evaluación
					if (!String.valueOf(current).equals(cnt.value)) {
						finValid.add(true);
						contN++;
					}
				}
			}
			
			
			last = current;
		}
		return hasAllTrue(finValid);
	}
	
	public String toString() {
		String r = "";
		for (int i = 0; i < this.ins.size(); i++) {
			r.concat(this.ins.get(i).toString()).concat("\n");
		}
		return r;
	}
}
