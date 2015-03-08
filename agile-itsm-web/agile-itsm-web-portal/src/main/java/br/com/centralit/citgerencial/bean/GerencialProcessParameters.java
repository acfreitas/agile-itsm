package br.com.centralit.citgerencial.bean;

import java.util.Collection;
import java.util.HashMap;

public abstract class GerencialProcessParameters {
	public abstract String processParameters(HashMap hsmParms, Collection colParmsUtilizadosNoSQL, Collection colDefinicaoParametros);
}
