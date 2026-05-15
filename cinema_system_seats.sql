-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: cinema_system
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `seats`
--

DROP TABLE IF EXISTS `seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seats` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hall_id` int NOT NULL,
  `seat_row` char(1) NOT NULL,
  `seat_number` int NOT NULL,
  `seat_type` enum('REGULAR','VIP') DEFAULT 'REGULAR',
  PRIMARY KEY (`id`),
  UNIQUE KEY `hall_id` (`hall_id`,`seat_row`,`seat_number`),
  CONSTRAINT `seats_ibfk_1` FOREIGN KEY (`hall_id`) REFERENCES `cinema_halls` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES (1,7,'A',1,'REGULAR'),(2,7,'A',2,'REGULAR'),(3,7,'A',3,'REGULAR'),(4,7,'A',4,'REGULAR'),(5,7,'A',5,'REGULAR'),(6,7,'A',6,'REGULAR'),(7,7,'B',1,'REGULAR'),(8,7,'B',2,'REGULAR'),(9,7,'B',3,'REGULAR'),(10,7,'B',4,'REGULAR'),(11,7,'B',5,'REGULAR'),(12,7,'B',6,'REGULAR'),(13,7,'C',1,'REGULAR'),(14,7,'C',2,'REGULAR'),(15,7,'C',3,'REGULAR'),(16,7,'C',4,'REGULAR'),(17,7,'C',5,'REGULAR'),(18,7,'C',6,'REGULAR'),(19,7,'D',1,'REGULAR'),(20,7,'D',2,'REGULAR'),(21,7,'D',3,'REGULAR'),(22,7,'D',4,'REGULAR'),(23,7,'D',5,'REGULAR'),(24,7,'D',6,'REGULAR'),(25,7,'E',1,'REGULAR'),(26,7,'E',2,'REGULAR'),(27,7,'E',3,'REGULAR'),(28,7,'E',4,'REGULAR'),(29,7,'E',5,'REGULAR'),(30,7,'E',6,'REGULAR'),(31,7,'F',1,'REGULAR'),(32,7,'F',2,'REGULAR'),(33,7,'F',3,'REGULAR'),(34,7,'F',4,'REGULAR'),(35,7,'F',5,'REGULAR'),(36,7,'F',6,'REGULAR'),(37,7,'G',1,'REGULAR'),(38,7,'G',2,'REGULAR'),(39,7,'G',3,'REGULAR'),(40,7,'G',4,'REGULAR'),(41,7,'G',5,'REGULAR'),(42,7,'G',6,'REGULAR'),(43,7,'H',1,'REGULAR'),(44,7,'H',2,'REGULAR'),(45,7,'H',3,'REGULAR'),(46,7,'H',4,'REGULAR'),(47,7,'H',5,'REGULAR'),(48,7,'H',6,'REGULAR');
/*!40000 ALTER TABLE `seats` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-15 17:10:46
