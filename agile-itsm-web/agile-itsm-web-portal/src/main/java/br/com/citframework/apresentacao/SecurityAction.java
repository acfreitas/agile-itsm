package br.com.citframework.apresentacao;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Mensagens;
import br.com.citframework.util.WebUtil;
import br.com.citframework.util.converter.ConverterUtils;



public abstract class SecurityAction extends Action {
	
	//private static final Logger LOGGER = Logger.getLogger(SecurityAction.class);
	
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			return executar(map, form, request, response);
		} catch (LogicException e) {
			//LOGGER.debug("[SECURITY ACTION] - CLASSE: "+this.getClass().getName());

			//LOGGER.info(e.getMessage(), e);
			ActionForward fwd = map.findForward(Constantes.getValue("ERRO"));
			if (fwd == null) {
				//gravaMensagem(Constantes.MSG02 + this.getClass().getName(), request);
				gravaMensagem(e.getMessage(), request);
				return map.findForward(Constantes.getValue("MENSAGEM"));
			}
			gravaMensagem(e.getMessage(), request);
			//LOGGER.error(e.getMessage());
			return fwd;
		} catch (Exception e) {
			//LOGGER.debug("[SECURITY ACTION] - CLASSE: "+this.getClass().getName());
			Throwable t = e;
			boolean val = true;
			while (val) {
				if (t.getCause() != null && t.getCause() instanceof LogicException) {

					ActionForward fwd = map.findForward(Constantes.getValue("ERRO"));
					if (fwd == null) {
						gravaMensagem(Mensagens.getValue("MSG02") + this.getClass().getName(), request);
						return map.findForward(Constantes.getValue("MENSAGEM"));
					}
					gravaMensagem(t.getCause().getMessage(), request);
					return fwd;

				} else if (t.getCause() != null) {
					t = t.getCause();
				} else
					val = false;
				//LOGGER.error(e.getMessage(), e);
			}
			
			

			ActionForward fwd = map.findForward(Constantes.getValue("ERRO"));
			String mensagem;
			mensagem = Mensagens.getValue("MSG01");
			if (fwd == null) {
				gravaMensagem(mensagem, request);
				return map.findForward(Constantes.getValue("MENSAGEM"));
			}
			gravaMensagem(mensagem, request);
			return fwd;
		}

	}

	protected abstract ActionForward executar(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;

	protected void gravaMensagem(String msg, HttpServletRequest request) {

		ActionMessages erro = new ActionMessages();
		erro.add("error", new ActionMessage("errors.errorGeneral", msg));
		saveErrors(request, erro);
	}

	protected void gravaMensagens(String[] msg, HttpServletRequest request) {

		ActionMessages erro = new ActionMessages();
		for (int i = 0; i < msg.length; i++) {
			String message = msg[i];
			message = message.replaceAll("\\'", "\\\\'");
			erro.add("error", new ActionMessage("errors.errorGeneral", message));
			saveErrors(request, erro);
		}

	}

	protected void copyFormToModel(ActionForm form, Object bean, HttpServletRequest req) throws Exception {
		ConverterUtils.copyFromStringObject(form, bean);
	}

	protected void copyModelToForm(Object bean, ActionForm form, HttpServletRequest req) throws Exception {
		ConverterUtils.copyToStringObject(form, bean);
	}

	protected void setUsuario(Usuario usuario, HttpServletRequest req) {
		WebUtil.setUsuario(usuario, req);

	}

	protected Usuario getUsuario(HttpServletRequest req) {
			return WebUtil.getUsuario(req);
	}

	public static boolean uploadFile(FormFile formFile, String arqDest, boolean isRealPath) {
		if (formFile == null) {
			//LOGGER.debug("FormFile está nulo!!");
			return false;
		}
		if (!isRealPath) {
			//arqDest = this.getServlet().getServletContext().getRealPath(arqDest);
			//TODO implementar esse bloco.....
		}
		
		File file = new File(arqDest);
		if (!file.exists()) {
			new File(file.getParent()).mkdirs();
		}
		boolean retorno = false;
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(formFile.getFileData());
			fos.flush();
			fos.close();
			retorno = true;
			//LOGGER.debug("\n*** Upload efetuado com sucesso!! ***\n >>> Arquivo Destino: " + arqDest + "\n");
		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}
}
