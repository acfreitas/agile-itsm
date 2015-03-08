ALTER TABLE limitealcada DROP COLUMN `qualificacaofornecedor` ;

alter table categoriaproduto add pesocotacaopreco int;

alter table categoriaproduto add pesocotacaoprazoentrega int;

alter table categoriaproduto add pesocotacaoprazopagto int;

alter table categoriaproduto add pesocotacaotaxajuros int;

alter table categoriaproduto add pesocotacaoprazogarantia int;

alter table itemcotacao add pesopreco int;

alter table itemcotacao add pesoprazoentrega int;

alter table itemcotacao add pesoprazopagto int;

alter table itemcotacao add pesotaxajuros int;

alter table itemcotacao add pesoprazogarantia int;

alter table criterioavaliacao add tipoavaliacao char(1);

drop table if exists criteriocotacaocategoria;

alter table itemrequisicaoproduto add valoraprovado decimal(8,2);

alter table itemcotacao add exigefornecedorqualificado char(1);

alter table cotacaoitemrequisicao add iditemtrabalho int;
alter table cotacaoitemrequisicao add constraint fk_reference_699 foreign key (iditemtrabalhoaprovacao)
      references bpm_itemtrabalhofluxo (iditemtrabalho) on delete restrict on update restrict;

ALTER TABLE `inspecaoentregaitem` CHANGE COLUMN `avaliacao` `avaliacao` VARCHAR(25) NULL DEFAULT NULL  ;

ALTER TABLE `inspecaopedidocompra` CHANGE COLUMN `avaliacao` `avaliacao` VARCHAR(25) NULL DEFAULT NULL  ;


/*==============================================================*/
/* Table: criteriocotacaocategoria                              */
/*==============================================================*/
create table criteriocotacaocategoria
(
   idcategoria          int not null,
   idcriterio           int not null,
   pesocotacao          int not null
);

alter table criteriocotacaocategoria
   add primary key (idcategoria, idcriterio);

alter table criteriocotacaocategoria add constraint fk_reference_722 foreign key (idcategoria)
      references categoriaproduto (idcategoria) on delete restrict on update restrict;

alter table criteriocotacaocategoria add constraint fk_reference_723 foreign key (idcriterio)
      references criterioavaliacao (idcriterio) on delete restrict on update restrict;

drop table if exists historicosituacaocotacao;

/*==============================================================*/
/* Table: historicosituacaocotacao                              */
/*==============================================================*/
create table historicosituacaocotacao
(
   idhistorico          int not null,
   idcotacao            int not null,
   idresponsavel        int not null,
   datahora             timestamp not null,
   situacao             varchar(25) not null
);

alter table historicosituacaocotacao
   add primary key (idhistorico);

alter table historicosituacaocotacao add constraint fk_reference_696 foreign key (idcotacao)
      references cotacao (idcotacao) on delete restrict on update restrict;

alter table historicosituacaocotacao add constraint fk_reference_697 foreign key (idresponsavel)
      references empregados (idempregado) on delete restrict on update restrict;




