-- Início MARIO HAYASAKI JUNIOR 13/12/2013

update situacaoservico set idsituacaoservico = 999 where idsituacaoservico = -999;
update servico_hist set idsituacaoservico = 999 where idsituacaoservico = -999;

-- FIM - MARIO HAYASAKI JUNIOR

-- Início FLAVIO JUNIOR NEVES SANTANA SILVA 13/12/2013

alter table empregados alter column nome varchar(255);
alter table empregados alter column nomeprocura varchar(255);

-- FIM - FLAVIO JUNIOR NEVES SANTANA SILVA