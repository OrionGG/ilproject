package comparers;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;


public class NumberEquals  implements LocalSimilarityFunction {

	@Override
	public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException
	{
		if ((caseObject == null) || (queryObject == null))
			return 0;
		if (!(caseObject instanceof String))
			throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
		if (!(queryObject instanceof String))
			throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

		String caseText = (String) caseObject;
		String queryText = (String) queryObject;

		List caselist = Arrays.asList(caseText.split(" "));
		Set<String> caseSet = new HashSet(caselist);
		
		List querylist = Arrays.asList(queryText.split(" "));
		Set<String> querySet = new HashSet(querylist);
		

		double size1 = caseSet.size();
		double size2 = querySet.size();
		
		caseSet.retainAll(querySet);
		double intersectionSize = caseSet.size();

		return intersectionSize;	
	}

	@Override
	public boolean isApplicable(Object caseObject, Object queryObject) {
		// TODO Auto-generated method stub
		return false;
	}
}
