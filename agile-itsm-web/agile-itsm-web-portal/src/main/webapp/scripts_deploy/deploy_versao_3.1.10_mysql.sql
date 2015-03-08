set sql_safe_updates = 0;

-- INÍCIO - MARIO HAYASAKI JUNIOR 30/12/2013

ALTER TABLE planomelhoria CHANGE COLUMN criadopor criadopor VARCHAR(255);

ALTER TABLE planomelhoria CHANGE COLUMN modificadopor modificadopor VARCHAR(255);

-- FIM - MARIO HAYASAKI JUNIOR

-- INÍCIO - GILBERTO TAVARES DE FRANCO NERY 14/01/2014

ALTER TABLE rh_curriculo ADD COLUMN naturalidade VARCHAR(45);

-- FIM - GILBERTO TAVARES DE FRANCO NERY

-- INÍCIO - THIAGO BORGES DA SILVA 16/01/2014

ALTER TABLE rh_requisicaopessoal ADD observacoes varchar(255);

-- FIM - THIAGO BORGES DA SILVA

-- INÍCIO - DAVID RODRIGUES 17/01/2014

ALTER TABLE rh_requisicaopessoal change observacoes observacoes varchar(500);

-- FIM - DAVID RODRIGUES

set sql_safe_updates = 1;
