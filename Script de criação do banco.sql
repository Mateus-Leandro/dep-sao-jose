CREATE DATABASE  IF NOT EXISTS `banco_deposito` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
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
  `barras` varchar(14) NOT NULL,
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
INSERT INTO `barras_produto` VALUES (14,'15',0,'2021-10-02'),(10,'157',0,'2021-09-26'),(8,'158',0,'2021-09-24'),(10,'15810',0,'2021-09-24'),(10,'1589',1,'2021-09-24'),(8,'56',1,'2021-09-24'),(17,'6578',1,'2021-09-26'),(14,'65789',1,'2021-09-26'),(14,'89',0,'2021-09-26');
/*!40000 ALTER TABLE `barras_produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `idClientes` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `apelido` varchar(20) DEFAULT NULL,
  `cpf` varchar(14) DEFAULT NULL,
  `cep` varchar(9) DEFAULT NULL,
  `cidade` varchar(30) NOT NULL,
  `tipoLogradouro` varchar(15) NOT NULL,
  `descricaoLogradouro` varchar(50) NOT NULL,
  `referencia` varchar(50) DEFAULT NULL,
  `numero` varchar(8) DEFAULT NULL,
  `bairro` varchar(30) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `celular` varchar(14) DEFAULT NULL,
  `telefone` varchar(13) DEFAULT NULL,
  `dataCadastro` date NOT NULL,
  PRIMARY KEY (`idClientes`),
  UNIQUE KEY `Cpf_UNIQUE` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parcelas_venda`
--

DROP TABLE IF EXISTS `parcelas_venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parcelas_venda` (
  `idVenda` int NOT NULL,
  `valorParcela` double NOT NULL,
  `paga` tinyint NOT NULL,
  `numeroDaParcela` int NOT NULL,
  `formaPagamento` enum('Dinheiro','Cartao','Cheque','Transferencia','Pix','Outros') NOT NULL,
  `dataVencimento` date NOT NULL,
  KEY `fk_Parcelas_Vendas_Vendas1_idx` (`idVenda`),
  CONSTRAINT `fk_Parcelas_Vendas_Vendas1` FOREIGN KEY (`idVenda`) REFERENCES `vendas` (`idVenda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parcelas_venda`
--

LOCK TABLES `parcelas_venda` WRITE;
/*!40000 ALTER TABLE `parcelas_venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `parcelas_venda` ENABLE KEYS */;
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
  `preco` double DEFAULT NULL,
  `codSetor` int DEFAULT NULL,
  `unidadeVenda` enum('UN','CX','FD','PC','KG','MT','L') NOT NULL,
  `bloqueadoVenda` tinyint NOT NULL DEFAULT '0',
  `dataCadastro` date DEFAULT NULL,
  PRIMARY KEY (`idProduto`),
  KEY `codSetor_idx` (`codSetor`),
  CONSTRAINT `codSetor` FOREIGN KEY (`codSetor`) REFERENCES `setor` (`codSetor`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (8,'Parafusos grande',6.58,25,'UN',0,'2021-09-24'),(10,'Teste brras',15.3,2,'UN',0,'2021-09-24'),(13,'Pe de cabra',15.32,1,'UN',0,'2021-09-25'),(14,'Teste fator',15.8,30,'PC',0,'2021-09-26'),(17,'Novo produto',8.5,27,'UN',0,'2021-09-26');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos_venda`
--

DROP TABLE IF EXISTS `produtos_venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produtos_venda` (
  `idVenda` int NOT NULL,
  `produto` int NOT NULL,
  `quantidade` double NOT NULL,
  KEY `IdProdutos_Venda_idx` (`idVenda`),
  KEY `IdProdutos_produto_idx` (`produto`),
  CONSTRAINT `IdProdutos_produto` FOREIGN KEY (`produto`) REFERENCES `produto` (`idProduto`),
  CONSTRAINT `IdProdutos_Venda` FOREIGN KEY (`idVenda`) REFERENCES `vendas` (`idVenda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos_venda`
--

LOCK TABLES `produtos_venda` WRITE;
/*!40000 ALTER TABLE `produtos_venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `produtos_venda` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setor`
--

LOCK TABLES `setor` WRITE;
/*!40000 ALTER TABLE `setor` DISABLE KEYS */;
INSERT INTO `setor` VALUES (1,'Geral'),(2,'Betoneiras'),(25,'Parafusos'),(27,'Lixas'),(30,'Teste'),(44,'Teste 6');
/*!40000 ALTER TABLE `setor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendas`
--

DROP TABLE IF EXISTS `vendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendas` (
  `idVenda` int NOT NULL AUTO_INCREMENT,
  `idClientes` int NOT NULL,
  `valorProdutos` double NOT NULL,
  `valorFinal` double NOT NULL,
  `frete` double DEFAULT NULL,
  `desconto` double DEFAULT NULL,
  `acrescimo` double DEFAULT NULL,
  `tipoPagamento` enum('A Vista','A Prazo') NOT NULL COMMENT '"A vista ou a Prazo."',
  `numeroDeParcelas` int NOT NULL,
  `pago` tinyint NOT NULL,
  `observacao` varchar(150) DEFAULT NULL,
  `dataVenda` datetime NOT NULL,
  PRIMARY KEY (`idVenda`),
  KEY `fk_Vendas_Clientes1_idx` (`idClientes`),
  CONSTRAINT `fk_Vendas_Clientes1` FOREIGN KEY (`idClientes`) REFERENCES `clientes` (`idClientes`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendas`
--

LOCK TABLES `vendas` WRITE;
/*!40000 ALTER TABLE `vendas` DISABLE KEYS */;
/*!40000 ALTER TABLE `vendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'banco_deposito'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-08  6:18:07
