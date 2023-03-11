package traductores;

import java.lang.String;

public class Token {
	private String lexema;
	private int numero_token;
	private String tipo;
	
	//Constructor
	public Token() {
		
	}
	//Constructor parametrizado
	public Token(String lexema, int numero_token, String tipo) {
		this.lexema = lexema;
		this.numero_token = numero_token;
		this.tipo = tipo;
	}
	//Getters and Setters
	public String getLexema() {
		return lexema;
	}
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	public int getNumero_token() {
		return numero_token;
	}
	public void setNumero_token(int numero_token) {
		this.numero_token = numero_token;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	//Muestra la informacion del texto a analizar
	public String toString() {
		return "[ " + lexema + " ] --> " + "numeroToken= " + numero_token + ", Tipo= " + tipo;
	}	
}
