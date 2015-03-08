package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ContadorAcessoDTO;
import br.com.centralit.citcorpore.integracao.ContadorAcessoDao;
import br.com.citframework.service.CrudServiceImpl;

public class ContadorAcessoServiceEjb extends CrudServiceImpl implements ContadorAcessoService {

    private ContadorAcessoDao dao;

    @Override
    protected ContadorAcessoDao getDao() {
        if (dao == null) {
            dao = new ContadorAcessoDao();
        }
        return dao;
    }

    @Override
    public boolean verificarDataHoraDoContadorDeAcesso(final ContadorAcessoDTO contadorDto) throws Exception {
        return this.getDao().verificarDataHoraDoContadorDeAcesso(contadorDto);
    }

    @Override
    public Integer quantidadesDeAcessoPorBaseConhecimnto(final BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
        return this.getDao().quantidadesDeAcessoPorBaseConhecimnto(baseConhecimentoDTO);
    }

    @Override
    public Integer quantidadesDeAcessoPorPeriodo(final BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
        Integer quantidadeAcessos = 0;
        try {
            quantidadeAcessos = this.getDao().quantidadesDeAcessoPorPeriodo(baseConhecimentoDTO);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return quantidadeAcessos;
    }

}
