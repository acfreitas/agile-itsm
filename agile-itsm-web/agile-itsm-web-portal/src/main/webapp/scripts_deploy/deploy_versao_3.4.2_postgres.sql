
-- INICIO - renato.jesus - 15/05/2014

create table BI_DASHBOARD (
   IDDASHBOARD          INT8                 not null,
   TIPO                 CHAR(1)              not null,
   IDUSUARIO            INT8                 null,
   NOMEDASHBOARD        VARCHAR(150)         null,
   IDENTIFICACAO        VARCHAR(70)          null,
   SITUACAO             CHAR(1)              null,
   PARAMETROS           TEXT                 null,
   NAOATUALIZBASE       CHAR(1)              null,
   TEMPOREFRESH         INT8                 null,
   constraint PK_BI_DASHBOARD primary key (IDDASHBOARD)
);

create  index IX_IDENT_DASH on BI_DASHBOARD (
IDENTIFICACAO
);

create table BI_ITEMDASHBOARD (
   IDITEMDASHBOARD      INT8                 not null,
   TITULO               VARCHAR(150)         not null,
   IDDASHBOARD          INT8                 not null,
   IDCONSULTA           INT8                 null,
   POSICAO              INT2                 null,
   ITEMTOP              INT4                 null,
   ITEMLEFT             INT4                 null,
   ITEMWIDTH            INT4                 null,
   ITEMHEIGHT           INT4                 null,
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
   IDDASHBOARD          INT8                 not null,
   IDGRUPO              INT4                 not null,
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
