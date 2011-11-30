package ar.uba.fi.ia.notarilovey.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ar.uba.fi.ia.notarilovey.training.Fact;


public class Parser {

	public static ArrayList<Fact> Parsear(String archivo, int largo) throws IOException{
		
		
		 FileReader f;// the actual file stream
	     BufferedReader r;// used to read the file line by line
	     ArrayList<Fact> facts = new ArrayList<Fact>();
	     
	     
	     
	      f = new FileReader( new File(archivo) );
	      r = new BufferedReader(f);
	      String line;
	      String pp;
	      
	      Fact fact;

	      while ( (line=r.readLine()) !=null ) {
	        
	    	 if (line.charAt(0) == '#') continue;
	    	  
	    	  
	    	fact = new Fact(line.charAt(0));
	   
	        int idx=2;
	        
	        for (int x=0; x < largo ; x++) {
        		
	        	pp = String.valueOf(line.charAt(idx++));
	            fact.setValor(pp);
	            
	        }
	        
	        facts.add(fact);
	        
	      }

	      r.close();
	      f.close();
		
		return facts;
	}
	
	
	
	
}
