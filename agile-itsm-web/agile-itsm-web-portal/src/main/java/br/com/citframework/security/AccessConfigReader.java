package br.com.citframework.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class AccessConfigReader {

	private static final Logger LOGGER = Logger.getLogger(AccessConfigReader.class);
	private static final String VIRGULA = ",";
	private static final List PUBLIC_ACCESS;

	static {
		List publicAccess = new ArrayList(1);
		publicAccess.add(Access.PUBLIC);
		PUBLIC_ACCESS = Collections.unmodifiableList(publicAccess);
	}

	public static HashMap read(InputStream xmlStream, boolean publicAccess) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		LOGGER.debug(">>>>>>Iniciando processamento do XML...");
		Document document = builder.parse(xmlStream);
		LOGGER.debug(">>>>>>Parse ok!...");
		HashMap resultMap = new HashMap();
		LOGGER.debug(">>>>>>Processando mapeamentos...");
		fillMap(resultMap, document, publicAccess);
		LOGGER.debug(">>>>>>Mapeamentos processados!...");
		return resultMap;
	}

	private static void fillMap(HashMap accessMap, Document document, boolean publicAccess) {
		Node accessConfigNode = document.getChildNodes().item(0);
		for (int i = 0; i < accessConfigNode.getChildNodes().getLength(); i++) {
			Node accessNode = accessConfigNode.getChildNodes().item(i);
			if (accessNode.getNodeName().equals("#text"))
				continue;
			if (accessNode.getNodeName().equals("#comment"))
				continue;
			ArrayList transactions = new ArrayList();
			for (int j = 0; j < accessNode.getChildNodes().getLength(); j++) {
				Node accessNodeItem = accessNode.getChildNodes().item(j);
				if (accessNodeItem.getNodeName().equals("#text"))
					continue;
				if (accessNodeItem.getNodeName().equals("#comment"))
					continue;
				if (accessNodeItem.getChildNodes().item(0) == null)
					continue;
				String path = accessNodeItem.getChildNodes().item(0).getNodeValue();
				if (accessNodeItem.getNodeName().equalsIgnoreCase("path")) {
					Access access = new Access();
					access.setPath(path);
					access.setAccessingTransactionList(transactions);
					accessMap.put(path, access);
				} else if (accessNodeItem.getNodeName().equalsIgnoreCase("accessing-transaction")) {
					if (publicAccess) {
						transactions.addAll(PUBLIC_ACCESS);
					} else {
						transactions.addAll(getAccessingTransactionList(path));
					}
				}
			}
		}
	}

	private static ArrayList getAccessingTransactionList(String accessingTransaction) {
		StringTokenizer tokenizer = new StringTokenizer(StringUtils.trimToEmpty(accessingTransaction), VIRGULA);
		ArrayList results = new ArrayList(tokenizer.countTokens());
		while (tokenizer.hasMoreTokens()) {
			String grupos = tokenizer.nextToken().toUpperCase();
			LOGGER.debug("Grupo(s) de acesso metodo getAccessingTransactionList = " + grupos);
			results.add(grupos);
		}
		return results;
	}

}
