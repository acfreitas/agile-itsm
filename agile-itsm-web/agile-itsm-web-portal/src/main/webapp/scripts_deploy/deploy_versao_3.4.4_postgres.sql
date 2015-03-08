
-- Inicio - thiago.borges 16/05/2014
alter table rh_formacaocurriculo alter column descricao type varchar(1000);
-- FIM - thiago.borges 16/05/2014

-- INICIO - david.silva 16/05/2014
alter table rh_atitudeindividual alter column descricao type varchar(100);
alter table rh_atitudeindividual alter column detalhe type varchar(1000);
-- FIM - david.silva 16/05/2014

-- INICIO - david.silva 16/05/2014
alter table rh_curso alter column descricao type varchar(100);
alter table rh_curso alter column detalhe type varchar(1000);
alter table rh_certificacao alter column descricao type varchar(100);
alter table rh_certificacao alter column detalhe type varchar(1000);
alter table rh_experienciainformatica alter column descricao type varchar(100);
alter table rh_experienciainformatica alter column detalhe type varchar(1000);
-- FIM - david.silva 16/05/2014
