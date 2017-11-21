
-- -----------------------------------------------------
-- Schema StudyWebDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema StudyWebDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `StudyWebDB` DEFAULT CHARACTER SET utf8 ;
USE `StudyWebDB` ;

-- -----------------------------------------------------
-- Table `StudyWebDB`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StudyWebDB`.`User` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Version` BIGINT NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `Password` VARCHAR(100) NOT NULL,
  `Vorname` VARCHAR(45) NOT NULL,
  `Nachname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `Email_idx` (`Email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `StudyWebDB`.`Fragebogen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StudyWebDB`.`Fragebogen` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Bezeichnung` VARCHAR(100) NOT NULL,
  `Version` BIGINT NOT NULL,
  `User_Id` BIGINT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Fragebogen_User1_idx` (`User_Id` ASC),
  CONSTRAINT `fk_Fragebogen_User1`
    FOREIGN KEY (`User_Id`)
    REFERENCES `StudyWebDB`.`User` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `StudyWebDB`.`Frage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StudyWebDB`.`Frage` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Version` BIGINT NOT NULL,
  `Fragebogen_Id` BIGINT NOT NULL,
  `Frage` VARCHAR(500) NOT NULL,
  `IsMultipleChoice` TINYINT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Frage_Fragebogen1_idx` (`Fragebogen_Id` ASC),
  CONSTRAINT `fk_Frage_Fragebogen1`
    FOREIGN KEY (`Fragebogen_Id`)
    REFERENCES `StudyWebDB`.`Fragebogen` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `StudyWebDB`.`Antwort`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StudyWebDB`.`Antwort` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Version` BIGINT NOT NULL,
  `Frage_Id` BIGINT NOT NULL,
  `Antwort` VARCHAR(500) NOT NULL,
  `IsCorrect` TINYINT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Antwort_Frage_idx` (`Frage_Id` ASC),
  CONSTRAINT `fk_Antwort_Frage`
    FOREIGN KEY (`Frage_Id`)
    REFERENCES `StudyWebDB`.`Frage` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `StudyWebDB`.`Berechtigt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StudyWebDB`.`Berechtigt` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Version` BIGINT NOT NULL,
  `User_Id` BIGINT NOT NULL,
  `Fragebogen_Id` BIGINT NOT NULL,
  `Darf_bearbeiten` TINYINT NOT NULL,
  INDEX `fk_Berechtigungen_User1_idx` (`User_Id` ASC),
  INDEX `fk_Berechtigungen_Fragebogen1_idx` (`Fragebogen_Id` ASC),
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_Berechtigungen_User1`
    FOREIGN KEY (`User_Id`)
    REFERENCES `StudyWebDB`.`User` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Berechtigungen_Fragebogen1`
    FOREIGN KEY (`Fragebogen_Id`)
    REFERENCES `StudyWebDB`.`Fragebogen` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `StudyWebDB`.`Beantwortet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StudyWebDB`.`Beantwortet` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Version` BIGINT NOT NULL,
  `Frage_Id` BIGINT NOT NULL,
  `User_Id` BIGINT NOT NULL,
  `Anzahl_richtig` INT NULL,
  `Anzahl_falsch` INT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Beantwortet_User1_idx` (`User_Id` ASC),
  CONSTRAINT `fk_Beantwortet_Frage1`
    FOREIGN KEY (`Frage_Id`)
    REFERENCES `StudyWebDB`.`Frage` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Beantwortet_User1`
    FOREIGN KEY (`User_Id`)
    REFERENCES `StudyWebDB`.`User` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

