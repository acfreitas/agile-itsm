package br.com.centralit.testeselenium.suitedeteste;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.centralit.testeselenium.gestaocontratos.FormulaOSSelenium;
import br.com.centralit.testeselenium.gestaocontratos.FormulaSelenium;

@RunWith(Suite.class)
@SuiteClasses({FormulaOSSelenium.class, FormulaSelenium.class})
public class SuiteDeTestesGerenciaGestaoContratos
{

}