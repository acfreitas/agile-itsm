package br.com.centralit.testeselenium.suitedeteste;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.centralit.testeselenium.gerenciaincidentes.OrigemSolicitacaoSelenium;

@RunWith(Suite.class)
@SuiteClasses({OrigemSolicitacaoSelenium.class})
public class SuiteDeTestesGerenciaDeIncidentes
{

}