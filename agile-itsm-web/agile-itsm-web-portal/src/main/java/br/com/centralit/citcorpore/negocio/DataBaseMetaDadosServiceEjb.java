package br.com.centralit.citcorpore.negocio;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.util.DataBaseMetaDadosUtil;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.SQLConfig;

/**
 *
 * @author flavio.santana
 *
 */
public class DataBaseMetaDadosServiceEjb extends CrudServiceImpl implements DataBaseMetaDadosService {

    private ObjetoNegocioService objetoNegocioService;
    private CamposObjetoNegocioService camposObjetoNegocioService;

    @Override
    protected CrudDAO getDao() {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void corrigeTabelaComplexidade() throws ServiceException, Exception {
        final List<ObjetoNegocioDTO> respList = (List<ObjetoNegocioDTO>) this.getObjetoNegocioService().findByNomeTabelaDB("complexidade");

        Integer idCampoNegContrato = null;
        Integer idCampoNegComplexidade = null;
        if (respList != null && !respList.isEmpty()) {
            for (final ObjetoNegocioDTO objetoNegocioDTO : respList) {
                final List<CamposObjetoNegocioDTO> listIdContrato = (List<CamposObjetoNegocioDTO>) this.getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
                        objetoNegocioDTO.getIdObjetoNegocio(), "IDCONTRATO");
                for (final CamposObjetoNegocioDTO camposObjetoNegocioDTO : listIdContrato) {
                    idCampoNegContrato = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
                }

                final List<CamposObjetoNegocioDTO> listComplexidade = (List<CamposObjetoNegocioDTO>) this.getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
                        objetoNegocioDTO.getIdObjetoNegocio(), "COMPLEXIDADE");
                for (final CamposObjetoNegocioDTO camposObjetoNegocioDTO2 : listComplexidade) {
                    idCampoNegComplexidade = camposObjetoNegocioDTO2.getIdCamposObjetoNegocio();
                }

                if (idCampoNegContrato != null && idCampoNegComplexidade != null) {
                    this.getCamposObjetoNegocioService().updateComplexidade(idCampoNegContrato, idCampoNegComplexidade);
                }

            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void corrigeTabelaSla() throws ServiceException, Exception {
        final List<ObjetoNegocioDTO> respList = (List<ObjetoNegocioDTO>) this.getObjetoNegocioService().findByNomeTabelaDB("SLAREQUISITOSLA");

        Integer idCampoNegContrato = null;
        Integer idCampoNegComplexidade = null;

        if (respList != null && !respList.isEmpty()) {
            for (final ObjetoNegocioDTO objetoNegocioDTO : respList) {
                final List<CamposObjetoNegocioDTO> listIdContrato = (List<CamposObjetoNegocioDTO>) this.getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
                        objetoNegocioDTO.getIdObjetoNegocio(), "IDREQUISITOSLA");
                for (final CamposObjetoNegocioDTO camposObjetoNegocioDTO : listIdContrato) {
                    idCampoNegContrato = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
                }

                final List<CamposObjetoNegocioDTO> listComplexidade = (List<CamposObjetoNegocioDTO>) this.getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
                        objetoNegocioDTO.getIdObjetoNegocio(), "IDACORDONIVELSERVICO");
                for (final CamposObjetoNegocioDTO camposObjetoNegocioDTO2 : listComplexidade) {
                    idCampoNegComplexidade = camposObjetoNegocioDTO2.getIdCamposObjetoNegocio();
                }

                if (idCampoNegContrato != null && idCampoNegComplexidade != null) {
                    this.getCamposObjetoNegocioService().updateComplexidade(idCampoNegContrato, idCampoNegComplexidade);
                }

            }
        }
    }

    @Override
    public void corrigeTabelaFluxoServico() throws Exception {
        final List<ObjetoNegocioDTO> respList = (List<ObjetoNegocioDTO>) this.getObjetoNegocioService().findByNomeTabelaDB("FLUXOSERVICO");

        Integer idCampoNegServicoContrato = null;
        Integer idCampoNegIdTipoFluxo = null;
        Integer idCampoNegFase = null;

        if (respList != null && !respList.isEmpty()) {
            for (final ObjetoNegocioDTO objetoNegocioDTO : respList) {
                final List<CamposObjetoNegocioDTO> listIdServicoContrato = (List<CamposObjetoNegocioDTO>) this.getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
                        objetoNegocioDTO.getIdObjetoNegocio(), "IDSERVICOCONTRATO");
                for (final CamposObjetoNegocioDTO camposObjetoNegocioDTO : listIdServicoContrato) {
                    idCampoNegServicoContrato = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
                }

                final List<CamposObjetoNegocioDTO> listTipoFluxo = (List<CamposObjetoNegocioDTO>) this.getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
                        objetoNegocioDTO.getIdObjetoNegocio(), "IDTIPOFLUXO");
                for (final CamposObjetoNegocioDTO camposObjetoNegocioDTO2 : listTipoFluxo) {
                    idCampoNegIdTipoFluxo = camposObjetoNegocioDTO2.getIdCamposObjetoNegocio();
                }

                final List<CamposObjetoNegocioDTO> listFase = (List<CamposObjetoNegocioDTO>) this.getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
                        objetoNegocioDTO.getIdObjetoNegocio(), "IDFASE");
                for (final CamposObjetoNegocioDTO camposObjetoNegocioDTO2 : listFase) {
                    idCampoNegFase = camposObjetoNegocioDTO2.getIdCamposObjetoNegocio();
                }

                if (idCampoNegServicoContrato != null && idCampoNegIdTipoFluxo != null) {
                    this.getCamposObjetoNegocioService().updateFluxoServico(idCampoNegServicoContrato, idCampoNegIdTipoFluxo, idCampoNegFase);
                }
            }
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection getDataBaseMetaDadosUtil() throws Exception {
        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        final VisaoDao visaoDao = new VisaoDao();
        Connection con = null;
        try {
            con = visaoDao.getTransactionControler().getConnection();
            String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, "");
            if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
                DB_SCHEMA = null;
            } else if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")) {
                DB_SCHEMA = "citsmart";
            }
            final Collection colObsNegocio = dataBaseMetaDadosUtil.readTables(con, DB_SCHEMA, DB_SCHEMA, null, true);
            return colObsNegocio;
        } finally {
            con.close();
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    @Deprecated
    public String carregaTodosMetaDados() throws Exception {
        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        final VisaoDao visaoDao = new VisaoDao();
        final Connection con = visaoDao.getTransactionControler().getConnection();
        String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, "");
        if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
            DB_SCHEMA = null;
        } else if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")) {
            DB_SCHEMA = "citsmart";
        }

        // Desabilitando as tabelas para garantir que as que não existam mais não fiquem ativas
        this.desabilitaTabelas();

        final Collection colObsNegocio = dataBaseMetaDadosUtil.readTables(con, DB_SCHEMA, DB_SCHEMA, null, true);
        con.close();

        String carregados = "";
        if (!colObsNegocio.isEmpty()) {
            for (final Iterator it = colObsNegocio.iterator(); it.hasNext();) {
                final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();

                System.out.println("-----: Objeto de Negocio: " + objetoNegocioDTO.getNomeTabelaDB());
                carregados += objetoNegocioDTO.getNomeTabelaDB() + "<br>";

                final Collection colObjs = this.getObjetoNegocioService().findByNomeTabelaDB(objetoNegocioDTO.getNomeTabelaDB());
                if (colObjs == null || colObjs.size() == 0) {
                    System.out.println("----------: Criando....  " + objetoNegocioDTO.getNomeTabelaDB());
                    this.getObjetoNegocioService().create(objetoNegocioDTO);
                } else {
                    final ObjetoNegocioDTO objetoNegocioAux = (ObjetoNegocioDTO) ((List) colObjs).get(0);
                    objetoNegocioDTO.setIdObjetoNegocio(objetoNegocioAux.getIdObjetoNegocio());
                    System.out.println("----------: Atualizando....  " + objetoNegocioDTO.getNomeTabelaDB() + "    Id Interno: " + objetoNegocioAux.getIdObjetoNegocio());
                    this.getObjetoNegocioService().update(objetoNegocioDTO);
                }
            }

            this.corrigeTabelaComplexidade();
            this.corrigeTabelaSla();
            this.corrigeTabelaFluxoServico();

            carregados = "<b>Finalizado!</b> <br><b>Tabelas carregadas:</b> <br>" + carregados;
        } else {
            carregados += "Não foi possível carregar metadados para o Schema:" + DB_SCHEMA;
        }

        return carregados;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public String carregaTodosMetaDados(final Collection colecao) throws Exception {

        // Desabilitando as tabelas para garantir que as que não existam mais não fiquem ativas
        this.desabilitaTabelas();

        final Collection colObsNegocio = colecao;
        String carregados = "";
        if (!colObsNegocio.isEmpty()) {
            for (final Iterator it = colObsNegocio.iterator(); it.hasNext();) {
                final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();

                System.out.println("-----: Objeto de Negocio: " + objetoNegocioDTO.getNomeTabelaDB());
                carregados += objetoNegocioDTO.getNomeTabelaDB() + "<br>";

                final Collection colObjs = this.getObjetoNegocioService().findByNomeTabelaDB(objetoNegocioDTO.getNomeTabelaDB());
                if (colObjs == null || colObjs.size() == 0) {
                    System.out.println("----------: Criando....  " + objetoNegocioDTO.getNomeTabelaDB());
                    this.getObjetoNegocioService().create(objetoNegocioDTO);
                } else {
                    final ObjetoNegocioDTO objetoNegocioAux = (ObjetoNegocioDTO) ((List) colObjs).get(0);
                    objetoNegocioDTO.setIdObjetoNegocio(objetoNegocioAux.getIdObjetoNegocio());
                    System.out.println("----------: Atualizando....  " + objetoNegocioDTO.getNomeTabelaDB() + "    Id Interno: " + objetoNegocioAux.getIdObjetoNegocio());
                    this.getObjetoNegocioService().update(objetoNegocioDTO);
                }
            }

            this.corrigeTabelaComplexidade();
            this.corrigeTabelaSla();
            this.corrigeTabelaFluxoServico();

            carregados = "<b>Finalizado!</b> <br><b>Tabelas carregadas:</b> <br>" + carregados;
        }

        return carregados;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void desabilitaTabelas() throws LogicException, ServiceException, Exception {
        final Collection<ObjetoNegocioDTO> listObjetoNegocio =  this.getObjetoNegocioService().list();

        for (final ObjetoNegocioDTO objetoNegocioDTO : listObjetoNegocio) {
            objetoNegocioDTO.setSituacao("I");
            this.getObjetoNegocioService().updateDisable(objetoNegocioDTO);
        }

    }

    private ObjetoNegocioService getObjetoNegocioService() throws ServiceException, Exception {
        if (objetoNegocioService == null) {
            return (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        } else {
            return objetoNegocioService;
        }
    }

    private CamposObjetoNegocioService getCamposObjetoNegocioService() throws ServiceException, Exception {
        if (camposObjetoNegocioService == null) {
            return (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
        } else {
            return camposObjetoNegocioService;
        }
    }

}
