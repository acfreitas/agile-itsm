-- IN�CIO - MARIO HAYASAKI JUNIOR 30/12/2013

ALTER TABLE PLANOMELHORIA MODIFY (CRIADOPOR VARCHAR2(255 CHAR) );

ALTER TABLE PLANOMELHORIA MODIFY (MODIFICADOPOR VARCHAR2(255 CHAR) );

-- FIM - MARIO HAYASAKI JUNIOR

-- IN�CIO - GILBERTO TAVARES DE FRANCO NERY 14/01/2014

alter table rh_curriculo add naturalidade varchar2(45);

-- FIM - GILBERTO TAVARES DE FRANCO NERY

-- IN�CIO - THIAGO BORGES DA SILVA 16/01/2014

ALTER TABLE rh_requisicaopessoal ADD observacoes varchar(255);

-- FIM - THIAGO BORGES DA SILVA

-- IN�CIO - DAVID RODRIGUES 17/01/2014

ALTER TABLE rh_requisicaopessoal MODIFY observacoes varchar2(500);

-- FIM - DAVID RODRIGUES