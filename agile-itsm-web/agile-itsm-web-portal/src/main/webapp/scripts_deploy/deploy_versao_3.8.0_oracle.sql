
-- INICIO - CARLOS ALBERTO DOS SANTOS 23/07/2014

alter table rest_operation add generatelog char(1);
update rest_operation set generatelog = 'Y';

-- FIM

-- INICIO - EULER JOSE RAMOS 19/08/2014

CREATE TABLE grupoassinatura (
  idgrupoassinatura number(10,0) NOT NULL ,
  titulo VARCHAR(254) NOT NULL ,
  datainicio DATE NOT NULL ,
  datafim DATE NULL ,
  PRIMARY KEY (idgrupoassinatura));

CREATE TABLE assinatura (
  idassinatura number(10,0) NOT NULL ,
  idempregado number(10,0) NULL ,
  papel VARCHAR(254) NULL ,
  fase VARCHAR(254) NULL ,
  datainicio DATE NOT NULL ,
  datafim DATE NULL ,
  PRIMARY KEY (idassinatura));

CREATE  TABLE itemgrupoassinatura (
  iditemgrupoassinatura number(10,0) NOT NULL ,
  idgrupoassinatura number(10,0) NOT NULL ,
  idassinatura number(10,0) NOT NULL ,
  ordem number(10,0) NOT NULL ,
  datainicio DATE NOT NULL ,
  datafim DATE NULL ,
  PRIMARY KEY (iditemgrupoassinatura));
  
alter table os add idgrupoassinatura number(10,0);

-- FIM
-- Inicio MARIO HAYASAKI JUNIOR 28/08/2014
CREATE TABLE imagemitemconfiguracaorelacao (
  idimagemitemconfiguracaorel NUMBER(11) NOT NULL,
  idimagemitemconfiguracao NUMBER(11) NOT NULL,
  idImagemItemConfiguracaoPai NUMBER(11) NOT NULL,
  PRIMARY KEY (idimagemitemconfiguracaorel));
   --FIM

-- INICIO - DAVID RODRIGUES DA SILVA 21/08/2014

ALTER TABLE RH_IDIOMA MODIFY (DETALHE VARCHAR2(100) NULL);
ALTER TABLE RH_CURRICULO MODIFY (IDNACIONALIDADE NUMBER(10) NULL);
ALTER TABLE RH_ENDERECOCURRICULO MODIFY (IDUF NUMBER(10) NULL);
ALTER TABLE RH_ENDERECOCURRICULO MODIFY (COMPLEMENTO VARCHAR2(100) NULL);
ALTER TABLE RH_CERTIFICACAOCURRICULO MODIFY (VERSAO CHAR(100) NULL);
ALTER TABLE RH_CERTIFICACAOCURRICULO MODIFY (VALIDADE NUMBER(38) NULL);
ALTER TABLE RH_EXPERIENCIAPROFISSIONALCURR DROP COLUMN IDREQUISICAOMUDANCA;
ALTER TABLE RH_FUNCAOEXPERIENCIAPROFISSION RENAME COLUMN "idfuncao" TO IDFUNCAO;
ALTER TABLE RH_FUNCAOEXPERIENCIAPROFISSION RENAME COLUMN "idexperienciaprofissionalcurri" TO IDEXPERIENCIAPROFISSIONALCURRI;
ALTER TABLE RH_FUNCAOEXPERIENCIAPROFISSION RENAME COLUMN "nomefuncao" TO NOMEFUNCAO;
ALTER TABLE RH_FUNCAOEXPERIENCIAPROFISSION RENAME COLUMN "descricaofuncao" TO DESCRICAOFUNCAO;

-- FIM

-- INICIO - DAVID RODRIGUES DA SILVA 26/08/2014

-- INICIO SCRIPT QUE NÃO FORAM EXECUTADOS NO deploy_versao_3.4.0_oracle.sql

ALTER TABLE importardados MODIFY (HORAEXECUCAO VARCHAR2(10));

CREATE TABLE RH_COMPETENCIATECNICA (
    IDCOMPETENCIASTECNICAS INT NOT NULL,
    DESCRICAOCOMPTECNICAS VARCHAR(200) DEFAULT NULL,
    NIVELCOMPETENCIASTECNICAS INT DEFAULT NULL,
    IDSOLICITACAOSERVICO INT DEFAULT NULL,
    PRIMARY KEY (IDCOMPETENCIASTECNICAS),
    CONSTRAINT IDSOLICITACAOSERVICO_COMP FOREIGN KEY (IDSOLICITACAOSERVICO)
        REFERENCES RH_REQUISICAOFUNCAO (IDSOLICITACAOSERVICO)
);

CREATE TABLE RH_REQUISICAOFUNCAO (
    IDSOLICITACAOSERVICO INT NOT NULL,
    NOMEFUNCAO VARCHAR(200) NULL,
    NUMEROPESSOAS INT NULL,
    POSSUISUBORDINADOS VARCHAR(45) NULL,
    JUSTIFICATIVAFUNCAO VARCHAR(500) NULL,
    RESUMOATIVIDADES VARCHAR(500) NULL,
    REQUISICAOVALIDA VARCHAR(45) NULL,
    JUSTIFICATIVAVALIDACAO VARCHAR(45) NULL,
    COMPLEMENTOJUSTIFVALIDACAO VARCHAR(500) NULL,  
    IDCARGO INT NULL,
    FUNCAO VARCHAR(100) NULL,
    RESUMOFUNCAO VARCHAR(500) NULL,
    DESCRICAOVALIDA VARCHAR(45) NULL,
    JUSTIFICATIVADESCRICAOFUNCAO VARCHAR(45) NULL,
    COMPLEMENTOJUSTIFDESCFUNCAO VARCHAR(500) NULL, 
    FASE VARCHAR(45) NULL,
    PRIMARY KEY (IDSOLICITACAOSERVICO)
);

CREATE TABLE RH_PERSPECTIVACOMPORTAMFUNCAO (  
    IDPERSPECTIVA INT NOT NULL,
    DESCRICAOPERSPECTIVA VARCHAR(200) DEFAULT NULL,
    DETALHEPERSPECTIVA VARCHAR(500) DEFAULT NULL,
    IDSOLICITACAOSERVICO INT DEFAULT NULL,
    PRIMARY KEY (IDPERSPECTIVA),
    CONSTRAINT FK_PERSPECTIVA_REQUISICAO FOREIGN KEY (IDSOLICITACAOSERVICO)
        REFERENCES RH_REQUISICAOFUNCAO (IDSOLICITACAOSERVICO)
);

CREATE TABLE RH_PERSPECTIVACOMPLEXIDADE (
    IDPERSPECTIVACOMPLEXIDADE INT NOT NULL,
    DESCPERSPECTIVACOMPLEXIDADE VARCHAR(200) DEFAULT NULL,
    NIVELPERSPECTIVACOMPLEXIDADE INT DEFAULT NULL,
    IDSOLICITACAOSERVICO INT DEFAULT NULL,
    PRIMARY KEY (IDPERSPECTIVACOMPLEXIDADE),
    CONSTRAINT IDSOLICITACAOSERVICO_COMPL FOREIGN KEY (IDSOLICITACAOSERVICO)
        REFERENCES RH_REQUISICAOFUNCAO (IDSOLICITACAOSERVICO)
);

CREATE TABLE RH_PERSPECTIVATECFORMCADEMICA (
    IDPERSPECTIVATECFORMACADEMICA INT NOT NULL,
    DESCRICAOFORMACAOACADEMICA VARCHAR(200) DEFAULT NULL,
    DETALHEFORMACAOACADEMICA VARCHAR(500) DEFAULT NULL,
    OBRIGATORIOFORMACAOACADEMICA VARCHAR(1) DEFAULT NULL,
    IDSOLICITACAOSERVICO INT DEFAULT NULL,
    PRIMARY KEY (IDPERSPECTIVATECFORMACADEMICA),
    CONSTRAINT IDSOLICITACAOSERVICO_FORM FOREIGN KEY (IDSOLICITACAOSERVICO)
        REFERENCES RH_REQUISICAOFUNCAO (IDSOLICITACAOSERVICO)
);

CREATE TABLE RH_PERSPECTIVATECCERTIFICACAO (
    IDPERSPECTIVATECCERTIFICACAO INT NOT NULL,
    DESCRICAOCERTIFICACAO VARCHAR(200) DEFAULT NULL,
    VERSAOCERTIFICACAO VARCHAR(500) DEFAULT NULL,
    OBRIGATORIOCERTIFICACAO VARCHAR(1) DEFAULT NULL,
    IDSOLICITACAOSERVICO INT DEFAULT NULL,
    PRIMARY KEY (IDPERSPECTIVATECCERTIFICACAO),
    CONSTRAINT IDSOLICITACAOSERVICO_CERT FOREIGN KEY (IDSOLICITACAOSERVICO)
        REFERENCES RH_REQUISICAOFUNCAO (IDSOLICITACAOSERVICO)
);

CREATE TABLE RH_PERSPECTIVATECNICACURSO (
    IDPERSPECTIVATECNICACURSO INT NOT NULL,
    DESCRICAOCURSO VARCHAR(200) DEFAULT NULL,
    DETALHECURSO VARCHAR(500) DEFAULT NULL,
    OBRIGATORIOCURSO VARCHAR(1) DEFAULT NULL,
    IDSOLICITACAOSERVICO INT DEFAULT NULL,
    PRIMARY KEY (IDPERSPECTIVATECNICACURSO),
    CONSTRAINT IDSOLICITACAOSERVICO_CURS FOREIGN KEY (IDSOLICITACAOSERVICO)
        REFERENCES RH_REQUISICAOFUNCAO (IDSOLICITACAOSERVICO)
);

CREATE TABLE RH_PERSPECTIVATECNICAIDIOMA (
    IDPERSPECTIVATECNICAIDIOMA INT NOT NULL,
    DESCRICAOIDIOMA VARCHAR(200) DEFAULT NULL,
    DETALHEIDIOMA VARCHAR(500) DEFAULT NULL,
    OBRIGATORIOIDIOMA VARCHAR(1) DEFAULT NULL,
    IDSOLICITACAOSERVICO INT DEFAULT NULL,
    PRIMARY KEY (IDPERSPECTIVATECNICAIDIOMA),
    CONSTRAINT IDSOLICITACAOSERVICO_IDIO FOREIGN KEY (IDSOLICITACAOSERVICO)
        REFERENCES RH_REQUISICAOFUNCAO (IDSOLICITACAOSERVICO)
);

CREATE TABLE RH_PERSPECTIVATECEXPERIENCIA (
    IDPERSPECTIVATECEXPERIENCIA INT NOT NULL,
    DESCRICAOEXPERIENCIA VARCHAR(200) DEFAULT NULL,
    DETALHEEXPERIENCIA VARCHAR(500) DEFAULT NULL,
    OBRIGATORIOEXPERIENCIA VARCHAR(1) DEFAULT NULL,
    IDSOLICITACAOSERVICO INT DEFAULT NULL,
    PRIMARY KEY (IDPERSPECTIVATECEXPERIENCIA),
    CONSTRAINT IDSOLICITACAOSERVICO_EXPER FOREIGN KEY (IDSOLICITACAOSERVICO)
        REFERENCES RH_REQUISICAOFUNCAO (IDSOLICITACAOSERVICO)
);

ALTER TABLE RH_REQUISICAOPESSOAL ADD IDLOTACAO NUMBER (10,0) DEFAULT NULL;
ALTER TABLE RH_CARGAHORARIA ADD ENTRADA NUMBER (10,0) DEFAULT NULL;
ALTER TABLE RH_CARGAHORARIA ADD SAIDA NUMBER (10,0) DEFAULT NULL;

CREATE TABLE RH_IDIOMACURRICULO (
    IDIDIOMA INTEGER NOT NULL,
    IDCURRICULO INTEGER NOT NULL,
    IDNIVELCONVERSA INTEGER NOT NULL,
    IDNIVELESCRITA INTEGER NOT NULL,
    IDNIVELLEITURA INTEGER NOT NULL,
    PRIMARY KEY (IDIDIOMA , IDCURRICULO)
);

ALTER TABLE RH_PERSPCOMPORTAMENTAL ADD (DESCPERSPECTIVACOMPORTAMENTAL CLOB);
ALTER TABLE RH_PERSPCOMPORTAMENTAL ADD (DETPERSPECTIVACOMPORTAMENTAL CLOB);

-- FIM SCRIPT QUE NÃO FORAM EXECUTADOS NO deploy_versao_3.4.0_oracle.sql 

-- INICIO SCRIPT QUE NÃO FORAM EXECUTADOS NO deploy_versao_3.7.0_oracle.sql

ALTER TABLE RH_PERSPECTIVATECFORMCADEMICA ADD IDFORMACAOACADEMICA INTEGER NOT NULL;
ALTER TABLE RH_PERSPECTIVATECFORMCADEMICA ADD CONSTRAINT FK_FORMACAOACADEMICA FOREIGN KEY (IDFORMACAOACADEMICA) REFERENCES RH_FORMACAOACADEMICA (IDFORMACAOACADEMICA);
ALTER TABLE RH_PERSPECTIVATECCERTIFICACAO ADD IDCERTIFICACAO INTEGER NOT NULL;
ALTER TABLE RH_PERSPECTIVATECCERTIFICACAO ADD CONSTRAINT FK_CERTIFICACAO FOREIGN KEY (IDCERTIFICACAO) REFERENCES RH_CERTIFICACAO (IDCERTIFICACAO);
ALTER TABLE RH_PERSPECTIVATECNICACURSO ADD IDCURSO INTEGER NOT NULL;
ALTER TABLE RH_PERSPECTIVATECNICACURSO ADD CONSTRAINT FK_CURSO FOREIGN KEY (IDCURSO) REFERENCES RH_CURSO (IDCURSO)
ALTER TABLE RH_PERSPECTIVATECNICAIDIOMA ADD IDIDIOMA INTEGER NOT NULL;
ALTER TABLE RH_PERSPECTIVATECNICAIDIOMA ADD CONSTRAINT FK_IDIOMA FOREIGN KEY (IDIDIOMA) REFERENCES RH_IDIOMA (IDIDIOMA);
ALTER TABLE RH_PERSPECTIVATECEXPERIENCIA ADD IDCONHECIMENTO INTEGER NOT NULL;
ALTER TABLE RH_PERSPECTIVATECEXPERIENCIA ADD CONSTRAINT FK_CONHECIMENTO FOREIGN KEY (IDCONHECIMENTO) REFERENCES RH_CONHECIMENTO (IDCONHECIMENTO);
ALTER TABLE RH_PERSPECTIVACOMPORTAMFUNCAO ADD IDATITUDEINDIVIDUAL INTEGER NOT NULL;
ALTER TABLE RH_PERSPECTIVACOMPORTAMFUNCAO ADD CONSTRAINT FK_ATITUDEINDIVIDUAL FOREIGN KEY (IDATITUDEINDIVIDUAL) REFERENCES RH_ATITUDEINDIVIDUAL (IDATITUDEINDIVIDUAL);

-- FIM

-- Inicio - renato.jesus - 25/09/2014

create table ROTEIROVIAGEM (
   idroteiroviagem      number(10) not null,
   datainicio           date not null,
   datafim              date,
   idsolicitacaoservico number(10) not null,
   idintegrante         number(10),
   origem               number(10),
   destino              number(10),
   ida                  date not null,
   volta                date,
   primary key (idroteiroviagem),
   constraint fk_roteiro_requisicaoviagem foreign key (idsolicitacaoservico) references requisicaoviagem (idsolicitacaoservico)
);

create index idx_idsolicitacaoservico on roteiroviagem (idsolicitacaoservico);

create table DESPESAVIAGEM (
   iddespesaviagem             number(10) not null,
   datainicio                  date not null,
   datafim                     date,
   idroteiro                   number(10) not null,
   idtipo                      number(10) not null,
   idparceiro                  number(10) not null,
   valor                       number(8,2),
   validade                    timestamp,
   original                    char(1) not null,
   idsolicitacaoservico        number not null,
   prestacaocontas             char(1) null,
   situacao                    varchar(80),
   quantidade                  number not null,
   datahoracompra              timestamp,
   idresponsavelcompra         number(10),
   idmoeda                     number(10),
   idformapagamento            number(10),
   observacoes                 long,
   primary key (iddespesaviagem),
   constraint fk_despesa_roteiroviagem foreign key (idroteiro) references roteiroviagem (idroteiroviagem),
   constraint fk_despesaviagem_parceiro foreign key (idparceiro) references rh_parceiro (idparceiro),
   constraint fk_despesa_tipomovfinviagem foreign key (idtipo) references tipomovimfinanceiraviagem (idtipomovimfinanceiraviagem),
   constraint fk_despesaviagem_moeda foreign key (idmoeda) references moedas (idmoeda),
   constraint fk_despesaviagem_formapagto foreign key (idformapagamento) references formapagamento (idformapagamento),
   constraint fk_despesaviagem_respconfirma foreign key (idresponsavelcompra) references empregados (idempregado)
);

create index idx_idsolicitacaoservico_despe on despesaviagem(idsolicitacaoservico);
create index fk_despesa_roteiroviagem on despesaviagem(idroteiro);
create index fk_despesaviagem_parceiro on despesaviagem(idparceiro);
create index fk_despesa_tipomovfinviagem on despesaviagem(idtipo);
create index fk_despesaviagem_moeda on despesaviagem(idmoeda);
create index fk_despesaviagem_formapagto on despesaviagem(idformapagamento);
create index fk_despesaviagem_respconfirma on despesaviagem(idresponsavelcompra);

alter table requisicaoviagem drop constraint FK_REQ_CENTRORESULTADO;
alter table requisicaoviagem rename column idcentrocusto to idcentroresultado;
alter table requisicaoviagem add constraint fk_reqviagem_centroresultado foreign key (idcentroresultado) references centroresultado (idcentroresultado);

-- Fim - renato.jesus - 25/09/2014

-- Inicio - thiago.borges - 26/09/2014

-- Eliminar as referências à tabela [integranteviagem] primeiro
alter table adiantamentoviagem drop constraint FK_ADIANTAMENTOVIAGEM_INTEGRAN;
alter table itemcontrolefinanceiroviagem drop constraint fk_itemctrlfinan_soliserv_em;
alter table prestacaocontasviagem drop constraint FK_PRESTACAOCONTASVIAGEM_EMPRE;

alter table integranteviagem drop constraint FK_INT_SOLICITACAOSERVICO;
alter table integranteviagem drop constraint INTVIAGEM_SOLSERVICO_FK1;
alter table integranteviagem drop constraint FK_INT_REFERENCE_EMPREGADOS;
alter table integranteviagem drop constraint FK_INTEGRANTEVIAGEM_EMPREGADOS;
drop index fk_integranteviagem_empregados;
drop index rel_integranteviagem_empregado;

-- Adicionar as novas colunas na tabela [integranteviagem]
alter table integranteviagem add idintegranteviagem number(10) not null;
alter table integranteviagem add integrantefuncionario varchar(1) null;
alter table integranteviagem add nome varchar(255) null;
alter table integranteviagem add infonaofuncionario long null;
alter table integranteviagem add estado varchar(255) null;
alter table integranteviagem drop primary key;
alter table integranteviagem add primary key (idintegranteviagem);

create index fk_integrante_empregado on integranteviagem(idempregado);
alter table integranteviagem add constraint fk_integrante_empregado foreign key (idempregado) references empregados (idempregado);
create index fk_integrante_resprestcontas on integranteviagem(idrespprestacaocontas);
alter table integranteviagem add constraint fk_integrante_resprestcontas foreign key (idrespprestacaocontas) references empregados (idempregado);
create index fk_integrante_reqviagem on integranteviagem(idsolicitacaoservico);
alter table integranteviagem add constraint fk_integrante_reqviagem foreign key (idsolicitacaoservico) references requisicaoviagem (idsolicitacaoservico);

alter table tipomovimfinanceiraviagem rename column exigejustificativa to exigedatahoracotacao;

-- Fim - thiago.borges - 26/09/2014

-- INICIO - DAVID RODRIGUES DA SILVA 02/10/2014
UPDATE menu SET    datafim = DATE '2014-08-08' WHERE  LINK = '/controleFinanceiroViagemImprevisto/controleFinanceiroViagemImprevisto.load' AND nome = '$menu.nome.controleFinanceiroViagemImprevisto';
-- FIM