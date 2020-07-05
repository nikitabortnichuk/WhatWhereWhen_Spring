CREATE DATABASE  IF NOT EXISTS `game_www_spring` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `game_www_spring`;
-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: localhost    Database: game_www_spring
-- ------------------------------------------------------
-- Server version	5.7.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3x1e18lmu2r0xekpql9xme0pp` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','$2y$10$PUp28xBgz6ssyJFHYYojnOY/fyIeTQ5bjedDQq7mS3NY58PCNcVGW');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `games` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `players_number` int(11) NOT NULL,
  `round_time` int(11) NOT NULL,
  `rounds_number` int(11) NOT NULL,
  `game_identification` varchar(255) NOT NULL,
  `is_available` bit(1) NOT NULL,
  `average_score_per_round` int(11) DEFAULT NULL,
  `average_time_per_round` int(11) DEFAULT NULL,
  `expert_score` int(11) DEFAULT NULL,
  `number_of_used_hints` int(11) DEFAULT NULL,
  `opponent_score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_loelk55gos3py4hamwap5xgbr` (`game_identification`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
INSERT INTO `games` VALUES (1,2,20,2,'E3Ct6e0GU',_binary '\0',0,0,1,0,1),(9,2,20,2,'9dIjbrto',_binary '\0',0,0,0,0,2),(10,2,20,2,'7LTbPQ0sCI',_binary '\0',0,0,0,0,2),(11,2,20,2,'oTJzyHTV',_binary '\0',0,0,1,0,1),(12,2,20,3,'T0N7hw',_binary '',NULL,NULL,NULL,NULL,NULL),(13,2,20,2,'ObH6ZMPy3',_binary '\0',0,0,2,0,0),(14,2,20,2,'76fB591Q',_binary '\0',0,0,2,0,0),(15,2,20,2,'8Bxb0dNIoZ',_binary '\0',0,0,0,0,2),(16,2,20,2,'pA1OZSlE',_binary '\0',0,0,0,0,2),(17,2,20,2,'WEU3Osw3',_binary '',NULL,NULL,NULL,NULL,NULL),(18,2,20,2,'ya62KjLQ',_binary '\0',0,0,0,0,2),(19,2,20,2,'bTUnQbjeZ',_binary '\0',0,0,0,0,2),(20,2,20,2,'hRYAUk4B',_binary '\0',0,0,0,0,2),(21,2,20,2,'E39JfLKJ',_binary '\0',0,0,0,0,2);
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `question_type` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question_text` varchar(255) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES ('NO_VARIANTS',1,'It was in Zagreb, Bucharest, Dresden, Berlin, Tbilisi, Munsk, Moscow and Kiev. Now it remains only in some of these cities, including Kyiv. Name their main competitor in Ukraine?','Shakhtar'),('NO_VARIANTS',2,'Find the match: Kyiv - Shevchenko, Lviv - Franko, Kharkiv - Karazin. Who corresponds to Odessa?','Mechnikov'),('NO_VARIANTS',8,'Initially, an ice chopper was used for HER, which was later replaced by a specially invented orbitoclast. In 1950, in the newspaper Pravda, SHE was called a pseudo-scientific method that demonstrates the powerlessness of bourgeois science. Name HER.','Lobotomy'),('NO_VARIANTS',9,'English Admiral Edward Vernon hated when sailors got drunk. So he ordered to dilute what they drank (rum). What was the nickname of this admiral?','Grog'),('NO_VARIANTS',10,'The first SHE refers to the speaker. We just used the third. What word did we replace with \"SHE\"?','Person'),('NO_VARIANTS',11,'The first Ukrainian medical schools took in students from seminaries and theological academies, because they also had to deal with the dead. What word is omitted here?','Languages'),('NO_VARIANTS',12,'In \"The Story of Igor\'s Regiment\" the names of animals and birds are used as symbols. Yes, a crow is a symbol of grief, a swan is a symbol of a beautiful girl, a falcon is a symbol of a good fellow. And what bird became a symbol of a grieving woman?','Cuckoo'),('WITH_VARIANTS',13,'It became popular among Europeans when the Industrial Revolution took place in the 12th century and most citizens began working in factories. SHE is considered one of the most dangerous health trends. Name it.',NULL),('WITH_VARIANTS',14,'In 2000, Elon Musk contracted malaria during an entertainment trip. Then he talked about it like \"SHE KILLS.\" The decree is its kind. Name HER.',NULL),('WITH_VARIANTS',15,'It is HIM that devours the night. It is HIS dualism that is studied in school. Peace, not HIM Master was awarded. What it is?',NULL),('WITH_VARIANTS',16,'A newspaper article on the history of Irish cuisine mentions \"white meat\". This product is obtained after a certain process of processing what was obtained from the cow.',NULL),('WITH_VARIANTS',17,'An Arabic proverb says that a brave man is tested by war, a friend is tested by need, and a sage is tested by what the English poet Alexander Pop called \"The displacement of the mistakes of another.\" What it is ?',NULL),('WITH_VARIANTS',18,'The goddess Hera, wishing the death of Hercules, appeared before him in the form of ALPHA. Most Peruvians call the upper part of ALPHA the word \"Rio de Janeiro\". What has been replaced by the word \"ALPHA\"?',NULL),('WITH_VARIANTS',19,'According to the teachings of Islam, at the second sound of the trumpet of the angel of Israel, everyone will die, except for a few wounded. The last to die is the angel Azrael. What is he considered an angel?',NULL),('WITH_VARIANTS',20,'French scientists have created a device consisting of fur that mimics the lungs, tubes and airways that mimic the trachea, larynx and nasopharynx. The device is designed to find ways to combat a phenomenon. What is this phenomenon?',NULL);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions_games`
--

DROP TABLE IF EXISTS `questions_games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions_games` (
  `question_id` bigint(20) NOT NULL,
  `game_id` bigint(20) NOT NULL,
  PRIMARY KEY (`question_id`,`game_id`),
  KEY `FKor6hkv856dse5pw4eb4t7uq` (`game_id`),
  CONSTRAINT `FKor6hkv856dse5pw4eb4t7uq` FOREIGN KEY (`game_id`) REFERENCES `games` (`id`),
  CONSTRAINT `FKtexlcowuoepiqlwhje2admk41` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions_games`
--

LOCK TABLES `questions_games` WRITE;
/*!40000 ALTER TABLE `questions_games` DISABLE KEYS */;
INSERT INTO `questions_games` VALUES (9,1),(17,1),(10,9),(16,9),(16,10),(19,10),(10,11),(12,11),(8,13),(14,13),(2,14),(14,14),(9,15),(20,15),(12,16),(16,16),(17,18),(19,18),(1,19),(13,19),(2,20),(14,20),(17,21),(20,21);
/*!40000 ALTER TABLE `questions_games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'nikita@mail.com','$2a$10$sjrLk6YF2zZnCS3lfRyuLuBp2J8Vl2AQmOKqc5cYrXcXopFa18Y1W','nikita'),(2,'param@mail.com','$2a$10$x42Q4.9TESjwv.qCoIAz3OJdAabCLUcxQ9cBqWwovLgXhmQAbUbjC','param'),(3,'safari@mail.com','$2a$10$t4Ud/YlHqC9ujDQ0gy3vTu.4YTR7gqQg.FjnRANTVJl00WZ8/4Qj6','safari');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_games`
--

DROP TABLE IF EXISTS `users_games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_games` (
  `game_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`game_id`,`user_id`),
  KEY `FKk70xanxxbjs1yuum23xcmvohi` (`user_id`),
  CONSTRAINT `FKj02ehjgkwhp5dqqe8iskk6f8t` FOREIGN KEY (`game_id`) REFERENCES `games` (`id`),
  CONSTRAINT `FKk70xanxxbjs1yuum23xcmvohi` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_games`
--

LOCK TABLES `users_games` WRITE;
/*!40000 ALTER TABLE `users_games` DISABLE KEYS */;
INSERT INTO `users_games` VALUES (1,1),(9,1),(10,1),(11,1),(13,1),(14,1),(15,1),(16,1),(18,1),(19,1),(20,1),(21,1),(1,2),(9,2),(10,2),(11,2),(13,2),(14,2),(15,2),(16,2),(18,2),(19,2),(20,2),(21,2);
/*!40000 ALTER TABLE `users_games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variants`
--

DROP TABLE IF EXISTS `variants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variants` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_correct` bit(1) NOT NULL,
  `text` varchar(255) NOT NULL,
  `question_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKchl2eq4o9hfot9bdt9oa05ooa` (`question_id`),
  CONSTRAINT `FKchl2eq4o9hfot9bdt9oa05ooa` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variants`
--

LOCK TABLES `variants` WRITE;
/*!40000 ALTER TABLE `variants` DISABLE KEYS */;
INSERT INTO `variants` VALUES (33,_binary '\0','Dress with a corset',13),(34,_binary '\0','Handkerchief',13),(35,_binary '','Tanning',13),(36,_binary '\0','Tie',13),(37,_binary '\0','Africa',14),(38,_binary '\0','Disease',14),(39,_binary '','Vacation',14),(40,_binary '\0','Negligence',14),(41,_binary '','Light',15),(42,_binary '\0','Fear',15),(43,_binary '\0','Rest',15),(44,_binary '\0','Life',15),(45,_binary '\0','Brisket',16),(46,_binary '','Cottage cheese',16),(47,_binary '\0','Salo',16),(48,_binary '\0','Milk',16),(49,_binary '\0','Silence',17),(50,_binary '\0','Time',17),(51,_binary '\0','Thoughts',17),(52,_binary '','Anger',17),(53,_binary '','Amazon',18),(54,_binary '\0','Brazil',18),(55,_binary '\0','Love',18),(56,_binary '\0','Aphrodite',18),(57,_binary '\0','Amour',19),(58,_binary '\0','Hope',19),(59,_binary '\0','Life',19),(60,_binary '','Death',19),(61,_binary '\0','Pneumonia',20),(62,_binary '\0','Lung Cancer',20),(63,_binary '','Snore',20),(64,_binary '\0','Cough',20);
/*!40000 ALTER TABLE `variants` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-05 19:28:22
