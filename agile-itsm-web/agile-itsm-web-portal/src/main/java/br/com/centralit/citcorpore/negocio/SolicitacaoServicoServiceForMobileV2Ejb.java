package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.GerenciamentoRotasDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.result.GerenciamentoRotasResultDTO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoForMobileV2Dao;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.PageImpl;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Assert;
import br.com.citframework.util.geo.GeoLocation;
import br.com.citframework.util.geo.GeoUtils;

public class SolicitacaoServicoServiceForMobileV2Ejb extends SolicitacaoServicoServiceEjb implements SolicitacaoServicoServiceForMobileV2 {

    private static final String PAGEABLE_MUST_NOT_BE_NULL = "'Pageable' must not be null.";
    private static final String USUARIO_MUST_NOT_BE_NULL = "'Usuário' must not be null.";

    private SolicitacaoServicoForMobileV2Dao dao;

    @Override
    protected SolicitacaoServicoForMobileV2Dao getDao() {
        if (dao == null) {
            dao = new SolicitacaoServicoForMobileV2Dao();
        }
        return dao;
    }

    @Override
    public Page<SolicitacaoServicoDTO> listNewest(final Integer newestNumber, final UsuarioDTO usuario, final TipoSolicitacaoServico[] tiposSolicitacao, final String aprovacao)
            throws ServiceException {
        Assert.notNull(usuario, USUARIO_MUST_NOT_BE_NULL);
        try {
            return this.calculaSLA(this.getDao().listNewest(newestNumber, usuario, tiposSolicitacao, aprovacao));
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<SolicitacaoServicoDTO> listOldest(final Integer oldestNumber, final UsuarioDTO usuario, final TipoSolicitacaoServico[] tiposSolicitacao, final String aprovacao)
            throws ServiceException {
        Assert.notNull(usuario, USUARIO_MUST_NOT_BE_NULL);
        try {
            final Integer oldest = oldestNumber != null && oldestNumber.intValue() > 0 ? oldestNumber : 0;
            return this.calculaSLA(this.getDao().listOldest(oldest, usuario, tiposSolicitacao, aprovacao));
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<SolicitacaoServicoDTO> listNotificationByNumberAndUser(final Integer number, final UsuarioDTO user) throws ServiceException {
        try {
            return this.calculaSLA(this.getDao().listNotificationByNumberAndUser(number, user));
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<SolicitacaoServicoDTO> listByCoordinates(final Double latitude, final Double longitude, final UsuarioDTO usuario, final TipoSolicitacaoServico[] tiposSolicitacao,
            final String aprovacao, final Pageable pageable) throws ServiceException {
        Assert.notNull(usuario, USUARIO_MUST_NOT_BE_NULL);
        Assert.notNull(pageable, PAGEABLE_MUST_NOT_BE_NULL);
        try {
            final GeoLocation referencePoint = GeoLocation.fromDegrees(latitude, longitude);
            final double distance = Double.valueOf(StringUtils.trim(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.MOBILE_RANGE_ACTION, "10")));
            final double distanceRadius = distance / GeoUtils.EARTH_RADIUS;
            final GeoLocation[] bounds = referencePoint.boundingCoordinates(distance, GeoUtils.EARTH_RADIUS);
            return this.calculaSLA(this.getDao().listByCoordinates(referencePoint, bounds, distanceRadius, bounds[0].getLongitudeInRadians() > bounds[1].getLongitudeInRadians(),
                    usuario, tiposSolicitacao, aprovacao, pageable));
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<GerenciamentoRotasResultDTO> listSolicitacoesParaRoteirizacao(final GerenciamentoRotasDTO filter, final Pageable pageable) throws ServiceException {
        Assert.notNull(filter, "'GerenciamentoRotas' must not be null.");
        Assert.notNull(pageable, PAGEABLE_MUST_NOT_BE_NULL);
        try {
            final Page<GerenciamentoRotasResultDTO> result = this.getDao().listarSolicitacoesParaRoteirizacao(filter, pageable);

            final List<GerenciamentoRotasResultDTO> content = result.getContent();
            final List<GerenciamentoRotasResultDTO> newContent = new ArrayList<>();
            for (final GerenciamentoRotasResultDTO gerResult : content) {
                if (gerResult.getDataInicioAtendimento() != null) {
                    gerResult.setIniciada(true);
                    gerResult.setDataInicioAtendimento(null);
                }
                newContent.add(gerResult);
            }

            return new PageImpl<GerenciamentoRotasResultDTO>(newContent, pageable, result.getTotalElements());
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    private Page<SolicitacaoServicoDTO> calculaSLA(final Page<SolicitacaoServicoDTO> page) throws Exception {
        for (final SolicitacaoServicoDTO solicitacao : page) {
            solicitacao.setNomeServico(solicitacao.getServico());
            if (StringUtils.isNotBlank(solicitacao.getNomeUnidadeSolicitante())) {
                solicitacao.setSolicitanteUnidade(solicitacao.getSolicitante() + " (" + solicitacao.getNomeUnidadeSolicitante() + ")");
            }
            this.verificaSituacaoSLA(solicitacao, null);
        }
        return page;
    }

    private UsuarioService usuarioService;

    public UsuarioService getUsuarioService() throws ServiceException {
        if (usuarioService == null) {
            usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        }
        return usuarioService;
    }

}
