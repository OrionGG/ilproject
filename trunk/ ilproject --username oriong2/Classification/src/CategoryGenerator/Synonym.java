package CategoryGenerator;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

public class Synonym {
	public static void main(String[] args) 
	throws JWNLException {
		 //Set<String> oSyn = lookupSynonyms(args[0]);

		 Set<String> oSyn = lookupSynonyms("wing");
		 for(String sWord:oSyn){
			 System.out.println(sWord);
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
       return synonyms;
    }
	
	public static void configureJWordNet() {
		// WARNING: This still does not work in Java 5!!!
		try {
			// initialize JWNL (this must be done before JWNL can be used)
			// See the JWordnet documentation for details on the properties file
			FileInputStream oFileInputStream = new FileInputStream("..\\config\\file_properties.xml");
			JWNL.initialize(oFileInputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
