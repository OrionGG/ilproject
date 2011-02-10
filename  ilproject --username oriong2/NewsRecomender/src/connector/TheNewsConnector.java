package connector;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import entities.APiecesOfNews;

import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CaseBaseFilter;
import jcolibri.cbrcore.Connector;
import jcolibri.exception.InitializingException;


public class TheNewsConnector implements Connector{
	
	List<APiecesOfNews> oNewsList = new ArrayList<APiecesOfNews>();
	
	
	public List<APiecesOfNews> getoTheNewsList() {
		return oNewsList;
	}

	public void setoTheNewsList(List<APiecesOfNews> oTheNewsList) {
		this.oNewsList = oTheNewsList;
	}
	
	public TheNewsConnector(List<APiecesOfNews> oNewsListParam){
		oNewsList = oNewsListParam;
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCases(Collection<CBRCase> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initFromXMLfile(URL arg0) throws InitializingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<CBRCase> retrieveAllCases() {

		Collection<CBRCase> res = new ArrayList<CBRCase>();


		for(APiecesOfNews oAPiecesOfNews : oNewsList){
			CBRCase _case = new CBRCase();
			_case.setDescription(oAPiecesOfNews);
			res.add(_case);
		}
		return res;
		
	}

	@Override
	public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeCases(Collection<CBRCase> arg0) {
		// TODO Auto-generated method stub
		
	}

}
