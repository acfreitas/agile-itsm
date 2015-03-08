-- Desabilita restricao quanto a atualizacao de varios registros sem a clausula where em update.
-- Ao fim do arquivo e' habilitada a restricao.
set sql_safe_updates = 0;

create table alcada
(
   idalcada             int not null,
   nomealcada           varchar(70) not null,
   situacao             char(1) not null,
   tipoalcada           varchar(40) comment 'C - Autorização de compras'
) ENGINE=InnoDB;

alter table alcada
   add primary key (idalcada);

create table alcadacentroresultado
(
   idalcadacentroresultado int not null,
   idcentroresultado    int not null,
   idempregado          int not null,
   idalcada             int not null,
   datainicio           date not null,
   datafim              date
) ENGINE=InnoDB;

alter table alcadacentroresultado
   add primary key (idalcadacentroresultado);

create table avaliacaocoletapreco
(
   idcriterio           int not null,
   idcoletapreco        int not null,
   avaliacao            int not null
) ENGINE=InnoDB;

create table categoriaproduto
(
   idcategoria          int not null,
   idcategoriapai       int,
   nomecategoria        varchar(100) not null,
   situacao             char(1) not null,
   pesocotacaopreco     int,
   pesocotacaoprazoentrega int,
   pesocotacaoprazopagto int,
   pesocotacaotaxajuros int,
   pesocotacaoprazogarantia int
) ENGINE=InnoDB;

alter table categoriaproduto
   add primary key (idcategoria);

create table centroresultado
(
   idcentroresultado    int not null,
   codigocentroresultado varchar(25) not null,
   nomecentroresultado  varchar(200) not null,
   idcentroresultadopai int,
   permiterequisicaoproduto char(1),
   situacao             char(1) not null
) ENGINE=InnoDB;

alter table centroresultado
   add primary key (idcentroresultado);

create table coletapreco
(
   idcoletapreco        int not null,
   idfornecedor         bigint not null,
   iditemcotacao        int not null,
   idresponsavel        int not null,
   idrespresultado      int,
   idjustifresultado    int,
   datacoleta           date not null,
   datavalidade         date,
   especificacoes       text,
   preco                numeric(8,2) not null,
   valoracrescimo       numeric(8,2) not null,
   valordesconto        numeric(8,2) not null,
   valorfrete           numeric(8,2) not null,
   prazoentrega         int not null,
   prazomediopagto      numeric(4,2) not null,
   taxajuros            numeric(4,2) not null,
   prazogarantia        int not null,
   quantidadecotada     numeric(8,2) not null,
   pontuacao            numeric(8,4),
   resultadocalculo     char(1) comment 'M - Melhor cotação
            E - Empate
            N - Não vencedora',
   quantidadecalculo    numeric(8,2),
   resultadofinal       char(1) comment 'M - Melhor cotação
            N - Não vencedora',
   quantidadecompra     numeric(8,2),
   complemjustifresultado text,
   quantidadeaprovada   numeric(8,2),
   quantidadepedido     numeric(8,2)
) ENGINE=InnoDB;

alter table coletapreco
   add primary key (idcoletapreco);

create table cotacao
(
   idcotacao            int not null,
   identificacao        varchar(100),
   idempresa            int not null,
   idresponsavel        int not null,
   situacao             varchar(25) not null,
   datahoracadastro     timestamp not null,
   observacoes          text,
   datafinalprevista    date
) ENGINE=InnoDB;

alter table cotacao
   add primary key (idcotacao);

create table cotacaoitemrequisicao
(
   idcoletapreco        int not null,
   iditemrequisicaoproduto int not null,
   idparecer            int,
   iditemtrabalhoaprovacao int,
   idsolicitacaoservico int,
   iditemtrabalhoinspecao int,
   idcotacao            int,
   quantidade           numeric(8,2) not null,
   situacao             char(25) comment 'Aguardando - Aguardando aprovação
            PreAprovado - Pré aprovado
            Aprovado - Aprovado
            NaoAprovado - Não aprovado',
   quantidadeentregue   numeric(8,2)
) ENGINE=InnoDB;

alter table cotacaoitemrequisicao
   add primary key (idcoletapreco, iditemrequisicaoproduto);

create table criterioavaliacao
(
   idcriterio           int not null,
   descricao            varchar(100) not null,
   aplicavelcotacao     char(1) not null,
   aplicavelavaliacaosolicitante char(1) not null,
   aplicavelavaliacaocomprador char(1) not null,
   aplicavelqualificacaofornecedor char(1) not null,
   tipoavaliacao        char(1) not null comment 'S - Sim/N�o
            A - Aceito/Nao Aceito
            C - Conforme/Nao Conforme
            N - Numero - 0 a 10'
) ENGINE=InnoDB;

alter table criterioavaliacao
   add primary key (idcriterio);

create table criterioitemcotacao
(
   iditemcotacao        int not null,
   idcriterio           int not null,
   peso                 int not null
);

alter table criterioitemcotacao
   add primary key (idcriterio, iditemcotacao);

create table endereco
(
   idendereco           int not null,
   logradouro           varchar(200) not null,
   numero               varchar(20),
   complemento          varchar(200),
   bairro               varchar(200),
   idcidade             int,
   idpais               int,
   cep                  varchar(8),
   iduf                 int
) ENGINE=InnoDB;

alter table endereco
   add primary key (idendereco);

create table entregaitemrequisicao
(
   identrega            int not null,
   idpedido             int not null,
   idcoletapreco        int not null,
   iditemrequisicaoproduto int not null,
   idsolicitacaoservico int not null,
   quantidadeentregue   numeric(8,2) not null,
   iditemtrabalho       int,
   idparecer            int,
   situacao             varchar(25) not null,
   observacoes          text
) ENGINE=InnoDB;

alter table entregaitemrequisicao
   add primary key (identrega);

create table fornecedorcotacao
(
   idcotacao            int not null,
   idfornecedor         int not null
) ENGINE=InnoDB;

alter table fornecedorcotacao
   add primary key (idcotacao, idfornecedor);

create table fornecedorproduto
(
   idfornecedorproduto  int not null,
   idfornecedor         int not null,
   idtipoproduto        int not null,
   idmarca              int,
   datainicio           date not null,
   datafim              date
) ENGINE=InnoDB;

alter table fornecedorproduto
   add primary key (idfornecedorproduto);

create table inspecaoentregaitem
(
   identrega            int not null,
   idcriterio           int not null,
   idresponsavel        int,
   datahorainspecao     timestamp,
   avaliacao            int,
   observacoes          longtext
) ENGINE=InnoDB;

alter table inspecaoentregaitem
   add primary key (identrega, idcriterio);

create table inspecaopedidocompra
(
   idpedido             int not null,
   idcriterio           int not null,
   idresponsavel        int not null,
   datahorainspecao     timestamp not null,
   avaliacao            int not null,
   observacoes          longtext
) ENGINE=InnoDB;

alter table inspecaopedidocompra
   add primary key (idpedido, idcriterio);

create table itemcotacao
(
   iditemcotacao        int not null,
   idcotacao            int,
   idproduto            int,
   tipoidentificacao    char(1) not null,
   quantidade           numeric(8,2),
   situacao             varchar(20),
   datahoralimite       timestamp,
   idcategoriaproduto   int,
   idunidademedida      int,
   descricaoitem        varchar(200) not null,
   especificacoes       longtext,
   marcapreferencial    varchar(100),
   precoaproximado      numeric(8,2),
   solicitacoesatendidas text,
   resultadovalidacao   char(1),
   mensagensvalidacao   text,
   pesopreco            int,
   pesoprazoentrega     int,
   pesoprazopagto       int,
   pesotaxajuros        int,
   pesoprazogarantia    int,
   exigefornecedorqualificado char(1)
) ENGINE=InnoDB;

alter table itemcotacao
   add primary key (iditemcotacao);

create table itempedidocompra
(
   iditempedido         int not null,
   idpedido             int not null,
   idproduto            int not null,
   idcoletapreco        int,
   quantidade           numeric(8,2) not null,
   valortotal           numeric(8,2) not null,
   valordesconto        numeric(8,2),
   valoracrescimo       numeric(8,2),
   basecalculoicms      numeric(8,2),
   aliquotaicms         numeric(8,2),
   aliquotaipi          numeric(8,2)
) ENGINE=InnoDB;

alter table itempedidocompra
   add primary key (iditempedido);

create table itemrequisicaoproduto
(
   iditemrequisicaoproduto int not null,
   idsolicitacaoservico int not null,
   idparecervalidacao   int,
   idparecerautorizacao int,
   idcategoriaproduto   int,
   idunidademedida      int,
   idproduto            int,
   iditemcotacao        int,
   descricaoitem        varchar(200) not null,
   especificacoes       longtext,
   quantidade           numeric(8,2) not null,
   marcapreferencial    varchar(100),
   precoaproximado      numeric(8,2),
   situacao             varchar(20),
   percvariacaopreco    numeric(8,2),
   qtdeaprovada         numeric(8,2),
   tipoatendimento      char(1) not null comment 'C - Cotação
            E - Estoque',
   tipoidentificacao    char(1) not null,
   aprovacotacao        char(1),
   qtdecotada           numeric(8,2)
) ENGINE=InnoDB;

alter table itemrequisicaoproduto
   add primary key (iditemrequisicaoproduto);

create table justificativaparecer
(
   idjustificativa      int not null,
   descricaojustificativa varchar(100) not null,
   situacao             char(1),
   aplicavelrequisicao  char(1),
   aplicavelcotacao     char(1),
   aplicavelinspecao    char(1)
) ENGINE=InnoDB;

alter table justificativaparecer
   add primary key (idjustificativa);

create table limitealcada
(
   idlimitealcada       int not null,
   idalcada             int not null,
   idgrupo              int not null,
   tipolimite           char(1) comment 'F - Por faixa de valores
            Q - Qualquer valor
            ',
   abrangenciacentrocusto varchar(20) not null comment 'T - Todos
            R - Somente o responsável',
   limitevaloritem      numeric(8,2),
   limitevalormensal    numeric(8,2),
   situacao             char not null
) ENGINE=InnoDB;

alter table limitealcada
   add primary key (idlimitealcada);

create table marca
(
   idmarca              int not null,
   idfabricante         int,
   nomemarca            varchar(100) not null,
   situacao             char(1) not null
) ENGINE=InnoDB;

alter table marca
   add primary key (idmarca);

create table pais
(
   idpais               int not null,
   nomepais             varchar(200) not null
) ENGINE=InnoDB;

alter table pais
   add primary key (idpais);

create table parecer
(
   idparecer            int not null,
   idjustificativa      int,
   idalcada             int,
   idresponsavel        int not null,
   datahoraparecer      timestamp not null,
   complementojustificativa text,
   aprovado             char(1) not null,
   observacoes          text
) ENGINE=InnoDB;

alter table parecer
   add primary key (idparecer);

create table pedidocompra
(
   idpedido             int not null,
   idempresa            int not null,
   idfornecedor         bigint not null,
   datapedido           date not null,
   dataprevistaentrega  date,
   numeropedido         varchar(25) not null,
   identificacaoentrega varchar(25),
   valorfrete           numeric(8,2),
   valorseguro          numeric(8,2),
   numeronf             varchar(25),
   outrasdespesas       numeric(8,2),
   situacao             varchar(20) not null,
   idcotacao            int,
   idenderecoentrega    int,
   dataentrega          date,
   observacoes          text
) ENGINE=InnoDB;

alter table pedidocompra
   add primary key (idpedido);

create table produto
(
   idproduto            int not null,
   idtipoproduto        int not null,
   idmarca              int,
   modelo               varchar(25),
   precomercado         numeric(8,2),
   detalhes             text,
   codigoproduto        varchar(25),
   situacao             char(1) not null,
   complemento          varchar(100)
) ENGINE=InnoDB;

alter table produto
   add primary key (idproduto);

create table relacionamentoproduto
(
   idtipoproduto        int not null,
   idtipoprodutorelacionado int not null,
   tiporelacionamento   char(1) comment 'A - Acessório
            S - Produto semelhante'
) ENGINE=InnoDB;

alter table relacionamentoproduto
   add primary key (idtipoproduto, idtipoprodutorelacionado);

create table requisicaoproduto
(
   idsolicitacaoservico int not null,
   idprojeto            int,
   idcentrocusto        int,
   idenderecoentrega    int,
   finalidade           char(1) not null comment 'C - Atendimento ao cliente
            I - Uso interno',
   rejeitada            char(1) not null
) ENGINE=InnoDB;

alter table requisicaoproduto
   add primary key (idsolicitacaoservico);

create table tipoproduto
(
   idtipoproduto        int not null,
   idcategoria          int,
   idunidademedida      int,
   nomeproduto          varchar(100) not null,
   situacao             char(1) not null,
   aceitarequisicao     char(1) not null
) ENGINE=InnoDB;

alter table tipoproduto
   add primary key (idtipoproduto);

create table unidademedida
(
   idunidademedida      int not null,
   nomeunidademedida    varchar(100) not null,
   siglaunidademedida   varchar(10) not null,
   situacao             char(1) not null
) ENGINE=InnoDB;

ALTER TABLE fornecedor ADD PRIMARY KEY (idfornecedor);

CREATE TABLE avaliacaofornecedor (
    idavaliacaofornecedor INT PRIMARY KEY,
    idfornecedor BIGINT(20),
    idresponsavel INT,
    dataavaliacao date,
    decisaoqualificacao CHAR(1),
    observacoes TEXT
) ENGINE=InnoDB;

ALTER TABLE avaliacaofornecedor
    ADD CONSTRAINT fk_reference_678 FOREIGN KEY (idfornecedor) REFERENCES fornecedor (idfornecedor);

ALTER TABLE avaliacaofornecedor
    ADD CONSTRAINT fk_reference_683 FOREIGN KEY (idresponsavel) REFERENCES empregados (idempregado);

CREATE TABLE avaliacaoreferenciafornecedor (
    idavaliacaofornecedor INT,
    idempregado INT,
    decisao CHAR(1),
    observacoes TEXT,
    PRIMARY KEY (idavaliacaofornecedor, idempregado)
) ENGINE=InnoDB;

ALTER TABLE avaliacaoreferenciafornecedor
    ADD CONSTRAINT fk_reference_681 FOREIGN KEY (idavaliacaofornecedor) REFERENCES avaliacaofornecedor (idavaliacaofornecedor);

ALTER TABLE avaliacaoreferenciafornecedor
    ADD CONSTRAINT fk_reference_682 FOREIGN KEY (idavaliacaofornecedor) REFERENCES empregados (idempregado);

CREATE TABLE criterioavaliacaofornecedor (
    idavaliacaofornecedor INT,
    idcriterio INT,
    valor INT,
    observacoes TEXT,
    PRIMARY KEY (idavaliacaofornecedor, idcriterio)
) ENGINE=InnoDB;

ALTER TABLE criterioavaliacaofornecedor
    ADD CONSTRAINT fk_reference_679 FOREIGN KEY (idavaliacaofornecedor) REFERENCES avaliacaofornecedor (idavaliacaofornecedor);

ALTER TABLE criterioavaliacaofornecedor
    ADD CONSTRAINT fk_reference_680 FOREIGN KEY (idcriterio) REFERENCES criterioavaliacao (idcriterio);

ALTER TABLE avaliacaofornecedor ADD COLUMN contato VARCHAR(245) NULL AFTER observacoes;

alter table unidademedida
   add primary key (idunidademedida);

alter table alcadacentroresultado add constraint fk_reference_662 foreign key (idcentroresultado)
      references centroresultado (idcentroresultado) on delete restrict on update restrict;

alter table alcadacentroresultado add constraint fk_reference_663 foreign key (idempregado)
      references empregados (idempregado) on delete restrict on update restrict;

alter table alcadacentroresultado add constraint fk_reference_673 foreign key (idalcada)
      references alcada (idalcada) on delete restrict on update restrict;

alter table avaliacaocoletapreco add constraint fk_reference_36 foreign key (idcriterio)
      references criterioavaliacao (idcriterio) on delete restrict on update restrict;

alter table avaliacaocoletapreco add constraint fk_reference_689 foreign key (idcoletapreco)
      references coletapreco (idcoletapreco) on delete restrict on update restrict;

alter table categoriaproduto add constraint fk_reference_670 foreign key (idcategoriapai)
      references categoriaproduto (idcategoria) on delete restrict on update restrict;

alter table coletapreco add constraint fk_reference_28 foreign key (iditemcotacao)
      references itemcotacao (iditemcotacao) on delete restrict on update restrict;

alter table coletapreco add constraint fk_reference_635 foreign key (idresponsavel)
      references empregados (idempregado) on delete restrict on update restrict;

alter table coletapreco add constraint fk_reference_636 foreign key (idfornecedor)
      references fornecedor (idfornecedor) on delete restrict on update restrict;

alter table coletapreco add constraint fk_reference_695 foreign key (idrespresultado)
      references empregados (idempregado) on delete restrict on update restrict;

alter table coletapreco add constraint fk_reference_698 foreign key (idjustifresultado)
      references justificativaparecer (idjustificativa) on delete restrict on update restrict;

alter table cotacao add constraint fk_reference_633 foreign key (idempresa)
      references empresa (idempresa) on delete restrict on update restrict;

alter table cotacaoitemrequisicao add constraint fk_reference_687 foreign key (idcoletapreco)
      references coletapreco (idcoletapreco) on delete restrict on update restrict;

alter table cotacaoitemrequisicao add constraint fk_reference_688 foreign key (idcotacao)
      references cotacao (idcotacao) on delete restrict on update restrict;

alter table cotacaoitemrequisicao add foreign key (iditemrequisicaoproduto)
      references itemrequisicaoproduto (iditemrequisicaoproduto) on delete restrict on update restrict;

alter table cotacaoitemrequisicao add constraint fk_reference_694 foreign key (idparecer)
      references parecer (idparecer) on delete restrict on update restrict;

alter table cotacaoitemrequisicao change column iditemtrabalhoaprovacao iditemtrabalhoaprovacao BIGINT(20);

alter table cotacaoitemrequisicao add foreign key (iditemtrabalhoaprovacao)
      references bpm_itemtrabalhofluxo (iditemtrabalho) on delete restrict on update restrict;

alter table cotacaoitemrequisicao change column idsolicitacaoservico idsolicitacaoservico BIGINT(20);

alter table cotacaoitemrequisicao add foreign key (idsolicitacaoservico)
      references solicitacaoservico (idsolicitacaoservico) on delete restrict on update restrict;

alter table cotacaoitemrequisicao change column iditemtrabalhoinspecao iditemtrabalhoinspecao BIGINT(20);

alter table cotacaoitemrequisicao add foreign key (iditemtrabalhoinspecao)
      references bpm_itemtrabalhofluxo (iditemtrabalho) on delete restrict on update restrict;

alter table criterioitemcotacao add constraint fk_reference_33 foreign key (idcriterio)
      references criterioavaliacao (idcriterio) on delete restrict on update restrict;

alter table criterioitemcotacao add constraint fk_reference_724 foreign key (iditemcotacao)
      references itemcotacao (iditemcotacao) on delete restrict on update restrict;

alter table entregaitemrequisicao add constraint fk_reference_701 foreign key (idpedido)
      references pedidocompra (idpedido) on delete restrict on update restrict;

alter table entregaitemrequisicao add constraint fk_reference_702 foreign key (idcoletapreco, iditemrequisicaoproduto)
      references cotacaoitemrequisicao (idcoletapreco, iditemrequisicaoproduto) on delete restrict on update restrict;

alter table entregaitemrequisicao change column idsolicitacaoservico idsolicitacaoservico BIGINT(20);

alter table entregaitemrequisicao add constraint fk_reference_711 foreign key (idsolicitacaoservico)
      references solicitacaoservico (idsolicitacaoservico) on delete restrict on update restrict;

alter table entregaitemrequisicao change column iditemtrabalho iditemtrabalho BIGINT(20);

alter table entregaitemrequisicao add constraint fk_reference_712 foreign key (iditemtrabalho)
      references bpm_itemtrabalhofluxo (iditemtrabalho) on delete restrict on update restrict;

alter table entregaitemrequisicao add constraint fk_reference_719 foreign key (idparecer)
      references parecer (idparecer) on delete restrict on update restrict;

alter table fornecedorcotacao add constraint fk_reference_684 foreign key (idcotacao)
      references cotacao (idcotacao) on delete restrict on update restrict;

alter table fornecedorcotacao change column idfornecedor idfornecedor BIGINT(20);

alter table fornecedorcotacao add constraint fk_reference_685 foreign key (idfornecedor)
      references fornecedor (idfornecedor) on delete restrict on update restrict;

alter table fornecedorproduto change column idfornecedor idfornecedor BIGINT(20);

alter table fornecedorproduto add constraint fk_reference_660 foreign key (idfornecedor)
      references fornecedor (idfornecedor) on delete restrict on update restrict;

alter table fornecedorproduto add foreign key (idtipoproduto)
      references tipoproduto (idtipoproduto) on delete restrict on update restrict;

alter table fornecedorproduto add constraint fk_reference_675 foreign key (idmarca)
      references marca (idmarca) on delete restrict on update restrict;

alter table inspecaoentregaitem add constraint fk_reference_713 foreign key (idresponsavel)
      references empregados (idempregado) on delete restrict on update restrict;

alter table inspecaoentregaitem add constraint fk_reference_714 foreign key (idcriterio)
      references criterioavaliacao (idcriterio) on delete restrict on update restrict;

alter table inspecaoentregaitem add constraint fk_reference_715 foreign key (identrega)
      references entregaitemrequisicao (identrega) on delete restrict on update restrict;

alter table inspecaopedidocompra add constraint fk_reference_716 foreign key (idpedido)
      references pedidocompra (idpedido) on delete restrict on update restrict;

alter table inspecaopedidocompra add constraint fk_reference_717 foreign key (idcriterio)
      references criterioavaliacao (idcriterio) on delete restrict on update restrict;

alter table inspecaopedidocompra add constraint fk_reference_718 foreign key (idresponsavel)
      references empregados (idempregado) on delete restrict on update restrict;

alter table itemcotacao add constraint fk_reference_25 foreign key (idcotacao)
      references cotacao (idcotacao) on delete restrict on update restrict;

alter table itemcotacao add constraint fk_reference_674 foreign key (idproduto)
      references produto (idproduto) on delete restrict on update restrict;

alter table itemcotacao add constraint fk_reference_690 foreign key (idunidademedida)
      references unidademedida (idunidademedida) on delete restrict on update restrict;

alter table itemcotacao add constraint fk_reference_691 foreign key (idcategoriaproduto)
      references categoriaproduto (idcategoria) on delete restrict on update restrict;

alter table itempedidocompra add constraint fk_reference_703 foreign key (idcoletapreco)
      references coletapreco (idcoletapreco) on delete restrict on update restrict;

alter table itempedidocompra add constraint fk_reference_704 foreign key (idpedido)
      references pedidocompra (idpedido) on delete restrict on update restrict;

alter table itempedidocompra add constraint fk_reference_705 foreign key (idproduto)
      references produto (idproduto) on delete restrict on update restrict;

alter table itemrequisicaoproduto add constraint fk_reference_625 foreign key (idsolicitacaoservico)
      references requisicaoproduto (idsolicitacaoservico) on delete restrict on update restrict;

alter table itemrequisicaoproduto add constraint fk_reference_643 foreign key (idparecervalidacao)
      references parecer (idparecer) on delete restrict on update restrict;

alter table itemrequisicaoproduto add constraint fk_reference_644 foreign key (idparecerautorizacao)
      references parecer (idparecer) on delete restrict on update restrict;

alter table itemrequisicaoproduto add constraint fk_reference_669 foreign key (idproduto)
      references produto (idproduto) on delete restrict on update restrict;

alter table itemrequisicaoproduto add constraint fk_reference_676 foreign key (idunidademedida)
      references unidademedida (idunidademedida) on delete restrict on update restrict;

alter table itemrequisicaoproduto add constraint fk_reference_677 foreign key (idcategoriaproduto)
      references categoriaproduto (idcategoria) on delete restrict on update restrict;

alter table itemrequisicaoproduto add constraint fk_reference_692 foreign key (iditemcotacao)
      references itemcotacao (iditemcotacao) on delete restrict on update restrict;

alter table limitealcada add constraint fk_reference_647 foreign key (idalcada)
      references alcada (idalcada) on delete restrict on update restrict;

alter table limitealcada add constraint fk_reference_649 foreign key (idgrupo)
      references grupo (idgrupo) on delete restrict on update restrict;

alter table marca change column idfabricante idfabricante BIGINT(20);

alter table marca add constraint fk_reference_661 foreign key (idfabricante)
      references fornecedor (idfornecedor) on delete restrict on update restrict;

alter table parecer add constraint fk_reference_35 foreign key (idjustificativa)
      references justificativaparecer (idjustificativa) on delete restrict on update restrict;

alter table parecer add constraint fk_reference_650 foreign key (idalcada)
      references alcada (idalcada) on delete restrict on update restrict;

alter table pedidocompra add constraint fk_reference_638 foreign key (idfornecedor)
      references fornecedor (idfornecedor) on delete restrict on update restrict;

alter table pedidocompra add constraint fk_reference_706 foreign key (idcotacao)
      references cotacao (idcotacao) on delete restrict on update restrict;

alter table pedidocompra add constraint fk_reference_707 foreign key (idenderecoentrega)
      references endereco (idendereco) on delete restrict on update restrict;

alter table produto add constraint fk_reference_671 foreign key (idtipoproduto)
      references tipoproduto (idtipoproduto) on delete restrict on update restrict;

alter table produto add constraint fk_reference_672 foreign key (idmarca)
      references marca (idmarca) on delete restrict on update restrict;

alter table relacionamentoproduto add constraint fk_reference_654 foreign key (idtipoproduto)
      references tipoproduto (idtipoproduto) on delete restrict on update restrict;

alter table relacionamentoproduto add constraint fk_reference_655 foreign key (idtipoprodutorelacionado)
      references tipoproduto (idtipoproduto) on delete restrict on update restrict;

alter table requisicaoproduto add constraint fk_reference_626 foreign key (idprojeto)
      references projetos (idprojeto) on delete restrict on update restrict;

alter table requisicaoproduto add constraint fk_reference_646 foreign key (idcentrocusto)
      references centroresultado (idcentroresultado) on delete restrict on update restrict;

alter table requisicaoproduto add constraint fk_reference_668 foreign key (idenderecoentrega)
      references endereco (idendereco) on delete restrict on update restrict;

alter table tipoproduto add constraint fk_reference_658 foreign key (idcategoria)
      references categoriaproduto (idcategoria) on delete restrict on update restrict;

alter table tipoproduto add constraint fk_reference_664 foreign key (idunidademedida)
      references unidademedida (idunidademedida) on delete restrict on update restrict;

alter table bpm_tipofluxo add nomeclassefluxo varchar(255);
alter table bpm_elementofluxo add template varchar(40);
alter table bpm_elementofluxo add intervalo int;
alter table bpm_elementofluxo add condicaodisparo text;
alter table bpm_elementofluxo add multiplasinstancias char(1);
alter table bpm_itemtrabalhofluxo add datahoraexecucao timestamp;

create table templatesolicitacaoservico
(
   idtemplate           int not null,
   identificacao        varchar(40) not null,
   nometemplate         varchar(200) not null,
   nomeclassedto        varchar(255) not null,
   nomeclasseaction     varchar(255) not null,
   nomeclasseservico    varchar(255) not null,
   urlrecuperacao       varchar(255) not null,
   scriptaposrecuperacao text,
   habilitadirecionamento char(1),
   habilitasituacao     char(1),
   habilitasolucao      char(1),
   alturadiv            int,
   habilitaurgenciaimpacto char(1),
   habilitanotificacaoemail char(1),
   habilitaproblema     char(1),
   habilitamudanca      char(1)
)ENGINE=InnoDB;

alter table templatesolicitacaoservico
   add primary key (idtemplate);

alter table servico add idtemplatesolicitacao int;
alter table servico add idtemplateacompanhamento int;

alter table servico add foreign key (idtemplatesolicitacao)
      references templatesolicitacaoservico (idtemplate) on delete restrict on update restrict;

alter table servico add constraint fk_reference_642 foreign key (idtemplateacompanhamento)
      references templatesolicitacaoservico (idtemplate) on delete restrict on update restrict;

alter table fornecedor add telefone varchar(20);
alter table fornecedor add fax varchar(20);
alter table fornecedor add nomeContato varchar(100);
alter table fornecedor add inscricaoEstadual varchar(25);
alter table fornecedor add inscricaoMunicipal varchar(25);
alter table fornecedor add idendereco int;
alter table fornecedor add tipopessoa char(1);
alter table fornecedor add constraint fk_forn_end foreign key (idendereco)
      references endereco (idendereco) on delete restrict on update restrict;

alter table unidade add idendereco int;
alter table unidade add aceitaentregaproduto char(1);
alter table unidade add constraint fk_unid_end foreign key (idendereco)
      references endereco (idendereco) on delete restrict on update restrict;

alter table ufs add idpais int;
alter table ufs add constraint fk_uf_pais foreign key (idpais)
      references pais (idpais) on delete restrict on update restrict;

INSERT INTO `pais` (`idpais`,`nomepais`) VALUES (1,'Brasil');

update ufs set idpais = 1;

update fornecedor set tipopessoa = 'J';

create table liberacao
(
   idliberacao          int not null,
   idsolicitante        int not null,
   idresponsavel        int,
   titulo               varchar(100) not null,
   descricao            text not null,
   datainicial          date not null,
   datafinal            date not null,
   dataliberacao        date,
   situacao             char(1) not null comment 'A - Aceita
            E - Em execução
            F - Finalizada
            X - Cancelada',
   risco                char(1) not null comment 'B - Baixo
            M - Médio
            A - Alto',
   versao               varchar(25)
);

alter table liberacao
   add primary key (idliberacao);

create table liberacaomudanca
(
   idliberacao          int not null,
   idrequisicaomudanca  int not null
);

alter table liberacaomudanca
   add primary key (idliberacao, idrequisicaomudanca);

alter table liberacao add constraint fk_reference_720 foreign key (idsolicitante)
      references empregados (idempregado) on delete restrict on update restrict;

alter table liberacao add constraint fk_reference_721 foreign key (idresponsavel)
      references empregados (idempregado) on delete restrict on update restrict;

alter table liberacaomudanca add constraint fk_reference_709 foreign key (idliberacao)
      references liberacao (idliberacao) on delete restrict on update restrict;

alter table liberacaomudanca add constraint fk_reference_710 foreign key (idrequisicaomudanca)
      references requisicaomudanca (idrequisicaomudanca) on delete restrict on update restrict;

INSERT INTO midia
(idmidia, nome)
VALUES
(1, "Blu-ray"),
(2, "Cartão de Memória"),
(3, "CD"),
(4, "Disquete"),
(5, "DVD"),
(6, "Fita Magnética"),
(7, "HD"),
(8, "Pen drive"),
(9, "Outros");



INSERT INTO tiposoftware
(idtiposoftware, nome)
VALUES
(1, "Anti-virus"),
(2, "Auxiliar de escritório"),
(3, "Comunicador Instantâneo"),
(4, "Editor de Imagem"),
(5, "Editor de Texto"),
(6, "Navegador"),
(7, "Sistema Operacional"),
(8, "Outros");

set sql_safe_updates = 1;
