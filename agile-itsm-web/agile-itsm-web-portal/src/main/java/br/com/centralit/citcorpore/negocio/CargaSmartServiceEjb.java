/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargaSmartDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.UnidadeDao;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Cleon Xavier
 *
 */
public class CargaSmartServiceEjb extends CrudServiceImpl implements CargaSmartService {

    @Override
    protected CrudDAO getDao() {
        return null;
    }

    @Override
    public List<CargaSmartDTO> gerarCarga(final File carga, final Integer idEmpresa) throws Exception {
        final EmpregadoDao empregadoDao = new EmpregadoDao();
        final UnidadeDao unidadeDao = new UnidadeDao();

        final TransactionControler tc = new TransactionControlerImpl(empregadoDao.getAliasDB());
        try (BufferedReader arq = new BufferedReader(new InputStreamReader(new FileInputStream(carga), "WINDOWS-1252"))) {
            empregadoDao.setTransactionControler(tc);
            unidadeDao.setTransactionControler(tc);

            tc.start();

            boolean primeiraLinha = true;

            while (arq.ready()) {
                EmpregadoDTO empregadoDTO = new EmpregadoDTO();
                UnidadeDTO unidadeDTO = new UnidadeDTO();
                UnidadeDTO unidadeFilhaDTO = new UnidadeDTO();
                final String linhaAux = arq.readLine();
                String linha = null;
                if (linhaAux != null) {
                    linha = new String(linhaAux);
                    linha = linha.trim();
                }
                EmpregadoDTO beanEmpregadoDTO = null;

                String[] linhaQuebrada = null;
                if (linha != null) {
                    linhaQuebrada = linha.split("\n");
                }
                if (linhaQuebrada != null && linhaQuebrada.length > 0) {
                    for (final String string : linhaQuebrada) {
                        final String[] colunasArray = string.split(";");
                        if (colunasArray.length > 0) {
                            if (primeiraLinha) {
                                primeiraLinha = false;
                                break;
                            }
                            int j = 0;
                            for (final String coluna : colunasArray) {
                                UnidadeDTO beanUnidadeDTO = null;
                                if (coluna.equals("")) {
                                    j++;
                                } else {
                                    if (j == 0) {
                                        beanUnidadeDTO = new UnidadeDTO();
                                        beanUnidadeDTO = existeUnidadePorNome(coluna, unidadeDao);
                                        if (beanUnidadeDTO.getIdUnidade() == null) {
                                            beanUnidadeDTO.setIdEmpresa(idEmpresa);
                                            beanUnidadeDTO.setIdUnidade(unidadeDTO.getIdUnidade());
                                            beanUnidadeDTO.setNome(coluna);
                                            beanUnidadeDTO.setDataInicio(Util.getSqlDataAtual());
                                            unidadeDTO = criaUnidadeDTO(beanUnidadeDTO, unidadeDao);
                                        } else {
                                            unidadeDTO = beanUnidadeDTO;
                                        }
                                        j++;
                                    }

                                    else if (j == 1) {
                                        beanUnidadeDTO = new UnidadeDTO();
                                        beanUnidadeDTO = existeUnidadePorNome(coluna, unidadeDao);
                                        if (beanUnidadeDTO.getIdUnidade() == null) {
                                            beanUnidadeDTO.setIdEmpresa(idEmpresa);
                                            beanUnidadeDTO.setNome(coluna);
                                            if (unidadeDTO.getIdUnidade() != null) {
                                                beanUnidadeDTO.setIdUnidadePai(unidadeDTO.getIdUnidade());
                                            }
                                            beanUnidadeDTO.setDataInicio(Util.getSqlDataAtual());
                                            unidadeFilhaDTO = criaUnidadeDTO(beanUnidadeDTO, unidadeDao);

                                        } else {
                                            if (unidadeDTO.getIdUnidade() != null) {
                                                beanUnidadeDTO.setIdUnidadePai(unidadeDTO.getIdUnidade());
                                            }
                                            beanUnidadeDTO.setDataInicio(Util.getSqlDataAtual());
                                            unidadeFilhaDTO = beanUnidadeDTO;
                                            unidadeDao.update(unidadeFilhaDTO);
                                        }
                                        j++;
                                    } else if (j == 3) {
                                        beanEmpregadoDTO = new EmpregadoDTO();
                                        beanEmpregadoDTO = existeEmpregadoPorNome(coluna, empregadoDao);
                                        if (beanEmpregadoDTO.getIdEmpregado() == null) {
                                            beanEmpregadoDTO.setNome(coluna);
                                            beanEmpregadoDTO.setNomeProcura(coluna);
                                            beanEmpregadoDTO.setIdUnidade(unidadeFilhaDTO.getIdUnidade());
                                            beanEmpregadoDTO.setIdSituacaoFuncional(1);
                                        } else {
                                            beanEmpregadoDTO.setIdUnidade(unidadeFilhaDTO.getIdUnidade());
                                            empregadoDTO = beanEmpregadoDTO;
                                        }
                                        j++;
                                    }

                                    else if (j == 4) {
                                        if (coluna.equalsIgnoreCase("outros")) {
                                            empregadoDTO.setTipo("O");
                                        } else if (coluna.equalsIgnoreCase("Contrato Empresa - PJ")) {
                                            empregadoDTO.setTipo("C");
                                        } else if (coluna.equalsIgnoreCase("Empregado CLT")) {
                                            empregadoDTO.setTipo("E");
                                        } else if (coluna.equalsIgnoreCase("Estágio")) {
                                            empregadoDTO.setTipo("T");
                                        } else if (coluna.equalsIgnoreCase("FreeLancer")) {
                                            empregadoDTO.setTipo("F");
                                        } else if (coluna.equalsIgnoreCase("Sócio")) {
                                            empregadoDTO.setTipo("X");
                                        } else if (coluna.equalsIgnoreCase("Solicitante")) {
                                            empregadoDTO.setTipo("S");
                                        }
                                        j++;

                                    }

                                    else if (j == 5) {
                                        if (coluna.equalsIgnoreCase("Ativo")) {
                                            empregadoDTO.setIdSituacaoFuncional(1);
                                        }

                                        else if (coluna.equalsIgnoreCase("Inativo")) {
                                            empregadoDTO.setIdSituacaoFuncional(2);
                                        }
                                        j++;
                                    }

                                    else if (j == 6) {
                                        empregadoDTO.setEmail(coluna);
                                        j++;
                                    }

                                    else if (j == 7) {
                                        empregadoDTO.setTelefone(coluna);
                                        if (empregadoDTO.getIdEmpregado() != null) {
                                            if (empregadoDTO.getIdCargo() != null
                                                    && empregadoDTO.getIdUnidade() != null) {
                                                empregadoDao.update(empregadoDTO);
                                            }
                                        }
                                        j++;
                                    }
                                }
                            }
                        }
                    }
                }

            }
            tc.commit();

        } catch (final PersistenceException e) {
            rollbackTransaction(tc, e);
            e.printStackTrace();
        } finally {
            tc.closeQuietly();
        }
        return null;
    }

    private EmpregadoDTO criaEmpregadoDTO(final EmpregadoDTO empregadoDTO, final EmpregadoDao dao) throws Exception {
        return (EmpregadoDTO) dao.create(empregadoDTO);
    }

    private EmpregadoDTO existeEmpregadoPorNome(final String coluna, final EmpregadoDao empregadoDao) throws Exception {
        final EmpregadoDTO empregadoDTO = new EmpregadoDTO();
        empregadoDTO.setNome(coluna);
        final List<EmpregadoDTO> listEmpregado = (List<EmpregadoDTO>) empregadoDao.findByNomeEmpregado(empregadoDTO);
        if (listEmpregado != null) {
            return listEmpregado.get(0);
        }

        return empregadoDTO;
    }

    private UnidadeDTO existeUnidadePorNome(final String coluna, final UnidadeDao unidadeDao) throws Exception {
        final UnidadeDTO unidadeDTO = new UnidadeDTO();
        unidadeDTO.setNome(coluna);
        final List<UnidadeDTO> listUnidade = (List<UnidadeDTO>) unidadeDao.findByNomeUnidade(unidadeDTO);

        if (listUnidade != null) {
            return listUnidade.get(0);
        }

        return unidadeDTO;
    }

    private UnidadeDTO criaUnidadeDTO(final UnidadeDTO unidadeDTO, final UnidadeDao dao) throws Exception {
        return (UnidadeDTO) dao.create(unidadeDTO);

    }

}
