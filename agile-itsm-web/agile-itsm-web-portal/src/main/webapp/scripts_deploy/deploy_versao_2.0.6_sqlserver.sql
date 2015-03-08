alter table solicitacaoservico 
add idtarefaencerramento BIGINT null default null;
alter table solicitacaoservico 	add constraint fk_tarefa_encerramento foreign key (idtarefaencerramento) references bpm_itemtrabalhofluxo (iditemtrabalho);
create index fk_tarefa_encerramento_idx ON solicitacaoservico(idtarefaencerramento asc);

update solicitacaoservico set idtarefaencerramento = 
		(select max(ocorrenciasolicitacao.iditemtrabalho) from ocorrenciasolicitacao  inner join bpm_itemtrabalhofluxo  
		on bpm_itemtrabalhofluxo.iditemtrabalho = ocorrenciasolicitacao.iditemtrabalho
		where upper(bpm_itemtrabalhofluxo.situacao) = 'EXECUTADO' 
			and bpm_itemtrabalhofluxo.idresponsavelatual is not null 
			and upper(ocorrenciasolicitacao.categoria) = 'EXECUCAO' 
			and (upper(dadossolicitacao) like '%RESOLVIDA%' 
			or upper(dadossolicitacao) like '%CANCELADA%')
			and ocorrenciasolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico)
where idtarefaencerramento is null;
