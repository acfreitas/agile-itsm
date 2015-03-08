set sql_safe_updates = 0;

-- INICIO - RODRIGO PECCI ACORSE - 10/09/2014

alter table ocorrenciaproblema change column tempogasto tempogasto int(11) null default null;
alter table ocorrencialiberacao change column tempogasto tempogasto int(11) null default null;
alter table ocorrenciamudanca change column tempogasto tempogasto int(11) null default null;

alter table justificativaparecer   add column datafim date default null;

alter table justificativaparecer add column datainicio date default null;

-- FIM - RODRIGO PECCI ACORSE - 10/09/2014

set sql_safe_updates = 1;