
-- Inicio - thiago.borges 16/05/2014
ALTER TABLE rh_formacaocurriculo ALTER COLUMN descricao varchar(1000);
-- FIM - thiago.borges 16/05/2014

-- INICIO - david.silva 16/05/2014
ALTER TABLE rh_atitudeindividual ALTER COLUMN descricao varchar(100);
ALTER TABLE rh_atitudeindividual ALTER COLUMN detalhe varchar(1000);
-- FIM - david.silva 16/05/2014

-- INICIO - david.silva 16/05/2014
ALTER TABLE rh_curso ALTER COLUMN descricao varchar(100);
ALTER TABLE rh_curso ALTER COLUMN detalhe varchar(1000);
ALTER TABLE rh_certificacao ALTER COLUMN descricao varchar(100);
ALTER TABLE rh_certificacao ALTER COLUMN detalhe varchar(1000);
ALTER TABLE rh_experienciainformatica ALTER COLUMN descricao varchar(100);
ALTER TABLE rh_experienciainformatica ALTER COLUMN detalhe varchar(1000);
-- FIM - david.silva 16/05/2014
