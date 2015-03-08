-- Início MARIO HAYASAKI JUNIOR 13/12/2013

update situacaoservico set idsituacaoservico = 999 where idsituacaoservico = -999;
update servico_hist set idsituacaoservico = 999 where idsituacaoservico = -999;

-- FIM - MARIO HAYASAKI JUNIOR

-- Início FLAVIO JUNIOR NEVES SANTANA SILVA 13/12/2013

ALTER TABLE EMPREGADOS  MODIFY (NOME VARCHAR2(255 CHAR) );
ALTER TABLE EMPREGADOS  MODIFY (NOMEPROCURA VARCHAR2(255 CHAR) );

-- FIM - FLAVIO JUNIOR NEVES SANTANA SILVA