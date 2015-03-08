package br.com.centralit.citcorpore.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class UtilClasses {
    /**
     * Retorna os nomes de classes de um determinadao pacote
     *
     * @param packageName
     * @return
     * @throws IOException
     */
    public static ArrayList<String> getClassNamesFromPackage(String packageName) throws IOException {
	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	URL packageURL;
	ArrayList<String> names = new ArrayList<String>();
	;

	packageName = packageName.replace(".", "/");
	packageURL = classLoader.getResource(packageName);

	if (packageURL.getProtocol().equals("jar")) {
	    String jarFileName;
	    JarFile jf;
	    Enumeration<JarEntry> jarEntries;
	    String entryName;

	    // build jar file name, then loop through zipped entries
	    jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
	    jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
	    System.out.println(">" + jarFileName);
	    jf = new JarFile(jarFileName);
	    jarEntries = jf.entries();
	    while (jarEntries.hasMoreElements()) {
		entryName = jarEntries.nextElement().getName();
		if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
		    entryName = entryName.substring(packageName.length(), entryName.lastIndexOf('.'));
		    names.add(entryName);
		}
	    }
	    jf.close();
	    // loop through files in classpath
	} else {
	    File folder = new File(packageURL.getFile());
	    File[] contenuti = folder.listFiles();
	    String entryName;
	    for (File actual : contenuti) {
		entryName = actual.getName();
		entryName = entryName.substring(0, entryName.lastIndexOf('.'));
		names.add(entryName);
	    }
	}
	return names;
    }

    /**
     * Verifica se uma classe implementa uma interface (mesmo na hierarquia - heranças)
     *
     * @param classeAnalisar
     * @param nomeCompletoInterfaceComPacote
     * @return
     */
    public static boolean isClassImplInterface(Class classeAnalisar, String nomeCompletoInterfaceComPacote) {
	if (classeAnalisar == null) {
	    return false;
	}
	Class[] interfaces = classeAnalisar.getInterfaces();
	if (interfaces != null) {
	    for (int i = 0; i < interfaces.length; i++) {
		Class interfaceAux = interfaces[i];
		if (interfaceAux.getName().equalsIgnoreCase(nomeCompletoInterfaceComPacote)) {
		    return true;
		} else {
		    // Se nao for, verifica na hierarquia desta se possui.
		    boolean bOk = isClassImplInterface(interfaceAux, nomeCompletoInterfaceComPacote);
		    if (bOk) {
			return true;
		    }
		}
	    }
	}
	if (classeAnalisar.getSuperclass() != null) {
	    boolean bOk = isClassImplInterface(classeAnalisar.getSuperclass(), nomeCompletoInterfaceComPacote);
	    if (bOk) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Retorna a lista de Classes de um determinado pacote que implementam determinada interface.
     *
     * @param namePackage
     * @param interfaceNameImpl
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     *
     *             Exemplo: getClassesFromPackageImplInterface("br.com.centralit.citcorpore.bean", "br.com.citframework.dto.IDto")
     */
    public static ArrayList<Class> getClassesFromPackageImplInterface(String namePackage, String interfaceNameImpl) throws IOException, ClassNotFoundException {
	ArrayList<String> list = UtilClasses.getClassNamesFromPackage(namePackage);
	ArrayList<Class> listReturn = null;
	for (String className : list) {
	    Class classe = Class.forName(namePackage + "." + className);
	    boolean bOk = UtilClasses.isClassImplInterface(classe, interfaceNameImpl);
	    if (bOk) {
		if (listReturn == null) {
		    listReturn = new ArrayList<Class>();
		}
		listReturn.add(classe);
	    }
	}
	return listReturn;
    }
}
