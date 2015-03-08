-- INICIO - carlos.alberto 27/05/2014

alter table delegacaocentroresultado    idresponsavelregistro integer not null;
alter table delegacaocentroresultado    idresponsavelrevogacao integer;
alter table delegacaocentroresultado    datahoraregistro   timestamp not null;
alter table delegacaocentroresultado    datahorarevogacao  timestamp ;

alter table delegacaocentroresultado
   add constraint fk_delegaca_reference_empregad foreign key (idresponsavelregistro)
      references empregados (idempregado);

alter table delegacaocentroresultado
   add constraint fk_delegaca_reference_empregad foreign key (idresponsavelrevogacao)
      references empregados (idempregado);

-- FIM - carlos.alberto 27/05/2014
