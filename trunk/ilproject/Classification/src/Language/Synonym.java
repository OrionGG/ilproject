package Language;

import java.io.FileInputStream;
import java.util.*;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

public class Synonym {
	public static void main(String[] args) 
	throws Exception {
		 //Set<String> oSyn = lookupSynonyms(args[0]);

		 
		 String sWord = getSenses("sport");
		 System.out.println(sWord);
		 sWord = Traductor.Translate(sWord);
		 System.out.println(sWord);
		 
		 Set<String> oSyn = lookupSynonyms("sport");
		 for(String sSynon:oSyn){
			 String sSynonDef = getSenses(sSynon);
			 System.out.println(sSynonDef);
			 sSynonDef = Traductor.Translate(sSynonDef);
			 System.out.println(sSynonDef);
		 }
	}
	
	public static Set<String> lookupSynonyms(String lexicalForm) throws JWNLException
    {
       Set<String> synonyms = new HashSet<String>();

		configureJWordNet();
		Dictionary dictionary = Dictionary.getInstance();
       IndexWord indexWord = dictionary.getIndexWord(
          POS.NOUN, lexicalForm);
       if (indexWord == null)
          return synonyms;
       Synset[] synSets = indexWord.getSenses();
       for (Synset synset : synSets)
       {
          Word[] words = synset.getWords();
          for (Word word : words)
          {
             synonyms.add(word.getLemma());
          }
       }
       synonyms.remove(lexicalForm);
       return synonyms;
    }
	
	public static void configureJWordNet() {
		// WARNING: This still does not work in Java 5!!!
		try {
			// initialize JWNL (this must be done before JWNL can be used)
			// See the JWordnet documentation for details on the properties file
			FileInputStream oFileInputStream = new FileInputStream(".\\resources\\config\\file_properties.xml");
			JWNL.initialize(oFileInputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static String getSenses(String sWord) throws Exception {
		configureJWordNet();
		Dictionary dictionary = Dictionary.getInstance();
		IndexWord word = dictionary.lookupIndexWord(POS.NOUN, sWord);
		Synset[] senses = word.getSenses();
		String oResult = "";
		for (int i=0; i<senses.length; i++) {
		  Synset sense = senses[i];
		  Traductor.Translate(sWord);
		    
		  oResult += sense.getGloss() + " ";
		}
		return oResult;
	}

	
}
