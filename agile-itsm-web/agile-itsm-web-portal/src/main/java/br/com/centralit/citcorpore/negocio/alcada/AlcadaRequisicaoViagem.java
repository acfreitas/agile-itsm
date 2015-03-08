package br.com.centralit.citcorpore.negocio.alcada;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.LimiteAlcadaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.AlcadaCentroResultadoDAO;
import br.com.centralit.citcorpore.integracao.AlcadaDao;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.LimiteAlcadaDao;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoAlcada;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
@SuppressWarnings({"unchecked","rawtypes"})
public class AlcadaRequisicaoViagem  extends AlcadaImpl {
	
    private Integer idGrupoViagem = null;
    
    private GrupoEmpregadoDao grupoEmpregadoDao = null;
    
    private Collection<IntegranteViagemDTO> listaIntegratesViagem = null;

    @Override
    public AlcadaDTO determinaAlcada(IDto objetoNegocioDto, CentroResultadoDTO centroCustoDto, TransactionControler tc) throws Exception {
        AlcadaDao alcadaDao = new AlcadaDao();
        IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
        setTransacaoDao(alcadaDao);
        
        this.objetoNegocioDto = objetoNegocioDto;
        alcadaDto = alcadaDao.findByTipo(TipoAlcada.Viagem);
        if (alcadaDto == null)
            throw new LogicException("Tipo de alçada 'Viagem' não encontrada"); 

        if (isNovaAlcada()) {
        	alcadaDto.setColResponsaveis(AlcadaProcessoNegocio.getInstance().getResponsaveis((SolicitacaoServicoDTO) objetoNegocioDto, centroCustoDto, tc));
        }else{
	        LimiteAlcadaDao limiteAlcadaDao = new LimiteAlcadaDao();
	        setTransacaoDao(limiteAlcadaDao);
	        Collection<LimiteAlcadaDTO> colLimites = limiteAlcadaDao.findByIdAlcada(alcadaDto.getIdAlcada());
	        if (colLimites == null || colLimites.isEmpty())
	            throw new LogicException("Não foram encontrados limites de valores para a alçada '"+alcadaDto.getNomeAlcada()+"'"); 
	        
	        String idGrupo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_REQ_VIAGEM_EXECUCAO, null);
	        if (idGrupo == null || idGrupo.trim().equals(""))
	            throw new Exception("Grupo padrão de requisição de produtos não parametrizado");
	        idGrupoViagem = new Integer(idGrupo.trim());
	        
	        AlcadaCentroResultadoDAO alcadaCentroResultadoDao = new AlcadaCentroResultadoDAO();
	        setTransacaoDao(alcadaCentroResultadoDao);
	        centroCustoDto.setColAlcadas(alcadaCentroResultadoDao.findByIdCentroResultadoAndIdAlcada(centroCustoDto.getIdCentroResultado(), alcadaDto.getIdAlcada()));
	        
	        RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) objetoNegocioDto;
	        grupoEmpregadoDao = new GrupoEmpregadoDao();
	        setTransacaoDao(grupoEmpregadoDao);
	        Collection<GrupoEmpregadoDTO> colGrupos = grupoEmpregadoDao.findAtivosByIdEmpregado(requisicaoViagemDto.getIdSolicitante());
	        HashMap<String, GrupoEmpregadoDTO> mapGrupos = new HashMap();
	        if (colGrupos != null) {
	            for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupos) 
	                mapGrupos.put(""+grupoEmpregadoDto.getIdGrupo(), grupoEmpregadoDto);
	        }
	        
	        GrupoDao grupoDao = new GrupoDao();
	        setTransacaoDao(grupoDao);
	        alcadaDto.setColResponsaveis(new ArrayList());
	        
	        solicitante = recuperaEmpregado(requisicaoViagemDto.getIdSolicitante());
	        
	        if(requisicaoViagemDto.getIdSolicitacaoServico()!=null){
	        	 listaIntegratesViagem = integranteViagemDao.findAllByIdSolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
	        }
	        
	        
	        for (LimiteAlcadaDTO limiteAlcadaDto : colLimites) {
	        	GrupoDTO grupoDto = new GrupoDTO();
	            grupoDto.setIdGrupo(limiteAlcadaDto.getIdGrupo());
	            grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
	            if (grupoDto == null){
	            	 throw new LogicException("Grupo "+limiteAlcadaDto.getIdGrupo()+" não encontrado");
	            }
	            
	            if(limiteAlcadaDto.getTipoLimite().equalsIgnoreCase("Q") && limiteAlcadaDto.getAbrangenciaCentroCusto().equalsIgnoreCase("T")){
	            	if (mapGrupos.get(""+limiteAlcadaDto.getIdGrupo()) != null) {
	                    Collection<EmpregadoDTO> colResponsaveis = new ArrayList();
	                    colResponsaveis.add(solicitante);
	                    alcadaDto.setColResponsaveis(colResponsaveis);
	                    return alcadaDto;
	                }
	            }
	            
	            
	           determinaResponsaveis(grupoDto, limiteAlcadaDto.getAbrangenciaCentroCusto(), centroCustoDto , limiteAlcadaDto.getTipoLimite(),requisicaoViagemDto);
	        	
	        }
	        if (alcadaDto.getColResponsaveis().size() > 0)
	            return alcadaDto;
        }
        
        return alcadaDto;
    }
	 
    public void determinaResponsaveis(GrupoDTO grupoDto, String abrangenciaCentroCusto, CentroResultadoDTO centroCustoDto,String tipoLimite,RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
       Date dataAtual = UtilDatas.getDataAtual();
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

            if (tipoLimite.equalsIgnoreCase("F") || tipoLimite.equalsIgnoreCase("Q") && abrangenciaCentroCusto.equalsIgnoreCase("R")) {
            	if(requisicaoViagemDto.getDataInicioViagem().compareTo(dataAtual)>=0){
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
            	}
               
            }else{
            	if (tipoLimite.equalsIgnoreCase("F")  && abrangenciaCentroCusto.equalsIgnoreCase("T")) {
            		if(requisicaoViagemDto.getDataInicioViagem().compareTo(dataAtual)>=0){
            			 if (!permiteResponsavel(grupoEmpregadoDto.getIdEmpregado()))
                             continue;
                         EmpregadoDTO empregadoDto = recuperaEmpregado(grupoEmpregadoDto.getIdEmpregado());
                         if (empregadoDto != null) {
                             mapResponsaveis.put(""+empregadoDto.getIdEmpregado(), empregadoDto);
                             colResponsaveis.add(empregadoDto);
                         }    
            		}
                               
                }else{
                	if(tipoLimite.equalsIgnoreCase("Q") && abrangenciaCentroCusto.equalsIgnoreCase("T")){
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


	@Override
	public boolean permiteResponsavel(Integer idEmpregado) throws Exception {
		
		  if (idEmpregado.intValue() == solicitante.getIdEmpregado().intValue())
	            return false;
	        
	        Collection<GrupoEmpregadoDTO> colGrupos = grupoEmpregadoDao.findAtivosByIdEmpregado(idEmpregado);
	        if (colGrupos != null) {
	            for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupos) {
	                if (grupoEmpregadoDto.getIdGrupo().intValue() == idGrupoViagem)
	                    return false;
	            }
	        }
	        if(listaIntegratesViagem!=null){
	        	for(IntegranteViagemDTO integranteViagemDto : listaIntegratesViagem){
	        		
	        		if(integranteViagemDto.getIntegranteFuncionario().equals("N"))
	        			continue;
	        		
	        		if (integranteViagemDto.getIdEmpregado().intValue() ==  idEmpregado.intValue())
	                    return false;
	        	}
	        }
	        return true;
	}
	 
	 

}
