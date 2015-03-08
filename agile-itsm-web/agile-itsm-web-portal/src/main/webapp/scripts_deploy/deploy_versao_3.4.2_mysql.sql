
set sql_safe_updates = 0;

-- INICIO - renato.jesus - 15/05/2014

create table BI_DASHBOARD (
   IDDASHBOARD          INT                 not null,
   TIPO                 CHAR(1)              not null,
   IDUSUARIO            INT                 null,
   NOMEDASHBOARD        VARCHAR(150)         null,
   IDENTIFICACAO        VARCHAR(70)          null,
   SITUACAO             CHAR(1)              null,
   PARAMETROS           TEXT                 null,
   NAOATUALIZBASE       CHAR(1)              null,
   TEMPOREFRESH         INT                 null,
   constraint PK_BI_DASHBOARD primary key (IDDASHBOARD)
);

create  index IX_IDENT_DASH on BI_DASHBOARD (
IDENTIFICACAO
);

create table BI_ITEMDASHBOARD (
   IDITEMDASHBOARD      INT                 not null,
   TITULO               VARCHAR(150)         not null,
   IDDASHBOARD          INT                 not null,
   IDCONSULTA           INT                 null,
   POSICAO              INT                 null,
   ITEMTOP              INT                 null,
   ITEMLEFT             INT                 null,
   ITEMWIDTH            INT                 null,
   ITEMHEIGHT           INT                 null,
   PARMSSUBST           TEXT                 null,
   constraint PK_BI_ITEMDASHBOARD primary key (IDITEMDASHBOARD)
);

create  index IX_ID_DASH on BI_ITEMDASHBOARD (
IDDASHBOARD
);

alter table BI_ITEMDASHBOARD
   add constraint FK_BI_ITEMD_REFERENCE_BI_DASHB foreign key (IDDASHBOARD)
      references BI_DASHBOARD (IDDASHBOARD)
      on delete restrict on update restrict;

alter table BI_ITEMDASHBOARD
   add constraint FK_BI_ITEMD_REFERENCE_BI_CONSU foreign key (IDCONSULTA)
      references BI_CONSULTA (IDCONSULTA)
      on delete restrict on update restrict;

create table BI_DASHBOARDSEGUR (
   IDDASHBOARD          INT                 not null,
   IDGRUPO              INT                 not null,
   constraint PK_BI_DASHBOARDSEGUR primary key (IDDASHBOARD, IDGRUPO)
);

alter table BI_DASHBOARDSEGUR
   add constraint FK_BI_DASHB_REFERENCE_BI_DASHB foreign key (IDDASHBOARD)
      references BI_DASHBOARD (IDDASHBOARD)
      on delete restrict on update restrict;

alter table BI_DASHBOARDSEGUR
   add constraint FK_BI_DASHB_REFERENCE_GRUPO foreign key (IDGRUPO)
      references GRUPO (IDGRUPO)
      on delete restrict on update restrict;

-- FIM - renato.jesus - 15/05/2014

set sql_safe_updates = 1;
