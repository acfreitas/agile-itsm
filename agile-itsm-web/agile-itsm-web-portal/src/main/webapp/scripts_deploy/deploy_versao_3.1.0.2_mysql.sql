set sql_safe_updates = 0;

-- Início Rodrigo Pecci Acorse 26/11/2013

ALTER TABLE acordonivelservico MODIFY criadoPor VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;
ALTER TABLE acordonivelservico MODIFY modificadoPor VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;

-- Fim Rodrigo Pecci Acorse

-- Início Thiago Fernandes Oliveira 26/11/2013

ALTER TABLE historicomudanca MODIFY registroexecucao LONGTEXT;

-- Fim Thiago Fernandes Oliveira

set sql_safe_updates = 1;
