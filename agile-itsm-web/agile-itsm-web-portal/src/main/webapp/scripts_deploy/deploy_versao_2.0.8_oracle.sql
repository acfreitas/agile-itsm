-- Oracle

set define off;

-- Início Carlos Santos 16/05/13 autorizado 28/06/13

alter table templatesolicitacaoservico  drop constraint fk_questionario;

alter table solicitacaoservicoquestionario  drop constraint  fk_solquest_questionario;

drop table respostaitemquestionarioanexos;
drop table respostaitemquestionarioopcoes;
drop table opcaorespostaquestionario;
drop table respostaitemquestionario;
drop table questaoquestionario;
drop table grupoquestionario;
drop table questionario;

create table questionario (
   idquestionario     integer not null,
   idquestionarioorigem integer,
   idcategoriaquestionario integer not  null,
   nomequestionario   varchar2(50) not null,
   idempresa          integer not null,
   ativo              char(1) default 'S' not null,
   javascript         clob
);

alter table questionario add constraint pk_questionario primary key (idquestionario);

alter table questionario add constraint fk_question_reference_categori foreign key (idcategoriaquestionario) references categoriaquestionario (idcategoriaquestionario);

alter table questionario add constraint fk_questionarioorigem foreign key (idquestionarioorigem) references questionario (idquestionario);

create table grupoquestionario (
   idgrupoquestionario integer not null,
   idquestionario integer not null,
   nomegrupoquestionario varchar2(80) not null,
   ordem smallint
);

alter table grupoquestionario add constraint pk_grupoquestionario primary key (idgrupoquestionario);

alter table grupoquestionario add constraint fk_grupoque_reference_question foreign key (idquestionario) references questionario (idquestionario);

create table questaoquestionario (
   idquestaoquestionario integer not null,
   idgrupoquestionario integer,
   idquestaoagrupadora integer,
   idquestaoorigem    integer,
   tipo               char(1) not null,
   tituloquestaoquestionario clob not null,
   tipoquestao        char(1) not null,
   sequenciaquestao   integer not null,
   valordefault       clob,
   textoinicial       clob,
   tamanho            integer,
   decimais           integer,
   inforesposta       char(1)                        
      constraint ckc_inforesposta_questaoq check (inforesposta is null or (inforesposta in ('L','B'))),
   valoresreferencia  clob,
   unidade            clob,
   obrigatoria        char(1) not null
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

alter table questaoquestionario add constraint pk_questaoquestionario primary key (idquestaoquestionario);

alter table questaoquestionario add constraint fk_questaoagrupadora foreign key (idquestaoagrupadora) references questaoquestionario (idquestaoquestionario);

alter table questaoquestionario add constraint fk_questaoq_reference_question foreign key (idsubquestionario) references questionario (idquestionario);

alter table questaoquestionario add constraint fk_questaoq_reference_grupoque foreign key (idgrupoquestionario) references grupoquestionario (idgrupoquestionario);

create table respostaitemquestionario (
   idrespostaitemquestionario integer not null,
   ididentificadorresposta integer not null,
   idquestaoquestionario integer not null,
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

alter table respostaitemquestionario add constraint pk_respostaitemquestionario primary key (idrespostaitemquestionario);

alter table respostaitemquestionario add constraint fk_resposta_reference_questaoq foreign key (idquestaoquestionario) references questaoquestionario (idquestaoquestionario);

create index ix_ident_questao on respostaitemquestionario (
   ididentificadorresposta asc,
   idquestaoquestionario asc
);

create index  ix_idquestao on  respostaitemquestionario (
   idquestaoquestionario asc
);

create table opcaorespostaquestionario (
   idopcaorespostaquestionario integer not null,
   idquestaoquestionario integer not null,
   titulo             varchar2(255) not null,
   peso               integer,
   valor              varchar2(50),
   geraalerta         char(1),
   exibecomplemento   char(1),
   idquestaocomplemento integer
);

alter table opcaorespostaquestionario add constraint pk_opcaorespostaquestionario primary key (idopcaorespostaquestionario);

alter table opcaorespostaquestionario add constraint fk_opcaores_reference_questaoq foreign key (idquestaocomplemento) references questaoquestionario (idquestaoquestionario);

alter table opcaorespostaquestionario add constraint fk_opcaores_ref_questaoq foreign key (idquestaoquestionario) references questaoquestionario (idquestaoquestionario) on delete cascade;

create index ix_sigla_questao on questaoquestionario (
   sigla asc
);

create index ix_idquestaoorigem on questaoquestionario (
   idquestaoorigem asc
);

create table respostaitemquestionarioopcoes (
   idrespostaitemquestionario integer not null,
   idopcaorespostaquestionario integer not null
);

alter table respostaitemquestionarioopcoes add constraint pk_respostaitemquestionarioopc primary key (idrespostaitemquestionario, idopcaorespostaquestionario);

alter table respostaitemquestionarioopcoes add constraint fk_rspta_q_reference_rspta_itm foreign key (idrespostaitemquestionario) references respostaitemquestionario (idrespostaitemquestionario) on delete cascade;

alter table respostaitemquestionarioopcoes add constraint fk_resposta_reference_opcaores foreign key (idopcaorespostaquestionario) references opcaorespostaquestionario (idopcaorespostaquestionario);

create table respostaitemquestionarioanexos (
   idrespostaitmquestionarioanexo integer not null,
   idrespostaitemquestionario integer not null,
   caminhoanexo varchar2(255) not null,
   observacao clob
);

alter table respostaitemquestionarioanexos add constraint pk_respostaitemquestionarioane primary key (idrespostaitmquestionarioanexo);

alter table respostaitemquestionarioanexos add constraint fk_rspt_anx_reference_rspt_itm foreign key (idrespostaitemquestionario) references respostaitemquestionario (idrespostaitemquestionario) on delete cascade;

create table requisicaoquestionario (
	idrequisicaoquestionario NUMBER(10,0),
	idquestionario NUMBER(10,0), 
	idtiporequisicao NUMBER(10,0),
	idtipoaba NUMBER(10,0),
	idrequisicao NUMBER(10,0),
	dataquestionario DATE,
	idresponsavel NUMBER(10,0),
	idtarefa NUMBER(38,0),
	aba varchar(100),
	situacao varchar(1), 
	datahoragrav DATE,
	conteudoimpresso clob, 
	confirmacao varchar(1), 
	CONSTRAINT req_pkey PRIMARY KEY (idrequisicaoquestionario),
	CONSTRAINT req_idquestionario_fkey FOREIGN KEY (idquestionario) REFERENCES questionario (idquestionario),
	CONSTRAINT req_idtarefa_fkey FOREIGN KEY (idtarefa) REFERENCES bpm_itemtrabalhofluxo (iditemtrabalho)
);

INSERT INTO templatesolicitacaoservico (idtemplate, identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar) VALUES (13,'ChecklistQuestionario','Checklist Questionario',' ','br.com.centralit.citcorpore.ajaxForms.ChecklistQuestionario','br.com.centralit.citcorpore.negocio.ChecklistQuestionarioServiceEjb','/pages/checklistQuestionario/checklistQuestionario.load', '','N','S','N',820,'N','N','N','N','N','N','S');

INSERT INTO categoriaquestionario (idcategoriaquestionario,nomecategoriaquestionario, idempresa) VALUES (1,'CONTRATOS',1);

-- Fim Carlos Santos

-- Início Maycon

create table requisicaoliberacaomidia (
  idRequisicaoLiberacaoMidia int not null,
  idMidiaSoftware int not null,
  idRequisicaoLiberacao int not null
);

alter table requisicaoliberacaomidia add constraint pk_requisicaoliberacaomidia primary key (idRequisicaoLiberacaoMidia);

update bpm_fluxo set datafim = '10-06-2013' where idtipofluxo = 54 and idfluxo <> 62;

INSERT INTO bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) VALUES (62, '2.0', 54, 'requisicaoLiberacao;requisicaoLiberacao.status;requisicaoLiberacao.nomeGrupoAtual', '', '10-06-2013', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (631, 62, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 98, 144, 32, 32, '', '', NULL, '', '', '', '', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (632, 62, 'Tarefa', '', 'Liberar', 'Liberar', 'U', 'pages/requisicaoLiberacao/requisicaoLiberacao.load?escalar=S&alterarSituacao=S', '', NULL, '#{requisicaoLiberacao.usuarioSolicitante}', '', '', '', '', '', 243, 127, 65, 140, '', '', NULL, '', '', '', 'S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (633, 62, 'Tarefa', '', 'Execução', 'Execução', 'U', 'pages/requisicaoLiberacao/requisicaoLiberacao.load?escalar=S&alterarSituacao=S', '', '#{requisicaoLiberacao.nomeGrupoAtual}', '', '', '', '', '', '', 579, 0, 65, 140, '', '', NULL, '', '', '', 'S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (634, 62, 'Tarefa', '', 'Teste', 'Teste', 'U', 'pages/requisicaoLiberacao/requisicaoLiberacao.load?escalar=S&alterarSituacao=S', '', '#{requisicaoLiberacao.nomeGrupoAtual}', '', '', '', '', '', '', 808, 51, 65, 140, '', '', NULL, '', '', '', 'S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (635, 62, 'Tarefa', '', 'Homologação', 'Homologação', 'U', 'pages/requisicaoLiberacao/requisicaoLiberacao.load?escalar=S&alterarSituacao=S', '', '#{requisicaoLiberacao.nomeGrupoAtual}', '', '', '', '', '', '', 621, 249, 65, 140, '', '', NULL, '', '', '', 'S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (636, 62, 'Tarefa', '', 'Resolvido', 'Resolvido', 'U', 'pages/requisicaoLiberacao/requisicaoLiberacao.load?escalar=S&alterarSituacao=S', '', NULL, '#{requisicaoLiberacao.usuarioSolicitante}', '', '#{execucaoFluxo}.finalizaIC(#{requisicaoLiberacao});', '', '', '', 70, 328, 65, 140, '', '', NULL, '', '', '', 'S', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (637, 62, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 561, 137, 42, 42, '', '', NULL, '', '', '', '', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (638, 62, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 856, 215, 42, 42, '', '', NULL, '', '', '', '', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (639, 62, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 294, 340, 42, 42, '', '', NULL, '', '', '', '', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (640, 62, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 670, 337, 42, 42, '', '', NULL, '', '', '', '', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (641, 62, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 292, 45, 42, 42, '', '', NULL, '', '', '', '', NULL);
INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (642, 62, 'Finalizacao', '', '', '', '', '', '', '', '', '', '', '', '', '', 123, 241, 32, 32, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (631, 632, 62, NULL, NULL, '', 1, 3, 186.5, 159.75, '', '');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (632, 641, 62, NULL, NULL, '', 0, 2, 313, 107, '', '');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (633, 637, 62, NULL, NULL, '', 2, 1, 649, 159, 'S', '');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (634, 638, 62, NULL, NULL, '', 2, 0, 877.5, 165.5, '', '');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (635, 640, 62, NULL, NULL, '', 2, 0, 691, 325.5, '', '');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (636, 642, 62, NULL, NULL, '', 0, 2, 139.5, 300.5, '', '');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (637, 632, 62, NULL, NULL, '#{requisicaoLiberacao}.naoResolvida();', 3, 1, 472, 158.75, '', 'Não Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (637, 634, 62, NULL, NULL, '!#{requisicaoLiberacao}.naoResolvida();', 2, 3, 799, 189, 'S', 'Em Execução');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (638, 632, 62, NULL, NULL, '#{requisicaoLiberacao}.naoResolvida();', 3, 1, 395, 238, 'S', 'Não Resolvido');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (638, 635, 62, NULL, NULL, '!#{requisicaoLiberacao}.naoResolvida();', 2, 1, 878, 280, 'S', 'Em Execução');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (639, 632, 62, NULL, NULL, '!#{requisicaoLiberacao}.atendida();', 0, 2, 314, 266, '', 'Não Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (639, 636, 62, NULL, NULL, '#{requisicaoLiberacao}.atendida();', 3, 1, 252, 360.75, '', 'Resolvida');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (640, 638, 62, NULL, NULL, '#{requisicaoLiberacao}.emAtendimento();', 1, 1, 914, 359, 'S', 'Em Execução');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (640, 639, 62, NULL, NULL, '#{requisicaoLiberacao}.liberada();', 3, 1, 503, 359.5, '', 'Liberar');
INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (641, 633, 62, NULL, NULL, '', 0, 3, 440, 32.75, '', '');

-- Fim Maycon

-- Início Bruno

-- FLUXO MÓDULO DE MUDANÇA

UPDATE bpm_fluxo SET datafim = '10-06-2013' WHERE idtipofluxo = 8;

INSERT INTO bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) VALUES (125, '2.0', 8, 'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual', '', '10-06-2013', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (753, 125, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 21, 109, 32, 32, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (754, 125, 'Tarefa', '', 'Aprovar', 'Aprovar', 'U', '/pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Aprovacao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 313, 93, 65, 140, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (755, 125, 'Tarefa', '', 'Planejar', 'Planejar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Planejamento', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 518, 91, 65, 140, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (756, 125, 'Tarefa', '', 'Executar', 'Executar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 723, 94, 65, 140, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (757, 125, 'Tarefa', '', 'Avaliar', 'Avaliar', 'U', 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Avaliacao', '', '#{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 943, 92, 65, 140, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (758, 125, 'Tarefa', '', 'Proposta', 'Proposta', 'U', ' pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Proposta', '', ' #{requisicaoMudanca.nomeGrupoAtual}', '', '', '', '', '', '', 66, 307, 65, 140, '', '', NULL, '', '', '', 'S', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (759, 125, 'Script', '', 'Encerra', '', '', '', '', '', '', '', '', '#{execucaoFluxo}.encerra();', '', '', 1154, 92, 65, 140, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (760, 125, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 117, 105, 42, 42, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (761, 125, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 366, 318, 42, 42, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES (762, 125, 'Finalizacao', '', '', '', '', '', '', '', '', '', '', '', '', '', 1207, 311, 32, 32, '', '', NULL, '', '', '', '', NULL);


INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (753, 760, 125, NULL, NULL, '', 1, 3, 85, 125.5, '', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (754, 755, 125, NULL, NULL, '', 1, 3, 485.5, 124.5, '', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (755, 756, 125, NULL, NULL, '', 1, 3, 690.5, 125, '', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (756, 757, 125, NULL, NULL, '', 1, 3, 903, 125.5, '', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (757, 759, 125, NULL, NULL, '', 1, 3, 1118.5, 124.5, '', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (758, 761, 125, NULL, NULL, '', 1, 3, 286, 339.25, '', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (759, 762, 125, NULL, NULL, '', 2, 0, 1223.5, 234, '', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (760, 754, 125, NULL, NULL, '!#{requisicaoMudanca}.ehProposta();', 1, 3, 236, 125.75, '', 'naoEhProposta');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (760, 758, 125, NULL, NULL, '#{requisicaoMudanca}.ehProposta();', 2, 0, 137, 227, '', 'ehProposta');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (761, 754, 125, NULL, NULL, '#{requisicaoMudanca}.votacaoPropostaAprovada();', 0, 2, 385, 238, '', 'votacaoPropostaAprovada');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome) VALUES (761, 762, 125, NULL, NULL, '!#{requisicaoMudanca}.votacaoPropostaAprovada();', 1, 3, 807.5, 333, '', 'rejeitada');

-- Fim Módulo Mudança

-- Fim Bruno

-- Riubbe Oliveira -- 14/06/2013 - inicio
alter table itemconfiguracao add(idliberacao number(10,0) null);
alter table itemconfiguracao add constraint fk_itemconfiguracao_liberacao foreign key(idliberacao) references liberacao(idliberacao);

INSERT INTO grupoitemconfiguracao (idgrupoitemconfiguracao, nomegrupoitemconfiguracao, datainicio, datafim, email, emailgrupoitemconfiguracao, idGrupoItemConfiguracaoPai) VALUES (1001,'Desenvolvimento - Padrão','12-06-2013',NULL,NULL,NULL,997);
INSERT INTO grupoitemconfiguracao (idgrupoitemconfiguracao, nomegrupoitemconfiguracao, datainicio, datafim, email, emailgrupoitemconfiguracao, idGrupoItemConfiguracaoPai) VALUES (1002,'Homologação - Padrão','12-06-2013',NULL,NULL,NULL,999);
INSERT INTO grupoitemconfiguracao (idgrupoitemconfiguracao, nomegrupoitemconfiguracao, datainicio, datafim, email, emailgrupoitemconfiguracao, idGrupoItemConfiguracaoPai) VALUES (1003,'Produção - Padrão','12-06-2013',NULL,NULL,NULL,998);

-- Riubbe Oliveira -- 14/06/2013 - fim

-- inicio requisicaoliberacaoresponsavel - Thiago Matias
CREATE TABLE REQUISICAOLIBERACAORESPONSAVEL(
	IDREQUISICAOLIBERACAORESP NUMBER(10,0),
	IDRESPONSAVEL NUMBER(10,0),
	IDREQUISICAOLIBERACAO NUMBER(10,0),
	PAPELRESPONSAVEL VARCHAR2(200 CHAR)
);

-- fim requisicaoliberacaoresponsavel - Thiago Matias
alter table historicoic add origemmodificacao varchar2(20) null;
alter table historicoic add idmodificacao number(10,0) null;
-- Fim Thiago

-- Módulo de Liberação
-- Geber Costa 21/06/2013
create table situacaoliberacaomudanca (
	idsituacaoliberacaomudanca number(10,0) not null, 
	situacao varchar(45)
);

alter table situacaoliberacaomudanca add constraint pk_situacaoliberacaomudanca primary key (idsituacaoliberacaomudanca);

insert into situacaoliberacaomudanca values(1,'Em Execução');
insert into situacaoliberacaomudanca values(2,'Resolvida');
insert into situacaoliberacaomudanca values(3,'Não Resolvida');
insert into situacaoliberacaomudanca values(4,'Cancelada');

alter table liberacaomudanca add (status VARCHAR2(45));
alter table liberacaomudanca add (situacaoliberacao VARCHAR2(45));

-- Fim Geber

-- Início Bruno Mudança 24/06/13
CREATE TABLE requisicaomudancaresponsavel(
       idRequisicaoMudancaResp NUMBER(10,0) NOT NULL,
       idResponsavel NUMBER(10,0),
       idRequisicaoMudanca NUMBER(10,0),
       papelResponsavel VARCHAR2(200 CHAR)
);

alter table requisicaomudancaresponsavel add constraint pk_requisicaomudancaresp primary key (idRequisicaoMudancaResp);
alter table requisicaomudancaresponsavel add constraint fk_requisicaomudancaresp_resp foreign key (idResponsavel) references empregados (idempregado);

-- Fim Bruno

-- Início Thiago Fernandes 24/06/13
ALTER TABLE ocorrenciamudanca drop CONSTRAINT fk_ocorrenc_reference_justific;
ALTER TABLE ocorrenciamudanca ADD CONSTRAINT fk_ocorrenc_reference_justific FOREIGN KEY (idjustificativa) REFERENCES justificativamudanca (idjustificativamudanca);

-- Fim Thiago Fernandes

-- Início Thiago Matias 26/06/13

CREATE TABLE REQUISICAOLIBERACAOCOMPRAS(
	IDREQUISICAOLIBERACAOCOMPRAS NUMBER (10,0),	  
	IDSOLICITACAOSERVICO NUMBER (10,0),
	IDREQUISICAOLIBERACAO NUMBER (10,0)
);

-- Fim Thiago Matias

-- Início Maycon 26/06/13

create table requisicaoliberacaomidia (
  idRequisicaoLiberacaoMidia number(10,0) not null,
  idMidiaSoftware number(10,0) not null,
  idRequisicaoLiberacao number(10,0) not null,
  constraint pk_requisicaoliberacaomidia primary key (idRequisicaoLiberacaoMidia),
  constraint fk_requesteM_midia FOREIGN KEY (idmidiasoftware) REFERENCES midiasoftware (idmidiasoftware),
  constraint  fk_requesteM_liberacao FOREIGN KEY (idrequisicaoliberacao) REFERENCES liberacao (idliberacao)
);

-- Fim Maycon

-- Início Thiago Fernandes 01/07/13

update bpm_elementofluxo set url = 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Avaliacao' where idelemento = 406;

update bpm_elementofluxo set url = 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Avaliacao' where idelemento = 411;

-- Fim Thiago Fernandes

-- Início Murilo Pacheco 02/07/13

create table ligacao_historico_ged (
    idligacao_historico_ged NUMBER(10,0) not null,
    idcontroleged NUMBER(10,0),
    idrequisicaoliberacao NUMBER(10,0),
    idhistoricoliberacao NUMBER(10,0),
    idtabela NUMBER(10,0),
    datafim DATE DEFAULT NULL,
    primary key (idligacao_historico_ged)
);

create table ligacao_lib_hist_midia (
    idligacao_lib_hist_midia  NUMBER(10,0) not null,
    idrequisicaoliberacaomidia NUMBER(10,0),
    idrequisicaoliberacao    NUMBER(10,0),
    idhistoricoliberacao      NUMBER(10,0),
    primary key (idligacao_lib_hist_midia)
);

ALTER TABLE requisicaoliberacaomidia ADD (DATAFIM DATE DEFAULT NULL);

create table ligacao_lib_hist_resp (
    idligacao_lib_hist_resp  NUMBER(10,0) not null,
    idrequisicaoliberacaoresp NUMBER(10,0),
    idrequisicaoliberacao    NUMBER(10,0),
    idhistoricoliberacao      NUMBER(10,0),
    primary key (idligacao_lib_hist_resp)
);

ALTER TABLE requisicaoliberacaoresponsavel ADD (datafim DATE DEFAULT NULL);

create table ligacaolibhistcompras (
    idligacaolibhistcompras  NUMBER(10,0) not null,
    idRequisicaoLiberacaoCompras NUMBER(10,0),
    idrequisicaoliberacao NUMBER(10,0),
    idhistoricoliberacao NUMBER(10,0),
    primary key (idligacaolibhistcompras)
);

ALTER TABLE requisicaoliberacaocompras ADD(datafim DATE DEFAULT NULL);

-- Fim Murilo Pacheco

-- Início Danillo Sardinha 04/07/13

drop index primary_161;

-- Fim Danillo Sardinha

-- CLEDSON JUNIOR - 11/06/2013
alter table origematendimento add (datainicio date);

alter table origematendimento add (datafim date);

-- FIM

-- Cleison Ferreira - 13/06/2013
CREATE UNIQUE INDEX "INDEX_EMP_NOMEPROC" ON "EMPREGADOS" ("NOMEPROCURA");
-- Fim

-- Carlos Santos - 09/06/2013
ALTER TABLE cargos ADD iddescricaocargo INTEGER;
-- Fim

-- Início rodrigo.oliveira - 14/06/2013

create table prioridadeservicousuario (
  idusuario number(10,0) not null,
  idacordonivelservico number(19,0) not null,
  idprioridade number(19,0) default null,
  idservicocontrato number(19,0) default null,
  datainicio date not null,
  datafim date default null
);

alter table prioridadeservicousuario add constraint pk_prioridadeservicousuario primary key (idusuario, idacordonivelservico);

-- Fim rodrigo.oliveira
