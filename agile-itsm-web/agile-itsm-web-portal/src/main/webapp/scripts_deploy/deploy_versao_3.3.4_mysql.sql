set sql_safe_updates = 0;

-- Inicio Mario Hayasaki - 31/03/2014

ALTER TABLE categoriaocorrencia CHANGE COLUMN nome nome VARCHAR(255);

-- Fim Mario Hayasaki - 31/03/2014

-- INICIO - MÁRIO HAYASAKI JÚNIOR - 08/04/2014

alter table bpm_tipofluxo change column nomefluxo nomefluxo varchar(255);

-- FIM - MÁRIO HAYASAKI JÚNIOR - 08/04/2014

-- Início Thiago Matias 07/04/14

alter table aprovacaomudanca change column nomeempregado nomeempregado varchar(256) NULL;

-- Fim Thiago Matias

-- INICIO - FLÁVIO JÚNIOR - 1/04/2014

ALTER TABLE ocorrenciasolicitacao CHANGE COLUMN registradopor registradopor VARCHAR(256) NULL DEFAULT NULL;

-- FIM - FLÁVIO JÚNIOR - 1/04/2014

-- INICIO - RODRIGO PECCI ACORSE - 10/04/2014

ALTER TABLE contatosolicitacaoservico CHANGE COLUMN nomecontato nomecontato VARCHAR(256);

-- FIM - RODRIGO PECCI ACORSE - 10/04/2014

set sql_safe_updates = 1;
