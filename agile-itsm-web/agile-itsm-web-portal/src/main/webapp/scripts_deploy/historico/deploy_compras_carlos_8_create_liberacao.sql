/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     08/01/2013 23:03:48                          */
/*==============================================================*/


drop table if exists liberacao;

drop table if exists liberacaomudanca;

/*==============================================================*/
/* Table: liberacao                                             */
/*==============================================================*/
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

/*==============================================================*/
/* Table: liberacaomudanca                                      */
/*==============================================================*/
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

