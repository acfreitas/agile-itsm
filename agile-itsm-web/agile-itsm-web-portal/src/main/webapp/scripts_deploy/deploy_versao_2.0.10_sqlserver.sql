-- SQLServer

-- Início Thays 08/07/13

alter table tipomudanca add impacto char(1) NULL;

alter table tipomudanca add urgencia char(1) NULL;

-- Fim Thays

-- Danillo Sardinha 08/07/13

sp_rename 'requisicaoliberacaoResponsavel.idRequisicaoLiberacaoResponsavel', 'idRequisicaoLiberacaoResp', 'COLUMN';

-- Fim Danillo Sardinha

-- Início Thays 09/07/13

insert into categoriaproblema (idcategoriaproblema, cat_idcategoriaproblema, idcategoriaproblemapai, nomecategoria, idtipofluxo, idgrupoexecutor, datainicio, datafim, nomecategoriaproblema, idtemplate) values (1, null, null, null, 50, 1, '2013-07-09', null, 'Categoria Padrão', null);

-- Fim Thays

-- Início Murilo Pacheco 09/07/13

ALTER TABLE requisicaoliberacaoresponsavel ADD papelresponsavel varchar(200);

-- Fim Murilo Pacheco

-- Início Riubbe 09/07/13

alter table permissoesfluxo add cancelar char(1) NULL;

-- Fim Riubbe

-- Início Bruno 09/07/13

ALTER TABLE requisicaomudanca ADD idgrupoatvperiodica INT NULL;

ALTER TABLE liberacao ADD idgrupoatvperiodica INT NULL;

-- fim Bruno

-- Início Thiago Fernandes 10/07/13

alter table liberacao alter column datainicial datetime;

alter table liberacao alter column datafinal datetime;

-- Fim Thiago Fernandes

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

alter table categoriaproblema add impacto char(1) NULL;

alter table categoriaproblema add urgencia char(1) NULL;

-- Fim Bruno

-- Início Pedro Lino/Emauri 19/07/13

create table linhabaseprojeto (
  idlinhabaseprojeto integer not null,
  idprojeto integer not null,
  datalinhabase date not null,
  horalinhabase varchar(4) not null,
  situacao char(1) not null,
  dataultalteracao date not null,
  horaultalteracao varchar(4) not null,
  usuarioultalteracao varchar(40) not null,
  justificativamudanca text default null,
  datasolmudanca date default null,
  horasolmudanca varchar(4) default null,
  usuariosolmudanca varchar(40) default null
);

alter table linhabaseprojeto add constraint pk_linhabaseprojeto primary key (idlinhabaseprojeto);

create table marcopagamentoprj (
   idmarcopagamentoprj integer not null,
   idprojeto integer not null,
   nomemarcopag varchar(150) not null,
   dataprevisaopag date not null,
   valorpagamento decimal(15,3) default null,
   situacao char(1) not null,
   dataultalteracao date not null,
   horaultalteracao varchar(4) not null,
   usuarioultalteracao  varchar(40) not null
);

alter table marcopagamentoprj add constraint pk_marcopagamentoprj primary key (idmarcopagamentoprj);

create table pagamentoprojeto (
   idpagamentoprojeto integer not null,
   idprojeto integer default null,
   datapagamento date not null,
   valorpagamento decimal(15,3) not null,
   valorglosa decimal(15,3) default null,
   detpagamento text default null,
   situacao char(1) not null,
   dataultalteracao date not null,
   horaultalteracao varchar(4) not null,
   usuarioultalteracao varchar(40) not null
);

alter table pagamentoprojeto add constraint pk_pagamentoprojeto primary key (idpagamentoprojeto);

create table perfilcontrato (
   idperfilcontrato integer not null,
   idcontrato integer not null,
   nomeperfilcontrato varchar(100) not null,
   custohora decimal(15,3) default null,
   deleted char(1) default null
);

alter table perfilcontrato add constraint pk_perfilcontrato primary key (idperfilcontrato);

create table produtocontrato (
   idprodutocontrato integer not null,
   idcontrato integer default null,
   nomeproduto varchar(120) not null,
   deleted char(1) default null
);

alter table produtocontrato add constraint pk_produtocontrato primary key (idprodutocontrato);

create table produtotarefalinbaseproj (
   idtarefalinhabaseprojeto integer not null,
   idprodutocontrato integer not null
);

alter table produtotarefalinbaseproj add constraint pk_produtotarefalinbaseproj primary key (idtarefalinhabaseprojeto, idprodutocontrato);

create table recursoprojeto (
   idprojeto integer not null,
   idempregado integer not null,
   custohora decimal(15,3) default null
);

alter table recursoprojeto add constraint pk_recursoprojeto primary key (idprojeto, idempregado);

create table recursotarefalinbaseproj (
   idrecursotarefalinbaseproj integer not null,
   idtarefalinhabaseprojeto integer not null,
   idperfilcontrato integer not null,
   idempregado integer default null,
   percentualaloc decimal(6,2) null,
   tempoaloc varchar(4) default null,
   percentualexec decimal(6,2) default null,
   tempoalocminutos decimal(15,3) default null,
   custo decimal(15,3) default null,
   custoperfil decimal(15,3) default null
);

alter table recursotarefalinbaseproj add constraint pk_recursotarefalinbaseproj primary key (idrecursotarefalinbaseproj);

create table tarefalinhabaseprojeto (
   idtarefalinhabaseprojeto integer not null,
   idlinhabaseprojeto integer not null,
   idcalendario integer default null,
   idtarefalinhabaseprojetovinc integer default null,
   idtarefalinhabaseprojetomigr integer default null,
   idtarefalinhabaseprojetopai integer default null,
   idpagamentoprojeto integer default null,
   idmarcopagamentoprj integer default null,
   sequencia integer not null,
   nometarefa varchar(4000) not null,
   detalhamentotarefa text default null,
   codetarefa varchar(40) default null,
   progresso decimal(6,2) default null,
   datainicio date not null,
   datafim date not null,
   duracaohora decimal(15,3) default null,
   nivel integer default null,
   idinternal varchar(40) not null,
   collapsed char(1) default null,
   custo decimal(15,3) default null,
   custoperfil decimal(15,3) default null,
   situacao char(1) not null,
   trabalho decimal(7,2) default null,
   datainicioreal date default null,
   datafimreal date default null,
   duracaohorareal decimal(15,3) default null,
   custoreal decimal(15,3) default null,
   custorealperfil decimal(15,3) default null,
   estimada char(1) not null,
   tempototalocminutos decimal(15,3) default null
);

alter table tarefalinhabaseprojeto add constraint pk_tarefalinhabaseprojeto primary key (idtarefalinhabaseprojeto);

create table templateimpressao (
   idtemplateimpressao integer not null,
   nometemplate varchar(100) not null,
   htmlcabecalho text null,
   htmlcorpo text not null,
   htmlrodape text null,
   idtipotemplateimp integer default null,
   tamcabecalho integer default null,
   tamrodape integer default null
);

alter table templateimpressao add constraint pk_templateimpressao primary key (idtemplateimpressao);

create table timesheetprojeto (
   idtimesheetprojeto integer not null,
   idrecursotarefalinbaseproj integer not null,
   datahorareg datetime not null,
   data date not null,
   hora varchar(5) not null,
   qtdehoras decimal(6,2) not null,
   custo decimal(18,3) default null,
   detalhamento text default null,
   percexecutado decimal(6,2) default null
);

alter table timesheetprojeto add constraint pk_timesheetprojeto primary key (idtimesheetprojeto);

alter table linhabaseprojeto add constraint fk_linhabas_reference_projetos foreign key (idprojeto) references projetos (idprojeto);

alter table marcopagamentoprj add constraint fk_marcopag_reference_projetos foreign key (idprojeto) references projetos (idprojeto);

alter table pagamentoprojeto add constraint fk_pagament_reference_projetos foreign key (idprojeto) references projetos (idprojeto);

alter table perfilcontrato add constraint fk_perfilco_reference_contrato foreign key (idcontrato) references contratos (idcontrato);

alter table produtocontrato add constraint fk_produtoc_reference_contrato foreign key (idcontrato) references contratos (idcontrato);

alter table produtotarefalinbaseproj add constraint fk_produtot_reference_produtoc foreign key (idprodutocontrato) references produtocontrato (idprodutocontrato);

alter table produtotarefalinbaseproj add constraint fk_produtot_reference_tarefali foreign key (idtarefalinhabaseprojeto) references tarefalinhabaseprojeto (idtarefalinhabaseprojeto);

alter table recursoprojeto add constraint fk_recursop_reference_empregad foreign key (idempregado) references empregados (idempregado);

alter table recursoprojeto add constraint fk_recursop_reference_projetos foreign key (idprojeto) references projetos (idprojeto);

alter table recursotarefalinbaseproj add constraint fk_recursot_reference_empregad foreign key (idempregado) references empregados (idempregado);

alter table recursotarefalinbaseproj add constraint fk_recursot_reference_tarefali foreign key (idtarefalinhabaseprojeto) references tarefalinhabaseprojeto (idtarefalinhabaseprojeto);

alter table recursotarefalinbaseproj add constraint fk_recursot_reference_perfilco foreign key (idperfilcontrato) references perfilcontrato (idperfilcontrato);
 
alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_tarefali foreign key (idtarefalinhabaseprojetomigr) references tarefalinhabaseprojeto (idtarefalinhabaseprojeto);

alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_tar_pai foreign key (idtarefalinhabaseprojetopai) references tarefalinhabaseprojeto (idtarefalinhabaseprojeto);

alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_pagament foreign key (idpagamentoprojeto) references pagamentoprojeto (idpagamentoprojeto);

alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_marcopag foreign key (idmarcopagamentoprj) references marcopagamentoprj (idmarcopagamentoprj);

alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_linhabas foreign key (idlinhabaseprojeto) references linhabaseprojeto (idlinhabaseprojeto);

alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_calendar foreign key (idcalendario) references calendario (idcalendario);

alter table tarefalinhabaseprojeto add constraint fk_tarefali_reference_tar_vinc foreign key (idtarefalinhabaseprojetovinc) references tarefalinhabaseprojeto (idtarefalinhabaseprojeto);

alter table timesheetprojeto add constraint fk_timeshee_reference_recursot foreign key (idrecursotarefalinbaseproj) references recursotarefalinbaseproj (idrecursotarefalinbaseproj);


alter table os add constraint pk_idos primary key(idos);

alter table os add dataemissao date;

alter table projetos add idos integer;

alter table projetos add sigla varchar(50);

alter table projetos add emergencial char(1);

alter table projetos add severidade char(1);

alter table projetos add nomegestor varchar(50);

alter table projetos add idrequisicaomudanca integer;

alter table projetos add idliberacao integer;

alter table projetos add constraint fk_proj_ref_os foreign key (idos) references os (idos);

alter table projetos add constraint fk_proj_ref_mudanc foreign key (idrequisicaomudanca) references requisicaomudanca (idrequisicaomudanca);

alter table projetos add constraint fk_proj_ref_liber foreign key (idliberacao) references liberacao (idliberacao);

-- Fim Pedro Lino/Emauri

-- Início Bruno 19/07/13

ALTER TABLE requisicaomudanca ADD idcategoriasolucao int;

ALTER TABLE liberacao ADD idcategoriasolucao int;

-- Fim Bruno

-- Início Danillo Sardinha 19/07/13

create table historicomudanca (
  idhistoricomudanca integer not null,
  datahoramodificacao datetime,
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
  datahoraconclusao datetime,
  dataaceitacao date,
  datavotacao date,
  datahorainicio datetime,
  datahoratermino datetime,
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
  datahoracaptura datetime,
  datahorareativacao datetime,
  datahorasuspensao datetime,
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
  datahorainicioagendada datetime,
  datahoraterminoagendada datetime,
  idlocalidade integer,
  fechamento text,
  tipo varchar(255) default null,
  razaomudanca varchar(200),
  ehpropostaaux char(1),
  votacaopropostaaprovadaaux char(1),
  idgrupoatvperiodica integer
);

alter table historicomudanca add constraint pk_historicomudanca primary key (idhistoricomudanca);

create table ligacao_mud_hist_resp (
    idligacao_mud_hist_resp integer not null,
    idrequisicaomudancaresp integer,
    idrequisicaomudanca integer,
    idhistoricomudanca integer
);

alter table requisicaomudancaresponsavel add datafim date default null;

create table ligacao_mud_hist_ic (
    idligacao_mud_hist_ic integer not null,
    idrequisicaomudancaitemconfiguracao integer,
    idrequisicaomudanca integer,
    idhistoricomudanca integer
);

alter table requisicaomudancaitemconfiguracao add datafim date default null;

create table ligacao_mud_hist_se (
    idligacao_mud_hist_se integer not null,
    idrequisicaomudancaservico integer,
    idrequisicaomudanca integer,
    idhistoricomudanca integer
);

alter table requisicaomudancaservico add datafim date default null;

create table ligacao_mud_hist_pr (
    idligacao_mud_hist_pr integer not null,
    idproblemamudanca integer,
    idrequisicaomudanca integer,
    idhistoricomudanca integer
);

alter table problemamudanca add datafim date default null;

create table ligacao_mud_hist_risco(
    idligacao_mud_hist_risco integer not null,
    idrequisicaomudancarisco integer,
    idrequisicaomudanca integer,
    idhistoricomudanca integer
);

alter table requisicaomudancarisco add datafim date default null;

alter table aprovacaomudanca add idhistoricomudanca integer default null;

alter table solicitacaoservicomudanca add idhistoricomudanca integer default null;

alter table liberacaomudanca add idhistoricomudanca integer default null;

-- Fim Danillo Sardinha

-- Início Murilo Pacheco 22/07/13

alter table historicomudanca add registroexecucao varchar(250) default null;

alter table historicomudanca add emailsolicitante varchar(250) default null;

alter table historicomudanca add alterarsituacao varchar(250) default null;

alter table historicomudanca add acaofluxo varchar(250) default null;

alter table controleged add versao varchar(250) default null;

-- Fim Murilo Pacheco

-- Início Geber 22/07/13

create table formapagamento(
  idformapagamento integer not null,
  nomeformapagamento varchar(100),
  situacao char(1),
  constraint formapagamento_pkey primary key (idformapagamento)
);

-- Fim Geber

-- Início Ronnie 24/07/13

create table tipomovimfinanceiraviagem (
	idtipomovimfinanceiraviagem integer not null,
	nome varchar(100) not null,
	descricao text null,
	classificacao varchar(30) not null,
	situacao char(1) not null,
	permiteadiantamento char(1) not null,
	exigeprestacaoconta char(1) not null,
	exigejustificativa char(1) not null,
	valorpadrao decimal(8, 2) null,
	tipo char(1) not null
);

alter table tipomovimfinanceiraviagem add constraint tipomovimfinanceiraviagem_pkey primary key (idtipomovimfinanceiraviagem);

-- Fim Ronnie

-- Início Geber 24/07/13

alter table justificativasolicitacao add viagem char(1);

-- Fim Geber

-- Início Thays 29/07/13

update justificativasolicitacao set viagem = 'n' where idjustificativa > 0;

create table requisicaoviagem (
   idsolicitacaoservico bigint not null,
   idcidadeorigem int not null,
   idcidadedestino int not null,
   idprojeto int not null,
   idcentrocusto int not null,
   idmotivoviagem int not null,
   idaprovacao int,
   descricaomotivo text not null,
   datainicio date not null,
   datafim date not null,
   qtdedias int not null,
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
   idempregado int not null,
   primary key (idsolicitacaoservico, idempregado)
);

alter table integranteviagem add constraint fk_integranteviagem_reference_solicitacaoservico foreign key (idsolicitacaoservico) references requisicaoviagem (idsolicitacaoservico);

alter table integranteviagem add constraint fk_integranteviagem_reference_empregados foreign key (idempregado) references empregados (idempregado);

-- Fim Thays

-- Início Bruno 01/08/13

alter table tipomudanca add exigeaprovacao char(1);

-- Fim Bruno

-- Início Bruno 02/08/13

alter table tarefalinhabaseprojeto add depends varchar(45);

-- Fim Bruno


-- Início Thiago Matias 09/08/13

alter table infocatalogoservico add idServicoContrato integer not null;

alter table infocatalogoservico add nomeServicoContrato varchar(500) null;

-- Fim Thiago Matias 09/08/13

-- Início Thiago Matias 21/08/13

EXEC sp_rename 'infocatalogoservico.idservicocontrato', 'idservicocatalogo', 'COLUMN';

alter table infocatalogoservico alter column idservicocatalogo [integer] null;

-- Fim Thiago Matias 21/08/13

-- Início Flavio 21/08/13

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
    ON UPDATE NO ACTION );


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
    ON UPDATE NO ACTION );


-- Fim Flavio 21/08/13

-- Início Mário  30/08/13

alter table itemconfiguracao ADD  idcontrato INTEGER NULL;

alter table itemconfiguracao ADD idresponsavel INTEGER NULL;

alter table itemconfiguracao ADD ativofixo VARCHAR(255) NULL;

-- Fim Mário
