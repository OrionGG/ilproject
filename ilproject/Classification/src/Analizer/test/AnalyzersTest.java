package Analizer.test;

import java.io.File;
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
				new SpanishAnalyzer(Version.LUCENE_30, new File (".\\resources\\stopwords\\spanishSmart.txt")),
				new SpanishSnowballAnalyzer(Version.LUCENE_30)
		};
		
		String sText = "Rafael Nadal se clasificó para la segunda ronda del Australian Open (donde jugará ante el estadounidense Ryan Sweeting, verdugo hoy del español Gimeno Traver, al que venció por 6-4, 6-4 y 6-1) tras un paseo de 47 minutos ante el brasileño Marcos Daniel, ni siquiera el tiempo que dura un entrenamiento de rutina. Con 6-0 y 5-0 para Nadal, Daniel se retiró y puso fin a su 'via crucis' particular.";
        
		
		for(Analyzer oAnalyzer : oAnalyzerList){
			Reader stringReader = new StringReader(sText);
			System.out.println(oAnalyzer.toString());
            TokenStream tokenStream = oAnalyzer.tokenStream("defaultFieldName", stringReader);
			sText =  (new Analizer.test.TermAnalyzerView()).GetView(tokenStream, 0).trim();
			System.out.println(sText);
			System.out.println();
		}
	}

}
