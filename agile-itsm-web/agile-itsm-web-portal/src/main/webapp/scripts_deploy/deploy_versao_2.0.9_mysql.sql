-- MySQL

set sql_safe_updates = 0;

-- Início Murilo Gabriel 27/06/13

insert into modelosemails (idmodeloemail, titulo, texto, situacao, identificador) values (54, 'Pesquisa de Satisfação - Solicitação ${IDSOLICITACAOSERVICO}', 'Uma nota &quot;${NOTA}&quot; foi dada pelo usu&aacute;rio ${USUARIO} na pesquisa de satisfa&ccedil;&atilde;o da solicita&ccedil;&atilde;o&nbsp;${IDSOLICITACAOSERVICO}.<br />Segue o coment&aacute;rio/sujest&atilde;o de melhoria informados pelo usu&aacute;rio:<br /><br />${COMENTARIO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br type="_moz" />', 'A', 'pesqSatisfNegativo');

-- Fim Murilo Gabriel

-- Início Flávio 28/06/13

ALTER TABLE acordoservicocontrato ADD COLUMN habilitado VARCHAR(45);

UPDATE acordoservicocontrato SET habilitado = 'S';

-- Fim Flávio

-- Início Cledson 04/07/13

CREATE TABLE historicosolicitacaoservico (
	  idhistoricosolicitacao int(11) NOT NULL,
	  idsolicitacaoservico int(11) NOT NULL,
	  idresponsavelatual int(11),
	  idgrupo int(11),
	  idocorrencia int(11) NOT NULL,
	  idservicocontrato int(11) NOT NULL,
	  idcalendario int(11) NOT NULL,
	  datacriacao timestamp NULL ,
	  datafinal timestamp NULL ,
	  horastrabalhadas float,
	  status varchar(50)
);

-- fim Cledson

-- Início Flávio Júnior 19/07/13

ALTER TABLE moedas ADD COLUMN datainicio date;

ALTER TABLE moedas ADD COLUMN datafim date;

ALTER TABLE moedas ADD COLUMN usarcotacao varchar(1);

-- Fim Flávio Júnior

-- Início Rodrigo Engelberg 22/07/13

update menu set datafim = current_date() where descricao like 'Download Agente';

-- Fim Rodrigo Engelberg

set sql_safe_updates = 1;