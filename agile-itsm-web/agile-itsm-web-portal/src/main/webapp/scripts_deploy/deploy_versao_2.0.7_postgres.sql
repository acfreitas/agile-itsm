-- PostgreSQL

-- autor: geber
-- data: 12/04/2013
create table ocorrenciaproblema (
  idocorrencia int not null,
  iditemtrabalho bigint default null,
  idjustificativa int default null,
  idproblema int default null,
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
  idcategoriaocorrencia int default null,
  idorigemocorrencia int default null
);

alter table ocorrenciaproblema add constraint pk_ocorrenciaproblema primary key (idocorrencia);

alter table ocorrenciaproblema add constraint fk_ocorrenciaproblema_problema foreign key (idproblema) references problema (idproblema);

create index fk_idx_idproblema on problema (idproblema);

-- autor: thiago.monteiro
-- data: 12/04/2013
alter table problema add column acoescorretas varchar(4000);
alter table problema add column acoesincorretas varchar(4000);
alter table problema add column melhoriasfuturas varchar(4000);
alter table problema add column recorrenciaproblema varchar(4000);
alter table problema add column responsabilidadeterceiros varchar(4000);

-- autor: thiago.monteiro
-- data: 10/04/2013
alter table atividadeperiodica add column idproblema int null;
alter table atividadeperiodica add constraint fk_atividadeperiodica_problema foreign key (idproblema) references problema (idproblema);

-- autor: thiago.monteiro
-- data: 11/04/2013
create table contatoproblema (
  idcontatoproblema int not null,
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
alter table problema add column datahoracaptura timestamp null;
alter table problema add column datahorainiciosla timestamp null;
alter table problema add column datahorareativacao timestamp null;
alter table problema add column datahorareativacaosla timestamp null;
alter table problema add column datahorasuspensao timestamp null;
alter table problema add column datahorasuspensaosla timestamp null;
alter table problema add column enviaemailacoes char(1) null;
alter table problema add column enviaemailcriacao char(1) null;
alter table problema add column enviaemailfinalizacao char(1) null;
alter table problema add column idcalendario int null;
alter table problema add column idfaseatual bigint null;
alter table problema add column idsolicitacaoservico bigint null;
alter table problema add column idsolicitante int null;
alter table problema add column resposta text null;
alter table problema add column seqreabertura int null;
alter table problema add column situacaosla char(1) null;
alter table problema add column tempoatendimentohh smallint null;
alter table problema add column tempoatendimentomm smallint null;
alter table problema add column tempoatrasohh smallint null;
alter table problema add column tempoatrasomm smallint null;
alter table problema add column tempocapturahh smallint null;
alter table problema add column tempocapturamm smallint null;
alter table problema add column tempodecorridohh smallint null;
alter table problema add column tempodecorridomm smallint null;

-- Tabela problema - chave(s) estrangeira(s) (fk)
alter table problema add constraint fk_problema_calendario foreign key (idcalendario) references calendario (idcalendario);
alter table problema add constraint fk_problema_faseservico foreign key (idfaseatual) references faseservico (idfase);
alter table problema add constraint fk_problema_solicitacaoservico foreign key (idsolicitacaoservico) references solicitacaoservico(idsolicitacaoservico);
alter table problema add constraint fk_problema_solicitante foreign key (idsolicitante) references empregados (idempregado);

-- autor: thiago.monteiro
-- data: 14/04/2013
alter table problema add column idorigematendimento bigint null default null;
alter table problema add column diagnostico varchar(4000) null default null;
alter table problema add column fechamento varchar(1000) null default null;

alter table problema add constraint fk_problema_origematendimento foreign key (idorigematendimento) references origematendimento (idorigem);

-- autor: thiago.monteiro
-- data: 11/04/2013
alter table problema add column idcontatoproblema int null;

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
insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (34,'Problema Registrado  -  ${IDPROBLEMA}','Senhor(a) ${SOLICITANTE},<br /><br />Informamos que o Problema Criado, foi registrado em ${DATAHORACAPTURA}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDPROBLEMA}<br /><strong>Tipo:</strong> ${PROATIVOREATIVO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong> ${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','registroProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (35,'Problema em Andamento  -  ${IDPROBLEMA}','Senhor(a) ${SOLICITANTE},<br /><br />Informamos que o Problema criado em ${DATAHORACAPTURA} esta em atendimento, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDPROBLEMA}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br />${TITULO}<br /><strong><br /></strong>${DESCRICAO}<br /><br /><strong>Grupo de atendimento:</strong>&nbsp;${NOMEGRUPOATUAL}<br /><br />Atenciosamente,<br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda','A','andamentoProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (36,'Problema Finalizado -  ${IDPROBLEMA}','Senhor(a) ${SOLICITANTE},<br /><br />Informamos que o Problema ${IDPROBLEMA},&nbsp; foi finalizado em ${DATAHORAFIM}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong> ${IDPROBLEMA}<br /><strong>Tipo:</strong> ${PROATIVOREATIVO}<br /><strong>T&iacute;tulo:</strong> ${TITULO}<br /><br /><br /><strong>Status:</strong>${STATUS}<br /><strong>Descri&ccedil;&atilde;o:</strong> ${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','finalizadoProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (37,'Problema encaminhado para seu GRUPO DE TRABALHO','&nbsp;A solicita&ccedil;&atilde;o abaixo foi encaminhada para seu Grupo de Trabalho:<div>&nbsp;</div><div>N&uacute;mero: ${IDPROBLEMA}</div><div>Tipo: ${PROATIVOREATIVO}</div><div><strong>T&iacute;tulo:</strong> ${TITULO}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div>','A','grupoProblema');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (38,'Requisições Problemas com prazo expirados','<br />informamos que existem&nbsp;requisi&ccedil;&otilde;es problemas com prazo para contornar ou solucionar expirados.<br /><br /><br />atenciosamente.<br />central it&nbsp;tecnologia da informa&ccedil;&atilde;o ltda.<br /><br />','a','problemaprazocontexp');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (39,'Requisição Mudança Reunião Marcada', '&nbsp;Senhor(a) ${NOMECONTATO},&nbsp;<br /><br />Informamos que haver&aacute; uma reuni&atilde;o relacionada a Requisi&ccedil;&atilde;o de mudan&ccedil;a de n&uacute;mero&nbsp;${IDREQUISICAOMUDANCA}.<br /><br /><strong><span style="font-size:12.0pt;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;mso-ansi-language:PT-BR;mso-fareast-language:PT-BR;mso-bidi-language:AR-SA"><font face="Times New Roman, serif">Local</font>:</span></strong><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,&quot;serif&quot;;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;mso-ansi-language:PT-BR;mso-fareast-language:PT-BR;mso-bidi-language:AR-SA">&nbsp;${LOCALREUNIAO}<br /> <strong>Data:</strong>&nbsp;${DATAINICIO}<br /> <strong>Hor&aacute;rio:</strong>&nbsp;${HORAINICIO}<br /> <strong>Dura&ccedil;&atilde;o Estimada:</strong>&nbsp;${DURACAOESTIMADA} minutos<br /> <br /> <strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br /></span><span serif; font-size: 16px;">${DESCRICAO}</span><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,&quot;serif&quot;;mso-fareast-font-family:Calibri;mso-fareast-theme-font:minor-latin;mso-ansi-language:PT-BR;mso-fareast-language:PT-BR;mso-bidi-language:AR-SA"><br /> <!--[if !supportLineBreakNewLine]--><br /> <!--[endif]--></span><br />Atenciosamente,&nbsp;<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.', 'A', 'reuniao');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (51,'Registro de Liberação  -  ${IDREQUISICAOLIBERACAO}' , 'Senhor(a) ${NOMESOLICITANTE},<br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de Libera&ccedil;&atilde;o foi registrada em ${DATAHORAINICIOSTR}, conforme os dados abaixo:<br /><br />N&uacute;mero: ${IDREQUISICAOLIBERACAO}<br />Tipo: ${TIPO}<br />T&iacute;tulo: ${TITULO}<br /><br />Descri&ccedil;&atilde;o: ${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br /><br /><br />' , 'A' , 'regestroLib');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (52,'Requisição Liberação finalizada  -  ${IDREQUISICAOLIBERACAO}' , 'Senhor(a) ${NOMESOLICITANTE},<br /><br />Informamos que a sua Requisi&ccedil;&atilde;o de libera&ccedil;&atilde;o foi finalizada em ${DATAHORACONCLUSAO}, conforme os dados abaixo:<br /><br />N&uacute;mero: ${IDREQUISICAOLIBERACAO}<br />Tipo: ${TIPO}<br />T&iacute;tulo: ${TITULO}<br /><br />Status:${STATUS}<br />Descri&ccedil;&atilde;o:<br />${DESCRICAO}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br />' , 'A' , 'liberacaoFinalizada');

insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values (53,'Requisição  Liberação em andamento- ${IDREQUISICAOLIBERACAO}' , 'Senhor(a) ${NOMESOLICITANTE},<br /><br />Informamos que a requisi&ccedil;&atilde;o de libera&ccedil;&atilde;o registrada em ${DATAHORAINICIOSTR} est&aacute; em atendimento, conforme os dados abaixo:<br /><br />N&uacute;mero: ${IDREQUISICAOLIBERACAO}<br />T&iacute;tulo: ${TITULO}<br /><br />Descri&ccedil;&atilde;o: <br />${TITULO}<br /><br />${DESCRICAO}<br /><br />Grupo de atendimento: ${NOMEGRUPOATUAL}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda<br />' , 'A' , 'liberacaoEmAndamento');

-- autor: thiago.monteiro
-- data: 19/04/2013
insert into bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) values (50, 'ProblemaPadrao', 'Problema Padrão', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoProblema');

insert into bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) values (100, '1.0', 50, 'problema;problema.status;problema.nomeGrupoAtual', '', '2013-04-19', NULL);

alter table problema alter column datahoralimite type timestamp;

alter table problema alter column datahoralimite set default null;

-- autor: thiago.monteiro
-- data: 22/04/2013
insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1000, 100, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 66, 216, 32, 32, '', '', null, '', '', '', 'S', NULL);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1001, 100, 'Tarefa', '', 'Registrada', 'Registrada', 'U', 'pages/problema/problema.load?alterarSituacao=N&faseAtual=Registrada&fase=EmInvestigacao', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 178, 200, 65, 140, '', '', null, '', 'N', '', 'S', NULL);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1002, 100, 'Tarefa', '', 'Investigação e diagnóstico', 'Investigação e diagnóstico', 'U', ' pages/problema/problema.load?alterarSituacao=N&faseAtual=EmInvestigacao&fase=RegistroErroConhecido', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 410, 200, 65, 140, '', '', null, '', 'N', '', 'S', NULL);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1003, 100, 'Tarefa', '', 'Registro de Erro Conhecido', 'Registro de Erro Conhecido', 'U', ' pages/problema/problema.load?alterarSituacao=N&faseAtual=RegistroErroConhecido&fase=Resolucao', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 663, 203, 65, 140, '', '', null, '', 'N', '', 'S', NULL);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1004, 100, 'Tarefa', '', 'Resolução', 'Resolução', 'U', ' pages/problema/problema.load?alterarSituacao=N&faseAtual=Resolucao', '', '#{problema.nomeGrupoAtual}', '', '', '', '', '', '', 895, 213, 65, 140, '', '', null, '', 'N', '', 'S', NULL);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1005, 100, 'Script', '', 'Encerrar', '', '', '', '', '', '', '', '', '#{execucaoFluxo}.encerra();', '', '', 1088, 190, 65, 140, '', '', null, '', '', '', 'S', NULL);

insert into bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) values (1006, 100, 'Finalizacao', '', '', '', '', '', '', '', '', '', '', '', '', '', 1291, 215, 32, 32, '', '', null, '', '', '', 'S', NULL);


insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1000,1001,100,null,null,'',1,3,138,232.25,'n','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1001,1002,100,null,null,'',1,3,364,232.5,'n','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1002,1003,100,null,null,'',1,3,606.5,234,'n','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1003,1004,100,null,null,'',1,3,849,240.5,'n','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1004,1005,100,null,null,'',1,3,1061.5,234,'n','');

insert into  bpm_sequenciafluxo  ( idelementoorigem , idelementodestino , idfluxo , nomeclasseorigem , nomeclassedestino , condicao , idconexaoorigem , idconexaodestino , bordax , borday , posicaoalterada , nome ) values (1005,1006,100,null,null,'',1,3,1259.5,226.75,'n','');



-- autor: riubbe.oliveira
-- data: 24/04/2013
create table solucaocontorno (
	idsolucaocontorno int not null,
	titulo varchar(120) not null,
	descricao text not null,
	datahoracriacao timestamp null
);

alter table solucaocontorno add primary key (idsolucaocontorno);

create table solucaodefinitiva (
	idsolucaodefinitiva int not null,
	titulo varchar(120) not null,
	descricao text not null,
	datahoracriacao timestamp null
);

-- autor: geber.costa
-- data: 24/04/2013
-- inicio

	alter table categoriaproblema 
	add idtipofluxo int references bpm_tipofluxo(idtipofluxo),
	add idgrupoexecutor int references grupo(idgrupo),
	add datainicio date,
	add datafim date;

	alter table categoriaproblema 
	add constraint fk_tipofluxo_reference_bpm_tipofluxo 
	foreign key (idtipofluxo) references bpm_tipofluxo(idtipofluxo),
	add constraint fk_grupoexecutor_reference_grupo 
	foreign key (idgrupoexecutor) references grupo(idgrupo);
	
	alter table categoriaproblema add nomecategoriaproblema varchar(100);	
	
	alter table categoriaproblema alter column idcategoriaproblemapai drop not null;
-- fim

alter table solucaodefinitiva add primary key (idsolucaodefinitiva);

-- autor: riubbe.oliveira
-- data: 25/04/2013
alter table solucaocontorno add column idproblema int null, add constraint fk_solucaocontorno_problema foreign key (idproblema) references problema (idproblema);
alter table solucaodefinitiva add column idproblema int null, add constraint fk_solucaodefinitiva_problema foreign key(idproblema) references problema(idproblema);


-- autor:thays.araujo
-- data: 17/04/2013
alter table baseconhecimento add column erroconhecido char(1) null ;

-- autor:thays.araujo
-- data: 19/04/2013

alter table problema add column faseatual varchar(100) null;
alter table problema alter column  msgerroassociada type   text   ;
alter table problema alter column  solucaodefinitiva    type text ;
alter table problema alter column  diagnostico     type text  ;
alter table problema alter column  acoescorretas    type text   ;
alter table problema alter column  acoesincorretas   type  text  ;
alter table problema alter column  melhoriasfuturas   type  text ;
alter table problema alter column  recorrenciaproblema  type   text  ;
alter table problema alter column  responsabilidadeterceiros  type   text ;

-- autor:thays.araujo
-- data: 23/04/2013

alter table problema add column idcausa int null;

alter table bpm_fluxo   add constraint fk_bpm_fluxo_bpm_tipofluxo  foreign key (idtipofluxo)  references bpm_tipofluxo (idtipofluxo );
alter table bpm_elementofluxo add constraint fk_bpm_elementofluxo_bpm_fluxo foreign key (idfluxo)  references bpm_fluxo (idfluxo );
alter table bpm_sequenciafluxo add constraint fk_bpm_sequenciafluxo_bpm_fluxo foreign key (idfluxo) references bpm_fluxo (idfluxo );

alter table  problema  alter column  fechamento     type text  ;

alter table  problemaitemconfiguracao  alter column  descricaoproblema   type text   ;

-- autor:thays.araujo
-- data: 06/05/2013
alter table   problema  alter column  causaraiz   type  text ;
alter table   problema  alter column  solucaocontorno   type  text  ;

-- autor:thays.araujo
-- data: 09/05/2013

alter table categoriaproblema add column idtemplate int null;

alter table categoriaproblema   
add constraint fk_categoriaproblema_templatesolicitacaoservico  
foreign key (idtemplate )  
references templatesolicitacaoservico (idtemplate ) ;

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
alter table problema add column confirmasolucaocontorno varchar(1);
alter table categoriaproblema alter column nomecategoria drop not null;

-- autor: thays.araujo
-- data: 15/05/2013
alter table problema add column idunidade int null;
alter table problema alter column datahoralimite drop not null;
alter table problema alter column datahoralimite type timestamp;
alter table problema add column enviaemailprazosolucionarexpirou char(1);

-- autor: thays.araujo
-- data: 16/05/2013
alter table problema alter column datahorasolicitacao drop not null;
alter table problema alter column datahorasolicitacao type timestamp;

alter table problema  alter column solucaocontorno  DROP DEFAULT;
alter table problema add column fase varchar(100) null;

alter table problema alter column datahoralimite drop not null;
alter table problema alter column datahoralimite set default null;

-- DROP TRIGGER add_current_date_to_problema ON problema;

-- INICIO - MODULO DE LIBERACAO

-- danillo.lisboa
-- adicona campo idliberacao na tabela atividadeperiodica
alter table atividadeperiodica add idLiberacao integer REFERENCES liberacao(idliberacao);
-- fim


-- danillo.lisboa
-- adiciona tabela de ligacao entre a tabela de liberacao e problema
-- CREATE TABLE liberacaoproblema (  idliberacao integer NOT NULL,  idproblema integer NOT NULL,  PRIMARY KEY (idliberacao,idproblema),  FOREIGN KEY (idproblema) REFERENCES problema (idproblema));
CREATE TABLE liberacaoproblema ( idliberacao integer, idproblema integer NOT NULL, idhistoricoliberacao integer DEFAULT NULL);
ALTER TABLE liberacaoproblema ALTER COLUMN idproblema DROP NOT NULL;
-- fim

-- criação da coluna idhistoricoliberacao na tabela liberacaomudança caso a tabela ja exista
alter table liberacaomudanca add column idhistoricoliberacao integer;

ALTER TABLE liberacaomudanca DROP CONSTRAINT liberacaomudanca_pkey;
ALTER TABLE liberacaomudanca ALTER COLUMN idliberacao DROP NOT NULL;
-- remoção da contraint da tabela liberacaomudanca


CREATE TABLE ocorrencialiberacao
(
  idocorrencia integer NOT NULL,
  iditemtrabalho bigint,
  idjustificativa integer,
  idrequisicaoliberacao integer,
  dataregistro date,
  horaregistro character varying(5) DEFAULT NULL::character varying,
  registradopor character varying(100) DEFAULT NULL::character varying,
  descricao character varying(200) DEFAULT NULL::character varying,
  datainicio date,
  datafim date,
  complementojustificativa text,
  dadosliberacao text,
  informacoescontato text,
  categoria character varying(20) DEFAULT NULL::character varying,
  origem character(1) DEFAULT NULL::bpchar,
  tempogasto smallint,
  ocorrencia text,
  idcategoriaocorrencia integer,
  idorigemocorrencia integer,
  CONSTRAINT ocorrencialiberacao_pkey PRIMARY KEY (idocorrencia ),
  CONSTRAINT ocorrencialiberacao_iditemtrabalho_fkey FOREIGN KEY (iditemtrabalho)
      REFERENCES bpm_itemtrabalhofluxo (iditemtrabalho) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ocorrencialiberacao_idjustificativa_fkey FOREIGN KEY (idjustificativa)
      REFERENCES justificativasolicitacao (idjustificativa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX ocorrencialiberacao_iditemtrabalho_idx
  ON ocorrencialiberacao
  USING btree
  (iditemtrabalho );
  
  CREATE INDEX ocorrencialiberacao_idjustificativa_idx
  ON ocorrencialiberacao
  USING btree
  (idjustificativa );
  
  CREATE INDEX ocorrencialiberacao_idrequisicaoliberacao_idx
  ON ocorrencialiberacao
  USING btree
  (idrequisicaoliberacao );
  
-- MURILO ALMEIDA PACHECO 22/04/2013
-- ALTERAÇÃO DO NOME DA TABELA PARA SEGUIR O PADRÃO DOS OUTROS MODULOS DO SISTEMA.
-- COMENTEI POR ENQUANTO PRECISAMOS TROCAR O NOME DA TABELA DEPOIS DE ALINHAR COM O CLEISON ALGUMAS DUVIDAS NÃO APAGAR !!!!!
-- ALTER TABLE liberacao RENAME TO requisicaoliberacao;

-- MURILO ALMEIDA PACHECO 25/04/2013
-- ALTERAÇÃO DO TIPO DE DADOS DA TABELA DE BYTEA PARA TEXT QUE É COMPATIVEL COM O TIPO BLOB DO MYSQL
ALTER TABLE controleged ALTER COLUMN conteudoarquivo type text;

-- MURILO ALMEIDA PACHECO
-- CRIAÇÃO DA TABELA DE HISTORICOS DE ALTERAÇÕES DAS LIBERAÇÕES
CREATE TABLE historicoliberacao (
  idhistoricoliberacao integer NOT NULL,
  idexecutormodificacao integer NOT NULL,
  datahoraModificacao timestamp without time zone NULL DEFAULT NULL,
  tipoModificacao character varying(1) DEFAULT NULL,
  historicoVersao double precision DEFAULT NULL,
  idliberacao integer NOT NULL,
  idsolicitante integer NOT NULL,
  idresponsavel integer DEFAULT NULL,
  titulo character varying(100) NOT NULL,
  descricao text NOT NULL,
  datainicial date NOT NULL,
  datafinal date NOT NULL,
  dataliberacao date DEFAULT NULL,
  situacao char(1) NOT NULL,
  risco char(1) NOT NULL,
  versao character varying(25) DEFAULT NULL,
  seqreabertura smallint DEFAULT NULL,
  enviaemailcriacao character varying(1) DEFAULT NULL,
  enviaemailacoes character varying(1) DEFAULT NULL,
  tempoatrasohh smallint DEFAULT NULL,
  tempoatrasomm smallint DEFAULT NULL,
  tempocapturahh smallint DEFAULT NULL,
  tempocapturamm smallint DEFAULT NULL,
  datahoratermino timestamp without time zone NULL DEFAULT NULL,
  datahoraconclusao timestamp without time zone NULL DEFAULT NULL,
  status character varying(45) DEFAULT NULL,
  tempodecorridohh smallint DEFAULT NULL,
  tempodecorridomm smallint DEFAULT NULL,
  tempoatendimentohh smallint DEFAULT NULL,
  tempoatendimentomm smallint DEFAULT NULL,
  datahoracaptura timestamp without time zone NULL DEFAULT NULL,
  datahorareativacao timestamp without time zone NULL DEFAULT NULL,
  datahorainicio timestamp without time zone NULL DEFAULT NULL,
  idcalendario integer DEFAULT NULL,
  datahorasuspensao timestamp without time zone NULL DEFAULT NULL,
  enviaemailfinalizacao character varying(1) DEFAULT NULL,
  prazohh smallint DEFAULT NULL,
  prazomm smallint DEFAULT NULL,
  idproprietario integer NOT NULL,
  datahorainicioagendada timestamp without time zone NULL DEFAULT NULL,
  datahoraterminoagendada timestamp without time zone NULL DEFAULT NULL,
  idtipoliberacao integer DEFAULT NULL,
  idGrupoAtual integer DEFAULT NULL,
  idcontatorequisicaoliberacao integer,
  telefonecontato character varying(45),
  ramal character varying(5),
  observacao text,
  idunidade integer,
  nomecontato2 character varying(80),
  emailcontato character varying(200),
  Idlocalidade integer,
  CONSTRAINT historicoliberacao_pkey PRIMARY KEY (idhistoricoliberacao)
);

-- adiciona a coluna baseline na tabela historicoliberacao
ALTER TABLE historicoliberacao ADD baseline character varying(30);
alter table historicoliberacao add column alterarsituacao character varying(1);
alter table historicoliberacao add  column acaoFluxo character varying(1);

--adiciona colunas a tabela historicoliberacao
alter table historicoliberacao add column alterarsituacao character varying;
alter table historicoliberacao add column acaoFluxo character varying;

-- criação da tabela requisicaoliberacaoitemconfiguracao para registrar os ics da liberacao.
CREATE TABLE requisicaoliberacaoitemconfiguracao (
  idrequisicaoliberacaoitemconfiguracao integer NOT NULL,
  idrequisicaoliberacao integer DEFAULT NULL,
  iditemconfiguracao integer DEFAULT NULL,
  descricao character varying(100) DEFAULT NULL,
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
  nometipoliberacao character varying(100) DEFAULT NULL::character varying,
  datainicio date,
  datafim date,
  CONSTRAINT tipoliberacao_pkey PRIMARY KEY (idtipoliberacao),
  CONSTRAINT tipoliberacao_idcalendario_fkey FOREIGN KEY (idcalendario)
      REFERENCES calendario (idcalendario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idcalendario_fkey1 FOREIGN KEY (idcalendario)
      REFERENCES calendario (idcalendario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idcalendario_fkey2 FOREIGN KEY (idcalendario)
      REFERENCES calendario (idcalendario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idcalendario_fkey3 FOREIGN KEY (idcalendario)
      REFERENCES calendario (idcalendario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idcalendario_fkey4 FOREIGN KEY (idcalendario)
      REFERENCES calendario (idcalendario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idgrupoexecutor_fkey FOREIGN KEY (idgrupoexecutor)
      REFERENCES grupo (idgrupo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idgrupoexecutor_fkey1 FOREIGN KEY (idgrupoexecutor)
      REFERENCES grupo (idgrupo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idgrupoexecutor_fkey2 FOREIGN KEY (idgrupoexecutor)
      REFERENCES grupo (idgrupo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idgrupoexecutor_fkey3 FOREIGN KEY (idgrupoexecutor)
      REFERENCES grupo (idgrupo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idgrupoexecutor_fkey4 FOREIGN KEY (idgrupoexecutor)
      REFERENCES grupo (idgrupo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailacoes_fkey FOREIGN KEY (idmodeloemailacoes)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailacoes_fkey1 FOREIGN KEY (idmodeloemailacoes)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailacoes_fkey2 FOREIGN KEY (idmodeloemailacoes)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailacoes_fkey3 FOREIGN KEY (idmodeloemailacoes)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailcriacao_fkey FOREIGN KEY (idmodeloemailcriacao)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailcriacao_fkey1 FOREIGN KEY (idmodeloemailcriacao)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailcriacao_fkey2 FOREIGN KEY (idmodeloemailcriacao)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailcriacao_fkey3 FOREIGN KEY (idmodeloemailcriacao)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailcriacao_fkey4 FOREIGN KEY (idmodeloemailcriacao)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailfinalizacao_fkey FOREIGN KEY (idmodeloemailfinalizacao)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailfinalizacao_fkey1 FOREIGN KEY (idmodeloemailfinalizacao)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailfinalizacao_fkey2 FOREIGN KEY (idmodeloemailfinalizacao)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idmodeloemailfinalizacao_fkey3 FOREIGN KEY (idmodeloemailfinalizacao)
      REFERENCES modelosemails (idmodeloemail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idtipofluxo_fkey FOREIGN KEY (idtipofluxo)
      REFERENCES bpm_tipofluxo (idtipofluxo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idtipofluxo_fkey1 FOREIGN KEY (idtipofluxo)
      REFERENCES bpm_tipofluxo (idtipofluxo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idtipofluxo_fkey2 FOREIGN KEY (idtipofluxo)
      REFERENCES bpm_tipofluxo (idtipofluxo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idtipofluxo_fkey3 FOREIGN KEY (idtipofluxo)
      REFERENCES bpm_tipofluxo (idtipofluxo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tipoliberacao_idtipofluxo_fkey4 FOREIGN KEY (idtipofluxo)
      REFERENCES bpm_tipofluxo (idtipofluxo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE tipoliberacao
  OWNER TO postgres;

CREATE INDEX tipoliberacao_idcalendario_idx   ON tipoliberacao  USING btree  (idcalendario);

CREATE INDEX tipoliberacao_idgrupoexecutor_idx ON tipoliberacao USING btree  (idgrupoexecutor);

CREATE INDEX tipoliberacao_idmodeloemailacoes_idx ON tipoliberacao USING btree (idmodeloemailacoes);

CREATE INDEX tipoliberacao_idmodeloemailcriacao_idx ON tipoliberacao USING btree (idmodeloemailcriacao);

CREATE INDEX tipoliberacao_idmodeloemailfinalizacao_idx ON tipoliberacao USING btree (idmodeloemailfinalizacao);

CREATE INDEX tipoliberacao_idtipofluxo_idx ON tipoliberacao USING btree (idtipofluxo);

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
      REFERENCES bpm_fluxo (idfluxo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT execucaoliberacao_idinstanciafluxo_fkey FOREIGN KEY (idinstanciafluxo)
      REFERENCES bpm_instanciafluxo (idinstancia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE execucaoliberacao
  OWNER TO postgres;

CREATE INDEX execucaoliberacao_idfluxo_idx
  ON execucaoliberacao
  USING btree
  (idfluxo);

CREATE INDEX execucaoliberacao_idinstanciafluxo_idx
  ON execucaoliberacao
  USING btree
  (idinstanciafluxo);

CREATE INDEX execucaoliberacao_idrequisicaoliberacao_idx
  ON execucaoliberacao
  USING btree
  (idliberacao);
  
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
alter table liberacao 
add column  seqreabertura smallint,
add column enviaemailcriacao character varying(1) DEFAULT NULL::character varying,
add column enviaemailacoes character varying(1) DEFAULT NULL::character varying,
add column tempoatrasohh smallint,
add column tempoatrasomm smallint,
add column tempocapturahh smallint,
add column tempocapturamm smallint,
add column datahoratermino timestamp without time zone DEFAULT '1970-01-01 00:00:00'::timestamp without time zone,
add column datahoraconclusao timestamp without time zone  DEFAULT '1970-01-01 00:00:00'::timestamp without time zone,
add column status character varying(45) DEFAULT NULL::character varying,
add column tempodecorridohh smallint,
add column tempodecorridomm smallint,
add column tempoatendimentohh smallint,
add column tempoatendimentomm smallint,
add column datahoracaptura timestamp without time zone DEFAULT '1970-01-01 00:00:00'::timestamp without time zone,
add column datahorareativacao timestamp without time zone DEFAULT '1970-01-01 00:00:00'::timestamp without time zone,
add column datahorainicio timestamp without time zone DEFAULT '1970-01-01 00:00:00'::timestamp without time zone,
add column idcalendario integer,
add column datahorasuspensao timestamp without time zone DEFAULT '1970-01-01 00:00:00'::timestamp without time zone,
add column enviaemailfinalizacao character varying(1) DEFAULT NULL::character varying,
add column prazohh smallint,
add column prazomm smallint,
add column idproprietario integer NOT NULL,
add column datahorainicioagendada timestamp without time zone DEFAULT '1970-01-01 00:00:00'::timestamp without time zone,
add column datahoraterminoagendada timestamp without time zone DEFAULT '1970-01-01 00:00:00'::timestamp without time zone,
add column idtipoliberacao integer,
add column 	idGrupoAtual integer,
add column 	prioridade integer DEFAULT NULL,
add column 	nivelurgencia varchar(255) DEFAULT NULL,
add column 	nivelimpacto varchar(255) DEFAULT NULL,
add column idaprovador integer  DEFAULT NULL,
add column 	datahoraaprovacao timestamp without time zone DEFAULT '1970-01-01 00:00:00'::timestamp without time zone,
add column fechamento text,
add column idgruponivel1 integer,
add column idGrupoAprovador integer,
add column idcontrato integer;


alter table liberacao alter column situacao drop not null;
alter table liberacao alter column situacao set default null;

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
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (596,593,50,'','','!#{requisicaoLiberacao}.atendida();',3,2,391,353,'S','Não Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (597,596,50,'','','#{requisicaoLiberacao}.liberada();',2,1,822,353,'S','Liberada');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (598,594,50,'','','!#{requisicaoLiberacao}.atendida();',0,3,665,59.75,'N','Não Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (597,598,50,'','','#{requisicaoLiberacao}.emAtendimento();',3,1,706.5,173,'N','Em Atendimento');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (595,599,50,'','','',3,1,394,235.25,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (598,595,50,'','','#{requisicaoLiberacao}.atendida();',2,0,589,158,'N','Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (596,595,50,'','','#{requisicaoLiberacao}.atendida();',0,2,589.5,300.5,'N','Resolvida');

-- TIPO LIBERACAO
INSERT INTO tipoliberacao (idtipoliberacao, idtipofluxo, idmodeloemailcriacao, idmodeloemailfinalizacao, idmodeloemailacoes, idgrupoexecutor, idcalendario, nometipoliberacao, datainicio, datafim) VALUES (1,54,51,52,53,1,1,'Requisição Liberação Normal','2013-05-20',NULL);

-- fim modelos emails

-- EDMAR FAGUNDES - 16/05/2013 {
-- ADICIONA TABELA DE APROVAÇÃO PARA AUXILIAR NA PARTE DE PESQUISA
CREATE TABLE aprovacaorequisicaoliberacao
(
  idaprovacaorequisicaoliberacao integer NOT NULL,
  idrequisicaoliberacao bigint NOT NULL,
  idtarefa bigint,
  idresponsavel integer NOT NULL,
  datahora timestamp without time zone,
  idjustificativa integer,
  complementojustificativa text,
  observacoes text,
  aprovacao character(1) NOT NULL,
  CONSTRAINT idaprovacaorequisicao PRIMARY KEY (idaprovacaorequisicaoliberacao)
);

-- ADICIONA TABELA DE CONTATO PARA SALVAR OS DADOS DO CONTATO AO CRIAR UMA NOVA LIBERAÇÃO
CREATE TABLE contatorequisicaoliberacao
(
  idcontatorequisicaoliberacao integer NOT NULL,
  nomecontato character varying(70),
  telefonecontato character varying(20),
  emailcontato character varying(120),
  observacao text,
  idlocalidade integer,
  ramal character varying(4),
  idunidade integer,
  CONSTRAINT contatorequisicaoliberacao_pkey PRIMARY KEY (idcontatorequisicaoliberacao)
);
-- ADICIONA COLUNA IDCONTATOREQUISICAOLIBERACAO PARA FAZER A CONEXAO COM A TABELA CONTATOREQUISICAOLIBERACAO
ALTER TABLE liberacao ADD COLUMN idcontatorequisicaoliberacao integer;
-- ADICIONA COLUNA IDULTIMAAPROVACAO PARA FAZER A CONEXAO COM A TABELA APROVACAOREQUISICAOLIBERACAO
ALTER TABLE liberacao ADD COLUMN idultimaaprovacao integer;
-- }

-- FIM - MODULO DE LIBERACAO

-- autor: pedro.lino
-- data: 16/05/2013

CREATE TABLE tiposubscricao
(
  idtiposubscricao integer NOT NULL,
  nometiposubscricao character varying,
  CONSTRAINT tiposubscricao_pk PRIMARY KEY (idtiposubscricao)
);

CREATE TABLE controlecontrato
(
  idcontrolecontrato integer NOT NULL,
  idcontrato integer,
  numerosubscricao character varying,
  endereco character varying,
  contato character varying,
  email character varying,
  telefone1 character varying,
  telefone2 character varying,
  tiposubscricao integer,
  url character varying,
  login character varying,
  senha character varying,
  datainicio character varying,
  datafim character varying,
  cliente character varying,
  CONSTRAINT pk_controlecontrato PRIMARY KEY (idcontrolecontrato),
  CONSTRAINT fk_contrato FOREIGN KEY (idcontrato)
      REFERENCES contratos (idcontrato),
  CONSTRAINT fk_tiposubscricao FOREIGN KEY (tiposubscricao)
      REFERENCES tiposubscricao (idtiposubscricao) 
);

CREATE TABLE controlecontratoocorrencia
(
  idccocorrencia integer NOT NULL,
  assuntoccocorrencia character varying,
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
  nomecctreinamento character varying,
  datacctreinamento date,
  CONSTRAINT pk_itemcontrolecontratotreinamento PRIMARY KEY (idcctreinamento),
  CONSTRAINT fk_controlecontrato_treinamento FOREIGN KEY (idcontrolecontrato)
      REFERENCES controlecontrato (idcontrolecontrato) ,
  CONSTRAINT fk_empregadotreinamento FOREIGN KEY (idempregadotreinamento)
      REFERENCES empregados (idempregado) 
);

-- Tabela de Modulos do sistema

CREATE TABLE modulosistema
(
  idmodulosistema integer NOT NULL,
  nomemodulosistema character varying(250),
  CONSTRAINT pk_idmodulosistema PRIMARY KEY (idmodulosistema)
);

CREATE TABLE controlecontratoversao
(
  idcontrolecontrato integer,
  idccversao integer NOT NULL,
  nomeccversao character varying,
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
INSERT INTO MODULOSISTEMA VALUES(2, 'REQUISIÇÃO');
INSERT INTO MODULOSISTEMA VALUES(3, 'MUDANÇA');
INSERT INTO MODULOSISTEMA VALUES(4, 'EVENTO');
INSERT INTO MODULOSISTEMA VALUES(5, 'CONTINUIDADE');
INSERT INTO MODULOSISTEMA VALUES(6, 'CONHECIMENTO');
INSERT INTO MODULOSISTEMA VALUES(7, 'SLA');
INSERT INTO MODULOSISTEMA VALUES(8, 'PROBLEMA');
INSERT INTO MODULOSISTEMA VALUES(9, 'CAPACIDADE');
INSERT INTO MODULOSISTEMA VALUES(10, 'PORTIFÓLIO');
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
  idsolicitacaoquestionario int4 NOT NULL,
  idquestionario int4 NOT NULL,
  idsolicitacaoservico int4 NOT NULL,
  dataquestionario timestamp NOT NULL,
  idresponsavel int4 NOT NULL,
  idtarefa int4 DEFAULT NULL,
  aba varchar(100) DEFAULT NULL,
  situacao char(1) NOT NULL,
  datahoragrav timestamp NOT NULL,
  conteudoimpresso text
);

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


create table categoriaquestionario (
   idcategoriaquestionario int4                 not null,
   nomecategoriaquestionario varchar(50)          not null
);

alter table categoriaquestionario
   add constraint pk_categoriaquestionario primary key (idcategoriaquestionario);

create table grupoquestionario (
   idgrupoquestionario  int4                 not null,
   idquestionario       int4                 not null,
   nomegrupoquestionario varchar(80)          not null,
   ordem                int2                 null
);

alter table grupoquestionario
   add constraint pk_grupoquestionario primary key (idgrupoquestionario);

create table opcaorespostaquestionario (
   idopcaorespostaquestionario int4                 not null,
   idquestaoquestionario int4                 not null,
   titulo               varchar(255)         not null,
   peso                 int4                 null,
   valor                varchar(50)          null,
   geraalerta           char(1)              null,
   exibecomplemento     char(1)              null,
   idquestaocomplemento int4                 null
);

alter table opcaorespostaquestionario
   add constraint pk_opcaorespostaquestionario primary key (idopcaorespostaquestionario);

create table questaoquestionario (
   idquestaoquestionario int4                 not null,
   idgrupoquestionario  int4                 null,
   idquestaoagrupadora  int4                 null,
   idquestaoorigem      int4                 null,
   tipo                 char(1)              not null,
   tituloquestaoquestionario text                 not null,
   tipoquestao          char(1)              not null,
   sequenciaquestao     int4                 not null,
   valordefault         text                 null,
   textoinicial         text                 null,
   tamanho              int4                 null,
   decimais             int4                 null,
   inforesposta         char(1)              null 
      constraint ckc_inforesposta_questaoq check (inforesposta is null or (inforesposta in ('L','B'))),
   valoresreferencia    text                 null,
   unidade              text                 null,
   obrigatoria          char(1)              not null 
      constraint ckc_obrigatoria_questaoq check (obrigatoria in ('S','N')),
   ponderada            char(1)              null,
   qtdelinhas           int4                 null,
   qtdecolunas          int4                 null,
   cabecalholinhas      char(1)              null,
   cabecalhocolunas     char(1)              null,
   nomelistagem         varchar(30)          null,
   ultimovalor          char(1)              null,
   idsubquestionario    int4                 null,
   abaresultsubform     varchar(200)         null,
   sigla                varchar(100)         null,
   imprime              char(1)              null,
   calculada            char(1)              null,
   editavel             char(1)              null,
   valorpermitido1      numeric(15,5)        null,
   valorpermitido2      numeric(15,5)        null,
   idimagem             int4                 null
);

alter table questaoquestionario
   add constraint pk_questaoquestionario primary key (idquestaoquestionario);

create  index ix_sigla_questao on questaoquestionario (
sigla
);

create  index ix_idquestaoorigem on questaoquestionario (
idquestaoorigem
);

create table questionario (
   idquestionario       int4                 not null,
   idquestionarioorigem int4                 null,
   idcategoriaquestionario int4                 not null,
   nomequestionario     varchar(50)          not null,
   idempresa            int4                 not null,
   ativo                char(1)              not null default 'S',
   javascript           text
);

alter table questionario
   add constraint pk_questionario primary key (idquestionario);

create table respostaitemquestionario (
   idrespostaitemquestionario int4                 not null,
   ididentificadorresposta int4                 not null,
   idquestaoquestionario int4                 not null,
   sequencialresposta   int4                 null,
   respostatextual      text                 null,
   respostapercentual   numeric(15,5)        null,
   respostavalor        numeric(15,5)        null,
   respostavalor2       numeric(15,5)        null,
   respostanumero       numeric(8,0)         null,
   respostanumero2      numeric(8,0)         null,
   respostadata         date                 null,
   respostahora         varchar(4)           null,
   respostames          int2                 null,
   respostaano          int2                 null,
   respostaidlistagem   varchar(10)          null,
   respostadia          int2                 null
);

alter table respostaitemquestionario
   add constraint pk_respostaitemquestionario primary key (idrespostaitemquestionario);

create  index ix_ident_questao on respostaitemquestionario (
ididentificadorresposta,
idquestaoquestionario
);

create  index ix_idquestao on respostaitemquestionario (
idquestaoquestionario
);

create table respostaitemquestionarioanexos (
   idrespostaitmquestionarioanexo int4                 not null,
   idrespostaitemquestionario int4                 not null,
   caminhoanexo         varchar(255)         not null,
   observacao           text                 null
);

alter table respostaitemquestionarioanexos
   add constraint pk_respostaitemquestionarioane primary key (idrespostaitmquestionarioanexo);

create table respostaitemquestionarioopcoes (
   idrespostaitemquestionario int4                 not null,
   idopcaorespostaquestionario int4                 not null
);

alter table respostaitemquestionarioopcoes
   add constraint pk_respostaitemquestionarioopc primary key (idrespostaitemquestionario, idopcaorespostaquestionario);

alter table grupoquestionario
   add constraint fk_grupoque_reference_question foreign key (idquestionario)
      references questionario (idquestionario)
      on delete restrict on update restrict;

alter table opcaorespostaquestionario
   add constraint fk_opcaores_reference_questaoq foreign key (idquestaocomplemento)
      references questaoquestionario (idquestaoquestionario)
      on delete restrict on update restrict;

alter table opcaorespostaquestionario
   add constraint fk_opcaores_ref_questaoq foreign key (idquestaoquestionario)
      references questaoquestionario (idquestaoquestionario)
      on delete cascade on update restrict;

alter table questaoquestionario
   add constraint fk_questaoagrupadora foreign key (idquestaoagrupadora)
      references questaoquestionario (idquestaoquestionario)
      on delete restrict on update restrict;

alter table questaoquestionario
   add constraint fk_questaoq_reference_question foreign key (idsubquestionario)
      references questionario (idquestionario)
      on delete restrict on update restrict;

alter table questaoquestionario
   add constraint fk_questaoq_reference_grupoque foreign key (idgrupoquestionario)
      references grupoquestionario (idgrupoquestionario)
      on delete restrict on update restrict;

alter table questionario
   add constraint fk_question_reference_categori foreign key (idcategoriaquestionario)
      references categoriaquestionario (idcategoriaquestionario)
      on delete restrict on update restrict;

alter table questionario
   add constraint fk_questionarioorigem foreign key (idquestionarioorigem)
      references questionario (idquestionario)
      on delete restrict on update restrict;

alter table respostaitemquestionario
   add constraint fk_resposta_reference_questaoq foreign key (idquestaoquestionario)
      references questaoquestionario (idquestaoquestionario)
      on delete restrict on update restrict;

alter table respostaitemquestionarioanexos
   add constraint fk_rspt_anx_reference_rspt_itm foreign key (idrespostaitemquestionario)
      references respostaitemquestionario (idrespostaitemquestionario)
      on delete cascade on update restrict;

alter table respostaitemquestionarioopcoes
   add constraint fk_rspta_q_reference_rspta_itm foreign key (idrespostaitemquestionario)
      references respostaitemquestionario (idrespostaitemquestionario)
      on delete cascade on update restrict;

alter table respostaitemquestionarioopcoes
   add constraint fk_resposta_reference_opcaores foreign key (idopcaorespostaquestionario)
      references opcaorespostaquestionario (idopcaorespostaquestionario)
      on delete restrict on update restrict;

ALTER TABLE templatesolicitacaoservico ADD idquestionario int4 NULL;
 
ALTER TABLE templatesolicitacaoservico ALTER COLUMN nomeclassedto DROP NOT NULL;
ALTER TABLE templatesolicitacaoservico ALTER COLUMN nomeclasseaction DROP NOT NULL;
ALTER TABLE templatesolicitacaoservico ALTER COLUMN nomeclasseservico DROP NOT NULL;
ALTER TABLE templatesolicitacaoservico ALTER COLUMN urlrecuperacao DROP NOT NULL;

-- autor:thays.araujo
-- data: 20/05/2013
alter table problema add column idcategoriasolucao int ;
alter table problema add column precisasolucaocontorno char(1);
alter table problema add column resolvido char(1) ;
 
-- Scripts do módulo de mudança

-- INICIO - MODULO DE MUDANCA

ALTER TABLE APROVACAOMUDANCA ADD COLUMN dataHoraVotacao character varying(25);
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
      REFERENCES requisicaomudanca (idrequisicaomudanca) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE requisicaomudancarisco
  OWNER TO postgres;

CREATE INDEX requisicaomudancarisco_idrequisicaomudanca_idx
  ON requisicaomudancarisco
  USING btree
  (idrequisicaomudanca);

CREATE INDEX requisicaomudancarisco_idrisco_idx
  ON requisicaomudancarisco
  USING btree
  (idrisco);
  
  
CREATE TABLE requisicaomudancaliberacao
(
  idrequisicaomudancaliberacao integer NOT NULL,
  idrequisicaomudanca integer NOT NULL,
  idliberacao bigint NOT NULL,
  CONSTRAINT requisicaomudancaliberacao_pkey PRIMARY KEY (idrequisicaomudancaliberacao),
  CONSTRAINT requisicaomudancaliberacao_idrequisicaomudanca_fkey FOREIGN KEY (idrequisicaomudanca)
      REFERENCES requisicaomudanca (idrequisicaomudanca) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT requisicaomudancaliberacao_idliberacao_fkey FOREIGN KEY (idliberacao)
      REFERENCES liberacao (idliberacao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE requisicaomudancaliberacao
  OWNER TO postgres;

CREATE INDEX requisicaomudancaliberacao_idrequisicaomudanca_idx
  ON requisicaomudancaliberacao
  USING btree
  (idrequisicaomudanca);
  
  
ALTER TABLE requisicaomudanca ALTER COLUMN DATAHORACONCLUSAO DROP NOT NULL;
  
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
      REFERENCES requisicaomudanca (idrequisicaomudanca) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- tabela netmap

ALTER TABLE netmap ADD COLUMN dataInventario date;

-- Flávio
CREATE TABLE midiasoftwarechave
(
   "idmidiasoftwarechave" integer, 
   "idmidiasoftware" integer, 
   chave character varying(255)
);

ALTER TABLE netmap RENAME date TO date_;

ALTER TABLE tipoitemconfiguracao ADD COLUMN imagem character varying(255);

-- alterar tabela parametrocorpore
ALTER TABLE parametrocorpore ALTER COLUMN valor SET NOT NULL;

-- INICIO - MURILO GABRIEL RODRIGUES - 27/05/2013

alter table fluxoservico add column idfluxoservico bigint;
create sequence rownumseq;
update fluxoservico set idfluxoservico = nextval('rownumseq');
drop sequence rownumseq;
alter index fluxoservico_pkey rename to fluxoservico_pkey_old;
alter table fluxoservico add constraint fluxoservico_pkey primary key(idfluxoservico);
alter table fluxoservico drop constraint fluxoservico_pkey_old;
alter table fluxoservico add constraint _fk foreign key (type_id) references cvterm (cvterm_id);
alter table fluxoservico add constraint servicocontrato_fk foreign key (idservicocontrato) references servicocontrato (idservicocontrato);
alter table fluxoservico add constraint tipofluxo_fk foreign key (idtipofluxo) references bpm_tipofluxo (idtipofluxo);
alter table fluxoservico add constraint faseservico_fk foreign key (idfase) references faseservico (idfase);

-- FIM - MURILO GABRIEL RODRIGUES - 27/05/2013

-- autor: thays.araujo
-- data : 04/06/2013
alter table problema alter column datahorafim type timestamp ;


DROP TRIGGER add_current_date_to_problema ON problema;

-- INICIO - MURILO GABRIEL RODRIGUES - 05/06/2013
ALTER TABLE versao ADD COLUMN idusuario INTEGER;
-- FIM - MURILO GABRIEL RODRIGUES - 05/06/2013

-- Flavio Júnior - 06/06/2013
ALTER TABLE midiasoftwarechave ADD COLUMN qtdpermissoes integer;

ALTER TABLE itemconfiguracao ADD COLUMN dtultimacaptura timestamp without time zone;

ALTER TABLE historicoic ADD COLUMN dtultimacaptura timestamp without time zone;

-- Módulo Mudança
ALTER TABLE REQUISICAOMUDANCA ADD COLUMN EHPROPOSTAaux char(1);
ALTER TABLE REQUISICAOMUDANCA ADD COLUMN VOTACAOPROPOSTAAPROVADAAUX char(1);


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


