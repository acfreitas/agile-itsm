package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.PaisServico;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EmailCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EnderecoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.TelefoneCurriculoDTO;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.IdiomaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
 
 
public class CadastroCurriculo extends AjaxFormAction {
 
	 public Class getBeanClass() {
         return CurriculoDTO.class;
   }

   public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return;
		}

		request.getSession(true).setAttribute("colUploadsGED", null);
		
		CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean(); 
		
		UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);
		Collection colUfs = ufService.list();
		
		HTMLSelect idUF = document.getSelectById("endereco#idUF");
		idUF.addOption(" ", "--- Selecione ---");
		idUF.addOptions(colUfs, "idUf", "siglaUf", null);
		
		IdiomaService idiomaService = (IdiomaService) ServiceLocator.getInstance().getService(IdiomaService.class, null);
		Collection colIdioma = idiomaService.list();
		
		HTMLSelect idIdioma = document.getSelectById("idioma#idIdioma");
		idIdioma.addOption(" ", "--- Selecione ---");
		idIdioma.addOptions(colIdioma, "idIdioma", "descricao", null);
		
		PaisServico paisService = (PaisServico) ServiceLocator.getInstance().getService(PaisServico.class, null);
		Collection colPais = paisService.list();
		
		HTMLSelect idNaturalidade = document.getSelectById("idNaturalidade");
		idNaturalidade.addOption(" ", "--- Selecione ---");
		idNaturalidade.addOptions(colPais, "idPais", "nomePais", null);
		
		if (curriculoDto.getIdCurriculo() != null)
			restore(document, request, response);
	}
	
   public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return;
		}
		request.getSession(true).setAttribute("colUploadsGED", null);
		
		CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean(); 
		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
  		
  		curriculoDto = (CurriculoDTO) curriculoService.restore(curriculoDto);
    	
		HTMLForm form = document.getForm("form");
        form.setValues(curriculoDto);
        
        if (curriculoDto.getDataNascimento()==null){
  			document.executeScript("$('spnIdade').innerHTML = ''");
  		}else{
  			String textoIdade = UtilDatas.calculaIdade(curriculoDto.getDataNascimento(), "LONG");
  			document.executeScript("$('spnIdade').innerHTML = '" + textoIdade + "'");
  		}
        
        
        HTMLTable tblTelefones = document.getTableById("tblTelefones");
  		tblTelefones.deleteAllRows();
  		if (curriculoDto.getColTelefones()!=null){
  			tblTelefones.addRowsByCollection(curriculoDto.getColTelefones(), 
  					new String[] {"", "descricaoTipoTelefone", "ddd", "numeroTelefone"}, 
  					null, 
  					"Já existe registrado esta demanda na tabela", 
  					null, 
  					"marcaTelefone", 
  					null);		
  		}
  		tblTelefones.applyStyleClassInAllCells("celulaGrid");
  		
  		HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
  		tblCertificacao.deleteAllRows();
  		if (curriculoDto.getColCertificacao()!=null){
  			tblCertificacao.addRowsByCollection(curriculoDto.getColCertificacao(), 
  					new String[] {"", "descricao", "versao", "validade"}, 
  					null, 
  					"Já existe registrado esta demanda na tabela", 
  					null, 
  					"marcaCertificacao", 
  					null);		
  		}
  		tblCertificacao.applyStyleClassInAllCells("celulaGrid");
  		
  		HTMLTable tblIdioma = document.getTableById("tblIdioma");
  		tblIdioma.deleteAllRows();
  		if (curriculoDto.getColIdioma()!=null){
  			tblIdioma.addRowsByCollection(curriculoDto.getColIdioma(), 
  					new String[] {"", "descricaoIdioma", "detalhamentoNivel"}, 
  					null, 
  					"Já existe registrado esta demanda na tabela", 
  					null, 
  					"marcaIdioma", 
  					null);		
  		}
  		tblIdioma.applyStyleClassInAllCells("celulaGrid");
  		
  		HTMLTable tblEnderecos = document.getTableById("tblEnderecos");
  		tblEnderecos.deleteAllRows();	
  		if (curriculoDto.getColEnderecos()!=null){
  			tblEnderecos.addRowsByCollection(curriculoDto.getColEnderecos(), 
  					new String[] {"", "principal", "descricaoTipoEndereco", "detalhamentoEndereco"}, 
  					null, 
  					"Já existe registrado esta informação na tabela", 
  					null, 
  					"marcaEndereco", 
  					null);		
  		}
  		tblEnderecos.applyStyleClassInAllCells("celulaGrid");
  		
  		HTMLTable tblFormacao = document.getTableById("tblFormacao");
  		tblFormacao.deleteAllRows();	
  		if (curriculoDto.getColFormacao()!=null){
  			tblFormacao.addRowsByCollection(curriculoDto.getColFormacao(), 
  					new String[] {"", "descricaoTipoFormacao", "instituicao", "descricaoSituacao", "descricao"}, 
  					null, 
  					"Já existe registrado esta informação na tabela", 
  					null, 
  					"marcaEndereco", 
  					null);		
  		}
  		tblFormacao.applyStyleClassInAllCells("celulaGrid");
  		
  		HTMLTable tblEmail = document.getTableById("tblEmail");
  		tblEmail.deleteAllRows();	
  		if (curriculoDto.getColEmail()!=null){
  			tblEmail.addRowsByCollection(curriculoDto.getColEmail(), 
  					new String[] {"","imagemEmailprincipal", "descricaoEmail"}, 
  					null, 
  					"Já existe registrado esta informação na tabela", 
  					null, 
  					"marcaEndereco", 
  					null);		
  		}
  		tblEmail.applyStyleClassInAllCells("celulaGrid");

		document.executeScript("uploadAnexos.refresh()");
  		restaurarAnexos(request, curriculoDto.getIdCurriculo());
		setaFoto(document, request, response);
	}
   
	protected void restaurarAnexos(HttpServletRequest request, Integer idCurriculo) throws ServiceException, Exception {
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection<ControleGEDDTO> colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_CURRICULO, idCurriculo);
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);

		colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.FOTO_CURRICULO, idCurriculo);
		Collection colArquivos  = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
		request.getSession(true).setAttribute("ARQUIVOS_UPLOAD", colArquivos);
	}
	
    public void calculaIdade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
 		CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean();
 		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return;
		}
 		if (curriculoDto.getDataNascimento()==null){			
 			document.executeScript("document.getElementById('spnIdade').innerHTML = ''");
 			document.alert("Informe uma data válida para a Data de Nascimento!");
 			return;
 		}
 		
 		String textoIdade = UtilDatas.calculaIdade(curriculoDto.getDataNascimento(), "LONG");
 		
 		document.executeScript("document.getElementById('spnIdade').innerHTML = '" + textoIdade + "'");
 	}
   
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return;
		}
		
		CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean();
  		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
  		
  		Collection colTelefones = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(TelefoneCurriculoDTO.class, "colTelefones_Serialize", request);
  		Collection colEnderecos = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EnderecoCurriculoDTO.class, "colEnderecos_Serialize", request);
  		Collection colFormacao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FormacaoCurriculoDTO.class, "colFormacao_Serialize", request);
  		Collection colEmail = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EmailCurriculoDTO.class, "colEmail_Serialize", request);
  		Collection colExperienciaProfissional = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ExperienciaProfissionalCurriculoDTO.class, "colExperienciaProfissional_Serialize", request);
  		Collection colCertificacao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CertificacaoCurriculoDTO.class, "colCertificacao_Serialize", request);
  		Collection colIdioma = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(IdiomaCurriculoDTO.class, "colIdioma_Serialize", request);
  		
  		curriculoDto.setColTelefones(colTelefones);
  		curriculoDto.setColEnderecos(colEnderecos);
  		curriculoDto.setColFormacao(colFormacao);
  		curriculoDto.setColEmail(colEmail);
  		curriculoDto.setColExperienciaProfissional(colExperienciaProfissional);
  		curriculoDto.setColCertificacao(colCertificacao);
  		curriculoDto.setColIdioma(colIdioma);
  		
  		
  		Collection<UploadDTO> anexos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
		List<UploadDTO> colFoto = (List)request.getSession().getAttribute("ARQUIVOS_UPLOAD");
		if (colFoto != null && colFoto.size() > 0)
			curriculoDto.setFoto(colFoto.get(0));

		curriculoDto.setAnexos(anexos);
  		
		String cpf = curriculoDto.getCpf().replaceAll("\\.", "");
		cpf = curriculoDto.getCpf().replaceAll("-", "");
		curriculoDto.setCpf(cpf);
  		if (curriculoDto.getIdCurriculo()==null || curriculoDto.getIdCurriculo().intValue()==0){
  			curriculoDto = (CurriculoDTO) curriculoService.create(curriculoDto);
  		}else{
  			curriculoService.update(curriculoDto);
  		}
  
        document.alert("Registro gravado com sucesso!");        
		document.executeScript("limpar()");
	}
	
	public void setaFoto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Collection col = (Collection)request.getSession().getAttribute("ARQUIVOS_UPLOAD");
		if (col != null && col.size() > 0){
			Iterator it = col.iterator();
			UploadDTO uploadItem = (UploadDTO)it.next();
			
			document.executeScript("document.getElementById('divImgFoto').innerHTML = '<img src=\"" + uploadItem.getCaminhoRelativo() + "\" border=0 width=\"167px\" />'");
		}
	}
      
	public void clear(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws ServiceException, Exception{
		request.getSession(true).setAttribute("colUploadsGED", null);
		request.getSession(true).setAttribute("ARQUIVOS_UPLOAD", null);
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("uploadAnexos.refresh()");
  		HTMLTable tblEnderecos = document.getTableById("tblEnderecos");
  		HTMLTable tblTelefones = document.getTableById("tblTelefones");
  		HTMLTable tblFormacao = document.getTableById("tblFormacao");
  		HTMLTable tblEmail = document.getTableById("tblEmail");
  		HTMLTable tblIdioma = document.getTableById("tblIdioma");
  		HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
  		
  		tblEnderecos.deleteAllRows();
  		tblTelefones.deleteAllRows();  
  		tblFormacao.deleteAllRows();
  		tblEmail.deleteAllRows();
  		tblIdioma.deleteAllRows();
  		tblCertificacao.deleteAllRows();
  		

	}
}


