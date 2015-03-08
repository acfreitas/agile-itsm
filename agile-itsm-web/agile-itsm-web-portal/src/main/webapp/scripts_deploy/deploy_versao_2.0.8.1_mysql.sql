set sql_safe_updates = 0;

-- MySQL

-- Início rodrigo.oliveira - 02/07/13

ALTER TABLE atividadesservicocontrato CHANGE COLUMN complexidade complexidade CHAR(1) NULL DEFAULT NULL;

ALTER TABLE atividadesservicocontrato CHANGE COLUMN periodo periodo CHAR(1) NULL DEFAULT NULL;

-- Fim rodrigo.oliveira

set sql_safe_updates = 1;

