-- INICIO - rodrigo.acorse - 16.06.2014

CREATE TABLE monitoramentoativos (
	idmonitoramentoativos INT NOT NULL,
  	idtipoitemconfiguracao INT NOT NULL,
  	titulo VARCHAR(255) NOT NULL,
  	descricao CLOB NULL,
  	tiporegra CHAR NULL,
  	enviaremail CHAR NULL,
  	criarproblema CHAR NULL,
  	criarincidente CHAR NULL,
  	datainicio DATE NOT NULL,
  	datafim DATE NULL,
	PRIMARY KEY (idmonitoramentoativos),
	CONSTRAINT fk_monitoramento_tipoitemconf FOREIGN KEY (idtipoitemconfiguracao) REFERENCES tipoitemconfiguracao (idtipoitemconfiguracao) 
); 

CREATE TABLE notificacaousuariomonit (
  	idnotificacaousuariomonit INT NOT NULL,
  	idmonitoramentoativos INT NOT NULL,
  	idusuario INT NOT NULL,
  	datainicio DATE NOT NULL,
  	datafim DATE NULL,
	PRIMARY KEY (idnotificacaousuariomonit),
	CONSTRAINT fk_notificacao_monitoramento FOREIGN KEY (idmonitoramentoativos) REFERENCES monitoramentoativos (idmonitoramentoativos),
	CONSTRAINT fk_notificacao_usuario FOREIGN KEY (idusuario) REFERENCES usuario (idusuario)
); 

CREATE TABLE notificacaogrupomonit (
  	idnotificacaogrupomonit INT NOT NULL,
  	idmonitoramentoativos INT NOT NULL,
  	idgrupo INT NOT NULL,
  	datainicio DATE NOT NULL,
  	datafim DATE NULL,
	PRIMARY KEY (idnotificacaogrupomonit),
	CONSTRAINT fk_notificacaogrupo_monit_idx FOREIGN KEY (idmonitoramentoativos) REFERENCES monitoramentoativos (idmonitoramentoativos),
	CONSTRAINT fk_notificacao_grupo_idx FOREIGN KEY (idgrupo) REFERENCES grupo (idgrupo)
); 

CREATE TABLE caracteristicamonit (
 	idcaracteristicamonit INT NOT NULL,
  	idcaracteristica INT NOT NULL,
  	idmonitoramentoativos INT NOT NULL,
  	datainicio DATE NOT NULL,
  	datafim DATE NULL,
	PRIMARY KEY (idcaracteristicamonit),
	CONSTRAINT fk_caracteristicamonit_carac FOREIGN KEY (idcaracteristica) REFERENCES caracteristica (idcaracteristica),
	CONSTRAINT fk_caracteristicamonit_monit FOREIGN KEY (idmonitoramentoativos) REFERENCES monitoramentoativos (idmonitoramentoativos)
); 
	
CREATE TABLE scriptmonit (
  	idscriptmonit INT NOT NULL,
  	idmonitoramentoativos INT NOT NULL,
  	script CLOB NOT NULL,
  	datainicio DATE NOT NULL,
  	datafim DATE NULL,
	PRIMARY KEY (idscriptmonit),
	CONSTRAINT fk_scriptmonit_monit_idx FOREIGN KEY (idmonitoramentoativos) REFERENCES monitoramentoativos (idmonitoramentoativos)
); 

-- FIM - rodrigo.acorse - 16.06.2014
    
-- INICIO - rodrigo.acorse - 16.06.2014

insert into modelosemails (idmodeloemail, titulo, texto, situacao, identificador) values ($id_modelo_email_monitoramento, 'Monitoramento Tipo Item de Configuração - ${IDENTIFICACAO}', 'Informamos que o item de configura&ccedil;&atilde;o identificado como <strong>${IDENTIFICACAO}</strong> sofreu altera&ccedil;&atilde;o.<br /><br /><strong>Descri&ccedil;&atilde;o:</strong>&nbsp;${DESCRICAO}<br /><strong>Novo valor:</strong>&nbsp;${VALORSTR}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.', 'A', 'monitAtivosNotif');
    
-- FIM - rodrigo.acorse - 16.06.2014