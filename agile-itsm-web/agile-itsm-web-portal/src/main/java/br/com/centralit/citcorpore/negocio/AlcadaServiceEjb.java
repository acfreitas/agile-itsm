package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.integracao.AlcadaDao;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.LimiteAlcadaDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoAlcada;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class AlcadaServiceEjb extends CrudServiceImpl implements AlcadaService {

    private AlcadaDao dao;

    @Override
    protected AlcadaDao getDao() {
        if (dao == null) {
            dao = new AlcadaDao();
        }
        return dao;
    }

    public AlcadaDTO determinaAlcada(final IDto objetoNegocioDto, final CentroResultadoDTO centroCustoDto) throws Exception {
        return null;
    }

    public void determinaResponsaveis(final AlcadaDTO alcadaDto, final IDto objetoNegocioDto, final EmpregadoDTO solicitante, final GrupoDTO grupoDto,
            final String abrangenciaCentroCusto, final CentroResultadoDTO centroCustoDto) throws Exception {
        final Collection<GrupoEmpregadoDTO> colGrupoEmpregado = new GrupoEmpregadoDao().findByIdGrupo(grupoDto.getIdGrupo());
        if (colGrupoEmpregado == null || colGrupoEmpregado.isEmpty()) {
            return;
        }
        Collection<EmpregadoDTO> colResponsaveis = alcadaDto.getColResponsaveis();
        if (colResponsaveis == null) {
            colResponsaveis = new ArrayList<>();
            alcadaDto.setColResponsaveis(colResponsaveis);
        }
        final HashMap<String, EmpregadoDTO> mapResponsaveis = new HashMap<>();
        for (final EmpregadoDTO empregadoDto : colResponsaveis) {
            mapResponsaveis.put("" + empregadoDto.getIdEmpregado(), empregadoDto);
        }
        for (final GrupoEmpregadoDTO grupoEmpregadoDto : colGrupoEmpregado) {
            if (solicitante != null && grupoEmpregadoDto.getIdEmpregado().intValue() == solicitante.getIdEmpregado().intValue()) {
                continue;
            }
            if (mapResponsaveis.get("" + grupoEmpregadoDto.getIdEmpregado()) != null) {
                continue;
            }
            if (abrangenciaCentroCusto.equalsIgnoreCase("R")) {
                if (centroCustoDto.getColAlcadas() != null) {
                    for (final AlcadaCentroResultadoDTO alcadaCentroResultadoDto : centroCustoDto.getColAlcadas()) {
                        if (alcadaCentroResultadoDto.getIdEmpregado() != null
                                && alcadaCentroResultadoDto.getIdEmpregado().intValue() == grupoEmpregadoDto.getIdEmpregado().intValue()) {
                            final EmpregadoDTO empregadoDto = this.recuperaEmpregado(grupoEmpregadoDto.getIdEmpregado());
                            if (empregadoDto != null) {
                                mapResponsaveis.put("" + empregadoDto.getIdEmpregado(), empregadoDto);
                                colResponsaveis.add(empregadoDto);
                            }
                        }
                    }
                }
            } else if (abrangenciaCentroCusto.equalsIgnoreCase("T")) {
                final EmpregadoDTO empregadoDto = this.recuperaEmpregado(grupoEmpregadoDto.getIdEmpregado());
                if (empregadoDto != null) {
                    mapResponsaveis.put("" + empregadoDto.getIdEmpregado(), empregadoDto);
                    colResponsaveis.add(empregadoDto);
                }
            }
        }
        if (colResponsaveis.size() == 0 && centroCustoDto.getIdCentroResultadoPai() != null) {
            CentroResultadoDTO ccustoPaiDto = new CentroResultadoDTO();
            ccustoPaiDto.setIdCentroResultado(centroCustoDto.getIdCentroResultadoPai());
            ccustoPaiDto = (CentroResultadoDTO) new CentroResultadoDao().restore(ccustoPaiDto);
            if (ccustoPaiDto != null) {
                this.determinaResponsaveis(alcadaDto, objetoNegocioDto, solicitante, grupoDto, abrangenciaCentroCusto, ccustoPaiDto);
            }
        }
    }

    private EmpregadoDTO recuperaEmpregado(final Integer idEmpregado) throws Exception {
        final EmpregadoDTO empregadoDto = new EmpregadoDTO();
        empregadoDto.setIdEmpregado(idEmpregado);
        return (EmpregadoDTO) new EmpregadoDao().restore(empregadoDto);
    }

    @Override
    public void deletarAlcada(final IDto model, final DocumentHTML document) throws ServiceException, Exception {
        final AlcadaDTO alcadaDto = (AlcadaDTO) model;
        final LimiteAlcadaDao limiteAlcadaDao = new LimiteAlcadaDao();
        try {
            this.validaUpdate(model);
            if (limiteAlcadaDao.verificarSeAlcadaPossuiLimite(alcadaDto.getIdAlcada())) {
                document.alert(this.i18nMessage("citcorpore.comum.registroNaoPodeSerExcluido"));
                return;
            } else {
                alcadaDto.setSituacao("I");
                this.getDao().update(model);
                document.alert(this.i18nMessage("MSG07"));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean existeIgual(final AlcadaDTO alcada) throws Exception {
        boolean result = this.getDao().existeIgual(alcada);
        if (!result && alcada.getIdAlcada() == null && alcada.getTipoAlcada() != null) {
            result = this.getDao().findByTipo(TipoAlcada.valueOf(alcada.getTipoAlcada())) != null;
        }
        return result;
    }

}
