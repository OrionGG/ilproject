package comparers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

public class WeightCoefficient  implements LocalSimilarityFunction {
	@Override
	public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException
	{
		if ((caseObject == null) || (queryObject == null))
			return 0;
		if (!(caseObject instanceof Double))
			throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
		if (!(queryObject instanceof Double))
			throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

		double caseInteger = (Double) caseObject;
		return caseInteger;	
	}

	@Override
	public boolean isApplicable(Object caseObject, Object queryObject) {
		// TODO Auto-generated method stub
		return false;
	}
}
