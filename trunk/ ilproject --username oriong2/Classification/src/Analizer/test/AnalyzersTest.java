package Analizer.test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.*;
import org.apache.lucene.util.Version;

import Analizer.SpanishAnalyzer;
import Analizer.SpanishSnowballAnalyzer;

public class AnalyzersTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Analyzer[] oAnalyzerList = new Analyzer[]{
				new SpanishAnalyzer(),
				new SpanishSnowballAnalyzer(Version.LUCENE_30)
		};
		
		String sText = "Para analizar un texto hay que ver como funcionan la narrativa, la lírica... En una obra literaria hay 6 elementos a comentar:";
        Reader stringReader = new StringReader(sText);
		
		for(Analyzer oAnalyzer : oAnalyzerList){
			
			System.out.println(oAnalyzer.toString());
            TokenStream tokenStream = oAnalyzer.tokenStream("defaultFieldName", stringReader);
			sText =  (new Analizer.test.TermAnalyzerView()).GetView(tokenStream, 0).trim();
			System.out.println(sText);
			System.out.println();
		}
	}

}
