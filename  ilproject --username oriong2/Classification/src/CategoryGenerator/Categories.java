package CategoryGenerator;

import java.util.*;

public enum Categories {
	
	BritishRoyalty(
			new UrlByCategory[]{
					new UrlByCategory("http://www.hola.com","/realeza/","realeza")
					}
	),
	FictionalCharacter(
			new UrlByCategory[]{
					
						new UrlByCategory("http://www.mangaes.com/","/noticias/anime-japon/", ""),
					new UrlByCategory("http://marcaplayer.com", "/", "marcaplayer")
			}
	),
	Judge(
			new UrlByCategory[]{
					new UrlByCategory("http://www.europapress.es", "/economia/legal-00345/", "legal"),
					new UrlByCategory("	http://noticias.juridicas.com/","", ""),
			}
	),
	PokerPlayer(
			new UrlByCategory[]{
					new UrlByCategory("http://www.marca.com", "/mas_deportes/ajedrez.html", "ajedrez"),
					new UrlByCategory("http://www.marca.com", "/mas_deportes/poker.html", "poker")
					}
	),
	Model(
			new UrlByCategory[]{
					new UrlByCategory("http://www.abc.es", "/estilo-moda/modelos-pasarela-modas.asp", "moda"),
					new UrlByCategory("http://www.estiloymoda.com","/noticias/", ""),
			}	
	),
	PlayboyPlaymate(
			new UrlByCategory[]{
					new UrlByCategory("http://www.20minutos.es/", "minuteca/sexo", "sexo")
					
			}
	),
	Philosopher(
			new UrlByCategory[]{
					new UrlByCategory("http://www.noticias.com","/noticias/psicologicas","psicolog"),
					new UrlByCategory("http://www.filosofia.org", "/bol/not/index.htm", "filosofia")
			}
	),
	Cleric(
			new UrlByCategory[]{
					new UrlByCategory("http://www.larazon.es", "/secciones/religion/noticias-generales", "religion")					
			}
	),
	Criminal(
			new UrlByCategory[]{
					new UrlByCategory("http://www.noticias.com", "/noticias/sucesos", "sucesos"),
					new UrlByCategory("http://www.lavanguardia.es","/sucesos/index.html", "sucesos"),
			}
	),
	Monarch(
			new UrlByCategory[]{
					new UrlByCategory("http://www.noticias.com", "/noticias/gobierno", "noticias" )
			}
	),
	OfficeHolder(
			new UrlByCategory[]{
					new UrlByCategory("http://www.elpais.com", "/economia/", "economia"),
					new UrlByCategory("http://www.elmundo.es", "/mundodinero/index.html", "mundodinero")
					}
	),
	MilitaryPerson(
			new UrlByCategory[]{
					new UrlByCategory("http://www.soitu.es", "/soitu/tags/portadilla/militares/", "/info")

					}
	),
	Politician(
			new UrlByCategory[]{
					
					new UrlByCategory("http://www.lavanguardia.es","/politica/index.html", "/politica/"),
					
			}
	),
	Astronaut(
			new UrlByCategory[]{
					new UrlByCategory("http://www.cassanya.com" , "/anuario.php", "cassanya"),
					new UrlByCategory("http://www.astrologia.org", "/informacion/revistas/revistas.html", "astrologia")
			}
	),
	Celebrity(
			new UrlByCategory[]{
					new UrlByCategory("http://www.abc.es", "/estilo-gente/celebrities-famosos.asp", "gente"),
					new UrlByCategory("http://www.hola.com", "/famosos/", "famosos"),
					new UrlByCategory("http://www.larazon.es", "/secciones/gente-2", "gente"),
					new UrlByCategory("http://www.elpais.com", "/gentetv/", "gentetv")
			}
	),
	Scientist(
			new UrlByCategory[]{
					
					new UrlByCategory("http://www.elpais.com", "/sociedad/ciencia/" , "ciencia"),
					new UrlByCategory("http://www.elmundo.es", "/elmundo/ciencia.html ", "ciencia"),
				

			}
	),
	Journalist(
			new UrlByCategory[]{
					new UrlByCategory("http://www.lavanguardia.es","/comunicacion/index.html", "comunicacion"),
			
			
					
			}
	),
	Architect(
			new UrlByCategory[]{
					new UrlByCategory("http://www.arquitecturaviva.com", "/", "arquitecturaviva")
			}
	),
	Ambassador(
			new UrlByCategory[]{
			//NATURALEZA
					new UrlByCategory("http://www.larazon.es/","secciones/verde", ""),
		
						
			}
	),
	SoccerManager(
			//TECNOLOGIA
			new UrlByCategory[]{
					new UrlByCategory("http://www.elpais.com", "/tecnologia/", "tecnologia"),
					new UrlByCategory("http://www.elmundo.es", "/elmundo/navegante.html", "navegante"),
					new UrlByCategory("http://noticias.lainformacion.com","/tecnologia", "tecnologia"),
			}
	),
	CollegeCoach(
			//SALUD
			new UrlByCategory[]{
			
					
							new UrlByCategory("http://www.larazon.es","/secciones/a-tu-salud/", ""),
							new UrlByCategory("http://www.elmundo.es","/elmundosalud/index.html", ""),
			}
	),
	NascarDriver(
			new UrlByCategory[]{
					new UrlByCategory("http://www.as.com", "/motor/", "motor"),
					new UrlByCategory("http://www.elpais.com", "/deportes/motociclismo/", "motociclismo")
			}
	),
	FigureSkater(
			new UrlByCategory[]{
					new UrlByCategory("http://www.europapress.es", "/deportes/invierno-00658/", "invierno"),
					new UrlByCategory("http://www.as.com", "/mas-deporte/estaciones-esqui/", "esqui"),
					new UrlByCategory("http://www.marca.com", "/mas_deportes/nieve.html", "nieve")
			}
	),
	Boxer(
			new UrlByCategory[]{
					new UrlByCategory("http://www.as.com", "/mas-deporte/boxeo/", "boxeo")			
					}
	),
	IceHockeyPlayer(
			new UrlByCategory[]{
					
			}
	),
	Cyclist(
			new UrlByCategory[]{
					new UrlByCategory("http://www.as.com", "/ciclismo/", "ciclismo")
			}
	),
	BadmintonPlayer(
			new UrlByCategory[]{}

	),
	GridironFootballPlayer(
			new UrlByCategory[]{}
	),
	NationalCollegiateAthleticAssociationAthlete(
			new UrlByCategory[]{
					new UrlByCategory("http://www.marca.com", "/atletismo.html", "atletismo"),
					new UrlByCategory("http://www.as.com", "/mas-deporte/atletismo/", "atletismo")
			}
	),
	Wrestler(
			new UrlByCategory[]{
				
						new UrlByCategory("http://www.mundowrestling.com/","", ""),
			}
	),
	FormulaOneRacer(
			new UrlByCategory[]{
					new UrlByCategory("http://www.as.com", "/motor/formula-1/", "formula-1"),
					new UrlByCategory("http://www.elpais.com", "/deportes/formula1/", "formula1")
			}
	),
	BaseballPlayer(
			new UrlByCategory[]{
					new UrlByCategory("http://www.as.com", "/baloncesto/", "baloncesto"),
					new UrlByCategory("http://www.elpais.com", "/deportes/baloncesto/", "baloncesto")					
			}
	),
	GaelicGamesPlayer(
			new UrlByCategory[]{}
	),
	RugbyPlayer(
			new UrlByCategory[]{
					new UrlByCategory("http://www.marca.com", "/mas_deportes/rugby.html", "rugby")
			}
	),
	TennisPlayer(
			new UrlByCategory[]{
					new UrlByCategory("http://www.marca.com", "/mas_deportes/padel.html", "padel"),
					new UrlByCategory("http://www.as.com", "/tenis/", "tenis"),
					new UrlByCategory("http://www.elpais.com", "/deportes/tenis/", "tenis")
			}
	),
	BasketballPlayer(
			new UrlByCategory[]{
					new UrlByCategory("http://www.lavanguardia.es","/especiales/nba/index.html", ""),
					new UrlByCategory("http://www.marca.com", "/baloncesto.html", "baloncesto"),
					}
	),
	GolfPlayer(
			new UrlByCategory[]{
					
			
						new UrlByCategory("http://es.eurosport.yahoo.com/","/golf/", ""),
					
						new UrlByCategory("http://www.degolf.org","/noticias/", ""),
						
			}
	),
	SoccerPlayer(
			new UrlByCategory[]{
					new UrlByCategory("http://www.as.com", "/futbol/", "futbol"),
					new UrlByCategory("http://www.elmundo.es", "/elmundodeporte/futbol.html", "futbol"),
					new UrlByCategory("http://www.elpais.com", "/deportes/futbol/", "futbol")
			}
	),
	Cricketer(
			new UrlByCategory[]{
					
					///VELA PA ORION
					new UrlByCategory("http://www.lavanguardia.es","/deportes/barcelona-world-race/index.html", ""),
			}
	),
	Actor(
			new UrlByCategory[]{
					new UrlByCategory("http://www.abc.es", "/cultura-cine/pelicula-actor-actrices.asp", "cine"),
					new UrlByCategory("http://www.elpais.com","/cine/", "articulo"),
					new UrlByCategory("http://www.abc.es", "/cultura-teatros/teatros.asp", "teatros")
			}
	),
	ComicsCreator(
			new UrlByCategory[]{
					new UrlByCategory("http://www.comicdigital.com","/comic/comic_noticias__1.html","comicdigital")	
			}
	),
	Comedian(
			new UrlByCategory[]{
					new UrlByCategory("http://www.eljueves.es", "/categoria/24/prensa_seria.html/", ""),
			
			}
	),
	Writer(
			new UrlByCategory[]{
					new UrlByCategory("http://www.abc.es", "/cultura-libros/libro.asp", "libros"),
					new UrlByCategory("http://www.elpais.com", "/suple/babelia/", "articulo")
			}
	),
	MusicalArtist(
			new UrlByCategory[]{
					new UrlByCategory("http://www.elpais.com", "/musica/", "musica"),
					new UrlByCategory("http://www.abc.es", "/cultura-musica/concierto-cantante.asp", "musica")
			}
	);
	
	private UrlByCategory[] lUrlList;
	
	public UrlByCategory[] getLUrlList() {
		return lUrlList;
	}

	public void setLUrlList(UrlByCategory[] urlList) {
		lUrlList = urlList;
	}
	
	private Categories(UrlByCategory[]lUrlListParam){
		lUrlList = lUrlListParam;
	}
	
	
	public static Categories[] allCategories = new Categories[]{
		BritishRoyalty,
		FictionalCharacter,
		Judge,
		PokerPlayer,
		Model,
		PlayboyPlaymate,
		Philosopher,
		Cleric,
		Criminal,
		Monarch,
		OfficeHolder,
		MilitaryPerson,
		Politician,
		Astronaut,
		Celebrity,
		Scientist,
		Journalist,
		Architect,
		Ambassador,
		SoccerManager,
		CollegeCoach,
		NascarDriver,
		FigureSkater,
		Boxer,
		IceHockeyPlayer,
		Cyclist,
		BadmintonPlayer,
		GridironFootballPlayer,
		NationalCollegiateAthleticAssociationAthlete,
		Wrestler,
		FormulaOneRacer,
		BaseballPlayer,
		GaelicGamesPlayer,
		RugbyPlayer,
		TennisPlayer,
		BasketballPlayer,
		GolfPlayer,
		SoccerPlayer,
		Cricketer,
		Actor,
		ComicsCreator,
		Comedian,
		Writer,
		MusicalArtist,
	};
}
