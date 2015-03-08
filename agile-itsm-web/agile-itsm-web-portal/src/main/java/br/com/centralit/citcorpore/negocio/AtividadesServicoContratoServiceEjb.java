package br.com.centralit.citcorpore.negocio;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ComplexidadeDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.integracao.AtividadesOSDao;
import br.com.centralit.citcorpore.integracao.AtividadesServicoContratoDao;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.OSDao;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"rawtypes", "unchecked"})
public class AtividadesServicoContratoServiceEjb extends CrudServiceImpl implements AtividadesServicoContratoService {

    private AtividadesServicoContratoDao dao;

    @Override
    protected AtividadesServicoContratoDao getDao() {
        if (dao == null) {
            dao = new AtividadesServicoContratoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdServicoContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdServicoContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection obterAtividadesAtivasPorIdServicoContrato(final Integer idServicoContrato) throws ServiceException {
        final Collection colRetorno = new ArrayList();
        try {
            final Collection col = this.getDao().obterAtividadesAtivasPorIdServicoContrato(idServicoContrato);
            if (col != null && col.size() > 0) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final AtividadesServicoContratoDTO atividadesServicoContratoDTO = (AtividadesServicoContratoDTO) it.next();
                    if (atividadesServicoContratoDTO.getDeleted() == null || atividadesServicoContratoDTO.getDeleted().equalsIgnoreCase("N")) {
                        colRetorno.add(atividadesServicoContratoDTO);
                    }
                }
            }
            return colRetorno;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Método para atualizar a observacao de os que estão em criação
     *
     * @param mapFields
     * @throws Exception
     */
    @Override
    public boolean atualizaObservacao(final HashMap mapFields) throws Exception {
        final String observacao = (String) mapFields.get("OBSATIVIDADE");
        final String idatividadeServico = (String) mapFields.get("IDATIVIDADESERVICOCONTRATO");
        final OSDao daoOs = new OSDao();
        final AtividadesOSDao daoAtividade = new AtividadesOSDao();
        try {
            final List<OSDTO> respOs = daoOs.buscaOsEmCriacao();
            return daoAtividade.atualizaObservacao(Integer.parseInt(idatividadeServico), observacao, respOs);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String calculaFormula(final HashMap mapFields) throws Exception {

        Double result = null;

        // Pegando valores do parâmetro recebido
        final String idContratoTxt = (String) mapFields.get("IDSERVICOCONTRATO");
        String horaTxt = (String) mapFields.get("HORA");
        final String complexidade = (String) mapFields.get("COMPLEXIDADE");
        final String quantidadeTxt = (String) mapFields.get("QUANTIDADE");
        final String periodoTxt = (String) mapFields.get("PERIODO");

        // Fazendo parse dos valores
        Double hora = new Double(0);
        Integer quantidade = new Integer(0);
        final DecimalFormat df = new DecimalFormat("0.00");

        if (horaTxt != null && !horaTxt.equals("")) {
            horaTxt = horaTxt.replace(",", ".");
            hora = Double.valueOf(horaTxt);
            df.setMaximumFractionDigits(2);
            df.format(hora);
        }

        if (quantidadeTxt != null && !quantidadeTxt.equals("")) {
            quantidade = Integer.parseInt(quantidadeTxt);
        }

        if (periodoTxt != null && !periodoTxt.equals("")) {
            Integer.parseInt(periodoTxt);
        }

        // Consultar valor da complexidade
        if (idContratoTxt != null && !idContratoTxt.equals("") && complexidade != null && !complexidade.equals("")) {
            final Integer idContrato = Integer.parseInt(idContratoTxt);
            final ContratoDao contratoDao = new ContratoDao();
            final Double valorComplex = contratoDao.consultaComplexidade(idContrato, complexidade);
            // Calcula custo
            result = hora * valorComplex * quantidade;
        } else {
            result = 0.0;
        }

        return UtilFormatacao.formatDouble(result, 2);
    }

    @Override
    public Double calculaFormula(final AtividadesServicoContratoDTO atividadesServicoContrato) throws Exception {
        Double result = null;

        // Pegando valores do parâmetro recebido
        final Integer idServicoContrato = atividadesServicoContrato.getIdServicoContrato();
        final Double hora = atividadesServicoContrato.getHora();
        final String complexidade = atividadesServicoContrato.getComplexidade();
        final Integer quantidade = atividadesServicoContrato.getQuantidade();
        // String periodoTxt = atividadesServicoContrato.getPeriodo();

        if (idServicoContrato != null && complexidade != null && !complexidade.trim().equals("")) {
            final ContratoDao contratoDao = new ContratoDao();
            final Double valorComplex = contratoDao.consultaComplexidade(idServicoContrato, complexidade);
            if (valorComplex != null && valorComplex != 0 && hora != null && hora != 0 && quantidade != null && quantidade != 0) {
                result = hora * valorComplex * quantidade;
            } else {
                result = 0.0;
            }
        } else {
            result = 0.0;
        }

        return result;
    }

    @Override
    public boolean verificaComplexidade(final HashMap mapFields) throws Exception {

        final String idContratoTxt = (String) mapFields.get("IDSERVICOCONTRATO");

        if (idContratoTxt != null) {
            final Integer idContrato = Integer.parseInt(idContratoTxt);
            final ContratoDao contratoDao = new ContratoDao();
            final Collection listaComplexidades = contratoDao.listaComplexidadePorContrato(idContrato);
            if (listaComplexidades != null && listaComplexidades.size() > 0) {
                for (final Iterator it = listaComplexidades.iterator(); it.hasNext();) {
                    final ComplexidadeDTO complexidadeDTO = (ComplexidadeDTO) it.next();
                    if (complexidadeDTO.getValorComplexidade() != null) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public Collection preencheComboServicoContrato(final HashMap mapFields, final String language) throws Exception {
        final String idContratoTxt = (String) mapFields.get("IDCONTRATO");;
        Collection colServicoContrato = new ArrayList();

        if (idContratoTxt != null) {
            colServicoContrato = this.obtemServicosContratoAtivos(Integer.parseInt(idContratoTxt));
            if (language != null && !language.equals("")) {
                mapFields.put("SERVICOCONTRATOSERIALIZADO", WebUtil.serializeObjects(colServicoContrato, language));
            } else {
                mapFields.put("SERVICOCONTRATOSERIALIZADO", WebUtil.serializeObjects(colServicoContrato));
            }
            return colServicoContrato;
        }
        return colServicoContrato;

    }

    private Collection obtemServicosContratoAtivos(final Integer idServicoContrato) throws ServiceException, Exception {
        final ServicoContratoService serviceContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
        final ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);

        final Collection colServicosContrato = serviceContratoService.findByIdContrato(idServicoContrato);

        final List<ServicoContratoDTO> colFinal = new ArrayList();

        if (colServicosContrato != null) {
            for (final Iterator it = colServicosContrato.iterator(); it.hasNext();) {
                final ServicoContratoDTO servicoContratoAux = (ServicoContratoDTO) it.next();

                final ServicoContratoDTO servicoContratoFinal = new ServicoContratoDTO();

                if (servicoContratoAux.getDeleted() != null && !servicoContratoAux.getDeleted().equalsIgnoreCase("N")) {
                    continue;
                }
                if (servicoContratoAux.getDataFim() != null && !servicoContratoAux.getDataFim().after(UtilDatas.getDataAtual())) {
                    continue;
                }

                if (servicoContratoAux.getIdServico() != null) {
                    ServicoDTO servicoDto = new ServicoDTO();
                    servicoDto.setIdServico(servicoContratoAux.getIdServico());
                    servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
                    if (servicoDto != null) {
                        if (servicoDto.getDeleted() != null && !servicoDto.getDeleted().equalsIgnoreCase("N")) {
                            continue;
                        }
                        servicoContratoFinal.setIdServicoContrato(servicoContratoAux.getIdServicoContrato());
                        servicoContratoFinal.setNomeServico(servicoDto.getNomeServico());

                        colFinal.add(servicoContratoFinal);
                    }
                }
            }
        }
        Collections.sort(colFinal, new ObjectSimpleComparator("getNomeServico", ObjectSimpleComparator.ASC));

        return colFinal;
    }

    @Override
    public Collection listarPorFormula() throws Exception {
        return this.getDao().listarPorFormula();
    }

}
