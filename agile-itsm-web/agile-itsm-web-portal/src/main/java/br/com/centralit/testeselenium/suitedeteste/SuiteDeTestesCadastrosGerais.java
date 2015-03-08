package br.com.centralit.testeselenium.suitedeteste;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.centralit.testeselenium.cadastrosgerais.CargosSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.ClientesSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.ColaboradorSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.EmpresaSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.FornecedorSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.UnidadeSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.UsuarioSelenium;

@RunWith(Suite.class)
@SuiteClasses({CargosSelenium.class, ClientesSelenium.class, ColaboradorSelenium.class, EmpresaSelenium.class, FornecedorSelenium.class, UnidadeSelenium.class, UsuarioSelenium.class})
public class SuiteDeTestesCadastrosGerais
{

}