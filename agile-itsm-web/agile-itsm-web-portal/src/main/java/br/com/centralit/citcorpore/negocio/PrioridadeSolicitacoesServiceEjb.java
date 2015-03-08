package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ImpactoDTO;
import br.com.centralit.citcorpore.bean.MatrizPrioridadeDTO;
import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.centralit.citcorpore.integracao.ImpactoDAO;
import br.com.centralit.citcorpore.integracao.MatrizPrioridadeDAO;
import br.com.centralit.citcorpore.integracao.UrgenciaDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * Classe que encapsula os serviços referente ao cadastro de Prioridade
 *
 * @author rodrigo.oliveira
 *
 */
@SuppressWarnings("rawtypes")
public class PrioridadeSolicitacoesServiceEjb extends CrudServiceImpl implements PrioridadeSolicitacoesService {

    private ImpactoDAO impactoDAO;
    private UrgenciaDAO urgenciaDAO;
    private MatrizPrioridadeDAO matrizPrioridadeDAO;

    @Override
    protected CrudDAO getDao() {
        return null;
    }

    @Override
    public Collection findById(final Integer idMatrizPrioridade) throws Exception {
        return null;
    }

    @Override
    public void restaurarGridMatrizPrioridade(final DocumentHTML document, final Collection<MatrizPrioridadeDTO> matrizPrioridade) {

    }

    @Override
    public Integer consultaValorPrioridade(final Integer idImpacto, final Integer idUrgencia) {
        return null;
    }

    @Override
    public boolean consultaCadastros() throws Exception {
        boolean flag = false;

        final Collection listImpacto = this.getImpactoDAO().list();
        if (!listImpacto.isEmpty()) {
            final Collection listUrgencia = this.getUrgenciaDAO().list();
            if (!listUrgencia.isEmpty()) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public IDto restoreImpactoBySigla(final ImpactoDTO impacto) throws Exception {
        ImpactoDTO impactoResp = new ImpactoDTO();
        final List<ImpactoDTO> resp = this.getImpactoDAO().restoreBySigla(impacto.getSiglaImpacto().trim().toString().toUpperCase());
        if (resp == null) {
            return null;
        }
        for (final ImpactoDTO imp : resp) {
            impactoResp = imp;
        }
        return impactoResp;
    }

    @Override
    public IDto restoreUrgenciaBySigla(final UrgenciaDTO urgencia) throws Exception {
        UrgenciaDTO urgenciaResp = new UrgenciaDTO();
        final List<UrgenciaDTO> resp = this.getUrgenciaDAO().restoreBySigla(urgencia.getSiglaUrgencia().trim().toString().toUpperCase());
        if (resp == null) {
            return null;
        }
        for (final UrgenciaDTO urg : resp) {
            urgenciaResp = urg;
        }
        return urgenciaResp;
    }

    @Override
    public void createImpacto(final IDto impacto) throws Exception {
        this.getImpactoDAO().create(impacto);
    }

    @Override
    public void deleteImpacto() throws Exception {
        this.getImpactoDAO().deleteImpacto();
    }

    @Override
    public void createUrgencia(final IDto urgencia) throws Exception {
        this.getUrgenciaDAO().create(urgencia);
    }

    @Override
    public void deleteUrgencia() throws Exception {
        this.getUrgenciaDAO().deleteUrgencia();
    }

    @Override
    public void createMatrizPrioridade(final IDto matrizPrioridade) throws Exception {
        this.getMatrizPrioridadeDAO().create(matrizPrioridade);
    }

    @Override
    public void deleteMatrizPrioridade() throws Exception {
        this.getMatrizPrioridadeDAO().deleteMatriz();
    }

    @Override
    public Collection consultaImpacto() throws Exception {
        return this.getImpactoDAO().list();
    }

    @Override
    public Collection consultaUrgencia() throws Exception {
        return this.getUrgenciaDAO().list();
    }

    @Override
    public Collection consultaMatrizPrioridade() throws Exception {
        return this.getMatrizPrioridadeDAO().list();
    }

    @Override
    public IDto restoreImpacto(final IDto impacto) throws Exception {
        return this.getImpactoDAO().restore(impacto);
    }

    @Override
    public IDto restoreUrgencia(final IDto urgencia) throws Exception {
        return this.getUrgenciaDAO().restore(urgencia);
    }

    /**
     * @return the impactoDAO
     */
    public ImpactoDAO getImpactoDAO() {
        if (impactoDAO == null) {
            impactoDAO = new ImpactoDAO();
        }
        return impactoDAO;
    }

    /**
     * @return the urgenciaDAO
     */
    public UrgenciaDAO getUrgenciaDAO() {
        if (urgenciaDAO == null) {
            urgenciaDAO = new UrgenciaDAO();
        }
        return urgenciaDAO;
    }

    /**
     * @return the matrizPrioridadeDAO
     */
    public MatrizPrioridadeDAO getMatrizPrioridadeDAO() {
        if (matrizPrioridadeDAO == null) {
            matrizPrioridadeDAO = new MatrizPrioridadeDAO();
        }
        return matrizPrioridadeDAO;
    }

    @Override
    public boolean verificaImpactoJaExiste(final ImpactoDTO impacto) throws Exception {
        final List<ImpactoDTO> lista = this.getImpactoDAO().restoreByNivel(impacto.getNivelImpacto());
        if (lista != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean verificaUrgenciaJaExiste(final UrgenciaDTO urgencia) throws Exception {
        final List<UrgenciaDTO> lista = this.getUrgenciaDAO().restoreByNivel(urgencia.getNivelUrgencia());
        if (lista != null) {
            return true;
        }
        return false;
    }

}
