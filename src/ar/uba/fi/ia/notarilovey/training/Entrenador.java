package ar.uba.fi.ia.notarilovey.training;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.Perceptron;
import org.neuroph.util.VectorParser;

import ar.uba.fi.ia.notarilovey.util.Parser;
import ar.uba.fi.ia.notarilovey.util.Utils;
import ar.uba.fi.ia.notarilovey.training.Fact;


public class Entrenador {
	
	
	private String archivo;
	private ArrayList<double[]> inputs;
	
	public String getArchivo() {
		return archivo;
	}


	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}


	public ArrayList<double[]> getInputs() {
		return inputs;
	}


	public void setInputs(ArrayList<double[]> inputs) {
		this.inputs = inputs;
	}


	public Entrenador(String archivo ) {
		// TODO Auto-generated constructor stub
		
		this.archivo = archivo;
		this.inputs = new ArrayList<double[]>();
		
	}
	
	
	public TrainingSet entrenar(int input, int output) throws IOException {
		
		int salidas = output;
		ArrayList<Fact> echos = Parser.Parsear(this.archivo, input);
		
		//TrainingSet set = new TrainingSet(input, output);
		TrainingSet set = new TrainingSet();		
			
		//set.setTrainingSetCount(output);
		
		ArrayList<String> values;
		
		double[] in;
		double[] out;
		int rta;
		for (Fact fact : echos) {
			
			//TODO: hacer un mapa para las letras
			//rta = fact.getRta();
			//System.out.println("-----------------");
			
			in = new double[input];
			out = new double[1];
			
			in = Utils.toArrayDouble(fact.getValores());
			
			
			this.inputs.add(in);
			System.out.println("IN  -->" + Arrays.toString(in));
			
			
			
			if (fact.getRta() == ','){
				rta = 10;
			}else if (fact.getRta() == '.'){
				rta = 11;
			}else{
				rta = Integer.parseInt(fact.getRta()+"");
			}
				
			
			//out = Utils.getBinario(rta, 4);
			
			out = Utils.getMarscara(rta, salidas);
			
			System.out.println("OUT  -->" + Arrays.toString(out));
			
			set.addElement(new SupervisedTrainingElement(VectorParser.convertToVector(in), VectorParser.convertToVector(out)));
			
			//System.out.println("-----------------");
		}

		
//		NeuralNetwork myPerceptron = new Perceptron(input, output);
//		
//		
//		myPerceptron.learnInNewThread(set);
//		//.learnInSameThread(set);
		
		return set;
		

	}
	

}
