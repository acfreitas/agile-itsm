-- Oracle

set define off;

-- autor: geber
-- data: 12/04/2013
create table ocorrenciaproblema (
  idocorrencia number(10,0) not null,
  iditemtrabalho number(19,0) default null,
  idjustificativa number(10,0) default null,
  idproblema number(10,0) default null,
  dataregistro date default null,
  horaregistro varchar2(5) default null,
  registradopor varchar2(100) default null,
  descricao varchar2(200) default null,
  datainicio date default null,
  datafim date default null,
  complementojustificativa clob,
  dadosproblema clob,
  informacoescontato clob,
  categoria varchar2(20) default null,
  origem char(1) default null,
  tempogasto number(5,0) default null,
  ocorrencia clob,
  idcategoriaocorrencia number(10,0) default null,
  idorigemocorrencia number(10,0) default null
);

alter table ocorrenciaproblema add constraint pk_ocorrenciaproblema primary key (idocorrencia);

alter table ocorrenciaproblema add constraint fk_ocorrenciaproblema_problema foreign key (idproblema) references problema (idproblema);

-- autor: thiago.monteiro
-- data: 12/04/2013
alter table problema add (acoescorretas varchar2(4000) );
alter table problema add (acoesincorretas varchar2(4000) );
alter table problema add (melhoriasfuturas varchar2(4000) );
alter table problema add (recorrenciaproblema varchar2(4000) );
alter table problema add (responsabilidadeterceiros varchar2(4000) );

-- autor: thiago.monteiro
-- data: 10/04/2013
alter table atividadeperiodica add (idproblema number(10,0) NULL);
alter table atividadeperiodica add constraint fk_atividadeperiodica_problema foreign key (idproblema) references problema (idproblema);

-- autor: thiago.monteiro
-- data: 11/04/2013
create table contatoproblema (
  idcontatoproblema number(10,0) not null,
  nomecontato varchar2(100) default null,
  telefonecontato varchar2(100) default null,
  emailcontato varchar2(200) default null,
  observacao clob,
  idlocalidade number(10,0) default null,
  ramal varchar2(5) default null
);

alter table contatoproblema add constraint pk_contatoproblema primary key (idcontatoproblema);

-- autor: thiago.monteiro
-- data: 10/04/2013
create table justificativaproblema (
    idjustificativaproblema number(10,0) not null,
    descricaoproblema varchar2(100) not null,
    suspensao char(1) not null,
    situacao char(1) not null,
    aprovacao char(1) default null,
    deleted char(1) default null
);

alter table justificativaproblema add constraint pk_justificativaproblema primary key (idjustificativaproblema);

-- autor: thiago.monteiro
-- data: 09/04/2013

create table execucaoproblema (
    idexecucao number(19,0) not null,
    idproblema number(19,0) default null,
    idfase number(19,0) default null,
    idinstanciafluxo number(19,0) default null,
    idfluxo number(19,0) default null,
    prazohh number(5,0) default null,
    prazomm number(5,0) default null,
    seqreabertura number(10,0) default null
);

-- Tabela execucaoproblema - chave primaria (pk)
alter table execucaoproblema add primary key (idexecucao);

-- Tabela execucaoproblema - indices (idx)
create index fk_execprob_problema on execucaoproblema (idproblema);
create index fk_execprob_fase on execucaoproblema (idfase);
create index fk_execprob_fluxo on execucaoproblema (idfluxo);
create index fk_execprob_instanciafluxo on execucaoproblema (idinstanciafluxo);

-- Tabela problema - acrescimo de novas colunas
alter table problema add datahoracaptura date null;
alter table problema add datahorainiciosla date null;
alter table problema add datahorareativacao date null;
alter table problema add datahorareativacaosla date null;
alter table problema add datahorasuspensao date null;
alter table problema add datahorasuspensaosla date null;
alter table problema add enviaemailacoes char(1) null;
alter table problema add enviaemailcriacao char(1) null;
alter table problema add enviaemailfinalizacao char(1) null;
alter table problema add idcalendario number(10,0) null;
alter table problema add idfaseatual number(19,0) null;
alter table problema add idsolicitacaoservico number(19,0) null;
alter table problema add idsolicitante number(10,0) null;
alter table problema add resposta clob null;
alter table problema add seqreabertura number(10,0) null;
alter table problema add situacaosla char(1) null;
alter table problema add tempoatendimentohh number(5,0) null;
alter table problema add tempoatendimentomm number(5,0) null;
alter table problema add tempoatrasohh number(5,0) null;
alter table problema add tempoatrasomm number(5,0) null;
alter table problema add tempocapturahh number(5,0) null;
alter table problema add tempocapturamm number(5,0) null;
alter table problema add tempodecorridohh number(5,0) null;
alter table problema add tempodecorridomm number(5,0) null;

-- Tabela problema - chave(s) estrangeira(s) (fk)
alter table problema add constraint fk_problema_calendario foreign key (idcalendario) references calendario (idcalendario);
alter table problema add constraint fk_problema_faseservico foreign key (idfaseatual) references faseservico (idfase);
alter table problema add constraint fk_problema_solicitacaoservico foreign key (idsolicitacaoservico) references solicitacaoservico (idsolicitacaoservico);
alter table problema add constraint fk_problema_solicitante foreign key (idsolicitante) references empregados (idempregado);

-- autor: thiago.monteiro
-- data: 14/04/2013
alter table problema add idorigematendimento number(24,0) null;
alter table problema add diagnostico varchar2(4000) null;
alter table problema add fechamento varchar2(1000) null;

alter table problema add constraint fk_problema_origematendimento foreign key (idorigematendimento) references origematendimento (idorigem);

-- autor: thiago.monteiro
-- data: 11/04/2013
alter table problema add (idcontatoproblema number(10,0) NULL);

alter table problema add constraint fk_problema_contatoproblema foreign key (idcontatoproblema) references contatoproblema (idcontatoproblema);

-- autor: geber
-- data: 15/04/2013
alter table problema add (acompanhamento char(1) default 'N');

-- autor: thiago.monteiro
-- data: 16/04/2013
alter table problema add (grave char(1) default 'N');
alter table problema add (precisamudanca char(1) default 'N');

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

insert into bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) values (100, '1.0', 50, 'problema;problema.status;problema.nomeGrupoAtual', '', to_date('2013-04-19', 'yyyy-mm-dd'), NULL);

alter table problema modify (datahoralimite date);

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
	idsolucaocontorno number(11,0) not null,
	titulo varchar2(120) not null,
	descricao clob not null,
	datahoracriacao date null
);

alter table solucaocontorno add constraint pk_solucaocontorno primary key (idsolucaocontorno);

create table solucaodefinitiva (
	idsolucaodefinitiva number(11,0) not null,
	titulo varchar2(120) not null,
	descricao clob not null,
	datahoracriacao date null
);

alter table solucaodefinitiva add constraint pk_solucaodefinitiva primary key (idsolucaodefinitiva);


-- autor geber.costa
-- data 24/04/2013
alter table categoriaproblema
	add(idtipofluxo int ,
	idgrupoexecutor int ,
	datainicio date,
	datafim date);
	
  
alter table categoriaproblema add constraint fk_idtipofluxo_bpm_tipofluxo foreign key(idtipofluxo) references bpm_tipofluxo(idtipofluxo);
alter table categoriaproblema add constraint fk_idgrupoexecutor_grupo foreign key(idgrupoexecutor) references grupo(idgrupo);
alter table categoriaproblema add( nomecategoriaproblema varchar(100));
alter table categoriaproblema modify (idcategoriaproblemapai null );

-- autor: riubbe.oliveira
-- data: 25/04/2013
alter table solucaocontorno add idproblema number(11,0) null;
alter table solucaodefinitiva add idproblema number(11,0) null;

alter table solucaocontorno add constraint fk_solucaocontorno_problema foreign key (idproblema) references problema (idproblema);
alter table solucaodefinitiva add constraint fk_solucaodefinitiva_problema foreign key (idproblema) references problema (idproblema);

-- autor:thays.araujo
-- data: 17/04/2013
alter table baseconhecimento add ( erroconhecido char(1) NULL);

-- autor:thays.araujo
-- data: 19/04/2013

alter table problema add ( faseatual varchar(100) NULL);

alter table problema add msgerroassociada_aux clob null;
update problema set msgerroassociada_aux = msgerroassociada;
alter table problema drop column msgerroassociada;
alter table problema rename column msgerroassociada_aux to msgerroassociada;

alter table problema add solucaodefinitiva_aux clob null;
update problema set solucaodefinitiva_aux = solucaodefinitiva;
alter table problema drop column solucaodefinitiva;
alter table problema rename column solucaodefinitiva_aux to solucaodefinitiva;

alter table problema add diagnostico_aux clob null;
update problema set diagnostico_aux = diagnostico;
alter table problema drop column diagnostico;
alter table problema rename column diagnostico_aux to diagnostico;

alter table problema add acoescorretas_aux clob null;
update problema set acoescorretas_aux = acoescorretas;
alter table problema drop column acoescorretas;
alter table problema rename column acoescorretas_aux to acoescorretas;

alter table problema add acoesincorretas_aux clob null;
update problema set acoesincorretas_aux = melhoriasfuturas;
alter table problema drop column acoesincorretas;
alter table problema rename column acoesincorretas_aux to acoesincorretas;

alter table problema add melhoriasfuturas_aux clob null;
update problema set melhoriasfuturas_aux = melhoriasfuturas;
alter table problema drop column melhoriasfuturas;
alter table problema rename column melhoriasfuturas_aux to melhoriasfuturas;


alter table problema add recorrenciaproblema_aux clob null;
update problema set recorrenciaproblema_aux = recorrenciaproblema_aux;
alter table problema drop column recorrenciaproblema;
alter table problema rename column recorrenciaproblema_aux to recorrenciaproblema;


alter table problema add responsabilidadeterceiros_aux clob null;
update problema set responsabilidadeterceiros_aux = responsabilidadeterceiros;
alter table problema drop column responsabilidadeterceiros;
alter table problema rename column responsabilidadeterceiros_aux to responsabilidadeterceiros;

-- autor:thays.araujo
-- data: 23/04/2013
alter table problema add ( idcausa int NULL);

alter table bpm_fluxo   add constraint fk_bpm_fluxo_bpm_tipofluxo  foreign key (idtipofluxo)  references bpm_tipofluxo (idtipofluxo );
alter table bpm_elementofluxo add constraint fk_bpm_elementofluxo_bpm_fluxo foreign key (idfluxo)  references bpm_fluxo (idfluxo );
alter table bpm_sequenciafluxo add constraint fk_bpm_sequenciafluxo_bpm_flux foreign key (idfluxo) references bpm_fluxo (idfluxo );

alter table problema add fechamento_aux clob null;
update problema set fechamento_aux = fechamento;
alter table problema drop column fechamento;
alter table problema rename column fechamento_aux to fechamento;

alter table problemaitemconfiguracao add descricaoproblema_aux clob null;
update problemaitemconfiguracao set descricaoproblema_aux = descricaoproblema;
alter table problemaitemconfiguracao drop column descricaoproblema;
alter table problemaitemconfiguracao rename column descricaoproblema_aux to descricaoproblema;

-- autor:thays.araujo
-- data: 06/05/2013
alter table problema add causaraiz_aux clob null;
update problema set causaraiz_aux = causaraiz;
alter table problema drop column causaraiz;
alter table problema rename column causaraiz_aux to causaraiz;

alter table problema add solucaocontorno_aux clob null;
update problema set solucaocontorno_aux = solucaocontorno;
alter table problema drop column solucaocontorno;
alter table problema rename column solucaocontorno_aux to solucaocontorno;

-- autor:thays.araujo
-- data: 09/05/2013

alter table categoriaproblema add ( idtemplate int NULL);

alter table categoriaproblema   
add constraint fk_categoriaproblema_templates  
foreign key (idtemplate )  
references templatesolicitacaoservico (idtemplate ) ;

create  table validacaorequisicaoproblema 
(  idvalidacaorequisicaoproblema int not null ,     
   observacaoproblema clob null ,  
   datainicio date not null ,  
   datafim date null ,  
   idproblema int null ,  
  primary key (idvalidacaorequisicaoproblema) ,  
  constraint fk_validacaorequisicaoproblema    foreign key (idproblema)    
  references problema (idproblema ));
  
-- autor:geber.costa
-- data: 14/05/2013
-- inicio
alter table problema add ( enviaemailprazosolucionarexpir char(1));
-- fim

-- autor: riubbe.oliveira
-- data: 15/05/2013
alter table problema add (confirmasolucaocontorno varchar(1));
alter table categoriaproblema modify (nomecategoria  null );

-- autor: thays.araujo
-- data: 15/05/2013
alter table problema add ( idunidade int NULL);

-- autor: thays.araujo
-- data: 16/05/2013
alter table problema add ( fase varchar(100) NULL);

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

-- MURILO ALMEIDA PACHECO CRIAÇÃƒO 
CREATE TABLE liberacaomudanca ( idliberacao integer, idrequisicaomudanca integer, idhistoricoliberacao integer DEFAULT NULL);

--caso a tabela exista ele cria a coluna idhistoricoliberacao
alter table liberacaomudanca add (idhistoricoliberacao number(6,0));
alter table liberacaomudanca drop constraint primary_161;
alter table liberacaomudanca modify (idliberacao default NULL);


-- oracle murilo almeida pacheco
-- criação da tabela HISTORICOLIBERACAO no oracle
CREATE TABLE HISTORICOLIBERACAO (
  IDHISTORICOLIBERACAO NUMBER(10,0) NOT NULL PRIMARY KEY,
  IDEXECUTORMODIFICACAO NUMBER(10,0) NOT NULL,
  DATAHORAMODIFICACAO DATE DEFAULT SYSDATE NULL,
  TIPOMODIFICACAO VARCHAR2(1) DEFAULT NULL,
  HISTORICOVERSAO FLOAT(126) DEFAULT NULL,
  IDLIBERACAO NUMBER(10,0) NOT NULL,
  IDSOLICITANTE NUMBER(10,0) NOT NULL,
  IDRESPONSAVEL NUMBER(10,0) DEFAULT NULL,
  TITULO VARCHAR2(100) NOT NULL,
  DESCRICAO NVARCHAR2(2000) NOT NULL,
  DATAINICIAL DATE DEFAULT SYSDATE NOT NULL,
  DATAFINAL DATE DEFAULT SYSDATE NOT NULL,
  DATALIBERACAO DATE DEFAULT SYSDATE NULL,
  SITUACAO CHAR(1) NOT NULL,
  RISCO CHAR(1) NOT NULL,
  VERSAO VARCHAR2(25) DEFAULT NULL,
  SEQREABERTURA NUMBER(6,0) DEFAULT NULL,
  ENVIAEMAILCRIACAO VARCHAR2(1) DEFAULT NULL,
  ENVIAEMAILACOES VARCHAR2(1) DEFAULT NULL,
  TEMPOATRASOHH NUMBER(6,0) DEFAULT NULL,
  TEMPOATRASOMM NUMBER(6,0) DEFAULT NULL,
  TEMPOCAPTURAHH NUMBER(6,0) DEFAULT NULL,
  TEMPOCAPTURAMM NUMBER(6,0) DEFAULT NULL,
  DATAHORATERMINO DATE DEFAULT SYSDATE NULL,
  DATAHORACONCLUSAO DATE DEFAULT SYSDATE NULL,
  STATUS VARCHAR2(45) DEFAULT NULL,
  TEMPODECORRIDOHH NUMBER(6,0) DEFAULT NULL,
  TEMPODECORRIDOMM NUMBER(6,0) DEFAULT NULL,
  TEMPOATENDIMENTOHH NUMBER(6,0) DEFAULT NULL,
  TEMPOATENDIMENTOMM NUMBER(6,0) DEFAULT NULL,
  DATAHORACAPTURA DATE DEFAULT SYSDATE NULL,
  DATAHORAREATIVACAO DATE DEFAULT SYSDATE NULL,
  DATAHORAINICIO DATE DEFAULT SYSDATE NULL,
  IDCALENDARIO NUMBER(10,0) DEFAULT NULL,
  DATAHORASUSPENSAO DATE DEFAULT SYSDATE NULL ,
  ENVIAEMAILFINALIZACAO VARCHAR2(1) DEFAULT NULL,
  PRAZOHH NUMBER(6,0) DEFAULT NULL,
  PRAZOMM NUMBER(6,0) DEFAULT NULL,
  IDPROPRIETARIO NUMBER(10,0) NOT NULL,
  DATAHORAINICIOAGENDADA DATE DEFAULT SYSDATE NULL ,
  DATAHORATERMINOAGENDADA DATE DEFAULT SYSDATE NULL,
  IDTIPOLIBERACAO NUMBER(10,0) DEFAULT NULL,
  IDGRUPOATUAL NUMBER(10,0) DEFAULT NULL,
  IDCONTATOREQUISICAOLIBERACAO NUMBER(10,0),
  TELEFONECONTATO VARCHAR2(45),
  RAMAL VARCHAR2(5),
  OBSERVACAO VARCHAR2(2000),
  IDUNIDADE NUMBER(10,0),
  NOMECONTATO2 VARCHAR2(80),
  EMAILCONTATO VARCHAR2(200),
  IDLOCALIDADE NUMBER(10,0)
);

-- CRIAÇÃƒO DA COLUNA BASELINE NA TABELA HISTORICOLIBERACAO NO ORACLE
ALTER TABLE HISTORICOLIBERACAO ADD BASELINE VARCHAR2(30) ;
ALTER TABLE HISTORICOLIBERACAO ADD (ALTERARSITUACAO VARCHAR(1));
ALTER TABLE HISTORICOLIBERACAO ADD (ACAOFLUXO VARCHAR(1));

-- criação da tabela requisicaoliberacaoitemconfiguracao para registrar os ics da liberacao.
CREATE TABLE REQLIBITEMCONFIGURACAO (
  IDREQLIBITEMCONFIGURACAO NUMBER(10,0) NOT NULL PRIMARY KEY,
  IDREQUISICAOLIBERACAO NUMBER(10,0) DEFAULT NULL,
  IDITEMCONFIGURACAO NUMBER(10,0) DEFAULT NULL,
  DESCRICAO VARCHAR(100) DEFAULT NULL,
  IDHISTORICOLIBERACAO NUMBER(10,0) DEFAULT NULL 
);

-- CRIAÇÃƒO DA TABELA CONHECIMENTO LIBERACAO
CREATE TABLE CONHECIMENTOLIBERACAO (
  IDREQUISICAOLIBERACAO NUMBER(10,0) NOT NULL,
  IDBASECONHECIMENTO NUMBER(10,0) NOT NULL,
  PRIMARY KEY (IDREQUISICAOLIBERACAO,IDBASECONHECIMENTO),
  CONSTRAINT FK_REF_CONHLIB_ICC FOREIGN KEY (IDREQUISICAOLIBERACAO) REFERENCES LIBERACAO (IDLIBERACAO),
  CONSTRAINT FK_REF_CONHLIB_BC FOREIGN KEY (IDBASECONHECIMENTO) REFERENCES BASECONHECIMENTO (IDBASECONHECIMENTO)
);

-- =======TIPO LIBERACAO======

CREATE TABLE TIPOLIBERACAO (
  IDTIPOLIBERACAO NUMBER(10,0) NOT NULL,
  IDTIPOFLUXO NUMBER(10,0) DEFAULT NULL,
  IDMODELOEMAILCRIACAO NUMBER(10,0) DEFAULT NULL,
  IDMODELOEMAILFINALIZACAO NUMBER(10,0) DEFAULT NULL,
  IDMODELOEMAILACOES NUMBER(10,0) DEFAULT NULL,
  IDGRUPOEXECUTOR NUMBER(10,0) DEFAULT NULL,
  IDCALENDARIO NUMBER(10,0) DEFAULT NULL,
  NOMETIPOLIBERACAO varchar(100) DEFAULT NULL,
  DATAINICIO date DEFAULT NULL,
  DATAFIM date DEFAULT NULL,
  PRIMARY KEY (IDTIPOLIBERACAO),
  CONSTRAINT TIPOLIBERACAO_IBFK_1 FOREIGN KEY (IDTIPOFLUXO) REFERENCES BPM_TIPOFLUXO (IDTIPOFLUXO),
  CONSTRAINT TIPOLIBERACAO_IBFK_2 FOREIGN KEY (IDMODELOEMAILACOES) REFERENCES MODELOSEMAILS (IDMODELOEMAIL),
  CONSTRAINT TIPOLIBERACAO_IBFK_5 FOREIGN KEY (IDGRUPOEXECUTOR) REFERENCES grupo (IDGRUPO),
  CONSTRAINT TIPOLIBERACAO_IBFK_6 FOREIGN KEY (IDCALENDARIO) REFERENCES CALENDARIO (IDCALENDARIO),
  CONSTRAINT TIPOLIBERACAO_IBFK_7 FOREIGN KEY (IDMODELOEMAILFINALIZACAO) REFERENCES MODELOSEMAILS (IDMODELOEMAIL)
);

-- ============EXECUCAO LIBERACAO===========

CREATE TABLE EXECUCAOLIBERACAO (
  IDEXECUCAO NUMBER(10,0) NOT NULL,
  IDINSTANCIAFLUXO NUMBER(20,0) NOT NULL,
  IDLIBERACAO NUMBER(10,0) NOT NULL,
  IDFLUXO NUMBER(20,0) NOT NULL,
  SEQREABERTURA NUMBER(6,0) DEFAULT NULL,
  PRIMARY KEY (IDEXECUCAO),
  CONSTRAINT FK_EXECLIB_REFERENCE_BPM_FLUX FOREIGN KEY (IDFLUXO) REFERENCES BPM_FLUXO (IDFLUXO),
  CONSTRAINT FK_EXECLIB_REFERENCE_BPM_INST FOREIGN KEY (IDINSTANCIAFLUXO) REFERENCES BPM_INSTANCIAFLUXO (IDINSTANCIA),
  CONSTRAINT FK_EXECLIB_REFERENCE_LIBERACAO FOREIGN KEY (IDLIBERACAO) REFERENCES LIBERACAO (IDLIBERACAO)
);

-- ============JUSTIFICATIVA LIBERACAO===========

CREATE TABLE JUSTIFICATIVALIBERACAO (
    IDJUSTIFICATIVALIBERACAO NUMBER(10,0) NOT NULL,
    DESCRICAOJUSTIFICATIVA VARCHAR(100) NOT NULL,
    SUSPENSAO CHAR(1) NOT NULL,
    SITUACAO CHAR(1) NOT NULL,
    APROVACAO CHAR(1) DEFAULT NULL,
    DELETED CHAR(1) DEFAULT NULL,
    PRIMARY KEY (IDJUSTIFICATIVALIBERACAO)
);


-- ====================== ALTER TABLE LIBERACAO ======================

ALTER TABLE LIBERACAO ADD ( SEQREABERTURA NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  ENVIAEMAILCRIACAO VARCHAR(1));
ALTER TABLE LIBERACAO ADD (  ENVIAEMAILACOES VARCHAR2(1));
ALTER TABLE LIBERACAO ADD (  TEMPOATRASOHH NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  TEMPOATRASOMM NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  TEMPOCAPTURAHH NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  TEMPOCAPTURAMM NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  DATAHORATERMINO TIMESTAMP NULL);
ALTER TABLE LIBERACAO ADD (  DATAHORACONCLUSAO TIMESTAMP NULL);
ALTER TABLE LIBERACAO ADD (  STATUS VARCHAR2(45));
ALTER TABLE LIBERACAO ADD (  TEMPODECORRIDOHH NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  TEMPODECORRIDOMM NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  TEMPOATENDIMENTOHH NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  TEMPOATENDIMENTOMM NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  DATAHORACAPTURA TIMESTAMP NULL);
ALTER TABLE LIBERACAO ADD (  DATAHORAREATIVACAO TIMESTAMP NULL);
ALTER TABLE LIBERACAO ADD (  DATAHORAINICIO TIMESTAMP NULL);
ALTER TABLE LIBERACAO ADD (  IDCALENDARIO NUMBER(10,0));
ALTER TABLE LIBERACAO ADD (  DATAHORASUSPENSAO TIMESTAMP NULL);
ALTER TABLE LIBERACAO ADD (  ENVIAEMAILFINALIZACAO VARCHAR(1));
ALTER TABLE LIBERACAO ADD (  PRAZOHH NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  PRAZOMM NUMBER(6,0));
ALTER TABLE LIBERACAO ADD (  IDPROPRIETARIO NUMBER(10,0) NOT NULL);
ALTER TABLE LIBERACAO ADD (  DATAHORAINICIOAGENDADA TIMESTAMP NULL);
ALTER TABLE LIBERACAO ADD (  DATAHORATERMINOAGENDADA TIMESTAMP NULL);
ALTER TABLE LIBERACAO ADD (  IDTIPOLIBERACAO NUMBER(10,0));
ALTER TABLE LIBERACAO ADD (  IDGRUPOATUAL NUMBER(10,0));
ALTER TABLE LIBERACAO ADD (  PRIORIDADE NUMBER(10,0) DEFAULT NULL);
ALTER TABLE LIBERACAO ADD (  NIVELURGENCIA VARCHAR(255) DEFAULT NULL);
ALTER TABLE LIBERACAO ADD (  NIVELIMPACTO VARCHAR(255) DEFAULT NULL);
ALTER TABLE LIBERACAO ADD (  IDAPROVADOR NUMBER(10,0)  DEFAULT NULL);
ALTER TABLE LIBERACAO ADD (  DATAHORAAPROVACAO TIMESTAMP NULL);
ALTER TABLE LIBERACAO ADD (  FECHAMENTO CLOB);
ALTER TABLE LIBERACAO ADD (  IDGRUPONIVEL1 NUMBER(10,0));
ALTER TABLE LIBERACAO ADD (  IDGRUPOAPROVADOR NUMBER(10,0));


ALTER TABLE LIBERACAO MODIFY (SITUACAO CHAR(1) DEFAULT NULL);

-- fim modelos emails

-- BPM TIPO FLUXO
INSERT INTO bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) VALUES (54, 'LiberacaoDeploy', 'Liberação Deploy', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoLiberacao');

-- BPM FLUXO
INSERT INTO bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) VALUES (50, '1.0', 54, 'requisicaoLiberacao;requisicaoLiberacao.status;requisicaoLiberacao.nomeGrupoAtual', '', '01/06/2013', NULL);

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
INSERT INTO tipoliberacao (idtipoliberacao, idtipofluxo, idmodeloemailcriacao, idmodeloemailfinalizacao, idmodeloemailacoes, idgrupoexecutor, idcalendario, nometipoliberacao, datainicio, datafim) VALUES (1,54,51,52,53,1,1,'Requisição Liberação Normal','01/06/2013',NULL);




-- fim modelos emails

-- EDMAR FAGUNDES - 16/05/2013 {
-- ADICIONA TABELA DE APROVAÇÃƒO PARA AUXILIAR NA PARTE DE PESQUISA
CREATE TABLE APROVACAOREQUISICAOLIBERACAO 
(
  IDAPROVACAOREQUISICAOLIBERACAO NUMBER(11, 0) NOT NULL 
, IDREQUISICAOLIBERACAO NUMBER(20) NOT NULL
, IDTAREFA NUMBER(20) NULL
, IDRESPONSAVEL NUMBER(11,0)  NOT NULL
, DATAHORA TIMESTAMP NULL
, IDJUSTIFICATIVA NUMBER(11, 0) NULL
, COMPLEMENTOJUSTIFICATIVA CLOB 
, OBSERVACOES CLOB
, APROVACAO CHAR(1) NOT NULL
, PRIMARY KEY(IDAPROVACAOREQUISICAOLIBERACAO)
);

-- ADICIONA TABELA DE CONTATO PARA SALVAR OS DADOS DO CONTATO AO CRIAR UMA NOVA LIBERAÇÃƒO
CREATE TABLE CONTATOREQUISICAOLIBERACAO 
(
  IDCONTATOREQUISICAOLIBERACAO NUMBER(11, 0) NOT NULL 
, NOMECONTATO VARCHAR2(70) NULL
, TELEFONECONTATO VARCHAR2(20) NULL
, EMAILCONTATO VARCHAR2(120) NULL
, OBSERVACAO CLOB 
, IDLOCALIDADE NUMBER(11, 0) NULL
, RAMAL VARCHAR2(4) NULL
, IDUNIDADE NUMBER(11, 0) NULL 
, PRIMARY KEY(IDCONTATOREQUISICAOLIBERACAO)
);


-- ADICIONA COLUNA IDCONTATOREQUISICAOLIBERACAO PARA FAZER A CONEXAO COM A TABELA CONTATOREQUISICAOLIBERACAO
ALTER TABLE LIBERACAO ADD IDCONTATOREQUISICAOLIBERACAO NUMBER(11,0) NULL ;
-- ADICIONA COLUNA IDULTIMAAPROVACAO PARA FAZER A CONEXAO COM A TABELA APROVACAOREQUISICAOLIBERACAO
ALTER TABLE LIBERACAO ADD IDULTIMAAPROVACAO NUMBER(11,0) NULL ;
-- }


--CRIAÇÃO TABELAOCORRENCIA LIBERACAO CASO NÃO EXISTA
CREATE TABLE OCORRENCIALIBERACAO
  (
    IDOCORRENCIA          NUMBER(6,0) NOT NULL ENABLE,
    IDITEMTRABALHO        NUMBER(6,0) DEFAULT NULL,
    IDJUSTIFICATIVA       NUMBER(6,0) DEFAULT NULL,
    IDREQUISICAOLIBERACAO NUMBER(6,0) DEFAULT NULL,
    DATAREGISTRO DATE DEFAULT NULL,
    HORAREGISTRO  VARCHAR2(5 BYTE) DEFAULT NULL,
    REGISTRADOPOR VARCHAR2(100 BYTE) DEFAULT NULL,
    DESCRICAO     VARCHAR2(200 BYTE) DEFAULT NULL,
    DATAINICIO DATE DEFAULT NULL,
    DATAFIM DATE DEFAULT NULL,
    COMPLEMENTOJUSTIFICATIVA VARCHAR2(1000),
    DADOSLIBERACAO CLOB,
    INFORMACOESCONTATO VARCHAR2(1000),
    CATEGORIA  VARCHAR2(20 BYTE) DEFAULT NULL,
    ORIGEM     CHAR(1 BYTE) DEFAULT NULL,
    TEMPOGASTO NUMBER(6,0) DEFAULT NULL,
    OCORRENCIA VARCHAR2(1000),
    IDCATEGORIAOCORRENCIA NUMBER(6,0) DEFAULT NULL,
    IDORIGEMOCORRENCIA    NUMBER(6,0) DEFAULT NULL,
    PRIMARY KEY (IDOCORRENCIA),
    FOREIGN KEY (IDREQUISICAOLIBERACAO) REFERENCES LIBERACAO (IDLIBERACAO),
    FOREIGN KEY (IDITEMTRABALHO) REFERENCES BPM_ITEMTRABALHOFLUXO (IDITEMTRABALHO)
  );

-- FIM - MODULO DE LIBERACAO --

set define on;

-- INSERT CATEGORIA IMAGEM
INSERT INTO categoriagaleriaimagem VALUES(1, 'EMAIL', null, sysdate);
-- FIM

-- autor: Carlos Santos
-- Data: 16/05/2013

CREATE TABLE solicitacaoservicoquestionario (
  idsolicitacaoquestionario integer NOT NULL,
  idquestionario integer NOT NULL,
  idsolicitacaoservico integer NOT NULL,
  dataquestionario timestamp NOT NULL,
  idresponsavel integer NOT NULL,
  idtarefa integer DEFAULT NULL,
  aba varchar2(100) DEFAULT NULL,
  situacao char(1) NOT NULL,
  datahoragrav timestamp NOT NULL,
  conteudoimpresso clob
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

alter table grupoquestionario
   drop constraint fk_grupoque_reference_question;

alter table opcaorespostaquestionario
   drop constraint fk_opcaores_reference_questaoq;

alter table opcaorespostaquestionario
   drop constraint fk_opcaores_ref_questaoq;

alter table questaoquestionario
   drop constraint fk_questaoagrupadora;

alter table questaoquestionario
   drop constraint fk_questaoq_reference_question;

alter table questaoquestionario
   drop constraint fk_questaoq_reference_grupoque;

alter table questionario
   drop constraint fk_question_reference_categori;

alter table questionario
   drop constraint fk_questionarioorigem;

alter table respostaitemquestionario
   drop constraint fk_resposta_reference_questaoq;

alter table respostaitemquestionarioanexos
   drop constraint fk_rspt_anx_reference_rspt_itm;

alter table respostaitemquestionarioopcoes
   drop constraint fk_rspta_q_reference_rspta_itm;

alter table respostaitemquestionarioopcoes
   drop constraint fk_resposta_reference_opcaores;

drop index ix_idquestaoorigem;

drop index ix_sigla_questao;

drop index ix_idquestao;

drop index ix_ident_questao;

create table categoriaquestionario  (
   idcategoriaquestionario integer                         not null,
   nomecategoriaquestionario varchar2(50)                    not null
);

alter table categoriaquestionario
   add constraint pk_categoriaquestionario primary key (idcategoriaquestionario);

create table grupoquestionario  (
   idgrupoquestionario integer                         not null,
   idquestionario     integer                         not null,
   nomegrupoquestionario varchar2(80)                    not null,
   ordem              smallint
);

alter table grupoquestionario
   add constraint pk_grupoquestionario primary key (idgrupoquestionario);

create table opcaorespostaquestionario  (
   idopcaorespostaquestionario integer                         not null,
   idquestaoquestionario integer                         not null,
   titulo             varchar2(255)                   not null,
   peso               integer,
   valor              varchar2(50),
   geraalerta         char(1),
   exibecomplemento   char(1),
   idquestaocomplemento integer
);

alter table opcaorespostaquestionario
   add constraint pk_opcaorespostaquestionario primary key (idopcaorespostaquestionario);

create table questaoquestionario  (
   idquestaoquestionario integer                         not null,
   idgrupoquestionario integer,
   idquestaoagrupadora integer,
   idquestaoorigem    integer,
   tipo               char(1)                         not null,
   tituloquestaoquestionario clob                            not null,
   tipoquestao        char(1)                         not null,
   sequenciaquestao   integer                         not null,
   valordefault       clob,
   textoinicial       clob,
   tamanho            integer,
   decimais           integer,
   inforesposta       char(1)                        
      constraint ckc_inforesposta_questaoq check (inforesposta is null or (inforesposta in ('L','B'))),
   valoresreferencia  clob,
   unidade            clob,
   obrigatoria        char(1)                         not null
      constraint ckc_obrigatoria_questaoq check (obrigatoria in ('S','N')),
   ponderada          char(1),
   qtdelinhas         integer,
   qtdecolunas        integer,
   cabecalholinhas    char(1),
   cabecalhocolunas   char(1),
   nomelistagem       varchar2(30),
   ultimovalor        char(1),
   idsubquestionario  integer,
   abaresultsubform   varchar2(200),
   sigla              varchar2(100),
   imprime            char(1),
   calculada          char(1),
   editavel           char(1),
   valorpermitido1    number(15,5),
   valorpermitido2    number(15,5),
   idimagem           integer
);

alter table questaoquestionario
   add constraint pk_questaoquestionario primary key (idquestaoquestionario);

create index ix_sigla_questao on questaoquestionario (
   sigla asc
);

create index ix_idquestaoorigem on questaoquestionario (
   idquestaoorigem asc
);

create table questionario  (
   idquestionario     integer                         not null,
   idquestionarioorigem integer,
   idcategoriaquestionario integer                         not null,
   nomequestionario   varchar2(50)                    not null,
   idempresa          integer                         not null,
   ativo              char(1)                        default 'S' not null,
   javascript         clob
);

alter table questionario
   add constraint pk_questionario primary key (idquestionario);

create table respostaitemquestionario  (
   idrespostaitemquestionario integer                         not null,
   ididentificadorresposta integer                         not null,
   idquestaoquestionario integer                         not null,
   sequencialresposta integer,
   respostatextual    clob,
   respostapercentual number(15,5),
   respostavalor      number(15,5),
   respostavalor2     number(15,5),
   respostanumero     number(8,0),
   respostanumero2    number(8,0),
   respostadata       date,
   respostahora       varchar2(4),
   respostames        smallint,
   respostaano        smallint,
   respostaidlistagem varchar2(10),
   respostadia        smallint
);

alter table respostaitemquestionario
   add constraint pk_respostaitemquestionario primary key (idrespostaitemquestionario);

create index ix_ident_questao on respostaitemquestionario (
   ididentificadorresposta asc,
   idquestaoquestionario asc
);

create index ix_idquestao on respostaitemquestionario (
   idquestaoquestionario asc
);

create table respostaitemquestionarioanexos  (
   idrespostaitmquestionarioanexo integer                         not null,
   idrespostaitemquestionario integer                         not null,
   caminhoanexo       varchar2(255)                   not null,
   observacao         clob
);

alter table respostaitemquestionarioanexos
   add constraint pk_respostaitemquestionarioane primary key (idrespostaitmquestionarioanexo);

create table respostaitemquestionarioopcoes  (
   idrespostaitemquestionario integer                         not null,
   idopcaorespostaquestionario integer                         not null
);

alter table respostaitemquestionarioopcoes
   add constraint pk_respostaitemquestionarioopc primary key (idrespostaitemquestionario, idopcaorespostaquestionario);

alter table grupoquestionario
   add constraint fk_grupoque_reference_question foreign key (idquestionario)
      references questionario (idquestionario);

alter table opcaorespostaquestionario
   add constraint fk_opcaores_reference_questaoq foreign key (idquestaocomplemento)
      references questaoquestionario (idquestaoquestionario);

alter table opcaorespostaquestionario
   add constraint fk_opcaores_ref_questaoq foreign key (idquestaoquestionario)
      references questaoquestionario (idquestaoquestionario)
      on delete cascade;

alter table questaoquestionario
   add constraint fk_questaoagrupadora foreign key (idquestaoagrupadora)
      references questaoquestionario (idquestaoquestionario);

alter table questaoquestionario
   add constraint fk_questaoq_reference_question foreign key (idsubquestionario)
      references questionario (idquestionario);

alter table questaoquestionario
   add constraint fk_questaoq_reference_grupoque foreign key (idgrupoquestionario)
      references grupoquestionario (idgrupoquestionario);

alter table questionario
   add constraint fk_question_reference_categori foreign key (idcategoriaquestionario)
      references categoriaquestionario (idcategoriaquestionario);

alter table questionario
   add constraint fk_questionarioorigem foreign key (idquestionarioorigem)
      references questionario (idquestionario);

alter table respostaitemquestionario
   add constraint fk_resposta_reference_questaoq foreign key (idquestaoquestionario)
      references questaoquestionario (idquestaoquestionario);

alter table respostaitemquestionarioanexos
   add constraint fk_rspt_anx_reference_rspt_itm foreign key (idrespostaitemquestionario)
      references respostaitemquestionario (idrespostaitemquestionario)
      on delete cascade;

alter table respostaitemquestionarioopcoes
   add constraint fk_rspta_q_reference_rspta_itm foreign key (idrespostaitemquestionario)
      references respostaitemquestionario (idrespostaitemquestionario)
      on delete cascade;

alter table respostaitemquestionarioopcoes
   add constraint fk_resposta_reference_opcaores foreign key (idopcaorespostaquestionario)
      references opcaorespostaquestionario (idopcaorespostaquestionario);


ALTER TABLE templatesolicitacaoservico ADD idquestionario INTEGER NULL;
alter table templatesolicitacaoservico
   add constraint fk_questionario foreign key (idquestionario)
      references questionario (idquestionarioorigem);
 
ALTER TABLE templatesolicitacaoservico MODIFY nomeclassedto VARCHAR2(255) NULL;
ALTER TABLE templatesolicitacaoservico MODIFY nomeclasseaction VARCHAR2(255) NULL;
ALTER TABLE templatesolicitacaoservico MODIFY nomeclasseservico VARCHAR2(255) NULL;
ALTER TABLE templatesolicitacaoservico MODIFY urlrecuperacao VARCHAR2(255) NULL;
 
-- autor:thays.araujo
-- data: 20/05/2013
alter table problema add ( idcategoriasolucao int);
alter table problema add ( precisasolucaocontorno char(1));
alter table problema add ( resolvido char(1) );
 
-- TABELA PROJETOS
ALTER TABLE projetos ADD (IDPROJETOPAI NUMBER(5));

-- INICIO - MODULO DE MUDANCA

ALTER TABLE aprovacaomudanca ADD (dataHoraVotacao character varying(25));
ALTER TABLE requisicaomudanca ADD razaomudanca VARCHAR(200);

CREATE TABLE risco
(
  idrisco integer NOT NULL,
  nomerisco character varying(150) NOT NULL,
  detalhamento VARCHAR (2000),
  nivelrisco integer,
  datainicio date,
  datafim date,
  CONSTRAINT risco_pkey PRIMARY KEY (idrisco)
);

CREATE TABLE requisicaomudancarisco
(
  idrequisicaomudancarisco integer NOT NULL,
  idrequisicaomudanca integer NOT NULL,
  idrisco integer NOT NULL,
  CONSTRAINT requisicaomudancarisco_pkey PRIMARY KEY (idrequisicaomudancarisco),
  CONSTRAINT reqomudrisco_idreqmud_fkey FOREIGN KEY (idrequisicaomudanca)
      REFERENCES requisicaomudanca (idrequisicaomudanca) 
);


CREATE TABLE requisicaomudancaliberacao
(
  idrequisicaomudancaliberacao integer NOT NULL,
  idrequisicaomudanca integer NOT NULL,
  idliberacao integer NOT NULL,
  CONSTRAINT rqmudancadliberacao_pkey PRIMARY KEY (idrequisicaomudancaliberacao),
  CONSTRAINT reqmudanca_idreqmud_fkey FOREIGN KEY (idrequisicaomudanca)
      REFERENCES requisicaomudanca (idrequisicaomudanca),
  CONSTRAINT reqliberacao_idreqmud_fkey FOREIGN KEY (idliberacao)
      REFERENCES liberacao (idliberacao) 
);

alter table requisicaomudanca modify (DATAHORATERMINO NULL);
alter table requisicaomudanca modify (DATAHORAREATIVACAO NULL);
alter table requisicaomudanca modify (DATAHORASUSPENSAO NULL);
alter table requisicaomudanca modify (DATAHORACAPTURA NULL);
alter table requisicaomudanca modify (DATAHORACONCLUSAO NULL);
alter table REQUISICAOMUDANCAITEMCONFIGURA RENAME COLUMN idrequisicaomudancaitemconfigu to idrequisicaomudancaitemconfig;

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

CREATE TABLE TIPOSUBSCRICAO 
   (	IDTIPOSUBSCRICAO NUMBER(10,0) NOT NULL ENABLE, 
	NOMETIPOSUBSCRICAO VARCHAR2(250 BYTE), 
	 CONSTRAINT TIPOSUBSCRICAO_PK PRIMARY KEY (IDTIPOSUBSCRICAO)
   );
   
   
  CREATE TABLE CONTROLECONTRATO 
   (	IDCONTROLECONTRATO NUMBER(10,0) NOT NULL ENABLE, 
	IDCONTRATO NUMBER(10,0), 
	NUMEROSUBSCRICAO VARCHAR2(250 BYTE), 
	ENDERECO VARCHAR2(250 BYTE), 
	CONTATO VARCHAR2(250 BYTE), 
	EMAIL VARCHAR2(250 BYTE), 
	TELEFONE1 VARCHAR2(250 BYTE), 
	TELEFONE2 VARCHAR2(250 BYTE), 
	TIPOSUBSCRICAO NUMBER(10,0), 
	URL VARCHAR2(250 BYTE), 
	LOGIN VARCHAR2(250 BYTE), 
	SENHA VARCHAR2(250 BYTE), 
	DATAINICIO DATE, 
	DATAFIM DATE, 
	CLIENTE VARCHAR2(250 BYTE), 
	 CONSTRAINT CONTROLECONTRATO_PK PRIMARY KEY (IDCONTROLECONTRATO) ENABLE, 
  
	 CONSTRAINT CONTROLECONTRATO_CONTRATO_FK1 FOREIGN KEY (IDCONTRATO)
	  REFERENCES CONTRATOS (IDCONTRATO) ENABLE, 
    
	 CONSTRAINT CONTROLECONTRATO_TS FOREIGN KEY (TIPOSUBSCRICAO)
	  REFERENCES TIPOSUBSCRICAO (IDTIPOSUBSCRICAO) ENABLE
   );
 
--  TABLE CONTROLECONTRATOOCORRENCIA	  
 CREATE TABLE CONTROLECONTRATOOCORRENCIA 
   (	IDCCOCORRENCIA NUMBER(10,0) NOT NULL ENABLE, 
	ASSUNTOCCOCORRENCIA VARCHAR2(250 BYTE), 
	IDEMPREGADOOCORRENCIA NUMBER(10,0), 
	IDCONTROLECONTRATO NUMBER(10,0), 
	DATACCOCORRENCIA DATE, 
	 CONSTRAINT CONTROLECONTRATOOCORRENCI_PK PRIMARY KEY (IDCCOCORRENCIA), 
	 CONSTRAINT CONTROLECONTRATOOCORRENCI_FK1 FOREIGN KEY (IDCONTROLECONTRATO)
	  REFERENCES CONTROLECONTRATO (IDCONTROLECONTRATO)
   ) ;
--  TABLE CONTROLECONTRATOPAGAMENTO
     CREATE TABLE CONTROLECONTRATOPAGAMENTO 
   (	IDCCPAGAMENTO NUMBER(10,0) NOT NULL ENABLE, 
	PARCELACCPAGAMENTO NUMBER(10,0), 
	IDCONTROLECONTRATO NUMBER(10,0), 
	DATAATRASOCCPAGAMENTO DATE, 
	DATACCPAGAMENTO DATE, 
	 CONSTRAINT CONTROLECONTRATOPAGAMENTO_PK PRIMARY KEY (IDCCPAGAMENTO), 
	 CONSTRAINT CONTROLECONTRATOPAG_FK_CC FOREIGN KEY (IDCONTROLECONTRATO)
	  REFERENCES CONTROLECONTRATO (IDCONTROLECONTRATO) ENABLE
   );
--  TABLE CONTROLECONTRATOTREINAMENTO
CREATE TABLE CONTROLECONTRATOTREINAMENTO 
   (	IDCCTREINAMENTO NUMBER(10,0) NOT NULL ENABLE, 
	IDCONTROLECONTRATO NUMBER(10,0), 
	IDEMPREGADOTREINAMENTO NUMBER(10,0), 
	NOMECCTREINAMENTO VARCHAR2(250 BYTE), 
	DATACCTREINAMENTO DATE, 
	 CONSTRAINT CONTROLECONTRATOTREINA_PK_CC PRIMARY KEY (IDCCTREINAMENTO), 
  
	 CONSTRAINT CONTROLECONTRATOTREINA_FK_CC FOREIGN KEY (IDCONTROLECONTRATO)
	  REFERENCES CONTROLECONTRATO (IDCONTROLECONTRATO) ENABLE, 
    
	 CONSTRAINT CONTROLECONTRATOTREINA_FK_EMP FOREIGN KEY (IDEMPREGADOTREINAMENTO)
	  REFERENCES EMPREGADOS (IDEMPREGADO) ENABLE
   );
--  TABLE CONTROLECONTRATOVERSAO
     CREATE TABLE CONTROLECONTRATOVERSAO 
   (	IDCONTROLECONTRATO NUMBER(10,0), 
	IDCCVERSAO NUMBER(10,0), 
	NOMECCVERSAO VARCHAR2(100 BYTE)
   ) ;

  CREATE UNIQUE INDEX CONTROLECONTRATOVERSAO_PK ON CITSMART.CONTROLECONTRATOVERSAO (IDCCVERSAO);
  ALTER TABLE CONTROLECONTRATOVERSAO ADD CONSTRAINT CONTROLECONTRATOVERSAO_PK PRIMARY KEY (IDCCVERSAO);
  ALTER TABLE CONTROLECONTRATOVERSAO MODIFY (IDCCVERSAO NOT NULL ENABLE);
  ALTER TABLE CONTROLECONTRATOVERSAO ADD CONSTRAINT CONTROLECONTRATOVERSAO_CO_FK1 FOREIGN KEY (IDCONTROLECONTRATO)
	  REFERENCES CONTROLECONTRATO (IDCONTROLECONTRATO) ENABLE;
   
    CREATE TABLE MODULOSISTEMA 
   (	IDMODULOSISTEMA NUMBER(10,0) NOT NULL ENABLE, 
	NOMEMODULOSISTEMA VARCHAR2(250 BYTE),
  
	 CONSTRAINT MODULOSISTEMA_PK PRIMARY KEY (IDMODULOSISTEMA)
   );
   --INSERTS TABLE "MODULOSISTEMA" 
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
INSERT INTO MODULOSISTEMA VALUES(13, 'LIBERAÇÃƒO');
INSERT INTO MODULOSISTEMA VALUES(14, 'DISPONIBILIDADE');
--  TABLE CCMODULOSISTEMA
 CREATE TABLE CCMODULOSISTEMA 
   (	IDMODULOSISTEMA NUMBER(10,0) NOT NULL ENABLE, 
	IDCONTROLECONTRATO NUMBER(10,0), 
	 
   CONSTRAINT CCMODULOSISTEMA_PK PRIMARY KEY (IDMODULOSISTEMA) ENABLE, 
   CONSTRAINT CCMODULOSISTEMA_CONTROLEC_FK1 FOREIGN KEY (IDCONTROLECONTRATO)
	  REFERENCES CONTROLECONTRATO (IDCONTROLECONTRATO) ENABLE
   );
   
   
-- table netmap

ALTER TABLE netmap ADD (datainventario date);

CREATE TABLE MIDIASOFTWARECHAVE 
(
  IDMIDIASOFTWARECHAVE NUMBER 
, IDMIDIASOFTWARE NUMBER 
, CHAVE VARCHAR2(255) 
);

ALTER TABLE MIDIASOFTWARECHAVE  
MODIFY (IDMIDIASOFTWARECHAVE NOT NULL);

ALTER TABLE MIDIASOFTWARECHAVE
ADD CONSTRAINT MIDIASOFTWARECHAVE_PK PRIMARY KEY 
(
  IDMIDIASOFTWARECHAVE 
)
ENABLE;

ALTER TABLE NETMAP RENAME COLUMN DATA TO DATE_;

ALTER TABLE TIPOITEMCONFIGURACAO ADD (IMAGEM VARCHAR2(255) );

-- alterar tabela parametrocorpore
alter table parametrocorpore modify valor null;

-- INICIO - MURILO GABRIEL RODRIGUES - 27/05/2013

alter table fluxoservico add idfluxoservico number(10,0);
create sequence rownumseq;
update fluxoservico set idfluxoservico = rownumseq.nextval;
drop sequence rownumseq;
alter table fluxoservico drop primary key;
alter table fluxoservico add primary key (idfluxoservico);
alter table fluxoservico add constraint servicocontrato_fk foreign key (idservicocontrato) references servicocontrato (idservicocontrato);
alter table fluxoservico add constraint tipofluxo_fk foreign key (idtipofluxo) references bpm_tipofluxo (idtipofluxo);
alter table fluxoservico add constraint faseservico_fk foreign key (idfase) references faseservico (idfase);

-- FIM - MURILO GABRIEL RODRIGUES - 27/05/2013

-- INICIO - MURILO GABRIEL RODRIGUES - 05/06/2013
ALTER TABLE versao ADD (idusuario NUMBER(10,0));
-- FIM - MURILO GABRIEL RODRIGUES - 05/06/2013

-- Murilo Pacheco - 05/06/2013
alter table bpm_itemtrabalhofluxo modify situacao varchar(20);
-- fim

-- Flávio Júnior - 06/06/2013
ALTER TABLE MIDIASOFTWARECHAVE ADD (QTDPERMISSOES NUMBER);

ALTER TABLE ITEMCONFIGURACAO ADD (DTULTIMACAPTURA TIMESTAMP);

ALTER TABLE HISTORICOIC ADD (DTULTIMACAPTURA TIMESTAMP);

-- Módulo de mudança
ALTER TABLE REQUISICAOMUDANCA ADD (EHPROPOSTAAUX CHAR(1));

ALTER TABLE REQUISICAOMUDANCA ADD (VOTACAOPROPOSTAAPROVADAAUX CHAR(1));

create table aprovacaoproposta (
    idaprovacaoproposta number(10,0) not null,
    idrequisicaomudanca number(10,0) null,
    idempregado number(10,0) null,
    nomeempregado varchar2(45) null,
    voto char(1) null,
    comentario varchar2(200) null,
    dataHoraVotacao character varying(25),
    datahorainicio date null
);

-- Fim