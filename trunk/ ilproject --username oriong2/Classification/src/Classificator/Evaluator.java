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
import dominio.EvaluationMetrics;
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
	public static double dTotalNumberOfTestInstances = 0;
	public static double dNumberTotalOfTestInstancesCorrectlyClassified = 0;



	public static void changeValues(){
		//read DB

	}

	public static void main(String[] args) throws SQLException, IOException{

		FinalScoreCalculator.setWeightDbpedia(new Float(0.2));
		FinalScoreCalculator.setWeightWiki(new Float(0.2));
		FinalScoreCalculator.setWeightNews(new Float(0.6));
		evaluate();
	}
	public static void evaluate() throws SQLException, IOException{

		Hashtable<Integer, EvaluationMetrics> hCategoyEvaluationMetrics = new Hashtable<Integer, EvaluationMetrics>();
		for(Category oCategory: Category.values()){
			//read DB, take the rastreated webd webs, rady for categ
			List<String> lUrlsEvaluation=DAOUrlsRastreated.getInstance().selectUrls(oCategory, State.Classified);
			dTotalNumberOfTestInstances += lUrlsEvaluation.size();

			EvaluationMetrics oCurrentEvaluationMetrics = hCategoyEvaluationMetrics.get(oCategory.ordinal());
			
			//añadiendo falso positivo a la categoria que pone como primera
			if(oCurrentEvaluationMetrics == null){
				oCurrentEvaluationMetrics = new EvaluationMetrics();
			}
			////Read Db, taking the catgeorizated and ready for evaluated webs
			int nUrlsEvaluated=0;
			for(String sUrl : lUrlsEvaluation){
				nUrlsEvaluated++;
				Url url=new Url();
				url.setUrl(sUrl);
				url.setOriginalCategory(oCategory);

				TreeMap<Float, Category> categoryScores = DAOUrlsClassified.getInstance().selectCategoryScores(url.getUrl());
				url.setCategoryScore(categoryScores);

				if(categoryScores.firstEntry() == null){
					continue;
				}

				hCategoyEvaluationMetrics = takeMetrics(categoryScores, url,oCurrentEvaluationMetrics, hCategoyEvaluationMetrics);

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

			hCategoyEvaluationMetrics.put(oCategory.ordinal(), oCurrentEvaluationMetrics);
		}
		writeEvaluationResultsByCategory(hCategoyEvaluationMetrics);


	}

	public static Hashtable<Integer, EvaluationMetrics> takeMetrics(TreeMap<Float, Category> categoryScores, Url url, 
			EvaluationMetrics oCurrentEvaluationMetrics, 
			Hashtable<Integer, EvaluationMetrics> hCategoyEvaluationMetrics){

		Category oFirstCategory = categoryScores.firstEntry().getValue();
		if(url.getOriginalCategory() == oFirstCategory){
			oCurrentEvaluationMetrics.dTruePositive++;
			dNumberTotalOfTestInstancesCorrectlyClassified++;
		}
		else{
			oCurrentEvaluationMetrics.dFalseNegative++;
			EvaluationMetrics oEvaluationMetrics = hCategoyEvaluationMetrics.get(oFirstCategory.ordinal());
			
			//añadiendo falso positivo a la categoria que pone como primera
			if(oEvaluationMetrics != null){
				oEvaluationMetrics.dFalsePositive++;
			}
			else{
				oEvaluationMetrics = new EvaluationMetrics();
				oEvaluationMetrics.dFalsePositive = 1;
				hCategoyEvaluationMetrics.put(oFirstCategory.ordinal(), oEvaluationMetrics);

			}

			//añadiendo true negativo a las categorias que no incluye
			for(Category oCategory: Category.values()){
				if((oCategory == url.getOriginalCategory())
						|| (oCategory ==oFirstCategory)){
					continue;
				}
				
				EvaluationMetrics oEvaluationMetricsRestCategories = hCategoyEvaluationMetrics.get(oCategory.ordinal());
				
				if(oEvaluationMetricsRestCategories != null){
					oEvaluationMetricsRestCategories.dTrueNegative++;
				}
				else{
					oEvaluationMetricsRestCategories = new EvaluationMetrics();
					oEvaluationMetricsRestCategories.dTrueNegative = 1;
					hCategoyEvaluationMetrics.put(oCategory.ordinal(), oEvaluationMetricsRestCategories);

				}

			}

		}
		
		return hCategoyEvaluationMetrics;
	}

	private static void writeEvaluationResultsByCategory(
			Hashtable<Integer, EvaluationMetrics> hCategoyEvaluationMetrics) throws IOException {

		String sFichero = ".\\resources\\evaluation.txt";

		File fichero = new File(sFichero);

		if (!fichero.exists()) {
			fichero.createNewFile();
		}


		FileOutputStream fis=new FileOutputStream(fichero);

		BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));
		
		bw.write("WEIGHTS\n\n dbpedia="+  FinalScoreCalculator.getWeightDbpedia()+"\n wiki="+FinalScoreCalculator.getWeightWiki()+"\n weightNews ="+FinalScoreCalculator.getWeightNews()+"\n\n");
		bw.write("\n");

		for(Entry<Integer, EvaluationMetrics> oEntry : hCategoyEvaluationMetrics.entrySet()){
			int iCategoryOrdinal = oEntry.getKey();
			Category oCategory = Category.values()[iCategoryOrdinal];
			EvaluationMetrics oEvaluationMetrics = oEntry.getValue();
			
			bw.write("CATEGORIA: " + oCategory.toString() + "\n");
			bw.write("	TP = " + oEvaluationMetrics.dTruePositive + "\n");
			bw.write("	FN = " + oEvaluationMetrics.dFalseNegative + "\n");
			bw.write("	TN = " + oEvaluationMetrics.dTrueNegative + "\n");
			bw.write("	FP = " + oEvaluationMetrics.dFalsePositive + "\n");

			bw.write("\n");

			double dPrecision =oEvaluationMetrics.dTruePositive / (oEvaluationMetrics.dTruePositive + oEvaluationMetrics.dFalsePositive);
			bw.write("	Precision = " + dPrecision+ "\n");

			double dRecall = oEvaluationMetrics.dTruePositive / (oEvaluationMetrics.dTruePositive + oEvaluationMetrics.dFalseNegative);
			bw.write("	Recall = " + dRecall+ "\n");

			double FMeasure = (2*dRecall*dPrecision)/ (dRecall + dPrecision);
			bw.write("	F-Measure = " + FMeasure+ "\n");

			double dAccuracy = (oEvaluationMetrics.dTruePositive + oEvaluationMetrics.dTrueNegative) / (oEvaluationMetrics.dTruePositive + oEvaluationMetrics.dFalsePositive + oEvaluationMetrics.dFalseNegative + oEvaluationMetrics.dTrueNegative);
			bw.write("	Accuracy = " + dAccuracy+ "\n");
			bw.write("\n");
		}

		double dClassificationAccuracy = dNumberTotalOfTestInstancesCorrectlyClassified / dTotalNumberOfTestInstances;
		bw.write("	Classification Accuracy = " + dClassificationAccuracy+ "\n");

		bw.flush();
		bw.close();


	}

	private static void writeEvaluationResults(int nUrlsEvaluated) throws IOException {
		// TODO Auto-generated method stub


		String sFichero = ".\\resources\\evaluation.txt";

		File fichero = new File(sFichero);

		if (!fichero.exists()) {
			fichero.createNewFile();
		}
		FileOutputStream fis=new FileOutputStream(fichero);

		BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));
		java.util.Date fecha = new Date();
		bw.write(fecha.toString());
		bw.write("WEIGHTS\n\n dbpedia="+  FinalScoreCalculator.getWeightDbpedia()+"\n wiki="+FinalScoreCalculator.getWeightWiki()+"\n weightNews ="+FinalScoreCalculator.getWeightNews()+"\n\n");
		bw.write("1Method: "+ countEvaluation+" de "+nUrlsEvaluated+" percent of "+ (double)((double)countEvaluation/(double)nUrlsEvaluated)+"\n");
		bw.write("2Method: "+ adderEvaluation+ "la suma, segun la posicion\n");
		bw.write("3Method: "+ adderScores +" sumando scores");
		bw.flush();
		bw.close();



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
