-- MySQL Script generated by MySQL Workbench
-- Tue Sep 28 07:38:25 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema banco_deposito
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema banco_deposito
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `banco_deposito` DEFAULT CHARACTER SET utf8 ;
USE `banco_deposito` ;

-- -----------------------------------------------------
-- Table `banco_deposito`.`setor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `banco_deposito`.`setor` (
  `codSetor` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`codSetor`))
ENGINE = InnoDB
AUTO_INCREMENT = 44
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `banco_deposito`.`produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `banco_deposito`.`produto` (
  `idProduto` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(50) NOT NULL,
  `preco` DOUBLE NULL DEFAULT NULL,
  `codSetor` INT NULL DEFAULT NULL,
  `unidadeVenda` ENUM('UN', 'CX', 'FD', 'PC', 'KG', 'MT', 'L') NOT NULL,
  `bloqueadoVenda` TINYINT NOT NULL DEFAULT '0',
  `dataCadastro` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`idProduto`),
  INDEX `codSetor_idx` (`codSetor` ASC) VISIBLE,
  CONSTRAINT `codSetor`
    FOREIGN KEY (`codSetor`)
    REFERENCES `banco_deposito`.`setor` (`codSetor`))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `banco_deposito`.`barras_produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `banco_deposito`.`barras_produto` (
  `idProduto` INT NOT NULL,
  `barras` VARCHAR(14) NOT NULL,
  `principal` TINYINT NULL DEFAULT NULL,
  `dt_vinculacao` DATE NULL DEFAULT NULL,
  UNIQUE INDEX `Barras_UNIQUE` (`barras` ASC) VISIBLE,
  INDEX `idProduto_idx` (`idProduto` ASC) VISIBLE,
  CONSTRAINT `idProduto`
    FOREIGN KEY (`idProduto`)
    REFERENCES `banco_deposito`.`produto` (`idProduto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `banco_deposito`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `banco_deposito`.`clientes` (
  `idClientes` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `apelido` VARCHAR(20) NULL DEFAULT NULL,
  `cpf` VARCHAR(14) NULL DEFAULT NULL,
  `cep` VARCHAR(9) NULL DEFAULT NULL,
  `município` VARCHAR(30) NOT NULL,
  `tipoLogradouro` ENUM('Rua', 'Avenida', 'Viela', 'Travessa', 'Sitio', 'Quadra', 'Outro') NOT NULL,
  `descricaoLogradouro` VARCHAR(50) NOT NULL,
  `referencia` VARCHAR(60) NULL DEFAULT NULL,
  `numero` VARCHAR(8) NULL DEFAULT NULL,
  `bairro` VARCHAR(30) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `celular` VARCHAR(14) NULL DEFAULT NULL,
  `telefone` VARCHAR(13) NULL DEFAULT NULL,
  `dataCadastro` DATE NOT NULL,
  PRIMARY KEY (`idClientes`),
  UNIQUE INDEX `Cpf_UNIQUE` (`cpf` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `banco_deposito`.`vendas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `banco_deposito`.`vendas` (
  `idVenda` INT NOT NULL AUTO_INCREMENT,
  `idClientes` INT NOT NULL,
  `valorProdutos` DOUBLE NOT NULL,
  `valorFinal` DOUBLE NOT NULL,
  `frete` DOUBLE NULL DEFAULT NULL,
  `desconto` DOUBLE NULL DEFAULT NULL,
  `acrescimo` DOUBLE NULL DEFAULT NULL,
  `tipoPagamento` ENUM('A Vista', 'A Prazo') NOT NULL COMMENT '\"A vista ou a Prazo.\"',
  `numeroDeParcelas` INT NOT NULL,
  `pago` TINYINT NOT NULL,
  `observacao` VARCHAR(150) NULL DEFAULT NULL,
  `dataVenda` DATETIME NOT NULL,
  PRIMARY KEY (`idVenda`),
  INDEX `fk_Vendas_Clientes1_idx` (`idClientes` ASC) VISIBLE,
  CONSTRAINT `fk_Vendas_Clientes1`
    FOREIGN KEY (`idClientes`)
    REFERENCES `banco_deposito`.`clientes` (`idClientes`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `banco_deposito`.`parcelas_venda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `banco_deposito`.`parcelas_venda` (
  `idVenda` INT NOT NULL,
  `valorParcela` DOUBLE NOT NULL,
  `paga` TINYINT NOT NULL,
  `numeroDaParcela` INT NOT NULL,
  `formaPagamento` ENUM('Dinheiro', 'Cartao', 'Cheque', 'Transferencia', 'Pix', 'Outros') NOT NULL,
  `dataVencimento` DATE NOT NULL,
  INDEX `fk_Parcelas_Vendas_Vendas1_idx` (`idVenda` ASC) VISIBLE,
  CONSTRAINT `fk_Parcelas_Vendas_Vendas1`
    FOREIGN KEY (`idVenda`)
    REFERENCES `banco_deposito`.`vendas` (`idVenda`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `banco_deposito`.`produtos_venda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `banco_deposito`.`produtos_venda` (
  `idVenda` INT NOT NULL,
  `produto` INT NOT NULL,
  `quantidade` DOUBLE NOT NULL,
  INDEX `IdProdutos_Venda_idx` (`idVenda` ASC) VISIBLE,
  INDEX `IdProdutos_produto_idx` (`produto` ASC) VISIBLE,
  CONSTRAINT `IdProdutos_produto`
    FOREIGN KEY (`produto`)
    REFERENCES `banco_deposito`.`produto` (`idProduto`),
  CONSTRAINT `IdProdutos_Venda`
    FOREIGN KEY (`idVenda`)
    REFERENCES `banco_deposito`.`vendas` (`idVenda`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
