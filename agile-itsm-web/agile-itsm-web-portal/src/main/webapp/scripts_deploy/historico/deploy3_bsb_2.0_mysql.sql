ALTER TABLE atividadeperiodica ADD idrequisicaomudanca INT;
alter table atividadeperiodica add constraint fk_ref_reqmud foreign key (idrequisicaomudanca)
      references requisicaomudanca (idrequisicaomudanca);
ALTER TABLE atividadeperiodica ADD blackout CHAR(1);

alter table problema add idgrupo INT;
alter table problema add idservico INT;
alter table problema add idcontrato INT;
alter table problema add idservicocontrato INT;
alter table problema add idprioridade INT;
alter table problema add datahoralimite timestamp;
alter table problema add datahorasolicitacao timestamp;
alter table problema add prazoHH INT;
alter table problema add prazoMM INT;
alter table problema add slaACombinar CHAR(1);

CREATE TABLE `servico_hist` (
  `idhistoricoservico` bigint(20) NOT NULL,
  `idservico` bigint(20) NOT NULL,
  `idcategoriaservico` int(11) NOT NULL,
  `idsituacaoservico` int(11) NOT NULL,
  `idtiposervico` int(11) DEFAULT NULL,
  `idimportancianegocio` int(11) DEFAULT NULL,
  `idempresa` bigint(20) NOT NULL,
  `idtipoeventoservico` int(11) DEFAULT NULL,
  `idtipodemandaservico` int(11) DEFAULT NULL,
  `idlocalexecucaoservico` int(11) DEFAULT NULL,
  `nomeservico` varchar(500) NOT NULL,
  `detalheservico` text,
  `objetivo` text,
  `passosservico` text,
  `datainicio` date DEFAULT NULL,
  `linkprocesso` varchar(500) DEFAULT NULL,
  `descricaoprocesso` text,
  `tipodescprocess` char(1) DEFAULT NULL,
  `dispportal` char(1) DEFAULT NULL,
  `quadroorientportal` text,
  `deleted` char(1) DEFAULT NULL,
  `detalhesServico` varchar(255) DEFAULT NULL,
  `siglaAbrev` varchar(150) DEFAULT NULL,
  `idbaseconhecimento` int(11) DEFAULT NULL,
  `idtemplatesolicitacao` int(11) DEFAULT NULL,
  `idtemplateacompanhamento` int(11) DEFAULT NULL,
  PRIMARY KEY (`idhistoricoservico`)
) ENGINE=InnoDB;

alter table servico_hist add criadoEm Timestamp;
alter table servico_hist add criadoPor varchar(25);
alter table servico_hist add modificadoEm Timestamp;
alter table servico_hist add modificadoPor varchar(25);
alter table servico_hist add conteudodados text;

alter table acordonivelservico add tempoauto DECIMAL(15,3);
alter table acordonivelservico add idprioridadeauto1 INT;
alter table acordonivelservico add idprioridadeauto2 INT;
alter table acordonivelservico add idprioridadeauto3 INT;
alter table acordonivelservico add idprioridadeauto4 INT;
alter table acordonivelservico add idprioridadeauto5 INT;
alter table acordonivelservico add idgrupo1 INT;
alter table acordonivelservico add idgrupo2 INT;
alter table acordonivelservico add idgrupo3 INT;
alter table acordonivelservico add idgrupo4 INT;
alter table acordonivelservico add idgrupo5 INT;

create table planomelhoria
(
   idplanomelhoria      int not null,
   idfornecedor         int,
   idcontrato           int,
   titulo               varchar(100) not null,
   datainicio           date not null,
   datafim              date,
   objetivo             text,
   visaogeral           text,
   escopo               text,
   visao                text,
   missao               text,
   datacriacao          date,
   notas                text,
   criadopor            varchar(40),
   modificadopor        varchar(40),
   ultmodificacao       date,
   situacao             char(1),
   primary key (idplanomelhoria)
)ENGINE=InnoDB;

create index ix_pm_forn on planomelhoria
(
   idfornecedor
);

create index ix_pm_contrato on planomelhoria
(
   idcontrato,
   idfornecedor
);

create table objetivoplanomelhoria
(
   idobjetivoplanomelhoria int not null,
   idplanomelhoria      int not null,
   tituloobjetivo       varchar(255) not null,
   detalhamento         text,
   resultadoesperado    text,
   medicao              varchar(255),
   responsavel          varchar(255),
   criadopor            varchar(40),
   modificadopor        varchar(40),
   datacriacao          date,
   ultmodificacao       date,
   primary key (idobjetivoplanomelhoria)
)ENGINE=InnoDB;

create index ix_obj_pm_id on objetivoplanomelhoria
(
   idplanomelhoria
);

alter table objetivoplanomelhoria add constraint fk_ref_objplm foreign key (idplanomelhoria)
      references planomelhoria (idplanomelhoria) on delete restrict on update restrict;

	  
create table acaoplanomelhoria
(
   idacaoplanomelhoria  int not null,
   idplanomelhoria      int not null,
   idobjetivoplanomelhoria int not null,
   tituloacao           varchar(255) not null,
   detalhamentoacao     text,
   datainicio           date,
   datafim              date,
   responsavel          varchar(255),
   dataconclusao        date,
   criadopor            varchar(40),
   modificadopor        varchar(40),
   datacriacao          date,
   ultmodificacao       date,
   primary key (idacaoplanomelhoria)
)ENGINE=InnoDB;

create index ix_act_pm_id on acaoplanomelhoria
(
   idobjetivoplanomelhoria
);

alter table acaoplanomelhoria add constraint fk_ref_plmact foreign key (idplanomelhoria)
      references planomelhoria (idplanomelhoria) on delete restrict on update restrict;

alter table acaoplanomelhoria add constraint fk_ref_objact foreign key (idobjetivoplanomelhoria)
      references objetivoplanomelhoria (idobjetivoplanomelhoria) on delete restrict on update restrict;

create table objetivomonitoramento
(
   idobjetivomonitoramento int not null,
   idobjetivoplanomelhoria int not null,
   titulomonitoramento  varchar(255) not null,
   fatorcriticosucesso  varchar(255),
   kpi                  varchar(255),
   metrica              text,
   medicao              text,
   relatorios           text,
   responsavel          varchar(255),
   criadopor            varchar(40),
   modificadopor        varchar(40),
   datacriacao          date,
   ultmodificacao       date,
   primary key (idobjetivomonitoramento)
)ENGINE=InnoDB;

alter table objetivomonitoramento add constraint fk_ref_objmn foreign key (idobjetivoplanomelhoria)
      references objetivoplanomelhoria (idobjetivoplanomelhoria) on delete restrict on update restrict;

	  
alter table baseconhecimento add gerenciamentoDisponibilidade CHAR(1);
alter table baseconhecimento add direitoAutoral CHAR(1);
alter table baseconhecimento add legislacao CHAR(1);

CREATE TABLE catalogoservico (
  idcatalogoservico int(11) NOT NULL DEFAULT '0',
  idcontrato int(11) DEFAULT NULL,
  datainicio date DEFAULT NULL,
  datafim date DEFAULT NULL,
  obs text,
  nomeservico char(150) DEFAULT NULL,
  titulocatalogoservico varchar(256) DEFAULT NULL,
  PRIMARY KEY (`idcatalogoservico`),
  UNIQUE KEY `ak_key_1_catalago` (`idcatalogoservico`)
) ENGINE=InnoDB;

alter table catalogoservico
add constraint fk_infocata_reference_contratos 
foreign key (idcontrato)
references contratos (idcontrato);

create table infocatalogoservico (
idinfocatalogoservico integer not null,
idcatalogoservico integer,
descinfocatalogoservico text
);

alter table infocatalogoservico
add constraint pk_infocatalogoservico 
primary key (idinfocatalogoservico);

alter table infocatalogoservico
add constraint fk_infocata_reference_catalago 
foreign key (idcatalogoservico)
references catalagoservico (idcatalogoservico);

CREATE TABLE servcontratocatalogoserv (
  idservicocontrato int(11) DEFAULT NULL,
  idcatalogoservico int(11) DEFAULT NULL
) ENGINE=InnoDB;

alter table servcontratocatalogoserv change column idservicocontrato idservicocontrato BIGINT(20);

alter table  servcontratocatalogoserv   add constraint fk_servcontr_reference_servico 
foreign key ( idservicocontrato )  
references  servico  ( idservico );
alter table  servcontratocatalogoserv   add constraint fk_servcontr_reference_catalogoservico 
foreign key ( idcatalogoservico )  
references  catalogoservico  ( idcatalogoservico );

CREATE  TABLE conhecimentoic (
  iditemconfiguracao INT(11) NOT NULL ,
  idbaseconhecimento INT(11) NOT NULL ,
  PRIMARY KEY (iditemconfiguracao, idbaseconhecimento) ) ENGINE=InnoDB;
      
    alter table conhecimentoic
       add constraint fk_ref_conhic_icc foreign key (iditemconfiguracao)
          references itemconfiguracao (iditemconfiguracao);
    alter table conhecimentoic
       add constraint fk_ref_conhic_bc foreign key (idbaseconhecimento)
          references baseconhecimento (idbaseconhecimento);


CREATE  TABLE conhecimentoproblema (
  idproblema INT(11) NOT NULL ,
  idbaseconhecimento INT(11) NOT NULL ,
  PRIMARY KEY (idproblema, idbaseconhecimento) ) ENGINE=InnoDB;
    alter table conhecimentoproblema
       add constraint fk_ref_conhpb_icc foreign key (idproblema)
          references problema (idproblema);
    alter table conhecimentoproblema
       add constraint fk_ref_conhpb_bc foreign key (idbaseconhecimento)
          references baseconhecimento (idbaseconhecimento);

  
  CREATE  TABLE conhecimentomudanca (
  idrequisicaomudanca INT(11) NOT NULL ,
  idbaseconhecimento INT(11) NOT NULL ,
  PRIMARY KEY (idrequisicaomudanca, idbaseconhecimento) ) ENGINE=InnoDB;
    alter table conhecimentomudanca
       add constraint fk_ref_conhmd_icc foreign key (idrequisicaomudanca)
          references requisicaomudanca (idrequisicaomudanca);
    alter table conhecimentomudanca
       add constraint fk_ref_conhmud_bc foreign key (idbaseconhecimento)
          references baseconhecimento (idbaseconhecimento);  

  CREATE  TABLE conhecimentosolicitacaoservico (
  idsolicitacaoservico BIGINT(20) NOT NULL ,
  idbaseconhecimento INT(11) NOT NULL ,
  PRIMARY KEY (idsolicitacaoservico, idbaseconhecimento) ) ENGINE=InnoDB;
    alter table conhecimentosolicitacaoservico
       add constraint fk_ref_conhss_icc foreign key (idsolicitacaoservico)
          references solicitacaoservico (idsolicitacaoservico);
    alter table conhecimentosolicitacaoservico
       add constraint fk_ref_conhss_bc foreign key (idbaseconhecimento)
          references baseconhecimento (idbaseconhecimento);

alter table itemrequisicaoproduto add valoraprovado decimal(8,2);

alter table cotacaoitemrequisicao add iditemtrabalho int;
alter table cotacaoitemrequisicao add constraint fk_reference_699 foreign key (iditemtrabalhoaprovacao)
      references bpm_itemtrabalhofluxo (iditemtrabalho) on delete restrict on update restrict;

ALTER TABLE `inspecaoentregaitem` CHANGE COLUMN `avaliacao` `avaliacao` VARCHAR(25) NULL DEFAULT NULL  ;

ALTER TABLE `inspecaopedidocompra` CHANGE COLUMN `avaliacao` `avaliacao` VARCHAR(25) NULL DEFAULT NULL  ;


/*==============================================================*/
/* Table: criteriocotacaocategoria                              */
/*==============================================================*/
create table criteriocotacaocategoria
(
   idcategoria          int not null,
   idcriterio           int not null,
   pesocotacao          int not null
);

alter table criteriocotacaocategoria
   add primary key (idcategoria, idcriterio);

alter table criteriocotacaocategoria add constraint fk_reference_722 foreign key (idcategoria)
      references categoriaproduto (idcategoria) on delete restrict on update restrict;

alter table criteriocotacaocategoria add constraint fk_reference_723 foreign key (idcriterio)
      references criterioavaliacao (idcriterio) on delete restrict on update restrict;

drop table if exists historicosituacaocotacao;

/*==============================================================*/
/* Table: historicosituacaocotacao                              */
/*==============================================================*/
create table historicosituacaocotacao
(
   idhistorico          int not null,
   idcotacao            int not null,
   idresponsavel        int not null,
   datahora             timestamp not null,
   situacao             varchar(25) not null
);

alter table historicosituacaocotacao
   add primary key (idhistorico);

alter table historicosituacaocotacao add constraint fk_reference_696 foreign key (idcotacao)
      references cotacao (idcotacao) on delete restrict on update restrict;

alter table historicosituacaocotacao add constraint fk_reference_697 foreign key (idresponsavel)
      references empregados (idempregado) on delete restrict on update restrict;

INSERT INTO `modelosemails` (`idmodeloemail`,`titulo`,`texto`,`situacao`,`identificador`) VALUES (8,'Criação da Pasta - ${NOME}','Informamos que foi criado a pasta ${NOME}.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','cp');
INSERT INTO `modelosemails` (`idmodeloemail`,`titulo`,`texto`,`situacao`,`identificador`) VALUES (9,'Alteração da Pasta - ${NOME}','Informamos que a pasta ${NOME} foi alterada.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','ap');
INSERT INTO `modelosemails` (`idmodeloemail`,`titulo`,`texto`,`situacao`,`identificador`) VALUES (10,'Exclusão da pasta - ${NOME}','Informamos que a pasta ${NOME} foi exclu&iacute;da.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','ep');
INSERT INTO `modelosemails` (`idmodeloemail`,`titulo`,`texto`,`situacao`,`identificador`) VALUES (11,'Criação do documento - ${TITULO}','Informamos que foi criado o documento ${TITULO}.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','cc');
INSERT INTO `modelosemails` (`idmodeloemail`,`titulo`,`texto`,`situacao`,`identificador`) VALUES (12,'Alteração do documento - ${TITULO}','Informamos que o documento ${TITULO} foi alterado.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','ac');
INSERT INTO `modelosemails` (`idmodeloemail`,`titulo`,`texto`,`situacao`,`identificador`) VALUES (13,'Exclusão do documento - ${TITULO}','Informamos que o documento ${TITULO} foi exclu&iacute;do.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />','A','ec');
INSERT INTO `modelosemails` (`idmodeloemail`,`titulo`,`texto`,`situacao`,`identificador`) VALUES (14,'Alteração do Item configuração - ${IDENTIFICACAO}','Informamos que o item de configura&ccedil;&atilde;o identificado como ${IDENTIFICACAO} sofreu altera&ccedil;&atilde;o.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','alteracaoIC');
INSERT INTO `modelosemails` (`idmodeloemail`,`titulo`,`texto`,`situacao`,`identificador`) VALUES (15,'Alteração do Item configuração para Grupo- ${IDENTIFICACAO}','Informamos que o item de configura&ccedil;&atilde;o identificado como ${IDENTIFICACAO} foi alterado para o Grupo ${NOMEGRUPOITEMCONFIGURACAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','alteracaoICGrupo');
INSERT INTO `modelosemails` (`idmodeloemail`,`titulo`,`texto`,`situacao`,`identificador`) VALUES (16,'Criação do Item configuração - ${IDENTIFICACAO}','Informamos a cria&ccedil;&atilde;o do item de configura&ccedil;&atilde;o identificado como ${IDENTIFICACAO} .<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','CriacaoIC');
INSERT INTO `modelosemails` (`idmodeloemail`,`titulo`,`texto`,`situacao`,`identificador`) VALUES (17,'Alteração Serviço - ${NOMESERVICO}','Informamos que o servi&ccedil;o identificado como ${NOMESERVICO} sofreu altera&ccedil;&atilde;o.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','alteracaoServico');

ALTER TABLE camposobjetonegocio ADD COLUMN `precisiondb` BIGINT(20) NOT NULL DEFAULT 0 AFTER `situacao`;

INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (1,1,4,2,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (2,1,3,2,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (3,1,2,1,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (4,1,1,1,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (5,2,4,3,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (6,2,3,2,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (7,2,2,2,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (8,2,1,1,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (9,3,4,3,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (10,3,3,3,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (11,3,2,2,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (12,3,1,2,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (13,4,4,4,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (14,4,3,3,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (15,4,2,3,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (16,4,1,2,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (17,5,4,5,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (18,5,3,4,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (19,5,2,3,NULL,'');
INSERT INTO `matrizprioridade` (`idMatrizPrioridade`,`idImpacto`,`idUrgencia`,`valorPrioridade`,`idcontrato`,`deleted`) 

VALUES (20,5,1,3,NULL,'');

ALTER TABLE itemconfiguracao DROP FOREIGN KEY itemconfiguracao_ibfk_1 ;
