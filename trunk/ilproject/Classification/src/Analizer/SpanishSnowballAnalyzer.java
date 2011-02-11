package Analizer;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.util.Version;

public class SpanishSnowballAnalyzer extends Analyzer {

	private static SnowballAnalyzer analyzer;


	private String SPANISH_STOP_WORDS[] = {

	"un", "una", "unas", "unos", "uno", "sobre", "todo", "tambi�n", "tras",
	"otro", "alg�n", "alguno", "alguna",

	"algunos", "algunas", "ser", "es", "soy", "eres", "somos", "sois", "estoy",
	"esta", "estamos", "estais",

	"estan", "en", "para", "atras", "porque", "por qu�", "estado", "estaba",
	"ante", "antes", "siendo",

	"ambos", "pero", "por", "poder", "puede", "puedo", "podemos", "podeis",
	"pueden", "fui", "fue", "fuimos",

	"fueron", "hacer", "hago", "hace", "hacemos", "haceis", "hacen", "cada",
	"fin", "incluso", "primero",

	"desde", "conseguir", "consigo", "consigue", "consigues", "conseguimos",
	"consiguen", "ir", "voy", "va",

	"vamos", "vais", "van", "vaya", "bueno", "ha", "tener", "tengo", "tiene",
	"tenemos", "teneis", "tienen",

	"el", "la", "lo", "las", "los", "su", "aqui", "mio", "tuyo", "ellos",
	"ellas", "nos", "nosotros", "vosotros",

	"vosotras", "si", "dentro", "solo", "solamente", "saber", "sabes", "sabe",
	"sabemos", "sabeis", "saben",

	"ultimo", "largo", "bastante", "haces", "muchos", "aquellos", "aquellas",
	"sus", "entonces", "tiempo",

	"verdad", "verdadero", "verdadera", "cierto", "ciertos", "cierta",
	"ciertas", "intentar", "intento",

	"intenta", "intentas", "intentamos", "intentais", "intentan", "dos", "bajo",
	"arriba", "encima", "usar",

	"uso", "usas", "usa", "usamos", "usais", "usan", "emplear", "empleo",
	"empleas", "emplean", "ampleamos",

	"empleais", "valor", "muy", "era", "eras", "eramos", "eran", "modo", "bien",
	"cual", "cuando", "donde",

	"mientras", "quien", "con", "entre", "sin", "trabajo", "trabajar",
	"trabajas", "trabaja", "trabajamos",

	"trabajais", "trabajan", "podria", "podrias", "podriamos", "podrian",
	"podriais", "yo", "aquel", "mi",

	"de", "a", "e", "i", "o", "u"};

	public SpanishSnowballAnalyzer(Version matchVersion) {

	analyzer = new SnowballAnalyzer(matchVersion, "Spanish", SPANISH_STOP_WORDS);

	}

	public SpanishSnowballAnalyzer(Version matchVersion, String stopWords[]) {

	analyzer = new SnowballAnalyzer(matchVersion, "Spanish", stopWords);

	}

	public TokenStream tokenStream(String fieldName, Reader reader) {

	return analyzer.tokenStream(fieldName, reader);

	}

	}

