package comparers;

import jcolibri.method.retrieve.NNretrieval.similarity.StandardGlobalSimilarityFunction;

public class Multiply extends StandardGlobalSimilarityFunction {


	public double computeSimilarity(double[] values, double[] weights, int ivalue)
	{
		double acum = 1;
		for(int i=0; i<ivalue; i++)
		{
			acum *= values[i];
		}
		return acum;
	}

}
