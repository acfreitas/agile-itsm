-- INICIO - renato.jesus - 15/05/2014

create table BI_DASHBOARD (
   IDDASHBOARD          Number(19)                 not null,
   TIPO                 CHAR(1)              not null,
   IDUSUARIO            Number(19)                 null,
   NOMEDASHBOARD        varchar2(150)         null,
   IDENTIFICACAO        VARCHAR(70)          null,
   SITUACAO             CHAR(1)              null,
   PARAMETROS           clob                 null,
   NAOATUALIZBASE       CHAR(1)              null,
   TEMPOREFRESH         Number(19)                 null,
   constraint PK_BI_DASHBOARD primary key (IDDASHBOARD)
);

create  index IX_IDENT_DASH on BI_DASHBOARD (
IDENTIFICACAO
);

create table BI_ITEMDASHBOARD (
   IDITEMDASHBOARD      Number(19)                 not null,
   TITULO               varchar2(150)         not null,
   IDDASHBOARD          Number(19)                 not null,
   IDCONSULTA           Number(19)                 null,
   POSICAO              Number(5)                 null,
   ITEMTOP              Number(10)                 null,
   ITEMLEFT             Number(10)                 null,
   ITEMWIDTH            Number(10)                 null,
   ITEMHEIGHT           Number(10)                 null,
   PARMSSUBST           clob                 null,
   constraint PK_BI_ITEMDASHBOARD primary key (IDITEMDASHBOARD)
);

create  index IX_ID_DASH on BI_ITEMDASHBOARD (
IDDASHBOARD
);

alter table BI_ITEMDASHBOARD
   add constraint FK_BI_ITEMD_REFERENCE_BI_DASHB foreign key (IDDASHBOARD)
      references BI_DASHBOARD (IDDASHBOARD);

                  
alter table BI_ITEMDASHBOARD
   add constraint FK_BI_ITEMD_REFERENCE_BI_CONSU foreign key (IDCONSULTA)
      references BI_CONSULTA (IDCONSULTA);

create table BI_DASHBOARDSEGUR (
   IDDASHBOARD          Number(19)                 not null,
   IDGRUPO              Number(10)                 not null,
   constraint PK_BI_DASHBOARDSEGUR primary key (IDDASHBOARD, IDGRUPO)
);

alter table BI_DASHBOARDSEGUR
   add constraint FK_BI_DASHB_REFERENCE_BI_DASHB foreign key (IDDASHBOARD)
      references BI_DASHBOARD (IDDASHBOARD);

alter table BI_DASHBOARDSEGUR
   add constraint FK_BI_DASHB_REFERENCE_GRUPO foreign key (IDGRUPO)
      references GRUPO (IDGRUPO);

-- FIM - renato.jesus - 15/05/2014