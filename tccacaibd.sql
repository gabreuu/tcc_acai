SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema tccacai
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tccacai` DEFAULT CHARACTER SET utf8 ;
USE `tccacai` ;

-- -----------------------------------------------------
-- Table `tccacai`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccacai`.`cliente` (
  `idCliente` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(128) NOT NULL,
  `cpf` VARCHAR(14) NULL,
  `endereco` VARCHAR(256) NULL,
  `telefone` VARCHAR(15) NULL,
  `status` INT(11) NOT NULL,
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tccacai`.`atendimento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccacai`.`atendimento` (
  `idAtendimento` INT(11) NOT NULL AUTO_INCREMENT,
  `tipoAtendimento` VARCHAR(24) NOT NULL,
  `tipoPagto` VARCHAR(24) NOT NULL,
  `status` INT(11) NOT NULL,
  PRIMARY KEY (`idAtendimento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tccacai`.`menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccacai`.`menu` (
  `idMenu` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `link` VARCHAR(128) NOT NULL,
  `exibir` INT(11) NOT NULL,
  `status` INT(11) NOT NULL,
  PRIMARY KEY (`idMenu`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tccacai`.`perfil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccacai`.`perfil` (
  `idPerfil` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `dataCadastro` DATE NOT NULL,
  `status` INT(11) NOT NULL,
  PRIMARY KEY (`idPerfil`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tccacai`.`produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccacai`.`produto` (
  `idProduto` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(128) NOT NULL,
  `descricao` VARCHAR(256) NULL DEFAULT NULL,
  `preco` DECIMAL(8,2) NOT NULL,
  `status` INT(11) NOT NULL,
  PRIMARY KEY (`idProduto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tccacai`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccacai`.`usuario` (
  `idUsuario` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(128) NOT NULL,
  `login` VARCHAR(24) NOT NULL,
  `senha` VARCHAR(128) NOT NULL,
  `status` INT(11) NOT NULL,
  `idPerfil` INT(11) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  INDEX `fk_usuario_perfil1_idx` (`idPerfil` ASC),
  CONSTRAINT `fk_usuario_perfil1`
    FOREIGN KEY (`idPerfil`)
    REFERENCES `tccacai`.`perfil` (`idPerfil`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tccacai`.`venda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccacai`.`venda` (
  `idVenda` INT(11) NOT NULL AUTO_INCREMENT,
  `dataVenda` DATE NOT NULL,
  `precoTotal` DECIMAL(8,2) NOT NULL,
  `status` INT(11) NOT NULL,
  `idCliente` INT(11) NOT NULL,
  `idAtendimento` INT(11) NOT NULL,
  `idUsuario` INT(11) NOT NULL,
  PRIMARY KEY (`idVenda`),
  INDEX `fk_venda_cliente1_idx` (`idCliente` ASC),
  INDEX `fk_venda_delivery1_idx` (`idAtendimento` ASC),
  INDEX `fk_venda_usuario1_idx` (`idUsuario` ASC),
  CONSTRAINT `fk_venda_cliente1`
    FOREIGN KEY (`idCliente`)
    REFERENCES `tccacai`.`cliente` (`idCliente`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_venda_delivery1`
    FOREIGN KEY (`idAtendimento`)
    REFERENCES `tccacai`.`atendimento` (`idAtendimento`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_venda_usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `tccacai`.`usuario` (`idUsuario`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tccacai`.`venda_produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccacai`.`venda_produto` (
  `idVenda` INT(11) NOT NULL,
  `idProduto` INT(11) NOT NULL,
  PRIMARY KEY (`idVenda`, `idProduto`),
  INDEX `fk_venda_has_produto_produto1_idx` (`idProduto` ASC),
  INDEX `fk_venda_has_produto_venda1_idx` (`idVenda` ASC),
  CONSTRAINT `fk_venda_has_produto_venda1`
    FOREIGN KEY (`idVenda`)
    REFERENCES `tccacai`.`venda` (`idVenda`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_venda_has_produto_produto1`
    FOREIGN KEY (`idProduto`)
    REFERENCES `tccacai`.`produto` (`idProduto`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tccacai`.`perfil_menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccacai`.`perfil_menu` (
  `idPerfil` INT(11) NOT NULL,
  `idMenu` INT(11) NOT NULL,
  PRIMARY KEY (`idPerfil`, `idMenu`),
  INDEX `fk_perfil_has_menu_menu1_idx` (`idMenu` ASC),
  INDEX `fk_perfil_has_menu_perfil1_idx` (`idPerfil` ASC),
  CONSTRAINT `fk_perfil_has_menu_perfil1`
    FOREIGN KEY (`idPerfil`)
    REFERENCES `tccacai`.`perfil` (`idPerfil`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_perfil_has_menu_menu1`
    FOREIGN KEY (`idMenu`)
    REFERENCES `tccacai`.`menu` (`idMenu`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
