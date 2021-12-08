CREATE DATABASE  IF NOT EXISTS `banco_deposito` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `banco_deposito`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: banco_deposito
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `barras_produto`
--

DROP TABLE IF EXISTS `barras_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `barras_produto` (
  `idProduto` int NOT NULL,
  `barras` varchar(14) DEFAULT NULL,
  `principal` tinyint DEFAULT NULL,
  `dt_vinculacao` date DEFAULT NULL,
  UNIQUE KEY `Barras_UNIQUE` (`barras`),
  KEY `idProduto_idx` (`idProduto`),
  CONSTRAINT `idProduto` FOREIGN KEY (`idProduto`) REFERENCES `produto` (`idProduto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barras_produto`
--

LOCK TABLES `barras_produto` WRITE;
/*!40000 ALTER TABLE `barras_produto` DISABLE KEYS */;
INSERT INTO `barras_produto` VALUES (3,'123',1,'2021-12-05'),(5,'1234',1,'2021-12-05');
/*!40000 ALTER TABLE `barras_produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `idCliente` int NOT NULL AUTO_INCREMENT,
  `bloqueado` tinyint NOT NULL,
  `nome` varchar(45) NOT NULL,
  `apelido` varchar(20) DEFAULT NULL,
  `documento` varchar(18) DEFAULT NULL,
  `inscricaoEstadual` varchar(15) DEFAULT NULL,
  `cep` varchar(9) DEFAULT NULL,
  `cidade` varchar(30) DEFAULT NULL,
  `endereco` varchar(50) DEFAULT NULL,
  `referencia` varchar(50) DEFAULT NULL,
  `numero` varchar(8) DEFAULT NULL,
  `bairro` varchar(30) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `celular` varchar(14) DEFAULT NULL,
  `telefone` varchar(13) DEFAULT NULL,
  `dataCadastro` date NOT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE KEY `Cpf_UNIQUE` (`documento`),
  UNIQUE KEY `InscricaoEstadual_UNIQUE` (`inscricaoEstadual`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,1,'Mateus Leandro Chagas','Chagas','151.253.906-66',NULL,'32371-560','Contagem','Rua Rio Capibaribe','Casa Verde, Portão Branco','100A','Eldoradinho','mateusleandro2205@gmail.com','(31)98444-8086',NULL,'2021-10-30'),(4,0,'Mauro','Maurão','116.166.464-46',NULL,'32371-570','Contagem','Rua Rio Jaguaribe','Casa verde','100A','Eldoradinho','mauro@gmail.com','(31)98444-8086',NULL,'2021-11-17'),(5,0,'AVANCO INFORMATICA S.A',NULL,'42.790.097/0001-23','152030661.61-61','30140-000','Belo Horizonte','Avenida Brasil','CONJ: 01;','131','Santa Efigênia','adm@avancoinfo.com.br','(31)99999-9999','(31)3025-1188','2021-12-01');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orcamento`
--

DROP TABLE IF EXISTS `orcamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orcamento` (
  `idOrcamento` int NOT NULL AUTO_INCREMENT,
  `quantidadeProdutos` int NOT NULL,
  `totalMercadoriasBruto` double NOT NULL,
  `totalMercadoriasLiquido` double NOT NULL,
  `frete` double DEFAULT NULL,
  `descontoFinal` double DEFAULT NULL,
  `valorTotal` double DEFAULT NULL,
  `dataInclusao` datetime NOT NULL,
  PRIMARY KEY (`idOrcamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orcamento`
--

LOCK TABLES `orcamento` WRITE;
/*!40000 ALTER TABLE `orcamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `orcamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto` (
  `idProduto` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(50) NOT NULL,
  `PrCusto` double DEFAULT NULL,
  `prVenda` double DEFAULT NULL,
  `margem` double DEFAULT NULL,
  `prSugerido` double DEFAULT NULL,
  `margemPraticada` double DEFAULT NULL,
  `codSetor` int DEFAULT NULL,
  `unidadeVenda` enum('UN','CX','FD','PC','KG','MT','L') NOT NULL,
  `bloqueadoVenda` tinyint NOT NULL DEFAULT '0',
  `dataCadastro` date DEFAULT NULL,
  PRIMARY KEY (`idProduto`),
  KEY `codSetor_idx` (`codSetor`),
  CONSTRAINT `codSetor` FOREIGN KEY (`codSetor`) REFERENCES `setor` (`codSetor`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,'Teste fator',15.2,20,25,19,31.58,2,'MT',0,'2021-12-04'),(2,'Teste Novo Item',12.35,1.6,25.1,15.45,-87.04,27,'UN',0,'2021-12-04'),(3,'Teste Margem',0,150,10,0,100,1,'UN',0,'2021-12-05'),(4,'Teste novo item',0,0.15,0,0,100,1,'UN',0,'2021-12-05'),(5,'Teste inclusão',0,15.2,0,0,100,1,'UN',0,'2021-12-05');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto_orcamento`
--

DROP TABLE IF EXISTS `produto_orcamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto_orcamento` (
  `idVenda` int NOT NULL,
  `idProdutoOrcamento` int NOT NULL,
  `quantidade` double NOT NULL,
  `precoUnitario` double NOT NULL,
  `descontoProduto` double DEFAULT NULL,
  `totalProduto` double NOT NULL,
  KEY `idProduto_idx` (`idProdutoOrcamento`),
  KEY `Venda_idx` (`idVenda`),
  CONSTRAINT `Venda` FOREIGN KEY (`idVenda`) REFERENCES `orcamento` (`idOrcamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto_orcamento`
--

LOCK TABLES `produto_orcamento` WRITE;
/*!40000 ALTER TABLE `produto_orcamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `produto_orcamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setor`
--

DROP TABLE IF EXISTS `setor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setor` (
  `codSetor` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  PRIMARY KEY (`codSetor`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setor`
--

LOCK TABLES `setor` WRITE;
/*!40000 ALTER TABLE `setor` DISABLE KEYS */;
INSERT INTO `setor` VALUES (1,'Geral'),(2,'Betoneiras'),(25,'Parafusos'),(27,'Lixas'),(45,'Furadeiras'),(46,'cimentos');
/*!40000 ALTER TABLE `setor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-06  7:31:19
