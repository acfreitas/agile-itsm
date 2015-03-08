package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.rh.bean.AdmissaoEmpregadoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.integracao.EntrevistaCandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.MovimentacaoPessoalDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Reflexao;

public class AdmissaoEmpregadoServiceEjb extends CrudServiceImpl implements AdmissaoEmpregadoService {

    private MovimentacaoPessoalDao dao;

    @Override
    protected MovimentacaoPessoalDao getDao() {
        if (dao == null) {
            dao = new MovimentacaoPessoalDao();
        }
        return dao;
    }

    @Override
    public AdmissaoEmpregadoDTO calcularCustos(final AdmissaoEmpregadoDTO admissaoEmpregado) throws ServiceException, LogicException {
        double valorCustoTotal = 0;
        double valorSalario = 0;
        double custoHora = 0;
        if (admissaoEmpregado.getValorSalario() == null) {
            admissaoEmpregado.setValorSalario(new Double(0));
        }
        valorSalario = admissaoEmpregado.getValorSalario().doubleValue();
        if (admissaoEmpregado.getTipo().equalsIgnoreCase("E")) { // CLT
            valorCustoTotal = valorSalario * 1.78; // 78% de encargos
        } else if (admissaoEmpregado.getTipo().equalsIgnoreCase("P")) {// PJ
            valorCustoTotal = valorSalario * 1.1; // 10% de encargos
        } else if (admissaoEmpregado.getTipo().equalsIgnoreCase("F")) {// Free Lancer
            valorCustoTotal = valorSalario;
        } else if (admissaoEmpregado.getTipo().equalsIgnoreCase("S")) {// Estagio
            valorCustoTotal = valorSalario;
        } else if (admissaoEmpregado.getTipo().equalsIgnoreCase("X")) {// Socio
            valorCustoTotal = valorSalario;
        }
        // Acrescenta 25% de encargos na produtividade.
        if (admissaoEmpregado.getValorProdutividadeMedia() == null) {
            admissaoEmpregado.setValorProdutividadeMedia(new Double(0));
        }
        if (admissaoEmpregado.getValorPlanoSaudeMedia() == null) {
            admissaoEmpregado.setValorPlanoSaudeMedia(new Double(0));
        }
        if (admissaoEmpregado.getValorVRefMedia() == null) {
            admissaoEmpregado.setValorVRefMedia(new Double(0));
        }
        if (admissaoEmpregado.getValorVTraMedia() == null) {
            admissaoEmpregado.setValorVTraMedia(new Double(0));
        }
        valorCustoTotal = valorCustoTotal + admissaoEmpregado.getValorProdutividadeMedia().doubleValue() * 1.25;
        valorCustoTotal = valorCustoTotal + admissaoEmpregado.getValorPlanoSaudeMedia().doubleValue();
        valorCustoTotal = valorCustoTotal + admissaoEmpregado.getValorVRefMedia().doubleValue();
        valorCustoTotal = valorCustoTotal + admissaoEmpregado.getValorVTraMedia().doubleValue();
        custoHora = valorCustoTotal / 168;
        admissaoEmpregado.setCustoPorHora(new Double(custoHora));
        admissaoEmpregado.setCustoTotalMes(new Double(valorCustoTotal));
        return admissaoEmpregado;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final CrudDAO crudDao = this.getDao();

        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

        final EmpregadoDao empregadoDao = new EmpregadoDao();
        final EntrevistaCandidatoDao entrevistaCandidatoDao = new EntrevistaCandidatoDao();
        final AdmissaoEmpregadoDTO admissaoEmpregadoDto = (AdmissaoEmpregadoDTO) model;

        try {
            this.validaCreate(model);

            crudDao.setTransactionControler(tc);
            empregadoDao.setTransactionControler(tc);
            entrevistaCandidatoDao.setTransactionControler(tc);

            tc.start();

            EntrevistaCandidatoDTO entrevistaCandidatoDto = new EntrevistaCandidatoDTO();

            entrevistaCandidatoDto.setIdEntrevista(admissaoEmpregadoDto.getIdEntrevista());
            entrevistaCandidatoDto = (EntrevistaCandidatoDTO) entrevistaCandidatoDao.restore(entrevistaCandidatoDto);

            EmpregadoDTO empregadoDto = new EmpregadoDTO();

            Reflexao.copyPropertyValues(admissaoEmpregadoDto, empregadoDto);
            empregadoDto.setIdCurriculo(entrevistaCandidatoDto.getIdCurriculo());
            empregadoDto = (EmpregadoDTO) empregadoDao.create(empregadoDto);

            admissaoEmpregadoDto.setIdEmpregado(empregadoDto.getIdEmpregado());
            admissaoEmpregadoDto.setTipoMovimentacao("A");
            crudDao.create(admissaoEmpregadoDto);

            entrevistaCandidatoDto.setResultado("C");

            entrevistaCandidatoDao.update(entrevistaCandidatoDto);

            tc.commit();
            tc.close();

            // JUNTAR COM O CREATE EM MovimentacaoColaborador

            return model;
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

}
