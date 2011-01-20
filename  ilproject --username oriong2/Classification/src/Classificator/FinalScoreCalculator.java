package Classificator;

public class FinalScoreCalculator {
	private static float weightWiki= 0.2f;	
	private static float weightDbpedia= 0.2f;	
	private static float weightNews= 0.6f;
	
	
	public static void calculateFinalScoreTable(float score1, float score2, float score3){
		double finalScore = score1*weightWiki+score2*weightDbpedia+score3*weightNews;

	}

}
