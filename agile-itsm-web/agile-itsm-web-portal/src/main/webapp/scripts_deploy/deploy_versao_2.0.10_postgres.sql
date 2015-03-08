-- PostgreSQL

-- Thays 08/07/13

alter table tipomudanca add column  impacto char(1) NULL;

alter table tipomudanca add column  urgencia char(1) NULL;

-- Fim Thays

-- Início Thays 09/07/13

insert into categoriaproblema (idcategoriaproblema, cat_idcategoriaproblema, idcategoriaproblemapai, nomecategoria, idtipofluxo, idgrupoexecutor, datainicio, datafim, nomecategoriaproblema, idtemplate) values (1, null, null, null, 50, 1, '2013-07-09', null, 'Categoria Padrão', null);

-- Fim Thays

-- Início Riubbe 09/07/13

alter table permissoesfluxo add cancelar char(1) NULL;

-- Fim Riubbe

-- Início Bruno 09/07/13

ALTER TABLE requisicaomudanca ADD idgrupoatvperiodica INT NULL;

ALTER TABLE liberacao ADD idgrupoatvperiodica INT NULL;

-- Fim Bruno

-- Início Thiago Fernandes 15/07/13

alter table requisicaoliberacaocompras add iditemrequisicaoproduto integer;

-- Fim Thiago Fernandes

-- Início Bruno 16/07/13

CREATE TABLE controlerendimento (
  idcontrolerendimento int NOT NULL,
  idgrupo int NOT NULL,
  idpessoa int NULL,
  mesapuracao varchar(45) NOT NULL,
  anoapuracao bigint DEFAULT NULL,
  datahoraexecucao date NOT NULL,
  aprovado varchar(1)NOT NULL,
  qtdpontospositivos int NULL,
  qtdpontosnegativos int NULL,
  qtdsolicitacoes int NULL,
  qtdpontos int NULL,
  mediarelativa varchar(100) NULL,
  PRIMARY KEY (idcontrolerendimento),
  foreign key (idgrupo) references grupo (idgrupo)
);

CREATE TABLE controlerendimentogrupo (
  idcontrolerendimentogrupo int NOT NULL,
  idcontrolerendimento int NOT NULL,
  idgrupo int NOT NULL,
  PRIMARY KEY (idcontrolerendimentogrupo),
  foreign key (idcontrolerendimento) references controlerendimento (idcontrolerendimento),
  foreign key (idgrupo) references grupo (idgrupo)
);

CREATE TABLE controlerendimentousuario (
  idcontrolerendimentousuario int NOT NULL,
  idcontrolerendimento int NOT NULL,
  idgrupo int NOT NULL,
  idusuario int NOT NULL,
  qtdtotalpontos varchar(45),
  aprovacao varchar(45),
  ano varchar(45),
  mes varchar(45),
  qtdpontospositivos varchar(45),
  qtdpontosnegativos varchar(45),
  qtditensentregues varchar(45),
  qtditensretornados varchar(45),
  PRIMARY KEY (idcontrolerendimentousuario),
  foreign key (idcontrolerendimento) references controlerendimento (idcontrolerendimento),
  foreign key (idusuario) references usuario (idusuario),
  foreign key (idgrupo) references grupo (idgrupo)
);

-- Fim Bruno

-- Início Bruno 17/07/13

alter table categoriaproblema add column  impacto char(1) NULL;

alter table categoriaproblema add column  urgencia char(1) NULL;

-- Fim Bruno

-- Início Pedro Lino/Emauri 19/07/13

create table linhabaseprojeto (
   idlinhabaseprojeto   int4                 not null,
   idprojeto            int4                 not null,
   datalinhabase        date                 not null,
   horalinhabase        varchar(4)           not null,
   situacao             char(1)              not null,
   dataultalteracao     date                 not null,
   horaultalteracao     varchar(4)           not null,
   usuarioultalteracao  varchar(40)          not null,
   justificativamudanca text                 null,
   datasolmudanca       date                 null,
   horasolmudanca       varchar(4)           null,
   usuariosolmudanca    varchar(40)          null,
   constraint pk_linhabaseprojeto primary key (idlinhabaseprojeto)
);

create table marcopagamentoprj (
   idmarcopagamentoprj  int4                 not null,
   idprojeto            int4                 not null,
   nomemarcopag         varchar(150)         not null,
   dataprevisaopag      date                 not null,
   valorpagamento       decimal(15,3)        null,
   situacao             char(1)              not null,
   dataultalteracao     date                 not null,
   horaultalteracao     varchar(4)           not null,
   usuarioultalteracao  varchar(40)          not null,
   constraint pk_marcopagamentoprj primary key (idmarcopagamentoprj)
);

create table pagamentoprojeto (
   idpagamentoprojeto   int4                 not null,
   idprojeto            int4                 null,
   datapagamento        date                 not null,
   valorpagamento       decimal(15,3)        not null,
   valorglosa           decimal(15,3)        null,
   detpagamento         text                 null,
   situacao             char(1)              not null,
   dataultalteracao     date                 not null,
   horaultalteracao     varchar(4)           not null,
   usuarioultalteracao  varchar(40)          not null,
   constraint pk_pagamentoprojeto primary key (idpagamentoprojeto)
);

create table perfilcontrato (
   idperfilcontrato     int4                 not null,
   idcontrato           int4                 not null,
   nomeperfilcontrato   varchar(100)         not null,
   custohora            decimal(15,3)        null,
   deleted              char(1)              null,
   constraint pk_perfilcontrato primary key (idperfilcontrato)
);

create table produtocontrato (
   idprodutocontrato    int4                 not null,
   idcontrato           int4                 null,
   nomeproduto          varchar(120)         not null,
   deleted              char(1)              null,
   constraint pk_produtocontrato primary key (idprodutocontrato)
);

create table produtotarefalinbaseproj (
   idtarefalinhabaseprojeto int4                 not null,
   idprodutocontrato    int4                 not null,
   constraint pk_produtotarefalinbaseproj primary key (idtarefalinhabaseprojeto, idprodutocontrato)
);

create table recursoprojeto (
   idprojeto            int4                 not null,
   idempregado          int4                 not null,
   custohora            decimal(15,3)        null,
   constraint pk_recursoprojeto primary key (idprojeto, idempregado)
);

create table recursotarefalinbaseproj (
   idrecursotarefalinbaseproj int4                 not null,
   idtarefalinhabaseprojeto int4                 not null,
   idperfilcontrato     int4                 not null,
   idempregado          int4                 null,
   percentualaloc       decimal(6,2)         null,
   tempoaloc            varchar(4)           null,
   percentualexec       decimal(6,2)         null,
   tempoalocminutos     decimal(15,3)        null,
   custo                decimal(15,3)        null,
   custoperfil          decimal(15,3)        null,
   constraint pk_recursotarefalinbaseproj primary key (idrecursotarefalinbaseproj)
);

create table tarefalinhabaseprojeto (
   idtarefalinhabaseprojeto int4                 not null,
   idlinhabaseprojeto   int4                 not null,
   idcalendario         int4                 null,
   idtarefalinhabaseprojetovinc int4                 null,
   idtarefalinhabaseprojetomigr int4                 null,
   idtarefalinhabaseprojetopai int4                 null,
   idpagamentoprojeto   int4                 null,
   idmarcopagamentoprj  int4                 null,
   sequencia            int4                 not null,
   nometarefa           varchar(4000)        not null,
   detalhamentotarefa   text                 null,
   codetarefa           varchar(40)          null,
   progresso            decimal(6,2)         null,
   datainicio           date                 not null,
   datafim              date                 not null,
   duracaohora          decimal(15,3)        null,
   nivel                int4                 null,
   idinternal           varchar(40)          not null,
   collapsed            char(1)              null,
   custo                decimal(15,3)        null,
   custoperfil          decimal(15,3)        null,
   situacao             char(1)              not null,
   trabalho             decimal(7,2)         null,
   datainicioreal       date                 null,
   datafimreal          date                 null,
   duracaohorareal      decimal(15,3)        null,
   custoreal            decimal(15,3)        null,
   custorealperfil      decimal(15,3)        null,
   estimada             char(1)              not null,
   tempototalocminutos  decimal(15,3)        null,
   constraint pk_tarefalinhabaseprojeto primary key (idtarefalinhabaseprojeto)
);

create table templateimpressao (
   idtemplateimpressao  int4                 not null,
   nometemplate         varchar(100)         not null,
   htmlcabecalho        text                 null,
   htmlcorpo            text                 not null,
   htmlrodape           text                 null,
   idtipotemplateimp    int4                 null,
   tamcabecalho         int4                 null,
   tamrodape            int4                 null,
   constraint pk_templateimpressao primary key (idtemplateimpressao)
);

create table timesheetprojeto (
   idtimesheetprojeto   int4                 not null,
   idrecursotarefalinbaseproj int4                 not null,
   datahorareg          timestamp            not null,
   data                 date                 not null,
   hora                 varchar(5)           not null,
   qtdehoras            decimal(6,2)         not null,
   custo                decimal(18,3)        null,
   detalhamento         text                 null,
   percexecutado        decimal(6,2)         null,
   constraint pk_timesheetprojeto primary key (idtimesheetprojeto)
);

alter table linhabaseprojeto add constraint fk_linhabas_reference_projetos foreign key (idprojeto) references projetos (idprojeto) on delete restrict on update restrict;
	  
alter table marcopagamentoprj add constraint fk_marcopag_reference_projetos foreign key (idprojeto) references projetos (idprojeto) on delete restrict on update restrict;
	  
alter table pagamentoprojeto add constraint fk_pagament_reference_projetos foreign key (idprojeto) references projetos (idprojeto) on delete restrict on update restrict;
	  
alter table perfilcontrato add constraint fk_perfilco_reference_contrato foreign key (idcontrato) references contratos (idcontrato) on delete restrict on update restrict;
	  
alter table produtocontrato add constraint fk_produtoc_reference_contrato foreign key (idcontrato) references contratos (idcontrato) on delete restrict on update restrict;
	  
alter table produtotarefalinbaseproj add constraint fk_produtot_reference_produtoc foreign key (idprodutocontrato) references produtocontrato (idprodutocontrato) on delete restrict on update restrict;
	  
alter table produtotarefalinbaseproj add constraint fk_produtot_reference_tarefali foreign key (idtarefalinhabaseprojeto) references tarefalinhabaseprojeto (idtarefalinhabaseprojeto) on delete restrict on update restrict;
	  
alter table recursoprojeto add constraint fk_recursop_reference_empregad foreign key (idempregado) references empregados (idempregado) on delete restrict on update restrict;
	  
alter table recursoprojeto add constraint fk_recursop_reference_projetos foreign key (idprojeto) references projetos (idprojeto) on delete restrict on update restrict;
	  
alter table recursotarefalinbaseproj add constraint fk_recursot_reference_empregad foreign key (idempregado) references empregados (idempregado) on delete restrict on update restrict;
	  
alter table recursotarefalinbaseproj add constraint fk_recursot_reference_tarefali foreign key (idtarefalinhabaseprojeto) references tarefalinhabaseprojeto (idtarefalinhabaseprojeto) on delete restrict on update restrict;
	  
alter table recursotarefalinbaseproj add constraint fk_recursot_reference_perfilco foreign key (idperfilcontrato) references perfilcontrato (idperfilcontrato) on delete restrict on update restrict;
	  
alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_tarefali foreign key (idtarefalinhabaseprojetomigr) references tarefalinhabaseprojeto (idtarefalinhabaseprojeto) on delete restrict on update restrict;
	  
alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_tarefali_pai foreign key (idtarefalinhabaseprojetopai) references tarefalinhabaseprojeto (idtarefalinhabaseprojeto) on delete restrict on update restrict;
	  
alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_pagament foreign key (idpagamentoprojeto) references pagamentoprojeto (idpagamentoprojeto) on delete restrict on update restrict;
	  
alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_marcopag foreign key (idmarcopagamentoprj) references marcopagamentoprj (idmarcopagamentoprj) on delete restrict on update restrict;
	  
alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_linhabas foreign key (idlinhabaseprojeto) references linhabaseprojeto (idlinhabaseprojeto) on delete restrict on update restrict;
	  
alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_calendar foreign key (idcalendario) references calendario (idcalendario) on delete restrict on update restrict;
	  
alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_tarefali_vinc foreign key (idtarefalinhabaseprojetovinc) references tarefalinhabaseprojeto (idtarefalinhabaseprojeto) on delete restrict on update restrict;
	  
alter table timesheetprojeto add constraint fk_timeshee_reference_recursot foreign key (idrecursotarefalinbaseproj) references recursotarefalinbaseproj (idrecursotarefalinbaseproj) on delete restrict on update restrict;
	  
alter table os add constraint pk_idos primary key(idos);

alter table os add dataemissao date;

alter table projetos add idos integer;

alter table projetos add sigla varchar(50);

alter table projetos add emergencial char(1);

alter table projetos add severidade char(1);

alter table projetos add nomegestor varchar(50);

alter table projetos add idrequisicaomudanca integer;

alter table projetos add idliberacao integer;

alter table projetos add constraint fk_proj_ref_os foreign key (idos) references os (idos) match simple on update restrict on delete restrict;
	  
alter table projetos add constraint fk_proj_ref_mudanc foreign key (idrequisicaomudanca) references requisicaomudanca (idrequisicaomudanca) match simple on update restrict on delete restrict;
	  
alter table projetos add constraint fk_proj_ref_liber foreign key (idliberacao) references liberacao (idliberacao) match simple on update restrict on delete restrict;
	  	  
-- Fim Pedro Lino/Emauri

-- Início Bruno 19/07/13

ALTER TABLE requisicaomudanca ADD COLUMN idcategoriasolucao int;

ALTER TABLE liberacao ADD COLUMN idcategoriasolucao int;

-- Fim Bruno

-- Início Danillo Sardinha 19/07/13

create table historicomudanca (
  idhistoricomudanca integer not null,
  datahoramodificacao timestamp,
  idexecutormodificacao integer not null,
  tipomodificacao varchar(1) default null,
  historicoversao double precision,
  baseline varchar(30),
  idrequisicaomudanca integer not null,
  idproprietario integer not null,
  idsolicitante integer not null,
  idtipomudanca integer,
  idgruponivel1 integer,
  idgrupoatual integer,
  idcalendario integer,
  motivo varchar(255) default null,
  nivelimportancianegocio varchar(255) default null,
  classificacao varchar(255) default null,
  nivelimpacto varchar(255) default null,
  analiseimpacto varchar(3000) default null,
  datahoraconclusao timestamp,
  dataaceitacao date,
  datavotacao date,
  datahorainicio timestamp,
  datahoratermino timestamp,
  titulo varchar(255) default null,
  descricao varchar(255) default null,
  risco varchar(255) default null,
  estimativacusto double precision,
  planoreversao varchar(3000) default null,
  status varchar(45) default null,
  prioridade integer,
  enviaemailcriacao varchar(1) default null,
  enviaemailfinalizacao varchar(1) default null,
  enviaemailacoes varchar(1) default null,
  exibirquadromudancas varchar(1) default null,
  seqreabertura smallint,
  datahoracaptura timestamp,
  datahorareativacao timestamp,
  datahorasuspensao timestamp,
  tempodecorridohh smallint,
  tempodecorridomm smallint,
  prazohh smallint,
  prazomm smallint,
  tempoatendimentohh smallint,
  tempoatendimentomm smallint,
  tempoatrasohh smallint,
  tempoatrasomm smallint,
  tempocapturahh smallint,
  tempocapturamm smallint,
  fase varchar(20) default null,
  nivelurgencia varchar(255) default null,
  idbaseconhecimento integer,
  nomecategoriamudanca varchar(15) default null,
  idcontrato integer not null,
  idunidade integer,
  idcontatorequisicaomudanca integer,
  idgrupocomite integer,
  enviaemailgrupocomite varchar(1) default null,
  datahorainicioagendada timestamp,
  datahoraterminoagendada timestamp,
  idlocalidade integer,
  fechamento text,
  tipo varchar(255) default null,
  razaomudanca varchar(200),
  ehpropostaaux char(1),
  votacaopropostaaprovadaaux char(1),
  idgrupoatvperiodica integer,
  constraint pk_historicomudanca primary key (idhistoricomudanca)
);

create table ligacao_mud_hist_resp (
    idligacao_mud_hist_resp integer not null,
    idrequisicaomudancaresp integer,
    idrequisicaomudanca integer,
    idhistoricomudanca integer
);

alter table requisicaomudancaresponsavel add column datafim date default null;

create table ligacao_mud_hist_ic (
    idligacao_mud_hist_ic integer not null,
    idrequisicaomudancaitemconfiguracao integer,
    idrequisicaomudanca integer,
    idhistoricomudanca integer
);

alter table requisicaomudancaitemconfiguracao add column datafim date default null;

create table ligacao_mud_hist_se (
    idligacao_mud_hist_se integer not null,
    idrequisicaomudancaservico integer,
    idrequisicaomudanca integer,
    idhistoricomudanca integer
);

alter table requisicaomudancaservico add column datafim date default null;

create table ligacao_mud_hist_pr (
    idligacao_mud_hist_pr integer not null,
    idproblemamudanca integer,
    idrequisicaomudanca integer,
    idhistoricomudanca integer
);

alter table problemamudanca add column datafim date default null;

create table ligacao_mud_hist_risco(
    idligacao_mud_hist_risco integer not null,
    idrequisicaomudancarisco integer,
    idrequisicaomudanca integer,
    idhistoricomudanca integer
);

alter table requisicaomudancarisco add column datafim date default null;

alter table aprovacaomudanca add column idhistoricomudanca integer default null;

alter table solicitacaoservicomudanca add column idhistoricomudanca integer default null;

alter table liberacaomudanca add column idhistoricomudanca integer default null;

-- Fim Danillo Sardinha

-- Início Murilo Pacheco 22/07/13

alter table historicomudanca add column registroexecucao character varying default null;

alter table historicomudanca add column emailsolicitante character varying default null;

alter table historicomudanca add column alterarsituacao character varying default null;

alter table historicomudanca add column acaofluxo character varying default null;

alter table controleged add column versao character varying default null;

-- Fim Murilo Pacheco

-- Início Geber 22/07/13

create table formapagamento(
  idformapagamento integer not null,
  nomeformapagamento character varying(100) not null,
  situacao char(1),
  constraint formapagamento_pkey primary key (idformapagamento)
);

-- Fim Geber

-- Início Ronnie 24/07/13

create table tipomovimfinanceiraviagem (
  idtipomovimfinanceiraviagem integer not null,
  nome varchar(100) not null,
  descricao text,
  classificacao varchar(30) not null,
  situacao char(1) not null,
  permiteadiantamento char(1) not null,
  exigeprestacaoconta char(1) not null,
  exigejustificativa char(1) not null,
  valorpadrao decimal(8,2),
  tipo char(1) not null
);

alter table tipomovimfinanceiraviagem add constraint tipomovimfinanceiraviagem_pkey primary key (idtipomovimfinanceiraviagem);

-- Fim Ronnie

-- Início Geber 24/07/13

alter table justificativasolicitacao add column viagem char(1);

-- Fim Geber

-- Início Thays 29/07/13

update justificativasolicitacao set viagem = 'n' where idjustificativa > 0;

create table requisicaoviagem (
   idsolicitacaoservico bigint not null,
   idcidadeorigem integer not null,
   idcidadedestino integer not null,
   idprojeto integer not null,
   idcentrocusto integer not null,
   idmotivoviagem integer not null,
   idaprovacao integer,
   descricaomotivo text not null,
   datainicio date not null,
   datafim date not null,
   qtdedias integer not null,
   estado varchar(30) not null
);

alter table requisicaoviagem add constraint requisicaoviagem_pkey primary key (idsolicitacaoservico);

alter table requisicaoviagem add constraint fk_requisicaoviagem_reference_solicitacaoservico foreign key (idsolicitacaoservico) references solicitacaoservico (idsolicitacaoservico);

alter table requisicaoviagem add constraint fk_requisicaoviagem_reference_cidadeorigem foreign key (idcidadeorigem) references cidades (idcidade);

alter table requisicaoviagem add constraint fk_requisicaoviagem_reference_cidadedestino foreign key (idcidadedestino) references cidades (idcidade);

alter table requisicaoviagem add constraint fk_requisicaoviagem_reference_centroresultado foreign key (idcentrocusto) references centroresultado (idcentroresultado);

alter table requisicaoviagem add constraint fk_requisicaoviagem_reference_projetos foreign key (idprojeto) references projetos (idprojeto);

alter table requisicaoviagem add constraint fk_requisicaoviagem_reference_justificativasolicitacao foreign key (idmotivoviagem) references justificativasolicitacao (idjustificativa);

alter table requisicaoviagem add constraint fk_requisicaoviagem_reference_parecer foreign key (idaprovacao) references parecer (idparecer);

create table integranteviagem (
   idsolicitacaoservico bigint not null,
   idempregado integer not null,
   primary key (idsolicitacaoservico, idempregado)
);

alter table integranteviagem add constraint fk_integranteviagem_reference_solicitacaoservico foreign key (idsolicitacaoservico) references requisicaoviagem (idsolicitacaoservico);

alter table integranteviagem add constraint fk_integranteviagem_reference_empregados foreign key (idempregado) references empregados (idempregado);

-- Fim Thays

-- Início Bruno 01/08/13

alter table tipomudanca add exigeaprovacao char(1);

-- Fim Bruno

-- Início Bruno 02/08/13

alter table tarefalinhabaseprojeto add column depends varchar(40);

-- Fim Bruno

-- Início Thiago Matias 09/08/13

alter table infocatalogoservico add idServicoContrato integer not null;

alter table infocatalogoservico add nomeServicoContrato varchar(500) null;

-- Fim Thiago Matias

-- Início Thiago Matias 21/08/13

alter table infocatalogoservico rename column idservicocontrato to idservicocatalogo;

alter table infocatalogoservico alter idservicocatalogo drop not null;

-- Fim Thiago Matias

-- Início Flávio 21/08/13

CREATE  TABLE pedidoportal (
  idpedidoportal INT NOT NULL ,
  idusuario INT NULL ,
  datapedido DATE NULL ,
  precototal double precision NULL ,
  status VARCHAR(254) NULL ,
  PRIMARY KEY (idpedidoportal) ,
  CONSTRAINT rel_pedidosolicitacao_usuario
    FOREIGN KEY (idusuario )
    REFERENCES usuario (idusuario )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE  TABLE itempedidoportal (
  iditempedidoportal INT NOT NULL ,
  idpedidoportal INT NULL ,
  idsolicitacaoservico BIGINT NULL ,
  valor double precision NULL ,
  PRIMARY KEY (iditempedidoportal) ,
  CONSTRAINT rel_solicitacao_itempedido
    FOREIGN KEY (idsolicitacaoservico )
    REFERENCES solicitacaoservico (idsolicitacaoservico )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT rel_pedido_itempedido
    FOREIGN KEY (idpedidoportal )
    REFERENCES pedidoportal (idpedidoportal )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- Fim Flávio

-- Início Mário  30/08/13

alter table itemconfiguracao ADD column  idcontrato INT NULL;

alter table itemconfiguracao ADD column idresponsavel INT NULL;

alter table itemconfiguracao ADD column ativofixo VARCHAR(255) NULL;

-- Fim Mário