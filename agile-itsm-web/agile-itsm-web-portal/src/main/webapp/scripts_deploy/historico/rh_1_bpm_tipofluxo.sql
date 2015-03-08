/*
-- Query: SELECT * FROM citsmart.bpm_tipofluxo where idtipofluxo in (6,7)
LIMIT 0, 1000

-- Date: 2013-04-24 10:11
*/
INSERT INTO `bpm_tipofluxo` (`idtipofluxo`,`nomefluxo`,`descricao`,`nomeclassefluxo`) 
VALUES (51,'RequisicaoPessoal','Requisição de Pessoal','br.com.centralit.citcorpore.bpm.negocio.ExecucaoRequisicaoPessoal');
INSERT INTO `bpm_tipofluxo` 
(`idtipofluxo`,`nomefluxo`,`descricao`,`nomeclassefluxo`) VALUES (52,'SolicitacaoCargo','Solicitação de Cargo','br.com.centralit.citcorpore.bpm.negocio.ExecucaoSolicitacaoCargo');
