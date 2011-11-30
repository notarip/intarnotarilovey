package ar.uba.fi.ia.notarilovey.training;

import java.util.ArrayList;

public class Fact {
	
	private char rta;
	private ArrayList<String> valores = null;
	
	
	public Fact(char rta) {
		
		this.rta = rta;
		this.valores = new ArrayList<String>();
		
	}
	
	
	
	public void setValor(String c) {
		this.valores.add(c);
	}
	
	
	public ArrayList<String> getValores() {
		return valores;
	}
	
	
	public char getRta() {
		return rta;
	}

}
