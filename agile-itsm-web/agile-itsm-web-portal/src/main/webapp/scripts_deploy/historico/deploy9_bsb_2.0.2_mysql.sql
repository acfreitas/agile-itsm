set sql_safe_updates = 0;

alter table baseconhecimento add column conteudosemformatacao text;
alter table historicobaseconhecimento add column conteudosemformatacao text;

create index index_servico_idcatservico on servico (idcategoriaservico);
create index index_servico_idsitservico on servico (idsituacaoservico);
create index index_servico_idtiposervico on servico (idtiposervico);
-- O MySQL nao indexa todo um campo texto, portanto foi criado um indice para os primeiros 255 caracteres (varchar).
create index index_servico_nomeservico on servico (nomeservico(255) );

set sql_safe_updates = 1;