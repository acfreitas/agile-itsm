package br.com.centralit.citcorpore.ajaxForms;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.IntegranteViagemService;
import br.com.centralit.citcorpore.negocio.ParecerService;
import br.com.centralit.citcorpore.negocio.PrestacaoContasViagemService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.negocio.RoteiroViagemService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class CorrigirPrestacaoContas extends PrestacaoContasViagem {
	/**
	 * Inicializa os dados ao carregar a tela.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		super.load(document, request, response);

		PrestacaoContasViagemDTO prestacaoContasViagemDTO = (PrestacaoContasViagemDTO) document.getBean();
		RequisicaoViagemDTO requisicaoViagemDTO = null;

		if(prestacaoContasViagemDTO.getIdSolicitacaoServico() != null) {
			requisicaoViagemDTO = this.restoreRequisicaoViagem(prestacaoContasViagemDTO.getIdSolicitacaoServico());
		}

		if(requisicaoViagemDTO != null) {
			requisicaoViagemDTO.setUsuarioDto(usuario);
			requisicaoViagemDTO.setIdTarefa(prestacaoContasViagemDTO.getIdTarefa());

			IntegranteViagemDTO integranteViagemDto = this.restoreIntegranteViagem(requisicaoViagemDTO.getIdSolicitacaoServico(), prestacaoContasViagemDTO.getIdTarefa());

			if(integranteViagemDto != null){
				prestacaoContasViagemDTO.setIntegranteViagemDto(integranteViagemDto);
				prestacaoContasViagemDTO = this.restorePrestacaoContasViagem(prestacaoContasViagemDTO);

				this.restoreInformacoesCorrecao(document, request, response, prestacaoContasViagemDTO);
			}
		}
	}


	/**
	 * Restaura todas as informações de prestação de contas
	 *
	 * @param prestacaoContasViagemDTO
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	private PrestacaoContasViagemDTO restorePrestacaoContasViagem(PrestacaoContasViagemDTO prestacaoContasViagemDTO) throws ServiceException, Exception {
		PrestacaoContasViagemService prestacaoContasViagemService = (PrestacaoContasViagemService) ServiceLocator.getInstance().getService(PrestacaoContasViagemService.class, null);

		Integer idPrestacaoContasViagem = prestacaoContasViagemService.recuperaIdPrestacaoSeExistir(prestacaoContasViagemDTO.getIdSolicitacaoServico(), prestacaoContasViagemDTO.getIntegranteViagemDto().getIdEmpregado());
		prestacaoContasViagemDTO.setIdPrestacaoContasViagem(idPrestacaoContasViagem);

		PrestacaoContasViagemDTO prestacaoContasViagemDTOAux = (PrestacaoContasViagemDTO) prestacaoContasViagemService.restore(prestacaoContasViagemDTO);
		prestacaoContasViagemDTOAux.setIntegranteViagemDto(prestacaoContasViagemDTO.getIntegranteViagemDto());

		return prestacaoContasViagemDTOAux;
	}

	/**
	 * Restaura as informeções de nome do integrante e o motivo que a prestação foi rejeitada
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @param prestacaoContasViagemDTO
	 * @throws Exception
	 */
	private void restoreInformacoesCorrecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, PrestacaoContasViagemDTO prestacaoContasViagemDTO) throws Exception {
		if(prestacaoContasViagemDTO != null && prestacaoContasViagemDTO.getIntegranteViagemDto() != null){
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoDTO = new EmpregadoDTO();

			empregadoDTO.setIdEmpregado(prestacaoContasViagemDTO.getIntegranteViagemDto().getIdEmpregado());
			empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);

			document.getElementById("nomeEmpregado").setInnerHTML(empregadoDTO.getNome());

			ParecerDTO parecerDto = this.restoreParecer(prestacaoContasViagemDTO);
			if(parecerDto != null) {
				document.getElementById("corrigir").setValue(parecerDto.getComplementoJustificativa());
			}
		}
	}

	/**
	 * Restaura a requisição de viagem para a qual o prestação de contas foi realizada
	 *
	 * @param idSolicitacao
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	private RequisicaoViagemDTO restoreRequisicaoViagem(Integer idSolicitacao) throws ServiceException, Exception{
		RequisicaoViagemDTO requisicaoViagemDTO = new RequisicaoViagemDTO();
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);

		requisicaoViagemDTO.setIdSolicitacaoServico(idSolicitacao);
		requisicaoViagemDTO = (RequisicaoViagemDTO) reqViagemService.restore(requisicaoViagemDTO);
		if(requisicaoViagemDTO != null) {
			return requisicaoViagemDTO;
		}

		return null;
	}

	/**
	 * Restaura o integrante ligado a esta prestação de contas
	 *
	 * @param idsolicitacaoServico
	 * @param idTarefa
	 * @return
	 * @throws Exception
	 */
	private IntegranteViagemDTO restoreIntegranteViagem(Integer idsolicitacaoServico, Integer idTarefa) throws Exception{
		IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
		return integranteViagemService.getIntegranteByIdSolicitacaoAndTarefa(idsolicitacaoServico, idTarefa);
	}

	/**
	 * Restaura o parecer que é informado quando a prestação de contas é rejeitada
	 *
	 * @param prestacaoContasViagemDTO
	 * @return
	 * @throws Exception
	 */
	private ParecerDTO restoreParecer(PrestacaoContasViagemDTO prestacaoContasViagemDTO) throws Exception {
		ParecerService parecerService = (ParecerService) ServiceLocator.getInstance().getService(ParecerService.class, null);

		ParecerDTO parecerDTO = new ParecerDTO();
		if (prestacaoContasViagemDTO.getIdAprovacao() != null) {
			parecerDTO.setIdParecer(prestacaoContasViagemDTO.getIdAprovacao());
			return (ParecerDTO) parecerService.restore(parecerDTO);
		}

		return null;
	}

	/**
	 * Adiciona o item a grid de itens de prestação de contas e verifica se a nota fiscal foi emitida há mais de 3 meses.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void adicionarItem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrestacaoContasViagemDTO prestacaoContasViagemDto = (PrestacaoContasViagemDTO) document.getBean();
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);

		RoteiroViagemDTO roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(prestacaoContasViagemDto.getIdIntegrante());

		if(roteiroViagemDTO != null && prestacaoContasViagemDto.getData() != null && !prestacaoContasViagemDto.getData().equals("")){
			Date dataViagem = UtilDatas.alteraData(roteiroViagemDTO.getIda(), -3, Calendar.MONTH ) ;
			Date dataPrestacao = UtilDatas.convertStringToDate(TipoDate.FORMAT_DATABASE , prestacaoContasViagemDto.getData().toString(), UtilI18N.getLocale());

			if(dataViagem.compareTo(dataPrestacao) > 0){
				document.alert("Nota fiscal emitida há mais de 3 meses!");
				document.getElementById("data").setValue("");
				document.getElementById("data").setFocus();
				return;
			}
		}
		document.executeScript("adicionarItem()");
	}
}
