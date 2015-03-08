-- SCRIPT SQL COMPLEMENTAR ADICIONADO EM 18:21 04-04-2013

insert into scripts (idscript, nome, descricao, sqlquery, tipo, datainicio) 
values (1, 'Consulta erros de rotina de scripts', 'Consulta os erros que ocorreram durante a execução automática de scripts feita durante a inicialização do sistema', 'select scripts.descricao, scripts.sqlquery, versao.nomeversao from scripts inner join versao on scripts.idversao = versao.idversao where scripts.idversao is not null and scripts.descricao like ''ERRO%'';', 'consulta', '2013-04-04');

insert into scripts (idscript, nome, descricao, sqlquery, tipo, datainicio) 
values (3, 'Limpa erros de rotina de scripts', 'Limpa o registro de erros que ocorreram durante a execução automática de scripts feita durante a inicialização do sistema', 'update scripts set descricao = concat(''CORRIGIDO '', descricao) where idversao is not null and descricao like ''ERRO%'' and idscript > 0;', 'update', '2013-04-04');

alter table scripts alter column tipo type character varying(10);

insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('B', 'Baixa');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('I', 'Intermediária');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('M', 'Mediana');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('A', 'Alta');
insert into tipocomplexidade (complexidade, desctipocomplexidade) values ('E', 'Especialista');

-- INICIO - MODULO DE MUDANCA
alter table requisicaomudanca add column nomecategoriamudanca varchar(15);

alter table grupo add column comiteconsultivomudanca varchar(1) null ;

drop table tipomudanca;

create table tipomudanca (
   idtipomudanca integer not null,
   idtipofluxo integer null,
   idmodeloemailcriacao integer null,
   idmodeloemailfinalizacao integer null,
   idmodeloemailacoes integer null,
   idgrupoexecutor integer null,
   idcalendario integer null,
   nometipomudanca varchar(100) null,
   datainicio date null,
   datafim date null,
   primary key (idtipomudanca),
   foreign key (idtipofluxo) references bpm_tipofluxo (idtipofluxo),
   foreign key (idmodeloemailcriacao) references modelosemails (idmodeloemail),
   foreign key (idmodeloemailfinalizacao) references modelosemails (idmodeloemail),
   foreign key (idmodeloemailacoes) references modelosemails (idmodeloemail),
   foreign key (idgrupoexecutor) references grupo (idgrupo),
   foreign key (idcalendario) references calendario (idcalendario)
);

alter table requisicaomudanca

drop constraint requisicaomudanca_idcategoriamudanca_fkey;

-- alter table requisicaomudanca drop column tipo;

alter table requisicaomudanca rename idcategoriamudanca to idtipomudanca;

alter table requisicaomudanca 
add constraint fk_requisic_reference_tipomudanca foreign key (idtipomudanca) references tipomudanca (idtipomudanca);

alter table requisicaomudanca 
add column idcontrato int not null,
add column idunidade int,
add column idcontatorequisicaomudanca int;

create table contatorequisicaomudanca (
    idcontatorequisicaomudanca int not null,
    nomecontato varchar(100),
    telefonecontato varchar(100),
    emailcontato varchar(200),
    observacao text,
    idlocalidade int,
    ramal varchar(5),
    primary key ( idcontatorequisicaomudanca)
);

alter table requisicaomudanca add column idgrupocomite int null;

alter table requisicaomudanca add constraint fk_requisic_reference_grupo_comite 
foreign key (idgrupocomite) references grupo (idgrupo);

create table aprovacaomudanca (
   idaprovacaomudanca int not null,
   idrequisicaomudanca int null,
   idempregado int null,
   nomeempregado varchar(45) null,
   voto char(1) null,
   comentario varchar(200) null
);

alter table aprovacaomudanca add primary key (idaprovacaomudanca);

alter table aprovacaomudanca add constraint fk_requisicaomudanca_aprovacao 
foreign key (idrequisicaomudanca) references requisicaomudanca (idrequisicaomudanca);

alter table aprovacaomudanca add constraint fk_empregado_aprovacao foreign key (idempregado) 
references empregados (idempregado);

create index fk_empregado_idx on aprovacaomudanca (idempregado asc);

create index fk_requisicaomudanca_idx on aprovacaomudanca (idrequisicaomudanca asc);

alter table requisicaomudanca add column enviaemailgrupocomite varchar(1) null;

alter table ocorrenciamudanca
add column idcategoriaocorrencia int,
add column idorigemocorrencia int;

alter table requisicaomudanca 
add column datahorainicioagendada timestamp null default '1970-01-01 00:00:00', 
add column datahoraterminoagendada timestamp null default '1970-01-01 00:00:00';

alter table requisicaomudanca add column idlocalidade int;

alter table aprovacaomudanca add column datahorainicio timestamp null;

create table justificativamudanca (
   idjustificativamudanca int not null,
   descricaojustificativa varchar(100) not null,
   suspensao char(1) not null,
   situacao char(1) not null,
   aprovacao char(1) default null,
   deleted char(1) default null,
   primary key (idjustificativamudanca)
);

create table anexomudanca (
    idanexomudanca int not null primary key,
    idmudanca int,
    datainicio date,
    datafim date,
    nome varchar(256),
    link varchar(256),
    extensao varchar(10),
    descricao varchar(256)
);

alter table requisicaomudanca add column fechamento text null ;

alter table requisicaomudanca alter column datahoraconclusao set not null;

alter table requisicaomudanca alter column datahoraconclusao set default '1970-01-01 00:00:00';

alter table requisicaomudanca alter column datahoraconclusao drop not null;
alter table requisicaomudanca alter column datahoraconclusao set default null;
 
alter table requisicaomudanca alter column datahorainicio drop not null;
alter table requisicaomudanca alter column datahorainicio set default null;
 
alter table requisicaomudanca alter column datahoratermino drop not null;
alter table requisicaomudanca alter column datahoratermino set default null;
 
alter table requisicaomudanca alter column datahoracaptura drop not null;
alter table requisicaomudanca alter column datahoracaptura set default null;
 
alter table requisicaomudanca alter column datahorareativacao drop not null;
alter table requisicaomudanca alter column datahorareativacao set default null;
 
alter table requisicaomudanca alter column datahorasuspensao drop not null;
alter table requisicaomudanca alter column datahorasuspensao set default null;
 
alter table requisicaomudanca alter column datahorainicioagendada drop not null;
alter table requisicaomudanca alter column datahorainicioagendada set default null;
 
alter table requisicaomudanca alter column datahoraterminoagendada drop not null;
alter table requisicaomudanca alter column datahoraterminoagendada set default null;

-- EXCLUINDO A TRIGGER RESPONSAVEL PELO COMPORTAMENTO DE CONFIGURAR A DATA ATUAL POR PADRAO.
drop trigger add_current_date_to_requisicaomudanca on requisicaomudanca;

-- CARGA DE DADOS
-- bpm_tipofluxo

insert into bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) 
values (6, 'RequisicaoMudancaPadrao', 'Requisição de Mudança Padrão', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca');

insert into bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) 
values (7, 'RequisicaoMudancaEmergencial', 'Requisição Mudança Emergencial', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca');

insert into bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) 
values (8, 'RequisicaodeMudancaNormal', 'Requisição de Mudança Normal', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca');

-- bpm_fluxo

insert into bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) 
values (42, '1.0', 6, 'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual', '', '2013-03-26', null);

insert into bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) 
values (43, '1.0', 7, 'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual', '', '2013-03-26', null);

insert into bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) 
values (44, '1.0', 8, 'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual', '', '2013-03-26', null);


-- bpm_elementofluxo

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (404, 42, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 163, 161, 32, 32, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (405, 42, 'Tarefa', '', 'Executar', 'Executar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao', '', ' #{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 397, 216, 65, 140, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (406, 42, 'Tarefa', '', 'Avaliar', 'Avaliar', 'U', ' pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 719, 260, 65, 140, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (407, 42, 'Script', '', 'Encerra', '', '', '', '', '', '', '', '', '#{execucaoFluxo}.encerra()', '', '', 1014, 246, 65, 140, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (408, 42, 'Finalizacao', null, null, null, null, null, null, null, null, null, null, null, null, null, 1326, 305, 32, 32, null, null, null, null, null, null, null, null);


insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (409, 43, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 90, 139, 32, 32, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (410, 43, 'Tarefa', '', 'Executar', 'Executar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 313, 162, 65, 140, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (411, 43, 'Tarefa', '', 'Avaliar', 'Avaliar', 'U', ' pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao', '', ' #{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 677, 231, 65, 140, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (412, 43, 'Script', null, 'Encerra', null, null, null, null, null, null, null, null, '#{execucaoFluxo}.encerra();', null, null, 1050, 247, 65, 140, null, null, null, null, null, null, null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (413, 43, 'Finalizacao', null, null, null, null, null, null, null, null, null, null, null, null, null, 1300, 287, 32, 32, null, null, null, null, null, null, null, null);

insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (414, 44, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 94, 56, 32, 32, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (415, 44, 'Tarefa', '', 'Aprovar', 'Aprovar', 'U', '/pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Aprovacao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 67, 171, 65, 140, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (416, 44, 'Tarefa', '', 'Planejar', 'Planejar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Planejamento', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 255, 168, 65, 140, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (417, 44, 'Tarefa', '', 'Executar', 'Executar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 482, 168, 65, 140, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (418, 44, 'Tarefa', '', 'Avaliar', 'Avaliar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 783, 169, 65, 140, '', '', null, '', '', '', null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (419, 44, 'Script', null, 'Encerra', null, null, null, null, null, null, null, null, '#{execucaoFluxo}.encerra();', null, null, 1045, 173, 65, 140, null, null, null, null, null, null, null, null);
insert into bpm_elementofluxo ( idelemento , idfluxo , tipoelemento , subtipo , nome , documentacao , tipointeracao , url , visao , grupos , usuarios , acaoentrada , acaosaida , script , textoemail , nomefluxoencadeado , posx , posy , altura , largura , modeloemail , template , intervalo , condicaodisparo , multiplasinstancias , destinatariosemail , contabilizasla , percexecucao ) 
values (420, 44, 'Finalizacao', null, null, null, null, null, null, null, null, null, null, null, null, null, 1135, 425, 32, 32, null, null, null, null, null, null, null, null);


-- bpm_sequenciafluxo

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (404, 405, 42, null, null, '', 1, 3, 296, 212.75, 'N', '');
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (405, 406, 42, null, null, '', 1, 3, 628, 270.5, 'N', '');
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (406, 407, 42, null, null, '', 1, 3, 936.5, 285.5, 'N', null);
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (407, 408, 42, null, null, '', 1, 3, 1240, 299.75, 'N', null);

insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (409, 410, 43, null, null, '', 1, 3, 296, 212.75, 'N', '');
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (410, 411, 43, null, null, '', 1, 3, 628, 270.5, 'N', '');
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (411, 412, 43, null, null, '', 1, 3, 936.5, 285.5, 'N', null);
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (412, 413, 43, null, null, '', 1, 3, 1240, 299.75, 'N', null);


insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (414, 415, 44, null, null, '', 2, 0, 1143, 341.5, 'N', null);
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (415, 416, 44, null, null, '', 1, 3, 994, 213.5, 'N', null);
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (416, 417, 44, null, null, '', 1, 3, 702.5, 201, 'N', null);
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (417, 418, 44, null, null, '', 1, 3, 438.5, 200.5, 'N', null);
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (418, 419, 44, null, null, '', 1, 3, 231, 202, 'N', null);
insert into bpm_sequenciafluxo ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) 
values (419, 420, 44, null, null, '', 2, 0, 123.5, 129.5, 'N', null);

-- modelosemails

insert into modelosemails ( idmodeloemail , titulo , texto , situacao , identificador ) 
values (26, 'Requisição de Mudança em andamento - ${IDREQUISICAOMUDANCA}', 'Senhor(a) ${NOMESOLICITANTE}, <br /><br />Informamos que a requisi&ccedil;&atilde;o de mudan&ccedil;a registrada em ${DATAHORAINICIOSTR} est&aacute; em atendimento, conforme os dados abaixo:<br /><br /><strong>N&uacute;mero:</strong>&nbsp;${IDREQUISICAOMUDANCA}<br /><strong>T&iacute;tulo:</strong>&nbsp;${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br />${TITULO}<br /><strong><br /></strong>${DESCRICAO}<br /><br /><strong>Grupo de atendimento:</strong>&nbsp;${NOMEGRUPOATUAL}<br /><br />Atenciosamente, <br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda', 'A', 'AndamentoReqMud');
insert into modelosemails ( idmodeloemail , titulo , texto , situacao , identificador ) 
values (27, 'Requisição de mudança registrada - ${IDREQUISICAOMUDANCA}', 'Senhor(a) ${NOMESOLICITANTE}, <br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de mudan&ccedil;a foi registrada em ${DATAHORAINICIOSTR}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDREQUISICAOMUDANCA}<br /><strong>Tipo:</strong> ${TIPO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong> <br />${DESCRICAO}<br /><br />Atenciosamente, <br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.', 'A', 'AberturaReqMud');
insert into modelosemails ( idmodeloemail , titulo , texto , situacao , identificador ) 
values (28, 'Requisição de mudança finalizada - ${IDREQUISICAOMUDANCA}', 'Senhor(a) ${NOMESOLICITANTE}, <br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de mudan&ccedil;a foi finalizada em ${DATAHORACONCLUSAO}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDREQUISICAOMUDANCA}<br /><strong>Tipo:</strong> ${TIPO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Status:</strong>${STATUS}<br /><strong>Descri&ccedil;&atilde;o:</strong> <br />${DESCRICAO}<br /><br /><br />Atenciosamente, <br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.', 'A', 'FinalizadaReqMud');
insert into modelosemails ( idmodeloemail , titulo , texto , situacao , identificador ) 
values (29, 'Requisição encaminhada para o COMITÊ CONSULTIVO DE MUDANÇA ', '&nbsp;A Requisi&ccedil;&atilde;o de Mudan&ccedil;a abaixo foi encaminhada para o Comit&ecirc; Consultivo de Mudan&ccedil;a do qual o Senhor(a) faz parte:<div>&nbsp;</div><div><strong>N&uacute;mero</strong>: ${IDREQUISICAOMUDANCA}</div><div><strong>Tipo</strong>: ${TIPO}</div><div><strong>T&iacute;tulo</strong>: ${TITULO}</div><div>&nbsp;</div><div><strong>Descri&ccedil;&atilde;o</strong>:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div>', 'A', 'emailCCM');
insert into modelosemails ( idmodeloemail , titulo , texto , situacao , identificador ) 
values (30, 'Requisição Mudança encaminhada para seu GRUPO DE TRABALHO', '<div>&nbsp; A &nbsp;requisi&ccedil;&atilde;o mudan&ccedil;a abaixo foi encaminhada para seu Grupo de Trabalho:</div><div>&nbsp;</div><div>N&uacute;mero: ${IDREQUISICAOMUDANCA}</div><div>Tipo: ${TIPO}</div><div>Titulo: ${TITULO}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>Atenciosamente, <br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda.</div>', 'A', 'ReqMudancaGrupo');

-- tipomudanca

insert into tipomudanca ( idtipomudanca , idtipofluxo , idmodeloemailcriacao , idmodeloemailfinalizacao , idmodeloemailacoes , idgrupoexecutor , idcalendario , nometipomudanca , datainicio , datafim ) 
values (1, 7, 27, 28, 26, 1, 1, 'Emergencial', '2013-03-14', null);
insert into tipomudanca ( idtipomudanca , idtipofluxo , idmodeloemailcriacao , idmodeloemailfinalizacao , idmodeloemailacoes , idgrupoexecutor , idcalendario , nometipomudanca , datainicio , datafim ) 
values (2, 6, 27, 28, 26, 1, 1, 'Padrão', '2013-04-05', null);
insert into tipomudanca ( idtipomudanca , idtipofluxo , idmodeloemailcriacao , idmodeloemailfinalizacao , idmodeloemailacoes , idgrupoexecutor , idcalendario , nometipomudanca , datainicio , datafim ) 
values (3, 8, 27, 28, 26, 1, 1, 'Normal', '2013-03-15', null);

-- FIM - MODULO DE MUDANCA

-- Início rodrigo.oliveira

alter table atividadesos add column contabilizar char(1);
alter table atividadesos alter column contabilizar set default null;

alter table atividadesos add column idservicocontratocontabil bigint;
alter table atividadesos alter column idservicocontratocontabil set default null;

alter table atividadesservicocontrato add column contabilizar char(1);
alter table atividadesservicocontrato alter column contabilizar set default null;

alter table atividadesservicocontrato add column idservicocontratocontabil bigint;
alter table atividadesservicocontrato alter column idservicocontratocontabil set default null;

create  table vinculaosincidente (
   idos int not null,
   idsolicitacaoservico bigint not null,
   idatividadesos bigint not null 
);

alter table vinculaosincidente add primary key (idos, idsolicitacaoservico);

-- fim rodrigo.oliveira

-- INICIO - MODULO DE PROBLEMA
alter table problema alter column datahoralimite drop not null;
alter table problema alter column datahoralimite set default null;

alter table problema alter column datahorasolicitacao drop not null;
alter table problema alter column datahorasolicitacao set default null;
   
-- FIM - MODULO DE PROBLEMA

-- Postgres - Flávio Júnior - 15/04

DROP TRIGGER add_current_date_to_slarequisitosla ON slarequisitosla;
DROP FUNCTION update_slarequisitosla();

CREATE LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_slarequisitosla() RETURNS TRIGGER 
LANGUAGE plpgsql AS
$$
BEGIN
    NEW.dataultmodificacao := CURRENT_TIMESTAMP; 
    RETURN NEW; 
END; 
$$;

CREATE TRIGGER add_current_date_to_slarequisitosla BEFORE INSERT OR UPDATE ON slarequisitosla
FOR EACH ROW EXECUTE PROCEDURE update_slarequisitosla();

ALTER TABLE slarequisitosla
   ALTER COLUMN dataultmodificacao SET DEFAULT now();
   
-- SCRIPTS GERADOS APARTIR DO DIA 26/04, NÃO SERÃO USADOS ATÉ QUE O VALDOÍLO LIBERE.

alter table categoriagaleriaimagem add column datafim date;

alter table categoriagaleriaimagem add column datainicio date;
   
-- inicio murilo.rodrigues - 29-04-2013
ALTER TABLE processamentobatch ALTER COLUMN descricao TYPE character varying(256);
-- fim murilo.rodrigues

-- SCRIPTS GERADOS A PARTIR DO DIA 30/04
alter table baseconhecimento add column erroconhecido char(1) null;

-- INICIO CLEDSON.JUNIOR 30-04-2013
ALTER TABLE valorservicocontrato ALTER COLUMN valorservico TYPE numeric(18,2);
-- FIM

-- deploy_compras_carlos_16 SCRIPT GERADO NO DIA 02/05
alter table projetos add column idprojetopai int null, 
add constraint fk_idprojetopai                                                   
  foreign key (idprojetopai )                                                      
  references projetos (idprojeto );
  
alter table cotacao alter column datahoracadastro drop not null;
alter table cotacao alter column datahoracadastro type timestamp ;

alter table limitealcada 
rename column limitevaloritem to limiteitemusointerno  ;

alter table limitealcada 
rename column limitevalormensal to limitemensalusointerno  ;

alter table limitealcada 
add column limiteitematendcliente numeric(8,2) DEFAULT NULL, 
add column limitemensalatendcliente numeric(8,2) DEFAULT NULL;
