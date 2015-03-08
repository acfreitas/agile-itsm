package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.GrupoAssinaturaDTO;
import br.com.centralit.citcorpore.bean.ItemGrupoAssinaturaDTO;
import br.com.centralit.citcorpore.integracao.GrupoAssinaturaDAO;
import br.com.centralit.citcorpore.integracao.ItemGrupoAssinaturaDAO;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author euler.ramos
 *
 */
public class GrupoAssinaturaServiceEjb extends CrudServiceImpl implements GrupoAssinaturaService {

    private GrupoAssinaturaDAO dao;

    @Override
    protected GrupoAssinaturaDAO getDao() {
        if (dao == null) {
            dao = new GrupoAssinaturaDAO();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model, final ArrayList<ItemGrupoAssinaturaDTO> listaAssinaturas) throws ServiceException, br.com.citframework.excecao.LogicException {
        GrupoAssinaturaDTO grupoAssinaturaDTO = (GrupoAssinaturaDTO) model;
        final GrupoAssinaturaDAO grupoAssinaturaDAO = this.getDao();
        final ItemGrupoAssinaturaDAO itemGrupoAssinaturaDAO = new ItemGrupoAssinaturaDAO();

        final TransactionControler tc = new TransactionControlerImpl(grupoAssinaturaDAO.getAliasDB());
        try {
            grupoAssinaturaDAO.setTransactionControler(tc);
            itemGrupoAssinaturaDAO.setTransactionControler(tc);

            tc.start();

            grupoAssinaturaDTO = (GrupoAssinaturaDTO) grupoAssinaturaDAO.create(grupoAssinaturaDTO);

            this.mantemAssinaturasGrupo(grupoAssinaturaDTO, listaAssinaturas, itemGrupoAssinaturaDAO);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return grupoAssinaturaDTO;
    }

    @Override
    public void update(final IDto model, final ArrayList<ItemGrupoAssinaturaDTO> listaAssinaturas) throws ServiceException, br.com.citframework.excecao.LogicException {
        final GrupoAssinaturaDTO grupoAssinaturaDTO = (GrupoAssinaturaDTO) model;
        final GrupoAssinaturaDAO grupoAssinaturaDAO = this.getDao();
        final ItemGrupoAssinaturaDAO itemGrupoAssinaturaDAO = new ItemGrupoAssinaturaDAO();

        final TransactionControler tc = new TransactionControlerImpl(grupoAssinaturaDAO.getAliasDB());
        try {
            grupoAssinaturaDAO.setTransactionControler(tc);
            itemGrupoAssinaturaDAO.setTransactionControler(tc);

            tc.start();

            grupoAssinaturaDAO.update(grupoAssinaturaDTO);

            this.mantemAssinaturasGrupo(grupoAssinaturaDTO, listaAssinaturas, itemGrupoAssinaturaDAO);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @SuppressWarnings("unchecked")
    private void mantemAssinaturasGrupo(final GrupoAssinaturaDTO grupoAssinaturaDTO, final ArrayList<ItemGrupoAssinaturaDTO> listaAssinaturas,
            final ItemGrupoAssinaturaDAO itemGrupoAssinaturaDAO) throws Exception {
        // Cadastrar as assinaturas lançadas pelo usuário e apagar as
        // assinaturas que não mais estão presentes no lançamento.
        final ArrayList<ItemGrupoAssinaturaDTO> listaBanco = (ArrayList<ItemGrupoAssinaturaDTO>) itemGrupoAssinaturaDAO.findByIdGrupoAssinatura(grupoAssinaturaDTO
                .getIdGrupoAssinatura());
        ItemGrupoAssinaturaDTO itemGrupoAssinaturaDTO;
        ItemGrupoAssinaturaDTO itemGrupoAssinaturaBanco;
        boolean encontrou;
        // Cadastrando as assinaturas lançadas pelo usuário
        if (listaAssinaturas != null && listaAssinaturas.size() > 0) {
            for (int i = 0; i < listaAssinaturas.size(); i++) {
                itemGrupoAssinaturaDTO = listaAssinaturas.get(i);
                encontrou = false;
                if (listaBanco != null && listaBanco.size() > 0) {

                    int j = 0;
                    do {
                        itemGrupoAssinaturaBanco = listaBanco.get(j);
                        // Verificando se já foi lançada esta assinatura
                        if (itemGrupoAssinaturaDTO.getIdAssinatura().equals(itemGrupoAssinaturaBanco.getIdAssinatura())
                                && itemGrupoAssinaturaDTO.getOrdem().equals(itemGrupoAssinaturaBanco.getOrdem())) {
                            encontrou = true;
                            // Removendo para não ser setado datafim, que será
                            // aplicada nos que restarem
                            listaBanco.remove(itemGrupoAssinaturaBanco);
                        } else {
                            j += 1;
                        }
                    } while (!encontrou && j < listaBanco.size());

                }
                if (!encontrou) {
                    itemGrupoAssinaturaDTO.setDataInicio(UtilDatas.getDataAtual());
                    itemGrupoAssinaturaDTO.setIdGrupoAssinatura(grupoAssinaturaDTO.getIdGrupoAssinatura());
                    itemGrupoAssinaturaDAO.create(itemGrupoAssinaturaDTO);
                }
            }
        }
        // Apagando as Assinaturas que não mais estão presentes no lançamento,
        // setando data fim para os registros que foram excluídos pelo usuário
        if (listaBanco != null && listaBanco.size() > 0) {
            for (final ItemGrupoAssinaturaDTO itemGADTO : listaBanco) {
                itemGADTO.setDataFim(UtilDatas.getDataAtual());
                itemGrupoAssinaturaDAO.update(itemGADTO);
            }
        }
    }

    @Override
    public boolean violaIndiceUnico(final GrupoAssinaturaDTO grupoAssinaturaDTO) throws ServiceException {
        return this.getDao().violaIndiceUnico(grupoAssinaturaDTO);
    }

    @Override
    public boolean naoEstaSendoUtilizado(final Integer idGrupoAssinatura) throws ServiceException {
        return this.getDao().naoEstaSendoUtilizado(idGrupoAssinatura);
    }

    @Override
    public Collection geraListaCamposAssinatura(final Integer idGrupoAssinatura, final HttpServletRequest request) throws Exception {
        final String ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS, "N");
        if (ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS != null && ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS.equalsIgnoreCase("S") && idGrupoAssinatura != null && idGrupoAssinatura > 0) {
            final ItemGrupoAssinaturaService itemGrupoAssinaturaService = (ItemGrupoAssinaturaService) ServiceLocator.getInstance().getService(ItemGrupoAssinaturaService.class,
                    null);
            return itemGrupoAssinaturaService.findByIdGrupoAssinatura(idGrupoAssinatura);
        } else {
            final ArrayList<ItemGrupoAssinaturaDTO> listaAssinaturas = new ArrayList<ItemGrupoAssinaturaDTO>();
            ItemGrupoAssinaturaDTO itemGrupoAssinaturaDTO = new ItemGrupoAssinaturaDTO();
            itemGrupoAssinaturaDTO.setFase(UtilI18N.internacionaliza(request, "citcorpore.comum.solicitacao"));
            itemGrupoAssinaturaDTO.setPapel(UtilI18N.internacionaliza(request, "citcorpore.comum.solicitanteServicos"));
            itemGrupoAssinaturaDTO.setOrdem(1);
            listaAssinaturas.add(itemGrupoAssinaturaDTO);

            itemGrupoAssinaturaDTO = new ItemGrupoAssinaturaDTO();
            itemGrupoAssinaturaDTO.setFase(UtilI18N.internacionaliza(request, "citcorpore.comum.autorizacao"));
            itemGrupoAssinaturaDTO.setPapel(UtilI18N.internacionaliza(request, "citcorpore.comum.gestorOperacionalContrato"));
            itemGrupoAssinaturaDTO.setOrdem(2);
            listaAssinaturas.add(itemGrupoAssinaturaDTO);

            itemGrupoAssinaturaDTO = new ItemGrupoAssinaturaDTO();
            itemGrupoAssinaturaDTO.setFase(UtilI18N.internacionaliza(request, "citcorpore.comum.aprovacao"));
            itemGrupoAssinaturaDTO.setPapel(UtilI18N.internacionaliza(request, "citcorpore.comum.gestorContrato"));
            itemGrupoAssinaturaDTO.setOrdem(3);
            listaAssinaturas.add(itemGrupoAssinaturaDTO);

            itemGrupoAssinaturaDTO = new ItemGrupoAssinaturaDTO();
            itemGrupoAssinaturaDTO.setFase(UtilI18N.internacionaliza(request, "citcorpore.comum.execucao"));
            itemGrupoAssinaturaDTO.setPapel(UtilI18N.internacionaliza(request, "citcorpore.comum.prepostoContratada"));
            itemGrupoAssinaturaDTO.setOrdem(4);
            listaAssinaturas.add(itemGrupoAssinaturaDTO);
            return listaAssinaturas;
        }

    }

}
