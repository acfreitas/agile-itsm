-- Inicio - thiago.borges 29/05/2014
ALTER TABLE rh_requisicaopessoal MODIFY(beneficios TEXT);
-- FIM - thiago.borges 29/05/2014

-- Inicio - thiago.borges 16/05/2014
alter table rh_atitudecandidato drop constraint fk_reference_atitudorg;
ALTER TABLE rh_atitudecandidato ADD CONSTRAINT fk_reference_atitudorg 
FOREIGN KEY (idatitudeorganizacional) REFERENCES rh_atitudeindividual (idatitudeindividual);
-- FIM - thiago.borges 16/05/2014

-- Inicio - thiago.borges 06/06/2014
ALTER TABLE rh_enderecocurriculo RENAME COLUMN correspondencia TO principal;
-- FIM - thiago.borges 06/06/2014