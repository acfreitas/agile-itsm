package br.com.centralit.citcorpore.ajaxForms;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ControleRendimentoDTO;
import br.com.centralit.citcorpore.bean.ControleRendimentoExecucaoDTO;
import br.com.centralit.citcorpore.bean.ControleRendimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.ControleRendimentoUsuarioDTO;
import br.com.centralit.citcorpore.negocio.ControleRendimentoGrupoService;
import br.com.centralit.citcorpore.negocio.ControleRendimentoService;
import br.com.centralit.citcorpore.negocio.ControleRendimentoUsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class ControleRendimento extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Class getBeanClass() {
		return ControleRendimentoDTO.class;
	}

	public void carregaCombos(DocumentHTML document, HttpServletRequest request) throws Exception{

		Calendar dataAtual = Calendar.getInstance();
		String anoAtual = (dataAtual.get(GregorianCalendar.YEAR)) + "";

		//carrega informações da aba Apurar Mês
		((HTMLSelect) document.getSelectById("comboMes")).removeAllOptions();
		((HTMLSelect) document.getSelectById("comboMes")).addOption("1", UtilI18N.internacionaliza(request, "controle.mesJaneiro"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("2", UtilI18N.internacionaliza(request, "controle.mesFevereiro"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("3", UtilI18N.internacionaliza(request, "controle.mesMarco"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("4", UtilI18N.internacionaliza(request, "controle.mesAbril"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("5", UtilI18N.internacionaliza(request, "controle.mesMaio"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("6", UtilI18N.internacionaliza(request, "controle.mesJunho"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("7", UtilI18N.internacionaliza(request, "controle.mesJulho"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("8", UtilI18N.internacionaliza(request, "controle.mesAgosto"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("9", UtilI18N.internacionaliza(request, "controle.mesSetembro"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("10", UtilI18N.internacionaliza(request, "controle.mesOutubro"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("11", UtilI18N.internacionaliza(request, "controle.mesNovembro"));
		((HTMLSelect) document.getSelectById("comboMes")).addOption("12", UtilI18N.internacionaliza(request, "controle.mesDezembro"));

		((HTMLSelect) document.getSelectById("comboAno")).removeAllOptions();


		int i = 2005;
		while (i < 2040){
			 ((HTMLSelect) document.getSelectById("comboAno")).addOption(i + "", i + "");
			 i++;
		}
		((HTMLSelect) document.getSelectById("comboAno")).setValue(anoAtual);

		//carrega informações da aba Relatórios
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).removeAllOptions();
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("1", UtilI18N.internacionaliza(request, "controle.mesJaneiro"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("2", UtilI18N.internacionaliza(request, "controle.mesFevereiro"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("3", UtilI18N.internacionaliza(request, "controle.mesMarco"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("4", UtilI18N.internacionaliza(request, "controle.mesAbril"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("5", UtilI18N.internacionaliza(request, "controle.mesMaio"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("6", UtilI18N.internacionaliza(request, "controle.mesJunho"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("7", UtilI18N.internacionaliza(request, "controle.mesJulho"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("8", UtilI18N.internacionaliza(request, "controle.mesAgosto"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("9", UtilI18N.internacionaliza(request, "controle.mesSetembro"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("10", UtilI18N.internacionaliza(request, "controle.mesOutubro"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("11", UtilI18N.internacionaliza(request, "controle.mesNovembro"));
		((HTMLSelect) document.getSelectById("comboMesRelatorio")).addOption("12", UtilI18N.internacionaliza(request, "controle.mesDezembro"));

		((HTMLSelect) document.getSelectById("comboAnoRelatorio")).removeAllOptions();

		int j = 2005;
		while (j < 2040){
			 ((HTMLSelect) document.getSelectById("comboAnoRelatorio")).addOption(j + "", j + "");
			 j++;
		}

		((HTMLSelect) document.getSelectById("comboAnoRelatorio")).setValue(anoAtual);

	}

	public void fecharMes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Collection<ControleRendimentoExecucaoDTO> colecaoPessoas) throws ServiceException, Exception{
		ControleRendimentoExecucaoDTO controleExecucaoDto = (ControleRendimentoExecucaoDTO) document.getBean();
		ControleRendimentoService controleRendimentoService = (ControleRendimentoService) ServiceLocator.getInstance().getService(ControleRendimentoService.class, WebUtil.getUsuarioSistema(request));

		//confere se mês já acabou

		Calendar dataAtual = Calendar.getInstance();
		Integer anoAtual = (dataAtual.get(GregorianCalendar.YEAR));
		Integer mesAtual = (dataAtual.get(GregorianCalendar.MONTH) + 1); // tem que somar um porque os meses do Calendar vão de 0 a 11
		Integer diaAtual = (dataAtual.get(GregorianCalendar.DAY_OF_MONTH));

		String anoControle = controleExecucaoDto.getAno();
		String mesControle = controleExecucaoDto.getMes();

		if(Integer.parseInt(anoControle) == anoAtual){
			if(Integer.parseInt(mesControle) == mesAtual){
				if(!diaAtual.equals(confereQualUltimoDiaDoMes(controleExecucaoDto))){
					document.alert(UtilI18N.internacionaliza(request, "controle.aguardeAteOFimDoMes") + " " + diaAtual);
					return;
				}
			}
		}

		//verifica se o mês ainda não chegou
		if(Integer.parseInt(anoControle) == anoAtual){
			if(Integer.parseInt(mesControle) > mesAtual){
				document.alert(UtilI18N.internacionaliza(request, "controle.mesAindaNaoChegou"));
				return;
			}
		}

		//verifica se o mes já foi fechado
		Collection<ControleRendimentoDTO> listaConfereSeMesJaFoiFechado = (Collection<ControleRendimentoDTO>) controleRendimentoService.findByMesAno(controleExecucaoDto.getMes(), controleExecucaoDto.getAno(), controleExecucaoDto.getIdGrupo());

		if(listaConfereSeMesJaFoiFechado != null){
			document.alert(UtilI18N.internacionaliza(request, "controle.mesJaFechado"));
			return;
		}
		//gravar o registro relacionado ao grupo
		ControleRendimentoDTO controleRendimentoDto = new ControleRendimentoDTO();

		controleRendimentoDto.setAnoApuracao(controleExecucaoDto.getAno());
		controleRendimentoDto.setMesApuracao(controleExecucaoDto.getMes());
		controleRendimentoDto.setIdGrupo(controleExecucaoDto.getIdGrupo());
		controleRendimentoDto.setMediaRelativa(controleExecucaoDto.getMediaRelativa());
		controleRendimentoDto.setQtdPontos(Double.parseDouble(controleExecucaoDto.getQtdTotalPontos()));
		controleRendimentoDto.setQtdPontosPositivos(Integer.parseInt(controleExecucaoDto.getQtdPontosPositivos()));
		controleRendimentoDto.setQtdPontosNegativos(Integer.parseInt(controleExecucaoDto.getQtdPontosNegativos()));
		controleRendimentoDto.setQtdSolicitacoes(Integer.parseInt(controleExecucaoDto.getQtdSolicitacoes()));
		controleRendimentoDto.setDataHoraExecucao(UtilDatas.getDataHoraAtual());

		//transforma a média relativa em double de volta
		String mediaRelativa = controleRendimentoDto.getMediaRelativa();
		mediaRelativa = mediaRelativa.replace(",", ".");
		controleRendimentoDto.setMediaRelativa(mediaRelativa);

		if(Double.parseDouble(mediaRelativa) > Double.parseDouble(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NIVEL_EXCELENCIA_EXIGIDO, "90"))){
			controleRendimentoDto.setAprovado("S");
		} else {
			controleRendimentoDto.setAprovado("N");
		}

		controleRendimentoService.create(controleRendimentoDto);

		document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		ControleRendimentoGrupoService controleRendimentoGrupoService = (ControleRendimentoGrupoService) ServiceLocator.getInstance().getService(ControleRendimentoGrupoService.class, WebUtil.getUsuarioSistema(request));
		ControleRendimentoGrupoDTO controleRendimentoGrupoDto = new ControleRendimentoGrupoDTO();

		controleRendimentoGrupoDto.setIdControleRendimento(controleRendimentoDto.getIdControleRendimento());
		controleRendimentoGrupoDto.setIdGrupo(controleRendimentoDto.getIdGrupo());

		controleRendimentoGrupoService.create(controleRendimentoGrupoDto);

		//gravar o registro relacionado as pessoas
		ControleRendimentoUsuarioService controleRendimentoUsuarioService = (ControleRendimentoUsuarioService) ServiceLocator.getInstance().getService(ControleRendimentoUsuarioService.class, WebUtil.getUsuarioSistema(request));
		for (ControleRendimentoExecucaoDTO controleRendimentoExecucaoDTO : colecaoPessoas) {
			ControleRendimentoUsuarioDTO controleRendimentoUsuario = new ControleRendimentoUsuarioDTO();
			controleRendimentoUsuario.setIdControleRendimento(controleRendimentoDto.getIdControleRendimento());
			controleRendimentoUsuario.setIdGrupo(controleRendimentoDto.getIdGrupo());
			controleRendimentoUsuario.setIdUsuario(controleRendimentoExecucaoDTO.getIdPessoa());
			controleRendimentoUsuario.setQtdTotalPontos(Double.parseDouble(controleRendimentoExecucaoDTO.getQtdTotalPontos()));
			controleRendimentoUsuario.setAprovacao(controleRendimentoExecucaoDTO.getAprovacao());
			controleRendimentoUsuario.setAno(controleRendimentoDto.getAnoApuracao());
			controleRendimentoUsuario.setMes(controleRendimentoDto.getMesApuracao());
			controleRendimentoUsuario.setQtdPontosPositivos(controleRendimentoExecucaoDTO.getQtdPontosPositivos());
			controleRendimentoUsuario.setQtdPontosNegativos(controleRendimentoExecucaoDTO.getQtdPontosNegativos());
			controleRendimentoUsuario.setQtdItensEntregues(controleRendimentoExecucaoDTO.getQtdItensEntregues());
			controleRendimentoUsuario.setQtdItensRetornados(controleRendimentoExecucaoDTO.getQtdItensRetornados());

			controleRendimentoUsuarioService.create(controleRendimentoUsuario);
		}


	}


	public static Integer confereQualUltimoDiaDoMes(ControleRendimentoExecucaoDTO controleExecucaoDto){

		Integer ultimoDia = 0;
		Integer mes = Integer.parseInt(controleExecucaoDto.getMes());
		if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){
			ultimoDia = 31;
		}
		else if(mes == 2){
			ultimoDia = 28;
		}
		else {
			ultimoDia = 30;
		}

		return ultimoDia;
	}
}
