--SQLServer

-- autor: geber
-- data: 12/04/2013
create table ocorrenciaproblema (
  idocorrencia integer not null,
  iditemtrabalho bigint default null,
  idjustificativa integer default null,
  idproblema integer default null,
  dataregistro date default null,
  horaregistro varchar(5) default null,
  registradopor varchar(100) default null,
  descricao varchar(200) default null,
  datainicio date default null,
  datafim date default null,
  complementojustificativa text,
  dadosproblema text,
  informacoescontato text,
  categoria varchar(20) default null,
  origem char(1) default null,
  tempogasto smallint default null,
  ocorrencia text,
  idcategoriaocorrencia integer default null,
  idorigemocorrencia integer default null
);

alter table ocorrenciaproblema add constraint pk_ocorrenciaproblema primary key (idocorrencia);

alter table ocorrenciaproblema add constraint fk_ocorrenciaproblema_problema foreign key (idproblema) references problema (idproblema);

create index fk_idx_idproblema on problema (idproblema);

-- autor: thiago.monteiro
-- data: 12/04/2013
alter table problema add  acoescorretas varchar(4000);

alter table problema add  acoesincorretas varchar(4000);

alter table problema add  melhoriasfuturas varchar(4000);

alter table problema add  recorrenciaproblema varchar(4000);

alter table problema add  responsabilidadeterceiros varchar(4000);


-- autor: thiago.monteiro
-- data: 10/04/2013
alter table atividadeperiodica add  idproblema integer null;

alter table atividadeperiodica add constraint fk_atividadeperiodica_problema foreign key (idproblema) references problema (idproblema);

-- autor: thiago.monteiro
-- data: 11/04/2013
create table contatoproblema (
  idcontatoproblema integer not null,
  nomecontato varchar(100) default null,
  telefonecontato varchar(100) default null,
  emailcontato varchar(200) default null,
  observacao text,
  idlocalidade int default null,
  ramal varchar(5) default null
);

alter table contatoproblema add constraint pk_contatoproblema primary key (idcontatoproblema);

-- autor: thiago.monteiro
-- data: 10/04/2013
create table justificativaproblema (
    idjustificativaproblema int not null,
    descricaoproblema varchar(100) not null,
    suspensao char(1) not null,
    situacao char(1) not null,
    aprovacao char(1) default null,
    deleted char(1) default null
);


alter table justificativaproblema add constraint pk_justificativaproblema primary key (idjustificativaproblema);

-- autor: thiago.monteiro
-- data: 09/04/2013

create table execucaoproblema (
    idexecucao bigint not null,
    idproblema bigint default null,
    idfase bigint default null,
    idinstanciafluxo bigint default null,
    idfluxo bigint default null,
    prazohh smallint default null,
    prazomm smallint default null,
    seqreabertura int default null
);

-- Tabela execucaoproblema - chave primaria (pk)
alter table execucaoproblema add primary key (idexecucao);


-- Tabela execucaoproblema - indices (idx)
create index fk_execprob_problema on execucaoproblema (idproblema);

create index fk_execprob_fase on execucaoproblema (idfase);

create index fk_execprob_fluxo on execucaoproblema (idfluxo);

create index fk_execprob_instanciafluxo on execucaoproblema (idinstanciafluxo);

-- Tabela problema - acrescimo de novas colunas
alter table problema add  datahoracaptura datetime null;

alter table problema add  datahorainiciosla datetime null;

alter table problema add  datahorareativacao datetime null;

alter table problema add  datahorareativacaosla datetime null;

alter table problema add  datahorasuspensao datetime null;

alter table problema add  datahorasuspensaosla datetime null;

alter table problema add  enviaemailacoes char(1) null;

alter table problema add  enviaemailcriacao char(1) null;

alter table problema add  enviaemailfinalizacao char(1) null;

alter table problema add  idcalendario int null;

alter table problema add  idfaseatual bigint null;

alter table problema add  idsolicitacaoservico bigint null;

alter table problema add  idsolicitante int null;

alter table problema add  resposta text null;

alter table problema add  seqreabertura int null;

alter table problema add  situacaosla char(1) null;

alter table problema add  tempoatendimentohh smallint null;

alter table problema add  tempoatendimentomm smallint null;

alter table problema add  tempoatrasohh smallint null;

alter table problema add  tempoatrasomm smallint null;

alter table problema add  tempocapturahh smallint null;

alter table problema add  tempocapturamm smallint null;

alter table problema add  tempodecorridohh smallint null;

alter table problema add  tempodecorridomm smallint null;

-- Tabela problema - chave(s) estrangeira(s) (fk)
alter table problema add constraint fk_problema_calendario foreign key (idcalendario) references calendario (idcalendario);

alter table problema add constraint fk_problema_faseservico foreign key (idfaseatual) references faseservico (idfase);

alter table problema add constraint fk_problema_solicitacaoservico foreign key (idsolicitacaoservico) references solicitacaoservico(idsolicitacaoservico);

alter table problema add constraint fk_problema_solicitante foreign key (idsolicitante) references empregados (idempregado);

-- autor: thiago.monteiro
-- data: 14/04/2013
alter table problema add  idorigematendimento bigint null default null;

alter table problema add  diagnostico varchar(4000) null default null;

alter table problema add  fechamento varchar(1000) null default null;

alter table problema add constraint fk_problema_origematendimento foreign key (idorigematendimento) references origematendimento (idorigem);

-- autor: thiago.monteiro
-- data: 11/04/2013
alter table problema add  idcontatoproblema int null;

alter table problema add constraint fk_problema_contatoproblema foreign key (idcontatoproblema) references contatoproblema (idcontatoproblema);

-- autor: geber
-- data: 15/04/2013
alter table problema add acompanhamento char(1) default '';

-- autor: thiago.monteiro
-- data: 16/04/2013
alter table problema add  grave char(1) default '';

alter table problema add  precisamudanca char(1) default '';

-- autor: riubbe
-- data: 16/04/2013
insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (34,'Problema Registrado  -  ${IDPROBLEMA}','Senhor(a) ${SOLICITANTE},<br /><br />Informamos que o Problema Criado, foi registrado em ${DATAHORACAPTURA}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDPROBLEMA}<br /><strong>Tipo:</strong> ${PROATIVOREATIVO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong> ${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','registroProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (35,'Problema em Andamento  -  ${IDPROBLEMA}','Senhor(a) ${SOLICITANTE},<br /><br />Informamos que o Problema criado em ${DATAHORACAPTURA} esta em atendimento, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDPROBLEMA}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br />${TITULO}<br /><strong><br /></strong>${DESCRICAO}<br /><br /><strong>Grupo de atendimento:</strong>&nbsp;${NOMEGRUPOATUAL}<br /><br />Atenciosamente,<br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda','A','andamentoProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (36,'Problema Finalizado -  ${IDPROBLEMA}','Senhor(a) ${SOLICITANTE},<br /><br />Informamos que o Problema ${IDPROBLEMA},&nbsp; foi finalizado em ${DATAHORAFIM}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDPROBLEMA}<br /><strong>Tipo:</strong> ${PROATIVOREATIVO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><br /><strong>Status:</strong>${STATUS}<br /><strong>Descri&ccedil;&atilde;o:</strong> ${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','finalizadoProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (37,'Problema encaminhado para seu GRUPO DE TRABALHO','&nbsp;A solicita&ccedil;&atilde;o abaixo foi encaminhada para seu Grupo de Trabalho:<div>&nbsp;</div><div>N&uacute;mero: ${IDPROBLEMA}</div><div>Tipo: ${PROATIVOREATIVO}</div><div><strong>T&iacute;tulo:</strong> ${TITULO}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div>','A','grupoProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (38,'Requisições Problemas com prazo expirados','<br />informamos que existem&nbsp;requisi&ccedil;&otilde;es problemas com prazo para contornar ou solucionar expirados.<br /><br /><br />atenciosamente.<br />central it&nbsp;tecnologia da informa&ccedil;&atilde;o ltda.<br /><br />','a','problemaprazocontexp');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (39,'Requisição Mudança Reunião Marcada', '&nbsp;Senhor(a) ${NOMECONTATO},&nbsp;<br /><br />Informamos que haver&aacute; uma reuni&atilde;o relacionada a Requisi&ccedil;&atilde;o de mudan&ccedil;a de n&uacute;mero&nbsp;${IDREQUISICAOMUDANCA}.<br /><br /><strong><span style="font-size:12.0pt;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;mso-ansi-language:PT-BR;mso-fareast-language:PT-BR;mso-bidi-language:AR-SA"><font face="Times New Roman, serif">Local</font>:</span></strong><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,&quot;serif&quot;;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;mso-ansi-language:PT-BR;mso-fareast-language:PT-BR;mso-bidi-language:AR-SA">&nbsp;${LOCALREUNIAO}<br /> <strong>Data:</strong>&nbsp;${DATAINICIO}<br /> <strong>Hor&aacute;rio:</strong>&nbsp;${HORAINICIO}<br /> <strong>Dura&ccedil;&atilde;o Estimada:</strong>&nbsp;${DURACAOESTIMADA} minutos<br /> <br /> <strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br /></span><span serif; font-size: 16px;">${DESCRICAO}</span><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,&quot;serif&quot;;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;mso-ansi-language:PT-BR;mso-fareast-language:PT-BR;mso-bidi-language:AR-SA"><br /> <!--[if !supportLineBreakNewLine]--><br /> <!--[endif]--></span><br />Atenciosamente,&nbsp;<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.', 'A', 'reuniao');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (51,'Registro de Liberação  -  ${IDREQUISICAOLIBERACAO}' , 'Senhor(a) ${NOMESOLICITANTE},<br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de Libera&ccedil;&atilde;o foi registrada em ${DATAHORAINICIOSTR}, conforme os dados abaixo:<br /><br />N&uacute;mero: ${IDREQUISICAOLIBERACAO}<br />Tipo: ${TIPO}<br />T&iacute;tulo: ${TITULO}<br /><br />Descri&ccedil;&atilde;o: ${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br /><br /><br />' , 'A' , 'regestroLib');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (52,'Requisição Liberação finalizada  -  ${IDREQUISICAOLIBERACAO}' , 'Senhor(a) ${NOMESOLICITANTE},<br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de libera&ccedil;&atilde;o foi finalizada em ${DATAHORACONCLUSAO}, conforme os dados abaixo:<br /><br />N&uacute;mero: ${IDREQUISICAOLIBERACAO}<br />Tipo: ${TIPO}<br />T&iacute;tulo: ${TITULO}<br /><br />Status:${STATUS}<br />Descri&ccedil;&atilde;o:<br />${DESCRICAO}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />' , 'A' , 'liberacaoFinalizada');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (53,'Requisição Liberação em andamento- ${IDREQUISICAOLIBERACAO}' , 'Senhor(a) ${NOMESOLICITANTE},<br /><br />Informamos que a requisi&ccedil;&atilde;o de libera&ccedil;&atilde;o registrada em ${DATAHORAINICIOSTR} est&aacute; em atendimento, conforme os dados abaixo:<br /><br />N&uacute;mero: ${IDREQUISICAOLIBERACAO}<br />T&iacute;tulo: ${TITULO}<br /><br />Descri&ccedil;&atilde;o: <br />${TITULO}<br /><br />${DESCRICAO}<br /><br />Grupo de atendimento: ${NOMEGRUPOATUAL}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda<br />' , 'A' , 'liberacaoEmAndamento');

-- autor: thiago.monteiro
-- data: 19/04/2013
insert into bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) values (50, 'ProblemaPadrao', 'Problema Padrão', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoProblema');

insert into bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) values (100, '1.0', 50, 'problema;problema.status;problema.nomeGrupoAtual', '', '2013-04-19', NULL);;

alter table problema alter column datahoralimite  datetime null;

-- autor: thiago.monteiro
-- data: 22/04/2013
insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1000, 100, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 66, 216, 32, 32, '', '', null, '', '', '', 'S', NULL);;

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1001, 100, 'Tarefa', '', 'Registrada', 'Registrada', 'U', 'pages/problema/problema.load?alterarSituacao=N&faseAtual=Registrada&fase=EmInvestigacao', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 178, 200, 65, 140, '', '', null, '', '', '', 'S', NULL);;

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1002, 100, 'Tarefa', '', 'Investigação e diagnóstico', 'Investigação e diagnóstico', 'U', ' pages/problema/problema.load?alterarSituacao=N&faseAtual=EmInvestigacao&fase=RegistroErroConhecido', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 410, 200, 65, 140, '', '', null, '', '', '', 'S', NULL);;

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1003, 100, 'Tarefa', '', 'Registro de Erro Conhecido', 'Registro de Erro Conhecido', 'U', ' pages/problema/problema.load?alterarSituacao=N&faseAtual=RegistroErroConhecido&fase=Resolucao', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 663, 203, 65, 140, '', '', null, '', '', '', 'S', NULL);;

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1004, 100, 'Tarefa', '', 'Resoluçãoo', 'Resoluçãoo', 'U', ' pages/problema/problema.load?alterarSituacao=N&faseAtual=Resolucao', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 895, 213, 65, 140, '', '', null, '', '', '', 'S', NULL);;

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1005, 100, 'Script', '', 'Encerrar', '', '', '', '', '', '', '', '', '#{execucaoFluxo}.encerra();', '', '', 1088, 190, 65, 140, '', '', null, '', '', '', 'S', NULL);;

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1006, 100, 'Finalizacao', '', '', '', '', '', '', '', '', '', '', '', '', '', 1291, 215, 32, 32, '', '', null, '', '', '', 'S', NULL);;


insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1000,1001,100,null,null,'',1,3,138,232.25,'','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1001,1002,100,null,null,'',1,3,364,232.5,'','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1002,1003,100,null,null,'',1,3,606.5,234,'','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1003,1004,100,null,null,'',1,3,849,240.5,'','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1004,1005,100,null,null,'',1,3,1061.5,234,'','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1005,1006,100,null,null,'',1,3,1259.5,226.75,'','');

-- autor: riubbe.oliveira
-- data: 24/04/2013
create table solucaocontorno (
	idsolucaocontorno integer not null,
	titulo varchar(120) not null,
	descricao text not null,
	datahoracriacao datetime null
);


alter table solucaocontorno add primary key (idsolucaocontorno);

create table solucaodefinitiva (
	idsolucaodefinitiva int not null,
	titulo varchar(120) not null,
	descricao text not null,
	datahoracriacao datetime null
);

alter table solucaodefinitiva add primary key (idsolucaodefinitiva);

-- autor: geber.costa
-- data: 24/04/2013
-- inicio

alter table categoriaproblema add idtipofluxo int references bpm_tipofluxo(idtipofluxo);

alter table categoriaproblema add idgrupoexecutor int references grupo(idgrupo);

alter table categoriaproblema add datainicio date;

alter table categoriaproblema add datafim date;

alter table categoriaproblema add constraint fk_tipofluxo_reference_bpm_tipofluxo foreign key (idtipofluxo) references bpm_tipofluxo(idtipofluxo);

alter table categoriaproblema add constraint fk_grupoexecutor_reference_grupo foreign key (idgrupoexecutor) references grupo(idgrupo);
	
alter table categoriaproblema add nomecategoriaproblema varchar(100);

-- autor: riubbe.oliveira
-- data: 25/04/2013
alter table solucaocontorno add  idproblema integer null;
 
alter table solucaocontorno add constraint fk_solucaocontorno_problema foreign key (idproblema) references problema (idproblema);

alter table solucaodefinitiva add  idproblema integer null;

alter table solucaodefinitiva add constraint fk_solucaodefinitiva_problema foreign key(idproblema) references problema(idproblema);

-- autor:thays.araujo
-- data: 17/04/2013
alter table baseconhecimento add  erroconhecido char(1) null ;

-- autor:thays.araujo
-- data: 19/04/2013

alter table problema alter column msgerroassociada    text   ;

alter table problema alter column  solucaodefinitiva     text ;

alter table problema alter column  diagnostico      text  ;

alter table problema alter column  acoescorretas     text   ;

alter table problema alter column  acoesincorretas     text  ;

alter table problema alter column  melhoriasfuturas     text ;

alter table problema alter column  recorrenciaproblema     text  ;

alter table problema alter column  responsabilidadeterceiros     text ;

alter table problema add  faseatual varchar(100) null;

alter table categoriaproblema alter column idcategoriaproblemapai integer  null;

-- autor:thays.araujo
-- data: 23/04/2013

alter table problema add  idcausa integer null;

alter table bpm_fluxo   add constraint fk_bpm_fluxo_bpm_tipofluxo  foreign key (idtipofluxo)  references bpm_tipofluxo (idtipofluxo );

alter table bpm_elementofluxo add constraint fk_bpm_elementofluxo_bpm_fluxo foreign key (idfluxo)  references bpm_fluxo (idfluxo );

alter table bpm_sequenciafluxo add constraint fk_bpm_sequenciafluxo_bpm_fluxo foreign key (idfluxo) references bpm_fluxo (idfluxo );

alter table  problema  alter column  fechamento      text  ;

alter table  problemaitemconfiguracao  alter column  descricaoproblema    text   ;

-- autor:thays.araujo
-- data: 06/05/2013
alter table   problema  alter column  causaraiz     text ;

alter table   problema  alter column  solucaocontorno     text  ;

-- autor:thays.araujo
-- data: 09/05/2013

alter table categoriaproblema add  idtemplate int null;

alter table categoriaproblema   add constraint fk_categoriaproblema_templatesolicitacaoservico  foreign key (idtemplate )  references templatesolicitacaoservico (idtemplate ) ;

create  table validacaorequisicaoproblema 
(  idvalidacaorequisicaoproblema int not null ,    
   observacaoproblema text null ,  
   datainicio date not null ,  
   datafim date null ,  idproblema int null ,  
   primary key (idvalidacaorequisicaoproblema) ,  
   constraint fk_validacaorequisicaoproblema_problema   
   foreign key (idproblema)    
   references problema (idproblema ));
   

-- autor: riubbe.oliveira
-- data: 15/05/2013
alter table problema add  confirmasolucaocontorno varchar(1);

alter table categoriaproblema alter column nomecategoria  int null;

-- autor: thays.araujo
-- data: 15/05/2013
alter table problema add  idunidade int null;

alter table problema alter column datahoralimite datetime null;

alter table problema alter column datahoralimite  datetime ;

alter table problema add  enviaemailprazosolucionarexpirou char(1);

alter table problema alter column datahorasolicitacao datetime null;

alter table problema alter column datahorasolicitacao  datetime;

alter table problema add  fase varchar(100) null;

alter table problema alter column datahoralimite datetime null;


 DROP TRIGGER add_current_date_to_problema;
 
 
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
CREATE TABLE liberacaoproblema ( idliberacao integer, idproblema integer NOT NULL, idhistoricoliberacao integer DEFAULT NULL, PRIMARY KEY (idproblema));
-- fim

-- danillo.lisboa
-- deleta a CONSTRAINT da tabela liberacaomudanca

DECLARE @SQL VARCHAR(4000)
SET @SQL = 'alter table liberacaomudanca DROP CONSTRAINT |ConstraintName| '
SET @SQL = REPLACE(@SQL, '|ConstraintName|', ( select name from sysobjects where parent_obj = OBJECT_ID('liberacaomudanca' )))
EXEC (@SQL);

-- fim
-- danillo.lisboa
-- deleta a CONSTRAINT da tabela liberacaoproblema
DECLARE @SQL VARCHAR(4000)
SET @SQL = 'alter table liberacaomudanca DROP CONSTRAINT |ConstraintName| '
SET @SQL = REPLACE(@SQL, '|ConstraintName|', ( select name from sysobjects where parent_obj = OBJECT_ID('liberacaoproblema')))
EXEC (@SQL);
-- fim
-- danillo.lisboa
-- adiciona coluna idhistoricoliberacao a coluna liberacaomudanca
alter table liberacaomudanca add idhistoricoliberacao integer;
alter table liberacaomudanca  alter column idliberacao integer null ;

-- fim

-- CRIAÃƒÂ‡ÃƒÂƒO DA TABELA LIBERACAOMUDANCA

CREATE TABLE liberacaomudanca
(
  idliberacao integer,
  idrequisicaomudanca integer,
  idhistoricoliberacao integer
);

CREATE TABLE ocorrencialiberacao
(
  idocorrencia integer NOT NULL,
  iditemtrabalho bigint,
  idjustificativa integer,
  idrequisicaoliberacao integer,
  dataregistro date,
  horaregistro varchar(5) DEFAULT NULL,
  registradopor varchar(100) DEFAULT NULL,
  descricao varchar(200) DEFAULT NULL,
  datainicio date,
  datafim date,
  complementojustificativa text,
  dadosliberacao text,
  informacoescontato text,
  categoria varchar(20) DEFAULT NULL,
  origem character(1) DEFAULT NULL,
  tempogasto smallint,
  ocorrencia text,
  idcategoriaocorrencia integer,
  idorigemocorrencia integer,
  CONSTRAINT ocorrencialiberacao_pkey PRIMARY KEY (idocorrencia ),
  CONSTRAINT ocorrencialiberacao_iditemtrabalho_fkey FOREIGN KEY (iditemtrabalho) REFERENCES bpm_itemtrabalhofluxo (iditemtrabalho),
  CONSTRAINT ocorrencialiberacao_idjustificativa_fkey FOREIGN KEY (idjustificativa) REFERENCES justificativasolicitacao (idjustificativa)
);
  CREATE INDEX ocorrencialiberacao_iditemtrabalho_idx ON ocorrencialiberacao(iditemtrabalho );
  CREATE INDEX ocorrencialiberacao_idjustificativa_idx ON ocorrencialiberacao (idjustificativa );
  CREATE INDEX ocorrencialiberacao_idrequisicaoliberacao_idx ON ocorrencialiberacao (idrequisicaoliberacao );

  
-- MURILO ALMEIDA PACHECO 22/04/2013
-- ALTERAÇÃO DO NOME DA TABELA PARA SEGUIR O PADRÃƒO DOS OUTROS MODULOS DO SISTEMA.
-- COMENTEI POR ENQUANTO PRECISAMOS TROCAR O NOME DA TABELA DEPOIS DE ALINHAR COM O CLEISON ALGUMAS DUVIDAS NÃO APAGAR !!!!!
-- ALTER TABLE liberacao RENAME TO requisicaoliberacao;

-- MURILO ALMEIDA PACHECO 25/04/2013
-- ALTERAÇÃO DO TIPO DE DADOS DA TABELA DE BYTEA PARA TEXT QUE SEJA COMPATIVEL COM O TIPO BLOB DO MYSQL
ALTER TABLE controleged ALTER COLUMN conteudoarquivo varbinary(max);

-- MURILO ALMEIDA PACHECO
-- CRIAÇÃO DA TABELA DE HISTORICOS DE ALTERAÇÕES DAS LIBERAÇÕES
CREATE TABLE historicoliberacao (
  idhistoricoliberacao integer NOT NULL,
  idexecutormodificacao integer NOT NULL,
  datahoraModificacao datetime NOT NULL DEFAULT sysdatetime(),
  tipoModificacao varchar(1) DEFAULT NULL,
  historicoVersao double precision DEFAULT NULL,
  idliberacao integer NOT NULL,
  idsolicitante integer NOT NULL,
  idresponsavel integer DEFAULT NULL,
  titulo varchar(100) NOT NULL,
  descricao text NOT NULL,
  datainicial date NOT NULL,
  datafinal date NOT NULL,
  dataliberacao date DEFAULT NULL,
  situacao char(1) NOT NULL,
  risco char(1) NOT NULL,
  versao varchar(25) DEFAULT NULL,
  seqreabertura smallint DEFAULT NULL,
  enviaemailcriacao varchar(1) DEFAULT NULL,
  enviaemailacoes varchar(1) DEFAULT NULL,
  tempoatrasohh smallint DEFAULT NULL,
  tempoatrasomm smallint DEFAULT NULL,
  tempocapturahh smallint DEFAULT NULL,
  tempocapturamm smallint DEFAULT NULL,
  datahoratermino datetime NOT NULL DEFAULT sysdatetime(),
  datahoraconclusao datetime NOT NULL DEFAULT sysdatetime(),
  status varchar(45) DEFAULT NULL,
  tempodecorridohh smallint DEFAULT NULL,
  tempodecorridomm smallint DEFAULT NULL,
  tempoatendimentohh smallint DEFAULT NULL,
  tempoatendimentomm smallint DEFAULT NULL,
  datahoracaptura datetime NULL DEFAULT sysdatetime(),
  datahorareativacao datetime NULL DEFAULT sysdatetime(),
  datahorainicio datetime NOT NULL DEFAULT sysdatetime(),
  idcalendario integer DEFAULT NULL,
  datahorasuspensao datetime NULL DEFAULT sysdatetime(),
  enviaemailfinalizacao varchar(1) DEFAULT NULL,
  prazohh smallint DEFAULT NULL,
  prazomm smallint DEFAULT NULL,
  idproprietario integer NOT NULL,
  datahorainicioagendada datetime NULL DEFAULT sysdatetime(),
  datahoraterminoagendada datetime NULL DEFAULT sysdatetime(),
  idtipoliberacao integer DEFAULT NULL,
  idGrupoAtual integer DEFAULT NULL,
  idcontatorequisicaoliberacao integer,
  telefonecontato varchar(45),
  ramal varchar(5),
  observacao text,
  idunidade integer,
  nomecontato2 varchar(80),
  emailcontato varchar(200),
  Idlocalidade integer,
  CONSTRAINT historicoliberacao_pkey PRIMARY KEY (idhistoricoliberacao)
);
-- adiciona a coluna baseline na tabela historicoliberacao
ALTER TABLE historicoliberacao ADD baseline varchar(30);
alter table historicoliberacao add  alterarsituacao varchar(1);
alter table historicoliberacao add  acaoFluxo varchar(1);
alter table historicoliberacao alter column datahoraconclusao datetime null;

-- criação da tabela requisicaoliberacaoitemconfiguracao para registrar os ics da liberacao.
CREATE TABLE requisicaoliberacaoitemconfiguracao (
  idrequisicaoliberacaoitemconfiguracao integer NOT NULL,
  idrequisicaoliberacao integer DEFAULT NULL,
  iditemconfiguracao integer DEFAULT NULL,
  descricao varchar(100) DEFAULT NULL,
  idhistoricoliberacao integer DEFAULT NULL, 
  CONSTRAINT requisicaoliberacaoic_pkey PRIMARY KEY (idrequisicaoliberacaoitemconfiguracao)
);

-- CRIAÇÃO TABELA CONHECIMENTOLIBERACAO
CREATE TABLE CONHECIMENTOLIBERACAO (
  IDREQUISICAOLIBERACAO INTEGER NOT NULL,
  IDBASECONHECIMENTO INTEGER NOT NULL,
  PRIMARY KEY (IDREQUISICAOLIBERACAO,IDBASECONHECIMENTO),
  CONSTRAINT FK_REF_CONHLIB_LIB FOREIGN KEY (IDREQUISICAOLIBERACAO) REFERENCES LIBERACAO (IDLIBERACAO),
  CONSTRAINT FK_REF_CONHLIB_BAS FOREIGN KEY (IDBASECONHECIMENTO) REFERENCES BASECONHECIMENTO (IDBASECONHECIMENTO)
);

-- MAYCON 

-- =======TIPO LIBERACAO======
CREATE TABLE tipoliberacao
(
  idtipoliberacao integer NOT NULL,
  idtipofluxo integer,
  idmodeloemailcriacao integer,
  idmodeloemailfinalizacao integer,
  idmodeloemailacoes integer,
  idgrupoexecutor integer,
  idcalendario integer,
  nometipoliberacao varchar(100) DEFAULT NULL,
  datainicio date,
  datafim date,
  CONSTRAINT tipoliberacao_pkey PRIMARY KEY (idtipoliberacao),
  CONSTRAINT tipoliberacao_idcalendario_fkey FOREIGN KEY (idcalendario)
      REFERENCES calendario (idcalendario) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idcalendario_fkey1 FOREIGN KEY (idcalendario)
      REFERENCES calendario (idcalendario) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idcalendario_fkey2 FOREIGN KEY (idcalendario)
      REFERENCES calendario (idcalendario) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idcalendario_fkey3 FOREIGN KEY (idcalendario)
      REFERENCES calendario (idcalendario) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idcalendario_fkey4 FOREIGN KEY (idcalendario)
      REFERENCES calendario (idcalendario) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idgrupoexecutor_fkey FOREIGN KEY (idgrupoexecutor)
      REFERENCES grupo (idgrupo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idgrupoexecutor_fkey1 FOREIGN KEY (idgrupoexecutor)
      REFERENCES grupo (idgrupo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idgrupoexecutor_fkey2 FOREIGN KEY (idgrupoexecutor)
      REFERENCES grupo (idgrupo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idgrupoexecutor_fkey3 FOREIGN KEY (idgrupoexecutor)
      REFERENCES grupo (idgrupo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idgrupoexecutor_fkey4 FOREIGN KEY (idgrupoexecutor)
      REFERENCES grupo (idgrupo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailacoes_fkey FOREIGN KEY (idmodeloemailacoes)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailacoes_fkey1 FOREIGN KEY (idmodeloemailacoes)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailacoes_fkey2 FOREIGN KEY (idmodeloemailacoes)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailacoes_fkey3 FOREIGN KEY (idmodeloemailacoes)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailcriacao_fkey FOREIGN KEY (idmodeloemailcriacao)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailcriacao_fkey1 FOREIGN KEY (idmodeloemailcriacao)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailcriacao_fkey2 FOREIGN KEY (idmodeloemailcriacao)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailcriacao_fkey3 FOREIGN KEY (idmodeloemailcriacao)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailcriacao_fkey4 FOREIGN KEY (idmodeloemailcriacao)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailfinalizacao_fkey FOREIGN KEY (idmodeloemailfinalizacao)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailfinalizacao_fkey1 FOREIGN KEY (idmodeloemailfinalizacao)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailfinalizacao_fkey2 FOREIGN KEY (idmodeloemailfinalizacao)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailfinalizacao_fkey3 FOREIGN KEY (idmodeloemailfinalizacao)
      REFERENCES modelosemails (idmodeloemail) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idtipofluxo_fkey FOREIGN KEY (idtipofluxo)
      REFERENCES bpm_tipofluxo (idtipofluxo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idtipofluxo_fkey1 FOREIGN KEY (idtipofluxo)
      REFERENCES bpm_tipofluxo (idtipofluxo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idtipofluxo_fkey2 FOREIGN KEY (idtipofluxo)
      REFERENCES bpm_tipofluxo (idtipofluxo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idtipofluxo_fkey3 FOREIGN KEY (idtipofluxo)
      REFERENCES bpm_tipofluxo (idtipofluxo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idtipofluxo_fkey4 FOREIGN KEY (idtipofluxo)
      REFERENCES bpm_tipofluxo (idtipofluxo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX tipoliberacao_idcalendario_idx   ON tipoliberacao(idcalendario);
CREATE INDEX tipoliberacao_idgrupoexecutor_idx ON tipoliberacao(idgrupoexecutor);
CREATE INDEX tipoliberacao_idmodeloemailacoes_idx ON tipoliberacao(idmodeloemailacoes);
CREATE INDEX tipoliberacao_idmodeloemailcriacao_idx ON tipoliberacao(idmodeloemailcriacao);
CREATE INDEX tipoliberacao_idmodeloemailfinalizacao_idx ON tipoliberacao(idmodeloemailfinalizacao);
CREATE INDEX tipoliberacao_idtipofluxo_idx ON tipoliberacao(idtipofluxo);

-- ============EXECUCAO LIBERACAO===========
CREATE TABLE execucaoliberacao
(
  idexecucao integer NOT NULL,
  idinstanciafluxo bigint NOT NULL,
  idliberacao integer NOT NULL,
  idfluxo bigint NOT NULL,
  seqreabertura smallint,
  CONSTRAINT execucaoliberacao_pkey PRIMARY KEY (idexecucao),
  CONSTRAINT execucaoliberacao_idfluxo_fkey FOREIGN KEY (idfluxo)
      REFERENCES bpm_fluxo (idfluxo) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT execucaoliberacao_idinstanciafluxo_fkey FOREIGN KEY (idinstanciafluxo)
      REFERENCES bpm_instanciafluxo (idinstancia) 
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX execucaoliberacao_idfluxo_idx ON execucaoliberacao(idfluxo);
CREATE INDEX execucaoliberacao_idinstanciafluxo_idx ON execucaoliberacao(idinstanciafluxo);
CREATE INDEX execucaoliberacao_idrequisicaoliberacao_idx ON execucaoliberacao(idliberacao);
  
-- ============JUSTIFICATIVA LIBERACAO===========
create table justificativaliberacao (
   idjustificativaliberacao int not null,
   descricaojustificativa varchar(100) not null,
   suspensao char(1) not null,
   situacao char(1) not null,
   aprovacao char(1) default null,
   deleted char(1) default null,
   primary key (idjustificativaliberacao)
);
  
-- ============LIBERACAO=========== 
alter table liberacao add seqreabertura smallint;
alter table liberacao add enviaemailcriacao varchar(1) DEFAULT NULL;
alter table liberacao add enviaemailacoes varchar(1) DEFAULT NULL;
alter table liberacao add tempoatrasohh smallint;
alter table liberacao add tempoatrasomm smallint;
alter table liberacao add tempocapturahh smallint;
alter table liberacao add tempocapturamm smallint;
alter table liberacao add datahoratermino datetime DEFAULT '1970-01-01 00:00:00';
alter table liberacao add datahoraconclusao datetime  DEFAULT '1970-01-01 00:00:00';
alter table liberacao add status varchar(45) DEFAULT NULL;
alter table liberacao add tempodecorridohh smallint;
alter table liberacao add tempodecorridomm smallint;
alter table liberacao add tempoatendimentohh smallint;
alter table liberacao add tempoatendimentomm smallint;
alter table liberacao add datahoracaptura datetime DEFAULT '1970-01-01 00:00:00';
alter table liberacao add datahorareativacao datetime DEFAULT '1970-01-01 00:00:00';
alter table liberacao add datahorainicio datetime DEFAULT '1970-01-01 00:00:00';
alter table liberacao add idcalendario integer;
alter table liberacao add datahorasuspensao datetime DEFAULT '1970-01-01 00:00:00';
alter table liberacao add enviaemailfinalizacao varchar(1) DEFAULT NULL;
alter table liberacao add prazohh smallint;
alter table liberacao add prazomm smallint;
alter table liberacao add idproprietario integer NOT NULL;
alter table liberacao add datahorainicioagendada datetime DEFAULT '1970-01-01 00:00:00';
alter table liberacao add datahoraterminoagendada datetime DEFAULT '1970-01-01 00:00:00';
alter table liberacao add idtipoliberacao integer;
alter table liberacao add 	idGrupoAtual integer;
alter table liberacao add 	prioridade integer DEFAULT NULL;
alter table liberacao add 	nivelurgencia varchar(255) DEFAULT NULL;
alter table liberacao add 	nivelimpacto varchar(255) DEFAULT NULL;
alter table liberacao add idaprovador integer  DEFAULT NULL;
alter table liberacao add 	datahoraaprovacao datetime DEFAULT '1970-01-01 00:00:00';
alter table liberacao add fechamento text;
alter table liberacao add idgruponivel1 integer;
alter table liberacao add idGrupoAprovador integer;

--alter table liberacao alter column situacao drop not null;
alter table liberacao alter column situacao char(1) null;

-- BPM TIPO FLUXO
INSERT INTO bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) VALUES (54, 'LiberacaoDeploy', 'Liberação Deploy', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoLiberacao');

-- BPM FLUXO
INSERT INTO bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) VALUES (50, '1.0', 54, 'requisicaoLiberacao;requisicaoLiberacao.status;requisicaoLiberacao.nomeGrupoAtual', '', '2013-05-18', NULL);;

-- BPM ELEMENTO FLUXO
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (592,50,'Inicio','','','','','','','','','','','','','',160,73,32,32,'','',NULL,'','','','S', NULL);;
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (593,50,'Tarefa','','Liberar','Liberar','U','pages/requisicaoLiberacao/requisicaoLiberacao.load?escalar=S&alterarSituacao=S','','#{requisicaoLiberacao.nomeGrupoAtual}','','','','','','',324,59,65,140,'','',NULL,'','','','S', NULL);;
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (594,50,'Tarefa','','Em Execução ','Em Execução','U','pages/requisicaoLiberacao/requisicaoLiberacao.load?escalar=S&alterarSituacao=S','','#{requisicaoLiberacao.nomeGrupoAtual}','','','','','','',753,28,65,140,'','',NULL,'','','','S', NULL);;
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (595,50,'Script','','Encerrar','','','','','','','','','#{execucaoFluxo}.encerra();','','',519,203,65,140,'','',NULL,'','','','S', NULL);;
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (596,50,'Porta','','','','','','','','','','','','','',569,333,42,42,'','',NULL,'','','','S', NULL);;
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (597,50,'Porta','','','','','','','','','','','','','',803,233,42,42,'','',NULL,'','','','S', NULL);;
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (598,50,'Porta','','','','','','','','','','','','','',568,71,42,42,'','',NULL,'','','','S', NULL);;
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (599,50,'Finalizacao','','','','','','','','','','','','','',237,219,32,32,'','',NULL,'','','','S', NULL);;

-- BPM SEQUENCIA FLUXO
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (592,593,50,'','','',1,3,258,90.25,'','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (593,598,50,'','','',1,3,516,91.75,'','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (594,597,50,'','','',2,0,823.5,163,'','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (596,593,50,'','','!#{requisicaoLiberacao}.atendida();',3,2,391,353,'S','Não Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (597,596,50,'','','#{requisicaoLiberacao}.liberada();',2,1,822,353,'S','Liberada');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (598,594,50,'','','!#{requisicaoLiberacao}.atendida();',0,3,665,59.75,'','Não Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (597,598,50,'','','#{requisicaoLiberacao}.emAtendimento();',3,1,706.5,173,'','Em Atendimento');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (595,599,50,'','','',3,1,394,235.25,'','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (598,595,50,'','','#{requisicaoLiberacao}.atendida();',2,0,589,158,'','Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (596,595,50,'','','#{requisicaoLiberacao}.atendida();',0,2,589.5,300.5,'','Resolvida');

-- TIPO LIBERACAO
INSERT INTO tipoliberacao (idtipoliberacao, idtipofluxo, idmodeloemailcriacao, idmodeloemailfinalizacao, idmodeloemailacoes, idgrupoexecutor, idcalendario, nometipoliberacao, datainicio, datafim) VALUES (1,54,51,52,53,1,1,'Requisição Liberaçãoo Normal','2013-05-20',NULL);;

-- fim modelos emails

-- EDMAR FAGUNDES - 16/05/2013 {
-- ADICIONA TABELA DE APROVAÇÃO PARA AUXILIAR NA PARTE DE PESQUISA
CREATE TABLE aprovacaorequisicaoliberacao
(
  idaprovacaorequisicaoliberacao integer NOT NULL,
  idrequisicaoliberacao bigint NOT NULL,
  idtarefa bigint,
  idresponsavel integer NOT NULL,
  datahora datetime,
  idjustificativa integer,
  complementojustificativa text,
  observacoes text,
  aprovacao char(1) NOT NULL,
  CONSTRAINT idaprovacaorequisicao PRIMARY KEY (idaprovacaorequisicaoliberacao)
);

-- ADICIONA TABELA DE CONTATO PARA SALVAR OS DADOS DO CONTATO AO CRIAR UMA NOVA LIBERAÇÃO
CREATE TABLE contatorequisicaoliberacao
(
  idcontatorequisicaoliberacao integer NOT NULL,
  nomecontato varchar(70),
  telefonecontato varchar(20),
  emailcontato varchar(120),
  observacao text,
  idlocalidade integer,
  ramal varchar(4),
  idunidade integer,
  CONSTRAINT contatorequisicaoliberacao_pkey PRIMARY KEY (idcontatorequisicaoliberacao)
);
-- ADICIONA COLUNA IDCONTATOREQUISICAOLIBERACAO PARA FAZER A CONEXAO COM A TABELA CONTATOREQUISICAOLIBERACAO
ALTER TABLE liberacao ADD idcontatorequisicaoliberacao integer;
-- ADICIONA COLUNA IDULTIMAAPROVACAO PARA FAZER A CONEXAO COM A TABELA APROVACAOREQUISICAOLIBERACAO
ALTER TABLE liberacao ADD idultimaaprovacao integer;
-- }

-- FIM - MODULO DE LIBERACAO

CREATE TABLE tiposubscricao
(
  idtiposubscricao integer NOT NULL,
  nometiposubscricao varchar(250),
  CONSTRAINT tiposubscricao_pk PRIMARY KEY (idtiposubscricao)
);

CREATE TABLE controlecontrato
(
  idcontrolecontrato integer NOT NULL,
  idcontrato integer,
  numerosubscricao varchar(255),
  endereco varchar(255),
  contato varchar(255),
  email varchar(255),
  telefone1 varchar(255),
  telefone2 varchar(255),
  tiposubscricao integer,
  url varchar(255),
  login varchar(255),
  senha varchar(255),
  datainicio varchar(255),
  datafim varchar(255),
  cliente varchar(255),
  CONSTRAINT pk_controlecontrato PRIMARY KEY (idcontrolecontrato),
  CONSTRAINT fk_contrato FOREIGN KEY (idcontrato)
      REFERENCES contratos (idcontrato),
  CONSTRAINT fk_tiposubscricao FOREIGN KEY (tiposubscricao)
      REFERENCES tiposubscricao (idtiposubscricao) 
);

CREATE TABLE controlecontratoocorrencia
(
  idccocorrencia integer NOT NULL,
  assuntoccocorrencia varchar(255),
  idempregadoocorrencia integer,
  idcontrolecontrato integer,
  dataccocorrencia date,
  CONSTRAINT pk_itemcontrolecontratoocorrencia PRIMARY KEY (idccocorrencia),
  CONSTRAINT fk_controlecontrato_ocorrencia FOREIGN KEY (idcontrolecontrato)
      REFERENCES controlecontrato (idcontrolecontrato) ,
  CONSTRAINT fk_idusuarioempregado FOREIGN KEY (idempregadoocorrencia)
      REFERENCES empregados (idempregado) 
);

CREATE TABLE controlecontratopagamento
(
  idccpagamento integer NOT NULL,
  parcelaccpagamento integer,
  idcontrolecontrato integer,
  dataatrasoccpagamento date,
  dataccpagamento date,
  CONSTRAINT pk_ccpagamento PRIMARY KEY (idccpagamento),
  CONSTRAINT fk_controlecontrato_pagamento FOREIGN KEY (idcontrolecontrato)
      REFERENCES controlecontrato (idcontrolecontrato) 
);

CREATE TABLE controlecontratotreinamento
(
  idcctreinamento integer NOT NULL,
  idcontrolecontrato integer,
  idempregadotreinamento integer,
  nomecctreinamento varchar(255),
  datacctreinamento date,
  CONSTRAINT pk_itemcontrolecontratotreinamento PRIMARY KEY (idcctreinamento),
  CONSTRAINT fk_controlecontrato_treinamento FOREIGN KEY (idcontrolecontrato)
      REFERENCES controlecontrato (idcontrolecontrato) ,
  CONSTRAINT fk_empregadotreinamento FOREIGN KEY (idempregadotreinamento)
      REFERENCES empregados (idempregado) 
);

CREATE TABLE modulosistema
(
  idmodulosistema integer NOT NULL,
  nomemodulosistema varchar(255),
  CONSTRAINT pk_idmodulosistema PRIMARY KEY (idmodulosistema)
);

CREATE TABLE controlecontratoversao
(
  idcontrolecontrato integer,
  idccversao integer NOT NULL,
  nomeccversao varchar(255),
  CONSTRAINT pk_versao PRIMARY KEY (idccversao),
  CONSTRAINT fk_controlecontrato_versao FOREIGN KEY (idcontrolecontrato)
      REFERENCES controlecontrato (idcontrolecontrato) 
);

CREATE TABLE ccmodulosistema
(
  idmodulosistema integer,
  idcontrolecontrato integer,
  CONSTRAINT fk_controlecontrato FOREIGN KEY (idcontrolecontrato)
      REFERENCES controlecontrato (idcontrolecontrato) ,
  CONSTRAINT fk_modulosistema FOREIGN KEY (idmodulosistema)
      REFERENCES modulosistema (idmodulosistema) 
);



-- Inserts
INSERT INTO MODULOSISTEMA VALUES(1, 'INCIDENTE');
INSERT INTO MODULOSISTEMA VALUES(2, 'REQUISIÇÃƒO');
INSERT INTO MODULOSISTEMA VALUES(3, 'MUDANÇA');
INSERT INTO MODULOSISTEMA VALUES(4, 'EVENTO');
INSERT INTO MODULOSISTEMA VALUES(5, 'CONTINUIDADE');
INSERT INTO MODULOSISTEMA VALUES(6, 'CONHECIMENTO');
INSERT INTO MODULOSISTEMA VALUES(7, 'SLA');
INSERT INTO MODULOSISTEMA VALUES(8, 'PROBLEMA');
INSERT INTO MODULOSISTEMA VALUES(9, 'CAPACIDADE');
INSERT INTO MODULOSISTEMA VALUES(10, 'PORTIFÓ“LIO');
INSERT INTO MODULOSISTEMA VALUES(11, 'ATIVOS E CONFIG.');
INSERT INTO MODULOSISTEMA VALUES(12, 'GERENCIAMENTO DE SERVIÇOS');
INSERT INTO MODULOSISTEMA VALUES(13, 'LIBERAÇÃO');
INSERT INTO MODULOSISTEMA VALUES(14, 'DISPONIBILIDADE');

-- INSERT CATEGORIA IMAGEM
INSERT INTO categoriagaleriaimagem VALUES(1, 'EMAIL', null, CURRENT_TIMESTAMP);
-- FIM

-- autor: Carlos Santos
-- Data: 16/05/2013

CREATE TABLE solicitacaoservicoquestionario (
  idsolicitacaoquestionario integer NOT NULL,
  idquestionario integer NOT NULL,
  idsolicitacaoservico integer NOT NULL,
  dataquestionario datetime NOT NULL,
  idresponsavel integer NOT NULL,
  idtarefa integer DEFAULT NULL,
  aba varchar(100) DEFAULT NULL,
  situacao char(1) NOT NULL,
  datahoragrav datetime NOT NULL,
  conteudoimpresso text
);
-- aqui
ALTER TABLE  solicitacaoservicoquestionario
  ADD CONSTRAINT pk_solicitacaoservquest primary key (idsolicitacaoquestionario);
  
ALTER TABLE  solicitacaoservicoquestionario
  ADD CONSTRAINT fk_solquest_tarefa FOREIGN KEY (idtarefa) REFERENCES bpm_itemtrabalhofluxo (iditemtrabalho);

ALTER TABLE  solicitacaoservicoquestionario
  ADD CONSTRAINT fk_solquest_empregado FOREIGN KEY (idresponsavel) REFERENCES empregados (idempregado);

ALTER TABLE  solicitacaoservicoquestionario
  ADD CONSTRAINT fk_solquest_questionario FOREIGN KEY (idquestionario) REFERENCES questionario (idquestionario);

ALTER TABLE  solicitacaoservicoquestionario
  ADD CONSTRAINT fk_solquest_solicitacao FOREIGN KEY (idsolicitacaoservico) REFERENCES solicitacaoservico (idsolicitacaoservico);



drop index ix_idquestaoorigem;

drop index ix_sigla_questao;


drop index ix_idquestao;

drop index ix_ident_questao;


create table categoriaquestionario (
   idcategoriaquestionario integer not null,
   nomecategoriaquestionario varchar(50) not null
);

alter table categoriaquestionario
  add constraint pk_categoriaquestionario primary key (idcategoriaquestionario);
  
create table grupoquestionario (
   idgrupoquestionario  integer not null,
   idquestionario       integer not null,
   nomegrupoquestionario varchar(80) not null,
   ordem                integer null
);

alter table grupoquestionario
   add constraint pk_grupoquestionario primary key (idgrupoquestionario);
   
create table opcaorespostaquestionario (
   idopcaorespostaquestionario integer not null,
   idquestaoquestionario integer not null,
   titulo                varchar(255) not null,
   peso                  integer      null,
   valor                 varchar(50)  null,
   geraalerta            char(1)      null,
   exibecomplemento      char(1)      null,
   idquestaocomplemento  integer      null
);

alter table opcaorespostaquestionario
   add constraint pk_opcaorespostaquestionario primary key (idopcaorespostaquestionario);

create table questaoquestionario (
   idquestaoquestionario 		integer not null,
   idgrupoquestionario  		integer null,
   idquestaoagrupadora  		integer null,
   idquestaoorigem      		integer null,
   tipo                 		char(1) not null,
   tituloquestaoquestionario 	text    not null,
   tipoquestao          		char(1) not null,
   sequenciaquestao     		integer not null,
   valordefault         		text    null,
   textoinicial         		text    null,
   tamanho             			integer null,
   decimais             		integer null,
   inforesposta         		char(1) null 
      constraint ckc_inforesposta_questaoq check (inforesposta is null or (inforesposta in ('L','B'))),
   valoresreferencia    		text    null,
   unidade              		text    null,
   obrigatoria         			char(1) not null 
      constraint ckc_obrigatoria_questaoq check (obrigatoria in ('S','')),
   ponderada            		char(1) null,
   qtdelinhas           		integer null,
   qtdecolunas          		integer null,
   cabecalholinhas      		char(1) null,
   cabecalhocolunas     		char(1) null,
   nomelistagem         		varchar(30) null,
   ultimovalor          		char(1) null,
   idsubquestionario    		integer  null,
   abaresultsubform     		varchar(200) null,
   sigla                		varchar(100) null,
   imprime              		char(1) null,
   calculada            		char(1) null,
   editavel             		char(1) null,
   valorpermitido1      		numeric(15,5) null,
   valorpermitido2      		numeric(15,5) null,
   idimagem             		integer       null
);

alter table questaoquestionario
   add constraint pk_questaoquestionario primary key (idquestaoquestionario);

create  index ix_sigla_questao
 on questaoquestionario (sigla);

create  index ix_idquestaoorigem 
 on questaoquestionario (idquestaoorigem);

create table questionario (
   idquestionario			integer		not null,
   idquestionarioorigem		integer		null,
   idcategoriaquestionario	integer		not null,
   nomequestionario			varchar(50) not null,
   idempresa				integer     not null,
   ativo					char(1)     not null default 'S',
   javascript				text        null
);

alter table questionario
   add constraint pk_questionario primary key (idquestionario);

create table respostaitemquestionario (
   idrespostaitemquestionario	integer not null,
   ididentificadorresposta		integer not null,
   idquestaoquestionario		integer not null,
   sequencialresposta			integer null,
   respostatextual				text null,
   respostapercentual			numeric(15,5) null,
   respostavalor				numeric(15,5) null,
   respostavalor2				numeric(15,5) null,
   respostanumero				numeric(8,0)  null,
   respostanumero2				numeric(8,0)  null,
   respostadata					date          null,
   respostahora					varchar(4)    null,
   respostames					integer       null,
   respostaano					integer       null,
   respostaidlistagem			varchar(10)   null,
   respostadia					integer       null
);

alter table respostaitemquestionario
   add constraint pk_respostaitemquestionario primary key (idrespostaitemquestionario);

create  index ix_ident_questao 
 on respostaitemquestionario (ididentificadorresposta, idquestaoquestionario);

create  index ix_idquestao 
 on respostaitemquestionario (idquestaoquestionario);

create table respostaitemquestionarioanexos (
   idrespostaitmquestionarioanexo	integer      not null,
   idrespostaitemquestionario		integer      not null,
   caminhoanexo						varchar(255) not null,
   observacao						text         null
);

alter table respostaitemquestionarioanexos
   add constraint pk_respostaitemquestionarioane primary key (idrespostaitmquestionarioanexo);

create table respostaitemquestionarioopcoes (
	idrespostaitemquestionario integer  not null,
	idopcaorespostaquestionario integer not null
);

alter table respostaitemquestionarioopcoes
   add constraint pk_respostaitemquestionarioopc primary key (idrespostaitemquestionario, idopcaorespostaquestionario);

alter table grupoquestionario
   add constraint fk_grupoque_reference_question foreign key (idquestionario)
      references questionario (idquestionario)
      on delete no action on update no action;

alter table opcaorespostaquestionario
   add constraint fk_opcaores_reference_questaoq foreign key (idquestaocomplemento)
      references questaoquestionario (idquestaoquestionario)
      on delete no action on update no action;

alter table opcaorespostaquestionario
   add constraint fk_opcaores_ref_questaoq foreign key (idquestaoquestionario)
      references questaoquestionario (idquestaoquestionario)
      on delete cascade on update no action;

alter table questaoquestionario
   add constraint fk_questaoagrupadora foreign key (idquestaoagrupadora)
      references questaoquestionario (idquestaoquestionario)
      on delete no action on update no action;

alter table questaoquestionario
   add constraint fk_questaoq_reference_question foreign key (idsubquestionario)
      references questionario (idquestionario)
      on delete no action on update no action;

alter table questaoquestionario
   add constraint fk_questaoq_reference_grupoque foreign key (idgrupoquestionario)
      references grupoquestionario (idgrupoquestionario)
      on delete no action on update no action;

alter table questionario
   add constraint fk_question_reference_categori foreign key (idcategoriaquestionario)
      references categoriaquestionario (idcategoriaquestionario)
      on delete no action on update no action;

alter table questionario
   add constraint fk_questionarioorigem foreign key (idquestionarioorigem)
      references questionario (idquestionario)
      on delete no action on update no action;

alter table respostaitemquestionario
   add constraint fk_resposta_reference_questaoq foreign key (idquestaoquestionario)
      references questaoquestionario (idquestaoquestionario)
      on delete no action on update no action;

alter table respostaitemquestionarioanexos
   add constraint fk_rspt_anx_reference_rspt_itm foreign key (idrespostaitemquestionario)
      references respostaitemquestionario (idrespostaitemquestionario)
      on delete cascade on update no action;

alter table respostaitemquestionarioopcoes
   add constraint fk_rspta_q_reference_rspta_itm foreign key (idrespostaitemquestionario)
      references respostaitemquestionario (idrespostaitemquestionario)
      on delete cascade on update no action;

alter table respostaitemquestionarioopcoes
   add constraint fk_resposta_reference_opcaores foreign key (idopcaorespostaquestionario)
      references opcaorespostaquestionario (idopcaorespostaquestionario)
      on delete no action on update no action;

ALTER TABLE templatesolicitacaoservico ADD idquestionario integer NULL;
alter table templatesolicitacaoservico
   add constraint fk_questionario foreign key (idquestionario)
      references questionario (idquestionarioorigem);
 
ALTER TABLE templatesolicitacaoservico ALTER COLUMN nomeclassedto varchar(255)		NULL;
ALTER TABLE templatesolicitacaoservico ALTER COLUMN nomeclasseaction varchar(255)	NULL;
ALTER TABLE templatesolicitacaoservico ALTER COLUMN nomeclasseservico varchar(255)	NULL;
ALTER TABLE templatesolicitacaoservico ALTER COLUMN urlrecuperacao varchar(255)		NULL;

-- autor:thays.araujo
-- data: 20/05/2013
alter table problema add idcategoriasolucao integer ;
alter table problema add precisasolucaocontorno char(1);
alter table problema add resolvido char(1) ;
 
-- Scripts do módulo de mudanÃ§a

-- INICIO - MODULO DE MUDANCA

alter table APROVACAOMUDANCA add dataHoraVotacao varchar(25);
alter table requisicaomudanca add razaomudanca varchar(200);

CREATE TABLE risco
(
  idrisco integer NOT NULL,
  nomerisco varchar(150) NOT NULL,
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
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX requisicaomudancarisco_idrequisicaomudanca_idx
  ON requisicaomudancarisco (idrequisicaomudanca);

CREATE INDEX requisicaomudancarisco_idrisco_idx
  ON requisicaomudancarisco (idrisco);

  
  CREATE INDEX requisicaomudancarisco_idrequisicaomudanca_idx ON requisicaomudancarisco(idrequisicaomudanca);

CREATE INDEX requisicaomudancarisco_idrisco_idx ON requisicaomudancarisco(idrisco);
   
CREATE TABLE requisicaomudancaliberacao
(
  idrequisicaomudancaliberacao integer NOT NULL,
  idrequisicaomudanca integer NOT NULL,
  idliberacao int NOT NULL,
  CONSTRAINT requisicaomudancaliberacao_pkey PRIMARY KEY (idrequisicaomudancaliberacao),
  CONSTRAINT requisicaomudancaliberacao_idrequisicaomudanca_fkey FOREIGN KEY (idrequisicaomudanca)
      REFERENCES requisicaomudanca (idrequisicaomudanca) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT requisicaomudancaliberacao_idliberacao_fkey FOREIGN KEY (idliberacao)
      REFERENCES liberacao (idliberacao)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE INDEX requisicaomudancaliberacao_idrequisicaomudanca_idx  ON requisicaomudancaliberacao(idrequisicaomudanca);
  
  
ALTER TABLE requisicaomudanca ALTER COLUMN DATAHORACONCLUSAO datetime NULL;
  
   CREATE TABLE reuniaorequisicaomudanca
(
  idreuniaorequisicaomudanca integer NOT NULL,
  idrequisicaomudanca integer NOT NULL,
  localreuniao character varying(255),
  horaInicio character varying(255),
  criadopor character varying(255),
  descricao character varying(255),
  duracaoestimada integer,
  datacriacao date,
  datainicio date,
  status character varying(30),
  CONSTRAINT reuniaorequisicaomudanca_pkey PRIMARY KEY (idreuniaorequisicaomudanca),
  CONSTRAINT reuniaorequisicaomudanca_idreuniaorequisicaomudanca_fkey FOREIGN KEY (idrequisicaomudanca)
      REFERENCES requisicaomudanca (idrequisicaomudanca)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- tabela netmap

ALTER TABLE netmap ADD dataInventario date;

-- Flávio
CREATE TABLE midiasoftwarechave
(
   "idmidiasoftwarechave" integer, 
   "idmidiasoftware" integer, 
   chave character varying(255)
);

exec sp_RENAME 'netmap.date', 'date_';

ALTER TABLE tipoitemconfiguracao ADD imagem character varying(255);

-- alterar tabela parametrocorpore
ALTER TABLE parametrocorpore ALTER COLUMN valor varchar(200) NOT NULL;

-- INICIO - MURILO GABRIEL RODRIGUES - 27/05/2013

alter table fluxoservico add idfluxoservico bigint not null;
alter table fluxoservico DROP CONSTRAINT [PK__fluxoser__36C4B2C235F2D38B];
alter table fluxoservico add constraint fluxoservico_pkey primary key(idfluxoservico);

alter table fluxoservico alter column idservicocontrato bigint not null;
alter table fluxoservico add constraint tipofluxo_fk foreign key (idtipofluxo) references bpm_tipofluxo (idtipofluxo);
alter table fluxoservico add constraint faseservico_fk foreign key (idfase) references faseservico (idfase);

-- FIM - MURILO GABRIEL RODRIGUES - 27/05/2013

-- Tabela LimiteAlcada

EXEC sp_rename 'limitealcada.limitevalormensal', 'limitemensalusointerno', 'COLUMN';
EXEC sp_rename 'limitealcada.limitevaloritem', 'limiteitemusointerno', 'COLUMN';

-- Fim tabela LimiteAlcada
-- autor:thays.araujo
-- data : 04/06/2013
alter table problema drop constraint DF__problema__dataho__32375140;

alter table problema alter column  datahorafim datetime null;

-- INICIO - MURILO GABRIEL RODRIGUES - 05/06/2013
ALTER TABLE versao ADD idusuario INT;
-- FIM - MURILO GABRIEL RODRIGUES - 05/06/2013

-- Flávio Júnior - 06/06/2013
ALTER TABLE midiasoftwarechave ADD qtdpermissoes integer;

ALTER TABLE itemconfiguracao ADD dtultimacaptura datetime;

ALTER TABLE historicoic ADD dtultimacaptura datetime;

--Módulo Mudança
ALTER TABLE requisicaomudanca ADD ehpropostaaux char(1);
ALTER TABLE requisicaomudanca ADD VOTACAOPROPOSTAAPROVADAAUX char(1);

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

-- fim módulo mudança

-- Fim
