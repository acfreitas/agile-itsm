-- Oracle

-- Início Murilo Gabriel 07/08/2013

UPDATE bpm_elementofluxo SET grupos = '#{solicitacaoServico.grupoNivel1}' WHERE idelemento = 22;

UPDATE bpm_elementofluxo SET grupos = '#{solicitacaoServico.grupoAtual}' WHERE idelemento = 23;

UPDATE bpm_elementofluxo SET script = '#{execucaoFluxo}.encerra();' WHERE idelemento = 24;

-- Fim Murilo Gabriel
