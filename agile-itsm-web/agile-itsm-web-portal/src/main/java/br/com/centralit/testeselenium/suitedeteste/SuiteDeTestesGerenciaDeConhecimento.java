package br.com.centralit.testeselenium.suitedeteste;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.centralit.testeselenium.gerenciadeconhecimentos.CategoriaGaleriaImagensSelenium;
import br.com.centralit.testeselenium.gerenciadeconhecimentos.PalavraGemeaSelenium;

@RunWith(Suite.class)
@SuiteClasses({PalavraGemeaSelenium.class, CategoriaGaleriaImagensSelenium.class})
public class SuiteDeTestesGerenciaDeConhecimento
{

}