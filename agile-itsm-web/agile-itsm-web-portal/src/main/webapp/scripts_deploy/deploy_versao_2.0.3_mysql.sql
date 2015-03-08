-- Autor: Thiago Monteiro
-- Data: Quinta-feira, 28 de março de 2013 13:02
alter table atividadesservicocontrato modify periodo char(1);
alter table atividadesservicocontrato modify complexidade char(1);







set sql_safe_updates = 0;

alter table bpm_itemtrabalhofluxo change column iditemtrabalho iditemtrabalho bigint(20) not null,
change column idinstancia idinstancia bigint(20) not null, change column idelemento idelemento bigint(20) not null;

-- coluna para indicar contabilizacao do sla no elemento fluxo
alter table bpm_elementofluxo add contabilizasla char(1);
update bpm_elementofluxo set contabilizasla = 'S';

-- coluna para indicar percentual de execucao do sla no elemento fluxo
alter table bpm_elementofluxo add percexecucao double;

-- coluna para indicar o grupo aprovador do servico contrato
alter table servicocontrato add idgrupoaprovador int;

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
	'Aprovaçãoo da Solicitação de Serviço',
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
  idaprovacaosolicitacaoservico int(11) not null,
  idsolicitacaoservico bigint(20) not null,
  idtarefa bigint(20) default null,
  idresponsavel int(11) not null,
  datahora timestamp null default null,
  idjustificativa int(11) default null,
  complementojustificativa text,
  observacoes text,
  aprovacao char(1) not null,
  key fk_aprovacao_tarefa_idx (idtarefa),
  key fk_aprovacao_solicitacao_idx (idsolicitacaoservico),
  key fk_aprovacao_justificativa_idx (idjustificativa),
  key fk_aprovacao_responsavel_idx (idresponsavel),
  constraint fk_aprovacao_justificativa foreign key (idjustificativa) references justificativasolicitacao (idjustificativa) on delete no action on update no action,
  constraint fk_aprovacao_responsavel foreign key (idresponsavel) references empregados (idempregado) on delete no action on update no action,
  constraint fk_aprovacao_solicitacao foreign key (idsolicitacaoservico) references solicitacaoservico (idsolicitacaoservico) on delete no action on update no action,
  constraint fk_aprovacao_tarefa foreign key (idtarefa) references bpm_itemtrabalhofluxo (iditemtrabalho) on delete no action on update no action
) engine=InnoDB default charset=utf8;

-- alteracoes em solicitacao servico para controle de suspensao / reativacao SLA
alter table solicitacaoservico add datahorainiciosla timestamp null;
alter table solicitacaoservico add idultimaaprovacao int;
alter table solicitacaoservico add situacaosla char(1);
alter table solicitacaoservico add datahorasuspensaosla timestamp null;
alter table solicitacaoservico add datahorareativacaosla timestamp null;
alter table solicitacaoservico change column datahoralimite datahoralimite timestamp null;

update solicitacaoservico set datahorainiciosla = datahorainicio;
update solicitacaoservico set situacaosla = 'A';
update solicitacaoservico set situacaosla = 'S' where situacao = 'Suspensa';
update solicitacaoservico set datahorasuspensaosla = datahorasuspensao;
update solicitacaoservico set datahorareativacaosla = datahorareativacao;

alter table aprovacaosolicitacaoservico add primary key (idaprovacaosolicitacaoservico);

alter table solicitacaoservico add constraint fk_solicitacao_aprovacao foreign key (idultimaaprovacao ) references aprovacaosolicitacaoservico (idaprovacaosolicitacaoservico ),
add index fk_solicitacao_aprovacao_idx (idultimaaprovacao asc);

-- inclusao de campo no objeto negocio ServicoContrato
insert into camposobjetonegocio (idcamposobjetonegocio,idobjetonegocio,nome,nomedb,
pk,sequence,unico,tipodb,obrigatorio,situacao) values
(10000,175,'IDGRUPOAPROVADOR','IDGRUPOAPROVADOR','N','N','N','INT','N','A');

-- inclusao de campo no objeto negocio JustificativaSolicitacao
insert into camposobjetonegocio (idcamposobjetonegocio,idobjetonegocio,nome,nomedb,pk,sequence,unico,tipodb,obrigatorio,situacao,precisiondb) values (10001,364,'APROVACAO','APROVACAO','N','N','N','CHAR','S','A', 0);

-- INICIO - SCRIPTS CARLOS ALBERTO SANTOS

-- Necessario para implantacao na ANAC (autor: Carlos Alberto Santos).
alter table solicitacaoservico add prazocapturahh int;
alter table solicitacaoservico add prazocapturamm int;

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

set sql_safe_updates = 1;