alter table baseconhecimento add (conteudosemformatacao clob);
alter table historicobaseconhecimento add (conteudosemformatacao clob);

-- Indices na tabela servico ja criados no bd da SEFAZ.
create index index_servico_idcatservico on servico (idcategoriaservico);
create index index_servico_idsitservico on servico (idsituacaoservico);
create index index_servico_idtiposervico on servico (idtiposervico);
create index index_servico_nomeservico on servico (nomeservico);
