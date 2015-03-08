/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     20/01/2013 20:06:50                          */
/*==============================================================*/


drop table if exists alcada;

drop table if exists alcadacentroresultado;

drop table if exists avaliacaocoletapreco;

drop table if exists avaliacaofornecedor;

drop table if exists avaliacaoreferenciafornecedor;

drop table if exists categoriaproduto;

drop table if exists centroresultado;

drop table if exists coletapreco;

drop table if exists cotacao;

drop table if exists cotacaoitemrequisicao;

drop table if exists criterioavaliacao;

drop table if exists criterioavaliacaofornecedor;

drop table if exists criterioitemcotacao;

drop table if exists endereco;

drop table if exists entregaitemrequisicao;

drop table if exists fornecedorcotacao;

drop table if exists fornecedorproduto;

drop table if exists inspecaoentregaitem;

drop table if exists inspecaopedidocompra;

drop table if exists itemcotacao;

drop table if exists itempedidocompra;

drop table if exists citsmart.itemrequisicaoproduto;

drop table if exists justificativaparecer;

drop table if exists limitealcada;

drop table if exists marca;

drop table if exists pais;

drop table if exists parecer;

drop table if exists pedidocompra;

drop table if exists produto;

drop table if exists relacionamentoproduto;

drop table if exists citsmart.requisicaoproduto;

drop table if exists citsmart.templatesolicitacaoservico;

drop table if exists tipoproduto;

drop table if exists unidademedida;

/*==============================================================*/
/* Table: alcada                                                */
/*==============================================================*/
create table alcada
(
   idalcada             int not null,
   nomealcada           varchar(70) not null,
   situacao             char(1) not null,
   tipoalcada           varchar(40) comment 'C - Autorização de compras'
);

alter table alcada
   add primary key (idalcada);

/*==============================================================*/
/* Table: alcadacentroresultado                                 */
/*==============================================================*/
create table alcadacentroresultado
(
   idalcadacentroresultado int not null,
   idcentroresultado    int not null,
   idempregado          int not null,
   idalcada             int not null,
   datainicio           date not null,
   datafim              date
);

alter table alcadacentroresultado
   add primary key (idalcadacentroresultado);

/*==============================================================*/
/* Table: avaliacaocoletapreco                                  */
/*==============================================================*/
create table avaliacaocoletapreco
(
   idcriterio           int not null,
   idcoletapreco        int not null,
   avaliacao            int not null
);

alter table avaliacaocoletapreco
   add primary key (idcriterio, idcoletapreco);

/*==============================================================*/
/* Table: avaliacaofornecedor                                   */
/*==============================================================*/
create table avaliacaofornecedor
(
   idavaliacaofornecedor int not null,
   idfornecedor         int not null,
   idresponsavel        int not null,
   dataavaliacao        date not null,
   decisaoqualificacao  char(1) not null comment 'Q - Qualificado
            D - Desqualificado',
   observacoes          text
);

alter table avaliacaofornecedor
   add primary key (idavaliacaofornecedor);

/*==============================================================*/
/* Table: avaliacaoreferenciafornecedor                         */
/*==============================================================*/
create table avaliacaoreferenciafornecedor
(
   idavaliacaofornecedor int not null,
   idempregado          int not null,
   decisao              char(1) not null comment 'A - Aprovado
            N - Não aprovado',
   observacoes          char(1)
);

alter table avaliacaoreferenciafornecedor
   add primary key (idavaliacaofornecedor, idempregado);

/*==============================================================*/
/* Table: categoriaproduto                                      */
/*==============================================================*/
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
);

alter table categoriaproduto
   add primary key (idcategoria);

/*==============================================================*/
/* Table: centroresultado                                       */
/*==============================================================*/
create table centroresultado
(
   idcentroresultado    int not null,
   codigocentroresultado varchar(25) not null,
   nomecentroresultado  varchar(200) not null,
   idcentroresultadopai int,
   permiterequisicaoproduto char(1),
   situacao             char(1) not null
);

alter table centroresultado
   add primary key (idcentroresultado);

/*==============================================================*/
/* Table: coletapreco                                           */
/*==============================================================*/
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
);

alter table coletapreco
   add primary key (idcoletapreco);

/*==============================================================*/
/* Table: cotacao                                               */
/*==============================================================*/
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
);

alter table cotacao
   add primary key (idcotacao);

/*==============================================================*/
/* Table: cotacaoitemrequisicao                                 */
/*==============================================================*/
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
);

alter table cotacaoitemrequisicao
   add primary key (idcoletapreco, iditemrequisicaoproduto);

/*==============================================================*/
/* Table: criterioavaliacao                                     */
/*==============================================================*/
create table criterioavaliacao
(
   idcriterio           int not null,
   descricao            varchar(100) not null,
   aplicavelcotacao     char(1) not null,
   aplicavelavaliacaosolicitante char(1) not null,
   aplicavelavaliacaocomprador char(1) not null,
   aplicavelqualificacaofornecedor char(1) not null,
   tipoavaliacao        char(1) not null comment 'S - Sim/Não
            A - Aceito/Não Aceito
            C - Conforme/Não Conforme
            N - Número - 0 a 10'
);

alter table criterioavaliacao
   add primary key (idcriterio);

/*==============================================================*/
/* Table: criterioavaliacaofornecedor                           */
/*==============================================================*/
create table criterioavaliacaofornecedor
(
   idavaliacaofornecedor int not null,
   idcriterio           int not null,
   valor                int not null,
   observacoes          text
);

alter table criterioavaliacaofornecedor
   add primary key (idavaliacaofornecedor, idcriterio);

/*==============================================================*/
/* Table: criterioitemcotacao                                   */
/*==============================================================*/
create table criterioitemcotacao
(
   iditemcotacao        int not null,
   idcriterio           int not null,
   peso                 int not null
);

alter table criterioitemcotacao
   add primary key (idcriterio, iditemcotacao);

/*==============================================================*/
/* Table: endereco                                              */
/*==============================================================*/
create table endereco
(
   idendereco           int not null,
   logradouro           varchar(200),
   numero               varchar(20),
   complemento          varchar(200),
   bairro               varchar(200),
   idcidade             int,
   idpais               int,
   cep                  varchar(8),
   iduf                 int
);

alter table endereco
   add primary key (idendereco);

/*==============================================================*/
/* Table: entregaitemrequisicao                                 */
/*==============================================================*/
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
);

alter table entregaitemrequisicao
   add primary key (identrega);

/*==============================================================*/
/* Table: fornecedorcotacao                                     */
/*==============================================================*/
create table fornecedorcotacao
(
   idcotacao            int not null,
   idfornecedor         int not null
);

alter table fornecedorcotacao
   add primary key (idcotacao, idfornecedor);

/*==============================================================*/
/* Table: fornecedorproduto                                     */
/*==============================================================*/
create table fornecedorproduto
(
   idfornecedorproduto  int not null,
   idfornecedor         int not null,
   idtipoproduto        int not null,
   idmarca              int,
   datainicio           date not null,
   datafim              date
);

alter table fornecedorproduto
   add primary key (idfornecedorproduto);

/*==============================================================*/
/* Table: inspecaoentregaitem                                   */
/*==============================================================*/
create table inspecaoentregaitem
(
   identrega            int not null,
   idcriterio           int not null,
   idresponsavel        int,
   datahorainspecao     timestamp,
   avaliacao            int,
   observacoes          longtext
);

alter table inspecaoentregaitem
   add primary key (identrega, idcriterio);

/*==============================================================*/
/* Table: inspecaopedidocompra                                  */
/*==============================================================*/
create table inspecaopedidocompra
(
   idpedido             int not null,
   idcriterio           int not null,
   idresponsavel        int not null,
   datahorainspecao     timestamp not null,
   avaliacao            int not null,
   observacoes          longtext
);

alter table inspecaopedidocompra
   add primary key (idpedido, idcriterio);

/*==============================================================*/
/* Table: itemcotacao                                           */
/*==============================================================*/
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
);

alter table itemcotacao
   add primary key (iditemcotacao);

/*==============================================================*/
/* Table: itempedidocompra                                      */
/*==============================================================*/
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
);

alter table itempedidocompra
   add primary key (iditempedido);

/*==============================================================*/
/* Table: itemrequisicaoproduto                                 */
/*==============================================================*/
create table citsmart.itemrequisicaoproduto
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
);

alter table citsmart.itemrequisicaoproduto
   add primary key (iditemrequisicaoproduto);

/*==============================================================*/
/* Table: justificativaparecer                                  */
/*==============================================================*/
create table justificativaparecer
(
   idjustificativa      int not null,
   descricaojustificativa varchar(100) not null,
   situacao             char(1),
   aplicavelrequisicao  char(1),
   aplicavelcotacao     char(1),
   aplicavelinspecao    char(1)
);

alter table justificativaparecer
   add primary key (idjustificativa);

/*==============================================================*/
/* Table: limitealcada                                          */
/*==============================================================*/
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
);

alter table limitealcada
   add primary key (idlimitealcada);

/*==============================================================*/
/* Table: marca                                                 */
/*==============================================================*/
create table marca
(
   idmarca              int not null,
   idfabricante         int,
   nomemarca            varchar(100) not null,
   situacao             char(1) not null
);

alter table marca
   add primary key (idmarca);

/*==============================================================*/
/* Table: pais                                                  */
/*==============================================================*/
create table pais
(
   idpais               int not null,
   nomepais             varchar(200) not null
);

alter table pais
   add primary key (idpais);

/*==============================================================*/
/* Table: parecer                                               */
/*==============================================================*/
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
);

alter table parecer
   add primary key (idparecer);

/*==============================================================*/
/* Table: pedidocompra                                          */
/*==============================================================*/
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
);

alter table pedidocompra
   add primary key (idpedido);

/*==============================================================*/
/* Table: produto                                               */
/*==============================================================*/
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
);

alter table produto
   add primary key (idproduto);

/*==============================================================*/
/* Table: relacionamentoproduto                                 */
/*==============================================================*/
create table relacionamentoproduto
(
   idtipoproduto        int not null,
   idtipoprodutorelacionado int not null,
   tiporelacionamento   char(1) comment 'A - Acessório
            S - Produto semelhante'
);

alter table relacionamentoproduto
   add primary key (idtipoproduto, idtipoprodutorelacionado);

/*==============================================================*/
/* Table: requisicaoproduto                                     */
/*==============================================================*/
create table citsmart.requisicaoproduto
(
   idsolicitacaoservico int not null,
   idprojeto            int,
   idcentrocusto        int,
   idenderecoentrega    int,
   finalidade           char(1) not null comment 'C - Atendimento ao cliente
            I - Uso interno',
   rejeitada            char(1) not null
);

alter table citsmart.requisicaoproduto
   add primary key (idsolicitacaoservico);

/*==============================================================*/
/* Table: templatesolicitacaoservico                            */
/*==============================================================*/
create table citsmart.templatesolicitacaoservico
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
);

alter table citsmart.templatesolicitacaoservico
   add primary key (idtemplate);

/*==============================================================*/
/* Table: tipoproduto                                           */
/*==============================================================*/
create table tipoproduto
(
   idtipoproduto        int not null,
   idcategoria          int,
   idunidademedida      int,
   nomeproduto          varchar(100) not null,
   situacao             char(1) not null,
   aceitarequisicao     char(1) not null
);

alter table tipoproduto
   add primary key (idtipoproduto);

/*==============================================================*/
/* Table: unidademedida                                         */
/*==============================================================*/
create table unidademedida
(
   idunidademedida      int not null,
   nomeunidademedida    varchar(100) not null,
   siglaunidademedida   varchar(10) not null,
   situacao             char(1) not null
);

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

alter table avaliacaofornecedor add constraint fk_reference_678 foreign key (idfornecedor)
      references fornecedor (idfornecedor) on delete restrict on update restrict;

alter table avaliacaofornecedor add constraint fk_reference_683 foreign key (idresponsavel)
      references empregados (idempregado) on delete restrict on update restrict;

alter table avaliacaoreferenciafornecedor add constraint fk_reference_681 foreign key (idavaliacaofornecedor)
      references avaliacaofornecedor (idavaliacaofornecedor) on delete restrict on update restrict;

alter table avaliacaoreferenciafornecedor add constraint fk_reference_682 foreign key (idempregado)
      references empregados (idempregado) on delete restrict on update restrict;

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

alter table cotacaoitemrequisicao add constraint fk_reference_693 foreign key (iditemrequisicaoproduto)
      references citsmart.itemrequisicaoproduto (iditemrequisicaoproduto) on delete restrict on update restrict;

alter table cotacaoitemrequisicao add constraint fk_reference_694 foreign key (idparecer)
      references parecer (idparecer) on delete restrict on update restrict;

alter table cotacaoitemrequisicao add constraint fk_reference_699 foreign key (iditemtrabalhoaprovacao)
      references bpm_itemtrabalhofluxo (iditemtrabalho) on delete restrict on update restrict;

alter table cotacaoitemrequisicao add constraint fk_reference_700 foreign key (idsolicitacaoservico)
      references solicitacaoservico (idsolicitacaoservico) on delete restrict on update restrict;

alter table cotacaoitemrequisicao add constraint fk_reference_708 foreign key (iditemtrabalhoinspecao)
      references bpm_itemtrabalhofluxo (iditemtrabalho) on delete restrict on update restrict;

alter table criterioavaliacaofornecedor add constraint fk_reference_679 foreign key (idavaliacaofornecedor)
      references avaliacaofornecedor (idavaliacaofornecedor) on delete restrict on update restrict;

alter table criterioavaliacaofornecedor add constraint fk_reference_680 foreign key (idcriterio)
      references criterioavaliacao (idcriterio) on delete restrict on update restrict;

alter table criterioitemcotacao add constraint fk_reference_33 foreign key (idcriterio)
      references criterioavaliacao (idcriterio) on delete restrict on update restrict;

alter table criterioitemcotacao add constraint fk_reference_724 foreign key (iditemcotacao)
      references itemcotacao (iditemcotacao) on delete restrict on update restrict;

alter table entregaitemrequisicao add constraint fk_reference_701 foreign key (idpedido)
      references pedidocompra (idpedido) on delete restrict on update restrict;

alter table entregaitemrequisicao add constraint fk_reference_702 foreign key (idcoletapreco, iditemrequisicaoproduto)
      references cotacaoitemrequisicao (idcoletapreco, iditemrequisicaoproduto) on delete restrict on update restrict;

alter table entregaitemrequisicao add constraint fk_reference_711 foreign key (idsolicitacaoservico)
      references solicitacaoservico (idsolicitacaoservico) on delete restrict on update restrict;

alter table entregaitemrequisicao add constraint fk_reference_712 foreign key (iditemtrabalho)
      references bpm_itemtrabalhofluxo (iditemtrabalho) on delete restrict on update restrict;

alter table entregaitemrequisicao add constraint fk_reference_719 foreign key (idparecer)
      references parecer (idparecer) on delete restrict on update restrict;

alter table fornecedorcotacao add constraint fk_reference_684 foreign key (idcotacao)
      references cotacao (idcotacao) on delete restrict on update restrict;

alter table fornecedorcotacao add constraint fk_reference_685 foreign key (idfornecedor)
      references fornecedor (idfornecedor) on delete restrict on update restrict;

alter table fornecedorproduto add constraint fk_reference_660 foreign key (idfornecedor)
      references fornecedor (idfornecedor) on delete restrict on update restrict;

alter table fornecedorproduto add constraint fk_reference_665 foreign key (idtipoproduto)
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

alter table citsmart.itemrequisicaoproduto add constraint fk_reference_625 foreign key (idsolicitacaoservico)
      references citsmart.requisicaoproduto (idsolicitacaoservico) on delete restrict on update restrict;

alter table citsmart.itemrequisicaoproduto add constraint fk_reference_643 foreign key (idparecervalidacao)
      references parecer (idparecer) on delete restrict on update restrict;

alter table citsmart.itemrequisicaoproduto add constraint fk_reference_644 foreign key (idparecerautorizacao)
      references parecer (idparecer) on delete restrict on update restrict;

alter table citsmart.itemrequisicaoproduto add constraint fk_reference_669 foreign key (idproduto)
      references produto (idproduto) on delete restrict on update restrict;

alter table citsmart.itemrequisicaoproduto add constraint fk_reference_676 foreign key (idunidademedida)
      references unidademedida (idunidademedida) on delete restrict on update restrict;

alter table citsmart.itemrequisicaoproduto add constraint fk_reference_677 foreign key (idcategoriaproduto)
      references categoriaproduto (idcategoria) on delete restrict on update restrict;

alter table citsmart.itemrequisicaoproduto add constraint fk_reference_692 foreign key (iditemcotacao)
      references itemcotacao (iditemcotacao) on delete restrict on update restrict;

alter table limitealcada add constraint fk_reference_647 foreign key (idalcada)
      references alcada (idalcada) on delete restrict on update restrict;

alter table limitealcada add constraint fk_reference_649 foreign key (idgrupo)
      references grupo (idgrupo) on delete restrict on update restrict;

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

alter table citsmart.requisicaoproduto add constraint fk_reference_622 foreign key (idsolicitacaoservico)
      references solicitacaoservico (idsolicitacaoservico) on delete restrict on update restrict;

alter table citsmart.requisicaoproduto add constraint fk_reference_626 foreign key (idprojeto)
      references projetos (idprojeto) on delete restrict on update restrict;

alter table citsmart.requisicaoproduto add constraint fk_reference_646 foreign key (idcentrocusto)
      references centroresultado (idcentroresultado) on delete restrict on update restrict;

alter table citsmart.requisicaoproduto add constraint fk_reference_668 foreign key (idenderecoentrega)
      references endereco (idendereco) on delete restrict on update restrict;

alter table tipoproduto add constraint fk_reference_658 foreign key (idcategoria)
      references categoriaproduto (idcategoria) on delete restrict on update restrict;

alter table tipoproduto add constraint fk_reference_664 foreign key (idunidademedida)
      references unidademedida (idunidademedida) on delete restrict on update restrict;

