set sql_safe_updates = 0;

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
 idproblemarelacionado int(11) not null,
 idproblema int(11) not null,
  primary key (idproblema, idproblemarelacionado)
)ENGINE = InnoDB;
-- Fim - maycon.fernandes 02/06/2014

-- INICIO - rodrigo.acorse - 02/06/2014
CREATE TABLE emailsolicitacao (
	idemail  int(11) NOT NULL,
	idmessage  varchar(500) NOT NULL,
	idsolicitacao  int(11) NOT NULL,
	origem  varchar(255) NOT NULL,
	PRIMARY KEY (idemail),
	INDEX index_idmessage (idmessage),
	INDEX index_idsolicitacao (idsolicitacao),
	UNIQUE INDEX index_idemail (idemail)
);
-- FIM - rodrigo.acorse - 02/06/2014

set sql_safe_updates = 1;