-- INICIO - Bruno.Aquino 29/05/2014
	INSERT INTO modelosemails (idmodeloemail,	titulo,	texto,	situacao,	identificador)
		VALUES(86,'Alteração da Solicitação de Serviço - ${IDSOLICITACAOSERVICO}',
			'&nbsp;Informamos que a Solicita&ccedil;&atilde;o de Servi&ccedil;o de N&uacute;mero ${IDSOLICITACAOSERVICO} sofreu altera&ccedil;&atilde;o.<div>&nbsp;</div><div>Atenciosamente,</div><div>&nbsp;</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div><div>&nbsp;</div>',
			'A','alterSolServico');
	
	INSERT INTO modelosemails (	idmodeloemail,	titulo,	texto,	situacao,	identificador)
		VALUES	(87,'Alteração da Descrição da Solicitação de Serviço - ${IDSOLICITACAOSERVICO}',
			'&nbsp;Informamos que a Solicita&ccedil;&atilde;o de Servi&ccedil;o de N&uacute;mero ${IDSOLICITACAOSERVICO} sofreu altera&ccedil;&atilde;o.<div>&nbsp;</div><div><strong>Descri&ccedil;&atilde;o</strong>:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>Atenciosamente,</div><div>&nbsp;</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div><div>&nbsp;</div>',
			'A','alterSolServDesc');
      
-- FIM - Bruno.Aquino 29/05/2014

-- Inicio - maycon.fernandes 02/06/2014
create table problemarelacionado(
 idproblemarelacionado number(19,0) not null,
 idproblema number(19,0) not null,
  primary key (idproblema, idproblemarelacionado)
);
-- Fim - maycon.fernandes 02/06/2014

-- INICIO - rodrigo.acorse - 02/06/2014
CREATE TABLE EMAILSOLICITACAO (
	IDEMAIL NUMBER(11) NOT NULL,
	IDMESSAGE VARCHAR2(500) NOT NULL,
	IDSOLICITACAO NUMBER(11) NOT NULL,
	ORIGEM VARCHAR2(255) NOT NULL,
	PRIMARY KEY (IDEMAIL)
);

CREATE INDEX INDEX_IDMESSAGE ON EMAILSOLICITACAO (IDMESSAGE ASC);
CREATE INDEX INDEX_IDSOLICITACAO ON EMAILSOLICITACAO (IDSOLICITACAO ASC);
CREATE UNIQUE INDEX INDEX_IDEMAIL ON EMAILSOLICITACAO (IDEMAIL ASC);
-- FIM - rodrigo.acorse - 02/06/2014