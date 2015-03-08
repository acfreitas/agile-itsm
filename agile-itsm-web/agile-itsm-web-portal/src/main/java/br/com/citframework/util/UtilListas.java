package br.com.citframework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.excecao.CompareException;

public class UtilListas {
	/**
	 * Ordena uma lista de beans (nao importa o tipo), atraves de uma propriedade (get).
	 * @param lst
	 * @param getProperty (exemplo: "getDataInicio") - Pode ser data, Numero, String, ...
	 * @param typeOrder ("ASC" ou "DESC") - pode ser utilizado: ObjectSimpleComparator.ASC, ObjectSimpleComparator.DESC
	 * @return A colecao ordenada pela propriedade indicada
	 * @throws CompareException
	 */
	public static Collection sortListOfBensByProperty(List lst, String getProperty, String typeOrder) throws CompareException{
		if (lst == null) return new ArrayList();
		Collections.sort(lst, new ObjectSimpleComparator(getProperty, typeOrder));
		return lst;
	}
}
