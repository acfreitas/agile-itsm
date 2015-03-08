package br.com.centralit.citsmart.rest.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.centralit.citcorpore.ajaxForms.DataManager;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.citframework.service.ServiceLocator;

@Path("/services")
public class RestDataResources {

    protected static ObjetoNegocioDTO restoreByName(final String name) throws Exception {
        final ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        return objetoNegocioService.findByNomeObjetoNegocio(name);
    }

    protected static String exportDB(final Map<String, String> map, final ObjetoNegocioDTO objetoNegocioDto, final String cond, final String order, final boolean links)
            throws Exception {
        map.put("excluirAoExportar", "N");
        StringBuilder result = null;
        if (links) {
            map.put("exportarVinculos", "S");
            result = new DataManager().geraRecursiveExportObjetoNegocio(map, objetoNegocioDto.getIdObjetoNegocio(), "", "", cond, order);
        } else {
            map.put("exportarVinculos", "N");
            result = new DataManager().geraExportObjetoNegocio(map, objetoNegocioDto.getIdObjetoNegocio(), "", "", cond, order);
        }
        return "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='0'>\n" + result.toString() + "\n</tables>";
    }

    protected static Collection<CamposObjetoNegocioDTO> restoreDataFields(final ObjetoNegocioDTO objetoNegocioDto) throws Exception {
        final CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
        return camposObjetoNegocioService.findByIdObjetoNegocio(objetoNegocioDto.getIdObjetoNegocio());
    }

    public CtError buildError(final String code, final String description) {
        final CtError error = new CtError();
        error.setCode(code);
        error.setDescription(description);
        return error;
    }

    @GET
    @Path("/data/{name}/{id}")
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getBusinessObjectById(@PathParam("name") final String name, @PathParam("id") final String id,
            @QueryParam("format") @DefaultValue(RestEnum.FORMAT_DB) final String format, @QueryParam("links") @DefaultValue("N") final String links,
            @QueryParam("cond") @DefaultValue("") final String cond, @QueryParam("order") @DefaultValue("") final String order) {
        if (format.equalsIgnoreCase(RestEnum.FORMAT_DB)) {
            return this.getDataBaseObjectById(name, id, links, cond, order);
        } else {
            final CtError error = this.buildError(RestEnum.PARAM_ERROR, "### Erro nos parâmetros da consulta -> Formato '" + format + "' não suportado");
            return Response.status(Status.OK).entity(error).build();
        }
    }

    @GET
    @Path("/data/{name}")
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getBusinessObjectByCondition(@PathParam("name") final String name, @QueryParam("format") @DefaultValue(RestEnum.FORMAT_DB) final String format,
            @QueryParam("links") @DefaultValue("N") final String links, @QueryParam("cond") @DefaultValue("") final String cond,
            @QueryParam("order") @DefaultValue("") final String order) {
        if (cond == null || cond.equals("")) {
            final CtError error = this.buildError(RestEnum.PARAM_ERROR, "### Erro nos parâmetros da consulta -> Condição não informada");
            return Response.status(Status.OK).entity(error).build();
        }

        if (format.equalsIgnoreCase(RestEnum.FORMAT_DB)) {
            return this.getDataBaseObjectByCondition(name, links, cond, order);
        } else {
            final CtError error = this.buildError(RestEnum.PARAM_ERROR, "### Erro nos parâmetros da consulta -> Formato '" + format + "' não suportado");
            return Response.status(Status.OK).entity(error).build();
        }
    }

    @GET
    @Path("/data/{name}/{id}")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getDataBaseObjectById(@PathParam("name") final String name, @PathParam("id") final String id, @QueryParam("links") @DefaultValue("N") final String links,
            @QueryParam("cond") @DefaultValue("") final String cond, @QueryParam("order") @DefaultValue("") final String order) {
        try {
            final ObjetoNegocioDTO objetoNegocioDto = RestDataResources.restoreByName(name);
            if (objetoNegocioDto == null) {
                final CtError error = this.buildError(RestEnum.PARAM_ERROR, "### Erro nos parâmetros da consulta -> Objeto '" + name + "' não existe");
                return Response.status(Status.OK).entity(error).build();
            }

            final Collection<CamposObjetoNegocioDTO> fields = RestDataResources.restoreDataFields(objetoNegocioDto);
            if (fields == null) {
                final CtError error = this.buildError(RestEnum.PARAM_ERROR, "Erro na recuperação dos atributos do objeto '" + name + "' não existe");
                return Response.status(Status.OK).entity(error).build();
            }

            final Map<String, String> map = new HashMap<>();
            for (final CamposObjetoNegocioDTO campoDto : fields) {
                if (campoDto.getPk() != null && campoDto.getPk().equalsIgnoreCase("S")) {
                    map.put("COND_" + campoDto.getIdCamposObjetoNegocio(), "=");
                    map.put("VALOR_" + campoDto.getIdCamposObjetoNegocio(), id);
                    break;
                }
            }
            if (map.size() == 0) {
                final CtError error = this.buildError(RestEnum.PARAM_ERROR, "Erro na recuperação dos atributos do objeto '" + name + "' não existe");
                return Response.status(Status.OK).entity(error).build();
            }

            final boolean bLinks = links.equalsIgnoreCase("S") || links.equalsIgnoreCase("Y");
            return Response.status(Status.OK).entity(RestDataResources.exportDB(map, objetoNegocioDto, cond, order, bLinks)).build();
        } catch (final Exception e) {
            e.printStackTrace();
            final CtError error = this.buildError(RestEnum.INTERNAL_ERROR, e.getMessage());
            return Response.status(Status.OK).entity(error).build();
        }
    }

    @GET
    @Path("/data/{name}")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getDataBaseObjectByCondition(@PathParam("name") final String name, @QueryParam("links") @DefaultValue("N") final String links,
            @QueryParam("cond") @DefaultValue("") final String cond, @QueryParam("order") @DefaultValue("") final String order) {
        try {
            final ObjetoNegocioDTO objetoNegocioDto = RestDataResources.restoreByName(name);
            if (objetoNegocioDto == null) {
                final CtError error = this.buildError(RestEnum.PARAM_ERROR, "### Erro nos parâmetros da consulta -> Objeto '" + name + "' não existe");
                return Response.status(Status.OK).entity(error).build();
            }

            final Map<String, String> map = new HashMap<>();
            final boolean bLinks = links.equalsIgnoreCase("S") || links.equalsIgnoreCase("Y");
            return Response.status(Status.OK).entity(RestDataResources.exportDB(map, objetoNegocioDto, cond, order, bLinks)).build();
        } catch (final Exception e) {
            e.printStackTrace();
            final CtError error = this.buildError(RestEnum.INTERNAL_ERROR, e.getMessage());
            return Response.status(Status.OK).entity(error).build();
        }
    }

}
