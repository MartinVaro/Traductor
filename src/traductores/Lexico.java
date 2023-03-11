package traductores;

import java.util.ArrayList;

public class Lexico {
	ArrayList<Token> lista_token = new ArrayList<Token>();
	
	//Constructor parametrizado
	public Lexico(ArrayList<Token> lista_token) {
		this.lista_token = lista_token;
	}
	
	public void analizar(String cadena) {
		int estado = -1;
		int numero_token = -1;
		String lexema = "";
		String tipo = "";
		//Separa cada que encuentre un espacio
		String [] lineas = separador(cadena, ' ');
		
		//Analiza cada caracter
		for(int i=0; i<lineas.length; i++) {			//Num de filas
			for(int j=0; j<lineas[i].length(); j++) {	//Num de columas de la fila
				
				int n_actual, n_siguiente = -1;
				
				//Palabras reservadas
				if((lineas[i].equals("int"))
						|| (lineas[i].equals("float"))
						|| (lineas[i].equals("void"))
						|| (lineas[i].equals("char"))) {
					estado = 0;
					j=lineas[i].length()-1;
				} else if(lineas[i].equals("if")) {
					estado = 9;
					j=lineas[i].length()-1;
				} else if(lineas[i].equals("while")) {
					estado = 10;
					j=lineas[i].length()-1;
				} else if(lineas[i].equals("return")) {
					estado = 11;
					j=lineas[i].length()-1;
				} else if(lineas[i].equals("else")) {
					estado = 12;
					j=lineas[i].length()-1;
				}
				
				//Se obtiene el valor en ASCII del caracter
				n_actual = lineas[i].codePointAt(j);
				
				if(estado == -1) {
					estado = estado_transicion(n_actual);
				}
				
				//Avanza al siguiente caracter
				try {
					n_siguiente = lineas[i].codePointAt(j+1);
				}catch(Exception e) {
					
				}
				
				//Estado de aceptacion
				switch(estado) {
				
					case 0: //Tipo de dato: int/float/char/void
						lexema = lineas[i];
						numero_token = 0;
						tipo = "Tipo_de_dato";
						estado = -1;
						break;
				
					case 1: //ID
						//Va almacenando los caracteres
						lexema = lexema + lineas[i].charAt(j);
						//Si el siguiente es un caracter o numero
						if((n_siguiente>96 && n_siguiente<123)
								|| (n_siguiente>64 && n_siguiente<91)
								|| (n_siguiente>47 && n_siguiente<58)
								|| (n_siguiente==95)) {
							estado = 1;
						} else {
							numero_token = 1;
							tipo = "Identificador";
							estado = -1;
						}
						break;
						
					case 2: //Punto y coma
						lexema = lexema + lineas[i].charAt(j);
						numero_token = 2;
						tipo = "PuntoComa";
						estado = -1;
						break;
						
					case 3: //Coma
						lexema = lexema + lineas[i].charAt(j);
						numero_token = 3;
						tipo = "Coma";
						estado = -1;
						break;
					
					case 4: //Parentesis apertura
						lexema = lexema + lineas[i].charAt(j);
						numero_token = 4;
						tipo = "Parentesis_a";
						estado = -1;
						break;
						
					case 5: //Parentesis cierre
						lexema = lexema + lineas[i].charAt(j);
						numero_token = 5;
						tipo = "Parentesis_c";
						estado = -1;
						break;
						
					case 6: //Llave apertura
						lexema = lexema + lineas[i].charAt(j);
						numero_token = 6;
						tipo = "Llave_a";
						estado = -1;
						break;
						
					case 7: //Llave cierre
						lexema = lexema + lineas[i].charAt(j);
						numero_token = 7;
						tipo = "Llave_c";
						estado = -1;
						break;
						
					case 8: //Asignacion
						lexema = lexema + lineas[i].charAt(j);
						//Si hay otro =
						if(n_siguiente == 61)
							estado = 17;
						else {
							numero_token = 8;
							tipo = "Asignacion";
							estado = -1;
						}
						break;
					
					case 9: //if
						lexema = lineas[i];
						numero_token = 9;
						tipo = "Reservado";
						estado = -1;
						break;
						
					case 10: //while
						lexema = lineas[i];
						numero_token = 10;
						tipo = "Reservado";
						estado = -1;
						break;
						
					case 11: //return
						lexema = lineas[i];
						numero_token = 11;
						tipo = "Reservado";
						estado = -1;
						break;
						
					case 12: //else
						lexema = lineas[i];
						numero_token = 12;
						tipo = "Reservado";
						estado = -1;
						break;
					
					case 13: //Entero o Real
						lexema = lexema + lineas[i].charAt(j);
						//Si es un numero o un punto
						if(n_siguiente>47 && n_siguiente<58 || n_siguiente == 46) {
							estado = 13;
						} else {
							numero_token = 13;
							tipo = "Constante";
							estado = -1;
						}
						break;
						
					case 14: //Operador de adicion +/-
						lexema = lexema + lineas[i].charAt(j);
						numero_token = 14;
						tipo = "OpAdicion";
						estado = -1;
						break;
					
					case 15: //Operador OR y AND
						lexema = lexema + lineas[i].charAt(j);
						if(n_siguiente == 124 || n_siguiente == 38) {
							estado = 15;
						} else {
							numero_token = 15;
							tipo = "OpLogico";
							estado = -1;
						}
						break;
					
					case 16: //Operador de multiplicacion
						lexema = lexema + lineas[i].charAt(j);
						numero_token = 16;
						tipo = "OpMul";
						estado = -1;
						break;
						
					case 17: //Operador relacional
						lexema = lexema + lineas[i].charAt(j);
						if(n_siguiente == 61) {
							estado = 17;
						} else {
							numero_token = 17;
							tipo = "OpRelac";
							estado = -1;
						}
						break;
						
					case 18: //$
							lexema = lexema + lineas[i].charAt(j);
							numero_token = 18;
							tipo = "Pesos";
							estado = -1;
							break;
							
					case 100: //Ignora los espacios
							estado = -2;
							break;
							
					case 999:
							lexema = String.valueOf(lineas[i].charAt(j));
							numero_token = 999;
							tipo = "Error";
							estado = -1;
							break;
				}
				//Se agrega nuevo Token y le pasa las variables
				if(estado == -1) {
					lista_token.add(new Token(lexema, numero_token, tipo));
					lexema = "";
					tipo = "";
				}
				//Cuando hay un espacio o enter
				if(estado == -2) {
					estado = -1;
				}
			}
		}
	}
	
	public int estado_transicion(int n) {
		//Si pertenece a-z o A-Z
		if((n>96 && n<123) || (n>64 && n<91) || (n==95)) {
			return 1;
		} //Punto y coma
		else if(n==59) {
			return 2;
		} //Coma
		else if(n==44) {
			return 3;
		} //Parentesis abierto
		else if(n==40) {
			return 4;
		} //Parentesis cerrado
		else if(n==41) {
			return 5;
		} //Llave abierta
		else if(n==123) {
			return 6;
		} //Lave cerrada
		else if(n==125) {
			return 7;
		} //Operador de asignacion
		else if(n==61) {
			return 8;
		} //Entero
		else if(n>47 && n<58) {
			return 13;
		} //Operador de adicion
		else if(n==43 || n==45) {
			return 14;
		} //Operador OR
		else if(n==124 || n==38) {
			return 15;
		} //Operador de multiplicacion
		else if(n==42 || n==47) {
			return 16;
		} //Operador relacional
		else if(n==60 || n==61 || n==62 || n==33) {
			return 17;
		} //$
		else if(n==36) {
			return 18;
		}
		//estado de aceptacion de espacios o enter
		else if(n<=32) {
			return 100; 
		}
		else {
			return 999;
		}
	}
	
	public String[] separador(String texto, char separar1) {
		String linea = "";
		int contador = 0;
		
		//Cuenta la cantidad de lineas de la cadena
		for(int i=0; i<texto.length(); i++) {
			if(texto.charAt(i) == separar1) {
				contador++;
			}
		}
		
		//Se crea un nuevo arreglo del tamaño del contador
		String[] cadenas = new String[contador];
		contador = 0;
		
		for(int i=0; i<texto.length(); i++) {
			if(texto.charAt(i) != separar1) { //Si no es un espacio
				linea = linea + String.valueOf(texto.charAt(i)); //Concatena los caracteres
			}
			else { //Si es un espacio
				cadenas[contador] = linea; //Guarda el lexema
				contador++; //Cambia a la siguiente linea
				linea = ""; //Se limpia para la siguiente linea
			}
		}
		
		return cadenas; //Devuelve los lexemas encontrados
	}
}
