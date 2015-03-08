package br.com.centralit.citcorpore.batch;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Semaphore;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.EscalonamentoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.centralit.citcorpore.bean.RelEscalonamentoProblemaDto;
import br.com.centralit.citcorpore.integracao.EscalonamentoDAO;
import br.com.centralit.citcorpore.integracao.OcorrenciaProblemaDAO;
import br.com.centralit.citcorpore.integracao.ProblemaDAO;
import br.com.centralit.citcorpore.integracao.RegraEscalonamentoDAO;
import br.com.centralit.citcorpore.integracao.RelEscalonamentoProblemaDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.ProblemaServiceEjb;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class MonitoraProblema extends Thread{

	private final ProblemaDAO problemaDao = new ProblemaDAO();
	private final Semaphore performanceDataSemaphore = new Semaphore(1);

	@Override
	public void run() {
		while (true) {
			performanceDataSemaphore.acquireUninterruptibly();
			ProblemaServiceEjb problemaServiceEjb = new ProblemaServiceEjb();
			Timestamp dataHoraAtual = UtilDatas.getDataHoraAtual();
			String ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA_STR = "";
			Integer ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA = 0;
			String ligaFuncionamentoRegrasEscalonamento = "N";
			Integer ID_MODELO_EMAIL_PRAZO_VENCENDO = null;
			String ID_MODELO_EMAIL_PRAZO_VENCENDO_STR = "";

			OcorrenciaProblemaDAO ocorrenciaDao = new OcorrenciaProblemaDAO();
			Collection<ProblemaDTO> col = null;
			try {
				ligaFuncionamentoRegrasEscalonamento = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.HABILITA_ESCALONAMENTO_PROBLEMA, "N");
				ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA_STR = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA, "0");
				if(!ID_MODELO_EMAIL_PRAZO_VENCENDO_STR.trim().equals("")) {
					ID_MODELO_EMAIL_PRAZO_VENCENDO  = new Integer(ID_MODELO_EMAIL_PRAZO_VENCENDO_STR);
				}
				ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA = new Integer(ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA_STR.trim());
			} catch (Exception e) {
				e.printStackTrace();
			}

			try{

			try {
				//col = solicitacaoServicoDao.getEmAndamentoParaTratamentoBatch();
				/* Lista todas as solicitaçãoes relacionadas a regra de escalonamento definido e demais.*/
				col = problemaDao.listSolicitacoesByRegra();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (col != null) {

				for (ProblemaDTO problemaDTO : col) {

					OcorrenciaProblemaDTO ocorrSolDto = null;
					try {
						ocorrSolDto = ocorrenciaDao.findUltimoByIdSolicitacaoServico(problemaDTO.getIdProblema());
					} catch (Exception e) {
						e.printStackTrace();
					}

					if(ligaFuncionamentoRegrasEscalonamento!=null && ligaFuncionamentoRegrasEscalonamento.equalsIgnoreCase("S")) {

						RegraEscalonamentoDAO regraEscalonamentoDao = new RegraEscalonamentoDAO();
						EscalonamentoDAO escalonamentoDao = new EscalonamentoDAO();
						RelEscalonamentoProblemaDao relEscalonamentoProblemaDao = new RelEscalonamentoProblemaDao();
						Collection<RegraEscalonamentoDTO> colecaoRegrasEscalonamento = null;
						Collection<EscalonamentoDTO> colecaoEscalonamento = null;
						try {
							//regra: 1 para solicitação/incidente, 3 para mudança, 2 para problema
							colecaoRegrasEscalonamento = regraEscalonamentoDao.findRegraByProblema(problemaDTO, 2);
							if(colecaoRegrasEscalonamento != null) {
								for (RegraEscalonamentoDTO regraEscalonamentoDTO : colecaoRegrasEscalonamento) {

									//trataSituacaoVencimentoSolicitacao(requisicaoMudancaDto, regraEscalonamentoDTO, mudancaServiceEjb, dataHoraAtual, ocorrSolDto, ID_MODELO_EMAIL_PRAZO_VENCENDO);

									colecaoEscalonamento = escalonamentoDao.findByRegraEscalonamento(regraEscalonamentoDTO);
									if(colecaoEscalonamento != null) {
										for (EscalonamentoDTO escalonamentoDTO : colecaoEscalonamento) {
											//System.out.println("Estabelecendo regra... Solicitação: " + solicitacaoServicoDTO.getIdProblema());
											if (escalonamentoDTO.getPrazoExecucao() == null || escalonamentoDTO.getPrazoExecucao().intValue() == 0)
												continue;

											//Verifica se já existe referencia
											boolean temEscalonamento = relEscalonamentoProblemaDao.temRelacionamentoSolicitacaoEscalonamento(problemaDTO.getIdProblema(), escalonamentoDTO.getIdEscalonamento());
											if(temEscalonamento) {
												//System.out.println("Escalonamento " + escalonamentoDTO.getIdEscalonamento() + " já executado.");
												continue;
											}

											if(regraEscalonamentoDTO.getTipoDataEscalonamento() != null && regraEscalonamentoDTO.getTipoDataEscalonamento().intValue() == 1) {
												/**
												 * Verifica se o tempo que se passou é maior que o prazo de execução
												 */
												if((problemaDTO.getDataHoraInicio() != null) &&
														(dataHoraAtual.getTime() - problemaDTO.getDataHoraSolicitacao().getTime()) > (escalonamentoDTO.getPrazoExecucao() * 60 * 1000)){
													if (escalonamentoDTO.getIdGrupoExecutor() != null) {
														/**
														 * Atualizando a tabela de relacionamento
														 */
														RelEscalonamentoProblemaDto dto = new RelEscalonamentoProblemaDto();
														dto.setIdProblema(problemaDTO.getIdProblema());
														dto.setIdEscalonamento(escalonamentoDTO.getIdEscalonamento());
														relEscalonamentoProblemaDao.create(dto);

														//System.out.println("Realizando escalação automática...");
														/**
														 * Realizando o escalonamento da solicitação com base nas regras estabelecidas
														 * Se prioridade for nula estão se escalonamento com a mesma prioridade antiga
														 */
														problemaServiceEjb.updateTimeAction(escalonamentoDTO.getIdGrupoExecutor(),
																(escalonamentoDTO.getIdPrioridade() != null ? escalonamentoDTO.getIdPrioridade() : problemaDTO.getPrioridade()),
																problemaDTO.getIdProblema());
														/**
														 * Enviando email de escalação automática
														 */
														enviaEmail(ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA, problemaDTO.getIdProblema());

														AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
														List<AcordoNivelServicoDTO> listaContratos = acordoNivelServicoService.findIdEmailByIdSolicitacaoServico(problemaDTO.getIdProblema());
														if(listaContratos != null && !listaContratos.isEmpty())
															enviaEmail(listaContratos.get(0).getIdEmail(), problemaDTO.getIdProblema());
													}
												}
											} else if (regraEscalonamentoDTO.getTipoDataEscalonamento() != null && regraEscalonamentoDTO.getTipoDataEscalonamento().intValue() == 2) {

												Date dateCons = null;
												OcorrenciaProblemaDTO ocorrEscalacao = ocorrenciaDao.findUltimoByIdSolicitacaoServicoAndOcorrencia(problemaDTO.getIdProblema());

												if (ocorrSolDto != null) {
													if(ocorrEscalacao != null)
														ocorrSolDto = ocorrEscalacao;

													dateCons = ocorrSolDto.getDataregistro();
													String hora = ocorrSolDto.getHoraregistro();
													//System.out.println("Ultima ocorrência: " + hora);
													try {
														Timestamp timeAux = UtilDatas.strToTimestamp(UtilDatas.dateToSTR(dateCons) + " " + hora + ":00");
														if((dataHoraAtual.getTime() - timeAux.getTime()) > (escalonamentoDTO.getPrazoExecucao() * 60 * 1000)){
															if (escalonamentoDTO.getIdGrupoExecutor() != null) {

																/**
																 * Atualizando a tabela de relacionamento
																 */
																RelEscalonamentoProblemaDto dto = new RelEscalonamentoProblemaDto();
																dto.setIdProblema(problemaDTO.getIdProblema());
																dto.setIdEscalonamento(escalonamentoDTO.getIdEscalonamento());
																relEscalonamentoProblemaDao.create(dto);

																//System.out.println("Realizando escalação automática...");
																/**
																 * Realizando o escalonamento da solicitação com base nas regras estabelecidas
																 * Se prioridade for nula estão se escalonamento com a mesma prioridade antiga
																 */
																problemaServiceEjb.updateTimeAction(escalonamentoDTO.getIdGrupoExecutor(),
																		(escalonamentoDTO.getIdPrioridade() != null ? escalonamentoDTO.getIdPrioridade() : problemaDTO.getPrioridade()),
																		problemaDTO.getIdProblema());
																/**
																 * Enviando email de escalação automática
																 */
																enviaEmail(ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA, problemaDTO.getIdProblema());

																AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
																List<AcordoNivelServicoDTO> listaContratos = acordoNivelServicoService.findIdEmailByIdSolicitacaoServico(problemaDTO.getIdProblema());
																if(listaContratos != null && !listaContratos.isEmpty())
																	enviaEmail(listaContratos.get(0).getIdEmail(), problemaDTO.getIdProblema());
															}
														}
														System.out.println("Finalizando regra de escalonamento...");
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											}
										}
									}

								}
							}
						} catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}

		} finally {
			performanceDataSemaphore.release();
		}

			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
			}
		}
	}

	public void enviaEmail(Integer idModeloEmail, Integer idProblema) throws Exception {
		if (idModeloEmail == null || idModeloEmail.intValue() == 0)
			return;

		String enviaEmail = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EnviaEmailFluxo, "N");
		if (!enviaEmail.equalsIgnoreCase("S"))
			return;

		String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
		if (remetente == null)
			throw new LogicException("Remetente para notificações de solicitação de serviço não foi parametrizado");

		String urlSistema = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.URL_Sistema, "");

		ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(idProblema, null);
		problemaAuxDto.setNomeTarefa("Automatic escalation");

		String idHashValidacao = CriptoUtils.generateHash("CODED" + problemaAuxDto.getIdProblema(), "MD5");
		problemaAuxDto.setLinkPesquisaSatisfacao("<a href=\"" + urlSistema + "/pages/pesquisaSatisfacao/pesquisaSatisfacao.load?idSolicitacaoServico=" + problemaAuxDto.getIdProblema() + "&hash="
				+ idHashValidacao + "\">Clique aqui para fazer a avaliação do Atendimento</a>");

		MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] { problemaAuxDto });
		try {
			mensagem.envia(problemaAuxDto.getEmailContato(), remetente, remetente);
		} catch (Exception e) {
		}

	}

}
