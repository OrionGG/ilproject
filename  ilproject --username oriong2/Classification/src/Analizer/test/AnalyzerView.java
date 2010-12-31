package Analizer.test;

import java.io.IOException;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.AttributeSource;

public abstract class AnalyzerView
{

	//private TermAttribute termAt = AttributeSource.addAttribute(TermAttribute.class);
	
    
	public String GetView(TokenStream tokenStream, int numberOfTokens) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        //String sb = "";
        String sTerm = "";

        do{
            if (tokenStream.incrementToken()) {
            	TermAttribute termAtt = tokenStream.getAttribute(TermAttribute.class);
    			sTerm = termAtt.term();
                sb.append(GetTokenView(sTerm));
    			//sb += GetTokenView(sTerm);
            }
            else{
            	TermAttribute termAtt = tokenStream.getAttribute(TermAttribute.class);
            	sTerm = termAtt.term();
                sb.append(GetTokenView(sTerm));
    			//sb += GetTokenView(sTerm);
            	
            }
        }while((sTerm != null) && !sTerm.equals(""));
        
        return sb.toString();
    }

    protected abstract String GetTokenView(String sToken);
}
