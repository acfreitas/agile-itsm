-- Inicio - thiago.borges 16/05/2014
alter table rh_atitudecandidato drop constraint fk_reference_atitudorg;
ALTER TABLE rh_atitudecandidato ADD CONSTRAINT fk_reference_atitudorg
FOREIGN KEY (idatitudeorganizacional) REFERENCES rh_atitudeindividual (idatitudeindividual) ON DELETE NO ACTION ON UPDATE NO ACTION;
-- FIM - thiago.borges 16/05/2014

