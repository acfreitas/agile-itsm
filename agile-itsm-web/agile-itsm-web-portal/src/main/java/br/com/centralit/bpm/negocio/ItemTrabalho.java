package br.com.centralit.bpm.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.NativeJavaObject;

import br.com.centralit.bpm.config.Config;
import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.ItemTrabalhoFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.centralit.bpm.util.UtilScript;
import br.com.citframework.util.UtilDatas;

public abstract class ItemTrabalho extends NegocioBpm implements IItemTrabalho {

	protected ElementoFluxoDTO elementoFluxoDto;
	protected InstanciaFluxo instanciaFluxo;
	protected ItemTrabalhoFluxoDTO itemTrabalhoDto;

	public static ItemTrabalho getItemTrabalhoDeElemento(InstanciaFluxo instanciaFluxo, Integer idElemento) throws Exception {
		ElementoFluxoDao elementoFluxoDao = new ElementoFluxoDao();
		if (instanciaFluxo.getTransacao() != null)
			elementoFluxoDao.setTransactionControler(instanciaFluxo.getTransacao());
		ElementoFluxoDTO elementoDto = elementoFluxoDao.restore(idElemento);
		String nomeClasse = Config.getClasseNegocioElemento(elementoDto.getTipoElemento());
		ItemTrabalho classe = (ItemTrabalho) Class.forName(nomeClasse).newInstance();
		classe.instanciaFluxo = instanciaFluxo;
		classe.elementoFluxoDto = elementoDto;
		classe.itemTrabalhoDto = null;
		classe.setTransacao(instanciaFluxo.getTransacao());
		return classe;
	}

	public static ItemTrabalho getItemTrabalho(InstanciaFluxo instanciaFluxo, Integer idItemTrabalho) throws Exception {
		ItemTrabalhoFluxoDao itemTrabalhoDao = new ItemTrabalhoFluxoDao();
		if (instanciaFluxo.getTransacao() != null)
			itemTrabalhoDao.setTransactionControler(instanciaFluxo.getTransacao());
		ItemTrabalhoFluxoDTO itemDto = new ItemTrabalhoFluxoDTO();
		itemDto.setIdItemTrabalho(idItemTrabalho);
		itemDto = (ItemTrabalhoFluxoDTO) itemTrabalhoDao.restore(itemDto);

		ElementoFluxoDao elementoFluxoDao = new ElementoFluxoDao();
		if (instanciaFluxo.getTransacao() != null)
			elementoFluxoDao.setTransactionControler(instanciaFluxo.getTransacao());
		ElementoFluxoDTO elementoDto = elementoFluxoDao.restore(itemDto.getIdElemento());

		String nomeClasse = Config.getClasseNegocioElemento(elementoDto.getTipoElemento());
		ItemTrabalho classe = (ItemTrabalho) Class.forName(nomeClasse).newInstance();
		classe.instanciaFluxo = instanciaFluxo;
		classe.elementoFluxoDto = elementoDto;
		classe.itemTrabalhoDto = itemDto;
		classe.setTransacao(instanciaFluxo.getTransacao());
		return classe;

	}

	public static ItemTrabalho getItemTrabalho(Integer idItemTrabalho) throws Exception {
		ItemTrabalhoFluxoDao itemTrabalhoDao = new ItemTrabalhoFluxoDao();
		ItemTrabalhoFluxoDTO itemDto = new ItemTrabalhoFluxoDTO();
		itemDto.setIdItemTrabalho(idItemTrabalho);
		itemDto = (ItemTrabalhoFluxoDTO) itemTrabalhoDao.restore(itemDto);

		ElementoFluxoDao elementoFluxoDao = new ElementoFluxoDao();
		ElementoFluxoDTO elementoDto = elementoFluxoDao.restore(itemDto.getIdElemento());

		String nomeClasse = Config.getClasseNegocioElemento(elementoDto.getTipoElemento());
		ItemTrabalho classe = (ItemTrabalho) Class.forName(nomeClasse).newInstance();
		classe.elementoFluxoDto = elementoDto;
		classe.itemTrabalhoDto = itemDto;
		return classe;

	}

	protected String[] substituiParametros(String[] arrayExpr, Map<String, Object> objetos, String separador) throws Exception {
		if (arrayExpr == null || arrayExpr.length == 0)
			return null;

		if (separador == null || separador.trim().equals(""))
			separador = ";";
		List<String> list = new ArrayList<String>();
    	for (String expr : arrayExpr) {
    		String arraySubst = substituiParametros(expr, objetos);
    		if (arraySubst != null) {
    			String[] arrayTrat = arraySubst.split(separador);
                for (String str : arrayTrat) {
                	str = substituiParametros(str, objetos);
                	if (str != null && !str.trim().equals(""))
                    	list.add(str.trim());
                }
    		}
    	}

    	int i = 0;
    	String[] result = new String[list.size()];
    	for (String str : list) {
			result[i] = str;
			i++;
		}
    	return result;
	}

	protected String substituiParametros(String str, Map<String, Object> objetos) throws Exception {
	    if (str.indexOf("script:", 0) >= 0) {
	        try{
	            String[] s = str.split(":");
	            NativeJavaObject result = (NativeJavaObject) UtilScript.executaScript("script", s[1], objetos);
                if (result != null)
                    return (String) NativeJavaObject.coerceType(String.class, result);
	        }catch (Exception e) {
            }
	    }else if (str.indexOf("#{", 0) >= 0) {
		    Map<String, Object> params = UtilScript.getParams(objetos);
			for(String var: params.keySet()) {
				try{
					if (!String.class.isInstance(params.get(var)) || params.get(var) == null)
						continue;
					str = str.replaceAll("\\#\\{"+var+"\\}", (String) params.get(var));
				}catch (Exception e) {
				}
			}
		}
		return str;
	}

	public void registra() throws Exception{
	}

	@Override
	public void redireciona(String loginUsuario, String usuarioDestino, String grupoDestino) throws Exception {
	}

	@Override
	public void executa(String loginUsuario, Map<String, Object> campos) throws Exception{
	}

	@Override
	public void inicia(String loginUsuario, Map<String, Object> campos) throws Exception{
	}

	@Override
	public void delega(String loginUsuario, String usuarioDestino, String grupoDestino) throws Exception {

	}

	@Override
	public void cancela(String loginUsuario) throws Exception{

	}

	@Override
	public void atribui(String usuario, String grupo, TipoAtribuicao tipoAtribuicao) throws Exception {

	}

    @Override
    public void encerra() throws Exception{
        TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
        setTransacaoDao(tarefaFluxoDao);

        itemTrabalhoDto.setSituacao(SituacaoItemTrabalho.Encerrado.name());
        itemTrabalhoDto.setDataHoraFinalizacao(UtilDatas.getDataHoraAtual());
        tarefaFluxoDao.update(itemTrabalhoDto);
    }

	public abstract List<ItemTrabalho> resolve() throws Exception;

	public boolean executavel() {
		return false;
	}

	public boolean existe() {
		return itemTrabalhoDto != null && itemTrabalhoDto.getIdItemTrabalho() != null;
	}

	@Override
	public boolean iniciado() {
		if (!executavel())
			return false;
		if (itemTrabalhoDto == null)
			return false;
		if (getSituacao().equals(Enumerados.SituacaoItemTrabalho.EmAndamento))
			return true;
		else
			return false;
	}

	@Override
	public boolean pendente() throws Exception {
        if (itemTrabalhoDto.getSituacao() == null)
            return true;
        if (elementoFluxoDto != null) {
            if (elementoFluxoDto.getMultiplasInstancias() != null && !elementoFluxoDto.getMultiplasInstancias().equals("N"))
                return false;
        }
        return !getSituacao().equals(SituacaoItemTrabalho.Executado) && !getSituacao().equals(SituacaoItemTrabalho.Cancelado);
	}

	@Override
	public boolean resolvido() throws Exception {
        if (itemTrabalhoDto == null || itemTrabalhoDto.getSituacao() == null)
            return false;
        return getSituacao().equals(SituacaoItemTrabalho.Executado) || getSituacao().equals(SituacaoItemTrabalho.Cancelado);
	}

	@Override
	public boolean finalizado() throws Exception {
		if (!executavel())
			return true;
		if (getSituacao() == null)
			return false;

		return resolvido();
	}

	public Integer getIdFluxo() {
		return elementoFluxoDto.getIdFluxo();
	}

	public String getTipoElemento() {
		return elementoFluxoDto.getTipoElemento();
	}

	public Integer getIdElemento() {
		return elementoFluxoDto.getIdElemento();
	}

	public String getNome() {
		return elementoFluxoDto.getNome();
	}

	public String getDocumentacao() {
		return elementoFluxoDto.getDocumentacao();
	}

	public String getAcaoEntrada() {
		return elementoFluxoDto.getAcaoEntrada();
	}

	public String getAcaoSaida() {
		return elementoFluxoDto.getAcaoSaida();
	}

	public ElementoFluxoDTO getElementoFluxoDto() {
		return elementoFluxoDto;
	}

	public ItemTrabalhoFluxoDTO getItemTrabalhoDto() {
		return itemTrabalhoDto;
	}

	public Integer getIdItemTrabalho(){
		return getItemTrabalhoDto().getIdItemTrabalho();
	}

	public InstanciaFluxo getInstanciaFluxo() {
		return instanciaFluxo;
	}

	public String getContabilizaSLA() {
        return elementoFluxoDto.getContabilizaSLA();
    }

	public SituacaoItemTrabalho getSituacao() {
		if (itemTrabalhoDto == null || itemTrabalhoDto.getSituacao() == null)
			return null;
		return SituacaoItemTrabalho.valueOf(itemTrabalhoDto.getSituacao().trim());
	}

}
