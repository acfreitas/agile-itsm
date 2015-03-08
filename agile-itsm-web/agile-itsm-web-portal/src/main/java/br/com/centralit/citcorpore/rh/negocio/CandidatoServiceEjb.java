package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.integracao.CandidatoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

/**
 * @author thiago.borges
 *
 */
@SuppressWarnings("rawtypes")
public class CandidatoServiceEjb extends CrudServiceImpl implements CandidatoService {

    private CandidatoDao dao;

    @Override
    protected CandidatoDao getDao() {
        if (dao == null) {
            dao = new CandidatoDao();
        }
        return dao;
    }

    @Override
    public void deletarCandidato(final IDto model, final DocumentHTML document) throws ServiceException, Exception {
        final CandidatoDTO candidatoDto = (CandidatoDTO) model;
        this.validaUpdate(model);
        candidatoDto.setDataFim(UtilDatas.getDataAtual());
        this.getDao().update(model);
    }

    @Override
    public boolean consultarCandidatosAtivos(final CandidatoDTO obj) throws Exception {
        return this.getDao().consultarCandidatosAtivos(obj);
    }

    @Override
    public Collection<CandidatoDTO> seCandidatoJaCadastrado(final CandidatoDTO candidatoDTO) throws Exception {
        return this.getDao().seCandidatoJaCadastrado(candidatoDTO);
    }

    @Override
    public Collection<CandidatoDTO> listarAtivos() throws Exception {
        return this.getDao().listarAtivos();
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

    @Override
    public Collection findByCpf(final String nome) throws Exception {
        return this.getDao().findByCPF(nome);
    }

    @Override
    public Collection findListTodosCandidatos() throws Exception {
        return this.getDao().findListTodosCandidatos();
    }

    @Override
    public Collection findByIdCandidatoJoinIdHistorico(final Integer idCandidato) throws Exception {
        return this.getDao().findByIdCandidatoJoinIdHistorico(idCandidato);
    }

    @Override
    public Collection recuperaColecaoCandidatos(final CandidatoDTO candidatoDto, final Integer pgAtual, final Integer qtdPaginacao) throws Exception {
        return this.getDao().recuperaColecaoCandidatos(candidatoDto, pgAtual, qtdPaginacao);
    }

    @Override
    public Integer calculaTotalPaginas(final Integer itensPorPagina, final CandidatoDTO candidatoDto) throws Exception {
        return this.getDao().calculaTotalPaginas(itensPorPagina, candidatoDto);
    }

    @Override
    public Integer restoreIdCandidato(final Integer idCurriculo) throws Exception {
        CandidatoDTO candidatoDTO = new CandidatoDTO();

        candidatoDTO = this.getDao().restoreByID(idCurriculo);
        Integer idCandidato = null;
        if (candidatoDTO != null) {
            idCandidato = candidatoDTO.getIdCandidato();
        }

        return idCandidato;
    }

    @Override
    public Integer findByCpfCurriculo(final String cpf) throws Exception {
        final CandidatoDTO candidatoDTO = this.getDao().findByCpfCurriculo(cpf);
        Integer idCandidato = null;
        if (candidatoDTO != null) {
            idCandidato = candidatoDTO.getIdCandidato();
        }

        return idCandidato;
    }

    @Override
    public CandidatoDTO findByEmail(final String email) throws Exception {
        return this.getDao().findByEmail(email);
    }

    @Override
    public CandidatoDTO restoreByCpf(final String nome) throws Exception {
        return this.getDao().restoreByCpf(nome);
    }

    @Override
    public CandidatoDTO findByHashID(final String nome) throws Exception {
        return this.getDao().findByHashID(nome);
    }

    @Override
    public void updateNotNull(final IDto obj) throws Exception {
        this.getDao().updateNotNull(obj);
    }

    @Override
    public CandidatoDTO restoreByIdEmpregado(final Integer idEmpregado) throws Exception {
        return this.getDao().restoreByIdEmpregado(idEmpregado);
    }
}
