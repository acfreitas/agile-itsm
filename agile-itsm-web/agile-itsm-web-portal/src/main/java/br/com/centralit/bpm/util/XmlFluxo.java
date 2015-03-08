package br.com.centralit.bpm.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ElementoFluxoEmailDTO;
import br.com.centralit.bpm.dto.ElementoFluxoFinalizacaoDTO;
import br.com.centralit.bpm.dto.ElementoFluxoInicioDTO;
import br.com.centralit.bpm.dto.ElementoFluxoPortaDTO;
import br.com.centralit.bpm.dto.ElementoFluxoRaiaDTO;
import br.com.centralit.bpm.dto.ElementoFluxoScriptDTO;
import br.com.centralit.bpm.dto.ElementoFluxoTarefaDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.SequenciaFluxoDTO;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTratamentoArquivos;

public class XmlFluxo {

    private static String prefixo = "";

    public static void carregaTextoXml(final FluxoDTO fluxoDto) throws Exception {
        final InputStream ioos = new ByteArrayInputStream(fluxoDto.getConteudoXml().getBytes());
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document doc = builder.parse(ioos);
        carrega(doc, fluxoDto);
    }

    public static void carregaArquivoXml(final String nomeArquivo, final FluxoDTO fluxoDto) throws Exception {
        InputStream ioos = ClassLoader.getSystemResourceAsStream(nomeArquivo);
        if (ioos == null) {
            ioos = new FileInputStream(nomeArquivo);
        }

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document doc = builder.parse(ioos);
        carrega(doc, fluxoDto);
        fluxoDto.setConteudoXml(UtilTratamentoArquivos.getStringTextFromFileTxt(nomeArquivo));
    }

    public static void carrega(final Document doc, final FluxoDTO fluxoDto) throws Exception {
        if (doc == null) {
            return;
        }

        final List<ElementoFluxoDTO> tarefas = new ArrayList<>();
        final List<SequenciaFluxoDTO> sequenciamentos = new ArrayList<>();
        final List<ElementoFluxoDTO> portas = new ArrayList<>();
        final List<ElementoFluxoDTO> scripts = new ArrayList<>();
        final List<ElementoFluxoRaiaDTO> raias = new ArrayList<>();
        final List<ElementoFluxoDTO> emails = new ArrayList<>();
        final List<ElementoFluxoDTO> finalizacoes = new ArrayList<>();

        String variaveis = "";
        final Node noRoot = doc.getChildNodes().item(0);
        for (int j = 0; j < noRoot.getChildNodes().getLength(); j++) {
            final Node noItem = noRoot.getChildNodes().item(j);
            if (noItem.getNodeName().equals("#text")) {
                continue;
            }

            final String nodeName = noItem.getNodeName();
            if (nodeName.indexOf(":process") > 0) {
                prefixo = nodeName.substring(0, nodeName.indexOf(":"));

                final NamedNodeMap map = noItem.getAttributes();

                String nome = UtilStrings.nullToVazio(map.getNamedItem("name").getNodeValue());
                nome = nome.replaceAll("\n", "");
                fluxoDto.setNomeFluxo(nome.trim());
                // fluxoDto.setVersao(UtilStrings.nullToVazio(map.getNamedItem("drools:version").getNodeValue()));

                for (int i = 0; i < noItem.getChildNodes().getLength(); i++) {
                    final Node noItemParm = noItem.getChildNodes().item(i);
                    if (noItemParm.getNodeName().equals("#text")) {
                        continue;
                    }

                    if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":property")) {
                        final String str = carregaVariavel(noItemParm);
                        if (str != null) {
                            if (variaveis.length() > 0) {
                                variaveis += ";";
                            }
                            variaveis += str;
                        }
                    }

                    if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":startEvent")) {
                        fluxoDto.setInicioFluxo(carregaInicioFluxo(noItemParm));
                    }

                    if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":laneSet")) {
                        carregaRaia(noItemParm, raias);
                    }

                    if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":sendTask")) {
                        emails.add(carregaEmail(noItemParm));
                    }

                    if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":task")) {
                        tarefas.add(carregaTarefa(noItemParm));
                    }

                    if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":scriptTask")) {
                        final ElementoFluxoScriptDTO scriptDto = carregaScript(noItemParm);
                        if (scriptDto != null) {
                            scripts.add(scriptDto);
                        }
                    }

                    if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":exclusiveGateway") || noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":parallelGateway")) {
                        portas.add(carregaPorta(noItemParm));
                    }

                    if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":endEvent")) {
                        finalizacoes.add(carregaFinalizacao(noItemParm));
                    }

                    if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":sequenceFlow")) {
                        sequenciamentos.add(carregaSequencia(noItemParm));
                    }

                }
            }
        }

        fluxoDto.setVariaveis(variaveis);
        fluxoDto.setColTarefas(tarefas);
        fluxoDto.setColPortas(portas);
        fluxoDto.setColScripts(scripts);
        fluxoDto.setColEmails(emails);
        fluxoDto.setColRaias(raias);
        fluxoDto.setColFinalizacoes(finalizacoes);
        fluxoDto.setColSequenciamentos(sequenciamentos);
    }

    private static String carregaVariavel(final Node noItem) {
        if (!noItem.getNodeName().equalsIgnoreCase(prefixo + ":property")) {
            return null;
        }

        final NamedNodeMap mapItem = noItem.getAttributes();
        return UtilStrings.nullToVazio(mapItem.getNamedItem("id").getNodeValue());
    }

    private static ElementoFluxoTarefaDTO carregaTarefa(final Node noItem) throws Exception {
        if (!noItem.getNodeName().equalsIgnoreCase(prefixo + ":task")) {
            return null;
        }

        final NamedNodeMap map = noItem.getAttributes();
        final ElementoFluxoTarefaDTO tarefaDto = new ElementoFluxoTarefaDTO();
        tarefaDto.setIdDesenho(UtilStrings.nullToVazio(map.getNamedItem("id").getNodeValue()));
        tarefaDto.setNome(UtilStrings.nullToVazio(map.getNamedItem("name").getNodeValue()).replaceAll("\n", ""));
        final String id[] = UtilStrings.nullToVazio(map.getNamedItem("drools:taskName").getNodeValue()).split(":");
        if (id.length > 0) {
            if (id[0].equalsIgnoreCase("visao")) {
                tarefaDto.setTipoInteracao(Enumerados.INTERACAO_VISAO);
                tarefaDto.setVisao(id[1].trim());
            } else if (id[0].equalsIgnoreCase("url")) {
                tarefaDto.setTipoInteracao(Enumerados.INTERACAO_URL);
                tarefaDto.setUrl(id[1].trim());
            }
        }

        final List<String> usuarios = new ArrayList<>();
        final List<String> grupos = new ArrayList<>();
        for (int i = 0; i < noItem.getChildNodes().getLength(); i++) {
            final Node noItemParm = noItem.getChildNodes().item(i);
            if (noItemParm.getNodeName().equals("#text")) {
                continue;
            }

            if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":documentation")) {
                tarefaDto.setDocumentacao(UtilStrings.nullToVazio(noItemParm.getTextContent()));
            }

            if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":potentialOwner")) {
                final String str[] = UtilStrings.nullToVazio(noItemParm.getTextContent().trim()).split(",");
                if (str.length > 0) {
                    for (final String element : str) {
                        final String potentialOwner[] = element.split(":");
                        if (potentialOwner.length > 1) {
                            if (potentialOwner[0].equalsIgnoreCase("usuario")) {
                                usuarios.add(potentialOwner[1]);
                            } else if (potentialOwner[0].equalsIgnoreCase("grupo")) {
                                grupos.add(potentialOwner[1]);
                            }
                        } else {
                            grupos.add(element);
                        }
                    }
                }
            }
        }

        if (usuarios.size() > 0) {
            final String[] usr = new String[usuarios.size()];
            final int i = 0;
            for (final String str : usuarios) {
                usr[i] = str;
            }
            tarefaDto.setColUsuarios(usr);
        }

        if (grupos.size() > 0) {
            final String[] grp = new String[grupos.size()];
            final int i = 0;
            for (final String str : grupos) {
                grp[i] = str;
            }
            tarefaDto.setColGrupos(grp);
        }

        if (tarefaDto.getIdDesenho() == null) {
            throw new Exception("ID XML não definido para tarefa");
        }
        if (tarefaDto.getNome() == null) {
            throw new Exception("Nome da tarefa " + tarefaDto.getIdDesenho() + " não definido");
        }
        if (tarefaDto.getDocumentacao() == null) {
            throw new Exception("Documentação da tarefa " + tarefaDto.getIdDesenho() + " não definido");
        }
        if (tarefaDto.getTipoInteracao() == null) {
            throw new Exception("Tipo de interação da tarefa " + tarefaDto.getIdDesenho() + " não definido");
        }

        return tarefaDto;
    }

    private static SequenciaFluxoDTO carregaSequencia(final Node noItem) throws Exception {
        if (!noItem.getNodeName().equalsIgnoreCase(prefixo + ":sequenceFlow")) {
            return null;
        }

        final SequenciaFluxoDTO sequenciaDto = new SequenciaFluxoDTO();
        final NamedNodeMap map = noItem.getAttributes();
        sequenciaDto.setIdDesenhoOrigem(UtilStrings.nullToVazio(map.getNamedItem("sourceRef").getNodeValue()));
        sequenciaDto.setIdDesenhoDestino(UtilStrings.nullToVazio(map.getNamedItem("targetRef").getNodeValue()));

        for (int i = 0; i < noItem.getChildNodes().getLength(); i++) {
            final Node noItemParm = noItem.getChildNodes().item(i);
            if (noItemParm.getNodeName().equals("#text")) {
                continue;
            }

            if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":conditionExpression")) {
                sequenciaDto.setCondicao(UtilStrings.nullToVazio(noItemParm.getTextContent()));
            }
        }

        if (sequenciaDto.getIdDesenhoOrigem() == null) {
            throw new Exception("ID origem não definido para sequência");
        }
        if (sequenciaDto.getIdDesenhoDestino() == null) {
            throw new Exception("ID destino não definido para sequência");
        }

        return sequenciaDto;
    }

    private static ElementoFluxoInicioDTO carregaInicioFluxo(final Node noItem) throws Exception {
        if (!noItem.getNodeName().equalsIgnoreCase(prefixo + ":startEvent")) {
            return null;
        }

        final ElementoFluxoInicioDTO inicioFluxoDto = new ElementoFluxoInicioDTO();
        final NamedNodeMap map = noItem.getAttributes();
        inicioFluxoDto.setIdDesenho(UtilStrings.nullToVazio(map.getNamedItem("id").getNodeValue()));

        return inicioFluxoDto;
    }

    private static ElementoFluxoPortaDTO carregaPorta(final Node noItem) throws Exception {
        if (!noItem.getNodeName().equalsIgnoreCase(prefixo + ":exclusiveGateway") && !noItem.getNodeName().equalsIgnoreCase(prefixo + ":parallelGateway")) {
            return null;
        }

        final NamedNodeMap map = noItem.getAttributes();
        final String direction = UtilStrings.nullToVazio(map.getNamedItem("gatewayDirection").getNodeValue());

        final ElementoFluxoPortaDTO decisaoDto = new ElementoFluxoPortaDTO();
        if (noItem.getNodeName().equalsIgnoreCase(prefixo + ":exclusiveGateway")) {
            decisaoDto.setSubTipo(Enumerados.TipoPorta.Decisao.name());
        } else if (direction.equalsIgnoreCase("Converging")) {
            decisaoDto.setSubTipo(Enumerados.TipoPorta.Convergente.name());
        } else {
            decisaoDto.setSubTipo(Enumerados.TipoPorta.Paralela.name());
        }
        decisaoDto.setIdDesenho(UtilStrings.nullToVazio(map.getNamedItem("id").getNodeValue()));

        if (decisaoDto.getIdDesenho() == null) {
            throw new Exception("ID XML não definido para decisão");
        }

        return decisaoDto;
    }

    private static void carregaRaia(final Node noItem, final List<ElementoFluxoRaiaDTO> raias) throws Exception {
        if (!noItem.getNodeName().equalsIgnoreCase(prefixo + ":laneSet")) {
            return;
        }

        for (int i = 0; i < noItem.getChildNodes().getLength(); i++) {
            final Node noItemParm = noItem.getChildNodes().item(i);
            if (noItemParm.getNodeName().equals("#text")) {
                continue;
            }

            if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":lane")) {
                final ElementoFluxoRaiaDTO raiaDto = new ElementoFluxoRaiaDTO();
                final NamedNodeMap map = noItemParm.getAttributes();
                raiaDto.setIdDesenho(UtilStrings.nullToVazio(map.getNamedItem("id").getNodeValue()));
                raiaDto.setSiglaGrupo(UtilStrings.nullToVazio(map.getNamedItem("name").getNodeValue()));

                final List<String> elementos = new ArrayList<>();
                for (int x = 0; x < noItemParm.getChildNodes().getLength(); x++) {
                    final Node noItemLane = noItemParm.getChildNodes().item(x);
                    if (noItemLane.getNodeName().equals("#text")) {
                        continue;
                    }

                    if (noItemLane.getNodeName().equalsIgnoreCase(prefixo + ":flowNodeRef")) {
                        elementos.add(UtilStrings.nullToVazio(noItemLane.getTextContent()));
                    }
                }
                raiaDto.setIdElementosAgrupados(elementos);

                if (raiaDto.getIdDesenho() == null) {
                    throw new Exception("ID XML não definido para raia");
                }
                if (raiaDto.getSiglaGrupo() == null) {
                    throw new Exception("Identificação do grupo executor não definido para raia " + raiaDto.getIdDesenho());
                }

                raias.add(raiaDto);
            }
        }

    }

    private static ElementoFluxoEmailDTO carregaEmail(final Node noItem) throws Exception {
        if (!noItem.getNodeName().equalsIgnoreCase(prefixo + ":sendTask")) {
            return null;
        }

        final ElementoFluxoEmailDTO emailDto = new ElementoFluxoEmailDTO();
        final NamedNodeMap map = noItem.getAttributes();
        emailDto.setIdDesenho(UtilStrings.nullToVazio(map.getNamedItem("id").getNodeValue()));
        emailDto.setNome(UtilStrings.nullToVazio(map.getNamedItem("drools:taskName").getNodeValue()));

        if (emailDto.getIdDesenho() == null) {
            throw new Exception("ID XML não definido para email");
        }
        if (emailDto.getNome() == null) {
            throw new Exception("Identificador de email não definido para " + emailDto.getIdDesenho());
        }

        return emailDto;
    }

    private static ElementoFluxoFinalizacaoDTO carregaFinalizacao(final Node noItem) throws Exception {
        if (!noItem.getNodeName().equalsIgnoreCase(prefixo + ":endEvent")) {
            return null;
        }

        final ElementoFluxoFinalizacaoDTO finalizacaoDto = new ElementoFluxoFinalizacaoDTO();
        final NamedNodeMap map = noItem.getAttributes();
        finalizacaoDto.setIdDesenho(UtilStrings.nullToVazio(map.getNamedItem("id").getNodeValue()));

        String doc = "";
        boolean bEncadeado = false;
        for (int i = 0; i < noItem.getChildNodes().getLength(); i++) {
            final Node noItemParm = noItem.getChildNodes().item(i);
            if (noItemParm.getNodeName().equals("#text")) {
                continue;
            }

            if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":documentation")) {
                doc = UtilStrings.nullToVazio(noItemParm.getTextContent());
            }

            if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":signalEventDefinition")) {
                bEncadeado = true;
            }

        }

        if (doc.trim().length() > 0 && bEncadeado) {
            final String[] str = doc.split(":");
            if (str.length > 1 && str[0].equalsIgnoreCase("nomeFluxo")) {
                finalizacaoDto.setSubTipo(Enumerados.TipoFinalizacao.Encadeada.name());
                finalizacaoDto.setNomeFluxoEncadeado(str[1]);
            }
        }

        return finalizacaoDto;
    }

    private static ElementoFluxoScriptDTO carregaScript(final Node noItem) throws Exception {
        if (!noItem.getNodeName().equalsIgnoreCase(prefixo + ":scriptTask")) {
            return null;
        }

        final ElementoFluxoScriptDTO scriptDto = new ElementoFluxoScriptDTO();
        final NamedNodeMap map = noItem.getAttributes();

        scriptDto.setIdDesenho(UtilStrings.nullToVazio(map.getNamedItem("id").getNodeValue()));

        for (int i = 0; i < noItem.getChildNodes().getLength(); i++) {
            final Node noItemParm = noItem.getChildNodes().item(i);
            if (noItemParm.getNodeName().equals("#text")) {
                continue;
            }

            if (noItemParm.getNodeName().equalsIgnoreCase(prefixo + ":script")) {
                scriptDto.setScript(UtilStrings.nullToVazio(noItemParm.getTextContent()));
            }
        }

        if (scriptDto.getIdDesenho() == null) {
            throw new Exception("ID XML não definido para script");
        }
        if (scriptDto.getScript() == null) {
            throw new Exception("Texto script não definido para " + scriptDto.getIdDesenho());
        }

        return scriptDto;
    }

}
