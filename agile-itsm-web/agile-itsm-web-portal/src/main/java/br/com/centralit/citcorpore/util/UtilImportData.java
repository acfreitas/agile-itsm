package br.com.centralit.citcorpore.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import br.com.citframework.util.UtilStrings;

public class UtilImportData {
	public static List readXMLFile(String pathFile) {
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(pathFile));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Node node = null;
		Node nodeTable = null;
		NodeList nodes = null;
		String origem;
		String filter;
		String tableName;
		String tableType;
		String key;
		String sequence;
		String check;
		String exclusion;
		String type;
		String nameField;
		NamedNodeMap mapX;
		NamedNodeMap map;
		Node nodeRecord;
		ImportInfoRecord importInfoRecord;
		Node nodeField;
		NamedNodeMap mapField;
		ImportInfoField importInfoField;

		if (doc != null) {
			nodes = doc.getChildNodes();
		}
		List lstRecords = new ArrayList();
		if (nodes != null) {
			for (int i = 0; i < nodes.getLength(); i++) {
				nodeTable = nodes.item(i);
				if (nodeTable.getNodeName().equalsIgnoreCase("tables")) {
					mapX = nodeTable.getAttributes();

					if (mapX.getNamedItem("origem") == null) {
						origem = "";
					} else {
						origem = mapX.getNamedItem("origem").getNodeValue();
					}

					if (mapX.getNamedItem("filter") == null) {
						filter = "";
					} else {
						filter = mapX.getNamedItem("filter").getNodeValue();
					}

					for (int x = 0; x < nodeTable.getChildNodes().getLength(); x++) {
						node = nodeTable.getChildNodes().item(x);
						if (node.getNodeName().equalsIgnoreCase("table")) {
							map = node.getAttributes();

							if (map.getNamedItem("name") == null) {
								tableName = "";
							} else {
								tableName = map.getNamedItem("name").getNodeValue();
							}

							if (map.getNamedItem("type") == null) {
								tableType = "";
							} else {
								tableType = map.getNamedItem("type").getNodeValue();
							}

							if (node.getChildNodes() != null) {
								for (int j = 0; j < node.getChildNodes().getLength(); j++) {
									nodeRecord = node.getChildNodes().item(j);
									if (nodeRecord.getNodeName().equalsIgnoreCase("command") || nodeRecord.getNodeName().equalsIgnoreCase("commandDelete")) {
										continue;
									}
									if (nodeRecord.getChildNodes() != null && nodeRecord.getChildNodes().getLength() > 0) {
										importInfoRecord = new ImportInfoRecord();
										importInfoRecord.setTableName(tableName);
										importInfoRecord.setType(tableType);
										Collection colFields = new ArrayList();
										for (int z = 0; z < nodeRecord.getChildNodes().getLength(); z++) {
											nodeField = nodeRecord.getChildNodes().item(z);
											if (nodeField == null || nodeField.getNodeName() == null) {
												continue;
											}
											if (nodeField.getNodeName().equals("#text"))
												continue;
											if (nodeField.getNodeName().equalsIgnoreCase("command") || nodeField.getNodeName().equalsIgnoreCase("commandDelete")) {
												continue;
											}

											mapField = nodeField.getAttributes();

											if (mapField.getNamedItem("key") == null) {
												key = "";
											} else {
												key = mapField.getNamedItem("key").getNodeValue();
											}

											if (mapField.getNamedItem("sequence") == null) {
												sequence = "";
											} else {
												sequence = mapField.getNamedItem("sequence").getNodeValue();
											}

											if (mapField.getNamedItem("check") == null) {
												check = "";
											} else {
												check = mapField.getNamedItem("check").getNodeValue();
											}

											if (mapField.getNamedItem("exclusion") == null) {
												exclusion = "";
											} else {
												exclusion = mapField.getNamedItem("exclusion").getNodeValue();
											}

											if (mapField.getNamedItem("type") == null) {
												type = "";
											} else {
												type = mapField.getNamedItem("type").getNodeValue();
											}

											if (mapField.getNamedItem("name") == null) {
												nameField = "";
											} else {
												nameField = mapField.getNamedItem("name").getNodeValue();
											}

											importInfoField = new ImportInfoField();
											importInfoField.setNameField(nameField);
											importInfoField.setValueField(nodeField.getTextContent());

											if (UtilStrings.nullToVazio(key).equalsIgnoreCase("y")) {
												importInfoField.setKey(true);
											} else {
												importInfoField.setKey(false);
											}

											if (UtilStrings.nullToVazio(sequence).equalsIgnoreCase("y")) {
												importInfoField.setSequence(true);
											} else {
												importInfoField.setSequence(false);
											}

											if (UtilStrings.nullToVazio(check).equalsIgnoreCase("y")) {
												importInfoField.setCheck(true);
											} else {
												importInfoField.setCheck(false);
											}

											if (UtilStrings.nullToVazio(exclusion).equalsIgnoreCase("y")) {
												importInfoField.setExclusion(true);
											} else {
												importInfoField.setExclusion(false);
											}

											importInfoField.setType(type);
											colFields.add(importInfoField);
										}
										importInfoRecord.setColFields(colFields);
										importInfoRecord.setOrigem(origem);
										importInfoRecord.setFilter(filter);
										lstRecords.add(importInfoRecord);
									}
								}
							}
						}
					}
				}
			}
		}
		return lstRecords;
	}

	public static List readXMLSource(String xmlSource) {
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(xmlSource)));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Node node = null;
		Node nodeTable = null;
		NodeList nodes = null;
		String origem;
		String filter;
		String tableName;
		String tableType;
		String key;
		String sequence;
		String check;
		String exclusion;
		String type;
		String nameField;
		NamedNodeMap mapX;
		NamedNodeMap map;
		Node nodeRecord;
		ImportInfoRecord importInfoRecord;
		Node nodeField;
		NamedNodeMap mapField;
		ImportInfoField importInfoField;

		if (doc != null) {
			nodes = doc.getChildNodes();
		}
		List lstRecords = new ArrayList();
		if (nodes != null) {
			for (int i = 0; i < nodes.getLength(); i++) {
				nodeTable = nodes.item(i);
				if (nodeTable.getNodeName().equalsIgnoreCase("tables")) {
					mapX = nodeTable.getAttributes();

					if (mapX.getNamedItem("origem") == null) {
						origem = "";
					} else {
						origem = mapX.getNamedItem("origem").getNodeValue();
					}

					if (mapX.getNamedItem("filter") == null) {
						filter = "";
					} else {
						filter = mapX.getNamedItem("filter").getNodeValue();
					}

					for (int x = 0; x < nodeTable.getChildNodes().getLength(); x++) {
						node = nodeTable.getChildNodes().item(x);
						if (node.getNodeName().equalsIgnoreCase("table")) {
							map = node.getAttributes();

							if (map.getNamedItem("name") == null) {
								tableName = "";
							} else {
								tableName = map.getNamedItem("name").getNodeValue();
							}

							if (map.getNamedItem("type") == null) {
								tableType = "";
							} else {
								tableType = map.getNamedItem("type").getNodeValue();
							}

							if (node.getChildNodes() != null) {
								for (int j = 0; j < node.getChildNodes().getLength(); j++) {
									nodeRecord = node.getChildNodes().item(j);
									if (nodeRecord.getNodeName().equalsIgnoreCase("command") || nodeRecord.getNodeName().equalsIgnoreCase("commandDelete")) {
										continue;
									}
									if (nodeRecord.getChildNodes() != null && nodeRecord.getChildNodes().getLength() > 0) {
										importInfoRecord = new ImportInfoRecord();
										importInfoRecord.setTableName(tableName);
										importInfoRecord.setType(tableType);
										Collection colFields = new ArrayList();
										for (int z = 0; z < nodeRecord.getChildNodes().getLength(); z++) {
											nodeField = nodeRecord.getChildNodes().item(z);
											if (nodeField == null || nodeField.getNodeName() == null) {
												continue;
											}
											if (nodeField.getNodeName().equals("#text"))
												continue;
											if (nodeField.getNodeName().equalsIgnoreCase("command") || nodeField.getNodeName().equalsIgnoreCase("commandDelete")) {
												continue;
											}

											mapField = nodeField.getAttributes();

											if (mapField.getNamedItem("key") == null) {
												key = "";
											} else {
												key = mapField.getNamedItem("key").getNodeValue();
											}

											if (mapField.getNamedItem("sequence") == null) {
												sequence = "";
											} else {
												sequence = mapField.getNamedItem("sequence").getNodeValue();
											}

											if (mapField.getNamedItem("check") == null) {
												check = "";
											} else {
												check = mapField.getNamedItem("check").getNodeValue();
											}

											if (mapField.getNamedItem("exclusion") == null) {
												exclusion = "";
											} else {
												exclusion = mapField.getNamedItem("exclusion").getNodeValue();
											}

											if (mapField.getNamedItem("type") == null) {
												type = "";
											} else {
												type = mapField.getNamedItem("type").getNodeValue();
											}

											if (mapField.getNamedItem("name") == null) {
												nameField = "";
											} else {
												nameField = mapField.getNamedItem("name").getNodeValue();
											}

											importInfoField = new ImportInfoField();
											importInfoField.setNameField(nameField);
											importInfoField.setValueField(nodeField.getTextContent());

											if (UtilStrings.nullToVazio(key).equalsIgnoreCase("y")) {
												importInfoField.setKey(true);
											} else {
												importInfoField.setKey(false);
											}

											if (UtilStrings.nullToVazio(sequence).equalsIgnoreCase("y")) {
												importInfoField.setSequence(true);
											} else {
												importInfoField.setSequence(false);
											}

											if (UtilStrings.nullToVazio(check).equalsIgnoreCase("y")) {
												importInfoField.setCheck(true);
											} else {
												importInfoField.setCheck(false);
											}

											if (UtilStrings.nullToVazio(exclusion).equalsIgnoreCase("y")) {
												importInfoField.setExclusion(true);
											} else {
												importInfoField.setExclusion(false);
											}

											importInfoField.setType(type);
											colFields.add(importInfoField);
										}
										importInfoRecord.setColFields(colFields);
										importInfoRecord.setOrigem(origem);
										importInfoRecord.setFilter(filter);
										lstRecords.add(importInfoRecord);
									}
								}
							}
						}
					}
				}
			}
		}
		return lstRecords;
	}

}
