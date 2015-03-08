package br.com.centralit.citcorpore.negocio.alcada;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ParametroDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.ParametroDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.TransactionControler;
public class AlcadaImpl implements IAlcada {
    
    protected IDto objetoNegocioDto = null;
    protected EmpregadoDTO solicitante = null;
    protected AlcadaDTO alcadaDto = null;
    protected TransactionControler tc = null;

    public static boolean isNovaAlcada() throws Exception {
    	ParametroDTO parametroDto = new ParametroDao().getValue("ALCADA", "NOVA_ALCADA", new Integer(1));
    	return parametroDto != null && parametroDto.getValor() != null && parametroDto.getValor().equalsIgnoreCase("S");
    }
    
    @Override
    public AlcadaDTO determinaAlcada(IDto objetoNegocioDto, CentroResultadoDTO centroCustoDto, TransactionControler tc) throws Exception {
        return null;
    }
    

    @Override
    public void determinaResponsaveis(GrupoDTO grupoDto, String abrangenciaCentroCusto, CentroResultadoDTO centroCustoDto) throws Exception {
    }

    protected EmpregadoDTO recuperaEmpregado(Integer idEmpregado) throws Exception {
        EmpregadoDTO empregadoDto = new EmpregadoDTO();
        empregadoDto.setIdEmpregado(idEmpregado);
        EmpregadoDao empregadoDao = new EmpregadoDao();
        setTransacaoDao(empregadoDao);
        return (EmpregadoDTO) empregadoDao.restore(empregadoDto);
    }
    
    protected void setTransacaoDao(CrudDaoDefaultImpl dao) throws Exception {
        if (tc != null)
            dao.setTransactionControler(tc);
    }


    @Override
    public boolean permiteResponsavel(Integer idEmpregado) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

}
