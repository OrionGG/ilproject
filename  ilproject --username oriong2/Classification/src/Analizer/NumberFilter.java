package Analizer;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

public class NumberFilter extends TokenFilter{ 
	private TermAttribute termAtt;   

	public NumberFilter(TokenStream in) {   
		super(in);   
		termAtt = addAttribute(TermAttribute.class);

	}     


	/**
	 * @return  Returns true for the next token in the stream, or false at EOS
	 */
	@Override
	public boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			String term = termAtt.term();
			if(!term.isEmpty()){
				try{
					Integer.parseInt(term.substring(0, 1));
					return false;
				}
				catch(NumberFormatException e){
					return true;
				}
			}
			else{
				return false;
			}
		} else {
			return false;
		}
	}

}
