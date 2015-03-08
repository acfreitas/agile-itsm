set sql_safe_updates = 0;

-- INICIO - carlos.alberto 27/05/2014

alter table delegacaocentroresultado add idresponsavelregistro int not null;
alter table delegacaocentroresultado add idresponsavelrevogacao int;
alter table delegacaocentroresultado add datahoraregistro     datetime not null;
alter table delegacaocentroresultado add datahorarevogacao    datetime;

alter table delegacaocentroresultado add constraint fk_reference_138 foreign key (idresponsavelregistro)
      references empregados (idempregado) on delete restrict on update restrict;

alter table delegacaocentroresultado add constraint fk_reference_139 foreign key (idresponsavelrevogacao)
      references empregados (idempregado) on delete restrict on update restrict;

-- FIM - carlos.alberto 27/05/2014

set sql_safe_updates = 1;