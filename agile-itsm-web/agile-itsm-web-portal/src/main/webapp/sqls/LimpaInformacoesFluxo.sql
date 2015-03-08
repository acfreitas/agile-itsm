--SQLs de exclusão de dados do fluxo:
delete from bpm_objetoinstanciafluxo;
delete FROM bpm_atribuicaofluxo;
delete from bpm_historicoitemtrabalho;
delete from bpm_itemtrabalhofluxo;
delete from bpm_instanciafluxo;
delete from solicitacaoservico;
delete from solicitacaoservicomudanca;
delete from solicitacaoservicoevtmon;
delete from solicitacaoservicoproblema;
delete from contatosolicitacaoservico;
delete from conhecimentosolicitacaoservico;
delete from execucaosolicitacao;
delete from reaberturasolicitacao;
delete from pesquisasatisfacao;
delete from ocorrenciasolicitacao;

--Limpeza do log do sistema
delete from logdados;

--Se quiser apagar os elementos e fluxo use estes:
delete from citsmart.bpm_sequenciafluxo;
delete from citsmart.bpm_elementofluxo;
delete from citsmart.bpm_fluxo;
delete from citsmart.bpm_tipofluxo;

