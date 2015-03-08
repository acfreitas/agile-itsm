-- Desabilita restricao quanto a atualizacao de varios registros sem a clausula where em update.
-- Ao fim do arquivo e' habilitada a restricao.
set sql_safe_updates = 0;

alter table portal modify hora timestamp;

alter table opiniao modify hora timestamp;

ALTER TABLE categoriaservico ADD nomeCatServicoConcatenado VARCHAR(520);

create table matrizvisao(
   idmatriz             bigint not null,
   idvisao              bigint,
   idobjetonegocio      bigint,
   idcamposobjetonegocio1 bigint,  
   idcamposobjetonegocio2 bigint,
   idcamposobjetonegocio3 bigint,
   strinfo              text,
   nomecampo1           varchar(255),
   nomecampo2           varchar(255),
   nomecampo3           varchar(255),
   descricaocampo1      varchar(255),
   descricaocampo2      varchar(255),
   descricaocampo3      varchar(255)
)engine = innodb;
create table grupoitemconfiguracao(
	idgrupoitemconfiguracao int not null,
	nomegrupoitemconfiguracao varchar(100) not null, 
	datainicio date not null, 
	datafim date, primary key (idgrupoitemconfiguracao)
) engine = innodb;
create table acordoservicocontrato(
   idacordoservicocontrato bigint not null,
   idacordonivelservico bigint not null,
   idservicocontrato    bigint not null,
   datacriacao          date not null,
   datainicio           date,
   datafim              date,
   dataultatualiz       date,
   deleted              char(1)
)engine = innodb;
create table tipocomplexidade(
    complexidade char(1) not null,
    desctipocomplexidade varchar(50) not null
)engine = innodb;
create table valorajusteglosa(
   idservicocontrato    bigint(20) not null,
   idacordonivelservico bigint(20) not null,
   quantidadefalhas     int(11),
   valorajuste          decimal(10,2),
   deleted              char(1)
)engine = innodb;
create table glosaservicocontrato(
   idglosaservicocontrato bigint(20) not null,
   idservicocontrato    bigint(20) not null,
   quantidadeglosa      int(11),
   datafim              date,
   primary key(idglosaservicocontrato)
)engine = innodb;
create table resultadosesperados(
   idservicocontrato   bigint(20)  not null,
   idacordonivelservico bigint(20) not null,
   descricaoresultados     varchar(1000),
   limites          varchar(200),
   glosa            varchar(200),
   limiteglosa      varchar(200),
   deleted              char(1)
)engine = innodb;

create table solicitacaoservicomudanca  (
   idrequisicaomudanca integer,
   idsolicitacaoservico integer
) engine=innodb;

create table categoriamudanca  (
   idcategoriamudanca integer not null,
   idtipofluxo       INT(11),
   idmodeloemailcriacao INT(11),
   idmodeloemailfinalizacao INT(11),
   idmodeloemailacoes INT(11),
   idgruponivel1      INT(11),
   idgrupoexecutor   INT(11),
   idcalendario      INT(11),
   idcategoriamudancapai INT(11),
   nomecategoria      varchar(100),
   datainicio         date,
   datafim            date
)
ENGINE = InnoDB;
	  
create table requisicaomudanca  (
   idrequisicaomudanca integer                        not null,
   idproprietario     integer                         not null,
   idsolicitante      integer                         not null,
   tipo            varchar(25),
   idcategoriamudanca integer,
   idgruponivel1      INT(11),
   idgrupoatual       INT(11),
   idcalendario       INT(11),
   motivo             varchar(255),
   nivelimportancianegocio varchar(255),
   classificacao      varchar(255),
   nivelimpacto       varchar(255),
   analiseimpacto     varchar(3000),
   datahoraconclusao  timestamp,
   dataaceitacao     date,
   datavotacao        date,
   datahorainicio     timestamp,
   datahoratermino    timestamp,
   titulo           varchar(255),
   descricao          varchar(255),
   risco              varchar(255),
   estimativacusto   Double,
   planoreversao     varchar(3000),
   status            varchar(45),
   prioridade        integer,
   enviaemailcriacao  varchar(1),
   enviaemailfinalizacao varchar(1),
   enviaemailacoes    varchar(1),
   exibirquadromudancas varchar(1),
   seqreabertura      smallint,
   datahoracaptura    timestamp,
   datahorareativacao timestamp,
   datahorasuspensao  timestamp,
   tempodecorridohh   smallint,
   tempodecorridomm   smallint,
   prazohh            smallint,
   prazomm           smallint,
   tempoatendimentohh smallint,
   tempoatendimentomm smallint,
   tempoatrasohh      smallint,
   tempoatrasomm      smallint,
   tempocapturahh     smallint,
   tempocapturamm     smallint,
   fase            varchar(20)
)
ENGINE = InnoDB;

create table execucaomudanca  (
   idexecucao         integer not null,
   idinstanciafluxo   BIGINT(20) not null,
   idrequisicaomudanca INT(11) not null,
   idfluxo           BIGINT(20) not null,
   seqreabertura     smallint
)
ENGINE = InnoDB; 

create table ocorrenciamudanca  (
   idocorrencia      integer                         not null,
    iditemtrabalho      BIGINT(20),
    idjustificativa     INT(11),
    idrequisicaomudanca  INT(11),
    dataregistro        date,
    horaregistro        varchar(5),
    registradopor       varchar(100),
    descricao           varchar(200),
    datainicio          date,
    datafim             date,
    complementojustificativa   	LONGTEXT,
    dadosmudanca         	LONGTEXT,
    informacoescontato   	LONGTEXT,
    categoria           varchar(20),
    origem              char(1),
    tempogasto          smallint,
    ocorrencia           	LONGTEXT
)
ENGINE = InnoDB;

create table  requisicaomudancaitemconfiguracao   (
    idrequisicaomudancaitemconfiguracao  integer    not null,
    idrequisicaomudanca  INt(11),
    iditemconfiguracao  INt(11),
    descricao           varchar(100)
)
ENGINE = InnoDB;

create table  requisicaomudancaservico   (
    idrequisicaomudancaservico  integer  not null,
    idrequisicaomudanca  INT(11)  not null,
    idservico           BIGINT(20) not null
)
ENGINE = InnoDB;

alter table categoriamudanca   add constraint pk_categoriamudanca primary key (idcategoriamudanca);
alter table requisicaomudanca   add constraint pk_requisicaomudanca primary key (idrequisicaomudanca);
alter table execucaomudanca   add constraint pk_execucaomudanca primary key (idexecucao);
alter table  ocorrenciamudanca add constraint pk_ocorrenciamudanca primary key ( idocorrencia );
alter table  requisicaomudancaitemconfiguracao  add constraint pk_requisicaomudancaitemconfig primary key ( idrequisicaomudancaitemconfiguracao );
alter table  requisicaomudancaservico  add constraint pk_requisicaomudancaservico primary key ( idrequisicaomudancaservico );

alter table solicitacaoservicomudanca  add constraint fk_solicita_reference_requisic foreign key (idrequisicaomudanca)  references requisicaomudanca (idrequisicaomudanca);
alter table solicitacaoservicomudanca modify idsolicitacaoservico BIGINT(20) NULL DEFAULT NULL;
alter table solicitacaoservicomudanca add constraint fk_solicita_reference_solmud foreign key (idsolicitacaoservico) references solicitacaoservico (idsolicitacaoservico);
alter table acordoservicocontrato add primary key (idacordoservicocontrato);
alter table matrizvisao add primary key (idmatriz);
alter table itemconfiguracao add foreign key (idgrupoitemconfiguracao) references grupoitemconfiguracao (idgrupoitemconfiguracao);

alter table matrizvisao add foreign key (idvisao) references visao (idvisao) on delete restrict on update restrict;
alter table matrizvisao add foreign key (idobjetonegocio) references objetonegocio (idobjetonegocio) on delete restrict on update restrict;
alter table matrizvisao add foreign key (idcamposobjetonegocio1)references camposobjetonegocio (idcamposobjetonegocio) on delete restrict on update restrict;
alter table matrizvisao add foreign key (idcamposobjetonegocio2) references camposobjetonegocio (idcamposobjetonegocio) on delete restrict on update restrict;
alter table matrizvisao add foreign key (idcamposobjetonegocio3) references camposobjetonegocio (idcamposobjetonegocio) on delete restrict on update restrict;
alter table acordonivelservico add column contatos text;
alter table acordoservicocontrato add constraint fk_reference_627 foreign key (idacordonivelservico) references acordonivelservico (idacordonivelservico) on delete restrict on update restrict;
alter table acordoservicocontrato add constraint fk_reference_628 foreign key (idservicocontrato) references servicocontrato (idservicocontrato) on delete restrict on update restrict;
alter table menu add column menurapido varchar(45) null  after horizontal;  
alter table solicitacaoservico add column idacordonivelservico integer;
alter table tipocomplexidade add primary key (complexidade);
alter table complexidade DROP idcomplexidadecontrato, DROP PRIMARY KEY;
alter table complexidade add primary key (idcontrato, complexidade);
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('B', 'Baixa');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('I', 'Intermediária');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('M', 'Mediana');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('A', 'Alta');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('E', 'Especialista');

alter table empregados add column (ramal varchar(5) );
alter table  contatosolicitacaoservico add column (ramal varchar(5) );
alter table logdados add column datalog timestamp;

alter table categoriamudanca   add constraint fk_categori_reference_bpm_tipo foreign key (idtipofluxo)      references bpm_tipofluxo (idtipofluxo);
alter table categoriamudanca   add constraint fk_categori_reference_modelose foreign key (idmodeloemailcriacao)      references modelosemails (idmodeloemail);
alter table categoriamudanca   add constraint fk_categori_reference_modelose_01 foreign key (idmodeloemailfinalizacao)     references modelosemails (idmodeloemail);
alter table categoriamudanca   add constraint fk_categori_reference_modelose_02 foreign key (idmodeloemailacoes)      references modelosemails (idmodeloemail);
alter table categoriamudanca   add constraint fk_categori_reference_grupo foreign key (idgruponivel1)      references grupo (idgrupo);
alter table categoriamudanca   add constraint fk_categori_reference_grupo_01 foreign key (idgrupoexecutor)      references grupo (idgrupo);
alter table categoriamudanca   add constraint fk_categori_reference_calendar foreign key (idcalendario)      references calendario (idcalendario);	 
alter table requisicaomudanca   add constraint fk_requisic_reference_cat foreign key (idcategoriamudanca)      references categoriamudanca (idcategoriamudanca);
alter table requisicaomudanca   add constraint fk_requisic_reference_grupo foreign key (idgruponivel1)      references grupo (idgrupo);
alter table requisicaomudanca   add constraint fk_requisic_reference_grupo_01 foreign key (idgrupoatual)      references grupo (idgrupo);
alter table requisicaomudanca   add constraint fk_requisic_reference_calendar foreign key (idcalendario)      references calendario (idcalendario);
ALTER TABLE requisicaomudanca ADD COLUMN nivelurgencia VARCHAR(255) NULL  AFTER fase ;

alter table execucaomudanca add constraint fk_execucao_reference_bpm_inst foreign key (idinstanciafluxo) references bpm_instanciafluxo (idinstancia);
alter table execucaomudanca add constraint fk_execucao_reference_requisic foreign key (idrequisicaomudanca)  references requisicaomudanca (idrequisicaomudanca);
alter table execucaomudanca add constraint fk_execucao_reference_bpm_flux foreign key (idfluxo) references bpm_fluxo (idfluxo);	 

alter table  ocorrenciamudanca add constraint fk_ocorrenc_reference_bpm_item foreign key ( iditemtrabalho )   references  bpm_itemtrabalhofluxo  ( iditemtrabalho );
alter table  ocorrenciamudanca add constraint fk_ocorrenc_reference_justific foreign key ( idjustificativa )  references justificativasolicitacao  ( idjustificativa );
alter table  ocorrenciamudanca add constraint fk_ocorrenc_reference_requisic foreign key ( idrequisicaomudanca )  references  requisicaomudanca  ( idrequisicaomudanca ); 

alter table  requisicaomudancaitemconfiguracao   add constraint fk_requisic_reference_req foreign key ( idrequisicaomudanca )   references  requisicaomudanca  ( idrequisicaomudanca );
alter table  requisicaomudancaitemconfiguracao   add constraint fk_requisic_reference_itemconf foreign key ( iditemconfiguracao )   references  itemconfiguracao  ( iditemconfiguracao );

alter table requisicaomudancaservico add constraint fk_requisic_referenc_req foreign key (idrequisicaomudanca) references requisicaomudanca (idrequisicaomudanca);
alter table  requisicaomudancaservico  add constraint fk_requisic_reference_servico foreign key ( idservico )  references  servico  ( idservico );	 
ALTER TABLE itemconfiguracao ADD COLUMN idproprietario int;
ALTER TABLE itemconfiguracao ADD column dataexpiracao date;
ALTER TABLE itemconfiguracao ADD CONSTRAINT fk_idproprietario foreign key (idproprietario) REFERENCES empregados (idempregado);
ALTER TABLE grupoitemconfiguracao ADD COLUMN email varchar(50);
ALTER TABLE itemconfiguracao ADD COLUMN versao varchar(50);

create table requisitosla
(
   idrequisitosla       int not null,
   idempregado          int,
   requisitadoem        date,
   assunto              varchar(200) not null,
   detalhamento         text,
   situacao             char(1) not null,
   criadopor            varchar(30),
   criadoem             timestamp,
   modificadopor        varchar(30),
   modificadoem         timestamp,
   deleted              char(1)
)ENGINE = InnoDB;

alter table requisitosla
   add primary key (idrequisitosla);

alter table requisitosla add constraint fk_reference_552 foreign key (idempregado)
      references empregados (idempregado) on delete restrict on update restrict;
	  
create table slarequisitosla
(
   idrequisitosla       int not null,
   idacordonivelservico bigint not null,
   datavinculacao       timestamp,
   dataultmodificacao   timestamp,
   deleted              char(1)
)ENGINE = InnoDB;

alter table slarequisitosla
   add primary key (idrequisitosla, idacordonivelservico);

alter table slarequisitosla add constraint fk_reference_553 foreign key (idrequisitosla)
      references requisitosla (idrequisitosla) on delete restrict on update restrict;

alter table slarequisitosla add constraint fk_reference_554 foreign key (idacordonivelservico)
      references acordonivelservico (idacordonivelservico) on delete restrict on update restrict;	  
	  
alter table acordonivelservico add criadoEm timestamp;
alter table acordonivelservico add criadoPor VARCHAR(30);
alter table acordonivelservico add modificadoEm timestamp;
alter table acordonivelservico add modificadoPor VARCHAR(30);

create table acordonivelservico_hist
(
   idacordonivelservico_hist bigint not null,
   idacordonivelservico bigint not null,
   idservicocontrato    bigint,
   idprioridadepadrao   bigint,
   situacao             char(1) not null,
   titulosla            varchar(500) not null,
   disponibilidade      decimal(10 , 3),
   descricaosla         text,
   escoposla            text,
   datainicio           date not null,
   datafim              date,
   avaliarem            date,
   tipo                 char(1),
   valorlimite          decimal(15,3),
   detalheglosa         text,
   detalhelimiteglosa   text,
   unidadevalorlimite   varchar(150),
   impacto              char(1),
   urgencia             char(1),
   permiteMudarImpUrg   char(1),
   deleted              char(1),
   criadoem             timestamp,
   criadopor            varchar(30),
   modificadoem         timestamp,
   modificadopor        varchar(30),
   conteudodados        text,
   idformula            int
)ENGINE = InnoDB;

alter table acordonivelservico_hist
   add primary key (idacordonivelservico_hist);

alter table acordonivelservico_hist modify idprioridadepadrao int(11);

alter table acordonivelservico_hist add constraint fk_reference_102 foreign key (idprioridadepadrao)
      references prioridade (idprioridade) on delete restrict on update restrict;

alter table acordonivelservico_hist add constraint fk_reference_91 foreign key (idservicocontrato)
      references servicocontrato (idservicocontrato);

CREATE TABLE contratos_hist (
  idcontrato_hist int(11) NOT NULL,
  idcontrato int(11) NOT NULL,
  idcliente int(11) NOT NULL,
  idmoeda int(11) DEFAULT NULL,
  idfornecedor bigint(20) NOT NULL,
  numero varchar(30) NOT NULL,
  objeto text NOT NULL,
  datacontrato date NOT NULL,
  valorestimado decimal(18,3) DEFAULT NULL,
  tipotempoestimado char(1) DEFAULT NULL,
  tempoestimado int(11) DEFAULT NULL,
  tipo char(1) NOT NULL,
  situacao char(1) NOT NULL,
  cotacaomoeda decimal(18,3) DEFAULT NULL,
  cadastromanualusuario char(1) DEFAULT NULL,
  deleted char(1) DEFAULT NULL,
  idgruposolicitante int(11) DEFAULT NULL,
  datafimcontrato date DEFAULT NULL,
  criadoem             timestamp,
  criadopor            varchar(30),
  modificadoem         timestamp,
  modificadopor        varchar(30),  
  conteudodados        text,
  PRIMARY KEY (idcontrato_hist),
  KEY fk_reference_26_2 (idmoeda),
  KEY fk_reference_3_2 (idcliente),
  KEY fk_reference_60_2 (idfornecedor)
) ENGINE=InnoDB;

alter table contatosolicitacaoservico change column telefonecontato telefonecontato varchar(70) null;

INSERT INTO processamentobatch
(idprocessamentobatch, descricao, expressaocron,  conteudo, tipo, situacao)
VALUES
(4, 'Verifica e avisa a data de expiração de um item de configuração', '0 45 23 * * ? *', 'br.com.centralit.citcorpore.quartz.job.VerificaValidadeLicenca',
'C', 'A');

INSERT INTO modelosemails
(idmodeloemail, titulo, texto, situacao, identificador)
VALUES
(6,'Validade do Item configuração - ${IDENTIFICACAO}',
'Informamos que o item de configura&ccedil;&atilde;o identificado como ${IDENTIFICACAO} expirar&aacute; no dia&nbsp;${DATAEXPIRACAO}.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />',
'A','');

create table categoriaocorrencia(
idcategoriaocorrencia int(11) not null,
nome varchar(20) not null,
dataInicio date not null,
dataFim date
) engine=InnoDB;
 
alter table categoriaocorrencia add primary key(idcategoriaocorrencia);

create table origemocorrencia(
idorigemocorrencia int(11) not null,
nome varchar(20) not null,
dataInicio date not null,
dataFim date
) engine=InnoDB;
 
alter table origemocorrencia add primary key(idorigemocorrencia);
	
alter table ocorrenciasolicitacao add column idcategoriaocorrencia int null;

alter table ocorrenciasolicitacao add column idorigemocorrencia int null;

alter table ocorrenciasolicitacao add foreign key(idcategoriaocorrencia) references categoriaocorrencia(idcategoriaocorrencia);

alter table ocorrenciasolicitacao add foreign key(idorigemocorrencia) references origemocorrencia(idorigemocorrencia);
	
CREATE  TABLE lingua (

  idlingua INT NOT NULL ,

  nome VARCHAR(245) NULL ,

  sigla VARCHAR(245) NULL ,

  datainicio DATE NULL ,

  datafim DATE NULL ,

  PRIMARY KEY (`idlingua`) )

ENGINE = InnoDB;

CREATE  TABLE dicionario (

  iddicionario INT NOT NULL ,

  nome VARCHAR(245) NULL ,

  valor VARCHAR(245) NULL ,

  idlingua INT NULL ,

  PRIMARY KEY (iddicionario) ,

  INDEX idlingua (idlingua ASC) ,

  CONSTRAINT idlingua

    FOREIGN KEY (idlingua )

    REFERENCES lingua (idlingua)
) ENGINE = InnoDB;

ALTER TABLE grupoitemconfiguracao ADD COLUMN emailgrupoitemconfiguracao VARCHAR(256) NULL AFTER email;

CREATE TABLE notificacao (
        idnotificacao INT NOT NULL,
        titulo VARCHAR(255) NULL,
         tiponotificacao CHAR(1) NULL,
         datainicio DATE NULL,
         datafim DATE NULL,
         PRIMARY KEY (idnotificacao) 
) ENGINE=InnoDB;

ALTER TABLE tipoitemconfiguracao ADD COLUMN categoria INT NULL;


CREATE TABLE midia (
        idmidia INT(11) NOT NULL,
         nome VARCHAR(200) DEFAULT NULL,
         midiacol VARCHAR(45) DEFAULT NULL,
         PRIMARY KEY (idmidia)
) ENGINE=InnoDB;


CREATE TABLE midiasoftware (
        idmidiasoftware INT(11) NOT NULL,
         nome VARCHAR(200) NOT NULL,
         endfisico VARCHAR(500) DEFAULT NULL,
         versao VARCHAR(20) DEFAULT NULL,
         endlogico VARCHAR(200) DEFAULT NULL,
         licencas INT(11) DEFAULT NULL,
         idmidia INT(11) DEFAULT NULL,
         idtiposoftware INT(11) DEFAULT NULL,
         datainicio DATE DEFAULT NULL,
         datafim DATE DEFAULT NULL,
         PRIMARY KEY (idmidiasoftware),
         KEY fk_midia (idmidia),
         KEY fk_tiposoftware (idtiposoftware)
) ENGINE=InnoDB;


CREATE TABLE tiposoftware (
        idtiposoftware INT(11) NOT NULL,
         nome VARCHAR(200) DEFAULT NULL,
         PRIMARY KEY (idtiposoftware)
) ENGINE=InnoDB;


CREATE TABLE notificacaogrupo (
   idnotificacao INT(11),
   idgrupo INT(11)
) ENGINE=InnoDB;


ALTER TABLE notificacaogrupo
   ADD CONSTRAINT fk_alertagr_reference_alerta FOREIGN KEY (idnotificacao)
      REFERENCES notificacao (idnotificacao);

ALTER TABLE notificacaogrupo
   ADD CONSTRAINT fk_alertagr_reference_grupo FOREIGN KEY (idgrupo)
      REFERENCES grupo (idgrupo);

CREATE TABLE notificacaousuario (
    idnotificacao INT,
    idusuario INT
) ENGINE=InnoDB;

ALTER TABLE notificacaousuario
   ADD CONSTRAINT fk_alertaus_reference_alerta FOREIGN KEY (idnotificacao)
      REFERENCES notificacao (idnotificacao);

ALTER TABLE notificacaousuario
   ADD CONSTRAINT fk_alertaus_reference_usuario FOREIGN KEY (idusuario)
      REFERENCES usuario (idusuario);

ALTER TABLE baseconhecimento 
    ADD COLUMN idnotificacao INT NULL,
    ADD CONSTRAINT idnotificacao FOREIGN KEY (idnotificacao) REFERENCES notificacao (idnotificacao);

ALTER TABLE pasta ADD COLUMN idnotificacao INT NULL;

CREATE TABLE importanciaconhecimentousuario (
    idbaseconhecimento INT(11) NOT NULL,
        idusuario INT(11) NOT NULL,
        grauimportanciausuario VARCHAR(45) NOT NULL,
        PRIMARY KEY (idbaseconhecimento, idusuario) 
) ENGINE=InnoDB;

CREATE TABLE importanciaconhecimentogrupo (
    idbaseconhecimento INT(11) NOT NULL,
        idgrupo INT(11) NOT NULL,
        grauimportanciagrupo VARCHAR(45) NOT NULL,
        PRIMARY KEY (idbaseconhecimento, idgrupo)
) ENGINE=InnoDB;

CREATE TABLE baseconhecimentorelacionado (
    idbaseconhecimento INT(11) NOT NULL,
    idbaseconhecimentorelacionado INT(11) NOT NULL,
    PRIMARY KEY (idbaseconhecimento, idbaseconhecimentorelacionado)
) ENGINE=InnoDB;

ALTER TABLE baseconhecimento ADD COLUMN justificativaobservacao VARCHAR(500) NULL;

ALTER TABLE baseconhecimento ADD COLUMN datapublicacao DATE NULL;

ALTER TABLE baseconhecimento ADD COLUMN fontereferencia VARCHAR(255);

ALTER TABLE baseconhecimento ADD COLUMN faq VARCHAR(45) NULL;

ALTER TABLE baseconhecimento ADD COLUMN arquivado VARCHAR(45) NULL;

ALTER TABLE baseconhecimento
        ADD COLUMN idusuarioautor INT(11) NULL, 
        ADD COLUMN idusuarioaprovador INT(11) NULL;

ALTER TABLE perfilacessopasta 
        ADD COLUMN permiteleitura CHAR(1) NULL, 
        ADD COLUMN permiteleituragravacao CHAR(1) NULL;

ALTER TABLE pasta ADD COLUMN herdapermissoes CHAR(1) NULL;

-- ATRIBUI PERMISSÃO DE LEITURA/GRAVAÇÃO PARA O PERFIL ADMINISTRADOR --
UPDATE perfilacessopasta SET permiteleitura = 'N', permiteleituragravacao = 'S' WHERE idperfil = 1;

-- ATRIBUI PERMISSÃO DE LEITURA PARA OS DEMAIS PERFIS --
UPDATE perfilacessopasta SET permiteleitura = 'S', permiteleituragravacao = 'N' WHERE idperfil <> 1;

CREATE TABLE historicobaseconhecimento (
   idhistoricobaseconhecimento INT(11) NOT NULL,
   idbaseconhecimento INT(11) NOT NULL,
   idpasta  INT(11) DEFAULT NULL,
   datainicio DATE DEFAULT NULL,
   datafim DATE DEFAULT NULL,
   titulo VARCHAR(256) DEFAULT NULL,
   conteudo TEXT,
   status CHAR(1) DEFAULT NULL,
   idbaseconhecimentopai  INT(11) DEFAULT NULL,
   dataexpiracao DATE DEFAULT NULL,
   versao VARCHAR(45) DEFAULT NULL,
   idusuarioautor INT(11) DEFAULT NULL,
   idusuarioaprovador INT(11) DEFAULT NULL,
   fontereferencia VARCHAR(255) DEFAULT NULL,
   idnotificacao INT(11) DEFAULT NULL,
   datapublicacao DATE DEFAULT NULL,
   justificativaobservacao VARCHAR(500) DEFAULT NULL,
   faq VARCHAR(45) DEFAULT NULL,
   origem CHAR(1) DEFAULT NULL,
   arquivado VARCHAR(45) DEFAULT NULL,
   idusuarioalteracao INT(11) DEFAULT NULL,
   datahoraalteracao TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (idhistoricobaseconhecimento , idbaseconhecimento)
) ENGINE=InnoDB ;

ALTER TABLE baseconhecimento 
    ADD COLUMN idhistoricobaseconhecimento INT NULL, 
    ADD CONSTRAINT idhistoricobaseconhecimento 
        FOREIGN KEY (idhistoricobaseconhecimento) REFERENCES historicobaseconhecimento (idhistoricobaseconhecimento);

INSERT INTO processamentobatch (idprocessamentobatch, descricao, expressaocron, conteudo, tipo, situacao) VALUES (6, 'Verifica e avisa a data de expiração de um item de configuração', '0 03 09 * * ? *', 'br.com.centralit.citcorpore.quartz.job.VerificaValidadeLicenca', 'C', 'A');

INSERT INTO modelosemails (idmodeloemail, titulo, texto, situacao, identificador) VALUES (7, 'Validade do documento - ${TITULO}', 'Informamos que o documento titulado como ${TITULO} expirar&aacute; no dia&nbsp;${DATAEXPIRACAO}.<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />', 'A', 'bc');

ALTER TABLE baseconhecimento ADD COLUMN origem CHAR(1) NOT NULL;

ALTER TABLE requisicaomudanca
    ADD COLUMN idbaseconhecimento INT;

ALTER TABLE requisicaomudanca 
    ADD CONSTRAINT fk_idbaseconhecimento FOREIGN KEY (idbaseconhecimento) REFERENCES baseconhecimento (idbaseconhecimento);

ALTER TABLE solicitacaoservico  ADD idSolicitacaoRelacionada INTEGER;

CREATE  TABLE revisarsla (
  `idrevisarsla` BIGINT(20) NOT NULL ,
  `idacordonivelservico` BIGINT(20) NOT NULL ,
  `datarevisao` DATE NOT NULL ,
  `detalherevisao` TEXT NULL DEFAULT NULL ,
  `observacao` VARCHAR(200) NULL DEFAULT NULL ,
  `deleted` CHAR(1) NULL DEFAULT NULL ,
  PRIMARY KEY (`idrevisarsla`)) ENGINE = InnoDB;

drop table if exists matrizprioridade;

CREATE TABLE `matrizprioridade` (
  `idmatrizprioridade` int(11) NOT NULL,
  `idimpacto` int(11) NOT NULL,
  `idurgencia` int(11) NOT NULL,
  `valorprioridade` int(11) NOT NULL,
  `idcontrato` int(11) DEFAULT NULL,
  `deleted` char(1) DEFAULT NULL,
  PRIMARY KEY (`idmatrizprioridade`)
) ENGINE=InnoDB;

ALTER TABLE `matrizprioridade` ADD FOREIGN KEY (`idimpacto` )  REFERENCES `impacto` (`idimpacto` )  ON DELETE RESTRICT  ON UPDATE RESTRICT;
ALTER TABLE `matrizprioridade` ADD CONSTRAINT `fk_urgencia`  FOREIGN KEY (`idurgencia` ) REFERENCES `urgencia` (`idurgencia` )  ON DELETE RESTRICT  ON UPDATE RESTRICT;
ALTER TABLE `matrizprioridade` ADD INDEX `fk_impacto_idx` (`idimpacto` ASC);
ALTER TABLE `matrizprioridade` ADD INDEX `fk_urgencia_idx` (`idurgencia` ASC);

ALTER TABLE baseconhecimento ADD COLUMN privacidade VARCHAR(45) NULL;
  
ALTER TABLE baseconhecimento ADD COLUMN situacao VARCHAR(45) NULL;

CREATE  TABLE eventomonitoramento (
	  ideventomonitoramento INT(11) NOT NULL,
	  nomeevento VARCHAR(255) NOT NULL,
	  detalhamento TEXT NULL,
	  criadopor VARCHAR(255) NULL,
	  modificadopor VARCHAR(255) NULL,
	  datacriacao DATE NULL,
	  ultmodificacao DATE NULL,
	  PRIMARY KEY (ideventomonitoramento)
	) ENGINE=InnoDB;
	
CREATE  TABLE eventomonitconhecimento (
  ideventomonitoramento INT(11) NOT NULL ,
  idbaseconhecimento INT(11) NOT NULL ,
  PRIMARY KEY (ideventomonitoramento, idbaseconhecimento) ) ENGINE=InnoDB;

create table nagiosconexao
(
   idnagiosconexao      int not null,
   nome                 varchar(255) not null,
   nomejndi             varchar(255),
   criadopor            varchar(40),
   modificadopor        varchar(40),
   datacriacao          date,
   ultmodificacao       date,
   primary key (idnagiosconexao)
)ENGINE = InnoDB;

create table recurso
(
   idrecurso            int not null,
   idgruporecurso       int not null,
   idrecursopai         int,
   nomerecurso          varchar(150) not null,
   datainicio           date not null,
   datafim              date,
   tipoatualizacao      char(1) not null,
   deleted              char(1)
) ENGINE = InnoDB;

alter table recurso
   add primary key (idrecurso);

alter table recurso add constraint fk_reference_605 foreign key (idrecursopai)
      references recurso (idrecurso) on delete restrict on update restrict;

alter table recurso add idnagiosconexao int;

alter table recurso add constraint fk_ref_rec_nagios foreign key (idnagiosconexao)
      references nagiosconexao (idnagiosconexao) on delete restrict on update restrict;
	  
alter table recurso add hostName varchar(255);
alter table recurso add serviceName varchar(255);

alter table recurso add horaInicioFunc varchar(5);
alter table recurso add horaFimFunc varchar(5);

alter table recurso add idCalendario int;

alter table recurso add constraint fk_ref_rec_calend foreign key (idCalendario)
      references calendario (idCalendario) on delete restrict on update restrict;

alter table recurso add statusaberturainc varchar(255);
alter table recurso add idsolicitante int;
alter table recurso add constraint fk_ref_solic_rec foreign key (idsolicitante)
      references empregados (idempregado);
alter table recurso add emailaberturainc varchar(255);
alter table recurso add descricaoabertinc text;
alter table recurso add impacto CHAR(1);
alter table recurso add urgencia CHAR(1);
alter table recurso add idgrupo int;
alter table recurso add constraint fk_ref_grp_rec foreign key (idgrupo)
      references grupo (idgrupo);
alter table recurso add idorigem int;

alter table recurso change column idorigem idorigem BIGINT(20) DEFAULT NULL;

alter table recurso add constraint fk_ref_orig_rec foreign key (idorigem)
      references origematendimento (idorigem);
alter table recurso add idservicocontrato BIGINT(20);

alter table recurso add constraint fk_ref_sc_rec foreign key (idservicocontrato)
      references servicocontrato (idservicocontrato) on delete restrict on update restrict;
alter table recurso add ideventomonitoramento int;

alter table recurso add constraint fk_ref_evt_rec foreign key (ideventomonitoramento)
      references eventomonitoramento (ideventomonitoramento) on delete restrict on update restrict;
alter table recurso add iditemconfiguracao int;
alter table recurso add constraint fk_ref_ic_rec foreign key (iditemconfiguracao)
      references itemconfiguracao (iditemconfiguracao) on delete restrict on update restrict;
alter table recurso add statusalerta varchar(255);
alter table recurso add emailsalerta text;
alter table recurso add descricaoalerta text; 

create table solicitacaoservicoevtmon
(
   idsolicitacaoservico bigint not null,
   ideventomonitoramento int not null,
   idrecurso            int,
   nomehost             varchar(255),
   nomeservice          varchar(255),
   infoadd              text,
   primary key (idsolicitacaoservico, ideventomonitoramento)
)ENGINE = InnoDB;

alter table solicitacaoservicoevtmon add constraint fk_ref_ssevtmon foreign key (idsolicitacaoservico)
      references solicitacaoservico (idsolicitacaoservico) on delete restrict on update restrict;

alter table solicitacaoservicoevtmon add constraint fk_ref_evtmon foreign key (ideventomonitoramento)
      references eventomonitoramento (ideventomonitoramento) on delete restrict on update restrict;

alter table solicitacaoservicoevtmon add constraint fk_ref_recevtmon foreign key (idrecurso)
      references recurso (idrecurso) on delete restrict on update restrict;
	  
create table gruporecursos
(
   idgruporecurso       int not null,
   nomegruporecurso     varchar(70) not null,
   situacao             char(1) not null,
   deleted              char(1)
) ENGINE = InnoDB;

alter table gruporecursos
   add primary key (idgruporecurso);

alter table recurso add constraint fk_ref_grprec foreign key (idgruporecurso)
      references gruporecursos (idgruporecurso) on delete restrict on update restrict;
      
alter table nagiosconexao add deleted char(1);

alter table acordoservicocontrato add idrecurso INT;
alter table acordoservicocontrato add constraint fk_ref_asc_rec foreign key (idrecurso)
      references recurso (idrecurso) on delete restrict on update restrict;
      
create table externalconnection
(
   idexternalconnection int not null,
   nome                 varchar(80) not null,
   tipo                 char(1) not null,
   urljdbc              varchar(255),
   jdbcdbname           varchar(255),
   jdbcdriver           varchar(255),
   jdbcuser             varchar(255),
   jdbcpassword         varchar(255),
   filename             varchar(500),
   primary key (idexternalconnection)
)ENGINE = InnoDB;

create table importconfig
(
   idimportconfig       int not null,
   tipo                 char(1) not null,
   idexternalconnection int,
   tabelaorigem         varchar(255),
   tabeladestino        varchar(255),
   filtroorigem         text,
   primary key (idimportconfig)
) ENGINE = InnoDB;

alter table importconfig add constraint fk_ref_impconx foreign key (idexternalconnection)
      references externalconnection (idexternalconnection) on delete restrict on update restrict;

create table importconfigcampos
(
   idimportconfigcampo  int not null,
   idimportconfig       int not null,
   origem               varchar(255),
   destino              varchar(255),
   script               text,
   primary key (idimportconfigcampo)
) ENGINE = InnoDB;

alter table importconfigcampos add constraint fk_ref_impcp_imp foreign key (idimportconfig)
      references importconfig (idimportconfig) on delete restrict on update restrict;
      
alter table importconfig add nome varchar(100);

alter table externalconnection add schemadb varchar(255);

ALTER TABLE itemconfiguracao
  ADD COLUMN familia varchar(250) DEFAULT NULL,
  ADD COLUMN classe varchar(250) DEFAULT NULL,
  ADD COLUMN localidade varchar(250) DEFAULT NULL,
  ADD COLUMN status int(11) DEFAULT NULL,
  ADD COLUMN criticidade int(11) DEFAULT NULL,
  ADD COLUMN numeroSerie varchar(45) DEFAULT NULL,
  ADD COLUMN idMudanca int(11) DEFAULT NULL,
  ADD COLUMN idProblema int(11) DEFAULT NULL,
  ADD COLUMN IdIncidente int(11) DEFAULT NULL,
  ADD COLUMN idMidiaSoftware int(11) DEFAULT NULL,
  ADD COLUMN impacto varchar(255) DEFAULT NULL,
  ADD COLUMN urgencia varchar(255) DEFAULT NULL,
  ADD COLUMN idbaseconhecimento int(11) DEFAULT NULL;

alter table itemconfiguracao add constraint fk_idbaseconhecimento_itemconfiguracao foreign key (idbaseconhecimento)
     references baseconhecimento (idbaseconhecimento) on delete restrict on update restrict;

CREATE TABLE historicoic (
  idhistoricoic int(11) NOT NULL,
  iditemconfiguracao int(11) NOT NULL,
  identificacao varchar(400) NOT NULL,
  iditemconfiguracaopai int(11) DEFAULT NULL,
  idtipoitemconfiguracao int(11) DEFAULT NULL,
  idgrupoitemconfiguracao int(11) DEFAULT NULL,
  idproprietario int(11) DEFAULT NULL,
  versao varchar(250) DEFAULT NULL,
  familia varchar(250) DEFAULT NULL,
  idfamiliaitemconfiguracao int(11) DEFAULT NULL,
  classe varchar(250) DEFAULT NULL,
  idclasseitemconfiguracao int(11) DEFAULT NULL,
  localidade varchar(250) DEFAULT NULL,
  status int(11) DEFAULT NULL,
  criticidade int(11) DEFAULT NULL,
  numeroSerie varchar(45) DEFAULT NULL,
  dataExpiracao date DEFAULT NULL,
  idMudanca int(11) DEFAULT NULL,
  idProblema int(11) DEFAULT NULL,
  IdIncidente int(11) DEFAULT NULL,
  idautoralteracao int(11) NOT NULL,
  datahoraalteracao timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  baseline varchar(30) DEFAULT NULL,
  restauracao int(11) DEFAULT NULL,
  idMidiaSoftware int(11) DEFAULT NULL,
  impacto varchar(255) DEFAULT NULL,
  urgencia varchar(255) DEFAULT NULL,
  historicoVersao float DEFAULT NULL,
  PRIMARY KEY (idhistoricoic),
  KEY fk_itemconfiguracao (iditemconfiguracao),
  KEY fk_autoralteracao (idautoralteracao),
  KEY fk_familiaitemconfiguracao (idfamiliaitemconfiguracao),
  KEY fk_classeitemconfiguracao (idclasseitemconfiguracao)
) ENGINE=InnoDB;

CREATE TABLE historicovalor (
  idhistoricovalor int(11) NOT NULL,
  idvalor int(11) NOT NULL,
  iditemconfiguracao int(11) DEFAULT NULL,
  idcaracteristica int(11) DEFAULT NULL,
  valorstr varchar(4000) DEFAULT NULL,
  valorlongo text,
  valordecimal decimal(18,4) DEFAULT NULL,
  valordate date DEFAULT NULL,
  idbaseitemconfiguracao int(11) DEFAULT NULL,
  datahoraalteracao timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  idautoralteracao int(11) NOT NULL,
  baseline varchar(30) DEFAULT NULL,
  idHistoricoIC int(11) DEFAULT NULL,
  PRIMARY KEY (idhistoricovalor),
  KEY fk_valor (idvalor)
) ENGINE=InnoDB;

create table itemcfgsolicitacaoserv  (
  iditemconfiguracao integer,
  idsolicitacaoservico integer,
  datainicio        date,
  datafim           date
) ENGINE=InnoDB;

alter table itemcfgsolicitacaoserv
  add constraint fk_itemcfgs_reference_itemconf foreign key (iditemconfiguracao)
     references itemconfiguracao (iditemconfiguracao);

alter table itemcfgsolicitacaoserv change column idsolicitacaoservico idsolicitacaoservico BIGINT(20);

alter table itemcfgsolicitacaoserv
  add constraint fk_itemcfgs_reference_solicita foreign key (idsolicitacaoservico)
     references solicitacaoservico (idsolicitacaoservico);

ALTER TABLE grupoitemconfiguracao ADD COLUMN idGrupoItemConfiguracaoPai INT(11) NULL  AFTER emailGrupoItemConfiguracao ;

create table valorservicocontrato
(
  idservicocontrato    int,
  idservico            int,
  valorServico         decimal(10,2),
  datainicio           date,
  datafim              date,
  idvalorservicocontrato integer not null
) ENGINE=InnoDB;

alter table valorservicocontrato
  add primary key (idvalorservicocontrato);

alter table valorservicocontrato change column idservicocontrato idservicocontrato BIGINT(20);

alter table valorservicocontrato add constraint fk_reference_665 foreign key (idservicocontrato)
     references servicocontrato (idservicocontrato) on delete restrict on update restrict;
     
CREATE TABLE imagemservicorelacionado (
  idimagemservicorelacionado int(11) NOT NULL,
  idservico int(11) DEFAULT NULL,
  idservicorelacionado int(11) DEFAULT NULL,
  posx int(11) DEFAULT NULL,
  posy int(11) DEFAULT NULL,
  descricao varchar(256) DEFAULT NULL,
  caminhoimagem varchar(256) DEFAULT NULL,
  idimagemservicorelacionadopai int(11) DEFAULT NULL,
  PRIMARY KEY (idimagemservicorelacionado),
  KEY INDEX_IMAGEMITEMSERVICO (idservico),
  KEY INDEX_IMAGEMSERVICORELACIONADO (idservicorelacionado),
  KEY INDEX_IMAGEMSERVICORELACIONADOPAI (idimagemservicorelacionadopai)
) ENGINE=InnoDB;

CREATE  TABLE notificacaoservico (
  idNotificacao INT NULL ,
  idServico INT NULL)
ENGINE = InnoDB;


ALTER TABLE notificacao ADD COLUMN origemnotificacao CHAR NOT NULL, ADD COLUMN idContrato INT ;

update baseconhecimento set faq='N' where idbaseconhecimento is not null;
 
update baseconhecimento set idusuarioautor = 20 where idbaseconhecimento is not null;
 
update baseconhecimento set arquivado = 'N' where idbaseconhecimento is not null;

CREATE TABLE solicitacaoservicoproblema (
  idproblema INT(11) DEFAULT NULL,
  idsolicitacaoservico INT(11) DEFAULT NULL,
  KEY fk_solicita_reference_solicita (idsolicitacaoservico)
) ENGINE=InnoDB;

set sql_safe_updates = 1;

