-- INICIO - RODRIGO PECCI ACORSE - 09/11/2013

ALTER TABLE ATIVIDADEPERIODICA MODIFY (CRIADOPOR VARCHAR2(256));
ALTER TABLE ATIVIDADEPERIODICA MODIFY (ALTERADOPOR VARCHAR2(256));

-- FIM - RODRIGO PECCI ACORSE - 09/11/2013

-- INICIO - MARIO HAYASAKI JUNIOR - 21/03/2014

ALTER TABLE pedidoportal DROP CONSTRAINT PEDIDOPORTAL_USUARIO_FK1;
ALTER TABLE pedidoportal rename column idusuario to idempregado;
ALTER TABLE pedidoportal ADD CONSTRAINT PEDIDOPORTAL_EMPREGADO_FK1 FOREIGN KEY ( idempregado ) REFERENCES empregados ( idempregado )  ENABLE;

-- FIM - MARIO HAYASAKI JUNIOR - 21/03/2014

-- INICIO - BRUNO CARVALHO DE AQUINO - 28/03/2014

ALTER TABLE dicionario ADD personalizado CHAR(1) DEFAULT 'N';

-- FIM - BRUNO CARVALHO DE AQUINO - 28/03/2014

-- Inicio - M�RIO HAYASAKI J�NIOR - 14/07/2014
alter table empregados modify telefone varchar(100);
-- Fim