/**
 *
 * ************************************************************************************************************
 *
 * Dependentes: BI Citsmart
 *
 * Obs:
 * Qualquer alteração nesta tabela deverá ser informada aos responsáveis pelo desenvolvimento do BI Citsmart.
 * O database do BI Citsmart precisa ter suas tabelas atualizadas de acordo com as mudanças nesta tabela.
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
