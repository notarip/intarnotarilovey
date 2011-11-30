package ar.uba.fi.ia.notarilovey.core;

import java.awt.Dimension;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.text.html.HTMLDocument.RunElement;

import org.neuroph.contrib.imgrec.ColorMode;
import org.neuroph.contrib.imgrec.ImageRecognitionPlugin;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.core.transfer.Sigmoid;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.nnet.learning.SigmoidDeltaRule;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.plugins.PluginBase;

import ar.uba.fi.ia.notarilovey.training.Entrenador;
import ar.uba.fi.ia.notarilovey.training.Fact;
import ar.uba.fi.ia.notarilovey.util.Parser;
import ar.uba.fi.ia.notarilovey.util.Utils;

import org.neuroph.nnet.learning.LMS;

public class OcrOk_01 implements Observer {
	
	
	public OcrOk_01() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public void arrancar () throws IOException{
		
		// TODO Auto-generated method stub
		System.out.println("Time stamp N1:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));
		
		String archivoMuestra = "capa1_01.txt";
		String archivoPruebas = "prueba.txt";
		
		int maxIterations = 10000;
		int w = 5;
		int h = 6;
		int in = w*h;
		int salida= 12;
		Entrenador coach1 = new Entrenador(archivoMuestra);
		
		TrainingSet set1 = coach1.entrenar(in, salida);
		 

		
		//MultiLayerPerceptron mlnet =  new MultiLayerPerceptron(TransferFunctionType.LINEAR, 30, 3, 1);
		//MultiLayerPerceptron mlnet = new MultiLayerPerceptron(TransferFunctionType.TANH, in, 400, 30, 15, 3,1);//(error 0.0001) en 3498 iteraciones
		//MultiLayerPerceptron mlnet = new MultiLayerPerceptron(TransferFunctionType.TANH, in, 100, 50, 25, 12,1);
		//MultiLayerPerceptron mlnet = new MultiLayerPerceptron(TransferFunctionType.TANH, in,60,120,60,30, 27,24,21,18,15,12,9,6,31,12,6,1);
		//MultiLayerPerceptron mlnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, in,60,120,60,30, 27,24,21,18,15,12,9,6,31,12,4);
		//MultiLayerPerceptron mlnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, in,60,120,240,120,60,30,25,20,15,salida);
		MultiLayerPerceptron mlnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, in,60,salida);
		//MultiLayerPerceptron mlnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, in,40,50,60,70,80,90,100,110,120,240,120,60,30,25,20,15,salida);
		//MultiLayerPerceptron mlnet = new MultiLayerPerceptron(TransferFunctionType.TANH, in,60,120,60,30,60,55,50,45,40,35,30,25,20,15,11);
		
		PluginBase imageRecognitionPlugin = new ImageRecognitionPlugin(new Dimension(w,h), ColorMode.BLACK_AND_WHITE);
		mlnet.addPlugin(imageRecognitionPlugin);
		mlnet.setLearningRule(new MomentumBackpropagation());
		
		
		((LMS) mlnet.getLearningRule()).setMaxError(0.03);
	    //((LMS) mlnet.getLearningRule()).setLearningRate(0.7);//0-1
	    //((LMS) mlnet.getLearningRule()).setMaxIterations(maxIterations);//0-1

		mlnet.getLearningRule().addObserver(this);		
		
	    if( mlnet.getLearningRule() instanceof MomentumBackpropagation )
	    	((MomentumBackpropagation)mlnet.getLearningRule()).setBatchMode(true);
		
	    
       System.out.println("Training neural network...");
       
       
       //mlnet.learnInSameThread(set1, new MomentumBackpropagation());
       //mlnet.learnInSameThread(set1, new MomentumBackpropagation());
      mlnet.learnInSameThread(set1);
       //mlnet.learnInNewThread(set1);
      
      
      
      try {
		      mlnet.save("net1.nnet");

      } catch (RuntimeException e) {
		System.out.println("Error al guardar la red");
      
      
      }
      

		
       System.out.println("Time stamp N2:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));
		

		double[] networkOutput;
		ArrayList<Fact> facts = Parser.Parsear(archivoPruebas, in);
		int rta;
		
		for (Fact ds : facts) {
			
			mlnet.setInput(Utils.toArrayDouble(ds.getValores()));
			
			mlnet.calculate();
			networkOutput = mlnet.getOutput();
			 
			
			
			System.out.println("Numero: " + ds.getRta());
			System.out.println(Arrays.toString(networkOutput));
			System.out.println("GANADOR:  " + Utils.getWinner(networkOutput));
			
			
			if (ds.getRta() == ',') 
				rta = 10;
			else
				if (ds.getRta() == '.')
					rta = 11;
				else
					rta = Integer.parseInt(ds.getRta()+"");
			
			if (rta == Utils.getWinner(networkOutput))
				System.out.println("MATCH !!!!!");
				

		}


	}




	@Override
	public void update(Observable arg0, Object arg1) {
		SupervisedLearning rule = (SupervisedLearning)arg0;
		System.out.println( "Training, Network Epoch " + rule.getCurrentIteration() + ", Error:" + rule.getTotalNetworkError());
		/*
		if (rule.getCurrentIteration() == 50){ 
			
			System.out.println("llego a 50");
			try {
				
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	*/
	}
	

}
