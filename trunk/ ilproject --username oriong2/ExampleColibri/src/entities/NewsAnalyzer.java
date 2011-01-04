package entities;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;

import Analizer.SpanishAnalyzer;

import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.extensions.textual.IE.opennlp.IETextOpenNLP;


public class NewsAnalyzer {

	public static void analyze(CBRQuery query) throws IOException {
		APiecesOfNews oAPiecesOfNews = (APiecesOfNews) query.getDescription();
        Reader stringReader = new StringReader(oAPiecesOfNews.getsSimpleText());
        TokenStream tokenStream = (new SpanishAnalyzer()).tokenStream("defaultFieldName", stringReader);
        String sText =  (new Analizer.test.TermAnalyzerView()).GetView(tokenStream, 0).trim();
        oAPiecesOfNews.setsSimpleText(sText);
        query.setDescription(oAPiecesOfNews);
	}

	public static void analyze(Collection<CBRCase> cases) throws IOException {
		// TODO Auto-generated method stub
		for(CBRCase oCase : cases){
			APiecesOfNews oAPiecesOfNews = (APiecesOfNews) oCase.getDescription();
            Reader stringReader = new StringReader(oAPiecesOfNews.getsSimpleText());
            TokenStream tokenStream = (new SpanishAnalyzer()).tokenStream("defaultFieldName", stringReader);
            String sText =  (new Analizer.test.TermAnalyzerView()).GetView(tokenStream, 0).trim();
            oAPiecesOfNews.setsSimpleText(sText);
            oCase.setDescription(oAPiecesOfNews);
		}
			
			
	}
}