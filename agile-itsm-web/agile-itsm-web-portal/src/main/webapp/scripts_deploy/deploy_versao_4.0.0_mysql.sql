set sql_safe_updates = 0;

-- inicio - Bruno César (22/09/2014)
alter table endereco add latitude numeric(17, 15);
alter table endereco add longitude numeric(18, 15);
alter table endereco add latitude_radians numeric(17, 15);
alter table endereco add longitude_radians numeric(17, 15);

create index endereco_coordinates_ix ON endereco (latitude_radians, longitude_radians) USING BTREE;

CREATE TRIGGER endereco_coordinates_insert BEFORE INSERT ON `endereco`
	FOR EACH ROW
BEGIN
	SET NEW.latitude_radians = radians(NEW.latitude),
		NEW.longitude_radians = radians(NEW.longitude); 
END;

CREATE TRIGGER endereco_coordinates_update BEFORE UPDATE ON `endereco`
	FOR EACH ROW
BEGIN
	SET NEW.latitude_radians = radians(NEW.latitude),
		NEW.longitude_radians = radians(NEW.longitude); 
END;

alter table dicionario modify valor varchar(500);
-- fim - Bruno César (22/09/2014)

-- inicio - Bruno César (03/10/2014)
create table `motivonegacaocheckin` (
	`idmotivo` int(11) not null,
	`descricao` varchar(100) not null,
	`datafim` date,
	primary key (`idmotivo`)
) engine = InnoDB default charset = utf8;
-- fim - Bruno César (03/10/2014)

-- inicio - Bruno César (06/10/2014)
create table `posicionamentoatendente` (
	`id` int(11) not null,
	`idusuario` int(11) not null,
	`latitude` numeric(17, 15) not null,
	`longitude` numeric(18, 15) not null,
	`datetime` timestamp not null,
	`datealtertime` timestamp not null,
	primary key (id),
	constraint `fk_posicionamento_usuario` foreign key `fk_posicionamento_usuario` (`idusuario`) references `usuario` (`idusuario`) on delete no action on update no action
) engine = InnoDB default charset = utf8;
-- fim - Bruno César (06/10/2014)

-- inicio - Bruno César (08/10/2014)
alter table solicitacaoservico add latitude numeric(17, 15);
alter table solicitacaoservico add longitude numeric(18, 15);
-- fim - Bruno César (08/10/2014)

-- inicio - Bruno César (09/10/2014)
create table `atribuicaosolicitacao` ( 
     `id`                    int(11) not null, 
     `idsolicitacao`         bigint(20) not null, 
     `idusuario`             int(11) not null, 
     `priorityorder`         int(11) null, 
     `latitude`              numeric(17, 15) null, 
     `longitude`             numeric(18, 15) null, 
     `dataexecucao`          date null, 
     `datainicioatendimento` timestamp null, 
     `active`                int(1) not null default 1, 
     primary key (id), 
     constraint `fk_atribsolicit_usuario` foreign key `fk_atribsolicit_usuario` (`idusuario`) references `usuario` (`idusuario`) on delete no action on update no action,
     constraint `fk_atribsolicit_solicitacao` foreign key `fk_atribsolicit_solicitacao` (`idsolicitacao`) references `solicitacaoservico` (`idsolicitacaoservico`) on delete no action on update no action 
  ) engine = InnoDB default charset = utf8;
-- fim - Bruno César (09/10/2014)

-- inicio - Maycon Fernandes (29/10/2014)
create table checkin (
	`idcheckin` int(11) not null,
	`idsolicitacao` bigint(20) not null,
	`idtarefa` bigint(20) not null,
	`idusuario` int(11) not null,
	`latitude` numeric(17, 15) not null,
	`longitude` numeric(18, 15) not null,
	`datahoracheckin` timestamp not null default current_timestamp,
	primary key (idcheckin),
	constraint `fk_checkin_usuario` foreign key `fk_checkin_usuario` (`idusuario`) references `usuario` (`idusuario`) on delete no action on update no action,
	constraint `fk_checkin_solicitacao` foreign key `fk_checkin_solicitacao` (`idsolicitacao`) references `solicitacaoservico` (`idsolicitacaoservico`) on delete no action on update no action,
	constraint `fk_checkin_bpmitemtrabalho` foreign key `fk_checkin_bpmitemtrabalho` (idtarefa) references `bpm_itemtrabalhofluxo` (`iditemtrabalho`)
) engine = InnoDB default charset = utf8;
-- fim - Maycon Fernandes (29/10/2014)

-- inicio - Maycon Fernandes (29/10/2014)
create table checkout ( 
     `idcheckout`       int(11) not null, 
     `idsolicitacao`    bigint(20) not null, 
     `idtarefa`         bigint(20) not null, 
     `idusuario`        int(11) not null, 
     `status`           int(11) not null, 
     `latitude`         numeric(17, 15) not null, 
     `longitude`        numeric(18, 15) not null, 
     `datahoracheckout` timestamp not null default current_timestamp, 
     primary key (idcheckout), 
     constraint `fk_checkout_usuario` foreign key `fk_checkout_usuario` (`idusuario`) references `usuario` (`idusuario`) on delete no action on update no action,
     constraint `fk_checkout_solicitacao` foreign key `fk_checkout_solicitacao` (`idsolicitacao`) references `solicitacaoservico` (`idsolicitacaoservico`) on delete no action on update no action,
     constraint `fk_checkout_bpmitemtrabalho` foreign key `fk_checkout_bpmitemtrabalho` (idtarefa) references `bpm_itemtrabalhofluxo` (`iditemtrabalho`)
  ) engine = InnoDB default charset = utf8;
-- fim - Maycon Fernandes (29/10/2014)

-- inicio - Maycon Fernandes (04/11/2014)
create table checkindenied (
	`idcheckindenied` int(11) not null,
	`idtarefa` bigint(20) not null,
	`idusuario` int(11) not null,
	`idjustificativa` int(11) not null,
	`latitude` numeric(17, 15) not null,
	`longitude` numeric(18, 15) not null,
	`datahora` timestamp not null default current_timestamp,
	primary key (`idcheckindenied`),
	constraint `fk_checkindenied_usuario` foreign key `fk_checkindenied_usuario` (`idusuario`) references `usuario` (`idusuario`) on delete no action on update no action,
	constraint `fk_checkindenied_bpmitemtrabalho` foreign key `fk_checkindenied_bpmitemtrabalho` (`idtarefa`) references `bpm_itemtrabalhofluxo` (`iditemtrabalho`),
	constraint `fk_checkindenied_motivo` foreign key `fk_checkindenied_motivo`(`idjustificativa`) references `motivonegacaocheckin` (`idmotivo`)
) engine = InnoDB default charset = utf8;
-- fim - Maycon Fernandes (04/11/2014)

-- inicio - Maycon Fernandes (31/10/2014)
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_01,'service_coordinates','Recebe a coordenada da unidade check -in Unidade','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_02,'service_listContracts','Retornar lista de  Contratos ativos do Citsmart','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_03,'service_listDeniedReasons','Lista de Justificativa','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_04,'service_listSolicitationStatus','Lista status da solicitação de serviço','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_05,'service_listUnits','Lista de unidade de um contrato','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_06,'notification_attendantLocation','Localização de um atendente','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_07,'notification_getNewest','Lista de solicitação novas ','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_08,'notification_getOldest','Lista de solicitações destinadas ao usuária novas','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_09,'notification_checkin','Check - in da solicitação  ','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_10,'notification_checkinDenied','Negação de Check - in de uma sugestão de solicitação de serviço','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_11,'notification_checkout','Check - out, finalização de uma solicitação de serviço','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_12,'notification_getByCoordinates','Lista de solicitação novas - Coordenadas','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_13,'notification_attendRequest','Atender Solicitação','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_14,'service_deviceDisassociate','Desassociar device do Atendente','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_15,'notification_updateNotification','Restaura tarefas e permissões de uma solicitação de serviço','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_16,'notification_getById_v2','Retorna detalhes de uma notificação para Mobile','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','N');
-- fim - Maycon Fernandes (31/10/2014)

-- inicio - Bruno César (15/11/2014)
create table associacaodeviceatendente (
	`id` int(11) not null,
	`idusuario` int(11) not null,
	`token` varchar(300) not null,
	`connection` varchar(100) not null,
	`active` int(1) not null default 1,
	`deviceplatform` int(1) not null,
	`datetime` timestamp not null default current_timestamp,
	primary key (`id`),
	constraint `fk_associacao_usuario` foreign key `fk_associacao_usuario` (`idusuario`) references `usuario` (`idusuario`) on delete no action on update no action
) engine = InnoDB default charset = utf8;

CREATE INDEX associacao_device_ix ON associacaodeviceatendente (token(200), connection, idusuario, active) USING btree;
-- fim - Bruno César (15/11/2014)

-- inicio - Bruno César (28/11/2014)
create table `posatendentehistorico` (
	`id` int(11) not null,
	`idusuario` int(11) not null,
	`latitude` numeric(17, 15) not null,
	`longitude` numeric(18, 15) not null,
	`datetime` timestamp not null,
	primary key (id),
	constraint `fk_posatendente_historico_usuario` foreign key `fk_posicionamento_usuario` (`idusuario`) references `usuario` (`idusuario`) on delete no action on update no action
) engine = InnoDB default charset = utf8;

create index posatendente_historico_date_ix ON posatendentehistorico (datetime) USING BTREE;

CREATE TRIGGER populate_posatendentehistorico AFTER INSERT ON `posicionamentoatendente`
	FOR EACH ROW
BEGIN
	DECLARE distance                numeric(10,  9); 
	DECLARE earth_radius            numeric( 6,  2); 
	DECLARE radians_distance        numeric(17, 15); 

	DECLARE max_latitude            numeric(17, 15); 
	DECLARE min_latitude            numeric(17, 15); 
	DECLARE max_longitude           numeric(17, 15); 
	DECLARE min_longitude           numeric(17, 15); 

	DECLARE radians_latitude        numeric(17, 15); 
	DECLARE radians_longitude       numeric(17, 15); 

	DECLARE delta_longitude         numeric(17, 15); 

	DECLARE acos_value              numeric(16, 15); 
	DECLARE same_position           boolean; 

	DECLARE last_latitude           numeric(17, 15); 
	DECLARE last_longitude          numeric(18, 15); 

	DECLARE radians_last_latitude   numeric(17, 15); 
	DECLARE radians_last_longitude  numeric(17, 15); 

	DECLARE min_latitude_find       numeric(17, 15); 
	DECLARE max_latitude_find       numeric(17, 15); 
	DECLARE min_longitude_find      numeric(17, 15); 
	DECLARE max_longitude_find      numeric(17, 15); 

	DECLARE CONTINUE HANDLER FOR NOT FOUND
	BEGIN
		INSERT INTO posatendentehistorico VALUES(NEW.id, NEW.idusuario, NEW.latitude, NEW.longitude, NEW.datetime); 
	END; 

	SET @distance                   := 0.000016; 
	SET @earth_radius               := 6371.01; 
	SET @radians_distance           := @distance / @earth_radius; 

	SET @min_latitude               := radians( -90); 
	SET @max_latitude               := radians(  90); 
	SET @min_longitude              := radians(-180); 
	SET @max_longitude              := radians( 180); 

	SET @radians_latitude           := radians(NEW.latitude); 
	SET @radians_longitude          := radians(NEW.longitude); 

	SET @min_latitude_find          := @radians_latitude - @radians_distance; 
	SET @max_latitude_find          := @radians_latitude + @radians_distance; 

	IF (@min_latitude_find > @min_latitude AND @max_latitude_find < @max_latitude) THEN
		SET @delta_longitude := asin(sin(@radians_distance) / cos(@radians_latitude)); 
		SET @min_longitude_find := @radians_longitude - @delta_longitude; 
		IF (@min_longitude_find < @min_latitude) THEN
			SET @min_longitude_find := @min_longitude_find + (2 * pi()); 
		END IF; 
		SET @max_longitude_find := @radians_longitude + @delta_longitude; 
		IF (@max_longitude_find > @max_latitude) THEN
			SET @max_longitude_find := @max_longitude_find - (2 * pi()); 
		END IF; 
	ELSE
		SET @min_latitude_find  := greatest(@min_latitude_find, @min_latitude); 
		SET @max_latitude_find  := least(@max_latitude_find, @max_latitude); 
		SET @min_longitude_find := @min_longitude; 
		SET @max_longitude_find := @max_longitude; 
	END IF; 

	SELECT phis.latitude,
		   phis.longitude
	FROM   posatendentehistorico phis
	WHERE  phis.idusuario = NEW.idusuario
	ORDER  BY phis.datetime DESC, phis.id DESC
	LIMIT  1
	INTO last_latitude, last_longitude; 

	SET @radians_last_latitude  := radians(last_latitude); 
	SET @radians_last_longitude := radians(last_longitude); 

	SET @acos_value := sin(@radians_latitude) * sin(@radians_last_latitude) + cos(@radians_latitude) * cos(@radians_last_latitude) * cos(@radians_last_longitude - (@radians_longitude)); 

	IF (@acos_value > 1.0) THEN
		SET @acos_value := 1.0; 
	ELSEIF (@acos_value < -1.0) THEN
		SET @acos_value := -1.0; 
	END IF; 

	SET @same_position := (@radians_last_latitude >= @min_latitude_find
	AND
	@radians_last_latitude <= @max_latitude_find)
	AND
	(
	  @radians_last_longitude >= @min_longitude_find
	  OR
	  @radians_last_longitude <= @max_longitude_find
	)
	AND
	acos(@acos_value) <= @distance; 

	IF (@same_position = 0) THEN
		INSERT INTO posatendentehistorico VALUES(NEW.id, NEW.idusuario, NEW.latitude, NEW.longitude, NEW.datetime); 
	END IF; 
END;
-- fim - Bruno César (28/11/2014)

-- inicio - Bruno César (08/12/2014)
create table historicopushmessage (
	`id` int(11) not null,
	`idusuario` int(11) not null,
	`message` text not null,
	`datetime` timestamp not null default current_timestamp,
	primary key (id),
	constraint `fk_historicopush_usuario` foreign key `fk_historicopush_usuario` (`idusuario`) references `usuario` (`idusuario`) on delete no action on update no action
);
-- fim - Bruno César (08/12/2014)

-- inicio - Ezequiel (02/01/2014)

update modelosemails set 
texto = '&nbsp;Informamos ao grupo executor que foi registrada uma ocorr&ecirc;ncia para a solicita&ccedil;&atilde;o de n&uacute;mero ${IDSOLICITACAOSERVICO} conforme os dados abaixo:<div>&nbsp;</div><div>&nbsp;</div><div>Data/hora: ${DATAHORA}</div><div>Registrado por: ${REGISTRADOPOR},</div><div>Categoria: ${CATEGORIA}</div><div>Origem: ${ORIGEM}</div><div>Ocorr&ecirc;ncia: ${OCORRENCIAS}</div><div>Informa&ccedil;&otilde;es do Contato: ${INFORMACOESCONTATO}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:${DESCRICAO}</div><div>&nbsp;</div><div>Atenciosamente,</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div><div>&nbsp;</div><div>&quot;Essa conta de e-mail &eacute; usada apenas para notifica&ccedil;&atilde;o, favor n&atilde;o responder. D&uacute;vidas, entrar em contato com o canal de atendimento.&quot;</div>'
where identificador = 'regOcorrenciaPortal';

-- fim - Ezequiel (02/01/2014)

-- INICIO - CARLOS ALBERTO DOS SANTOS - 15/12/2014

INSERT INTO parametros (modulo, idempresa, nomeparametro, valor) VALUES ('COMPRAS', 1, 'TRATA_EXPIRACAO', 'N');

ALTER TABLE requisicaoproduto       ADD COLUMN exigenovaaprovacao      CHAR(1) DEFAULT 'N';
ALTER TABLE requisicaoproduto       ADD COLUMN itemalterado            CHAR(1) DEFAULT 'N';
ALTER TABLE processonegocio         ADD COLUMN alcadaprimeironivel     CHAR(1) DEFAULT 'N';
ALTER TABLE historicoitemrequisicao ADD COLUMN atributosanteriores     TEXT;
ALTER TABLE historicoitemrequisicao ADD COLUMN atributosatuais         TEXT;

-- FIM - CARLOS ALBERTO DOS SANTOS - 15/01/2015

-- INICIO - OPERAÇÃO USAIN BOLT - 30/01/2015

CREATE INDEX idx_situacao ON bpm_itemtrabalhofluxo (situacao) USING btree;
CREATE INDEX idx_idtabela ON controleged (idtabela, id) USING btree;
CREATE INDEX idx_tipo ON bpm_atribuicaofluxo (tipo) USING btree;

-- FIM - OPERAÇÃO USAIN BOLT - 30/01/2015

set sql_safe_updates = 1;
