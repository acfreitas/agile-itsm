package br.com.citframework.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.html.HtmlParser;

public class Html2Pdf {   
	  
    public static void convert(String input, OutputStream out) throws DocumentException, UnsupportedEncodingException{   
        convert(new ByteArrayInputStream(input.getBytes("ISO-8859-1")), out);   
    }   
    
    /**
     * O campo style, define tamanhos da pagina e margens:
     * 				Exemplo: <style>@page {size: 4.18in 6.88in; margin: 30px 20px 15px 35px; size:landscape;}</style>
     * @param input
     * @param out
     * @param style
     * @throws DocumentException
     */
    public static void convert(String input, OutputStream out, String style) throws DocumentException{
    	String str = input;
    	if (str == null){
    		str = "";
    	}
    	str = style + str;
    	convert(new ByteArrayInputStream(str.getBytes()), out);   
    }   
       
    public static void convert(InputStream input, OutputStream out) throws DocumentException{   
        Tidy tidy = new Tidy();  
        Document doc = tidy.parseDOM(input, null);   
        ITextRenderer renderer = new ITextRenderer();  
        renderer.setDocument(doc, null);   
        renderer.layout();     
        renderer.createPDF(out);                   
    }   
    
    public static void convert(com.lowagie.text.Document document, String strNameFileInDisk) throws DocumentException{   
    	HtmlParser.parse(document, strNameFileInDisk);
    }
  
} 
