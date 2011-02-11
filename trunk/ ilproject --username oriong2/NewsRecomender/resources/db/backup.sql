-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: websclassified
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.9

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `list`
--

CREATE TABLE `list` (
  `id` int(11) NOT NULL,
  `url` varchar(2000) DEFAULT NULL,
  `cat1` int(11) DEFAULT NULL,
  `score1` float DEFAULT NULL,
  `cat2` int(11) DEFAULT NULL,
  `score2` float DEFAULT NULL,
  `cat3` int(11) DEFAULT NULL,
  `score3` float DEFAULT NULL,
  `cat4` int(11) DEFAULT NULL,
  `score4` float DEFAULT NULL,
  `cat5` int(11) DEFAULT NULL,
  `score5` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list`
--

LOCK TABLES `list` WRITE;
/*!40000 ALTER TABLE `list` DISABLE KEYS */;
INSERT INTO `list` VALUES (1,'http://blogs.elpais.com/del-tirador-a-la-ciudad/2010/05/index.html',17,0.267781,43,0.0341927,0,0.0114169,1,0.0107946,3,0.00895446),(2,'http://blogs.elpais.com/del-tirador-a-la-ciudad/2010/06/index.html',17,0.182488,43,0.0224425,3,0.0118941,0,0.011729,31,0.00916156),(3,'http://blogs.elpais.com/del-tirador-a-la-ciudad/2010/07/index.html',17,0.144174,43,0.0228646,0,0.0150017,3,0.0113206,18,0.0112369),(4,'http://blogs.elpais.com/del-tirador-a-la-ciudad/2010/08/index.html',17,0.16685,43,0.0212183,3,0.0154012,22,0.0106398,0,0.0090917),(5,'http://blogs.elpais.com/del-tirador-a-la-ciudad/2010/09/index.html',17,0.143959,43,0.0195682,0,0.00800903,22,0.00783672,3,0.00662278),(6,'http://marcaplayer.com/2011/01/18/pc/1295360674.html',3,0.0122895,22,0.00732715,7,0.00497657,16,0.00450126,27,0.00424174),(7,'http://marcaplayer.com/2011/01/20/ds/1295512301.html',22,0.0104603,3,0.010241,31,0.0101967,32,0.00612023,27,0.00547886),(8,'http://marcaplayer.com/2011/01/24/ps3/1295860615.html',3,0.0105733,43,0.00750858,24,0.00651601,22,0.00597775,38,0.0055945),(9,'http://marcaplayer.com/2011/01/24/xbox360/1295866073.html',3,0.0120162,24,0.00605331,31,0.00556285,32,0.0043683,27,0.00420755),(10,'http://marcaplayer.com/2011/01/25/ps3/1295949762.html',22,0.014547,3,0.0115343,31,0.00816954,1,0.00617346,24,0.00486628),(11,'http://marcaplayer.com/2011/01/31/xbox360/1296466415.html',3,0.00762237,0,0.00749047,22,0.00734519,16,0.00496798,8,0.0041953),(12,'http://marcaplayer.com/2011/02/01/ps3/1296577862.html',31,0.0116818,3,0.00908438,4,0.00812984,22,0.00720966,24,0.00485643),(13,'http://marcaplayer.com/2011/02/01/xbox360/1296553040.html',33,0.0164489,8,0.0147292,6,0.0114943,0,0.00946878,9,0.00898549),(14,'http://marcaplayer.com/2011/02/03/ps3/1296728039.html',3,0.00773549,8,0.00535678,22,0.00354508,1,0.00300451,16,0.00286768),(15,'http://www.20minutos.es/galeria/7013/0/0/sexo-en-piedra/',0,0.0272749,17,0.0192362,31,0.0160953,3,0.0117046,1,0.0086873),(16,'http://www.20minutos.es/galeria/7013/0/1/sexo-en-piedra/',0,0.029162,17,0.0241075,31,0.0194139,1,0.0107707,3,0.00977609),(17,'http://www.20minutos.es/galeria/7013/0/2/sexo-en-piedra/',0,0.028186,17,0.0233007,31,0.0198236,1,0.0103633,3,0.00875873),(18,'http://www.20minutos.es/galeria/7013/0/3/sexo-en-piedra/',0,0.0280231,17,0.0242004,31,0.0186557,1,0.0111445,3,0.0103032),(19,'http://www.20minutos.es/galeria/7013/0/4/sexo-en-piedra/',0,0.0286925,17,0.0258105,31,0.0198508,1,0.0107599,33,0.00784045),(20,'http://www.20minutos.es/minuteca/sexo',5,0.0757035,0,0.0193554,31,0.0182963,17,0.0174606,33,0.0136588),(21,'http://www.abc.es/20101115/cultura-musica/rihanna-michaeljackson-rihanna-navidad-201011151101.html',14,0.0208274,42,0.0185993,0,0.0169207,4,0.0163588,39,0.0146118),(22,'http://www.abc.es/20101130/cultura-teatros/premio-nacional-danza-201011301428.html',42,0.0216455,14,0.0193963,0,0.0180738,43,0.0167273,39,0.014477),(23,'http://www.abc.es/20101201/cultura-teatros/spider-choca-tecnica-pero-201011301908.html',43,0.0322228,42,0.0180558,14,0.0141486,4,0.0140307,0,0.0139992),(24,'http://www.abc.es/20101216/cultura-teatros/judy-dench-201012161055.html',42,0.0223496,0,0.0221758,14,0.0206085,39,0.0195777,4,0.0160663),(25,'http://www.abc.es/20110110/cultura-teatros/abci-juanito-navarro-201101100758.html',42,0.0217294,14,0.0181879,0,0.0173146,39,0.0151733,4,0.0142477),(26,'http://www.abc.es/20110110/cultura-teatros/abci-juanito-navarro-videos-201101100812.html',14,0.0215064,42,0.0203166,39,0.0182997,4,0.0168474,0,0.0144721),(27,'http://www.abc.es/20110121/cultura-teatros/abci-alex-rigola-gata-201101210949.html',42,0.0219356,14,0.0181848,39,0.0145007,4,0.0141768,0,0.0128934),(28,'http://www.abc.es/20110121/cultura-teatros/abci-alex-rigola-gata-201101210949.html#formcomentarios',42,0.0219356,14,0.0181848,39,0.0145007,4,0.0141768,0,0.0128934),(29,'http://www.abc.es/20110121/cultura-teatros/abci-arriba-telon-201101201641.html',0,0.0182503,42,0.0163985,43,0.0143041,4,0.0138282,14,0.0137911),(30,'http://www.abc.es/20110121/cultura-teatros/abci-arriba-telon-201101201641.html#formcomentarios',0,0.0182503,42,0.0163985,43,0.0143041,4,0.0138282,14,0.0137911),(31,'http://www.abc.es/20110121/cultura-teatros/abcm-will-smith-quiere-hija-201101211558.html',42,0.0231813,14,0.0218132,39,0.019512,4,0.0187664,0,0.015463),(32,'http://www.abc.es/20110121/cultura-teatros/abcm-will-smith-quiere-hija-201101211558.html#formcomentarios',42,0.0231813,14,0.0218132,39,0.019512,4,0.0187664,0,0.015463),(33,'http://www.abc.es/20110201/cultura-musica/abci-rihanna-single-sexy-201102011601.html',14,0.0286951,42,0.0280749,39,0.023343,4,0.023132,31,0.0201057),(34,'http://www.abc.es/20110201/cultura-musica/abci-rihanna-single-sexy-201102011601.html#formcomentarios',14,0.0286951,42,0.0280749,39,0.023343,4,0.023132,31,0.0201057),(35,'http://www.abc.es/20110202/cultura-musica/abci-strokes-201102020948.html#formcomentarios',42,0.0257086,14,0.0249128,39,0.0198657,0,0.0196409,4,0.0194219),(36,'http://www.abc.es/blogs/tendencias-moda/public/post/un-birkin-por-35-dolares-7847.asp',31,0.00887656,33,0.00845216,0,0.00674667,4,0.00651838,17,0.00633657),(37,'http://www.abc.es/fotos-musica/20110131/gran-compositor-john-barry-1401044091694.html',14,0.0160496,42,0.0159864,4,0.0102548,39,0.0101062,17,0.00808892),(38,'http://www.abc.es/fotos-teatros/20101216/judy-dench-140915202981.html',42,0.0168246,14,0.0167184,4,0.0106768,39,0.0105273,17,0.00823355),(39,'http://www.abc.es/fotos-teatros/20110121/chantal-aimee-1401007078719.html',14,0.0167821,42,0.016716,4,0.0109603,39,0.0105675,17,0.00744211),(40,'http://www.abc.es/fotos-teatros/20110121/escena-obra-rodrigo-garcia-1401007114210.html',14,0.0184085,42,0.0183393,4,0.0119456,39,0.0118823,17,0.0106161),(41,'http://www.abc.es/fotos-teatros/20110121/integrante-belarus-free-theater-1401008450766.html',14,0.0180388,42,0.0179711,4,0.0117098,39,0.0116437,43,0.00761933),(42,'http://www.abc.es/fotos-teatros/20110203/ariadna-vicky-pena-1401053512682.html',14,0.0184993,42,0.0164205,39,0.0103806,4,0.00915923,17,0.00731053),(43,'http://www.as.com/ciclismo/articulo/martinelli-cree-contador/20110203dasdaicic_1/Tes',31,0.0463393,3,0.026198,8,0.0257191,33,0.0239936,28,0.0204546),(44,'http://www.as.com/ciclismo/articulo/martinelli-cree-contador/20110203dasdaicic_1/Tes#EnlaceComentarios',31,0.0463393,3,0.026198,8,0.0257191,33,0.0239936,28,0.0204546),(45,'http://www.as.com/ciclismo/articulo/nuevo-calendario-gran-equipo-2011/20110202dasdaicic_2/Tes',31,0.0434007,33,0.0268823,3,0.0252558,8,0.0241843,28,0.0236365),(46,'http://www.as.com/ciclismo/articulo/nuevo-calendario-gran-equipo-2011/20110202dasdaicic_2/Tes#EnlaceComentarios',31,0.0434007,33,0.0268823,3,0.0252558,8,0.0241843,28,0.0236365),(47,'http://www.as.com/ciclismo/articulo/tondo-tengo-panico-comer-carne/20110201dasdascic_1/Tes',31,0.0368143,33,0.0286712,3,0.0279781,8,0.0253792,36,0.0223665),(48,'http://www.as.com/ciclismo/articulo/tondo-tengo-panico-comer-carne/20110201dasdascic_1/Tes#EnlaceComentarios',31,0.0368143,33,0.0286712,3,0.0279781,8,0.0253792,36,0.0223665),(49,'http://www.as.com/mas-deporte/articulo/ajedrez-debe-recuperar-tiempo-ha/20110202dasdaimas_1/Tes',3,0.0893995,0,0.0424579,33,0.0410642,31,0.0400814,43,0.0261718),(50,'http://www.as.com/mas-deporte/articulo/javier-castillejo-vuelve-boxeo-entrenador/20110201dasdasmas_5/Tes',31,0.0454745,3,0.0234934,33,0.0230982,8,0.0217309,36,0.0178274),(51,'http://www.as.com/mas-deporte/atletismo/mundial',28,0.0539601,3,0.0524673,36,0.0489754,33,0.0466475,31,0.0185321),(52,'http://www.as.com/mas-deporte/balonmano/',36,0.0357379,31,0.0337632,3,0.0331468,33,0.0285598,8,0.0283573),(53,'http://www.as.com/mas-deporte/nosotras/',31,0.0378072,3,0.0340762,33,0.0317355,28,0.0274292,36,0.0269492),(54,'http://www.as.com/mas-deporte/video/super-bowl-peluda-habla-espanol/20110202dasdasmas_3/Ves',31,0.0436988,33,0.0356085,28,0.034976,3,0.0337564,8,0.0334794),(55,'http://www.as.com/tenis/articulo/djokovic-une-rafa-nadal-cartel/20110203dasdasten_3/Tes',3,0.0413346,31,0.0351121,36,0.0284757,33,0.0253167,28,0.0228211),(56,'http://www.as.com/tenis/articulo/feliciano-lopez-tipsarevic-caen-primera/20110202dasdasten_6/Tes',31,0.0332864,3,0.0322567,36,0.0218025,33,0.0201848,28,0.0168229),(57,'http://www.as.com/tenis/articulo/feliciano-lopez-tipsarevic-caen-primera/20110202dasdasten_6/Tes#EnlaceComentarios',31,0.0332864,3,0.0322567,36,0.0218025,33,0.0201848,28,0.0168229),(58,'http://www.as.com/tenis/articulo/llagostera-resfriada-ausenta-entrenamiento/20110202dasdasten_5/Tes',31,0.0398967,3,0.0349794,33,0.0270376,36,0.0236635,28,0.0182874),(59,'http://www.as.com/tenis/articulo/nadal-guardara-reposo-dias-verdasco/20110202dasdaiten_1/Tes',31,0.0346527,3,0.0322628,36,0.0227635,33,0.0214752,28,0.0177847),(60,'http://www.as.com/tenis/articulo/sharapova-juegos-sueno-mayor-objetivo/20110202dasdasten_4/Tes',31,0.0557867,3,0.0341795,33,0.0278625,36,0.0214301,28,0.02143),(61,'http://www.as.com/tenis/articulo/sharapova-juegos-sueno-mayor-objetivo/20110202dasdasten_4/Tes#EnlaceComentarios',31,0.0557867,3,0.0341795,33,0.0278625,36,0.0214301,28,0.02143),(62,'http://www.as.com/tenis/articulo/tve-tvc-unen-barcelona-open/20110202dasdaiten_2/Tes',3,0.0342331,31,0.0337791,36,0.0267133,33,0.0223978,8,0.0196134),(63,'http://www.as.com/tenis/final-atp/',36,0.0528644,3,0.0409853,28,0.0331227,33,0.0293601,31,0.0213727),(64,'http://www.as.com/tenis/us-open/',36,0.0859125,3,0.0519147,33,0.036708,28,0.0280359,8,0.0132857),(65,'http://www.astrologia.org',43,0.0200703,3,0.0118124,22,0.0115602,11,0.0111688,31,0.0110036),(66,'http://www.astrologia.org/carta_astral/carta_astralpartes.html',0,0.00379278,43,0.00299208,17,0.00152803,12,0.000948425,1,0.000739447),(67,'http://www.astrologia.org/horoscopofamosos/horoscopofamosos.html',3,0.0128463,31,0.00992878,43,0.00898368,13,0.00744973,22,0.00688155),(68,'http://www.astrologia.org/relaciones/relacionesporsigno.htm',13,0.00941133,22,0.00831244,11,0.00644221,8,0.00640532,7,0.00594736),(69,'http://www.comicdigital.com/3007_1-Superman_vuelve_a_casa.html',40,0.0325027,1,0.0226186,0,0.00730506,8,0.00444695,43,0.00444548),(70,'http://www.comicdigital.com/3031_1-Vuelve_Dark_Horse_Presents.html',40,0.0322748,1,0.0290422,16,0.00411975,43,0.00367237,0,0.0035331),(71,'http://www.comicdigital.com/3034_1-Zack_Snyder_ya_tiene_a_su_Superman.html',1,0.0201449,40,0.0179751,43,0.00737275,22,0.00580176,33,0.00575339),(72,'http://www.comicdigital.com/comic/comic_imagen__1.html',40,0.111705,1,0.0636128,0,0.0185995,43,0.00906636,39,0.00446496),(73,'http://www.degolf.org/noticias/asociacion-argentina-golf.asp',36,0.113264,33,0.0432327,3,0.0333535,28,0.0329888,31,0.029956),(74,'http://www.degolf.org/noticias/colombia.asp',36,0.115915,3,0.0571139,31,0.0393905,28,0.0337064,33,0.0331684),(75,'http://www.degolf.org/noticias/fga.asp',36,0.130744,3,0.071094,28,0.0394176,33,0.03866,31,0.0315784),(76,'http://www.degolf.org/noticias/fgcv.asp',36,0.239835,16,0.177731,8,0.171012,6,0.1394,3,0.117554),(77,'http://www.degolf.org/noticias/fgm.asp',36,0.122589,33,0.0580176,3,0.0393446,0,0.0352623,28,0.0259145),(78,'http://www.degolf.org/noticias/fgpa.asp',36,0.240498,31,0.0729298,28,0.0615895,3,0.0606459,0,0.0556122),(79,'http://www.degolf.org/noticias/masnoticias.asp',36,0.143308,3,0.0542643,33,0.0411713,28,0.0304614,31,0.0282142),(80,'http://www.degolf.org/noticias/mexico.asp',36,0.131083,33,0.0473996,3,0.0408815,28,0.038131,43,0.0359343),(81,'http://www.eljueves.es/categoria/24/prensa_seria.html',16,0.0110576,8,0.0102085,0,0.00979462,1,0.0090648,43,0.00634718),(82,'http://www.eljueves.es/login.html?ReturnUrl=/categoria/24/prensa_seria.html',16,0.00780501,1,0.00775662,0,0.00644988,7,0.00454261,9,0.00454261),(83,'http://www.eljueves.es/login.html?ReturnUrl=/categoria/25/manda_guevos.html',16,0.00780501,1,0.00775662,0,0.00644988,7,0.00454261,9,0.00454261),(84,'http://www.eljueves.es/pagina/ultimos_videos.html',0,0.0109756,17,0.00708962,1,0.00642763,22,0.00496669,16,0.00461917),(85,'http://www.elmundo.es/america/2011/02/03/economia/1296756585.html',0,0.0167963,17,0.0123534,8,0.01229,6,0.00830705,43,0.00711527),(86,'http://www.elmundo.es/blogs/elmundo/clima/2011/02/02/catrastrofe-economica-y-la-ciencia-de-la.html',4,0.0373991,17,0.0251622,15,0.0234505,10,0.0200003,2,0.0138041),(87,'http://www.elmundo.es/elmundo/2011/01/31/ciencia/1296481064.html',8,0.0158125,0,0.0124188,43,0.0118412,17,0.0113846,6,0.0109625),(88,'http://www.elmundo.es/elmundo/2011/02/01/ciencia/1296577965.html',43,0.0166057,17,0.0152984,3,0.00996301,11,0.00933313,4,0.00750705),(89,'http://www.elmundo.es/mundodinero/2011/02/02/economia/1296633838.html',8,0.0451373,6,0.0305657,33,0.0182206,32,0.0132189,0,0.0131768),(90,'http://www.elmundo.es/mundodinero/2011/02/02/economia/1296650839.html',8,0.0365743,6,0.0255478,0,0.0114007,33,0.0082202,17,0.00810345),(91,'http://www.elmundo.es/mundodinero/2011/02/03/economia/1296741439.html',8,0.0453733,6,0.0350613,17,0.0184726,0,0.0144744,1,0.00893132),(92,'http://www.elmundo.es/mundodinero/2011/02/03/economia/1296742149.html',8,0.0496867,6,0.0375441,17,0.0150108,0,0.0144167,33,0.00945729),(93,'http://www.elmundo.es/mundodinero/2011/02/03/economia/1296744631.html',8,0.0362731,6,0.0257811,0,0.0122043,17,0.00989451,43,0.00802877),(94,'http://www.elmundo.es/mundodinero/2011/02/03/economia/1296753256.html',8,0.0410144,6,0.0309609,0,0.0197869,17,0.0128836,33,0.00850295),(95,'http://www.elmundo.es/mundodinero/2011/02/03/economia/1296758523.html',8,0.0364594,6,0.0294365,0,0.0116057,17,0.0106949,33,0.00754395),(96,'http://www.elpais.com/articulo/cine/Vida/santa/elpepucin/20110128elpepicin_10/Tes',17,0.0311371,31,0.0261782,0,0.0177607,1,0.0175913,43,0.0122611),(97,'http://www.elpais.com/articulo/cine/tiempo/comedia/elpepuculcin/20110128elpepicin_4/Tes',17,0.0354695,31,0.030853,0,0.0193116,1,0.0189425,43,0.0166015),(98,'http://www.elpais.com/articulo/cultura/debutantes/muchas/ganas/cine/elpepucul/20110125elpepucul_25/Tes?print=1',3,0.0139499,0,0.00928257,33,0.00836828,43,0.00771277,31,0.0043561),(99,'http://www.elpais.com/articulo/portada/Especial/Red/elpepuculbab/20110129elpbabpor_19/Tes?print=1',42,0.0275044,17,0.0150356,22,0.012249,18,0.0100856,33,0.00854093),(100,'http://www.elpais.com/articulo/portada/Espejo/torbellino/elpepuculbab/20110129elpbabpor_13/Tes',17,0.0276259,31,0.0193938,1,0.0157012,43,0.0149799,0,0.0118727),(101,'http://www.elpais.com/articulo/portada/Espejo/torbellino/elpepuculbab/20110129elpbabpor_13/Tes/',17,0.0276259,31,0.0193938,1,0.0157012,43,0.0149799,0,0.0118727),(102,'http://www.elpais.com/articulo/portada/ira/Henry/James/elpepuculbab/20110129elpbabpor_15/Tes',17,0.0455996,31,0.0231524,43,0.0222988,1,0.0176115,0,0.0155652),(103,'http://www.elpais.com/articulo/portada/ira/Henry/James/elpepuculbab/20110129elpbabpor_15/Tes/',17,0.0455996,31,0.0231524,43,0.0222988,1,0.0176115,0,0.0155652),(104,'http://www.elpais.com/articulo/portada/mil/historias/elpepuculbab/20110129elpbabpor_17/Tes/',17,0.0381827,31,0.0242367,1,0.0202976,0,0.016574,43,0.0158837),(105,'http://www.elpais.com/articulo/portada/presentes/lejanos/elpepuculbab/20110129elpbabpor_11/Tes',17,0.0367532,31,0.0181803,43,0.0138428,1,0.01354,0,0.0124222),(106,'http://www.elpais.com/articulo/portada/presentes/lejanos/elpepuculbab/20110129elpbabpor_11/Tes/',17,0.0367532,31,0.0181803,43,0.0138428,1,0.01354,0,0.0124222),(107,'http://www.elpais.com/articulo/portada/ventana/Nazario/elpepuculbab/20110129elpbabpor_2/Tes',17,0.0324987,31,0.024138,1,0.0212716,43,0.0172687,0,0.0136636),(108,'http://www.elpais.com/articulo/tecnologia/Quien/sube/peliculas/BitTorrent/elpepuculcin/20110126elpeputec_3/Tes',17,0.0317608,31,0.0242928,1,0.0186434,43,0.0182258,0,0.0138393),(109,'http://www.elpais.com/articulo/tecnologia/Quien/sube/peliculas/BitTorrent/elpeputec/20110126elpeputec_3/Tes?print=1',43,0.0143799,22,0.00971549,17,0.00867833,4,0.00808189,3,0.00804468),(110,'http://www.elpais.com/deportes/formula1/escuderias/mclaren',0,0.0262411,31,0.0231975,17,0.0219697,43,0.0155796,8,0.0112532),(111,'http://www.elpais.com/deportes/formula1/escuderias/mercedes',17,0.0262209,31,0.0175595,0,0.0169706,28,0.0130104,33,0.012976),(112,'http://www.elpais.com/deportes/formula1/pilotos/felipe-massa',0,0.0238598,31,0.0172365,17,0.0152593,43,0.0125945,1,0.00880924),(113,'http://www.elpais.com/deportes/formula1/pilotos/jenson-button',0,0.021512,31,0.0178341,17,0.0152322,43,0.0129012,33,0.00927376),(114,'http://www.elpais.com/deportes/formula1/pilotos/lewis-hamilton',0,0.0309219,17,0.0178089,30,0.015165,31,0.0147631,33,0.0116145),(115,'http://www.elpais.com/deportes/formula1/pilotos/mark-webber',26,0.0564091,0,0.0182227,31,0.0155076,17,0.0142958,1,0.0136349),(116,'http://www.elpais.com/deportes/formula1/pilotos/nico-rosberg',0,0.0237371,33,0.0175199,17,0.0174065,31,0.0160743,43,0.0112526),(117,'http://www.elpais.com/deportes/formula1/pilotos/robert-kubica',0,0.0164434,17,0.0163859,31,0.0128867,33,0.00958964,43,0.00940818),(118,'http://www.elpais.com/deportes/futbol/partido.html?p=0545_00_22_0003_0002',17,0.0324872,31,0.0234017,8,0.0228164,6,0.0185323,28,0.0161614),(119,'http://www.elpais.com/deportes/futbol/partido.html?p=0545_00_22_0013_0014',17,0.0323663,31,0.0233147,8,0.0218427,6,0.0177326,33,0.0157122),(120,'http://www.elpais.com/deportes/futbol/partido.html?p=0545_00_22_0020_0227',17,0.0324731,31,0.0233916,8,0.0220681,6,0.0179165,33,0.0152472),(121,'http://www.elpais.com/deportes/futbol/partido.html?p=0545_00_22_0022_0129',17,0.0324643,31,0.0233852,8,0.0210992,6,0.0171204,33,0.0152432),(122,'http://www.elpais.com/deportes/futbol/partido.html?p=0545_00_22_0042_0221',17,0.0324643,31,0.0233852,8,0.0210992,6,0.0171204,33,0.0152432),(123,'http://www.elpais.com/deportes/futbol/partido.html?p=0545_00_22_0134_0133',17,0.032482,31,0.023398,8,0.0230585,6,0.0187304,33,0.0152512),(124,'http://www.elpais.com/deportes/futbol/partido.html?p=0545_00_22_0140_0035',17,0.0324643,31,0.0233852,8,0.0210992,6,0.0171204,33,0.0152432),(125,'http://www.elpais.com/deportes/motociclismo/pilotos/alex-de-angelis',17,0.0239363,31,0.0154338,33,0.0107706,8,0.0103672,36,0.00847663),(126,'http://www.elpais.com/deportes/motociclismo/pilotos/ben-spies',17,0.0255563,31,0.0172549,33,0.011595,0,0.0105903,8,0.0100885),(127,'http://www.elpais.com/deportes/motociclismo/pilotos/gabor-talmacsi',17,0.023948,31,0.0154416,33,0.010776,8,0.0103724,36,0.00877484),(128,'http://www.elpais.com/deportes/motociclismo/pilotos/julian-simon',17,0.023992,31,0.0159847,33,0.0108555,8,0.0104489,36,0.00883953),(129,'http://www.elpais.com/deportes/motociclismo/pilotos/kenny-noyes',17,0.0240891,31,0.0151174,8,0.0112332,33,0.0111286,28,0.00943056),(130,'http://www.elpais.com/deportes/motociclismo/pilotos/marc-marquez',17,0.0251876,31,0.0170607,33,0.0119843,8,0.0104452,28,0.00990676),(131,'http://www.elpais.com/deportes/motociclismo/pilotos/toni-elias',17,0.0240333,31,0.0154965,33,0.0112047,8,0.0104093,0,0.00942857),(132,'http://www.estiloymoda.com/articulos/aerosoles-aperturas-febrero07.php',4,0.0309889,31,0.0274097,14,0.0264691,0,0.0232447,34,0.0133029),(133,'http://www.estiloymoda.com/articulos/converse-aperturas-07.php',4,0.0751792,14,0.0687168,31,0.0511758,0,0.0292495,1,0.019292),(134,'http://www.estiloymoda.com/articulos/guess-holiday-accesories-2010.php',4,0.050489,14,0.0330653,31,0.0235207,0,0.0143067,1,0.0109629),(135,'http://www.estiloymoda.com/articulos/regalos-navidad-2010-online.php',31,0.0341456,4,0.0317841,14,0.0313037,1,0.017098,0,0.0131335),(136,'http://www.estiloymoda.com/articulos/springfield-preciados-madrid08.php',4,0.0603948,14,0.0551094,31,0.0431348,0,0.0379554,1,0.0200676),(137,'http://www.estiloymoda.com/articulos/tous-cumpleanos05.php',14,0.0284877,4,0.0258124,31,0.0159208,1,0.0134128,0,0.0133456),(138,'http://www.estiloymoda.com/articulos/tous-kylieminogue-concierto-pdm08.php',4,0.0322909,14,0.0232239,31,0.0187986,0,0.014086,22,0.0110577),(139,'http://www.estiloymoda.com/articulos/vertiche-bolsos-exclusivos07.php',4,0.033625,31,0.0322105,14,0.0270029,0,0.0141266,1,0.00732335),(140,'http://www.estiloymoda.com/articulos/vertiche-modaonline-firmas07.php',4,0.0662888,31,0.0618742,14,0.040072,0,0.0191034,1,0.0173624),(141,'http://www.estiloymoda.com/articulos/vestuario-saga-crepusculo-expo10.php',14,0.0432544,4,0.0416916,31,0.0260654,0,0.0237789,1,0.00700371),(142,'http://www.europapress.es/nacional/noticia-economia-legal-afectados-filatelicos-piden-amparo-cgpj-acelerar-procesos-judiciales-abiertos-2006-20110120171259.html',2,0.0218354,22,0.0189113,17,0.0157736,25,0.0157313,8,0.0148356),(143,'http://www.europapress.es/nacional/noticia-economia-legal-constitucional-modifica-eleccion-representantes-planes-pensiones-empresas-20110121111751.html',22,0.0202292,2,0.0200785,31,0.0154606,25,0.0153871,8,0.0125735),(144,'http://www.europapress.es/nacional/noticia-economia-legal-diaz-ferran-emplaza-pullmantur-reclamar-deuda-millones-proceso-concursal-20110120132529.html',2,0.0211704,22,0.0177924,25,0.0149335,31,0.013508,8,0.0132229),(145,'http://www.europapress.es/nacional/noticia-economia-legal-foro-derecho-concursal-reunira-35-jueces-debatir-reforma-ley-concursal-20110120181909.html',2,0.0239028,22,0.0203496,25,0.0162881,8,0.0137332,31,0.0131626),(146,'http://www.europapress.es/nacional/noticia-economia-legal-hosteleros-demandaran-estado-obras-realizadas-anterior-ley-antitabaco-20110120143547.html',2,0.0206336,31,0.0185284,22,0.0182966,25,0.0150804,8,0.013201),(147,'http://www.europapress.es/nacional/noticia-economia-legal-posibilitum-recusa-dos-administradores-concursales-matriz-grupo-marsans-20110119194322.html',2,0.0205146,22,0.0187971,25,0.0154529,31,0.012471,0,0.0109737),(148,'http://www.europapress.es/salud/asistencia-00670/noticia-coordinacion-patologos-oncologos-clave-terapias-individualizadas-cancer-pulmon-20110203173116.html',31,0.0189622,22,0.0152967,8,0.0139186,2,0.0122739,43,0.0112697),(149,'http://www.europapress.es/salud/asistencia-00670/noticia-unido-test-sanguineo-podria-detectar-portadores-variante-humana-mal-vacas-locas-20110203181229.html',31,0.0193857,22,0.0172358,43,0.0135239,2,0.0128012,8,0.0127552),(150,'http://www.europapress.es/salud/investigacion-00669/noticia-eeuu-producen-vasos-sanguineos-listos-injertar-pacientes-cirugia-cardiaca-20110203104015.html',22,0.0167716,31,0.0160213,2,0.0114977,8,0.0112401,43,0.0101766),(151,'http://www.europapress.es/salud/noticia-investigadores-centro-regulacion-genomica-barcelona-identifican-oncogen-causante-cancer-piel-20110203180807.html',31,0.0229425,22,0.0181115,8,0.0150411,2,0.0145073,0,0.0133942),(152,'http://www.europapress.es/salud/noticia-menos-17000-casos-cancer-pulmon-podrian-evitarse-cada-ano-20110203172957.html',22,0.0170996,31,0.0169424,2,0.0137012,17,0.0118264,0,0.0117278),(153,'http://www.europapress.es/salud/politica-sanitaria-00666/noticia-ue-eurocamara-insta-redoblar-esfuerzos-contra-tuberculosis-remitir-casos-partir-2015-20110203181812.html',22,0.0167533,8,0.0163476,31,0.016053,2,0.0148374,25,0.0130466),(154,'http://www.europapress.es/salud/salud-bienestar-00667/noticia-componente-ketchup-puede-ayudar-prevenir-distintos-tipos-cancer-20110203185456.html',22,0.0207291,31,0.0203236,2,0.0126909,0,0.0117109,43,0.0115878),(155,'http://www.europapress.es/salud/salud-bienestar-00667/noticia-investigadores-inef-estudian-beneficios-ejercicio-fisico-contra-cancer-mama-20110203182626.html',22,0.0213787,31,0.0198418,17,0.0137685,0,0.0135717,2,0.0127219),(156,'http://www.europapress.es/salud/salud-bienestar-00667/noticia-medicina-personalizada-ayudara-minimizar-impacto-efectos-secundarios-20110203143655.html',31,0.0234802,22,0.0195731,0,0.0158297,2,0.0122851,43,0.0122733),(157,'http://www.forosderealeza.com/',0,0.0429162,18,0.0135622,1,0.0119476,31,0.0083726,22,0.00726186),(158,'http://www.forosderealeza.com/foros/index.php?action=login',1,0.10337,31,0.00798924,40,0.00771264,0,0.00752512,16,0.00463674),(159,'http://www.hola.com/famosos/2011011950736/carmen/martinezbordiu/josecampos/1/',0,0.126337,31,0.0144416,1,0.0135746,17,0.00962894,33,0.0075384),(160,'http://www.hola.com/famosos/2011011950745/nuria/roca/reaparece/1/',0,0.0993544,31,0.0242556,1,0.0149073,17,0.0117054,33,0.00838389),(161,'http://www.hola.com/famosos/2011012050753/obama/jintao/cena/1/',0,0.0697407,31,0.0210358,1,0.0111801,17,0.00962171,43,0.00765068),(162,'http://www.hola.com/famosos/2011012150783/francisco/rivera/entrevista/1/',0,0.114552,31,0.0213355,1,0.0132095,33,0.0100273,17,0.00798818),(163,'http://www.hola.com/famosos/2011012450814/litri/medalla/huelva/1/',0,0.0810559,31,0.0133782,17,0.0109291,1,0.00987573,43,0.00847946),(164,'http://www.hola.com/famosos/2011012550818/ikercasillas/embajador/onu/1/',0,0.0934639,31,0.020147,33,0.0123675,43,0.0097072,1,0.00862498),(165,'http://www.hola.com/famosos/2011012550830/jaydy/mitchel/entrevista/1/',0,0.12315,31,0.0225586,1,0.0119711,33,0.00955467,17,0.00680995),(166,'http://www.hola.com/famosos/2011012750868/famosos/desfiles/altacosturaparis/1/',0,0.0986325,31,0.0198817,1,0.0119641,4,0.00812144,33,0.00724403),(167,'http://www.hola.com/famosos/especiales/misterespana2008/votacion/',0,0.137446,31,0.0275972,1,0.0212754,17,0.0146911,4,0.012362),(168,'http://www.hola.com/realeza/casa_holanda/2011012850884/maxima-holanda/inauguracion/centro-cultural/1/',0,0.188476,31,0.0201887,1,0.0127373,43,0.00997667,17,0.00632195),(169,'http://www.hola.com/realeza/casa_holanda/galeria/2011012850884/maxima-holanda/inauguracion/centro-cultural/1/1/',0,0.0983216,1,0.0137774,5,0.0116709,17,0.008323,40,0.00785946),(170,'http://www.hola.com/realeza/casa_noruega/2011012750831/principe-haakon/princesa-mette-marit/regreso-nuruega/1/',0,0.182048,31,0.0160722,1,0.00984622,14,0.00824367,33,0.00795447),(171,'http://www.hola.com/realeza/casa_noruega/galeria/2011012750831/principe-haakon/princesa-mette-marit/regreso-nuruega/1/1/',0,0.114197,14,0.0132891,1,0.00990622,5,0.00839155,17,0.00598438),(172,'http://www.hola.com/tags/realeza/casa_espanola/principe-felipe-1.html',0,0.356364,43,0.0165549,17,0.0118329,31,0.0102339,1,0.00843296),(173,'http://www.hola.com/tags/realeza/principe-felipe/112010112050022-2',0,0.3354,31,0.0114629,43,0.0100277,33,0.00820569,1,0.0070122),(174,'http://www.lavanguardia.es/comunicacion/20110130/54107607792/medioson-aboga-por-mas-interaccion-entre-los-medios-para-lograr-un-negocio-sostenible-en.html',16,0.0314163,17,0.0138562,1,0.0135595,43,0.0131562,31,0.0131419),(175,'http://www.lavanguardia.es/deportes/barcelona-world-race/index.html',8,0.0282956,6,0.0250703,33,0.0167829,3,0.0147484,43,0.00812633),(176,'http://www.lavanguardia.es/deportes/motociclismo/index.html',28,0.0336086,33,0.0327877,3,0.0314914,32,0.0309369,31,0.028608),(177,'http://www.lavanguardia.es/deportes/otros/index.html',32,0.0375605,3,0.0345706,8,0.0306141,28,0.0301624,33,0.0300878),(178,'http://www.lavanguardia.es/especiales/nba/20110202/54109301366/pau-gasol-se-reivindica-en-su-tercer-aniversario-como-laker.html',3,0.0117108,8,0.0109204,31,0.00956451,22,0.00915393,35,0.00648049),(179,'http://www.lavanguardia.es/politica/20110203/54109599279/valeriano-gomez-contradice-a-salgado-espana-rebajara-algo-la-tasa-de-paro-en-2012.html#comentarios',17,0.0217072,33,0.0102612,0,0.00939001,32,0.00909759,31,0.00909664),(180,'http://www.lavanguardia.es/politica/20110203/54109621328/ciutadans-acusa-a-mas-de-aprovecharse-de-su-cargo-para-dejar-las-urnas-a-sus-amigos.html',8,0.0181676,17,0.0143635,32,0.0132961,33,0.010735,6,0.00983209),(181,'http://www.lavanguardia.es/politica/20110203/54109668678/duran-ve-aberrante-que-laporta-recurra-al-tc-decisiones-del-parlament.html#comentarios',17,0.012017,8,0.0119762,33,0.00737071,16,0.00628772,0,0.00621609),(182,'http://www.lavanguardia.es/politica/20110203/54110409097/erc-acusa-al-govern-de-alimentar-el-discurso-contra-catalunya-por-la-situacion-financiera.html',8,0.0158798,31,0.00959468,17,0.00866747,32,0.00862531,33,0.00814918),(183,'http://www.lavanguardia.es/politica/20110203/54110409097/erc-acusa-al-govern-de-alimentar-el-discurso-contra-catalunya-por-la-situacion-financiera.html#comentarios',8,0.0158798,31,0.00959468,17,0.00866747,32,0.00862531,33,0.00814918),(184,'http://www.lavanguardia.es/politica/20110203/54110456560/el-psc-replica-a-mas-que-montilla-siempre-viajo-en-turista-y-no-lo-publicito.html',3,0.0139486,32,0.0130553,33,0.0125926,0,0.0113945,28,0.00949478),(185,'http://www.lavanguardia.es/politica/20110203/54110456560/el-psc-replica-a-mas-que-montilla-siempre-viajo-en-turista-y-no-lo-publicito.html#comentarios',3,0.0139486,32,0.0130553,33,0.0125926,0,0.0113945,28,0.00949478),(186,'http://www.lavanguardia.es/sucesos/20110118/54103082476/india-detiene-al-guru-del-porno-por-rodar-peliculas-en-lugares-sagrados.html',17,0.0192649,0,0.00922997,22,0.00799344,3,0.0071146,33,0.00634589),(187,'http://www.lavanguardia.es/sucesos/20110202/54110227937/detenida-en-vizcaya-una-pareja-de-34-anos-por-maltrato-habitual-a-su-hija-de-17-anos.html',17,0.0178244,22,0.0169291,0,0.012114,11,0.0098509,16,0.007336),(188,'http://www.lavanguardia.es/sucesos/20110202/54110227937/detenida-en-vizcaya-una-pareja-de-34-anos-por-maltrato-habitual-a-su-hija-de-17-anos.html#comentarios',17,0.0178244,22,0.0169291,0,0.012114,11,0.0098509,16,0.007336),(189,'http://www.lavanguardia.es/sucesos/20110202/54110228291/la-madre-de-la-amiga-de-marta-dice-que-la-menor-nunca-salio-viva-del-piso-de-miguel-carcano.html',17,0.0110266,22,0.0100113,16,0.00902274,8,0.00867071,33,0.00802015),(190,'http://www.lavanguardia.es/sucesos/20110203/54109610679/123-muertos-y-miles-de-afectados-por-las-fuertes-lluvias-en-africa-del-sur.html',17,0.0286478,8,0.0174129,33,0.0132804,22,0.0128311,11,0.0118249),(191,'http://www.mangaes.com/enciclopedia/',1,0.538121,40,0.0699494,43,0.015201,17,0.00576601,0,0.00506064),(192,'http://www.mangaes.com/ocio/videojuegos/',1,0.542575,32,0.0532281,37,0.0513839,19,0.0513839,27,0.0498125),(193,'http://www.marca.com/2010/09/29/mas_deportes/ajedrez/1285766811.html',3,0.0937689,33,0.0628463,31,0.0264553,28,0.0246185,36,0.0215212),(194,'http://www.marca.com/2010/09/29/mas_deportes/ajedrez/1285773980.html',3,0.0706016,33,0.0647487,28,0.0434869,36,0.0348343,31,0.0278828),(195,'http://www.marca.com/2011/01/05/mas_deportes/poker/1294232176.html',3,0.0628369,33,0.0460676,31,0.0314744,28,0.0303712,36,0.0258661),(196,'http://www.marca.com/2011/01/09/mas_deportes/poker/1294559783.html',3,0.09203,33,0.0494326,28,0.0323199,36,0.0274782,31,0.0235672),(197,'http://www.marca.com/2011/01/10/mas_deportes/poker/1294660978.html',3,0.0956285,33,0.0738752,28,0.049899,36,0.0399617,31,0.0334117),(198,'http://www.marca.com/2011/01/10/mas_deportes/poker/1294673080.html',3,0.0592695,33,0.0382133,28,0.0253261,31,0.023191,36,0.0210687),(199,'http://www.marca.com/2011/01/12/mas_deportes/poker/1294832659.html',3,0.0673545,33,0.0514608,28,0.0436171,36,0.0349297,31,0.0244163),(200,'http://www.marca.com/2011/01/12/mas_deportes/poker/1294833252.html',3,0.092998,33,0.0461423,28,0.0243474,31,0.0239159,36,0.0201693),(201,'http://www.marca.com/2011/01/13/mas_deportes/poker/1294921242.html',3,0.0675698,33,0.0503666,28,0.0404668,36,0.0310132,31,0.0209202),(202,'http://www.marca.com/2011/01/14/mas_deportes/poker/1295001900.html',3,0.089401,33,0.0511235,28,0.0356045,36,0.0285106,31,0.0265009),(203,'http://www.marca.com/2011/01/15/mas_deportes/poker/1295090707.html',3,0.107356,33,0.0469314,31,0.0249864,28,0.0191447,36,0.0157521),(204,'http://www.marca.com/2011/01/16/mas_deportes/poker/1295182973.html',3,0.076147,33,0.0476904,28,0.0367858,36,0.0294597,31,0.0225506),(205,'http://www.marca.com/2011/01/26/atletismo/1296043846.html',31,0.0583484,3,0.0439252,28,0.035298,42,0.033491,36,0.0298194),(206,'http://www.marca.com/2011/01/27/mas_deportes/nieve/1296152412.html',3,0.0650498,33,0.0595435,28,0.0477339,31,0.0377574,36,0.0365472),(207,'http://www.marca.com/2011/01/28/atletismo/1296170845.html',28,0.0512671,3,0.0482628,33,0.0449414,36,0.0398453,31,0.0224239),(208,'http://www.marca.com/2011/01/28/atletismo/1296170845.html#comentarios',28,0.0512671,3,0.0482628,33,0.0449414,36,0.0398453,31,0.0224239),(209,'http://www.marca.com/2011/01/28/mas_deportes/nieve/1296222697.html',3,0.0690242,33,0.0568521,28,0.0480307,36,0.0382699,31,0.0299454),(210,'http://www.marca.com/2011/01/28/mas_deportes/nieve/1296247722.html',3,0.0612734,33,0.0496567,28,0.0413736,36,0.0327374,31,0.0225377),(211,'http://www.marca.com/2011/01/29/mas_deportes/nieve/1296310105.html',3,0.0654233,33,0.0563839,28,0.0470852,36,0.0360316,31,0.0249196),(212,'http://www.marca.com/2011/01/30/atletismo/1296381940.html#comentarios',28,0.0487994,3,0.0487969,36,0.0413477,33,0.0387609,31,0.0236847),(213,'http://www.marca.com/2011/01/30/mas_deportes/nieve/1296411983.html',3,0.0471595,33,0.0361875,28,0.0316039,36,0.0242992,31,0.0236788),(214,'http://www.marca.com/2011/01/30/mas_deportes/nieve/1296411983.html#comentarios',3,0.0471595,33,0.0361875,28,0.0316039,36,0.0242992,31,0.0236788),(215,'http://www.marca.com/2011/01/31/mas_deportes/rugby/1296480842.html',3,0.0527908,33,0.043765,31,0.022249,28,0.0211535,36,0.0175097),(216,'http://www.marca.com/2011/01/31/mas_deportes/rugby/1296480842.html#comentarios',3,0.0527908,33,0.043765,31,0.022249,28,0.0211535,36,0.0175097),(217,'http://www.marca.com/2011/02/02/mas_deportes/nieve/1296681374.html',3,0.0628402,33,0.0521055,28,0.0457864,36,0.0362558,31,0.0259044),(218,'http://www.marca.com/2011/02/02/mas_deportes/nieve/1296681374.html#comentarios',3,0.0628402,33,0.0521055,28,0.0457864,36,0.0362558,31,0.0259044),(219,'http://www.marca.com/2011/02/02/mas_deportes/rugby/1296668030.html#comentarios',33,0.0560444,3,0.0554052,28,0.0256832,31,0.0255745,36,0.0241615),(220,'http://www.marca.com/2011/02/03/baloncesto/1296746708.html',3,0.0483727,28,0.0366869,33,0.032028,36,0.0311762,31,0.028071),(221,'http://www.marca.com/2011/02/03/baloncesto/1296746708.html#comentarios',3,0.0483727,28,0.0366869,33,0.032028,36,0.0311762,31,0.028071),(222,'http://www.marca.com/2011/02/03/baloncesto/euroliga/1296770090.html#comentarios',3,0.037077,28,0.0294089,33,0.0254483,36,0.0242199,31,0.0165448),(223,'http://www.marca.com/2011/02/03/baloncesto/euroliga/1296770678.html',3,0.0437789,33,0.0263238,31,0.0248754,28,0.0239553,36,0.0196655),(224,'http://www.marca.com/2011/02/03/baloncesto/euroliga/1296770678.html#comentarios',3,0.0437789,33,0.0263238,31,0.0248754,28,0.0239553,36,0.0196655),(225,'http://www.marca.com/2011/02/03/baloncesto/euroliga/1296771167.html',3,0.0404122,33,0.0345579,28,0.0270677,31,0.0266051,36,0.0223135),(226,'http://www.marca.com/2011/02/03/baloncesto/euroliga/1296771167.html#comentarios',3,0.0404122,33,0.0345579,28,0.0270677,31,0.0266051,36,0.0223135),(227,'http://www.marca.com/2011/02/03/baloncesto/nba/1296720479.html',3,0.0321437,33,0.0187904,31,0.0171206,28,0.0170914,36,0.0131657),(228,'http://www.marca.com/2011/02/03/baloncesto/nba/1296722495.html',3,0.0397384,31,0.0317717,28,0.030187,33,0.0279107,36,0.0247544),(229,'http://www.marca.com/2011/02/03/baloncesto/nba/1296769285.html',3,0.0387482,33,0.0303567,28,0.029239,31,0.0234284,36,0.0222871),(230,'http://www.marca.com/2011/02/03/baloncesto/nba/1296769285.html#comentarios',3,0.0387482,33,0.0303567,28,0.029239,31,0.0234284,36,0.0222871),(231,'http://www.marca.com/2011/02/03/mas_deportes/rugby/1296730030.html#comentarios',33,0.135629,3,0.0703241,28,0.0485031,36,0.0388369,31,0.0259806),(232,'http://www.marca.com/deporte/nieve/mundial/calendario.html',33,0.143786,3,0.12842,28,0.0920631,36,0.090005,31,0.0412649),(233,'http://www.marcaplayer.com/estaticas/revista.html',3,0.0115377,33,0.0111126,36,0.00903456,28,0.00725499,31,0.0056956),(234,'http://www.noticias.com/noticias/psicologicas/2',8,0.356663,6,0.194446,31,0.0943918,1,0.0691711,42,0.0668438),(235,'http://www.soitu.es/soitu/2009/05/25/info/1243271163_429406.html',11,0.0308302,43,0.0138469,16,0.00977937,31,0.00905696,3,0.00834714),(236,'http://www.soitu.es/soitu/2009/06/05/info/1244227316_749404.html',11,0.0176852,43,0.0133962,22,0.011396,3,0.0105367,31,0.0101335),(237,'http://www.soitu.es/soitu/2009/06/09/info/1244562860_437291.html',11,0.0275238,43,0.0141975,3,0.0104422,22,0.00965168,31,0.00834527),(238,'http://www.soitu.es/soitu/2009/06/10/info/1244653783_539418.html',43,0.015237,11,0.0146119,22,0.0103401,0,0.00873785,31,0.00849866);
/*!40000 ALTER TABLE `list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-02-11 11:31:13
