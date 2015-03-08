-- INICIO - valdoilo.damasceno - 13.06.2014

create table monitoramentoativos (
  idmonitoramentoativos int not null,
  idtipoitemconfiguracao int not null,
  titulo varchar(255) not null,
  descricao text null,
  tiporegra char null,
  enviaremail char null,
  criarproblema char null,
  criarincidente char null,
  datainicio date not null,
  datafim date null,
  primary key (idmonitoramentoativos),
  index fk_monitoramento_tipoitemconf_idx (idtipoitemconfiguracao asc),
  constraint fk_monitoramento_tipoitemconf
    foreign key (idtipoitemconfiguracao )
    references tipoitemconfiguracao (idtipoitemconfiguracao ));
	
create table notificacaousuariomonit (
  idnotificacaousuariomonit int not null,
  idmonitoramentoativos int not null,
  idusuario int not null,
  datainicio date not null,
  datafim date null,
  primary key (idnotificacaousuariomonit) ,
  index fk_notificacao_monitoramento_idx (idmonitoramentoativos asc),
  index fk_notificacao_usuario_idx (idusuario asc),
  constraint fk_notificacao_monitoramento
    foreign key (idmonitoramentoativos )
    references monitoramentoativos (idmonitoramentoativos ),
  constraint fk_notificacao_usuario
    foreign key (idusuario )
    references usuario (idusuario ));

create table notificacaogrupomonit (
  idnotificacaogrupomonit int not null,
  idmonitoramentoativos int not null,
  idgrupo int not null,
  datainicio date not null,
  datafim date null,
  primary key (idnotificacaogrupomonit),
  index fk_notificacaogrupo_monit_idx_idx ( idmonitoramentoativos asc),
  index fk_notificacao_usuario_idx_idx (idgrupo asc),
  constraint fk_notificacaogrupo_monit_idx
    foreign key ( idmonitoramentoativos )
    references monitoramentoativos (idmonitoramentoativos ),
  constraint fk_notificacao_grupo_idx
    foreign key (idgrupo )
    references grupo (idgrupo ));
	
create table caracteristicamonit (
  idcaracteristicamonit int not null,
  idcaracteristica int not null,
  idmonitoramentoativos int not null,
  datainicio date not null,
  datafim date null,
  primary key (idcaracteristicamonit) ,
  index fk_caracteristica_monit_idx (idcaracteristica asc),
  index fk_monitoramento_carac_idx (idmonitoramentoativos asc),
  constraint fk_caracteristicamonit_carac
    foreign key (idcaracteristica )
    references caracteristica (idcaracteristica ),
  constraint fk_caracteristicamonit_monit
    foreign key (idmonitoramentoativos )
    references monitoramentoativos (idmonitoramentoativos ));

create table scriptmonit (
  idscriptmonit int not null,
  idmonitoramentoativos int not null,
  script text not null,
  datainicio date not null,
  datafim date null,
  primary key (idscriptmonit),
  index fk_scriptmonit_monit_idx (idmonitoramentoativos asc),
  constraint fk_scriptmonit_monit
    foreign key (idmonitoramentoativos )
    references monitoramentoativos (idmonitoramentoativos ));

-- FIM - valdoilo.damasceno - 13.06.2014
    
-- INICIO - rodrigo.acorse - 16.06.2014

insert into modelosemails (idmodeloemail, titulo, texto, situacao, identificador) values ($id_modelo_email_monitoramento, 'Monitoramento Tipo Item de Configuração - ${IDENTIFICACAO}', 'Informamos que o item de configura&ccedil;&atilde;o identificado como <strong>${IDENTIFICACAO}</strong> sofreu altera&ccedil;&atilde;o.<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;${DESCRICAO}<br /><strong>Novo valor:</strong>&nbsp;${VALORSTR}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.', 'A', 'monitAtivosNotif');
    
-- FIM - rodrigo.acorse - 16.06.2014