package Analizer.test;

import org.apache.lucene.analysis.Token;


public class TermAnalyzerView extends AnalyzerView
{

    protected String GetTokenView(String sToken)
    {
        return sToken+ " ";
    }
}