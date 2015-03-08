-- bpm
alter table bpm_tipofluxo add nomeclassefluxo varchar(255);
alter table bpm_elementofluxo add template varchar(40);
alter table bpm_elementofluxo add intervalo int;
alter table bpm_elementofluxo add condicaodisparo text;
alter table bpm_elementofluxo add multiplasinstancias char(1);
alter table bpm_itemtrabalhofluxo add datahoraexecucao timestamp

-- servico
alter table servico add idtemplatesolicitacao int;
alter table servico add idtemplateacompanhamento int;

alter table servico add constraint fk_reference_641 foreign key (idtemplatesolicitacao)
      references citsmart.templatesolicitacaoservico (idtemplate) on delete restrict on update restrict;

alter table servico add constraint fk_reference_642 foreign key (idtemplateacompanhamento)
      references citsmart.templatesolicitacaoservico (idtemplate) on delete restrict on update restrict;

-- fornecedor
alter table fornecedor add telefone varchar(20);
alter table fornecedor add fax varchar(20);
alter table fornecedor add nomeContato varchar(100);
alter table fornecedor add inscricaoEstadual varchar(25);
alter table fornecedor add inscricaoMunicipal varchar(25);
alter table fornecedor add idendereco int;
alter table fornecedor add idendereco int;
alter table fornecedor add tipopessoa char(1);
alter table fornecedor add constraint fk_forn_end foreign key (idendereco)
      references endereco (idendereco) on delete restrict on update restrict;

-- unidade
alter table unidade add idendereco int;
alter table unidade add aceitaentregaproduto char(1);
alter table unidade add constraint fk_unid_end foreign key (idendereco)
      references endereco (idendereco) on delete restrict on update restrict;

-- ufs
alter table ufs add idpais int;
alter table ufs add constraint fk_uf_pais foreign key (idpais)
      references pais (idpais) on delete restrict on update restrict;

INSERT INTO `pais` (`idpais`,`nomepais`) VALUES (1,'Brasil');

update ufs set idpais = 1;

update fornecedor set tipopessoa = 'J';