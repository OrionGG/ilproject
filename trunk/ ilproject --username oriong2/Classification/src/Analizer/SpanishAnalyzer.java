package Analizer;

import java.io.File;   
import java.io.IOException;   
import java.io.Reader;   
import java.util.HashSet;   
import java.util.Set;   
   
import org.apache.lucene.analysis.Analyzer;   
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;   
import org.apache.lucene.analysis.StopFilter;   
import org.apache.lucene.analysis.TokenStream;   
import org.apache.lucene.analysis.WordlistLoader;   
import org.apache.lucene.analysis.standard.StandardFilter;   
import org.apache.lucene.analysis.standard.StandardTokenizer;   
import org.apache.lucene.util.Version;
   
   
/** Filters {@link StandardTokenizer} with {@link StandardFilter}, {@link  
 * LowerCaseFilter}, {@link StopFilter} and {@link SpanishStemFilter}. */   
   
/**  
 * Analyzer for Spanish using the SNOWBALL stemmer. Supports an external list of stopwords  
 * (words that will not be indexed at all).  
 * A default set of stopwords is used unless an alternative list is specified, the  
 * exclusion list is empty by default.  
 *  
 * @author jose  
 */   
   
public class SpanishAnalyzer extends Analyzer {   
       
    /** An array containing some common Spanish words that are usually not  
     * useful for searching. Imported from http://www.unine.ch/info/clef/.  
     */   
    // TODO: no pego en el tutorial el listado de stopWords utilizado para  
    // no sobredimensionarlo, son 351 términos.  
    public static final String[] SPANISH_STOP_WORDS = { "" };  
      
    /** 
     * Contains the stopwords used with the StopFilter. 
     */  
    private Set<Object> stopTable = new HashSet<Object>();  
      
    /** 
     * Contains words that should be indexed but not stemmed. 
     */  
    private Set<Object> exclTable = new HashSet<Object>();  

    private final Version matchVersion;
    
    public SpanishAnalyzer(Version matchVersion) {  
        this.matchVersion = matchVersion;
    } 
    /** 
     * Builds an analyzer with the default stop words. 
     */  
    public SpanishAnalyzer() {  
    	this(Version.LUCENE_30);
        stopTable = StopFilter.makeStopSet(SPANISH_STOP_WORDS);  
    }  
  
    /** Builds an analyzer with the given stop words. */  
    public SpanishAnalyzer(Version matchVersion, String[] stopWords) {
    	this(matchVersion);
    	stopTable = StopFilter.makeStopSet(stopWords);    
    }  
      
    /** 
     * Builds an analyzer with the given stop words from file. 
     * @throws IOException  
     */  
    public SpanishAnalyzer(Version matchVersion, File stopWords) throws IOException {

    	this(matchVersion);
        stopTable = new HashSet(WordlistLoader.getWordSet(stopWords));  
    }  
      
    /** Constructs a {@link StandardTokenizer} filtered by a {@link 
     * StandardFilter}, a {@link LowerCaseFilter}, a {@link StopFilter} 
     * and a {@link SpanishStemFilter}. */  
    public final TokenStream tokenStream(String fieldName, Reader reader) {  
        TokenStream result = new StandardTokenizer(matchVersion, reader);  
        result = new StandardFilter(result);  
        result = new LowerCaseFilter(result);  
        result = new StopFilter(StopFilter.getEnablePositionIncrementsVersionDefault(matchVersion),
        		result, stopTable);  
        result = new SpanishStemFilter(result);  
        return result;  
    }  
}  
