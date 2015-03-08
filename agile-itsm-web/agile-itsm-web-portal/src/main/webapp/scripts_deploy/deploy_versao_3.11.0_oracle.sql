alter table justificativaparecer   add column datafim date default null;

alter table justificativaparecer add column datainicio date default null;

alter table fornecedor modify responsabilidades  VARCHAR2(254 CHAR);