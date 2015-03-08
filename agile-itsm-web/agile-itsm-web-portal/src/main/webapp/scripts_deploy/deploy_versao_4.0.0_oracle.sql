-- inicio - Bruno CÈsar (22/09/2014)
CREATE OR REPLACE FUNCTION radians(degree IN NUMBER)
    RETURN NUMBER DETERMINISTIC IS
BEGIN
    RETURN degree / 57.2957795; 
END radians;

alter table endereco add latitude numeric(17, 15);
alter table endereco add longitude numeric(18, 15);

create index endereco_coordinates_ix ON endereco (radians(latitude), radians(longitude));

alter table dicionario modify(valor varchar2(500));
-- fim - Bruno CÈsar (22/09/2014)

-- inicio - Bruno CÈsar (03/10/2014)
create table motivonegacaocheckin (
	idmotivo number(11, 0) not null,
	descricao varchar2(100) not null,
	datafim date
);

alter table motivonegacaocheckin add constraint pk_motivonegacaocheckin primary key (idmotivo);
-- fim - Bruno CÈsar (03/10/2014)

-- inicio - Bruno CÈsar (06/10/2014)
create table posicionamentoatendente (
	id number(24, 0) not null,
	idusuario number(10, 0) not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datetime timestamp not null,
	datealtertime timestamp default current_timestamp not null
);

alter table posicionamentoatendente add constraint pk_posicionamentoatendente primary key (id);
alter table posicionamentoatendente add constraint fk_posicionamento_usuario foreign key (idusuario) references usuario (idusuario);
-- fim - Bruno CÈsar (06/10/2014)

-- inicio - Bruno CÈsar (08/10/2014)
alter table solicitacaoservico add latitude numeric(17, 15);
alter table solicitacaoservico add longitude numeric(18, 15);
-- fim - Bruno CÈsar (08/10/2014)

-- inicio - Bruno CÈsar (09/10/2014)
create table atribuicaosolicitacao (
	id number(24, 0) not null,
	idsolicitacao number(24, 0) not null,
	idusuario number(10, 0) not null,
	priorityorder number(10, 0) null,
	latitude numeric(17, 15) null,
	longitude numeric(18, 15) null,
	dataexecucao date null,
	datainicioatendimento timestamp null,
	active number(1, 0) default 1 not null
);

alter table atribuicaosolicitacao add constraint pk_atribsolicit primary key (id);
alter table atribuicaosolicitacao add constraint fk_atribsolicit_usuario foreign key (idusuario) references usuario (idusuario);
alter table atribuicaosolicitacao add constraint fk_atribsolicit_solicitacao foreign key (idsolicitacao) references solicitacaoservico (idsolicitacaoservico);
-- fim - Bruno CÈsar (09/10/2014)

-- inicio - Maycon Fernandes (29/10/2014)
create table checkin (
	idcheckin number(24, 0) not null,
	idsolicitacao number(24, 0) not null,
	idtarefa number(24, 0) not null,
	idusuario number(24, 0) not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datahoracheckin timestamp default current_timestamp not null
);

alter table checkin add constraint pk_checkin primary key (idcheckin);
alter table checkin add constraint fk_checkin_solicitacao foreign key (idsolicitacao) references solicitacaoservico (idsolicitacaoservico);
alter table checkin add constraint fk_checkin_bpmitemtrabalho foreign key (idtarefa) references bpm_itemtrabalhofluxo (iditemtrabalho);
alter table checkin add constraint fk_checkin_usuario foreign key (idusuario) references usuario (idusuario);
-- fim - Maycon Fernandes (29/10/2014)

-- inicio - Maycon Fernandes (04/11/2014)
create table checkout (
	idcheckout number(24, 0) not null,
	idsolicitacao number(24, 0) not null,
	idtarefa number(24, 0) not null,
	idusuario number(24, 0) not null,
	status number(24, 0) not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datahoracheckout timestamp default current_timestamp not null
);

alter table checkout add constraint pk_checkout primary key (idcheckout);
alter table checkout add constraint fk_checkout_solicitacao foreign key (idsolicitacao) references solicitacaoservico (idsolicitacaoservico);
alter table checkout add constraint fk_checkout_bpmitemtrabalho foreign key (idtarefa) references bpm_itemtrabalhofluxo (iditemtrabalho);
alter table checkout add constraint fk_checkout_usuario foreign key (idusuario) references usuario (idusuario);

create table checkindenied (
	idcheckindenied number(24, 0) not null,
	idtarefa number(24, 0) not null,
	idusuario number(24, 0) not null,
	idjustificativa number(24, 0) not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datahora timestamp default current_timestamp not null
);

alter table checkindenied add constraint pk_checkindenied primary key (idcheckindenied);
alter table checkindenied add constraint fk_checkindenied_bpm foreign key (idtarefa) references bpm_itemtrabalhofluxo (iditemtrabalho);
alter table checkindenied add constraint fk_checkindenied_usuario foreign key (idusuario) references usuario (idusuario);
alter table checkindenied add constraint fk_checkindenied_motivo foreign key (idjustificativa) references motivonegacaocheckin (idmotivo);
-- fim - Maycon Fernandes (04/11/2014)

-- inicio - Maycon Fernandes (31/10/2014)
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_01,'service_coordinates','Recebe a coordenada da unidade check -in Unidade','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_02,'service_listContracts','Retornar lista de  Contratos ativos do Citsmart','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_03,'service_listDeniedReasons','Lista de Justificativa','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_04,'service_listSolicitationStatus','Lista status da solicitaÁ„o de serviÁo','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_05,'service_listUnits','Lista de unidade de um contrato','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_06,'notification_attendantLocation','LocalizaÁ„o de um atendente','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_07,'notification_getNewest','Lista de solicitaÁ„o novas ','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_08,'notification_getOldest','Lista de solicitaÁıes destinadas ao usu·ria novas','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_09,'notification_checkin','Check - in da solicitaÁ„o  ','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_10,'notification_checkinDenied','NegaÁ„o de Check - in de uma sugest„o de solicitaÁ„o de serviÁo','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_11,'notification_checkout','Check - out, finalizaÁ„o de uma solicitaÁ„o de serviÁo','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_12,'notification_getByCoordinates','Lista de solicitaÁ„o novas - Coordenadas','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','N');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_13,'notification_attendRequest','Atender SolicitaÁ„o','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_14,'service_deviceDisassociate','Desassociar device do Atendente','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTOperation','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_15,'notification_updateNotification','Restaura tarefas e permissıes de uma solicitaÁ„o de serviÁo','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','Y');
INSERT INTO rest_operation (idrestoperation, name, description, operationtype, classtype, javaclass, status, generatelog) VALUES ($id_idrestoperation_16,'notification_getById_v2','Retorna detalhes de uma notificaÁ„o para Mobile','Sync','Java','br.com.centralit.citsmart.rest.v2.operation.RESTMobile','A','N');
-- fim - Maycon Fernandes (31/10/2014)

-- inicio - Bruno CÈsar (15/11/2014)
create table associacaodeviceatendente (
	id number(24, 0) not null,
	idusuario number(24, 0) not null,
	token varchar2(300) not null,
	connection varchar2(100) not null,
	active number(1, 0) default 1 not null,
	deviceplatform number(1, 0) not null,
	datetime timestamp default current_timestamp not null
);

alter table associacaodeviceatendente add constraint pk_associacaodevice primary key (id);
alter table associacaodeviceatendente add constraint fk_associacaodevice_usuario foreign key (idusuario) references usuario (idusuario);

CREATE INDEX associacao_device_ix ON associacaodeviceatendente (token, connection, idusuario, active);
-- fim - Bruno CÈsar (15/11/2014)

-- inicio - Bruno CÈsar (28/11/2014)
create table posatendentehistorico (
	id number(24, 0) not null,
	idusuario number(10, 0) not null,
	latitude numeric(17, 15) not null,
	longitude numeric(18, 15) not null,
	datetime timestamp not null
);

alter table posatendentehistorico add constraint pk_posatendentehistorico primary key (id);
alter table posatendentehistorico add constraint fk_posatendente_usuario foreign key (idusuario) references usuario (idusuario);

create index posatendente_historico_date_ix ON posatendentehistorico (datetime);

CREATE OR REPLACE TRIGGER populate_posatendentehistorico
	AFTER INSERT
	ON posicionamentoatendente
	FOR EACH ROW
DECLARE
	pi                      numeric(17, 15); 
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
	pi                      := asin(1) * 2; 
	distance                := 0.000016; 
	earth_radius            := 6371.01; 
	radians_distance        := distance / earth_radius; 

	min_latitude            := radians( -90); 
	max_latitude            := radians(  90); 
	min_longitude           := radians(-180); 
	max_longitude           := radians( 180); 

	radians_latitude        := radians(:NEW.latitude); 
	radians_longitude       := radians(:NEW.longitude); 

	min_latitude_find       := radians_latitude - radians_distance; 
	max_latitude_find       := radians_latitude + radians_distance; 

	IF (min_latitude_find > min_latitude AND max_latitude_find < max_latitude) THEN
		delta_longitude := asin(sin(radians_distance) / cos(radians_latitude)); 
		min_longitude_find := radians_longitude - delta_longitude; 
		IF (min_longitude_find < min_latitude) THEN
			min_longitude_find := min_longitude_find + (2 * pi); 
		END IF; 
		max_longitude_find := radians_longitude + delta_longitude; 
		IF (max_longitude_find > max_latitude) THEN
			max_longitude_find := max_longitude_find - (2 * pi); 
		END IF; 
	ELSE
		min_latitude_find  := greatest(min_latitude_find, min_latitude); 
		max_latitude_find  := least(max_latitude_find, max_latitude); 
		min_longitude_find := min_longitude; 
		max_longitude_find := max_longitude; 
	END IF; 

	SELECT phis.latitude,
		   phis.longitude
	INTO last_latitude, last_longitude
	FROM   posatendentehistorico phis
	WHERE  phis.idusuario = :NEW.idusuario AND ROWNUM = 1
	ORDER  BY phis.datetime DESC, phis.id DESC; 

	radians_last_latitude  := radians(last_latitude); 
	radians_last_longitude := radians(last_longitude); 

	acos_value := sin(radians_latitude) * sin(radians_last_latitude) + cos(radians_latitude) * cos(radians_last_latitude) * cos(radians_last_longitude - (radians_longitude)); 

	IF (acos_value > 1.0) THEN
		acos_value := 1.0; 
	ELSIF (acos_value < -1.0) THEN
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

	IF (same_position = FALSE) THEN
		DBMS_OUTPUT.PUT_LINE('Including new position for user with id ' || :NEW.idusuario || ' (lat=' || :NEW.latitude || '|lng=' || :NEW.longitude || ')'); 
		INSERT INTO posatendentehistorico VALUES(:NEW.id, :NEW.idusuario, :NEW.latitude, :NEW.longitude, :NEW.datetime); 
	ELSE
		DBMS_OUTPUT.PUT_LINE('User with id ' || :NEW.idusuario || ' has a posistion closest to the last and will not be included again. (lat=' || :NEW.latitude || '|lng=' || :NEW.longitude || ')'); 
	END IF; 

	EXCEPTION
	WHEN NO_DATA_FOUND THEN
		DBMS_OUTPUT.PUT_LINE('Including new position for user with id ' || :NEW.idusuario || ' (lat=' || :NEW.latitude || '|lng=' || :NEW.longitude || ')'); 
		INSERT INTO posatendentehistorico VALUES(:NEW.id, :NEW.idusuario, :NEW.latitude, :NEW.longitude, :NEW.datetime); 

	WHEN OTHERS THEN
		NULL; 
END;
-- fim - Bruno CÈsar (28/11/2014)

-- inicio - Bruno CÈsar (08/12/2014)
create table historicopushmessage (
	id number(24, 0) not null,
	idusuario number(10, 0) not null,
	message clob not null,
	datetime timestamp default current_timestamp not null
);

alter table historicopushmessage add constraint pk_historicopush primary key (id);
alter table historicopushmessage add constraint fk_historicopush_usuario foreign key (idusuario) references usuario (idusuario);
-- fim - Bruno CÈsar (08/12/2014)

-- inicio - Ezequiel (02/01/2014)
update modelosemails set 
texto = '&nbsp;Informamos ao grupo executor que foi registrada uma ocorr&ecirc;ncia para a solicita&ccedil;&atilde;o de n&uacute;mero ${IDSOLICITACAOSERVICO} conforme os dados abaixo:<div>&nbsp;</div><div>&nbsp;</div><div>Data/hora: ${DATAHORA}</div><div>Registrado por: ${REGISTRADOPOR},</div><div>Categoria: ${CATEGORIA}</div><div>Origem: ${ORIGEM}</div><div>Ocorr&ecirc;ncia: ${OCORRENCIAS}</div><div>Informa&ccedil;&otilde;es do Contato: ${INFORMACOESCONTATO}</div><div>&nbsp;</div><div>Descri&ccedil;&atilde;o:${DESCRICAO}</div><div>&nbsp;</div><div>Atenciosamente,</div><div>&nbsp;</div><div>Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.</div><div>&nbsp;</div><div>&quot;Essa conta de e-mail &eacute; usada apenas para notifica&ccedil;&atilde;o, favor n&atilde;o responder. D&uacute;vidas, entrar em contato com o canal de atendimento.&quot;</div>'
where identificador = 'regOcorrenciaPortal';
-- fim - Ezequiel (02/01/2014)

-- inicio - Bruno CÈsar (06/01/2015)
CREATE or REPLACE FUNCTION remove_acento(descricao IN VARCHAR2)
  RETURN varchar2 IS
  descricao_translated varchar2(255); 
BEGIN
  SELECT translate(descricao,
                   '·‡„‚‰¡¿√¬ƒÈËÍÎ…» ÀÌÏÓÔÕÃŒœÛÚıÙˆ”“’‘÷˙˘˚¸⁄Ÿ€‹Ò—Á«ˇ˝›',
                   'aaaaaAAAAAeeeeEEEEiiiiIIIIoooooOOOOOuuuuUUUUnNcCyyY')
    INTO descricao_translated
    FROM DUAL; 
  RETURN descricao_translated; 
END;
-- fim - Bruno CÈsar (06/01/2015)

-- INICIO - CARLOS ALBERTO DOS SANTOS - 15/12/2014

CREATE TABLE parametros (
  nomeparametro varchar2(70) NOT NULL,
  idempresa number(11) NOT NULL,
  modulo varchar2(200) DEFAULT NULL,
  valor varchar2(200) DEFAULT NULL,
  detalhamento varchar2(200) DEFAULT NULL
);

CREATE INDEX index_parametrosempresa
   ON parametros (idempresa);

INSERT INTO parametros (modulo, idempresa, nomeparametro, valor) VALUES ('COMPRAS', 1, 'TRATA_EXPIRACAO', 'N');

ALTER TABLE requisicaoproduto       ADD exigenovaaprovacao      CHAR(1) DEFAULT 'N';
ALTER TABLE requisicaoproduto       ADD itemalterado            CHAR(1) DEFAULT 'N';
ALTER TABLE processonegocio         ADD atribuicaoprimeironivel CHAR(1) DEFAULT 'N';
ALTER TABLE historicoitemrequisicao ADD atributosanteriores     CLOB;
ALTER TABLE historicoitemrequisicao ADD atributosatuais         CLOB;

-- FIM - CARLOS ALBERTO DOS SANTOS - 15/01/2015

-- INICIO - OPERA«√O USAIN BOLT - 30/01/2015

CREATE INDEX idx_situacao ON bpm_itemtrabalhofluxo (situacao);
CREATE INDEX idx_idtabela ON controleged (idtabela, id);
CREATE INDEX idx_tipo ON bpm_atribuicaofluxo (tipo);

-- FIM - OPERA«√O USAIN BOLT - 30/01/2015

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