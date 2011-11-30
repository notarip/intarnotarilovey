package ar.uba.fi.ia.notarilovey.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.neuroph.contrib.imgrec.FractionRgbData;

public class Utils {
	
	public Utils() {
		// TODO Auto-generated constructor stub
	}
	
	public static  double[] getBinario(int numero, int largo){
		
		
		
		String b = Integer.toBinaryString(numero);
		
		int dif = largo - b.length();
		
		//double borrar;
		
		double[] rta = new double[largo];
		
		
		for (int i = 0 ; i < largo ; i++){
			
			
			if (i < dif){
				
				rta[i] = 0;
			
			}else{
				
				//borrar = Integer.parseInt(b.charAt(i-dif)+"");
				//rta[i] = borrar/10;
				//FIXME
				rta[i] = Integer.parseInt(b.charAt(i-dif)+"");
				
			}
			
			
			System.out.println(rta[i]);
			
		}
		
		
		return rta;
	}


	   public static void sleepNanos (long nanoDuration) throws InterruptedException {
	        final long end = System.nanoTime() + nanoDuration;
	        long timeLeft = nanoDuration;
	        do {
	            if (timeLeft > 100)
	                Thread.sleep (1);
	            else
	                if (timeLeft > 100)
	                    Thread.yield();

	            timeLeft = end - System.nanoTime();
	        } while (timeLeft > 0);
	    }
	   
	   public static double[] getMarscara(int pos, int largo){
		
		   
		   double[] mascara = new double[largo];
		   
		   for (int i = 0; i < mascara.length; i++) {
			
			   
			   mascara[i] = 0;
			   
			   if (i == pos){
				   
				   mascara[i] = 1d;
			   }
				   
				   
			   
		   }
		   
		   
		   
		   
		   return mascara;
		   
	   }
	   
	   
	   public static int getWinner(double[] winers){
		   
		   double max = 0;
		   int winner = 0;
		   
		   for (int i = 0; i < winers.length; i++) {
			
			   if (winers[i] > max){
				   
				   winner = i;
				   max = winers[i];
				   
			   }
				   
			   
			   
		}
		   
		   return winner;
	   }
	   
	  
	   
	   
	   
	   
	   public static String getWinner(HashMap<String, Double> winers){
		   
		   double max = 0;
		   String winner = "";
		   
		   for (Entry<String, Double> entry : winers.entrySet()){
			   
			   if (entry.getValue() > max){
				   
				   winner = entry.getKey();
				   max = entry.getValue();
				   
			   }
			   
		   }

		   
		   return winner;
	   }
	   
	   
	   
 public static Character getWinnerC(HashMap<Character, Double> winers){
		   
		   double max = 0;
		   Character winner = null;
		   
		   for (Entry<Character, Double> entry : winers.entrySet()){
			   
			   if (entry.getValue() > max){
				   
				   winner = entry.getKey();
				   max = entry.getValue();
				   
			   }
			   
		   }

		   
		   return winner;
	   }
	   
	   
	   
	   public static double[] toArrayDouble(ArrayList<String> valores){
		   
			
			double[] in = new double[valores.size()];;
		
			
			for (int i = 0; i < valores.size() ; i++){
					
					
				in[i] = valores.get(i).equalsIgnoreCase("1")?1:0;
							
			
				}			   
			   
		   
		   
		   return in;
		   
	   }
	   

}
