-- Revisado por: thiago.monteiro
-- Data: 29/04/2013
-- SGBD: Oracle

set define off;

-- SCRIPT SQL COMPLEMENTAR ADICIONADO EM 18:21 04-04-2013

alter table scripts modify tipo varchar2(10 char);

insert into scripts (idscript, nome, descricao, sqlquery, tipo, datainicio) values (3, 'Consulta erros de rotina de scripts', 'Consulta os erros que ocorreram durante a execução automática de scripts feita durante a inicialização do sistema', 'select scripts.descricao, scripts.sqlquery, versao.nomeversao from scripts inner join versao on scripts.idversao = versao.idversao where scripts.idversao is not null and scripts.descricao like ''ERRO%'';', 'consulta', to_date('2013-04-04', 'YYYY-MM-DD') );

insert into scripts (idscript, nome, descricao, sqlquery, tipo, datainicio) values (5, 'Limpa erros de rotina de scripts', 'Limpa o registro de erros que ocorreram durante a execução automática de scripts feita durante a inicialização do sistema', 'update scripts set descricao = concat(''CORRIGIDO '', descricao) where idversao is not null and descricao like ''ERRO%'' and idscript > 0;', 'update', to_date('2013-04-04', 'YYYY-MM-DD') );

insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('B', 'Baixa');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('I', 'Intermediária');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('M', 'Mediana');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('A', 'Alta');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('E', 'Especialista');

-- INICIO - MURILO GABRIEL RODRIGUES - SENTENCAS ADICIONADAS A PEDIDO DO VALDOILO
CREATE TRIGGER "UPDATE_SLAREQ"
BEFORE INSERT OR UPDATE OF DATAULTMODIFICACAO ON SLAREQUISITOSLA 
FOR EACH ROW
BEGIN
  :NEW.DATAULTMODIFICACAO := SYSTIMESTAMP; 
END;
ALTER TRIGGER "UPDATE_SLAREQ" ENABLE;

alter table categoriagaleriaimagem add (datafim date);

alter table categoriagaleriaimagem add (datainicio date);
ALTER TRIGGER "UPDATE_SLAREQ" ENABLE;

ALTER TABLE PROCESSAMENTOBATCH MODIFY (EXPRESSAOCRON NULL);
-- FIM 

-- INICIO - MODULO DE MUDANCA
alter table requisicaomudanca add (nomecategoriamudanca varchar2(15) );

alter table grupo add (comiteconsultivomudanca varchar2(1) null);

create table tipomudanca (
    idtipomudanca number(10,0) not null,
    idtipofluxo number(10,0) null,
    idmodeloemailcriacao number(10,0) null,
    idmodeloemailfinalizacao number(10,0) null,
    idmodeloemailacoes number(10,0) null,
    idgrupoexecutor number(10,0) null,
    idcalendario number(10,0) null,
    nometipomudanca varchar2(100) null,
    datainicio date null,
    datafim date null
);

alter table tipomudanca add primary key (idtipomudanca);
alter table tipomudanca add constraint fk_tipomudanca_bpm_tipofluxo foreign key (idtipofluxo) references bpm_tipofluxo (idtipofluxo);
alter table tipomudanca add constraint fk_tipomudanca_modelosemails_c foreign key (idmodeloemailcriacao) references modelosemails (idmodeloemail);

alter table tipomudanca add constraint fk_tipomudanca_modelosemails_f foreign key (idmodeloemailfinalizacao) references modelosemails (idmodeloemail);

alter table tipomudanca add constraint fk_tipomudanca_modelosemails_a  foreign key (idmodeloemailacoes) references modelosemails (idmodeloemail);

alter table tipomudanca add constraint fk_tipomudanca_grupo foreign key (idgrupoexecutor) references grupo (idgrupo);

alter table tipomudanca add constraint fk_tipomudanca_calendario foreign key (idcalendario) references calendario (idcalendario);

alter table requisicaomudanca drop constraint fk_requisic_reference_cat;

-- alter table requisicaomudanca drop column tipo;
alter table requisicaomudanca rename column idcategoriamudanca to idtipomudanca;

alter table requisicaomudanca modify (idtipomudanca number(10,0) null);


alter table requisicaomudanca add constraint fk_requisicaomudanca_tipomudan foreign key (idtipomudanca) references tipomudanca (idtipomudanca);

alter table requisicaomudanca add (idcontrato number(10,0) not null);

alter table requisicaomudanca add (idunidade number(10,0) );

alter table requisicaomudanca add (idcontatorequisicaomudanca number(10,0) );

create table contatorequisicaomudanca (
    idcontatorequisicaomudanca number(10,0) not null,
    nomecontato varchar2(100),
    telefonecontato varchar2(100),
    emailcontato varchar2(200),
    observacao clob,
    idlocalidade number(10,0),
    ramal varchar2(5)
);

alter table contatorequisicaomudanca add primary key (idcontatorequisicaomudanca);

alter table requisicaomudanca add (idgrupocomite number(10,0) null );

alter table requisicaomudanca add constraint fk_requisicaomudanca_grupo foreign key (idgrupocomite) references grupo (idgrupo);

create table aprovacaomudanca (
    idaprovacaomudanca number(10,0) not null,
    idrequisicaomudanca number(10,0) null,
    idempregado number(10,0) null,
    nomeempregado varchar2(45) null,
    voto char(1) null,
    comentario varchar2(200) null
);

alter table aprovacaomudanca add primary key (idaprovacaomudanca);

create index fk_requisicaomudanca_idx on aprovacaomudanca (idrequisicaomudanca);

create index fk_empregado_idx on aprovacaomudanca (idempregado);

alter table aprovacaomudanca add constraint fk_requisicaomudanca foreign key (idrequisicaomudanca) references requisicaomudanca (idrequisicaomudanca);

alter table aprovacaomudanca add constraint fk_empregado foreign key (idempregado) references empregados (idempregado);

alter table requisicaomudanca add (enviaemailgrupocomite varchar2(1) null);

alter table ocorrenciamudanca add (idcategoriaocorrencia number(10,0) );

alter table ocorrenciamudanca add (idorigemocorrencia number(10,0) );

alter table requisicaomudanca add (datahorainicioagendada date default null);

alter table requisicaomudanca add (datahoraterminoagendada date default null);

alter table requisicaomudanca add (idlocalidade number(10,0) );

alter table aprovacaomudanca add (datahorainicio date null);

create table justificativamudanca (
    idjustificativamudanca number(10,0) not null,
    descricaojustificativa varchar2(100) not null,
    suspensao char(1) not null,
    situacao char(1) not null,
    aprovacao char(1) default null,
    deleted char(1) default null
);

alter table justificativamudanca add primary key (idjustificativamudanca);

create table anexomudanca (
    idanexomudanca number(10,0) not null,
    idmudanca number(10,0),
    datainicio date,
    datafim date,
    nome varchar2(256),
    link varchar2(256),
    extensao varchar2(10),
    descricao varchar2(256)
);

alter table anexomudanca add primary key (idanexomudanca);

-- No MySQL por padrão é '0000-00-00 00:00:00' no Oracle não tem essa representação.
alter table requisicaomudanca modify datahoraconclusao date not null;

alter table requisicaomudanca add (fechamento clob null);

-- SCRIPT ADICIONAL PARA RESOLVER O PROBLEMA DE ADICAO DA DATA ATUAL NOS CAMPOS DA TABELA.
alter table requisicaomudanca modify datahoraconclusao date default null;

alter table requisicaomudanca modify datahorainicio date default null;

alter table requisicaomudanca modify datahoratermino date default null;

alter table requisicaomudanca modify datahoracaptura date default null;

alter table requisicaomudanca modify datahorareativacao date default null;

alter table requisicaomudanca modify datahorasuspensao date default null;

alter table requisicaomudanca modify datahorainicioagendada date default null;

alter table requisicaomudanca modify datahoraterminoagendada date default null;

-- CARGA DE DADOS
-- bpm_tipofluxo

insert into bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) values (6, 'RequisicaoMudancaPadrao', 'Requisição de Mudança Padrão', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca');

insert into bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) values (7, 'RequisicaoMudancaEmergencial', 'Requisição Mudança Emergencial', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca');

insert into bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) values (8, 'RequisicaodeMudancaNormal', 'Requisição de Mudança Normal', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca');

-- bpm_fluxo

insert into bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) values (42, '1.0', 6, 'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual', '', to_date('2013-03-26', 'yyyy-mm-dd') , null);

insert into bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) values (43, '1.0', 7, 'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual', '', to_date('2013-03-26', 'yyyy-mm-dd'), null);

insert into bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) values (44, '1.0', 8, 'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual', '', to_date('2013-03-26', 'yyyy-mm-dd'), null);


-- bpm_elementofluxo

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (404, 42, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 163, 161, 32, 32, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (405, 42, 'Tarefa', '', 'Executar', 'Executar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao', '', ' #{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 397, 216, 65, 140, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (406, 42, 'Tarefa', '', 'Avaliar', 'Avaliar', 'U', ' pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 719, 260, 65, 140, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (407, 42, 'Script', '', 'Encerra', '', '', '', '', '', '', '', '', '#{execucaoFluxo}.encerra()', '', '', 1014, 246, 65, 140, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (408, 42, 'Finalizacao', null, null, null, null, null, null, null, null, null, null, null, null, null, 1326, 305, 32, 32, null, null, null, null, null, null, null, null);


insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (409, 43, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 90, 139, 32, 32, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (410, 43, 'Tarefa', '', 'Executar', 'Executar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 313, 162, 65, 140, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (411, 43, 'Tarefa', '', 'Avaliar', 'Avaliar', 'U', ' pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao', '', ' #{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 677, 231, 65, 140, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (412, 43, 'Script', null, 'Encerra', null, null, null, null, null, null, null, null, '#{execucaoFluxo}.encerra();', null, null, 1050, 247, 65, 140, null, null, null, null, null, null, null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (413, 43, 'Finalizacao', null, null, null, null, null, null, null, null, null, null, null, null, null, 1300, 287, 32, 32, null, null, null, null, null, null, null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (414, 44, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 94, 56, 32, 32, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (415, 44, 'Tarefa', '', 'Aprovar', 'Aprovar', 'U', '/pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Aprovacao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 67, 171, 65, 140, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (416, 44, 'Tarefa', '', 'Planejar', 'Planejar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Planejamento', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 255, 168, 65, 140, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (417, 44, 'Tarefa', '', 'Executar', 'Executar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 482, 168, 65, 140, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (418, 44, 'Tarefa', '', 'Avaliar', 'Avaliar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 783, 169, 65, 140, '', '', null, '', '', '', null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (419, 44, 'Script', null, 'Encerra', null, null, null, null, null, null, null, null, '#{execucaoFluxo}.encerra();', null, null, 1045, 173, 65, 140, null, null, null, null, null, null, null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) values (420, 44, 'Finalizacao', null, null, null, null, null, null, null, null, null, null, null, null, null, 1135, 425, 32, 32, null, null, null, null, null, null, null, null);


-- bpm_sequenciafluxo

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (404, 405, 42, null, null, '', 1, 3, 296, 212.75, 'N', '');

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (405, 406, 42, null, null, '', 1, 3, 628, 270.5, 'N', '');

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (406, 407, 42, null, null, '', 1, 3, 936.5, 285.5, 'N', null);

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (407, 408, 42, null, null, '', 1, 3, 1240, 299.75, 'N', null);

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (409, 410, 43, null, null, '', 1, 3, 296, 212.75, 'N', '');

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (410, 411, 43, null, null, '', 1, 3, 628, 270.5, 'N', '');

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (411, 412, 43, null, null, '', 1, 3, 936.5, 285.5, 'N', null);

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (412, 413, 43, null, null, '', 1, 3, 1240, 299.75, 'N', null);

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (414, 415, 44, null, null, '', 2, 0, 1143, 341.5, 'N', null);

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (415, 416, 44, null, null, '', 1, 3, 994, 213.5, 'N', null);

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (416, 417, 44, null, null, '', 1, 3, 702.5, 201, 'N', null);

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (417, 418, 44, null, null, '', 1, 3, 438.5, 200.5, 'N', null);

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (418, 419, 44, null, null, '', 1, 3, 231, 202, 'N', null);

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (419, 420, 44, null, null, '', 2, 0, 123.5, 129.5, 'N', null);


-- modelosemails

insert into modelosemails ( idmodeloemail , titulo , texto , situacao , identificador ) values (26, 'Requisição de Mudança em andamento - ${IDREQUISICAOMUDANCA}', 'Senhor(a) ${NOMESOLICITANTE}, <br /><br />Informamos que a requisi&ccedil;&atilde;o de mudan&ccedil;a registrada em ${DATAHORAINICIOSTR} est&aacute; em atendimento, conforme os dados abaixo:<br /><br /><strong>N&uacute;mero:</strong>&nbsp;${IDREQUISICAOMUDANCA}<br /><strong>T&iacute;tulo:</strong>&nbsp;${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br />${TITULO}<br /><strong><br /></strong>${DESCRICAO}<br /><br /><strong>Grupo de atendimento:</strong>&nbsp;${NOMEGRUPOATUAL}<br /><br />Atenciosamente, <br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda', 'A', 'AndamentoReqMud');

insert into modelosemails ( idmodeloemail , titulo , texto , situacao , identificador ) values (27, 'Requisição de mudança registrada - ${IDREQUISICAOMUDANCA}', 'Senhor(a) ${NOMESOLICITANTE}, <br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de mudan&ccedil;a foi registrada em ${DATAHORAINICIOSTR}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDREQUISICAOMUDANCA}<br /><strong>Tipo:</strong> ${TIPO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong> <br />${DESCRICAO}<br /><br />Atenciosamente, <br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.', 'A', 'AberturaReqMud');

insert into modelosemails ( idmodeloemail , titulo , texto , situacao , identificador ) values (28, 'Requisição de mudança finalizada - ${IDREQUISICAOMUDANCA}', 'Senhor(a) ${NOMESOLICITANTE}, <br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de mudan&ccedil;a foi finalizada em ${DATAHORACONCLUSAO}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDREQUISICAOMUDANCA}<br /><strong>Tipo:</strong> ${TIPO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Status:</strong>${STATUS}<br /><strong>Descri&ccedil;&atilde;o:</strong> <br />${DESCRICAO}<br /><br /><br />Atenciosamente, <br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.', 'A', 'FinalizadaReqMud');

insert into modelosemails ( idmodeloemail , titulo , texto , situacao , identificador ) values (29, 'Requisição encaminhada para o COMITÊ CONSULTIVO DE MUDANÇA ', '&nbsp;A Requisi&ccedil;&atilde;o de Mudan&ccedil;a abaixo foi encaminhada para o Comit&ecirc; Consultivo de Mudan&ccedil;a do qual o Senhor(a) faz parte:<div>&nbsp;</div><div><strong>N&uacute;mero</strong>: ${IDREQUISICAOMUDANCA}</div><div><strong>Tipo</strong>: ${TIPO}</div><div><strong>T&iacute;tulo</strong>: ${TITULO}</div><div>&nbsp;</div><div><strong>Descri&ccedil;&atilde;o</strong>:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div>', 'A', 'emailCCM');

insert into modelosemails ( idmodeloemail , titulo , texto , situacao , identificador ) values (30, 'Requisição Mudança encaminhada para seu GRUPO DE TRABALHO', '<div>&nbsp; A &nbsp;requisi&ccedil;&atilde;o mudan&ccedil;a abaixo foi encaminhada para seu Grupo de Trabalho:</div><div>&nbsp;</div><div>N&uacute;mero: ${IDREQUISICAOMUDANCA}</div><div>Tipo: ${TIPO}</div><div>Titulo: ${TITULO}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>Atenciosamente, <br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda.</div>', 'A', 'ReqMudancaGrupo');

-- tipomudanca

insert into tipomudanca (idtipomudanca, idtipofluxo, idmodeloemailcriacao, idmodeloemailfinalizacao, idmodeloemailacoes, idgrupoexecutor, idcalendario, nometipomudanca, datainicio, datafim) values (1, 7, 27, 28, 26, 1, 1, 'Emergencial', to_date('2013-03-14', 'yyyy-mm-dd'), null);

insert into tipomudanca (idtipomudanca, idtipofluxo, idmodeloemailcriacao, idmodeloemailfinalizacao, idmodeloemailacoes, idgrupoexecutor, idcalendario, nometipomudanca, datainicio, datafim) values (2, 6, 27, 28, 26, 1, 1, 'Padrão', to_date('2013-04-05', 'yyyy-mm-dd'), null);

insert into tipomudanca (idtipomudanca, idtipofluxo, idmodeloemailcriacao, idmodeloemailfinalizacao, idmodeloemailacoes, idgrupoexecutor, idcalendario, nometipomudanca, datainicio, datafim) values (3, 8, 27, 28, 26, 1, 1, 'Normal', to_date('2013-03-15', 'yyyy-mm-dd'), null);

-- FIM - MODULO MUDANCA

-- Início rodrigo.oliveira

alter table atividadesos add (contabilizar char(1) default null);

alter table atividadesos add (idservicocontratocontabil number(19,0) default null);

alter table atividadesservicocontrato add (contabilizar char(1) default null);

alter table atividadesservicocontrato add (idservicocontratocontabil number(19,0) default null);

create table vinculaosincidente (
   idos number(10,0) not null,
   idsolicitacaoservico number(19,0) not null,
   idatividadesos number(19,0) not null
);

alter table vinculaosincidente add primary key (idos, idsolicitacaoservico);

-- fim rodrigo.oliveira

-- INICIO - MODULO DE PROBLEMA

alter table problema modify datahoralimite date null;

alter table problema modify datahorasolicitacao date null;

-- FIM - MODULO DE PROBLEMA

-- Oracle - Flávio júnior - 15/04
-- Trigger DDL Statements

create or replace trigger trg_slareqsla_dataultmo_bf_upd
before update on slarequisitosla
for each row
begin
    select sysdate into :new.dataultmodificacao from dual; 
end trg_slareqsla_dataultmo_bf_upd;

create or replace trigger trg_slareqsla_dataultmo_bf_ins
before insert on slarequisitosla
for each row
begin
    select sysdate into :new.dataultmodificacao from dual; 
end trg_slareqsla_dataultmo_bf_ins;

alter table limitealcada rename column limitevaloritem to limiteitemusointerno;
alter table limitealcada rename column limitevalormensal to limitemensalusointerno;
alter table limitealcada add limiteitematendcliente numeric(11,2) DEFAULT NULL;
alter table limitealcada add limitemensalatendcliente numeric(11,2) DEFAULT NULL;
