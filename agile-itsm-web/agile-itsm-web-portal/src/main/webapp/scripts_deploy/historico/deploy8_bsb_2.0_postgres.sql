ALTER TABLE PROCESSAMENTOBATCH ALTER column DESCRICAO type varchar(256);

--Início rodrigo.oliveira
DROP TABLE impacto CASCADE;

CREATE TABLE impacto (
  idimpacto INTEGER NOT NULL, 
  nivelimpacto VARCHAR(100) NOT NULL, 
  siglaimpacto CHAR(2) DEFAULT NULL, 
  CONSTRAINT impacto_pkey PRIMARY KEY(idimpacto)
);

INSERT INTO impacto (idImpacto, nivelImpacto, siglaImpacto) VALUES ('1', 'Altíssimo', 'AL');
INSERT INTO impacto (idImpacto, nivelImpacto, siglaImpacto) VALUES ('2', 'Alto', 'A');
INSERT INTO impacto (idImpacto, nivelImpacto, siglaImpacto) VALUES ('3', 'Elevado', 'E');
INSERT INTO impacto (idImpacto, nivelImpacto, siglaImpacto) VALUES ('4', 'Médio', 'M');
INSERT INTO impacto (idImpacto, nivelImpacto, siglaImpacto) VALUES ('5', 'Baixo', 'B');


DROP TABLE urgencia CASCADE;

CREATE TABLE urgencia (
  idurgencia INTEGER NOT NULL, 
  nivelurgencia VARCHAR(100) NOT NULL, 
  siglaurgencia CHAR(2) DEFAULT NULL, 
  CONSTRAINT urgencia_pkey PRIMARY KEY(idurgencia)
);

INSERT INTO urgencia (idUrgencia, nivelUrgencia, siglaUrgencia) VALUES ('1', 'Crítica', 'C');
INSERT INTO urgencia (idUrgencia, nivelUrgencia, siglaUrgencia) VALUES ('2', 'Alta', 'A');
INSERT INTO urgencia (idUrgencia, nivelUrgencia, siglaUrgencia) VALUES ('3', 'Média', 'M');
INSERT INTO urgencia (idUrgencia, nivelUrgencia, siglaUrgencia) VALUES ('4', 'Baixa', 'B');


DROP TABLE matrizprioridade;

CREATE TABLE matrizprioridade (
  idmatrizprioridade INTEGER NOT NULL, 
  siglaimpacto CHAR(2) NOT NULL, 
  siglaurgencia CHAR(2) NOT NULL, 
  valorprioridade INTEGER NOT NULL, 
  idcontrato INTEGER, 
  deleted CHAR(1) DEFAULT NULL, 
  CONSTRAINT matrizprioridade_pkey PRIMARY KEY(idmatrizprioridade)
  );
  
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
   nometabela VARCHAR(255) NOT NULL,
   chavefinal VARCHAR(255) NOT NULL,
   chaveoriginal VARCHAR(255),
   origem VARCHAR(255),
   criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   ultatualiz TIMESTAMP DEFAULT to_timestamp('01-JAN-70 00:00:00', 'dd-MON-yy hh24:mi:ss'),
   CONSTRAINT tabfederacaodados_pkey PRIMARY KEY (nometabela, chavefinal)
);

--Fim rodrigo.oliveira
