-- INICIO - EULER JOSÉ RAMOS - 03/01/2013

INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modeloemail_85,'BI Citsmart - Notificação Erro Importação Auto Ag.Exceção','<span style="font-size: larger;"><strong>Importa&ccedil;&atilde;o autom&aacute;tica BI Citsmart n&atilde;o executada!</strong></span><br /><span style="font-size: larger;"><span style="font-family: Times New Roman;"><br /><samp>Ocorreu problema na execu&ccedil;&atilde;o do Agendamento de Exce&ccedil;&atilde;o:</samp></span></span><br /><span style="font-size: larger;"><span style="font-family: Times New Roman;">ID Conex&atilde;o: <strong>${IDCONEXAO}</strong><br />Nome Conex&atilde;o: <strong>${NOMECONEXAO}</strong><br />Link: <strong>${LINKCONEXAO}</strong><br />Processamento Batch:<strong> ${IDPROCESSAMENTO} - ${DESCRPROCESSAMENTO}</strong></span></span><br />','A','NotErrBiExc');
UPDATE parametrocorpore SET valor = '$id_modeloemail_85' WHERE idparametrocorpore = 207;

INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modeloemail_86,'BI Citsmart - Notificação Erro Importação Auto Ag.Específico','<span style="font-size: larger;"><strong>Importa&ccedil;&atilde;o autom&aacute;tica BI Citsmart n&atilde;o executada!</strong></span><br /><span style="font-size: larger;"><span style="font-family: Times New Roman;"><br /><samp>Ocorreu problema na execu&ccedil;&atilde;o do Agendamento Espec&iacute;fico:</samp></span></span><br /><span style="font-size: larger;"><span style="font-family: Times New Roman;">ID Conex&atilde;o: <strong>${IDCONEXAO}</strong><br />Nome Conex&atilde;o: <strong>${NOMECONEXAO}</strong><br />Link: <strong>${LINKCONEXAO}</strong><br />Processamento Batch:<strong> ${IDPROCESSAMENTO} - ${DESCRPROCESSAMENTO}</strong></span></span><br />','A','NotErrBiEsp');
UPDATE parametrocorpore set valor = '$id_modeloemail_86' WHERE idparametrocorpore = 208;

INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modeloemail_87,'BI Citsmart - Notificação Erro Importação Auto Ag.Padrão','<span style="font-size: larger;"><strong>Importa&ccedil;&atilde;o autom&aacute;tica BI Citsmart n&atilde;o executada!</strong></span><br /><span style="font-size: larger;"><span style="font-family: Times New Roman;"><br /><samp>Ocorreu problema na execu&ccedil;&atilde;o do Agendamento Padr&atilde;o:</samp></span></span><br /><span style="font-size: larger;"><span style="font-family: Times New Roman;">ID Conex&atilde;o: <strong>${IDCONEXAO}</strong><br />Nome Conex&atilde;o: <strong>${NOMECONEXAO}</strong><br />Link: <strong>${LINKCONEXAO}</strong><br />Processamento Batch:<strong> ${IDPROCESSAMENTO} - ${DESCRPROCESSAMENTO}</strong></span></span><br />','A','NotErrBiPadr');
UPDATE parametrocorpore SET valor = '$id_modeloemail_87' WHERE idparametrocorpore = 209;

INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modeloemail_88,'BI Citsmart - Notificação Erro Importação Auto','<span style="font-size: larger;"><strong>Importa&ccedil;&atilde;o autom&aacute;tica BI Citsmart n&atilde;o executada!</strong></span><p><span style="font-family: Times New Roman;"><samp><span style="font-size: larger;"><br />Ocorreu problema na execu&ccedil;&atilde;o antes da identifica&ccedil;&atilde;o do Agendamento e do processamento batch.</span></samp></span></p><br />','A','NotErrBi');
UPDATE parametrocorpore SET valor = '$id_modeloemail_88' WHERE idparametrocorpore = 211;

INSERT INTO modelosemails (idmodeloemail,titulo,texto,situacao,identificador) VALUES ($id_modeloemail_89,'BI Citsmart - Importação Auto Não Executada!','<span style="font-size: larger;"><strong>Importa&ccedil;&atilde;o autom&aacute;tica BI Citsmart n&atilde;o executada!</strong></span><p><span style="font-family: Times New Roman;"><samp><span style="font-size: larger;"><br />O par&acirc;metro: ${PARAMETRO} est&aacute; inativado, ou seja, diferente de "S"</span></samp></span></p><br />','A','NotBiInativo');
UPDATE parametrocorpore SET valor = '$id_modeloemail_89' WHERE idparametrocorpore = 210;

-- FIM - EULER JOSÉ RAMOS - 03/01/2013

-- INICIO - RODRIGO PECCI ACORSE - 09/11/2013

delete from menu where link like '/conexaoBI/conexaoBI.load';
delete from menu where link like '/deParaCatalogoServicosBI/deParaCatalogoServicosBI.load';
delete from menu where link like '/contratosBI/contratosBI.load';
delete from menu where link like '/servicoCorporeBI/servicoCorporeBI.load';

-- FIM - RODRIGO PECCI ACORSE - 09/11/2013

-- INICIO - FLAVIO JUNIOR NEVES SANTANA SILVA - 22/01/2014

ALTER TABLE historicoic ALTER COLUMN historicoversao TYPE DECIMAL(5,2);

-- FIM - FLAVIO JUNIOR NEVES SANTANA SILVA - 22/01/2014

-- INICIO - MURILO GABRIEL RODRIGUES - 29/01/2014

delete from menu where nome like '$menu.nome.recursosHumanos';
delete from menu where nome like '$menu.nome.cadastrosRH';
delete from menu where link like '/atitudeIndividual/atitudeIndividual.load';
delete from menu where link like '/templateCurriculo/templateCurriculo.load';
delete from menu where link like '/certificacao/certificacao.load';
delete from menu where link like '/conhecimento/conhecimento.load';
delete from menu where link like '/curso/curso.load';
delete from menu where link like '/experienciaInformatica/experienciaInformatica.load';
delete from menu where link like '/formacaoAcademica/formacaoAcademica.load';
delete from menu where link like '/habilidade/habilidade.load';
delete from menu where link like '/idioma/idioma.load';
delete from menu where link like '/jornadaEmpregado/jornadaEmpregado.load';
delete from menu where nome like '$menu.nome.viagem';
delete from menu where link like '/controleFinanceiroViagemImprevisto/controleFinanceiroViagemImprevisto.load';
delete from menu where link like '/tipoMovimFinanceiraViagem/tipoMovimFinanceiraViagem.load';
delete from menu where link like '/formaPagamento/formaPagamento.load';

-- FIM - MURILO GABRIEL RODRIGUES - 29/01/2014

-- INICIO - RODRIGO PECCI ACORSE - 03/02/2014

CREATE TABLE matrizcomunicacaotiporegistro (
  idtiporegistro int4 NOT NULL,
  tiporegistro varchar(255) NOT NULL,
  PRIMARY KEY (idtiporegistro)
);

CREATE TABLE matrizcomunicacaofrequencia (
  idfrequencia int4 NOT NULL,
  frequencia varchar(255) NOT NULL,
  PRIMARY KEY (idfrequencia)
);

CREATE TABLE matrizcomunicacaoformacontato (
  idformacontato int4 NOT NULL,
  formacontato varchar(255) NOT NULL,
  PRIMARY KEY (idformacontato)
);

CREATE TABLE matrizcomunicacao (
  idmatrizcomunicacao int4 NOT NULL,
  idcontrato int4 NOT NULL,
  grupoenvolvido int4 NOT NULL,
  responsabilidades text NOT NULL,
  idtiporegistro int4,
  idfrequencia int4,
  idformacontato int4,
  deleted char(1) null,
  PRIMARY KEY (idmatrizcomunicacao),
  foreign key (idcontrato) references contratos (idcontrato),
  foreign key (idtiporegistro) references matrizcomunicacaotiporegistro (idtiporegistro),
  foreign key (idfrequencia) references matrizcomunicacaofrequencia (idfrequencia),
  foreign key (idformacontato) references matrizcomunicacaoformacontato (idformacontato)
);

insert into matrizcomunicacaotiporegistro (idtiporegistro, tiporegistro) values (1,'Atas de reunião');
insert into matrizcomunicacaotiporegistro (idtiporegistro, tiporegistro) values (2,'Registro de solicitação');
insert into matrizcomunicacaotiporegistro (idtiporegistro, tiporegistro) values (3,'Formulários');
insert into matrizcomunicacaotiporegistro (idtiporegistro, tiporegistro) values (4,'Não conformidades / Reclamações de clientes');
insert into matrizcomunicacaotiporegistro (idtiporegistro, tiporegistro) values (5,'Falhas de sistema');

insert into matrizcomunicacaofrequencia (idfrequencia, frequencia) values (1,'Periódica');
insert into matrizcomunicacaofrequencia (idfrequencia, frequencia) values (2,'Diário');
insert into matrizcomunicacaofrequencia (idfrequencia, frequencia) values (3,'Semanal');
insert into matrizcomunicacaofrequencia (idfrequencia, frequencia) values (4,'Quinzenal');
insert into matrizcomunicacaofrequencia (idfrequencia, frequencia) values (5,'Mensal');
insert into matrizcomunicacaofrequencia (idfrequencia, frequencia) values (6,'Trimestral');
insert into matrizcomunicacaofrequencia (idfrequencia, frequencia) values (7,'Semestral');
insert into matrizcomunicacaofrequencia (idfrequencia, frequencia) values (8,'Anual');
insert into matrizcomunicacaofrequencia (idfrequencia, frequencia) values (9,'Quando o evento ocorrer');

insert into matrizcomunicacaoformacontato (idformacontato, formacontato) values (1,'E-mail');
insert into matrizcomunicacaoformacontato (idformacontato, formacontato) values (2,'Solicitação via Citsmart ou e-mail');
insert into matrizcomunicacaoformacontato (idformacontato, formacontato) values (3,'Telefone');
insert into matrizcomunicacaoformacontato (idformacontato, formacontato) values (4,'Internet');
insert into matrizcomunicacaoformacontato (idformacontato, formacontato) values (5,'Videoconferência');

-- FIM - RODRIGO PECCI ACORSE - 03/02/2014

-- INICIO - FLAVIO JUNIOR NEVES SANTANA SILVA - 05/02/2014

ALTER TABLE linhabaseprojeto ALTER COLUMN usuarioultalteracao TYPE character varying(255);
ALTER TABLE linhabaseprojeto ALTER COLUMN usuariosolmudanca TYPE character varying(255);

-- FIM - FLAVIO JUNIOR NEVES SANTANA SILVA - 05/02/2014

-- INICIO - THIAGO MATIAS BARBOSA - 06/02/2014

ALTER TABLE fornecedor add responsabilidades varchar(254);
ALTER TABLE fornecedor add idtiporegistro integer;
ALTER TABLE fornecedor add idfrequencia integer;
ALTER TABLE fornecedor add idformacontato integer;
ALTER TABLE fornecedor add ativ_responsabilidades text;
ALTER TABLE fornecedor add gerenciamentodesacordo text;

-- FIM - THIAGO MATIAS BARBOSA - 06/02/2014

-- INICIO - Thays Lorrana Almeida Araujo - 17/02/2014

INSERT INTO modelosemails ( idmodeloemail, titulo, texto, situacao, identificador ) VALUES ($id_modeloemail_criacao_relacionada,'Criação Solicitação Relacionada ','Informamos ao grupo executor que foi <strong>registrada </strong>a solicita&ccedil;&atilde;o conforme os dados abaixo, e a mesma &nbsp;est&aacute; relacionada a solicita&ccedil;&atilde;o<strong>&nbsp;${IDSOLICITACAORELACIONADA}</strong>&nbsp;.<br /><strong><br />N&uacute;mero:</strong>&nbsp;${IDSOLICITACAOSERVICO}<br /><strong>Tipo:</strong>&nbsp;${DEMANDA}<br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br />${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br /><br /><span style=\"background-color: rgb(255, 255, 0);\"><strong>&quot;Essa conta de e-mail &eacute; usada apenas para notifica&ccedil;&atilde;o, favor n&atilde;o responder. D&uacute;vidas, entrar em contato com o canal de atendimento.&quot;</strong></span>','A','CSR');
UPDATE parametrocorpore SET valor = '$id_modeloemail_criacao_relacionada' WHERE idparametrocorpore = 216;

INSERT INTO modelosemails ( idmodeloemail, titulo, texto, situacao, identificador ) VALUES ($id_modeloemail_atendimento_relacionada,'Atendimento Solicitação Relacionada','Informamos ao grupo executor que &nbsp;a solicita&ccedil;&atilde;o de n&uacute;mero&nbsp;<strong>${IDSOLICITACAOSERVICO}</strong> est&aacute; em <strong>atendimento&nbsp;</strong>conforme os dados abaixo, e a mesma &nbsp;est&aacute; relacionada a solicita&ccedil;&atilde;o&nbsp;<strong>${IDSOLICITACAORELACIONADA}</strong> .<br /><br /><strong>Tipo:</strong>&nbsp;${DEMANDA}<br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br />${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br /><br /><span style=\"background-color: rgb(255, 255, 0);\"><strong>&quot;Essa conta de e-mail &eacute; usada apenas para notifica&ccedil;&atilde;o, favor n&atilde;o responder. D&uacute;vidas, entrar em contato com o canal de atendimento.&quot;</strong></span>','A','ASR');
UPDATE parametrocorpore SET valor = '$id_modeloemail_atendimento_relacionada' WHERE idparametrocorpore = 217;

INSERT INTO modelosemails ( idmodeloemail, titulo, texto, situacao, identificador ) VALUES ($id_modeloemail_concluida_relacionada,'Concluída Solicitação Relacionada','Informamos ao grupo executor que &nbsp;a solicita&ccedil;&atilde;o de n&uacute;mero<strong> ${IDSOLICITACAOSERVICO}&nbsp;</strong> foi&nbsp;<strong>conclu&iacute;da</strong>&nbsp;conforme os dados abaixo, e a mesma &nbsp;est&aacute; relacionada a solicita&ccedil;&atilde;o<strong>&nbsp;${IDSOLICITACAORELACIONADA}</strong>&nbsp;.<br /><strong><br /></strong><br /><strong>Tipo:</strong>&nbsp;${DEMANDA}<br /><strong>Servi&ccedil;o:</strong>&nbsp;${SERVICO}<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;<br />${DESCRICAO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br /><br /><span style=\"background-color: rgb(255, 255, 0);\"><strong>&quot;Essa conta de e-mail &eacute; usada apenas para notifica&ccedil;&atilde;o, favor n&atilde;o responder. D&uacute;vidas, entrar em contato com o canal de atendimento.&quot;</strong></span>','A','ESR');
UPDATE parametrocorpore SET valor = '$id_modeloemail_concluida_relacionada' WHERE idparametrocorpore = 218;

-- FIM - Thays

-- INICIO - EDU RODRIGUES BRAZ - 17/03/2014

ALTER TABLE empregados ALTER nome TYPE character varying(256);
ALTER TABLE empregados ALTER nomeprocura TYPE character varying(256);

-- FIM - EDU RODRIGUES BRAZ - 17/03/2014

-- INICIO - EDU RODRIGUES BRAZ - 24/02/2014

ALTER TABLE contatosolicitacaoservico ALTER COLUMN nomecontato TYPE varchar(255);
ALTER TABLE contatosolicitacaoservico ALTER COLUMN nomecontato SET NOT NULL;

-- FIM - EDU RODRIGUES BRAZ - 24/02/2014

-- INICIO - RODRIGO PECCI ACORSE - 25/02/2014

ALTER TABLE modelosemails ALTER COLUMN titulo TYPE CHARACTER VARYING(255);

-- FIM - RODRIGO PECCI ACORSE - 25/02/2014

-- INICIO -  GILBERTO TAVARES DE FRANCO NERY - 12/03/2013

CREATE  TABLE rh_parceiro (
  idparceiro INTEGER NOT NULL,
  nome VARCHAR(120) NOT NULL,
  razaosocial VARCHAR(120),
  tipopessoa CHAR(1) NOT NULL,
  dataalteracao DATE NOT NULL,
  ativo CHAR(1) NOT NULL,
  situacao CHAR(1) NULL,
  fornecedor CHAR(1) NULL,
  PRIMARY KEY (idparceiro)
);

CREATE TABLE importardados (
  idimportardados integer NOT NULL,
  idexternalconnection integer NOT NULL,
  importarpor char(1) NOT NULL,
  tipo char(1) NOT NULL,
  nome varchar(100),
  script text NULL,
  agendarrotina char(1) NULL,
  executarpor char(1) NULL,
  horaexecucao varchar(10) NULL,
  periodohora int NULL,
  datafim date NULL,
  tabelaorigem varchar(100) NULL,
  tabeladestino varchar(100) NULL,
  jsonmatriz text NULL,
  CONSTRAINT importardados_pkey PRIMARY KEY (idimportardados),
  CONSTRAINT externalconnection_fkey FOREIGN KEY (idexternalconnection)
      REFERENCES externalconnection (idexternalconnection) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE controleimportardados (
  idcontroleimportardados integer NOT NULL,
  idimportardados integer NOT NULL,
  dataexecucao date NULL,
  CONSTRAINT controleimportardados_pkey PRIMARY KEY (idcontroleimportardados),
  CONSTRAINT importardados_fkey FOREIGN KEY (idimportardados)
      REFERENCES importardados (idimportardados) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

alter table importconfig add idimportardados integer;
alter table importconfig add datafim DATE;
alter table importconfigcampos add idimportardados integer;

-- FIM -  GILBERTO TAVARES DE FRANCO NERY - 12/03/2013

-- INICIO -  GILBERTO TAVARES DE FRANCO NERY - 13/03/2013

alter table rh_jornadadetrabalho add codcargahor INTEGER;

CREATE  TABLE rh_cargahoraria (
  idcargahoraria INTEGER NOT NULL ,
  codcargahor INTEGER NOT NULL ,
  diasemana SMALLINT NOT NULL ,
  entrada SMALLINT NOT NULL ,
  saida SMALLINT NOT NULL ,
  descansoem CHAR(1) NOT NULL ,
  turno SMALLINT NOT NULL ,
  dataalter DATE NOT NULL ,
  PRIMARY KEY (idcargahoraria)
);

CREATE TABLE rh_funcionario (
  idfuncionario INTEGER NOT NULL ,
  nome VARCHAR(100) NOT NULL ,
  cpf CHAR(11) NOT NULL ,
  datainicio DATE NULL ,
  datafim DATE NULL ,
  PRIMARY KEY (idfuncionario) 
);

CREATE  TABLE rh_departamento (
  iddepartamento INTEGER NOT NULL ,
  descricao VARCHAR(30) NULL ,
  idcentrocusto INTEGER NULL ,
  lotacaopai INTEGER NULL ,
  situacao CHAR(1) NULL ,
  analitico CHAR(1) NULL ,
  idparceiro INTEGER NULL ,
  coddep INTEGER NULL ,
  PRIMARY KEY (iddepartamento) 
);

-- FIM -  GILBERTO TAVARES DE FRANCO NERY - 13/03/2013
