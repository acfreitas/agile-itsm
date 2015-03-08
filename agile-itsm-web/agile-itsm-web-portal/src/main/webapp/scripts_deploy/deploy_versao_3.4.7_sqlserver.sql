-- INICIO - carlos.alberto 27/05/2014

alter table delegacaocentroresultado add idresponsavelregistro int  not null;
alter table delegacaocentroresultado add idresponsavelrevogacao int null;
alter table delegacaocentroresultado add datahoraregistro     datetime not null;
alter table delegacaocentroresultado add datahorarevogacao    datetime null;
alter table delegacaocentroresultado
   add constraint fk_delegaca_reference_empregad foreign key (idresponsavelregistro)
      references empregados (idempregado);

alter table delegacaocentroresultado
   add constraint fk_delegaca_reference_empregad foreign key (idresponsavelrevogacao)
      references empregados (idempregado);

-- FIM - carlos.alberto 27/05/2014
