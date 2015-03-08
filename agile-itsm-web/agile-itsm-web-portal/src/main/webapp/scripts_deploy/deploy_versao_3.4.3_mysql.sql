
set sql_safe_updates = 0;

-- INICIO - renato.jesus - 15/05/2014 - Só no MySQL

DROP TABLE BI_DASHBOARDSEGUR;

DROP TABLE BI_ITEMDASHBOARD;

DROP TABLE BI_DASHBOARD;

create table bi_dashboard (
   iddashboard          int                 not null,
   tipo                 char(1)              not null,
   idusuario            int                 null,
   nomedashboard        varchar(150)         null,
   identificacao        varchar(70)          null,
   situacao             char(1)              null,
   parametros           text                 null,
   naoatualizbase       char(1)              null,
   temporefresh         int                 null,
   constraint pk_bi_dashboard primary key (iddashboard)
);

create  index ix_ident_dash on bi_dashboard (
identificacao
);

create table bi_itemdashboard (
   iditemdashboard      int                 not null,
   titulo               varchar(150)         not null,
   iddashboard          int                 not null,
   idconsulta           int                 null,
   posicao              int                 null,
   itemtop              int                 null,
   itemleft             int                 null,
   itemwidth            int                 null,
   itemheight           int                 null,
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
   iddashboard          int                 not null,
   idgrupo              int                 not null,
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

-- FIM - renato.jesus - 15/05/2014 - Só no MySQL

set sql_safe_updates = 1;
