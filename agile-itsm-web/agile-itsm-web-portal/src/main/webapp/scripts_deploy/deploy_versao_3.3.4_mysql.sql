set sql_safe_updates = 0;

-- Inicio Mario Hayasaki - 31/03/2014

ALTER TABLE categoriaocorrencia CHANGE COLUMN nome nome VARCHAR(255);

-- Fim Mario Hayasaki - 31/03/2014

-- INICIO - M�RIO HAYASAKI J�NIOR - 08/04/2014

alter table bpm_tipofluxo change column nomefluxo nomefluxo varchar(255);

-- FIM - M�RIO HAYASAKI J�NIOR - 08/04/2014

-- In�cio Thiago Matias 07/04/14

alter table aprovacaomudanca change column nomeempregado nomeempregado varchar(256) NULL;

-- Fim Thiago Matias

-- INICIO - FL�VIO J�NIOR - 1/04/2014

ALTER TABLE ocorrenciasolicitacao CHANGE COLUMN registradopor registradopor VARCHAR(256) NULL DEFAULT NULL;

-- FIM - FL�VIO J�NIOR - 1/04/2014

-- INICIO - RODRIGO PECCI ACORSE - 10/04/2014

ALTER TABLE contatosolicitacaoservico CHANGE COLUMN nomecontato nomecontato VARCHAR(256);

-- FIM - RODRIGO PECCI ACORSE - 10/04/2014

set sql_safe_updates = 1;
