set sql_safe_updates = 0;

-- In�cio MARIO HAYASAKI JUNIOR 13/12/2013

update situacaoservico set idsituacaoservico = 999 where idsituacaoservico = -999;
update servico_hist set idsituacaoservico = 999 where idsituacaoservico = -999;

-- FIM - MARIO HAYASAKI JUNIOR

-- In�cio FLAVIO JUNIOR NEVES SANTANA SILVA 13/12/2013

ALTER TABLE empregados CHANGE COLUMN nome nome VARCHAR(255) NOT NULL  , CHANGE COLUMN nomeprocura nomeprocura VARCHAR(255) NULL;

-- FIM - FLAVIO JUNIOR NEVES SANTANA SILVA

set sql_safe_updates = 1;
