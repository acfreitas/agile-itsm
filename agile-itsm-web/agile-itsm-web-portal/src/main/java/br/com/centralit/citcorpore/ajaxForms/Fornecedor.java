package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.PaisDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.EnderecoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.PaisServico;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Fornecedor extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");

			return;
		}

		document.executeScript("$('#cnpj').unmask()");

		document.executeScript("$('#cnpj').attr('disabled', 'disabled')");

		this.preencherComboPaises(document, request, response);

		HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("comboUfs");

		if (comboUfs != null) {
			this.inicializarCombo(comboUfs, request);
		}

		HTMLSelect comboCidades = (HTMLSelect) document.getSelectById("comboCidades");

		if (comboCidades != null) {
			this.inicializarCombo(comboCidades, request);
		}
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FornecedorDTO fornecedorDTO = (FornecedorDTO) document.getBean();
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);

		if (fornecedorDTO != null && fornecedorDTO.getIdFornecedor() != null) {
			fornecedorDTO = (FornecedorDTO) fornecedorService.restore(fornecedorDTO);

			if (fornecedorDTO.getIdEndereco() != null) {
				EnderecoDTO enderecoDTO = new EnderecoDTO();
				enderecoDTO.setIdEndereco(fornecedorDTO.getIdEndereco());
				EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, null);
				enderecoDTO = (EnderecoDTO) enderecoService.restore(enderecoDTO);

				fornecedorDTO.setLogradouro(enderecoDTO.getLogradouro());
				fornecedorDTO.setNumero(enderecoDTO.getNumero());
				fornecedorDTO.setComplemento(enderecoDTO.getComplemento());
				fornecedorDTO.setBairro(enderecoDTO.getBairro());
				fornecedorDTO.setCep(enderecoDTO.getCep());

				// País
				if (enderecoDTO.getIdPais() != null) {
					fornecedorDTO.setIdPais(enderecoDTO.getIdPais());
					HTMLElement idPais = document.getElementById("idPais");
					idPais.setValue(enderecoDTO.getIdPais().toString());
					this.preencherComboPaises(document, request, response);
				}

				// Uf
				if (enderecoDTO.getIdUf() != null) {
					fornecedorDTO.setIdUf(enderecoDTO.getIdUf());
					HTMLElement idUf = document.getElementById("idUf");
					idUf.setValue(enderecoDTO.getIdUf().toString());
					this.preencherComboUfs(document, request, response);
				}

				// Cidade
				if (enderecoDTO.getIdCidade() != null) {
					fornecedorDTO.setIdCidade(enderecoDTO.getIdCidade());
					HTMLElement idCidade = document.getElementById("idCidade");
					idCidade.setValue(enderecoDTO.getIdCidade().toString());
					this.preencherComboCidades(document, request, response);
				}
			}

			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(fornecedorDTO);

			String mascara = "";

			// Retirando a máscara antiga.
			document.executeScript("$('#cnpj').unmask()");

			if (fornecedorDTO.getTipoPessoa() != null && !fornecedorDTO.getTipoPessoa().equals("")) {
				document.executeScript("$('#comboTiposPessoa option[value=\"" + fornecedorDTO.getTipoPessoa() + "\"]').prop('selected', true)");

				if (fornecedorDTO.getTipoPessoa().equalsIgnoreCase("F")) {
					mascara = "999.999.999-99";
				} else if (fornecedorDTO.getTipoPessoa().equalsIgnoreCase("J")) {
					mascara = "99.999.999/9999-99";
				}

				// Aplicando a nova máscara ao campo.
				document.executeScript("$('#cnpj').mask(\"" + mascara + "\")");
			}
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Obtendo os dados provenientes do formulário (encapsulados em um DTO - Data Transfer Object).
		FornecedorDTO fornecedorDTO = (FornecedorDTO) document.getBean();
		// Obtendo um serviço para persistir os dados recebidos.
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);

		// Verificando a existência dos dados e do serviço.
		if (fornecedorDTO != null && fornecedorService != null) {
			// 1 - Desformatando os valores de alguns atributos.
			// 1.1 - Cnpj
			if (fornecedorDTO.getCnpj() != null) {
				if (fornecedorDTO.getCnpj().equals("")) {
					fornecedorDTO.setCnpj(null);
				} else {
					fornecedorDTO.setCnpj(fornecedorDTO.getCnpj().replaceAll("[\\./-]", ""));
				}
			}

			// 1.2 - Telefone
			if (fornecedorDTO.getTelefone() != null && fornecedorDTO.getTelefone().equals("")) {
				fornecedorDTO.setTelefone(null);
			}

			// 1.3 - Fax
			if (fornecedorDTO.getFax() != null && fornecedorDTO.getFax().equals("")) {
				fornecedorDTO.setFax(null);
			}

			EnderecoDTO enderecoDTO = new EnderecoDTO();
			EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, null);

			if (enderecoDTO != null && enderecoService != null) {

				if (fornecedorDTO.getIdEndereco() != null) {
					enderecoDTO.setIdEndereco(fornecedorDTO.getIdEndereco());
				}

				if (fornecedorDTO.getLogradouro() != null) {
					if (fornecedorDTO.getLogradouro().equals("")) {
						enderecoDTO.setLogradouro(null);
					} else {
						enderecoDTO.setLogradouro(fornecedorDTO.getLogradouro());
					}
				}

				if (fornecedorDTO.getNumero() != null) {
					if (fornecedorDTO.getNumero().equals("")) {
						enderecoDTO.setNumero(null);
					} else {
						enderecoDTO.setNumero(fornecedorDTO.getNumero());
					}
				}

				if (fornecedorDTO.getComplemento() != null) {
					if (fornecedorDTO.getComplemento().equals("")) {
						enderecoDTO.setComplemento(null);
					} else {
						enderecoDTO.setComplemento(fornecedorDTO.getComplemento());
					}
				}

				if (fornecedorDTO.getBairro() != null) {
					if (fornecedorDTO.getBairro().equals("")) {
						enderecoDTO.setBairro(null);
					} else {
						enderecoDTO.setBairro(fornecedorDTO.getBairro());
					}
				}

				if (fornecedorDTO.getIdPais() != null) {
					enderecoDTO.setIdPais(fornecedorDTO.getIdPais());
				}

				if (fornecedorDTO.getIdUf() != null) {
					enderecoDTO.setIdUf(fornecedorDTO.getIdUf());
				}

				if (fornecedorDTO.getIdCidade() != null) {
					enderecoDTO.setIdCidade(fornecedorDTO.getIdCidade());
				}

				if (fornecedorDTO.getCep() != null) {
					if (fornecedorDTO.getCep().equals("")) {
						enderecoDTO.setCep(null);
					} else {
						enderecoDTO.setCep(fornecedorDTO.getCep().replace("-", ""));
					}
				}

				// Inserindo o endereço.
				if (fornecedorDTO.getIdEndereco() == null) {
					enderecoService.create(enderecoDTO);
				} else { // Atualizando o endereço.
					enderecoService.update(enderecoDTO);
				}

				// Definindo o endereço do fornecedor.
				fornecedorDTO.setIdEndereco(enderecoDTO.getIdEndereco());
			}

			if (fornecedorDTO.getIdFornecedor() == null) {
				/*
				 * List<FornecedorDTO> fornecedores = (List) fornecedorService.list();
				 * 
				 * // Verificando se existe algum fornecedor com CPF/CNPJ, Razão social ou Nome fantasia já cadastrado. if (fornecedorDTO.getCnpj() != null) { for (FornecedorDTO fornecedor :
				 * fornecedores) { if (fornecedorDTO.getCnpj().equals(fornecedor.getCnpj() ) || fornecedorDTO.getRazaoSocial().equals(fornecedor.getRazaoSocial() ) ||
				 * fornecedorDTO.getNomeFantasia().equals(fornecedor.getNomeFantasia() ) ) { document.alert(UtilI18N.internacionaliza(request, "fornecedor.jaCadastrado") ); return; } } }
				 */

				if (fornecedorService.consultarCargosAtivos(fornecedorDTO)) {
					document.alert(UtilI18N.internacionaliza(request, "fornecedor.jaCadastrado"));
					return;
				} else {
					// Inserindo o fornecedor.
					fornecedorService.create(fornecedorDTO);
					document.alert(UtilI18N.internacionaliza(request, "MSG05"));
					HTMLForm form = document.getForm("form");
					form.clear();
				}

			} else {
				// Atualizando o fornecedor.
				if (fornecedorService.consultarCargosAtivos(fornecedorDTO)) {
					document.alert(UtilI18N.internacionaliza(request, "fornecedor.jaCadastrado"));
					return;
				} else {
					fornecedorService.update(fornecedorDTO);
					document.alert(UtilI18N.internacionaliza(request, "MSG06"));
					HTMLForm form = document.getForm("form");
					form.clear();
					
				}
			}
		}

		document.executeScript("limpar_LOOKUP_FORNECEDOR()");
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FornecedorDTO fornecedorDTO = (FornecedorDTO) document.getBean();
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		boolean retornoExclusao;
		if (fornecedorDTO != null && fornecedorDTO.getIdFornecedor() != null && fornecedorService != null) {
			
			//Verifica se existe algum contrato associado ao fornecedor, se existir, não deixa excluir.
			boolean fornecedorAssociadoContrato = fornecedorService.existeFornecedorAssociadoContrato(fornecedorDTO.getIdFornecedor());
			
			if(fornecedorAssociadoContrato){
				document.alert(UtilI18N.internacionaliza(request, "fornecedor.fornecedorVinculadoContrato"));
				return;
			}
			
			// Excluindo o fornecedor.
			retornoExclusao = fornecedorService.excluirFornecedor(fornecedorDTO);

			if (retornoExclusao == false) {
				document.alert(UtilI18N.internacionaliza((HttpServletRequest) request, "fornecedor.exclusao"));
				return;
			} else {
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			}
			
			if (fornecedorDTO.getIdEndereco() != null && fornecedorDTO.getIdEndereco() > 0) {
				EnderecoDTO enderecoDTO = new EnderecoDTO();
				enderecoDTO.setIdEndereco(fornecedorDTO.getIdEndereco());

				EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, null);

				// Excluindo o endereço associado.
				enderecoService.delete(enderecoDTO);
			}

			HTMLForm form = document.getForm("form");
			form.clear();

			document.setBean(new FornecedorDTO());

			load(document, request, response);
		}

		document.executeScript("limpar_LOOKUP_FORNECEDOR()");
	}

	@Override
	public Class getBeanClass() {
		return FornecedorDTO.class;
	}

	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public void preencherComboPaises(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboPaises = (HTMLSelect) document.getSelectById("comboPaises");

		if (comboPaises != null) {
			this.inicializarCombo(comboPaises, request);

			PaisServico paisServico = (PaisServico) ServiceLocator.getInstance().getService(PaisServico.class, null);

			if (paisServico != null) {
				List<PaisDTO> paises = (List) paisServico.list();

				// Ordenando paises alfabeticamente.
				Collections.sort(paises, new Comparator<PaisDTO>() {
					@Override
					public int compare(PaisDTO o1, PaisDTO o2) {
						if (o1 == null || o1.getNomePais().trim().equals("")) {
							return -999;
						}

						if (o1 == null || o1.getNomePais().trim().equals("")) {
							return -999;
						}

						return o1.getNomePais().compareTo(o2.getNomePais());
					}

				});

				if (paises != null) {
					for (PaisDTO pais : paises) {
						comboPaises.addOption(pais.getIdPais().toString(), pais.getNomePais());
					}
				}
			}
		}
	}

	public void preencherComboUfs(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("comboUfs");

		if (comboUfs != null) {
			this.inicializarCombo(comboUfs, request);

			int paisId = 0;

			if (request.getParameter("idPais") != null && !request.getParameter("idPais").equals("")) {
				paisId = Integer.parseInt(request.getParameter("idPais"));
			} else {
				HTMLElement idPais = document.getElementById("idPais");

				if (idPais != null && idPais.getValue() != null && !idPais.getValue().equals("")) {
					paisId = Integer.parseInt(idPais.getValue());
				}
			}

			if (paisId > 0) {
				UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

				UfDTO obj = new UfDTO();
				obj.setIdPais(paisId);

				if (ufService != null) {
					List<UfDTO> ufs = (List) ufService.listByIdPais(obj);

					// Ordenando ufs alfabeticamente.
					Collections.sort(ufs, new Comparator<UfDTO>() {
						@Override
						public int compare(UfDTO o1, UfDTO o2) {
							if (o1 == null || o1.getNomeUf().trim().equals("")) {
								return -999;
							}

							if (o1 == null || o1.getNomeUf().trim().equals("")) {
								return -999;
							}

							return o1.getNomeUf().compareTo(o2.getNomeUf());
						}

					});

					if (ufs != null) {
						for (UfDTO uf : ufs) {
							comboUfs.addOption(uf.getIdUf().toString(), uf.getNomeUf());
						}
					}
				}
			}
		}
	}

	public void preencherComboCidades(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboCidades = (HTMLSelect) document.getSelectById("comboCidades");

		if (comboCidades != null) {
			this.inicializarCombo(comboCidades, request);

			int ufId = 0;

			if (request.getParameter("idUf") != null && !request.getParameter("idUf").equals("")) {
				ufId = Integer.parseInt(request.getParameter("idUf"));
			} else {
				HTMLElement idUf = document.getElementById("idUf");

				if (idUf != null && idUf.getValue() != null && !idUf.getValue().equals("")) {
					ufId = Integer.parseInt(idUf.getValue());
				}
			}

			if (ufId > 0) {
				CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

				CidadesDTO obj = new CidadesDTO();
				obj.setIdUf(ufId);

				if (cidadesService != null && comboCidades != null) {
					// O nome do método deveria ser listByIdUf e não listByIdCidades.
					List<CidadesDTO> cidades = (List) cidadesService.listByIdCidades(obj);

					if (cidades != null) {
						// Ordenando cidades alfabeticamente.
						Collections.sort(cidades, new Comparator<CidadesDTO>() {
							@Override
							public int compare(CidadesDTO o1, CidadesDTO o2) {
								if (o1 == null || o1.getNomeCidade().trim().equals("")) {
									return -999;
								}

								if (o1 == null || o1.getNomeCidade().trim().equals("")) {
									return -999;
								}

								return o1.getNomeCidade().compareTo(o2.getNomeCidade());
							}

						});
						for (CidadesDTO cidade : cidades) {
							comboCidades.addOption(cidade.getIdCidade().toString(), cidade.getNomeCidade());
						}
					}
				}
			}
		}
	}
}
