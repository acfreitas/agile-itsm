-- Início Bruno 23/08/2013

alter table recursotarefalinbaseproj add (esforcoporos varchar2(50) null);

alter table tarefalinhabaseprojeto add (esforcoporos varchar2(50) null);

-- Fim Bruno

-- Início Carlos 27/08/2013

alter table templatesolicitacaoservico add  aprovacao char(1);

-- Fim Carlos

-- Início Carlos 28/08/2013

create table rest_domain  (
   "idrestparameter"    integer                         not null,
   "idrestoperation"    integer                         not null,
   "value"              clob                            not null,
   constraint pk_rest_domain primary key ("idrestparameter", "idrestoperation")
);

create table rest_execution  (
   "idrestexecution"    integer                         not null,
   "idrestoperation"    integer                         not null,
   "iduser"             integer,
   "inputdatetime"      date                            not null,
   "inputclass"         varchar2(200),
   "inputdata"          clob,
   "status"             varchar2(20)                    not null,
   constraint pk_rest_execution primary key ("idrestexecution")
);

comment on column rest_execution."status" is 'N - Não processado P - Processado E - Erro X - Cancelado';

create table rest_log  (
   "idrestlog"          integer                         not null,
   "idrestexecution"    integer                         not null,
   "datetime"           date                            not null,
   "resultclass"        varchar2(200),
   "resultdata"         clob,
   "status"             varchar2(20)                    not null,
   constraint pk_rest_log primary key ("idrestlog")
);

comment on column rest_log."status" is 'P - Processado E - Erro';

create table rest_operation  (
   "idrestoperation"    integer                         not null,
   "idbatchprocessing"  integer,
   "name"               varchar2(50)                    not null,
   "description"        clob                            not null,
   "operationtype"      varchar2(20)                    not null,
   "classtype"          varchar2(20)                    not null,
   "javaclass"          varchar2(200),
   "javascript"         clob,
   "status"             varchar2(20)                    not null,
   constraint pk_rest_operation primary key ("idrestoperation")
);

comment on column rest_operation."operationtype" is 'Sync - Síncrona com log Async - Assíncrona com log';

comment on column rest_operation."classtype" is 'S = JavaScript J = Java';

comment on column rest_operation."status" is 'A = Ativo I = Inativo';

create unique index ix_url on rest_operation (
   "name" asc
);

create table rest_parameter  (
   "idrestparameter"    integer                         not null,
   "identifier"         varchar2(100)                   not null,
   "description"        varchar2(200)                   not null,
   constraint pk_rest_parameter primary key ("idrestparameter")
);

create table rest_permission  (
   "idrestoperation"    integer                         not null,
   "idgroup"            integer                         not null,
   "status"             char(1)                         not null,
   constraint pk_rest_permission primary key ("idrestoperation", "idgroup")
);

create table rest_translation  (
   "idresttranslation"  integer                         not null,
   "idrestoperation"    integer,
   "idbusinessobject"   integer                         not null,
   "fromvalue"          varchar2(100)                   not null,
   "tovalue"            varchar2(100)                   not null,
   constraint pk_rest_translation primary key ("idresttranslation")
);

alter table rest_domain add constraint fk_rest_domain1 foreign key ("idrestparameter") references rest_parameter ("idrestparameter");

alter table rest_domain add constraint fk_rest_dom_reference_rest_ope foreign key ("idrestoperation") references rest_operation ("idrestoperation");

alter table rest_execution add constraint fk_restexecution_1 foreign key ("iduser") references usuario ("idusuario");

alter table rest_execution add constraint fk_ref_restexecution_2 foreign key ("idrestoperation") references rest_operation ("idrestoperation");

alter table rest_log add constraint fk_ref_restexecution foreign key ("idrestexecution") references rest_execution ("idrestexecution");

alter table rest_operation add constraint fk_ref_procbatch foreign key ("idbatchprocessing") references processamentobatch ("idprocessamentobatch");

alter table rest_permission add constraint fk_restpermission_2 foreign key ("idgroup") references grupo ("idgrupo");

alter table rest_permission add constraint fk_rest_permission1 foreign key ("idrestoperation") references rest_operation ("idrestoperation");

alter table rest_translation add constraint fk_ref_restoperation1 foreign key ("idrestoperation") references rest_operation ("idrestoperation");

alter table rest_translation add constraint fk_ref_businessobj foreign key ("idbusinessobject") references objetonegocio ("idobjetonegocio");

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
  idassinaturaaprovacaoprojeto number(10,0) NOT NULL,
  idprojeto number(10,0) NOT NULL,
  idempregado number(10,0) NOT NULL,
  papel varchar(100) NULL,
  ordem varchar(100) NULL
);

alter table assinaturaaprovacaoprojeto add constraint pk_assinaturaaprovacaoprojeto primary key (idassinaturaaprovacaoprojeto);

-- Fim Bruno Franco

alter table tarefalinhabaseprojeto add (esforcoporos varchar2(50) null);

-- Início Maycon 04/09/2013

alter table problema add (fecharItensRelacionados varchar2(4) null);
alter table requisicaomudanca add (fecharItensRelacionados varchar2(4) null);

-- Fim Maycon

-- Início Gebber 04/09/2013

alter table acordonivelservico add(idemail numeric);

ALTER TABLE acordonivelservico ADD CONSTRAINT fk_email FOREIGN KEY (idemail) REFERENCES modelosemails (idmodeloemail);

-- Fim Gebber
