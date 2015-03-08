
ALTER TABLE coletapreco ALTER COLUMN prazomediopagto DECIMAL(10,2) NOT NULL;

-- INICIO - thyen.chang - 27.11.2014

ALTER TABLE coletapreco ALTER COLUMN prazomediopagto INT(11) NOT NULL;

-- FIM - thyen.chang - 27.11.2014

-- Inicio Script  autor Ezequiel --

ALTER TABLE perfilacesso ADD acessosistemacitsmart char(1)  DEFAULT 'S';

-- FIM Script --

-- INICIO - thyen.chang - 02/12/2014

alter table itemconfiguracao add nome varchar(400);
update itemconfiguracao set nome = identificacao;
alter table itemconfiguracao alter column nome varchar(400) not null;
alter table itemconfiguracao add informacoesadicionais varchar(4000) default '' not null;

-- FIM - thyen.chang 02/12/2014

-- INICIO - thyen.chang - 04/12/2014

alter table itemconfiguracao add idgruporesponsavel integer;

-- FIM - thyen.chang	- 04/12/2014

-- INICIO - euler.ramos - 03.12.2014

alter table ocorrenciasolicitacao add notificarsolicitante char(1);
alter table ocorrenciasolicitacao add default 'N' for notificarsolicitante;
update ocorrenciasolicitacao set notificarsolicitante = 'N';
insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values ($id_modeloemail_90,'Ocorrência da Solicitação - ${IDSOLICITACAOSERVICO}','Senhor(a) <strong>${NOMECONTATO}</strong>,<br /><br />O t&eacute;cnico solucionador <strong>${REGISTRADOPOR}</strong> registrou a seguinte ocorr&ecirc;ncia:<br />Descri&ccedil;&atilde;o:<br /><strong>${DESCRICAO}</strong><br /><br />Ocorr&ecirc;ncia:<br /><strong>${OCORRENCIA}</strong><br /><br />N&uacute;mero da solicita&ccedil;&atilde;o: <strong>${IDSOLICITACAOSERVICO}</strong><br />Tipo: <strong>${DEMANDA}</strong><br />Servi&ccedil;o:<strong> ${SERVICO}</strong><br />Informa&ccedil;&otilde;es de Contato:<strong> ${INFORMACOESCONTATO}</strong><br />Categoria:<strong> ${CATEGORIA}</strong><br />Origem: <strong>${ORIGEM}</strong><br />Tempo gasto:<strong> ${TEMPOGASTO}</strong><br /><br />Atenciosamente,<br />Central de Servi&ccedil;os de TI','A','NOTIFOCORRENCIA');
update parametrocorpore set valor = '$id_modeloemail_90' where nomeparametrocorpore =  'parametro.251';

-- FIM - euler.ramos - 03.12.2014

-- INICIO - thyen.chang - 08/12/2014

UPDATE menu SET datafim = GETDATE() WHERE nome LIKE  '%$menu.nome.relatorioRetorno%';

-- FIM - thyen.chang - 08/12/2014

-- INICIO - ezequiel.nunes - 11.12.2014

alter table ocorrenciasolicitacao add notificarresponsavel char(1);
alter table ocorrenciasolicitacao add default 'N' for notificarresponsavel;
insert into modelosemails (idmodeloemail,titulo,texto,situacao,identificador) values($id_modelo_email_notificar_responsavel,'Registro ocorrência pelo portal para solicitação - ${IDSOLICITACAOSERVICO}','&nbsp;Informamos ao grupo executor que foi registrada uma ocorr&ecirc;ncia para a solicita&ccedil;&atilde;o de n&uacute;mero ${IDSOLICITACAOSERVICO} conforme os dados abaixo:<div>&nbsp;</div><div>&nbsp;</div><div>Data/hora: ${dataHora}</div><div>Registrado por: ${registradoPor},</div><div>Categoria: ${categoria}</div><div>Origem: ${origem}</div><div>Ocorr&ecirc;ncia: ${ocorrencias}</div><div>Informa&ccedil;&otilde;es do Contato: ${informacoesContato}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:${descricao}</div><div>&nbsp;</div><div>Atenciosamente,</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div><div>&nbsp;</div><div>&quot;Essa conta de e-mail &eacute; usada apenas para notifica&ccedil;&atilde;o, favor n&atilde;o responder. D&uacute;vidas, entrar em contato com o canal de atendimento.&quot;</div>','A','regOcorrenciaPortal');
update parametrocorpore set valor = '$id_modelo_email_notificar_responsavel' where nomeparametrocorpore =  'parametro.253';

-- FIM - ezequiel.nunes - 11.12.2014

-- INICIO - thyen.chang - 18/12/2014

ALTER TABLE itemconfiguracao ALTER COLUMN nome VARCHAR(400) NULL;
ALTER TABLE itemconfiguracao ALTER COLUMN informacoesadicionais VARCHAR(4000) NULL;

-- FIM -- thyen.chang - 18/12/2014
