package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.RelatorioSolicitacaoReabertaDTO;
import br.com.citframework.service.CrudService;

public interface RelatorioSolicitacaoReabertaService extends CrudService {

    ArrayList<RelatorioSolicitacaoReabertaDTO> listSolicitacaoReaberta(final RelatorioSolicitacaoReabertaDTO relatorioSolicitacaoReabertaDTO);

}
