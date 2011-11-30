package ar.uba.fi.ia.notarilovey.core;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import ar.uba.fi.ia.notarilovey.training.EntrenadorPro;
import ar.uba.fi.ia.notarilovey.util.Utils;

public class OcrPro implements Observer{
	/*Tipografias
	 * fuente 		10 
	 * tiempo	 	84 
	 * iteraciones	31
	 * 
	 */
	
	/*Manuscritas
	 * fuente 		3 
	 * tiempo	 	41 
	 * iteraciones	53
	 */
	
	
	public void arrancar () throws IOException, InterruptedException{
		int w = 30;
		int h = 40;
		int salidas = 12; 
		double error = 0.04;
		double lrate = 0.01;
		boolean load = false;
		boolean save = true;
		boolean threads  = false;
		TransferFunctionType funcionTransferencia = TransferFunctionType.SIGMOID;
		
		String muestras = Propiedades.muestras;
		String entrada = Propiedades.entrada;
		String recortes_muestras = Propiedades.recortes_muestras;
		String recortes_entrada =  Propiedades.recortes_entrada;
		String red = Propiedades.redes.concat("/red_o4.net");
		long inicio = System.currentTimeMillis();
		
		
		
		EntrenadorPro coach = new EntrenadorPro();
	
		Dimension dimension = new Dimension(w, h);
		
		ArrayList<TrainingSet> lset = new ArrayList<TrainingSet>();
		
		TrainingSet set = coach.getTrainingSet(dimension, muestras, recortes_muestras, lset);
		
		
		ArrayList<Integer> capas = new ArrayList<Integer>();
		capas.add(new Integer(w*h));
		capas.add(new Integer(70));//108

		
		capas.add(new Integer(salidas));
		
		
		
		NeuralNetwork net;
		
		
		if (load)
			net = NeuralNetwork.load(red);
		else
			net = new MultiLayerPerceptron(capas,funcionTransferencia);
		
		 
		//PluginBase imageRecognitionPlugin = new ImageRecognitionPlugin(new Dimension(w,h), ColorMode.BLACK_AND_WHITE);
		//net.addPlugin(imageRecognitionPlugin);

		//net.setLearningRule(new MomentumBackpropagation());
		
		
		((LMS) net.getLearningRule()).setMaxError(error);
		//((LMS) net.getLearningRule()).setLearningRate(lrate);//0-1
		//((LMS) net.getLearningRule()).setMaxIterations(maxIterations);//0-1
			
		if( net.getLearningRule() instanceof MomentumBackpropagation )
			((MomentumBackpropagation)net.getLearningRule()).setBatchMode(true);

		
		net.getLearningRule().addObserver(this);
		
		
		/*****************/
		
		if (threads){
			for (TrainingSet tr : lset) {
				net.learnInNewThread(tr);
			}
		}else{
			net.learnInSameThread(set);
		}
		
		while (!net.getLearningRule().isStopped() && threads){
			System.out.println("esperando....");
			Thread.sleep(1000*3);
		}
		
		/******************************/
		
		
		if (save) net.save(red);
		
		
		EntrenadorPro tester = new EntrenadorPro();
		
		TrainingSet testeos = tester.getTrainingSet(dimension, entrada, recortes_entrada, lset);
		
		double[] networkOutput;
		
		ArrayList<Integer> ganadores = new ArrayList<Integer>();
		
		for (TrainingElement elem : testeos.trainingElements()) {
		
			net.setInput(elem.getInput());
        	
        	//System.out.println(Arrays.toString(elem.getInput()));
        	
        	net.calculate();
        	
        	networkOutput = net.getOutput();
        	
        	System.out.println("GANADOR:  " + Utils.getWinner(networkOutput));
			
			ganadores.add(new Integer(Utils.getWinner(networkOutput)));
		}

		
		String importe = new String();
		
		for (Integer integer : ganadores) {
			
			if (integer == 10)
				importe += ".";
			else if (integer == 11)
				importe += ",";
			else
				importe += integer.toString();
		}
		
		System.out.println("*******************");
		System.out.println("Importe:  " + importe);
		
		double total = (System.currentTimeMillis() - inicio)/1000;
		System.out.println("tiempo:  "  + total + "  segundos" );
		
	}
	
	
	

	@Override
	public void update(Observable arg0, Object arg1) {
		SupervisedLearning rule = (SupervisedLearning)arg0;
		System.out.println( "Iteracion " + rule.getCurrentIteration() + ", Error:" + rule.getTotalNetworkError());		
	}
	

	

}
