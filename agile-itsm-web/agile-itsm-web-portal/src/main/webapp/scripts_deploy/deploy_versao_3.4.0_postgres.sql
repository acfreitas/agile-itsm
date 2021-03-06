
-- INICIO - Bruno Aquino - 11/04/2014


CREATE TABLE rh_perspectivacomportamentalfuncao (
idperspectivacomportamental int NOT NULL,
descricaoperspectivacomportamental varchar(200) DEFAULT NULL,
detalhePerspectivaComportamental varchar(500) DEFAULT NULL,
idsolicitacaoservico int DEFAULT NULL,
PRIMARY KEY (idperspectivacomportamental)
ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE rh_perspectivacomplexidade (
idperspectivacomplexidade int NOT NULL,
descricaoperspectivacomplexidade varchar(200) DEFAULT NULL,
nivelperspectivacomplexidade int DEFAULT NULL,
idsolicitacaoservico int DEFAULT NULL,
PRIMARY KEY (idperspectivacomplexidade)
ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE rh_perspectivatecnicaformacaoacademica (
idperspectivatecnicaformacaoacademica int NOT NULL,
descricaoformacaoacademica varchar(200) DEFAULT NULL,
detalheformacaoacademica varchar(500) DEFAULT NULL,
obrigatorioformacaoacademica varchar(1) DEFAULT NULL,
idsolicitacaoservico int DEFAULT NULL,
PRIMARY KEY (idperspectivatecnicaformacaoacademica)
ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE rh_perspectivatecnicacertificacao (
idperspectivatecnicacertificacao int NOT NULL,
descricaocertificacao varchar(200) DEFAULT NULL,
versaocertificacao varchar(500) DEFAULT NULL,
obrigatoriocertificacao varchar(1) DEFAULT NULL,
idsolicitacaoservico int DEFAULT NULL,
PRIMARY KEY (idperspectivatecnicacertificacao)
ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE rh_perspectivatecnicacurso (
idperspectivatecnicacurso int NOT NULL,
descricaocurso varchar(200) DEFAULT NULL,
detalhecurso varchar(500) DEFAULT NULL,
obrigatoriocurso varchar(1) DEFAULT NULL,
idsolicitacaoservico int DEFAULT NULL,
PRIMARY KEY (idperspectivatecnicacurso)
ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE rh_perspectivatecnicaidioma (
idperspectivatecnicaidioma int NOT NULL,
descricaoidioma varchar(200) DEFAULT NULL,
detalheidioma varchar(500) DEFAULT NULL,
obrigatorioidioma varchar(1) DEFAULT NULL,
idsolicitacaoservico int DEFAULT NULL,
PRIMARY KEY (idperspectivatecnicaidioma)
ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE rh_perspectivatecnicaexperiencia (
idperspectivatecnicaexperiencia int NOT NULL,
descricaoexperiencia varchar(200) DEFAULT NULL,
detalheexperiencia varchar(500) DEFAULT NULL,
obrigatorioexperiencia varchar(1) DEFAULT NULL,
idsolicitacaoservico int DEFAULT NULL,
PRIMARY KEY (idperspectivatecnicaexperiencia)
ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE  TABLE justificativarequisicaofuncao (
idjustificativa INT NOT NULL ,
descricao VARCHAR(100) NULL ,
situacao VARCHAR(1) NULL ,
PRIMARY KEY (idjustificativa)
);

CREATE  TABLE rh_atribuicao(
idatribuicao INT NOT NULL ,
descricao VARCHAR(100) NULL ,
detalhe VARCHAR(500) NULL ,
datainicio DATE NULL ,
datafim DATE NULL ,
PRIMARY KEY (idatribuicao)
);

-- FIM -Bruno Aquino

-- INICIO GILBERTO TAVARES DE FRANCO NERY (08/04/2010)

CREATE  TABLE integranteviagem (
idintegranteviagem integer NOT NULL ,
idsolicitacaoservico bigint NOT NULL ,
idempregado integer NULL ,
idrespprestacaocontas integer NULL,
integrantefuncionario VARCHAR(1) NULL ,
nomenaofuncionario VARCHAR(255) NULL ,
PRIMARY KEY (idintegranteviagem)
);

ALTER TABLE integranteviagem ADD CONSTRAINT rel_integranteviagem_solserv FOREIGN KEY ( idsolicitacaoservico ) REFERENCES solicitacaoservico ( idsolicitacaoservico )  ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE integranteviagem ADD CONSTRAINT rel_integranteviagem_empregado FOREIGN KEY ( idempregado ) REFERENCES empregados ( idempregado )  ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE itemcontrolefinanceiroviagem ADD nomenaofuncionario varchar(255) NULL;

alter table adiantamentoviagem ADD integrantefuncionario varchar(1) NULL;
alter table adiantamentoviagem ADD nomenaofuncionario varchar(255) NULL;

alter table prestacaocontasviagem ADD integrantefuncionario varchar(1) NULL;
alter table prestacaocontasviagem ADD nomenaofuncionario varchar(255) NULL;

alter table requisicaoviagem ADD remarcacao char(1) NULL;

ALTER TABLE integranteviagem ADD remarcacao VARCHAR(1) NULL;

create table histitemcontrolefinanceiroviagem (
                idhistitemcontrolefinanceiroviagem bigint not null,
                iditemcontrolefinanceiroviagem bigint not null,
                idcontrolefinanceiroviagem bigint default null,
                idformapagamento int default null,
                idadiantamentoviagem bigint default null,
                idfornecedor bigint default null,
                idjustificativa int default null,
                idsolicitacaoservico bigint default null,
                idempregado int(11) default null,
                idtipomovimfinanceiraviagem int(11) default null,
                complementojustificativa text default null,
                quantidade numeric(8,2) default null,
                valorunitario decimal(8,2) default null,
                valoradiantamento decimal(8,2) default null,
                tipopassagem varchar(20) default null,
                localizador varchar(50) default null,
                assento varchar(20) default null,
                situacao varchar(20) default null,
                datafim DATE NULL DEFAULT NULL,
                prazocotacao DATE NULL DEFAULT NULL,
                observacao TEXT NULL,
                dataexecucao TIMESTAMP NULL DEFAULT NULL,
                datahoraprazocotacao TIMESTAMP NULL DEFAULT NULL,
                dataalteracao TIMESTAMP NULL,
                idintegranteviagem int(11),
                idusuarioalteracao int(11),
                nomenaofuncionario varchar(255) NULL

);

alter table "itemcontrolefinanceiroviagem"
   drop constraint fk_itemcontrolefinaceiroviagem_controlefinanceiroviagem;

ALTER TABLE itemcontrolefinanceiroviagem DROP INDEX fk_itemcontrolefinaceiroviagem_fornecedor ;

alter table rh_funcionario ADD idempregado integer NULL;

alter table rh_cargahoraria drop column entrada;
alter table rh_cargahoraria drop column saida;

alter table rh_cargahoraria ADD entrada integer NULL DEFAULT NULL;
alter table rh_cargahoraria ADD saida integer NULL DEFAULT NULL;

insert into importardados (idimportardados,idexternalconnection,importarpor,tipo,nome,script,agendarrotina,executarpor,horaexecucao,periodohora,datafim,tabelaorigem,tabeladestino,jsonmatriz)
values ('1', '1', 'S', 'J', 'importar funcionario - rh_funcionario', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\nvar stmt2 = conn.createStatement();\n \nvar sql = \"select idfuncionario from rh_funcionario where idfuncionario >= ?\";\nvar sql_consulta_empregado;\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar funcs = jdbcEngine.execSQL(sql, objs, 0);\nvar emps;\n \nvar rs;\nvar rs2;\nvar meta;\nvar aux;\nvar idFuncionario = 1;\nvar idEmpregado;\n\nvar str = \"\"; \nvar res = \"\";\n\nvar auxEmp; \n\n//Valida se a tabela esta vazia\nif(funcs == null || funcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select distinct (CPF) CPF, NOMEFUNC FROM [MGE_CENTRALIT].[sankhya].[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTDEM is null order by NOMEFUNC\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idFuncionario;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		objs.push(dataAtual);\n\n		str = objs[2]; \n		\n		//Remove os espa�os em branco\n		res = str.trim();\n		res = res.replace(\" \", \"\");\n		\n		res = res.toLowerCase();\n	\n		sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n		sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), '' '', '''')) like ''\" + res + \"'' order by usr.idempregado limit 1;\"\n		\n		emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n		if(emps != null && !emps.isEmpty()){\n		\n			var auxEmp = emps.get(0);\n			\n			idEmpregado = Number(auxEmp[0]);\n			\n			objs[4] = idEmpregado;\n\n			sql = \"insert into rh_funcionario (idfuncionario, cpf, nome, datainicio, datafim, idempregado) values (?,?,?,?, null,?) \";\n	 \n			//Inserir registro\n			jdbcEngine.execUpdate(sql, objs);\n			\n		}\n\n 		idFuncionario += 1;\n \n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n	\n 	sql = \"select NOMEFUNC, DTDEM, CPF FROM [MGE_CENTRALIT].[sankhya].[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTALTER >= ''\";\n 	sql = sql + dataAtualFormatada + \"'' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_funcionario set nome = ?, datafim = ? where cpf = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 2){\n \n 				if(rs.getObject(i) == null || rs.getObject(i).equals(\"\"))\n 					objs[i-1] = null;\n 				else \n 					objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n \n 			}else\n 				objs[i-1] = rs.getObject(i);\n \n 		}\n		\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso n�o encontrou o registro)\n		// Se n�o eencontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n			str = objs[0]; \n			\n			//Remove os espa�os em branco\n			res = str.trim();\n			res = res.replace(\" \", \"\");\n			\n			res = res.toLowerCase();\n		\n			sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n			sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), '' '', '''')) like ''\" + res + \"'' order by usr.idempregado limit 1; \"\n			\n			emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n			if(emps != null && !emps.isEmpty()){\n		\n				var auxEmp = emps.get(0);\n				\n				idEmpregado = Number(auxEmp[0]);\n				\n				objs2 =  new Array();\n	 \n				sql = \"select idfuncionario from rh_funcionario order by idfuncionario desc limit 1\";\n	 \n				funcs = jdbcEngine.execSQL(sql, objs2, 0);\n	 \n				var aux = funcs.get(0);\n				\n				idFuncionario = Number(aux[0]);\n				\n				objs2.push(idFuncionario + 1);\n				objs2.push(objs[0]);\n				objs2.push(objs[2]);\n				objs2.push(dataAtual);\n				objs2.push(objs[1]);\n				objs2.push(idEmpregado);\n\n				sql = \"insert into rh_funcionario (idfuncionario, nome, cpf, datainicio, datafim, idempregado) values (?,?,?,?,?,?)\";\n				\n				jdbcEngine.execUpdate(sql, objs2);\n			\n			}\n 			\n 		}\n 	}\n \n }\n \n //Fecha conex�o\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '00:00', NULL, NULL, NULL, NULL, ''),
('2', '1', 'S', 'J', 'Carga horaria', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\n \nvar sql = \"delete from rh_cargahoraria where idcargahoraria >= 0\";\nvar objs =  new Array();\n\njdbcEngine.execUpdate(sql, objs);\n \nvar rs;\nvar meta;\nvar idJCgh = 1;\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select CODCARGAHOR, DIASEM, ENTRADA, SAIDA, DESCANSOSEM, TURNO, DTALTER from \" +  schema + \".[TFPHOR] order by CODCARGAHOR, DIASEM, TURNO\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idJCgh;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		sql = \"insert into rh_cargahoraria (idcargahoraria, codcargahor, diasemana, entrada, saida, descansoem, turno, dataalter) values (?,?,?,?,?,?,?,?) \";\n \n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n 		\n 		idJCgh += 1;\n \n 	}\n  \n //Fecha conex�o\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '00:30', NULL, NULL, NULL, NULL, ''),
('3', '1', 'S', 'J', 'Centro de custo', 'var importNames = JavaImporter();\n\nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n\njava.lang.Class.forName(driver);\n\nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n\nvar stmt = conn.createStatement();\n\nvar sql = \"select idcentroresultado from centroresultado where idcentroresultado >= ?\";\n\nvar objs =  new Array();\nobjs[0]= 1;\n\nvar cencus = jdbcEngine.execSQL(sql, objs, 0);\n\nvar rs;\nvar meta;\nvar aux;\nvar idParc = 1;\nvar situacao;\nvar codFormatado;\nvar codAux;\n\n//Valida se a tabela esta vazia\nif(cencus == null || cencus.isEmpty()){\n\n	//Tabela vazia, popula a tabela com todos os dados\n\n 	sql = \"select DESCRCENCUS, CODCENCUSPAI, ATIVO, CODCENCUS from \" +  schema + \".TSICUS order by CODCENCUS;\";\n\n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n\n 	while(rs.next()) {\n\n 		objs = new Array();\n\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n\n			switch(i) {\n\n				case 1:\n					objs[i-1] = new String(rs.getObject(i).toString()).replace(/^\\s+|\\s+$/g,\"\");\n					break;\n				case 2:\n					if(rs.getObject(i) < 0)\n						objs[i-1] = null;\n					else\n						objs[i-1] = rs.getObject(i);\n\n					break;\n				case 3:\n					situacao = rs.getObject(i);\n\n					if(situacao == new String(''S''))\n						objs[i-1] = ''A''\n					else\n						objs[i-1] = ''I''\n\n					break;\n				case 4:\n					objs[i-1] = rs.getObject(i);\n					codAux = new String (objs[i-1]);\n					\n					if(codAux.length > 1)\n						codFormatado = ''0'' + codAux.substring(0,1) + ''.'' + codAux.substring(1,codAux.length);\n					else\n						codFormatado = ''0'' + codAux.substring(0,1) + ''.000'';\n						\n					objs[i] = codFormatado;\n					break;\n			}\n 		}\n\n 		sql = \"insert into centroresultado (nomecentroresultado, idcentroresultadopai, permiterequisicaoproduto, situacao, idcentroresultado, codigocentroresultado) values (?,?,''N'',?,?,?) \";\n\n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n\n 	}\n\n } else {\n\n	//Tabela ja contem dados, realiza update\n\n 	sql = \"select DESCRCENCUS, CODCENCUSPAI, ATIVO, CODCENCUS from \" +  schema + \".TSICUS order by CODCENCUS;\";\n\n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n\n	objs = new Array();\n\n 	while(rs.next()) {\n\n 		objs = new Array();\n\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n\n 			switch(i) {\n\n				case 1:\n					objs[i-1] = new String(rs.getObject(i).toString()).replace(/^\\s+|\\s+$/g,\"\");\n					break;\n				case 2:\n					if(rs.getObject(i) < 0)\n						objs[i-1] = null;\n					else\n						objs[i-1] = rs.getObject(i);\n\n					break;\n				case 3:\n					situacao = rs.getObject(i);\n\n					if(situacao == ''S'')\n						objs[i-1] = ''A'';\n					else\n						objs[i-1] = ''I''\n\n					break;\n				case 4:\n					codAux = new String(rs.getObject(i));\n					\n					if(codAux.length > 1)\n						codFormatado = ''0'' + codAux.substring(0,1) + ''.'' + codAux.substring(1,codAux.length);\n					else\n						codFormatado = ''0'' + codAux.substring(0,1) + ''.000'';\n						\n					objs[i-1] = codFormatado;\n					objs[i] = rs.getObject(i);\n					break;\n			}\n\n 		}\n		\n		sql = \"update centroresultado set nomecentroresultado = ?, idcentroresultadopai = ?, situacao = ?, codigocentroresultado = ? where idcentroresultado = ?\";\n\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso n�o encontrou o registro)\n		// Se n�o encontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n\n			sql = \"insert into centroresultado (permiterequisicaoproduto, nomecentroresultado, idcentroresultadopai, situacao, codigocentroresultado, idcentroresultado) values (''N'',?,?,?,?,?) \";\n\n 			jdbcEngine.execUpdate(sql, objs);\n\n 		}\n 	}\n\n }\n\n //Fecha conex�o\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '01:00', NULL, NULL, NULL, NULL, ''),
('4', '1', 'S', 'J', 'Departamento', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\nvar stmt2 = conn.createStatement();\n \nvar sql = \"select idfuncionario from rh_funcionario where idfuncionario >= ?\";\nvar sql_consulta_empregado;\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar funcs = jdbcEngine.execSQL(sql, objs, 0);\nvar emps;\n \nvar rs;\nvar rs2;\nvar meta;\nvar aux;\nvar idFuncionario = 1;\nvar idEmpregado;\n\nvar str = \"\"; \nvar res = \"\";\n\nvar auxEmp; \n\n//Valida se a tabela esta vazia\nif(funcs == null || funcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select distinct (CPF) CPF, NOMEFUNC FROM \" +  schema + \".[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTDEM is null order by NOMEFUNC\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idFuncionario;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		objs.push(dataAtual);\n\n		str = objs[2]; \n		\n		//Remove os espa�os em branco\n		res = str.trim();\n		res = res.replace(\" \", \"\");\n		\n		res = res.toLowerCase();\n	\n		sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n		sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), '' '', '''')) like ''\" + res + \"'' order by usr.idempregado limit 1;\"\n		\n		emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n		if(emps != null && !emps.isEmpty()){\n		\n			var auxEmp = emps.get(0);\n			\n			idEmpregado = Number(auxEmp[0]);\n			\n			objs[4] = idEmpregado;\n\n			sql = \"insert into rh_funcionario (idfuncionario, cpf, nome, datainicio, datafim, idempregado) values (?,?,?,?, null,?) \";\n	 \n			//Inserir registro\n			jdbcEngine.execUpdate(sql, objs);\n			\n		}\n\n 		idFuncionario += 1;\n \n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n	\n 	sql = \"select NOMEFUNC, DTDEM, CPF FROM \" +  schema + \".[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTALTER >= ''\";\n 	sql = sql + dataAtualFormatada + \"'' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_funcionario set nome = ?, datafim = ? where cpf = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 2){\n \n 				if(rs.getObject(i) == null || rs.getObject(i).equals(\"\"))\n 					objs[i-1] = null;\n 				else \n 					objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n \n 			}else\n 				objs[i-1] = rs.getObject(i);\n \n 		}\n		\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso n�o encontrou o registro)\n		// Se n�o eencontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n			str = objs[0]; \n			\n			//Remove os espa�os em branco\n			res = str.trim();\n			res = res.replace(\" \", \"\");\n			\n			res = res.toLowerCase();\n		\n			sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n			sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), '' '', '''')) like ''\" + res + \"'' order by usr.idempregado limit 1; \"\n			\n			emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n			if(emps != null && !emps.isEmpty()){\n		\n				var auxEmp = emps.get(0);\n				\n				idEmpregado = Number(auxEmp[0]);\n				\n				objs2 =  new Array();\n	 \n				sql = \"select idfuncionario from rh_funcionario order by idfuncionario desc limit 1\";\n	 \n				funcs = jdbcEngine.execSQL(sql, objs2, 0);\n	 \n				var aux = funcs.get(0);\n				\n				idFuncionario = Number(aux[0]);\n				\n				objs2.push(idFuncionario + 1);\n				objs2.push(objs[0]);\n				objs2.push(objs[2]);\n				objs2.push(dataAtual);\n				objs2.push(objs[1]);\n				objs2.push(idEmpregado);\n\n				sql = \"insert into rh_funcionario (idfuncionario, nome, cpf, datainicio, datafim, idempregado) values (?,?,?,?,?,?)\";\n				\n				jdbcEngine.execUpdate(sql, objs2);\n			\n			}\n 			\n 		}\n 	}\n \n }\n \n //Fecha conex�o\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '01:30', NULL, NULL, NULL, NULL, ''),
('5', '1', 'S', 'J', 'Jornada', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\n \n//Limpa a tabela\nvar sql = \"delete from rh_jornadadetrabalho where idjornada >= 0\";\nvar objs =  new Array(); \n\njdbcEngine.execUpdate(sql, objs);\n \nvar rs;\nvar meta;\nvar idJornada = 1;\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select CODCARGAHOR, DESCRCARGAHOR, ESCALONAR, CONSIDERAFERIADOS from \" +  schema + \".[TFPCGH]\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idJornada;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		sql = \"insert into rh_jornadadetrabalho (idjornada, codcargahor, descricao, escala, considerarferiados) values (?,?,?,?,?) \";\n \n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n 		\n 		idJornada += 1;\n \n 	}\n \n //Fecha conex�o\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '02:00', '20', NULL, NULL, NULL, ''),
('6', '1', 'S', 'J', 'Importar Parceiro', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\n \nvar sql = \"select idparceiro from rh_parceiro where idparceiro >= ?\";\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar parcs = jdbcEngine.execSQL(sql, objs, 0);\n \nvar rs;\nvar meta;\nvar aux;\nvar idParc = 1;\n \n//Valida se a tabela esta vazia\nif(parcs == null || parcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"SELECT CODPARC, NOMEPARC, RAZAOSOCIAL, TIPPESSOA, DTALTER, ATIVO, SITUACAO, FORNECEDOR from \" +  schema + \".TGFPAR WHERE FORNECEDOR = ''S'' order by CODPARC;\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n			\n			if(i == 1)\n				objs[i-1] = rs.getObject(i);\n 			else\n 				objs[i-1] = new String(rs.getObject(i).toString()).replace(/^\\s+|\\s+$/g,\"\");\n 		}\n \n 		sql = \"insert into rh_parceiro (idparceiro, nome, razaosocial, tipopessoa, dataalteracao, ativo, situacao, fornecedor) values (?,?,?,?,?,?,?,?) \";\n \n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n 		\n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n\n 	sql = \"SELECT NOMEPARC, RAZAOSOCIAL, TIPPESSOA, DTALTER, ATIVO, SITUACAO, FORNECEDOR, CODPARC from \" +  schema + \".TGFPAR WHERE FORNECEDOR = ''S'' and DTALTER >= ''\";\n 	sql = sql + dataAtualFormatada + \"'' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_parceiro set nome = ?, razaosocial = ?, tipopessoa = ?, dataalteracao = ?, ativo = ?, situacao = ?, fornecedor = ? where idparceiro = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 8){\n				objs[i-1] = rs.getObject(i);\n			} else if(i == 4){\n				objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n 			}else\n 				objs[i-1] = rs.getObject(i).toString().trim();\n \n 		}\n \n \n		//Executa update(Retorna 1: atualizou o registro ou 0 caso n�o encontrou o registro)\n		// Se n�o encontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n 			sql = \"insert into rh_parceiro (nome, razaosocial, tipopessoa, dataalteracao, ativo, situacao, fornecedor, idparceiro) values (?,?,?,?,?,?,?,?) \";\n 			\n 			jdbcEngine.execUpdate(sql, objs);\n 			\n 		}\n 	}\n \n }\n \n //Fecha conex�o\n rs.close();\n stmt.close();\n conn.close();', 'N', NULL, NULL, NULL, NULL, NULL, NULL, '');

ALTER TABLE integranteviagem ADD iditemtrabalho integer NULL;

delete from importardados where idimportardados = 1;
insert into importardados (idimportardados,idexternalconnection,importarpor,tipo,nome,script,agendarrotina,executarpor,horaexecucao,periodohora,datafim,tabelaorigem,tabeladestino,jsonmatriz)
values ('1', '2', 'S', 'J', 'Popular tabela de rh_funcionario', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\nvar stmt2 = conn.createStatement();\n \nvar sql = \"select idfuncionario from rh_funcionario where idfuncionario >= ?\";\nvar sql_consulta_empregado;\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar funcs = jdbcEngine.execSQL(sql, objs, 0);\nvar emps;\n \nvar rs;\nvar rs2;\nvar meta;\nvar aux;\nvar idFuncionario = 1;\nvar idEmpregado;\n\nvar str = \"\"; \nvar res = \"\";\n\nvar auxEmp; \n\n//Valida se a tabela esta vazia\nif(funcs == null || funcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select distinct (CPF) CPF, NOMEFUNC FROM \" +  schema + \".[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTDEM is null order by NOMEFUNC\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idFuncionario;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		objs.push(dataAtual);\n\n		str = objs[2]; \n		\n		//Remove os espa�os em branco\n		res = str.trim();\n		res = res.replace(\" \", \"\");\n		\n		res = res.toLowerCase();\n	\n		sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n		sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), \' \', \'\')) like \'\" + res + \"\' order by usr.idempregado limit 1;\"\n		\n		emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n		if(emps != null && !emps.isEmpty()){\n		\n			var auxEmp = emps.get(0);\n			\n			idEmpregado = Number(auxEmp[0]);\n			\n			objs[4] = idEmpregado;\n\n			sql = \"insert into rh_funcionario (idfuncionario, cpf, nome, datainicio, datafim, idempregado) values (?,?,?,?, null,?) \";\n	 \n			//Inserir registro\n			jdbcEngine.execUpdate(sql, objs);\n			\n		}\n\n 		idFuncionario += 1;\n \n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n	\n 	sql = \"select NOMEFUNC, DTDEM, CPF FROM \" +  schema + \".[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTALTER >= \'\";\n 	sql = sql + dataAtualFormatada + \"\' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_funcionario set nome = ?, datafim = ? where cpf = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 2){\n \n 				if(rs.getObject(i) == null || rs.getObject(i).equals(\"\"))\n 					objs[i-1] = null;\n 				else \n 					objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n \n 			}else\n 				objs[i-1] = rs.getObject(i);\n \n 		}\n		\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso n�o encontrou o registro)\n		// Se n�o eencontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n			str = objs[0]; \n			\n			//Remove os espa�os em branco\n			res = str.trim();\n			res = res.replace(\" \", \"\");\n			\n			res = res.toLowerCase();\n		\n			sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n			sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), \' \', \'\')) like \'\" + res + \"\' order by usr.idempregado limit 1; \"\n			\n			emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n			if(emps != null && !emps.isEmpty()){\n		\n				var auxEmp = emps.get(0);\n				\n				idEmpregado = Number(auxEmp[0]);\n				\n				objs2 =  new Array();\n	 \n				sql = \"select idfuncionario from rh_funcionario order by idfuncionario desc limit 1\";\n	 \n				funcs = jdbcEngine.execSQL(sql, objs2, 0);\n	 \n				var aux = funcs.get(0);\n				\n				idFuncionario = Number(aux[0]);\n				\n				objs2.push(idFuncionario + 1);\n				objs2.push(objs[0]);\n				objs2.push(objs[2]);\n				objs2.push(dataAtual);\n				objs2.push(objs[1]);\n				objs2.push(idEmpregado);\n\n				sql = \"insert into rh_funcionario (idfuncionario, nome, cpf, datainicio, datafim, idempregado) values (?,?,?,?,?,?)\";\n				\n				jdbcEngine.execUpdate(sql, objs2);\n			\n			}\n 			\n 		}\n 	}\n \n }\n \n //Fecha conex�o\n rs.close();\n stmt.close();\n conn.close();', 'S', 'P', '00:00', '4', NULL, NULL, NULL, '');

-- FIM GILBERTO TAVARES DE FRANCO NERY (08/04/2010)

-- INICIO RODRIGO PECCI ACORSE (11/04/2014)

ALTER TABLE  tipomovimfinanceiraviagem add imagem varchar(255);

-- FIM RODRIGO PECCI ACORSE (11/04/2014)

-- INICIO - THIAGO BORGES DA SILVA - 24/02/2014

CREATE  TABLE rh_candidato (
idcandidato INTEGER NOT NULL ,
nome VARCHAR(150) NOT NULL ,
cpf VARCHAR(14) NOT NULL ,
email VARCHAR(150) NOT NULL ,
situacao CHAR(1) NOT NULL ,
datainicio DATE NOT NULL ,
datafim DATE default null,
senha VARCHAR(300) NOT NULL ,
tipo CHAR(1) NOT NULL ,
PRIMARY KEY (idcandidato)
);

-- FIM - THIAGO BORGES DA SILVA - 24/02/2014

-- INICIO - DAVID RODRIGUES DA SILVA - 24/03/2014

CREATE TABLE  rh_historicofuncional  (
     idhistoricofuncional  INTEGER NOT NULL,
     idcandidato  INTEGER REFERENCES rh_candidato (idcandidato),
     dtcriacao  DATE,
    PRIMARY KEY ( idhistoricofuncional )
);

CREATE TABLE  rh_itemhistoricofuncional (
    iditemhistorico INTEGER NOT NULL,
    idhistoricofuncional INTEGER REFERENCES rh_historicofuncional (idhistoricofuncional),
    titulo  VARCHAR(100) NOT NULL,
    descricao  VARCHAR(500) NOT NULL,
    dtcriacao  DATE,
    idresponsavel INTEGER REFERENCES usuario  ( idusuario ),
    PRIMARY KEY ( iditemhistorico )
);

ALTER TABLE  rh_itemhistoricofuncional  ADD COLUMN  tipo  CHAR(1);

CREATE TABLE  rh_justificativalistanegra  (
    idjustificativa INTEGER NOT NULL,
    justificativa  VARCHAR(100) NOT NULL,
    situacao  CHAR(1) NOT NULL,
    dtcriacao  DATE,
    PRIMARY KEY ( idjustificativa )
);

CREATE TABLE  rh_listanegra  (
    idlistanegra INTEGER NOT NULL,
    idcandidato INTEGER REFERENCES rh_candidato  ( idcandidato ),
    idjustificativa INTEGER REFERENCES  rh_justificativalistanegra  ( idjustificativa ),
    idresponsavel INTEGER REFERENCES usuario  ( idusuario ),
    descricao  VARCHAR(500),
    datainicio  DATE,
    datafim  DATE,
    PRIMARY KEY ( idlistanegra )
);

-- FIM - DAVID RODRIGUES DA SILVA - 24/03/2014

-- INICIO - M�rio Hayasaki J�nior - 11/04/2014

alter table rh_candidato add column autenticado char(1);
alter table rh_candidato add column hashID varchar(255);

-- FIM - M�rio Hayasaki J�nior - 11/04/2014

-- INICIO - RODRIGO PECCI ACORSE - 09/11/2013

ALTER TABLE atividadeperiodica ALTER COLUMN criadopor TYPE varchar(256);
ALTER TABLE atividadeperiodica ALTER COLUMN alteradopor TYPE varchar(256);

-- FIM - RODRIGO PECCI ACORSE - 09/11/2013

-- Inicio David - 14-04-2014

create table rh_complexidade(
    idcomplexidade integer not null,
    nivel integer not null,
    descricao varchar(500) not null,
    situacao char(1) not null,
    primary key(idcomplexidade)
);

insert into rh_complexidade (idcomplexidade, nivel, descricao, situacao) values (1,1,'Baixa','A');
insert into rh_complexidade (idcomplexidade, nivel, descricao, situacao) values (2,2,'Intermedi�ria','A');
insert into rh_complexidade (idcomplexidade, nivel, descricao, situacao) values (3,3,'Mediana','A');
insert into rh_complexidade (idcomplexidade, nivel, descricao, situacao) values (4,4,'Alta','A');
insert into rh_complexidade (idcomplexidade, nivel, descricao, situacao) values (5,5,'Especialista','A');

create table rh_nivelcompetencia(
    idnivelcompetencia integer not null,
    nivel integer not null,
    descricao varchar(500) not null,
    situacao char(1) not null,
    primary key(idnivelcompetencia)
);

insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (1,0,'N�o Tem Conhecimento','A');
insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (2,1,'Tem Conhecimento','A');
insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (3,2,'Tem Conhecimento e Pr�tica em Nivel B�sico','A');
insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (4,3,'Tem Conhecimento e Pr�tica em Nivel Intermedi�rio','A');
insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (5,4,'Tem Conhecimento e Pr�tica em Nivel Avan�ado','A');
insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (6,5,'� Multiplicador','A');

create table rh_descricao_atruibuicaoresponsabilidade(
    iddescricao integer not null,
    descricao varchar(256) not null,
    situacao char(1) not null,
    primary key(iddescricao)
);

insert into rh_descricao_atruibuicaoresponsabilidade(iddescricao,descricao,situacao)
values (1,'Definir a��es administrativas para o gerentes respons�veis','A');
insert into rh_descricao_atruibuicaoresponsabilidade(iddescricao,descricao,situacao)
values
(2,'Explicar as determina��es da diretoria para os gerentes','A');
insert into rh_descricao_atruibuicaoresponsabilidade(iddescricao,descricao,situacao)
values
(3,'Orientar os gerentes nas atividades cotidianas para a execu��o dos processos administrativos','A');
insert into rh_descricao_atruibuicaoresponsabilidade(iddescricao,descricao,situacao)
values
(4,'Representar a �rea administrativa perante a diretoria','A');

create table rh_comportamento(
    idcomportamento integer not null,
    descricao varchar(256) not null,
    situacao char(1) not null,
    primary key(idcomportamento)
);

insert into rh_comportamento (idcomportamento,descricao,situacao) values (1,'Cumpre prazos dos trabalhos acordados, consideradando o impacto das atividades na organiza��o','A');
insert into rh_comportamento (idcomportamento,descricao,situacao) values (2,'Demostra interese em participar das a��es desenvolvida pela empresa','A');

CREATE TABLE rh_manualFuncao (
  idManualFuncao integer NOT NULL,
  tituloCargo varchar(255),
  tituloFuncao varchar(255),
  resumoFuncao varchar(255),
  CBO varchar(255),
  codigo varchar(255),

  idFormacaoRA varchar(255),
  idIdiomaRA varchar(255),
  idNivelEscritaRA varchar(255),
  idNivelLeituraRA varchar(255),
  idNivelConversaRA varchar(255),
  expAnteriorRA varchar(255),

  idFormacaoRF varchar(255),
  idIdiomaRF varchar(255),
  idNivelEscritaRF varchar(255),
  idNivelLeituraRF varchar(255),
  idNivelConversaRF varchar(255),
  expAnteriorRF varchar(255),

  pesoComplexidade varchar(255),
  pesoTecnica varchar(255),
  pesoComportamental varchar(255),
  pesoResultados varchar(255),
  PRIMARY KEY (idManualFuncao)
);

CREATE TABLE rh_atribuicaoresponsabilidade (
  idAtribuicaoResponsabilidade integer NOT NULL,
  descricaoPerspectivaComplexidade varchar(255),
  idNivel integer NOT NULL,
  idManualFuncao integer NOT NULL,
  PRIMARY KEY (idAtribuicaoResponsabilidade)
);

CREATE TABLE rh_perspectivacomportamental (
  idPerspectivaComportamental integer NOT NULL,
  cmbCompetenciaComportamental varchar(255),
  comportamento varchar(255),
  idManualFuncao integer NOT NULL,
  PRIMARY KEY (idPerspectivaComportamental)
);

CREATE TABLE rh_manualCertificacao(
  idManualCertificacao integer NOT NULL,
  descricao varchar(255),
  detalhe varchar(255),
  idManualFuncao integer NOT NULL,
  RAouRF varchar(255),
  PRIMARY KEY (idManualCertificacao)
);

CREATE TABLE rh_manualCurso(
  idManualCurso integer NOT NULL,
  descricao varchar(255),
  detalhe varchar(255),
  idManualFuncao integer NOT NULL,
  RAouRF varchar(255),
  PRIMARY KEY (idManualCurso)
);

CREATE TABLE rh_manualcompetenciatecnica (
  idmanualcompetenciatecnica integer NOT NULL,
  descricao varchar(255),
  situacao varchar(255),
  idManualFuncao integer NOT NULL,
  idNivelCompetenciaAcesso integer,
  idNivelCompetenciaFuncao integer,
  idCompetenciaTecnica integer,
  PRIMARY KEY (idmanualcompetenciatecnica)
);

-- Fim David - 14-04-2014

-- Inicio - DAVID 14/04/2014

ALTER TABLE rh_perspectivacomportamental
ADD CONSTRAINT fk_manualFuncao
  FOREIGN KEY (idManualFuncao)
  REFERENCES rh_manualfuncao (idManualFuncao)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Fim - DAVID 14/04/2014

-- INICIO - CARLOS SANTOS - 14/04/2014

alter table bpm_tipofluxo add idprocessonegocio int;

drop sequence s_logdados;

drop sequence s_opiniao;

drop sequence s_servicos;

create sequence s_logdados;

create sequence s_opiniao;

create sequence s_servicos;

create table delegcentroresultadofluxo (
   iddelegacaocentroresultado int4                 not null,
   idinstanciafluxo     int8                 not null,
   constraint pk_delegcentroresultadofluxo primary key (iddelegacaocentroresultado, idinstanciafluxo)
);

create table delegcentroresultadoprocesso (
   iddelegacaocentroresultado int4                 not null,
   idprocessonegocio    int4                 not null,
   constraint pk_delegcentroresultadoprocess primary key (iddelegacaocentroresultado, idprocessonegocio)
);

create table delegacaocentroresultado (
   iddelegacaocentroresultado int4                 not null,
   idresponsavel        int4                 not null,
   idcentroresultado    int4                 not null,
   idempregado          int4                 not null,
   datainicio           date                 not null,
   datafim              date                 not null,
   abrangencia          char(1)              not null,
   revogada             char(1)              null,
   constraint pk_delegacaocentroresultado primary key (iddelegacaocentroresultado)
);

create table gruponivelautoridade (
   idgrupo              int4                 not null,
   idnivelautoridade    int4                 not null,
   constraint pk_gruponivelautoridade primary key (idgrupo, idnivelautoridade)
);

create table historicorespcentroresultado (
   idhistoricorespcentroresultado int4                 not null,
   idcentroresultado    int4                 not null,
   idresponsavel        int4                 not null,
   datainicio           date                 not null,
   datafim              date                 null,
   constraint pk_historicorespcentroresultad primary key (idhistoricorespcentroresultado)
);

create table limiteaprovacao (
   idlimiteaprovacao    int4                 not null,
   tipolimiteporvalor   char(1)              not null,
   abrangenciacentroresultado char(1)              not null,
   identificacao        varchar(70)          not null,
   constraint pk_limiteaprovacao primary key (idlimiteaprovacao)
);

create table limiteaprovacaoautoridade (
   idnivelautoridade    int4                 not null,
   idlimiteaprovacao    int4                 not null,
   constraint pk_limiteaprovacaoautoridade primary key (idnivelautoridade, idlimiteaprovacao)
);

create table limiteaprovacaoprocesso (
   idlimiteaprovacao    int4                 not null,
   idprocessonegocio    int4                 not null,
   constraint pk_limiteaprovacaoprocesso primary key (idlimiteaprovacao, idprocessonegocio)
);

create table nivelautoridade (
   idnivelautoridade    int4                 not null,
   nomenivelautoridade  varchar(100)         not null,
   hierarquia           int4                 not null,
   situacao             char(1)              not null,
   constraint pk_nivelautoridade primary key (idnivelautoridade)
);

create table processonegocio (
   idprocessonegocio    int4                 not null,
   idgrupoexecutor      int4                 null,
   idgrupoadministrador int4                 null,
   nomeprocessonegocio  varchar(100)         not null,
   permissaosolicitacao char(1)              not null,
   percdispensanovaaprovacao decimal(5,2)         not null,
   permiteaprovacaonivelinferior char(1)              not null,
   constraint pk_processonegocio primary key (idprocessonegocio)
);

comment on column processonegocio.permissaosolicitacao is
'T - Todos
A - Por nivel de autoridade';

create table processonivelautoridade (
   idprocessonegocio    int4                 not null,
   idnivelautoridade    int4                 not null,
   permiteaprovacaopropria char(1)              not null,
   permitesolicitacao   char(1)              not null,
   antecedenciaminimaaprovacao int4                 not null,
   constraint pk_processonivelautoridade primary key (idprocessonegocio, idnivelautoridade)
);

create table respcentroresultadoprocesso (
   idresponsavel        int4                 not null,
   idcentroresultado    int4                 not null,
   idprocessonegocio    int4                 not null,
   constraint pk_respcentroresultadoprocesso primary key (idresponsavel, idcentroresultado, idprocessonegocio)
);

create table responsavelcentroresultado (
   idresponsavel        int4                 not null,
   idcentroresultado    int4                 not null,
   constraint pk_responsavelcentroresultado primary key (idresponsavel, idcentroresultado)
);

create table valorlimiteaprovacao (
   idvalorlimiteaprovacao int4                 not null,
   idlimiteaprovacao    int4                 not null,
   tipoutilizacao       char(1)              not null,
   tipolimite           char(1)              not null,
   valorlimite          numeric(10,2)        not null,
   intervalodias        int4                 null,
   constraint pk_valorlimiteaprovacao primary key (idvalorlimiteaprovacao)
);

comment on column valorlimiteaprovacao.tipoutilizacao is
'T - Todos
I - Uso interno
C - Atendimento ao cliente';

comment on column valorlimiteaprovacao.tipolimite is
'I - Individual
M - No mes
A - No ano
D - Intervalo dias';

alter table delegcentroresultadofluxo
   add constraint fk_delegcen_reference_delegaca foreign key (iddelegacaocentroresultado)
      references delegacaocentroresultado (iddelegacaocentroresultado)
      on delete restrict on update restrict;

alter table delegcentroresultadofluxo
   add constraint fk_delegcen_reference_bpm_inst foreign key (idinstanciafluxo)
      references bpm_instanciafluxo (idinstancia)
      on delete restrict on update restrict;

alter table delegcentroresultadoprocesso
   add constraint fk_delegcen_reference_delegaca foreign key (iddelegacaocentroresultado)
      references delegacaocentroresultado (iddelegacaocentroresultado)
      on delete restrict on update restrict;

alter table delegcentroresultadoprocesso
   add constraint fk_delegcen_reference_processo foreign key (idprocessonegocio)
      references processonegocio (idprocessonegocio)
      on delete restrict on update restrict;

alter table delegacaocentroresultado
   add constraint fk_delegaca_reference_responsa foreign key (idresponsavel, idcentroresultado)
      references responsavelcentroresultado (idresponsavel, idcentroresultado)
      on delete restrict on update restrict;

alter table delegacaocentroresultado
   add constraint fk_delegaca_reference_empregad foreign key (idempregado)
      references empregados (idempregado)
      on delete restrict on update restrict;

alter table gruponivelautoridade
   add constraint fk_gruponiv_reference_nivelaut foreign key (idnivelautoridade)
      references nivelautoridade (idnivelautoridade)
      on delete restrict on update restrict;

alter table gruponivelautoridade
   add constraint fk_gruponiv_reference_grupo foreign key (idgrupo)
      references grupo (idgrupo)
      on delete restrict on update restrict;

alter table historicorespcentroresultado
   add constraint fk_historic_reference_centrore foreign key (idcentroresultado)
      references centroresultado (idcentroresultado)
      on delete restrict on update restrict;

alter table historicorespcentroresultado
   add constraint fk_historic_reference_empregad foreign key (idresponsavel)
      references empregados (idempregado)
      on delete restrict on update restrict;

alter table limiteaprovacaoautoridade
   add constraint fk_limiteap_reference_nivelaut foreign key (idnivelautoridade)
      references nivelautoridade (idnivelautoridade)
      on delete restrict on update restrict;

alter table limiteaprovacaoautoridade
   add constraint fk_limiteap_reference_limiteap foreign key (idlimiteaprovacao)
      references limiteaprovacao (idlimiteaprovacao)
      on delete restrict on update restrict;

alter table limiteaprovacaoprocesso
   add constraint fk_limiteap_reference_limiteap foreign key (idlimiteaprovacao)
      references limiteaprovacao (idlimiteaprovacao)
      on delete restrict on update restrict;

alter table limiteaprovacaoprocesso
   add constraint fk_limiteap_reference_processo foreign key (idprocessonegocio)
      references processonegocio (idprocessonegocio)
      on delete restrict on update restrict;

alter table processonegocio
   add constraint fk_processo_reference_grupo foreign key (idgrupoexecutor)
      references grupo (idgrupo)
      on delete restrict on update restrict;

alter table processonegocio
   add constraint fk_processo_reference_grupo foreign key (idgrupoadministrador)
      references grupo (idgrupo)
      on delete restrict on update restrict;

alter table processonivelautoridade
   add constraint fk_processo_reference_processo foreign key (idprocessonegocio)
      references processonegocio (idprocessonegocio)
      on delete restrict on update restrict;

alter table processonivelautoridade
   add constraint fk_processo_reference_nivelaut foreign key (idnivelautoridade)
      references nivelautoridade (idnivelautoridade)
      on delete restrict on update restrict;

alter table respcentroresultadoprocesso
   add constraint fk_respcent_reference_responsa foreign key (idresponsavel, idcentroresultado)
      references responsavelcentroresultado (idresponsavel, idcentroresultado)
      on delete restrict on update restrict;

alter table respcentroresultadoprocesso
   add constraint fk_respcent_reference_processo foreign key (idprocessonegocio)
      references processonegocio (idprocessonegocio)
      on delete restrict on update restrict;

alter table responsavelcentroresultado
   add constraint fk_responsa_reference_empregad foreign key (idresponsavel)
      references empregados (idempregado)
      on delete restrict on update restrict;

alter table responsavelcentroresultado
   add constraint fk_responsa_reference_centrore foreign key (idcentroresultado)
      references centroresultado (idcentroresultado)
      on delete restrict on update restrict;

alter table valorlimiteaprovacao
   add constraint fk_valorlim_reference_limiteap foreign key (idlimiteaprovacao)
      references limiteaprovacao (idlimiteaprovacao)
      on delete restrict on update restrict;

-- FIM - CARLOS SANTOS - 14/04/2014

-- IN�CIO - RODRIGO PECCI ACORSE 17/04/2014

alter table rh_perspectivacomportamental add descricaoperspectivacomportamental text;
alter table rh_perspectivacomportamental add detalheperspectivacomportamental text;

-- FIM - RODRIGO PECCI ACORSE 17/04/2014

--Inicio - euler.ramos 21/04/2014

ALTER TABLE rh_requisicaopessoal ALTER COLUMN idcargo DROP NOT NULL;

-- FIM - euler.ramos 21/04/2014

-- FIM - euler.ramos 21/04/2014

-- INICIO - renato.jesus - 25/04/2014

CREATE TABLE formulaos (
    idformulaos INT,
    descricao VARCHAR (254),
    formula VARCHAR (254),
    situacao CHAR(1) DEFAULT 'A',
    PRIMARY KEY (idformulaos)
);

ALTER TABLE atividadesservicocontrato ADD estruturaformulaos VARCHAR(254);
ALTER TABLE atividadesservicocontrato ADD formulacalculofinal VARCHAR (254);

CREATE TABLE contratoformulaos (
    idcontratoformulaos INT,
    idcontrato INT,
    idformulaos INT,
    deleted char(1) DEFAULT 'N',
    PRIMARY KEY (idcontratoformulaos)
);

-- FIM - renato.jesus - 25/04/2014

-- INICIO - Bruno.aquino - 02/05/2014

INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (1,'Horas * Complexidade * Dias �teis','vValor{horas}*vComplexidade*vDiasUteis','A');
INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (2,'Horas * Complexidade * Dias Corridos','vValor{horas}*vComplexidade*{Periodo}vDiasCorridos','A');
INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (3,'Horas * Complexidade * Quantidade Mensal','vValor{horas}*vComplexidade*{Quantidade}vValor{Periodo}{Mensal}','A');
INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (4,'Horas * Complexidade * Quantidade Semanal','vValor{horas}*vComplexidade*{Quantidade}vValor{Periodo}{Semanal}','A');
INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (5,'Horas * Complexidade * Quantidade Di�rio','vValor{horas}*vComplexidade*{Quantidade}vValor{Periodo}{Di�rio}','A');
INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (6,'(minutos/minutos horas X Complexidade) X NU','(vValor/vValor{Horas}*vComplexidade)*vValor{NU}','A');

INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (1,1,1,'N');
INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (2,1,2,'N');
INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (3,1,3,'N');
INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (4,1,4,'N');
INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (5,1,5,'N');
INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (6,1,6,'N');

-- Fim - Bruno.aquino - 25/04/2014

-- inicio - maycon 09/05/20014

ALTER TABLE controlefinanceiroviagem ADD COLUMN iditemtrabalho int;

ALTER TABLE integranteviagem ADD COLUMN emprestacaocontas varchar(1);

ALTER TABLE requisicaoviagem ADD COLUMN iditemtrabalho  int NULL;

-- fim - maycon 09/05/20014

-- inicio - flavio.santana 15/05/2014

create table bi_dashboard (
   iddashboard          int8                 not null,
   tipo                 char(1)              not null,
   idusuario            int8                 null,
   nomedashboard        varchar(150)         null,
   identificacao        varchar(70)          null,
   situacao             char(1)              null,
   parametros           text                 null,
   naoatualizbase       char(1)              null,
   temporefresh         int8                 null,
   constraint pk_bi_dashboard primary key (iddashboard)
);

create  index ix_ident_dash on bi_dashboard (
    identificacao
);

create table bi_itemdashboard (
   iditemdashboard      int8                 not null,
   titulo               varchar(150)         not null,
   iddashboard          int8                 not null,
   idconsulta           int8                 null,
   posicao              int2                 null,
   itemtop              int4                 null,
   itemleft             int4                 null,
   itemwidth            int4                 null,
   itemheight           int4                 null,
   parmssubst           text                 null,
   constraint pk_bi_itemdashboard primary key (iditemdashboard)
);

create  index ix_id_dash on bi_itemdashboard (
    iddashboard
);

alter table bi_itemdashboard
   add constraint fk_bi_itemd_reference_bi_dashb foreign key (iddashboard)
      references bi_dashboard (iddashboard)
      on delete restrict on update restrict;

alter table bi_itemdashboard
   add constraint fk_bi_itemd_reference_bi_consu foreign key (idconsulta)
      references bi_consulta (idconsulta)
      on delete restrict on update restrict;

create table bi_dashboardsegur (
   iddashboard          int8                 not null,
   idgrupo              int4                 not null,
   constraint pk_bi_dashboardsegur primary key (iddashboard, idgrupo)
);

alter table bi_dashboardsegur
   add constraint fk_bi_dashb_reference_bi_dashb foreign key (iddashboard)
      references bi_dashboard (iddashboard)
      on delete restrict on update restrict;

alter table bi_dashboardsegur
   add constraint fk_bi_dashb_reference_grupo foreign key (idgrupo)
      references grupo (idgrupo)
      on delete restrict on update restrict;

-- fim - flavio.santana