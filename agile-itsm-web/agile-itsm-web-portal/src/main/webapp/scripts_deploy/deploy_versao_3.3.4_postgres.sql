-- Inicio Mario Hayasaki - 31/03/2014

ALTER TABLE categoriaocorrencia ALTER COLUMN nome TYPE character varying(255);

-- Fim Mario Hayasaki - 31/03/2014

-- INICIO - MÁRIO HAYASAKI JÚNIOR - 08/04/2014

ALTER TABLE bpm_tipofluxo ALTER nomefluxo TYPE character varying(255);

-- FIM - MÁRIO HAYASAKI JÚNIOR - 08/04/2014

-- Início Thiago Matias 07/04/14

alter table aprovacaomudanca alter column nomeempregado type character varying(256);

-- Fim Thiago Matias

-- INICIO - FLÁVIO JÚNIOR - 1/04/2014

ALTER TABLE ocorrenciasolicitacao ALTER COLUMN registradopor TYPE character varying(256);

-- FIM - FLÁVIO JÚNIOR - 1/04/2014

-- INICIO - RODRIGO PECCI ACORSE - 10/04/2014

ALTER TABLE contatosolicitacaoservico ALTER COLUMN nomecontato TYPE character varying(256);

-- FIM - RODRIGO PECCI ACORSE - 10/04/2014