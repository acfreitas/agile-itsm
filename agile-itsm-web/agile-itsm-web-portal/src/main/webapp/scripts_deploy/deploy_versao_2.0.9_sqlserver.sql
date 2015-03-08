-- SQLServer

-- Início Murilo Gabriel 27/06/13

insert into modelosemails (idmodeloemail, titulo, texto, situacao, identificador) values (54, 'Pesquisa de Satisfação - Solicitação ${IDSOLICITACAOSERVICO}', 'Uma nota &quot;${NOTA}&quot; foi dada pelo usu&aacute;rio ${USUARIO} na pesquisa de satisfa&ccedil;&atilde;o da solicita&ccedil;&atilde;o&nbsp;${IDSOLICITACAOSERVICO}.<br />Segue o coment&aacute;rio/sujest&atilde;o de melhoria informados pelo usu&aacute;rio:<br /><br />${COMENTARIO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br type="_moz" />', 'A', 'pesqSatisfNegativo');

-- Fim Murilo Gabriel

-- Início Flávio 28/06/13

ALTER TABLE acordoservicocontrato ADD habilitado varchar (45);

UPDATE acordoservicocontrato SET habilitado = 'S';

-- Fim Flávio

-- Início Cledson 04/07/13

CREATE TABLE historicosolicitacaoservico (
  idhistoricosolicitacao integer NOT NULL,
  idsolicitacaoservico integer NOT NULL,
  idresponsavelatual integer,
  idgrupo integer,
  idocorrencia integer NOT NULL,
  idservicocontrato integer NOT NULL,
  idcalendario integer NOT NULL,
  datacriacao datetime,
  datafinal datetime,
  horastrabalhadas float,
  status varchar(50)
);

-- Fim Cledson

-- Início Flávio Júnior 19/07/13

ALTER TABLE moedas ADD datainicio DATE NULL;

ALTER TABLE moedas ADD datafim DATE NULL;

ALTER TABLE moedas ADD usarcotacao VARCHAR(1) NULL;

-- Fim Flávio Júnior

-- Início Rodrigo Engelberg 22/07/13

update menu set datafim = sysdatetime() where descricao like 'Download Agente';

-- Fim Rodrigo Engelberg
