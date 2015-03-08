package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.ImagemItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ImagemItemConfiguracaoRelacaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.integracao.ImagemItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.ImagemItemConfiguracaoRelacaoDao;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 *
 * @author breno.guimaraes
 *
 */
public class ImagemItemConfiguracaoServiceEjb extends CrudServiceImpl implements ImagemItemConfiguracaoService {

    private ImagemItemConfiguracaoDao dao;

    @Override
    protected ImagemItemConfiguracaoDao getDao() {
        if (dao == null) {
            dao = new ImagemItemConfiguracaoDao();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model) throws LogicException, ServiceException {
        final ImagemItemConfiguracaoDTO imgDto = (ImagemItemConfiguracaoDTO) model;
        System.out.println("Item não existe. Create.");
        return super.create(imgDto);
    }

    @Override
    public IDto restore(final IDto model) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public void update(final IDto model) throws LogicException, ServiceException {
        final ImagemItemConfiguracaoDTO imgDto = (ImagemItemConfiguracaoDTO) model;
        System.out.println("Item já existe. Update.");
        super.update(imgDto);
    }

    @Override
    public void delete(final IDto model) throws LogicException, ServiceException {
        final ImagemItemConfiguracaoDTO imagem = (ImagemItemConfiguracaoDTO) model;
        this.getDao().excluiIdPaiDeItensFilho(imagem.getIdImagemItemConfiguracao());
        super.delete(model);
    }

    @Override
    public Collection findByIdServico(final int idServico) throws LogicException, ServiceException {
        return this.getDao().findByIdServico(idServico);
    }

    @Override
    public Collection findByIdImagemItemConfiguracaoPai(final int id) throws LogicException, ServiceException {
        return this.getDao().findByIdImagemItemConfiguracaoPai(id);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Collection findItensRelacionadosHierarquia(final Integer idItemCfg) throws Exception {
        final ImagemItemConfiguracaoDao imagemItemConfiguracaoDao = new ImagemItemConfiguracaoDao();
        final ImagemItemConfiguracaoRelacaoDao imagemItemConfiguracaoRel = new ImagemItemConfiguracaoRelacaoDao();
        final ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
        final Collection<ItemConfiguracaoDTO> colRetorno = new ArrayList<ItemConfiguracaoDTO>();

        Collection<ImagemItemConfiguracaoDTO> col = imagemItemConfiguracaoDao.findByIdItemConfiguracao(idItemCfg);

        if (col != null) {
            for (final Object element : col) {
                final ImagemItemConfiguracaoDTO imagemItemConfiguracaoDTO = (ImagemItemConfiguracaoDTO) element;
                ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
                itemConfiguracaoDTO.setIdItemConfiguracao(imagemItemConfiguracaoDTO.getIdItemConfiguracao());
                itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoDao.restore(itemConfiguracaoDTO);
                if (itemConfiguracaoDTO != null) {
                    itemConfiguracaoDTO.setTipoVinculo("FILHO");
                    colRetorno.add(itemConfiguracaoDTO);
                }
                final Collection colRel = imagemItemConfiguracaoDao.findByIdImagemItemConfiguracaoPai(imagemItemConfiguracaoDTO.getIdImagemItemConfiguracao());
                if (colRel != null) {
                    for (final Iterator it2 = colRel.iterator(); it2.hasNext();) {
                        final ImagemItemConfiguracaoDTO imagemItemConfiguracaoAux = (ImagemItemConfiguracaoDTO) it2.next();
                        ItemConfiguracaoDTO itemConfiguracaoAux = new ItemConfiguracaoDTO();
                        itemConfiguracaoAux.setIdItemConfiguracao(imagemItemConfiguracaoAux.getIdItemConfiguracao());
                        itemConfiguracaoAux = (ItemConfiguracaoDTO) itemConfiguracaoDao.restore(itemConfiguracaoAux);
                        if (itemConfiguracaoAux != null) {
                            itemConfiguracaoAux.setTipoVinculo("VINC");
                            colRetorno.add(itemConfiguracaoAux);
                        }
                    }
                }
                final Collection<ImagemItemConfiguracaoRelacaoDTO> colRelImagemItemConfiguracaoPai = imagemItemConfiguracaoRel
                        .findByIdImagemItemConfiguracaoPai(imagemItemConfiguracaoDTO.getIdImagemItemConfiguracao());

                if (colRelImagemItemConfiguracaoPai != null) {
                    for (final ImagemItemConfiguracaoRelacaoDTO imgItemConfiguracaoRel : colRelImagemItemConfiguracaoPai) {
                        ImagemItemConfiguracaoDTO imagemItemConfiguracaoAux = new ImagemItemConfiguracaoDTO();
                        imagemItemConfiguracaoAux.setIdImagemItemConfiguracao(imgItemConfiguracaoRel.getIdImagemItemConfiguracao());
                        imagemItemConfiguracaoAux = (ImagemItemConfiguracaoDTO) imagemItemConfiguracaoDao.restore(imagemItemConfiguracaoAux);
                        if (imagemItemConfiguracaoAux != null && imagemItemConfiguracaoAux.getIdItemConfiguracao() != null) {
                            ItemConfiguracaoDTO itemConfiguracaoAux = new ItemConfiguracaoDTO();
                            itemConfiguracaoAux.setIdItemConfiguracao(imagemItemConfiguracaoAux.getIdItemConfiguracao());
                            itemConfiguracaoAux = (ItemConfiguracaoDTO) itemConfiguracaoDao.restore(itemConfiguracaoAux);
                            if (itemConfiguracaoAux != null) {
                                itemConfiguracaoAux.setTipoVinculo("VINC");
                                colRetorno.add(itemConfiguracaoAux);
                            }
                        }
                    }
                }
            }
        }
        col = itemConfiguracaoDao.findByIdItemConfiguracaoPai(idItemCfg);
        if (col != null) {
            for (final Object element : col) {
                final ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO) element;
                if (itemConfiguracaoDTO != null) {
                    itemConfiguracaoDTO.setTipoVinculo("FILHO");
                    colRetorno.add(itemConfiguracaoDTO);
                }
                Collection colItensVinc = null;
                if (itemConfiguracaoDTO != null) {
                    colItensVinc = this.findItensRelacionadosHierarquia(itemConfiguracaoDTO.getIdItemConfiguracao());
                }
                if (colItensVinc != null) {
                    colRetorno.addAll(colItensVinc);
                }
            }
        }
        return colRetorno;
    }

    @Override
    public Collection findServicosRelacionadosHierarquia(final Integer idItemCfg) throws Exception {
        final ImagemItemConfiguracaoDao imagemItemConfiguracaoDao = new ImagemItemConfiguracaoDao();
        final ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
        final ImagemItemConfiguracaoRelacaoDao imagemItemConfiguracaoRel = new ImagemItemConfiguracaoRelacaoDao();
        final ServicoDao servicoDao = new ServicoDao();
        Collection<ImagemItemConfiguracaoDTO> col = imagemItemConfiguracaoDao.findByIdItemConfiguracao(idItemCfg);
        final Collection<ServicoDTO> colRetorno = new ArrayList<ServicoDTO>();
        if (col != null) {
            for (final Object element : col) {
                final ImagemItemConfiguracaoDTO imagemItemConfiguracaoDTO = (ImagemItemConfiguracaoDTO) element;
                ServicoDTO servicoDTO = new ServicoDTO();
                servicoDTO.setIdServico(imagemItemConfiguracaoDTO.getIdServico());
                servicoDTO = (ServicoDTO) servicoDao.restore(servicoDTO);
                if (servicoDTO != null) {
                    colRetorno.add(servicoDTO);
                }
                final Collection colRel = imagemItemConfiguracaoDao.findByIdImagemItemConfiguracaoPai(imagemItemConfiguracaoDTO.getIdImagemItemConfiguracao());
                if (colRel != null) {
                    for (final Iterator it2 = colRel.iterator(); it2.hasNext();) {
                        final ImagemItemConfiguracaoDTO imagemItemConfiguracaoAux = (ImagemItemConfiguracaoDTO) it2.next();
                        servicoDTO = new ServicoDTO();
                        servicoDTO.setIdServico(imagemItemConfiguracaoAux.getIdServico());
                        servicoDTO = (ServicoDTO) servicoDao.restore(servicoDTO);
                        if (servicoDTO != null) {
                            colRetorno.add(servicoDTO);
                        }
                    }
                }
                final Collection<ImagemItemConfiguracaoRelacaoDTO> colRelImagemItemConfiguracaoPai = imagemItemConfiguracaoRel
                        .findByIdImagemItemConfiguracaoPai(imagemItemConfiguracaoDTO.getIdImagemItemConfiguracao());

                if (colRelImagemItemConfiguracaoPai != null) {
                    for (final ImagemItemConfiguracaoRelacaoDTO imgItemConfiguracaoRel : colRelImagemItemConfiguracaoPai) {
                        ImagemItemConfiguracaoDTO imagemItemConfiguracaoAux = new ImagemItemConfiguracaoDTO();
                        imagemItemConfiguracaoAux.setIdImagemItemConfiguracao(imgItemConfiguracaoRel.getIdImagemItemConfiguracao());
                        imagemItemConfiguracaoAux = (ImagemItemConfiguracaoDTO) imagemItemConfiguracaoDao.restore(imagemItemConfiguracaoAux);
                        if (imagemItemConfiguracaoAux != null && imagemItemConfiguracaoAux.getIdServico() != null) {
                            servicoDTO = new ServicoDTO();
                            servicoDTO.setIdServico(imagemItemConfiguracaoAux.getIdServico());
                            servicoDTO = (ServicoDTO) servicoDao.restore(servicoDTO);
                            if (servicoDTO != null) {
                                colRetorno.add(servicoDTO);
                            }
                        }
                    }
                }

            }
        }
        col = itemConfiguracaoDao.findByIdItemConfiguracaoPai(idItemCfg);
        if (col != null) {
            for (final Object element : col) {
                final ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO) element;
                final Collection colItensVinc = this.findServicosRelacionadosHierarquia(itemConfiguracaoDTO.getIdItemConfiguracao());
                if (colItensVinc != null) {
                    colRetorno.addAll(colItensVinc);
                }
            }
        }
        return colRetorno;
    }

}
