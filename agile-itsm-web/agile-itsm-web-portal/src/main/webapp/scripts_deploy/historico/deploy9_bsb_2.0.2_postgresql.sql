alter table baseconhecimento add column conteudosemformatacao text;
alter table historicobaseconhecimento add column conteudosemformatacao text;

create index index_servico_idcatservico on servico (idcategoriaservico);
create index index_servico_idsitservico on servico (idsituacaoservico);
create index index_servico_idtiposervico on servico (idtiposervico);
create index index_servico_nomeservico on servico (nomeservico);
