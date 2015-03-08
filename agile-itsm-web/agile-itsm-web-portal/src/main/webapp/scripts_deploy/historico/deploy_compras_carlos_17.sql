alter table 'itemrequisicaoproduto' change column 'situacao' 'situacao' varchar(30) null default null  ;

create table 'historicoitemrequisicao' (
  'idhistorico' int(11) not null,
  'iditemrequisicao' int(11) not null,
  'idresponsavel' int(11) not null,
  'datahora' timestamp null default null,
  'situacao' varchar(30) not null,
  'complemento' text,
  primary key ('idhistorico'),
  key 'iditemrequisicao' ('iditemrequisicao'),
  key 'idresponsavel' ('idresponsavel'),
  constraint 'historicoitemrequisicao_ibfk_1' foreign key ('iditemrequisicao') references 'itemrequisicaoproduto' ('iditemrequisicaoproduto'),
  constraint 'historicoitemrequisicao_ibfk_2' foreign key ('idresponsavel') references 'empregados' ('idempregado')
) engine=innodb
