set sql_safe_updates = 0;

-- INICIO - MURILO GABRIEL RODRIGUES - 18/03/2014

ALTER TABLE matrizcomunicacaotiporegistro ENGINE = INNODB;
ALTER TABLE matrizcomunicacaofrequencia ENGINE = INNODB;
ALTER TABLE matrizcomunicacaoformacontato ENGINE = INNODB;

CREATE TABLE matrizcomunicacao (
  idmatrizcomunicacao int(11) NOT NULL,
  idcontrato int(11) NOT NULL,
  grupoenvolvido int(11) NOT NULL,
  responsabilidades text NOT NULL,
  idtiporegistro int(11),
  idfrequencia int(11),
  idformacontato int(11),
  deleted char(1) default null,
  PRIMARY KEY (idmatrizcomunicacao),
  constraint foreign key (idcontrato) references contratos (idcontrato),
  constraint foreign key (idtiporegistro) references matrizcomunicacaotiporegistro (idtiporegistro),
  constraint foreign key (idfrequencia) references matrizcomunicacaofrequencia (idfrequencia),
  constraint foreign key (idformacontato) references matrizcomunicacaoformacontato (idformacontato)
) ENGINE=InnoDB;

-- FIM - MURILO GABRIEL RODRIGUES - 18/03/2014

-- INICIO - MARIO HAYASAKI JUNIOR - 21/03/2014

ALTER TABLE pedidoportal DROP FOREIGN KEY rel_pedidosolicitacao_usuario;
DROP index rel_pedidosolicitacao_usuario on pedidoportal;
ALTER TABLE pedidoportal change column idusuario idempregado int null;
ALTER TABLE pedidoportal ADD CONSTRAINT rel_pedidosolicitacao_empregado FOREIGN KEY ( idempregado ) REFERENCES empregados ( idempregado )  ON DELETE NO ACTION ON UPDATE NO ACTION;

-- FIM - MARIO HAYASAKI JUNIOR - 21/03/2014

-- INICIO - BRUNO CARVALHO DE AQUINO - 28/03/2014

ALTER TABLE dicionario ADD personalizado CHAR(1) DEFAULT 'N';

-- FIM - BRUNO CARVALHO DE AQUINO - 28/03/2014

-- INICIO - RODRIGO PECCI ACORSE - 09/11/2013

ALTER TABLE atividadeperiodica CHANGE COLUMN criadopor criadopor VARCHAR(256);
ALTER TABLE atividadeperiodica CHANGE COLUMN alteradopor alteradopor VARCHAR(256);

-- FIM - RODRIGO PECCI ACORSE - 09/11/2013
-- Inicio - MÁRIO HAYASAKI JÚNIOR - 14/07/2014
alter table empregados modify telefone varchar(100);
-- FIM

set sql_safe_updates = 1;
