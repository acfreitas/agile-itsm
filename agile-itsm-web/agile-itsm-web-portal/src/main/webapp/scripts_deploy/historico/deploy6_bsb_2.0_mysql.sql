set sql_safe_updates = 0;

drop table if exists criteriocotacaocategoria;

alter table cotacaoitemrequisicao add iditemtrabalho int;
alter table cotacaoitemrequisicao add foreign key (iditemtrabalhoaprovacao)
      references bpm_itemtrabalhofluxo (iditemtrabalho) on delete restrict on update restrict;

ALTER TABLE `inspecaoentregaitem` CHANGE COLUMN `avaliacao` `avaliacao` VARCHAR(25) NULL DEFAULT NULL  ;

ALTER TABLE `inspecaopedidocompra` CHANGE COLUMN `avaliacao` `avaliacao` VARCHAR(25) NULL DEFAULT NULL  ;

create table criteriocotacaocategoria
(
   idcategoria          int not null,
   idcriterio           int not null,
   pesocotacao          int not null
) ENGINE=InnoDB;

alter table criteriocotacaocategoria
   add primary key (idcategoria, idcriterio);

alter table criteriocotacaocategoria add foreign key (idcategoria)
      references categoriaproduto (idcategoria) on delete restrict on update restrict;

alter table criteriocotacaocategoria add foreign key (idcriterio)
      references criterioavaliacao (idcriterio) on delete restrict on update restrict;

drop table if exists historicosituacaocotacao;

create table historicosituacaocotacao
(
   idhistorico          int not null,
   idcotacao            int not null,
   idresponsavel        int not null,
   datahora             timestamp not null,
   situacao             varchar(25) not null
) ENGINE=InnoDB;

alter table historicosituacaocotacao
   add primary key (idhistorico);

alter table historicosituacaocotacao add foreign key (idcotacao)
      references cotacao (idcotacao) on delete restrict on update restrict;

alter table historicosituacaocotacao add foreign key (idresponsavel)
      references empregados (idempregado) on delete restrict on update restrict;

ALTER TABLE `endereco` CHANGE COLUMN `logradouro` `logradouro` VARCHAR(200) NULL;

ALTER TABLE avaliacaoreferenciafornecedor DROP FOREIGN KEY `fk_reference_682` ;

ALTER TABLE avaliacaoreferenciafornecedor
    ADD FOREIGN KEY (idempregado) REFERENCES empregados (idempregado);

create index index_menu_nome on menu
(
   nome,
   ordem
);
 
create index index_menu_link on menu
(
   link
);
 
create index index_menu_rapido on menu
(
   menurapido
);
 
create index index_dic_nome on dicionario
(
   nome
);
 
create index index_dic_valor on dicionario
(
   valor
);
 
create index index_dic_lingua on dicionario
(
   idlingua
);

alter table bpm_elementofluxo add destinatariosemail text;

ALTER TABLE `matrizvisao` ENGINE = InnoDB;
 
ALTER TABLE `vinculovisao` ENGINE = InnoDB;
 
ALTER TABLE `scriptsvisao` ENGINE = InnoDB;
 
ALTER TABLE `botaoacaovisao` ENGINE = InnoDB;
 
ALTER TABLE `htmlcodevisao` ENGINE = InnoDB;
 
ALTER TABLE `visaorelacionada` ENGINE = InnoDB;
 
ALTER TABLE `grupovisaocamposnegocioligacao` ENGINE = InnoDB;
 
ALTER TABLE `valorvisaocamposnegocio` ENGINE = InnoDB;
 
ALTER TABLE `grupovisaocamposnegocio` ENGINE = InnoDB;

ALTER TABLE `bpm_itemtrabalhofluxo` CHANGE COLUMN `datahoraexecucao` `datahoraexecucao` TIMESTAMP NULL;

UPDATE `bpm_itemtrabalhofluxo` set datahoraexecucao = null where datahoraexecucao = "0000-00-00 00:00:00";

ALTER TABLE `logdados` CHANGE COLUMN `idlog` `idlog` INT(255) NOT NULL AUTO_INCREMENT  ;

INSERT INTO `bpm_tipofluxo` (`idtipofluxo`,`nomefluxo`,`descricao`,`nomeclassefluxo`) VALUES (5,'RequisicaoProduto','Requisição de Produto','br.com.centralit.citcorpore.bpm.negocio.ExecucaoRequisicaoProduto');

UPDATE bpm_fluxo set datafim = datainicio where idtipofluxo = 5;

INSERT INTO `bpm_fluxo` (`idfluxo`,`versao`,`idtipofluxo`,`variaveis`,`conteudoxml`,`datainicio`,`datafim`) VALUES (30,'17.0',5,'solicitacaoServico;solicitacaoServico.situacao;solicitacaoServico.grupoAtual;solicitacaoServico.grupoNivel1',NULL,'2013-01-20',NULL);

INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (250,30,'Inicio','','','','','','','','','','','','','',15,21,32,32,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (251,30,'Tarefa','','Validar requisição','Validar requisição','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','','#{solicitacaoServico.grupoAtual}','','','','','','',136,5,65,140,'','ValidacaoRequisicaoProduto',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (252,30,'Tarefa','','Autorizar requisição','Autorizar requisição','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','',NULL,'script:#{execucaoFluxo}.recuperaLoginAutorizadores();','#{execucaoFluxo}.atribuiAcompanhamento(#{itemTrabalho},null,#{solicitacaoServico.grupoAtual});\n#{execucaoFluxo}.atribuiAcompanhamento(#{itemTrabalho},#{execucaoFluxo}.recuperaLoginAutorizadores(),null); \n','','','','',489,94,65,140,'','AutorizacaoCotacao',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (253,30,'Tarefa','','Acompanhar requisição','Acompanhamento requisição','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?alterarSituacao=S','','#{solicitacaoServico.grupoNivel1}','','#{execucaoFluxo}.atribuiAcompanhamento(#{itemTrabalho},#{execucaoFluxo}.recuperaLoginAutorizadores(),null);\n#{execucaoFluxo}.atribuiAcompanhamento(#{itemTrabalho},null,#{solicitacaoServico.grupoAtual});','','','','',489,217,65,140,'','AcompanhamentoRequisicaoProduto',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (254,30,'Tarefa','','Complementar dados da requisicão','Complementar dados da requisicão','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?alterarSituacao=S','','#{solicitacaoServico.grupoNivel1}','script:#{execucaoFluxo}.recuperaLoginResponsaveis();','#{execucaoFluxo}.atribuiAcompanhamento(#{itemTrabalho},#{execucaoFluxo}.recuperaLoginAutorizadores(),null);\n#{execucaoFluxo}.atribuiAcompanhamento(#{itemTrabalho},null,#{solicitacaoServico.grupoAtual});','','','','',137,237,65,140,'','AlteracaoRequisicaoProduto',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (255,30,'Tarefa','','Aprovar cotação','Aprovar cotação','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load',NULL,'script:#{execucaoFluxo}.recuperaLoginAutorizadores();','#{execucaoFluxo}.associaItemTrabalhoAprovacao(#{itemTrabalho}); \n#{execucaoFluxo}.atribuiAcompanhamento(#{itemTrabalho},null,#{solicitacaoServico.grupoAtual});','','','','',907,22,65,140,'','AprovacaoCotacao',NULL,'','S');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (256,30,'Tarefa','','Inspecionar entrega','Inspecionar entrega','U','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','#{solicitacaoServico.grupoNivel1}','script:#{execucaoFluxo}.recuperaLoginResponsaveis();','#{execucaoFluxo}.associaItemTrabalhoInspecao(#{itemTrabalho});','','','','',911,143,65,140,'','InspecaoEntregaItem',NULL,'','S');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (257,30,'Tarefa','','Acionar garantia','Acionar garantia','','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?alterarSituacao=S','/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load','#{solicitacaoServico.grupoAtual}','','#{execucaoFluxo}.associaItemTrabalhoGarantia(#{itemTrabalho});','','','','',910,244,65,140,'','AcionamentoGarantia',NULL,'','S');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (258,30,'Script','','Encerra','','','','','','','','','#{execucaoFluxo}.encerra();','','',718,314,65,140,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (259,30,'Evento','','','','','','','','','','','','','',669,294,32,32,'','',30,'!#{solicitacaoServico}.finalizada();','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (260,30,'Porta','','','','','','','','','','','','','',345,105,42,42,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (261,30,'Porta','','','','','','','','','','','','','',185,104,42,42,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (262,30,'Porta','','','','','','','','','','','','','',538,17,42,42,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (263,30,'Porta','','','','','','','','','','','','','',767,154,42,42,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (264,30,'Finalizacao','','','','','','','','','','','','','',960,331,32,32,'','',NULL,'','');

INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (250,251,30,NULL,NULL,'!#{solicitacaoServico}.atendida();',1,3,91.5,37.25,'N','não atendida');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (251,261,30,NULL,NULL,'',2,0,206,87,'N','');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (252,253,30,NULL,NULL,'!#{execucaoFluxo}.requisicaoRejeitada();',2,0,559,188,'N','não rejeitada');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (252,262,30,NULL,NULL,'',0,2,559,76.5,'N','');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (253,258,30,NULL,NULL,'#{solicitacaoServico}.finalizada();',2,2,559,391,'S','finalizada');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (254,251,30,NULL,NULL,'',3,3,125,161,'S','');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (254,258,30,NULL,NULL,'#{solicitacaoServico}.finalizada();',2,3,206,346,'S','finalizada');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (258,264,30,NULL,NULL,'',1,3,909,346.75,'N','');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (259,263,30,NULL,NULL,'',1,3,734,242.5,'N','');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (260,252,30,NULL,NULL,'#{execucaoFluxo}.exigeAutorizacao();',1,3,438,126.25,'N','exige autorização');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (260,253,30,NULL,NULL,'!#{execucaoFluxo}.exigeAutorizacao();',2,3,422,203,'S','não exige autorização');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (260,259,30,NULL,NULL,'!#{execucaoFluxo}.exigeAutorizacao();',2,2,365,337,'S','não exige autorização');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (261,254,30,NULL,NULL,'#{execucaoFluxo}.requisicaoRejeitada();',2,0,206.5,191.5,'N','rejeitada');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (261,260,30,NULL,NULL,'!#{execucaoFluxo}.requisicaoRejeitada();',1,3,286,125.5,'N','não rejeitada');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (262,254,30,NULL,NULL,'#{execucaoFluxo}.requisicaoRejeitada();',3,1,289,39,'S','rejeitada');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (262,259,30,NULL,NULL,'!#{execucaoFluxo}.requisicaoRejeitada();',1,0,684,39,'S','não rejeitada');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (263,255,30,NULL,NULL,'#{execucaoFluxo}.existeAprovacaoPendente();',0,3,788,56,'S','aprovação pendente');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (263,256,30,NULL,NULL,'#{execucaoFluxo}.existeEntregaPendenteInspecao();',1,3,855,175,'S','inspeção entrega pendente');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (263,257,30,NULL,NULL,'#{execucaoFluxo}.existeEntregaNaoAprovada();',1,3,859.5,225.75,'N','entrega não aprovada');
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (263,258,30,NULL,NULL,'#{execucaoFluxo}.entregaFinalizada();',2,0,788,255,'N','entrega finalizada');

INSERT INTO `templatesolicitacaoservico` (`idtemplate`,`identificacao`,`nometemplate`,`nomeclassedto`,`nomeclasseaction`,`nomeclasseservico`,`urlrecuperacao`,`scriptaposrecuperacao`,`habilitadirecionamento`,`habilitasituacao`,`habilitasolucao`,`alturadiv`,`habilitaurgenciaimpacto`,`habilitanotificacaoemail`,`habilitamudanca`,`habilitaproblema`) VALUES (1,'CriacaoRequisicaoProduto','Requisição Produto','br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO','br.com.centralit.citcorpore.ajaxForms.RequisicaoProduto','br.com.centralit.citcorpore.negocio.RequisicaoProdutoServiceEjb','/pages/requisicaoProduto/requisicaoProduto.load','','N','N','N',600,'N','N','N','N');
INSERT INTO `templatesolicitacaoservico` (`idtemplate`,`identificacao`,`nometemplate`,`nomeclassedto`,`nomeclasseaction`,`nomeclasseservico`,`urlrecuperacao`,`scriptaposrecuperacao`,`habilitadirecionamento`,`habilitasituacao`,`habilitasolucao`,`alturadiv`,`habilitaurgenciaimpacto`,`habilitanotificacaoemail`,`habilitamudanca`,`habilitaproblema`) VALUES (2,'ValidacaoRequisicaoProduto','Validação Requisição Produto','br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO','br.com.centralit.citcorpore.ajaxForms.ValidacaoRequisicaoProduto','br.com.centralit.citcorpore.negocio.RequisicaoProdutoServiceEjb','/pages/validacaoRequisicaoProduto/validacaoRequisicaoProduto.load','','N','N','N',650,'N','N','N','N');
INSERT INTO `templatesolicitacaoservico` (`idtemplate`,`identificacao`,`nometemplate`,`nomeclassedto`,`nomeclasseaction`,`nomeclasseservico`,`urlrecuperacao`,`scriptaposrecuperacao`,`habilitadirecionamento`,`habilitasituacao`,`habilitasolucao`,`alturadiv`,`habilitaurgenciaimpacto`,`habilitanotificacaoemail`,`habilitamudanca`,`habilitaproblema`) VALUES (3,'AutorizacaoCotacao','Autorização Cotação','br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO','br.com.centralit.citcorpore.ajaxForms.AutorizacaoCotacao','br.com.centralit.citcorpore.negocio.RequisicaoProdutoServiceEjb','/pages/autorizacaoCotacao/autorizacaoCotacao.load','','N','N','N',650,'N','N','N','N');
INSERT INTO `templatesolicitacaoservico` (`idtemplate`,`identificacao`,`nometemplate`,`nomeclassedto`,`nomeclasseaction`,`nomeclasseservico`,`urlrecuperacao`,`scriptaposrecuperacao`,`habilitadirecionamento`,`habilitasituacao`,`habilitasolucao`,`alturadiv`,`habilitaurgenciaimpacto`,`habilitanotificacaoemail`,`habilitamudanca`,`habilitaproblema`) VALUES (4,'AcompanhamentoRequisicaoProduto','Acompanhamento Requisição Produto','br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO','br.com.centralit.citcorpore.ajaxForms.AcompRequisicaoProduto','br.com.centralit.citcorpore.negocio.RequisicaoProdutoServiceEjb','/pages/acompRequisicaoProduto/acompRequisicaoProduto.load','','N','S','N',650,'N','N','N','N');
INSERT INTO `templatesolicitacaoservico` (`idtemplate`,`identificacao`,`nometemplate`,`nomeclassedto`,`nomeclasseaction`,`nomeclasseservico`,`urlrecuperacao`,`scriptaposrecuperacao`,`habilitadirecionamento`,`habilitasituacao`,`habilitasolucao`,`alturadiv`,`habilitaurgenciaimpacto`,`habilitanotificacaoemail`,`habilitamudanca`,`habilitaproblema`) VALUES (5,'AprovacaoCotacao','Aprovação da Cotação','br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO','br.com.centralit.citcorpore.ajaxForms.AprovacaoCotacao','br.com.centralit.citcorpore.negocio.RequisicaoProdutoServiceEjb','/pages/aprovacaoCotacao/aprovacaoCotacao.load','','N','N','N',650,'N','N','N','N');
INSERT INTO `templatesolicitacaoservico` (`idtemplate`,`identificacao`,`nometemplate`,`nomeclassedto`,`nomeclasseaction`,`nomeclasseservico`,`urlrecuperacao`,`scriptaposrecuperacao`,`habilitadirecionamento`,`habilitasituacao`,`habilitasolucao`,`alturadiv`,`habilitaurgenciaimpacto`,`habilitanotificacaoemail`,`habilitamudanca`,`habilitaproblema`) VALUES (7,'AlteracaoRequisicaoProduto','Alteração da Requisição','br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO','br.com.centralit.citcorpore.ajaxForms.RequisicaoProduto','br.com.centralit.citcorpore.negocio.RequisicaoProdutoServiceEjb','/pages/requisicaoProduto/requisicaoProduto.load',NULL,'N','S','N',650,'N','N','N','N');
INSERT INTO `templatesolicitacaoservico` (`idtemplate`,`identificacao`,`nometemplate`,`nomeclassedto`,`nomeclasseaction`,`nomeclasseservico`,`urlrecuperacao`,`scriptaposrecuperacao`,`habilitadirecionamento`,`habilitasituacao`,`habilitasolucao`,`alturadiv`,`habilitaurgenciaimpacto`,`habilitanotificacaoemail`,`habilitamudanca`,`habilitaproblema`) VALUES (8,'InspecaoEntregaItem','Inspeção de entrega da requisiçao','br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO','br.com.centralit.citcorpore.ajaxForms.InspecaoEntregaItem','br.com.centralit.citcorpore.negocio.RequisicaoProdutoServiceEjb','/pages/inspecaoEntregaItem/inspecaoEntregaItem.load','','N','N','N',650,'N','N','N','N');
INSERT INTO `templatesolicitacaoservico` (`idtemplate`,`identificacao`,`nometemplate`,`nomeclassedto`,`nomeclasseaction`,`nomeclasseservico`,`urlrecuperacao`,`scriptaposrecuperacao`,`habilitadirecionamento`,`habilitasituacao`,`habilitasolucao`,`alturadiv`,`habilitaurgenciaimpacto`,`habilitanotificacaoemail`,`habilitamudanca`,`habilitaproblema`) VALUES (9,'AcionamentoGarantia','Acionamento da garantia','br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO','br.com.centralit.citcorpore.ajaxForms.AcionamentoGarantia','br.com.centralit.citcorpore.negocio.RequisicaoProdutoServiceEjb','/pages/acionamentoGarantia/acionamentoGarantia.load','','N','N','N',650,'N','N','N','N');

update bpm_fluxo set datafim = datainicio where idtipofluxo = 4;
INSERT INTO `bpm_fluxo` (`idfluxo`,`versao`,`idtipofluxo`,`variaveis`,`conteudoxml`,`datainicio`,`datafim`) VALUES (25,'3.0',4,'requisicaoMudanca;requisicaoMudanca.status;requisicaoMudanca.nomeGrupoAtual;requisicaoMudanca.grupoNivel1',NULL,'2013-01-09',NULL);

INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (184,25,'Inicio','','','','','','','','','','','','','',94,56,32,32,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (185,25,'Tarefa','','Avaliar','Avaliar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao&fase=Avaliacao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',703,168,65,140,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (186,25,'Tarefa','','Aprovar','Aprovar','U','/pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Aprovacao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',40,168,65,140,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (187,25,'Tarefa','','Planejar','Planejar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Planejamento','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',255,168,65,140,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (188,25,'Tarefa','','Executar','Executar','U','pages/requisicaoMudanca/requisicaoMudanca.load?alterarSituacao=N&fase=Execucao','','#{requisicaoMudanca.nomeGrupoAtual}','','','','','','',482,168,65,140,'','',NULL,'','');
INSERT INTO `bpm_elementofluxo` (`idelemento`,`idfluxo`,`tipoelemento`,`subtipo`,`nome`,`documentacao`,`tipointeracao`,`url`,`visao`,`grupos`,`usuarios`,`acaoentrada`,`acaosaida`,`script`,`textoemail`,`nomefluxoencadeado`,`posx`,`posy`,`altura`,`largura`,`modeloemail`,`template`,`intervalo`,`condicaodisparo`,`multiplasinstancias`) VALUES (189,25,'Finalizacao','','','','','','','','','','','','','',918,184,32,32,'','',NULL,'','');

INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (184,186,25,NULL,NULL,'',2,0,110,128,'N',NULL);
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (185,189,25,NULL,NULL,'',1,3,880.5,200.25,'N',NULL);
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (186,187,25,NULL,NULL,'',1,3,217.5,200.5,'N',NULL);
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (187,188,25,NULL,NULL,'',1,3,438.5,200.5,'N',NULL);
INSERT INTO `bpm_sequenciafluxo` (`idelementoorigem`,`idelementodestino`,`idfluxo`,`nomeclasseorigem`,`nomeclassedestino`,`condicao`,`idconexaoorigem`,`idconexaodestino`,`bordax`,`borday`,`posicaoalterada`,`nome`) VALUES (188,185,25,NULL,NULL,'',1,3,662.5,200.5,'N',NULL);

ALTER TABLE `limitealcada` CHANGE COLUMN `limitevaloritem` `limitevaloritem` DECIMAL(11,2) NULL  , CHANGE COLUMN `limitevalormensal` `limitevalormensal` DECIMAL(11,2) NULL DEFAULT NULL  ;

ALTER TABLE historicoic CHANGE COLUMN historicoVersao historicoVersao DOUBLE(10,1) NULL DEFAULT NULL;

ALTER TABLE `slarequisitosla` CHANGE COLUMN `datavinculacao` `datavinculacao` DATE NOT NULL;
ALTER TABLE `slarequisitosla` DROP FOREIGN KEY `fk_reference_554`, DROP FOREIGN KEY `fk_reference_553`;
ALTER TABLE `slarequisitosla` DROP PRIMARY KEY;
ALTER TABLE `slarequisitosla` ADD COLUMN `idslarequisitosla` INT(11) NOT NULL  FIRST, ADD PRIMARY KEY (`idslarequisitosla`);
ALTER TABLE `slarequisitosla` ADD CONSTRAINT `fk_reference_553`      FOREIGN KEY (`idrequisitosla` ) REFERENCES `requisitosla` (`idrequisitosla` )
ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_reference_554` FOREIGN KEY (`idacordonivelservico`) REFERENCES `acordonivelservico` (`idacordonivelservico` )
ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD INDEX `fk_reference_553_idx` (`idrequisitosla` ASC), ADD INDEX `fk_reference_554_idx` (`idacordonivelservico` ASC);

set sql_safe_updates = 1;