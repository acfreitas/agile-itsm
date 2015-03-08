-- Início Carlos Santos 19/08/2013

ALTER TABLE solicitacaoservico ADD codigoexterno varchar(25);
create index idx_codigoexterno on solicitacaoservico (codigoexterno);

-- Fim Carlos Santos

-- Início Murilo Gabriel Rodrigues 19/08/2013

ALTER TABLE menu ADD mostrar varchar(10);

-- Fim Murilo Gabriel Rodrigues
