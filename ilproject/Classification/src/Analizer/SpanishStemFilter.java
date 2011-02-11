package Analizer;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.tartarus.snowball.ext.SpanishStemmer;

/**   
 * Spanish stemming algorithm.  
 */   
public final class SpanishStemFilter extends TokenFilter {   

	private SpanishStemmer stemmer;   
	private TermAttribute termAtt;   

	public SpanishStemFilter(TokenStream in) {   
		super(in);   
		stemmer = new SpanishStemmer(); 
		termAtt = addAttribute(TermAttribute.class);

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
			String term = termAtt.term();
			stemmer.setCurrent(term);
			stemmer.stem();   
			String s = stemmer.getCurrent();   
			// If not stemmed, don't waste the time  adjusting the token.
			if ((s != null) && !s.equals( term ) )
				termAtt.setTermBuffer(s);
			return true;
		} else {
			return false;
		}
	}


}  
