-- Oracle

-- In�cio Murilo Gabriel 27/06/13

insert into modelosemails (idmodeloemail, titulo, texto, situacao, identificador) values (54, 'Pesquisa de Satisfa��o - Solicita��o ${IDSOLICITACAOSERVICO}', 'Uma nota &quot;${NOTA}&quot; foi dada pelo usu&aacute;rio ${USUARIO} na pesquisa de satisfa&ccedil;&atilde;o da solicita&ccedil;&atilde;o&nbsp;${IDSOLICITACAOSERVICO}.<br />Segue o coment&aacute;rio/sujest&atilde;o de melhoria informados pelo usu&aacute;rio:<br /><br />${COMENTARIO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br type="_moz" />', 'A', 'pesqSatisfNegativo');

-- Fim Murilo Gabriel

-- In�cio Fl�vio 28/06/13

ALTER TABLE ACORDOSERVICOCONTRATO ADD (HABILITADO VARCHAR2(45));

UPDATE acordoservicocontrato SET habilitado = 'S';

-- Fim Fl�vio

-- In�cio Cledson 04/07/13

CREATE TABLE historicosolicitacaoservico (
	idhistoricosolicitacao number(11,0) NOT NULL,
	idsolicitacaoservico number(11,0) NOT NULL,
	idresponsavelatual number(11,0),
	idgrupo number(11,0),
	idocorrencia number(11,0) NOT NULL,
	idservicocontrato number(11,0) NOT NULL,
	idcalendario number(11,0) NOT NULL,
	datacriacao date default null,
	datafinal date default null,
	horastrabalhadas number(11,2),
	status varchar2(45)
);

-- Fim Cledson

-- In�cio Fl�vio J�nior 19/07/13

ALTER TABLE MOEDAS ADD (DATAINICIO DATE);

ALTER TABLE MOEDAS ADD (DATAFIM DATE);

ALTER TABLE MOEDAS ADD (USARCOTACAO VARCHAR2(1));

-- Fim Fl�vio J�nior

-- In�cio Rodrigo Engelberg 22/07/13

update menu set datafim = sysdate where descricao like 'Download Agente';

-- Fim Rodrigo Engelberg