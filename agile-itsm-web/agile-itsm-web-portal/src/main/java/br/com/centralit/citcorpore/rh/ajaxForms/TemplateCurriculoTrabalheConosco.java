package br.com.centralit.citcorpore.rh.ajaxForms;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CompetenciaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.EmailCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EnderecoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FuncaoExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.TelefoneCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.TreinamentoCurriculoDTO;
import br.com.centralit.citcorpore.rh.negocio.CandidatoService;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.CursoService;
import br.com.centralit.citcorpore.rh.negocio.HistoricoFuncionalService;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "unchecked", "rawtypes","unused" })
public class TemplateCurriculoTrabalheConosco extends TemplateCurriculo {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
		CandidatoDTO candidatoDTO = (CandidatoDTO) request.getSession().getAttribute("CANDIDATO");
		if (candidatoDTO != null) {
			request.setAttribute("nomeCandidatoAbrev", abreviarNomeCandidato(candidatoDTO.getNome()));
			request.setAttribute("nomeCandidato", candidatoDTO.getNome());
			request.setAttribute("emailCandidato", candidatoDTO.getEmail());
			request.setAttribute("metodoAutenticacao", candidatoDTO.getMetodoAutenticacao());
			
			document.executeScript("$('#idCandidato').val('" + candidatoDTO.getIdCandidato() + "')");
			document.executeScript("$('#nome').val('" + candidatoDTO.getNome() + "');");
			
			inicializarLoad(document, request, response);
			
			if(candidatoDTO.getCpf() != null && !candidatoDTO.getCpf().equals("")) {
				candidatoDTO.setCpf(candidatoDTO.getCpf().replaceAll("[^0-9]*", ""));
			}
			CurriculoDTO curriculoDto = curriculoService.findIdByCpf(candidatoDTO.getCpf());
			
			if(candidatoDTO != null && curriculoDto == null){
				document.executeScript("$('#auxEmailPrincipal').val(1).attr('readonly', true)");
				document.executeScript("validaPrincipalEmail("+1+")");
			}
//			if(candidatoDTO != null && curriculoDto == null){
//				document.executeScript("$('#auxEnderecoPrincipal').val(1).attr('readonly', true)");
//			}
			
			if (curriculoDto != null) {
				HistoricoFuncionalService historicoFuncionalService = (HistoricoFuncionalService) ServiceLocator.getInstance().getService(HistoricoFuncionalService.class, null);
				Date dataUltimaAtualizacao = historicoFuncionalService.getUltimaAtualizacao(curriculoDto.getIdCurriculo());
				request.setAttribute("ultimaAtualizacao", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, dataUltimaAtualizacao, UtilI18N.getLocale()));
				
				document.setBean(curriculoDto);
				restore(document, request, response);
				
				// Configura o CPF com o valor formatado
				if(candidatoDTO.getCpf() != null && !candidatoDTO.getCpf().equals("")) {
					document.executeScript("$('#cpf').val('" + candidatoDTO.getCpfFormatado() + "');");
				}
			} else {
				HTMLTable tblEmail = document.getTableById("tblEmail");
				tblEmail.deleteAllRows();
				List<EmailCurriculoDTO> listEmail = new ArrayList<>();
				EmailCurriculoDTO email = new EmailCurriculoDTO();
				email.setPrincipal("S");
				email.setDescricaoEmail(candidatoDTO.getEmail());
				listEmail.add(email);

				tblEmail.addRowsByCollection(listEmail, new String[] { "descricaoEmail", "principal", "" }, null, "Já existe registrado esta informação na tabela",
						new String[] { "gerarImgDelEmail" }, "funcaoClickRowEmail", null);
			}

		} else {
			document.executeScript("window.location.href = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/loginCandidato/loginCandidato.load'");
		}
		document.executeScript("$('#formPesquisaCurriculo').attr('action', '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()
				+ "/pages/templateCurriculoTrabalheConosco/templateCurriculoTrabalheConosco')");
	}

	
	@Override
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {

			CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
			CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);
			
			CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean();
			CandidatoDTO candidatoDTO = (CandidatoDTO) request.getSession().getAttribute("CANDIDATO");
			
			curriculoDto.setCpf(curriculoDto.getCpf().replaceAll("[^0-9]*", ""));
			
			Collection colTelefones = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(TelefoneCurriculoDTO.class, "colTelefones_Serialize", request);
			Collection<EnderecoCurriculoDTO> colEnderecos = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EnderecoCurriculoDTO.class, "colEnderecos_Serialize", request);
			Collection colFormacao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FormacaoCurriculoDTO.class, "colFormacao_Serialize", request);
			Collection colEmail = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EmailCurriculoDTO.class, "colEmail_Serialize", request);
			Collection colCertificacao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CertificacaoCurriculoDTO.class, "colCertificacao_Serialize", request);
			Collection colIdioma = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(IdiomaCurriculoDTO.class, "colIdioma_Serialize", request);
			Collection<CompetenciaCurriculoDTO> colCompetencias = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CompetenciaCurriculoDTO.class, "colCompetencias_Serialize", request);
			Collection<ExperienciaProfissionalCurriculoDTO> colExperienciaAux = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ExperienciaProfissionalCurriculoDTO.class, "colExperienciaProfissional_Serialize", request);
			Collection<TreinamentoCurriculoDTO> colTreinamento = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(TreinamentoCurriculoDTO.class, "colTreinamento_Serialize", request);
			
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			if(usuario != null){
				curriculoDto.setIdUsuarioSessao(usuario.getIdUsuario());
			}else{
				curriculoDto.setIdUsuarioSessao(null);
			}
			
			// Se o candidato for interno pode ser que não tenha CPF
			// Aqui é feita esta verificação
			if(candidatoDTO.getCpf() == null || candidatoDTO.getCpf().equals("")) {
				candidatoDTO.setCpf(curriculoDto.getCpf());
				// e se caso não tenha é atualizado no momento de atualizar o curriculo
				candidatoService.update(candidatoDTO);
			} else if(!candidatoDTO.getCpf().trim().equals(curriculoDto.getCpf())) {
				candidatoDTO.setCpf(curriculoDto.getCpf());
				// e se caso não tenha é atualizado no momento de atualizar o curriculo
				candidatoService.update(candidatoDTO);
			}
			
			if(!candidatoDTO.getNome().trim().equals(curriculoDto.getNome())) {
				candidatoDTO.setNome(curriculoDto.getNome());
				// e se caso não tenha é atualizado no momento de atualizar o curriculo
				candidatoService.update(candidatoDTO);
			}
			
			Collection<ExperienciaProfissionalCurriculoDTO> colExperienciaProfissional = new ArrayList<ExperienciaProfissionalCurriculoDTO>();
			if(colExperienciaAux != null){
				for (ExperienciaProfissionalCurriculoDTO experienciaDTO : colExperienciaAux) {
					Collection colFuncaoExperienciaProfissional = br.com.citframework.util.WebUtil.deserializeCollectionFromString(FuncaoExperienciaProfissionalCurriculoDTO.class,
							experienciaDTO.getColFuncaoSerialize());
					experienciaDTO.setColFuncao(colFuncaoExperienciaProfissional);
					colExperienciaProfissional.add(experienciaDTO);
				}
			}
			
			if(colTreinamento != null){
					curriculoDto.setColTreinamentos(colTreinamento);
			}

			UfDTO obj = new UfDTO();
			try {
				obj.setIdPais(Integer.parseInt(request.getParameter("idPais")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);
			List<UfDTO> ufs = (List) ufService.listByIdPais(obj);

			// TODO É NECESSÁRIO CORRIGIR TODA ESSA LÓGICA. O CORRETO É QUE O ID DA CIDADE JÁ VENHA SERIALIZADO DA TELA. DEVIDO A URGÊNCIA NA TRATATIVA FOI NECESSÁRIO EFETUAR A CORREÇÃO DE CIDADES COM
			// A MESMA LÓGICA QUE JÁ HAVIA SIDO IMPLEMENTADA.
			// adicionar idufs a coleção
			CidadesService cidadeService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
			EnderecoCurriculoDTO enderecoCurriculoDTOAux = new EnderecoCurriculoDTO();
			if (ufs != null) {
				if (colEnderecos != null && colEnderecos.size() > 0) {
					for (EnderecoCurriculoDTO endereco : colEnderecos) {
						for (UfDTO uf : ufs) {
							if (uf.getNomeUf().equalsIgnoreCase(endereco.getNomeUF())) {
								endereco.setEnderecoIdUF(uf.getIdUf());

								Collection<CidadesDTO> listCidade = cidadeService.listByIdUf(uf.getIdUf());

								for (CidadesDTO cidadeDto : listCidade) {
									if (cidadeDto.getNomeCidade().equalsIgnoreCase(endereco.getNomeCidade())) {
										endereco.setIdCidade(cidadeDto.getIdCidade());
										break;
									}
								}
							}
						}
					}
				}
			} else {
				if (colEnderecos != null && colEnderecos.size() > 0) {
					for (EnderecoCurriculoDTO endereco : colEnderecos) {
						List<CidadesDTO> listCidade = (List<CidadesDTO>) cidadeService.findByNome(endereco.getNomeCidade());
						if (listCidade != null) {
							for (CidadesDTO cidadeDto : listCidade) {
								if (cidadeDto.getNomeCidade().equalsIgnoreCase(endereco.getNomeCidade())) {
									endereco.setIdCidade(cidadeDto.getIdCidade());
									break;
								}
							}
						}
					}
				}
			}

			curriculoDto.setColTelefones(colTelefones);
			curriculoDto.setColEnderecos(colEnderecos);
			curriculoDto.setColFormacao(colFormacao);
			curriculoDto.setColEmail(colEmail);
			curriculoDto.setColExperienciaProfissional(colExperienciaProfissional);
			curriculoDto.setColCertificacao(colCertificacao);
			curriculoDto.setColIdioma(colIdioma);
			curriculoDto.setColCompetencias(colCompetencias);
			curriculoDto.setIdResponsavel(candidatoDTO.getIdCandidato());
			// seta o email principal
			if (curriculoDto.getColEmail() != null && curriculoDto.getColEmail().size() > 0) {
				for (Object obj2 : curriculoDto.getColEmail()) {
					EmailCurriculoDTO emailCurriculoDTO = (EmailCurriculoDTO) obj2;
					if (emailCurriculoDTO.getImagemEmailprincipal() != null && !emailCurriculoDTO.getImagemEmailprincipal().equals("")) {
						if (emailCurriculoDTO.getImagemEmailprincipal().equalsIgnoreCase("S")) {
							emailCurriculoDTO.setPrincipal("S");
						} else {
							emailCurriculoDTO.setPrincipal("N");
						}
					}
				}
			}
			

			// seta idTipoTelefone
			for (Object objTel : curriculoDto.getColTelefones()) {
				TelefoneCurriculoDTO telefone = (TelefoneCurriculoDTO) objTel;
//				telefone.setIdTipoTelefone(0);
				if (telefone.getIdTipoTelefone() == null) {
					if (telefone.getDescricaoTipoTelefone().trim().equalsIgnoreCase("RESIDENCIAL")) {
						telefone.setIdTipoTelefone(1);
					} else {
						telefone.setIdTipoTelefone(3);
					}
				}
			}

			Collection<UploadDTO> anexos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
			curriculoDto.setAnexos(anexos);
			Collection<UploadDTO> uploadDTOs = (Collection<UploadDTO>) request.getSession().getAttribute("ARQUIVOS_UPLOAD");

			if (uploadDTOs != null && uploadDTOs.size() > 0) {

				for (UploadDTO uploadDTO : uploadDTOs) {
					curriculoDto.setFoto(uploadDTO);
				}

			}

			if (curriculoDto.getIdCurriculo() == null || curriculoDto.getIdCurriculo().intValue() == 0) {
				curriculoDto.setListaNegra("N");
				curriculoDto = (CurriculoDTO) curriculoService.create(curriculoDto);
			} else {
				curriculoService.update(curriculoDto);
			}

			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			document.executeScript("fechar_aguarde();");
			document.executeScript("window.location.href = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/trabalheConosco/trabalheConosco.load'");

		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public Class getBeanClass() {
		return CurriculoDTO.class;
	}

	private String abreviarNomeCandidato(String nameUser) {
		StringBuilder finalNameUser = new StringBuilder();
		if (nameUser != null) {
			String[] array = new String[15];
			int index;

			if (nameUser.contains(" ")) {
				int cont = 0;

				nameUser = nameUser.trim();
				array = nameUser.split(" ");
				index = array.length;

				for (String nome : array) {
					if (cont == 0) {
						finalNameUser.append(nome.toUpperCase() + " ");
						cont++;
					} else {
						if (cont == index - 1) {
							finalNameUser.append(" " + nome.toUpperCase());
						} else {
							if (nome.length() > 3) {
								finalNameUser.append(nome.substring(0, 1).toUpperCase() + ". ");
							}
							cont++;
						}
					}
				}
			} else {
				finalNameUser.append(nameUser.toUpperCase());
			}
		}
		return finalNameUser.toString();
	}
	
	/**
	 * Busca curso e preenche tabela curso
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void buscaTreinamento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		CurriculoDTO curriculoDTO = (CurriculoDTO) document.getBean();
		
		
		
		CursoDTO cursoDTO = new CursoDTO();
		cursoDTO.setIdCurso(curriculoDTO.getIdCurso());
		CursoService cursoService = (CursoService) ServiceLocator.getInstance().getService(CursoService.class, null);
		cursoDTO = (CursoDTO) cursoService.restore(cursoDTO);
		
		TreinamentoCurriculoDTO treinamentoCurriculoDTO = new TreinamentoCurriculoDTO();
		treinamentoCurriculoDTO.setTreinamento(cursoDTO.getDescricao());
		treinamentoCurriculoDTO.setDescricaoTreinamento(cursoDTO.getDetalhe());
		treinamentoCurriculoDTO.setIdCurso(cursoDTO.getIdCurso());
		
		
		HTMLTable tblTreinamento =  document.getTableById("tblTreinamento");
		tblTreinamento.addRow(treinamentoCurriculoDTO, new String[]{"treinamento","descricaoTreinamento" ,""}, null, null, new String[]{"gerarImagemDelTreinamento"}, null, null);

		document.getElementById("idCurso").setValue("");
		document.getElementById("treinamento").setValue("");
		
	}
	
	public void verificarParametroAnexos(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		
		String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
		if(DISKFILEUPLOAD_REPOSITORYPATH == null){
			DISKFILEUPLOAD_REPOSITORYPATH = "";
		}
		if(DISKFILEUPLOAD_REPOSITORYPATH.equals("")){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.anexosUploadSemParametro"));
			return;
		}
		File pasta = new File(DISKFILEUPLOAD_REPOSITORYPATH);
		if(!pasta.exists()){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.pastaIndicadaNaoExiste"));
			return;
		}
		document.executeScript("abrirModalFoto();");
	}
	
	public void verificaEmailPrincipalJaCadastrado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		
		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
		
		Integer idCurriculo = 0;
		
		if(request.getParameter("idCurriculo") != null && request.getParameter("idCurriculo") != "") {
			idCurriculo = Integer.parseInt(request.getParameter("idCurriculo"));
		}
		
		String email = request.getParameter("email");
		
		out.print("{\"existe\":\"" + curriculoService.verificaEmailPrincipalJaCadastrado(idCurriculo, email) + "\"}");
	}
	
	public void verificaCPFJaCadastrado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		
		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
		
		Integer idCurriculo = 0;
		
		if(request.getParameter("idCurriculo") != null && request.getParameter("idCurriculo") != "") {
			idCurriculo = Integer.parseInt(request.getParameter("idCurriculo"));
		}
		
		String cpf = (request.getParameter("cpf")).replaceAll("[^0-9]", "");
		
		out.print("{\"existe\":\"" + curriculoService.verificaCPFJaCadastrado(idCurriculo, cpf) + "\"}");
	}
}
