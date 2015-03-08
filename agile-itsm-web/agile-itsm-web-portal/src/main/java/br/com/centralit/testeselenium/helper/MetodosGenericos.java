package br.com.centralit.testeselenium.helper;

/**
 *
 * @author Central IT - Luiz Augusto da Silva Prado
 *
 */
public interface MetodosGenericos {

    String testarQuantidadeCaracteres() throws Exception;

    String getNomeDoBotaoDePesquisa();

    String getNomeDoBotaoDeSalvar();

    String getNomeDoBotaoDeExcluir();

    String getNomeDoBotaoDeLimpar();

    String getNomeDoBotaoDeLimparPesquisa();

    String getNomeDoCampoDePesquisa();

    String getNomeDaAbaDePesquisa();

    String getXpathDoPrimeiroItemDaListaDePesquisa();

    String REGISTRO_ALTERADO_SUCESSO = "Registro alterado com sucesso";

    String REGISTRO_EXCLUIDO_SUCESSO = "Registro excluído com sucesso";

    String REGISTRO_INSERIDO_SUCESSO = "Registro inserido com sucesso";

    String PREENCHA_TODOS_CAMPOS_OBRIGATORIOS = "Preencha todos os campos obrigatórios";

}
