package Analizer;

import java.io.IOException; 

import org.tartarus.snowball.ext.SpanishStemmer;

import org.apache.lucene.analysis.Token;   
import org.apache.lucene.analysis.TokenFilter;   
import org.apache.lucene.analysis.TokenStream;   

/**   
 * Spanish stemming algorithm.  
 */   
public final class SpanishStemFilter extends TokenFilter {   

	private SpanishStemmer stemmer;   
	private Token token = null;   

	public SpanishStemFilter(TokenStream in) {   
		super(in);   
		stemmer = new SpanishStemmer();   
	}     

	/**  
	 * Set a alternative/custom Stemmer for this filter.  
	 */   
	public void setStemmer(SpanishStemmer stemmer) {   
		if ( stemmer != null ) {   
			this.stemmer = stemmer;   
		}   
	}

	/**
	 * @return  Returns true for the next token in the stream, or false at EOS
	 */
	@Override
	public boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			String term = token.term();  
			stemmer.stem();   
			String s = stemmer.getCurrent();   
			// If not stemmed, don't waste the time  adjusting the token.
			if ((s != null) && !s.equals( term ) )
				token.setTermBuffer(s);
			return true;
		} else {
			return false;
		}
	}


}  
