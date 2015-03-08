-- Início Mário 05/09/2013

CREATE TABLE gruporequisicaomudanca (
  idgruporequisicaomudanca NUMBER(10,0) NOT NULL,
  idgrupo NUMBER(10,0) NOT NULL,
  idrequisicaomudanca NUMBER(10,0) NOT NULL,
  PRIMARY KEY (idgruporequisicaomudanca),
  foreign key (idgrupo) references grupo (idgrupo),
  foreign key (idrequisicaomudanca) references requisicaomudanca (idrequisicaomudanca)
);

ALTER TABLE gruporequisicaomudanca ADD (nomegrupo VARCHAR2(250) null);
ALTER TABLE gruporequisicaomudanca ADD (datafim date default null);

CREATE TABLE ligacao_mud_his_gru
(
  idligacao_mud_his_gru NUMBER(10,0) NOT NULL,
  idgruporequisicaomudanca NUMBER(10,0),
  idrequisicaomudanca NUMBER(10,0),
  idhistoricomudanca NUMBER(10,0),
  constraint pk_ligacao_mud_his_gru primary key (idligacao_mud_his_gru)
);

-- Fim Mário

-- Início Murilo Gabriel 07/09/2013

alter table historicoic add (idcontrato number(11,0) default null);
alter table historicoic add (idliberacao number(11,0) default null);
alter table historicoic add (idresponsavel number(11,0) default null);
alter table historicoic add (ativofixo varchar2(255) default null);

-- Fim Murilo Gabriel

-- Inicio Ronnie Mikihiro 10/09/2013

CREATE TABLE softwareslistanegra (
    idsoftwareslistanegra NUMBER NOT NULL,
    nomesoftwareslistanegra varchar(100) NOT NULL
);
ALTER TABLE softwareslistanegra ADD CONSTRAINT pk_softwareslistanegra PRIMARY KEY(idsoftwareslistanegra);
CREATE INDEX fk_idx_softwareslistanegra ON softwareslistanegra(idsoftwareslistanegra);

CREATE TABLE softwareslistanegraencontrados (
    idsoftwareslistanegraencontrad NUMBER NOT NULL,
    iditemconfiguracao NUMBER NOT NULL,
    idsoftwareslistanegra NUMBER NOT NULL,
    softwarelistanegraencontrado varchar(200) NOT NULL,
	data timestamp NOT NULL
);
ALTER TABLE softwareslistanegraencontrados ADD CONSTRAINT pk_softlistencont PRIMARY KEY(idsoftwareslistanegraencontrad);
ALTER TABLE softwareslistanegraencontrados ADD CONSTRAINT fk_softlistencont_itemconf FOREIGN KEY(iditemconfiguracao) REFERENCES itemconfiguracao(iditemconfiguracao);
ALTER TABLE softwareslistanegraencontrados ADD CONSTRAINT fk_softlistencont_softlist FOREIGN KEY (idsoftwareslistanegra) REFERENCES softwareslistanegra(idsoftwareslistanegra);
CREATE INDEX fk_idx_softlistnegrencontr ON softwareslistanegraencontrados(idsoftwareslistanegraencontrad);

-- Fim Ronnie Mikihiro 10/09/2013

-- Início Bruno 10/09/2013

alter table solicitacaoservico add (vencendo varchar(1) default NULL);
alter table solicitacaoservico add (criouproblemaautomatico varchar(1) default NULL);

-- Fim Bruno

-- Incio Euler 10/09/2013

CREATE  TABLE regraescalonamento (
  idregraescalonamento NUMBER(10,0) NOT NULL ,
  idtipogerenciamento NUMBER(10,0) NOT NULL ,
  idservico NUMBER(10,0) NULL ,
  idcontrato NUMBER(10,0) NULL ,
  idsolicitante NUMBER(10,0) NULL ,
  idgrupo NUMBER(10,0) NULL ,
  idtipodemandaservico NUMBER(10,0) NULL ,
  urgencia VARCHAR(1) NULL ,
  impacto VARCHAR(1) NULL ,
  tempoexecucao NUMBER(10,0) NOT NULL ,
  intervalonotificacao NUMBER(10,0) NOT NULL ,
  enviaremail VARCHAR(1),
  criaproblema VARCHAR(1),
  prazocriarproblema NUMBER(10,0) NULL ,
  datainicio date null,
  datafim date null,
  PRIMARY KEY (idregraescalonamento) ,
  foreign key (idtipodemandaservico) references tipodemandaservico (idtipodemandaservico),
  foreign key (idservico) references servico (idservico),
  foreign key (idcontrato) references contratos (idcontrato),
  foreign key (idgrupo) references grupo (idgrupo)
);

CREATE  TABLE escalonamento (
  idescalonamento integer NOT NULL ,
  idregraescalonamento integer NOT NULL ,
  idgrupoexecutor integer NOT NULL ,
  prazoexecucao integer NOT NULL ,
  datainicio date null,
  datafim date null,
  PRIMARY KEY (idescalonamento) ,
  foreign key (idgrupoexecutor) references grupo (idgrupo),
  foreign key (idregraescalonamento) references regraescalonamento (idregraescalonamento)
);

alter table regraescalonamento add tipodataescalonamento number(11,0);
update regraescalonamento set tipodataescalonamento = 1 where tipodataescalonamento is null;
alter table regraescalonamento modify (tipodataescalonamento not null);
alter table escalonamento add idprioridade number(11,0);

-- Fim Euler

-- Incio Emauri 11/09/2013

ALTER TABLE netmap ADD (hardware VARCHAR(255));
ALTER TABLE netmap ADD (sistemaoper VARCHAR(255));
ALTER TABLE netmap ADD (uptime VARCHAR(20));

-- Fim Emauri 11/09/2013

-- Inicio Cleison 12/09/2013

ALTER TABLE itemrequisicaoproduto MODIFY (situacao VARCHAR2(50) DEFAULT NULL);

-- Fim Cleison

-- Inicio Mário Haysaki Júnior 12/09/2013

ALTER TABLE projetos ADD (deleted varchar2(1) DEFAULT NULL);

-- Fim Mário Haysaki Júnior

-- Inicio Bruno Franco 12/09/2013

CREATE TABLE relEscalonamentoSolServico
(
   idsolicitacaoservico integer, 
   idescalonamento integer, 
   CONSTRAINT pk_escalonamento_solicitacao PRIMARY KEY (idsolicitacaoservico, idescalonamento), 
   CONSTRAINT fk_solicitacaoservico FOREIGN KEY (idsolicitacaoservico) REFERENCES solicitacaoservico (idsolicitacaoservico), 
   CONSTRAINT fk_escalonamento FOREIGN KEY (idescalonamento) REFERENCES escalonamento (idescalonamento)
);

-- Fim Bruno Franco

-- Inicio - Bruno Franco 13/09/2013

alter table requisicaomudanca add (vencendo varchar(1) default NULL);

insert into modelosemails (idmodeloemail, titulo, texto, situacao, identificador) values ($id_modeloemail_escalonamento,'Prazo para resolução terminando - ${IDSOLICITACAOSERVICO}','Senhor(a) ${NOMECONTATO},<br /><br />Informamos que o prazo para resolu&ccedil;&atilde;o da solicita&ccedil;&atilde;o&nbsp;${IDSOLICITACAOSERVICO} &nbsp;est&aacute; terminando.<br /><br /><strong>N&uacute;mero:</strong> ${IDSOLICITACAOSERVICO}<br /><strong>Tipo:</strong> ${DEMANDA}<br /><strong>Servi&ccedil;o:</strong> ${SERVICO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong> <br />${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT&nbsp;Tecnologia da Informa&ccedil;&atilde;o Ltda<br />','A','PrazoSolicitacao');

-- Fim - Bruno Franco

-- Inicio - Ronnie Mikihiro Sato Lopes 13/09/2013

INSERT INTO modelosemails
(idmodeloemail,
titulo,
texto,
situacao,
identificador)
VALUES
($id_listanegraemail,
'Softwares Irregulares Encontrados Perante Lista Negra',
'Senhor(a) ${NOMECONTATO},<br /><br />Informamos que foram encontrados softwares irregulares instalados perante a Lista Negra de Softwares, como segue a tabela abaixo:<strong><br /><br /></strong><div style="text-align: left;"><strong>TABELA DE SOFTWARES IRREGULARES PERANTE LISTA NEGRA<br /><br /></strong>${TABELALISTANEGRA}</div><div style="text-align: center;">&nbsp;</div><br /><br />Atenciosamente,<br /><font face="Calibri"><font size="1"><b><span style="COLOR: #31849b; FONT-SIZE: 14pt">Central</span></b><b><span style="FONT-SIZE: 14pt">IT</span></b></font></font>',
'A',
'softwaresListaNegra');

-- Fim - Ronnie Mikihiro Sato Lopes

-- Incio Maycon 13/09/2013 

CREATE TABLE auditoriaitemconfig
(
  idauditoriaitemConfig number(10,0)  NOT NULL,
  iditemconfiguracao number(10,0) ,
  iditemconfiguracaopai number(10,0) ,
  idhistoricoic number(10,0) ,
  idhistoricovalor number(10,0) ,
  idvalor number(10,0) ,
  idusuario number(10,0) ,
  datahoraalteracao timestamp,
  tipoalteracao VARCHAR(20),
  primary key (idauditoriaitemconfig) 
);

alter table itemconfiguracao add (datahoradesinstalacao TIMESTAMP);

-- Fim Maycon

-- Inicio - Bruno Franco 13/09/2013

CREATE TABLE relEscalonamentomudanca
(
   idrequisicaomudanca integer, 
   idescalonamento integer, 
   CONSTRAINT pk_escalonamento_mudanca PRIMARY KEY (idrequisicaomudanca, idescalonamento), 
   CONSTRAINT fk_requisicaomudanca FOREIGN KEY (idrequisicaomudanca) REFERENCES requisicaomudanca (idrequisicaomudanca), 
   CONSTRAINT fk_escalonamento FOREIGN KEY (idescalonamento) REFERENCES escalonamento (idescalonamento)
);

CREATE TABLE relEscalonamentoproblema
(
   idproblema integer, 
   idescalonamento integer, 
   CONSTRAINT pk_escalonamento_problema PRIMARY KEY (idproblema, idescalonamento), 
   CONSTRAINT fk_ problema FOREIGN KEY (idproblema) REFERENCES problema (idproblema) , 
   CONSTRAINT fk_escalonamento FOREIGN KEY (idescalonamento) REFERENCES escalonamento (idescalonamento)
);

-- Fim - Bruno Franco

-- Inicio - Bruno Franco 16/09/2013

alter table problema add (vencendo varchar(1) default NULL);

-- Fim - Bruno Franco

-- Inicio - Riubbe Da Silva Oliveira 16/09/2013

ALTER TABLE tipomovimfinanceiraviagem ADD CONSTRAINT pk_tipomovimfinanviagem PRIMARY KEY(idtipomovimfinanceiraviagem);
ALTER TABLE requisicaoviagem  ADD  tarefainiciada varchar2(1) NULL;

CREATE TABLE controlefinanceiroviagem (
	idcontrolefinanceiroviagem NUMBER(19,0)NOT NULL,
	idresponsavel NUMBER(10,0) DEFAULT NULL,
	idmoeda NUMBER(10,0) DEFAULT NULL,
	datahora date NULL,
	situacao varchar2(20)DEFAULT NULL,
	observacoes clob DEFAULT NULL
);

ALTER TABLE controlefinanceiroviagem ADD CONSTRAINT pk_controlefinanceiroviagem PRIMARY KEY(idcontrolefinanceiroviagem);
ALTER TABLE controlefinanceiroviagem ADD CONSTRAINT fk_controlefinanceiroviagem_em FOREIGN KEY (idresponsavel) REFERENCES empregados(idempregado);
ALTER TABLE controlefinanceiroviagem ADD CONSTRAINT fk_controlefinanceiroviagem_mo FOREIGN KEY (idmoeda) REFERENCES moedas(idmoeda);
create index fk_idx_controlefinanceiroviag on controlefinanceiroviagem(idcontrolefinanceiroviagem);

CREATE TABLE adiantamentoviagem (
	idadiantamentoviagem NUMBER(19, 0) NOT NULL,
	idresponsavel NUMBER(10,0) DEFAULT NULL,
	idsolicitacaoservico NUMBER(19, 0) DEFAULT NULL,
	idempregado NUMBER(10,0) DEFAULT NULL,
	datahora DATE,
	valortotaladiantado FLOAT,
	situacao varchar2(20)NOT NULL,
	observacoes clob DEFAULT NULL
);

ALTER TABLE adiantamentoviagem ADD CONSTRAINT pk_adiantamentoviagem PRIMARY KEY(idadiantamentoviagem);
ALTER TABLE adiantamentoviagem ADD CONSTRAINT fk_adiantamentoviagem_empregad FOREIGN KEY(idresponsavel) REFERENCES empregados(idempregado);
ALTER TABLE adiantamentoviagem ADD CONSTRAINT fk_adiantamentoviagem_integran FOREIGN KEY(idsolicitacaoservico, idempregado) 
REFERENCES integranteviagem(idsolicitacaoservico, idempregado);
CREATE INDEX fk_idx_adiantamentoviagem on adiantamentoviagem(idadiantamentoviagem);

CREATE TABLE itemcontrolefinanceiroviagem (
	iditemcontrolefinanceiroviagem NUMBER(19,0) NOT NULL,
	idcontrolefinanceiroviagem NUMBER(19,0) DEFAULT NULL,
	idformapagamento  NUMBER(10,0) DEFAULT NULL,
	idadiantamentoviagem NUMBER(19,0) DEFAULT NULL,
	idfornecedor NUMBER(19,0) DEFAULT NULL,
	idjustificativa NUMBER(10,0) DEFAULT NULL,
	idsolicitacaoservico NUMBER(19,0) DEFAULT NULL,
	idempregado NUMBER(10,0) DEFAULT NULL,
	idtipomovimfinanceiraviagem NUMBER(19,0) DEFAULT NULL,
	complementojustificativa clob DEFAULT NULL,
	quantidade numeric(8,2) DEFAULT NULL,
	valorunitario float DEFAULT NULL,
	valoradiantamento float DEFAULT NULL,
	tipopassagem varchar2(20) DEFAULT NULL,
	localizador varchar2(50) DEFAULT NULL,
	assento varchar2(20) DEFAULT NULL,
	situacao varchar2(20) DEFAULT NULL,
	datafim DATE NULL DEFAULT NULL,
	prazocotacao DATE NULL DEFAULT NULL,
	observacao clob NULL,
	dataexecucao DATE NULL DEFAULT NULL,
	datahoraprazocotacao DATE NULL DEFAULT NULL
);

ALTER TABLE itemcontrolefinanceiroviagem ADD CONSTRAINT pk_itemcontrolefinanceiroviage PRIMARY KEY(iditemcontrolefinanceiroviagem);
ALTER TABLE itemcontrolefinanceiroviagem ADD CONSTRAINT fk_itemctrlfinan_ctrlfinan FOREIGN KEY(idcontrolefinanceiroviagem) REFERENCES controlefinanceiroviagem(idcontrolefinanceiroviagem);
ALTER TABLE itemcontrolefinanceiroviagem ADD CONSTRAINT fk_itemctrlfinan_formapagam FOREIGN KEY(idformapagamento) REFERENCES formapagamento(idformapagamento);
ALTER TABLE itemcontrolefinanceiroviagem ADD CONSTRAINT fk_itemctrlfinan_adiantviag FOREIGN KEY(idadiantamentoviagem) REFERENCES adiantamentoviagem(idadiantamentoviagem);
ALTER TABLE itemcontrolefinanceiroviagem ADD CONSTRAINT fk_itemctrlfinan_fornecedor FOREIGN KEY(idfornecedor) REFERENCES fornecedor(idfornecedor);
ALTER TABLE itemcontrolefinanceiroviagem ADD CONSTRAINT fk_itemctrlfinan_justificsoli FOREIGN KEY(idjustificativa) REFERENCES justificativasolicitacao(idjustificativa);
ALTER TABLE itemcontrolefinanceiroviagem ADD CONSTRAINT fk_itemctrlfinan_soliserv_em FOREIGN KEY(idsolicitacaoservico, idempregado) REFERENCES integranteviagem(idsolicitacaoservico, idempregado);
ALTER TABLE itemcontrolefinanceiroviagem ADD CONSTRAINT fk_itemctrlfinan_tipomovfinan FOREIGN KEY(idtipomovimfinanceiraviagem) REFERENCES tipomovimfinanceiraviagem(idtipoMovimFinanceiraViagem);
create index fk_idx_itemctrlfinanviagem on itemcontrolefinanceiroviagem(iditemcontrolefinanceiroviagem);


CREATE TABLE prestacaocontasviagem (
  idprestacaocontasviagem NUMBER(19,0) NOT NULL,
  idresponsavel  NUMBER(10,0) DEFAULT NULL,
  idaprovacao NUMBER(10,0) DEFAULT NULL,
  idsolicitacaoservico NUMBER(19,0) DEFAULT NULL,
  idempregado  NUMBER(10,0) DEFAULT NULL,
  datahora DATE NULL,
  situacao varchar2(35) DEFAULT NULL,
  iditemtrabalho NUMBER(20,0) DEFAULT NULL
);

ALTER TABLE prestacaocontasviagem ADD CONSTRAINT pk_prestacaocontasviagem PRIMARY KEY (idprestacaocontasviagem);
ALTER TABLE prestacaocontasviagem ADD CONSTRAINT fk_prestacaocontasviagem_respo FOREIGN KEY (idresponsavel) REFERENCES empregados (idempregado);
ALTER TABLE prestacaocontasviagem ADD CONSTRAINT fk_prestacaocontasviagem_aprov FOREIGN KEY (idaprovacao) REFERENCES parecer (idparecer);
ALTER TABLE prestacaocontasviagem ADD CONSTRAINT fk_prestacaocontasviagem_empre FOREIGN KEY (idsolicitacaoservico, idempregado) REFERENCES integranteviagem (idsolicitacaoservico, idempregado);
CREATE INDEX fk_idx_prestacaocontasviagem on prestacaocontasviagem(idprestacaocontasviagem);

CREATE TABLE itemprestacaocontasviagem (
  iditemprestcontasviagem NUMBER(19,0) NOT NULL,
  idprestacaocontasviagem NUMBER(19,0) DEFAULT NULL,
  iditemdespesaviagem NUMBER(19,0) DEFAULT NULL,
  idfornecedor NUMBER(19,0) DEFAULT NULL,
  data DATE DEFAULT NULL,
  nomefornecedor VARCHAR2(100) DEFAULT NULL,
  numeroDocumento VARCHAR2(50) DEFAULT NULL,
  descricao VARCHAR2(200) DEFAULT NULL,
  valor FLOAT NOT NULL
);

ALTER TABLE itemprestacaocontasviagem ADD CONSTRAINT pk_itemprestacaocontasviagem PRIMARY KEY (iditemprestcontasviagem);
ALTER TABLE itemprestacaocontasviagem ADD CONSTRAINT fk_prestacaocontasviagem_prest FOREIGN KEY (idprestacaocontasviagem) REFERENCES prestacaocontasviagem (idprestacaocontasviagem);
ALTER TABLE itemprestacaocontasviagem ADD CONSTRAINT fk_prestacaocontasviagem_itemd FOREIGN KEY (iditemdespesaviagem) REFERENCES itemcontrolefinanceiroviagem (iditemcontrolefinanceiroviagem);
ALTER TABLE itemprestacaocontasviagem ADD CONSTRAINT fk_prestacaocontasviagem_forne FOREIGN KEY(idfornecedor) REFERENCES fornecedor (idfornecedor);
CREATE INDEX fk_idx_itemprestacaocontasviag on itemprestacaocontasviagem(iditemprestcontasviagem);
  
  
INSERT INTO bpm_tipofluxo (idtipofluxo,nomefluxo,descricao,nomeclassefluxo) VALUES ($id_tipo_fluxo,'RequisicaoViagem','Requisicão Viagem','br.com.centralit.citcorpore.bpm.negocio.ExecucaoRequisicaoViagem');

INSERT INTO bpm_fluxo (idfluxo,versao,idtipofluxo,variaveis,conteudoxml,datainicio,datafim) VALUES ($id_fluxo,'01.0',$id_tipo_fluxo,'solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1','',TO_DATE('2013/09/12','YYYY/MM/DD'),NULL);

INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_00,$id_fluxo,'Inicio','','','','','','','','','','','','','',47,33,32,32,'','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_01,$id_fluxo,'Tarefa','','Em Execução','Em Execução','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','',NULL,'script:#{execucaoFluxo}.recuperaLoginResponsaveisCotacao();','','','','','',201,16,65,140,'','ControleFinanceiroViagem',NULL,'','N','','S',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_02,$id_fluxo,'Tarefa','','Autorizar requisição','Autorizar requisição','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','',NULL,'script:#{execucaoFluxo}.recuperaLoginAutorizadores();','','','','','',226,197,65,140,'','AutorizacaoViagem',NULL,'','N','','S',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_03,$id_fluxo,'Tarefa','','Adiantamento','Adiantamento','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','',NULL,'script:#{execucaoFluxo}.recuperaLoginResponsaveisAdiantamento();','','','','','',532,238,65,140,'','AdiantamentoViagem',NULL,'','N','','S',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_04,$id_fluxo,'Tarefa','','Prestação de Contas','Prestação de Contas','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','',NULL,'script:#{execucaoFluxo}.recuperaLoginIntegrantes();','','','','','',939,44,65,140,'','PrestacaoContasViagem',NULL,'','U','','S',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_05,$id_fluxo,'Tarefa','','Conferência','Conferência','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','',NULL,'script:#{execucaoFluxo}.recuperaLoginResponsaveisConferencia();','#{execucaoFluxo}.associaItemTrabalhoPrestacaoConferencia(#{itemTrabalho}); ','#{execucaoFluxo}.enviaEmailNaoAprovado();','','','',936,161,65,140,'','ConferenciaViagem',NULL,'','S','','S',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_06,$id_fluxo,'Tarefa','','Em Revisão','Em Revisão','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?alterarSituacao=S','','#{solicitacaoServico.grupoNivel1}','','','','','','',224,341,65,140,'','AlteracaoRequisicaoViagem',NULL,'','N','','S',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_07,$id_fluxo,'Tarefa','','Corrigir Prestação de Contas','Corrigir Prestação de Contas','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','',NULL,'script:#{execucaoFluxo}.recuperaLoginIntegranteCorrecao();','#{execucaoFluxo}.associaItemTrabalhoPrestacaoCorrecao(#{itemTrabalho}); ','','','','',895,307,65,140,'','CorrigirPrestacaoContas',NULL,'','S','','S',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_08,$id_fluxo,'Tarefa','','Execução Compras','Execução Compras','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','',NULL,'script:#{execucaoFluxo}.recuperaLoginResponsaveisCotacao();','','','','','',532,72,65,140,'','ExecComprasViagem',NULL,'','N','','S',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_09,$id_fluxo,'Script','','Encerra','','','','','','','','','#{execucaoFluxo}.encerra();','','',532,342,65,140,'','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_10,$id_fluxo,'Evento','','','','','','','','','','','','','',710,178,32,32,'','',5,'!#{solicitacaoServico}.finalizada();','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_11,$id_fluxo,'Email','','','','','','','','script:#{execucaoFluxo}.recuperaLoginResponsaveisCotacao();','','','','','',127,38,22,31,'CriacaoReqViagem','',NULL,'','','#{solicitacaoServico.emailcontato}','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_12,$id_fluxo,'Email','','','','','','','','script:#{execucaoFluxo}.recuperaLoginResponsaveisCotacao();','','','','','',254,101,22,31,'ItemAtrasadosViagem','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_13,$id_fluxo,'Email','','','','','','','','script:#{execucaoFluxo}.recuperaLoginResponsaveisCotacao();','','','','','',478,36,22,31,'AprovacaoReqViagem','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_14,$id_fluxo,'Email','','','','','','','','script:#{execucaoFluxo}.recuperaLoginAutorizadores();','','','','','',321,158,22,31,'AutorizarReqViagem','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_15,$id_fluxo,'Email','','','','','','','','','','','','','',733,325,22,31,'ReqViagemFinalizada','',NULL,'','','#{solicitacaoServico.emailcontato}','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_16,$id_fluxo,'Email','','','','','','','','','','','','','',414,348,22,31,'ReprovacaoReqViagem','',NULL,'','','#{solicitacaoServico.emailcontato}','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_17,$id_fluxo,'Email','','','','','','','','script:#{execucaoFluxo}.recuperaLoginResponsaveisConferencia();','','','','','',861,183,22,31,'ConfReqViagem','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_18,$id_fluxo,'Email','','','','','','','','script:#{execucaoFluxo}.recuperaLoginResponsaveisAdiantamento();','','','','','',586,175,22,31,'AdiantaReqViagem','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_19,$id_fluxo,'Email','','','','','','','','script:#{execucaoFluxo}.recuperaLoginIntegrantes();','','','','','',857,65,22,31,'PrestContaViagem','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_20,$id_fluxo,'Porta','','','','','','','','','','','','','',408,271,42,42,'','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_21,$id_fluxo,'Porta','','','','','','','','','','','','','',770,173,42,42,'','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_22,$id_fluxo,'Porta','','','','','','','','','','','','','',383,27,42,42,'','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_23,$id_fluxo,'Porta','','','','','','','','','','','','','',473,123,42,42,'','',NULL,'','','','',NULL);
INSERT INTO bpm_elementofluxo (idelemento,idfluxo,tipoelemento,subtipo,nome,documentacao,tipointeracao,url,visao,grupos,usuarios,acaoentrada,acaosaida,script,textoemail,nomefluxoencadeado,posx,posy,altura,largura,modeloemail,template,intervalo,condicaodisparo,multiplasinstancias,destinatariosemail,contabilizasla,percexecucao) VALUES ($id_elemento_24,$id_fluxo,'Finalizacao','','','','','','','','','','','','','',586,464,32,32,'','',NULL,'','','','',NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_00,$id_elemento_11,$id_fluxo,NULL,NULL,'',1,3,103,49,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_01,$id_elemento_22,$id_fluxo,NULL,NULL,'',1,3,362,48.25,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_02,$id_elemento_20,$id_fluxo,NULL,NULL,'',2,3,296,292,'S','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_03,$id_elemento_10,$id_fluxo,NULL,NULL,'',1,3,691,232.25,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_06,$id_elemento_09,$id_fluxo,NULL,NULL,'#{solicitacaoServico}.finalizada();',2,3,480,419,'S','finalizada');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_06,$id_elemento_11,$id_fluxo,NULL,NULL,'!#{solicitacaoServico}.finalizada();',3,2,142,327,'S','não finalizada');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_08,$id_elemento_18,$id_fluxo,NULL,NULL,'',2,0,601.75,156,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_09,$id_elemento_24,$id_fluxo,NULL,NULL,'',2,0,602,435.5,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_10,$id_elemento_21,$id_fluxo,NULL,NULL,'',1,3,756,194,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_11,$id_elemento_01,$id_fluxo,NULL,NULL,'',1,3,179.5,48.75,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_12,$id_elemento_01,$id_fluxo,NULL,NULL,'',0,2,270.25,91,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_13,$id_elemento_08,$id_fluxo,NULL,NULL,'',1,0,561.5,53.5,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_14,$id_elemento_02,$id_fluxo,NULL,NULL,'',3,0,296,169,'S','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_15,$id_elemento_09,$id_fluxo,NULL,NULL,'',3,1,702.5,355.25,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_16,$id_elemento_06,$id_fluxo,NULL,NULL,'',3,1,389,366.25,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_17,$id_elemento_05,$id_fluxo,NULL,NULL,'',1,3,914,193.75,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_18,$id_elemento_03,$id_fluxo,NULL,NULL,'',2,0,601.75,217.5,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_19,$id_elemento_04,$id_fluxo,NULL,NULL,'',1,3,913.5,76.25,'N','');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_20,$id_elemento_16,$id_fluxo,NULL,NULL,'!#{execucaoFluxo}.requisicaoAutorizada();',2,0,429.25,330.5,'N','Não autorizado');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_20,$id_elemento_23,$id_fluxo,NULL,NULL,'#{execucaoFluxo}.requisicaoAutorizada();',1,3,462,221,'S','Autorizado');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_21,$id_elemento_07,$id_fluxo,NULL,NULL,'#{execucaoFluxo}.corrigirPrestacaoContas();',2,3,852,304,'S','Corrigir Prestação de Contas');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_21,$id_elemento_15,$id_fluxo,NULL,NULL,'#{execucaoFluxo}.requisicaoViagemFinalizada();',2,0,748,280,'S','Aprovada e Finalizada');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_21,$id_elemento_17,$id_fluxo,NULL,NULL,'#{execucaoFluxo}.isTarefaConferencia();',1,3,836.5,194,'N','Aguardando Conferência');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_21,$id_elemento_19,$id_fluxo,NULL,NULL,'#{execucaoFluxo}.isEstadoPrestacaoContas();',0,3,818,118.5,'N','Aguardando Prestação de Contas');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_22,$id_elemento_13,$id_fluxo,NULL,NULL,'!#{execucaoFluxo}.exigeAutorizacao();',1,3,451.5,47.5,'N','não exige autorização');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_22,$id_elemento_14,$id_fluxo,NULL,NULL,'#{execucaoFluxo}.exigeAutorizacao();',2,1,405,168,'S','exige autorização');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_23,$id_elemento_12,$id_fluxo,NULL,NULL,'!#{execucaoFluxo}.validaPrazoItens();',0,1,429,111,'S','Itens Não Aprovados');
INSERT INTO bpm_sequenciafluxo (idelementoorigem,idelementodestino,idfluxo,nomeclasseorigem,nomeclassedestino,condicao,idconexaoorigem,idconexaodestino,bordax,borday,posicaoalterada,nome) VALUES ($id_elemento_23,$id_elemento_13,$id_fluxo,NULL,NULL,'#{execucaoFluxo}.validaPrazoItens();',0,2,493.75,90.5,'N','Itens Aprovados');

INSERT INTO templatesolicitacaoservico (idtemplate,identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar,idquestionario,aprovacao) VALUES ($id_template_14,'CriacaoRequisicaoViagem','Requisiçao Viagem','br.com.centralit.citcorpore.bean.RequisicaoViagemDTO','br.com.centralit.citcorpore.ajaxForms.RequisicaoViagem','br.com.centralit.citcorpore.negocio.RequisicaoViagemServiceEjb','/pages/requisicaoViagem/requisicaoViagem.load','','N','N','N',600,'N','N','N','N','N','N','S',NULL,'N');
INSERT INTO templatesolicitacaoservico (idtemplate,identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar,idquestionario,aprovacao) VALUES ($id_template_15,'ControleFinanceiroViagem','Controle Financeiro Viagem','br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO','br.com.centralit.citcorpore.ajaxForms.ControleFinanceiroViagem','br.com.centralit.citcorpore.negocio.ControleFinanceiroViagemServiceEjb','/pages/controleFinanceiroViagem/controleFinanceiroViagem.load','','N','N','N',700,'N','N','N','N','N','N','S',NULL,'N');
INSERT INTO templatesolicitacaoservico (idtemplate,identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar,idquestionario,aprovacao) VALUES ($id_template_16,'AutorizacaoViagem','Autorização Viagem','br.com.centralit.citcorpore.bean.RequisicaoViagemDTO','br.com.centralit.citcorpore.ajaxForms.AutorizacaoViagem','br.com.centralit.citcorpore.negocio.AutorizacaoViagemServiceEjb','/pages/autorizacaoViagem/autorizacaoViagem.load','','N','N','N',800,'N','N','N','N','N','N','S',NULL,'N');
INSERT INTO templatesolicitacaoservico (idtemplate,identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar,idquestionario,aprovacao) VALUES ($id_template_17,'AlteracaoRequisicaoViagem','Alteração Requisição Viagem','br.com.centralit.citcorpore.bean.RequisicaoViagemDTO','br.com.centralit.citcorpore.ajaxForms.RequisicaoViagem','br.com.centralit.citcorpore.negocio.RequisicaoViagemServiceEjb','/pages/requisicaoViagem/requisicaoViagem.load','','N','S','N',600,'N','N','N','N','N','N','S',NULL,'N');
INSERT INTO templatesolicitacaoservico (idtemplate,identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar,idquestionario,aprovacao) VALUES ($id_template_18,'AdiantamentoViagem','Adiantamento Viagem','br.com.centralit.citcorpore.bean.AdiantamentoViagemDTO','br.com.centralit.citcorpore.ajaxForms.AdiantamentoViagem','br.com.centralit.citcorpore.negocio.AdiantamentoViagemServiceEjb','/pages/adiantamentoViagem/adiantamentoViagem.load','','N','N','N',500,'N','N','N','N','N','N','S',NULL,'N');
INSERT INTO templatesolicitacaoservico (idtemplate,identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar,idquestionario,aprovacao) VALUES ($id_template_19,'PrestacaoContasViagem','Prestacao Contas Viagem','br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO','br.com.centralit.citcorpore.ajaxForms.PrestacaoContasViagem','br.com.centralit.citcorpore.negocio.PrestacaoContasViagemServiceEjb','/pages/prestacaoContasViagem/prestacaoContasViagem.load','','N','N','N',600,'N','N','N','N','N','N','S',NULL,'N');
INSERT INTO templatesolicitacaoservico (idtemplate,identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar,idquestionario,aprovacao) VALUES ($id_template_20,'ConferenciaViagem','Conferencia Viagem','br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO','br.com.centralit.citcorpore.ajaxForms.ConferenciaViagem','br.com.centralit.citcorpore.negocio.ConferenciaViagemServiceEjb','/pages/conferenciaViagem/conferenciaViagem.load','','N','N','N',600,'N','N','N','N','N','N','S',NULL,'N');
INSERT INTO templatesolicitacaoservico (idtemplate,identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar,idquestionario,aprovacao) VALUES ($id_template_21,'ExecComprasViagem','Execução Compras Viagem','br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO','br.com.centralit.citcorpore.ajaxForms.CompraViagem','br.com.centralit.citcorpore.negocio.CompraViagemServiceEjb','/pages/compraViagem/compraViagem.load','','N','N','N',800,'N','N','N','N','N','N','S',NULL,'N');
INSERT INTO templatesolicitacaoservico (idtemplate,identificacao,nometemplate,nomeclassedto,nomeclasseaction,nomeclasseservico,urlrecuperacao,scriptaposrecuperacao,habilitadirecionamento,habilitasituacao,habilitasolucao,alturadiv,habilitaurgenciaimpacto,habilitanotificacaoemail,habilitaproblema,habilitamudanca,habilitaitemconfiguracao,habilitasolicitacaorelacionada,habilitagravarecontinuar,idquestionario,aprovacao) VALUES ($id_template_22,'CorrigirPrestacaoContas','Corrigir Prestacao Contas','br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO','br.com.centralit.citcorpore.ajaxForms.CorrigirPrestacaoContas','br.com.centralit.citcorpore.negocio.PrestacaoContasViagemServiceEjb','/pages/corrigirPrestacaoContas/corrigirPrestacaoContas.load','','N','N','N',700,'N','N','N','N','N','N','N',NULL,'S');

INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_55,'Requisiçao Viagem - ${IDSOLICITACAOSERVICO}','&nbsp;&nbsp;Senhor(a) ${NOMECONTATO},<br /><br />Informamos que a sua requisi&ccedil;&atilde;o foi registrada em ${DATAHORASOLICITACAO}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO}<br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br />${INFORMACOESCOMPLEMENTARESHTML}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','CriacaoReqViagem');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_56,'Requisição Viagem  - ${IDSOLICITACAOSERVICO}','&nbsp;&nbsp;&nbsp;Senhor(a) ${NOMECONTATO},<br /><br />Informamos que a sua requisi&ccedil;&atilde;o foi registrada em ${DATAHORASOLICITACAO}, conforme os dados abaixo:<br /><strong><br />N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO}<br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br />Data Inicio:${DATAINICIOVIAGEM}<br />DataFim:${DATAFIMVIAGEM}<br />Cidade Origem:<br />Cidade Destino:<br />Motivo:<br />${DESCRICAOMOTIVO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','ReqVigIntegrate');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_65,'Criação da Requisição de Viagem - ${IDSOLICITACAOSERVICO}','&nbsp;Informamos que foi criado a requisi&ccedil;&atilde;o de viagem : ${IDSOLICITACAOSERVICO}.<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','CRV');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_66,'Requisiçao Viagem - ${IDSOLICITACAOSERVICO}','&nbsp;&nbsp;&nbsp;Senhor(a) ${NOMECONTATO},<br /><br />Informamos que a requisi&ccedil;&atilde;o&nbsp;<strong>N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO}&nbsp;&nbsp;necessita de sua autoriza&ccedil;&atilde;o:<br /><br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br />${INFORMACOESCOMPLEMENTARESHTML}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','AutorizarReqViagem');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_67,'Requisiçao Viagem - ${IDSOLICITACAOSERVICO}','&nbsp;&nbsp;&nbsp;&nbsp;Senhor(a),<br /><br />Informamos que a requisi&ccedil;&atilde;o de viagem&nbsp;<strong>N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO} foi aprovada e aguarda a compras dos itens da viagem <br /><br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br />${INFORMACOESCOMPLEMENTARESHTML}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','AprovacaoReqViagem');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_68,'Requisiçao Viagem - ${IDSOLICITACAOSERVICO}','Senhor(a) ${NOMECONTATO},<br /><br />Informamos que a requisi&ccedil;&atilde;o de viagem&nbsp;<strong>N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO} n&atilde;o foi Autorizada pela seguinte justificativa :<br /><br />${JUSTIFICATIVA}<br /><br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br />${INFORMACOESCOMPLEMENTARESHTML}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','ReprovacaoReqViagem');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_70,'Requisiçao Viagem - ${IDSOLICITACAOSERVICO}','&nbsp;&nbsp;&nbsp; Senhor(a),<br /><br />Informamos que a requisi&ccedil;&atilde;o de viagem&nbsp;<strong>N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO} foi aprovada e aguarda o adiantamento do(s) item(s) da viagem <br /><br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br />${INFORMACOESCOMPLEMENTARESHTML}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','AdiantaReqViagem');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_71,'Requisiçao Viagem - ${IDSOLICITACAOSERVICO}','&nbsp;&nbsp;&nbsp; Senhor(a),<br /><br />Informamos que algum(s) Item(s) da requisi&ccedil;&atilde;o de viagem&nbsp;<strong>N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO} tiveram o prazo de cota&ccedil;&atilde;o estourado.<br />Favor verificar.<br /><br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br />${INFORMACOESCOMPLEMENTARESHTML}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','ItemAtrasadosViagem');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_72,'Requisiçao Viagem - ${IDSOLICITACAOSERVICO}','&nbsp;&nbsp;&nbsp; Senhor(a),<br /><br />Desejamos boa viagem e infomamos que no seu retorno a requisi&ccedil;&atilde;o de viagem&nbsp;<strong>N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO} j&aacute; estar&aacute; aguardando para a tarefa de presta&ccedil;&atilde;o de contas.<br /><br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br />${INFORMACOESCOMPLEMENTARESHTML}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','PrestContaViagem');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_73,'Requisiçao Viagem - ${IDSOLICITACAOSERVICO}','&nbsp;&nbsp;&nbsp; Senhor(a),<br /><br />Informamos que um dos integrantes da requisi&ccedil;&atilde;o de viagem&nbsp;<strong>N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO} ja prestou contas de seus gastos e agora aguarda a conf&ecirc;rencia dos mesmos.<br /><br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br />${INFORMACOESCOMPLEMENTARESHTML}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','ConfReqViagem');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_74,'Requisiçao Viagem - ${IDSOLICITACAOSERVICO}','&nbsp;&nbsp;&nbsp; Senhor(a),<br /><br />Informamos que presta&ccedil;&atilde;o de contas da requisi&ccedil;&atilde;o de viagem&nbsp;<strong>N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO} n&atilde;o foi aprovada, favor verificar o motivo e fazer as corre&ccedil;&otilde;es necess&aacute;rias.<br /><br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br />${INFORMACOESCOMPLEMENTARESHTML}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','PrestNaoApViagem');
INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modelo_75,'Requisiçao Viagem - ${IDSOLICITACAOSERVICO}','&nbsp;&nbsp;&nbsp; Senhor(a)&nbsp; ${NOMECONTATO},<br /><br /><br />Informamos que a requisi&ccedil;&atilde;o de viagem&nbsp;<strong>N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO} foi encerrada com sucesso.<br /><br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br />${INFORMACOESCOMPLEMENTARESHTML}<br /><br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.','A','ReqViagemFinalizada');

alter table controlefinanceiroviagem add idresponsavelcompras number(11,0) null;

-- Fim - Riubbe Da Silva Oliveira

-- Inicio - Bruno Franco 18/09/2013

alter table regraescalonamento modify (tempoexecucao null);

-- Fim - Bruno Franco

-- Inicio - Uelen Paulo - 23/09/2013

create index idx_datahorafinalizacao on bpm_itemtrabalhofluxo(datahorafinalizacao);

-- Fim - Uelen Paulo

-- Inicio - Bruno Franco 24/09/2013

ALTER TABLE problema MODIFY(descricao varchar2(3000));

-- Fim - Bruno Franco

-- Inicio Maycon 25/09/2013

alter table solicitacaoservico add (idusuarioresponsavelatual number(10,0));

-- Fim Maycon

-- Inicio - Bruno Franco 04/10/2013

ALTER TABLE baseconhecimento ADD (idsolicitacaoservico number(11,0) default null);

-- Fim Bruno Franco

-- Inicio - Maycon 09/10/2013

ALTER TABLE parametrocorpore add ( tipodado varchar2(50) NULL);

-- Fim Maycon

-- Inicio - Rodrigo Pecci Acorse 17/10/2013

CREATE INDEX INDEX_GRUPO ON GRUPOSEMPREGADOS (IDGRUPO ASC);
CREATE INDEX INDEX_CONTRATOSGRUPOS_GRUPO ON CONTRATOSGRUPOS (IDGRUPO ASC);
CREATE INDEX INDEX_CONTRATOSGRUPOS_CONTRATO ON CONTRATOSGRUPOS (IDCONTRATO ASC);

-- Fim - Rodrigo Pecci Acorse

-- Inicio - Euler 23/10/2013

delete from menu where nome = '$menu.esconder';
delete from parametrocorpore where idparametrocorpore in (20,21);

-- Fim - Euler

-- Inicio - Rodrigo Pecci Acorse 28/10/2013

CREATE INDEX idx_idelementoorigem ON bpm_sequenciafluxo (idelementoorigem ASC);

-- Fim - Rodrigo Pecci Acorse

-- Inicio - Murilo Gabriel Rodrigues 31/10/2013

UPDATE BPM_TIPOFLUXO SET DESCRICAO = 'Solicitação de Serviço' WHERE IDTIPOFLUXO = 1;
INSERT INTO lingua (idlingua, nome, sigla, datainicio, datafim) VALUES ($id_ligua_espanhol, 'Español', 'ES', to_date('2013-10-31', 'yyyy-mm-dd'), NULL);

-- Fim - Murilo Gabriel Rodrigues

-- Inicio - Bruno Carvalho de Aquino 01/11/2013

CREATE TABLE historicoitemrequisicao ( 
     idhistorico      NUMBER (10, 0) NOT NULL, 
     iditemrequisicao NUMBER (10, 0) NOT NULL, 
     idresponsavel    NUMBER (10, 0) NOT NULL, 
     datahora         TIMESTAMP NOT NULL, 
     acao             VARCHAR(100), 
     situacao         NUMBER (10, 0) NOT NULL, 
     complemento      VARCHAR2(400), 
     PRIMARY KEY (idhistorico, iditemrequisicao, idresponsavel), 
     FOREIGN KEY (iditemrequisicao) REFERENCES itemrequisicaoproduto (iditemrequisicaoproduto), 
     FOREIGN KEY (idresponsavel) REFERENCES empregados (idempregado) 
);

-- Fim - Bruno Carvalho de Aquino 01/11/2013

-- INICIO Thiago Fernandes Oliveira 01/11/2013

alter table motivosuspensaoativid add datafim date;

-- FIM Thiago Fernandes Oliveira

-- INICIO Emauri 05/11/2013

CREATE TABLE bi_categorias 
  ( 
     idcategoria    NUMBER(10,0) NOT NULL, 
     idcategoriapai NUMBER(10,0) NULL, 
     nomecategoria  VARCHAR2(80) NOT NULL, 
     identificacao  VARCHAR2(70) NOT NULL, 
     situacao       CHAR(1) NOT NULL, 
     PRIMARY KEY (idcategoria),
	 FOREIGN KEY (idcategoriapai) references bi_categorias (idcategoria)
  );

CREATE INDEX ix_bi_identcatg ON bi_categorias (identificacao ASC);

CREATE TABLE bi_consulta 
  ( 
     idconsulta     NUMBER(10,0) NOT NULL, 
     idcategoria    NUMBER(10,0) NOT NULL, 
     identificacao  VARCHAR2(70) NOT NULL, 
     nomeconsulta   VARCHAR2(255) NOT NULL, 
     tipoconsulta   CHAR(1) NOT NULL, 
     textosql       CLOB NULL, 
     acaocruzado    CHAR(1) NULL, 
     situacao       CHAR(1) NULL, 
     template       CLOB NULL, 
     scriptexec     CLOB NULL, 
     parametros     CLOB NULL, 
     naoatualizbase CHAR(1) NULL, 
     PRIMARY KEY (idconsulta),
     FOREIGN KEY (idcategoria) references bi_categorias (idcategoria)
  );

CREATE INDEX ix_bi_ident ON bi_consulta (identificacao ASC);
CREATE INDEX ix_bi_categ ON bi_consulta (idcategoria ASC);

CREATE TABLE bi_consultacolunas 
  ( 
     idconsultacoluna NUMBER(10,0) NOT NULL, 
     idconsulta       NUMBER(10,0) NOT NULL, 
     nomecoluna       VARCHAR2(90) NOT NULL, 
     tipofiltro       CHAR(1) NULL, 
     ordem            NUMBER(10,0) NULL, 
     PRIMARY KEY (idconsultacoluna),
     FOREIGN KEY (idconsulta) references bi_consulta (idconsulta)
  );
  
CREATE INDEX ix_bi_idconscols ON bi_consultacolunas (idconsulta ASC);

INSERT INTO bi_categorias 
            (idcategoria,idcategoriapai,nomecategoria,identificacao,situacao) 
VALUES      (1,NULL,'Incidentes/Requisições','INCREQ001','A');

INSERT INTO bi_categorias 
            (idcategoria,idcategoriapai,nomecategoria,identificacao,situacao) 
VALUES      (2,NULL,'Ativos e Configuração','ATVCFG001','A');

INSERT INTO bi_categorias 
            (idcategoria,idcategoriapai,nomecategoria,identificacao,situacao) 
VALUES      (3,NULL,'Base de Conhecimento','BASECG001','A');

INSERT INTO bi_categorias 
            (idcategoria,idcategoriapai,nomecategoria,identificacao,situacao) 
VALUES      (4,NULL,'Níveis de Serviço','NIVELS001','A');

INSERT INTO bi_categorias 
            (idcategoria,idcategoriapai,nomecategoria,identificacao,situacao) 
VALUES      (5,NULL,'Problemas','PROBL001','A');

INSERT INTO bi_categorias 
            (idcategoria,idcategoriapai,nomecategoria,identificacao,situacao) 
VALUES      (6,NULL,'Mudanças','MUDAN001','A');

INSERT INTO bi_categorias 
            (idcategoria,idcategoriapai,nomecategoria,identificacao,situacao) 
VALUES      (7,NULL,'Liberações','LIBER001','A');

INSERT INTO bi_categorias 
            (idcategoria,idcategoriapai,nomecategoria,identificacao,situacao) 
VALUES      (8,NULL,'Catalogo de Serviços','CATAL001','A');

INSERT INTO bi_categorias 
            (idcategoria,idcategoriapai,nomecategoria,identificacao,situacao) 
VALUES      (9,NULL,'Projetos','PROJET001','A');

-- FIM Emauri

-- INICIO Murilo Gabriel Rodrigues 01/11/2013

delete from menu where link like '/inventario/inventario.load';

-- FIM Murilo Gabriel Rodrigues

-- INICIO - MURILO PACHECO 12/11/2013

CREATE TABLE rh_experienciaprofissionalcurr (
  idexperienciaprofissional NUMBER(10,0) NOT NULL,
  periodo VARCHAR2(100) NULL,
  funcao VARCHAR2(100) NULL,
  localidade VARCHAR2(100) NULL,
  idcurriculo VARCHAR2(100) NULL,
  idrequisicaomudanca NUMBER(10,0) NOT NULL,
  descricaoempresa VARCHAR2(100) NULL,
  PRIMARY KEY (idexperienciaprofissional)
);

CREATE TABLE rh_competencia (
  idcompetencia NUMBER(10,0) NOT NULL,
  descricaocompetencia VARCHAR2(100) NULL,
  idcurriculo NUMBER(10,0) NULL,
  PRIMARY KEY (idcompetencia)
);

CREATE TABLE rh_justificativaacaocurriculo (
  idjustificativaacaocurriculo NUMBER(10,0) NOT NULL,
  nomejustificativaacaocurriculo VARCHAR2(250) NULL,
  PRIMARY KEY (idjustificativaacaocurriculo)
);

create table rh_historicoacaocurriculo (
  idhistoricoacaocurriculo NUMBER(10,0) NOT NULL,
  idcurriculo NUMBER(10,0) NULL,
  idjustificativaacaocurriculo NUMBER(10,0),
  complementojustificativa VARCHAR2(250),
  datahora TIMESTAMP,
  acao CHAR(1),
  primary key (idhistoricoacaocurriculo)
);

CREATE TABLE rh_conhecimento (
  idConhecimento NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) DEFAULT NULL,
  detalhe VARCHAR2(100) DEFAULT NULL,
  PRIMARY KEY (idConhecimento)
);

CREATE TABLE rh_experienciainformatica (
  idExperienciaInformatica NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  detalhe VARCHAR2(100) NOT NULL,
  PRIMARY KEY (idExperienciaInformatica)
);

CREATE TABLE rh_habilidade (
  idHabilidade NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  detalhe VARCHAR2(100) NOT NULL,
  PRIMARY KEY (idHabilidade)
);

CREATE TABLE rh_descricaocargo (
  iddescricaocargo NUMBER(10,0) NOT NULL,
  nomecargo VARCHAR2(100) NOT NULL,
  idcbo NUMBER(10,0) DEFAULT NULL,
  atividades VARCHAR2(100) NOT NULL,
  situacao CHAR(1) NOT NULL,
  idsolicitacaoservico NUMBER(10,0) DEFAULT NULL,
  observacoes CLOB,
  idParecerValidacao NUMBER(10,0) DEFAULT NULL,
  PRIMARY KEY (iddescricaocargo)
);

CREATE TABLE rh_atitudeindividual (
  idAtitudeIndividual NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  detalhe VARCHAR2(100) NOT NULL,
  PRIMARY KEY (idAtitudeIndividual)
);

CREATE TABLE rh_cargoatitudeindividual (
  idatitudeindividual NUMBER(10,0) NOT NULL,
  iddescricaocargo NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) NOT NULL,
  PRIMARY KEY (idatitudeindividual,iddescricaocargo),
  CONSTRAINT fk_reference_722 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo),
  CONSTRAINT fk_reference_723 FOREIGN KEY (idatitudeindividual) REFERENCES rh_atitudeindividual (idAtitudeIndividual)
);

CREATE TABLE rh_atitudeorganizacional (
  idatitudeorganizacional NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(200) NOT NULL,
  detalhe CLOB,
  PRIMARY KEY (idatitudeorganizacional)
);

CREATE TABLE rh_entrevistacandidato (
  identrevista NUMBER(10,0) NOT NULL,
  idcurriculo NUMBER(10,0) NOT NULL,
  identrevistador NUMBER(10,0) NOT NULL,
  tipoentrevista VARCHAR2(20) NOT NULL,
  datahora TIMESTAMP NOT NULL,
  caracteristicas CLOB,
  possuioutraatividade CHAR(1) NOT NULL,
  outraatividade CLOB,
  concordaexclusividade CHAR(1) NOT NULL,
  salarioatual FLOAT DEFAULT NULL,
  pretensaosalarial FLOAT DEFAULT NULL,
  datadisponibilidade date DEFAULT NULL,
  competencias CLOB,
  observacoes CLOB,
  resultado CHAR(1) NOT NULL,
  idtriagem NUMBER(10,0) DEFAULT NULL,
  trabalhoemequipe CLOB,
  cargoPretendido VARCHAR2(100) DEFAULT NULL,
  planoCarreira VARCHAR2(100) NOT NULL,
  metodosadicionais CLOB,
  notaavaliacao FLOAT,
  PRIMARY KEY (identrevista)
);

alter table rh_entrevistacandidato add (classificacao CHAR(1));

CREATE TABLE rh_atitudecandidato (
  identrevista NUMBER(10,0) NOT NULL,
  idatitudeorganizacional NUMBER(10,0) NOT NULL,
  avaliacao CHAR(1) DEFAULT NULL,
  PRIMARY KEY (identrevista,idatitudeorganizacional),
  CONSTRAINT fk_reference_entrevista FOREIGN KEY (identrevista) REFERENCES rh_entrevistacandidato (identrevista),
  CONSTRAINT fk_reference_atitudorg FOREIGN KEY (idatitudeorganizacional) REFERENCES rh_atitudeorganizacional (idatitudeorganizacional)
);

CREATE TABLE rh_cargoexperienciaanterior (
  iddescricaocargo NUMBER(10,0) NOT NULL,
  idconhecimento NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) NOT NULL,
  PRIMARY KEY (iddescricaocargo,idconhecimento)
);

CREATE TABLE rh_certificacao (
  idCertificacao NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  detalhe VARCHAR2(100) NOT NULL,
  PRIMARY KEY (idCertificacao)
);

CREATE TABLE rh_certificacaocurriculo (
  idcertificacao NUMBER(10,0) NOT NULL,
  idcurriculo NUMBER(10,0) NOT NULL,
  versao VARCHAR2(100) NOT NULL,
  validade NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  PRIMARY KEY (idcertificacao)
);

CREATE TABLE rh_certificacaorequisicaopesso (
  idcertificacao NUMBER(10,0) NOT NULL,
  versaocertificacao VARCHAR2(100) NOT NULL,
  validadecertificacao VARCHAR2(100) NOT NULL,
  descricaocertificacao VARCHAR2(100) NOT NULL,
  idcurriculo NUMBER(10,0) NOT NULL,
  PRIMARY KEY (idcertificacao)
);

CREATE TABLE rh_curriculo (
  idcurriculo NUMBER(10,0) NOT NULL,
  portadorNecessidadeEspecial CHAR(1) NOT NULL,
  iditemlistatipodeficiencia NUMBER(10,0) DEFAULT NULL,
  qtdefilhos NUMBER(10,0) DEFAULT NULL,
  nome VARCHAR2(100) NOT NULL,
  sexo CHAR(1) NOT NULL,
  cpf VARCHAR2(15) NOT NULL,
  estadoCivil NUMBER(10,0) NOT NULL,
  dataNascimento DATE NOT NULL,
  filhos CHAR(1) NOT NULL,
  cidadeNatal VARCHAR2(100) NOT NULL,
  idNaturalidade NUMBER(10,0) NOT NULL,
  observacoesEntrevista VARCHAR2(100) DEFAULT NULL,
  PRIMARY KEY (idcurriculo)
);

alter table rh_curriculo add (listanegra CHAR(1));

CREATE TABLE rh_curso (
  idCurso NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  detalhe VARCHAR2(100) NOT NULL,
  PRIMARY KEY (idCurso)
);

CREATE TABLE rh_emailcurriculo (
  idemail NUMBER(10,0) NOT NULL,
  idcurriculo NUMBER(10,0) NOT NULL,
  principal CHAR(1) NOT NULL,
  descricaoemail VARCHAR2(100) NOT NULL,
  PRIMARY KEY (idemail)
);

CREATE TABLE rh_enderecocurriculo (
  idendereco NUMBER(10,0) NOT NULL,
  idbairro NUMBER(10,0) NOT NULL,
  idcidade NUMBER(10,0) NOT NULL,
  iduf NUMBER(10,0) NOT NULL,
  idcurriculo NUMBER(10,0) NOT NULL,
  idtipoendereco NUMBER(10,0) NOT NULL,
  cep VARCHAR2(20) NOT NULL,
  complemento VARCHAR2(100) NOT NULL,
  correspondencia CHAR(1) NOT NULL,
  nomecidade VARCHAR2(100) NOT NULL,
  nomebairro VARCHAR2(100) NOT NULL,
  logradouro VARCHAR2(45) DEFAULT NULL,
  PRIMARY KEY (idendereco)
);

CREATE TABLE rh_formacaoacademica (
  idFormacaoAcademica NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  detalhe VARCHAR2(100) NOT NULL,
  PRIMARY KEY (idFormacaoAcademica)
);

CREATE TABLE rh_formacaocurriculo (
  idformacao NUMBER(10,0) NOT NULL,
  idtipoformacao NUMBER(10,0) NOT NULL,
  idsituacao NUMBER(10,0) NOT NULL,
  idcurriculo NUMBER(10,0) NOT NULL,
  instituicao VARCHAR2(100) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  PRIMARY KEY (idformacao)
);

CREATE TABLE rh_jornadadetrabalho (
  idjornada NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  escala CHAR(1) NOT NULL,
  considerarferiados CHAR(1) NOT NULL,
  PRIMARY KEY (idjornada)
);

CREATE TABLE rh_idioma (
  idIdioma NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  detalhe VARCHAR2(100) NOT NULL,
  PRIMARY KEY (idIdioma)
);

CREATE TABLE rh_requisicaoatitudeindividual (
  idatitudeindividual NUMBER(10,0) NOT NULL,
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) NOT NULL,
  PRIMARY KEY (idatitudeindividual,idsolicitacaoservico)
);

CREATE TABLE rh_requisicaocertificacao (
  idcertificacao NUMBER(10,0) NOT NULL,
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) DEFAULT NULL,
  PRIMARY KEY (idcertificacao,idsolicitacaoservico)
);

CREATE TABLE rh_requisicaoconhecimento (
  idconhecimento NUMBER(10,0) NOT NULL,
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) DEFAULT NULL,
  PRIMARY KEY (idconhecimento,idsolicitacaoservico)
);

CREATE TABLE rh_requisicaocurso (
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  idcurso NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) DEFAULT NULL,
  PRIMARY KEY (idsolicitacaoservico,idcurso)
);

CREATE TABLE rh_requisicaoexperienciaanteri (
  idconhecimento NUMBER(10,0) NOT NULL,
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) DEFAULT NULL,
  PRIMARY KEY (idconhecimento,idsolicitacaoservico)
);

CREATE TABLE rh_requisicaoexperienciainform (
  idexperienciainformatica NUMBER(10,0) NOT NULL,
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) DEFAULT NULL,
  PRIMARY KEY (idexperienciainformatica,idsolicitacaoservico)
);

CREATE TABLE rh_requisicaoformacaoacademica (
  idformacaoacademica NUMBER(10,0) NOT NULL,
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) DEFAULT NULL,
  PRIMARY KEY (idformacaoacademica,idsolicitacaoservico)
);

CREATE TABLE rh_requisicaopessoal (
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  idCargo VARCHAR2(100) NOT NULL,
  vagas NUMBER(10,0) NOT NULL,
  motivoContratacao CHAR(1) DEFAULT NULL,
  salario DOUBLE PRECISION NOT NULL,
  idCentroCusto NUMBER(10,0) NOT NULL,
  idProjeto NUMBER(10,0) NOT NULL,
  rejeitada CHAR(1) DEFAULT NULL,
  idParecerValidacao NUMBER(10,0) DEFAULT NULL,
  situacao CHAR(1) DEFAULT NULL,
  confidencial CHAR(1) NOT NULL,
  dataAbertura DATE NOT NULL,
  beneficios VARCHAR2(100) DEFAULT NULL,
  folgas VARCHAR2(100) DEFAULT NULL,
  horario VARCHAR2(100) DEFAULT NULL,
  iduf NUMBER(10,0),
  tipocontratacao CHAR(11),
  idpais NUMBER(10,0),
  PRIMARY KEY (idsolicitacaoservico)
);

alter table rh_requisicaopessoal add (prerequisitoentrevistagestor CHAR(1));

CREATE TABLE rh_telefonecurriculo (
  idtelefone NUMBER(10,0) NOT NULL,
  idtipotelefone NUMBER(10,0) NOT NULL,
  ddd NUMBER(10,0) NOT NULL,
  numerotelefone VARCHAR2(15) NOT NULL,
  idcurriculo NUMBER(10,0) NOT NULL,
  PRIMARY KEY (idtelefone)
);

CREATE TABLE rh_triagemrequisicaopessoal (
  idtriagem NUMBER(10,0) NOT NULL,
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  idcurriculo NUMBER(10,0) NOT NULL,
  iditemtrabalhoentrevistarh NUMBER(10,0) DEFAULT NULL,
  iditemtrabalhoentrevistagestor NUMBER(10,0) DEFAULT NULL,
  PRIMARY KEY (idtriagem),
  CONSTRAINT fk_reference_743 FOREIGN KEY (idsolicitacaoservico) REFERENCES rh_requisicaopessoal (idsolicitacaoservico),
  CONSTRAINT fk_reference_744 FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo (idcurriculo)
);

CREATE TABLE rh_cargocertificacao (
  iddescricaocargo NUMBER(10,0) NOT NULL,
  idcertificacao NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) NOT NULL,
  PRIMARY KEY (iddescricaocargo,idcertificacao),
  CONSTRAINT fk_reference_726 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo),
  CONSTRAINT fk_reference_727 FOREIGN KEY (idcertificacao) REFERENCES rh_certificacao (idCertificacao)
);

CREATE TABLE rh_cargoconhecimento (
  iddescricaocargo NUMBER(10,0) NOT NULL,
  idconhecimento NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) NOT NULL,
  PRIMARY KEY (iddescricaocargo,idconhecimento),
  CONSTRAINT fk_reference_724 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo),
  CONSTRAINT fk_reference_725 FOREIGN KEY (idconhecimento) REFERENCES rh_conhecimento (idConhecimento)
);

CREATE TABLE rh_cargocurso (
  iddescricaocargo NUMBER(10,0) NOT NULL,
  idcurso NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) NOT NULL,
  PRIMARY KEY (iddescricaocargo,idcurso),
  CONSTRAINT fk_reference_728 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo),
  CONSTRAINT fk_reference_729 FOREIGN KEY (idcurso) REFERENCES rh_curso (idCurso)
);

CREATE TABLE rh_cargoexperienciainformatica (
  idexperienciainformatica NUMBER(10,0) NOT NULL,
  iddescricaocargo NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) NOT NULL,
  PRIMARY KEY (idexperienciainformatica,iddescricaocargo),
  CONSTRAINT fk_reference_732 FOREIGN KEY (idexperienciainformatica) REFERENCES rh_experienciainformatica (idExperienciaInformatica),
  CONSTRAINT fk_reference_733 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo)
);

CREATE TABLE rh_cargoformacaoacademica (
  idformacaoacademica NUMBER(10,0) NOT NULL,
  iddescricaocargo NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) NOT NULL,
  PRIMARY KEY (idformacaoacademica,iddescricaocargo),
  CONSTRAINT fk_reference_734 FOREIGN KEY (idformacaoacademica) REFERENCES rh_formacaoacademica (idFormacaoAcademica),
  CONSTRAINT fk_reference_735 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo)
);

CREATE TABLE rh_cargohabilidade (
  idhabilidade NUMBER(10,0) NOT NULL,
  iddescricaocargo NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) NOT NULL,
  PRIMARY KEY (idhabilidade,iddescricaocargo),
  CONSTRAINT fk_reference_736 FOREIGN KEY (idhabilidade) REFERENCES rh_habilidade (idHabilidade),
  CONSTRAINT fk_reference_737 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo)
);

CREATE TABLE rh_cargoidioma (
  iddescricaocargo NUMBER(10,0) NOT NULL,
  ididioma NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) NOT NULL,
  PRIMARY KEY (iddescricaocargo,ididioma),
  CONSTRAINT fk_reference_730 FOREIGN KEY (iddescricaocargo) REFERENCES rh_descricaocargo (iddescricaocargo),
  CONSTRAINT fk_reference_731 FOREIGN KEY (ididioma) REFERENCES rh_idioma (idIdioma)
);

CREATE TABLE rh_requisicaohabilidade (
  idhabilidade NUMBER(10,0) NOT NULL,
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) DEFAULT NULL,
  PRIMARY KEY (idhabilidade,idsolicitacaoservico),
  CONSTRAINT fk_reference_738 FOREIGN KEY (idhabilidade) REFERENCES rh_habilidade (idHabilidade),
  CONSTRAINT fk_reference_739 FOREIGN KEY (idsolicitacaoservico) REFERENCES rh_requisicaopessoal (idsolicitacaoservico)
);

CREATE TABLE rh_requisicaoidioma (
  ididioma NUMBER(10,0) NOT NULL,
  idsolicitacaoservico NUMBER(10,0) NOT NULL,
  obrigatorio CHAR(1) DEFAULT NULL,
  PRIMARY KEY (ididioma,idsolicitacaoservico),
  CONSTRAINT fk_reference_740 FOREIGN KEY (ididioma) REFERENCES rh_idioma (idIdioma),
  CONSTRAINT fk_reference_741 FOREIGN KEY (idsolicitacaoservico) REFERENCES rh_requisicaopessoal (idsolicitacaoservico)
);

CREATE TABLE rh_jornadaempregado (
  idjornada NUMBER(10,0) NOT NULL,
  descricao VARCHAR2(100) NOT NULL,
  escala CHAR(1) NOT NULL,
  considerarferiados CHAR(1) NOT NULL,
  PRIMARY KEY (idjornada)
);

-- ####################### inicio tipofluxo RH ###################### --

INSERT INTO bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo) VALUES($id_tipofluxo_pessoal, 'RequisicaoPessoal', 'Requisição de Pessoal', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoRequisicaoPessoal');

-- ####################### inicio fluxo ###################### --

INSERT INTO bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim) VALUES($id_fluxo_pessoal_152, '21.0', $id_tipofluxo_pessoal, 'solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1', '', to_date('2013-10-03', 'yyyy-mm-dd'), NULL);

-- ####################### fim fluxo ###################### --

-- ############################## inicio elemento fluxo ################################ --

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1588, $id_fluxo_pessoal_152, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 19, 28, 32, 32, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1589, $id_fluxo_pessoal_152, 'Tarefa', '', 'Analisar requisição de pessoal', 'Analisar requisição de pessoal', 'U', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '', '#{solicitacaoServico.grupoAtual}', '', '', '', '', '', '', 216, 11, 65, 140, '', 'AnaliseRequisicaoPessoal', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1590, $id_fluxo_pessoal_152, 'Tarefa', '', 'Alterar requisição de pessoal', 'Alterar requisição de pessoal', 'U', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '#{solicitacaoServico.grupoNivel1}', '', '', '', '', '', '', 337, 203, 65, 140, '', 'RequisicaoPessoal', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1591, $id_fluxo_pessoal_152, 'Tarefa', '', 'Triagem de currículos', 'Triagem de currículos', 'U', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', ' #{solicitacaoServico.grupoAtual}', '', '', '', '', '', '', 542, 11, 65, 140, '', 'TriagemRequisicaoPessoal', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1592, $id_fluxo_pessoal_152, 'Tarefa', '', 'Entrevista com RH', 'Entrevista com RH', 'U', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '', ' #{solicitacaoServico.grupoAtual}', '', '#{execucaoFluxo}.associaItemTrabalhoEntrevistaRH(#{itemTrabalho}); ', '', '', '', '', 772, 111, 65, 140, '', 'EntrevistaRequisicaoPessoal', NULL, '', 'N', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1593, $id_fluxo_pessoal_152, 'Tarefa', '', 'Entrevista com Gestor', 'Entrevista com Gestor', 'U', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '', NULL, 'script:#{execucaoFluxo}.recuperaLoginGestores();', '#{execucaoFluxo}.associaItemTrabalhoEntrevistaGestor(#{itemTrabalho}); ', '', '', '', '', 781, 253, 65, 140, '', 'EntrevistaRequisicaoPessoal', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1594, $id_fluxo_pessoal_152, 'Tarefa', '', 'Entrevista com RH', 'Entrevista com RH', 'U', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '', '#{solicitacaoServico.grupoAtual}', '', '#{execucaoFluxo}.associaItemTrabalhoEntrevistaRH(#{itemTrabalho});', '', '', '', '', 774, 471, 65, 140, '', 'EntrevistaRequisicaoPessoal', NULL, '', 'N', '', 'S', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1595, $id_fluxo_pessoal_152, 'Tarefa', '', 'Entrevista com Gestor', 'Entrevista com Gestor', 'U', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '', NULL, 'script:#{execucaoFluxo}.recuperaLoginGestores();', '#{execucaoFluxo}.associaItemTrabalhoEntrevistaGestor(#{itemTrabalho});', '', '', '', '', 948, 471, 65, 140, '', 'EntrevistaRequisicaoPessoal', NULL, '', 'N', '', 'S', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1596, $id_fluxo_pessoal_152, 'Tarefa', '', 'Classificacao', 'Classificacao', 'U', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '', ' #{solicitacaoServico.grupoAtual}', '', '#{execucaoFluxo}.classificaCandidato();', '', '', '', '', 1165, 84, 65, 140, '', 'ClassificacaoRequisicaoPessoal', NULL, '', 'N', '', 'S', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1597, $id_fluxo_pessoal_152, 'Email', NULL, NULL, NULL, NULL, NULL, NULL, '#{solicitacaoServico.grupoAtual}', '', NULL, NULL, NULL, NULL, NULL, 86, 33, 22, 31, 'rhSolPesEncaminhada', NULL, NULL, NULL, NULL, '', NULL, NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1598, $id_fluxo_pessoal_152, 'Email', NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, NULL, NULL, 149, 33, 22, 31, 'rhSolPesAbertura', NULL, NULL, NULL, NULL, '#{solicitacaoServico.emailcontato}', NULL, NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1599, $id_fluxo_pessoal_152, 'Email', NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, NULL, NULL, 391, 125, 22, 31, 'rhSolPesRejeitada', NULL, NULL, NULL, NULL, '#{solicitacaoServico.emailcontato}', NULL, NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1600, $id_fluxo_pessoal_152, 'Email', NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, NULL, NULL, 468, 33, 22, 31, '', NULL, NULL, NULL, NULL, '', NULL, NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1601, $id_fluxo_pessoal_152, 'Email', NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, NULL, NULL, 1456, 226, 22, 31, 'rhSolPesPreenchida', NULL, NULL, NULL, NULL, '#{solicitacaoServico.emailcontato}', NULL, NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1602, $id_fluxo_pessoal_152, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 385, 23, 42, 42, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1603, $id_fluxo_pessoal_152, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 655, 192, 42, 42, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1604, $id_fluxo_pessoal_152, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 997, 181, 42, 42, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1605, $id_fluxo_pessoal_152, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 656, 369, 42, 42, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1606, $id_fluxo_pessoal_152, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 1448, 97, 42, 42, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1607, $id_fluxo_pessoal_152, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 587, 519, 42, 42, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1608, $id_fluxo_pessoal_152, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 998, 97, 42, 42, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo  (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) VALUES($id_elementofluxo_pessoal_1609, $id_fluxo_pessoal_152, 'Finalizacao', '', '', '', '', '', '', '', '', '', '', '', '', '', 1456, 296, 32, 32, '', '', NULL, '', '', '', '', NULL);

-- ################################# fim elemento fluxo ######################## --

-- ################################# inicio sequencia fluxo ######################## --

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1588, $id_elementofluxo_pessoal_1597, $id_fluxo_pessoal_152, NULL, NULL, '', 1, 3, 68.5, 44, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1589, $id_elementofluxo_pessoal_1602, $id_fluxo_pessoal_152, NULL, NULL, '', 1, 3, 370.5, 43.75, 'N', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1590, $id_elementofluxo_pessoal_1589, $id_fluxo_pessoal_152, NULL, NULL, '', 3, 2, 284, 235, 'S', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1591, $id_elementofluxo_pessoal_1607, $id_fluxo_pessoal_152, NULL, NULL, '', 2, 0, 610, 319, 'S', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1592, $id_elementofluxo_pessoal_1604, $id_fluxo_pessoal_152, NULL, NULL, '', 1, 3, 954.5, 172.75, 'N', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1593, $id_elementofluxo_pessoal_1604, $id_fluxo_pessoal_152, NULL, NULL, '', 1, 3, 959, 243.75, 'N', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1594, $id_elementofluxo_pessoal_1595, $id_fluxo_pessoal_152, NULL, NULL, '', 1, 3, 931, 503.5, 'N', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1595, $id_elementofluxo_pessoal_1604, $id_fluxo_pessoal_152, NULL, NULL, '', 0, 2, 1018, 347, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1596, $id_elementofluxo_pessoal_1606, $id_fluxo_pessoal_152, NULL, NULL, '', 1, 3, 1376.5, 117.25, 'N', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1597, $id_elementofluxo_pessoal_1598, $id_fluxo_pessoal_152, NULL, NULL, '', 1, 3, 133, 44, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1598, $id_elementofluxo_pessoal_1589, $id_fluxo_pessoal_152, NULL, NULL, '', 1, 3, 198, 43.75, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1599, $id_elementofluxo_pessoal_1590, $id_fluxo_pessoal_152, NULL, NULL, '', 2, 0, 406.75, 175, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1600, $id_elementofluxo_pessoal_1591, $id_fluxo_pessoal_152, NULL, NULL, '', 1, 3, 520.5, 43.75, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1601, $id_elementofluxo_pessoal_1609, $id_fluxo_pessoal_152, NULL, NULL, '', 2, 0, 1471.75, 272, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1602, $id_elementofluxo_pessoal_1599, $id_fluxo_pessoal_152, NULL, NULL, '#{execucaoFluxo}.requisicaoRejeitada();', 2, 0, 406.25, 95, 'N', 'rejeitada');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1602, $id_elementofluxo_pessoal_1600, $id_fluxo_pessoal_152, NULL, NULL, '!#{execucaoFluxo}.requisicaoRejeitada();', 1, 3, 456, 44, 'S', 'não rejeitada');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1603, $id_elementofluxo_pessoal_1592, $id_fluxo_pessoal_152, NULL, NULL, '#{execucaoFluxo}.existeEntrevistaPendenteRH();', 1, 3, 707, 143, 'S', 'existe entrevista RH');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1603, $id_elementofluxo_pessoal_1593, $id_fluxo_pessoal_152, NULL, NULL, '#{execucaoFluxo}.existeEntrevistaPendenteGestor();', 1, 3, 711, 287, 'S', 'existe entrevista Gestor');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1604, $id_elementofluxo_pessoal_1608, $id_fluxo_pessoal_152, NULL, NULL, '', 0, 2, 1018.5, 160, 'N', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1605, $id_elementofluxo_pessoal_1594, $id_fluxo_pessoal_152, NULL, NULL, '!#{execucaoFluxo}.preRequisitoEntrevistaGestor();', 2, 3, 678, 502, 'S', 'entrevistas sequenciasis');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1605, $id_elementofluxo_pessoal_1603, $id_fluxo_pessoal_152, NULL, NULL, '#{execucaoFluxo}.preRequisitoEntrevistaGestor();', 0, 2, 676.5, 301.5, 'N', 'entrevistas simultaneas');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1606, $id_elementofluxo_pessoal_1591, $id_fluxo_pessoal_152, NULL, NULL, '!#{execucaoFluxo}.vagasPreenchidas();', 0, 1, 1468, 47, 'S', 'vagas não preenchidas');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1606, $id_elementofluxo_pessoal_1601, $id_fluxo_pessoal_152, NULL, NULL, '#{execucaoFluxo}.vagasPreenchidas();', 2, 0, 1470.25, 182.5, 'N', 'vagas preenchidas');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1607, $id_elementofluxo_pessoal_1605, $id_fluxo_pessoal_152, NULL, NULL, '!#{execucaoFluxo}.vagasPreenchidas();', 1, 3, 642.5, 465, 'N', 'vagas não preenchidas e solicitacao em andamento');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1607, $id_elementofluxo_pessoal_1609, $id_fluxo_pessoal_152, NULL, NULL, '#{execucaoFluxo}.vagasPreenchidas();', 2, 2, 1471, 574, 'S', 'vagas não preenchidas e solicitacao cancelada');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1608, $id_elementofluxo_pessoal_1591, $id_fluxo_pessoal_152, NULL, NULL, '!#{execucaoFluxo}.entrevistaAprovadaENaoClassificada();', 0, 1, 1016, 84, 'S', 'não existe entrevista');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_pessoal_1608, $id_elementofluxo_pessoal_1596, $id_fluxo_pessoal_152, NULL, NULL, '#{execucaoFluxo}.entrevistaAprovadaENaoClassificada();', 1, 3, 1054, 118, 'S', 'existe entrevista aprovada e não classificada');

-- ################################# fim sequencia fluxo ######################## --

-- ####################### inicio tipofluxo Cargo ###################### --

INSERT INTO bpm_tipofluxo (idtipofluxo, nomefluxo, descricao, nomeclassefluxo)
VALUES($id_tipofluxo_cargo, 'SolicitacaoCargo', 'Solicitação de Cargo', 'br.com.centralit.citcorpore.bpm.negocio.ExecucaoSolicitacaoCargo');

-- ####################### inicio fluxo Pessoal ###################### --

-- ####################### inicio fluxo cargo ###################### --

INSERT INTO bpm_fluxo (idfluxo, versao, idtipofluxo, variaveis, conteudoxml, datainicio, datafim)
VALUES($id_fluxo_cargo_153, '4.0', $id_tipofluxo_cargo, 'solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1', '', to_date('2013-10-07', 'yyyy-mm-dd'), NULL);

-- ####################### fim fluxo cargo ###################### --

-- ############################## inicio elemento fluxo cargo ################################ --

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao)
VALUES($id_elementofluxo_cargo_1610, $id_fluxo_cargo_153, 'Inicio', '', '', '', '', '', '', '', '', '', '', '', '', '', 29, 60, 32, 32, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) 
VALUES($id_elementofluxo_cargo_1611, $id_fluxo_cargo_153, 'Tarefa', '', 'Analisar solicitação de cargo', 'Analisar solicitação de cargo', 'U', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '', '#{solicitacaoServico.grupoAtual}', '', '', '', '', '', '', 292, 43, 65, 140, '', 'AnaliseSolicitacaoCargo', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) 
VALUES($id_elementofluxo_cargo_1612, $id_fluxo_cargo_153, 'Tarefa', '', 'Alterar solicitação de cargo', 'Alterar solicitação de cargo', 'U', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load', '#{solicitacaoServico.grupoNivel1}', '', '', '', '', '', '', 594, 241, 65, 140, '', 'SolicitacaoCargo', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) 
VALUES($id_elementofluxo_cargo_1613, $id_fluxo_cargo_153, 'Script', '', 'encerra', '', '', '', '', '', '', '', '', '#{execucaoFluxo}.encerra();', '', '', 855, 44, 65, 140, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) 
VALUES($id_elementofluxo_cargo_1614, $id_fluxo_cargo_153, 'Email', NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, NULL, NULL, 116, 65, 22, 31, 'rhSolCargoAbertura', NULL, NULL, NULL, NULL, '#{solicitacaoServico.emailcontato}', NULL, NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) 
VALUES($id_elementofluxo_cargo_1615, $id_fluxo_cargo_153, 'Email', NULL, NULL, NULL, NULL, NULL, NULL, '#{solicitacaoServico.grupoAtual}', '', NULL, NULL, NULL, NULL, NULL, 207, 64, 22, 31, 'rhSolCarEncaminhada', NULL, NULL, NULL, NULL, '', NULL, NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) 
VALUES($id_elementofluxo_cargo_1616, $id_fluxo_cargo_153, 'Email', NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, NULL, NULL, 648, 163, 22, 31, 'rhSolCargogoRejeita', NULL, NULL, NULL, NULL, '#{solicitacaoServico.emailcontato}', NULL, NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) 
VALUES($id_elementofluxo_cargo_1617, $id_fluxo_cargo_153, 'Email', NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, NULL, NULL, 520, 66, 22, 31, 'rhSolCargoAndamento', NULL, NULL, NULL, NULL, '#{solicitacaoServico.emailcontato}', NULL, NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) 
VALUES($id_elementofluxo_cargo_1618, $id_fluxo_cargo_153, 'Email', NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, NULL, NULL, 755, 67, 22, 31, 'rhSolCargoAprovada', NULL, NULL, NULL, NULL, '#{solicitacaoServico.emailcontato}', NULL, NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) 
VALUES($id_elementofluxo_cargo_1619, $id_fluxo_cargo_153, 'Porta', '', '', '', '', '', '', '', '', '', '', '', '', '', 643, 57, 42, 42, '', '', NULL, '', '', '', '', NULL);

INSERT INTO bpm_elementofluxo (idelemento, idfluxo, tipoelemento, subtipo, nome, documentacao, tipointeracao, url, visao, grupos, usuarios, acaoentrada, acaosaida, script, textoemail, nomefluxoencadeado, posx, posy, altura, largura, modeloemail, template, intervalo, condicaodisparo, multiplasinstancias, destinatariosemail, contabilizasla, percexecucao) 
VALUES($id_elementofluxo_cargo_1620, $id_fluxo_cargo_153, 'Finalizacao', '', '', '', '', '', '', '', '', '', '', '', '', '', 1081, 60, 32, 32, '', '', NULL, '', '', '', '', NULL);

-- ################################# fim elemento fluxo cargo ######################## --

-- ################################# inicio sequencia fluxo cargo ######################## --

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1610, $id_elementofluxo_cargo_1614, $id_fluxo_cargo_153, NULL, NULL, '', 1, 3, 88.5, 76, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1611, $id_elementofluxo_cargo_1617, $id_fluxo_cargo_153, NULL, NULL, '', 1, 3, 476, 76.25, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1612, $id_elementofluxo_cargo_1611, $id_fluxo_cargo_153, NULL, NULL, '', 3, 2, 361, 272, 'S', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1613, $id_elementofluxo_cargo_1620, $id_fluxo_cargo_153, NULL, NULL, '', 1, 3, 1038, 76.25, 'N', '');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1614, $id_elementofluxo_cargo_1615, $id_fluxo_cargo_153, NULL, NULL, '', 1, 3, 177, 75.5, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1615, $id_elementofluxo_cargo_1611, $id_fluxo_cargo_153, NULL, NULL, '', 1, 3, 265, 75.25, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1616, $id_elementofluxo_cargo_1612, $id_fluxo_cargo_153, NULL, NULL, '', 2, 0, 663.75, 213, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1617, $id_elementofluxo_cargo_1619, $id_fluxo_cargo_153, NULL, NULL, '', 1, 3, 597, 77.5, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1618, $id_elementofluxo_cargo_1613, $id_fluxo_cargo_153, NULL, NULL, '', 1, 3, 820.5, 77.25, 'N', NULL);

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1619, $id_elementofluxo_cargo_1616, $id_fluxo_cargo_153, NULL, NULL, '#{execucaoFluxo}.solicitacaoRejeitada();', 2, 0, 663.75, 131, 'N', 'rejeitada');

INSERT INTO bpm_sequenciafluxo (idelementoorigem, idelementodestino, idfluxo, nomeclasseorigem, nomeclassedestino, condicao, idconexaoorigem, idconexaodestino, bordax, borday, posicaoalterada, nome)
VALUES($id_elementofluxo_cargo_1619, $id_elementofluxo_cargo_1618, $id_fluxo_cargo_153, NULL, NULL, '', '1', '3', '720', '78', 'N', NULL);

-- ################################# fim sequencia fluxo ######################## --

-- ######################################## template solicitacao servico ###########################################

INSERT INTO templatesolicitacaoservico (idtemplate, identificacao, nometemplate, nomeclassedto, nomeclasseaction, nomeclasseservico, urlrecuperacao, scriptaposrecuperacao, habilitadirecionamento, habilitasituacao, habilitasolucao, alturadiv, habilitaurgenciaimpacto, habilitanotificacaoemail, habilitaproblema, habilitamudanca, habilitaitemconfiguracao, habilitasolicitacaorelacionada, habilitagravarecontinuar, idquestionario,aprovacao) 
VALUES($id_template_sol_ser_pessoal, 'RequisicaoPessoal', 'Requisição Pessoal', 'br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO', 'br.com.centralit.citcorpore.ajaxForms.RequisicaoPessoal', 'br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalServiceEjb', '/pages/requisicaoPessoal/requisicaoPessoal.load', '', 'N', 'S', 'N', 1000, 'S', 'N', 'N', 'N', 'N', 'N', 'S', NULL, 'N');

INSERT INTO templatesolicitacaoservico (idtemplate, identificacao, nometemplate, nomeclassedto, nomeclasseaction, nomeclasseservico, urlrecuperacao, scriptaposrecuperacao, habilitadirecionamento, habilitasituacao, habilitasolucao, alturadiv, habilitaurgenciaimpacto, habilitanotificacaoemail, habilitaproblema, habilitamudanca, habilitaitemconfiguracao, habilitasolicitacaorelacionada, habilitagravarecontinuar, idquestionario,aprovacao) 
VALUES($id_template_sol_ser_analise_pessoal, 'AnaliseRequisicaoPessoal', 'Análise Requisição Pessoal', 'br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO', 'br.com.centralit.citcorpore.ajaxForms.AnaliseRequisicaoPessoal', 'br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalServiceEjb', '/pages/analiseRequisicaoPessoal/analiseRequisicaoPessoal.load', '', 'N', 'S', 'N', 1000, 'S', 'N', 'N', 'N', 'N', 'N', 'S', NULL, 'N');

INSERT INTO templatesolicitacaoservico (idtemplate, identificacao, nometemplate, nomeclassedto, nomeclasseaction, nomeclasseservico, urlrecuperacao, scriptaposrecuperacao, habilitadirecionamento, habilitasituacao, habilitasolucao, alturadiv, habilitaurgenciaimpacto, habilitanotificacaoemail, habilitaproblema, habilitamudanca, habilitaitemconfiguracao, habilitasolicitacaorelacionada, habilitagravarecontinuar, idquestionario,aprovacao) 
VALUES($id_template_sol_ser_triagem, 'TriagemRequisicaoPessoal', 'Triagem Requisição Pessoal', 'br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO', 'br.com.centralit.citcorpore.ajaxForms.TriagemRequisicaoPessoal', 'br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalServiceEjb', '/pages/triagemRequisicaoPessoal/triagemRequisicaoPessoal.load', '', 'N', 'N', 'N', 800, 'S', 'N', 'N', 'N', 'N', 'N', 'S', NULL, 'N');

INSERT INTO templatesolicitacaoservico (idtemplate, identificacao, nometemplate, nomeclassedto, nomeclasseaction, nomeclasseservico, urlrecuperacao, scriptaposrecuperacao, habilitadirecionamento, habilitasituacao, habilitasolucao, alturadiv, habilitaurgenciaimpacto, habilitanotificacaoemail, habilitaproblema, habilitamudanca, habilitaitemconfiguracao, habilitasolicitacaorelacionada, habilitagravarecontinuar, idquestionario,aprovacao) 
VALUES($id_template_sol_ser_entrevista, 'EntrevistaRequisicaoPessoal', 'Entrevista Requisicao Pessoal', 'br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO', 'br.com.centralit.citcorpore.ajaxForms.EntrevistaRequisicaoPessoal', 'br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalServiceEjb', '/pages/entrevistaRequisicaoPessoal/entrevistaRequisicaoPessoal.load', '', 'N', 'N', 'N', 800, 'S', 'N', 'N', 'N', 'N', 'N', 'S', NULL, 'N');

INSERT INTO templatesolicitacaoservico (idtemplate, identificacao, nometemplate, nomeclassedto, nomeclasseaction, nomeclasseservico, urlrecuperacao, scriptaposrecuperacao, habilitadirecionamento, habilitasituacao, habilitasolucao, alturadiv, habilitaurgenciaimpacto, habilitanotificacaoemail, habilitaproblema, habilitamudanca, habilitaitemconfiguracao, habilitasolicitacaorelacionada, habilitagravarecontinuar, idquestionario,aprovacao) 
VALUES($id_template_sol_ser_cargo, 'SolicitacaoCargo', 'Solicitação de Cargo', 'br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO', 'br.com.centralit.citcorpore.ajaxForms.SolicitacaoCargo', 'br.com.centralit.citcorpore.rh.negocio.DescricaoCargoServiceEjb', '/pages/solicitacaoCargo/solicitacaoCargo.load', '', 'N', 'N', 'N', 800, 'S', 'N', 'N', 'N', 'N', 'N', 'S', NULL, 'N');

INSERT INTO templatesolicitacaoservico (idtemplate, identificacao, nometemplate, nomeclassedto, nomeclasseaction, nomeclasseservico, urlrecuperacao, scriptaposrecuperacao, habilitadirecionamento, habilitasituacao, habilitasolucao, alturadiv, habilitaurgenciaimpacto, habilitanotificacaoemail, habilitaproblema, habilitamudanca, habilitaitemconfiguracao, habilitasolicitacaorelacionada, habilitagravarecontinuar, idquestionario,aprovacao) 
VALUES($id_template_sol_ser_analise_cargo, 'AnaliseSolicitacaoCargo', 'Análise Solicitação de Cargo', 'br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO', 'br.com.centralit.citcorpore.ajaxForms.AnaliseSolicitacaoCargo', 'br.com.centralit.citcorpore.rh.negocio.DescricaoCargoServiceEjb', '/pages/analiseSolicitacaoCargo/analiseSolicitacaoCargo.load', NULL, 'N', 'N', 'N', 800, 'S', 'N', 'N', 'N', 'N', 'N', 'S', NULL, 'N');

INSERT INTO templatesolicitacaoservico (idtemplate, identificacao, nometemplate, nomeclassedto, nomeclasseaction, nomeclasseservico, urlrecuperacao, scriptaposrecuperacao, habilitadirecionamento, habilitasituacao, habilitasolucao, alturadiv, habilitaurgenciaimpacto, habilitanotificacaoemail, habilitaproblema, habilitamudanca, habilitaitemconfiguracao, habilitasolicitacaorelacionada, habilitagravarecontinuar, idquestionario,aprovacao) 
VALUES($id_template_sol_ser_classificacao, 'ClassificacaoRequisicaoPessoal', 'ClassificacaoRequisicaoPessoal', 'br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO', 'br.com.centralit.citcorpore.ajaxForms.ClassificacaoRequisicaoPessoal', 'br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalServiceEjb', '/pages/classificacaoRequisicaoPessoal/classificacaoRequisicaoPessoal.load', '', 'N', 'N', 'N', 800, 'S', 'N', 'N', 'N', 'S', 'N', 'S', NULL, 'N');

-- ###################################### servicos ####################################

INSERT INTO servico (idservico, idcategoriaservico, idsituacaoservico, idtiposervico, idimportancianegocio, idempresa, idtipoeventoservico, idtipodemandaservico, idlocalexecucaoservico, nomeservico, detalheservico, objetivo, passosservico, datainicio, linkprocesso, descricaoprocesso, tipodescprocess, dispportal, quadroorientportal, deleted, detalhesServico, siglaAbrev, idbaseconhecimento, idtemplatesolicitacao, idtemplateacompanhamento)
VALUES($id_sol_serv_requisicao_pessoal, 2131, 1, 1, NULL, 1, 1, 1, NULL, 'REQUISIÇÃO DE PESSOAL', 'REQUISIÇÃO DE PESSOAL', NULL, NULL, to_date('2013-01-01', 'yyyy-mm-dd'), NULL, NULL, NULL, 'N', NULL, NULL, NULL, NULL, NULL, $id_template_sol_ser_pessoal, $id_template_sol_ser_pessoal);

INSERT INTO servico (idservico, idcategoriaservico, idsituacaoservico, idtiposervico, idimportancianegocio, idempresa, idtipoeventoservico, idtipodemandaservico, idlocalexecucaoservico, nomeservico, detalheservico, objetivo, passosservico, datainicio, linkprocesso, descricaoprocesso, tipodescprocess, dispportal, quadroorientportal, deleted, detalhesServico, siglaAbrev, idbaseconhecimento, idtemplatesolicitacao, idtemplateacompanhamento)
VALUES($id_sol_serv_requisicao_cargo, 2131, 1, 1, NULL, 1, 1, 1, NULL, 'SOLICITAÇÃO DE CARGO', 'SOLICITAÇÃO DE CARGO', NULL, NULL, to_date('2013-01-01', 'yyyy-mm-dd'), NULL, NULL, NULL, 'N', NULL, NULL, NULL, NULL, NULL, $id_template_sol_ser_cargo, $id_template_sol_ser_cargo);

INSERT INTO servicocontrato (idservicocontrato, idservico, idcontrato, idcondicaooperacao, datainicio, datafim, observacao, custo, restricoespressup, objetivo, permiteslanocadinc, linkprocesso, descricaoprocesso, tipodescprocess, deleted, arearequisitante, idgruponivel1, idModeloEmailCriacao, idModeloEmailFinalizacao, idModeloEmailAcoes, idgrupoexecutor, idcalendario, permSLATempoACombinar, permMudancaSLA, permMudancaCalendario, idgrupoaprovador)
VALUES($id_servicocontrato_sol_cargo, $id_sol_serv_requisicao_cargo, 1, 10, to_date('2012-08-28', 'yyyy-mm-dd'), NULL, '', NULL, '', '', NULL, '', '', NULL, NULL, '', NULL, 1, 2, -999, -999, 1, NULL, NULL, NULL, NULL);

INSERT INTO servicocontrato (idservicocontrato, idservico, idcontrato, idcondicaooperacao, datainicio, datafim, observacao, custo, restricoespressup, objetivo, permiteslanocadinc, linkprocesso, descricaoprocesso, tipodescprocess, deleted, arearequisitante, idgruponivel1, idModeloEmailCriacao, idModeloEmailFinalizacao, idModeloEmailAcoes, idgrupoexecutor, idcalendario, permSLATempoACombinar, permMudancaSLA, permMudancaCalendario, idgrupoaprovador)
VALUES($id_servicocontrato_sol_pessoal, $id_sol_serv_requisicao_pessoal, 1, 10, to_date('2012-08-28', 'yyyy-mm-dd'), NULL, '', NULL, '', '', NULL, '', '', NULL, NULL, '', NULL, 1, 2, -999, -999, 1, NULL, NULL, NULL, NULL);

INSERT INTO fluxoservico (idfluxoservico, idservicocontrato, idtipofluxo, idfase, principal, deleted)
VALUES($id_fluxoservico_sol_pessoal, $id_servicocontrato_sol_pessoal, $id_tipofluxo_pessoal, 2, 'S', NULL);

INSERT INTO fluxoservico (idfluxoservico, idservicocontrato, idtipofluxo, idfase, principal, deleted)
VALUES($id_fluxoservico_sol_cargo, $id_servicocontrato_sol_cargo, $id_tipofluxo_cargo, 2, 'S', NULL);

-- #####################################

ALTER TABLE rh_requisicaopessoal ADD (adimitido NUMBER(1) DEFAULT 0);
ALTER TABLE rh_requisicaopessoal ADD (qtdcandidatosaprovados NUMBER(10,0));

-- FIM - MURILO PACHECO 12/11/2013

-- INICIO - MURILO GABRIEL RODRIGUES - 18/11/2013

delete from menu where link like '/monitoramentoServicos/monitoramentoServicos.load';

-- FIM - MURILO GABRIEL RODRIGUES - 18/11/2013
