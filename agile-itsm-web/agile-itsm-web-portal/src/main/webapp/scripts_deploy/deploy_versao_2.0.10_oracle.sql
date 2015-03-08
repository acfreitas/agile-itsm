-- Oracle

-- Início Thays 08/07/13

alter table tipomudanca add (impacto char(1) NULL);

alter table tipomudanca add (urgencia char(1) NULL);

-- Fim Thays

-- Início Thays 09/07/13

insert into categoriaproblema (idcategoriaproblema, cat_idcategoriaproblema, idcategoriaproblemapai, nomecategoria, idtipofluxo, idgrupoexecutor, datainicio, datafim, nomecategoriaproblema, idtemplate ) values (1, null, null, null, 50, 1 ,to_date('2013-07-09', 'yyyy-mm-dd'), null, 'Categoria Padrão', null);

-- Fim Thays

-- Início Riubbe 09/07/13

alter table permissoesfluxo add(cancelar char(1) NULL);

-- Fim Riubbe

-- Início Bruno 09/07/13

ALTER TABLE requisicaomudanca ADD (idgrupoatvperiodica NUMBER(3));

ALTER TABLE liberacao ADD (idgrupoatvperiodica NUMBER(3));

-- Fim Bruno

-- Thiago Fernandes 12/07/13

update BPM_TIPOFLUXO set descricao = 'Requisição de Mudança Padrão' where idtipofluxo = 6;

update BPM_TIPOFLUXO set descricao = 'Requisição Mudança Emergencial' where idtipofluxo = 7;

update BPM_TIPOFLUXO set descricao = 'Requisição de Mudança Normal'  where idtipofluxo = 8;

-- Fim Thiago Fernandes

-- Início Thiago Fernandes 15/07/13

alter table requisicaoliberacaocompras add iditemrequisicaoproduto  number(10,0);

-- Fim Thiago Fernandes

-- Início Bruno 16/07/13

CREATE TABLE controlerendimento (
  idcontrolerendimento NUMBER(10,0) NOT NULL,
  idgrupo NUMBER(10,0) NOT NULL,
  idpessoa NUMBER(10,0) NULL,
  mesapuracao varchar(45) NOT NULL,
  anoapuracao NUMBER(10,0) DEFAULT NULL,
  datahoraexecucao date NOT NULL,
  aprovado varchar(1)NOT NULL,
  qtdpontospositivos NUMBER(10,0) NULL,
  qtdpontosnegativos NUMBER(10,0) NULL,
  qtdsolicitacoes NUMBER(10,0) NULL,
  qtdpontos NUMBER(10,0) NULL,
  mediarelativa varchar(100) NULL,
  PRIMARY KEY (idcontrolerendimento),
  foreign key (idgrupo) references grupo (idgrupo)
);

CREATE TABLE controlerendimentogrupo (
  idcontrolerendimentogrupo NUMBER(10,0) NOT NULL,
  idcontrolerendimento NUMBER(10,0) NOT NULL,
  idgrupo NUMBER(10,0) NOT NULL,
  PRIMARY KEY (idcontrolerendimentogrupo),
  foreign key (idcontrolerendimento) references controlerendimento (idcontrolerendimento),
  foreign key (idgrupo) references grupo (idgrupo)
);

CREATE TABLE controlerendimentousuario (
  idcontrolerendimentousuario NUMBER(10,0) NOT NULL,
  idcontrolerendimento NUMBER(10,0) NOT NULL,
  idgrupo NUMBER(10,0) NOT NULL,
  idusuario NUMBER(10,0) NOT NULL,
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

alter table categoriaproblema add (impacto char(1) NULL);

alter table categoriaproblema add (urgencia char(1) NULL);

-- Fim Bruno

-- Início Pedro Lino/Emauri 19/07/13

create table linhabaseprojeto (
  idlinhabaseprojeto number(10,0) not null,
  idprojeto number(10,0) not null,
  datalinhabase date not null,
  horalinhabase varchar2(4) not null,
  situacao char(1) not null,
  dataultalteracao date not null,
  horaultalteracao varchar2(4) not null,
  usuarioultalteracao varchar2(40) not null,
  justificativamudanca clob default null,
  datasolmudanca date default null,
  horasolmudanca varchar2(4) default null,
  usuariosolmudanca varchar2(40) default null
);

alter table linhabaseprojeto add constraint pk_linhabaseprojeto primary key (idlinhabaseprojeto);

create table marcopagamentoprj (
   idmarcopagamentoprj number(10,0) not null,
   idprojeto number(10,0) not null,
   nomemarcopag varchar2(150) not null,
   dataprevisaopag date not null,
   valorpagamento numeric(15,3) default null,
   situacao char(1) not null,
   dataultalteracao date not null,
   horaultalteracao varchar2(4) not null,
   usuarioultalteracao  varchar2(40) not null
);

alter table marcopagamentoprj add constraint pk_marcopagamentoprj primary key (idmarcopagamentoprj);

create table pagamentoprojeto (
   idpagamentoprojeto number(10,0) not null,
   idprojeto number(10,0) default null,
   datapagamento date not null,
   valorpagamento numeric(15,3) not null,
   valorglosa numeric(15,3) default null,
   detpagamento clob default null,
   situacao char(1) not null,
   dataultalteracao date not null,
   horaultalteracao varchar2(4) not null,
   usuarioultalteracao varchar2(40) not null
);

alter table pagamentoprojeto add constraint pk_pagamentoprojeto primary key (idpagamentoprojeto);

create table perfilcontrato (
   idperfilcontrato number(10,0) not null,
   idcontrato number(10,0) not null,
   nomeperfilcontrato varchar2(100) not null,
   custohora numeric(15,3) default null,
   deleted char(1) default null
);

alter table perfilcontrato add constraint pk_perfilcontrato primary key (idperfilcontrato);

create table produtocontrato (
   idprodutocontrato number(10,0) not null,
   idcontrato number(10,0) default null,
   nomeproduto varchar2(120) not null,
   deleted char(1) default null
);

alter table produtocontrato add constraint pk_produtocontrato primary key (idprodutocontrato);

create table produtotarefalinbaseproj (
   idtarefalinhabaseprojeto number(10,0) not null,
   idprodutocontrato number(10,0) not null
);

alter table produtotarefalinbaseproj add constraint pk_produtotarefalinbaseproj primary key (idtarefalinhabaseprojeto, idprodutocontrato);

create table recursoprojeto (
   idprojeto number(10,0) not null,
   idempregado number(10,0) not null,
   custohora numeric(15,3) default null
);

alter table recursoprojeto add constraint pk_recursoprojeto primary key (idprojeto, idempregado);

create table recursotarefalinbaseproj (
   idrecursotarefalinbaseproj number(10,0) not null,
   idtarefalinhabaseprojeto number(10,0) not null,
   idperfilcontrato number(10,0) not null,
   idempregado number(10,0) default null,
   percentualaloc numeric(6,2) null,
   tempoaloc varchar2(4) default null,
   percentualexec numeric(6,2) default null,
   tempoalocminutos numeric(15,3) default null,
   custo numeric(15,3) default null,
   custoperfil numeric(15,3) default null
);

alter table recursotarefalinbaseproj add constraint pk_recursotarefalinbaseproj primary key (idrecursotarefalinbaseproj);

create table tarefalinhabaseprojeto (
   idtarefalinhabaseprojeto number(10,0) not null,
   idlinhabaseprojeto number(10,0) not null,
   idcalendario number(10,0) default null,
   idtarefalinhabaseprojetovinc number(10,0) default null,
   idtarefalinhabaseprojetomigr number(10,0) default null,
   idtarefalinhabaseprojetopai number(10,0) default null,
   idpagamentoprojeto number(10,0) default null,
   idmarcopagamentoprj number(10,0) default null,
   sequencia number(10,0) not null,
   nometarefa varchar2(4000) not null,
   detalhamentotarefa clob default null,
   codetarefa varchar2(40) default null,
   progresso numeric(6,2) default null,
   datainicio date not null,
   datafim date not null,
   duracaohora numeric(15,3) default null,
   nivel number(10,0) default null,
   idinternal varchar2(40) not null,
   collapsed char(1) default null,
   custo numeric(15,3) default null,
   custoperfil numeric(15,3) default null,
   situacao char(1) not null,
   trabalho numeric(7,2) default null,
   datainicioreal date default null,
   datafimreal date default null,
   duracaohorareal numeric(15,3) default null,
   custoreal numeric(15,3) default null,
   custorealperfil numeric(15,3) default null,
   estimada char(1) not null,
   tempototalocminutos numeric(15,3) default null
);

alter table tarefalinhabaseprojeto add constraint pk_tarefalinhabaseprojeto primary key (idtarefalinhabaseprojeto);

create table templateimpressao (
   idtemplateimpressao number(10,0) not null,
   nometemplate varchar2(100) not null,
   htmlcabecalho clob null,
   htmlcorpo clob not null,
   htmlrodape clob null,
   idtipotemplateimp number(10,0) default null,
   tamcabecalho number(10,0) default null,
   tamrodape number(10,0) default null
);

alter table templateimpressao add constraint pk_templateimpressao primary key (idtemplateimpressao);

create table timesheetprojeto (
   idtimesheetprojeto number(10,0) not null,
   idrecursotarefalinbaseproj number(10,0) not null,
   datahorareg timestamp not null,
   data date not null,
   hora varchar2(5) not null,
   qtdehoras numeric(6,2) not null,
   custo numeric(18,3) default null,
   detalhamento clob default null,
   percexecutado numeric(6,2) default null
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

alter table projetos add idos number(10,0);

alter table projetos add sigla varchar2(50);

alter table projetos add emergencial char(1);

alter table projetos add severidade char(1);

alter table projetos add nomegestor varchar2(50);

alter table projetos add idrequisicaomudanca number(10,0);

alter table projetos add idliberacao number(10,0);

alter table projetos add constraint fk_proj_ref_os foreign key (idos) references os (idos);
	  
alter table projetos add constraint fk_proj_ref_mudanc foreign key (idrequisicaomudanca) references requisicaomudanca (idrequisicaomudanca);
	  
alter table projetos add constraint fk_proj_ref_liber foreign key (idliberacao) references liberacao (idliberacao);

-- Fim Pedro/Lino Emauri

-- Início Bruno 19/07/13

ALTER TABLE requisicaomudanca ADD (idcategoriasolucao number(10,0));

ALTER TABLE liberacao ADD (idcategoriasolucao number(10,0));

-- Fim Bruno

-- Início Danillo Sardinha 19/07/13

create table historicomudanca (
  idhistoricomudanca number(10,0) not null,
  datahoramodificacao timestamp,
  idexecutormodificacao number(10,0) not null,
  tipomodificacao varchar2(1) default null,
  historicoversao double precision,
  baseline varchar2(30),
  idrequisicaomudanca number(10,0) not null,
  idproprietario number(10,0) not null,
  idsolicitante number(10,0) not null,
  idtipomudanca number(10,0),
  idgruponivel1 number(10,0),
  idgrupoatual number(10,0),
  idcalendario number(10,0),
  motivo varchar2(255) default null,
  nivelimportancianegocio varchar2(255) default null,
  classificacao varchar2(255) default null,
  nivelimpacto varchar2(255) default null,
  analiseimpacto varchar2(3000) default null,
  datahoraconclusao timestamp,
  dataaceitacao date,
  datavotacao date,
  datahorainicio timestamp,
  datahoratermino timestamp,
  titulo varchar2(255) default null,
  descricao varchar2(255) default null,
  risco varchar2(255) default null,
  estimativacusto double precision,
  planoreversao varchar2(3000) default null,
  status varchar2(45) default null,
  prioridade number(10,0),
  enviaemailcriacao varchar2(1) default null,
  enviaemailfinalizacao varchar2(1) default null,
  enviaemailacoes varchar2(1) default null,
  exibirquadromudancas varchar2(1) default null,
  seqreabertura number(5,0),
  datahoracaptura timestamp,
  datahorareativacao timestamp,
  datahorasuspensao timestamp,
  tempodecorridohh number(5,0),
  tempodecorridomm number(5,0),
  prazohh number(5,0),
  prazomm number(5,0),
  tempoatendimentohh number(5,0),
  tempoatendimentomm number(5,0),
  tempoatrasohh number(5,0),
  tempoatrasomm number(5,0),
  tempocapturahh number(5,0),
  tempocapturamm number(5,0),
  fase varchar2(20) default null,
  nivelurgencia varchar2(255) default null,
  idbaseconhecimento number(10,0),
  nomecategoriamudanca varchar2(15) default null,
  idcontrato number(10,0) not null,
  idunidade number(10,0),
  idcontatorequisicaomudanca number(10,0),
  idgrupocomite number(10,0),
  enviaemailgrupocomite varchar2(1) default null,
  datahorainicioagendada timestamp,
  datahoraterminoagendada timestamp,
  idlocalidade number(10,0),
  fechamento clob,
  tipo varchar2(255) default null,
  razaomudanca varchar2(200),
  ehpropostaaux char(1),
  votacaopropostaaprovadaaux char(1),
  idgrupoatvperiodica number(10,0)
);

alter table historicomudanca add constraint pk_historicomudanca primary key (idhistoricomudanca);

create table ligacao_mud_hist_resp (
    idligacao_mud_hist_resp number(10,0) not null,
    idrequisicaomudancaresp number(10,0),
    idrequisicaomudanca number(10,0),
    idhistoricomudanca number(10,0)
);

alter table requisicaomudancaresponsavel add datafim date default null;

create table ligacao_mud_hist_ic (
    idligacao_mud_hist_ic number(10,0) not null,
    idrequisicaomudancaitemconfig number(10,0),
    idrequisicaomudanca number(10,0),
    idhistoricomudanca number(10,0)
);

alter table requisicaomudancaitemconfigura add datafim date default null;

create table ligacao_mud_hist_se (
    idligacao_mud_hist_se number(10,0) not null,
    idrequisicaomudancaservico number(10,0),
    idrequisicaomudanca number(10,0),
    idhistoricomudanca number(10,0)
);

alter table requisicaomudancaservico add datafim date default null;

create table ligacao_mud_hist_pr (
    idligacao_mud_hist_pr number(10,0) not null,
    idproblemamudanca number(10,0),
    idrequisicaomudanca number(10,0),
    idhistoricomudanca number(10,0)
);

alter table problemamudanca add datafim date default null;

create table ligacao_mud_hist_risco(
    idligacao_mud_hist_risco number(10,0) not null,
    idrequisicaomudancarisco number(10,0),
    idrequisicaomudanca number(10,0),
    idhistoricomudanca number(10,0)
);

alter table requisicaomudancarisco add datafim date default null;

alter table aprovacaomudanca add idhistoricomudanca number(10,0) default null;

alter table solicitacaoservicomudanca add idhistoricomudanca number(10,0) default null;

alter table liberacaomudanca add idhistoricomudanca number(10,0) default null;

-- Fim Danillo Sardinha

-- Início Murilo Pacheco

alter table historicomudanca add(registroexecucao varchar2(250));

alter table historicomudanca add(emailsolicitante varchar2(250));

alter table historicomudanca add(alterarsituacao varchar2(250));

alter table historicomudanca add(acaofluxo varchar2(250));

alter table controleged add(versao varchar2(250));

-- Fim Murilo Pacheco

-- Início Geber 22/07/13

create table formapagamento(
  idformapagamento number(24,0),
  nomeformapagamento varchar2(100 char),
  situacao char(1 char)
);

alter table formapagamento add constraint formapagamento_pkey primary key (idformapagamento);

-- Fim Geber

-- Início Ronnie 24/07/13

create table tipomovimfinanceiraviagem (
	idtipomovimfinanceiraviagem number(10,0) not null,
	nome varchar2(100) not null,
	descricao clob null,
	classificacao varchar2(30) not null,
	situacao char(1) not null,
	permiteadiantamento char(1) not null,
	exigeprestacaoconta char(1) not null,
	exigejustificativa char(1) not null,
	valorpadrao numeric(8,2) null,
	tipo char(1) not null
);

alter table tipomovimfinanceiraviagem add constraint tipomovimfinanceiraviagem_pkey primary key (idtipomovimfinanceiraviagem);

-- Fim Ronnie

-- Início Geber 24/07/13

alter table justificativasolicitacao add (viagem char(1 char));

-- Fim Geber

-- Início Thiago Fernandes 26/07/13

update bpm_elementofluxo set grupos = '#{requisicaoMudanca.nomeGrupoAtual}' where idelemento in (415, 416, 417, 418, 185, 186, 187, 188, 405, 406, 410, 411);

update bpm_fluxo set variaveis = 'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual' where idtipofluxo in (6,7);

update bpm_elementofluxo set documentacao = 'Avaliar' where idelemento in (185, 406, 411, 418);

update bpm_elementofluxo set documentacao = 'Aprovar' where idelemento in (186, 415);

update bpm_elementofluxo set documentacao = 'Planejar' where idelemento in (187, 416);

update bpm_elementofluxo set documentacao = 'Executar' where idelemento in (188, 410, 417, 405);

-- Fim Thiago Fernandes

-- Início Thiago Fernandes 29/07/13

update bpm_elementofluxo set script = '#{execucaoFluxo}.encerra()' where idelemento in (407, 412);

-- Fim Thiago Fernandes

-- Início Thays 29/07/13

update justificativasolicitacao set viagem = 'n' where idjustificativa > 0;

create table requisicaoviagem (
   idsolicitacaoservico number(10,0) not null,
   idcidadeorigem number(10,0) not null,
   idcidadedestino number(10,0) not null,
   idprojeto number(10,0) not null,
   idcentrocusto number(10,0) not null,
   idmotivoviagem number(10,0) not null,
   idaprovacao number(10,0),
   descricaomotivo clob not null,
   datainicio date not null,
   datafim date not null,
   qtdedias number(10,0) not null,
   estado varchar2(30) not null
);

alter table requisicaoviagem add constraint requisicaoviagem_pkey primary key (idsolicitacaoservico);

alter table requisicaoviagem add constraint fk_req_solicitacaoservico foreign key (idsolicitacaoservico) references solicitacaoservico (idsolicitacaoservico);

alter table requisicaoviagem add constraint fk_req_cidadeorigem foreign key (idcidadeorigem) references cidades (idcidade);

alter table requisicaoviagem add constraint fk_req_reference_cidadedestino foreign key (idcidadedestino) references cidades (idcidade);

alter table requisicaoviagem add constraint fk_req_centroresultado foreign key (idcentrocusto) references centroresultado (idcentroresultado);

alter table requisicaoviagem add constraint fk_req_projetos foreign key (idprojeto) references projetos (idprojeto);

alter table requisicaoviagem add constraint fk_req_justsolicitacao foreign key (idmotivoviagem) references justificativasolicitacao (idjustificativa);

alter table requisicaoviagem add constraint fk_req_parecer foreign key (idaprovacao) references parecer (idparecer);

create table integranteviagem (
   idsolicitacaoservico number(10,0) not null,
   idempregado number(10,0) not null,
   primary key (idsolicitacaoservico, idempregado)
);

alter table integranteviagem add constraint fk_int_solicitacaoservico foreign key (idsolicitacaoservico) references requisicaoviagem (idsolicitacaoservico);

alter table integranteviagem add constraint fk_int_reference_empregados foreign key (idempregado) references empregados (idempregado);

-- Fim Thays

-- Início Bruno 01/08/13

alter table tipomudanca add (exigeaprovacao char(1));

-- Fim Bruno

-- Início Bruno 02/08/13

alter table tarefalinhabaseprojeto add (depends varchar2(40));

-- Fim Bruno


-- Início Thiago Matias 09/08/13

alter table infocatalogoservico add idServicoContrato number(10,0) not null;

alter table infocatalogoservico add nomeServicoContrato varchar2(500) null;

-- Fim Thiago Matias

-- Início Thiago Matias 21/08/13

alter table infocatalogoservico rename column idservicocontrato TO idservicocatalogo;

alter table infocatalogoservico modify (idservicocatalogo null);

-- Fim Thiago Matias

-- Início Flávio 21/08/13

CREATE TABLE PEDIDOPORTAL (
  IDPEDIDOPORTAL NUMBER NOT NULL , 
  IDUSUARIO NUMBER , 
  DATAPEDIDO DATE , 
  PRECOTOTAL DOUBLE PRECISION ,
  STATUS VARCHAR2(254) , 
  CONSTRAINT PEDIDOPORTAL_PK 
  PRIMARY KEY   (IDPEDIDOPORTAL) ENABLE 
);

ALTER TABLE PEDIDOPORTAL ADD CONSTRAINT PEDIDOPORTAL_USUARIO_FK1 FOREIGN KEY (IDUSUARIO) REFERENCES USUARIO (IDUSUARIO) ENABLE;

CREATE TABLE ITEMPEDIDOPORTAL (
  IDITEMPEDIDOPORTAL NUMBER NOT NULL , 
  IDPEDIDOPORTAL NUMBER , 
  IDSOLICITACAOSERVICO NUMBER ,
  VALOR DOUBLE PRECISION , 
  CONSTRAINT ITEMPEDIDOPORTAL_PK PRIMARY KEY   (IDITEMPEDIDOPORTAL) ENABLE 
 );

ALTER TABLE ITEMPEDIDOPORTAL ADD CONSTRAINT ITEMPEDIDOPORTAL_PEDIDOPO_FK1 FOREIGN KEY (IDPEDIDOPORTAL)REFERENCES PEDIDOPORTAL (IDPEDIDOPORTAL) ENABLE;

ALTER TABLE ITEMPEDIDOPORTAL ADD CONSTRAINT ITEMPEDIDOPORTAL_SOLICITA_FK1 FOREIGN KEY (  IDSOLICITACAOSERVICO ) REFERENCES SOLICITACAOSERVICO (IDSOLICITACAOSERVICO) ENABLE;

-- Fim Flavio

-- Início Mário  30/08/13

alter table itemconfiguracao ADD  (idcontrato number(10,0) NULL);

alter table itemconfiguracao ADD  (idresponsavel number(10,0)  NULL);

alter table itemconfiguracao ADD  (ativofixo varchar2(255) NULL);

-- Fim Mário
