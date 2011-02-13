package comparers;

import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;


public class Multiply  implements LocalSimilarityFunction {

	@Override
	public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException
	{
		if ((caseObject == null) || (queryObject == null))
			return 0;
		if (!(caseObject instanceof Float))
			throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
		if (!(queryObject instanceof Float))
			throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

		
		double mult  = (Float)caseObject * (Float)queryObject;
		
		return mult;	
	}

	@Override
	public boolean isApplicable(Object caseObject, Object queryObject) {
		// TODO Auto-generated method stub
		return false;
	}
}
