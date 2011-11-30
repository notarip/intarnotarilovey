package ar.uba.fi.ia.notarilovey.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.input.InputFunction;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.core.transfer.TransferFunction;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import ar.uba.fi.ia.notarilovey.training.Entrenador;
import org.neuroph.nnet.learning.LMS;

public class OcrSimple implements Observer {
	
	
	public OcrSimple() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public void arrancar () throws IOException{
		
		// TODO Auto-generated method stub
		 System.out.println("Time stamp N1:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));
		
		int maxIterations = 1000;
		int in = 30;
		Entrenador coach1 = new Entrenador("capa1_01.txt");
		//Entrenador coach1 = new Entrenador("capa_30_tocado.txt");
		
		TrainingSet set1 = coach1.entrenar(in, 1);
		
		
		NeuralNetwork mlnet = new Perceptron(30, 1);
		
		
		//((LMS) mlnet.getLearningRule()).setMaxError(0.03);
	    //((LMS) mlnet.getLearningRule()).setLearningRate(0.3);//0-1
	    //((LMS) mlnet.getLearningRule()).setMaxIterations(maxIterations);//0-1

		mlnet.getLearningRule().addObserver(this);	
		
		
		Layer layer1 = new Layer();
		layer1.addNeuron(new Neuron(new InputFunction(), new TransferFunction() {
			
			@Override
			public double getOutput(double net) {
				// TODO Auto-generated method stub
				return 0;
			}
		}));
		layer1.addNeuron(new Neuron());
		layer1.addNeuron(new Neuron());
		layer1.addNeuron(new Neuron());
		layer1.addNeuron(new Neuron());
		layer1.addNeuron(new Neuron());
		layer1.addNeuron(new Neuron());
		layer1.addNeuron(new Neuron());
		
		
		mlnet.addLayer(1, layer1);
		
	  //  if( mlnet.getLearningRule() instanceof MomentumBackpropagation )
	    //	((MomentumBackpropagation)mlnet.getLearningRule()).setBatchMode(true);
		
	    
       System.out.println("Training neural network...");
       
       
       //mlnet.learnInSameThread(set1, new MomentumBackpropagation());
       //mlnet.learnInSameThread(set1, new MomentumBackpropagation());
      mlnet.learnInSameThread(set1);
       //mlnet.learnInNewThread(set1);


		
       System.out.println("Time stamp N2:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));
		
		
		//Thread.sleep(20000);
		//mlnet.pauseLearning();
		double[] networkOutput;
   
	
		
		
		//mlnet.setInput(new double[]{0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0});//1
		mlnet.setInput(new double[]{0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0});//1
		mlnet.calculate();
		networkOutput = mlnet.getOutput();
		
		//System.out.println(mlnet.getOutputNeurons().toString());
		
		System.out.println(Arrays.toString(networkOutput));
       
		System.out.println(Math.round(networkOutput[0]));
		

		//mlnet.setInput(new double[]{0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,1,1,1,0});//2
		mlnet.setInput(new double[]{0,0,0,0,0,0,0,1,1,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,1,1,1}); //2
		mlnet.calculate();
       networkOutput = mlnet.getOutput();
       System.out.println(Arrays.toString(networkOutput));
       
       System.out.println(Math.round(networkOutput[0]));
       
       
       
       
       //mlnet.setInput(new double[]{0,0,1,1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0}); //3
       mlnet.setInput(new double[]{0,0,1,1,0,0,1,0,0,1,0,0,0,1,0,0,1,0,0,1,0,0,1,1,0,0,0,0,0,0}); //3
		mlnet.calculate();
       networkOutput = mlnet.getOutput();
       System.out.println(Arrays.toString(networkOutput));
		 
       System.out.println(Math.round(networkOutput[0]));
		
       
       
       mlnet.setInput(new double[]{0,0,0,0,0,0,1,0,1,0,0,1,0,1,0,0,1,1,1,0,0,0,0,1,0,0,0,0,1,0}); //4
		mlnet.calculate();
       networkOutput = mlnet.getOutput();
       System.out.println(Arrays.toString(networkOutput));
		 
       System.out.println(Math.round(networkOutput[0]));
       
       
       mlnet.setInput(new double[]{0,1,1,1,0,0,1,0,0,0,0,1,1,1,0,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0}); //5
		mlnet.calculate();
       networkOutput = mlnet.getOutput();
       System.out.println(Arrays.toString(networkOutput));
		 
       System.out.println(Math.round(networkOutput[0]));
       
       
		//mlnet.stopLearning();
		//net1.stopLearning();



	}




	@Override
	public void update(Observable arg0, Object arg1) {
		SupervisedLearning rule = (SupervisedLearning)arg0;
		System.out.println( "Training, Network Epoch " + rule.getCurrentIteration() + ", Error:" + rule.getTotalNetworkError());
		
		
		
		if (rule.getCurrentIteration() == 50){ 
			
			System.out.println("llego a 50");
			try {
				
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
