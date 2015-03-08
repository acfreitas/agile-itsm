-- Início Carlos Santos 19/08/2013

ALTER TABLE solicitacaoservico ADD codigoexterno varchar2(25);
create index idx_codigoexterno on solicitacaoservico (codigoexterno);

-- Fim Carlos Santos

-- Início Murilo Gabriel Rodrigues 19/08/2013

ALTER TABLE menu ADD mostrar varchar2(10);

-- Fim Murilo Gabriel Rodrigues
