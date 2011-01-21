SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `categorizedWebs` DEFAULT CHARACTER SET utf8 ;
USE `categorizedWebs` ;

-- -----------------------------------------------------
-- Table `categorizedWebs`.`web_cat`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `categorizedWebs`.`web_cat` (
  `idweb_cat` INT NOT NULL ,
  `url` VARCHAR(2000) NULL ,
  `category` VARCHAR(300) NULL ,
  `score` MEDIUMBLOB  NULL ,
  PRIMARY KEY (`idweb_cat`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `categorizedWebs`.`web_cat_eva`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `categorizedWebs`.`web_cat_eva` (
  `idweb_cat_eva` INT NOT NULL ,
  `url` VARCHAR(2000) NULL ,
  `index` INT NULL ,
  `category` VARCHAR(300) NULL ,
  `score` LONGBLOB NULL ,
  PRIMARY KEY (`idweb_cat_eva`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
