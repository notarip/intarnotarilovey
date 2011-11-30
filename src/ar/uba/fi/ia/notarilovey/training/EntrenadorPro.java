package ar.uba.fi.ia.notarilovey.training;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.neuroph.contrib.imgrec.FractionRgbData;
import org.neuroph.contrib.imgrec.ImageRecognitionHelper;
import org.neuroph.contrib.imgrec.ImageSampler;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;

import ar.uba.fi.ia.notarilovey.util.ExtractorCaracteres;
import ar.uba.fi.ia.notarilovey.util.Utils;

public class EntrenadorPro {

	private Dimension resolucion;
	private ArrayList<String> lables;

	
	public TrainingSet getTrainingSet(Dimension dimension, String muestras, String recortes, ArrayList<TrainingSet> lset) throws IOException{

		
		/*
		 * Devueve un TR con todos los casos cargados o
		 * por parametro una lista de TR por cada muestra 
		 * 
		 * 
		 */

		this.setResolucion(dimension);
		
		ArrayList<String> lista = getImagenes(muestras);
		
		ExtractorCaracteres extra;
		
		TrainingSet set = new TrainingSet();
		
		
		
		for (String muestra : lista) {
			
			extra = new ExtractorCaracteres();
			
			ArrayList<BufferedImage> lrecortes = extra.recortar(new File(muestras.concat("/"+muestra)), new File(recortes), dimension.width, dimension.height);
			
			
			HashMap<String, FractionRgbData> muestreo = this.getMapaRGB(lrecortes);
			
			//agrega el individual de la muestra
			//ademas agrega la muestra al set general
			lset.add(getTraining(muestreo, set));
			
		}
		

		return set;
		
	}
	

	private TrainingSet getTraining(HashMap<String, FractionRgbData> muestreo, TrainingSet set) {
		// TODO Auto-generated method stub
		
		
		double[] inputBW;
		double[] out;

		int i;
		
		TrainingSet set_solo = new TrainingSet();
		
		for (i = 0; i < muestreo.size() ; i++ ) {
			
			inputBW = FractionRgbData.convertRgbInputToBinaryBlackAndWhite((muestreo.get(String.valueOf(i)).getFlattenedRgbValues()));
			
			
			out = Utils.getMarscara(i, 12);
			
			//System.out.println("numero: " + i +  "mascara: " + Arrays.toString(out) );
			set_solo.addElement(new SupervisedTrainingElement(inputBW,out));
			set.addElement(new SupervisedTrainingElement(inputBW,out));
		
		}
		

		
		
		
		return set_solo;
	}

	
	
	private HashMap<String, FractionRgbData> getMapaRGB (ArrayList<BufferedImage> recortes) throws IOException{
	
		/*TODO
		 * Tiene que armar un mapa con el label de la imagen y el 
		 * RGB data usar:
		 * 
		 * 
		 */
		
		HashMap<String, FractionRgbData> rta = new HashMap<String, FractionRgbData>();

		int i = 0;
		
		FractionRgbData imgRgb;
		
		for (BufferedImage bufferedImage : recortes) {
			
			imgRgb = new FractionRgbData(ImageSampler.downSampleImage(this.getResolucion(), bufferedImage));
			
			rta.put(String.valueOf(i), imgRgb);
			
			//System.out.println("getMapaRGB:  " + i);
			
			//System.out.println("getMapaRGB:  " + imgRgb);
			
			i++;
			
		}
		

		return rta;
		
	}
	
	
	
	private ArrayList<String> getImagenes (String directorio){
		
		/*TODO
		 * Tiene que armar un map con el nombre de las imagenes 
		 * sin la extencion y la ruta a las mismas 
		 */
		File dir = new File(directorio);
		String[] archivos = dir.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				boolean rta = name.endsWith("png") ||  name.endsWith("jpg");
				return rta;
			}
		});  //(new FileNameExtensionFilter("imagenes", "png", "jpg"));
		
		
		
		for (int i = 0; i < archivos.length; i++) {
			System.out.println("getimagenes:    " + archivos[i]);
		}
		
		
		ArrayList<String> lista = new ArrayList<String>();
		
		for (int i = 0; i < archivos.length; i++) {
			
			lista.add(archivos[i]);
			
		}
		
		
		return lista;
	}
	
	
	
	public Dimension getResolucion() {
		return resolucion;
	}
	
	public void setResolucion(Dimension resolucion) {
		this.resolucion = resolucion;
	}


	public ArrayList<String> getLables() {
		return lables;
	}


	public void setLables(ArrayList<String> lables) {
		this.lables = lables;
	}
	
	

}
