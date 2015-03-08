package br.com.centralit.citcorpore.negocio;

import java.sql.Date;

import br.com.citframework.service.CrudService;


public interface FeriadoService extends CrudService {
    
    public boolean isFeriado(Date data, Integer idCidade, Integer idUf) throws Exception;
    
}