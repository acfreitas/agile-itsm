-- INICIO - carlos.alberto 27/05/2014

alter table delegacaocentroresultadoidresponsavelregistro int4  not null;
alter table delegacaocentroresultadoidresponsavelrevogacao int4 null;
alter table delegacaocentroresultadodatahoraregistro     timestamp not null;
alter table delegacaocentroresultado datahorarevogacao    timestamp null;

alter table delegacaocentroresultado
   add constraint fk_delegaca_reference_empregad foreign key (idresponsavelregistro)
      references empregados (idempregado)
      on delete restrict on update restrict;

alter table delegacaocentroresultado
   add constraint fk_delegaca_reference_empregad foreign key (idresponsavelrevogacao)
      references empregados (idempregado)
      on delete restrict on update restrict;

-- FIM - carlos.alberto 27/05/2014
