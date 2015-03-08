-- Conversão de mysql para sqlserver: Thiago Matias Barbosa
-- Data: terça-feira, 28 de Maio de 2013
set sql_safe_updates = 0;

alter table atividadesservicocontrato modify periodo char(1);
alter table atividadesservicocontrato modify complexidade char(1);

--no SQLSERVER o tipo bigint não permite especificação de tamanho pois já tem tamanho padrão de 8bytes --

/*alter table bpm_itemtrabalhofluxo change column iditemtrabalho iditemtrabalho bigint(20) not null,
change column idinstancia idinstancia bigint(20) not null, change column idelemento idelemento bigint(20) not null;*/

-- coluna para indicar contabilizacao do sla no elemento fluxo
alter table bpm_elementofluxo add contabilizasla char(1);
update bpm_elementofluxo set contabilizasla = 'S';

-- coluna para indicar percentual de execucao do sla no elemento fluxo
alter table bpm_elementofluxo add percexecucao float;

-- coluna para indicar o grupo aprovador do servico contrato
alter table servicocontrato add idgrupoaprovador integer;

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
	'Aprovação da Solicitação de Serviço',
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
  idaprovacaosolicitacaoservico integer not null,
  idsolicitacaoservico bigint not null,
  idtarefa bigint default null,
  idresponsavel integer not null,
  datahora datetime null default null,
  idjustificativa integer default null,
  complementojustificativa text,
  observacoes text,
  aprovacao char(1) not null
  
  );
  -- Criando indexs para aprovacaosolicitacaoservico
  create index fk_aprovacao_tarefa_idx
  on aprovacaosolicitacaoservico (idtarefa);
  
  create index fk_aprovacao_solicitacao_idx
  on aprovacaosolicitacaoservico (idsolicitacaoservico);
  
  create index fk_aprovacao_justificativa_idx
  on aprovacaosolicitacaoservico (idjustificativa);
  
  create index fk_aprovacao_responsavel_idx
  on aprovacaosolicitacaoservico (idresponsavel);
  
  --Criando Foreign key para aprovacaosolicitacaoservico

  alter table aprovacaosolicitacaoservico add constraint fk_aprovacao_justificativa foreign key (idjustificativa) references justificativasolicitacao (idjustificativa) on delete no action on update no action;
  alter table aprovacaosolicitacaoservico add constraint fk_aprovacao_responsavel foreign key (idresponsavel) references empregados (idempregado) on delete no action on update no action;
  alter table aprovacaosolicitacaoservico add constraint fk_aprovacao_solicitacao foreign key (idsolicitacaoservico) references solicitacaoservico (idsolicitacaoservico) on delete no action on update no action;
  alter table aprovacaosolicitacaoservico add constraint fk_aprovacao_tarefa foreign key (idtarefa) references bpm_itemtrabalhofluxo (iditemtrabalho) on delete no action on update no action;

  -- alteracoes em solicitacao servico para controle de suspensao / reativacao SLA
  alter table solicitacaoservico add datahorainiciosla datetime null;
  alter table solicitacaoservico add idultimaaprovacao integer;
  alter table solicitacaoservico add situacaosla char(1);
  alter table solicitacaoservico add datahorasuspensaosla datetime null;
  alter table solicitacaoservico add datahorareativacaosla datetime null;
  alter table solicitacaoservico alter column datahoralimite datetime null;

  update solicitacaoservico set datahorainiciosla = datahorainicio;
  update solicitacaoservico set situacaosla = 'A';
  update solicitacaoservico set situacaosla = 'S' where situacao = 'Suspensa';
  update solicitacaoservico set datahorasuspensaosla = datahorasuspensao;
  update solicitacaoservico set datahorareativacaosla = datahorareativacao;

  alter table aprovacaosolicitacaoservico add primary key (idaprovacaosolicitacaoservico);

  alter table solicitacaoservico add constraint fk_solicitacao_aprovacao foreign key (idultimaaprovacao) references aprovacaosolicitacaoservico (idaprovacaosolicitacaoservico);
  create index fk_solicitacao_aprovacao_idx
  on  solicitacaoservico (idultimaaprovacao);-- por padrão a ordenação já é ASC
  
  -- inclusao de campo no objeto negocio ServicoContrato
  insert into camposobjetonegocio (idcamposobjetonegocio,idobjetonegocio,nome,nomedb,
  pk,sequence,unico,tipodb,obrigatorio,situacao) values
  (10000,175,'IDGRUPOAPROVADOR','IDGRUPOAPROVADOR','N','N','N','INTEGER','N','A');

  -- inclusao de campo no objeto negocio JustificativaSolicitacao
  insert into camposobjetonegocio (idcamposobjetonegocio, idobjetonegocio, nome, nomedb, pk, sequence, unico, tipodb, obrigatorio, situacao, precisiondb) values (10001,364,'APROVACAO','APROVACAO','N','N','N','CHAR','S','A', 0);

  -- Necessario para implantacao na ANAC (autor: Carlos Alberto Santos).
  alter table solicitacaoservico add prazocapturahh integer;
  alter table solicitacaoservico add prazocapturamm integer;

  -- Script complementar enviado pelo Sr. Carlos Alberto Santos
  -- Data e hora: 4 de abril de 2013 12:48
  update solicitacaoservico set prazocapturahh = (
    select tempoacordonivelservico.tempohh
    from tempoacordonivelservico
    where tempoacordonivelservico.idacordonivelservico = solicitacaoservico.idacordonivelservico 
    and tempoacordonivelservico.idprioridade = solicitacaoservico.idprioridade
    and tempoacordonivelservico.idfase = 1
  ) where prazocapturahh is null;
 
  update solicitacaoservico set prazocapturamm = (
    select tempoacordonivelservico.tempomm
    from tempoacordonivelservico
    where tempoacordonivelservico.idacordonivelservico = solicitacaoservico.idacordonivelservico
    and tempoacordonivelservico.idprioridade = solicitacaoservico.idprioridade
    and tempoacordonivelservico.idfase = 1
  ) where prazocapturamm is null;

  -- FIM - SCRIPTS CARLOS ALBERTO SANTOS

set sql_safe_updates = 1;


