-- IN�CIO - MARIO HAYASAKI JUNIOR 30/12/2013

alter table planomelhoria alter column criadopor varchar(255);

alter table planomelhoria alter column modificadopor varchar(255);

-- FIM - MARIO HAYASAKI JUNIOR

-- IN�CIO - GILBERTO TAVARES DE FRANCO NERY 14/01/2014

alter table rh_curriculo add naturalidade varchar(45) NULL;

-- FIM - GILBERTO TAVARES DE FRANCO NERY

-- IN�CIO - THIAGO BORGES DA SILVA 16/01/2014

ALTER TABLE rh_requisicaopessoal ADD observacoes varchar(255);

-- FIM - THIAGO BORGES DA SILVA

-- IN�CIO - DAVID RODRIGUES 17/01/2014

ALTER TABLE rh_requisicaopessoal ALTER COLUMN observacoes varchar(500);

-- FIM - DAVID RODRIGUES