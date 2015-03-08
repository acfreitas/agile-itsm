
-- INICIO - CARLOS ALBERTO DOS SANTOS 23/07/2014

alter table rest_operation add generatelog char(1);
update rest_operation set generatelog = 'Y';

-- FIM

-- Inicio MARIO HAYASAKI JUNIOR 19/08/2014
alter table os alter column datafim datetime;
alter table os alter column datainicio datetime;
-- FIM
-- INICIO - DAVID RODRIGUES DA SILVA 19/08/2014
EXEC sp_RENAME 'rh_curriculo.idnaturalidade', 'idnacionalidade', 'COLUMN';
-- FIM

-- INICIO - DAVID RODRIGUES DA SILVA 21/08/2014

ALTER TABLE rh_curriculo ALTER COLUMN idnacionalidade INT NULL; 

ALTER TABLE rh_certificacaocurriculo ALTER COLUMN validade INT NULL; 

ALTER TABLE rh_competencia ADD nivelcompetencia INT NULL; 

ALTER TABLE rh_enderecocurriculo ALTER COLUMN iduf INT NULL;  

-- FIM

-- INICIO - EULER JOSE RAMOS 19/08/2014

CREATE TABLE grupoassinatura (
  idgrupoassinatura integer NOT NULL ,
  titulo VARCHAR(254) NOT NULL ,
  datainicio DATE NOT NULL ,
  datafim DATE NULL ,
  PRIMARY KEY (idgrupoassinatura));

CREATE TABLE assinatura (
  idassinatura integer NOT NULL ,
  idempregado integer NULL ,
  papel VARCHAR(254) NULL ,
  fase VARCHAR(254) NULL ,
  datainicio DATE NOT NULL ,
  datafim DATE NULL ,
  PRIMARY KEY (idassinatura));

CREATE  TABLE itemgrupoassinatura (
  iditemgrupoassinatura integer NOT NULL ,
  idgrupoassinatura integer NOT NULL ,
  idassinatura integer NOT NULL ,
  ordem integer NOT NULL ,
  datainicio DATE NOT NULL ,
  datafim DATE NULL ,
  PRIMARY KEY (iditemgrupoassinatura));
  
alter table os add idgrupoassinatura integer; 

-- FIM-- FIM

-- Inicio MARIO HAYASAKI JUNIOR 28/08/2014
CREATE TABLE imagemitemconfiguracaorelacao (
  idimagemitemconfiguracaorel INT NOT NULL,
  idimagemitemconfiguracao INT NOT NULL,
  idImagemItemConfiguracaoPai INT NOT NULL,
  PRIMARY KEY (idimagemitemconfiguracaorel));
  --Fim
-- INICIO - DAVID RODRIGUES DA SILVA 26/08/2014
-- Mudança necessaria para permitir escolher o id do insert
SET IDENTITY_INSERT dbo.importardados ON;

INSERT INTO importardados (idimportardados,idexternalconnection,importarpor,tipo,nome,script,agendarrotina,executarpor,horaexecucao,periodohora,datafim,tabelaorigem,tabeladestino,jsonmatriz)
VALUES ('1', '1', 'S', 'J', 'importar funcionario - rh_funcionario', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\nvar stmt2 = conn.createStatement();\n \nvar sql = \"select idfuncionario from rh_funcionario where idfuncionario >= ?\";\nvar sql_consulta_empregado;\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar funcs = jdbcEngine.execSQL(sql, objs, 0);\nvar emps;\n \nvar rs;\nvar rs2;\nvar meta;\nvar aux;\nvar idFuncionario = 1;\nvar idEmpregado;\n\nvar str = \"\"; \nvar res = \"\";\n\nvar auxEmp; \n\n//Valida se a tabela esta vazia\nif(funcs == null || funcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select distinct (CPF) CPF, NOMEFUNC FROM [MGE_CENTRALIT].[sankhya].[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTDEM is null order by NOMEFUNC\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idFuncionario;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		objs.push(dataAtual);\n\n		str = objs[2]; \n		\n		//Remove os espaços em branco\n		res = str.trim();\n		res = res.replace(\" \", \"\");\n		\n		res = res.toLowerCase();\n	\n		sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n		sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), '' '', '''')) like ''\" + res + \"'' order by usr.idempregado limit 1;\"\n		\n		emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n		if(emps != null && !emps.isEmpty()){\n		\n			var auxEmp = emps.get(0);\n			\n			idEmpregado = Number(auxEmp[0]);\n			\n			objs[4] = idEmpregado;\n\n			sql = \"insert into rh_funcionario (idfuncionario, cpf, nome, datainicio, datafim, idempregado) values (?,?,?,?, null,?) \";\n	 \n			//Inserir registro\n			jdbcEngine.execUpdate(sql, objs);\n			\n		}\n\n 		idFuncionario += 1;\n \n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n	\n 	sql = \"select NOMEFUNC, DTDEM, CPF FROM [MGE_CENTRALIT].[sankhya].[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTALTER >= ''\";\n 	sql = sql + dataAtualFormatada + \"'' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_funcionario set nome = ?, datafim = ? where cpf = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 2){\n \n 				if(rs.getObject(i) == null || rs.getObject(i).equals(\"\"))\n 					objs[i-1] = null;\n 				else \n 					objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n \n 			}else\n 				objs[i-1] = rs.getObject(i);\n \n 		}\n		\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso não encontrou o registro)\n		// Se não eencontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n			str = objs[0]; \n			\n			//Remove os espaços em branco\n			res = str.trim();\n			res = res.replace(\" \", \"\");\n			\n			res = res.toLowerCase();\n		\n			sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n			sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), '' '', '''')) like ''\" + res + \"'' order by usr.idempregado limit 1; \"\n			\n			emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n			if(emps != null && !emps.isEmpty()){\n		\n				var auxEmp = emps.get(0);\n				\n				idEmpregado = Number(auxEmp[0]);\n				\n				objs2 =  new Array();\n	 \n				sql = \"select idfuncionario from rh_funcionario order by idfuncionario desc limit 1\";\n	 \n				funcs = jdbcEngine.execSQL(sql, objs2, 0);\n	 \n				var aux = funcs.get(0);\n				\n				idFuncionario = Number(aux[0]);\n				\n				objs2.push(idFuncionario + 1);\n				objs2.push(objs[0]);\n				objs2.push(objs[2]);\n				objs2.push(dataAtual);\n				objs2.push(objs[1]);\n				objs2.push(idEmpregado);\n\n				sql = \"insert into rh_funcionario (idfuncionario, nome, cpf, datainicio, datafim, idempregado) values (?,?,?,?,?,?)\";\n				\n				jdbcEngine.execUpdate(sql, objs2);\n			\n			}\n 			\n 		}\n 	}\n \n }\n \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '00:00', NULL, NULL, NULL, NULL, ''),
('2', '1', 'S', 'J', 'Carga horaria', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\n \nvar sql = \"delete from rh_cargahoraria where idcargahoraria >= 0\";\nvar objs =  new Array();\n\njdbcEngine.execUpdate(sql, objs);\n \nvar rs;\nvar meta;\nvar idJCgh = 1;\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select CODCARGAHOR, DIASEM, ENTRADA, SAIDA, DESCANSOSEM, TURNO, DTALTER from \" +  schema + \".[TFPHOR] order by CODCARGAHOR, DIASEM, TURNO\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idJCgh;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		sql = \"insert into rh_cargahoraria (idcargahoraria, codcargahor, diasemana, entrada, saida, descansoem, turno, dataalter) values (?,?,?,?,?,?,?,?) \";\n \n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n 		\n 		idJCgh += 1;\n \n 	}\n  \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '00:30', NULL, NULL, NULL, NULL, ''),
('3', '1', 'S', 'J', 'Centro de custo', 'var importNames = JavaImporter();\n\nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n\njava.lang.Class.forName(driver);\n\nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n\nvar stmt = conn.createStatement();\n\nvar sql = \"select idcentroresultado from centroresultado where idcentroresultado >= ?\";\n\nvar objs =  new Array();\nobjs[0]= 1;\n\nvar cencus = jdbcEngine.execSQL(sql, objs, 0);\n\nvar rs;\nvar meta;\nvar aux;\nvar idParc = 1;\nvar situacao;\nvar codFormatado;\nvar codAux;\n\n//Valida se a tabela esta vazia\nif(cencus == null || cencus.isEmpty()){\n\n	//Tabela vazia, popula a tabela com todos os dados\n\n 	sql = \"select DESCRCENCUS, CODCENCUSPAI, ATIVO, CODCENCUS from \" +  schema + \".TSICUS order by CODCENCUS;\";\n\n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n\n 	while(rs.next()) {\n\n 		objs = new Array();\n\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n\n			switch(i) {\n\n				case 1:\n					objs[i-1] = new String(rs.getObject(i).toString()).replace(/^\\s+|\\s+$/g,\"\");\n					break;\n				case 2:\n					if(rs.getObject(i) < 0)\n						objs[i-1] = null;\n					else\n						objs[i-1] = rs.getObject(i);\n\n					break;\n				case 3:\n					situacao = rs.getObject(i);\n\n					if(situacao == new String(''S''))\n						objs[i-1] = ''A''\n					else\n						objs[i-1] = ''I''\n\n					break;\n				case 4:\n					objs[i-1] = rs.getObject(i);\n					codAux = new String (objs[i-1]);\n					\n					if(codAux.length > 1)\n						codFormatado = ''0'' + codAux.substring(0,1) + ''.'' + codAux.substring(1,codAux.length);\n					else\n						codFormatado = ''0'' + codAux.substring(0,1) + ''.000'';\n						\n					objs[i] = codFormatado;\n					break;\n			}\n 		}\n\n 		sql = \"insert into centroresultado (nomecentroresultado, idcentroresultadopai, permiterequisicaoproduto, situacao, idcentroresultado, codigocentroresultado) values (?,?,''N'',?,?,?) \";\n\n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n\n 	}\n\n } else {\n\n	//Tabela ja contem dados, realiza update\n\n 	sql = \"select DESCRCENCUS, CODCENCUSPAI, ATIVO, CODCENCUS from \" +  schema + \".TSICUS order by CODCENCUS;\";\n\n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n\n	objs = new Array();\n\n 	while(rs.next()) {\n\n 		objs = new Array();\n\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n\n 			switch(i) {\n\n				case 1:\n					objs[i-1] = new String(rs.getObject(i).toString()).replace(/^\\s+|\\s+$/g,\"\");\n					break;\n				case 2:\n					if(rs.getObject(i) < 0)\n						objs[i-1] = null;\n					else\n						objs[i-1] = rs.getObject(i);\n\n					break;\n				case 3:\n					situacao = rs.getObject(i);\n\n					if(situacao == ''S'')\n						objs[i-1] = ''A'';\n					else\n						objs[i-1] = ''I''\n\n					break;\n				case 4:\n					codAux = new String(rs.getObject(i));\n					\n					if(codAux.length > 1)\n						codFormatado = ''0'' + codAux.substring(0,1) + ''.'' + codAux.substring(1,codAux.length);\n					else\n						codFormatado = ''0'' + codAux.substring(0,1) + ''.000'';\n						\n					objs[i-1] = codFormatado;\n					objs[i] = rs.getObject(i);\n					break;\n			}\n\n 		}\n		\n		sql = \"update centroresultado set nomecentroresultado = ?, idcentroresultadopai = ?, situacao = ?, codigocentroresultado = ? where idcentroresultado = ?\";\n\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso não encontrou o registro)\n		// Se não encontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n\n			sql = \"insert into centroresultado (permiterequisicaoproduto, nomecentroresultado, idcentroresultadopai, situacao, codigocentroresultado, idcentroresultado) values (''N'',?,?,?,?,?) \";\n\n 			jdbcEngine.execUpdate(sql, objs);\n\n 		}\n 	}\n\n }\n\n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '01:00', NULL, NULL, NULL, NULL, ''),
('4', '1', 'S', 'J', 'Departamento', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\nvar stmt2 = conn.createStatement();\n \nvar sql = \"select idfuncionario from rh_funcionario where idfuncionario >= ?\";\nvar sql_consulta_empregado;\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar funcs = jdbcEngine.execSQL(sql, objs, 0);\nvar emps;\n \nvar rs;\nvar rs2;\nvar meta;\nvar aux;\nvar idFuncionario = 1;\nvar idEmpregado;\n\nvar str = \"\"; \nvar res = \"\";\n\nvar auxEmp; \n\n//Valida se a tabela esta vazia\nif(funcs == null || funcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select distinct (CPF) CPF, NOMEFUNC FROM \" +  schema + \".[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTDEM is null order by NOMEFUNC\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idFuncionario;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		objs.push(dataAtual);\n\n		str = objs[2]; \n		\n		//Remove os espaços em branco\n		res = str.trim();\n		res = res.replace(\" \", \"\");\n		\n		res = res.toLowerCase();\n	\n		sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n		sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), '' '', '''')) like ''\" + res + \"'' order by usr.idempregado limit 1;\"\n		\n		emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n		if(emps != null && !emps.isEmpty()){\n		\n			var auxEmp = emps.get(0);\n			\n			idEmpregado = Number(auxEmp[0]);\n			\n			objs[4] = idEmpregado;\n\n			sql = \"insert into rh_funcionario (idfuncionario, cpf, nome, datainicio, datafim, idempregado) values (?,?,?,?, null,?) \";\n	 \n			//Inserir registro\n			jdbcEngine.execUpdate(sql, objs);\n			\n		}\n\n 		idFuncionario += 1;\n \n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n	\n 	sql = \"select NOMEFUNC, DTDEM, CPF FROM \" +  schema + \".[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTALTER >= ''\";\n 	sql = sql + dataAtualFormatada + \"'' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_funcionario set nome = ?, datafim = ? where cpf = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 2){\n \n 				if(rs.getObject(i) == null || rs.getObject(i).equals(\"\"))\n 					objs[i-1] = null;\n 				else \n 					objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n \n 			}else\n 				objs[i-1] = rs.getObject(i);\n \n 		}\n		\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso não encontrou o registro)\n		// Se não eencontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n			str = objs[0]; \n			\n			//Remove os espaços em branco\n			res = str.trim();\n			res = res.replace(\" \", \"\");\n			\n			res = res.toLowerCase();\n		\n			sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n			sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), '' '', '''')) like ''\" + res + \"'' order by usr.idempregado limit 1; \"\n			\n			emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n			if(emps != null && !emps.isEmpty()){\n		\n				var auxEmp = emps.get(0);\n				\n				idEmpregado = Number(auxEmp[0]);\n				\n				objs2 =  new Array();\n	 \n				sql = \"select idfuncionario from rh_funcionario order by idfuncionario desc limit 1\";\n	 \n				funcs = jdbcEngine.execSQL(sql, objs2, 0);\n	 \n				var aux = funcs.get(0);\n				\n				idFuncionario = Number(aux[0]);\n				\n				objs2.push(idFuncionario + 1);\n				objs2.push(objs[0]);\n				objs2.push(objs[2]);\n				objs2.push(dataAtual);\n				objs2.push(objs[1]);\n				objs2.push(idEmpregado);\n\n				sql = \"insert into rh_funcionario (idfuncionario, nome, cpf, datainicio, datafim, idempregado) values (?,?,?,?,?,?)\";\n				\n				jdbcEngine.execUpdate(sql, objs2);\n			\n			}\n 			\n 		}\n 	}\n \n }\n \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '01:30', NULL, NULL, NULL, NULL, ''),
('5', '1', 'S', 'J', 'Jornada', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\n \n//Limpa a tabela\nvar sql = \"delete from rh_jornadadetrabalho where idjornada >= 0\";\nvar objs =  new Array(); \n\njdbcEngine.execUpdate(sql, objs);\n \nvar rs;\nvar meta;\nvar idJornada = 1;\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select CODCARGAHOR, DESCRCARGAHOR, ESCALONAR, CONSIDERAFERIADOS from \" +  schema + \".[TFPCGH]\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idJornada;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		sql = \"insert into rh_jornadadetrabalho (idjornada, codcargahor, descricao, escala, considerarferiados) values (?,?,?,?,?) \";\n \n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n 		\n 		idJornada += 1;\n \n 	}\n \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '02:00', '20', NULL, NULL, NULL, ''),
('6', '1', 'S', 'J', 'Importar Parceiro', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\n \nvar sql = \"select idparceiro from rh_parceiro where idparceiro >= ?\";\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar parcs = jdbcEngine.execSQL(sql, objs, 0);\n \nvar rs;\nvar meta;\nvar aux;\nvar idParc = 1;\n \n//Valida se a tabela esta vazia\nif(parcs == null || parcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"SELECT CODPARC, NOMEPARC, RAZAOSOCIAL, TIPPESSOA, DTALTER, ATIVO, SITUACAO, FORNECEDOR from \" +  schema + \".TGFPAR WHERE FORNECEDOR = ''S'' order by CODPARC;\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n			\n			if(i == 1)\n				objs[i-1] = rs.getObject(i);\n 			else\n 				objs[i-1] = new String(rs.getObject(i).toString()).replace(/^\\s+|\\s+$/g,\"\");\n 		}\n \n 		sql = \"insert into rh_parceiro (idparceiro, nome, razaosocial, tipopessoa, dataalteracao, ativo, situacao, fornecedor) values (?,?,?,?,?,?,?,?) \";\n \n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n 		\n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n\n 	sql = \"SELECT NOMEPARC, RAZAOSOCIAL, TIPPESSOA, DTALTER, ATIVO, SITUACAO, FORNECEDOR, CODPARC from \" +  schema + \".TGFPAR WHERE FORNECEDOR = ''S'' and DTALTER >= ''\";\n 	sql = sql + dataAtualFormatada + \"'' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_parceiro set nome = ?, razaosocial = ?, tipopessoa = ?, dataalteracao = ?, ativo = ?, situacao = ?, fornecedor = ? where idparceiro = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 8){\n				objs[i-1] = rs.getObject(i);\n			} else if(i == 4){\n				objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n 			}else\n 				objs[i-1] = rs.getObject(i).toString().trim();\n \n 		}\n \n \n		//Executa update(Retorna 1: atualizou o registro ou 0 caso não encontrou o registro)\n		// Se não encontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n 			sql = \"insert into rh_parceiro (nome, razaosocial, tipopessoa, dataalteracao, ativo, situacao, fornecedor, idparceiro) values (?,?,?,?,?,?,?,?) \";\n 			\n 			jdbcEngine.execUpdate(sql, objs);\n 			\n 		}\n 	}\n \n }\n \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'N', NULL, NULL, NULL, NULL, NULL, NULL, '');

DELETE FROM importardados WHERE  idimportardados = 1;

-- Correção de script da versão 3.4.0 - Erro de syntax e referência a chave estrangeira
INSERT INTO importardados (idimportardados,idexternalconnection,importarpor,tipo,nome,script,agendarrotina,executarpor,horaexecucao,periodohora,datafim,tabelaorigem,tabeladestino,jsonmatriz)
VALUES ('1', '1', 'S', 'J', 'Popular tabela de rh_funcionario', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\nvar stmt2 = conn.createStatement();\n \nvar sql = \"select idfuncionario from rh_funcionario where idfuncionario >= ?\";\nvar sql_consulta_empregado;\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar funcs = jdbcEngine.execSQL(sql, objs, 0);\nvar emps;\n \nvar rs;\nvar rs2;\nvar meta;\nvar aux;\nvar idFuncionario = 1;\nvar idEmpregado;\n\nvar str = \"\"; \nvar res = \"\";\n\nvar auxEmp; \n\n//Valida se a tabela esta vazia\nif(funcs == null || funcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select distinct (CPF) CPF, NOMEFUNC FROM \" +  schema + \".[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTDEM is null order by NOMEFUNC\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idFuncionario;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		objs.push(dataAtual);\n\n		str = objs[2]; \n		\n		//Remove os espaços em branco\n		res = str.trim();\n		res = res.replace(\" \", \"\");\n		\n		res = res.toLowerCase();\n	\n		sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n		sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), '' '', '''')) like ''\" + res + \"'' order by usr.idempregado limit 1;\"\n		\n		emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n		if(emps != null && !emps.isEmpty()){\n		\n			var auxEmp = emps.get(0);\n			\n			idEmpregado = Number(auxEmp[0]);\n			\n			objs[4] = idEmpregado;\n\n			sql = \"insert into rh_funcionario (idfuncionario, cpf, nome, datainicio, datafim, idempregado) values (?,?,?,?, null,?) \";\n	 \n			//Inserir registro\n			jdbcEngine.execUpdate(sql, objs);\n			\n		}\n\n 		idFuncionario += 1;\n \n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n	\n 	sql = \"select NOMEFUNC, DTDEM, CPF FROM \" +  schema + \".[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTALTER >= ''\";\n 	sql = sql + dataAtualFormatada + \"'' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_funcionario set nome = ?, datafim = ? where cpf = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 2){\n \n 				if(rs.getObject(i) == null || rs.getObject(i).equals(\"\"))\n 					objs[i-1] = null;\n 				else \n 					objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n \n 			}else\n 				objs[i-1] = rs.getObject(i);\n \n 		}\n		\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso não encontrou o registro)\n		// Se não eencontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n			str = objs[0]; \n			\n			//Remove os espaços em branco\n			res = str.trim();\n			res = res.replace(\" \", \"\");\n			\n			res = res.toLowerCase();\n		\n			sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n			sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), '' '', '''')) like ''\" + res + \"'' order by usr.idempregado limit 1; \"\n			\n			emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n			if(emps != null && !emps.isEmpty()){\n		\n				var auxEmp = emps.get(0);\n				\n				idEmpregado = Number(auxEmp[0]);\n				\n				objs2 =  new Array();\n	 \n				sql = \"select idfuncionario from rh_funcionario order by idfuncionario desc limit 1\";\n	 \n				funcs = jdbcEngine.execSQL(sql, objs2, 0);\n	 \n				var aux = funcs.get(0);\n				\n				idFuncionario = Number(aux[0]);\n				\n				objs2.push(idFuncionario + 1);\n				objs2.push(objs[0]);\n				objs2.push(objs[2]);\n				objs2.push(dataAtual);\n				objs2.push(objs[1]);\n				objs2.push(idEmpregado);\n\n				sql = \"insert into rh_funcionario (idfuncionario, nome, cpf, datainicio, datafim, idempregado) values (?,?,?,?,?,?)\";\n				\n				jdbcEngine.execUpdate(sql, objs2);\n			\n			}\n 			\n 		}\n 	}\n \n }\n \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'P', '00:00', '4', NULL, NULL, NULL, '');

-- Create table não foi rodado na versao 3.4
CREATE TABLE rh_idiomacurriculo ( 
     ididioma        INTEGER NOT NULL, 
     idcurriculo     INTEGER NOT NULL, 
     idnivelconversa INTEGER NOT NULL, 
     idnivelescrita  INTEGER NOT NULL, 
     idnivelleitura  INTEGER NOT NULL, 
     PRIMARY KEY (ididioma, idcurriculo) 
  );

-- Correção da sintaxe de drop null da versao 3.4
ALTER TABLE rh_requisicaopessoal ALTER COLUMN idcargo varchar(100) NULL;
ALTER TABLE rh_certificacaocurriculo ALTER COLUMN versao varchar(100) NULL;
ALTER TABLE rh_certificacaocurriculo ALTER COLUMN validade INTEGER NULL;
ALTER TABLE rh_competencia ADD nivelcompetencia INT NULL;

-- Script não executado na versao 3.7
ALTER TABLE rh_idiomacurriculo ADD CONSTRAINT fk_rh_idioma_curriculo FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo ( idcurriculo ) ON DELETE NO ACTION ON UPDATE NO ACTION;
-- FIM

-- INICIO - DAVID RODRIGUES DA SILVA - 28/08/2014

ALTER TABLE rh_curriculo ALTER COLUMN idnacionalidade INT NULL;
ALTER TABLE rh_enderecocurriculo ALTER COLUMN iduf INT NULL;

-- FIM


-- Inicio - renato.jesus - 25/09/2014 
 
create table roteiroviagem (
   idroteiroviagem      int  not null,
   datainicio           date not null,
   datafim              date,
   idsolicitacaoservico bigint not null,
   idintegrante         int,
   origem               int,
   destino              int,
   ida                  date not null,
   volta                date,
   constraint roteiroviagem_pk primary key (idroteiroviagem),
   constraint fk_roteiro_requisicaoviagem foreign key (idsolicitacaoservico) references requisicaoviagem (idsolicitacaoservico)
);

create index idx_idsolicitacaoservico on roteiroviagem (idsolicitacaoservico);

create table despesaviagem (
   iddespesaviagem             int  not null,
   datainicio                  date not null,
   datafim                     date,
   idroteiro                   int  not null,
   idtipo                      int  not null,
   idparceiro                  int  not null,
   valor                       decimal(8,2),
   validade                    datetime,
   original                    char(1) not null default 'N',
   idsolicitacaoservico        bigint not null,
   prestacaocontas             char(1) null,
   situacao                    varchar(80),
   quantidade                  int not null,
   datahoracompra              datetime,
   idresponsavelcompra         int,
   idmoeda                     int,
   idformapagamento            int,
   observacoes                 text,
   primary key (iddespesaviagem),
   constraint fk_despesa_roteiroviagem foreign key (idroteiro) references roteiroviagem (idroteiroviagem),
   constraint fk_despesaviagem_parceiro foreign key (idparceiro) references rh_parceiro (idparceiro),
   constraint fk_despesa_tipomovfinviagem foreign key (idtipo) references tipomovimfinanceiraviagem (idtipomovimfinanceiraviagem),
   constraint fk_despesaviagem_moeda foreign key (idmoeda) references moedas (idmoeda),
   constraint fk_despesaviagem_formapagto foreign key (idformapagamento) references formapagamento (idformapagamento),
   constraint fk_despesaviagem_respconfirma foreign key (idresponsavelcompra) references empregados (idempregado)
);

create index idx_idsolicitacaoservico_despesaviagem on despesaviagem(idsolicitacaoservico);
create index fk_despesa_roteiroviagem on despesaviagem(idroteiro);
create index fk_despesaviagem_parceiro on despesaviagem(idparceiro);
create index fk_despesa_tipomovfinviagem on despesaviagem(idtipo);
create index fk_despesaviagem_moeda on despesaviagem(idmoeda);
create index fk_despesaviagem_formapagto on despesaviagem(idformapagamento);
create index fk_despesaviagem_respconfirma on despesaviagem(idresponsavelcompra);

alter table requisicaoviagem drop constraint fk_requisicaoviagem_reference_centroresultado;
exec sp_rename 'requisicaoviagem.idcentrocusto', 'idcentroresultado';
alter table requisicaoviagem add constraint fk_reqviagem_centroresultado foreign key (idcentroresultado) references centroresultado (idcentroresultado);

 -- Fim - renato.jesus - 25/09/2014

-- Inicio - thiago.borges - 26/09/2014

-- Eliminar as referências à tabela [integranteviagem] primeiro
alter table adiantamentoviagem drop constraint fk_adiantamentoviagem_integranteviagem;
alter table itemcontrolefinanceiroviagem drop constraint fk_itemcontrolefinaceiroviagem_solicitacaoservico_empregado;
alter table prestacaocontasviagem drop constraint fk_prestacaocontasviagem_solicitacaoservico_empregado;

alter table integranteviagem drop constraint rel_integranteviagem_solserv;
alter table integranteviagem drop constraint fk_integranteviagem_reference_solicitacaoservico;
alter table integranteviagem drop constraint rel_integranteviagem_empregado;
alter table integranteviagem drop constraint fk_integranteviagem_empregados;
alter table integranteviagem drop constraint fk_integranteviagem_reference_empregados;
drop index integranteviagem.fk_integranteviagem_empregados;
drop index integranteviagem.rel_integranteviagem_empregado;

-- Adicionar as novas colunas na tabela [integranteviagem]
alter table integranteviagem add idintegranteviagem integer not null;
alter table integranteviagem add integrantefuncionario varchar(1) null;
alter table integranteviagem add nome varchar(255) null default null;
alter table integranteviagem add infonaofuncionario text null default null;
alter table integranteviagem add estado varchar(255) null default null;
DECLARE @CName nvarchar(400) = 'Nenhuma'
DECLARE @SQLString nvarchar(400) = 'ALTER TABLE integranteviagem DROP CONSTRAINT '
BEGIN
SET @CName = ( SELECT name FROM sys.key_constraints
				WHERE type = 'PK'
					AND OBJECT_NAME(parent_object_id) = N'integranteviagem')
IF @CName <> 'Nenhuma'
BEGIN
	SET @SQLString = @SQLString + @CName
	EXECUTE sp_executesql @SQLString
END
END;
alter table integranteviagem add primary key (idintegranteviagem);

create index fk_integrante_empregado on integranteviagem(idempregado);
alter table integranteviagem add constraint fk_integrante_empregado foreign key (idempregado) references empregados (idempregado);
create index fk_integrante_resprestacaocontas on integranteviagem(idrespprestacaocontas);
alter table integranteviagem add constraint fk_integrante_resprestacaocontas foreign key (idrespprestacaocontas) references empregados (idempregado);
create index fk_integrante_reqviagem on integranteviagem(idsolicitacaoservico);
alter table integranteviagem add constraint fk_integrante_reqviagem foreign key (idsolicitacaoservico) references requisicaoviagem (idsolicitacaoservico);

exec sp_rename 'tipomovimfinanceiraviagem.exigejustificativa', 'exigedatahoracotacao';

-- Fim - thiago.borges - 26/09/2014

-- INICIO - DAVID RODRIGUES DA SILVA 02/10/2014
UPDATE menu SET    datafim = '2014-08-08' WHERE  link = '/controleFinanceiroViagemImprevisto/controleFinanceiroViagemImprevisto.load' AND nome = '$menu.nome.controleFinanceiroViagemImprevisto';
-- FIM