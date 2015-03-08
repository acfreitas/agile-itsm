package br.com.centralit.citcorpore.negocio.alcada;

import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.TransactionControler;

public interface IAlcada {
    public AlcadaDTO determinaAlcada(IDto objetoNegocioDto, CentroResultadoDTO centroCustoDto, TransactionControler tc) throws Exception;
    public void determinaResponsaveis(GrupoDTO grupoDto, String abrangenciaCentroCusto, CentroResultadoDTO centroCustoDto) throws Exception;
    public boolean permiteResponsavel(Integer idEmpregado) throws Exception;
}
