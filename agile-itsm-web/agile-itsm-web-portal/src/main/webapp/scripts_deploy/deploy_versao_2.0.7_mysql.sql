set sql_safe_updates = 0;

-- MySQL

alter table problema engine = InnoDB;

-- autor: geber
-- data: 12/04/2013
create table ocorrenciaproblema (
  idocorrencia int(11) not null,
  iditemtrabalho bigint(20) default null,
  idjustificativa int(11) default null,
  idproblema int(11) default null,
  dataregistro date default null,
  horaregistro varchar(5) default null,
  registradopor varchar(100) default null,
  descricao varchar(200) default null,
  datainicio date default null,
  datafim date default null,
  complementojustificativa longtext,
  dadosproblema longtext,
  informacoescontato longtext,
  categoria varchar(20) default null,
  origem char(1) default null,
  tempogasto smallint(6) default null,
  ocorrencia longtext,
  idcategoriaocorrencia int(11) default null,
  idorigemocorrencia int(11) default null
) engine= InnoDB default charset=utf8;

alter table ocorrenciaproblema add constraint pk_ocorrenciaproblema primary key (idocorrencia);

alter table ocorrenciaproblema add constraint fk_ocorrenciaproblema_problema foreign key (idproblema) references problema (idproblema);

create index fk_idx_idproblema on problema (idproblema);

-- autor: thiago.monteiro
-- data: 12/04/2013
alter table problema add column acoescorretas longtext;
alter table problema add column acoesincorretas longtext;
alter table problema add column melhoriasfuturas longtext;
alter table problema add column recorrenciaproblema longtext;
alter table problema add column responsabilidadeterceiros longtext;

-- autor: thiago.monteiro
-- data: 10/04/2013
alter table atividadeperiodica add column idproblema int(11) null;
alter table atividadeperiodica add constraint fk_atividadeperiodica_problema foreign key (idproblema) references problema (idproblema);

-- autor: thiago.monteiro
-- data: 11/04/2013
create table contatoproblema (
  idcontatoproblema int(10) not null,
  nomecontato varchar(100) default null,
  telefonecontato varchar(100) default null,
  emailcontato varchar(200) default null,
  observacao text,
  idlocalidade int(11) default null,
  ramal varchar(5) default null
) engine=InnoDB default charset=utf8;

alter table contatoproblema add constraint pk_contatoproblema primary key (idcontatoproblema);

-- autor: thiago.monteiro
-- data: 10/04/2013
create table justificativaproblema (
    idjustificativaproblema int(11) not null,
    descricaoproblema varchar(100) not null,
    suspensao char(1) not null,
    situacao char(1) not null,
    aprovacao char(1) default null,
    deleted char(1) default null
) engine=InnoDB default charset=utf8;

alter table justificativaproblema add constraint pk_justificativaproblema primary key (idjustificativaproblema);

-- autor: thiago.monteiro
-- data: 09/04/2013

create table execucaoproblema (
    idexecucao bigint(20) not null,
    idproblema bigint(20) default null,
    idfase bigint(20) default null,
    idinstanciafluxo bigint(20) default null,
    idfluxo bigint(20) default null,
    prazohh smallint(6) default null,
    prazomm smallint(6) default null,
    seqreabertura int(11) default null
) engine=InnoDB default charset=latin1;

-- Tabela execucaoproblema - chave primaria (pk)
alter table execucaoproblema add primary key (idexecucao);

-- Tabela execucaoproblema - indices (idx)
create index fk_execprob_problema on execucaoproblema (idproblema);
create index fk_execprob_fase on execucaoproblema (idfase);
create index fk_execprob_fluxo on execucaoproblema (idfluxo);
create index fk_execprob_instanciafluxo on execucaoproblema (idinstanciafluxo);

-- Tabela problema - acrescimo de novas colunas
alter table problema add column datahoracaptura timestamp null;
alter table problema add column datahorainiciosla timestamp null;
alter table problema add column datahorareativacao timestamp null;
alter table problema add column datahorareativacaosla timestamp null;
alter table problema add column datahorasuspensao timestamp null;
alter table problema add column datahorasuspensaosla timestamp null;
alter table problema add column enviaemailacoes char(1) null;
alter table problema add column enviaemailcriacao char(1) null;
alter table problema add column enviaemailfinalizacao char(1) null;
alter table problema add column idcalendario int(11) null;
alter table problema add column idfaseatual bigint(20) null;
alter table problema add column idsolicitacaoservico bigint(20) null;
alter table problema add column idsolicitante int(11) null;
alter table problema add column resposta text null;
alter table problema add column seqreabertura int(11) null;
alter table problema add column situacaosla char(1) null;
alter table problema add column tempoatendimentohh smallint(6) null;
alter table problema add column tempoatendimentomm smallint(6) null;
alter table problema add column tempoatrasohh smallint(6) null;
alter table problema add column tempoatrasomm smallint(6) null;
alter table problema add column tempocapturahh smallint(6) null;
alter table problema add column tempocapturamm smallint(6) null;
alter table problema add column tempodecorridohh smallint(6) null;
alter table problema add column tempodecorridomm smallint(6) null;

-- Tabela problema - chave(s) estrangeira(s) (fk)
alter table problema add constraint fk_problema_calendario foreign key (idcalendario) references calendario (idcalendario);
alter table problema add constraint fk_problema_faseservico foreign key (idfaseatual) references faseservico (idfase);
alter table problema add constraint fk_problema_solicitacaoservico foreign key (idsolicitacaoservico) references solicitacaoservico (idsolicitacaoservico);
alter table problema add constraint fk_problema_solicitante foreign key (idsolicitante) references empregados (idempregado);

-- autor: thiago.monteiro
-- data: 14/04/2013
alter table problema add column idorigematendimento bigint null default null;
alter table problema add column diagnostico varchar(4000) null default null;
alter table problema add column fechamento varchar(1000) null default null;

alter table problema add constraint fk_problema_origematendimento foreign key (idorigematendimento) references origematendimento (idorigem);

-- autor: thiago.monteiro
-- data: 11/04/2013
alter table problema add column idcontatoproblema int(10) null;

alter table contatoproblema engine = InnoDB;

alter table problema add constraint fk_problema_contatoproblema foreign key (idcontatoproblema) references contatoproblema (idcontatoproblema);

-- autor: geber
-- data: 15/04/2013
alter table problema add acompanhamento char(1) default 'N';

-- autor: thiago.monteiro
-- data: 16/04/2013
alter table problema add column grave char(1) default 'N';
alter table problema add column precisamudanca char(1) default 'N';

-- autor: riubbe
-- data: 16/04/2013
insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (34,'Problema Registrado  -  ${IDPROBLEMA}','Senhor(a) ${SOLICITANTE},<br /><br />Informamos que o Problema criado, foi registrado em ${DATAHORACAPTURA}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDPROBLEMA}<br /><strong>Tipo:</strong> ${PROATIVOREATIVO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong> ${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','registroProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (35,'Problema em Andamento  -  ${IDPROBLEMA}','Senhor(a) ${SOLICITANTE},<br /><br />Informamos que o Problema criado em ${DATAHORACAPTURA} esta em atendimento, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDPROBLEMA}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br />${TITULO}<br /><strong><br /></strong>${DESCRICAO}<br /><br /><strong>Grupo de atendimento:</strong>&nbsp;${NOMEGRUPOATUAL}<br /><br />Atenciosamente,<br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda','A','andamentoProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (36,'Problema Finalizado -  ${IDPROBLEMA}','Senhor(a) ${SOLICITANTE},<br /><br />Informamos que o Problema ${IDPROBLEMA},&nbsp; foi finalizado em ${DATAHORAFIM}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDPROBLEMA}<br /><strong>Tipo:</strong> ${PROATIVOREATIVO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><br /><strong>Status:</strong>${STATUS}<br /><strong>Descri&ccedil;&atilde;o:</strong> ${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','finalizadoProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (37,'Problema encaminhado para seu GRUPO DE TRABALHO','&nbsp;A solicita&ccedil;&atilde;o abaixo foi encaminhada para seu Grupo de Trabalho:<div>&nbsp;</div><div>N&uacute;mero: ${IDPROBLEMA}</div><div>Tipo: ${PROATIVOREATIVO}</div><div><strong>T&iacute;tulo:</strong> ${TITULO}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div>','A','grupoProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (38,'Requisições Problemas com prazo expirados','<br />informamos que existem&nbsp;requisi&ccedil;&otilde;es problemas com prazo para contornar ou solucionar expirados.<br /><br /><br />atenciosamente.<br />central it&nbsp;tecnologia da informa&ccedil;&atilde;o ltda.<br /><br />','a','problemaprazocontexp');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (39, 'Requisição Mudança Reunião Marcada', '&nbsp;Senhor(a) ${NOMECONTATO},&nbsp;<br /><br />Informamos que haver&aacute; uma reuni&atilde;o relacionada a Requisi&ccedil;&atilde;o de mudan&ccedil;a de n&uacute;mero&nbsp;${IDREQUISICAOMUDANCA}.<br /><br /><strong><span style="font-size:12.0pt;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;mso-ansi-language:PT-BR;mso-fareast-language:PT-BR;mso-bidi-language:AR-SA"><font face="Times New Roman, serif">Local</font>:</span></strong><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,&quot;serif&quot;;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;mso-ansi-language:PT-BR;mso-fareast-language:PT-BR;mso-bidi-language:AR-SA">&nbsp;${LOCALREUNIAO}<br /> <strong>Data:</strong>&nbsp;${DATAINICIO}<br /> <strong>Hor&aacute;rio:</strong>&nbsp;${HORAINICIO}<br /> <strong>Dura&ccedil;&atilde;o Estimada:</strong>&nbsp;${DURACAOESTIMADA} minutos<br /> <br /> <strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br /></span><span serif; font-size: 16px;">${DESCRICAO}</span><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,&quot;serif&quot;;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;mso-ansi-language:PT-BR;mso-fareast-language:PT-BR;mso-bidi-language:AR-SA"><br /> <!--[if !supportLineBreakNewLine]--><br /> <!--[endif]--></span><br />Atenciosamente,&nbsp;<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.', 'A', 'reuniao');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (51, 'Registro de Liberação  -  ${IDREQUISICAOLIBERACAO}' , 'Senhor(a) ${NOMESOLICITANTE},<br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de Libera&ccedil;&atilde;o foi registrada em ${DATAHORAINICIOSTR}, conforme os dados abaixo:<br /><br />N&uacute;mero: ${IDREQUISICAOLIBERACAO}<br />Tipo: ${TIPO}<br />T&iacute;tulo: ${TITULO}<br /><br />Descri&ccedil;&atilde;o: ${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br /><br /><br />' , 'A' , 'regestroLib');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (52, 'Requisição Liberação finalizada  -  ${IDREQUISICAOLIBERACAO}' , 'Senhor(a) ${NOMESOLICITANTE},<br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de libera&ccedil;&atilde;o foi finalizada em ${DATAHORACONCLUSAO}, conforme os dados abaixo:<br /><br />N&uacute;mero: ${IDREQUISICAOLIBERACAO}<br />Tipo: ${TIPO}<br />T&iacute;tulo: ${TITULO}<br /><br />Status:${STATUS}<br />Descri&ccedil;&atilde;o:<br />${DESCRICAO}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />' , 'A' , 'liberacaoFinalizada');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (53, 'Requisição  Liberação em andamento- ${IDREQUISICAOLIBERACAO}' , 'Senhor(a) ${NOMESOLICITANTE},<br /><br />Informamos que a requisi&ccedil;&atilde;o de libera&ccedil;&atilde;o registrada em ${DATAHORAINICIOSTR} est&aacute; em atendimento, conforme os dados abaixo:<br /><br />N&uacute;mero: ${IDREQUISICAOLIBERACAO}<br />T&iacute;tulo: ${TITULO}<br /><br />Descri&ccedil;&atilde;o: <br />${TITULO}<br /><br />${DESCRICAO}<br /><br />Grupo de atendimento: ${NOMEGRUPOATUAL}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda<br />' , 'A' , 'liberacaoEmAndamento');

-- autor: thiago.monteiro
-- data: 19/04/2013
insert into bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) values (50, 'ProblemaPadrao', 'Problema Padrão', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoProblema');

insert into bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) values (100, '1.0', 50, 'problema;problema.status;problema.nomeGrupoAtual', '', '2013-04-19', null);

alter table problema change datahoralimite  datahoralimite timestamp null;

-- autor: thiago.monteiro
-- data: 22/04/2013
insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1000, 100, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 66, 216, 32, 32, '', '', null, '', '', '', 'S', null);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1001, 100, 'Tarefa', '', 'Registrada', 'Registrada', 'U', 'pages/problema/problema.load?alterarSituacao=N&faseAtual=Registrada&fase=EmInvestigacao', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 178, 200, 65, 140, '', '', null, '', 'N', '', 'S', null);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1002, 100, 'Tarefa', '', 'Investigação e diagnóstico', 'Investigação e diagnóstico', 'U', ' pages/problema/problema.load?alterarSituacao=N&faseAtual=EmInvestigacao&fase=RegistroErroConhecido', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 410, 200, 65, 140, '', '', null, '', 'N', '', 'S', null);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1003, 100, 'Tarefa', '', 'Registro de Erro Conhecido', 'Registro de Erro Conhecido', 'U', ' pages/problema/problema.load?alterarSituacao=N&faseAtual=RegistroErroConhecido&fase=Resolucao', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 663, 203, 65, 140, '', '', null, '', 'N', '', 'S', null);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1004, 100, 'Tarefa', '', 'Resolução', 'Resolução', 'U', ' pages/problema/problema.load?alterarSituacao=N&faseAtual=Resolucao', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 895, 213, 65, 140, '', '', null, '', 'N', '', 'S', null);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1005, 100, 'Script', '', 'Encerrar', '', '', '', '', '', '', '', '', '#{execucaoFluxo}.encerra();', '', '', 1088, 190, 65, 140, '', '', null, '', '', '', 'S', null);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1006, 100, 'Finalizacao', '', '', '', '', '', '', '', '', '', '', '', '', '', 1291, 215, 32, 32, '', '', null, '', '', '', 'S', null);

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1000,1001,100,null,null,'',1,3,138,232.25,'n','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1001,1002,100,null,null,'',1,3,364,232.5,'n','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1002,1003,100,null,null,'',1,3,606.5,234,'n','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1003,1004,100,null,null,'',1,3,849,240.5,'n','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1004,1005,100,null,null,'',1,3,1061.5,234,'n','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1005,1006,100,null,null,'',1,3,1259.5,226.75,'n','');
-- autor: riubbe.oliveira
-- data: 24/04/2013
create table solucaocontorno (
	idsolucaocontorno int(11) not null,
	titulo varchar(120) not null,
	descricao text not null,
	datahoracriacao timestamp null,
	primary key (idsolucaocontorno)
	);
	
	create table solucaodefinitiva (
	idsolucaodefinitiva int(11) not null,
	titulo varchar(120) not null,
	descricao text not null,
	datahoracriacao timestamp null,
	primary key (idsolucaodefinitiva)
	);
	
-- autor: geber.costa
-- data: 24/04/2013
	alter table categoriaproblema 
	add idtipofluxo int(11) references bpm_tipofluxo(idtipofluxo),
	add idgrupoexecutor int(11) references grupo(idgrupo),
	add datainicio date,
	add datafim date;

	alter table categoriaproblema 

	add constraint fk_tipofluxo_reference_bpm_tipofluxo 
	foreign key (idtipofluxo) references bpm_tipofluxo(idtipofluxo),

	add constraint fk_grupoexecutor_reference_grupo 
	foreign key (idgrupoexecutor) references grupo(idgrupo);
	
	alter table categoriaproblema add nomecategoriaproblema varchar(100);
	
	alter table categoriaproblema change idcategoriaproblemapai idcategoriaproblemapai int(11) null;
	
-- autor: riubbe.oliveira
-- data: 25/04/2013
alter table solucaocontorno add column idproblema int(11) null, add constraint fk_solucaocontorno_problema foreign key (idproblema) references problema (idproblema);
alter table solucaodefinitiva add column idproblema int(11) null, add constraint fk_solucaodefinitiva_problema foreign key(idproblema) references problema(idproblema);

-- autor:thays.araujo
-- data: 17/04/2013
alter table baseconhecimento add column erroconhecido char(1) null ;

-- autor:thays.araujo
-- data: 19/04/2013

alter table problema add column faseatual varchar(100) null;
alter table problema change column  msgerroassociada   msgerroassociada  longtext null default null  ;
alter table problema change column  solucaodefinitiva   solucaodefinitiva  longtext null default null  ;
alter table problema change column  diagnostico   diagnostico  longtext null default null  ;
alter table problema change column  acoescorretas   acoescorretas  longtext null default null  ;
alter table problema change column  acoesincorretas   acoesincorretas  longtext null default null  ;
alter table problema change column  melhoriasfuturas   melhoriasfuturas  longtext null default null  ;
alter table problema change column  recorrenciaproblema   recorrenciaproblema  longtext null default null  ;
alter table problema change column  responsabilidadeterceiros   responsabilidadeterceiros  longtext null default null  ;


-- autor:thays.araujo
-- data: 23/04/2013

alter table problema add column idcausa int(11) null;

alter table bpm_fluxo   add constraint fk_bpm_fluxo_bpm_tipofluxo  foreign key (idtipofluxo)  references bpm_tipofluxo (idtipofluxo );
alter table bpm_elementofluxo add constraint fk_bpm_elementofluxo_bpm_fluxo foreign key (idfluxo)  references bpm_fluxo (idfluxo );
alter table bpm_sequenciafluxo add constraint fk_bpm_sequenciafluxo_bpm_fluxo foreign key (idfluxo) references bpm_fluxo (idfluxo );


alter table  problema  change column  fechamento   fechamento  longtext null default null  ;

alter table  problemaitemconfiguracao  change column  descricaoproblema   descricaoproblema  longtext null default null  ;

-- autor:thays.araujo
-- data: 06/05/2013

alter table   problema  change column  causaraiz   causaraiz  longtext null default null  ;
alter table   problema change column  solucaocontorno   solucaocontorno  longtext null default null  ;

-- autor:thays.araujo
-- data: 09/05/2013

alter table categoriaproblema add column idtemplate int(11) null;

alter table categoriaproblema   
add constraint fk_categoriaproblema_templatesolicitacaoservico  
foreign key (idtemplate )  
references templatesolicitacaoservico (idtemplate ) ;

create  table validacaorequisicaoproblema 
(  idvalidacaorequisicaoproblema int not null ,     observacaoproblema longtext null ,  
   datainicio date not null ,  
   datafim date null ,  idproblema int null ,  
primary key (idvalidacaorequisicaoproblema) ,  
constraint fk_validacaorequisicaoproblema_problema    foreign key (idproblema)    
references problema (idproblema ))
engine = innodb;

-- autor: riubbe.oliveira
-- data: 15/05/2013
alter table problema add column confirmasolucaocontorno varchar(1);
alter table categoriaproblema change column nomecategoria nomecategoria int(11) null  ;

-- autor: thays.araujo
-- data: 15/05/2013
alter table problema add column idunidade int(11) null;
alter table problema add column enviaemailprazosolucionarexpirou char(1);

-- autor:thays.araujo
-- data: 16/05/2013
alter table problema add column fase varchar(100) null;

-- autor: Carlos Santos
-- Data: 16/05/2013


CREATE TABLE categoriaquestionario (
  idcategoriaquestionario int(11) NOT NULL,
  nomecategoriaquestionario varchar(50) NOT NULL,
  PRIMARY KEY (idcategoriaquestionario)
) ENGINE=InnoDB;


CREATE TABLE questionario (
  idquestionario int(11) NOT NULL,
  idquestionarioorigem int(11) DEFAULT NULL,
  idcategoriaquestionario int(11) NOT NULL,
  nomequestionario varchar(50) NOT NULL,
  idempresa int(11) NOT NULL,
  ativo char(1) NOT NULL DEFAULT 'S',
  javascript text,
  PRIMARY KEY (idquestionario),
  KEY fk_reference_1 (idcategoriaquestionario),
  KEY fk_questionarioorigem (idquestionarioorigem),
  CONSTRAINT fk_questionarioorigem FOREIGN KEY (idquestionarioorigem) REFERENCES questionario (idquestionario),
  CONSTRAINT fk_reference_1 FOREIGN KEY (idcategoriaquestionario) REFERENCES categoriaquestionario (idcategoriaquestionario)
) ENGINE=InnoDB;


CREATE TABLE grupoquestionario (
  idgrupoquestionario int(11) NOT NULL,
  idquestionario int(11) NOT NULL,
  nomegrupoquestionario varchar(80) NOT NULL,
  ordem smallint(6) DEFAULT NULL,
  PRIMARY KEY (idgrupoquestionario),
  KEY fk_reference_2 (idquestionario),
  CONSTRAINT fk_reference_2 FOREIGN KEY (idquestionario) REFERENCES questionario (idquestionario)
) ENGINE=InnoDB;


CREATE TABLE questaoquestionario (
  idquestaoquestionario int(11) NOT NULL,
  idgrupoquestionario int(11) DEFAULT NULL,
  idquestaoagrupadora int(11) DEFAULT NULL,
  idquestaoorigem int(11) DEFAULT NULL,
  tipo char(1) NOT NULL,
  tituloquestaoquestionario text NOT NULL,
  tipoquestao char(1) NOT NULL,
  sequenciaquestao int(11) NOT NULL,
  valordefault text,
  textoinicial text,
  tamanho int(11) DEFAULT NULL,
  decimais int(11) DEFAULT NULL,
  inforesposta char(1) DEFAULT NULL,
  valoresreferencia text,
  unidade text,
  obrigatoria char(1) NOT NULL,
  ponderada char(1) DEFAULT NULL,
  qtdelinhas int(11) DEFAULT NULL,
  qtdecolunas int(11) DEFAULT NULL,
  cabecalholinhas char(1) DEFAULT NULL,
  cabecalhocolunas char(1) DEFAULT NULL,
  nomelistagem varchar(30) DEFAULT NULL,
  ultimovalor char(1) DEFAULT NULL,
  idsubquestionario int(11) DEFAULT NULL,
  abaresultsubform varchar(200) DEFAULT NULL,
  sigla varchar(100) DEFAULT NULL,
  imprime char(1) DEFAULT NULL,
  calculada char(1) DEFAULT NULL,
  editavel char(1) DEFAULT NULL,
  valorpermitido1 decimal(15,5) DEFAULT NULL,
  valorpermitido2 decimal(15,5) DEFAULT NULL,
  idimagem int(11) DEFAULT NULL,
  PRIMARY KEY (idquestaoquestionario),
  KEY ix_sigla_questao (sigla),
  KEY ix_idquestaoorigem (idquestaoorigem),
  KEY fk_questaoagrupadora (idquestaoagrupadora),
  KEY fk_reference_13 (idsubquestionario),
  KEY fk_reference_3 (idgrupoquestionario),
  CONSTRAINT fk_questaoagrupadora FOREIGN KEY (idquestaoagrupadora) REFERENCES questaoquestionario (idquestaoquestionario),
  CONSTRAINT fk_reference_13 FOREIGN KEY (idsubquestionario) REFERENCES questionario (idquestionario),
  CONSTRAINT fk_reference_3 FOREIGN KEY (idgrupoquestionario) REFERENCES grupoquestionario (idgrupoquestionario)
) ENGINE=InnoDB;


CREATE TABLE opcaorespostaquestionario (
  idopcaorespostaquestionario int(11) NOT NULL,
  idquestaoquestionario int(11) NOT NULL,
  titulo varchar(255) NOT NULL,
  peso int(11) DEFAULT NULL,
  valor varchar(50) DEFAULT NULL,
  geraalerta char(1) DEFAULT NULL,
  exibecomplemento char(1) DEFAULT NULL,
  idquestaocomplemento int(11) DEFAULT NULL,
  PRIMARY KEY (idopcaorespostaquestionario),
  KEY fk_reference_14 (idquestaocomplemento),
  KEY fk_reference_4 (idquestaoquestionario),
  CONSTRAINT fk_reference_14 FOREIGN KEY (idquestaocomplemento) REFERENCES questaoquestionario (idquestaoquestionario),
  CONSTRAINT fk_reference_4 FOREIGN KEY (idquestaoquestionario) REFERENCES questaoquestionario (idquestaoquestionario) ON DELETE CASCADE
) ENGINE=InnoDB;


CREATE TABLE respostaitemquestionario (
  idrespostaitemquestionario int(11) NOT NULL,
  ididentificadorresposta int(11) NOT NULL,
  idquestaoquestionario int(11) NOT NULL,
  sequencialresposta int(11) DEFAULT NULL,
  respostatextual text,
  respostapercentual decimal(15,5) DEFAULT NULL,
  respostavalor decimal(15,5) DEFAULT NULL,
  respostavalor2 decimal(15,5) DEFAULT NULL,
  respostanumero decimal(8,0) DEFAULT NULL,
  respostanumero2 decimal(8,0) DEFAULT NULL,
  respostadata date DEFAULT NULL,
  respostahora varchar(4) DEFAULT NULL,
  respostames smallint(6) DEFAULT NULL,
  respostaano smallint(6) DEFAULT NULL,
  respostaidlistagem varchar(10) DEFAULT NULL,
  respostadia smallint(6) DEFAULT NULL,
  PRIMARY KEY (idrespostaitemquestionario),
  KEY ix_ident_questao (ididentificadorresposta,idquestaoquestionario),
  KEY ix_idquestao (idquestaoquestionario),
  CONSTRAINT fk_reference_5 FOREIGN KEY (idquestaoquestionario) REFERENCES questaoquestionario (idquestaoquestionario)
) ENGINE=InnoDB;


CREATE TABLE respostaitemquestionarioanexos (
  idrespostaitmquestionarioanexo int(11) NOT NULL,
  idrespostaitemquestionario int(11) NOT NULL,
  caminhoanexo varchar(255) NOT NULL,
  observacao text,
  PRIMARY KEY (idrespostaitmquestionarioanexo),
  KEY fk_rspt_anx_reference_rspt_itm (idrespostaitemquestionario),
  CONSTRAINT fk_rspt_anx_reference_rspt_itm FOREIGN KEY (idrespostaitemquestionario) REFERENCES respostaitemquestionario (idrespostaitemquestionario) ON DELETE CASCADE
) ENGINE=InnoDB;


CREATE TABLE respostaitemquestionarioopcoes (
  idrespostaitemquestionario int(11) NOT NULL,
  idopcaorespostaquestionario int(11) NOT NULL,
  PRIMARY KEY (idrespostaitemquestionario,idopcaorespostaquestionario),
  KEY fk_reference_7 (idopcaorespostaquestionario),
  CONSTRAINT fk_reference_7 FOREIGN KEY (idopcaorespostaquestionario) REFERENCES opcaorespostaquestionario (idopcaorespostaquestionario),
  CONSTRAINT fk_rspta_q_reference_rspta_itm FOREIGN KEY (idrespostaitemquestionario) REFERENCES respostaitemquestionario (idrespostaitemquestionario) ON DELETE CASCADE
) ENGINE=InnoDB;


CREATE TABLE solicitacaoservicoquestionario (
  idsolicitacaoquestionario int(11) NOT NULL,
  idquestionario int(11) NOT NULL,
  idsolicitacaoservico bigint(20) NOT NULL,
  dataquestionario timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  idresponsavel int(11) NOT NULL,
  idtarefa bigint(20) DEFAULT NULL,
  aba varchar(100) DEFAULT NULL,
  situacao char(1) NOT NULL,
  datahoragrav timestamp NOT NULL,
  conteudoimpresso text,
  PRIMARY KEY (idsolicitacaoquestionario),
  KEY fk_questionario_idx (idquestionario),
  KEY fk_empregado_idx (idresponsavel),
  KEY fk_solquest_solicitacao_idx (idsolicitacaoservico),
  KEY fk_solquest_tarefa_idx (idtarefa),
  CONSTRAINT fk_solquest_tarefa FOREIGN KEY (idtarefa) REFERENCES bpm_itemtrabalhofluxo (iditemtrabalho) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_solquest_empregado FOREIGN KEY (idresponsavel) REFERENCES empregados (idempregado) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_solquest_questionario FOREIGN KEY (idquestionario) REFERENCES questionario (idquestionario) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_solquest_solicitacao FOREIGN KEY (idsolicitacaoservico) REFERENCES solicitacaoservico (idsolicitacaoservico) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB;


ALTER TABLE templatesolicitacaoservico ADD COLUMN idquestionario INT NULL  AFTER habilitagravarecontinuar ,   ADD CONSTRAINT fk_questionario  FOREIGN KEY (idquestionario )  REFERENCES questionario (idquestionarioorigem )  ON DELETE NO ACTION  ON UPDATE NO ACTION, ADD INDEX fk_questionario_idx (idquestionario ASC);

ALTER TABLE templatesolicitacaoservico CHANGE COLUMN nomeclassedto nomeclassedto VARCHAR(255) NULL DEFAULT NULL  , CHANGE COLUMN nomeclasseaction nomeclasseaction VARCHAR(255) NULL DEFAULT NULL  , CHANGE COLUMN nomeclasseservico nomeclasseservico VARCHAR(255) NULL DEFAULT NULL  , CHANGE COLUMN urlrecuperacao urlrecuperacao VARCHAR(255) NULL DEFAULT NULL;

-- autor:pedro.lino
-- data: 16/05/2013


 CREATE TABLE controlecontrato (
    idcontrolecontrato INT(11) NOT NULL,
    idcontrato INT(11),
    numerosubscricao VARCHAR(250),
    endereco VARCHAR(250),
    contato VARCHAR(250),
    email VARCHAR(250),
    telefone1 VARCHAR(250),
    telefone2 VARCHAR(250),
    tiposubscricao INT(11),
    url VARCHAR(250),
    login VARCHAR(250),
    senha VARCHAR(250),
    datainicio DATE,
    datafim DATE,
    cliente VARCHAR(250),
    CONSTRAINT CONTROLECONTRATO_PK PRIMARY KEY (idcontrolecontrato),
    CONSTRAINT CONTROLECONTRATO_CONTRATO_FK1 FOREIGN KEY (idcontrato)
        REFERENCES CONTRATOS (IDCONTRATO),
    CONSTRAINT CONTROLECONTRATO_TS FOREIGN KEY (tiposubscricao)
        REFERENCES TIPOSUBSCRICAO (idtiposubscricao)
);
	  
	  

	  
 CREATE TABLE controlecontratoocorrencia 
   (	idccocorrencia INT(11) NOT NULL , 
	assuntoccocorrencia VARCHAR(250), 
	idempregadoocorrencia INT(11), 
	idcontrolecontrato INT(11), 
	dataccocorrencia DATE, 
	 CONSTRAINT CONTROLECONTRATOOCORRENCI_PK PRIMARY KEY (idccocorrencia),
	 CONSTRAINT CONTROLECONTRATOOCORRENCI_FK1 FOREIGN KEY (idcontrolecontrato)
	  REFERENCES CONTROLECONTRATO (idcontrolecontrato)
   ) ;
   

   
     CREATE TABLE controlecontratopagamento 
   (	idccpagamento INT(11) NOT NULL , 
	parcelaccpagamento INT(11), 
	idcontrolecontrato INT(11), 
	dataatrasoccpagamento DATE, 
	dataccpagamento DATE, 
	 CONSTRAINT CONTROLECONTRATOPAGAMENTO_PK PRIMARY KEY (idccpagamento), 
	 CONSTRAINT CONTROLECONTRATOPAG_FK_CC FOREIGN KEY (idcontrolecontrato)
	  REFERENCES CONTROLECONTRATO (idcontrolecontrato) 
   );


	CREATE TABLE controlecontratotreinamento 
   (	idcctreinamento INT(11) NOT NULL , 
	idcontrolecontrato INT(11), 
	idempregadotreinamento INT(11), 
	nomecctreinamento VARCHAR(250 ), 
	datacctreinamento DATE, 
	 CONSTRAINT CONTROLECONTRATOTREINA_PK_CC PRIMARY KEY (idcctreinamento), 
  
	 CONSTRAINT CONTROLECONTRATOTREINA_FK_CC FOREIGN KEY (idcontrolecontrato)
	  REFERENCES CONTROLECONTRATO (idcontrolecontrato) , 
    
	 CONSTRAINT CONTROLECONTRATOTREINA_FK_EMP FOREIGN KEY (idempregadotreinamento)
	  REFERENCES EMPREGADOS (IDEMPREGADO) 
   );


    CREATE TABLE controlecontratoversao
   (	idcontrolecontrato INT(11), 
	idccversao INT(11) NOT NULL , 
	nomeccversao VARCHAR(100 ), 
	 CONSTRAINT CONTROLECONTRATOVERSAO_PK PRIMARY KEY (idccversao), 
  
	 CONSTRAINT CONTROLECONTRATOVERSAO_CO_FK1 FOREIGN KEY (idcontrolecontrato)
	  REFERENCES CONTROLECONTRATO (idcontrolecontrato) 
   ) ;


	CREATE TABLE ccmodulosistema 
   (	idmodulosistema INT(11) NOT NULL , 
	idcontrolecontrato INT(11), 
	 
   CONSTRAINT CCMODULOSISTEMA_PK PRIMARY KEY (idmodulosistema) , 
   CONSTRAINT CCMODULOSISTEMA_CONTROLEC_FK1 FOREIGN KEY (idcontrolecontrato)
	  REFERENCES CONTROLECONTRATO (idcontrolecontrato) 
   );

     CREATE TABLE tiposubscricao 
   (	idtiposubscricao INT(11) NOT NULL , 
	nometiposubscricao VARCHAR(250 ), 
	 CONSTRAINT TIPOSUBSCRICAO_PK PRIMARY KEY (idtiposubscricao)

   );
    CREATE TABLE modulosistema 
   (	idmodulosistema INT(11) NOT NULL , 
	nomemodulosistema VARCHAR(250),
  
	 CONSTRAINT MODULOSISTEMA_PK PRIMARY KEY (idmodulosistema)
   );
   
-- Inserts TABLE "MODULOSISTEMA" 
insert into modulosistema VALUES(1, 'INCIDENTE');
insert into modulosistema VALUES(2, 'REQUISIÇÃƒO');
insert into modulosistema VALUES(3, 'MUDANÇ‡A');
insert into modulosistema VALUES(4, 'EVENTO');
insert into modulosistema VALUES(5, 'CONTINUIDADE');
insert into modulosistema VALUES(6, 'CONHECIMENTO');
insert into modulosistema VALUES(7, 'SLA');
insert into modulosistema VALUES(8, 'PROBLEMA');
insert into modulosistema VALUES(9, 'CAPACIDADE');
insert into modulosistema VALUES(10, 'PORTIFÓ“LIO');
insert into modulosistema VALUES(11, 'ATIVOS E CONFIG.');
insert into modulosistema VALUES(12, 'GERENCIAMENTO DE SERVIÇ‡OS');
insert into modulosistema VALUES(13, 'LIBERAÇ‡ÃƒO');
insert into modulosistema VALUES(14, 'DISPONIBILIDADE');


-- INICIO - MODULO DE LIBERACAO

-- danillo.lisboa
-- adicona campo idliberacao na tabela atividadeperiodica
alter table atividadeperiodica add idLiberacao integer REFERENCES liberacao(idliberacao);
-- fim

-- danillo.lisboa
-- adicona campo idcontrato na tabela liberacao
alter table liberacao add idcontrato integer;
-- fim

-- danillo.lisboa
-- adiciona tabela de ligacao entre a tabela de liberacao e problema
-- CREATE TABLE liberacaoproblema (  idliberacao integer NOT NULL,  idproblema integer NOT NULL,  PRIMARY KEY (idliberacao,idproblema),  FOREIGN KEY (idproblema) REFERENCES problema (idproblema));
CREATE TABLE liberacaoproblema ( idliberacao integer, idproblema integer NOT NULL, idhistoricoliberacao integer DEFAULT NULL);
-- fim

-- CRIAÇÃO TABELA LIBERACAOMUDANCA
CREATE TABLE liberacaomudanca ( idliberacao integer, idrequisicaomudanca integer, idhistoricoliberacao integer DEFAULT NULL);

-- Cria a coluna idhistoricoliberacao caso a tabela liberacaomudanca exista.
alter table liberacaomudanca add idhistoricoliberacao int(11);
alter table liberacaomudanca drop primary key;
alter table liberacaomudanca modify idliberacao integer(11) default null;

-- criação tabela ocorrencialiberacao

CREATE TABLE ocorrencialiberacao (
  idocorrencia int(11) NOT NULL,
  iditemtrabalho bigint(20) DEFAULT NULL,
  idjustificativa int(11) DEFAULT NULL,
  idrequisicaoliberacao int(11) DEFAULT NULL,
  dataregistro date DEFAULT NULL,
  horaregistro varchar(5) DEFAULT NULL,
  registradopor varchar(100) DEFAULT NULL,
  descricao varchar(200) DEFAULT NULL,
  datainicio date DEFAULT NULL,
  datafim date DEFAULT NULL,
  complementojustificativa longtext,
  dadosliberacao longtext,
  informacoescontato longtext,
  categoria varchar(20) DEFAULT NULL,
  origem char(1) DEFAULT NULL,
  tempogasto smallint(6) DEFAULT NULL,
  ocorrencia longtext,
  idcategoriaocorrencia int(11) DEFAULT NULL,
  idorigemocorrencia int(11) DEFAULT NULL,
  PRIMARY KEY (idocorrencia),
  KEY fk_ocorrencia_reference_bpm_item (iditemtrabalho),
  KEY fk_ocorrencia_reference_justific (idjustificativa),
  KEY fk_ocorrencia_reference_requisic (idrequisicaoliberacao)
);

-- Murilo Almeida Pacheco
-- criação da tabela de historico de liberação no mysql
CREATE TABLE historicoliberacao (
  idhistoricoliberacao int(11) NOT NULL,
  idexecutormodificacao int(11) NOT NULL,
  datahoraModificacao timestamp NULL DEFAULT NULL,
  tipoModificacao varchar(1) DEFAULT NULL,
  historicoVersao double(10,1) DEFAULT NULL,
  idliberacao int(11) NOT NULL,
  idsolicitante int(11) NOT NULL,
  idresponsavel int(11) DEFAULT NULL,
  titulo varchar(100) NOT NULL,
  descricao text NOT NULL,
  datainicial date NOT NULL,
  datafinal date NOT NULL,
  dataliberacao date DEFAULT NULL,
  situacao char(1) NOT NULL COMMENT 'A - Aceita\n            E - Em execução\n            F - Finalizada\n            X - Cancelada',
  risco char(1) NOT NULL COMMENT 'B - Baixo\n            M - Médio\n            A - Alto',
  versao varchar(25) DEFAULT NULL,
  seqreabertura smallint(6) DEFAULT NULL,
  enviaemailcriacao varchar(1) DEFAULT NULL,
  enviaemailacoes varchar(1) DEFAULT NULL,
  tempoatrasohh smallint(6) DEFAULT NULL,
  tempoatrasomm smallint(6) DEFAULT NULL,
  tempocapturahh smallint(6) DEFAULT NULL,
  tempocapturamm smallint(6) DEFAULT NULL,
  datahoratermino timestamp NULL DEFAULT NULL,
  datahoraconclusao timestamp NULL DEFAULT NULL,
  status varchar(45) DEFAULT NULL,
  tempodecorridohh smallint(6) DEFAULT NULL,
  tempodecorridomm smallint(6) DEFAULT NULL,
  tempoatendimentohh smallint(6) DEFAULT NULL,
  tempoatendimentomm smallint(6) DEFAULT NULL,
  datahoracaptura timestamp NULL DEFAULT NULL,
  datahorareativacao timestamp NULL DEFAULT NULL,
  datahorainicio timestamp NULL DEFAULT NULL,
  idcalendario int(11) DEFAULT NULL,
  datahorasuspensao timestamp NULL DEFAULT NULL,
  enviaemailfinalizacao varchar(1) DEFAULT NULL,
  prazohh smallint(6) DEFAULT NULL,
  prazomm smallint(6) DEFAULT NULL,
  idproprietario int(11) NOT NULL,
  datahorainicioagendada timestamp NULL DEFAULT NULL,
  datahoraterminoagendada timestamp NULL DEFAULT NULL,
  idtipoliberacao int(11) DEFAULT NULL,
  idGrupoAtual int(11) DEFAULT NULL,
  PRIMARY KEY (idhistoricoliberacao),
  KEY fk_reference_requisicao_liberacao (idliberacao),
  KEY fk_reference_usuario_modificacao (idexecutormodificacao)
);

-- esta linha adiciona a coluna baseleine na tabela historicoliberacao
ALTER TABLE historicoliberacao ADD baseline varchar(30) ;
alter table historicoliberacao add  alterarsituacao varchar(1);
alter table historicoliberacao add  acaoFluxo varchar(1);

-- novos campos da tabela historicoliberacao relacionados ao contato.
ALTER TABLE historicoliberacao ADD idcontatorequisicaoliberacao int(11) ;
ALTER TABLE historicoliberacao ADD telefonecontato varchar(45) ;
ALTER TABLE historicoliberacao ADD ramal varchar(5) ;
ALTER TABLE historicoliberacao ADD observacao text ;
ALTER TABLE historicoliberacao ADD idunidade int(11) ;
ALTER TABLE historicoliberacao ADD nomecontato2 varchar(80) ;
ALTER TABLE historicoliberacao ADD emailcontato varchar(200) ;
ALTER TABLE historicoliberacao ADD Idlocalidade int(11) ;


-- criação tabela requisicaoliberacaoitemconfiguracao para armazenar os ICs vinculados as liberaÃ§Ãµes.
CREATE TABLE requisicaoliberacaoitemconfiguracao (
  idrequisicaoliberacaoitemconfiguracao int(11) NOT NULL,
  idrequisicaoliberacao int(11) DEFAULT NULL,
  iditemconfiguracao int(11) DEFAULT NULL,
  descricao varchar(100) DEFAULT NULL,
  idhistoricoliberacao int(11) DEFAULT NULL, 
  PRIMARY KEY (idrequisicaoliberacaoitemconfiguracao),
  KEY fk_requisiclib_reference_req (idrequisicaoliberacao),
  KEY fk_requisic_reference_itemconf (iditemconfiguracao),
  CONSTRAINT fk_requisiclib_reference_itemconf FOREIGN KEY (iditemconfiguracao) REFERENCES itemconfiguracao (iditemconfiguracao),
  CONSTRAINT fk_requisiclib_reference_req FOREIGN KEY (idrequisicaoliberacao) REFERENCES liberacao (idrequisicaoliberacao),
  CONSTRAINT fk_histlib_reference_hist FOREIGN KEY (idhistoricoliberacao) REFERENCES historicoliberacao (idhistoricoliberacao)
);

-- criação da tablea conhecimentoliberacao para vincular liberaÃ§Ãµes a base de conhecimento
CREATE TABLE conhecimentoliberacao (
  idrequisicaoliberacao int(11) NOT NULL,
  idbaseconhecimento int(11) NOT NULL,
  PRIMARY KEY (idrequisicaoliberacao,idbaseconhecimento),
  KEY fk_ref_conhmud_bc (idbaseconhecimento),
  CONSTRAINT fk_ref_conhmd_icc FOREIGN KEY (idrequisicaoliberacao) REFERENCES liberacao (idrequisicaoliberacao),
  CONSTRAINT fk_ref_conhmud_bc FOREIGN KEY (idbaseconhecimento) REFERENCES baseconhecimento (idbaseconhecimento)
);

-- MAYCON


-- =======TIPO LIBERACAO======

CREATE TABLE tipoliberacao (
  idtipoliberacao int(11) NOT NULL,
  idtipofluxo int(11) DEFAULT NULL,
  idmodeloemailcriacao int(11) DEFAULT NULL,
  idmodeloemailfinalizacao int(11) DEFAULT NULL,
  idmodeloemailacoes int(11) DEFAULT NULL,
  idgrupoexecutor int(11) DEFAULT NULL,
  idcalendario int(11) DEFAULT NULL,
  nometipoliberacao varchar(100) DEFAULT NULL,
  datainicio date DEFAULT NULL,
  datafim date DEFAULT NULL,
  PRIMARY KEY (idtipoliberacao),
  KEY idtipofluxo (idtipofluxo),
  KEY idmodeloemailfinalizacao (idmodeloemailfinalizacao),
  KEY idmodeloemailacoes (idmodeloemailacoes),
  KEY idgrupoexecutor (idgrupoexecutor),
  KEY idcalendario (idcalendario),
  KEY tipoliberacao_ibfk_2 (idmodeloemailcriacao),
  CONSTRAINT tipoliberacao_ibfk_1 FOREIGN KEY (idtipofluxo) REFERENCES bpm_tipofluxo (idtipofluxo),
  CONSTRAINT tipoliberacao_ibfk_2 FOREIGN KEY (idmodeloemailcriacao) REFERENCES modelosemails (idmodeloemail),
  CONSTRAINT tipoliberacao_ibfk_5 FOREIGN KEY (idgrupoexecutor) REFERENCES grupo (idgrupo),
  CONSTRAINT tipoliberacao_ibfk_6 FOREIGN KEY (idcalendario) REFERENCES calendario (idcalendario)
);

-- ============EXECUCAO LIBERACAO===========

CREATE TABLE execucaoliberacao (
  idexecucao int(11) NOT NULL,
  idinstanciafluxo bigint(20) NOT NULL,
  idliberacao int(11) NOT NULL,
  idfluxo bigint(20) NOT NULL,
  seqreabertura smallint(6) DEFAULT NULL,
  PRIMARY KEY (idexecucao),
  KEY fk_execucaoliberacao_reference_bpm_inst (idinstanciafluxo),
  KEY fk_execucaoliberacao_reference_requisic (idliberacao),
  KEY fk_execucaoliberacao_reference_bpm_flux (idfluxo),
  CONSTRAINT fk_execucaolib_reference_bpm_flux FOREIGN KEY (idfluxo) REFERENCES bpm_fluxo (idfluxo),
  CONSTRAINT fk_execucaolib_reference_bpm_inst FOREIGN KEY (idinstanciafluxo) REFERENCES bpm_instanciafluxo (idinstancia)
);

-- ============JUSTIFICATIVA LIBERACAO===========
create table justificativaliberacao (
    idjustificativaliberacao int(11) not null,
    descricaojustificativa varchar(100) not null,
    suspensao char(1) not null,
    situacao char(1) not null,
    aprovacao char(1) default null,
    deleted char(1) default null,
    primary key (idjustificativaliberacao)
);

-- ============LIBERACAO===========
alter table liberacao 
add column 	seqreabertura smallint(6) ,
add column 	enviaemailcriacao varchar(1) ,
add column 	enviaemailacoes varchar(1) ,
add column 	tempoatrasohh smallint(6) ,
add column 	tempoatrasomm smallint(6) ,
add column 	tempocapturahh smallint(6) ,
add column 	tempocapturamm smallint(6) ,
add column 	datahoratermino timestamp NULL ,
add column 	datahoraconclusao timestamp NULL ,
add column 	status varchar(45) ,
add column 	tempodecorridohh smallint(6) ,
add column 	tempodecorridomm smallint(6) ,
add column 	tempoatendimentohh smallint(6) ,
add column 	tempoatendimentomm smallint(6) ,
add column 	datahoracaptura timestamp NULL ,
add column 	datahorareativacao timestamp NULL ,
add column 	datahorainicio timestamp NULL ,
add column 	idcalendario int(11) ,
add column 	datahorasuspensao timestamp NULL ,
add column 	enviaemailfinalizacao varchar(1) ,
add column 	prazohh smallint(6) ,
add column 	prazomm smallint(6) ,
add column 	idproprietario int(11) NOT NULL,
add column 	datahorainicioagendada timestamp NULL ,
add column 	datahoraterminoagendada timestamp NULL ,
add column 	idtipoliberacao integer(11),
add column 	idGrupoAtual integer(11),
add column 	prioridade int(11) DEFAULT NULL,
add column 	nivelurgencia varchar(255) DEFAULT NULL,
add column 	nivelimpacto varchar(255) DEFAULT NULL,
add column idaprovador int(11)  DEFAULT NULL,
add column 	datahoraaprovacao timestamp NULL,
add column fechamento text,
add column idgruponivel1 int(11),
add column idGrupoAprovador int(11);


ALTER TABLE liberacao CHANGE situacao situacao char(1) DEFAULT null;


-- fim modelos emails

-- BPM TIPO FLUXO
INSERT INTO bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) VALUES (54, 'LiberacaoDeploy', 'Liberação Deploy', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoLiberacao');

-- BPM FLUXO
INSERT INTO bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) VALUES (50, '1.0', 54, 'requisicaoLiberacao;requisicaoLiberacao.status;requisicaoLiberacao.nomeGrupoAtual', '', '2013-05-18', NULL);

-- BPM ELEMENTO FLUXO
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (592,50,'Inicio','','','','','','','','','','','','','',160,73,32,32,'','',NULL,'','','','S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (593,50,'Tarefa','','Liberar','Liberar','U','pages/requisicaoLiberacao/requisicaoLiberacao.load?escalar=S&alterarSituacao=S','','#{requisicaoLiberacao.nomeGrupoAtual}','','','','','','',324,59,65,140,'','',NULL,'','N','','S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (594,50,'Tarefa','','Em Execução ','Em Execução','U','pages/requisicaoLiberacao/requisicaoLiberacao.load?escalar=S&alterarSituacao=S','','#{requisicaoLiberacao.nomeGrupoAtual}','','','','','','',753,28,65,140,'','',NULL,'','N','','S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (595,50,'Script','','Encerrar','','','','','','','','','#{execucaoFluxo}.encerra();','','',519,203,65,140,'','',NULL,'','','','S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (596,50,'Porta','','','','','','','','','','','','','',569,333,42,42,'','',NULL,'','','','S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (597,50,'Porta','','','','','','','','','','','','','',803,233,42,42,'','',NULL,'','','','S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (598,50,'Porta','','','','','','','','','','','','','',568,71,42,42,'','',NULL,'','','','S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (599,50,'Finalizacao','','','','','','','','','','','','','',237,219,32,32,'','',NULL,'','','','S', NULL);

-- BPM SEQUENCIA FLUXO
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (592,593,50,'','','',1,3,258,90.25,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (593,598,50,'','','',1,3,516,91.75,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (594,597,50,'','','',2,0,823.5,163,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (596,593,50,'','','!#{requisicaoLiberacao}.atendida();',3,2,391,353,'S','NÃ£o Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (597,596,50,'','','#{requisicaoLiberacao}.liberada();',2,1,822,353,'S','Liberada');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (598,594,50,'','','!#{requisicaoLiberacao}.atendida();',0,3,665,59.75,'N','NÃ£o Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (597,598,50,'','','#{requisicaoLiberacao}.emAtendimento();',3,1,706.5,173,'N','Em Atendimento');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (595,599,50,'','','',3,1,394,235.25,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (598,595,50,'','','#{requisicaoLiberacao}.atendida();',2,0,589,158,'N','Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (596,595,50,'','','#{requisicaoLiberacao}.atendida();',0,2,589.5,300.5,'N','Resolvida');

-- TIPO LIBERACAO
INSERT INTO tipoliberacao (idtipoliberacao, idtipofluxo, idmodeloemailcriacao, idmodeloemailfinalizacao, idmodeloemailacoes, idgrupoexecutor, idcalendario, nometipoliberacao, datainicio, datafim) VALUES (1,54,51,52,53,1,1,'Requisição Liberação Normal','2013-05-20',null);


-- EDMAR FAGUNDES - 16/05/2013 {
-- ADICIONA TABELA DE APROVAÃ‡ÃƒO PARA AUXILIAR NA PARTE DE PESQUISA
CREATE TABLE aprovacaorequisicaoliberacao (
  idaprovacaorequisicaoliberacao int(11) NOT NULL,
  idrequisicaoliberacao bigint(20) NOT NULL,
  idtarefa bigint(20) DEFAULT NULL,
  idresponsavel int(11) NOT NULL,
  datahora timestamp NULL DEFAULT NULL,
  idjustificativa int(11) DEFAULT NULL,
  complementojustificativa text,
  observacoes text,
  aprovacao char(1) NOT NULL,
  PRIMARY KEY (idaprovacaorequisicaoliberacao)
);

-- ADICIONA TABELA DE CONTATO PARA SALVAR OS DADOS DO CONTATO AO CRIAR UMA NOVA LIBERAÃ‡ÃƒO
CREATE TABLE contatorequisicaoliberacao (
  idContatoRequisicaoLiberacao int(10) NOT NULL,
  nomeContato varchar(70) DEFAULT NULL,
  telefoneContato varchar(20) DEFAULT NULL,
  emailContato varchar(120) DEFAULT NULL,
  observacao text,
  idLocalidade int(11) DEFAULT NULL,
  ramal varchar(4) DEFAULT NULL,
  idUnidade int(11) DEFAULT NULL,
  PRIMARY KEY (idContatoRequisicaoLiberacao)
);


-- ADICIONA COLUNA IDCONTATOREQUISICAOLIBERACAO PARA FAZER A CONEXAO COM A TABELA CONTATOREQUISICAOLIBERACAO
ALTER TABLE liberacao ADD COLUMN idContatoRequisicaoLiberacao INT(11) NULL DEFAULT NULL ;
-- ADICIONA COLUNA IDULTIMAAPROVACAO PARA FAZER A CONEXAO COM A TABELA APROVACAOREQUISICAOLIBERACAO
ALTER TABLE liberacao ADD COLUMN idUltimaAprovacao INT(11) NULL DEFAULT NULL ;
-- }

-- criação tabela requisicaomudancaliberacao

CREATE TABLE requisicaomudancaliberacao
( idrequisicaomudancaliberacao int(11) NOT NULL,
idrequisicaomudanca int(11) NOT NULL,
idliberacao bigint(20) NOT NULL,
PRIMARY KEY (idrequisicaomudancaliberacao),
KEY requisicaomudancaliberacao_idliberacao_fkey (idliberacao),
KEY requisicaomudancarisco_idrequisicaomudanca_idx (idrequisicaomudanca) );

-- FIM - MODULO DE LIBERACAO

-- INSERT CATEGORIA IMAGEM - CLEDSON.JUNIOR
insert into categoriagaleriaimagem VALUES(1, 'EMAIL', null, CURRENT_TIMESTAMP);
-- FIM

-- autor:thays.araujo
-- data: 20/05/2013
alter table problema add column idcategoriasolucao int(11) ;
alter table problema add column precisasolucaocontorno char(1) ;
alter table problema add column resolvido char(1) ;

-- INICIO - MODULO DE MUDANCA

ALTER TABLE aprovacaomudanca ADD COLUMN dataHoraVotacao character varying(25);
ALTER TABLE requisicaomudanca ADD razaomudanca VARCHAR(200);

CREATE TABLE risco
(
  idrisco integer NOT NULL,
  nomerisco character varying(150) NOT NULL,
  detalhamento text,
  nivelrisco integer,
  datainicio date,
  datafim date,
  CONSTRAINT risco_pkey PRIMARY KEY (idrisco)
);

CREATE TABLE requisicaomudancarisco
(
  idrequisicaomudancarisco integer NOT NULL,
  idrequisicaomudanca integer NOT NULL,
  idrisco bigint NOT NULL,
  CONSTRAINT requisicaomudancarisco_pkey PRIMARY KEY (idrequisicaomudancarisco),
  CONSTRAINT requisicaomudancarisco_idrequisicaomudanca_fkey FOREIGN KEY (idrequisicaomudanca)
      REFERENCES requisicaomudanca (idrequisicaomudanca)
);

CREATE INDEX requisicaomudancarisco_idrequisicaomudanca_idx
  ON requisicaomudancarisco
  (idrequisicaomudanca);

CREATE INDEX requisicaomudancarisco_idrisco_idx
  ON requisicaomudancarisco
  (idrisco);
  
  
CREATE TABLE requisicaomudancaliberacao
(
  idrequisicaomudancaliberacao integer NOT NULL,
  idrequisicaomudanca integer NOT NULL,
  idliberacao bigint NOT NULL,
  CONSTRAINT requisicaomudancaliberacao_pkey PRIMARY KEY (idrequisicaomudancaliberacao),
  CONSTRAINT requisicaomudancaliberacao_idrequisicaomudanca_fkey FOREIGN KEY (idrequisicaomudanca)
      REFERENCES requisicaomudanca (idrequisicaomudanca),
  CONSTRAINT requisicaomudancaliberacao_idliberacao_fkey FOREIGN KEY (idliberacao)
      REFERENCES liberacao (idliberacao)
);

CREATE INDEX requisicaomudancarisco_idrequisicaomudanca_idx
  ON requisicaomudancaliberacao
  (idrequisicaomudanca);
  
  CREATE TABLE reuniaorequisicaomudanca
(
  idreuniaorequisicaomudanca integer NOT NULL,
  idrequisicaomudanca integer NOT NULL,
  localreuniao character varying(255),
  horainicio character varying(255),
  criadopor character varying(255),
  descricao character varying(255),
  status character varying(30),
  duracaoestimada integer,
  datacriacao date,
  datainicio date,
  CONSTRAINT reuniaorequisicaomudanca_pkey PRIMARY KEY (idreuniaorequisicaomudanca),
  CONSTRAINT rreqmud_idrreqmud_fkey FOREIGN KEY (idrequisicaomudanca)
      REFERENCES requisicaomudanca (idrequisicaomudanca)
);


-- tabela netmap

ALTER TABLE netmap ADD (datainventario date);

-- MySQL Flávio
CREATE TABLE midiasoftwarechave
(
   idmidiasoftwarechave integer, 
   idmidiasoftware integer, 
   chave varchar(255)
);

ALTER TABLE netmap CHANGE COLUMN date date_ DATE NULL;

ALTER TABLE tipoitemconfiguracao ADD COLUMN imagem VARCHAR(255) NULL;

-- alterar tabela parametrocorpore
ALTER TABLE parametrocorpore CHANGE COLUMN valor valor VARCHAR(200) NULL;

-- INICIO - MURILO GABRIEL RODRIGUES - 27/05/2013

alter table fluxoservico add column idfluxoservico int(11) not null auto_increment first, 
  drop primary key,
  add primary key (idfluxoservico),
  add unique index idfluxoservico_unique (idfluxoservico asc);
  
-- FIM - MURILO GABRIEL RODRIGUES - 27/05/2013

-- autor: thays.araujo
-- data : 04/06/2013
alter table problema change column datahorafim datahorafim timestamp null;

-- INICIO - MURILO GABRIEL RODRIGUES - 05/06/2013
ALTER TABLE versao ADD COLUMN idusuario INT(11) NULL AFTER nomeversao;
-- FIM - MURILO GABRIEL RODRIGUES - 05/06/2013

-- Flávio Júnior - 06/06/2013
ALTER TABLE midiasoftwarechave ADD COLUMN qtdpermissoes INT;

ALTER TABLE itemconfiguracao ADD COLUMN dtultimacaptura TIMESTAMP;

ALTER TABLE historicoic ADD COLUMN dtultimacaptura TIMESTAMP;

-- Módulo Mudança
ALTER TABLE requisicaomudanca ADD COLUMN ehpropostaaux char(1);
ALTER TABLE requisicaomudanca ADD COLUMN VOTACAOPROPOSTAAPROVADAAUX char(1);


create table aprovacaoproposta (
    idaprovacaoproposta integer not null,
    idrequisicaomudanca integer default null,
    idempregado integer default null,
    nomeempregado varchar(45) null,
    voto char(1) null,
    comentario varchar(200) null,
    dataHoraVotacao character varying(25),
    datahorainicio date null
);


-- Fim
set sql_safe_updates = 1;

