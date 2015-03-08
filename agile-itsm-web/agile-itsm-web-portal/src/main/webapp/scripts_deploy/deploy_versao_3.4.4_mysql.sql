set sql_safe_updates = 0;

-- Inicio - thiago.borges 16/05/2014
alter table rh_formacaocurriculo change column descricao descricao varchar(1000) not null;
-- FIM - thiago.borges 16/05/2014

-- INICIO - david.silva 16/05/2014
alter table rh_atitudeindividual change column detalhe detalhe varchar(1000) not null;
alter table rh_atitudeindividual change column descricao descricao varchar(100) not null;
-- FIM - david.silva 16/05/2014

-- INICIO - david.silva 16/05/2014
alter table rh_curso change column detalhe detalhe varchar(1000) not null;
alter table rh_curso change column descricao descricao varchar(100) not null;
alter table rh_certificacao change column detalhe detalhe varchar(1000) not null;
alter table rh_certificacao change column descricao descricao varchar(100) not null;
alter table rh_experienciainformatica change column detalhe detalhe varchar(1000) not null;
alter table rh_experienciainformatica change column descricao descricao varchar(100) not null;
-- FIM - david.silva 16/05/2014

set sql_safe_updates = 1;