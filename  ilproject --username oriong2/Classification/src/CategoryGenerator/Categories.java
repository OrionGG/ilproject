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
			}
	),
	Judge(
			new UrlByCategory[]{}
	),
	PokerPlayer(
			new UrlByCategory[]{
					new UrlByCategory("http://www.marca.com", "/mas_deportes/ajedrez.html", "ajedrez"),
					new UrlByCategory("http://www.marca.com", "/mas_deportes/poker.html", "poker")
					}
	),
	Model(
			new UrlByCategory[]{
					new UrlByCategory("http://www.abc.es", "/estilo-moda/modelos-pasarela-modas.asp", "moda")
			}
	),
	PlayboyPlaymate(
			new UrlByCategory[]{}
	),
	Philosopher(
			new UrlByCategory[]{
					new UrlByCategory("http://www.noticias.com","/noticias/psicologicas","noticias"),
					new UrlByCategory("http://www.filosofia.org", "/bol/not/index.htm", "filosofia")
			}
	),
	Cleric(
			new UrlByCategory[]{}
	),
	Criminal(
			new UrlByCategory[]{}
	),
	Monarch(
			new UrlByCategory[]{}
	),
	OfficeHolder(
			new UrlByCategory[]{}
	),
	MilitaryPerson(
			new UrlByCategory[]{}
	),
	Politician(
			new UrlByCategory[]{}
	),
	Astronaut(
			new UrlByCategory[]{}
	),
	Celebrity(
			new UrlByCategory[]{}
	),
	Scientist(
			new UrlByCategory[]{}
	),
	Journalist(
			new UrlByCategory[]{}
	),
	Architect(
			new UrlByCategory[]{}
	),
	Ambassador(
			new UrlByCategory[]{}
	),
	SoccerManager(
			new UrlByCategory[]{}
	),
	CollegeCoach(
			new UrlByCategory[]{}
	),
	NascarDriver(
			new UrlByCategory[]{}
	),
	FigureSkater(
			new UrlByCategory[]{}
	),
	Boxer(
			new UrlByCategory[]{}
	),
	IceHockeyPlayer(
			new UrlByCategory[]{}
	),
	Cyclist(
			new UrlByCategory[]{}
	),
	BadmintonPlayer(
			new UrlByCategory[]{}
	),
	GridironFootballPlayer(
			new UrlByCategory[]{}
	),
	NationalCollegiateAthleticAssociationAthlete(
			new UrlByCategory[]{}
	),
	Wrestler(
			new UrlByCategory[]{}
	),
	FormulaOneRacer(
			new UrlByCategory[]{}
	),
	BaseballPlayer(
			new UrlByCategory[]{}
	),
	GaelicGamesPlayer(
			new UrlByCategory[]{}
	),
	RugbyPlayer(
			new UrlByCategory[]{}
	),
	TennisPlayer(
			new UrlByCategory[]{}
	),
	BasketballPlayer(
			new UrlByCategory[]{}
	),
	GolfPlayer(
			new UrlByCategory[]{}
	),
	SoccerPlayer(
			new UrlByCategory[]{}
	),
	Cricketer(
			new UrlByCategory[]{}
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
			new UrlByCategory[]{}
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
	
	
	public static Categories[] oCategories = new Categories[]{
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
