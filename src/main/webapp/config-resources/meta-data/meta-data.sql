-- MySQL dump 10.13  Distrib 5.6.16, for Win32 (x86)
--
-- Host: 192.168.1.30    Database: qa_report
-- ------------------------------------------------------
-- Server version	5.5.25

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
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES ('CN','China','2014-02-28 19:52:22','00917528',NULL,NULL),('DE','Deutchland','2014-02-28 19:52:22','00917528',NULL,NULL),('EU','Europe','2014-02-28 19:52:22','00917528',NULL,NULL),('JP','Japan','2014-02-28 19:52:22','00917528',NULL,NULL),('UK','United Kingdom','2014-02-28 19:52:22','00917528',NULL,NULL),('US','United States','2014-02-28 19:52:22','00917528',NULL,NULL);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `data_issue_data_type`
--

LOCK TABLES `data_issue_data_type` WRITE;
/*!40000 ALTER TABLE `data_issue_data_type` DISABLE KEYS */;
INSERT INTO `data_issue_data_type` VALUES ('Account','2014-02-28 20:03:45','00917528',NULL,NULL),('Login account','2014-02-28 20:03:45','00917528',NULL,NULL),('Open Account','2014-02-28 20:03:45','00917528',NULL,NULL),('Others','2014-02-28 20:03:45','00917528',NULL,NULL),('Product','2014-02-28 20:03:45','00917528',NULL,NULL),('QVC Gift Card','2014-02-28 20:03:45','00917528',NULL,NULL);
/*!40000 ALTER TABLE `data_issue_data_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `defect_status_type`
--

LOCK TABLES `defect_status_type` WRITE;
/*!40000 ALTER TABLE `defect_status_type` DISABLE KEYS */;
INSERT INTO `defect_status_type` VALUES ('Closed','2014-02-28 20:01:31',NULL,'00917528',NULL),('Deferred','2014-02-28 20:01:31',NULL,'00917528',NULL),('Failed','2014-02-28 20:01:31',NULL,'00917528',NULL),('Fix in Progress','2014-02-28 20:01:31',NULL,'00917528',NULL),('Fix Verified','2014-02-28 20:01:31',NULL,'00917528',NULL),('N/A','2014-03-04 14:07:47',NULL,'00909932',NULL),('New','2014-02-28 20:01:31',NULL,'00917528',NULL),('Ready for Test','2014-02-28 20:01:31',NULL,'00917528',NULL),('Reject','2014-02-28 20:01:31',NULL,'00917528',NULL),('Review','2014-02-28 20:01:31',NULL,'00917528',NULL);
/*!40000 ALTER TABLE `defect_status_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `demo`
--

LOCK TABLES `demo` WRITE;
/*!40000 ALTER TABLE `demo` DISABLE KEYS */;
INSERT INTO `demo` VALUES (1,'demo1');
/*!40000 ALTER TABLE `demo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `domain`
--

LOCK TABLES `domain` WRITE;
/*!40000 ALTER TABLE `domain` DISABLE KEYS */;
INSERT INTO `domain` VALUES ('QVC_INTERNATIONAL','International domain; Mainly for DE','2014-02-28 19:52:22','00917528',NULL,NULL),('QVC_UK','UK domain','2014-02-28 19:52:22','00917528',NULL,NULL),('QVC_US','US domain','2014-02-28 19:52:22','00917528',NULL,NULL);
/*!40000 ALTER TABLE `domain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `issue_component`
--

LOCK TABLES `issue_component` WRITE;
/*!40000 ALTER TABLE `issue_component` DISABLE KEYS */;
INSERT INTO `issue_component` VALUES ('actualResult','Actual Result','Y','00909932','2014-03-04 12:57:03'),('afterChange','After Change','Y','00909932','2014-03-04 12:57:03'),('beforeChange','Before Change','Y','00909932','2014-03-04 12:57:03'),('caseId','Case Id','N','00909932','2014-03-04 12:57:03'),('data','Data','Y','00909932','2014-03-04 12:57:03'),('dataType','Data Type','Y','00909932','2014-03-04 12:57:03'),('defectNumber','Defect Number','Y','00909932','2014-03-04 12:57:04'),('defectStatus','Defect Status','Y','00909932','2014-03-04 12:57:04'),('deviceName','Device Name','Y','00909932','2014-03-04 12:57:04'),('errorData','Error Data','Y','00909932','2014-03-04 12:57:04'),('errorDescription','Error Description','Y','00909932','2014-03-04 12:57:04'),('errorPageName','Error Page Name','Y','00909932','2014-03-04 12:57:04'),('expectedResult','Expected Result','Y','00909932','2014-03-04 12:57:04'),('issueType','Issue Type','Y','00909932','2014-03-04 12:57:03'),('moduleActionName','Module Action Name','Y','00909932','2014-03-04 12:57:04'),('reproducedSteps','Reproduced Steps','Y','00909932','2014-03-04 12:57:04'),('responser','Responser','Y','00909932','2014-03-04 12:57:03'),('setId','Set Id','N','00909932','2014-03-04 12:57:03'),('status','Status','Y','00909932','2014-03-04 12:57:03'),('taskId','Task Id','N','00909932','2014-03-04 12:57:03'),('testCaseName','Test Case Name','Y','00909932','2014-03-04 12:57:03'),('tester','Tester','Y','00909932','2014-03-04 12:57:03'),('testSetName','Test Set Name','Y','00909932','2014-03-04 12:57:03'),('title','Title','Y','00909932','2014-03-04 12:57:05');
/*!40000 ALTER TABLE `issue_component` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `issue_type`
--

LOCK TABLES `issue_type` WRITE;
/*!40000 ALTER TABLE `issue_type` DISABLE KEYS */;
INSERT INTO `issue_type` VALUES ('Data Issue',NULL,'2014-02-28 20:07:01',NULL,'00917528',NULL),('Device Issue',NULL,'2014-02-28 20:07:01',NULL,'00917528',NULL),('Known Issue',NULL,'2014-02-28 20:07:01',NULL,'00917528',NULL),('Object Change',NULL,'2014-02-28 20:07:01',NULL,'00917528',NULL),('Other Issue',NULL,'2014-02-28 20:07:01',NULL,'00917528',NULL),('Performance Issue',NULL,'2014-02-28 20:07:01',NULL,'00917528',NULL),('Possible Defect',NULL,'2014-02-28 20:07:01',NULL,'00917528',NULL),('Re-run Passed',NULL,'2014-02-28 20:07:01',NULL,'00917528',NULL),('Script Issue',NULL,'2014-02-28 20:07:01',NULL,'00917528',NULL),('Undetermined',NULL,'2014-02-28 20:07:01',NULL,'00917528',NULL);
/*!40000 ALTER TABLE `issue_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES ('CN_EPS','QVC_INTERNATIONAL',NULL,'2014-02-28 19:55:17','00917528',NULL,NULL),('eCommerce','QVC_US',NULL,'2014-02-28 19:55:17','00917528',NULL,NULL),('EU_CRM','QVC_INTERNATIONAL',NULL,'2014-02-28 19:55:17','00917528',NULL,NULL),('OECS_VRU','QVC_US',NULL,'2014-02-28 19:55:17','00917528',NULL,NULL),('UK_SystemTestCycles','QVC_UK',NULL,'2014-02-28 19:55:17','00917528',NULL,NULL);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'SERVER_ADMIN','admin for Tomcat server','2014-02-28 19:42:48','00917528',NULL,NULL,'N'),(2,'DBA','admin for mysql DB','2014-02-28 19:42:48','00917528',NULL,NULL,'N'),(3,'QA_ADMIN','admin for QA team','2014-02-28 19:42:48','00917528',NULL,NULL,'Y'),(4,'TESTER','tester of QA team','2014-02-28 19:42:48','00917528',NULL,NULL,'Y');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `scope`
--

LOCK TABLES `scope` WRITE;
/*!40000 ALTER TABLE `scope` DISABLE KEYS */;
INSERT INTO `scope` VALUES (1,'QVC_US','eCommerce','US','2014-02-28 19:56:23','00917528',NULL,NULL),(2,'QVC_US','OECS_VRU','US','2014-02-28 19:56:23','00917528',NULL,NULL),(3,'QVC_UK','UK_SystemTestCycles','UK','2014-02-28 19:56:23','00917528',NULL,NULL),(4,'QVC_INTERNATIONAL','CN_EPS','CN','2014-02-28 19:56:23','00917528',NULL,NULL),(5,'QVC_INTERNATIONAL','EU_CRM','EU','2014-02-28 19:56:23','00917528',NULL,NULL),(6,'QVC_US','eCommerce','UK','2014-02-28 19:56:23','00917528',NULL,NULL),(7,'QVC_US','eCommerce','DE','2014-02-28 19:56:23','00917528',NULL,NULL);
/*!40000 ALTER TABLE `scope` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-03-04 14:26:17
