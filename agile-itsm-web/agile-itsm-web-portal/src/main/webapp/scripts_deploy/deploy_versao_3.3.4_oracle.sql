-- Inicio Mario Hayasaki - 31/03/2014

ALTER TABLE categoriaocorrencia MODIFY ( nome varchar2(255));

-- Fim Mario Hayasaki - 31/03/2014

-- INICIO - M�RIO HAYASAKI J�NIOR - 08/04/2014

ALTER TABLE bpm_tipofluxo MODIFY (nomefluxo varchar2(255));

-- FIM - M�RIO HAYASAKI J�NIOR - 08/04/2014

-- Inicio Thiago Matias 07/04/14

alter table aprovacaomudanca (nomeempregado varchar2(256));

-- Fim Thiago Matias

-- INICIO - FL�VIO J�NIOR - 1/04/2014

ALTER TABLE OCORRENCIASOLICITACAO MODIFY (REGISTRADOPOR VARCHAR2(256 BYTE) );

-- FIM - FL�VIO J�NIOR - 1/04/2014

-- INICIO - RODRIGO PECCI ACORSE - 10/04/2014

ALTER TABLE contatosolicitacaoservico MODIFY (nomecontato VARCHAR2(256));

-- FIM - RODRIGO PECCI ACORSE - 10/04/2014