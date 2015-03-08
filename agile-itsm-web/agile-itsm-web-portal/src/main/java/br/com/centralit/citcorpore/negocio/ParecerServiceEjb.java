package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class ParecerServiceEjb extends CrudServiceImpl implements ParecerService {

    private ParecerDao dao;

    @Override
    protected ParecerDao getDao() {
        if (dao == null) {
            dao = new ParecerDao();
        }
        return dao;
    }

    public ParecerDTO create(final TransactionControler tc, final UsuarioDTO usuario, final Integer idJustificativa, final String complementoJustificativa, String aprovado)
            throws Exception {
        if (aprovado == null) {
            aprovado = "N";
        }
        if (aprovado.equalsIgnoreCase("N") && idJustificativa == null) {
            throw new LogicException("Justificativa não informada");
        }

        final ParecerDao parecerDao = new ParecerDao();
        parecerDao.setTransactionControler(tc);
        final ParecerDTO parecerDto = new ParecerDTO();
        parecerDto.setIdResponsavel(usuario.getIdEmpregado());
        parecerDto.setIdJustificativa(idJustificativa);
        parecerDto.setComplementoJustificativa(complementoJustificativa);
        parecerDto.setAprovado(aprovado);
        parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());
        return (ParecerDTO) parecerDao.create(parecerDto);
    }

    public ParecerDTO createOrUpdate(final TransactionControler tc, final Integer idParecer, final UsuarioDTO usuario, final Integer idJustificativa,
            final String complementoJustificativa, String aprovado) throws Exception {
        final ParecerDao parecerDao = new ParecerDao();
        parecerDao.setTransactionControler(tc);
        ParecerDTO parecerDto = new ParecerDTO();
        if (idParecer != null && idParecer.intValue() > 0) {
            parecerDto.setIdParecer(idParecer);
            parecerDto = (ParecerDTO) parecerDao.restore(parecerDto);
            if (parecerDto.getIdResponsavel().intValue() != usuario.getIdEmpregado().intValue()) {
                parecerDto = new ParecerDTO();
            }
        }
        if (aprovado == null) {
            aprovado = "N";
        }
        if (aprovado.equalsIgnoreCase("N") && idJustificativa == null) {
            throw new LogicException("Justificativa não informada");
        }

        parecerDto.setIdResponsavel(usuario.getIdEmpregado());
        parecerDto.setIdJustificativa(idJustificativa);
        parecerDto.setComplementoJustificativa(complementoJustificativa);
        parecerDto.setAprovado(aprovado);
        parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());
        if (parecerDto.getIdParecer() != null) {
            parecerDao.update(parecerDto);
        } else {
            parecerDto = (ParecerDTO) parecerDao.create(parecerDto);
        }
        return parecerDto;
    }

    @Override
    public boolean verificarSeExisteJustificativaParecer(final ParecerDTO obj) throws Exception {
        return this.getDao().verificarSeExisteJustificativaParecer(obj);
    }

}
