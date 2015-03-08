package br.com.centralit.citcorpore.metainfo.ajaxForms;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.metainfo.bean.DataBaseMetaDadosDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.util.DataBaseMetaDadosUtil;
import br.com.centralit.citcorpore.negocio.DataBaseMetaDadosService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilStrings;

public class DataBaseMetaDados extends AjaxFormAction {

	private ObjetoNegocioService objetoNegocioService;
	private CamposObjetoNegocioService camposObjetoNegocioService;

	@Override
    public Class<DataBaseMetaDadosDTO> getBeanClass() {
		return DataBaseMetaDadosDTO.class;
	}

	@Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

	}

    public void carregaMetaDados(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final DataBaseMetaDadosService data = (DataBaseMetaDadosService) ServiceLocator.getInstance().getService(
                DataBaseMetaDadosService.class, null);
        final DataBaseMetaDadosDTO dataBaseMetaDadosDTO = (DataBaseMetaDadosDTO) document.getBean();

        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
		String carregados = dataBaseMetaDadosUtil.sincronizaObjNegDB(dataBaseMetaDadosDTO.getNomeTabela(), true);
		carregados = UtilStrings.nullToVazio(carregados).replaceAll(",", "<br>");

		data.corrigeTabelaComplexidade();
		data.corrigeTabelaSla();
		data.corrigeTabelaFluxoServico();

		carregados = "<b>Finalizado!</b> <br><b>Tabelas carregadas:</b> <br>" + carregados;
		document.getElementById("divRetorno").setInnerHTML(carregados);
	}

    public void carregaTodosMetaDados(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final DataBaseMetaDadosService data = (DataBaseMetaDadosService) ServiceLocator.getInstance().getService(
                DataBaseMetaDadosService.class, null);
        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        final VisaoDao visaoDao = new VisaoDao();
        final Connection con = visaoDao.getTransactionControler().getConnection();
		String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA,"");
		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
			DB_SCHEMA = null;
		} else if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")){
		    DB_SCHEMA = "citsmart";
		}

		//Desabilitando as tabelas para garantir que as que não existam mais não fiquem ativas
		desabilitaTabelas();

        final Collection colObsNegocio = dataBaseMetaDadosUtil.readTables(con, DB_SCHEMA, DB_SCHEMA, null, true);
		con.close();

		String carregados = "";

        for (final Iterator it = colObsNegocio.iterator(); it.hasNext();) {
            final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();

			System.out.println("-----: Objeto de Negocio: " + objetoNegocioDTO.getNomeTabelaDB());
			carregados += objetoNegocioDTO.getNomeTabelaDB() + "<br>";

            final Collection colObjs = getObjetoNegocioService().findByNomeTabelaDB(objetoNegocioDTO.getNomeTabelaDB());
			if (colObjs == null || colObjs.size() == 0){
				System.out.println("----------: Criando....  " + objetoNegocioDTO.getNomeTabelaDB());
				getObjetoNegocioService().create(objetoNegocioDTO);
			}else{
                final ObjetoNegocioDTO objetoNegocioAux = (ObjetoNegocioDTO) ((List) colObjs).get(0);
				objetoNegocioDTO.setIdObjetoNegocio(objetoNegocioAux.getIdObjetoNegocio());
                System.out.println("----------: Atualizando....  " + objetoNegocioDTO.getNomeTabelaDB()
                        + "    Id Interno: " + objetoNegocioAux.getIdObjetoNegocio());
				getObjetoNegocioService().update(objetoNegocioDTO);
			}
		}

		data.corrigeTabelaComplexidade();
		data.corrigeTabelaSla();
		data.corrigeTabelaFluxoServico();

		carregados = "<b>Finalizado!</b> <br><b>Tabelas carregadas:</b> <br>" + carregados;
		document.getElementById("divRetorno").setInnerHTML(carregados);
	}

	private void desabilitaTabelas() throws LogicException, ServiceException, Exception{
        final Collection<ObjetoNegocioDTO> listObjetoNegocio = getObjetoNegocioService().list();

        for (final ObjetoNegocioDTO objetoNegocioDTO : listObjetoNegocio) {
			objetoNegocioDTO.setSituacao("I");
			getObjetoNegocioService().updateDisable(objetoNegocioDTO);
		}

	}

	private ObjetoNegocioService getObjetoNegocioService() throws ServiceException, Exception{
		if(objetoNegocioService == null){
			return (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        }
			return objetoNegocioService;
		}

	private CamposObjetoNegocioService getCamposObjetoNegocioService() throws ServiceException, Exception {
		if(camposObjetoNegocioService == null){
            return (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(
                    CamposObjetoNegocioService.class, null);
        }
			return camposObjetoNegocioService;
		}

}
