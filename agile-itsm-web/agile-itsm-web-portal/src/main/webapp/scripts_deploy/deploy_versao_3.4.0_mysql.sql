
set sql_safe_updates = 0;

-- INICIO - Bruno Aquino - 11/04/2014

create table rh_requisicaofuncao (
idsolicitacaoservico int(11) not null ,
nomefuncao varchar(200) null ,
numeropessoas int(3) null ,
possuisubordinados varchar(45) null ,
justificativafuncao varchar(500) null ,
resumoatividades varchar(500) null ,
requisicaovalida varchar(45) null ,
justificativavalidacao varchar(45) null ,
complementojustificativavalidacao varchar(500) null ,
idcargo INT null ,
funcao varchar(100) null ,
resumofuncao varchar(500) null ,
descricaovalida varchar(45) null ,
justificativadescricaofuncao varchar(45) null ,
complementojustificativadescricaofuncao varchar(500) null ,
fase varchar(45) null ,
primary key (idsolicitacaoservico) 
) engine = innodb default character set = latin1;

create table rh_perspectivacomportamentalfuncao (
idperspectivacomportamental int(11) not null,
descricaoperspectivacomportamental varchar(200) default null,
detalheperspectivacomportamental varchar(500) default null,
idsolicitacaoservico int(11) default null,
primary key (idperspectivacomportamental),
key idsolicitacaoservico_idx (idsolicitacaoservico),
constraint idsolicitacaoservico_comportamental foreign key (idsolicitacaoservico) references rh_requisicaofuncao (idsolicitacaoservico) 
on delete no action on update no action
) engine=innodb default charset=latin1;

create table rh_perspectivacomplexidade (
idperspectivacomplexidade int(11) not null,
descricaoperspectivacomplexidade varchar(200) default null,
nivelperspectivacomplexidade int(11) default null,
idsolicitacaoservico int(11) default null,
primary key (idperspectivacomplexidade),
key idsolicitacaoservico_idx (idsolicitacaoservico),
constraint idsolicitacaoservico_compl foreign key (idsolicitacaoservico) references rh_requisicaofuncao (idsolicitacaoservico) 
on delete no action on update no action
) engine=innodb default charset=latin1;

create table rh_perspectivatecnicaformacaoacademica (
idperspectivatecnicaformacaoacademica int(11) not null,
descricaoformacaoacademica varchar(200) default null,
detalheformacaoacademica varchar(500) default null,
obrigatorioformacaoacademica varchar(1) default null,	
idsolicitacaoservico int(11) default null,
primary key (idperspectivatecnicaformacaoacademica),
key idsolicitacaoservico_idx (idsolicitacaoservico),
constraint idsolicitacaoservico_form foreign key (idsolicitacaoservico) references rh_requisicaofuncao (idsolicitacaoservico) 
on delete no action on update no action
) engine=innodb default charset=latin1;

create table rh_perspectivatecnicacertificacao (
idperspectivatecnicacertificacao int(11) not null,
descricaocertificacao varchar(200) default null,
versaocertificacao varchar(500) default null,
obrigatoriocertificacao varchar(1) default null,	
idsolicitacaoservico int(11) default null,
primary key (idperspectivatecnicacertificacao),
key idsolicitacaoservico_idx (idsolicitacaoservico),
constraint idsolicitacaoservico_cert foreign key (idsolicitacaoservico) references rh_requisicaofuncao (idsolicitacaoservico) 
on delete no action on update no action
) engine=innodb default charset=latin1;

create table rh_perspectivatecnicacurso (
idperspectivatecnicacurso int(11) not null,
descricaocurso varchar(200) default null,
detalhecurso varchar(500) default null,
obrigatoriocurso varchar(1) default null,	
idsolicitacaoservico int(11) default null,
primary key (idperspectivatecnicacurso),
key idsolicitacaoservico_idx (idsolicitacaoservico),
constraint idsolicitacaoservico_curs foreign key (idsolicitacaoservico) references rh_requisicaofuncao (idsolicitacaoservico) 
on delete no action on update no action
) engine=innodb default charset=latin1;

create table rh_perspectivatecnicaidioma (
idperspectivatecnicaidioma int(11) not null,
descricaoidioma varchar(200) default null,
detalheidioma varchar(500) default null,
obrigatorioidioma varchar(1) default null,	
idsolicitacaoservico int(11) default null,
primary key (idperspectivatecnicaidioma),
key idsolicitacaoservico_idx (idsolicitacaoservico),
constraint idsolicitacaoservico_idio foreign key (idsolicitacaoservico) references rh_requisicaofuncao (idsolicitacaoservico) 
on delete no action on update no action
) engine=innodb default charset=latin1;

create table rh_perspectivatecnicaexperiencia (
idperspectivatecnicaexperiencia int(11) not null,
descricaoexperiencia varchar(200) default null,
detalheexperiencia varchar(500) default null,
obrigatorioexperiencia varchar(1) default null,	
idsolicitacaoservico int(11) default null,
primary key (idperspectivatecnicaexperiencia),
key idsolicitacaoservico_idx (idsolicitacaoservico),
constraint idsolicitacaoservico_exper foreign key (idsolicitacaoservico) references rh_requisicaofuncao (idsolicitacaoservico) 
on delete no action on update no action
) engine=innodb default charset=latin1;

create table rh_competenciatecnica (
idcompetenciastecnicas int(11) not null,
descricaocompetenciastecnicas varchar(200) default null,
nivelcompetenciastecnicas int(11) default null,
idsolicitacaoservico int(11) default null,
primary key (idcompetenciastecnicas),
key idsolicitacaoservico_idx (idsolicitacaoservico),
constraint idsolicitacaoservico_comp foreign key (idsolicitacaoservico) references rh_requisicaofuncao (idsolicitacaoservico) 
on delete no action on update no action
) engine=innodb default charset=latin1;

create  table justificativarequisicaofuncao (
idjustificativa int(11) not null ,
descricao varchar(100) null ,
situacao varchar(1) null ,
primary key (idjustificativa) 
)engine = innodb default character set = latin1;

create  table rh_atribuicao(
idatribuicao int(11) not null ,
descricao varchar(100) null ,
detalhe varchar(500) null ,
datainicio date null ,
datafim date null ,
primary key (idatribuicao) 
)engine = innodb default character set = latin1;

-- FIM -Bruno Aquino

-- INICIO GILBERTO TAVARES DE FRANCO NERY (08/04/2010)

create  table integranteviagem (
idintegranteviagem int(11) not null ,
idsolicitacaoservico bigint(20) not null ,
idempregado int(11) null ,
idrespprestacaocontas int(11) null ,
integrantefuncionario varchar(1) null ,
nomenaofuncionario varchar(255) null ,
primary key (idintegranteviagem))
engine = innodb;

alter table integranteviagem add constraint rel_integranteviagem_solserv foreign key ( idsolicitacaoservico ) references solicitacaoservico ( idsolicitacaoservico )  on delete no action on update no action;
alter table integranteviagem add constraint rel_integranteviagem_empregado foreign key ( idempregado ) references empregados ( idempregado )  on delete no action on update no action;

alter table integranteviagem add constraint fk_integranteviagem_reference_solicitacaoservico foreign key (idsolicitacaoservico) references requisicaoviagem (idsolicitacaoservico);
alter table integranteviagem add constraint fk_integranteviagem_reference_empregados foreign key (idempregado) references empregados (idempregado);
alter table adiantamentoviagem add constraint fk_adiantamentoviagem_integranteviagem foreign key (idsolicitacaoservico, idempregado) references integranteviagem(idsolicitacaoservico, idempregado);
alter table itemcontrolefinanceiroviagem add constraint fk_itemcontrolefinaceiroviagem_solicitacaoservico_empregado foreign key (idsolicitacaoservico, idempregado) references integranteviagem(idsolicitacaoservico, idempregado);
alter table prestacaocontasviagem add constraint fk_prestacaocontasviagem_solicitacaoservico_empregado foreign key (idsolicitacaoservico, idempregado) references integranteviagem (idsolicitacaoservico, idempregado);
alter table integranteviagem add constraint fk_integranteviagem_empregados foreign key (idrespprestacaocontas) references empregados (idempregado) on delete restrict on update restrict;

alter table itemcontrolefinanceiroviagem add column nomenaofuncionario varchar(255) null;

alter table adiantamentoviagem add column integrantefuncionario varchar(1) null;
alter table adiantamentoviagem add column nomenaofuncionario varchar(255) null;

alter table prestacaocontasviagem add column integrantefuncionario varchar(1) null;
alter table prestacaocontasviagem add column nomenaofuncionario varchar(255) null;

alter table requisicaoviagem ADD COLUMN remarcacao VARCHAR(1) NULL;

ALTER TABLE integranteviagem ADD COLUMN remarcacao VARCHAR(1) NULL;

ALTER TABLE integranteviagem ADD COLUMN iditemtrabalho INT(11) NULL;

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

ALTER TABLE itemcontrolefinanceiroviagem DROP FOREIGN KEY fk_itemcontrolefinaceiroviagem_controlefinanceiroviagem ;

ALTER TABLE itemcontrolefinanceiroviagem DROP INDEX fk_itemcontrolefinaceiroviagem_fornecedor ;

alter table rh_experienciaprofissionalcurriculo change column funcao funcao text not null;

alter table rh_funcionario add column idempregado INT(11) null;

ALTER TABLE rh_requisicaopessoal ADD COLUMN idlotacao INT(11) NULL DEFAULT NULL;

ALTER TABLE rh_cargahoraria CHANGE COLUMN entrada entrada SMALLINT(6) NULL  , 
CHANGE COLUMN saida saida SMALLINT(6) NULL  ;


insert into externalconnection (idexternalconnection,nome,tipo,urljdbc,jdbcdbname,jdbcdriver,jdbcuser,jdbcpassword,filename,schemadb,deleted)
values ('1', 'SQL Server Sankhya', 'J', 'jdbc:sqlserver://0.0.0.0:1433;', 'MGE_CENTRALIT', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'sa', 'senhadosankhya', '', '[MGE_CENTRALIT].sankhya', NULL);

insert into importardados (idimportardados,idexternalconnection,importarpor,tipo,nome,script,agendarrotina,executarpor,horaexecucao,periodohora,datafim,tabelaorigem,tabeladestino,jsonmatriz)
values ('1', '1', 'S', 'J', 'importar funcionario - rh_funcionario', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\nvar stmt2 = conn.createStatement();\n \nvar sql = \"select idfuncionario from rh_funcionario where idfuncionario >= ?\";\nvar sql_consulta_empregado;\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar funcs = jdbcEngine.execSQL(sql, objs, 0);\nvar emps;\n \nvar rs;\nvar rs2;\nvar meta;\nvar aux;\nvar idFuncionario = 1;\nvar idEmpregado;\n\nvar str = \"\"; \nvar res = \"\";\n\nvar auxEmp; \n\n//Valida se a tabela esta vazia\nif(funcs == null || funcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select distinct (CPF) CPF, NOMEFUNC FROM [MGE_CENTRALIT].[sankhya].[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTDEM is null order by NOMEFUNC\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idFuncionario;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		objs.push(dataAtual);\n\n		str = objs[2]; \n		\n		//Remove os espaços em branco\n		res = str.trim();\n		res = res.replace(\" \", \"\");\n		\n		res = res.toLowerCase();\n	\n		sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n		sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), \' \', \'\')) like \'\" + res + \"\' order by usr.idempregado limit 1;\"\n		\n		emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n		if(emps != null && !emps.isEmpty()){\n		\n			var auxEmp = emps.get(0);\n			\n			idEmpregado = Number(auxEmp[0]);\n			\n			objs[4] = idEmpregado;\n\n			sql = \"insert into rh_funcionario (idfuncionario, cpf, nome, datainicio, datafim, idempregado) values (?,?,?,?, null,?) \";\n	 \n			//Inserir registro\n			jdbcEngine.execUpdate(sql, objs);\n			\n		}\n\n 		idFuncionario += 1;\n \n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n	\n 	sql = \"select NOMEFUNC, DTDEM, CPF FROM [MGE_CENTRALIT].[sankhya].[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTALTER >= \'\";\n 	sql = sql + dataAtualFormatada + \"\' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_funcionario set nome = ?, datafim = ? where cpf = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 2){\n \n 				if(rs.getObject(i) == null || rs.getObject(i).equals(\"\"))\n 					objs[i-1] = null;\n 				else \n 					objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n \n 			}else\n 				objs[i-1] = rs.getObject(i);\n \n 		}\n		\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso não encontrou o registro)\n		// Se não eencontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n			str = objs[0]; \n			\n			//Remove os espaços em branco\n			res = str.trim();\n			res = res.replace(\" \", \"\");\n			\n			res = res.toLowerCase();\n		\n			sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n			sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), \' \', \'\')) like \'\" + res + \"\' order by usr.idempregado limit 1; \"\n			\n			emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n			if(emps != null && !emps.isEmpty()){\n		\n				var auxEmp = emps.get(0);\n				\n				idEmpregado = Number(auxEmp[0]);\n				\n				objs2 =  new Array();\n	 \n				sql = \"select idfuncionario from rh_funcionario order by idfuncionario desc limit 1\";\n	 \n				funcs = jdbcEngine.execSQL(sql, objs2, 0);\n	 \n				var aux = funcs.get(0);\n				\n				idFuncionario = Number(aux[0]);\n				\n				objs2.push(idFuncionario + 1);\n				objs2.push(objs[0]);\n				objs2.push(objs[2]);\n				objs2.push(dataAtual);\n				objs2.push(objs[1]);\n				objs2.push(idEmpregado);\n\n				sql = \"insert into rh_funcionario (idfuncionario, nome, cpf, datainicio, datafim, idempregado) values (?,?,?,?,?,?)\";\n				\n				jdbcEngine.execUpdate(sql, objs2);\n			\n			}\n 			\n 		}\n 	}\n \n }\n \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '00:00', NULL, NULL, NULL, NULL, ''),
('2', '1', 'S', 'J', 'Carga horaria', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\n \nvar sql = \"delete from rh_cargahoraria where idcargahoraria >= 0\";\nvar objs =  new Array();\n\njdbcEngine.execUpdate(sql, objs);\n \nvar rs;\nvar meta;\nvar idJCgh = 1;\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select CODCARGAHOR, DIASEM, ENTRADA, SAIDA, DESCANSOSEM, TURNO, DTALTER from \" +  schema + \".[TFPHOR] order by CODCARGAHOR, DIASEM, TURNO\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idJCgh;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		sql = \"insert into rh_cargahoraria (idcargahoraria, codcargahor, diasemana, entrada, saida, descansoem, turno, dataalter) values (?,?,?,?,?,?,?,?) \";\n \n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n 		\n 		idJCgh += 1;\n \n 	}\n  \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '00:30', NULL, NULL, NULL, NULL, ''),
('3', '1', 'S', 'J', 'Centro de custo', 'var importNames = JavaImporter();\n\nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n\njava.lang.Class.forName(driver);\n\nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n\nvar stmt = conn.createStatement();\n\nvar sql = \"select idcentroresultado from centroresultado where idcentroresultado >= ?\";\n\nvar objs =  new Array();\nobjs[0]= 1;\n\nvar cencus = jdbcEngine.execSQL(sql, objs, 0);\n\nvar rs;\nvar meta;\nvar aux;\nvar idParc = 1;\nvar situacao;\nvar codFormatado;\nvar codAux;\n\n//Valida se a tabela esta vazia\nif(cencus == null || cencus.isEmpty()){\n\n	//Tabela vazia, popula a tabela com todos os dados\n\n 	sql = \"select DESCRCENCUS, CODCENCUSPAI, ATIVO, CODCENCUS from \" +  schema + \".TSICUS order by CODCENCUS;\";\n\n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n\n 	while(rs.next()) {\n\n 		objs = new Array();\n\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n\n			switch(i) {\n\n				case 1:\n					objs[i-1] = new String(rs.getObject(i).toString()).replace(/^\\s+|\\s+$/g,\"\");\n					break;\n				case 2:\n					if(rs.getObject(i) < 0)\n						objs[i-1] = null;\n					else\n						objs[i-1] = rs.getObject(i);\n\n					break;\n				case 3:\n					situacao = rs.getObject(i);\n\n					if(situacao == new String(\'S\'))\n						objs[i-1] = \'A\'\n					else\n						objs[i-1] = \'I\'\n\n					break;\n				case 4:\n					objs[i-1] = rs.getObject(i);\n					codAux = new String (objs[i-1]);\n					\n					if(codAux.length > 1)\n						codFormatado = \'0\' + codAux.substring(0,1) + \'.\' + codAux.substring(1,codAux.length);\n					else\n						codFormatado = \'0\' + codAux.substring(0,1) + \'.000\';\n						\n					objs[i] = codFormatado;\n					break;\n			}\n 		}\n\n 		sql = \"insert into centroresultado (nomecentroresultado, idcentroresultadopai, permiterequisicaoproduto, situacao, idcentroresultado, codigocentroresultado) values (?,?,\'N\',?,?,?) \";\n\n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n\n 	}\n\n } else {\n\n	//Tabela ja contem dados, realiza update\n\n 	sql = \"select DESCRCENCUS, CODCENCUSPAI, ATIVO, CODCENCUS from \" +  schema + \".TSICUS order by CODCENCUS;\";\n\n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n\n	objs = new Array();\n\n 	while(rs.next()) {\n\n 		objs = new Array();\n\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n\n 			switch(i) {\n\n				case 1:\n					objs[i-1] = new String(rs.getObject(i).toString()).replace(/^\\s+|\\s+$/g,\"\");\n					break;\n				case 2:\n					if(rs.getObject(i) < 0)\n						objs[i-1] = null;\n					else\n						objs[i-1] = rs.getObject(i);\n\n					break;\n				case 3:\n					situacao = rs.getObject(i);\n\n					if(situacao == \'S\')\n						objs[i-1] = \'A\';\n					else\n						objs[i-1] = \'I\'\n\n					break;\n				case 4:\n					codAux = new String(rs.getObject(i));\n					\n					if(codAux.length > 1)\n						codFormatado = \'0\' + codAux.substring(0,1) + \'.\' + codAux.substring(1,codAux.length);\n					else\n						codFormatado = \'0\' + codAux.substring(0,1) + \'.000\';\n						\n					objs[i-1] = codFormatado;\n					objs[i] = rs.getObject(i);\n					break;\n			}\n\n 		}\n		\n		sql = \"update centroresultado set nomecentroresultado = ?, idcentroresultadopai = ?, situacao = ?, codigocentroresultado = ? where idcentroresultado = ?\";\n\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso não encontrou o registro)\n		// Se não encontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n\n			sql = \"insert into centroresultado (permiterequisicaoproduto, nomecentroresultado, idcentroresultadopai, situacao, codigocentroresultado, idcentroresultado) values (\'N\',?,?,?,?,?) \";\n\n 			jdbcEngine.execUpdate(sql, objs);\n\n 		}\n 	}\n\n }\n\n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '01:00', NULL, NULL, NULL, NULL, ''),
('4', '1', 'S', 'J', 'Departamento', 'var importNames = JavaImporter();\n\nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n\njava.lang.Class.forName(driver);\n\nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n \nvar stmt = conn.createStatement();\n\nvar sql = \"select iddepartamento from rh_departamento where iddepartamento >= ?\";\n\nvar objs =  new Array();\nobjs[0]= 1;\n\nvar funcs = jdbcEngine.execSQL(sql, objs, 0);\n\nvar rs;\nvar meta;\nvar aux;\nvar idDepartamento = 1;\n\n\n//Valida se a tabela esta vazia\nif(funcs == null || funcs.isEmpty()){\n\n  //Tabela vazia, popula a tabela com todos os dados\n\n            sql = \"select CODDEP, DESCRDEP, CODCENCUS, CODDEPPAI, ANALITICO, ATIVO, CODPARC FROM \" +  schema + \".[TFPDEP] order by CODDEP\";\n\n        rs = stmt.executeQuery(sql);\n        meta = rs.getMetaData();\n\n while(rs.next()) {\n\n                   objs = new Array();\n\n                      objs[0] = idDepartamento;\n\n                               for(var i = 1; i <= meta.getColumnCount(); i++) {\n                                         objs[i] = rs.getObject(i);\n                         }\n\n                    sql = \"insert into rh_departamento (iddepartamento, coddep, descricao, idcentrocusto, lotacaopai, analitico, situacao, idparceiro) values (?,?,?,?,?,?,?,?) \";\n\n                 //Inserir registro\n                        jdbcEngine.execUpdate(sql, objs);\n\n                               idDepartamento += 1;\n                             \n           }\n\n} else {\n\n             //Tabela ja contem dados, realiza update\n            \n           sql = \"select DESCRDEP, CODCENCUS, CODDEPPAI, ANALITICO, ATIVO, CODPARC, CODDEP FROM \" + schema + \".[TFPDEP] order by CODDEP\";\n\n        rs = stmt.executeQuery(sql);\n               objs = new Array();\n           meta = rs.getMetaData();\n\n while(rs.next()) {\n\n                   objs = new Array();\n   \n                           for(var i = 1; i <= meta.getColumnCount(); i++) {\n                                        objs[i-1] = rs.getObject(i);\n                     }\n\n                    sql = \"update rh_departamento set descricao = ?, idcentrocusto = ?, lotacaopai = ?, analitico = ?, situacao = ?, idparceiro = ? where coddep = ?\";\n\n                           //Executa update(Retorna 1: atualizou o registro ou 0 caso não encontrou o registro)\n                 // Se não eencontrou o registro, realiza insert\n                             if(jdbcEngine.execUpdate(sql, objs) == 0){\n\n                                              //Seleciona o ultimo id\n                                            sql = \"select iddepartamento from rh_departamento order by iddepartamento desc limit 1\";\n\n                                   funcs = jdbcEngine.execSQL(sql, null, 0);\n\n                                   var aux = funcs.get(0);\n\n                                        idDepartamento = Number(aux[0]);\n\n                                           objs.push(idDepartamento + 1);\n\n                                   sql = \" insert into rh_departamento (descricao, idcentrocusto, lotacaopai, analitico, situacao, idparceiro, coddep, iddepartamento) values (?,?,?,?,?,?,?,?) \";\n\n                                          jdbcEngine.execUpdate(sql, objs);\n\n                               }\n         }\n\n}\n\n//Fecha conexão\nrs.close();\nstmt.close();\nconn.close();', 'S', 'H', '01:30', NULL, NULL, NULL, NULL, ''),
('5', '1', 'S', 'J', 'Jornada', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\n \n//Limpa a tabela\nvar sql = \"delete from rh_jornadadetrabalho where idjornada >= 0\";\nvar objs =  new Array(); \n\njdbcEngine.execUpdate(sql, objs);\n \nvar rs;\nvar meta;\nvar idJornada = 1;\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select CODCARGAHOR, DESCRCARGAHOR, ESCALONAR, CONSIDERAFERIADOS from \" +  schema + \".[TFPCGH]\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idJornada;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		sql = \"insert into rh_jornadadetrabalho (idjornada, codcargahor, descricao, escala, considerarferiados) values (?,?,?,?,?) \";\n \n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n 		\n 		idJornada += 1;\n \n 	}\n \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'H', '02:00', '20', NULL, NULL, NULL, ''),
('6', '1', 'S', 'J', 'Importar Parceiro', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\n \nvar sql = \"select idparceiro from rh_parceiro where idparceiro >= ?\";\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar parcs = jdbcEngine.execSQL(sql, objs, 0);\n \nvar rs;\nvar meta;\nvar aux;\nvar idParc = 1;\n \n//Valida se a tabela esta vazia\nif(parcs == null || parcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"SELECT CODPARC, NOMEPARC, RAZAOSOCIAL, TIPPESSOA, DTALTER, ATIVO, SITUACAO, FORNECEDOR from \" +  schema + \".TGFPAR WHERE FORNECEDOR = \'S\' order by CODPARC;\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n			\n			if(i == 1)\n				objs[i-1] = rs.getObject(i);\n 			else\n 				objs[i-1] = new String(rs.getObject(i).toString()).replace(/^\\s+|\\s+$/g,\"\");\n 		}\n \n 		sql = \"insert into rh_parceiro (idparceiro, nome, razaosocial, tipopessoa, dataalteracao, ativo, situacao, fornecedor) values (?,?,?,?,?,?,?,?) \";\n \n		//Inserir registro\n 		jdbcEngine.execUpdate(sql, objs);\n 		\n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n\n 	sql = \"SELECT NOMEPARC, RAZAOSOCIAL, TIPPESSOA, DTALTER, ATIVO, SITUACAO, FORNECEDOR, CODPARC from \" +  schema + \".TGFPAR WHERE FORNECEDOR = \'S\' and DTALTER >= \'\";\n 	sql = sql + dataAtualFormatada + \"\' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_parceiro set nome = ?, razaosocial = ?, tipopessoa = ?, dataalteracao = ?, ativo = ?, situacao = ?, fornecedor = ? where idparceiro = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 8){\n				objs[i-1] = rs.getObject(i);\n			} else if(i == 4){\n				objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n 			}else\n 				objs[i-1] = rs.getObject(i).toString().trim();\n \n 		}\n \n \n		//Executa update(Retorna 1: atualizou o registro ou 0 caso não encontrou o registro)\n		// Se não encontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n 			sql = \"insert into rh_parceiro (nome, razaosocial, tipopessoa, dataalteracao, ativo, situacao, fornecedor, idparceiro) values (?,?,?,?,?,?,?,?) \";\n 			\n 			jdbcEngine.execUpdate(sql, objs);\n 			\n 		}\n 	}\n \n }\n \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'N', NULL, NULL, NULL, NULL, NULL, NULL, '');

delete from importardados where idimportardados = 1;
insert into importardados (idimportardados,idexternalconnection,importarpor,tipo,nome,script,agendarrotina,executarpor,horaexecucao,periodohora,datafim,tabelaorigem,tabeladestino,jsonmatriz)
values ('1', '1', 'S', 'J', 'Popular tabela de rh_funcionario', 'var importNames = JavaImporter();\n \nimportNames.importPackage(java.sql);\nimportNames.importPackage(java.util);\nimportNames.importPackage(Packages.br.com.citframework.integracao);\nimportNames.importPackage(Packages.br.com.centralit.citcorpore.bean);\n \njava.lang.Class.forName(driver);\n \nvar conn = java.sql.DriverManager.getConnection(url, user, password);\n  \nvar stmt = conn.createStatement();\nvar stmt2 = conn.createStatement();\n \nvar sql = \"select idfuncionario from rh_funcionario where idfuncionario >= ?\";\nvar sql_consulta_empregado;\n \nvar objs =  new Array();\nobjs[0]= 1;\n \nvar objs2;\n \nvar funcs = jdbcEngine.execSQL(sql, objs, 0);\nvar emps;\n \nvar rs;\nvar rs2;\nvar meta;\nvar aux;\nvar idFuncionario = 1;\nvar idEmpregado;\n\nvar str = \"\"; \nvar res = \"\";\n\nvar auxEmp; \n\n//Valida se a tabela esta vazia\nif(funcs == null || funcs.isEmpty()){\n \n	//Tabela vazia, popula a tabela com todos os dados\n \n 	sql = \"select distinct (CPF) CPF, NOMEFUNC FROM \" +  schema + \".[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTDEM is null order by NOMEFUNC\";\n \n 	rs = stmt.executeQuery(sql);\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		objs = new Array();\n \n 		objs[0] = idFuncionario;\n 		\n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n 			objs[i] = rs.getObject(i);\n 		}\n \n 		objs.push(dataAtual);\n\n		str = objs[2]; \n		\n		//Remove os espaços em branco\n		res = str.trim();\n		res = res.replace(\" \", \"\");\n		\n		res = res.toLowerCase();\n	\n		sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n		sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), \' \', \'\')) like \'\" + res + \"\' order by usr.idempregado limit 1;\"\n		\n		emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n		if(emps != null && !emps.isEmpty()){\n		\n			var auxEmp = emps.get(0);\n			\n			idEmpregado = Number(auxEmp[0]);\n			\n			objs[4] = idEmpregado;\n\n			sql = \"insert into rh_funcionario (idfuncionario, cpf, nome, datainicio, datafim, idempregado) values (?,?,?,?, null,?) \";\n	 \n			//Inserir registro\n			jdbcEngine.execUpdate(sql, objs);\n			\n		}\n\n 		idFuncionario += 1;\n \n 	}\n \n } else {\n \n	//Tabela ja contem dados, realiza update\n	\n 	sql = \"select NOMEFUNC, DTDEM, CPF FROM \" +  schema + \".[TFPFUN] where CODEMP in (1,5,6) and CPF is not null and DTALTER >= \'\";\n 	sql = sql + dataAtualFormatada + \"\' order by DTALTER\";\n \n 	rs = stmt.executeQuery(sql);\n 	objs = new Array();\n 	meta = rs.getMetaData();\n \n 	while(rs.next()) {\n \n 		sql = \"update rh_funcionario set nome = ?, datafim = ? where cpf = ?\";\n 		objs = new Array();\n \n 		for(var i = 1; i <= meta.getColumnCount(); i++) {\n \n 			if(i == 2){\n \n 				if(rs.getObject(i) == null || rs.getObject(i).equals(\"\"))\n 					objs[i-1] = null;\n 				else \n 					objs[i-1] = rs.getObject(i).toString().substring(0, 10);\n \n 			}else\n 				objs[i-1] = rs.getObject(i);\n \n 		}\n		\n		//Executa update(Retorna 1: atualizou o registro ou 0 caso não encontrou o registro)\n		// Se não eencontrou o registro, realiza insert\n 		if(jdbcEngine.execUpdate(sql, objs) == 0){\n \n			str = objs[0]; \n			\n			//Remove os espaços em branco\n			res = str.trim();\n			res = res.replace(\" \", \"\");\n			\n			res = res.toLowerCase();\n		\n			sql_consulta_empregado = \" select usr.idempregado from usuario usr join empregados emp on emp.idempregado = usr.idempregado \"\n			sql_consulta_empregado += \" where lcase(replace(trim(emp.nome), \' \', \'\')) like \'\" + res + \"\' order by usr.idempregado limit 1; \"\n			\n			emps = jdbcEngine.execSQL(sql_consulta_empregado, null, 0);\n	 \n			if(emps != null && !emps.isEmpty()){\n		\n				var auxEmp = emps.get(0);\n				\n				idEmpregado = Number(auxEmp[0]);\n				\n				objs2 =  new Array();\n	 \n				sql = \"select idfuncionario from rh_funcionario order by idfuncionario desc limit 1\";\n	 \n				funcs = jdbcEngine.execSQL(sql, objs2, 0);\n	 \n				var aux = funcs.get(0);\n				\n				idFuncionario = Number(aux[0]);\n				\n				objs2.push(idFuncionario + 1);\n				objs2.push(objs[0]);\n				objs2.push(objs[2]);\n				objs2.push(dataAtual);\n				objs2.push(objs[1]);\n				objs2.push(idEmpregado);\n\n				sql = \"insert into rh_funcionario (idfuncionario, nome, cpf, datainicio, datafim, idempregado) values (?,?,?,?,?,?)\";\n				\n				jdbcEngine.execUpdate(sql, objs2);\n			\n			}\n 			\n 		}\n 	}\n \n }\n \n //Fecha conexão\n rs.close();\n stmt.close();\n conn.close();', 'S', 'P', '00:00', '4', NULL, NULL, NULL, '');

-- FIM GILBERTO TAVARES DE FRANCO NERY (08/04/2010)

-- INICIO RODRIGO PECCI ACORSE (11/04/2014)

alter table  tipomovimfinanceiraviagem add imagem varchar(255);

-- FIM RODRIGO PECCI ACORSE (11/04/2014)

-- INICIO - THIAGO BORGES DA SILVA - 24/02/2014

create  table rh_candidato (
idcandidato int(11) not null auto_increment ,
nome varchar(150) not null ,
cpf varchar(14) not null ,
email varchar(150) not null ,
situacao char(1) not null ,
datainicio date not null ,
datafim date null default null ,
senha varchar(300) not null ,
tipo char(1) not null ,
primary key (idcandidato) 
) engine=innodb;

-- FIM - THIAGO BORGES DA SILVA - 24/02/2014

-- INICIO - THIAGO BORGES DA SILVA - 11/03/2014

create  table rh_funcaoexperienciaprofissionalcurriculo (
idfuncao int not null ,
idexperienciaprofissionalcurriculo int not null ,
nomefuncao varchar(100) null ,
descricaofuncao varchar(500) null ,
primary key (idfuncao),
index fk_idx (idexperienciaprofissionalcurriculo asc) ,
constraint fk_idexperienciaprofissionalcurriculo 
foreign key (idexperienciaprofissionalcurriculo)
references rh_experienciaprofissionalcurriculo (idexperienciaprofissional)
on delete no action on update no action)
engine = innodb;

-- FIM - THIAGO BORGES DA SILVA - 11/03/2014

-- INICIO - DAVID RODRIGUES DA SILVA - 24/03/2014

create table  rh_historicofuncional  (
     idhistoricofuncional  int(11) not null auto_increment,
     idcandidato  int(11) null default null,
     idcurriculo  int(11) null default null,
     dtcriacao  date null default null,
    primary key ( idhistoricofuncional ),
    index  fk_historicofuncional_candidato  ( idcandidato ),
    index  fk_historicofuncional_curriculo  ( idcurriculo ),
    constraint  fk_historicofuncional_candidato  foreign key ( idcandidato ) references  rh_candidato  ( idcandidato ),
    constraint  fk_historicofuncional_curriculo  foreign key ( idcurriculo ) references  rh_curriculo  ( idcurriculo )
)engine=innodb;

create table  rh_itemhistoricofuncional (
     iditemhistorico  int(11) not null auto_increment,
     idhistoricofuncional  int(11) null default null,
     titulo  varchar(100) not null,
     descricao  varchar(500) not null,
     dtcriacao  date not null,
     idresponsavel  int(11) not null,
    primary key ( iditemhistorico ),
    index  fk_itemhistoricofuncional_historicofuncional  ( idhistoricofuncional ),
    index  fk_itemhistoricofuncional_usuarios  ( idresponsavel ),
    constraint  fk_itemhistoricofuncional_historicofuncional  foreign key ( idhistoricofuncional ) references  rh_historicofuncional  ( idhistoricofuncional ),
    constraint  fk_itemhistoricofuncional_usuarios  foreign key ( idresponsavel ) references  usuario  ( idusuario )
)engine=innodb;

alter table  rh_itemhistoricofuncional  add column  tipo  char(1);

create table  rh_justificativalistanegra  (
     idjustificativa  int(11) not null auto_increment,
     justificativa  varchar(100) not null,
     situacao  char(1) not null,
     dtcriacao  date not null,
    primary key ( idjustificativa )
)engine=innodb;

create table  rh_listanegra  (
     idlistanegra  int(11) not null auto_increment,
     idcandidato  int(11) null default null,
     idjustificativa  int(11) null default null,
     idresponsavel  int(11) null default null,
     descricao  varchar(500) null default null,
     datainicio  date null default null,
     datafim  date null default null,
    primary key ( idlistanegra ),
    index  fk_listanegra_justificativalistanegra  ( idjustificativa ),
    index  fk_listanegra_usuarios  ( idresponsavel ),
    index  fk_listanegra_candidato  ( idcandidato ),
    constraint  fk_listanegra_candidato  foreign key ( idcandidato ) references  rh_candidato  ( idcandidato ),
    constraint  fk_listanegra_justificativalistanegra  foreign key ( idjustificativa ) references  rh_justificativalistanegra  ( idjustificativa ),
    constraint  fk_listanegra_usuarios  foreign key ( idresponsavel ) references  usuario  ( idusuario )
)engine=innodb;

-- FIM - DAVID RODRIGUES DA SILVA - 24/03/2014

-- INICIO - Mário Hayasaki Júnior - 11/04/2014

alter table rh_candidato add column autenticado char(1);
alter table rh_candidato add column hashid varchar(255);

-- FIM - Mário Hayasaki Júnior - 11/04/2014

-- INICIO - valdoilo.damasceno - 11/04/2014

alter table rh_curriculo change column idnaturalidade idnacionalidade int(11);
alter table rh_enderecocurriculo drop column idbairro;
alter table rh_experienciaprofissionalcurriculo drop column idrequisicaomudanca;

-- FIM - valdoilo.damasceno - 11/04/2014

-- INICIO - cleison.ferreira - 11/04/2014

create table rh_idiomacurriculo (
  ididioma int(11) not null,
  idcurriculo int(11) not null,
  idnivelconversa int(11) not null,
  idnivelescrita int(11) not null,
  idnivelleitura int(11) not null,
  primary key (ididioma,idcurriculo)
);

-- FIM - cleison.ferreira - 11/04/2014

-- INICIO - RODRIGO PECCI ACORSE - 09/11/2013

alter table atividadeperiodica change column criadopor criadopor varchar(256);
alter table atividadeperiodica change column alteradopor alteradopor varchar(256);

alter table rh_experienciaprofissionalcurriculo change column idcurriculo idcurriculo int null default null , add index fk_curriculo_idx (idcurriculo asc);
alter table  rh_experienciaprofissionalcurriculo add constraint fk_curriculo foreign key (idcurriculo) references rh_curriculo (idcurriculo) on delete no action on update no action;

alter table rh_curriculo add column pretensaosalarial decimal(8,2) null;

-- FIM - RODRIGO PECCI ACORSE - 09/11/2013

alter table rh_enderecocurriculo change column iduf iduf int(11) null;

-- Inicio David - 14-04-2014

 create table rh_complexidade(
	idcomplexidade int (11) not null auto_increment,
	nivel int(11) not null,
	descricao varchar(500) not null,
	situacao char(1) not null,
	primary key(idcomplexidade)
)engine=innodb;

insert into rh_complexidade (idcomplexidade, nivel, descricao, situacao) values (1,1,"Baixa",'A');
insert into rh_complexidade (idcomplexidade, nivel, descricao, situacao) values (2,2,"Intermediária",'A');
insert into rh_complexidade (idcomplexidade, nivel, descricao, situacao) values (3,3,"Mediana",'A');
insert into rh_complexidade (idcomplexidade, nivel, descricao, situacao) values (4,4,"Alta",'A');
insert into rh_complexidade (idcomplexidade, nivel, descricao, situacao) values (5,5,"Especialista",'A');

create table rh_nivelcompetencia(
	idnivelcompetencia int(11) not null auto_increment,
	nivel int(1) not null,
	descricao varchar(500) not null,
	situacao char(1) not null,
	primary key(idnivelcompetencia)
)engine=innodb;

insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (1,0,"Não Tem Conhecimento",'A');
insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (2,1,"Tem Conhecimento",'A');
insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (3,2,"Tem Conhecimento e Prática em Nivel Básico",'A');
insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (4,3,"Tem Conhecimento e Prática em Nivel Intermediário",'A');
insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (5,4,"Tem Conhecimento e Prática em Nivel Avançado",'A');
insert into rh_nivelcompetencia (idnivelcompetencia, nivel, descricao, situacao) values (6,5,"É Multiplicador",'A');

create table rh_descricao_atruibuicaoresponsabilidade(
	iddescricao int(11) not null auto_increment,
	descricao varchar(256) not null,
	situacao char(1) not null,
	primary key(iddescricao)
)engine=innodb;

insert into rh_descricao_atruibuicaoresponsabilidade(iddescricao,descricao,situacao) 
values (1,"Definir ações administrativas para o gerentes responsáveis",'A');
insert into rh_descricao_atruibuicaoresponsabilidade(iddescricao,descricao,situacao) 
values
(2,"Explicar as determinações da diretoria para os gerentes",'A');
insert into rh_descricao_atruibuicaoresponsabilidade(iddescricao,descricao,situacao) 
values
(3,"Orientar os gerentes nas atividades cotidianas para a execução dos processos administrativos",'A');
insert into rh_descricao_atruibuicaoresponsabilidade(iddescricao,descricao,situacao) 
values
(4,"Representar a área administrativa perante a diretoria",'A');

create table rh_competenciatecnica_(
	idcompetencia int(11) not null auto_increment,
	descricao varchar(256) not null,
	situacao char(1) not null,
	primary key(idcompetencia)
)engine=innodb;

insert into rh_competenciatecnica_ (idcompetencia,descricao,situacao) values (1,"itil",'a');
insert into rh_competenciatecnica_ (idcompetencia,descricao,situacao) values (2,"cobit",'a');
insert into rh_competenciatecnica_ (idcompetencia,descricao,situacao) values (3,"iso 20000",'a');
insert into rh_competenciatecnica_ (idcompetencia,descricao,situacao) values (4,"iso 27002",'a');
insert into rh_competenciatecnica_ (idcompetencia,descricao,situacao) values (5,"gestão de projetos",'a');

create table rh_comportamento(
	idcomportamento int(11) not null auto_increment,
	descricao varchar(256) not null,
	situacao char(1) not null,
	primary key(idcomportamento)
)engine=innodb;


insert into rh_comportamento (idcomportamento,descricao,situacao) values (1,"cumpre prazos dos trabalhos acordados, consideradando o impacto das atividades na organização",'a');
insert into rh_comportamento (idcomportamento,descricao,situacao) values (2,"demostra interese em participar das ações desenvolvida pela empresa",'a');

-- Fim David - 14-04-2014

-- Inicio Bruno Rodrigues - 14-04-2014

create table rh_manualfuncao (
  idmanualfuncao int(11) not null,
  titulocargo varchar(255),
  titulofuncao varchar(255),
  resumofuncao varchar(255),
  cbo varchar(255),
  codigo varchar(255),

  idformacaora varchar(255),
  ididiomara varchar(255),
  idnivelescritara varchar(255),
  idnivelleiturara varchar(255),  
  idnivelconversara varchar(255),
  expanteriorra varchar(255),

  idformacaorf varchar(255),
  ididiomarf varchar(255),
  idnivelescritarf varchar(255),
  idnivelleiturarf varchar(255),  
  idnivelconversarf varchar(255),
  expanteriorrf varchar(255),

  pesocomplexidade varchar(255),
  pesotecnica varchar(255),
  pesocomportamental varchar(255),
  pesoresultados varchar(255),
  primary key (idmanualfuncao)
) engine=innodb;

create table rh_atribuicaoresponsabilidade (
  idatribuicaoresponsabilidade int(11) not null,
  descricaoperspectivacomplexidade varchar(255),
  idnivel int(11) not null,
  idmanualfuncao int(11) not null,
  primary key (idatribuicaoresponsabilidade)
) engine=innodb;

create table rh_perspectivacomportamental (
  idperspectivacomportamental int(11) not null,
  cmbcompetenciacomportamental varchar(255),
  comportamento varchar(255),
  idmanualfuncao int(11) not null,
  primary key (idperspectivacomportamental)
) engine=innodb;

create table rh_manualcertificacao(
  idmanualcertificacao int(11) not null,
  descricao varchar(255),
  detalhe varchar(255),
  idmanualfuncao int(11) not null,
  raourf varchar(255),
  primary key (idmanualcertificacao)
) engine=innodb;

create table rh_manualcurso(
  idmanualcurso int(11) not null,
  descricao varchar(255),
  detalhe varchar(255),
  idmanualfuncao int(11) not null,
  raourf varchar(255),
  primary key (idmanualcurso)
) engine=innodb;

create table rh_manualcompetenciatecnica (
  idmanualcompetenciatecnica int(11) not null,
  descricao varchar(255),
  situacao varchar(255),
  idmanualfuncao int(11) not null,
  idnivelcompetenciaacesso varchar(255),
  idnivelcompetenciafuncao varchar(255),
  primary key (idmanualcompetenciatecnica)
) engine=innodb;

-- Fim Bruno Rodrigues - 14-04-2014

-- INICIO - EULER RAMOS - 13/04/2014

alter table rh_requisicaopessoal add justificativarejeicao text;
alter table rh_entrevistacandidato add observacaogestor text;
alter table rh_entrevistacandidato add notagestor decimal(6,2);
alter table rh_requisicaopessoal add motivodesistenciacandidato text;
alter table rh_requisicaopessoal add idjornada int(11);
alter table rh_requisicaopessoal add idcidade int(11);
alter table rh_requisicaopessoal add idunidade int(11);
alter table rh_requisicaopessoal change column dataabertura dataabertura date null;
alter table rh_requisicaopessoal change column salario salario double null;

-- FIM - EULER RAMOS - 13/04/2014

-- Inicio - Flávio 14/04/2014

alter table rh_perspectivacomportamental 
add constraint fk_manualfuncao
  foreign key (idmanualfuncao)
  references rh_manualfuncao (idmanualfuncao)
  on delete no action
  on update no action;


alter table rh_manualcompetenciatecnica 
change column idnivelcompetenciaacesso idnivelcompetenciaacesso int null default null ,
change column idnivelcompetenciafuncao idnivelcompetenciafuncao int null default null ;

alter table rh_manualcompetenciatecnica 
add column idcompetenciatecnica int null after idnivelcompetenciafuncao;

-- Fim - Flávio 14/04/2014

-- INICIO - CARLOS SANTOS - 14/04/2014

alter table bpm_tipofluxo add idprocessonegocio int;

create table delegcentroresultadofluxo
(
   iddelegacaocentroresultado int not null,
   idinstanciafluxo     bigint not null,
   primary key (iddelegacaocentroresultado, idinstanciafluxo)
);

create table delegcentroresultadoprocesso
(
   iddelegacaocentroresultado int not null,
   idprocessonegocio    int not null,
   primary key (iddelegacaocentroresultado, idprocessonegocio)
);

create table delegacaocentroresultado
(
   iddelegacaocentroresultado int not null,
   idresponsavel        int not null,
   idcentroresultado    int not null,
   idempregado          int not null,
   datainicio           date not null,
   datafim              date not null,
   abrangencia          char(1) not null,
   revogada             char(1),
   primary key (iddelegacaocentroresultado)
);

create table gruponivelautoridade
(
   idgrupo              int not null,
   idnivelautoridade    int not null,
   primary key (idgrupo, idnivelautoridade)
);

create table historicorespcentroresultado
(
   idhistoricorespcentroresultado int not null,
   idcentroresultado    int not null,
   idresponsavel        int not null,
   datainicio           date not null,
   datafim              date,
   primary key (idhistoricorespcentroresultado)
);

create table limiteaprovacao
(
   idlimiteaprovacao    int not null,
   tipolimiteporvalor   char(1) not null,
   abrangenciacentroresultado char(1) not null,
   identificacao        varchar(70) not null,
   primary key (idlimiteaprovacao)
);

create table limiteaprovacaoautoridade
(
   idnivelautoridade    int not null,
   idlimiteaprovacao    int not null,
   primary key (idnivelautoridade, idlimiteaprovacao)
);

create table limiteaprovacaoprocesso
(
   idlimiteaprovacao    int not null,
   idprocessonegocio    int not null,
   primary key (idlimiteaprovacao, idprocessonegocio)
);

create table nivelautoridade
(
   idnivelautoridade    int not null,
   nomenivelautoridade  varchar(100) not null,
   hierarquia           int not null,
   situacao             char(1) not null,
   primary key (idnivelautoridade)
);

create table processonegocio
(
   idprocessonegocio    int not null,
   idgrupoexecutor      int,
   idgrupoadministrador int,
   nomeprocessonegocio  varchar(100) not null,
   permissaosolicitacao char(1) not null comment 'T - Todos
            A - Por nivel de autoridade',
   percdispensanovaaprovacao decimal(5,2) not null,
   permiteaprovacaonivelinferior char(1) not null,
   primary key (idprocessonegocio)
);

create table processonivelautoridade
(
   idprocessonegocio    int not null,
   idnivelautoridade    int not null,
   permiteaprovacaopropria char(1) not null,
   permitesolicitacao   char(1) not null,
   antecedenciaminimaaprovacao int not null,
   primary key (idprocessonegocio, idnivelautoridade)
);

create table respcentroresultadoprocesso
(
   idresponsavel        int not null,
   idcentroresultado    int not null,
   idprocessonegocio    int not null,
   primary key (idresponsavel, idcentroresultado, idprocessonegocio)
);

create table responsavelcentroresultado
(
   idresponsavel        int not null,
   idcentroresultado    int not null,
   primary key (idresponsavel, idcentroresultado)
);

create table valorlimiteaprovacao
(
   idvalorlimiteaprovacao int not null,
   idlimiteaprovacao    int not null,
   tipoutilizacao       char(1) not null comment 'T - Todos
            I - Uso interno
            C - Atendimento ao cliente',
   tipolimite           char(1) not null comment 'I - Individual
            M - No mes
            A - No ano
            D - Intervalo dias',
   valorlimite          numeric(10,2) not null,
   intervalodias        int,
   primary key (idvalorlimiteaprovacao)
);

alter table delegcentroresultadofluxo add constraint fk_reference_131 foreign key (iddelegacaocentroresultado)
      references delegacaocentroresultado (iddelegacaocentroresultado) on delete restrict on update restrict;

alter table delegcentroresultadofluxo add constraint fk_reference_132 foreign key (idinstanciafluxo)
      references bpm_instanciafluxo (idinstancia) on delete restrict on update restrict;

alter table delegcentroresultadoprocesso add constraint fk_reference_129 foreign key (iddelegacaocentroresultado)
      references delegacaocentroresultado (iddelegacaocentroresultado) on delete restrict on update restrict;

alter table delegcentroresultadoprocesso add constraint fk_reference_130 foreign key (idprocessonegocio)
      references processonegocio (idprocessonegocio) on delete restrict on update restrict;

alter table delegacaocentroresultado add constraint fk_reference_127 foreign key (idresponsavel, idcentroresultado)
      references responsavelcentroresultado (idresponsavel, idcentroresultado) on delete restrict on update restrict;

alter table delegacaocentroresultado add constraint fk_reference_128 foreign key (idempregado)
      references empregados (idempregado) on delete restrict on update restrict;

alter table gruponivelautoridade add constraint fk_reference_109 foreign key (idnivelautoridade)
      references nivelautoridade (idnivelautoridade) on delete restrict on update restrict;

alter table gruponivelautoridade add constraint fk_reference_110 foreign key (idgrupo)
      references grupo (idgrupo) on delete restrict on update restrict;

alter table historicorespcentroresultado add constraint fk_reference_136 foreign key (idcentroresultado)
      references centroresultado (idcentroresultado) on delete restrict on update restrict;

alter table historicorespcentroresultado add constraint fk_reference_137 foreign key (idresponsavel)
      references empregados (idempregado) on delete restrict on update restrict;

alter table limiteaprovacaoautoridade add constraint fk_reference_134 foreign key (idnivelautoridade)
      references nivelautoridade (idnivelautoridade) on delete restrict on update restrict;

alter table limiteaprovacaoautoridade add constraint fk_reference_135 foreign key (idlimiteaprovacao)
      references limiteaprovacao (idlimiteaprovacao) on delete restrict on update restrict;

alter table limiteaprovacaoprocesso add constraint fk_reference_116 foreign key (idlimiteaprovacao)
      references limiteaprovacao (idlimiteaprovacao) on delete restrict on update restrict;

alter table limiteaprovacaoprocesso add constraint fk_reference_133 foreign key (idprocessonegocio)
      references processonegocio (idprocessonegocio) on delete restrict on update restrict;

alter table processonegocio add constraint fk_reference_120 foreign key (idgrupoexecutor)
      references grupo (idgrupo) on delete restrict on update restrict;

alter table processonegocio add constraint fk_reference_121 foreign key (idgrupoadministrador)
      references grupo (idgrupo) on delete restrict on update restrict;

alter table processonivelautoridade add constraint fk_reference_111 foreign key (idprocessonegocio)
      references processonegocio (idprocessonegocio) on delete restrict on update restrict;

alter table processonivelautoridade add constraint fk_reference_114 foreign key (idnivelautoridade)
      references nivelautoridade (idnivelautoridade) on delete restrict on update restrict;

alter table respcentroresultadoprocesso add constraint fk_reference_124 foreign key (idresponsavel, idcentroresultado)
      references responsavelcentroresultado (idresponsavel, idcentroresultado) on delete restrict on update restrict;

alter table respcentroresultadoprocesso add constraint fk_reference_125 foreign key (idprocessonegocio)
      references processonegocio (idprocessonegocio) on delete restrict on update restrict;

alter table responsavelcentroresultado add constraint fk_reference_122 foreign key (idresponsavel)
      references empregados (idempregado) on delete restrict on update restrict;

alter table responsavelcentroresultado add constraint fk_reference_123 foreign key (idcentroresultado)
      references centroresultado (idcentroresultado) on delete restrict on update restrict;

alter table valorlimiteaprovacao add constraint fk_reference_119 foreign key (idlimiteaprovacao)
      references limiteaprovacao (idlimiteaprovacao) on delete restrict on update restrict;

-- FIM - CARLOS SANTOS - 14/04/2014

-- Início Mário Júnior 15/04/2014

create table rh_histmanualfuncao(
  idhistmanualfuncao int(11) not null,
  idmanualfuncao int(11),
  titulocargo varchar(255),
  titulofuncao varchar(255),
  resumofuncao varchar(255),
  cbo varchar(255),
  codigo varchar(255),

  idformacaora varchar(255),
  ididiomara varchar(255),
  idnivelescritara varchar(255),
  idnivelleiturara varchar(255),  
  idnivelconversara varchar(255),
  expanteriorra varchar(255),

  idformacaorf varchar(255),
  ididiomarf varchar(255),
  idnivelescritarf varchar(255),
  idnivelleiturarf varchar(255),  
  idnivelconversarf varchar(255),
  expanteriorrf varchar(255),

  pesocomplexidade varchar(255),
  pesotecnica varchar(255),
  pesocomportamental varchar(255),
  pesoresultados varchar(255),
  
  dataalteracao date,
  horaalteracao timestamp,
  idusuarioalteracao int(11),
  versao decimal(11,1),
  
  primary key (idhistmanualfuncao)
) engine=innodb;

create table rh_histatribuicaoresponsabilidade (
  idhistatribuicaoresponsabilidade int(11) not null,
  descricaoperspectivacomplexidade varchar(255),
  idnivel int(11) not null,
  idmanualfuncao int(11) not null,
  idhistmanualfuncao int(11) not null,
  
  dataalteracao date,
  horaalteracao timestamp,
  idusuarioalteracao int(11),
  
  primary key (idhistatribuicaoresponsabilidade)
) engine=innodb;

create table rh_histperspectivacomportamental (
  idhistperspectivacomportamental int(11) not null,
  cmbcompetenciacomportamental varchar(255),
  comportamento varchar(255),
  idmanualfuncao int(11) not null,
  idhistmanualfuncao int(11) not null,
  
  dataalteracao date,
  horaalteracao timestamp,
  idusuarioalteracao int(11),
  
  primary key (idhistperspectivacomportamental)
) engine=innodb;

create table rh_histmanualcertificacao(
  idhistmanualcertificacao int(11) not null,
  descricao varchar(255),
  detalhe varchar(255),
  idmanualfuncao int(11) not null,
  idhistmanualfuncao int(11) not null,
  raourf varchar(255),
  
  dataalteracao date,
  horaalteracao timestamp,
  idusuarioalteracao int(11),
  
  primary key (idhistmanualcertificacao)
) engine=innodb;

create table rh_histmanualcurso(
  idhistmanualcurso int(11) not null,
  descricao varchar(255),
  detalhe varchar(255),
  idmanualfuncao int(11) not null,
  idhistmanualfuncao int(11) not null,
  raourf varchar(255),
  
  dataalteracao date,
  horaalteracao timestamp,
  idusuarioalteracao int(11),
  
  primary key (idhistmanualcurso)
) engine=innodb;

create table rh_histmanualcompetenciatecnica (
  idhistmanualcompetenciatecnica int(11) not null,
  descricao varchar(255),
  situacao varchar(255),
  idmanualfuncao int(11) not null,
  idhistmanualfuncao int(11) not null,
  idnivelcompetenciaacesso varchar(255),
  idnivelcompetenciafuncao varchar(255),
  
  dataalteracao date,
  horaalteracao timestamp,
  idusuarioalteracao int(11),
  
  primary key (idhistmanualcompetenciatecnica)
) engine=innodb;

-- Fim - Mário Júnior 15/04/2014

-- INÍCIO - RODRIGO PECCI ACORSE 17/04/2014

alter table rh_perspectivacomportamental add descricaoperspectivacomportamental text;
alter table rh_perspectivacomportamental add detalheperspectivacomportamental text;

-- FIM - RODRIGO PECCI ACORSE 17/04/2014

-- INÍCIO - EULER RAMOS 17/04/2014

alter table rh_requisicaopessoal add idfuncao int(11);

-- FIM - EULER RAMOS

-- INÍCIO - valdoilo.damasceno 20/04/2014

-- Inclui campo idrequisicaofuncao em rh_manualfuncao
ALTER TABLE rh_manualfuncao ENGINE = InnoDB;
ALTER TABLE rh_requisicaofuncao ENGINE = InnoDB;
ALTER TABLE cargos ENGINE = InnoDB;
ALTER TABLE rh_manualfuncao ADD COLUMN idrequisicaofuncao INT(11) NOT NULL AFTER idmanualfuncao;
ALTER TABLE rh_manualfuncao  ADD INDEX fk_solicitacaoservico_idx (idrequisicaofuncao ASC);
ALTER TABLE rh_manualfuncao ADD CONSTRAINT fk_solicitacaoservico FOREIGN KEY (idrequisicaofuncao) REFERENCES rh_requisicaofuncao (idsolicitacaoservico);
  
-- Inclui campo idcargo em rh_manualfuncao
ALTER TABLE rh_manualfuncao ADD COLUMN idcargo INT(11) NOT NULL AFTER idrequisicaofuncao;
ALTER TABLE rh_manualfuncao ADD INDEX fk_idcargo_idx (idcargo ASC);
ALTER TABLE rh_manualfuncao ADD CONSTRAINT fk_idcargo FOREIGN KEY (idcargo) REFERENCES cargos (idcargo);

-- FIM - valdoilo.damasceno 20/04/2014

-- Inicio - euler.ramos 21/04/2014

alter table rh_requisicaopessoal change column idcargo idcargo char(100) null;

create table rh_historicocandidato (
idHistoricoCandidato int(11) NOT NULL AUTO_INCREMENT,
idEntrevista int(11) DEFAULT NULL,
idcurriculo int(11) DEFAULT NULL,
resultado varchar(254) DEFAULT NULL,
idSolicitacaoServico int(11) DEFAULT NULL,
nome varchar(254) DEFAULT NULL,
funcao varchar(254) DEFAULT NULL,
PRIMARY KEY (idHistoricoCandidato),
KEY FK_historicocandidato_curriculo (idcurriculo)
) ENGINE=InnoDB;

alter table rh_historicoacaocurriculo add idusuario int(11);

ALTER TABLE rh_entrevistacandidato ADD COLUMN identrevistadorrh INT(11) NULL , ADD COLUMN identrevistadorgestor INT(11) NULL;

ALTER TABLE rh_entrevistacandidato CHANGE COLUMN identrevistador identrevistador int(11) NULL;

ALTER TABLE rh_entrevistacandidato CHANGE COLUMN tipoentrevista tipoentrevista VARCHAR(20) NULL;

ALTER TABLE rh_requisicaopessoal CHANGE COLUMN vagas vagas INT(11) NULL;

ALTER TABLE rh_requisicaopessoal CHANGE COLUMN idcentrocusto idcentrocusto INT(11) NULL;

ALTER TABLE controlefinanceiroviagem ADD COLUMN iditemtrabalho  INT(11) NULL;

ALTER TABLE integranteviagem ADD COLUMN emprestacaocontas varchar(1);

ALTER TABLE requisicaoviagem ADD COLUMN iditemtrabalho  INT(11) NULL;

ALTER TABLE requisicaoviagem ADD COLUMN cancelarrequisicao varchar(1);

-- FIM - euler.ramos 21/04/2014

-- Inicio - mario.haysaki 30/04/20014

ALTER TABLE rh_curriculo add COLUMN nacionalidade varchar(100);

ALTER TABLE rh_curriculo add COLUMN idcidadenatal int(11);

ALTER TABLE rh_curriculo add COLUMN idestadonatal int(11);

ALTER TABLE rh_curriculo CHANGE COLUMN cidadenatal cidadenatal VARCHAR(100) DEFAULT NULL ;

-- Fim - mario.haysaki 30/04/2014

--Inicio - renato.jesus 30/04/2014

ALTER TABLE rh_certificacaocurriculo CHANGE COLUMN versao versao CHAR(100) NULL;
ALTER TABLE rh_certificacaocurriculo CHANGE COLUMN validade validade INT(11) NULL;

-- FIM - renato.jesus 30/04/2014

-- INICIO - renato.jesus - 25/04/2014

CREATE TABLE formulaos (
	idformulaos INT(11),
	descricao VARCHAR (254),
	formula VARCHAR (254),
	situacao CHAR(1) DEFAULT 'A',
	PRIMARY KEY (idformulaos)
);

ALTER TABLE atividadesservicocontrato ADD estruturaformulaos VARCHAR(254);
ALTER TABLE atividadesservicocontrato ADD formulacalculofinal VARCHAR (254);

CREATE TABLE contratoformulaos (
	idcontratoformulaos INT(11),
	idcontrato INT(11),
	idformulaos INT(11),
	deleted char(1) DEFAULT 'N',
	PRIMARY KEY (idcontratoformulaos)
);

-- FIM - renato.jesus - 25/04/2014

-- INICIO Bruno.aquino - 02/05/2014

INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (1,'Horas * Complexidade * Dias Úteis','vValor{horas}*vComplexidade*vDiasUteis','A');
INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (2,'Horas * Complexidade * Dias Corridos','vValor{horas}*vComplexidade*{Periodo}vDiasCorridos','A');
INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (3,'Horas * Complexidade * Quantidade Mensal','vValor{horas}*vComplexidade*{Quantidade}vValor{Periodo}{Mensal}','A');
INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (4,'Horas * Complexidade * Quantidade Semanal','vValor{horas}*vComplexidade*{Quantidade}vValor{Periodo}{Semanal}','A');
INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (5,'Horas * Complexidade * Quantidade Diário','vValor{horas}*vComplexidade*{Quantidade}vValor{Periodo}{Diário}','A');
INSERT INTO formulaos (idformulaos,descricao,formula,situacao) VALUES (6,'(minutos/minutos horas X Complexidade) X NU','(vValor/vValor{Horas}*vComplexidade)*vValor{NU}','A');

INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (1,1,1,'N');
INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (2,1,2,'N');
INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (3,1,3,'N');
INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (4,1,4,'N');
INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (5,1,5,'N');
INSERT INTO contratoformulaos (idcontratoformulaos,idcontrato,idformulaos,deleted) VALUES (6,1,6,'N');

-- Fim - Bruno.aquino - 25/04/2014

-- Inicio - thiago.borges 07/05/2014
CREATE  TABLE rh_treinamentocurriculo(
idtreinamento INT NOT NULL ,
idcurso INT NOT NULL ,
idcurriculo INT NOT NULL ,
PRIMARY KEY (idtreinamento) ,
CONSTRAINT idcursofk FOREIGN KEY (idcurso) REFERENCES rh_curso (idcurso)
ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT idcurriculofk FOREIGN KEY (idcurriculo) REFERENCES rh_curriculo (idcurriculo)
ON DELETE NO ACTION ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
-- FIM - thiago.borges 07/05/2014

-- Inicio - mario.haysaki 07/05/20014
ALTER TABLE rh_experienciaprofissionalcurriculo CHANGE COLUMN funcao funcao varchar(200) DEFAULT NULL ;
ALTER TABLE rh_curriculo CHANGE COLUMN idnacionalidade idnacionalidade int(11) DEFAULT NULL;
-- Fim - mario.haysaki  07/05/2014

-- inicio - flavio.santana 15/05/2014

create table bi_dashboard (
   iddashboard          int(11)                 not null,
   tipo                 char(1)              not null,
   idusuario            int(11)                 null,
   nomedashboard        varchar(150)         null,
   identificacao        varchar(70)          null,
   situacao             char(1)              null,
   parametros           text                 null,
   naoatualizbase       char(1)              null,
   temporefresh         int(11)                 null,
   constraint pk_bi_dashboard primary key (iddashboard)
) engine = innodb;

create  index ix_ident_dash on bi_dashboard (
identificacao
);

create table bi_itemdashboard (
   iditemdashboard      int(11)                 not null,
   titulo               varchar(150)         not null,
   iddashboard          int(11)                 not null,
   idconsulta           int(11)                 null,
   posicao              int(11)                 null,
   itemtop              int(11)                 null,
   itemleft             int(11)                 null,
   itemwidth            int(11)                 null,
   itemheight           int(11)                 null,
   parmssubst           text                 null,
   constraint pk_bi_itemdashboard primary key (iditemdashboard)
) engine = innodb;

create  index ix_id_dash on bi_itemdashboard (
iddashboard
);

alter table bi_consulta engine = innodb ;

alter table bi_itemdashboard
   add constraint fk_bi_itemd_reference_bi_dashb foreign key (iddashboard)
      references bi_dashboard (iddashboard);
      
alter table bi_itemdashboard
   add constraint fk_bi_itemd_reference_bi_consu foreign key (idconsulta)
      references bi_consulta (idconsulta)
      on delete restrict on update restrict;
      
create table bi_dashboardsegur (
   iddashboard          int(11)                 not null,
   idgrupo              int(11)                 not null,
   constraint pk_bi_dashboardsegur primary key (iddashboard, idgrupo)
) engine = innodb;

alter table grupo engine = innodb;

alter table bi_dashboardsegur
   add constraint fk_bi_dashb_reference_bi_dashb foreign key (iddashboard)
      references bi_dashboard (iddashboard)
      on delete restrict on update restrict;
      
alter table bi_dashboardsegur
   add constraint fk_bi_dashb_reference_grupo foreign key (idgrupo)
      references grupo (idgrupo)
      on delete restrict on update restrict;
      
-- fim - flavio.santana

set sql_safe_updates = 1;
