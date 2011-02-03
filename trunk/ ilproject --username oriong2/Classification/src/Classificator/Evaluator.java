package Classificator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.james.mime4j.codec.EncoderUtil.Encoding;
import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA_2_3.portable.InputStream;


import dominio.Category;
import dominio.Url;
import DBLayer.*;
import DBLayer.DAOUrlsClassified.Fields;
import DBLayer.DAOUrlsRastreated.State;




import DBLayer.*; 
import Classificator.FinalScoreCalculator;


public class Evaluator {
	
	private static int countEvaluation=0;
	private static float adderEvaluation=0;
	private static float adderScores=0;

	
	
	public static void changeValues(){
		//read DB
	
	}
	
	public static void main(String[] args) throws SQLException, IOException{
		
		FinalScoreCalculator.setWeightDbpedia(new Float(0.2));
		FinalScoreCalculator.setWeightWiki(new Float(0.2));
		FinalScoreCalculator.setWeightDbpedia(new Float(0.6));
		evaluate();
	}
	public static void evaluate() throws SQLException, IOException{
		
	
		//read DB, take the rastreated webd webs, rady for categ
		TreeMap<String,Category> treeUrlsEvaluation=DAOUrlsRastreated.getInstance().selectUrlsCategory(State.Classified);
		Set<Entry<String, Category>>set=treeUrlsEvaluation.entrySet();
		////Read Db, taking the catgeorizated and ready for evaluated webs
		int nUrlsEvaluated=0;
		for(Entry<String, Category> entry : set){
			nUrlsEvaluated++;
			Url url=new Url();
			url.setUrl(entry.getKey());
			url.setOriginalCategory(entry.getValue());
			
			TreeMap<Float, Category> categoryScores = DAOUrlsClassified.getInstance().selectCategoryScores(url.getUrl());
			url.setCategoryScore(categoryScores);
			Set<Entry<Float, Category>>setClassified=categoryScores.entrySet();
			int i =0;
			for(Entry<Float, Category> entryClassified : setClassified){
				i++;
				Float score=entryClassified.getKey();
				
				Category categoryClassified=entryClassified.getValue();
				if(categoryClassified==url.getOriginalCategory()){
					calculateEvuation(i,score);
				}
			}
			

		}
		writeEvaluationResults(nUrlsEvaluated);
		
	
	}
	private static void writeEvaluationResults(int nUrlsEvaluated) throws IOException {
		// TODO Auto-generated method stub
		
	
		      String sFichero = ".\\resources\\evaluation.txt";
		
		      File fichero = new File(sFichero);
	
		      if (fichero.exists()) {
	
		    	  FileOutputStream fis=new FileOutputStream(fichero);
		    	  
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));
		    	  java.util.Date fecha = new Date();
		    	  bw.write(fecha.toString());
		    	  bw.write("WEIGHTS\n\n dbpedia="+  FinalScoreCalculator.getWeightDbpedia()+"\n wiki="+FinalScoreCalculator.getWeightWiki()+"\n weightNews ="+FinalScoreCalculator.getWeightNews()+"\n\n");
		    	  bw.write("1Method: "+ countEvaluation+" de "+nUrlsEvaluated+" percent of "+ nUrlsEvaluated/countEvaluation+"\n");
		    	  bw.write("2Method: "+ adderEvaluation+ "la suma, segun la posicion\n");
		    	  bw.write("3Method: "+ adderScores +" sumando scores");
		    	  
		      }else{
		    	  fichero.createNewFile();
		      }
		
	}

	private static void calculateEvuation(int i, Float score) {
		
		
		// FIRST METHOD
		if(i==1){
			countEvaluation++;
			adderEvaluation=adderEvaluation+1;
		
		}else {	
			// SECOND METHOD
				if (i==2){
					adderEvaluation=adderEvaluation+0.75F;
					
					
			}else 
				if(i==3){
					adderEvaluation=adderEvaluation+0.5F;
				
			}
		}
		// THIRD METHOD
		adderScores=score+adderScores;
			
	
		
	}


	@Deprecated
	public static void evaluate(Hashtable<Category,List<String>> urlsCategorizadas){
		//read DB
		
		//entryset
		for(Entry<Category,List<String>> oEntry:urlsCategorizadas.entrySet()){
			Category oCategory = oEntry.getKey();

			List<IndexCategScore> lCategIndexScore = new java.util.ArrayList<IndexCategScore>();

			for(String sUrl: oEntry.getValue()){
				lCategIndexScore = Classificator.getScoresCat(sUrl);

				TreeMap<Double, Category> oTreeMap = FinalScoreCalculator.indexShortedCross(lCategIndexScore);
				System.out.println("");
				System.out.println("URL: "+ sUrl);
				System.out.println("");

				FinalScoreCalculator.showFinalResults(oTreeMap);
			}
		}
	}

}
