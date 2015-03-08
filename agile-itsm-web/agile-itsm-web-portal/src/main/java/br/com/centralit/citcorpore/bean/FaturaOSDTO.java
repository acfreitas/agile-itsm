/**
 *
 * ************************************************************************************************************
 *
 * Dependentes: BI Citsmart
 *
 * Obs:
 * Qualquer altera��o nesta tabela dever� ser informada aos respons�veis pelo desenvolvimento do BI Citsmart.
 * O database do BI Citsmart precisa ter suas tabelas atualizadas de acordo com as mudan�as nesta tabela.
 *
 * ************************************************************************************************************
 *
 */

package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class FaturaOSDTO implements IDto {
    private Integer idFatura;
    private Integer idOs;

    public Integer getIdFatura(){
        return idFatura;
    }
    public void setIdFatura(Integer parm){
        idFatura = parm;
    }

    public Integer getIdOs(){
        return idOs;
    }
    public void setIdOs(Integer parm){
        idOs = parm;
    }

}
