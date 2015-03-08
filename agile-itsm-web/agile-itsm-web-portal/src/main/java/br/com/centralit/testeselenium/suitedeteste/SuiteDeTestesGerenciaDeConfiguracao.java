package br.com.centralit.testeselenium.suitedeteste;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.centralit.testeselenium.gerenciadeconfiguracoes.CaracteristicaSelenium;
import br.com.centralit.testeselenium.gerenciadeconfiguracoes.GrupoItemConfiguracaoSelenium;
import br.com.centralit.testeselenium.gerenciadeconfiguracoes.MidiaSoftwareSelenium;
import br.com.centralit.testeselenium.gerenciadeconfiguracoes.SoftwareListaNegraSelenium;
import br.com.centralit.testeselenium.gerenciadeconfiguracoes.TipoItemConfiguracaoSelenium;

@RunWith(Suite.class)
@SuiteClasses({CaracteristicaSelenium.class, GrupoItemConfiguracaoSelenium.class, MidiaSoftwareSelenium.class, SoftwareListaNegraSelenium.class, TipoItemConfiguracaoSelenium.class})
public class SuiteDeTestesGerenciaDeConfiguracao
{

}