-- INÍCIO - MARIO HAYASAKI JUNIOR 30/12/2013

ALTER TABLE planomelhoria ALTER column criadopor type character varying(255);

ALTER TABLE planomelhoria ALTER column modificadopor type character varying(255);

-- FIM - MARIO HAYASAKI JUNIOR

-- INÍCIO - GILBERTO TAVARES DE FRANCO NERY 14/01/2014

ALTER TABLE rh_curriculo ADD naturalidade VARCHAR(45);

-- FIM - GILBERTO TAVARES DE FRANCO NERY

-- INÍCIO - THIAGO BORGES DA SILVA 16/01/2014

ALTER TABLE rh_requisicaopessoal ADD observacoes varchar(255);

-- FIM - THIAGO BORGES DA SILVA

-- INÍCIO - DAVID RODRIGUES 17/01/2014

ALTER TABLE rh_requisicaopessoal alter COLUMN observacoes TYPE varchar(500);

-- FIM - DAVID RODRIGUES
