
-- INICIO - RODRIGO PECCI ACORSE - 09/11/2013

ALTER TABLE atividadeperiodica ALTER COLUMN criadopor VARCHAR(256);
ALTER TABLE atividadeperiodica ALTER COLUMN alteradopor VARCHAR(256);

-- FIM - RODRIGO PECCI ACORSE - 09/11/2013

-- INICIO - MARIO HAYASAKI JUNIOR - 21/03/2014

ALTER TABLE pedidoportal DROP CONSTRAINT rel_pedidosolicitacao_usuario;
EXEC sp_rename 'pedidoportal.idusuario', 'idempregado', 'COLUMN';
ALTER TABLE pedidoportal ADD CONSTRAINT rel_pedidosolicitacao_empregado FOREIGN KEY ( idempregado ) REFERENCES empregados ( idempregado )  ON DELETE NO ACTION ON UPDATE NO ACTION;

-- FIM - MARIO HAYASAKI JUNIOR - 21/03/2014

-- INICIO - BRUNO CARVALHO DE AQUINO - 28/03/2014

ALTER TABLE dicionario ADD personalizado CHAR(1) DEFAULT 'N';

-- FIM - BRUNO CARVALHO DE AQUINO - 28/03/2014

-- Inicio - MÁRIO HAYASAKI JÚNIOR - 14/07/2014

alter table empregados alter column telefone varchar(100);

-- Fim
