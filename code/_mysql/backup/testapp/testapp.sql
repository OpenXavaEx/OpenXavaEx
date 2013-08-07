-- MySQL dump 10.11
--
-- Host: localhost    Database: testapp
-- ------------------------------------------------------
-- Server version	5.0.67-community-nt

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `customer` (
  `number` int(11) NOT NULL,
  `name` varchar(40) default NULL,
  `photo` tinyblob,
  PRIMARY KEY  (`number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `images` (
  `ID` varchar(255) NOT NULL,
  `GALLERY` varchar(255) default NULL,
  `IMAGE` mediumblob,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `invoice` (
  `id` varchar(32) NOT NULL,
  `date` datetime default NULL,
  `number` int(11) default NULL,
  `remarks` varchar(255) default NULL,
  `vatPercentage` int(11) default NULL,
  `year` int(11) default NULL,
  `customer_number` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKD80EDB0D3D6769C9` (`customer_number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoicedetail`
--

DROP TABLE IF EXISTS `invoicedetail`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `invoicedetail` (
  `id` varchar(32) NOT NULL,
  `quantity` int(11) NOT NULL,
  `invoice_id` varchar(32) default NULL,
  `product_number` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK33BEA1BE48069759` (`invoice_id`),
  KEY `FK33BEA1BE475FCE47` (`product_number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `invoicedetail`
--

LOCK TABLES `invoicedetail` WRITE;
/*!40000 ALTER TABLE `invoicedetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoicedetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_sku_change`
--

DROP TABLE IF EXISTS `log_sku_change`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `log_sku_change` (
  `id` varchar(32) NOT NULL,
  `action` varchar(255) default NULL,
  `changeTime` datetime default NULL,
  `skuId` varchar(255) default NULL,
  `skuName` varchar(255) default NULL,
  `userName` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `log_sku_change`
--

LOCK TABLES `log_sku_change` WRITE;
/*!40000 ALTER TABLE `log_sku_change` DISABLE KEYS */;
INSERT INTO `log_sku_change` VALUES ('ff8081814056b771014056b909150001','Insert','2013-08-07 11:01:38','ff8081814056b771014056b8e43f0000','æµ‹è¯•01','tester'),('ff8081814056b771014056b95f470002','Update','2013-08-07 11:02:03','ff8081814056b771014056b8e43f0000','æµ‹è¯•01-ä¿®æ”¹å','tester'),('ff8081814056b771014056b9a09a0003','Remove','2013-08-07 11:02:20','ff8081814056b771014056b8e43f0000','æµ‹è¯•01-ä¿®æ”¹å','tester');
/*!40000 ALTER TABLE `log_sku_change` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_sku`
--

DROP TABLE IF EXISTS `md_sku`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `md_sku` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) default NULL,
  `descr` varchar(255) default NULL,
  `enabled` bit(1) default NULL,
  `name` varchar(255) default NULL,
  `version` datetime default NULL,
  `photo` mediumblob,
  `price` decimal(19,2) default NULL,
  `uom_id` varchar(32) NOT NULL,
  `vendor_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`),
  KEY `FK874FD655B6473876` (`vendor_id`),
  KEY `FK874FD655B1DEBE9E` (`uom_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `md_sku`
--

LOCK TABLES `md_sku` WRITE;
/*!40000 ALTER TABLE `md_sku` DISABLE KEYS */;
INSERT INTO `md_sku` VALUES ('ff8081813f91c906013f91cff0840002','1746A2S','IBM DS3512 å•æ§åˆ¶å™¨å­˜å‚¨ 1746A2S (å•æ§åˆ¶å™¨,æ ‡é…12ä¸ªç¡¬ç›˜ä½,æ— ç¡¬ç›˜,æ¯æ§åˆ¶å™¨1GBç¼“å­˜)','','IBM DS3512 å•æ§åˆ¶å™¨å­˜å‚¨ 1746A2S','2013-06-30 05:21:31','ÿØÿà\0JFIF\0\0\0d\0d\0\0ÿì\0Ducky\0\0\0\0\0d\0\0ÿî\0Adobe\0dÀ\0\0\0ÿÛ\0„\0ÿÀ\0\0l\0ú\0ÿÄ\0ª\0\0\0\0\0\0\0\0\0\0\0	\n\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0!1AQ\"	a2#\nqBRr’3$%‘¡‚±C4D&\0\0\0\0\0!1AğQaq‘±Á\"2¡ÑBá#ñRb3r’ÿÚ\0\0\0?\0÷ñ \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z \Z 8^‘2’e†ÀÜ­çPÒ\0÷•-I\0h6Ã“¸Ú¥JM§ áËGæDìªŠ+‰ÛØPüô/øj»ëŞˆ•ŞcëOTéÖ[Ë¸Wz|SÙgø¶\"fç¦£Ô§yõ\\ÑbYzàô×^¢†³ÇíÖ?–ŸÉ¦àª–Z?âÔz´äUåÆ¹–lï_Ü@ÑCŠrÆR÷ò\"£\nR³ìøçXEpoıÃª¼õ\\dzÔ|$³­~àñ«Rà.Qb¸º\"Uªë*]n9#½Ö#¾Cï%\'pÚl+Ú¡¬ŸUÅÁ4rdã?U#Ê‹\n‡1S>R‹eˆş›¿óÔ <k\"-›½çaòoHØu®<ø²}¯_Ez¿2DkbÃ@4@4@4@4@4@4@p¿\"<VË²_f;Iê§_u¶ºõ[ŠJGOÇFÒâFÇ’8î\nrÛ=Ã+û•a”RCoy‘9¾½5Wz.-{Ä3YzŸôóR….g2qù;ÃÈ Ù¹¿¹-V¹-Õÿ\0ÀQçÂ¸Ù°,}rúa¯JŠ9%N\'ÁŠ¬s)–êÏ¹èÍ´Ä³}^Î}Œ¬´÷áw»şò•ÿ\0ná+¬Ã‘å¸@şS*Ö;©ÅEÕb·Û%mô©e¼ï¯THRÍrTö“¿k¶WÓ¤íí_éNCâ:Ÿùı¬¦÷É2Ë°õÕÊ]Ri¸k¨i=İËÊy±\'aÓ¹¿öˆ> ª¾¢Ó¢^ò¯#î-	¾´¹Ös…¶Oâèr¥K·¾p~Ğll£üª¼ù?ñ!ä~›kê—Ÿ¦¿\riælR©¨ÒÚ”ëXÇ91©IBOÉÉUî<§Å8°Û­¬©	Ù`wUæÉşåîşƒÔ·iÛó·.YÙ¼ñÈ/¡[÷§Æ)ñ¾ŠñJ‰cá°=ÃPò]ÿ\0&UŞİå—;,³±mB~yÎW‹XıDØòã+q×ı;SØïWtñv+¹÷²É“ôØş=ohû„ìå¾ku8¨8Ôv  \0|I#®›ª¸X‰XX¨˜fÁ‹ä…™ë¸—1Å>¢juã­ÉR@İ{6BwVÀå]DÂ*v›rÂò58”@ŸÌbbXÃ¯\'m€R][*G‡´+ş:,‰‡Èøvß4J{“q>\ZzˆXÑëü¿Mb*Çİ§¨¤«­¸ò)rŸÈ¥.^Cxé •­¬ş;…H î}ÚnEbÅ£\"’D ìÙFbØl”¤¨8ã®À\n+Wr”µ«á@ÜãîÕeYé©oµK:©Ä]ò“çö—{‹«mA·ÉQİ¶’²	Ya=;üJ·#¦ÛRøÓ!7ÌÏ|oêœxœ³‡1•oFÇbF;•y™%BZFÀ3Ê}6µMö\r‚cJeÇ´ê+“>7r»™µ/\n>xÛ×ît#AäÜvÇ­›vêµ/±•,•¼ãm2›ÚæÎÀòòR	êæÃ}uS¬£Ó\"u¡ªÈ¹“ÌqLÚµøEM’V¬\'i”Ö§´…+}›{åÜZ£º\nH(XJÁqÓ]Uµl¦­4^grjÀh€h€h€h€h€ğ§È¤ùŒıXúÁ¹bnI”âu¼õÊ‘•ÆÖeÍŞËÙÍã±QR¹ßb}0İlˆAgÉk`ÁNŞQñ¯FïfÔ½ÌnÚü	é„\\á™7Û Å²¨Öcæ å™u%¹,GË³-”L8Êú8Û¬—›ZJU±ÜU\\µ.µÕp/¸¶-’Ãxv=	rÜˆØÍ4÷VØ	)|<ø°˜ÓkîğX\nuÃiñD¤]1¯®Ú?+õ5@íÿ\0Ùb®-8Øu%.µ[8?²£¬ç”–I¸¶—3š\0dö²UÚP‹©3¿µ%¶¥)°FŞtÔú‘¤•uq¡öº–ÆïËun§õJœY>;¬)J\'ÛïÓ~…6Ü¢£uô	0bÄşR¤)\rü;Ü		[‡Ø„’}ÿ\0…“³×‘cÄ«ŠÈÈìFáC!çXpKq”ªz<…(ö4ÿ\0vÌ0ñWê Ü\0ûô•\ZmiÄˆ¦APYm@äK{ xøl€¡ÿ\0=F¼¤«šø”Ò,wÚYOòş‘Ü|©gqü\0ÑnàkÜÏ¤THïk^Sg¢oµå\'å(3¿øµe+Œ¢›W.Mxı#1”¨rlçO{5r`Ãƒ½ÿ\0\'jÑ2{Ï%ôOccÚIÔéÛu\"Rğ67;ö©}}¤©E$’z“£Ÿ`„}¢¡¿iP;¨BR<}„÷\rˆG/ÒšNáEåì•l:{Ò”÷†_`ã­²â\\i¿-ÆÎí¸Š}Çj¼S×şº„ì¸|QÉ5VSÒ†§K•1¦A·&K®´üKm©jmçGk5©0Š*ëˆå´;|RüÀMÅv¢œíyWNİ÷ëà7ÜoøûtÏT6´Sİ¦.oú`nzôß~›xx¦ÙßÀìR›üVÅ7µİ¾9lØ³èçI­” ŸÊÛ®F[~{c¯Àçz´j6Z®k£ğ#UÂQ/8ûÖ¯)âáˆYíenVÙJW=¿*ƒ&i‘ÚËÑ˜4ö+BA;.;Z¿3¾İm^«-4ºÜ¿SZä|ø‡}Pğï\"-ˆpòDc×o\0X„RMS›\'vâÊuÕÔÏWqØ$8¥{µÕ©Å“ƒ‡Üô6$  €A¸#¨ ø} ëpt@4@4@4©1áF‘2cíE‰‡dÊ’û‰iˆñØmN¾ûÎ¬¥\r´ËH*R‰\0¹Ğ¯®{Ú[ŸZ^¨o1ûŠÛªKşoÍ§Ò]TLkSmÖbçC›_>¯ÃŸcïmÆÖ¤-$luç¸µ›ñ3´¦P8K™ó\Z¶Œş8ğ°©³y´ßb)4×¡–	T¶HİU7!–Hn[)$’ê\\GÃ¬¯Gí%_k7Ã\\Ÿƒó<oqñ<–î±ùéCwøäÇÖ%¬@­ÖÒ–•y2[+ağ7BÎÅ)æ´ÕÃ:)enr‹	@u=zÀwğüÛê8ù–ƒ¼ºHrRQ*)m¨‚¤Éf3½ÄŠ=ÑÉßññ\Z«­¹A2ŠµDjKõŒ¦Á@¡ÈÊstõş^å¤íîP¯tU¥3Ì¸Ş~m‹Áû\'¼\0Ù9%i¦É.©a	öl6ÛZVÓ«rDwé®©Bì³ÀxõÖÓÜU ÁãıĞ<v=v$\rIV¤çf?vı7ã¿´Ÿ†’UÕ¾)€¿A¿ñÿ\0ĞôÕ¤Ïc˜>~œ‘âw#İâ?aÒHÚı§Uq‰q¶P\nyÄ´•<´4ÚKŠJœuÒ†Úl(õR”‘Ô4’ÎœÎ;8\rÕBŸ6m­8c¼ú¢Bœ,¦É,¤‘­JCÒ#µ\0©)$õPu/N-.ŞV;µ-¸ğg¸ÓÒ Îu°âk,ÙùIêNÉ$´·cÌJ{‡r˜uä$ô$š¢µ[ñºJ+‚/vå\'\rÀß¡÷ÃW)0q‡m½İA¹üH÷hL¨\n>aK©¨´SÁ³lÔÇS£¼«ÈLIĞÛ%Ò¯‹Ì„m·]Ê˜ãÀ¡ZÆ°³°fY1ªĞË\nÔ*ˆ1 CR”ê]Sï6”º&¾{{B+RQÑ;nu^š/!ÃS¨`ZGNò#üË{õZ\r¬ØŸŠ*”J·$“å­g§åÔ¦×FŒá@aâ¯-m½Ùğ¬uC¯¡(uµlã+^\0è²K< 4áîZIO€Øÿ\0‡N„o©n¯îàD‰tÌ:’Ù;ô  l¯À…î:Àë;â¥”–M¯#\"`üÇË¼^cË¬~˜Ç†=x¡wB”n£å5j‹°¢wÿ\0Hìr}úÎ¹sáû\\×¹êoX¿\r4p/]¸ôµ3“ñ‰xÄ•v%W˜÷ŸwH¥©A%ÉêBn`6I!fÛxë¯_ÚdPÿ\0BÎ–^$ÒÄs¼7=¯˜nKO‘Âéæ9W5™GR€Pjd`¡*à(nÛÈBÆıF»©z]M\Zh©vjÀh€h€¤ßDfÂŠê†ƒñæÔØÄ}‚¥ <Ì˜o2ëEhøĞBÈÜuôÔ>q?2aE“\"5{Ñ+#Z=ä¶ûõ•ìºûM´ì”3Ì2Øo¸6‚´üE î5æWO3)S§ú¬f€w$‚½RzuIö(×[§¹jCR´3eÙV•DË±L‚UôS5ò!šSM üåuœu‚Å¥uš€.Æy+lí¸ë±N1¨ğïµ§¹›©ôÑêŸçx1i¬Llo›\\]*Ÿÿ\0lÉÜ™8„§UæJi*iEØn*6ÇüÄ3\\–V«×Ö¯>˜í°„¨x„n®§Äøo¦äN¥]˜Í,$ĞÓb7ğÜt;CU|Ôïİ:#n¾$¤|•€{\nÛaîÇİ×of£t=‘åu‹51ÛåãÔIR„y²™5.8”%%Õ¦L÷JœWÄSÚ	Øté­–O>òY-c¹|]Ÿ_.ÎKò$H—Qzöiuçn5c±ZrV4¸í¨6Ú™Ç)H+aJø„Í›ApRİÆ±–º©1ß§¿e¥<õ\r¢PÔÕ°‚æÖ¸Ú×ê´şÄU¸„ø8WÃ©WOÌ‡X.FOÄ¥oïß´ï|:i¹!ê.+NoíÜûFûmÓÇEuí*é/S…T¬,%)ÄìëíNÿ\0õj7®ı	Ùİ¡D²ªlª\'¨gSÎ&d‡$DmªĞ†”¦d¡§¿^C®:ä”¸‚{·Øj7®ít|yaQo[ñUkoYnñnsiîÜ\nû’•ù¡#p–¥¥{ôz\0ÔïÛöê»Š:·÷q>oT¿™L×UQ&iSgC¸	®“$)ÙS{]W•\" ­O°·Y	ëİ¯—ÿ\0*ÿ\07ÿ\0\Zÿ\0è©ùò~©t}L«lñåºw³J´şÖ;Å­f•f77SÔüOà?)ùœÖéÿ\0‹ÕÍZË[©X\\ß×jğ\\bc™nµÈx4ºÁcõ©©v*dÃ€Ìy,[Ïóc‰P£Ã¯œÔ7L«&Ô‘.yix­;+c¾¿/ëÿ\0ı#ÿ\0[t]}¿ÓÛò}oQL›-n›¢Ë—Z¶ÛZÙ4®Ú4÷µ¹Õ\'£ˆ>ÃıWşU›§]^JôØq:îK&jÖÚ©IUK—É8™>cåf|šÆ#aÓñ,$VdT1\\qÙ!8\rÅu”è¶.Äíxü$Kl)*İõoÇÿ\0Şø)ÔßCş;şE—º½:g‘eÆİ’¶Ou-i®¸²äÃ’\ZpÛÔ×Vèğ,Oäÿ\0\\M¯*«­´q}ÑeÇî­oYÑ÷—:±èmN•1–j\\¤4‰rqER‘)1ÒâÁİa”¨„ø„ï¯Û:.­õİ.>³<¸é–ŠÊ¹ily++í¾;%jYpu²”Ï‚Í‚˜2Û\r¯K:6›­•ªãl¥4ûÑÆåiD#:§’\"?š™\r‚ùr™	u oùwÛŞ»6ä|ja8“ÒÅ£}q“›W‚ ÉKÊ3ìU¡ˆW´ëY²¡@\ntl–Â›o¼ø¯®ªéŸÙãû–Of:È}CúsÆSÿ\0è¹³Œ*œÛuÆs6¡—5¥mİå»®mƒÍ¸7êµu…ÿ\0;\rÒâµ“äŸp_G[c”È\\APÄò‹¶Ô±â%Uqá¨ŸfÎYa¤Ä¶O÷_\n¤FüÛîÑé_sÈEW ÎRÈ•hÆ\'‰·%Jß³³ë™\"e6…ÑE‘ĞjË¥£zRÌNNvª\"~Q÷ááìÕ1zŠ|^â)\n‡s/™bÃ˜ÖÊJ›$âuIq=ÊI(Cªñ×F>šÔsJCñpfÓ|o>H¯qÿ\0îŞÎ)\'ÛRØãüEÉuuõ¥ª&ZFä,~t&ªØJÒôŒ´EjĞîZSå\Zÿ\0˜”µ¡à|z«;ÿ\0ly’­J¯©¶NGßºo\rÏ9Ä½[q×\Zñ¾ÉY&?MŠò—çìHªÄQ’½r³¬W(¶—oa& ÈŸY!N0ÓòK)*Õ•2Dµ¡iSÏ^z‚F€há@¤€Az‚Bö‚4Œ?ºÚïÒ•½÷¨N¯³È}6İÙI³ËhçØİpm¤Õ<çÌ¹ú²ì¸¾T§È=Îçj Äµ)¢ÜƒÇ›¯Õ^mmr¸|\r5@˜Ÿ„…l<G]÷ğÛøk:ÛB42%Dä¥-ş»¤‚<FÇq±=zmìë«×TüLíJ\'Nß¡z×Zª¦}cOG—N\\°¢z¹÷ Î`ÓŠ˜É‡29KñdªBŠ’êH!jÜìzë˜£ÈÕ]8²æµúnõİ§Óp^j±ÙÂÜ\Z.G|7ºé{†Ù‡–ö„Ç¬¶Y!\"h	ò¿Í\r(÷¯†ÕÚü¼w¯ñ6fÂV‚\\JÏjvØ¥)))\nB’´‚Oa\Z…cWDøhT˜Cïü(h¯´u-¡Ç\0ìJˆH\0$Ÿ]+[íM²›ç¡YƒÛOZ‘\n©+HJ––ÑùIØ¯r”’ÇGÒõWke,ı„»TäL	\r-m¼Z¤«ËZS…}Ûn[)e·Owà@Ö”é:¾¬y´QíåÄå‹×K)‹7Rå3¢É˜l^O¨nCˆKŒ­‡ĞËğâÙ!¤9º¢KïeD¥A%Inú<Û[šÊå?¾…e/#¯:‚³\"ŠˆÖŒüÚt=À·#Í¯˜ŸÉ.¾tw\Z›[5ÊóBı›í®X’‚Ì.Ï›\n%Í©Ê*ì>cäK±F{¸Œ©ù%Êá•fpáÇI[W2™í n¨ÏõsQ^(&™¢6ÔØ¬XDY‘[\rJ-´¨²ôwiŞå l•¡@ìBHö€wÕ•,Æä¸–ÍæS†c—ï³F\0]·É(êöØnw3\'GÛSé¾pB²z)0æCêÓÓ>&•&ë¸åN$lX«»FE íÓ·ÉÇ[´Yşj}5Í¢~·Â¬Â™Ü§Ò^=Ü†ókì	Ÿ á7ëe;{¤\\·LĞ÷5jã«ïa×\'$—™y[ïén$(ÑS‘Ë¯njŞÜq¾4‰ş(0ã®ÖÆúÊ)‘ŞæDK2‡@•lsâ¶¸ğİC­ªZîiè×™¶J]^¹+[­SM¦¼tÔ7?~\Z’dŠlKŒñÉ*Šò EU†qa”ßÈy¸üIõØn\'^¶T‡[I¸§\n+%D¨×¤üIÑ%^‡¤éğÿ\0ñ¦:Gÿ\0T‰ÏÖfê}OQ—\"ñµ­ñl‰¼¯ûˆr|Tü¦J™u˜j¤À./e%ä61Ùş Ên:”Îã	o~›ï¯Yô}d}n©{YÅ[tµMÖkÉ×ïÏê«9~CXÇşVù7ã>¸WGâ¬)Érö,(ÿ\0 ÌÙUìJ$îùyE 7)>^‹3Ósö#G|RÒö¶Zu¾³>â¼à‰LÈªÉ+¡ºãsŸQîªª(*Iï—]L2å:–·WbOqÛ§§ş=¹çÎ	Yqµ4T÷\Zéä/VşªØäÙüu7²“Iš5Šd5U¶W’·%Îr®~]}0Èí©ŒúÛ|@\0–ÎÀ«`u]“jg¾_Ì,Ö¶«‡°¢c¡y?\'äNGã×l~ª–«rz…A„¼zşsÔ3c3KQa2\n£H~qI%i\\;|c¢FÔÁeØê¶ÏÏÄÂùrú;Ó{Òùxq,~[É²œ‰ÌyMöK¶Ö÷eÚätqbÄï-ÈíÉ]›K–¶´¤©J@^ÛüGS¨éz|xëéÄÆºşÇ™Ñõ=Vk]åOŠ8¨ñî/N2àœbİèÖV\\fşQZòÙ•*ÂÎIo	ÒÓe¶Ş—*Dç\",6Ê”¥•\0’G‡Msú8vıQ>gcÍšf¼<‰ö”zXã2ÕQè8‡	È^§’„½ÇÕr„–TZ&5Y)ööµN)I=z¨kš•¢”£õ5½ò:é2×q¤ZÉwõ}\r{5*Ÿ\'rD3l«e1g)’üÙ1’ùä¥?&Ê+· —\Z„¡—–VÓúÛk£{¹>ÜIIíáÁ¯‘¾A¼…dß\0ú{ôí‚}¨x#?ænA—‹êw–.xòŸ!Ÿ7”ò7iñ¬®ç±”ä6¸óy\' L™>JXv(_–®Ôù›¬ßªô§]<;ZÖÙŠµ¶%Ue¥´—â¹ûÏÕ„n\0ÜîvŸy÷êç)ıĞ\r\0ĞK\nøĞ&ÕÚB‰eYeM}u„ff@°1•Ç™\nl9r<¨’£¸¦ÜmÄ©BŠT$hu?´åŸ¦Y¨ÏMÔó-½:Ì’í–o€AiùÖœô—ÚT¶ì«%[êÜşgè;‚WİÆåË‡ùPÂÉâr¿õü?§ÃÈÒU¦áµ!Ä©*JJV•$…à¤{HVş>Ñ¬+nòÜ¼á¹¢TYüÕ´%Ä~*İejK¬—šq¯5¢íu²²RFÛ­S”fê«¥Éuå®±ˆQf‚†¤Õ>ğ}´¼·˜ˆë&Â0¦Ö1Ò´:¡JîO‡iæËZ­£Z^4Iùé¿Ö…ŸµWˆçòŞÈ8ÕJj,ï>—¯ğÖÖ²ˆFCˆU½}İa©^c ~y*òëÖ³¯v«íSüMˆÉû‚zQ~¾Ä^oÆ2¸2\"G³Ã«/²v¦2Ê–Ï”üºŠ—£:ôwšZ°´-%+ø“ÓÓ]GQE²ÖZiÃ_ihmMS0ı×İÓFóïRËä)Î¡’ÓÒ`ÔÕãÑÒÊz„)Ü–ö½æÙ\'®å¤øjVn¢~›[ØUÕÇÕ·ÚFCûğğn6·¥¹S\r€¤n™™§+cUNÉNÛ$ù ÚÈqÍµê}.¦úµ‘ù·ó)8Ö›—ÄˆgîFã:şï¢«‡›S«SL%ó¼ÆBT;€[¢u1=*)IöjWI›šªóhØ»ìü‘ƒ­?p×\'_S\\ßaóôºÎÿ\0šrƒˆ¯b3;8¯˜E¦`©Í»£`êÕÙÜ¤÷PÖ«£ÊÚR¥÷&Ê»âãN÷B_îõÉ—ëÆ¨.9y—œ®¸±qnNÇ1ò\Z©„ìçØ‰\n¤™aŞâÚZ7İD\0N¶¯ãÛqk¹ğFvÏJ©TQæ`¸ÿ\0sPÜ©†r/ =ywı=ƒL¤w-Ïól¦æÂvF––ÛuŒªÒ\rbàC¯4²{AíOP¸úº£v÷Â+~¦ôZ*Ï‘ùî\rÏ²ç21»º\Zæ]ˆ§Öâq:Ùû×)Øé9i&É¥ad+[äüwO‰ªÕ&ãœ¿™–>«5çsyGìtñÎ]çşIi“kÎùåJ\'µäHMt5¥¤—K‹\\uÂ…²úÆÈî@ møëJt´NIy\"/Ô7Æ^½ì›|é§ÊŸvÏ¹\'œù™L$Õ¿ÈnÓÃ’€¥8Q/é0`¶İPJCè^ÃªA©¶5K*7+Üg\\ÎËt$Í?r+Œsæ[PúlâE§åëhTI¦vËìVAÈg³[ÎEœ¶gIËìÆC‹RÁZ<Å(w„…-T¬¸qFµmÓØK¯NXí¸ç¼³$!?ÒğÉx­åcĞŞ—Òé¥¤3ÛVÕç*IIq8”îõ$îïÖË+‚³îıÙŠËZá‹NçUÉùw#óG\"=N\nºîåÅmŒÈÓ¡°Ól°ôY1ù!ù2Q$\"+‘Rú\nR”½ÒÛk³¨ºu®œûû|Î^’®­©åÆ<LÆø®[Y\ZU¿$ÖÄ¼…DßéÊÈ5’¥–! )ÕÇ9éIdÿ\0šY†ç•âw‘«UiğíÜlñÚÉöøI&0¯TªÆàÎ‰ÓÔ\\C‚Éù‰y-êk¤¸ójm—\Z­€ +¿¹\\¥		Ø3É·%•š×Áÿ\0Bô­¨¶Î»Ù­ÎY»·Í¹bNQaNÄÛœÖ›ğÓó)«CÎÙÅm•½FÜ\r^XøÕ!âPw]7R„¸vãµ‰†ôíÊIOÇ|Æ;ò&VÕ?’ËÍsK±qwCieeKO¶¶E“M|ƒ#I¯yØjS1Äv‹ªJJ›Ø%ÅÄ¿UÖùíÈ¤i6ŞÄ½¼|ÌAÌügHÅ,¬ìg_Ù=*ÙÓgbìSÁ¯\nj¿5	,e–NÉw±‚ë@%#â>\ZÚûìë[4±ËªVµjâËâ\\X—$æ2*_­9ÆL#Ob-7\Z\Z\ZŠ#)JPiÙrdÌ,ÍR¿Uâ…;BO]c~™§ºÍ;yKıYºÏü+Wp¿B@cü|Öc‹1+µÊg6»[GI5ä¶ß]ÄXnd£Á&NÀõ	ß®¥cÚæ{ydmD/‰øñ˜ù†Ÿur[N]ˆeqÃ(RaYÂl&ñ¼Ş½èêL]S¥èj\nIl¨w©kêåÛÚş®qÏ8ö|L¿n<šÉw~ƒ\\°šöùÓIùvQE¬ûíó^i²û»(oºœ^ÿ\0Ú:ãµí;gé:š«¼ÿ\0->é§ª’4@4@pI\ZliæGb\\Il;\ZTY-6üi1ŸmM?C¥m<ÃÍ,¥hP)RIm <‡ıÕşÑóx\"FCêcÒ¶7*Ç…^v]ß(q%$w¥Ïâ7qrgå¸=lvÖì®0Nêrm{aNP€]`*\0Z!óeÃü©ÄÁ×ÓÕëøO¢vFDeü´„?jŒú—ëD²ú’RâPPÛpF¹¥Äîî/\n‹7‘\Z*$¼—$Ylv¶ä-)yh}’·)ÁÓWVÓÄª¢ââY¬ÎcäÌ†›š¹6…Â•-ÔK\ZåY¶_vµvuˆª[§GzS·Â¢­¼uÕ‹<‹u¦N‡’ÕªÛÈuÌ·y‰Éäìr;˜Å¥HÔD\\Õ*\\J¹R.e4rpŠ„ÌU0™`‡tÆİJ@÷îuÓéÓtKNı~&NÖ»–şE©êb\'-Sò´Ü_6ñi8æ?iVµpóì -É‹Ÿi\",‰)œ·TãEİÛ\0¾ÚíÆ“£z%ó9¬â®Ä*Ãés¬·\"j–VW]îå|Z»+‹¯¯Gã±\Z\\”\'¯^ğ6ñ×>8áo»ÄÚü>éÄğÛ¼ ÆN[\\ö$¨í…Krù¶+$2 7Qq‡ü©;ôè;}šè[s7×3«$Ï$z‡âvı>gÜjs>Ó(ÆäVV|¤%·^©kr+£æ$NìŠ\Zh¶;””8:î‘¹ßXÙM÷& ¾=Ñ\r3WœG`™9ë4¤ãB¾m\rô4¿)œ@\'Ëf”Ö]­®=)ˆî8ó¤)+[ˆ	ke\0„jo‡<?cK§²uãâH<Ê*ğî>Êñ\nê«<™¾K®Å,rÊ†ñ{›bÊªëb½À°aP`ÃyÓºÜóÜÜ$l 	øÕ*ÕÔî‚—İy\\0öTº9—®ÍÇN:ÔX®FMClB«]ƒH˜]ÿ\0RaI³œ¦R·;\nœr1êPì6µİ›MÏ|ÊãªIÃOY%NÈ8Ô*zè4|9„Ãq¸N(ZÂ²fE‹ö\0,Gaöfßåï¸=¾cÉÊNß\nºë=Ù\'ƒKÁ~ò_m]a´ûxA{âÔ|ÿ\0ÊnJ¯‹[q‹Õ¼$·CÏjq:ä•$˜½,º©NÆeï‰ÆÑ)M±ê&íÎ»“ñìˆ¥q¯öG»÷5õÊØ.WMÉùF™NİÜâ9DJ‹âä_WlÚ<¶ŞeØvr »hwu%á#Ì¶¶îMöö—¬%.íàIş-‡/õo]K|~õ¬®ê\\K‹dI´º®ÅqÁZõ­‚¦	²‡p%	ZBPDÚk“bàŞ¾äfµÅêsKO{1-bYv%S\n‘§™jçÌË®Æ«ëCMüªÖ#Ë”ÃqæÔĞWr]Z‘ïí®ÜÕÅHI$¦4rrt×Í{;]·ô÷G30qæ3}ò•óY¡°böXÙù5áD~.Å@¥ÇØm…2­ÉØ(¤ïÓXìÄ­õ%û²¾sğ\'oòGqôGŸ\' ˜´%/¿©N¨€—?QU•2¯6#µDïÓÃYf¥[^”G8­‘/îLøš¼ç|ê%×8dÙõãÚÒ_gnYŸuH‰]òšj\n¥­‹œdËˆÚ’ë‹(ùÊĞÛ‡âB\neëU<kÛ¸Ò’´ïo·@qÅèåÎJË²ûkcRdùEmU%#1dA·¦½Ç L‘d¸Ñ1ê×ä¼Ğ²Z€e˜­µ¶î)Áñª¿vWzwüH¸-İ¯!–W¨Zş¶¬Á#ñ¶9‘üímí›ö2ãÙA´±³†+Xi¨~{×Öhª‘Ú£æGAìÜ©;ëe“î³sËÀÏª³I%Vµùü2NDí,Ø4¼3\n¦šŒİ%œÌ‰¬Ş_ÅóHœ‹Òa2–ÎÅµ¶Ïµ)Ömåµæèn•cOÔÍÕ¼)ê<¤im©pñÚŸ˜•+#Lâ”†‡a\n+ÎF\r¨„:¥°îé¨y-™î!Õ:¶¢+ÇÌ_³È.[®Ğ¼1úl¾{rş£D™Qì˜mU\ZGÈ}Q¤J‰mÂØ(Ùk!(èçvİb\nè±»¤”¯‰ë_öûàø‡/z€ô?ŠrESY69¾Ì!ÕÌ—2*Á0éù†%d_&ï®Ÿ!¥jb*S.-BÛ:åÁ‡u6¯QcN|wÒ³î³^Ó³ªêº‹[§Á{7Š­¤¹$ég§›IûÑoX’4@4@4ÊĞ‡¶ÜB\\mÄ©BÒ…¡@¥HZT\nT•$ìAèF€òu÷\\ûBÊã5e>©}!âÏKÀÏd<½ÁXì\"ìœ\'á\\«LûŠê¢#Ì{*\n~Ú…”)uû®T˜şdvyòáİõWî1uôÜ×ìæ»¼WìyÙ¦¼bÂ)ÑœŞ<ØÑ¥°¢z–¤´—Pz·_ó×\'‰dÓÔÕ×¨Æ˜™Ê|Úã‰JŞE}CÑÖR•)µi½²RJTAkÓé£ÒíâM´…ÊMše¸ŞÅ8½“øËwWu²ÚnÊMÄ•ÙWÛ&İø\"¼\"±ÎÖà½X¾.(í¿°v+ÒªZ–c¶ÖN\"ÛäÎUä0ÈN#KÃ\"Ù.®%„†1ê)—¬µ\ZÊ2İÓó¬íà§æ„«f”ÚIèU±Öô«Ë]Üoô4¸öò\"Î/ê-ŠÙ‹\\êjÁ 8ºÌauÕqd÷¬‘X!*ªKoñ÷FRÊIø‰;ë™NJÊĞİÕVÒõpfzX“s–c42»Ê(“e*c†4J´ÎP”è!ƒòmÖÅLf¶m²Ú‚Gç[S«ôÏ/‰òÕë¶\\’~Ãƒñ,k„9?#‹}˜ÙåTøe†AVä›\nH4È³¯eÅFz\\VjİzC-|Ã›´d6Ú’zø\'jd««Öw™8²ËˆóYœNª¦2¿5©V\\^;2äA\nÕ*yØ3Ú–\rõCŒCj´|Å:É+%á#¹YãS_º=Ÿ\\“ééÂWÇÈ•<	G\"6ÏQ2Ÿ¨¯å8Âªq‰Œ¤ÉE„˜•Î:à}Œ‡aÁeO!N{Óù;µ|8­½]¯£C,¹*–Ôş¤Ù‡r¾61¤R³AyM1á\ZDIHr3°ü§Ó&;‰•çJ	lÅ	|¥E[8Û![ôèËik¸ËmYİó36+áô0š/ò~>ø!éréªüÇ¬#³<âÛa¬¡¢Ù¶…­DnFÚÉå®í¯‚í&w.fvÀ=I³Om~9KO|ähy/[dÖ3-Èí)ÔÇi´ÑªQ‘\'´¥¦PTë®¡ ’4ÉjYi1<ŠWN6pß¯N}¿³Ìù3%¾·ªf¦òâõ/Ë‚Ä×—[\nJÄvC¹:¶ä¶€„…VVûedÕRŞã£.=¿RUáê—ÉÙWKo¸½Èœœs±œs¦•hâd·;ò\"KC)„RéRIWÅŞuõ6äj©no»ÁyzIãNÍíK¿ÇÚ[·9ôÚiŸ9u>›™Kˆ44PÊ£E¹æ£Ó4¸°M*‘°;ƒ¸Öö¥T´ÿ\0CW«´*ºéÍËãÛ‘ÃÇ9fErÿ\0aÈòjF¤0¨R\"VO®¬*<¡Õ•]-ŒÄ3ÚIzS…µ$)\'~ºÅàÕ[w¡ä­jÖßÔœ¼GÁ˜Åš§Ê¹ÉsËÈsØy«X¿f\'Ì|Ë :•¼¸Råº‡ÚA	q$íãªeW¢Rç^hcº´­±äÈ	Ï4˜æ7Ì%<(S\"Ö@É*Ä&S*$ÙŒ´¸Ñ$ÌnL»\ZõË›1S‹S‰ñP#òímvñ]»‹QÕë¯FWâ¬aOòï%æ¹O]6q„YHŒó\nq«£ ò¤0Ôe*±™KŠSª2$TIÕkVòn\\ŸìÈm,.u_7äa)¼[Çk0¼¯ÿ\0#Ü½m,L® ®TÕ6ÒâGe%¨”ßÚ?!k%´´Oºou²\Zúc¼Î•µ²;)Sá¡±~ô©ë[“«X™ÃŞ‰½XrT¥Õqøƒ\"ÆèŞİ[w{(RTcµ²ÒvêHØë•çÛ¤~§O¢¸É´>0ûLıöù\nµ0èıáW¤+ê|»ËMtÆĞËŞ±Yœ§ÒŸˆ„ã¶²µ±ZÛœÉ+â\'O#%ğÏí\nû†XåñóPçLœmóqî:š¶÷’ó»h†ò$¸²mèØı}R%ÆT²´%—Ëhq;÷¾§]¼Ğôİ³ÈôéöÜâşk¸5Y_ªü«+È8:ëÈ©­0n:‰€½ak„Z±k½:Ã1ËæÆ‰`Z,<Û>^ì-I%]Ç~Jz”µì¯m·PÖ‘\Z8áŞ“;räÇ—)éãW£Êw7\rk-®4Kã>œµ&#@4@4@4@y[ûµıŸæT½“ú¬ôw‰®D\'^’s?cpŠm×Te[r/ÔÆ\0w“æH·ÇØGë¹0Qæy‘ÜÃ.©}Æ«£İO·šù¯Øğ×Ë’¡Ùó%!jSñ.ªéYCÑœHØ.ÈQBÒ£Úá¤t>:Û¦Qê]´ëV£‹yŠ5Ç	×b_OnÊ®ö4«,ÇÎ‘.]­k’k­§~³c†«^·ëcÌZ í°×gÑjlz9ãò3ú“İÅG™‡yc“éù\'+şªˆÇvÄÕÕÒºªê¼ŒÀ5Õ»¥½)Ø(yõ!Å)N(ÇA°×F*Sj˜×·ŠÎ%ü¾f\nã÷x²®ñÙR°ÙY‹k¥®d’+êâGqE-.KĞäÁ~R¢ƒ¸&Éò6(ök–i=º¾İ¸Ü³×EÛ· ^r“PÌ„bxòéí’ô¤A«¤ˆ‰èC\r<Ğ‰!ÅÌ“•Éoæ™YPAq\nl…mºIÖ¹>Úîò×ç¡›Ä“‡	~Ÿ&V&Yóva‰ä’ä;ÊSñ*Z—¬îD)5ØåKTĞ·í_³…qµJq1ğ¶Ú’ Gb†ÃKİÆå)\"+J\'\r-ÏÇıHëÆ±²²ÊÜ¦l›„˜R›™˜À‰.·ıCHbHSnÏu)!¢²¥€ÜAÖ4İk«9M²*ìuç=ä™àÈÓ9æxöQdÚIã¬™ŠW5.Ñ¸¿5™	•.ulyIM“Åˆ‰Kl–ÂÔwA:Û§·©‘Rî)§›©şÕJ}îCò<’¢V<İŒ)5Ur\"=Ö%\'Òâ‰(”„8K2™©±ú¥D{<u¶zâ­Ò¢úz[åËÚó¹2Aa¸æY«;lzîWÔî(eK¨R¦ÏÎÍ\Z­Æê;~³xñ&¡(æo¿#IqÅÜÑÃ|tÊe9šaHmN…Ó}:lö^\0ğİ$Io¦@öÌ7m¨µq¸Û·ØSvOüuòÎO/7æÌß(ãØïf¨Ë³W2GĞ•ÙX¬º®É*ƒ.‚U‹ï1Emw•,ö÷¤²¿Ó«íúæËšqÛ™´/JoRù¯$ä›AéCÕ¾]eQ“<¼xofSq{êûüJK;)6ÉÃ*èíujëó,¶‡A-å¨Ëôİİ›JÅ¿UcT­fV¾\Zø›\n·û\0}Èù÷««Ã}\ndx\"ãİ¦ÉÛ®jå\'ÀòËfK.Â9S‘Åqj	BkT°€@	VÄZİDèí>È#Oeii%»I,ø‹ö”ıÁ¬ëbã™ığK*B~+S›r†TÂ7Qó^·º¢%Ëh‚š“$ø¬k7¤ÁÑé.|M€q—í\0ªˆ¶æ¹\'=eÉ7ñ¶SÀ-ø©˜öVYNL@ë°&vÍOü‹ÿ\0¨ô1÷jNœö }§qµF6©õÌ²Û[nÉ<…Îù$HsB‚Ér\n	mjGy?ªód|Ë,t¬G#bX_ÙGíYƒZ.ú¿Ñd7î¸Ûòoy\"®Ó•­eÈj<hÈ•/’í²µ¼ú\"Be £ù[i)$ª;Ùñl²­WOL‚øK‹#µŒxw‹xê+˜ø/âx“-y`v7AS	)\0l@ßUzñ,eM\0Ğ\r\0Ğ\r\0Ğ\r\0Ğ\r\0Ğ\r\0Ğ\rùYıìšÂ¸Ãï±êÛ¨pÈ4Øæ[EƒXcõğQRe–ü\'æFHí#,ü³³¯.U%÷Ka½äÈ.lI:ïüU+“ªµn“¯£™Ã×U†í{«¹êp~EÛ\nÛîâáÜòQ?zn{ø\ZŸåø’gqåšc\\Dº‡b¼8·ä7õh‰SHZeĞáßb…G@uÏÅs:§ŞaŸU8„üÓ•,òlbÚ¢N*ºL~33LÑr\"×¢<¸Ñ¢„©F<g“Ú”Õü¤ê×Ç{¹P‘ZŞ•ªã$\\‡Æ—r%.<«<b	RÜ›Œ‚6¥”ÈGÛéVÿ\0	e……{7Ö.–\\t5ŞŸúÃñõc—‘á|ŞO=O$†1lzâÁo,”6ÃR¢@–ñ*ü¥-õÓ‹-)HÖLrÒ÷zDgâïOµùk\0Êğ®û~z½äQšb–Ô¸…Ä9…mT5^B\\4ÙH™+0<†éî„’7ßmZ÷ÆëDcJdY7>FMàÛ#÷›Ínk²	^“™Ài;$6ò¹G•8«˜›\rø¡nĞKÈmmÖ¨åàµ6íz’Jv):åMVÓ:V›VÔÛ¯\nşÒ¸Ëxåen]Íş’x}×«káßO¨°åBÈ$¦#]¨nL\n\\_¤±T>âó,„õ)ñÖµÎ«XKS7…ÚÒŞ“$òã¿Ù»…=ò3y××Ï$_Mêİvq\'´ßåùé‰w—_r\r‹y-öù²Ó x+U¶wnE«†µn83a\\ûQşÔx«qqQÏœÙ1€…J“9ŠÁÆ¦ºŸÌµ·ŠUcšB¿²—ú{²wmÉ¢ª¯\r	ûÄÿ\0cŸ´§\nºÄŒĞoqÊ™™;3“%)ÄlC®9ÉY_zÉ›~\Zo·	Ğ«3a87p¿4–8×ˆx¿@	C86ŠbM!#À%º\nšô$Àj¼xˆFPĞ‘ \Z \Z \Z \Z \Z \Z \Z \Z \Z <ÍıÅ?mÇúîõ§më2›Ô–AÃ¹&iAS_ãò¸Î¯‘ÑõÊvŸ®¼Â-ŸË18ÓñZ„GôÏBŞîy¥´SzÓ\\˜-¿‹4×²ÉÖËÚ›OÌ¦LtÍ]™ÖSö¦š~Æ“0ªÿ\0i7¥ûet÷­}F?q\"D.Şš‹‹ª`*iŒÉ—\Z-4ìfõØïJC=ˆusòIîí^Ûiê[”\r‹‰™8ûöšı¨±§ãKä8~¢9Ñøûo‘¹®Ö¶‘İãºŸ«ptv‚OÃæş5%ß7ªUrÔØ_ıŒ¾Ò<,[wôÀÿ\0:Ú’¿©åô¹\ZÑÅ¥]à® ÙäÒ*êRA÷j’É\\„àœÁüZ\ZeÃ|WÇ~BÛ?ĞÜ{‰be¤\'nÔ¶hj ·³Ba[@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4@4ÿÙ','29999.00','ff8081813f91b1cc013f91b68f0b0001','ff8081813f91b1cc013f91bcbfae0005'),('ff8081813f91c906013f91d20d540003','Z220SFF','æƒ æ™®(HP) Z220SFF C7T03PAå·¥ä½œç«™(i7-3770 2G 500G DVD é”®é¼  Linux ä¸‰å¹´ä¸Šé—¨æœåŠ¡)','','æƒ æ™®(HP) Z220SFF C7T03PAå·¥ä½œç«™','2013-06-30 05:23:49','ÿØÿà\0JFIF\0\0`\0`\0\0ÿÛ\0C\0		\n\n\r\n\n	\rÿÛ\0CÿÀ\0\0 \0 \0ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\n	ÿÄ\0I\0\n\n\0\0\0\0!1	A\n\"2Qq‘¡Á#BRa±$£Â3Cbdr‚’¤²Ñ%&EV„“¢³ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0!1ÿÚ\0\0\0?\0ûù\0€@ \0\'@\0€@ \0€@	À€ğoIokD:7j­Ïe?L¾«·\r£Qv•PD•1—Kí`-)qÇÄ8Î1¶Ûo£^~eRmİ®L¨y«¨ÖY`ZP…Œ4b×Â•ÕŠ²šX”¿\n¦¦&f×ğ(Ñ%fuİëî­é½Z½^ÔÛBÁi©æä$¥©´¹xÄóëLÃ¥Ä´Òv •­IJAòŠeMéÃÖí®­]—-N·İNZCÅæ×1,û­¦m©\\!,!)[c…BÁ\n\nEàû“ÔåÖ¹oõœô~niõÉÒõ.Ùi¦.Š+jÀ#	œ`Ì»¤s(PR UG°à\0€@ \0v€Ô_­ŸWjt.²İj’¦¢Kö‹Âà›[!K#²˜tà\0FÄ#zÙ]Íw[òõ×‘&™”ñvròÊG­DÀD·ìú…~·Pbvâ¯:Ôª!(u,+9áÑ¦ô–AÉ÷ÀL¥ÍÌºê–¢®YÆ0¡ßl_’,R(——a©t!ü)-Œ%JZJ½¼\" ô7é}yô!×Ú¢Ø³âN·D^Ë„™j”º±ÚÊ¾‘ç4âFæJ“…%$×]_}<ì¾±N4A³_-ÿ\0f«Rp*j‡:”‚ì³¸æFAJñ…¡IPØì¾\0€@ \0€@p£í€Ó­FdÔ:Ëu}ş}¥bëxü©Áò€ë¦U\'O)¨ÁòÇÆr×§ø½É] y©•ü8~påÑ‰Š‰#Î™Çî›€Åz°Én¥wµWÚ:`(’ıĞ¦êªë/ºú²ºGË]tıVÖ«pIİ\0ç\n*ò€ì¤gdÌµ’¦—é*AòV`6©Ğ=wµºMhıû²ªÒõ»få”LäŒÛGe¤ìR¡ÍJJwJ’ w„\0€@ Â¹{D›İd>õ=ÓÄ{_¬¯ìœ“Å9;ï€½ôö„²dR\06¢6Á>Z ![Ô‚ºÕÄ®ì²}Ìçç Å-OLT22<lİ·‹uªLKÚè^<£SR=™tÀZm&j\\oô7¨¯­ş{«·V>«]³“3:;wM…U\ZİÏ«ÓJÂ~‘e<ø0\0y	ó’À*F.Përw-\ZR£Nš–Ÿe2Ó2îY˜ii\nBĞ¡²’¤ADÔ\0€@ …î=£õ€Ó“§³.=ÓoQ]GœUµ00œœ®vgwùÑ4g[ÎQ±)ëRYm@íı5Ekz‚Q¹r9N2Ÿò­Ÿœ>—@ãEAÎsÎYä”@a^Ò^/h0{UCÿ\0ÖÁKx€.PzJ¸sÀ}ğxºàÕ£µÚN‚êUIFĞ«Ì&ZĞªÌ,‘DšqXLƒŠ<¥İYû3É·Ãæ,pİr \0€@ 3§?L×Dÿ\0KÍFt¨§6¬Ò¸‡0\\œÏñÆG®­Z|»:mMûß`H=şz£BÊ·hÉrjæq8uD zÄœ¾ß©m—è³ŠRêS\'»”œºTSŒ™\'¶«+½€Å¥½ #K·Ëh\n8aÁù@e®‰³†K¤¶œ:•”]tƒ¯1¸~o´ş°À \0€@BÅ¥s8ìĞUîÀiÉÒ^]uŞ“Ú‚[rÕ“l)jáH.ÌÊÉåçÄàöKÚq1J¥·(šåTK°8ÚRÂJS’qÏ\'\"‰[NÙn‰N¬šyJª-KraÀµ¬ªQ„v;`\r &l\Zf¨sdªÍ¿œÄ•:jJ¥‹2˜SÉULíı—Lìòf\ZÂ`&¥€Éæ<S]¬WAÇgrRÕîdÀn$ß›í?¬0\0€@ »ÚlHÙÕgÉÀfMåçÑ†Ô`4íÕäı/ÒNş= rB—ÆqçÌSö€úqÉs‡`•ÔÀc;Ú¶ıßŸ2¦^¸Û–a)æòÔ‰$„cïd)^O~şˆ®Âe©nz]×RuyÔñ:à\'gÔ7VÛò€òN&ÂlJ!Iâ¨!@ç;œ?8%ÙáX€Ëy\0@E—OÎ‹Ìx®ªZ®îU¹û¦š0£aí?¬0\0€@ ¼Ïı¡÷”ÖqâÔ9çsı™wÊRÆ´ÒZûÖû¢mêÛ4óTªQ¨-4©u8|a©V\'Yºâ¥”Ù?w9€ô%¥Ò1İb®Ó©ÔE\"b~¹N–ªI0Ì”Ãî¾—\\q¥4”$-Şİ	Jw_À€®êƒ\Z…Ñ¦Ğ©UiÔé4Õ\'ao4ÌÛky¶Ğ‡Yã—™s±y	o%·8V<­ˆ„n‘Ò‰åÏ®•Ou3¤MºC%Å±RO\n*QûD(dñzL]_ZEC˜	\"¼üŠø[\r¥F\\8ÎR‘²RBF1Z7õÀMÒeüni¦ÿ\0\Z‚~0ôÿ\0nMÔç†e²¯NQùî€¸ôÙ~/{PÜîn¥*¿sè0”Ì\0€@ :é>)}5BdÅ¥Us>ŒI»ªM£4SªN:=H”?úh¯œATè_q.ÊÕÍ=¸S)\'>m[J“Y2³s+–bd7?0ò›[È!m\'‹$å…[ãï¥ÿ\0J«;\\´Nµ#gÓL¼ŒëÕiš•IÙgë+Zå”Ò%Ôãâe?h®ÑG+$a(\0å ñÿ\0hDƒi8òY²Z÷bu}ğ­¢ÅvUûç¿ŞÌwÍAü \'h.§[Wá%^àL4¾ëÍ2…)Ji¥á “±åvY\röuêbıŒ+>§¹;jã@>‘Ú\0€@ u‹Õ>†è¬“Ç›T\0úå\\8”j­lÕÓ%x<á?óİMĞU¹i~c;Eı>—»¨ÂBÄ²¦QeĞøYó0òİS$œá+\0öÉßlÀ]z—&äı—Q¬šLåÈÑ¦$œLÊ›/N­aN‚@B\0NI$­G\0srªâPû$‘5h\r›N3ØoİËòä;  j›İ¶†[Ã9ÍÉR<»»UÀZîîØÄ‚úR¢R’xIÀ;@OÍÒM5+AhE´Dğ¤çoh€âÕ¸¦™¼–Á}FZVzK¾ä…`«ŞH\rĞ©nvÔæøÛIøğ\0€@ xëd©\nOVŞ´¼íIÆÇ­I	ş(\rU¤¦VªƒÏ$”Ü×$Êˆ\0ŠxF}çî·=Á¦÷4ÍVŞqÙ:å2Ú¤µ(çfPéaYOäxVG÷¢QqêÕz­9£ÕÁQ¡»E*àl94—»SØ»8@m¾{á(Å“ıªªÊo‡Ôm–ˆÈæÜ¶HöEíş×Em¤`d×*+Îy‚áÿ\0x\n!NĞ™Ftó“‹™CHVá\nRÉïR”rOÊFò/*‘Ñ1 ¯Óı 7Kµ]í­ªzÿ\0³GŞ?\0€@ åÎº™ãNê¹Ö7Ç\ri„«óÿ\0²­Ïë×Kşùt¦µ§é–X¹2ÄÜËƒHemKlòÇ`œ„ş{çÙİÎ%=T™¦Éimyór+YipÕƒCìÀO\Z”{É<»¢Ac¯Üªcéª\n}ˆ–×#Áİ¶“ßôœòÏµÈ	r*»hß$öùG[”¼j¨[ˆK&IÖ¥\0¥€H8ø#ºtM>{Æ,J+ŸA…{ÚLb\0€@ Çı}_¢ú©uW|–éÒãûõ)QWäæÑõoJPQ•¸ÏJH°^še$ªfèbR¢í=öåiÁ%sÈe„çÊQÉòylyÄ¢áé)mUhú]V~¥=3:”RLªjS+X	œ+#9;€SÂH ˆF,mæ×vn¥ãë0y£îÊœwûıœhwšs¶ÒûxHñÉ“ƒÜx†`\'8_ª²[c”fÀ@TååÙ˜–*u¦ÜS)+B”JÜbq=%W—[‡ÓK•?¹DÃ\0€@ áßš dz«ïTƒ©ÑÚõÿ\0ÄX?(\r[¥Ş?RĞ¯M®¿ñ>‘à¿ô fí¹Rxx¹V”UÁ±“€N~Çnè¢æéd™‰[B³.õauR\\qÂ¥º{…)cÉƒÏ>n=R\\+¹ä]J>‹ŠKá-F—zyo¹í__½I€«–ÉtíİLyY€ˆ€¡´âVS&ñÇ&—ş“¸†Œ9Úé¬¡¸U\"Lşá4\0€@ Ïß	z§ôVEÇÜôv½x˜+ş\raå-ØlíÙ¥n3çNñ€®\"mm^+JO®†Ü	òxÒ™NGƒî€¡;RvjÔQukt¹@+E\\K3<<G\'™Ò *SOŸ­\n<(Úâg»Ñ/òî€˜¥/´±­ôà9ñR`.WY€)­¹ˆÈ`( #:ÉDŒÇ£²_úLá\Zçm¢6zÿ\0Dÿ\0—nì€@ \0€ù½áIÔV¥=9Şbö¦£œ34¿áˆ5©bI×,†B[q\\VòP0’r\\È²\"Š¹eÄ]œ}›…\"»2æxN0‰lŸFvÌr®\"×	-¸\nh2è ¤ç*šÎ=”Nq¢‹¡G\nÁ¸U¾;’Ç8	ªcJ’¶¨-:0´ “¾yğ\njåE:YjÈ$w€¶,İS¸+NÉ8Ô¨<D3€A^;¹óÄèÜäê°ºsùÀT¥S1;N›íÙJ\0e~Rr{tà:ÙgClÔ+I¡Èƒ…ø³}ãcw@ \0€@y¿­/¡\r·Óû£l[šv­L•n¯-S—œ¦­)˜–y¤¸œ RBâĞA•`@|Á®ø2¹h\"Ÿ¬—Ô³Ii(B£È¼vìÿ\0Z«àÃÍ—Ô©-v¨q©@ÌÚŒ(ñ…¥áÌs=ğåOÁŠ½PÙL¶Ûî£ 	›QhòRr‘äºy^ˆ~«àÏë.Jê®Í(8§kHšhñ¨yGn-Èço£Á¥×\'\'ZeëçML¢0ãjœÛ’;ö¾ë§x,—-qéİa¦Ë¤Ó!Byò=®<ğ€¢Ş\nev”êœ´õ’œã­«‰(¬P\\`…~KaÕãèÀY—GP·J{\\‰9÷m±ä»N®	WÜš&lí€½z.õôƒÕ\Z«º¥(šy#Úp¼ªŒÛso„ç…¦®,J’?8¿=z7‰½èV2®«óU\nI©Ö¦¯¹“\rû6Qæ¡°HB@0:\0€@ “zI‰Ú7Ã€ş°êjÔJÇš?“#—ç\rVˆ.ç€(Â³Çe€Ÿ¸GÆ‡-/´$$ùàü :S-·äˆM°?(\rÙa3*q@+\' c”ÇÕœıØ\n­¹o†HÀe1°€@ ÿÙ','5699.00','ff8081813f91b1cc013f91b68f0b0001','ff8081813f91b1cc013f91bdd8470006'),('ff8081813f91c906013f91d339c60004','846053','æˆ´å°”ï¼ˆDELLï¼‰ PowerEdge T310æœåŠ¡å™¨ (X3430 2G 500GB DVD ä¸‰å¹´ä¸Šé—¨æœåŠ¡ï¼‰','','æˆ´å°”ï¼ˆDELLï¼‰ PowerEdge T310æœåŠ¡å™¨','2013-06-30 05:25:06','ÿØÿà\0JFIF\0\0`\0`\0\0ÿÛ\0C\0		\n\n\r\n\n	\rÿÛ\0CÿÀ\0\0 \0 \0ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\n	ÿÄ\0Q\0			\0\0\0\0!1\"AQq	\n2a‘±Á#Rr¡´Ñ$3BS‚’¤á4Cbƒ¢²³Ò%&cfu“£ğ7DVÿÄ\0\Z\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0,\0\0\0\0\0\0\01!AQa2\"q3BR‘¡ÿÚ\0\0\0?\0ıü€€€€€&ĞáX÷Û·ª\0+lÔå•Y$ËŠÅy‡ƒèjkÎjfªÈxfV}Ô[À›F±æ÷Œ7ÉÃ,¹æéÕºî1˜ná)£ÓQúoÅ½\"ñ_\'äF®fŸBûóEœ•L4„ÜùEr¨VT\0\'òm% wŠŸ<“òx(ÜKã<gÌÅ9©ª}\'/dTìÚĞôƒŠoBP›—A½ÕÆı]ñ·Å¸œ¯Ì=ãQç”‚g°&WU\0â¦„Ó*>ÇÈú¢>ä­ä×xØØÊYCğ¾JĞf@óŒ•uæ¾¥6¸_òsDñµ°úÒ‘UÉE,¯ÎT­y§‡¨)„ûâ~ĞFHxØBz~uœÆËšİ)€²©IªÉ›*EöK­<[±ó’µz„ÒkjeÕBñ9/Öóxò•~&kÛşÓ‹‰ØÇhŸaŸ;’^#Jufš)ÊWTí Í»Ï2G×µø–ğ¾òfÌªÌ¥>X\rùÙ÷‘/.ËÓşL·\\Z‚RJw$€;á>2ÈÀ@@š‡|\0F±æsá®–[Ø“Ğ(-¶.LüûRæİÊP&	É,šíšş\ZîNÙR•coÃó\rŞìÑ¤Üš$ö”zÖCÔ‰¬y¹ã7ašRkeÍZ¦°lÜÅZyÈ>’†Â÷¡¨õ<\Zµœ1~}c’ò(naŒ.åÀğû©Mâ½ı ¥-ìÕ,âå÷œYàµŒQ™Â®Êïv©8ÛÁ´=Š¤M·Ü§*—fRÖµ-j;•Iõ˜bHù…Şğ² ÃÄ:{yµÿ\0€ÅG#h\r•%ú4ŠV¤ùcÆÄ\\~E¼pJ>¤Ê:zR²Çû$ıÑ[PXÓ=K—veÖÒØJ%ÀRBA\ZI;ğ1›‚²“m\"ä)äw8±bâ6ÑG¥ÇO	Ù”KŠ·ÖLì”Ğ:¦İ(\'ËV¡`w)WÔÅ•bF¢â/ÓúPƒğ4ÂÇ|W5ˆdËÅ\niO„©:m~‰=GÑ\n­ò\reê™ÊÌ6â‰Rœ¥J¨’nI, Ç*Á«$PÄByIföSr~Æ¸˜†¨áú$İBY/$©²ãM)iÔâğ0gà^xxO3Ã4ff¥˜Ø‰¹g‰¼¼“ŞFÍ»4µm£ZHåvÍxÄxâ£ˆfTüôôÔëëİN>êR»É&\Z|Dr~}kVê¸ì†¼”•\r³/8Ã%±¾eÂGh\0N›ŞÓq.@1¸\0E¿Ê«è/ü&*9s(—£I)Å!	òÇ¥(»#k˜Ş8BY)L³T˜Ğ\'©òãB×ÎL>ƒ¥%Zné*ÖH¶ê m{ÅğlªJ±\'ˆ*r«›”ši¢P–tóO«e¡{“Äè‡VÁ]\rk”a;¶û‡¹ûÄÒìU±\'ä®=nôŸ„KCB\n¤ù@¹}wÛ¥?t.C€j­$SBÉ|,£¬”Ÿ]í	Œ?/ŞVdUùŠ™á`-dIí„´ì{$×ÎdîPàª<™ıİ¸å4$ğWòÚG9È÷4Gn©}™p	àæ‡«ñÕ˜Ğæ¡‚ewE¡ï‹ÃãŒ%€Ì‹v 78˜Ezpm\0Á¦\r…½0\09:VlmĞWøLTry(şM³Î%\n@šY:€#òIí£õ3)$âB¹™E‹ƒ¡$×	Ğùš“—éeA\0 [ro\nrzÍ6Ui±–—7ÿ\0† ¥cäÇ(²GŒ«7íÓotX;Ôi6¼Ötı>1Š±):l’ê²©šre©E¼Ø}hueIl¬k#á7\"Y¾\\ªy>r#Á3=WÉ<ÙÄØƒJÔeM¤MÕ6Ìë+{CëR$ÉJ’É+¾±b-cÂ)F7Ã3N}ÑÑ–AYƒ‰âhr?fn8V‚[\nÓ–juòFÌñÿ\0*TşÊäx9™Äj+|ñÜFË\'(Äêv´Q`OÌÀ\na7ò3ÂĞ¨	î0	!€ IÇ®\0R¬¥Ô…{Œ5jÒêÁ¥„¬!K™Pğ$#d›€,ØëB[Ë›@nƒHfµ-¦]±6–Øa-<ËŠ¹Q%*R˜!)P\ZŠî6\"£~Šº±}ª•zrt”°™ù—fRÚ”€§»zµZA@ŒÕ˜)Ùùû©ûâS\Z5H°y¢}ŒAé¤u(\\0™ràØõC~„ƒ/ü¥‘¹;¼>¸ãìv+ÉÕŞ{ pBÏáúz½²ÍÇ\"5y&P®ù^ /’†fƒ×…jdvÁÌ¥u&İQÑG-“	±ôC+Ğé¼˜˜	`3#\\\0€]á\0& à´Ìğ·¦\0™Ù§Oü5{Œ5>ğægáYÆVäÓo50—T»eÇ´u$«kÜvGN”wEØIÓ#5šE^…,fR%êR#Ê¸\0ö*Û¤ú!KMå\rIa\râ¤§gXy°zÅ–#;¬•œ\n&½%2R*öIOHú­\nÕƒNì™–mĞ>è\\ûa³#RiĞzĞØW¸AV’’ĞY“i¤+p%\0El~¹tš„­¦Ûí6ÔÃÍ]m†‘d•\0mrFâül\"%Qä¬ğv!ÉÎw“†\0YßV¦Ÿİ\ZDË\'0À€r°oäµ™IùØZ¦?tv<È×İºDktrŒ³#q¹\Z˜€ ‘c,e7@]ĞÈƒÉÚ€æÀÒ`\0	Ş“.ú5{¡ A¹}$òşMÊ´ş¿$”šl¹ t·J’-ë\":´gµ[ãn¦3å;—uÇ—2W’¨‘c5(–Cz‚½.B‡qSêt˜–œˆ­f‘!]£ÒêÍ5Ì\Z“JqA¡¡\n±ÙA;é$À$^3›áIĞÇ?‡Xi•¸ÚŞC¤©*J¬¤›uÈ°)LUReÄ7=4çjçlUr=F\'|¼ƒŠ}‚pİjmOL0µ­àV¥¸ŠÕqu\\õ›ÅGRI†Ô5F3î™˜u]ª7·wd\r·‘ÿ\0\0u…VÑÜ%`^÷ëşÏ)~ÔË®,ì›’áÕÉ£/n¦Ÿİ\ZŒ–ÉÜ0 ¼¨SÎrhÌDüì1Rº;9¯4]p	ºG\Z³fše_5^È¥èha¥|Õ{!•`O²«‰€NÀæUÎÃÚ b@nË«}ÑûB\nP;²×©¿ÛQ@“2šÍ‹¬¦ççt!¶£/Ì4ø#¤”©\'~°b4\n}B§†çÛ¦8ËS‰y¥¡n#ZRºö¼té¦âÒ	5db¿X®S\\[S8Òƒ*ë~r§¼…£ÔX1œ¥%şá¥ãÎªgĞV§ÛšZ›p—›N”:u¬;„TŸê‚9cDø¼«¶±è+qŒË+«sël(”G\rĞèû£™Í¾å¤‡ü´›zn¥8y°\nT¤ëH$Ø­¢ôİ·b‘3lmİEésÁœPãe-Ÿ•$é¾ ä˜å“©Øì§’[Âg’ÆZ¸89…ij¹6¢O%ƒD\'”¨ÕÉÓÜ9Qû+˜™Ì5V\\såĞµ¥Jm( ¶¹øİ+9XÍ4Ù\'Ï_¶(¤À&ó—·¦€Ì0/Å^ØÀßa$~wí	°W¥ÓıoÚ0ÂÁ—I¾ÇÚ`eLK!\'ÍŞÀê2¯u\n?T0Êµ–•6GQI÷Ç_O†N£±ƒ<Û~fµ*¦)ØjmIaAK«-”ó}=´óŠMÇØöDkÛ|%şG¦&‚¡èaÄË!ajL¹¤CdX‘§²Ä‹B•íVRû1¶|^UÑküš¶õÌ²¤ò5¤ÄNß¤?ê:~\r(ÊÄ©ª¬àSm0/Á\nÔWÓNÜO|m¤L‰³dÛ~¸Ø’9@íêÍÃbá6Óm^qã³Ë4ìÇy«_$L«#Âƒû“1\0YÀ†rG9É÷\'çaê€ıÕÈğsPSÍ¾OÉ©’Ú@Iâ×õZ6M#•±¦iõ…“n\Zä¤2ú¿DÜ1€ÌL+ôM@KzeCú¡’]›WèöAcàAÉÅ[ù»ÏãÌO¬æÒäw,ho«­§ÊJV‘Öö„†+–ë²§$\\\'ãZY2#™ãNü/U’Ñ‡fëå¶V\0e×[İCÎÑÛé„Fº·‹º<–IgQ›2ê“Siq<Â‰*gqÑ$ï·\r÷ñZÈêï.ç]Ğ}Æ ¡ƒä´¤ş”­T¥ªëü$óŒÊJÊŞå-Ø)Å¸RmÒ:R	:TM€ß\Zi«c”Ò¥àäN+u,·0$æäõ´ëéÒåÂÓvÔ87E\\ií|`.Ğö†ûo\Zaôˆêı¤§¥{•t£–Yf‘ØÏ\"³«‘¾RÜGû1\0Y°Àˆçó|öEã4|úğöË9—ºŒÈLÇ6¤¸\ZJ‚À¸=VïûãdF5L­¢|õôÿ\0c@3\nhŸÊÿ\0wøÃC˜æ¯ùaû&X#©m_Ó7ëî† wGSìúõ}ĞPÄ•§—?®GÂ\nƒ?$UÁÙcı¨èv6U,K¾…\r*@P#°ˆC<Ë÷nL‹Â~1Ñ¡Ü™P\nr£ \'“VX,¹¡2%=.’o«P>®qMqeéßcîãmàj74K!Ûœûj¶×†¾¨;ˆL»©…úR}Æ%¢.QgrğşE!¼C?Cò5©m—B”ËéVä€¢•\\¬oÔ@Œa¨’«	éÛ¶XÌ¶³Ë&\\Ì<Í&˜eÌÓ­†Ìã…Ğ¥»¤\r·!)p„ïx¥+—\0•!t ¨6¾Ñchá¼QVù2›¶“¨Ÿ;¤#™¯Ù”vÈqÿ\0)äW”ùØ*Œqf3\ne¥¶y\rY-‹‡mwìî@Áœ»Ïº—æÂÓ­-¥E$€mk^:ÈĞÕ7.â·	¸ƒ@®Ÿè×ì‚†3,¡pP¡Ş“D6û@tê€(\ræÑ{Y>È` ã·š!I‰dìG¬ÃH z’Ë’¯©D’P¢I<M¡¥ÈàÕ”¾øáÑèÒîLˆ¾uÖÏIÃÓTP¶–?mKSÖPùªO_Ï¨•W4\\£Æ&|£ÒçÜ›<ó€…;¿œA$ÜúLRú îÀİYBWn\ZHú¡1Å•h¨›Õ=Í«ïökş	&YL«¯€ûóK¨ôÁ	OI;‹“¿W®5Ò|ŠX\'¬½¤Zñ±#- bú¯A`©±Ò<HpŒ%öc;ä÷”rÉµÎÁo±5ÅÚ,·!ÎÄêÉÌX;hÓƒÿ\0àbx9pª°…¨,¤jĞúíÙxİ`å\Z&™HH¬vÃ*Æ÷Sbl¥\\\0Ğ;‹u7³®\\2D54ÄÂïé‡`íBm\'u¡}éáØ9RxyÌK/½´ıĞ[ğ\n “•%RRçºãÜaß¢¨©¤³5ÍÜ·¡E7ì´*_4ûİ@¤{ã}!|â›}™©2Äå&L×©S­¥wé\'ÍºëõDë·Ûÿ\0JŠGÌ³ÊwÒT·\Zy_)u²,Ú÷â\0ÛÔ!§ú«È+Ê»kÕ>èL\n¹3.„ÿ\0:§§¹ı1ÁÉ¿‹-&K•Ç‚¦Pÿ\0âê:P›ô“¿ÿ\0¦5Ò|äRÁ<`ƒ¾ş¸İ¢/‘€BñµOw	æ…ïæ’xFìÊìuıàîV®A9.äŠ?Øšx–\\‘@F³”_(±Hí¤Mÿ\0¸ğrß9­‰ën­¶RDõŞÑ¼pr±¾nyõ$]M«n\nl„1¡±ùµƒÒ–—Wp#ÜD;`ë›h”™EÃñ¼>	rjSó‘4ß±_õ\0wU$æÂeh?Öoî1_¯ärl¬ô&Ø=áIøCÚ¼‡ Ó”µ)¾‹’ëît|mcXÛP–SRóRt­-¨z¡$XÓJ_6úûJ~1¦LÛ—vvfHµL§ÔT–×*p 5ºwZo„F¿-R²£ü™$TŞ¦!m¶Ê’§m£t#~\0Üíë=ğ×Õòêº\nê€\n¼4âú´ôúT±ş¨á§àÜ‘eª”šëFT]…Y,ÚüSÆİ_Â4Ñû,ÖT\0õGKVf4açuc©ôó‹WÈ‰ô“Â9ä¿v;|x7çü¹(®ÜHû#qÎuÃÎ1|¤Åô‰¿ò\0™ËlãËf`2—™-Î)PWİhé^ÎV17(´LÍ5·B¾jX¾FáèÍúİ½Æ\n‹îÄJÇAùuş¾Ÿ|R‡†!i/u6WôWºÇ/´3\"¤­¥¤“âÖP\rïÈ6£º{…¡íC°)Êsa½µ§¹F\r¨¤Äj*.KL­D•”IíÚÈûĞù·Z~1qÍÉFçİ’SgªvK–L²ÊtnŸ:É;Ë].-B”À\ZÁtÖÃ.K)ÁÌ¸IS{ù¦àpîPú¡;±7xèb+$oÑ¦º}*q[ÇxFä‹.[S5å…K·.\n%G†ÜNßtm¢ì<¶•İ¼t™\r¸qey…6rÿ\0\"‹y»¦9ßŞ»üN‡¼Y& oşäÒ‡²U9Ùkç\0Èîn§VUbQÛJ›øW\r	àå¶uö[šº²Ñ-…İ\'ª×í´•rÎ>A§%ä‚ÛÌ9úö?\\k?7PÕ!Í\'SEC´\rBÒ—€Ü†ÙŠ[a[¶ôm°`ÎSôy«uìWßÆòeÉÉqòsKîPŠNkM!ª¤t‘.÷xßİt»¡T@g*$$ó’ úP~ã—˜/`•†Ò$æù»”sKµøğ‰¢¬ŠŒĞ·Z>è¤‹!YÀó‰*™–8äÀ¿7cr#\rwåÑQ¢isSKKumê^•;md_®Äïë‹‚ıS²}¹²v´Uªy¶qVf}ÃsÖ-ñ=¤ü›ö$9m.\ZÄ*Re]`5¸IÕÃnÿ\0Dk¢¿l,öc¨ÈæTÂu7¼¸:R:Ctq6Œú…¬sx.¯FI_ÿ\0ÆS>Î˜æy5Eõ\0üØÿ\0âìGÿ\0Kšÿ\0%pĞ]+-‚À‚‘Õèé8;†Wä˜(İ	×ØıP|k¸íö\0y•4Ai÷ÛımCëŠØÖó‘%ÏÎ [mÑØ´˜½Úˆİ«:	ç%R®Ò‚?„ßx…xbÖ%I:Ğó?HA¾<kò‰yÉ¾“nÑ£†*k ³­t	IJ÷ê0=68¾yêcE:jà‚]ÿ\0dÄUE\\?.…´„K4D1±3.Ì´Ì¥m™X”‚²okˆ;‹æÕ—4è2”òÁÔå)ÿ\0**ZÉzÄs›ñßlkp\\‰äõÄßÓŠ­ÉÔk!UÓ¹J\r‡£Îç_³t‡ÌµSKÄ¤¶f–K+\Zµ­·¤ï\ZhVâ\'ƒhrcÆ>Ïl»8¦„Å Qƒë—K“5´âÖ‹ê	E‰6±ºüoôgYÖtğê£©BI»“’¥Õ¶¢ÒMªVÿ\0êÑòÿ\0ş§éº=YhÎ”“J¢“¶ÕÒå7Ç.½”ÅIê3ò´	„¥!aNY×’@¸·Ãmº£âu.3ş§1:îğTL	¯îH,ƒƒi¿ä¤G,•6j™°0€Ğ›Äøv~šò–†jÎK-H¶¤¥h)$_®Æ\0gä–vø¶X®Iwf-®„\'£-\\’rIÓnkœA>	Û¯şHç–‡†iG*Ï¶ur7Áó˜a\'†$T„?Z“ŸbnIkFµ%ZĞ¥I‘\Zzúrá2œ¢k‚+mM 8ËÍ:Ú¿9\n\nO´mfÛ>\\œ±ï„KqğGˆ¸ğ#Œ*P#í4õî„M¡8\'”Rl\nnQ)IS­ßnŠÍ¾¸Ÿv-3â¦÷?#1«Š™W\r¿6p>½Ñ§Ø-\rêOœ±Ñ>¨‰ªTTJÚ¦ÌÓ%Å&W\r2“¾§KwãŠIå$jš$ôÄ­¬N.†VV Y·6 OÛkwGFŸÕòxµÜzh€«ü¥İD\n‹	±#KL•¿¡<cÎçÉ°ı—ÉyX‡Zæ&ßJ\ZUõ¶¤ ^ÀúıQ®‚n^I\rœÊn\\xï\'rÁ¼%BUºsN¸ò~@»2‚²J€^°nIµ£êz/Ìm¨ôúR”S[¥\rÍÛ»iºµ|:áWòxü,z·/“[R1uÄg·)]>êù(,U‡¦?™¶ä¥]Ks*YÔÙHì½¯nGÌëé½ÎU“Şƒ:ßğAÌyWƒ#WÛƒäG±øG,şÌÒ86B$f@@~$Ât¼eHzB¯MªHÌ.ËÍË¡öœŠJ×\r6°&“5Ÿ5ü\nüšsv¬Š„æWPéUºòš>©)@ƒÒKd!@ÛpSb#XëÍôâÊ>|[œ°Ç?3„ªSXbeË”4”ÊOp6şìk©¬˜ËAöf”çï‹¯l\"GI·r”Ë>€ò‡Ñ:I>¨éUÜÏâ’É¦ù½É{32qÆ1~Äô.l\\™§º–ÿ\0oNŸ®7RO”K‰\\µSC¤€¤•b¹â…s‚Û&¤53ªUáÚÒ½ĞGj(/´€Y\0è\"ábââ3š´4èŒN`Š&`LÌÊ¡nƒ©ë*ÿ\0[«ÕïŒV„.Ú4Üğ„j5µÏ8\nÊR”‹%)Ù)‚5áÏ_hNhtÄRëmõ~ÈÎâ˜š¼»\n(çYB‰Ü\0$úÌTd—!O	àúş2}-Ñ¨uÚºÖv4çæJ»¹´˜oZ,{ì]ùgàğÏüÃ	Ì‘Í*„³¶œ34ÓjIÄ%>Óú<6%¥/Nşl“®òrä3•˜#6Ãü5‡%dçÚeÎq<u7«)¾’F×ÄãÎ›NM£d‹ª$f@@@@@@\0õ:D¥jMRó’Òól,YM¼Øq\nï\r6°&|ÏOÇ\'Qó˜+pÏ–=ÆrÇÌÛ­­;÷Æ±ê5r^œM;Ï?-1K3àyŠ°”Â®Zb}(©Ë$õqÒ»zãeÕ¾è‹Á§ÃâËòŒËÏ(VÁöWJ’ƒ)4º|ÁlJºoÜ¨ÖL^Y.±XSü^şUõÇÛe¼-MP*vj±,Ã5g¯®*]D;	A²e…|T®Qxä®±^ÀÔP¾!s«|§Ô„˜ç}Eš¨–Öñ>ñÀB±pRX¿œŠ}5nëQ›Õ±¨²äÁ(TSd”šöbâú£Ê×.ËrúO£r=¢%ê†ÒÔÀ+?%Ü)¡U	LgˆO,¬”%]á	¾F=¦ÚäGƒ³&y7aùJnËü3 Ä“a¶ÜrœÄÃäµ:´¨úI¼Då\rZÃ.Y\Zd½2U,Ë2Ô»Ià†‘ê46,-ÛŞo` ÿÙ','6499.00','ff8081813f91b1cc013f91b68f0b0001','ff8081813f91b1cc013f91be91180007');
/*!40000 ALTER TABLE `md_sku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_uom`
--

DROP TABLE IF EXISTS `md_uom`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `md_uom` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) default NULL,
  `descr` varchar(255) default NULL,
  `enabled` bit(1) default NULL,
  `name` varchar(255) default NULL,
  `version` datetime default NULL,
  `category` varchar(2) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `md_uom`
--

LOCK TABLES `md_uom` WRITE;
/*!40000 ALTER TABLE `md_uom` DISABLE KEYS */;
INSERT INTO `md_uom` VALUES ('ff8081813f91b1cc013f91b5ee950000','M','1ç±³ = 100å˜ç±³','','ç±³','2013-06-30 04:54:01','L'),('ff8081813f91b1cc013f91b68f0b0001','PIC','ç‰‡ã€å°ã€ä¸ª...','','å°','2013-06-30 04:54:07','Q'),('ff8081813f91b1cc013f91b754070002','KG','1 å…¬æ–¤ = 1000 å…‹','','å…¬æ–¤','2013-06-30 04:54:38','W'),('ff8081813f91b1cc013f91b7db380003','L','1 å‡ = 1000 æ¯«å‡ = 0.001 ç«‹æ–¹ç±³','','å‡','2013-06-30 04:55:12','V');
/*!40000 ALTER TABLE `md_uom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_vendor`
--

DROP TABLE IF EXISTS `md_vendor`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `md_vendor` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) default NULL,
  `descr` varchar(255) default NULL,
  `enabled` bit(1) default NULL,
  `name` varchar(255) default NULL,
  `version` datetime default NULL,
  `leadTimeDays` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `md_vendor`
--

LOCK TABLES `md_vendor` WRITE;
/*!40000 ALTER TABLE `md_vendor` DISABLE KEYS */;
INSERT INTO `md_vendor` VALUES ('ff8081813f91b1cc013f91bcbfae0005','IBM','International Business Machines Corporation, or IBM, is an American multinational technology and consulting corporation, with headquarters in Armonk, New York, United States.','','International Business Machines Corporation','2013-06-30 05:00:33',30),('ff8081813f91b1cc013f91bdd8470006','HP','The Hewlett-Packard Company or HP is an American multinational information technology corporation headquartered in Palo Alto, California, United States.','','The Hewlett-Packard Company','2013-06-30 05:01:45',21),('ff8081813f91b1cc013f91be91180007','DELL','Dell Inc. (formerly Dell Computer) is an American multinational computer technology corporation based in Round Rock, Texas, United States, that develops, sells, repairs and supports computers and related products and services.','','Dell Inc. (formerly Dell Computer)','2013-06-30 05:02:32',15);
/*!40000 ALTER TABLE `md_vendor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ox_sys_dummy`
--

DROP TABLE IF EXISTS `ox_sys_dummy`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `ox_sys_dummy` (
  `DTYPE` varchar(31) NOT NULL,
  `id` varchar(31) NOT NULL default 'OK',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `ox_sys_dummy`
--

LOCK TABLES `ox_sys_dummy` WRITE;
/*!40000 ALTER TABLE `ox_sys_dummy` DISABLE KEYS */;
/*!40000 ALTER TABLE `ox_sys_dummy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `po_prd`
--

DROP TABLE IF EXISTS `po_prd`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `po_prd` (
  `dtlid` varchar(32) NOT NULL,
  `qty` decimal(19,2) default NULL,
  `requireDate` datetime default NULL,
  `billHead_id` varchar(32) default NULL,
  `sku_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`dtlid`),
  KEY `FK8D095842AE404A5E` (`sku_id`),
  KEY `FK8D095842437CC488` (`billHead_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `po_prd`
--

LOCK TABLES `po_prd` WRITE;
/*!40000 ALTER TABLE `po_prd` DISABLE KEYS */;
INSERT INTO `po_prd` VALUES ('ff8081813f91d7ac013f91d842b40000','1.00','2013-08-05 00:00:00','ff8081813f91c906013f91d4f98c0005','ff8081813f91c906013f91cff0840002'),('ff8081813f91d7ac013f91d884ed0001','5.00','2013-07-24 00:00:00','ff8081813f91c906013f91d4f98c0005','ff8081813f91c906013f91d20d540003'),('ff8081813f91d7ac013f91d8b8860002','2.00','2013-07-18 00:00:00','ff8081813f91c906013f91d4f98c0005','ff8081813f91c906013f91d339c60004');
/*!40000 ALTER TABLE `po_prd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `po_prh`
--

DROP TABLE IF EXISTS `po_prh`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `po_prh` (
  `id` varchar(32) NOT NULL,
  `billNo` varchar(32) default NULL,
  `version` datetime default NULL,
  `applicant` varchar(64) default NULL,
  `applyDate` datetime default NULL,
  `department` varchar(64) default NULL,
  `reason` varchar(64) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `billNo` (`billNo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `po_prh`
--

LOCK TABLES `po_prh` WRITE;
/*!40000 ALTER TABLE `po_prh` DISABLE KEYS */;
INSERT INTO `po_prh` VALUES ('ff8081813f91c906013f91d4f98c0005','RQ2013001','2013-06-30 05:27:01','Tiger','2013-06-20 00:00:00','DEV','TEST Devide');
/*!40000 ALTER TABLE `po_prh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `po_prr`
--

DROP TABLE IF EXISTS `po_prr`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `po_prr` (
  `dtlid` varchar(32) NOT NULL,
  `remark` varchar(255) default NULL,
  `type` int(11) default NULL,
  `billHead_id` varchar(32) default NULL,
  PRIMARY KEY  (`dtlid`),
  KEY `FK8D095850437CC488` (`billHead_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `po_prr`
--

LOCK TABLES `po_prr` WRITE;
/*!40000 ALTER TABLE `po_prr` DISABLE KEYS */;
INSERT INTO `po_prr` VALUES ('ff8081813f91d7ac013f91d90a090003','ç©ºè¿',1,'ff8081813f91c906013f91d4f98c0005'),('ff8081813f91d7ac013f91d9677d0004','ç¬¦åˆéƒ¨é—¨å¹´åº¦é¢„ç®—',3,'ff8081813f91c906013f91d4f98c0005'),('ff8081813f91d7ac013f91d9a81c0005','ä¾›åº”å•†é›¶å”®ä»·',0,'ff8081813f91c906013f91d4f98c0005');
/*!40000 ALTER TABLE `po_prr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `product` (
  `number` int(11) NOT NULL,
  `description` varchar(40) default NULL,
  `unitPrice` decimal(19,2) default NULL,
  PRIMARY KEY  (`number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-08-07  3:03:26
