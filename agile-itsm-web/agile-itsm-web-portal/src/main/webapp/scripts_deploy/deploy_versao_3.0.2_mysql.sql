
set sql_safe_updates = 0;

-- Início Bruno 23/08/2013

alter table recursotarefalinbaseproj add column esforcoporos varchar(50) NULL;

alter table tarefalinhabaseprojeto add column esforcoporos varchar(50) NULL;

-- Fim Bruno

-- Início Carlos 27/08/2013

alter table templatesolicitacaoservico add column aprovacao char(1);

-- Fim Carlos

-- Início Carlos 28/08/2013

create table rest_domain
(
   idrestparameter      int not null,
   idrestoperation      int not null,
   value                text not null,
   primary key (idrestparameter, idrestoperation)
)engine=innodb;

create table rest_execution
(
   idrestexecution      bigint not null,
   idrestoperation      int not null,
   iduser               int,
   inputdatetime        date not null,
   inputclass           varchar(200),
   inputdata            text,
   status               varchar(20) not null comment 'N - Não processado P - Processado  E - Erro   X - Cancelado',
   primary key (idrestexecution)
)engine=innodb;

create table rest_log
(
   idrestlog            bigint not null,
   idrestexecution      bigint not null,
   datetime             date not null,
   resultclass          varchar(200),
   resultdata           text,
   status               varchar(20) not null comment 'P - Processado  E - Erro',
   primary key (idrestlog)
)engine=innodb;

create table rest_operation
(
   idrestoperation      int not null,
   idbatchprocessing    int,
   name                 varchar(50) not null,
   description          text not null,
   operationtype        varchar(20) not null comment 'Sync - Síncrona com log Async - Assíncrona com log',
   classtype            varchar(20) not null comment 'S = JavaScript J = Java',
   javaclass            varchar(200),
   javascript           text,
   status               varchar(20) not null comment 'A = Ativo I = Inativo',
   primary key (idrestoperation)
)engine=innodb;

create unique index ix_url on rest_operation
(
   name
);

create table rest_parameter
(
   idrestparameter      int not null,
   identifier           varchar(100) not null,
   description          varchar(200) not null,
   primary key (idrestparameter)
)engine=innodb;

create table rest_permission
(
   idrestoperation      int not null,
   idgroup              int not null,
   status               char(1) not null,
   primary key (idrestoperation, idgroup)
)engine=innodb;

create table rest_translation
(
   idresttranslation    bigint not null,
   idrestoperation      int,
   idbusinessobject     bigint not null,
   fromvalue            varchar(100) not null,
   tovalue              varchar(100) not null,
   primary key (idresttranslation)
)engine=innodb;

alter table rest_domain add constraint fk_rest_domain1 foreign key (idrestparameter) references rest_parameter (idrestparameter) on delete restrict on update restrict;

alter table rest_domain add constraint fk_reference_9 foreign key (idrestoperation) references rest_operation (idrestoperation) on delete restrict on update restrict;

alter table rest_execution add constraint fk_restexecution_1 foreign key (iduser) references usuario (idusuario) on delete restrict on update restrict;

alter table rest_execution add constraint fk_ref_restexecution_2 foreign key (idrestoperation) references rest_operation (idrestoperation) on delete restrict on update restrict;

alter table rest_log add constraint fk_ref_restexecution foreign key (idrestexecution) references rest_execution (idrestexecution) on delete restrict on update restrict;

alter table rest_operation add constraint fk_ref_procbatch foreign key (idbatchprocessing) references processamentobatch (idprocessamentobatch) on delete restrict on update restrict;

alter table rest_permission add constraint fk_restpermission_2 foreign key (idgroup) references grupo (idgrupo) on delete restrict on update restrict;

alter table rest_permission add constraint fk_rest_permission1 foreign key (idrestoperation) references rest_operation (idrestoperation) on delete restrict on update restrict;

alter table rest_translation add constraint fk_ref_restoperation1 foreign key (idrestoperation) references rest_operation (idrestoperation) on delete restrict on update restrict;

alter table rest_translation add constraint fk_ref_businessobj foreign key (idbusinessobject) references objetonegocio (idobjetonegocio) on delete restrict on update restrict;

INSERT INTO rest_parameter (idrestparameter,identifier,description) VALUES (1,'CONTRACT_ID','ID do contrato');
INSERT INTO rest_parameter (idrestparameter,identifier,description) VALUES (2,'ORIGIN_ID','ID da origem');
INSERT INTO rest_parameter (idrestparameter,identifier,description) VALUES (3,'REQUEST_ID','ID do tipo de demanda para requisições');
INSERT INTO rest_parameter (idrestparameter,identifier,description) VALUES (4,'INCIDENT_ID','ID do tipo de demanda para incidentes');
INSERT INTO rest_parameter (idrestparameter,identifier,description) VALUES (5,'DEFAULT_DEPTO_ID','ID padrão da unidade');

INSERT INTO rest_operation (idrestoperation,idbatchprocessing,name,description,operationtype,classtype,javaclass,javascript,status) VALUES (1,NULL,'addServiceRequest','Criação de solicitação de serviço','Sync','Java','br.com.centralit.citsmart.rest.operation.RestAddServiceRequest',NULL,'A');
INSERT INTO rest_operation (idrestoperation,idbatchprocessing,name,description,operationtype,classtype,javaclass,javascript,status) VALUES (2,NULL,'listTasks','Listagem de tarefas','Sync','Java','br.com.centralit.citsmart.rest.operation.RestListTasks',NULL,'A');
INSERT INTO rest_operation (idrestoperation,idbatchprocessing,name,description,operationtype,classtype,javaclass,javascript,status) VALUES (3,NULL,'notification_getByUser','Retorna lista de notificações para Mobile','Sync','Java','br.com.centralit.citsmart.rest.operation.RestMobile',NULL,'A');
INSERT INTO rest_operation (idrestoperation,idbatchprocessing,name,description,operationtype,classtype,javaclass,javascript,status) VALUES (4,NULL,'notification_getById','Retorna detalhes de uma notificação para Mobile','Sync','Java','br.com.centralit.citsmart.rest.operation.RestMobile',NULL,'A');
INSERT INTO rest_operation (idrestoperation,idbatchprocessing,name,description,operationtype,classtype,javaclass,javascript,status) VALUES (5,NULL,'notification_feedback','Processa feedback de uma notificação para Mobile','Sync','Java','br.com.centralit.citsmart.rest.operation.RestMobile',NULL,'A');
INSERT INTO rest_operation (idrestoperation,idbatchprocessing,name,description,operationtype,classtype,javaclass,javascript,status) VALUES (6,NULL,'notification_new','Cria uma notificação para Mobile','Sync','Java','br.com.centralit.citsmart.rest.operation.RestMobile',NULL,'A');
INSERT INTO rest_operation (idrestoperation,idbatchprocessing,name,description,operationtype,classtype,javaclass,javascript,status) VALUES (7,NULL,'notification_getReasons','Retorna justificativas possíveis para uma notificação','Sync','Java','br.com.centralit.citsmart.rest.operation.RestMobile',NULL,'A');

INSERT INTO rest_domain (idrestparameter,idrestoperation,value) VALUES (1,1,'1');
INSERT INTO rest_domain (idrestparameter,idrestoperation,value) VALUES (1,6,'1');
INSERT INTO rest_domain (idrestparameter,idrestoperation,value) VALUES (2,1,'7');
INSERT INTO rest_domain (idrestparameter,idrestoperation,value) VALUES (2,6,'8');
INSERT INTO rest_domain (idrestparameter,idrestoperation,value) VALUES (3,1,'1');
INSERT INTO rest_domain (idrestparameter,idrestoperation,value) VALUES (3,6,'1');
INSERT INTO rest_domain (idrestparameter,idrestoperation,value) VALUES (4,1,'3');
INSERT INTO rest_domain (idrestparameter,idrestoperation,value) VALUES (4,6,'3');
INSERT INTO rest_domain (idrestparameter,idrestoperation,value) VALUES (5,1,'3');
INSERT INTO rest_domain (idrestparameter,idrestoperation,value) VALUES (5,6,'3');

-- Fim Carlos

-- Início Carlos 02/09/2013

update templatesolicitacaoservico set aprovacao = 'N' where identificacao not in ('AprovacaoSolicitacaoServico','AutorizacaoCotacao','AprovacaoCotacao');
update templatesolicitacaoservico set aprovacao = 'S' where identificacao in ('AprovacaoSolicitacaoServico','AutorizacaoCotacao','AprovacaoCotacao');

-- Fim Carlos

-- Início Bruno Franco 02/09/2013

CREATE TABLE assinaturaaprovacaoprojeto (
  idassinaturaaprovacaoprojeto int(11) NOT NULL,
  idprojeto int(11) NOT NULL,
  idempregado int(11) NOT NULL,
  papel varchar(100) NULL,
  ordem varchar(100) NULL,
  PRIMARY KEY (idassinaturaaprovacaoprojeto),
  constraint foreign key (idprojeto) references projetos (idprojeto),
  constraint foreign key (idempregado) references empregados (idempregado)
) ENGINE=InnoDB;

-- Fim Bruno Franco

-- Início Murilo Pacheco 03/09/2013
CREATE TABLE rh_conhecimento 
  ( 
     idconhecimento INT(11) NOT NULL, 
     descricao      CHAR(100) NOT NULL, 
     detalhe        CHAR(100) NOT NULL, 
     PRIMARY KEY (idconhecimento) 
  ) engine=innodb;

CREATE TABLE rh_experienciainformatica 
  ( 
     idexperienciainformatica INT(11) NOT NULL, 
     descricao                CHAR(100) NOT NULL, 
     detalhe                  CHAR(100) NOT NULL, 
     PRIMARY KEY (idexperienciainformatica) 
  ) engine=innodb;

CREATE TABLE rh_habilidade 
  ( 
     idhabilidade INT(11) NOT NULL, 
     descricao    CHAR(100) NOT NULL, 
     detalhe      CHAR(100) NOT NULL, 
     PRIMARY KEY (idhabilidade) 
  ) engine=innodb;

CREATE TABLE rh_descricaocargo 
  ( 
     iddescricaocargo     INT(11) NOT NULL, 
     nomecargo            CHAR(100) NOT NULL, 
     idcbo                INT(11) DEFAULT NULL, 
     atividades           CHAR(100) NOT NULL, 
     situacao             CHAR(1) NOT NULL, 
     idsolicitacaoservico INT(11) DEFAULT NULL, 
     observacoes          TEXT, 
     idparecervalidacao   INT(11) DEFAULT NULL, 
     PRIMARY KEY (iddescricaocargo) 
  ) engine=innodb;

CREATE TABLE rh_atitudeindividual 
  ( 
     idatitudeindividual INT(11) NOT NULL, 
     descricao           CHAR(100) NOT NULL, 
     detalhe             CHAR(100) NOT NULL, 
     PRIMARY KEY (idatitudeindividual) 
  ) engine=innodb;

CREATE TABLE rh_cargoatitudeindividual 
  ( 
     idatitudeindividual INT(11) NOT NULL, 
     iddescricaocargo    INT(11) NOT NULL, 
     obrigatorio         CHAR(1) NOT NULL, 
     PRIMARY KEY (idatitudeindividual, iddescricaocargo), 
     KEY fk_reference_722 (iddescricaocargo), 
     CONSTRAINT fk_reference_722 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo), 
     CONSTRAINT fk_reference_723 FOREIGN KEY (idatitudeindividual) REFERENCES rh_atitudeindividual (idatitudeindividual) 
  ) engine=innodb;

CREATE TABLE rh_atitudeorganizacional 
  ( 
     idatitudeorganizacional INT(11) NOT NULL, 
     descricao               VARCHAR(200) NOT NULL, 
     detalhe                 TEXT, 
     PRIMARY KEY (idatitudeorganizacional) 
  ) engine=innodb;

CREATE TABLE rh_entrevistacandidato 
  ( 
     identrevista          INT(11) NOT NULL, 
     idcurriculo           INT(11) NOT NULL, 
     identrevistador       INT(11) NOT NULL, 
     tipoentrevista        VARCHAR(20) NOT NULL, 
     datahora              TIMESTAMP NOT NULL, 
     caracteristicas       TEXT, 
     possuioutraatividade  CHAR(1) NOT NULL, 
     outraatividade        TEXT, 
     concordaexclusividade CHAR(1) NOT NULL, 
     salarioatual          DECIMAL(6, 2) DEFAULT NULL, 
     pretensaosalarial     DECIMAL(6, 2) DEFAULT NULL, 
     datadisponibilidade   DATE DEFAULT NULL, 
     competencias          TEXT, 
     observacoes           TEXT, 
     resultado             CHAR(1) NOT NULL, 
     idtriagem             INT(11) DEFAULT NULL, 
     trabalhoemequipe      TEXT, 
     cargopretendido       CHAR(100) DEFAULT NULL, 
     planocarreira         CHAR(100) NOT NULL, 
     PRIMARY KEY (identrevista) 
  ) engine=innodb;

CREATE TABLE rh_atitudecandidato 
  ( 
     identrevista            INT(11) NOT NULL, 
     idatitudeorganizacional INT(11) NOT NULL, 
     avaliacao               CHAR(1) DEFAULT NULL, 
     PRIMARY KEY (identrevista, idatitudeorganizacional), 
     KEY fk_reference_atitudorg (idatitudeorganizacional), 
     CONSTRAINT fk_reference_entrevista FOREIGN KEY (identrevista) REFERENCES rh_entrevistacandidato (identrevista), 
     CONSTRAINT fk_reference_atitudorg FOREIGN KEY (idatitudeorganizacional) REFERENCES rh_atitudeorganizacional (idatitudeorganizacional) 
  ) engine=innodb;

CREATE TABLE rh_cargoexperienciaanterior 
  ( 
     iddescricaocargo INT(11) NOT NULL, 
     idconhecimento   INT(11) NOT NULL, 
     obrigatorio      CHAR(1) NOT NULL, 
     PRIMARY KEY (iddescricaocargo, idconhecimento) 
  ) engine=innodb;

CREATE TABLE rh_certificacao 
  ( 
     idcertificacao INT(11) NOT NULL, 
     descricao      CHAR(100) NOT NULL, 
     detalhe        CHAR(100) NOT NULL, 
     PRIMARY KEY (idcertificacao) 
  ) engine=innodb;

CREATE TABLE rh_certificacaocurriculo 
  ( 
     idcertificacao INT(11) NOT NULL, 
     idcurriculo    INT(11) NOT NULL, 
     versao         CHAR(100) NOT NULL, 
     validade       INT(11) NOT NULL, 
     descricao      CHAR(100) NOT NULL, 
     PRIMARY KEY (idcertificacao) 
  ) engine=innodb;

CREATE TABLE rh_certificacaorequisicaopessoal 
  ( 
     idcertificacao        INT(11) NOT NULL, 
     versaocertificacao    CHAR(100) NOT NULL, 
     validadecertificacao  CHAR(100) NOT NULL, 
     descricaocertificacao CHAR(100) NOT NULL, 
     idcurriculo           INT(11) NOT NULL, 
     PRIMARY KEY (idcertificacao) 
  ) engine=innodb;

CREATE TABLE rh_curriculo 
  ( 
     idcurriculo                 INT(11) NOT NULL, 
     portadornecessidadeespecial CHAR(1) NOT NULL, 
     iditemlistatipodeficiencia  INT(11) DEFAULT NULL, 
     qtdefilhos                  INT(11) DEFAULT NULL, 
     nome                        CHAR(100) NOT NULL, 
     sexo                        CHAR(1) NOT NULL, 
     cpf                         CHAR(15) NOT NULL, 
     estadocivil                 SMALLINT(6) NOT NULL, 
     datanascimento              DATE NOT NULL, 
     filhos                      CHAR(1) NOT NULL, 
     cidadenatal                 VARCHAR(100) NOT NULL, 
     idnaturalidade              INT(11) NOT NULL, 
     observacoesentrevista       CHAR(100) DEFAULT NULL, 
     PRIMARY KEY (idcurriculo) 
  ) engine=innodb;

CREATE TABLE rh_curso 
  ( 
     idcurso   INT(11) NOT NULL, 
     descricao CHAR(100) NOT NULL, 
     detalhe   CHAR(100) NOT NULL, 
     PRIMARY KEY (idcurso) 
  ) engine=innodb;

CREATE TABLE rh_emailcurriculo 
  ( 
     idemail        INT(11) NOT NULL, 
     idcurriculo    INT(11) NOT NULL, 
     principal      CHAR(1) NOT NULL, 
     descricaoemail VARCHAR(100) NOT NULL, 
     PRIMARY KEY (idemail) 
  ) engine=innodb;

CREATE TABLE rh_enderecocurriculo 
  ( 
     idendereco      INT(11) NOT NULL, 
     idbairro        INT(11) NOT NULL, 
     idcidade        INT(11) NOT NULL, 
     iduf            INT(11) NOT NULL, 
     idcurriculo     INT(11) NOT NULL, 
     idtipoendereco  INT(11) NOT NULL, 
     cep             VARCHAR(20) NOT NULL, 
     complemento     VARCHAR(100) NOT NULL, 
     correspondencia CHAR(1) NOT NULL, 
     nomecidade      VARCHAR(100) NOT NULL, 
     nomebairro      VARCHAR(100) NOT NULL, 
     logradouro      VARCHAR(45) DEFAULT NULL, 
     PRIMARY KEY (idendereco) 
  ) engine=innodb;

CREATE TABLE rh_formacaoacademica 
  ( 
     idformacaoacademica INT(11) NOT NULL, 
     descricao           CHAR(100) NOT NULL, 
     detalhe             CHAR(100) NOT NULL, 
     PRIMARY KEY (idformacaoacademica) 
  ) engine=innodb;

CREATE TABLE rh_formacaocurriculo 
  ( 
     idformacao     INT(11) NOT NULL, 
     idtipoformacao INT(11) NOT NULL, 
     idsituacao     INT(11) NOT NULL, 
     idcurriculo    INT(11) NOT NULL, 
     instituicao    VARCHAR(100) NOT NULL, 
     descricao      VARCHAR(100) NOT NULL, 
     PRIMARY KEY (idformacao) 
  ) engine=innodb;

CREATE TABLE rh_jornadadetrabalho 
  ( 
     idjornada          INT(11) NOT NULL, 
     descricao          CHAR(100) NOT NULL, 
     escala             CHAR(1) NOT NULL, 
     considerarferiados CHAR(1) NOT NULL, 
     PRIMARY KEY (idjornada) 
  ) 
engine=innodb;

CREATE TABLE rh_idioma 
  ( 
     ididioma  INT(11) NOT NULL, 
     descricao CHAR(100) NOT NULL, 
     detalhe   CHAR(100) NOT NULL, 
     PRIMARY KEY (ididioma) 
  ) engine=innodb;

CREATE TABLE rh_requisicaoatitudeindividual 
  ( 
     idatitudeindividual  INT(11) NOT NULL, 
     idsolicitacaoservico INT(11) NOT NULL, 
     obrigatorio          CHAR(1) NOT NULL, 
     PRIMARY KEY (idatitudeindividual, idsolicitacaoservico) 
  ) engine=innodb;

CREATE TABLE rh_requisicaocertificacao 
  ( 
     idcertificacao       INT(11) NOT NULL, 
     idsolicitacaoservico INT(11) NOT NULL, 
     obrigatorio          CHAR(1) DEFAULT NULL, 
     PRIMARY KEY (idcertificacao, idsolicitacaoservico) 
  ) engine=innodb;

CREATE TABLE rh_requisicaoconhecimento 
  ( 
     idconhecimento       INT(11) NOT NULL, 
     idsolicitacaoservico INT(11) NOT NULL, 
     obrigatorio          CHAR(1) DEFAULT NULL, 
     PRIMARY KEY (idconhecimento, idsolicitacaoservico) 
  ) engine=innodb;

CREATE TABLE rh_requisicaocurso 
  ( 
     idsolicitacaoservico INT(11) NOT NULL, 
     idcurso              INT(11) NOT NULL, 
     obrigatorio          CHAR(1) DEFAULT NULL, 
     PRIMARY KEY (idsolicitacaoservico, idcurso) 
  ) engine=innodb;

CREATE TABLE rh_requisicaoexperienciaanterior 
  ( 
     idconhecimento       INT(11) NOT NULL, 
     idsolicitacaoservico INT(11) NOT NULL, 
     obrigatorio          CHAR(1) DEFAULT NULL, 
     PRIMARY KEY (idconhecimento, idsolicitacaoservico) 
  ) engine=innodb;

CREATE TABLE rh_requisicaoexperienciainformatica 
  ( 
     idexperienciainformatica INT(11) NOT NULL, 
     idsolicitacaoservico     INT(11) NOT NULL, 
     obrigatorio              CHAR(1) DEFAULT NULL, 
     PRIMARY KEY (idexperienciainformatica, idsolicitacaoservico) 
  ) engine=innodb;

CREATE TABLE rh_requisicaoformacaoacademica 
  ( 
     idformacaoacademica  INT(11) NOT NULL, 
     idsolicitacaoservico INT(11) NOT NULL, 
     obrigatorio          CHAR(1) DEFAULT NULL, 
     PRIMARY KEY (idformacaoacademica, idsolicitacaoservico) 
  ) engine=innodb;

CREATE TABLE rh_requisicaopessoal 
  ( 
     idsolicitacaoservico INT(11) NOT NULL, 
     idcargo              CHAR(100) NOT NULL, 
     vagas                INT(11) NOT NULL, 
     tipocontratacao      CHAR(1) DEFAULT NULL, 
     motivocontratacao    CHAR(1) DEFAULT NULL, 
     salario              DOUBLE NOT NULL, 
     idcentrocusto        INT(11) NOT NULL, 
     idprojeto            INT(11) NOT NULL, 
     rejeitada            CHAR(1) DEFAULT NULL, 
     idparecervalidacao   INT(11) DEFAULT NULL, 
     situacao             CHAR(1) DEFAULT NULL, 
     confidencial         CHAR(1) NOT NULL, 
     dataabertura         DATE NOT NULL, 
     beneficios           CHAR(100) DEFAULT NULL, 
     folgas               CHAR(100) DEFAULT NULL, 
     horario              CHAR(100) DEFAULT NULL, 
     PRIMARY KEY (idsolicitacaoservico) 
  ) engine=innodb;

CREATE TABLE rh_telefonecurriculo 
  ( 
     idtelefone     INT(11) NOT NULL, 
     idtipotelefone INT(11) NOT NULL, 
     ddd            INT(3) NOT NULL, 
     numerotelefone VARCHAR(15) NOT NULL, 
     idcurriculo    INT(11) NOT NULL, 
     PRIMARY KEY (idtelefone) 
  ) engine=innodb;

CREATE TABLE rh_triagemrequisicaopessoal 
  ( 
     idtriagem                      INT(11) NOT NULL, 
     idsolicitacaoservico           INT(11) NOT NULL, 
     idcurriculo                    INT(11) NOT NULL, 
     iditemtrabalhoentrevistarh     INT(11) DEFAULT NULL, 
     iditemtrabalhoentrevistagestor INT(11) DEFAULT NULL, 
     PRIMARY KEY (idtriagem), 
     KEY fk_reference_743 (idsolicitacaoservico), 
     KEY fk_reference_744 (idcurriculo), 
     CONSTRAINT fk_reference_743 FOREIGN KEY (idsolicitacaoservico) REFERENCES rh_requisicaopessoal (idsolicitacaoservico), 
     CONSTRAINT fk_reference_744 FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo (idcurriculo) 
  ) engine=innodb;

CREATE TABLE rh_cargocertificacao 
  ( 
     iddescricaocargo INT(11) NOT NULL, 
     idcertificacao   INT(11) NOT NULL, 
     obrigatorio      CHAR(1) NOT NULL, 
     PRIMARY KEY (iddescricaocargo, idcertificacao), 
     KEY fk_reference_727 (idcertificacao), 
     CONSTRAINT fk_reference_726 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo), 
     CONSTRAINT fk_reference_727 FOREIGN KEY (idcertificacao) REFERENCES rh_certificacao (idcertificacao) 
  ) engine=innodb;

CREATE TABLE rh_cargoconhecimento 
  ( 
     iddescricaocargo INT(11) NOT NULL, 
     idconhecimento   INT(11) NOT NULL, 
     obrigatorio      CHAR(1) NOT NULL, 
     PRIMARY KEY (iddescricaocargo, idconhecimento), 
     KEY fk_reference_725 (idconhecimento), 
     CONSTRAINT fk_reference_724 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo), 
     CONSTRAINT fk_reference_725 FOREIGN KEY (idconhecimento) REFERENCES rh_conhecimento (idconhecimento) 
  ) engine=innodb;

CREATE TABLE rh_cargocurso 
  ( 
     iddescricaocargo INT(11) NOT NULL, 
     idcurso          INT(11) NOT NULL, 
     obrigatorio      CHAR(1) NOT NULL, 
     PRIMARY KEY (iddescricaocargo, idcurso), 
     KEY fk_reference_729 (idcurso), 
     CONSTRAINT fk_reference_728 FOREIGN KEY (iddescricaocargo) REFERENCES  rh_descricaocargo (iddescricaocargo), 
     CONSTRAINT fk_reference_729 FOREIGN KEY (idcurso) REFERENCES rh_curso (idcurso) 
  ) engine=innodb;

CREATE TABLE rh_cargoexperienciainformatica 
  ( 
     idexperienciainformatica INT(11) NOT NULL, 
     iddescricaocargo         INT(11) NOT NULL, 
     obrigatorio              CHAR(1) NOT NULL, 
     PRIMARY KEY (idexperienciainformatica, iddescricaocargo), 
     KEY fk_reference_733 (iddescricaocargo), 
     CONSTRAINT fk_reference_732 FOREIGN KEY (idexperienciainformatica) REFERENCES rh_experienciainformatica (idexperienciainformatica), 
     CONSTRAINT fk_reference_733 FOREIGN KEY (iddescricaocargo) REFERENCES  rh_descricaocargo (iddescricaocargo) 
  ) engine=innodb;

CREATE TABLE rh_cargoformacaoacademica 
  ( 
     idformacaoacademica INT(11) NOT NULL, 
     iddescricaocargo    INT(11) NOT NULL, 
     obrigatorio         CHAR(1) NOT NULL, 
     PRIMARY KEY (idformacaoacademica, iddescricaocargo), 
     KEY fk_reference_735 (iddescricaocargo), 
     CONSTRAINT fk_reference_734 FOREIGN KEY (idformacaoacademica) REFERENCES rh_formacaoacademica (idformacaoacademica), 
     CONSTRAINT fk_reference_735 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo) 
  ) engine=innodb;

CREATE TABLE rh_cargohabilidade 
  ( 
     idhabilidade     INT(11) NOT NULL, 
     iddescricaocargo INT(11) NOT NULL, 
     obrigatorio      CHAR(1) NOT NULL, 
     PRIMARY KEY (idhabilidade, iddescricaocargo), 
     KEY fk_reference_737 (iddescricaocargo), 
     CONSTRAINT fk_reference_736 FOREIGN KEY (idhabilidade) REFERENCES rh_habilidade (idhabilidade), 
     CONSTRAINT fk_reference_737 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo) 
  ) engine=innodb;

CREATE TABLE rh_cargoidioma 
  ( 
     iddescricaocargo INT(11) NOT NULL, 
     ididioma         INT(11) NOT NULL, 
     obrigatorio      CHAR(1) NOT NULL, 
     PRIMARY KEY (iddescricaocargo, ididioma), 
     KEY fk_reference_731 (ididioma), 
     CONSTRAINT fk_reference_730 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo), 
     CONSTRAINT fk_reference_731 FOREIGN KEY (ididioma) REFERENCES rh_idioma (ididioma) 
  ) engine=innodb;

CREATE TABLE rh_requisicaohabilidade 
  ( 
     idhabilidade         INT(11) NOT NULL, 
     idsolicitacaoservico INT(11) NOT NULL, 
     obrigatorio          CHAR(1) DEFAULT NULL, 
     PRIMARY KEY (idhabilidade, idsolicitacaoservico), 
     KEY fk_reference_739 (idsolicitacaoservico), 
     CONSTRAINT fk_reference_738 FOREIGN KEY (idhabilidade) REFERENCES rh_habilidade (idhabilidade), 
     CONSTRAINT fk_reference_739 FOREIGN KEY (idsolicitacaoservico) REFERENCES rh_requisicaopessoal (idsolicitacaoservico) 
  ) engine=innodb;

CREATE TABLE rh_requisicaoidioma 
  ( 
     ididioma             INT(11) NOT NULL, 
     idsolicitacaoservico INT(11) NOT NULL, 
     obrigatorio          CHAR(1) DEFAULT NULL, 
     PRIMARY KEY (ididioma, idsolicitacaoservico), 
     KEY fk_reference_741 (idsolicitacaoservico), 
     CONSTRAINT fk_reference_740 FOREIGN KEY (ididioma) REFERENCES rh_idioma (ididioma), 
     CONSTRAINT fk_reference_741 FOREIGN KEY (idsolicitacaoservico) REFERENCES rh_requisicaopessoal (idsolicitacaoservico) 
  ) engine=innodb;

-- Fim Murilo Pacheco

-- Início Maycon 04/09/2013

alter table problema add column fecharItensRelacionados varchar(4);
alter table requisicaomudanca add column  fecharItensRelacionados varchar(4);

-- Fim Maycon

-- Início Gebber 04/09/2013

ALTER TABLE acordonivelservico ADD column (idemail integer);

ALTER TABLE acordonivelservico ADD CONSTRAINT fk_email FOREIGN KEY ( idemail ) REFERENCES modelosemails ( idmodeloemail);

-- Fim Gebber

-- Início Bruno Franco 12/09/2013

alter table aprovacaoproposta engine = innodb;
alter table aprovacaorequisicaoliberacao engine = innodb;
alter table ccmodulosistema engine = innodb;
alter table conhecimentoliberacao engine = innodb;
alter table contatorequisicaoliberacao engine = innodb;
alter table controlecontrato engine = innodb;
alter table controlecontratoocorrencia engine = innodb;
alter table controlecontratopagamento engine = innodb;
alter table controlecontratotreinamento engine = innodb;
alter table controlecontratoversao engine = innodb;
alter table criterioitemcotacao engine = innodb;
alter table execucaoliberacao engine = innodb;
alter table formapagamento engine = innodb;
alter table historicoliberacao engine = innodb;
alter table historicosolicitacaoservico engine = innodb;
alter table infocatalogoservico engine = innodb;
alter table justificativaliberacao engine = innodb;
alter table liberacaomudanca engine = innodb;
alter table liberacaoproblema engine = innodb;
alter table ligacao_mud_hist_ic engine = innodb;
alter table ligacao_mud_hist_pr engine = innodb;
alter table ligacao_mud_hist_resp engine = innodb;
alter table ligacao_mud_hist_risco engine = innodb;
alter table ligacao_mud_hist_se engine = innodb;
alter table midiasoftwarechave engine = innodb;
alter table modulosistema engine = innodb;
alter table ocorrencialiberacao engine = innodb;
alter table requisicaoliberacaoitemconfiguracao engine = innodb;
alter table requisicaomudancaliberacao engine = innodb;
alter table requisicaomudancarisco engine = innodb;
alter table reuniaorequisicaomudanca engine = innodb;
alter table risco engine = innodb;
alter table solucaocontorno engine = innodb;
alter table solucaodefinitiva engine = innodb;
alter table tipoliberacao engine = innodb;
alter table tipomovimfinanceiraviagem engine = innodb;
alter table tiposubscricao engine = innodb;
alter table versao engine = innodb;
alter table aprovacaorequisicaoliberacao engine = innodb;
alter table ccmodulosistema engine = innodb;
alter table conhecimentoliberacao engine = innodb;
alter table contatorequisicaoliberacao engine = innodb;
alter table controlecontrato engine = innodb;
alter table controlecontratoocorrencia engine = innodb;
alter table controlecontratopagamento engine = innodb;
alter table controlecontratotreinamento engine = innodb;
alter table controlecontratoversao engine = innodb;
alter table criterioitemcotacao engine = innodb;
alter table escalonamento engine = innodb;
alter table execucaoliberacao engine = innodb;
alter table historicoliberacao engine = innodb;
alter table infocatalogoservico engine = innodb;
alter table justificativaliberacao engine = innodb;

-- Fim Bruno Franco

set sql_safe_updates = 1;
