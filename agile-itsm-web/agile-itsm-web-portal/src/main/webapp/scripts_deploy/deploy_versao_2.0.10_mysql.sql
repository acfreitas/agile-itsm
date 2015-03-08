-- MySQL

set sql_safe_updates = 0;

-- Início Thays 08/07/13

alter table tipomudanca add column  impacto char(1) NULL;

alter table tipomudanca add column  urgencia char(1) NULL;

-- Fim Thays

-- Início Thays 09/07/13

insert into categoriaproblema (idcategoriaproblema, cat_idcategoriaproblema, idcategoriaproblemapai, nomecategoria, idtipofluxo, idgrupoexecutor, datainicio, datafim, nomecategoriaproblema, idtemplate) values (1, null, null, null, 50, 1, '2013-07-09', null, 'Categoria Padrão', null);

-- Fim Thays

-- Início Cledson 09/07/13

ALTER TABLE ccmodulosistema DROP PRIMARY KEY;

ALTER TABLE ccmodulosistema ADD FOREIGN KEY (idmodulosistema) REFERENCES modulosistema(idmodulosistema);

-- Fim Cledson

-- Início Riubbe 09/07/13

alter table permissoesfluxo add column cancelar char(1) NULL;

-- Fim Riubbe

-- Início Bruno 09/07/13

ALTER TABLE requisicaomudanca ADD idgrupoatvperiodica INT NULL;

ALTER TABLE liberacao ADD idgrupoatvperiodica INT NULL;

-- Fim Bruno

set sql_safe_updates = 1;

-- Início Thiago Fernandes 15/07/13

alter table requisicaoliberacaocompras add iditemrequisicaoproduto integer;

-- Fim Thiago Fernandes

-- Início Bruno 16/07/13

CREATE TABLE controlerendimento (
  idcontrolerendimento int(11) NOT NULL,
  idgrupo int(11) NOT NULL,
  idpessoa int(11) NULL,
  mesapuracao varchar(100)NOT NULL,
  anoapuracao bigint(20) DEFAULT NULL,
  datahoraexecucao date NOT NULL,
  aprovado varchar(1)NOT NULL,
  qtdpontospositivos int(11) NULL,
  qtdpontosnegativos int(11) NULL,
  qtdsolicitacoes int(11) NULL,
  qtdpontos int(11) NULL,
  mediarelativa varchar(100) NULL,
  PRIMARY KEY (idcontrolerendimento),
  constraint foreign key (idgrupo) references grupo (idgrupo)
) ENGINE=InnoDB;

CREATE TABLE controlerendimentogrupo (
  idcontrolerendimentogrupo int(11) NOT NULL,
  idcontrolerendimento int(11) NOT NULL,
  idgrupo int(11) NOT NULL,
  PRIMARY KEY (idcontrolerendimentogrupo),
  constraint foreign key (idcontrolerendimento) references controlerendimento (idcontrolerendimento),
  constraint foreign key (idgrupo) references grupo (idgrupo)
) ENGINE=InnoDB;

CREATE TABLE controlerendimentousuario (
  idcontrolerendimentousuario int(11) NOT NULL,
  idcontrolerendimento int(11) NOT NULL,
  idgrupo int(11) NOT NULL,
  idusuario int(11) NOT NULL,
  qtdtotalpontos varchar(45),
  aprovacao varchar(45),
  ano varchar(45),
  mes varchar(45),
  qtdpontospositivos varchar(45),
  qtdpontosnegativos varchar(45),
  qtditensentregues varchar(45),
  qtditensretornados varchar(45),
  PRIMARY KEY (idcontrolerendimentousuario),
  constraint foreign key (idcontrolerendimento) references controlerendimento (idcontrolerendimento),
  constraint foreign key (idusuario) references usuario (idusuario),
  constraint foreign key (idgrupo) references grupo (idgrupo)
) ENGINE=InnoDB;

-- Fim

-- Início Bruno 17/07/13

alter table categoriaproblema add column  impacto char(1) NULL;

alter table categoriaproblema add column  urgencia char(1) NULL;

-- Fim Bruno

-- Início Pedro Lino/Emauri 19/07/13

create table linhabaseprojeto (
  idlinhabaseprojeto int(11) not null,
  idprojeto int(11) not null,
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
) engine= InnoDB;

alter table linhabaseprojeto add constraint pk_linhabaseprojeto primary key (idlinhabaseprojeto);

create table marcopagamentoprj (
   idmarcopagamentoprj int(11) not null,
   idprojeto int(11) not null,
   nomemarcopag varchar(150) not null,
   dataprevisaopag date not null,
   valorpagamento decimal(15,3) default null,
   situacao char(1) not null,
   dataultalteracao date not null,
   horaultalteracao varchar(4) not null,
   usuarioultalteracao  varchar(40) not null
) engine= InnoDB;

alter table marcopagamentoprj add constraint pk_marcopagamentoprj primary key (idmarcopagamentoprj);

create table pagamentoprojeto (
   idpagamentoprojeto int(11) not null,
   idprojeto int(11) default null,
   datapagamento date not null,
   valorpagamento decimal(15,3) not null,
   valorglosa decimal(15,3) default null,
   detpagamento text default null,
   situacao char(1) not null,
   dataultalteracao date not null,
   horaultalteracao varchar(4) not null,
   usuarioultalteracao varchar(40) not null
) engine= InnoDB;

alter table pagamentoprojeto add constraint pk_pagamentoprojeto primary key (idpagamentoprojeto);

create table perfilcontrato (
   idperfilcontrato int(11) not null,
   idcontrato int(11) not null,
   nomeperfilcontrato varchar(100) not null,
   custohora decimal(15,3) default null,
   deleted char(1) default null
) engine= InnoDB;

alter table perfilcontrato add constraint pk_perfilcontrato primary key (idperfilcontrato);

create table produtocontrato (
   idprodutocontrato int(11) not null,
   idcontrato int(11) default null,
   nomeproduto varchar(120) not null,
   deleted char(1) default null
) engine= InnoDB;

alter table produtocontrato add constraint pk_produtocontrato primary key (idprodutocontrato);

create table produtotarefalinbaseproj (
   idtarefalinhabaseprojeto int(11) not null,
   idprodutocontrato int(11) not null
) engine= InnoDB;

alter table produtotarefalinbaseproj add constraint pk_produtotarefalinbaseproj primary key (idtarefalinhabaseprojeto, idprodutocontrato);

create table recursoprojeto (
   idprojeto int(11) not null,
   idempregado int(11) not null,
   custohora decimal(15,3) default null
) engine= InnoDB;

alter table recursoprojeto add constraint pk_recursoprojeto primary key (idprojeto, idempregado);

create table recursotarefalinbaseproj (
   idrecursotarefalinbaseproj int(11) not null,
   idtarefalinhabaseprojeto int(11) not null,
   idperfilcontrato int(11) not null,
   idempregado int(11) default null,
   percentualaloc decimal(6,2) null,
   tempoaloc varchar(4) default null,
   percentualexec decimal(6,2) default null,
   tempoalocminutos decimal(15,3) default null,
   custo decimal(15,3) default null,
   custoperfil decimal(15,3) default null
) engine= InnoDB;

alter table recursotarefalinbaseproj add constraint pk_recursotarefalinbaseproj primary key (idrecursotarefalinbaseproj);

create table tarefalinhabaseprojeto (
   idtarefalinhabaseprojeto int(11) not null,
   idlinhabaseprojeto int(11) not null,
   idcalendario int(11) default null,
   idtarefalinhabaseprojetovinc int(11) default null,
   idtarefalinhabaseprojetomigr int(11) default null,
   idtarefalinhabaseprojetopai int(11) default null,
   idpagamentoprojeto int(11) default null,
   idmarcopagamentoprj int(11) default null,
   sequencia int(11) not null,
   nometarefa varchar(4000) not null,
   detalhamentotarefa text default null,
   codetarefa varchar(40) default null,
   progresso decimal(6,2) default null,
   datainicio date not null,
   datafim date not null,
   duracaohora decimal(15,3) default null,
   nivel int(11) default null,
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
) engine= InnoDB;

alter table tarefalinhabaseprojeto add constraint pk_tarefalinhabaseprojeto primary key (idtarefalinhabaseprojeto);

create table templateimpressao (
   idtemplateimpressao int(11) not null,
   nometemplate varchar(100) not null,
   htmlcabecalho text null,
   htmlcorpo text not null,
   htmlrodape text null,
   idtipotemplateimp int(11) default null,
   tamcabecalho int(11) default null,
   tamrodape int(11) default null
) engine= InnoDB;

alter table templateimpressao add constraint pk_templateimpressao primary key (idtemplateimpressao);

create table timesheetprojeto (
   idtimesheetprojeto int(11) not null,
   idrecursotarefalinbaseproj int(11) not null,
   datahorareg timestamp not null,
   data date not null,
   hora varchar(5) not null,
   qtdehoras decimal(6,2) not null,
   custo decimal(18,3) default null,
   detalhamento text default null,
   percexecutado decimal(6,2) default null
) engine= InnoDB;

alter table timesheetprojeto add constraint pk_timesheetprojeto primary key (idtimesheetprojeto);


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

alter table projetos add idos int(11);

alter table projetos add sigla varchar(50);

alter table projetos add emergencial char(1);

alter table projetos add severidade char(1);

alter table projetos add nomegestor varchar(50);

alter table projetos add idrequisicaomudanca int(11);

alter table projetos add idliberacao int(11);

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
  idhistoricomudanca int(11) not null,
  datahoramodificacao timestamp,
  idexecutormodificacao int(11) not null,
  tipomodificacao varchar(1) default null,
  historicoversao double,
  baseline varchar(30),
  idrequisicaomudanca int(11) not null,
  idproprietario int(11) not null,
  idsolicitante int(11) not null,
  idtipomudanca int(11),
  idgruponivel1 int(11),
  idgrupoatual int(11),
  idcalendario int(11),
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
  estimativacusto double,
  planoreversao varchar(3000) default null,
  status varchar(45) default null,
  prioridade int(11),
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
  idbaseconhecimento int(11),
  nomecategoriamudanca varchar(15) default null,
  idcontrato int(11) not null,
  idunidade int(11),
  idcontatorequisicaomudanca int(11),
  idgrupocomite int(11),
  enviaemailgrupocomite varchar(1) default null,
  datahorainicioagendada timestamp,
  datahoraterminoagendada timestamp,
  idlocalidade int(11),
  fechamento text,
  tipo varchar(255) default null,
  razaomudanca varchar(200),
  ehpropostaaux char(1),
  votacaopropostaaprovadaaux char(1),
  idgrupoatvperiodica int(11)
) engine= InnoDB;

alter table historicomudanca add constraint pk_historicomudanca primary key (idhistoricomudanca);

create table ligacao_mud_hist_resp (
    idligacao_mud_hist_resp int(11) not null,
    idrequisicaomudancaresp int(11),
    idrequisicaomudanca int(11),
    idhistoricomudanca int(11)
);

alter table requisicaomudancaresponsavel add column datafim date default null;

create table ligacao_mud_hist_ic (
    idligacao_mud_hist_ic int(11) not null,
    idrequisicaomudancaitemconfiguracao int(11),
    idrequisicaomudanca int(11),
    idhistoricomudanca int(11)
);

alter table requisicaomudancaitemconfiguracao add column datafim date default null;

create table ligacao_mud_hist_se (
    idligacao_mud_hist_se int(11) not null,
    idrequisicaomudancaservico int(11),
    idrequisicaomudanca int(11),
    idhistoricomudanca int(11)
);

alter table requisicaomudancaservico add column datafim date default null;

create table ligacao_mud_hist_pr (
    idligacao_mud_hist_pr int(11) not null,
    idproblemamudanca int(11),
    idrequisicaomudanca int(11),
    idhistoricomudanca int(11)
);

alter table problemamudanca add column datafim date default null;

create table ligacao_mud_hist_risco(
    idligacao_mud_hist_risco int(11) not null,
    idrequisicaomudancarisco int(11),
    idrequisicaomudanca int(11),
    idhistoricomudanca int(11)
);

alter table requisicaomudancarisco add column datafim date default null;

alter table aprovacaomudanca add column idhistoricomudanca int(11) default null;

alter table solicitacaoservicomudanca add column idhistoricomudanca int(11) default null;

alter table liberacaomudanca add column idhistoricomudanca int(11) default null;

-- Fim Danillo Sardinha

-- Início Murilo Pacheco 22/07/13

alter table historicomudanca add registroexecucao varchar(250);

alter table historicomudanca add emailsolicitante varchar(250);

alter table historicomudanca add alterarsituacao varchar(250);

alter table historicomudanca add acaofluxo varchar(250);

alter table controleged add versao varchar(250);

-- Fim Murilo Pacheco

-- Início Geber 22/07/13

create table formapagamento(
  idformapagamento int(11) not null,
  nomeformapagamento varchar(100) not null,
  situacao char(1)
);

alter table formapagamento add constraint formapagamento_pkey primary key (idformapagamento);

-- Fim Geber

-- Início Ronnie 24/07/13

create table tipomovimfinanceiraviagem (
  idtipomovimfinanceiraviagem int(11) not null,
  nome varchar(100) not null,
  descricao text null,
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
)engine=InnoDB;

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
)engine=InnoDB;

alter table integranteviagem add constraint fk_integranteviagem_reference_solicitacaoservico foreign key (idsolicitacaoservico) references requisicaoviagem (idsolicitacaoservico);

alter table integranteviagem add constraint fk_integranteviagem_reference_empregados foreign key (idempregado) references empregados (idempregado);

-- Fim Thays

-- Início Bruno 01/08/13

alter table tipomudanca add column exigeaprovacao char(1);

-- Fim Bruno

-- Início Bruno 02/08/13

alter table tarefalinhabaseprojeto add column depends varchar(40);

-- Fim Bruno

-- Início Thiago Matias 09/08/13

alter table infocatalogoservico add idServicoContrato integer not null;

alter table infocatalogoservico add nomeServicoContrato varchar(500) null;

-- Fim Thiago Matias

-- Início Thiago Matias 21/08/13

alter table infocatalogoservico change column idservicocontrato idservicocatalogo int(11) null;

-- Fim Thiago Matias

-- Início Flávio  21/08/13

CREATE  TABLE pedidoportal (
  idpedidoportal INT NOT NULL ,
  idusuario INT NULL ,
  datapedido DATE NULL ,
  precototal DOUBLE NULL ,
  status VARCHAR(254) NULL ,
  PRIMARY KEY (idpedidoportal) ,
  CONSTRAINT rel_pedidosolicitacao_usuario
    FOREIGN KEY (idusuario )
    REFERENCES usuario (idusuario )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE  TABLE itempedidoportal (
  iditempedidoportal INT NOT NULL ,
  idpedidoportal INT NULL ,
  idsolicitacaoservico BIGINT NULL ,
  valor DOUBLE NULL ,
  PRIMARY KEY (iditempedidoportal) ,
  INDEX rel_pedido_itempedido_idx (idpedidoportal ASC) ,
  CONSTRAINT rel_solicitacao_itempedido
    FOREIGN KEY (idsolicitacaoservico )
    REFERENCES solicitacaoservico (idsolicitacaoservico )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT rel_pedido_itempedido
    FOREIGN KEY (idpedidoportal )
    REFERENCES pedidoportal (idpedidoportal )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- Fim Flávio

-- Início Mário 30/08/13

alter table itemconfiguracao ADD  idcontrato INT NULL;

alter table itemconfiguracao ADD  idresponsavel INT NULL;

alter table itemconfiguracao ADD  ativofixo VARCHAR(255) NULL;

-- Fim Mário
