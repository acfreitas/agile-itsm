package br.com.citframework.html;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class LeitorHTML {

    protected static HTMLParseLister process(final String nomeArquivo, final boolean debugParser) throws IOException {
        final FileInputStream fis = new FileInputStream(nomeArquivo);
        final InputStreamReader isr = new InputStreamReader(fis);
        final BufferedReader br = new BufferedReader(isr);
        final HTMLEditorKit.Parser parser = new ParserDelegator();
        final HTMLParseLister h = new HTMLParseLister(debugParser);
        parser.parse(br, h, true);
        br.close();
        return h;
    }

    /**
     * Obtem o texto nao formatado de um arquivo HTML (sem as tags).
     *
     * @param nomeArquivo
     * @param debugParser
     * @return
     * @throws IOException
     */
    public static StringBuilder getTextoFromArquivoHTML(final String nomeArquivo, final boolean debugParser) throws IOException {
        final HTMLParseLister h = process(nomeArquivo, debugParser);

        if (debugParser) {
            System.out.println(">>>> LeitorHTML:: TEXTO EXTRAÍDO: " + h.getStrBuffTextData().toString());
        }
        return h.getStrBuffTextData();
    }

    /**
     * Obtem uma coleção de elementos string - nao formatado de um arquivo HTML (sem as tags).
     *
     * @param nomeArquivo
     * @param debugParser
     * @return
     * @throws IOException
     */
    public static Collection getCollectionFromArquivoHTML(final String nomeArquivo, final boolean debugParser) throws IOException {
        final HTMLParseLister h = process(nomeArquivo, debugParser);

        if (debugParser) {
            if (h.getColBuffTextData() != null) {
                int i = 0;
                for (final Iterator it = h.getColBuffTextData().iterator(); it.hasNext();) {
                    final String element = (String) it.next();
                    System.out.println(">>>> LeitorHTML:: TEXTO EXTRAÍDO (" + i + "): " + element);
                    i++;
                }
            }
        }
        return h.getColBuffTextData();
    }
}

class HTMLParseLister extends HTMLEditorKit.ParserCallback {

    int indentSize = 0;
    boolean debug = false;
    StringBuilder strBuffTextData = null;
    Collection colBuffTextData = null;

    public HTMLParseLister() {
        debug = false;
    }

    public HTMLParseLister(final boolean debugParm) {
        debug = debugParm;
    }

    protected void indent() {
        indentSize += 3;
    }

    protected void unIndent() {
        indentSize -= 3;
        if (indentSize < 0) {
            indentSize = 0;
        }
    }

    protected void pIndent() {
        if (debug) {
            for (int i = 0; i < indentSize; i++) {
                System.out.print(" ");
            }
        }
    }

    @Override
    public void handleText(final char[] data, final int pos) {
        this.pIndent();
        // System.out.println("Text(" + data.length + " chars)");
        if (debug) {
            System.out.println(data);
        }
        if (strBuffTextData == null) {
            strBuffTextData = new StringBuilder();
        }
        if (colBuffTextData == null) {
            colBuffTextData = new ArrayList<>();
        }
        strBuffTextData.append(data);
        strBuffTextData.append("\n");

        colBuffTextData.add("" + new String(data));
    }

    @Override
    public void handleComment(final char[] data, final int pos) {
        this.pIndent();
        if (debug) {
            System.out.println("Comment(" + data.length + " chars)");
        }
    }

    @Override
    public void handleStartTag(final HTML.Tag t, final MutableAttributeSet a, final int pos) {
        this.pIndent();
        if (debug) {
            System.out.println("Tag start(<" + t.toString() + ">, " + a.getAttributeCount() + " attrs)");
        }
        this.indent();
    }

    @Override
    public void handleEndTag(final HTML.Tag t, final int pos) {
        this.unIndent();
        this.pIndent();
        if (debug) {
            System.out.println("Tag end<" + t.toString() + ">");
        }
    }

    @Override
    public void handleSimpleTag(final HTML.Tag t, final MutableAttributeSet a, final int pos) {
        this.pIndent();
        if (debug) {
            System.out.println("Tag(<" + t.toString() + ">, " + a.getAttributeCount() + " attrs)");
        }
    }

    @Override
    public void handleError(final String errorMsg, final int pos) {
        if (debug) {
            System.out.println("Parsing error: " + errorMsg + " at " + pos);
        }
    }

    public StringBuilder getStrBuffTextData() {
        return strBuffTextData;
    }

    public void setStrBuffTextData(final StringBuilder strBuffTextData) {
        this.strBuffTextData = strBuffTextData;
    }

    public Collection getColBuffTextData() {
        return colBuffTextData;
    }

    public void setColBuffTextData(final Collection colBuffTextData) {
        this.colBuffTextData = colBuffTextData;
    }

}
