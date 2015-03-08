package br.com.centralit.citcorpore.negocio.alcada;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.LimiteAlcadaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.AlcadaCentroResultadoDAO;
import br.com.centralit.citcorpore.integracao.AlcadaDao;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.LimiteAlcadaDao;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoAlcada;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.TransactionControler;

@SuppressWarnings({"unchecked","rawtypes"})
public class AlcadaRequisicaoPessoal extends AlcadaImpl {

    private Integer idGrupoRH = null;
    private GrupoEmpregadoDao grupoEmpregadoDao = null;

    /* 
     * Método para determinação de alçada expecifico para requisição pessoal, onde é adiquirida varias informações e trabalhada com as mesma como
     * Tipo da alçada, limite da alçada, responsaveis pela alçada, centro de resultado, grupo
     * 
     */
    @Override
    public AlcadaDTO determinaAlcada(IDto objetoNegocioDto, CentroResultadoDTO centroCustoDto, TransactionControler tc) throws Exception {
        AlcadaDao alcadaDao = new AlcadaDao();
        setTransacaoDao(alcadaDao);
        
        this.objetoNegocioDto = objetoNegocioDto;
        alcadaDto = alcadaDao.findByTipo(TipoAlcada.Pessoal);
        if (alcadaDto == null)
            throw new LogicException("Tipo de alçada 'Pessoal' não encontrada"); 

        if (isNovaAlcada()) {
        	alcadaDto.setColResponsaveis(AlcadaProcessoNegocio.getInstance().getResponsaveis((SolicitacaoServicoDTO) objetoNegocioDto, centroCustoDto, tc));
        }else{
	        LimiteAlcadaDao limiteAlcadaDao = new LimiteAlcadaDao();
	        setTransacaoDao(limiteAlcadaDao);
	        Collection<LimiteAlcadaDTO> colLimites = limiteAlcadaDao.findByIdAlcada(alcadaDto.getIdAlcada());
	        if (colLimites == null || colLimites.isEmpty())
	            throw new LogicException("Não foram encontrados limites de valores para a alçada '"+alcadaDto.getNomeAlcada()+"'"); 
	        
	        String idGrupo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_REQ_RH, null);
	        if (idGrupo == null || idGrupo.trim().equals(""))
	            throw new Exception("Grupo padrão de requisição de pessoal não parametrizado");
	        idGrupoRH = new Integer(idGrupo);
	        
	        AlcadaCentroResultadoDAO alcadaCentroResultadoDao = new AlcadaCentroResultadoDAO();
	        setTransacaoDao(alcadaCentroResultadoDao);
	        centroCustoDto.setColAlcadas(alcadaCentroResultadoDao.findByIdCentroResultadoAndIdAlcada(centroCustoDto.getIdCentroResultado(), alcadaDto.getIdAlcada()));
	        
	        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) objetoNegocioDto;
	        grupoEmpregadoDao = new GrupoEmpregadoDao();
	        setTransacaoDao(grupoEmpregadoDao);
	        Collection<GrupoEmpregadoDTO> colGrupos = grupoEmpregadoDao.findAtivosByIdEmpregado(requisicaoPessoalDto.getIdSolicitante());
	        HashMap<String, GrupoEmpregadoDTO> mapGrupos = new HashMap();
	        if (colGrupos != null) {
	            for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupos) 
	                mapGrupos.put(""+grupoEmpregadoDto.getIdGrupo(), grupoEmpregadoDto);
	        }
	        
	        GrupoDao grupoDao = new GrupoDao();
	        setTransacaoDao(grupoDao);
	        alcadaDto.setColResponsaveis(new ArrayList());
	        
	        solicitante = recuperaEmpregado(requisicaoPessoalDto.getIdSolicitante());
	        
	        for (LimiteAlcadaDTO limiteAlcadaDto : colLimites) {
	            if (!limiteAlcadaDto.getTipoLimite().equalsIgnoreCase("Q"))
	                continue;
	            
	            if (!limiteAlcadaDto.getAbrangenciaCentroCusto().equalsIgnoreCase("T"))
	                continue;
	
	            if (mapGrupos.get(""+limiteAlcadaDto.getIdGrupo()) != null) {
	                Collection<EmpregadoDTO> colResponsaveis = new ArrayList();
	                colResponsaveis.add(solicitante);
	                alcadaDto.setColResponsaveis(colResponsaveis);
	                return alcadaDto;
	            }
	        }
	
	        RequisicaoPessoalServiceEjb requisicaoPessoalService = new RequisicaoPessoalServiceEjb();
	        
	        for (LimiteAlcadaDTO limiteAlcadaDto : colLimites) {
	            if (!limiteAlcadaDto.getTipoLimite().equalsIgnoreCase("F"))
	                continue;
	            
	            if (mapGrupos.get(""+limiteAlcadaDto.getIdGrupo()) != null)
	                continue;
	            
	            GrupoDTO grupoDto = new GrupoDTO();
	            grupoDto.setIdGrupo(limiteAlcadaDto.getIdGrupo());
	            grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
	            if (grupoDto == null)
	                throw new LogicException("Grupo "+limiteAlcadaDto.getIdGrupo()+" não encontrado");
	            
	            determinaResponsaveis(grupoDto, limiteAlcadaDto.getAbrangenciaCentroCusto(), centroCustoDto);
	            //if (alcadaDto.getColResponsaveis().size() > 0)
	            //    break;
	        }
	        
	        if (alcadaDto.getColResponsaveis().size() > 0)
	            return alcadaDto;
	        
	        for (LimiteAlcadaDTO limiteAlcadaDto : colLimites) {
	            if (!limiteAlcadaDto.getTipoLimite().equalsIgnoreCase("Q"))
	                continue;
	            
	            GrupoDTO grupoDto = new GrupoDTO();
	            grupoDto.setIdGrupo(limiteAlcadaDto.getIdGrupo());
	            grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
	            if (grupoDto == null)
	                throw new LogicException("Grupo "+limiteAlcadaDto.getIdGrupo()+" não encontrado");
	            
	            determinaResponsaveis(grupoDto, limiteAlcadaDto.getAbrangenciaCentroCusto(), centroCustoDto);
	            if (alcadaDto.getColResponsaveis().size() > 0)
	                break;
	        }
        }
        
        return alcadaDto;
    }

    /* 
     * Método de determinação de responsaveis
     * 
     */
    public void determinaResponsaveis(GrupoDTO grupoDto, String abrangenciaCentroCusto, CentroResultadoDTO centroCustoDto) throws Exception {
        Collection<GrupoEmpregadoDTO> colGrupoEmpregado = grupoEmpregadoDao.findByIdGrupo(grupoDto.getIdGrupo());
        if (colGrupoEmpregado == null || colGrupoEmpregado.isEmpty()) 
            return; 

        Collection<EmpregadoDTO> colResponsaveis = alcadaDto.getColResponsaveis();
        if (colResponsaveis == null) {
            colResponsaveis = new ArrayList();
            alcadaDto.setColResponsaveis(colResponsaveis);
        }
        HashMap<String, EmpregadoDTO> mapResponsaveis = new HashMap();
        for (EmpregadoDTO empregadoDto : colResponsaveis) 
            mapResponsaveis.put(""+empregadoDto.getIdEmpregado(), empregadoDto);
        for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupoEmpregado) {
            if (mapResponsaveis.get(""+grupoEmpregadoDto.getIdEmpregado()) != null)
               continue;

            if (abrangenciaCentroCusto.equalsIgnoreCase("R")) {
                if (centroCustoDto.getColAlcadas() != null) {
                    for (AlcadaCentroResultadoDTO alcadaCentroResultadoDto : centroCustoDto.getColAlcadas()) {
                        if (alcadaCentroResultadoDto.getIdEmpregado() != null && alcadaCentroResultadoDto.getIdEmpregado().intValue() == grupoEmpregadoDto.getIdEmpregado().intValue()) {
                            if (!permiteResponsavel(grupoEmpregadoDto.getIdEmpregado()))
                                continue;
                            EmpregadoDTO empregadoDto = recuperaEmpregado(grupoEmpregadoDto.getIdEmpregado());
                            if (empregadoDto != null) {
                                mapResponsaveis.put(""+empregadoDto.getIdEmpregado(), empregadoDto);
                                colResponsaveis.add(empregadoDto);
                            }
                        }
                    }
                }
            }else if (abrangenciaCentroCusto.equalsIgnoreCase("T")) {
                if (!permiteResponsavel(grupoEmpregadoDto.getIdEmpregado()))
                    continue;
                EmpregadoDTO empregadoDto = recuperaEmpregado(grupoEmpregadoDto.getIdEmpregado());
                if (empregadoDto != null) {
                    mapResponsaveis.put(""+empregadoDto.getIdEmpregado(), empregadoDto);
                    colResponsaveis.add(empregadoDto);
                }                
            }
        }
        if (colResponsaveis.size() == 0 && centroCustoDto.getIdCentroResultadoPai() != null) {
            CentroResultadoDTO ccustoPaiDto = new CentroResultadoDTO();
            ccustoPaiDto.setIdCentroResultado(centroCustoDto.getIdCentroResultadoPai());
            CentroResultadoDao centroResultadoDao = new CentroResultadoDao();
            setTransacaoDao(centroResultadoDao);
            ccustoPaiDto = (CentroResultadoDTO) centroResultadoDao.restore(ccustoPaiDto);
            if (ccustoPaiDto != null)
                determinaResponsaveis(grupoDto, abrangenciaCentroCusto, ccustoPaiDto);
        }
    }
    
    /*
     * TODO
     * Verificação do refatoração do método
     */
    @Override
    public boolean permiteResponsavel(Integer idEmpregado) throws Exception {
        /*if (idEmpregado.intValue() == solicitante.getIdEmpregado().intValue())
            return false;
        
        Collection<GrupoEmpregadoDTO> colGrupos = grupoEmpregadoDao.findAtivosByIdEmpregado(idEmpregado);
        if (colGrupos != null) {
            for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupos) {
                if (grupoEmpregadoDto.getIdGrupo().intValue() == idGrupoRH)
                    return false;
            }
        }*/
        return true;
    }

}
