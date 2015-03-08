-- Inicio - thiago.borges 16/05/2014
ALTER TABLE rh_formacaocurriculo MODIFY(descricao VARCHAR2(1000));
-- FIM - thiago.borges 16/05/2014

-- INICIO - david.silva 16/05/2014
ALTER TABLE rh_atitudeindividual MODIFY(detalhe VARCHAR2(1000));
-- FIM - david.silva 16/05/2014

-- INICIO - david.silva 16/05/2014
ALTER TABLE rh_curso MODIFY(detalhe VARCHAR2(1000));
ALTER TABLE rh_certificacao MODIFY(detalhe VARCHAR2(1000));
ALTER TABLE rh_experienciainformatica MODIFY(detalhe VARCHAR2(1000));
-- FIM - david.silva 16/05/2014