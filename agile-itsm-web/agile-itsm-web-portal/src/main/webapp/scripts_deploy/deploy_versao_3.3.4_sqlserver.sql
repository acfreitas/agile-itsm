-- Inicio Mario Hayasaki - 31/03/2014

ALTER TABLE categoriaocorrencia ALTER COLUMN nome VARCHAR(255);

-- Fim Mario Hayasaki - 31/03/2014

-- INICIO - M�RIO HAYASAKI J�NIOR - 08/04/2014

ALTER TABLE bpm_tipofluxo ALTER COLUMN nomefluxo varchar(255);

-- FIM - M�RIO HAYASAKI J�NIOR - 08/04/2014

-- In�cio Thiago Matias 07/04/14

alter table aprovacaomudanca alter column nomeempregado varchar(256) NULL;

-- Fim Thiago Matias

-- INICIO - EDU RODRIGUES BRAZ - 01/04/2014

ALTER TABLE tipoitemconfiguracao ALTER COLUMN idtipoitemconfiguracao int NOT NULL;

-- FIM - EDU RODRIGUES BRAZ - 01/04/2014

-- INICIO - FL�VIO J�NIOR - 1/04/2014

ALTER TABLE ocorrenciasolicitacao ALTER COLUMN registradopor varchar(256);

-- FIM - FL�VIO J�NIOR - 1/04/2014

-- INICIO - RODRIGO PECCI ACORSE - 10/04/2014

ALTER TABLE contatosolicitacaoservico ALTER COLUMN nomecontato VARCHAR(256);

-- FIM - RODRIGO PECCI ACORSE - 10/04/2014