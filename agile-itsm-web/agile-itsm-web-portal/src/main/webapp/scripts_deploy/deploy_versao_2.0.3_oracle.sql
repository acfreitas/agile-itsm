-- SGBD: Oracle
-- Autor: thiago.monteiro
-- Data: 29/04/2013

set define off;

alter table atividadesservicocontrato modify periodo char(1);

alter table atividadesservicocontrato modify complexidade char(1);

alter table bpm_itemtrabalhofluxo modify iditemtrabalho number(19,0) not null;

alter table bpm_itemtrabalhofluxo modify idinstancia number(19,0) not null;

alter table bpm_itemtrabalhofluxo modify idelemento number(19,0) not null;

-- coluna para indicar contabilizacao do sla no elemento fluxo
alter table bpm_elementofluxo add contabilizasla char(1);

update bpm_elementofluxo set contabilizasla = 'S';

-- coluna para indicar percentual de execucao do sla no elemento fluxo
alter table bpm_elementofluxo add percexecucao float(24);

-- coluna para indicar o grupo aprovador do servico contrato
alter table servicocontrato add idgrupoaprovador number(10,0);

-- coluna para indicar se a justificativa pode ser utilizada na aprovacao da solicitacao
alter table justificativasolicitacao add aprovacao char(1);

update justificativasolicitacao set aprovacao = 'N';

-- colunas para configuracao do template de solicitacao servico
alter table templatesolicitacaoservico add habilitaitemconfiguracao char(1);

update templatesolicitacaoservico set habilitaitemconfiguracao = 'N';

alter table templatesolicitacaoservico add habilitasolicitacaorelacionada char(1);

update templatesolicitacaoservico set habilitasolicitacaorelacionada = 'N';

alter table templatesolicitacaoservico add habilitagravarecontinuar char(1);

update templatesolicitacaoservico set habilitagravarecontinuar = 'S';

-- inclusao de novo template de solicitacao servico
insert into 
templatesolicitacaoservico (
	idtemplate,
	identificacao,
	nometemplate,
	nomeclassedto,
	nomeclasseaction,
	nomeclasseservico,
	urlrecuperacao,
	scriptaposrecuperacao,
	habilitadirecionamento,
	habilitasituacao,
	habilitasolucao,
	alturadiv,
	habilitaurgenciaimpacto,
	habilitanotificacaoemail,
	habilitaproblema,
	habilitamudanca,
	habilitaitemconfiguracao,
	habilitasolicitacaorelacionada,
	habilitagravarecontinuar
) values (
	10,
	'AprovacaoSolicitacaoServico',
	'Aprova��oo da Solicita��o de Servi�o',
	'br.com.centralit.citcorpore.bean.AprovacaoSolicitacaoServicoDTO',
	'br.com.centralit.citcorpore.ajaxForms.AprovacaoSolicitacaoServico',
	'br.com.centralit.citcorpore.negocio.AprovacaoSolicitacaoServicoServiceEjb',
	'/pages/aprovacaoSolicitacaoServico/aprovacaoSolicitacaoServico.load', 
	null,
	'N',
	'N',
	'N',
	250,
	'N',
	'N',
	'N',
	'N',
	'N',
	'N',
	'N'
);

-- nova tabela para registrar aprovacoes de solicitacao servico
create table aprovacaosolicitacaoservico (
    idaprovacaosolicitacaoservico number(10,0) not null,
    idsolicitacaoservico number(19,0) not null,
    idtarefa number(19,0) default null,
    idresponsavel number(10,0) not null,
    datahora date default null,
    idjustificativa number(10,0) default null,
    complementojustificativa clob,
    observacoes clob,
    aprovacao char(1) not null
);

alter table aprovacaosolicitacaoservico add constraint fk_aprovacao_justificativa foreign key (idjustificativa) references justificativasolicitacao (idjustificativa);

alter table aprovacaosolicitacaoservico add constraint fk_aprovacao_responsavel foreign key (idresponsavel) references empregados (idempregado);

alter table aprovacaosolicitacaoservico add constraint fk_aprovacao_solicitacao foreign key (idsolicitacaoservico) references solicitacaoservico (idsolicitacaoservico);

alter table aprovacaosolicitacaoservico add constraint fk_aprovacao_tarefa foreign key (idtarefa) references bpm_itemtrabalhofluxo (iditemtrabalho);

-- Criando indices
create index fk_aprovacao_tarefa_idx on aprovacaosolicitacaoservico (idtarefa);
create index fk_aprovacao_solicitacao_idx on aprovacaosolicitacaoservico (idsolicitacaoservico);
create index fk_aprovacao_justificativa_idx on aprovacaosolicitacaoservico (idjustificativa);
create index fk_aprovacao_responsavel_idx on aprovacaosolicitacaoservico (idresponsavel);

-- alteracoes em solicitacao servico para controle de suspensao / reativacao SLA
alter table solicitacaoservico add (datahorainiciosla date null);
alter table solicitacaoservico add (idultimaaprovacao number(10,0) );
alter table solicitacaoservico add (situacaosla char(1) );
alter table solicitacaoservico add (datahorasuspensaosla date null);
alter table solicitacaoservico add (datahorareativacaosla date null);

-- alter table solicitacaoservico change column datahoralimite datahoralimite timestamp null;
alter table solicitacaoservico modify datahoralimite date null;

update solicitacaoservico set datahorainiciosla = datahorainicio;
update solicitacaoservico set situacaosla = 'A';
update solicitacaoservico set situacaosla = 'S' where situacao = 'Suspensa';
update solicitacaoservico set datahorasuspensaosla = datahorasuspensao;
update solicitacaoservico set datahorareativacaosla = datahorareativacao;

alter table aprovacaosolicitacaoservico add primary key (idaprovacaosolicitacaoservico);

alter table solicitacaoservico add constraint fk_solicitacao_aprovacao foreign key (idultimaaprovacao ) references aprovacaosolicitacaoservico (idaprovacaosolicitacaoservico);

create index fk_solicitacao_aprovacao_idx on solicitacaoservico (idultimaaprovacao);

-- inclusao de campo no objeto negocio ServicoContrato
insert into camposobjetonegocio (idcamposobjetonegocio, idobjetonegocio, nome, nomedb, pk, sequence, unico, tipodb, obrigatorio, situacao) values (10000, 175, 'IDGRUPOAPROVADOR', 'IDGRUPOAPROVADOR', 'N', 'N', 'N', 'INT', 'N', 'A');

-- inclusao de campo no objeto negocio JustificativaSolicitacao
insert into camposobjetonegocio (idcamposobjetonegocio, idobjetonegocio, nome, nomedb, pk, sequence, unico, tipodb, obrigatorio, situacao, precisiondb) values (10001, 364, 'APROVACAO', 'APROVACAO', 'N', 'N', 'N', 'CHAR', 'S', 'A', 0);

-- INICIO - SCRIPTS CARLOS ALBERTO SANTOS

-- Necessario para implantacao na ANAC (autor: Carlos Alberto Santos).
alter table solicitacaoservico add prazocapturahh number(10,0);

alter table solicitacaoservico add prazocapturamm number(10,0);

-- Script complementar enviado pelo Sr. Carlos Alberto Santos
-- Data e hora: 4 de abril de 2013 12:48
update solicitacaoservico sol set prazocapturahh = (
    select t.tempohh
    from tempoacordonivelservico t
    where t.idacordonivelservico = sol.idacordonivelservico 
    and t.idprioridade = sol.idprioridade
    and t.idfase = 1
) where prazocapturahh is null;
 
update solicitacaoservico sol set prazocapturamm = (
    select t.tempomm
    from tempoacordonivelservico t
    where t.idacordonivelservico = sol.idacordonivelservico
    and t.idprioridade = sol.idprioridade
    and t.idfase = 1
) where prazocapturamm is null;

-- FIM - SCRIPTS CARLOS ALBERTO SANTOS

set define on;