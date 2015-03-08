package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.integracao.CategoriaServicoDao;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ServicoServiceEjb extends CrudServiceImpl implements ServicoService {

    private ServicoDao dao;

    @Override
    protected ServicoDao getDao() {
        if (dao == null) {
            dao = new ServicoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCategoriaServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCategoriaServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCategoriaServico(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCategoriaServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdSituacaoServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdSituacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdSituacaoServico(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdSituacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdTipoDemandaAndIdCategoria(final Integer idTipoDemanda, final Integer idCategoria) throws Exception {
        try {
            return this.getDao().findByIdTipoDemandaAndIdCategoria(idTipoDemanda, idCategoria);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdTipoDemandaAndIdContrato(final Integer idTipoDemanda, final Integer idContrato, final Integer idCategoria) throws Exception {
        try {
            return this.getDao().findByIdTipoDemandaAndIdContrato(idTipoDemanda, idContrato, idCategoria);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String retornarSiglaPorIdOs(final Integer idOs) throws Exception {
        return this.getDao().retornaSiglaPorIdOs(idOs);
    }

    @Override
    public Collection findByIdServicoAndIdTipoDemandaAndIdCategoria(final Integer idServico, final Integer idTipoDemanda, final Integer idCategoria) throws Exception {
        try {
            return this.getDao().findByIdServicoAndIdTipoDemandaAndIdCategoria(idServico, idTipoDemanda, idCategoria);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    public ServicoDTO restoreByIdServico(final Integer idServico) {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();

        condicoes.add(new Condition("idServico", "=", idServico));

        ArrayList<ServicoDTO> retorno;
        try {
            retorno = (ArrayList<ServicoDTO>) this.getDao().findByCondition(condicoes, null);
            if (retorno != null) {
                return retorno.get(0);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Collection<ServicoDTO> findByServico(final Integer idServico) throws Exception {
        return this.getDao().findByServico(idServico);
    }

    @Override
    public Collection<ServicoDTO> findByServico(final Integer idServico, final String nome) throws Exception {
        return this.getDao().findByServico(idServico, nome);
    }

    @Override
    public Collection<ServicoDTO> listaQuantidadeServicoAnalitico(final ServicoDTO servicoDTO) throws Exception {
        return this.getDao().listaQuantidadeServicoAnalitico(servicoDTO);
    }

    @Override
    public ServicoDTO findByIdServico(final Integer idServico) throws Exception {
        return this.getDao().findByIdServico(idServico);
    }

    @Override
    public Collection<ServicoDTO> listAtivos() throws Exception {
        return this.getDao().listAtivos();
    }

    @Override
    public void desvincularServicosRelacionadosTemplate(final Integer idTemplate) throws Exception {
        final Collection<ServicoDTO> servicos = this.getDao().findByIdTemplate(idTemplate);

        if (servicos != null && !servicos.isEmpty()) {
            for (final ServicoDTO servico : servicos) {
                if (servico.getIdTemplateAcompanhamento() != null && servico.getIdTemplateAcompanhamento().intValue() == idTemplate.intValue()) {
                    servico.setIdTemplateAcompanhamento(null);
                }
                if (servico.getIdTemplateSolicitacao() != null && servico.getIdTemplateSolicitacao().intValue() == idTemplate.intValue()) {
                    servico.setIdTemplateSolicitacao(null);
                }
                this.getDao().update(servico);
            }
        }
    }

    @Override
    public Collection findByNomeAndContratoAndTipoDemandaAndCategoria(final Integer idTipoDemanda, final Integer idContrato, final Integer idCategoria, final String nome)
            throws Exception {
        return this.getDao().findByNomeAndContratoAndTipoDemandaAndCategoria(idTipoDemanda, idContrato, idCategoria, nome);
    }

    @Override
    public ServicoDTO findById(final Integer idServico) throws Exception {
        return this.getDao().findById(idServico);
    }

    @Override
    public String verificaIdCategoriaServico(final HashMap mapFields) throws Exception {
        final CategoriaServicoDao categoriaServicoDao = new CategoriaServicoDao();
        List<CategoriaServicoDTO> listaCategoriaServicoDTO = null;
        String id = mapFields.get("IDCATEGORIASERVICO").toString().trim();
        if (id == null || id.equals("")) {
            id = "0";
        }
        if (UtilStrings.soContemNumeros(id)) {
            final Integer idCategoriaServico = Integer.parseInt(id);
            listaCategoriaServicoDTO = categoriaServicoDao.findByIdCategoriaServico(idCategoriaServico);
        } else {
            listaCategoriaServicoDTO = categoriaServicoDao.findByNomeCategoria(id);
        }
        if (listaCategoriaServicoDTO != null && listaCategoriaServicoDTO.size() > 0) {
            return String.valueOf(listaCategoriaServicoDTO.get(0).getIdCategoriaServico());
        } else {
            return "0";
        }
    }

    @Override
    public Collection<ServicoDTO> listAtivosDiferenteContrato(final ServicoDTO servicoDto) throws Exception {
        return this.getDao().listAtivosDiferenteContrato(servicoDto);
    }

}
