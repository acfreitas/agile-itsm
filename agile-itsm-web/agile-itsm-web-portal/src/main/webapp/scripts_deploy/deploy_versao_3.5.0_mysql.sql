set sql_safe_updates = 0;

-- Inicio - thiago.borges 29/05/2014
ALTER TABLE rh_requisicaopessoal CHANGE COLUMN beneficios beneficios TEXT NULL DEFAULT NULL;
-- FIM - thiago.borges 29/05/2014

-- Inicio - thiago.borges 16/05/2014
ALTER TABLE rh_atitudecandidato DROP FOREIGN KEY fk_reference_atitudorg;
ALTER TABLE rh_atitudecandidato ADD CONSTRAINT fk_reference_atitudorg
FOREIGN KEY (idatitudeorganizacional) REFERENCES rh_atitudeindividual (idatitudeindividual);
-- FIM - thiago.borges 16/05/2014

-- Inicio - thiago.borges 06/06/2014
ALTER TABLE rh_enderecocurriculo CHANGE COLUMN correspondencia principal CHAR(1) NOT NULL;
-- FIM - thiago.borges 06/06/2014

set sql_safe_updates = 1;