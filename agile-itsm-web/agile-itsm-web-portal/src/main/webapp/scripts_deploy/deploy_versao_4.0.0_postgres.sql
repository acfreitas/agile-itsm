-- inicio - Bruno César (22/09/2014)
alter table endereco add latitude numeric(17, 15);
alter table endereco add longitude numeric(18, 15);

create index endereco_coordinates_ix ON endereco (radians(latitude), radians(longitude));

alter table dicionario alter column valor type character varying(500);
-- fim - Bruno César (22/09/2014)

-- inicio - Bruno César (03/10/2014)
create table motivonegacaocheckin (
	idmotivo integer not null,
	descricao character varying(100) not null,
	datafim date
);

alter table motivonegacaocheckin add constraint pk_motivonegacaocheckin primary key (idmotivo);
-- fim - Bruno César (03/10/2014)

-- inicio - Bruno César (06/10/2014)
create table posicionamentoatendente (
	id bigint not null,
	idusuario integer not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datetime timestamp not null default now(),
	datealtertime timestamp not null default now()
);

alter table posicionamentoatendente add constraint pk_posicionamentoatendente primary key (id);
alter table posicionamentoatendente add constraint fk_posicionamento_usuario foreign key (idusuario) references usuario (idusuario);
-- fim - Bruno César (06/10/2014)

-- inicio - Bruno César (08/10/2014)
alter table solicitacaoservico add latitude numeric(17, 15);
alter table solicitacaoservico add longitude numeric(18, 15);
-- fim - Bruno César (08/10/2014)

-- inicio - Bruno César (09/10/2014)
create table atribuicaosolicitacao (
	id bigint not null,
	idsolicitacao bigint not null,
	idusuario int not null,
	priorityorder int null,
	latitude numeric(17, 15) null,
	longitude numeric(18, 15) null,
	dataexecucao date null,
	datainicioatendimento timestamp null,
	active smallint not null default 1
);

alter table atribuicaosolicitacao add constraint pk_atribsolicit primary key (id);
alter table atribuicaosolicitacao add constraint fk_atribsolicit_usuario foreign key (idusuario) references usuario (idusuario);
alter table atribuicaosolicitacao add constraint fk_atribsolicit_solicitacao foreign key (idsolicitacao) references solicitacaoservico (idsolicitacaoservico);
-- fim - Bruno César (09/10/2014)

-- inicio - Maycon Fernandes (29/10/2014)
create table checkin (
	idcheckin bigint not null,
	idsolicitacao bigint not null,
	idtarefa bigint not null,
	idusuario int not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datahoracheckin timestamp not null default now()
);

alter table checkin add constraint pk_checkin primary key (idcheckin);
alter table checkin add constraint fk_checkin_solicitacao foreign key (idsolicitacao) references solicitacaoservico (idsolicitacaoservico);
alter table checkin add constraint fk_checkin_bpmitemtrabalho foreign key (idtarefa) references bpm_itemtrabalhofluxo (iditemtrabalho);
alter table checkin add constraint fk_checkin_usuario foreign key (idusuario) references usuario (idusuario);
-- fim - Maycon Fernandes (29/10/2014)

-- fim - Maycon Fernandes (03/11/2014)
create table checkout (
	idcheckout bigint not null,
	idsolicitacao bigint not null,
	idtarefa bigint not null,
	idusuario bigint not null,
	status bigint not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datahoracheckout timestamp not null default current_timestamp
);

alter table checkout add constraint pk_checkout primary key (idcheckout);
alter table checkout add constraint fk_checkout_solicitacao foreign key (idsolicitacao) references solicitacaoservico (idsolicitacaoservico);
alter table checkout add constraint fk_checkout_bpmitemtrabalho foreign key (idtarefa) references bpm_itemtrabalhofluxo (iditemtrabalho);
alter table checkout add constraint fk_checkout_usuario foreign key (idusuario) references usuario (idusuario);

create table checkindenied (
	idcheckindenied bigint not null,
	idtarefa bigint not null,
	idusuario bigint not null,
	idjustificativa bigint not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datahora timestamp not null default current_timestamp
);

alter table checkindenied add constraint pk_checkindenied primary key (idcheckindenied);
alter table checkindenied add constraint fk_checkindenied_bpmitemtrabalho foreign key (idtarefa) references bpm_itemtrabalhofluxo (iditemtrabalho);
alter table checkindenied add constraint fk_checkindenied_usuario foreign key (idusuario) references usuario (idusuario);
alter table checkindenied add constraint fk_checkindenied_motivo foreign key (idjustificativa) references motivonegacaocheckin (idmotivo);
-- fim - Maycon Fernandes (03/11/2014)

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
	idusuario int not null,
	token character varying(300) not null,
	connection character varying(100) not null,
	active smallint not null default 1,
	deviceplatform smallint not null,
	datahora timestamp not null default current_timestamp
);

alter table associacaodeviceatendente add constraint pk_associacaodevice primary key (id);
alter table associacaodeviceatendente add constraint fk_associacaodevice_usuario foreign key (idusuario) references usuario (idusuario);

create index associacao_device_ix ON associacaodeviceatendente (token, connection, idusuario, active);
-- fim - Bruno César (15/11/2014)

-- inicio - Bruno César (28/11/2014)
create table posatendentehistorico (
	id bigint not null,
	idusuario integer not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datetime timestamp not null default now()
);

alter table posatendentehistorico add constraint pk_posatendentehistorico primary key (id);
alter table posatendentehistorico add constraint fk_posatendentehistorico_usuario foreign key (idusuario) references usuario (idusuario);

create index posatendente_historico_date_ix ON posatendentehistorico (datetime);

CREATE OR REPLACE FUNCTION generate_historico_posicionamento() RETURNS TRIGGER AS $generate_historico_posicionamento$
	DECLARE
		distance                numeric(10,  9); 
		earth_radius            numeric( 6,  2); 
		radians_distance        numeric(17, 15); 

		max_latitude            numeric(17, 15); 
		min_latitude            numeric(17, 15); 
		max_longitude           numeric(17, 15); 
		min_longitude           numeric(17, 15); 

		radians_latitude        numeric(17, 15); 
		radians_longitude       numeric(17, 15); 

		delta_longitude         numeric(17, 15); 

		acos_value              numeric(16, 15); 
		same_position           boolean; 

		last_latitude           numeric(17, 15); 
		last_longitude          numeric(18, 15); 

		radians_last_latitude   numeric(17, 15); 
		radians_last_longitude  numeric(17, 15); 

		min_latitude_find       numeric(17, 15); 
		max_latitude_find       numeric(17, 15); 
		min_longitude_find      numeric(17, 15); 
		max_longitude_find      numeric(17, 15); 
	BEGIN
		distance                := 0.000016; 
		earth_radius            :=  6371.01; 
		radians_distance        := distance / earth_radius; 

		min_latitude            := radians( -90); 
		max_latitude            := radians(  90); 
		min_longitude           := radians(-180); 
		max_longitude           := radians( 180); 

		radians_latitude        := radians(NEW.latitude); 
		radians_longitude       := radians(NEW.longitude); 

		min_latitude_find       := radians_latitude - radians_distance; 
		max_latitude_find       := radians_latitude + radians_distance; 

		IF (min_latitude_find > min_latitude AND max_latitude_find < max_latitude) THEN
			delta_longitude := asin(sin(radians_distance) / cos(radians_latitude)); 
			min_longitude_find := radians_longitude - delta_longitude; 
			IF (min_longitude_find < min_latitude) THEN
				min_longitude_find := min_longitude_find + (2 * pi()); 
			END IF; 
			max_longitude_find := radians_longitude + delta_longitude; 
			IF (max_longitude_find > max_latitude) THEN
				max_longitude_find := max_longitude_find - (2 * pi()); 
			END IF; 
		ELSE
			min_latitude_find  := greatest(min_latitude_find, min_latitude); 
			max_latitude_find  := least(max_latitude_find, max_latitude); 
			min_longitude_find := min_longitude; 
			max_longitude_find := max_longitude; 
		END IF; 

		SELECT phis.latitude,
			   phis.longitude
		FROM   posatendentehistorico phis
		WHERE  phis.idusuario = NEW.idusuario
		ORDER  BY phis.datetime DESC, phis.id DESC
		LIMIT  1
		INTO last_latitude, last_longitude; 

		radians_last_latitude  := radians(last_latitude); 
		radians_last_longitude := radians(last_longitude); 

		acos_value := sin(radians_latitude) * sin(radians_last_latitude) + cos(radians_latitude) * cos(radians_last_latitude) * cos(radians_last_longitude - (radians_longitude)); 

		IF (acos_value > 1.0) THEN
			acos_value := 1.0; 
		ELSEIF (acos_value < -1.0) THEN
			acos_value := -1.0; 
		END IF; 

		same_position := (radians_last_latitude >= min_latitude_find
		AND
		radians_last_latitude <= max_latitude_find)
		AND
		(
		  radians_last_longitude >= min_longitude_find
		  OR
		  radians_last_longitude <= max_longitude_find
		)
		AND
		acos(acos_value) <= distance; 

		IF (same_position IS FALSE OR (last_latitude IS NULL AND last_longitude IS NULL)) THEN
			RAISE NOTICE 'Including new position for user with id %. (lat=%|lng=%)', NEW.idusuario, NEW.latitude, NEW.longitude; 
			INSERT INTO posatendentehistorico VALUES(NEW.id, NEW.idusuario, NEW.latitude, NEW.longitude, NEW.datetime); 
		ELSE
			RAISE NOTICE 'User with id % has a posistion closest to the last and will not be included again. (lat=%|lng=%)', NEW.idusuario, NEW.latitude, NEW.longitude; 
		END IF; 
		RETURN NEW; 
	END; 
$generate_historico_posicionamento$ LANGUAGE plpgsql;

CREATE TRIGGER populate_posatendentehistorico
	AFTER INSERT
	ON posicionamentoatendente
	FOR EACH ROW
	EXECUTE PROCEDURE generate_historico_posicionamento();
-- fim - Bruno César (28/11/2014)

-- inicio - Bruno César (08/12/2014)
create table historicopushmessage (
	id bigint not null,
	idusuario integer not null,
	message text not null,
	datetime timestamp not null default now()
);

alter table historicopushmessage add constraint pk_historicopush primary key (id);
alter table historicopushmessage add constraint fk_historicopush_usuario foreign key (idusuario) references usuario (idusuario);
-- fim - Bruno César (08/12/2014)


-- inicio - Ezequiel (02/01/2014)

update modelosemails set 
texto = '&nbsp;Informamos ao grupo executor que foi registrada uma ocorr&ecirc;ncia para a solicita&ccedil;&atilde;o de n&uacute;mero ${IDSOLICITACAOSERVICO} conforme os dados abaixo:<div>&nbsp;</div><div>&nbsp;</div><div>Data/hora: ${DATAHORA}</div><div>Registrado por: ${REGISTRADOPOR},</div><div>Categoria: ${CATEGORIA}</div><div>Origem: ${ORIGEM}</div><div>Ocorr&ecirc;ncia: ${OCORRENCIAS}</div><div>Informa&ccedil;&otilde;es do Contato: ${INFORMACOESCONTATO}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:${DESCRICAO}</div><div>&nbsp;</div><div>Atenciosamente,</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div><div>&nbsp;</div><div>&quot;Essa conta de e-mail &eacute; usada apenas para notifica&ccedil;&atilde;o, favor n&atilde;o responder. D&uacute;vidas, entrar em contato com o canal de atendimento.&quot;</div>'
where identificador = 'regOcorrenciaPortal';

-- fim - Ezequiel (02/01/2014)

-- INICIO - CARLOS ALBERTO DOS SANTOS - 15/12/2014

CREATE INDEX index_parametrosempresa
   ON parametros (idempresa);

INSERT INTO parametros (modulo, idempresa, nomeparametro, valor) VALUES ('COMPRAS', 1, 'TRATA_EXPIRACAO', 'N');

ALTER TABLE requisicaoproduto       ADD exigenovaaprovacao      CHAR(1) DEFAULT 'N';
ALTER TABLE requisicaoproduto       ADD itemalterado            CHAR(1) DEFAULT 'N';
ALTER TABLE processonegocio         ADD atribuicaoprimeironivel CHAR(1) DEFAULT 'N';
ALTER TABLE historicoitemrequisicao ADD atributosanteriores     TEXT;
ALTER TABLE historicoitemrequisicao ADD atributosatuais         TEXT;

-- FIM - CARLOS ALBERTO DOS SANTOS - 15/01/2015

-- INICIO - OPERAÇÃO USAIN BOLT - 30/01/2015

CREATE INDEX idx_situacao ON bpm_itemtrabalhofluxo (situacao);
CREATE INDEX idx_idtabela ON controleged (idtabela, id);
CREATE INDEX idx_tipo ON bpm_atribuicaofluxo (tipo);

-- FIM - OPERAÇÃO USAIN BOLT - 30/01/2015

-- INICIO - THYEN CHANG - 06/02/2015

ALTER TABLE processonegocio 		ADD  alcadaprimeironivel CHAR(1) DEFAULT 'N';
UPDATE processonegocio 				SET alcadaprimeironivel = atribuicaoprimeironivel;
ALTER TABLE processonegocio 		DROP COLUMN atribuicaoprimeironivel;

-- FIM - THYEN CHANG - 06/02/2015

-- INICIO - THYEN CHANG - 06/02/2015

ALTER TABLE processonegocio 		ADD  alcadaprimeironivel CHAR(1) DEFAULT 'N';
UPDATE processonegocio 				SET alcadaprimeironivel = atribuicaoprimeironivel;
ALTER TABLE processonegocio 		DROP COLUMN atribuicaoprimeironivel;

-- FIM - THYEN CHANG - 06/02/2015
