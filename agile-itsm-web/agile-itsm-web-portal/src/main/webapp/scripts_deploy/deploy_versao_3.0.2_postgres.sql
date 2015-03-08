-- Início Valdoílo Santos 21/08/2013

CREATE OR REPLACE FUNCTION remove_acento(text) 	RETURNS text AS $BODY$  SELECT TRANSLATE($1,'áàãâäÁÀÃÂÄéèêëÉÈÊËíìîïÍÌÎÏóòõôöÓÒÕÔÖúùûüÚÙÛÜñÑçÇÿýÝ','aaaaaAAAAAeeeeEEEEiiiiIIIIoooooOOOOOuuuuUUUUnNcCyyY') $BODY$ LANGUAGE sql IMMUTABLE STRICT COST 100;
ALTER FUNCTION remove_acento(text) OWNER TO postgres;

-- Fim Valdoílo

-- Início Bruno 23/08/2013

alter table recursotarefalinbaseproj add column  esforcoporos varchar(50);

alter table tarefalinhabaseprojeto add column  esforcoporos varchar(50);

-- Fim Bruno

-- Início Carlos 27/08/2013

alter table templatesolicitacaoservico add column aprovacao char(1);

-- Fim Carlos

-- Início Carlos 28/08/2013

create table rest_domain (
   idrestparameter      int4                 not null,
   idrestoperation      int4                 not null,
   value                text                 not null,
   constraint pk_rest_domain primary key (idrestparameter, idrestoperation)
);

create table rest_execution (
   idrestexecution      int4                 not null,
   idrestoperation      int4                 not null,
   iduser               int4                 null,
   inputdatetime        date                 not null,
   inputclass           varchar(200)         null,
   inputdata            text                 null,
   status               varchar(20)          not null,
   constraint pk_rest_execution primary key (idrestexecution)
);

comment on column rest_execution.status is 'N - Não processado P - Processado E - Erro  X - Cancelado';

create table rest_log (
   idrestlog            int4                 not null,
   idrestexecution      int4                 not null,
   datetime             date                 not null,
   resultclass          varchar(200)         null,
   resultdata           text                 null,
   status               varchar(20)          not null,
   constraint pk_rest_log primary key (idrestlog)
);

comment on column rest_log.status is 'P - Processado E - Erro';

create table rest_operation (
   idrestoperation      int4                 not null,
   idbatchprocessing    int4                 null,
   name                 varchar(50)          not null,
   description          text                 not null,
   operationtype        varchar(20)          not null,
   classtype            varchar(20)          not null,
   javaclass            varchar(200)         null,
   javascript           text                 null,
   status               varchar(20)          not null,
   constraint pk_rest_operation primary key (idrestoperation)
);

comment on column rest_operation.operationtype is 'Sync - Síncrona com log Async - Assíncrona com log';

comment on column rest_operation.classtype is 'S = JavaScript J = Java';

comment on column rest_operation.status is 'A = Ativo I = Inativo';

create unique index ix_url on rest_operation (
name
);

create table rest_parameter (
   idrestparameter      int4                 not null,
   identifier           varchar(100)         not null,
   description          varchar(200)         not null,
   constraint pk_rest_parameter primary key (idrestparameter)
);

create table rest_permission (
   idrestoperation      int4                 not null,
   idgroup              int4                 not null,
   status               char(1)              not null,
   constraint pk_rest_permission primary key (idrestoperation, idgroup)
);

create table rest_translation (
   idresttranslation    int4                 not null,
   idrestoperation      int4                 null,
   idbusinessobject     int4                 not null,
   fromvalue            varchar(100)         not null,
   tovalue              varchar(100)         not null,
   constraint pk_rest_translation primary key (idresttranslation)
);

alter table rest_domain add constraint fk_rest_domain1 foreign key (idrestparameter) references rest_parameter (idrestparameter) on delete restrict on update restrict;

alter table rest_domain add constraint fk_rest_dom_reference_rest_ope foreign key (idrestoperation) references rest_operation (idrestoperation) on delete restrict on update restrict;

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
  idassinaturaaprovacaoprojeto integer NOT NULL,
  idprojeto integer NOT NULL,
  idempregado integer NOT NULL,
  papel varchar(100) NULL,
  ordem varchar(100) NULL,
constraint pk_assinaturaaprovacaoprojeto primary key (idassinaturaaprovacaoprojeto)
);

-- Fim Bruno Franco

-- Início Maycon 04/09/2013

alter table problema add column fecharItensRelacionados VARCHAR(4);
alter table requisicaomudanca add column  fecharItensRelacionados varchar(4);

-- Fim Maycon

-- Início Gebber 04/09/2013

alter table acordonivelservico add column  idemail integer;


ALTER TABLE acordonivelservico ADD CONSTRAINT fk_email FOREIGN KEY ( idemail ) REFERENCES modelosemails ( idmodeloemail);

-- Fim Gebber
