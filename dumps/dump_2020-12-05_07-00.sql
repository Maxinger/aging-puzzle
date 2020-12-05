-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: localhost    Database: aging_puzzle
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.19.04.2

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
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area` (
  `id` bigint(20) NOT NULL,
  `language` varchar(2) NOT NULL,
  `description` mediumtext,
  `name` varchar(255) DEFAULT NULL,
  `base_entity_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrbjpdgedhnai9pqdqt0l6kp5c` (`base_entity_id`,`language`),
  CONSTRAINT `FKnuku50wkadl4pl8tp9wd0a9sn` FOREIGN KEY (`base_entity_id`) REFERENCES `base_area` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (6,'en','Targeting mitochondrial dysfunction','Mitochondrial repair',5),(8,'en','Resetting the epigenetic aging markers by cellular reprogramming','Epigenetics rollback',7),(61,'en','Rejuvenation by therapeutic elimination of senescent cells','Cellular senescence',60),(73,'en','Using pharmaceutical drugs against aging','Pharmacotherapy',72),(135,'ru','Омоложение путём уничтожения сенесцентных клеток','Сенесцентные клетки',60),(145,'ru','Омоложение путём обнуления эпегенетических маркеров','Эпигенетический откат',7),(147,'ru','Устранение митохондриальной дисфункции','Восстановление митохондрий',5),(194,'ru','Применение лекарственных средств против старения','Фармакотерапия',72),(231,'en','Extending the telomeres to prevent, delay, or even reverse aging','Telomere extension',230),(232,'ru','Удлинение теломер с целью отсрочить, остановить или даже обратить старение','Теломеры',230);
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `base_area`
--

DROP TABLE IF EXISTS `base_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_area` (
  `id` bigint(20) NOT NULL,
  `image_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr7vra0wqsqxvsc4droquvlnof` (`image_id`),
  CONSTRAINT `FKr7vra0wqsqxvsc4droquvlnof` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_area`
--

LOCK TABLES `base_area` WRITE;
/*!40000 ALTER TABLE `base_area` DISABLE KEYS */;
INSERT INTO `base_area` VALUES (5,248),(230,250),(7,251),(72,252),(60,253);
/*!40000 ALTER TABLE `base_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `base_organization`
--

DROP TABLE IF EXISTS `base_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_organization` (
  `id` bigint(20) NOT NULL,
  `links` varchar(255) DEFAULT NULL,
  `image_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `date_created` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrrmmq3rcnw1xgwny8s5pvvlwu` (`image_id`),
  KEY `FKnub9ata2ulg5vtx6o9sfi2yg5` (`parent_id`),
  CONSTRAINT `FKnub9ata2ulg5vtx6o9sfi2yg5` FOREIGN KEY (`parent_id`) REFERENCES `base_organization` (`id`),
  CONSTRAINT `FKrrmmq3rcnw1xgwny8s5pvvlwu` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_organization`
--

LOCK TABLES `base_organization` WRITE;
/*!40000 ALTER TABLE `base_organization` DISABLE KEYS */;
INSERT INTO `base_organization` VALUES (2,NULL,3,NULL,NULL),(10,'home#https://www.turn.bio/',11,NULL,NULL),(13,'home#https://www.agexinc.com',15,NULL,NULL),(16,'home#http://youthereum.io\r\nhttps://www.facebook.com/youthereum/',17,NULL,NULL),(19,NULL,20,NULL,NULL),(22,NULL,23,NULL,NULL),(25,'home#https://lifebiosciences.com',26,NULL,NULL),(40,'home#http://senolytx.com/',41,25,NULL),(43,'home#https://unitybiotechnology.com/',44,NULL,NULL),(46,'home#https://shiftbioscience.com/\r\nhttps://twitter.com/shiftbioscience',48,NULL,NULL),(49,'home#https://www.sens.org/',50,NULL,NULL),(52,'home#https://www.afar.org',53,NULL,NULL),(55,'home#https://www.genucurelabs.com/',56,NULL,NULL),(211,'home#https://www.oisinbio.com/',212,NULL,'2020-06-17'),(216,'home#https://www.juvenatherapeutics.com/',217,NULL,'2020-06-18'),(226,'home#https://www.libellagenetherapeutics.com/',227,NULL,'2020-06-28'),(239,'home#https://www.revelpharmaceuticals.com/',240,NULL,'2020-06-30'),(244,'https://www.elevian.com/',245,NULL,'2020-08-29');
/*!40000 ALTER TABLE `base_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `base_person`
--

DROP TABLE IF EXISTS `base_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_person` (
  `id` bigint(20) NOT NULL,
  `links` varchar(255) DEFAULT NULL,
  `image_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk45tvv9gbevj5xa8pu8qnytra` (`image_id`),
  CONSTRAINT `FKk45tvv9gbevj5xa8pu8qnytra` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_person`
--

LOCK TABLES `base_person` WRITE;
/*!40000 ALTER TABLE `base_person` DISABLE KEYS */;
INSERT INTO `base_person` VALUES (28,'https://www.linkedin.com/in/sinclairda/\r\nhttps://www.facebook.com/davidsinclairphd/',29),(31,'home#https://www.sens.org/\r\nhttps://en.wikipedia.org/wiki/Aubrey_de_Grey\r\nhttps://www.facebook.com/drdegrey/',32),(34,'home#https://forever-healthy.org/en/\r\nhttps://www.facebook.com/michael.greve\r\nhttps://www.linkedin.com/in/migreve/',35),(37,'home#http://youthereum.io/\r\nhttps://ca.linkedin.com/in/yurideigin\r\nhttps://www.facebook.com/ydeigin\r\nhttps://medium.com/@yurideigin\r\nhttps://habr.com/ru/users/yurideigin/posts/\r\nhttps://twitter.com/ydeigin',38),(111,'https://www.linkedin.com/in/vittorio-sebastiano-36605538/\r\nhttps://med.stanford.edu/profiles/vittorio-sebastiano',112),(119,'home#http://www.michaelwest.org/\r\nhttps://en.wikipedia.org/wiki/Michael_D._West',120),(128,'https://www.irbbarcelona.org/en/profile/manuel-serrano',129),(150,'https://www.linkedin.com/in/ilir-dubova-3446995\r\nhttps://www.researchgate.net/profile/Ilir_Dubova',151),(198,'https://en.wikipedia.org/wiki/Nir_Barzilai\r\nhttps://www.einstein.yu.edu/faculty/484/nir-barzilai/',199),(220,'home#https://www.juvenatherapeutics.com/hyousefbio\r\nhttps://www.linkedin.com/in/hanadie-yousef-a2120026\r\ninterview_en#https://www.lifespan.io/news/hanadie-yousef-embryonic-proteins-for-tissue-regeneration/',221);
/*!40000 ALTER TABLE `base_person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `base_project`
--

DROP TABLE IF EXISTS `base_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_project` (
  `id` bigint(20) NOT NULL,
  `links` varchar(255) DEFAULT NULL,
  `base_area_id` bigint(20) DEFAULT NULL,
  `base_organization_id` bigint(20) DEFAULT NULL,
  `image_id` bigint(20) DEFAULT NULL,
  `date_created` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKogn9pji9twniwhp18wt0q9s2p` (`base_area_id`),
  KEY `FKams9534m5eo5fne2fjyra77d5` (`base_organization_id`),
  KEY `FKb4nojd9cb4fo4kbubud7by91i` (`image_id`),
  CONSTRAINT `FKams9534m5eo5fne2fjyra77d5` FOREIGN KEY (`base_organization_id`) REFERENCES `base_organization` (`id`),
  CONSTRAINT `FKb4nojd9cb4fo4kbubud7by91i` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
  CONSTRAINT `FKogn9pji9twniwhp18wt0q9s2p` FOREIGN KEY (`base_area_id`) REFERENCES `base_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_project`
--

LOCK TABLES `base_project` WRITE;
/*!40000 ALTER TABLE `base_project` DISABLE KEYS */;
INSERT INTO `base_project` VALUES (58,NULL,NULL,40,NULL,NULL,NULL),(62,NULL,60,40,NULL,NULL,'preclinical'),(64,NULL,60,40,NULL,NULL,'discovery'),(66,NULL,60,40,NULL,NULL,'discovery'),(68,'https://clinicaltrials.gov/ct2/show/NCT03513016',60,43,NULL,NULL,'completed'),(70,'http://youthereum.io/experiments',7,16,NULL,NULL,'completed'),(74,NULL,5,46,NULL,NULL,NULL),(76,'home#https://www.sens.org/mitosens/\r\nhttps://www.lifespan.io/campaigns/mitosens-transgenic-mouse-project/',5,49,NULL,NULL,'discovery'),(78,'home#https://www.afar.org/tame-trial',72,52,NULL,NULL,'in-preparation'),(162,'https://clinicaltrials.gov/ct2/show/NCT04129944?term=NCT04129944',60,43,NULL,'2020-02-29','in-progress'),(234,'https://clinicaltrials.gov/ct2/show/NCT04133649?term=libella+gene+therapeutics&draw=2&rank=2',230,226,NULL,'2020-06-28','in-preparation');
/*!40000 ALTER TABLE `base_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `base_update`
--

DROP TABLE IF EXISTS `base_update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_update` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `base_organization_id` bigint(20) DEFAULT NULL,
  `base_project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7ofowj1c6e4hrli344sj2dryk` (`base_organization_id`),
  KEY `FKnyjkn9yxlpwgbvls7ua656jed` (`base_project_id`),
  CONSTRAINT `FK7ofowj1c6e4hrli344sj2dryk` FOREIGN KEY (`base_organization_id`) REFERENCES `base_organization` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKnyjkn9yxlpwgbvls7ua656jed` FOREIGN KEY (`base_project_id`) REFERENCES `base_project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_update`
--

LOCK TABLES `base_update` WRITE;
/*!40000 ALTER TABLE `base_update` DISABLE KEYS */;
INSERT INTO `base_update` VALUES (164,'2020-02-25',43,162),(166,'2020-02-25',43,162),(168,'2020-02-25',43,162),(177,'2019-11-07',49,76),(181,'2020-03-01',13,NULL),(254,'2020-10-11',16,70);
/*!40000 ALTER TABLE `base_update` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (260),(260),(260),(260),(260),(260),(260),(260),(260),(260),(260),(260),(260);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (3,'1566417387767_youthereum.png',NULL),(11,'1569510272203_turnbio.png','https://www.turn.bio/'),(15,'1569515487849_age-x.png','https://www.agexinc.com'),(17,'1569515546314_youthereum.png','http://youthereum.io'),(20,'1569735401497_life-biosciences.svg','home:https://lifebiosciences.com'),(23,'1569735470428_life-biosciences.svg',NULL),(26,'1569735747005_life-biosciences.svg','https://lifebiosciences.com'),(29,'1569736683167_Sinclair.jpg','https://lifebiosciences.com/team/'),(32,'1569736825661_deGrey.png','https://www.sens.org/about-us/leadership/executive-team/'),(35,'1569736886292_Greve.jpg','https://www.facebook.com/michael.greve'),(38,'1584114190356_deigin.jpg','https://www.facebook.com/ydeigin'),(41,'1569737162269_senolytx.png','http://senolytx.com/'),(44,'1569737231139_unity.svg','https://unitybiotechnology.com/'),(48,'1569737574386_shiftbio.svg','https://shiftbioscience.com'),(50,'1569737628210_sens.png','https://www.sens.org/'),(53,'1569737688800_afar.png','https://www.afar.org'),(56,'1569737732978_genucure.svg','https://www.genucurelabs.com/'),(112,'1572357962079_sebastiano.png','https://www.turn.bio/team'),(120,'1572615383466_west.png','https://www.agexinc.com/management-mike-west-aubrey-de-grey/'),(129,'1582055380905_serrano.jpg','https://www.irbbarcelona.org/en/profile/manuel-serrano'),(151,'1582476402373_Ilir_Dubova.jpg','https://www.researchgate.net/profile/Ilir_Dubova'),(199,'1592324219609_nir-barzilai.jpg','https://www.einstein.yu.edu/faculty/484/nir-barzilai/'),(212,'1592423933365_Oisin.png','https://www.oisinbio.com/'),(217,'1592494873649_Juvena.png','https://www.juvenatherapeutics.com/'),(221,'1592498513291_Yousef.jpg','https://www.juvenatherapeutics.com/hyousefbio'),(227,'1593360264929_libella.png','https://www.libellagenetherapeutics.com/'),(240,'1593531692394_revel.png','https://www.revelpharmaceuticals.com/'),(245,'1598698560606_Elevian.png','https://www.elevian.com/'),(248,'1600320142008_mitochondria.png','https://www.news-medical.net/life-sciences/Do-Nanomaterials-Affect-the-Mitochondria.aspx'),(250,'1600318959720_telomere.png','https://med.stanford.edu/'),(251,'1600320150959_epigenetics.png','https://pixabay.com/ru/illustrations/%D0%B4%D0%BD%D0%BA-%D0%B3%D0%B5%D0%BD%D0%B5%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B9-%D0%BC%D0%B0%D1%82%D0%B5%D1%80%D0%B8%D0%B0%D0%BB-%D1%81%D0%BF%D0%B8%D1%80%D0%B0%D0%BB%D1%8C-3539309/'),(252,'1600320346943_pills.png','https://www.freeimages.com/ru/photo/drugs-pills-tablets-meds-medicine-medical-prescription-drug-chemicals-1635881'),(253,'1600320830948_senescent.png','https://biologydictionary.net/animal-cell/');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `id` bigint(20) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `base_organization_id` bigint(20) DEFAULT NULL,
  `base_person_id` bigint(20) NOT NULL,
  `base_project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcw2vf5g25tu6h4u37i42flk9m` (`base_organization_id`),
  KEY `FK3hdwe7fk0monrj2cq26dnfpgx` (`base_person_id`),
  KEY `FKr6ohclexgois61vj6i31newww` (`base_project_id`),
  CONSTRAINT `FK3hdwe7fk0monrj2cq26dnfpgx` FOREIGN KEY (`base_person_id`) REFERENCES `base_person` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKcw2vf5g25tu6h4u37i42flk9m` FOREIGN KEY (`base_organization_id`) REFERENCES `base_organization` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKr6ohclexgois61vj6i31newww` FOREIGN KEY (`base_project_id`) REFERENCES `base_project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (90,'cso,co-founder',49,31,NULL),(114,'co-founder',10,111,NULL),(123,'founder,ceo',13,119,NULL),(131,'co-founder',40,128,NULL),(154,'founder,ceo',55,150,NULL),(188,'ceo',16,37,NULL),(207,'principal-investigator',NULL,198,78),(208,'scientific-director',52,198,NULL),(224,'ceo,co-founder',216,220,NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL,
  `key_` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (99,'ceo','en','CEO','role'),(100,'ceo','ru','Директор','role'),(101,'cso','en','Chief Science Officer','role'),(102,'cso','ru','Главный научный сотрудник','role'),(103,'founder','en','Founer','role'),(104,'founder','ru','Основатель','role'),(105,'co-founder','en','Co-founer','role'),(106,'co-founder','ru','Сооснователь','role'),(124,'discovery','en','Discovery','status'),(125,'discovery','ru','Исследование','status'),(126,'preclinical','en','Preclinical','status'),(127,'preclinical','ru','Доклинические исследования','status'),(158,'in-progress','en','In progress','status'),(159,'in-progress','ru','В процессе','status'),(160,'completed','en','Completed','status'),(161,'completed','ru','Завершён','status'),(196,'in-preparation','en','In preparation','status'),(197,'in-preparation','ru','В подготовке','status'),(203,'scientific-director','en','Scientific Director','role'),(204,'scientific-director','ru','Научный руководитель','role'),(205,'principal-investigator','en','Principal Investigator','role'),(206,'principal-investigator','ru','Ведущий исследователь','role');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization` (
  `id` bigint(20) NOT NULL,
  `language` varchar(2) NOT NULL,
  `description` mediumtext,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `base_entity_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKb9ctdlv5ykhy0k0bwgl90da5o` (`base_entity_id`,`language`),
  CONSTRAINT `FKj8rx0sfpv5ycrkvvem38rucl9` FOREIGN KEY (`base_entity_id`) REFERENCES `base_organization` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` VALUES (12,'en','**Turn Biotechnologies** is developing a platform reversing well-accepted hallmarks of aging *by reprogramming cells*. The platform already demonstrated a youthful reversion of 8 of the 9 hallmarks, rejuvenation of 5 different tissue types, and 3 potential target deseases to be addressed.','Mountain View, CA, USA','Turn Biotechnologies',10),(14,'en','**AgeX Therapeutics** is developing proprietary technologies such as *PureStem®* and *induced Tissue Regeneration (iTR™)* to address some of the largest unsolved problems in aging.\r\n\r\n*PureStem®* allows to generate pluripotent stem cell-derived young cells of any type. The goal of *iTR™* technology is to reprogam cells to only reverse their aging back to a regenerative state, not back to pluripotency, and thus to induce tissue regeneration.','Alameda, CA, USA','AgeX Therapeutics',13),(18,'en','**Youthereum Genetics** is developing an epigenetic rejuvenation gene therapy of aging',NULL,'Youthereum Genetics',16),(27,'en','**Life Biosciences** established and empowered daughter companies to tackle eight pathways of aging:\r\n\r\n* Mitochondrial Dysfunction\r\n* Altered Communication & Inflammation\r\n* Chromosomal Instability\r\n* Cellular Senescence\r\n* Loss of Proteostasis\r\n* Epigenetic Alterations\r\n* Stem Cell Exhaustion\r\n* Metabolism','Boston, MA, USA','Life Biosciences',25),(42,'en','**Senolytic Therapeutics** – a member of the **Life Biosciences** group – develops novel classes of medicines that target and eliminate damaged (senescent) cells.','Boston, MA, USA & Barselona, Spain','Senolytic Therapeutics',40),(45,'en','**UNITY Biotechnology**\'s mission is to extend human healthspan, the period in one’s life unburdened by the disease of aging. The company is developing medicines that potentially halt, slow or reverse age-associated diseases, while restoring human health.','Brisbane, CA, USA','UNITY Biotechnology',43),(47,'en','Developing next-generation epigenetic clocks with the goal to identify novel drug molecules which are more effective in decelerating the epigenetic aging clock.','Cambridge, United Kingdom','Shift Bioscience',46),(51,'en','SENS stands for ***Strategies for Engineered Negligible Senescence***. The company **SENS Resarch Foundation** is developing a panel of  rejuvenation biotechnilogies within those strategies, addressing seven major types of aging damage:\r\n* Cell loss, tissue atrophy\r\n* Cancerous cells\r\n* Mitochondrial mutations\r\n* Death-resistant cells\r\n* Extracellular matrix stiffening\r\n* Extracellular aggregates\r\n* Intracellular aggregates','Mountain View, CA, USA','SENS Research Foundation',49),(54,'en','AFAR is a national non-profit organization whose mission is to support and advance healthy aging through biomedical research.','New York, NY, USA','American Federation for Aging Research',52),(57,'en','GenuCure is a therapeutic company, focused on unlocking the regenerative capacity of human cartilage tissue.',NULL,'GenuCure',55),(109,'ru','Название SENS означает ***Стратегии достижения пренебрежимого старения инженерными методами***. Компания  **SENS Resarch Foundation** разрабатывает панель омолаживающих биотехнологий в рамках этих стратегий, направленных против семи основных типов возрастных повреждений организма:\r\n* Гибель и атрофия клеток\r\n* Повреждения ядерной ДНК\r\n* Повреждения митохондриальной ДНК\r\n* Появление бессмертных (раковых) клеток\r\n* Внеклеточные перекрёстные сшивки\r\n* Накопление внеклеточного мусора\r\n* Накопление внутриклеточного мусора','Маунтин-Вью, Калифорния, США','SENS Research Foundation',49),(115,'ru','**Turn Biotechnologies** разрабатывает платформу для обращения вспять общепринятых признаков старения путём *перепрограммирования клеток*. Платформа уже продемонстрировала откат 8 из 9 признаков, омоложение 5 различных типов тканей и 3 потенциально излечимых заболевания.','Маунтин-Вью, Калифорния, США','Turn Biotechnologies',10),(118,'ru','**AgeX Therapeutics** разрабатывает патентованные технологии, такие как *PureStem®* и *induced Tissue Regeneration (iTR™)*, для решения открытых проблем старения.\r\n\r\n*PureStem®* позволяет выращивать из плюрипотентных стволовых клеток молодые клетки любого типа. Цель технологии *iTR™* – перепрограммировать клетки так, чтоб откатить их возраст, но не обращать в плюрипотентное состояние, таким образом вызывая регенерацию тканей.','Аламида, Калифорния, США','AgeX Therapeutics',13),(132,'ru','**Senolytic Therapeutics** входит в группу **Life Biosciences** и разрабатывает новые классы лекарств для устранения сенесцентных клеток.','Бостон, Массачусетс, США и Барселона, Испания','Senolytic Therapeutics',40),(133,'ru','**Life Biosciences** представляет собой семейство дочерних компаний, нацеленных на изучение восьми путей старения:\r\n* митохондриальная дисфункция\r\n* изменения в сигналинге и воспаление\r\n* нестабильность хромосом\r\n* сенесцентные клетки\r\n* нарушение протеостаза\r\n* эпигенетические изменения\r\n* истощение стволовых клеток\r\n* метаболизм','Бостон, Массачусетс, США','Life Biosciences',25),(146,'ru','**Shift Bioscience** разрабатывает эпигенетические часы нового поколения с целью обнаружить новые молекулы, способные откатить эпигенетический возраст.','Кембридж, Великобритания','Shift Bioscience',46),(149,'ru','**GenuCure** – терапевтическая компания, разрабатывающая способы раскрыть регенеративные способности хрящевой ткани человека.',NULL,'GenuCure',55),(157,'ru','Миссия **UNITY Biotechnology** – продлить период здоровой жизни, не отягощённой возрастными заболеваниями. Компания разрабатывает средства, которые потенциально остановят, замедлят или обратят вспять возраст-зависимые заболевания, восстанавливая при этом здоровье.','Брисбен, Калифорния, США','UNITY Biotechnology',43),(189,'ru','**Youthereum Genetics** разрабатывает генную терапию старения путём эпигенетического омоложения',NULL,'Youthereum Genetics',16),(195,'ru','**AFAR** – американская некоммерческая организация с миссией поддерживать и продвигать здоровое старение путём биомедицинский исследований.','Нью-Йорк, США','American Federation for Aging Research',52),(213,'en','**Oisín Biotechnologies** is developing a highly precise, patent-pending, DNA-targeted intervention to clear senescent cells.','Seattle, WA, USA','Oisín Biotechnologies',211),(214,'ru','**Oisín Biotechnologies** разрабатывает высокоточную запатентованную интервенцию, основанную на экспрессии ДНК, способную уничтожать сенесцентные клетки.','Сиэтл, штат Вашингтон, США','Oisín Biotechnologies',211),(218,'en','**Juvena Therapeutics** is a venture-backed biopharma startup discovering novel protein-based therapeutics that promote tissue regeneration in the elderly and treat various age-related diseases.','Palo Alto, CA, USA','Juvena Therapeutics',216),(219,'ru','**Juvena Therapeutics** – биофармацевтический стартап, разрабатывающий терапии на основе протеинов, которые способствуют регенерации тканей у пожилых людей и лечению возраст-зависимых заболеваний.','Пало-Альто, штат Калифорния, США','Juvena Therapeutics',216),(228,'en','**Libella Gene Therapeutics** is developing telomerase gene therapy as a clinical treatment.','Manhattan, Kansas, USA','Libella Gene Thereapeutics',226),(229,'ru','**Libella Gene Therapeutics** разрабатывает генную терапию теломеразой в клинической практике.','Манхеттен, штат Канзас, США','Libella Gene Therapeutics',226),(241,'en','**Revel Pharmaceuticals** is developing therapeutic designer enzymes to degrade the molecular damage that accumulates with aging.','San Francisco, CA, USA','Revel Pharmaceuticals',239),(242,'ru','**Revel Pharmaceuticals** разрабатывает терапевтические ферменты для уменьшения молекулярных повреждений, которые накапливаются со старением.','Сан-Франциско, штат Калифорния, США','Revel Pharmaceuticals',239),(246,'en','**Elevian** is developing a number of therapeutics that regulate Growth Differentiation Factor *GDF11* and other circulating factors, in order to restore our body’s natural regenerative capacity, which may address a root cause of age-associated diseases.','Allston, MA, USA','Elevian',244),(247,'ru','**Elevian** разрабатывает ряд терапий, регулирующих фактор роста *GDF11* и другие циркулирующие факторы, с целью восстановить регенеративные способности организма, и, возможно, устранить первопричины возвраст-зависимых заболеваний.','Аллстон, штат Массачусетс, США','Elevian',244);
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL,
  `language` varchar(2) NOT NULL,
  `description` text,
  `name` varchar(255) DEFAULT NULL,
  `residence` varchar(255) DEFAULT NULL,
  `base_entity_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKcu0uqya5uxhpqils694fh88rd` (`base_entity_id`,`language`),
  CONSTRAINT `FK81i7hy6kqmujyw4oroom2f03m` FOREIGN KEY (`base_entity_id`) REFERENCES `base_person` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (30,'en','Harvard professor working extending healthy lifespan','David A. Sinclair, PhD, AO','Greater Boston Area, USA',28),(33,'en','Biomedical gerontologist who devised the **SENS** platform, and established **SENS Research Foundation** to implement it.','Aubrey de Grey, PhD','Cambridge, England, UK',31),(36,'en','Founder & CEO at Forever Healthy Foundation','Michael Greve','Berlin, Germany',34),(39,'en','Biotech entrepreneur, life extension activist, expert in drug development and venture investments in biotechnology and pharmaceuticals.','Yuri Deigin','Russia',37),(113,'en','Assistant Professor at Stanford University. 15+ years of research experience in Developmental Biology, Nuclear Reprogramming, Stem Cell Biology and Regenerative Medicine, Stanford Institute for Stem Cell Biology and Regenerative Medicine.','Vittorio Sebastiano, PhD',NULL,111),(116,'ru','Доцент в Стэнфордском университете. Более 15 лет опыта в области биологии развития, ядерного перепрограммирования, биологии стволовых клеток и регенеративной медицины.','Витторио Себастьяно, PhD',NULL,111),(121,'en','Gerontologist, and a pioneer in stem cells, cellular aging and telomerase.','Michael D. West, PhD','Northern California, USA',119),(122,'ru','Геронтолог, пионер в области стволовых клеток, клеточного старения и теломеразы.','Майкл Уэст, PhD','Северная Калифорния, США',119),(130,'en','Group leader of the Cellular Plasticity and Disease laboratory at IRB Barcelona and a co-founder at Senolytic Therapeutics','Dr. Manuel Serrano','Barcelona, Spain',128),(152,'en','Working on anti-aging and regenerative therapies. Primarily targeting Osteoarthritis, Sarcopenia and Acute Kidney Injury.','Ilir Dubova','La Jolla, CA, USA',150),(153,'ru','Работает над антивозрастной и омолаживающей терапией, в первую очередь нацеленной на остеоартрит, саркопению и острую почечную недостаточность.','Илир Дубова','Ла-Холья, Калифорния, США',150),(190,'ru','Предприниматель в области биотехнологий, специалист по разработке и выводу на рынок новых лекарственных препаратов, вице-президент Фонда *«Наука за продление жизни»*','Юрий Дейгин',NULL,37),(200,'en','Dr. Barzilai’s research interests are in the biology and genetics of aging, including hypothesis that centenarians have protective genes that allows the delay of aging and protect against age-related diseases.','Nir Barzilai, MD','New York, NY, USA',198),(201,'ru','Научные интересы доктора Барзилай связаны с биологией и генетикой старения, включая гипотезу о том, что долгожители имеют защитные гены, способные отсрочить старение и защитить от возрастных заболеваний.','Нир Барзилай, MD','Нью-Йорк, США',198),(222,'en','Neurobiologist, stem cell biologist and entrepreneur leading **Juvena Therapeutics** in its mission to develop protein-based therapeutics to promote tissue regeneration and increase healthspan in the elderly.','Hanadie Yousef, PhD','San Francisco Bay Area, CA, USA',220),(223,'ru','Нейробиолог, биолог в области стволовых клеток, предприниматель, возглавляющий **Juvena Therapeutics** с миссией разработать протеиновую терапию, способствующую регенерации тканей и продлению здоровой жизни у пожилых людей.','Ханади Юсеф, PhD','Область залива Сан-Франциско, штат Калифорния, США',220),(1000,'ru','Геронтолог, разработавший стратегии **SENS** и основавший **SENS Research Foundation** для их реализации.','Обри ди Грей, PhD',NULL,31);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` bigint(20) NOT NULL,
  `language` varchar(2) NOT NULL,
  `description` mediumtext,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `base_entity_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKa1fythkyu0xqeh12a365pv1gs` (`base_entity_id`,`language`),
  CONSTRAINT `FK5d3j91p3r1dhkhe62thctrgxf` FOREIGN KEY (`base_entity_id`) REFERENCES `base_project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (63,'en','The SEN01 program aims to develop senolytic compounds that target and eliminate senescent cells in kidney fibrotic diseases.','SEN01 – Novel Senolysis Target (Kidney)','Preclinical',62),(65,'en','The discovery program **SEN02** is focused on new mechanism of action associated with a senolytic target and lung fibrotic diseases.','SEN02 – Senolytic Therapy (Lung)','Discovery',64),(67,'en','Discovering novel senolytic compounds in collaboration with other companies and research organizations.','SEN03 – Discovery platform','Discovery',66),(69,'en','A Safety and Tolerability Study of UBX0101 in Patients With *Osteoarthritis of the Knee*','UBX0101 – Phase 1','Completed',68),(71,'en','Test Oct4 transcription factor alone to see if it can safely roll back the epigenetic clock of human cells in vitro while preserving their differentiation.','Oct4 experiment','In progress',70),(77,'en','Allotopic expression of functional mitochondrial genes involves placing “backup copies” of all of the protein-coding genes of the mitochondria in the cell’s nucleus, where they will be better protected. It prevents damage of mitochondrial DNA to contribute in the aging process.','MitoSENS',NULL,76),(79,'en','The **T**argeting **A**ging with **Me**tformin Trial is a series of nationwide, six-year clinical trials at 14 leading research institutions across the country that will engage over 3,000 individuals between the ages of 65-79. The goal is to test whether those taking metformin experience delayed development or progression of age-related chronic diseases – such as heart disease, cancer, and dementia.\r\n\r\nIn addition, this trial, being approved by FDA, will serve as a template for future trials aiming to delay aging and its diseases in humans.','TAME Trial','Recruiting',78),(134,'ru','Программа **SEN01** нацелена на разработку сенолитиков для прицельного устранения сенесцентных клеток при фиброзе почек.','SEN01 – Novel Senolysis Target (Kidney)',NULL,62),(136,'ru','Исследовательская программа **SEN02** направлена на подбор таргетных сенолитиков при фиброзе лёгких.','SEN02 – Senolytic Therapy (Lung)',NULL,64),(137,'ru','Поиск новых сенолитических соединений в сотрудничестве с другими компаниями и исследовательскими организациями.','SEN03 – Discovery platform',NULL,66),(163,'en','A Study to Assess the Safety and Efficacy of a Single Dose of UBX0101 in Patients With Osteoarthritis of the Knee','UBX0101 – Phase 2',NULL,162),(170,'ru','Исследование безопасности и переносимости сенолитика UBX0101 на пациентах с *остеоартиром коленного сустава*','UBX0101 – Фаза 1',NULL,68),(171,'ru','Исследование безопасности и эффективности разовой дозы сенолитика UBX0101 на пациентах с *остеоартритом коленного сустава*','UBX0101 – Фаза 2',NULL,162),(176,'ru','Аллотопическая экспрессия митохондриальных генов заключается в копировании всех генов, кодирующих белки, из ДНК митохондрий в клеточное ядро, где они будут лучше защищены. Таким образом, повреждение митохондриальной ДНК перестанет быть фактором старения.','MitoSENS',NULL,76),(191,'ru','Цель проекта – проверить способность фактора транскрипции **Oct4** безопасно откатывать эпигенетические часы клеток человека *(in vitro)*, сохраняя их дифференцировку.','Oct4 experiment',NULL,70),(202,'ru','**T**argeting **A**ging with **Me**tformin Trial – серия общенациональных 6-летних клинических исследований в 14 ведущих исследовательских институтах США, в которых примут участие более 3000 человек в возрасте от 65 до 79 лет. Цель состоит в том, чтобы проверить гипотезу, что приём *метформина* задерживает развитие возрастных хронических заболеваний, таких как болезни сердца, рак и деменция.\r\n\r\nКроме того, данное исследование, будучи утверждённым FDA, послужит образцом для будущих исследований, ставящих целью отсрочить старение и возрастные заболевания у людей.','TAME Trial',NULL,78),(235,'ru','Исследование безопасности и переносимости генной терапии для лечения старения','AAV- hTERT',NULL,234),(236,'en','Evaluation of Safety and Tolerability of Libella Gene Therapy for the Treatment of Aging','AAV- hTERT',NULL,234);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `puzzle_config`
--

DROP TABLE IF EXISTS `puzzle_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `puzzle_config` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `layout` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puzzle_config`
--

LOCK TABLES `puzzle_config` WRITE;
/*!40000 ALTER TABLE `puzzle_config` DISABLE KEYS */;
INSERT INTO `puzzle_config` VALUES (1,'2019-08-21 19:32:39','| 1|  |  |  |  |  |\n|  |  |  |  |  |  |\n|  |  |  |  |  |  |\n|  |  |  |  |  |  |'),(9,'2019-09-19 13:38:28','| 5|  |  |  |  |  |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  7|'),(138,'2020-02-19 10:40:39','| 60|  |  |  |  |  |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  7|'),(139,'2020-02-19 10:41:10','| 60|  |  |  |  |  |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  7|'),(148,'2020-02-23 16:24:01','| 60|  |  |  |  | 5 |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  7|'),(193,'2020-06-16 15:48:02','| 60|  |  |  |  | 5 |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  |\r\n| 72 |  |  |  |  |  7|'),(233,'2020-06-28 16:09:59','| 60|  |  |  |  | 5 |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  |\r\n| 72 | 230 |  |  |  |  7|'),(249,'2020-09-07 06:41:36','| 60|  |  |  |  | 5 |\r\n|  |  |  |  |  |  |\r\n|  |  |  |  |  |  |\r\n| 72 | 230 |  |  |  |  7|');
/*!40000 ALTER TABLE `puzzle_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `base_organization_id` bigint(20) DEFAULT NULL,
  `base_project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8qx17qkwqwxa95j727ceiglgi` (`base_organization_id`),
  KEY `FKrqk13kol257u79y1kumw25pki` (`base_project_id`),
  CONSTRAINT `FK8qx17qkwqwxa95j727ceiglgi` FOREIGN KEY (`base_organization_id`) REFERENCES `base_organization` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKrqk13kol257u79y1kumw25pki` FOREIGN KEY (`base_project_id`) REFERENCES `base_project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (110,'2019-10-28',NULL,49,NULL),(117,'2019-10-29',NULL,10,NULL),(140,'2020-02-19',NULL,NULL,66),(141,'2020-02-19',NULL,NULL,62),(142,'2020-02-19',NULL,NULL,64),(143,'2020-02-19',NULL,25,NULL),(144,'2020-02-19',NULL,40,NULL),(155,'2020-02-23',NULL,55,NULL),(156,'2020-02-23',NULL,46,NULL),(173,'2020-02-29',NULL,NULL,68),(174,'2020-02-29',NULL,43,NULL),(175,'2020-02-29',NULL,NULL,162),(180,'2020-02-29',NULL,NULL,76),(187,'2020-03-01',NULL,13,NULL),(192,'2020-03-13',NULL,NULL,70),(209,'2020-06-16',NULL,52,NULL),(210,'2020-06-16',NULL,NULL,78),(215,'2020-06-17',NULL,211,NULL),(225,'2020-06-18',NULL,216,NULL),(237,'2020-06-28',NULL,NULL,234),(238,'2020-06-28',NULL,226,NULL),(243,'2020-06-30',NULL,239,NULL),(258,'2020-11-01',NULL,16,NULL),(259,'2020-11-02',NULL,49,NULL);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `review_summary`
--

DROP TABLE IF EXISTS `review_summary`;
/*!50001 DROP VIEW IF EXISTS `review_summary`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `review_summary` AS SELECT 
 1 AS `id`,
 1 AS `base_entity_id`,
 1 AS `entity_type`,
 1 AS `entity_name`,
 1 AS `language`,
 1 AS `translations`,
 1 AS `reviews_count`,
 1 AS `days_waiting`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `update_`
--

DROP TABLE IF EXISTS `update_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `update_` (
  `id` bigint(20) NOT NULL,
  `language` varchar(2) DEFAULT NULL,
  `full_text` mediumtext,
  `preview` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `base_entity_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKom06eij23ylfgss8vhkr8n6sj` (`base_entity_id`,`language`),
  CONSTRAINT `FKinnu2f02y868h5kgfg7ap6cqc` FOREIGN KEY (`base_entity_id`) REFERENCES `base_update` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `update_`
--

LOCK TABLES `update_` WRITE;
/*!40000 ALTER TABLE `update_` DISABLE KEYS */;
INSERT INTO `update_` VALUES (169,'en','The study is now fully enrolled with 183 patients with *osteoarthritis*. The study is randomized, double-blind, and placebo-controlled and will evaluate three doses (0.5 mg, 2.0 mg and 4.0 mg) of UBX0101 administered via a single intra-articular injection. Both 12- and 24-week results are now expected in the second half of 2020.\r\n\r\n[Source](http://ir.unitybiotechnology.com/news-releases/news-release-details/unity-biotechnology-provides-pipeline-and-business-updates)','The study is now fully enrolled with 183 patients with *osteoarthritis*.','Phase 2 study of UBX0101 has completed enrollment',168),(172,'ru','В настоящее время в исследование включено 183 пациента с *остеоартритом коленного сустава*. В рамках рандомизированного двойного слепого плацебо-контролируемого исследования будут оцениваться 3 дозы (0.5 мг, 2.0 мг и 4.0 мг) сенолитика UBX0101, вводимого через разовую внутрисуставную инъекцию. Результаты после 12 и 24 недель ожидаются во второй половине 2020 года.\r\n\r\n[Источник](http://ir.unitybiotechnology.com/news-releases/news-release-details/unity-biotechnology-provides-pipeline-and-business-updates)','В настоящее время в исследование включено 183 пациента с *остеоартритом коленного сустава*.','Завершён набор в Фазу 2 исследования сенолитика UBX0101',168),(178,'en','$77,525 has been raised in support of mitochondrial repair project **MitoMouse** of the SENS Research Foundation.\r\nThe goal of the proejct is to create a transgenic mouse demonstrating the rescue of Mitochondrial DNA mutations in mammals. The mitochondrial ATP8 gene is going to be expressed from the nucleus as proof of concept towards gene therapies for mtDNA mutations.\r\n\r\n[Source](https://www.lifespan.io/news/mitomouse-smashes-all-fundraising-goals/)','$77,525 has been raised in support of mitochondrial repair project **MitoMouse** of the SENS Research Foundation.','MitoMouse Smashes All Fundraising Goals!',177),(179,'ru','$77,525 собрано в поддержку проекта по защите митохондриальной ДНК **MitoSENS**, организованного **SENS Research Foundation**. Цель проекта – создать трансгенную линию мышей, демонстрирующую защиту от мутаций в митохондриальной ДНК у млекопитающих. Митохондриальный ген ATP8 будет экспрессироваться из ядра, как доказательство концепции генной терапии мутаций мтДНК.\r\n\r\n[Источник](https://www.lifespan.io/news/mitomouse-smashes-all-fundraising-goals/)','$77,525 собрано в поддержку проекта по защите митохондриальной ДНК **MitoSENS**, организованного **SENS Research Foundation**.','MitoMouse достиг всех целей по сбору средств!',177),(182,'en','New paper co-authored by two AgeX scientists describes successful reprogramming of 114-year-old donor cell lines into  induced pluripotent stem cells. It supports hypothesis of no upper age limit for reprogramming cellular aging.\r\n\r\nBy way of comparison, the paper also describes undertaking a similar process with cells from two other donors: an eight-year-old with a rapid-aging syndrome commonly known as Progeria, and a 43-year-old, healthy disease-free control  subject. The paper notes that the supercentenarian’s cells reverted to induced pluripotent stem cells at the same rate as the other donors. Importantly, telomere length resetting occurred in iPSC from all donors albeit at a lower incidence in supercentenarian iPSCs.\r\n\r\nThe [paper](https://cts.businesswire.com/ct/CT?id=smartlink&url=https%3A%2F%2Fdoi.org%2F10.1016%2Fj.bbrc.2020.02.092&esheet=52181417&newsitemid=20200228005122&lan=en-US&anchor=paper&index=2&md5=0d38569dd0100f7d59cfcf649944ca7b), “Induced pluripotency and spontaneous reversal of cellular aging in supercentenarian donor cells”, is published online in the peer-reviewed scientific journal “Biochemical and Biophysical Research Communications” from Elsevier\r\n\r\n[Source](https://investors.agexinc.com/news/news-details/2020/AgeX-Therapeutics-Researchers-Publish-Paper-on-the-Age-Reprogramming-of-Super-Centenarian-Cells/default.aspx)','New paper co-authored by two AgeX scientists describes successful reprogramming of 114-year-old donor cell lines into induced pluripotent stem cells.','AgeX Therapeutics Researchers Published Paper on the Age Reprogramming of Super-Centenarian Cells',181),(186,'ru','Новая работа двух исследователей из AgeX описывает успешное репрограммирование клеточных линий 114-летнего донора в индуцированные плюрипотентные стволовые клетки (ИПСК). Это подтверждает гипотезу об отсутствии возрастного лимита для клеточного репрограммирования.\r\n\r\nДля сравнения описывается похожий эксперимент с клетками двух других доноров: восьмилетнего пациента с синдромом ускоренного старения (прогерией) и 43-летнего здорового человека. В работе отмечается, что клетки супердолгожителя откотились к состоянию ИПСК с такой же скоростью, как и у других доноров. Важно, что и длина теломер была восстановлена в ИПСК всех доноров, хотя и с меньшей частотой в случае с клетками супердолгожителя.\r\n\r\n[Статья](https://cts.businesswire.com/ct/CT?id=smartlink&url=https%3A%2F%2Fdoi.org%2F10.1016%2Fj.bbrc.2020.02.092&esheet=52181417&newsitemid=20200228005122&lan=en-US&anchor=paper&index=2&md5=0d38569dd0100f7d59cfcf649944ca7b) “Индуцированная плюрипотентность и спонтанный откат клеточного возраста в донорских клетках супердолгожителя\" доступна онлайн в научном журнале “Biochemical and Biophysical Research Communications” от Elsevier\r\n\r\n[Источник](https://investors.agexinc.com/news/news-details/2020/AgeX-Therapeutics-Researchers-Publish-Paper-on-the-Age-Reprogramming-of-Super-Centenarian-Cells/default.aspx)','Новая работа двух исследователей из AgeX описывает успешное репрограммирование клеточных линий 114-летнего донора в индуцированные плюрипотентные стволовые клетки (ИПСК).','Опубликована работа исследователей AgeX Therapeutics о репрограммировании клеток супердолгожителей',181),(255,'en','The experiment did not yield the desired results. The methylation age of gingival fibroblasts taken from a 60-year old female donor did not decrease to expected 10% value after various days of Oct4 induction. Since the target was not met, the further tinkering with doses and days of consecutive induction is not justified.','The experiment did not yield the desired results.','Oct4 transcription factor alone failed to rollabck epigenetic clock',254),(257,'ru','Эксперимент не достиг ожидаемых результатов. Возраст метилирования фибробластов дёсен, взятых у 60-летней женщины-донора, не снизился на ожидаемые 10% после различных интервалов индукции Oct4. Поскольку цель не достигнута, дальнейшее варьирование доз и интервалов последовательной индукции не оправдано.','Эксперимент не достиг ожидаемых результатов.','Фактор транскрипции Oct4 не сумел откатить эпигенетический возраст',254);
/*!40000 ALTER TABLE `update_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `review_summary`
--

/*!50001 DROP VIEW IF EXISTS `review_summary`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`springuser`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `review_summary` AS select `t`.`base_entity_id` AS `id`,`t`.`base_entity_id` AS `base_entity_id`,`t`.`entity_type` AS `entity_type`,`t`.`name` AS `entity_name`,`t`.`language` AS `language`,`t`.`translations` AS `translations`,`t`.`reviews_count` AS `reviews_count`,(to_days(curdate()) - to_days(coalesce(`t`.`last_review_date`,`t`.`date_created`,'2019-10-01'))) AS `days_waiting` from (select `p`.`base_entity_id` AS `base_entity_id`,'Project' AS `entity_type`,min(`p`.`name`) AS `name`,min(`bp`.`date_created`) AS `date_created`,min(`p`.`language`) AS `language`,count(`p`.`language`) AS `translations`,count(distinct `r`.`id`) AS `reviews_count`,max(`r`.`date`) AS `last_review_date` from ((`aging_puzzle`.`project` `p` join `aging_puzzle`.`base_project` `bp` on((`p`.`base_entity_id` = `bp`.`id`))) left join `aging_puzzle`.`review` `r` on((`p`.`base_entity_id` = `r`.`base_project_id`))) group by `p`.`base_entity_id` union select `o`.`base_entity_id` AS `base_entity_id`,'Organization' AS `entity_type`,min(`o`.`name`) AS `name`,min(`bo`.`date_created`) AS `date_created`,min(`o`.`language`) AS `language`,count(`o`.`language`) AS `translations`,count(distinct `r`.`id`) AS `reviews_count`,max(`r`.`date`) AS `last_review_date` from ((`aging_puzzle`.`organization` `o` join `aging_puzzle`.`base_organization` `bo` on((`o`.`base_entity_id` = `bo`.`id`))) left join `aging_puzzle`.`review` `r` on((`o`.`base_entity_id` = `r`.`base_organization_id`))) group by `o`.`base_entity_id`) `t` order by (to_days(curdate()) - to_days(coalesce(`t`.`last_review_date`,`t`.`date_created`,'2019-10-01'))) desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-05  7:00:01
