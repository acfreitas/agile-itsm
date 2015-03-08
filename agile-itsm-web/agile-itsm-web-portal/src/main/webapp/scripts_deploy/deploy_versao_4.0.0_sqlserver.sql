-- inicio - Bruno César (22/09/2014)
alter table endereco add latitude numeric(17, 15);
alter table endereco add longitude numeric(18, 15);
alter table endereco add latitude_radians numeric(17, 15);
alter table endereco add longitude_radians numeric(17, 15);

CREATE TRIGGER endereco_coordinates
ON endereco FOR INSERT, UPDATE
AS
BEGIN
  UPDATE T
  SET latitude_radians = radians(I.latitude), longitude_radians = radians(I.longitude)
  FROM
    INSERTED I
  INNER JOIN endereco T ON T.idendereco = I.idendereco
END;

create index endereco_coordinates_ix ON endereco (latitude_radians, longitude_radians);

alter table dicionario alter column valor varchar(500);
-- fim - Bruno César (22/09/2014)

-- inicio - Bruno César (03/10/2014)
create table motivonegacaocheckin (
	idmotivo int not null,
	descricao varchar(100) not null,
	datafim date,
	constraint MotivoNegacao_PK primary key (idmotivo)
);
-- fim - Bruno César (03/10/2014)

-- inicio - Bruno César (06/10/2014)
create table posicionamentoatendente (
	id bigint not null,
	idusuario int not null references usuario(idusuario),
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datetime datetime2 not null,
	datealtertime datetime2 not null default getdate(),
	constraint PosicionamentoAtendente_PK primary key (id)
);
-- fim - Bruno César (06/10/2014)

-- inicio - Bruno César (08/10/2014)
alter table solicitacaoservico add latitude numeric(17, 15);
alter table solicitacaoservico add longitude numeric(18, 15);
-- fim - Bruno César (08/10/2014)

-- inicio - Bruno César (09/10/2014)
create table atribuicaosolicitacao (
	id bigint not null,
	idsolicitacao bigint not null references solicitacaoservico(idsolicitacaoservico),
	idusuario int not null references usuario(idusuario),
	priorityorder int,
	latitude numeric(17, 15) null,
	longitude numeric(18, 15) null,
	dataexecucao date null,
	datainicioatendimento datetime2 null,
	active tinyint not null default 1,
	constraint AtribuicaoSolicitacao_PK primary key (id)
);
-- fim - Bruno César (09/10/2014)

-- inicio - Maycon Fernandes (29/10/2014)
create table checkin (
	idcheckin bigint not null,
	idsolicitacao bigint not null references solicitacaoservico(idsolicitacaoservico),
	idtarefa bigint not null references bpm_itemtrabalhofluxo(iditemtrabalho),
	idusuario int not null references usuario(idusuario),
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datahoracheckin datetime2 not null default getdate(),
	constraint Checkin_PK primary key (idcheckin)
);
-- fim - Maycon Fernandes (29/10/2014)

-- inicio - Maycon Fernandes (04/11/2014)
create table checkout (
	idcheckout bigint not null,
	idsolicitacao bigint not null references solicitacaoservico(idsolicitacaoservico),
	idtarefa bigint not null references bpm_itemtrabalhofluxo(iditemtrabalho),
	idusuario int not null references usuario(idusuario),
	status bigint not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datahoracheckout datetime2 not null default getdate(),
	constraint Checkout_PK primary key (idcheckout)
);

create table checkindenied (
	idcheckindenied bigint not null,
	idtarefa bigint not null references bpm_itemtrabalhofluxo(iditemtrabalho),
	idusuario int not null references usuario(idusuario),
	idjustificativa bigint not null references motivonegacaocheckin(idmotivo),
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datahora datetime2 not null default getdate(),
	constraint CheckinDenied_PK primary key (idcheckindenied)
);
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
	id bigint not null,
	idusuario int not null references usuario(idusuario),
	token varchar(300) not null,
	connection varchar(100) not null,
	active tinyint not null default 1,
	deviceplatform tinyint not null,
	datetime datetime2 not null default getdate(),
	constraint AssociacaoDeviceAtendente_PK primary key (id)
);

create index associacao_device_ix ON associacaodeviceatendente (token, connection, idusuario, active);
-- fim - Bruno César (15/11/2014)

-- inicio - Bruno César (28/11/2014)
create table posatendentehistorico (
	id bigint not null,
	idusuario int not null references usuario(idusuario),
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datetime datetime2 not null,
	constraint PosAtendenteHistorico_PK primary key (id)
);

create index posatendente_historico_date_ix ON posatendentehistorico (datetime);

CREATE TRIGGER populate_posatendentehistorico
ON posicionamentoatendente AFTER INSERT
AS
DECLARE @id                      bigint; 
DECLARE @idusuario               int; 
DECLARE @latitude                numeric(17, 15); 
DECLARE @longitude               numeric(18, 15); 
DECLARE @datetime                datetime2; 

DECLARE @distance                numeric(10,  9); 
DECLARE @earth_radius            numeric( 6,  2); 
DECLARE @radians_distance        numeric(17, 15); 

DECLARE @max_latitude            numeric(17, 15); 
DECLARE @min_latitude            numeric(17, 15); 
DECLARE @max_longitude           numeric(17, 15); 
DECLARE @min_longitude           numeric(17, 15); 

DECLARE @radians_latitude        numeric(17, 15); 
DECLARE @radians_longitude       numeric(17, 15); 

DECLARE @delta_longitude         numeric(17, 15); 

DECLARE @acos_value              numeric(16, 15); 
DECLARE @same_position           bit; 

DECLARE @last_latitude           numeric(17, 15); 
DECLARE @last_longitude          numeric(18, 15); 

DECLARE @radians_last_latitude   numeric(17, 15); 
DECLARE @radians_last_longitude  numeric(17, 15); 

DECLARE @min_latitude_find       numeric(17, 15); 
DECLARE @max_latitude_find       numeric(17, 15); 
DECLARE @min_longitude_find      numeric(17, 15); 
DECLARE @max_longitude_find      numeric(17, 15); 
BEGIN
	SET @id                   = (SELECT id FROM inserted); 
	SET @idusuario            = (SELECT idusuario FROM inserted); 
	SET @latitude             = (SELECT latitude FROM inserted); 
	SET @longitude            = (SELECT longitude FROM inserted); 
	SET @datetime             = (SELECT datetime FROM inserted); 

	SET @same_position        = 0; 
	SET @distance             = 0.000016; 
	SET @earth_radius         = 6371.01; 
	SET @radians_distance     = @distance / @earth_radius; 

	SET @min_latitude         = radians( -90); 
	SET @max_latitude         = radians(  90); 
	SET @min_longitude        = radians(-180); 
	SET @max_longitude        = radians( 180); 

	SET @radians_latitude     = radians(@latitude); 
	SET @radians_longitude    = radians(@longitude); 

	SET @min_latitude_find    = @radians_latitude - @radians_distance; 
	SET @max_latitude_find    = @radians_latitude + @radians_distance; 

	IF (@min_latitude_find > @min_latitude AND @max_latitude_find < @max_latitude)
		BEGIN
			SET @delta_longitude = asin(sin(@radians_distance) / cos(@radians_latitude)); 
			SET @min_longitude_find = @radians_longitude - @delta_longitude; 
			IF (@min_longitude_find < @min_latitude)
				BEGIN
					SET @min_longitude_find = @min_longitude_find + (2 * pi()); 
				END

			SET @max_longitude_find = @radians_longitude + @delta_longitude; 
			IF (@max_longitude_find > @max_latitude)
				BEGIN
					SET @max_longitude_find = @max_longitude_find - (2 * pi()); 
				END
		END
	ELSE
		BEGIN
			IF (@min_latitude_find > @min_latitude)
				BEGIN
					SET @min_latitude_find = @min_latitude_find; 
				END
			ELSE
				BEGIN
					SET @min_latitude_find = @min_latitude; 
				END

			IF (@max_latitude_find < @max_latitude)
				BEGIN
					SET @max_latitude_find = @max_latitude_find; 
				END
			ELSE
				BEGIN
					SET @max_latitude_find = @max_latitude; 
				END

			SET @min_longitude_find = @min_longitude; 
			SET @max_longitude_find = @max_longitude; 
		END

	SELECT TOP 1 @last_latitude = phis.latitude,
		   @last_longitude = phis.longitude
	FROM   posatendentehistorico phis
	WHERE  phis.idusuario = @idusuario
	ORDER  BY phis.datetime DESC, phis.id DESC; 

	SET @radians_last_latitude  = radians(@last_latitude); 
	SET @radians_last_longitude = radians(@last_longitude); 

	SET @acos_value = sin(@radians_latitude) * sin(@radians_last_latitude) + cos(@radians_latitude) * cos(@radians_last_latitude) * cos(@radians_last_longitude - (@radians_longitude)); 

	IF (@acos_value > 1.0)
		BEGIN
			SET @acos_value = 1.0; 
		END
	ELSE IF (@acos_value < -1.0)
		BEGIN
			SET @acos_value = -1.0; 
		END

	SELECT @same_position = 1 WHERE (@radians_last_latitude >= @min_latitude_find AND @radians_last_latitude <= @max_latitude_find)
			AND (@radians_last_longitude >= @min_longitude_find OR @radians_last_longitude <= @max_longitude_find)
			AND acos(@acos_value) <= @distance; 

	IF (@same_position = 0 OR (@last_latitude IS NULL AND @last_longitude IS NULL))
		BEGIN
			PRINT N'Including new position for user with id ' + CAST(@idusuario as varchar) + ' (lat=' + CAST(@latitude as varchar) + '|lng=' + CAST(@longitude as varchar) + ')'; 
			INSERT INTO posatendentehistorico VALUES(@id, @idusuario, @latitude, @longitude, @datetime); 
		END
	ELSE
		BEGIN
			PRINT N'User with id ' + CAST(@idusuario as varchar) + ' has a posistion closest to the last and will not be included again. (lat=' + CAST(@latitude as varchar) + '|lng=' + CAST(@longitude as varchar) + ')'; 
		END
END;
-- fim - Bruno César (28/11/2014)

-- inicio - Bruno César (08/12/2014)
create table historicopushmessage (
	id bigint not null,
	idusuario int not null references usuario(idusuario),
	message text not null,
	datetime datetime2 not null default getdate(),
	constraint HistoricoPushMessage_PK primary key (id)
);
-- fim - Bruno César (08/12/2014)

-- inicio - Ezequiel (02/01/2014)
update modelosemails set 
texto = '&nbsp;Informamos ao grupo executor que foi registrada uma ocorr&ecirc;ncia para a solicita&ccedil;&atilde;o de n&uacute;mero ${IDSOLICITACAOSERVICO} conforme os dados abaixo:<div>&nbsp;</div><div>&nbsp;</div><div>Data/hora: ${DATAHORA}</div><div>Registrado por: ${REGISTRADOPOR},</div><div>Categoria: ${CATEGORIA}</div><div>Origem: ${ORIGEM}</div><div>Ocorr&ecirc;ncia: ${OCORRENCIAS}</div><div>Informa&ccedil;&otilde;es do Contato: ${INFORMACOESCONTATO}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:${DESCRICAO}</div><div>&nbsp;</div><div>Atenciosamente,</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div><div>&nbsp;</div><div>&quot;Essa conta de e-mail &eacute; usada apenas para notifica&ccedil;&atilde;o, favor n&atilde;o responder. D&uacute;vidas, entrar em contato com o canal de atendimento.&quot;</div>'
where identificador = 'regOcorrenciaPortal';
-- fim - Ezequiel (02/01/2014)

-- inicio - Bruno César (06/01/2015)
create function dbo.remove_acento (@original varchar(255))
returns varchar(255) as
begin
	declare @replaced varchar(255) = @original Collate SQL_Latin1_General_Cp1251_CS_AS; 
	return @replaced; 
end;
-- fim - Bruno César (06/01/2015)

-- INICIO - CARLOS ALBERTO DOS SANTOS - 15/12/2014

CREATE INDEX index_parametrosempresa
   ON parametros (idempresa);

INSERT INTO parametros (modulo, idempresa, nomeparametro, valor) VALUES ('COMPRAS', 1, 'TRATA_EXPIRACAO', 'N');

ALTER TABLE requisicaoproduto       ADD exigenovaaprovacao      CHAR(1) DEFAULT 'N';
ALTER TABLE requisicaoproduto       ADD itemalterado            CHAR(1) DEFAULT 'N';
ALTER TABLE processonegocio         ADD atribuicaoprimeironivel CHAR(1) DEFAULT 'N';
ALTER TABLE historicoitemrequisicao ADD atributosanteriores     CLOB;
ALTER TABLE historicoitemrequisicao ADD atributosatuais         CLOB;

-- FIM - CARLOS ALBERTO DOS SANTOS - 15/01/2015

-- INICIO - OPERAÇÃO USAIN BOLT - 30/01/2015

CREATE INDEX idx_situacao ON bpm_itemtrabalhofluxo (situacao);
CREATE INDEX idx_idtabela ON controleged (idtabela, id);
CREATE INDEX idx_tipo ON bpm_atribuicaofluxo (tipo);

-- FIM - OPERAÇÃO USAIN BOLT - 30/01/2015

-- INICIO - THYEN CHANG - 06/02/2015

ALTER TABLE processonegocio 		ADD alcadaprimeironivel CHAR(1) DEFAULT 'N';
UPDATE processonegocio 				SET alcadaprimeironivel = atribuicaoprimeironivel;

declare @table_name nvarchar(256)
declare @col_name nvarchar(256)
declare @Command  nvarchar(1000)

set @table_name = N'processonegocio'
set @col_name = N'atribuicaoprimeironivel'

select @Command = 'ALTER TABLE ' + @table_name + ' drop constraint ' + d.name
 from sys.tables t   
  join    sys.default_constraints d       
   on d.parent_object_id = t.object_id  
  join    sys.columns c      
   on c.object_id = t.object_id      
    and c.column_id = d.parent_column_id
 where t.name = @table_name
  and c.name = @col_name

execute (@Command);
ALTER TABLE processonegocio 		DROP COLUMN atribuicaoprimeironivel;

-- FIM - THYEN CHANG - 06/02/2015

-- INICIO - THYEN CHANG - 06/02/2015

ALTER TABLE processonegocio 		ADD alcadaprimeironivel CHAR(1) DEFAULT 'N';
UPDATE processonegocio 				SET alcadaprimeironivel = atribuicaoprimeironivel;

declare @table_name nvarchar(256)
declare @col_name nvarchar(256)
declare @Command  nvarchar(1000)

set @table_name = N'processonegocio'
set @col_name = N'atribuicaoprimeironivel'

select @Command = 'ALTER TABLE ' + @table_name + ' drop constraint ' + d.name
 from sys.tables t   
  join    sys.default_constraints d       
   on d.parent_object_id = t.object_id  
  join    sys.columns c      
   on c.object_id = t.object_id      
    and c.column_id = d.parent_column_id
 where t.name = @table_name
  and c.name = @col_name

execute (@Command);
ALTER TABLE processonegocio 		DROP COLUMN atribuicaoprimeironivel;

-- FIM - THYEN CHANG - 06/02/2015
