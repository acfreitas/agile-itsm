package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.PesquisaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.citframework.service.CrudService;

public interface CurriculoService extends CrudService {

    String retornarCaminhoFoto(final Integer idCurriculo) throws Exception;

    Collection<CurriculoDTO> listaCurriculosPorCriterios(final PesquisaCurriculoDTO pesquisaCurriculoDto) throws Exception;

    CurriculoDTO findIdByCpf(final String cpf) throws Exception;

    boolean verificaEmailPrincipalJaCadastrado(final Integer idCurriculo, final String email) throws Exception;

    boolean verificaCPFJaCadastrado(final Integer idCurriculo, final String cpf) throws Exception;

    Integer calculaTotalPaginas(final RequisicaoPessoalDTO requisicaoPessoalDTO, final String idsCurriculosTriados, final Integer itensPorPagina) throws Exception;

}
