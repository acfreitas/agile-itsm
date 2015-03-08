set sql_safe_updates = 0;

ALTER TABLE processamentobatch CHANGE descricao descricao varchar(256) null; 


--Início rodrigo.oliveira
DROP TABLE IF EXISTS impacto;

CREATE TABLE impacto (
	idImpacto INT(11) NOT NULL,
	nivelImpacto VARCHAR(100) NOT NULL,
	siglaImpacto char(2) DEFAULT NULL,
	PRIMARY KEY (idImpacto)
)ENGINE = InnoDB;

INSERT INTO impacto (idImpacto, nivelImpacto, siglaImpacto) VALUES ('1', 'Altíssimo', 'AL');
INSERT INTO impacto (idImpacto, nivelImpacto, siglaImpacto) VALUES ('2', 'Alto', 'A');
INSERT INTO impacto (idImpacto, nivelImpacto, siglaImpacto) VALUES ('3', 'Elevado', 'E');
INSERT INTO impacto (idImpacto, nivelImpacto, siglaImpacto) VALUES ('4', 'Médio', 'M');
INSERT INTO impacto (idImpacto, nivelImpacto, siglaImpacto) VALUES ('5', 'Baixo', 'B');

DROP TABLE IF EXISTS urgencia; 

CREATE TABLE urgencia (
	idUrgencia INT(11) NOT NULL,
	nivelUrgencia VARCHAR(100) NOT NULL,
	siglaUrgencia char(2) DEFAULT NULL,
	PRIMARY KEY (idUrgencia)
)ENGINE = InnoDB;

INSERT INTO urgencia (idUrgencia, nivelUrgencia, siglaUrgencia) VALUES ('1', 'Crítica', 'C');
INSERT INTO urgencia (idUrgencia, nivelUrgencia, siglaUrgencia) VALUES ('2', 'Alta', 'A');
INSERT INTO urgencia (idUrgencia, nivelUrgencia, siglaUrgencia) VALUES ('3', 'Média', 'M');
INSERT INTO urgencia (idUrgencia, nivelUrgencia, siglaUrgencia) VALUES ('4', 'Baixa', 'B');

DROP TABLE IF EXISTS matrizprioridade;  

CREATE TABLE matrizprioridade (
	idMatrizPrioridade int(11) NOT NULL,
  	siglaImpacto char(2) NOT NULL,
  	siglaUrgencia char(2) NOT NULL,
  	valorPrioridade int(11) NOT NULL,
  	idcontrato int(11) DEFAULT NULL,
  	deleted char(1) DEFAULT NULL,
  	PRIMARY KEY (idMatrizPrioridade)
) ENGINE=InnoDB;

INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (1,'AL','B',2,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (2,'AL','M',2,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (3,'AL','A',1,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (4,'AL','C',1,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (5,'A','B',3,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (6,'A','M',2,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (7,'A','A',2,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (8,'A','C',1,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (9,'E','B',3,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (10,'E','M',3,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (11,'E','A',2,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (12,'E','C',2,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (13,'M','B',4,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (14,'M','M',3,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (15,'M','A',3,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (16,'M','C',2,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (17,'B','B',5,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (18,'B','M',4,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (19,'B','A',3,NULL,'');
INSERT INTO matrizprioridade (idMatrizPrioridade,siglaImpacto,siglaUrgencia,valorPrioridade,idcontrato,deleted) 
VALUES (20,'B','C',3,NULL,'');

CREATE TABLE tabfederacaodados (
  nometabela varchar(255) NOT NULL,
  chavefinal varchar(255) NOT NULL,
  chaveoriginal varchar(255) NOT NULL,
  origem varchar(255) NOT NULL,
  criacao timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  ultatualiz timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (nometabela,chavefinal)  
) ENGINE=InnoDB;

ALTER TABLE logdados CHANGE COLUMN dados dados TEXT NULL DEFAULT NULL;

set sql_safe_updates = 1;
--Fim rodrigo.oliveira
