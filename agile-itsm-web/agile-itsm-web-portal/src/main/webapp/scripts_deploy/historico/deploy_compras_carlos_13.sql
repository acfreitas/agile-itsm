ALTER TABLE `endereco` CHANGE COLUMN `logradouro` `logradouro` VARCHAR(200) NULL  ;

ALTER TABLE `avaliacaoreferenciafornecedor` DROP FOREIGN KEY `fk_reference_682` ;

ALTER TABLE avaliacaoreferenciafornecedor
    ADD CONSTRAINT fk_reference_682 FOREIGN KEY (idempregado) REFERENCES empregados (idempregado);

