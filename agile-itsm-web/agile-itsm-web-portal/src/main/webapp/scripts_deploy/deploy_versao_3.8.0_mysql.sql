set sql_safe_updates = 0;

-- INICIO - CARLOS ALBERTO DOS SANTOS 23/07/2014

alter table rest_operation add generatelog char(1);
update rest_operation set generatelog = 'Y';

-- FIM

-- INICIO - EULER JOSE RAMOS 19/08/2014

CREATE TABLE IF NOT EXISTS grupoassinatura (
  idgrupoassinatura INT(11) NOT NULL ,
  titulo VARCHAR(254) NOT NULL ,
  datainicio DATE NOT NULL ,
  datafim DATE NULL ,
  PRIMARY KEY (idgrupoassinatura))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS assinatura (
  idassinatura INT(11) NOT NULL ,
  idempregado INT(11) NULL ,
  papel VARCHAR(254) NULL ,
  fase VARCHAR(254) NULL ,
  datainicio DATE NOT NULL ,
  datafim DATE NULL ,
  PRIMARY KEY (idassinatura))
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS itemgrupoassinatura (
  iditemgrupoassinatura INT(11) NOT NULL ,
  idgrupoassinatura INT(11) NOT NULL ,
  idassinatura INT(11) NOT NULL ,
  ordem INT(11) NOT NULL ,
  datainicio DATE NOT NULL ,
  datafim DATE NULL ,
  PRIMARY KEY (iditemgrupoassinatura))
ENGINE = InnoDB;

alter table os add idgrupoassinatura INT(11);

-- FIM

-- Inicio MARIO HAYASAKI JUNIOR 28/08/2014
CREATE TABLE imagemitemconfiguracaorelacao (
  idimagemitemconfiguracaorel INT NOT NULL,
  idimagemitemconfiguracao INT NOT NULL,
  idImagemItemConfiguracaoPai INT NOT NULL,
  PRIMARY KEY (idimagemitemconfiguracaorel));
-- Fim

-- Inicio - renato.jesus - 25/09/2014

create table if not exists roteiroviagem (
   idroteiroviagem      int  not null,
   datainicio           date not null,
   datafim              date,
   idsolicitacaoservico bigint(20) not null,
   idintegrante         int,
   origem               int,
   destino              int,
   ida                  date not null,
   volta                date,
   primary key (idroteiroviagem),
   key idx_idsolicitacaoservico (idsolicitacaoservico),
   constraint fk_roteiro_requisicaoviagem foreign key (idsolicitacaoservico) references requisicaoviagem (idsolicitacaoservico) on delete restrict on update restrict
) engine = InnoDB;

create table if not exists despesaviagem (
   iddespesaviagem             int  not null,
   datainicio                  date not null,
   datafim                     date,
   idroteiro                   int  not null,
   idtipo                      int  not null,
   idparceiro                  int  not null,
   valor                       decimal(8,2),
   validade                    datetime,
   original                    char(1) not null default 'N',
   idsolicitacaoservico        bigint(20) not null,
   prestacaocontas             char(1) null,
   situacao                    varchar(80),
   quantidade                  int not null,
   datahoracompra              datetime null,
   idresponsavelcompra         int,
   idmoeda                     int,
   idformapagamento            int,
   observacoes                 text,
   primary key (iddespesaviagem),
   key idx_idsolicitacaoservico (idsolicitacaoservico),
   key fk_despesa_roteiroviagem (idroteiro),
   constraint fk_despesa_roteiroviagem foreign key (idroteiro) references roteiroviagem (idroteiroviagem) on delete restrict on update restrict,
   key fk_despesaviagem_parceiro (idparceiro),
   constraint fk_despesaviagem_parceiro foreign key (idparceiro) references rh_parceiro (idparceiro) on delete restrict on update restrict,
   key fk_despesa_tipomovfinviagem (idtipo),
   constraint fk_despesa_tipomovfinviagem foreign key (idtipo) references tipomovimfinanceiraviagem (idtipomovimfinanceiraviagem) on delete restrict on update restrict,
   key fk_despesaviagem_moeda (idmoeda),
   constraint fk_despesaviagem_moeda foreign key (idmoeda) references moedas (idmoeda) on delete restrict on update restrict,
   key fk_despesaviagem_formapagto (idformapagamento),
   constraint fk_despesaviagem_formapagto foreign key (idformapagamento) references formapagamento (idformapagamento) on delete restrict on update restrict,
   key fk_despesaviagem_respconfirma (idresponsavelcompra),
   constraint fk_despesaviagem_respconfirma foreign key (idresponsavelcompra) references empregados (idempregado) on delete restrict on update restrict
) engine = InnoDB;

alter table requisicaoviagem drop foreign key fk_requisicaoviagem_reference_centroresultado;

alter table requisicaoviagem change column idcentrocusto idcentroresultado int(11) not null;

alter table requisicaoviagem add constraint fk_reqviagem_centroresultado foreign key (idcentroresultado) references centroresultado (idcentroresultado);

-- Fim - renato.jesus - 25/09/2014

-- Inicio - thiago.borges - 26/09/2014
-- Eliminar as referências à tabela [integranteviagem] primeiro
alter table adiantamentoviagem drop foreign key fk_adiantamentoviagem_integranteviagem;
alter table itemcontrolefinanceiroviagem drop foreign key fk_itemcontrolefinaceiroviagem_solicitacaoservico_empregado;
alter table prestacaocontasviagem drop foreign key fk_prestacaocontasviagem_solicitacaoservico_empregado;

alter table integranteviagem drop foreign key rel_integranteviagem_solserv;
alter table integranteviagem drop foreign key fk_integranteviagem_reference_solicitacaoservico;
alter table integranteviagem drop foreign key rel_integranteviagem_empregado;
alter table integranteviagem drop foreign key fk_integranteviagem_empregados;
alter table integranteviagem drop foreign key fk_integranteviagem_reference_empregados;
alter table integranteviagem drop index fk_integranteviagem_empregados;
alter table integranteviagem drop index rel_integranteviagem_empregado;

-- Adicionar as novas colunas na tabela [integranteviagem]
ALTER TABLE integranteviagem ADD COLUMN idintegranteviagem int(11) NOT NULL FIRST , 
ADD COLUMN integrantefuncionario VARCHAR(1) NULL  AFTER idrespprestacaocontas , 
ADD COLUMN nome VARCHAR(255) NULL DEFAULT NULL  AFTER integrantefuncionario , 
ADD COLUMN infonaofuncionario TEXT NULL DEFAULT NULL  AFTER idtarefa ,
ADD COLUMN estado VARCHAR(255) NULL DEFAULT NULL  AFTER infonaofuncionario,
DROP PRIMARY KEY, ADD PRIMARY KEY (idintegranteviagem);

ALTER TABLE integranteviagem
ADD KEY fk_integrante_empregado(idempregado),
ADD CONSTRAINT fk_integrante_empregado FOREIGN KEY (idempregado) REFERENCES empregados (idempregado),
ADD KEY fk_integrante_resprestacaocontas (idrespprestacaocontas),
ADD CONSTRAINT fk_integrante_resprestacaocontas FOREIGN KEY (idrespprestacaocontas) REFERENCES empregados (idempregado),
ADD KEY fk_integrante_reqviagem(idsolicitacaoservico),
ADD CONSTRAINT fk_integrante_reqviagem FOREIGN KEY (idsolicitacaoservico) REFERENCES requisicaoviagem (idsolicitacaoservico);

alter table tipomovimfinanceiraviagem change column exigejustificativa exigedatahoracotacao char(1);
-- Fim - thiago.borges - 26/09/2014

-- INICIO - DAVID RODRIGUES DA SILVA 02/10/2014
UPDATE menu SET    datafim = '2014-08-08' WHERE  link = '/controleFinanceiroViagemImprevisto/controleFinanceiroViagemImprevisto.load' AND nome = '$menu.nome.controleFinanceiroViagemImprevisto';
-- FIM

set sql_safe_updates = 1;