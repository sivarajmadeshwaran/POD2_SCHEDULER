-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema scheduler
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema scheduler
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `scheduler` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema new_schema1
-- -----------------------------------------------------
USE `scheduler` ;

-- -----------------------------------------------------
-- Table `scheduler`.`dc_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler`.`dc_type` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type_description` VARCHAR(45) NOT NULL,
  `created_timestamp` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`dc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler`.`dc` (
  `dc_nbr` INT NOT NULL,
  `city` VARCHAR(45) NULL,
  `dc_type` INT UNSIGNED NOT NULL,
  `created_timestamp` DATETIME NOT NULL,
  `last_updated_timestamp` DATETIME NOT NULL,
  PRIMARY KEY (`dc_nbr`),
  INDEX `FK_dc_type_idx` (`dc_type` ASC) VISIBLE,
  CONSTRAINT `FK_dc_type`
    FOREIGN KEY (`dc_type`)
    REFERENCES `scheduler`.`dc_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`dc_slot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler`.`dc_slot` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `dc_nbr` INT NOT NULL,
  `booking_slot` VARCHAR(45) NOT NULL,
  `created_timestamp` DATETIME NOT NULL,
  `last_updated_timestamp` DATETIME NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  PRIMARY KEY (`dc_nbr`, `booking_slot`),
  CONSTRAINT `FK_dc_Reference`
    FOREIGN KEY (`id`)
    REFERENCES `scheduler`.`dc` (`dc_nbr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`vendor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler`.`vendor` (
  `id` INT UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `phone_nbr` INT UNSIGNED NULL,
  `email` VARCHAR(45) NULL,
  `address` VARCHAR(100) NULL,
  `created_timestamp` DATETIME NOT NULL,
  `last_updated_timestamp` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`truck_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler`.`truck_type` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type_description` VARCHAR(75) NOT NULL,
  `created_timestamp` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`truck`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler`.`truck` (
  `truck_nbr` INT UNSIGNED NOT NULL,
  `truck_type` INT UNSIGNED NOT NULL,
  `created_timestamp` DATETIME NOT NULL,
  `last_updated_timestamp` DATETIME NOT NULL,
  PRIMARY KEY (`truck_nbr`),
  INDEX `FK_truck_type_idx` (`truck_type` ASC) VISIBLE,
  CONSTRAINT `FK_truck_type`
    FOREIGN KEY (`truck_type`)
    REFERENCES `scheduler`.`truck_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`appointment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler`.`appointment` (
  `appointment_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `booking_slot` INT NOT NULL,
  `appt_date` DATE NOT NULL,
  `dc_nbr` INT NOT NULL,
  `created_timestamp` DATETIME NOT NULL,
  `last_updated_timestamp` DATETIME NOT NULL,
  `truck_nbr` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`appointment_id`),
  INDEX `FK_DC_Reference_idx` (`dc_nbr` ASC) VISIBLE,
  INDEX `FK_DC_Slot_idx` (`booking_slot` ASC) VISIBLE,
  INDEX `FK_TruckNbr_idx` (`truck_nbr` ASC) VISIBLE,
  CONSTRAINT `FK_DCNbr`
    FOREIGN KEY (`dc_nbr`)
    REFERENCES `scheduler`.`dc` (`dc_nbr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_DC_Slot`
    FOREIGN KEY (`booking_slot`)
    REFERENCES `scheduler`.`dc_slot` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_TruckNbr`
    FOREIGN KEY (`truck_nbr`)
    REFERENCES `scheduler`.`truck` (`truck_nbr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`purchase_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler`.`purchase_order` (
  `po_nbr` INT UNSIGNED NOT NULL,
  `vendor_nbr` INT UNSIGNED NULL,
  `po_date` DATE NULL,
  `address` VARCHAR(45) NULL,
  `created_timestamp` DATETIME NOT NULL,
  `last_updated_timestamp` DATETIME NOT NULL,
  PRIMARY KEY (`po_nbr`),
  INDEX `FK_vendor_idx` (`vendor_nbr` ASC) VISIBLE,
  CONSTRAINT `FK_vendor`
    FOREIGN KEY (`vendor_nbr`)
    REFERENCES `scheduler`.`vendor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`appointment_po`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler`.`appointment_po` (
  `appointment_id` INT UNSIGNED NOT NULL,
  `po_nbr` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`appointment_id`, `po_nbr`),
  INDEX `FK_PurchaseOrder_idx` (`po_nbr` ASC) VISIBLE,
  CONSTRAINT `FK_Appointment`
    FOREIGN KEY (`appointment_id`)
    REFERENCES `scheduler`.`appointment` (`appointment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_PurchaseOrder`
    FOREIGN KEY (`po_nbr`)
    REFERENCES `scheduler`.`purchase_order` (`po_nbr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduler`.`purchase_order_line`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler`.`purchase_order_line` (
  `po_nbr` INT UNSIGNED NOT NULL,
  `po_line_nbr` INT UNSIGNED NOT NULL,
  `upc_nbr` INT UNSIGNED NOT NULL,
  `item_description` VARCHAR(100) NULL,
  `ordered_qty` INT UNSIGNED NOT NULL,
  `created_timestamp` DATETIME NOT NULL,
  `last_updated_timestamp` DATETIME NOT NULL,
  PRIMARY KEY (`po_nbr`, `po_line_nbr`),
  CONSTRAINT `FK_PurchaseOrder`
    FOREIGN KEY (`po_nbr`)
    REFERENCES `scheduler`.`purchase_order` (`po_nbr`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
