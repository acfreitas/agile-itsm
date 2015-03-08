-- Início MARIO HAYASAKI JUNIOR 13/12/2013

update situacaoservico set idsituacaoservico = 999 where idsituacaoservico = -999;
update servico_hist set idsituacaoservico = 999 where idsituacaoservico = -999;

-- FIM - MARIO HAYASAKI JUNIOR

-- Início FLAVIO JUNIOR NEVES SANTANA SILVA 13/12/2013

ALTER TABLE empregados ALTER column nome type character varying(255);
ALTER TABLE empregados ALTER column nomeprocura type character varying(255);

-- FIM - FLAVIO JUNIOR NEVES SANTANA SILVA