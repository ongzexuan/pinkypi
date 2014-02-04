CREATE TABLE IF NOT EXISTS `#__formmenu_recordlist` (
`id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,

`created_by` INT(11)  NOT NULL ,
`category` VARCHAR(50)  NOT NULL ,
`title` VARCHAR(75)  NOT NULL ,
`description` TEXT NOT NULL ,
`award` VARCHAR(40)  NOT NULL ,
`year` YEAR(4)  NOT NULL ,
`score` VARCHAR(255)  NOT NULL ,
PRIMARY KEY (`id`)
) DEFAULT COLLATE=utf8_general_ci;

