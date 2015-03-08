package br.com.centralit.citsmart.rest.test;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import br.com.centralit.citsmart.rest.schema.CtLoginResp;

import com.google.gson.Gson;

public class RestTest2 {

    public static String autenticacaoComJSON() throws Exception {
        final DefaultHttpClient client = new DefaultHttpClient();
        // HttpPost req = new HttpPost("http://10.0.1.94:8080/citsmart/services/login");
        final HttpPost req = new HttpPost("http://localhost:8080/citsmart/services/login");
        req.setEntity(new StringEntity("{\"userName\":\"carlos.alberto\",\"password\":\"central123\"}"));
        req.addHeader("Content-type", "application/json");
        req.addHeader("Accept", "application/json");
        final HttpResponse res = client.execute(req);
        final InputStream in = res.getEntity().getContent();
        int i = -1;
        String ret = "";
        final byte[] buf = new byte[1024];
        while ((i = in.read(buf)) > -1) {
            ret += new String(buf, 0, i);
        }
        System.out.println("#### SAIDA: " + ret);
        client.close();
        return ret;
    }

    public static String autenticacaoComXML() throws Exception {
        final DefaultHttpClient client = new DefaultHttpClient();
        // HttpPost req = new HttpPost("http://10.0.1.94:8080/citsmart/services/login");
        final HttpPost req = new HttpPost("http://localhost:8080/citsmart/services/login");
        req.setEntity(new StringEntity("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Login><UserName>carlos.alberto</UserName><Password>central123</Password></Login>"));
        req.addHeader("Content-type", "application/xml");
        req.addHeader("Accept", "application/xml");
        final HttpResponse res = client.execute(req);
        final InputStream in = res.getEntity().getContent();
        int i = -1;
        String ret = "";
        final byte[] buf = new byte[1024];
        while ((i = in.read(buf)) > -1) {
            ret += new String(buf, 0, i);
        }
        System.out.println("#### SAIDA: " + ret);
        client.close();
        return ret;
    }

    public static String addServiceRequestComXML() throws Exception {
        final String json = autenticacaoComJSON();
        final CtLoginResp login = new Gson().fromJson(json, CtLoginResp.class);

        final StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        xml.append("<ctAddServiceRequest>");
        xml.append("    <SessionID>" + login.getSessionID() + "</SessionID>");
        xml.append("    <ServiceRequestSource>");
        xml.append("        <Number>876543688</Number>");
        xml.append("        <Type>I</Type>");
        xml.append("        <Description>Teste de inclusao de incidente no CITSmart</Description>");
        xml.append("        <StartDateTime>2013-11-14T13:05:42</StartDateTime>");
        xml.append("        <Urgency>L</Urgency>");
        xml.append("        <Impact>L</Impact>");
        xml.append("        <UserID>carlos.alberto</UserID>");
        xml.append("        <Service>");
        xml.append("            <Code>2000</Code>");
        xml.append("            <Description>ACESSOS.ALTERAR.REDE.CONFLITO DE IP.</Description>");
        xml.append("        </Service>");
        xml.append("    </ServiceRequestSource>");
        xml.append("</ctAddServiceRequest>");

        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpPost req = new HttpPost("http://localhost:8080/citsmart/services/xml/addServiceRequest");
        req.setEntity(new StringEntity(xml.toString()));
        req.addHeader("Content-type", "application/xml");
        req.addHeader("Accept", "application/xml");
        final HttpResponse res = client.execute(req);
        final InputStream in = res.getEntity().getContent();
        int i = -1;
        String ret = "";
        final byte[] buf = new byte[1024];
        while ((i = in.read(buf)) > -1) {
            ret += new String(buf, 0, i);
        }
        System.out.println("#### SAIDA: " + ret);
        client.close();
        return ret;
    }

    public static void main(final String[] args) throws Exception {
        autenticacaoComJSON();
        autenticacaoComXML();
        addServiceRequestComXML();
    }

}
