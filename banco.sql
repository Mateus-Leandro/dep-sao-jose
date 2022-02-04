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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,0,'Consumidor final',NULL,NULL,NULL,'32371-570','Contagem','Rua Rio Jaguaribe',NULL,'2000A','Eldoradinho',NULL,'(99)99999-9999',NULL,'2022-02-02');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracoes`
--

DROP TABLE IF EXISTS `configuracoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuracoes` (
  `nome_empresa` varchar(45) NOT NULL,
  `responsavel` varchar(45) DEFAULT NULL,
  `cnpj` varchar(18) DEFAULT NULL,
  `inscricao_estadual` varchar(15) DEFAULT NULL,
  `tel_fixo` varchar(13) DEFAULT NULL,
  `celular` varchar(14) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `endereco` varchar(63) DEFAULT NULL,
  `salva_parc_dif` varchar(9) NOT NULL,
  `alt_orc` varchar(9) NOT NULL,
  `gera_pdf` varchar(9) NOT NULL,
  `idConsumidor_final` int NOT NULL,
  KEY `Codigo Consumidor Final_idx` (`idConsumidor_final`),
  CONSTRAINT `Codigo Consumidor Final` FOREIGN KEY (`idConsumidor_final`) REFERENCES `clientes` (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracoes`
--

LOCK TABLES `configuracoes` WRITE;
/*!40000 ALTER TABLE `configuracoes` DISABLE KEYS */;
INSERT INTO `configuracoes` VALUES ('Depósito São José',NULL,NULL,NULL,NULL,'(31)98444-8086',NULL,'','SIM','SIM','PERGUNTAR',1);
/*!40000 ALTER TABLE `configuracoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forma_pagamento`
--

DROP TABLE IF EXISTS `forma_pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forma_pagamento` (
  `idFormaPagamento` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`idFormaPagamento`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forma_pagamento`
--

LOCK TABLES `forma_pagamento` WRITE;
/*!40000 ALTER TABLE `forma_pagamento` DISABLE KEYS */;
INSERT INTO `forma_pagamento` VALUES (1,'Dinheiro');
/*!40000 ALTER TABLE `forma_pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orcamento`
--

DROP TABLE IF EXISTS `orcamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orcamento` (
  `idOrcamento` int NOT NULL AUTO_INCREMENT,
  `idCliente` int NOT NULL,
  `quantidadeProdutos` int NOT NULL,
  `totalMercadoriasBruto` double NOT NULL,
  `totalMercadoriasLiquido` double NOT NULL,
  `frete` double DEFAULT NULL,
  `descontoFinal` double DEFAULT NULL,
  `valorTotal` double NOT NULL,
  `faturado` tinyint NOT NULL,
  `numeroParcelas` int NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `dataInclusao` date NOT NULL,
  PRIMARY KEY (`idOrcamento`),
  KEY `codigo cliente_idx` (`idCliente`),
  CONSTRAINT `codigo cliente` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orcamento`
--

LOCK TABLES `orcamento` WRITE;
/*!40000 ALTER TABLE `orcamento` DISABLE KEYS */;
INSERT INTO `orcamento` VALUES (1,1,2,3555.55,3055.55,150,550,2655.55,1,2,NULL,'2022-02-02'),(2,1,1,4.95,4.95,0,0,4.95,0,0,NULL,'2022-02-02');
/*!40000 ALTER TABLE `orcamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parcelas`
--

DROP TABLE IF EXISTS `parcelas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parcelas` (
  `idOrcamento` int NOT NULL,
  `valor` double NOT NULL,
  `idFormaPagamento` int DEFAULT NULL,
  `dataPagamento` date DEFAULT NULL,
  `dataVencimento` date NOT NULL,
  KEY `Numero Orcamento_idx` (`idOrcamento`),
  KEY `Forma de Pagamento_idx` (`idFormaPagamento`),
  CONSTRAINT `Forma de Pagamento` FOREIGN KEY (`idFormaPagamento`) REFERENCES `forma_pagamento` (`idFormaPagamento`),
  CONSTRAINT `Numero Orcamento` FOREIGN KEY (`idOrcamento`) REFERENCES `orcamento` (`idOrcamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parcelas`
--

LOCK TABLES `parcelas` WRITE;
/*!40000 ALTER TABLE `parcelas` DISABLE KEYS */;
INSERT INTO `parcelas` VALUES (1,1327.77,1,NULL,'2022-03-02'),(1,1327.78,1,NULL,'2022-04-02');
/*!40000 ALTER TABLE `parcelas` ENABLE KEYS */;
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
  `unidadeVenda` varchar(30) NOT NULL,
  `bloqueadoVenda` tinyint NOT NULL DEFAULT '0',
  `dataCadastro` date DEFAULT NULL,
  PRIMARY KEY (`idProduto`),
  KEY `codSetor_idx` (`codSetor`),
  CONSTRAINT `codSetor` FOREIGN KEY (`codSetor`) REFERENCES `setor` (`codSetor`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,'Betoneira HSW',500,3500,30,650,600,1,'UN',0,'2022-02-02'),(2,'Lixa',1.5,1.65,10,1.65,10,2,'UN',0,'2022-02-02'),(3,'Cimento Holcim',35,60,30,45.5,71.43,3,'PCT',0,'2022-02-02');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto_orcamento`
--

DROP TABLE IF EXISTS `produto_orcamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto_orcamento` (
  `idOrcamento` int NOT NULL,
  `idProdutoOrcamento` int NOT NULL,
  `quantidade` double NOT NULL,
  `fator` varchar(6) NOT NULL,
  `precoUnitario` double NOT NULL,
  `descontoProduto` double DEFAULT NULL,
  `totalProduto` double NOT NULL,
  KEY `idProduto_idx` (`idProdutoOrcamento`),
  KEY `Venda_idx` (`idOrcamento`),
  CONSTRAINT `Venda` FOREIGN KEY (`idOrcamento`) REFERENCES `orcamento` (`idOrcamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto_orcamento`
--

LOCK TABLES `produto_orcamento` WRITE;
/*!40000 ALTER TABLE `produto_orcamento` DISABLE KEYS */;
INSERT INTO `produto_orcamento` VALUES (1,3,1,'PCT',55.55,0,55.55),(1,1,1,'UN',3500,500,3000),(2,2,3,'UN',1.65,0,4.95);
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setor`
--

LOCK TABLES `setor` WRITE;
/*!40000 ALTER TABLE `setor` DISABLE KEYS */;
INSERT INTO `setor` VALUES (1,'Betoneiras'),(2,'Lixas'),(3,'Cimentos');
/*!40000 ALTER TABLE `setor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'banco_deposito'
--

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

-- Dump completed on 2022-02-03  6:17:39
