-- Início Rodrigo Pecci Acorse 26/11/2013

ALTER TABLE acordonivelservico MODIFY ( criadopor VARCHAR2(255 BYTE) );
ALTER TABLE acordonivelservico MODIFY ( modificadopor VARCHAR2(255 BYTE) );

-- Fim Rodrigo Pecci Acorse


-- Início Thiago Fernandes Oliveira 26/11/2013

ALTER TABLE historicomudanca MODIFY registroexecucao VARCHAR(4000);

-- Fim Thiago Fernandes Oliveira