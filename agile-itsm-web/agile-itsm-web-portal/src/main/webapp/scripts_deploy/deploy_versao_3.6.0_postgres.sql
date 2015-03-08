-- INICIO - Bruno.Aquino 29/05/2014
	INSERT INTO modelosemails (idmodeloemail,	titulo,	texto,	situacao,	identificador)
		VALUES(86,'Altera��o da Solicita��o de Servi�o - ${IDSOLICITACAOSERVICO}',
			'&nbsp;Informamos que a Solicita&ccedil;&atilde;o de Servi&ccedil;o de N&uacute;mero ${IDSOLICITACAOSERVICO} sofreu altera&ccedil;&atilde;o.<div>&nbsp;</div><div>Atenciosamente,</div><div>&nbsp;</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div><div>&nbsp;</div>',
			'A','alterSolServico');
	
	INSERT INTO modelosemails (	idmodeloemail,	titulo,	texto,	situacao,	identificador)
		VALUES	(87,'Altera��o da Descri��o da Solicita��o de Servi�o - ${IDSOLICITACAOSERVICO}',
			'&nbsp;Informamos que a Solicita&ccedil;&atilde;o de Servi&ccedil;o de N&uacute;mero ${IDSOLICITACAOSERVICO} sofreu altera&ccedil;&atilde;o.<div>&nbsp;</div><div><strong>Descri&ccedil;&atilde;o</strong>:&nbsp;</div><div>${DESCRICAO}</div><div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div><div>Atenciosamente,</div><div>&nbsp;</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div><div>&nbsp;</div>',
			'A','alterSolServDesc');
      
-- FIM - Bruno.Aquino 29/05/2014

-- Inicio - maycon.fernandes 02/06/2014
create table problemarelacionado(
 idproblemarelacionado integer not null,
 idproblema integer not null,
  primary key (idproblema, idproblemarelacionado)
);
-- Fim - maycon.fernandes 02/06/2014

-- INICIO - rodrigo.acorse - 02/06/2014
CREATE TABLE emailsolicitacao (
	idemail int4 NOT NULL,
	idmessage varchar(500) NOT NULL,
	idsolicitacao int4 NOT NULL,
	origem varchar(255) NOT NULL,
	PRIMARY KEY (idemail)
);

CREATE INDEX index_idmessage ON emailsolicitacao (idmessage);
CREATE INDEX index_idsolicitacao ON emailsolicitacao (idsolicitacao);
CREATE UNIQUE INDEX index_idemail ON emailsolicitacao (idemail);
-- FIM - rodrigo.acorse - 02/06/2014
