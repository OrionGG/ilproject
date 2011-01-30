package dominio;

import java.util.*;


public enum Category {
	
	BritishRoyalty(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.hola.com","/realeza/","realeza")
					}
	),
	FictionalCharacter(
			new UrlForFiltering[]{
					
						new UrlForFiltering("http://www.mangaes.com/","/noticias/anime-japon/", ""),
					new UrlForFiltering("http://marcaplayer.com", "/", "marcaplayer")
			}
	),
	Judge(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.europapress.es", "/economia/legal-00345/", "legal"),
					new UrlForFiltering("	http://noticias.juridicas.com/","", ""),
			}
	),
	PokerPlayer(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.marca.com", "/mas_deportes/ajedrez.html", "ajedrez"),
					new UrlForFiltering("http://www.marca.com", "/mas_deportes/poker.html", "poker")
					}
	),
	Model(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.abc.es", "/estilo-moda/modelos-pasarela-modas.asp", "moda"),
					new UrlForFiltering("http://www.estiloymoda.com","/noticias/", ""),
			}	
	),
	PlayboyPlaymate(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.20minutos.es/", "minuteca/sexo", "sexo")
					
			}
	),
	Philosopher(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.noticias.com","/noticias/psicologicas","psicolog"),
					new UrlForFiltering("http://www.filosofia.org", "/bol/not/index.htm", "filosofia")
			}
	),
	Cleric(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.larazon.es", "/secciones/religion/noticias-generales", "religion")					
			}
	),
	Criminal(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.noticias.com", "/noticias/sucesos", "sucesos"),
					new UrlForFiltering("http://www.lavanguardia.es","/sucesos/index.html", "sucesos"),
			}
	),
	Monarch(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.noticias.com", "/noticias/gobierno", "noticias" )
			}
	),
	OfficeHolder(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.elpais.com", "/economia/", "economia"),
					new UrlForFiltering("http://www.elmundo.es", "/mundodinero/index.html", "mundodinero")
					}
	),
	MilitaryPerson(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.soitu.es", "/soitu/tags/portadilla/militares/", "/info")

					}
	),
	Politician(
			new UrlForFiltering[]{
					
					new UrlForFiltering("http://www.lavanguardia.es","/politica/index.html", "/politica/"),
					
			}
	),
	Astronaut(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.cassanya.com" , "/anuario.php", "cassanya"),
					new UrlForFiltering("http://www.astrologia.org", "/informacion/revistas/revistas.html", "astrologia")
			}
	),
	Celebrity(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.abc.es", "/estilo-gente/celebrities-famosos.asp", "gente"),
					new UrlForFiltering("http://www.hola.com", "/famosos/", "famosos"),
					new UrlForFiltering("http://www.larazon.es", "/secciones/gente-2", "gente"),
					new UrlForFiltering("http://www.elpais.com", "/gentetv/", "gentetv")
			}
	),
	Scientist(
			new UrlForFiltering[]{
					
					new UrlForFiltering("http://www.elpais.com", "/sociedad/ciencia/" , "ciencia"),
					new UrlForFiltering("http://www.elmundo.es", "/elmundo/ciencia.html ", "ciencia"),
				

			}
	),
	Journalist(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.lavanguardia.es","/comunicacion/index.html", "comunicacion"),
			
			
					
			}
	),
	Architect(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.arquitecturaviva.com", "/", "arquitecturaviva")
			}
	),
	Ambassador(
			new UrlForFiltering[]{
			//NATURALEZA
					new UrlForFiltering("http://www.larazon.es/","secciones/verde", ""),
		
						
			}
	),
	SoccerManager(
			//TECNOLOGIA
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.elpais.com", "/tecnologia/", "tecnologia"),
					new UrlForFiltering("http://www.elmundo.es", "/elmundo/navegante.html", "navegante"),
					new UrlForFiltering("http://noticias.lainformacion.com","/tecnologia", "tecnologia"),
			}
	),
	CollegeCoach(
			//SALUD
			new UrlForFiltering[]{
			
					
							new UrlForFiltering("http://www.larazon.es","/secciones/a-tu-salud/", ""),
							new UrlForFiltering("http://www.elmundo.es","/elmundosalud/index.html", ""),
			}
	),
	NascarDriver(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.as.com", "/motor/", "motor"),
					new UrlForFiltering("http://www.elpais.com", "/deportes/motociclismo/", "motociclismo")
			}
	),
	FigureSkater(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.europapress.es", "/deportes/invierno-00658/", "invierno"),
					new UrlForFiltering("http://www.as.com", "/mas-deporte/estaciones-esqui/", "esqui"),
					new UrlForFiltering("http://www.marca.com", "/mas_deportes/nieve.html", "nieve")
			}
	),
	Boxer(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.as.com", "/mas-deporte/boxeo/", "boxeo")			
					}
	),
	IceHockeyPlayer(
			new UrlForFiltering[]{
					
			}
	),
	Cyclist(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.as.com", "/ciclismo/", "ciclismo")
			}
	),
	BadmintonPlayer(
			new UrlForFiltering[]{}

	),
	GridironFootballPlayer(
			new UrlForFiltering[]{}
	),
	NationalCollegiateAthleticAssociationAthlete(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.marca.com", "/atletismo.html", "atletismo"),
					new UrlForFiltering("http://www.as.com", "/mas-deporte/atletismo/", "atletismo")
			}
	),
	Wrestler(
			new UrlForFiltering[]{
				
						new UrlForFiltering("http://www.mundowrestling.com/","", ""),
			}
	),
	FormulaOneRacer(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.as.com", "/motor/formula-1/", "formula-1"),
					new UrlForFiltering("http://www.elpais.com", "/deportes/formula1/", "formula1")
			}
	),
	BaseballPlayer(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.as.com", "/baloncesto/", "baloncesto"),
					new UrlForFiltering("http://www.elpais.com", "/deportes/baloncesto/", "baloncesto")					
			}
	),
	GaelicGamesPlayer(
			new UrlForFiltering[]{}
	),
	RugbyPlayer(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.marca.com", "/mas_deportes/rugby.html", "rugby")
			}
	),
	TennisPlayer(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.marca.com", "/mas_deportes/padel.html", "padel"),
					new UrlForFiltering("http://www.as.com", "/tenis/", "tenis"),
					new UrlForFiltering("http://www.elpais.com", "/deportes/tenis/", "tenis")
			}
	),
	BasketballPlayer(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.lavanguardia.es","/especiales/nba/index.html", ""),
					new UrlForFiltering("http://www.marca.com", "/baloncesto.html", "baloncesto"),
					}
	),
	GolfPlayer(
			new UrlForFiltering[]{
					
			
						new UrlForFiltering("http://es.eurosport.yahoo.com/","/golf/", ""),
					
						new UrlForFiltering("http://www.degolf.org","/noticias/", ""),
						
			}
	),
	SoccerPlayer(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.as.com", "/futbol/", "futbol"),
					new UrlForFiltering("http://www.elmundo.es", "/elmundodeporte/futbol.html", "futbol"),
					new UrlForFiltering("http://www.elpais.com", "/deportes/futbol/", "futbol")
			}
	),
	Cricketer(
			new UrlForFiltering[]{
					
					///VELA PA ORION
					new UrlForFiltering("http://www.lavanguardia.es","/deportes/barcelona-world-race/index.html", ""),
			}
	),
	Actor(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.abc.es", "/cultura-cine/pelicula-actor-actrices.asp", "cine"),
					new UrlForFiltering("http://www.elpais.com","/cine/", "articulo"),
					new UrlForFiltering("http://www.abc.es", "/cultura-teatros/teatros.asp", "teatros")
			}
	),
	ComicsCreator(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.comicdigital.com","/comic/comic_noticias__1.html","comicdigital")	
			}
	),
	Comedian(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.eljueves.es", "/categoria/24/prensa_seria.html/", ""),
			
			}
	),
	Writer(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.abc.es", "/cultura-libros/libro.asp", "libros"),
					new UrlForFiltering("http://www.elpais.com", "/suple/babelia/", "articulo")
			}
	),
	MusicalArtist(
			new UrlForFiltering[]{
					new UrlForFiltering("http://www.elpais.com", "/musica/", "musica"),
					new UrlForFiltering("http://www.abc.es", "/cultura-musica/concierto-cantante.asp", "musica")
			}
	);
	
	private UrlForFiltering[] lUrlList;
	
	public UrlForFiltering[] getLUrlList() {
		return lUrlList;
	}

	public void setLUrlList(UrlForFiltering[] urlList) {
		lUrlList = urlList;
	}
	
	private Category(UrlForFiltering[]lUrlListParam){
		lUrlList = lUrlListParam;
	}
	
	
	public static Category[] allCategories = new Category[]{
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
