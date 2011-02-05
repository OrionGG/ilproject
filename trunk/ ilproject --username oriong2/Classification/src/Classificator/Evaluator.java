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
			//dTotalNumberOfTestInstances += lUrlsEvaluation.size();

			EvaluationMetrics oCurrentEvaluationMetrics = hCategoyEvaluationMetrics.get(oCategory.ordinal());

			
			if(oCurrentEvaluationMetrics == null){
				oCurrentEvaluationMetrics = new EvaluationMetrics();
			}
			////Read Db, taking the catgeorizated and ready for evaluated webs
			for(String sUrl : lUrlsEvaluation){
				Url url=new Url();
				url.setUrl(sUrl);
				url.setOriginalCategory(oCategory);

				TreeMap<Float, Category> categoryScores = DAOUrlsClassified.getInstance().selectCategoryScores(url.getUrl());
				url.setCategoryScore(categoryScores);

				if(categoryScores.firstEntry() == null){
					continue;
				}
				else{
					dTotalNumberOfTestInstances++;
				}

				hCategoyEvaluationMetrics = takeMetricsSimple(url,oCurrentEvaluationMetrics, hCategoyEvaluationMetrics);


			}

			hCategoyEvaluationMetrics.put(oCategory.ordinal(), oCurrentEvaluationMetrics);
		}
		writeEvaluationResultsByCategory(hCategoyEvaluationMetrics);


	}

	public static Hashtable<Integer, EvaluationMetrics> takeMetricsSimple(Url url, 
			EvaluationMetrics oCurrentEvaluationMetrics, 
			Hashtable<Integer, EvaluationMetrics> hCategoyEvaluationMetrics){


		TreeMap<Float, Category> categoryScores = url.getCategoryScore();
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

		return hCategoyEvaluationMetrics;
	}

	public static Hashtable<Integer, EvaluationMetrics> takeMetrics(Url url, 
			EvaluationMetrics oCurrentEvaluationMetrics, 
			Hashtable<Integer, EvaluationMetrics> hCategoyEvaluationMetrics){

		TreeMap<Float, Category> categoryScores = url.getCategoryScore();
		Iterator<Category> iIteratorC = categoryScores.values().iterator();
		double dPosition = 3;
		ArrayList<Integer> lBestCategories = new ArrayList<Integer>();
		for(int i = 0; i < 3 && i < categoryScores.values().size();i++){
			Category oBestCategory = iIteratorC.next();
			dPosition = i;
			if(url.getOriginalCategory() == oBestCategory){
				dPosition = i;
				break;
			}
			else{
				lBestCategories.add(oBestCategory.ordinal());
			}
		}

		oCurrentEvaluationMetrics.dTruePositive += 1 - (((double)1/3)*dPosition);
		dNumberTotalOfTestInstancesCorrectlyClassified += 1 - (((double)1/3)*dPosition);

		oCurrentEvaluationMetrics.dFalseNegative+= (((double)1/3)*dPosition);
		
		//añadiendo falso positivo a la categorias que pone por delante de la 
		for(Integer iCategoryOrdinal: lBestCategories){
			EvaluationMetrics oEvaluationMetrics = hCategoyEvaluationMetrics.get(iCategoryOrdinal);

			
			if(oEvaluationMetrics != null){
				oEvaluationMetrics.dFalsePositive += (((double)1/3)*dPosition);
			}
			else{
				oEvaluationMetrics = new EvaluationMetrics();
				oEvaluationMetrics.dFalsePositive  += (((double)1/3)*dPosition);
				hCategoyEvaluationMetrics.put(iCategoryOrdinal, oEvaluationMetrics);

			}
		}

		//añadiendo true negativo a las categorias que no incluye
		//que tendría que salir
		for(Category oCategory: Category.values()){
			if((oCategory == url.getOriginalCategory())
					|| lBestCategories.contains(oCategory.ordinal())){
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

		double dTotalTP = 0;
		double dTotalFN = 0;
		double dTotalTN = 0;
		double dTotalFP = 0;
		double dMacroaveragingPrecision = 0;
		double dMacroaveragingRecall = 0;
		double dMacroaveragingFMeasure = 0;
		double dMacroaveragingAccuracy = 0;

		for(Entry<Integer, EvaluationMetrics> oEntry : hCategoyEvaluationMetrics.entrySet()){
			int iCategoryOrdinal = oEntry.getKey();
			Category oCategory = Category.values()[iCategoryOrdinal];
			EvaluationMetrics oEvaluationMetrics = oEntry.getValue();

			bw.write("CATEGORIA: " + oCategory.toString() + "\n");
			bw.write("	TP = " + oEvaluationMetrics.dTruePositive + "\n");
			dTotalTP += oEvaluationMetrics.dTruePositive;

			bw.write("	FN = " + oEvaluationMetrics.dFalseNegative + "\n");
			dTotalFN += oEvaluationMetrics.dFalseNegative;

			bw.write("	TN = " + oEvaluationMetrics.dTrueNegative + "\n");
			dTotalTN += oEvaluationMetrics.dTrueNegative;

			bw.write("	FP = " + oEvaluationMetrics.dFalsePositive + "\n");
			dTotalFP += oEvaluationMetrics.dFalsePositive;

			bw.write("\n");

			double dPrecision = oEvaluationMetrics.dTruePositive / (oEvaluationMetrics.dTruePositive + oEvaluationMetrics.dFalsePositive);
			bw.write("	Precision = " + dPrecision+ "\n");
			if(!Double.isNaN(dPrecision)){
				dMacroaveragingPrecision += dPrecision;
			}

			double dRecall = oEvaluationMetrics.dTruePositive / (oEvaluationMetrics.dTruePositive + oEvaluationMetrics.dFalseNegative);
			bw.write("	Recall = " + dRecall+ "\n");
			if(!Double.isNaN(dRecall)){
				dMacroaveragingRecall += dRecall;
			}

			double FMeasure = (2*dRecall*dPrecision)/ (dRecall + dPrecision);
			bw.write("	F-Measure = " + FMeasure+ "\n");
			if(!Double.isNaN(FMeasure)){
				dMacroaveragingFMeasure += FMeasure;
			}

			double dAccuracy = (oEvaluationMetrics.dTruePositive + oEvaluationMetrics.dTrueNegative) / (oEvaluationMetrics.dTruePositive + oEvaluationMetrics.dFalsePositive + oEvaluationMetrics.dFalseNegative + oEvaluationMetrics.dTrueNegative);
			bw.write("	Accuracy = " + dAccuracy+ "\n");
			if(!Double.isNaN(dAccuracy)){
				dMacroaveragingAccuracy += dAccuracy;
			}

			bw.write("\n");
		}


		double dMicroaveragingPrecision =  calulateMacroMicroPrecision(hCategoyEvaluationMetrics, bw, dTotalTP, dTotalFP,
				dMacroaveragingPrecision);


		double dMicroaveragingRecall = calculateMacroMicroRecall(
				hCategoyEvaluationMetrics, bw, dTotalTP, dTotalFN,
				dMacroaveragingRecall);


		calculateMacroMicroFMeasure(hCategoyEvaluationMetrics, bw,
				dMacroaveragingFMeasure, dMicroaveragingPrecision,
				dMicroaveragingRecall);


		calculateMacroMicroAccuracy(hCategoyEvaluationMetrics, bw, dTotalTP,
				dTotalFN, dTotalTN, dTotalFP, dMacroaveragingAccuracy);


		double dClassificationAccuracy = dNumberTotalOfTestInstancesCorrectlyClassified / dTotalNumberOfTestInstances;
		bw.write("Number Total Of Test Instances Correctly Classified = " + dNumberTotalOfTestInstancesCorrectlyClassified+ "\n");
		bw.write("Number Total Number Of Test Instances = " + dTotalNumberOfTestInstances+ "\n");

		bw.write("Classification Accuracy = " + dClassificationAccuracy+ "\n");

		bw.flush();
		bw.close();


	}

	private static void calculateMacroMicroAccuracy(
			Hashtable<Integer, EvaluationMetrics> hCategoyEvaluationMetrics,
			BufferedWriter bw, double dTotalTP, double dTotalFN,
			double dTotalTN, double dTotalFP, double dMacroaveragingAccuracy)
	throws IOException {
		dMacroaveragingAccuracy = dMacroaveragingAccuracy/hCategoyEvaluationMetrics.size();
		bw.write("Macroaveraging Accuracy = " + dMacroaveragingAccuracy + "\n");

		double dMicroaveragingAccuracy = (dTotalTP +dTotalTN)/ (dTotalTP + dTotalFP + dTotalTN + dTotalFN);
		bw.write("Microaveraging Accuracy = " + dMicroaveragingAccuracy+ "\n");
		bw.write("\n");
	}

	private static void calculateMacroMicroFMeasure(
			Hashtable<Integer, EvaluationMetrics> hCategoyEvaluationMetrics,
			BufferedWriter bw, double dMacroaveragingFMeasure,
			double dMicroaveragingPrecision, double dMicroaveragingRecall)
	throws IOException {
		dMacroaveragingFMeasure = dMacroaveragingFMeasure/hCategoyEvaluationMetrics.size();
		bw.write("Macroaveraging F-Measure = " + dMacroaveragingFMeasure+ "\n");

		double dMicroaveragingFMeasure = (2*dMicroaveragingRecall*dMicroaveragingPrecision)/ (dMicroaveragingRecall + dMicroaveragingPrecision);
		bw.write("Microaveraging F-Measure = " + dMicroaveragingFMeasure+ "\n");
		bw.write("\n");
	}

	private static double calculateMacroMicroRecall(
			Hashtable<Integer, EvaluationMetrics> hCategoyEvaluationMetrics,
			BufferedWriter bw, double dTotalTP, double dTotalFN,
			double dMacroaveragingRecall) throws IOException {
		dMacroaveragingRecall = dMacroaveragingRecall/hCategoyEvaluationMetrics.size();
		bw.write("Macroaveraging Recall = " + dMacroaveragingRecall+ "\n");

		double dMicroaveragingRecall = dTotalTP / (dTotalTP + dTotalFN);
		bw.write("Microaveraging Recall = " + dMicroaveragingRecall+ "\n");
		bw.write("\n");
		return dMicroaveragingRecall;
	}

	private static double calulateMacroMicroPrecision(
			Hashtable<Integer, EvaluationMetrics> hCategoyEvaluationMetrics,
			BufferedWriter bw, double dTotalTP, double dTotalFP,
			double dMacroaveragingPrecision) throws IOException {
		dMacroaveragingPrecision = dMacroaveragingPrecision/hCategoyEvaluationMetrics.size();
		bw.write("Macroaveraging Precision = " + dMacroaveragingPrecision+ "\n");

		double dMicroaveragingPrecision = dTotalTP / (dTotalTP + dTotalFP);
		bw.write("Microaveraging Precision = " + dMicroaveragingPrecision+ "\n");
		bw.write("\n");

		return dMicroaveragingPrecision;
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
