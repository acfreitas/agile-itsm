
-- INICIO - RODRIGO PECCI ACORSE - 10/09/2014

alter table ocorrenciaproblema alter column tempogasto type int;
alter table ocorrencialiberacao alter column tempogasto type int;
alter table ocorrenciamudanca alter column tempogasto type int;
alter table ocorrenciasolicitacao alter column tempogasto type int;

alter table justificativaparecer   add column datafim date default null;

alter table justificativaparecer add column datainicio date default null;

-- FIM - RODRIGO PECCI ACORSE - 10/09/2014