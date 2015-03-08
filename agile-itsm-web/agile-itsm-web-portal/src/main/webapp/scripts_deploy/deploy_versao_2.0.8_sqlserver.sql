-- SQLServer

-- Início Carlos Santos 16/05/13 autorizado 28/06/13

ALTER TABLE solicitacaoservicoquestionario DROP CONSTRAINT pk_solicitacaoservquest;

ALTER TABLE solicitacaoservicoquestionario DROP CONSTRAINT fk_solquest_questionario;

DROP TABLE respostaitemquestionarioanexos;
DROP TABLE respostaitemquestionarioopcoes;
DROP TABLE opcaorespostaquestionario;
DROP TABLE respostaitemquestionario;
DROP TABLE questaoquestionario;
DROP TABLE grupoquestionario;
DROP TABLE questionario;

create table questionario (
   idquestionario			integer		not null,
   idquestionarioorigem		integer		null,
   idcategoriaquestionario	integer		not null,
   nomequestionario			varchar(50) not null,
   idempresa				integer     not null,
   ativo					char(1)     not null default 'S',
   javascript				text        null
);

alter table questionario  add constraint pk_questionario primary key (idquestionario);

create table respostaitemquestionarioopcoes (
	idrespostaitemquestionario integer  not null,
	idopcaorespostaquestionario integer not null
);

alter table respostaitemquestionarioopcoes add constraint pk_respostaitemquestionarioopc primary key (idrespostaitemquestionario, idopcaorespostaquestionario);

create table grupoquestionario (
   idgrupoquestionario  integer not null,
   idquestionario       integer not null,
   nomegrupoquestionario varchar(80) not null,
   ordem                integer null
);

alter table grupoquestionario  add constraint pk_grupoquestionario primary key (idgrupoquestionario);

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
   inforesposta         		char(1) null  constraint ckc_inforesposta_questaoq check (inforesposta is null or (inforesposta in ('L','B'))),
   valoresreferencia    		text    null,
   unidade              		text    null,
   obrigatoria         		char(1) not null constraint ckc_obrigatoria_questaoq check (obrigatoria in ('S','N', '')),
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

alter table questaoquestionario   add constraint pk_questaoquestionario primary key (idquestaoquestionario);

create  index ix_sigla_questao  on questaoquestionario (sigla);
create  index ix_idquestaoorigem on questaoquestionario (idquestaoorigem);

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

alter table respostaitemquestionario add constraint pk_respostaitemquestionario primary key (idrespostaitemquestionario);

create  index ix_ident_questao on respostaitemquestionario (ididentificadorresposta, idquestaoquestionario);
create  index ix_idquestao on respostaitemquestionario (idquestaoquestionario);

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

alter table opcaorespostaquestionario add constraint pk_opcaorespostaquestionario primary key (idopcaorespostaquestionario);

create table respostaitemquestionarioanexos (
   idrespostaitmquestionarioanexo	integer      not null,
   idrespostaitemquestionario		integer      not null,
   caminhoanexo				varchar(255) not null,
   observacao					text         null
);

alter table respostaitemquestionarioanexos  add constraint pk_respostaitemquestionarioane primary key (idrespostaitmquestionarioanexo);

alter table grupoquestionario add constraint fk_grupoque_reference_question foreign key (idquestionario)  references questionario (idquestionario)    on delete no action on update no action;
alter table opcaorespostaquestionario add constraint fk_opcaores_reference_questaoq foreign key (idquestaocomplemento)   references questaoquestionario (idquestaoquestionario) on delete no action on update no action;
alter table opcaorespostaquestionario add constraint fk_opcaores_ref_questaoq foreign key (idquestaoquestionario)    references questaoquestionario (idquestaoquestionario)   on delete cascade on update no action;
alter table questaoquestionario add constraint fk_questaoagrupadora foreign key (idquestaoagrupadora) references questaoquestionario (idquestaoquestionario)  on delete no action on update no action;
alter table questaoquestionario add constraint fk_questaoq_reference_question foreign key (idsubquestionario)  references questionario (idquestionario) on delete no action on update no action;
alter table questaoquestionario add constraint fk_questaoq_reference_grupoque foreign key (idgrupoquestionario)  references grupoquestionario (idgrupoquestionario)  on delete no action on update no action;
alter table questionario add constraint fk_question_reference_categori foreign key (idcategoriaquestionario)  references categoriaquestionario (idcategoriaquestionario) on delete no action on update no action;
alter table questionario add constraint fk_questionarioorigem foreign key (idquestionarioorigem) references questionario (idquestionario) on delete no action on update no action;
alter table respostaitemquestionario add constraint fk_resposta_reference_questaoq foreign key (idquestaoquestionario)   references questaoquestionario (idquestaoquestionario)  on delete no action on update no action;
alter table respostaitemquestionarioanexos add constraint fk_rspt_anx_reference_rspt_itm foreign key (idrespostaitemquestionario)  references respostaitemquestionario (idrespostaitemquestionario)  on delete cascade on update no action;
alter table respostaitemquestionarioopcoes add constraint fk_rspta_q_reference_rspta_itm foreign key (idrespostaitemquestionario)   references respostaitemquestionario (idrespostaitemquestionario)  on delete cascade on update no action;
alter table respostaitemquestionarioopcoes add constraint fk_resposta_reference_opcaores foreign key (idopcaorespostaquestionario)   references opcaorespostaquestionario (idopcaorespostaquestionario)  on delete no action on update no action;

ALTER TABLE solicitacaoservicoquestionario ADD CONSTRAINT pk_solicitacaoservquest PRIMARY KEY (idsolicitacaoquestionario);

ALTER TABLE solicitacaoservicoquestionario ADD CONSTRAINT fk_solquest_questionario FOREIGN KEY (idquestionario) REFERENCES questionario(idquestionario);

CREATE TABLE requisicaoquestionario (
  idrequisicaoquestionario integer not null,
  idquestionario integer not null,
  idtiporequisicao integer DEFAULT NULL,
  idtipoaba integer DEFAULT NULL,
  idrequisicao integer NOT NULL,
  dataquestionario datetime NOT NULL DEFAULT sysdatetime(),
  idresponsavel integer NOT NULL,
  idtarefa bigint DEFAULT NULL,
  aba varchar(100) DEFAULT NULL,
  situacao char(1) NOT NULL,
  datahoragrav datetime NOT NULL DEFAULT sysdatetime(),
  conteudoimpresso text,
  confirmacao varchar(1) DEFAULT NULL,
  PRIMARY KEY (idrequisicaoquestionario),
  CONSTRAINT fk_requesteq_empregado FOREIGN KEY (idresponsavel) REFERENCES empregados (idempregado),
  CONSTRAINT fk_requesteq_tarefa FOREIGN KEY (idtarefa) REFERENCES bpm_itemtrabalhofluxo (iditemtrabalho),
  CONSTRAINT fk_requesteq_questionario FOREIGN KEY (idquestionario) REFERENCES questionario (idquestionario)
);

INSERT INTO categoriaquestionario (idcategoriaquestionario,nomecategoriaquestionario) VALUES (1,'CONTRATOS');

INSERT INTO templatesolicitacaoservico (idtemplate, identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar) VALUES (13,'ChecklistQuestionario','Checklist Questionario','','br.com.centralit.citcorpore.ajaxForms.ChecklistQuestionario','br.com.centralit.citcorpore.negocio.ChecklistQuestionarioServiceEjb','/pages/checklistQuestionario/checklistQuestionario.load','','N','S','N',820,'N','N','N','N','N','N','S');

-- Fim Carlos Santos

-- Início Maycon

create table requisicaoliberacaomidia (
  idRequisicaoLiberacaoMidia integer not null,
  idMidiaSoftware integer not null,
  idRequisicaoLiberacao integer not null
);

alter table requisicaoliberacaomidia add constraint pk_requisicaoliberacaomidia primary key (idRequisicaoLiberacaoMidia);

update bpm_fluxo set datafim = '2013-06-11' where idtipofluxo = 54 and idfluxo <> 62;

INSERT INTO bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) VALUES (62, '2.0', 54, 'requisicaoLiberacao;requisicaoLiberacao.status;requisicaoLiberacao.nomeGrupoAtual', '', '2013-06-11', NULL);

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

-- danillo.lisboa
-- altera os os campos datahorareativacao e datahorasuspensao para aceitar null.
alter table historicoliberacao alter column datahorareativacao datetime  null;
alter table historicoliberacao alter column datahorasuspensao datetime  null;

-- Início Bruno

-- FLUXO MÓDULO DE MUDANÇA

UPDATE bpm_fluxo SET datafim = '2013-06-10' WHERE idtipofluxo = 8;

INSERT INTO bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) VALUES (125, '2.0', 8, 'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual', '', '2013-06-13', NULL);

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

alter table itemconfiguracao add idliberacao int null;
alter table itemconfiguracao add constraint fk_itemconfiguracao_liberacao foreign key(idliberacao) references liberacao(idliberacao);

INSERT INTO grupoitemconfiguracao (idgrupoitemconfiguracao, nomegrupoitemconfiguracao, datainicio, datafim, email, emailgrupoitemconfiguracao, idGrupoItemConfiguracaoPai) VALUES (1001,'Desenvolvimento - Padrão','2013-06-12',NULL,NULL,NULL,997);
INSERT INTO grupoitemconfiguracao (idgrupoitemconfiguracao, nomegrupoitemconfiguracao, datainicio, datafim, email, emailgrupoitemconfiguracao, idGrupoItemConfiguracaoPai) VALUES (1002,'Homologação - Padrão','2013-06-12',NULL,NULL,NULL,999);
INSERT INTO grupoitemconfiguracao (idgrupoitemconfiguracao, nomegrupoitemconfiguracao, datainicio, datafim, email, emailgrupoitemconfiguracao, idGrupoItemConfiguracaoPai) VALUES (1003,'Produção - Padrão','2013-06-12',NULL,NULL,NULL,998);

-- Riubbe Oliveira -- 14/06/2013 - fim

-- inicio requisicaoliberacaoresponsavel - thiago matias
CREATE TABLE requisicaoliberacaoresponsavel(
	idRequisicaoLiberacaoResp integer NULL,
	idResponsavel integer NULL,
	idRequisicaoLiberacao integer NULL,
	papelResponsavel varchar(200) NULL
);
-- fim requisicaoliberacaoresponsavel - thiago matias

alter table historicoic add origemmodificacao varchar(20) null;
alter table historicoic add idmodificacao int null;

-- Fim

-- Módulo de Liberação
-- Geber Costa 21/06/2013

CREATE TABLE situacaoliberacaomudanca (
	idsituacaoliberacaomudanca INTEGER NOT NULL PRIMARY KEY,
	situacao VARCHAR(45)
);

INSERT INTO situacaoliberacaomudanca VALUES
(1,'Em Execução'),
(2,'Resolvida'),
(3,'Não Resolvida'),
(4,'Cancelada');

ALTER TABLE liberacaomudanca add status VARCHAR(45);
ALTER TABLE liberacaomudanca add situacaoliberacao VARCHAR(45);
-- Fim

-- Início Bruno Mudança 24/06/13
CREATE TABLE requisicaomudancaresponsavel(
	idRequisicaoMudancaResp int NOT NULL,
	idResponsavel int NULL,
	idRequisicaoMudanca int NULL,
	papelResponsavel varchar(200) NULL
);

alter table requisicaomudancaresponsavel add constraint pk_requisicaomudancaresponsavel primary key (idRequisicaoMudancaResp);
alter table requisicaomudancaresponsavel add constraint fk_requisicaomudancaresp_resp foreign key (idResponsavel) references empregados (idempregado);

-- Fim Bruno

-- Início Thiago Fernandes 24/06/13
ALTER TABLE ocorrenciamudanca drop CONSTRAINT fk_ocorrenc_reference_justific;
ALTER TABLE ocorrenciamudanca ADD CONSTRAINT fk_ocorrenc_reference_justific FOREIGN KEY (idjustificativa) REFERENCES justificativamudanca (idjustificativamudanca);

-- Fim Thiago Fernandes

-- Início Thiago Matias 26/06/13

CREATE TABLE requisicaoliberacaocompras (
  idRequisicaoLiberacaoCompras integer NULL,
  idSolicitacaoServico integer NULL,
  idRequisicaoLiberacao integer NULL
);

-- Fim Thiago Matias

-- Início Maycon 26/06/13

create table requisicaoliberacaomidia (
  idRequisicaoLiberacaoMidia integer not null,
  idMidiaSoftware integer not null,
  idRequisicaoLiberacao integer not null,
  constraint pk_requisicaoliberacaomidia primary key (idRequisicaoLiberacaoMidia),
  constraint fk_requesteM_midia FOREIGN KEY (idMidiaSoftware) REFERENCES midiasoftware (idMidiaSoftware),
  constraint  fk_requesteM_liberacao FOREIGN KEY (idRequisicaoLiberacao) REFERENCES liberacao (idliberacao)
);

-- Fim Maycon

-- Início Thiago Fernandes 01/07/13

update bpm_elementofluxo set url = 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Avaliacao' where idelemento = 406;

update bpm_elementofluxo set url = 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Avaliacao' where idelemento = 411;

-- Fim Thiago Fernandes

-- Início Murilo Pacheco 02/07/13

create table ligacao_lib_hist_midia (
    idligacao_lib_hist_midia integer not null,
    idrequisicaoliberacaomidia integer,
    idrequisicaoliberacao integer,
    idhistoricoliberacao integer
);

alter table ligacao_lib_hist_midia add constraint pk_ligacao_lib_hist_midia primary key (idligacao_lib_hist_midia);

alter table requisicaoliberacaomidia add datafim date default null;

create table ligacao_lib_hist_resp (
    idligacao_lib_hist_resp integer not null,
    idrequisicaoliberacaoresp integer,
    idrequisicaoliberacao integer,
    idhistoricoliberacao integer
);

alter table ligacao_lib_hist_resp add constraint pk_ligacao_lib_hist_resp primary key (idligacao_lib_hist_resp);

alter table requisicaoliberacaoresponsavel add datafim date default null;

create table ligacaolibhistcompras (
    idligacaolibhistcompras  integer not null,
    idRequisicaoLiberacaoCompras integer,
    idrequisicaoliberacao integer,
    idhistoricoliberacao integer
);

alter table ligacaolibhistcompras add constraint pk_ligacaolibhistcompras primary key (idligacaolibhistcompras);

alter table requisicaoliberacaocompras add datafim date default null;

create table ligacao_historico_ged (
    idligacao_historico_ged integer not null,
    idcontroleged integer,
    idrequisicaoliberacao integer,
    idhistoricoliberacao integer,
    idtabela integer,
    datafim date default null
);

alter table ligacao_historico_ged add constraint pk_ligacao_historico_ged primary key (idligacao_historico_ged);

-- Fim Murilo Pacheco

-- Início Pedro Lino 03/07/13

-- danillo.lisboa
-- deleta a CONSTRAINT da tabela liberacaoproblema
ALTER TABLE liberacaoproblema DROP CONSTRAINT [DF__liberacao__idhis__5125ECB4];

-- deleta a CONSTRAINT da tabela liberacaoproblema
DECLARE @SQL VARCHAR(4000)
SET @SQL = 'alter table liberacaoproblema DROP CONSTRAINT |ConstraintName| '
SET @SQL = REPLACE(@SQL, '|ConstraintName|', ( select name from sysobjects where parent_obj = OBJECT_ID('liberacaoproblema')))
EXEC (@SQL);

-- Fim Pedro Lino

-- Início Maycon 04/07/13

alter table ocorrencialiberacao drop CONSTRAINT ocorrencialiberacao_idjustificativa_fkey;

alter table ocorrencialiberacao add CONSTRAINT ocorrencialiberacao_idjustificativa_fkey FOREIGN KEY (idjustificativa) REFERENCES justificativaliberacao (idjustificativaliberacao);

-- Fim Maycon

-- CLEDSON JUNIOR - 11/06/2013
alter table origematendimento add datainicio datetime;
alter table origematendimento add datafim datetime;

-- FIM

-- Carlos Santos - 09/06/2013
ALTER TABLE cargos ADD iddescricaocargo INT;
-- Fim

-- Cleison Ferreira - 13/06/2013
CREATE NONCLUSTERED INDEX [INDEX_EMP_NOMEPROC] ON [empregados] 
(
  [nomeprocura] ASC
)ON [PRIMARY];
-- Fim

-- Início rodrigo.oliveira - 14/06/2013

create table prioridadeservicousuario (
  idusuario integer not null,
  idacordonivelservico bigint not null,
  idprioridade bigint default null,
  idservicocontrato bigint default null,
  datainicio date not null,
  datafim date default null,
  PRIMARY KEY (idusuario, idacordonivelservico)
);

alter table prioridadeservicousuario add constraint pk_prioridadeservicousuario primary key (idusuario, idacordonivelservico);

-- Fim rodrigo.oliveira

