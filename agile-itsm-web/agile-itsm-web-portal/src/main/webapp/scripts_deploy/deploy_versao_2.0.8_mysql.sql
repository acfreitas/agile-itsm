set sql_safe_updates = 0;

-- MySQL

-- Início Carlos Santos 16/05/13 autorizado 28/06/13

alter table templatesolicitacaoservico drop foreign key fk_questionario;
alter table solicitacaoservicoquestionario drop foreign key fk_solquest_questionario;

drop table respostaitemquestionarioanexos;
drop table respostaitemquestionarioopcoes;
drop table opcaorespostaquestionario;
drop table respostaitemquestionario;
drop table questaoquestionario;
drop table grupoquestionario;
drop table questionario;

create table questionario (
  idquestionario int(11) not null,
  idquestionarioorigem int(11) default null,
  idcategoriaquestionario int(11) not null,
  nomequestionario varchar(50) not null,
  idempresa int(11) not null,
  ativo char(1) not null default 's',
  javascript text,
  primary key (idquestionario),
  key fk_reference_1 (idcategoriaquestionario),
  key fk_questionarioorigem (idquestionarioorigem),
  constraint fk_questionarioorigem foreign key (idquestionarioorigem) references questionario (idquestionario),
  constraint fk_reference_1 foreign key (idcategoriaquestionario) references categoriaquestionario (idcategoriaquestionario)
) engine=innodb;

create table grupoquestionario (
  idgrupoquestionario int(11) not null,
  idquestionario int(11) not null,
  nomegrupoquestionario varchar(80) not null,
  ordem smallint(6) default null,
  primary key (idgrupoquestionario),
  key fk_reference_2 (idquestionario),
  constraint fk_reference_2 foreign key (idquestionario) references questionario (idquestionario)
) engine=innodb;

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
) engine=innodb;

create table respostaitemquestionario (
  idrespostaitemquestionario int(11) not null,
  ididentificadorresposta int(11) not null,
  idquestaoquestionario int(11) not null,
  sequencialresposta int(11) default null,
  respostatextual text,
  respostapercentual decimal(15,5) default null,
  respostavalor decimal(15,5) default null,
  respostavalor2 decimal(15,5) default null,
  respostanumero decimal(8,0) default null,
  respostanumero2 decimal(8,0) default null,
  respostadata date default null,
  respostahora varchar(4) default null,
  respostames smallint(6) default null,
  respostaano smallint(6) default null,
  respostaidlistagem varchar(10) default null,
  respostadia smallint(6) default null,
  primary key (idrespostaitemquestionario),
  key ix_ident_questao (ididentificadorresposta,idquestaoquestionario),
  key ix_idquestao (idquestaoquestionario),
  constraint fk_reference_5 foreign key (idquestaoquestionario) references questaoquestionario (idquestaoquestionario)
) engine=innodb;

create table opcaorespostaquestionario (
  idopcaorespostaquestionario int(11) not null,
  idquestaoquestionario int(11) not null,
  titulo varchar(255) not null,
  peso int(11) default null,
  valor varchar(50) default null,
  geraalerta char(1) default null,
  exibecomplemento char(1) default null,
  idquestaocomplemento int(11) default null,
  primary key (idopcaorespostaquestionario),
  key fk_reference_14 (idquestaocomplemento),
  key fk_reference_4 (idquestaoquestionario),
  constraint fk_reference_14 foreign key (idquestaocomplemento) references questaoquestionario (idquestaoquestionario),
  constraint fk_reference_4 foreign key (idquestaoquestionario) references questaoquestionario (idquestaoquestionario) on delete cascade
) engine=innodb;

create table respostaitemquestionarioopcoes (
  idrespostaitemquestionario int(11) not null,
  idopcaorespostaquestionario int(11) not null,
  primary key (idrespostaitemquestionario,idopcaorespostaquestionario),
  key fk_reference_7 (idopcaorespostaquestionario),
  constraint fk_reference_7 foreign key (idopcaorespostaquestionario) references opcaorespostaquestionario (idopcaorespostaquestionario),
  constraint fk_rspta_q_reference_rspta_itm foreign key (idrespostaitemquestionario) references respostaitemquestionario (idrespostaitemquestionario) on delete cascade
) engine=innodb;

create table respostaitemquestionarioanexos (
  idrespostaitmquestionarioanexo int(11) not null,
  idrespostaitemquestionario int(11) not null,
  caminhoanexo varchar(255) not null,
  observacao text,
  primary key (idrespostaitmquestionarioanexo),
  key fk_rspt_anx_reference_rspt_itm (idrespostaitemquestionario),
  constraint fk_rspt_anx_reference_rspt_itm foreign key (idrespostaitemquestionario) references respostaitemquestionario (idrespostaitemquestionario) on delete cascade
) engine=innodb;

ALTER TABLE templatesolicitacaoservico  ADD CONSTRAINT fk_questionario1  FOREIGN KEY (idquestionario)  REFERENCES questionario (idquestionarioorigem);

CREATE TABLE requisicaoquestionario (
  idrequisicaoquestionario int(11) NOT NULL,
  idquestionario int(11) NOT NULL,
  idtiporequisicao int(11) DEFAULT NULL,
  idtipoaba int(11) DEFAULT NULL,
  idrequisicao bigint(20) NOT NULL,
  dataquestionario timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  idresponsavel int(11) NOT NULL,
  idtarefa bigint(20) DEFAULT NULL,
  aba varchar(100) DEFAULT NULL,
  situacao char(1) NOT NULL,
  datahoragrav timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  conteudoimpresso text,
  confirmacao varchar(1) DEFAULT NULL,
  PRIMARY KEY (idrequisicaoquestionario),
  CONSTRAINT fk_requisicaoq_empregado FOREIGN KEY (idresponsavel) REFERENCES empregados (idempregado) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_requisicaoq_tarefa FOREIGN KEY (idtarefa) REFERENCES bpm_itemtrabalhofluxo (iditemtrabalho) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_requisicaoq_questionario FOREIGN KEY (idquestionario) REFERENCES questionario (idquestionario) ON DELETE NO ACTION ON UPDATE NO ACTION
)engine=innodb;

INSERT INTO templatesolicitacaoservico (idtemplate, identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar) VALUES (13,'ChecklistQuestionario','Checklist Questionario','','br.com.centralit.citcorpore.ajaxForms.ChecklistQuestionario','br.com.centralit.citcorpore.negocio.ChecklistQuestionarioServiceEjb','/pages/checklistQuestionario/checklistQuestionario.load','','N','S','N',820,'N','N','N','N','N','N','S');

INSERT INTO categoriaquestionario (idcategoriaquestionario,nomecategoriaquestionario) VALUES (1,'CONTRATOS');

-- Fim Carlos Santos

-- Início Maycon

CREATE TABLE requisicaoliberacaomidia (
  idRequisicaoLiberacaoMidia int(11) NOT NULL,
  idMidiaSoftware int(11) NOT NULL,
  idRequisicaoLiberacao int(11) NOT NULL,
  PRIMARY KEY (idRequisicaoLiberacaoMidia)
)engine=innodb;

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

-- Fim módulo mudança

-- Fim Bruno

-- inicio requisicaoliberacaoresponsavel - thiago matias
CREATE TABLE requisicaoliberacaoresponsavel(
	idRequisicaoLiberacaoResp int(11) NULL,
	idResponsavel int(11) NULL,
	idRequisicaoLiberacao int(11) NULL,
	papelResponsavel varchar(200) NULL
)engine=innodb;
-- fim requisicaoliberacaoresponsavel - thiago matias


-- Módulo Liberação

-- Riubbe Oliveira -- 14/06/2013 - inicio

alter table itemconfiguracao add column idliberacao int(11) null;
alter table liberacao engine = InnoDB;
alter table itemconfiguracao add constraint fk_itemconfiguracao_liberacao foreign key(idliberacao) references liberacao(idliberacao);

INSERT INTO grupoitemconfiguracao (idgrupoitemconfiguracao,nomegrupoitemconfiguracao,datainicio,datafim,email,emailgrupoitemconfiguracao,idGrupoItemConfiguracaoPai) VALUES (1001,'Desenvolvimento - Padrão','2013-06-12',NULL,NULL,NULL,997);
INSERT INTO grupoitemconfiguracao (idgrupoitemconfiguracao,nomegrupoitemconfiguracao,datainicio,datafim,email,emailgrupoitemconfiguracao,idGrupoItemConfiguracaoPai) VALUES (1002,'Homologação - Padrão','2013-06-12',NULL,NULL,NULL,999);
INSERT INTO grupoitemconfiguracao (idgrupoitemconfiguracao,nomegrupoitemconfiguracao,datainicio,datafim,email,emailgrupoitemconfiguracao,idGrupoItemConfiguracaoPai) VALUES (1003,'Produção - Padrão','2013-06-12',NULL,NULL,NULL,998);

alter table historicoic add column origemmodificacao varchar(20) null;
alter table historicoic add column idmodificacao int(11) null;

-- Fim Riubbe Oliveira

-- Módulo de Liberação
-- Geber Costa 21/06/2013

CREATE TABLE situacaoliberacaomudanca (
	idsituacaoliberacaomudanca INT(11) NOT NULL PRIMARY KEY,
	situacao VARCHAR(45)
)engine=innodb;

INSERT INTO situacaoliberacaomudanca VALUES
(1,'Em Execução'),
(2,'Resolvida'),
(3,'Não Resolvida'),
(4,'Cancelada');

ALTER TABLE liberacaomudanca add status VARCHAR(45);
ALTER TABLE liberacaomudanca add situacaoliberacao VARCHAR(45);
-- Fim Geber

-- Início Bruno Mudança 24/06/13
CREATE TABLE requisicaomudancaresponsavel(
       idRequisicaoMudancaResp int(11) NOT NULL,
       idResponsavel int(11) NULL,
       idRequisicaoMudanca int(11) NULL,
       papelResponsavel varchar(200) NULL
)engine=innodb;

alter table requisicaomudancaresponsavel add constraint pk_requisicaomudancaresponsavel primary key (idRequisicaoMudancaResp);
alter table requisicaomudancaresponsavel add constraint fk_requisicaomudancaresp_resp foreign key (idResponsavel) references empregados (idempregado);

-- Fim Bruno

-- Thiago Fernandes 24/06/13
ALTER TABLE ocorrenciamudanca DROP FOREIGN KEY fk_ocorrenc_reference_justific;
ALTER TABLE ocorrenciamudanca ADD CONSTRAINT fk_ocorrenc_reference_justific FOREIGN KEY (idjustificativa) REFERENCES justificativamudanca (idjustificativamudanca);

-- Fim Thiago Fernandes

-- Início Thiago Matias 26/06/13

CREATE TABLE requisicaoliberacaocompras (
  idRequisicaoLiberacaoCompras int(11) NULL,
  idSolicitacaoServico int(11) NULL,
  idRequisicaoLiberacao int(11) NULL
) engine=innodb;

-- Fim Thiago Matias

-- Início Maycon 26/06/13

CREATE TABLE requisicaoliberacaomidia (
  idrequisicaoliberacaomidia int(11) NOT NULL,
  idmidiasoftware int(11) NOT NULL,
  idrequisicaoliberacao int(11) NOT NULL,
  PRIMARY KEY (idrequisicaoliberacaomidia),
  constraint fk_requesteM_midia FOREIGN KEY (idmidiasoftware) REFERENCES midiasoftware (idmidiasoftware),
  constraint fk_requesteM_liberacao FOREIGN KEY (idrequisicaoliberacao) REFERENCES liberacao (idliberacao)
)engine=InnoDB;

-- Fim Maycon

-- Início Thiago Fernandes 01/07/13

update bpm_elementofluxo set url = 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Avaliacao' where idelemento = 406;

update bpm_elementofluxo set url = 'pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Avaliacao' where idelemento = 411;

-- Fim Thiago Fernandes

-- Início Murilo Pacheco 02/07/13

create table ligacao_historico_ged (
    idligacao_historico_ged int(11) not null,
    idcontroleged int(11),
    idrequisicaoliberacao int(11),
    idhistoricoliberacao int(11),
    idtabela int(11),
    datafim DATE DEFAULT NULL,
    primary key (idligacao_historico_ged)
) engine=innodb;

create table ligacao_lib_hist_midia (
    idligacao_lib_hist_midia  int(11) not null,
    idrequisicaoliberacaomidia int(11),
    idrequisicaoliberacao    int(11),
    idhistoricoliberacao      int(11),
    primary key (idligacao_lib_hist_midia)
) engine=innodb;

ALTER TABLE requisicaoliberacaomidia ADD (DATAFIM DATE DEFAULT NULL);

create table ligacao_lib_hist_resp (
    idligacao_lib_hist_resp  int(11) not null,
    idrequisicaoliberacaoresp int(11),
    idrequisicaoliberacao    int(11),
    idhistoricoliberacao      int(11),
     primary key (idligacao_lib_hist_resp)
) engine=innodb;
   
ALTER TABLE requisicaoliberacaoresponsavel ADD (datafim DATE DEFAULT NULL);

create table ligacaolibhistcompras (
    idligacaolibhistcompras  int(11) not null,
    idRequisicaoLiberacaoCompras int(11),
    idrequisicaoliberacao int(11),
    idhistoricoliberacao int(11),
    primary key (idligacaolibhistcompras)
) engine=innodb;

ALTER TABLE requisicaoliberacaocompras ADD (datafim DATE DEFAULT NULL);

-- Fim Murilo Pacheco

-- CLEDSON JUNIOR - 11/06/2013
alter table origematendimento add column datainicio date;

alter table origematendimento add column datafim date;

-- FIM CLEDSON

-- Início rodrigo.oliveira - 14/06/2013

CREATE TABLE prioridadeservicousuario (
  idusuario int(11) NOT NULL,
  idacordonivelservico bigint(20) NOT NULL,
  idprioridade bigint(20) DEFAULT NULL,
  idservicocontrato bigint(20) DEFAULT NULL,
  datainicio date NOT NULL,
  datafim date DEFAULT NULL,
  PRIMARY KEY (idusuario, idacordonivelservico)
) ENGINE=InnoDB;

-- Fim rodrigo.oliveira

-- Carlos Santos - 09/06/2013
ALTER TABLE cargos ADD COLUMN iddescricaocargo INT;
-- Fim

-- Cleison Ferreira - 13/06/2013
ALTER TABLE empregados ADD INDEX INDEX_EMP_NOMEPROC (nomeprocura ASC);
-- Fim

set sql_safe_updates = 1;

