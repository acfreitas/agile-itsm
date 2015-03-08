package br.com.centralit.bpm.negocio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.ElementoFluxoEmailDTO;
import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.dto.UsuarioBpmDTO;
import br.com.centralit.bpm.integracao.ItemTrabalhoFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.citframework.util.UtilDatas;

public class Email extends ItemTrabalho{

	public List<ItemTrabalho> resolve() throws Exception {
		registra();
		executa(null,itemTrabalhoDto.getIdElemento(),instanciaFluxo.getMapObj());
		SequenciaFluxo sequenciaFluxo = new SequenciaFluxo(instanciaFluxo);
		return sequenciaFluxo.getDestinos(this);
	}

	public void executa(String loginUsuario, Integer id, Map<String, Object> objetos) throws Exception {
		if (resolvido())
			return;

        if (objetos == null) {
            objetos = new HashMap<>();
            this.instanciaFluxo.getExecucaoFluxo().mapObjetoNegocio(objetos);
            this.instanciaFluxo.getObjetos(objetos);
        }

		ItemTrabalhoFluxoDao itemTrabalhoFluxoDao = new ItemTrabalhoFluxoDao();
		setTransacaoDao(itemTrabalhoFluxoDao);

	    IUsuarioGrupo usuarioGrupo = new UsuarioGrupo();
        Map<String, String> mapEmails = new HashMap<>();

	    if (elementoFluxoDto.getDestinatariosEmail() != null && !elementoFluxoDto.getDestinatariosEmail().trim().equals("")) {
            String destinatarios[] = substituiParametros(((ElementoFluxoEmailDTO) elementoFluxoDto).getColDestinatarios(), objetos, ";");
            if (destinatarios != null) {
            	for (String destinatario : destinatarios) {
                    if (mapEmails.get(destinatario) == null) {
                        mapEmails.put(destinatario, destinatario);
                    }
            	}
            }
	    }

		if (elementoFluxoDto.getGrupos() != null && !elementoFluxoDto.getGrupos().trim().equals("")) {
            String grupos[] = substituiParametros(((ElementoFluxoEmailDTO) elementoFluxoDto).getColGrupos(), objetos, ";");
            if (grupos != null) {
            	for (String grupo: grupos) {
                    GrupoBpmDTO grupoDto = usuarioGrupo.recuperaGrupo(grupo);
                    if (grupoDto != null && grupoDto.getEmails() != null) {
                        for (String email : grupoDto.getEmails()) {
                            if (mapEmails.get(email) == null) {
                                mapEmails.put(email, email);
                            }
                        }
                    }
            	}
            }
        }

        if (elementoFluxoDto.getUsuarios() != null && !elementoFluxoDto.getUsuarios().trim().equals("")) {
            String usuarios[] = substituiParametros(((ElementoFluxoEmailDTO) elementoFluxoDto).getColUsuarios(), objetos, ";");
            if (usuarios != null) {
                for (String usuario: usuarios) {
                    UsuarioBpmDTO usuarioDto = usuarioGrupo.recuperaUsuario(usuario);
                    if (usuarioDto != null && usuarioDto.getEmails() != null) {
                        for (String email : usuarioDto.getEmails()) {
                            if (mapEmails.get(email) == null) {
                                mapEmails.put(email, email);
                            }
                        }
                    }
                }
            }
        }

        if (mapEmails.size() > 0) {
            String[] destinatarios = new String[mapEmails.size()];
            int i = 0;
            for (String email: mapEmails.values()) {
                destinatarios[i] = email;
                i ++;
            }
            instanciaFluxo.getExecucaoFluxo().enviaEmail(elementoFluxoDto.getModeloEmail(), destinatarios);
        }


		itemTrabalhoDto.setSituacao(Enumerados.SituacaoItemTrabalho.Executado.name());
		itemTrabalhoFluxoDao.update(itemTrabalhoDto);
	}

	@Override
	public void registra() throws Exception {
		if (existe())
			return;

		ItemTrabalhoFluxoDao itemTrabalhoDao = new ItemTrabalhoFluxoDao();
		setTransacaoDao(itemTrabalhoDao);

		itemTrabalhoDto = new ItemTrabalhoFluxoDTO();
		itemTrabalhoDto.setIdElemento(elementoFluxoDto.getIdElemento());
		itemTrabalhoDto.setIdInstancia(instanciaFluxo.getIdInstancia());
		itemTrabalhoDto.setDataHoraCriacao(UtilDatas.getDataHoraAtual());
		itemTrabalhoDto.setSituacao(Enumerados.SituacaoItemTrabalho.Criado.name());
		itemTrabalhoDto = (ItemTrabalhoFluxoDTO) itemTrabalhoDao.create(itemTrabalhoDto);
	}

	public String getTextoEmail() {
		return elementoFluxoDto.getTextoEmail();
	}

	@Override
	public boolean executavel() {
		return true;
	}

}
