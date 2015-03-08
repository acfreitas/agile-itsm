package br.com.citframework.util.cripto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CITAssinadorDigital {

    public void inicializar() {
        final BouncyCastleProvider bc = new BouncyCastleProvider();

        Security.addProvider(bc);
    }

    public void assinar(String cpf, String fileInWithPathComplete, String fileOutWithPathComplete,
            X509Certificate cert, boolean assinarAdicionadoConteudo) throws NoSuchAlgorithmException,
            NoSuchProviderException, CMSException, CertStoreException, CertificateExpiredException,
            CertificateNotYetValidException, IOException {
        final byte[] x = assinar(cpf, fileInWithPathComplete, cert, assinarAdicionadoConteudo);
        try {
            final FileOutputStream output = new FileOutputStream(fileOutWithPathComplete);

            output.write(x);
            output.close();
        } catch (final FileNotFoundException e1) {
            throw new RuntimeException(e1);
        } catch (final IOException e1) {
            throw new RuntimeException(e1);
        }
    }

    public byte[] assinar(String cpf, String fileInWithPathComplete, X509Certificate cert,
            boolean assinarAdicionadoConteudo) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException,
            CertStoreException, CertificateExpiredException, CertificateNotYetValidException, IOException {
        final File file = new File(fileInWithPathComplete);

        final byte[] content = getBytesFromFile(file);

        return assinar(cpf, content, cert, assinarAdicionadoConteudo);
    }

    public byte[] assinar(String cpf, byte[] infoAssinar, X509Certificate cert, boolean assinarAdicionadoConteudo)
            throws NoSuchAlgorithmException, NoSuchProviderException, CMSException, CertStoreException,
            CertificateExpiredException, CertificateNotYetValidException, IOException {
        byte[] content = null;

        cert.checkValidity();

        final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyGen.initialize(1024);
        final KeyPair keypair = keyGen.genKeyPair();
        final PrivateKey key = keypair.getPrivate();

        content = infoAssinar;

        final CMSSignedDataGenerator generator = new CMSSignedDataGenerator();

        generator.addSigner(key, cert, CMSSignedDataGenerator.DIGEST_SHA1);

        final List<X509Certificate> certList = new ArrayList<>();
        certList.add(cert);
        try {
            final CertStore certs = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList),
                    "BC");
            generator.addCertificatesAndCRLs(certs);
        } catch (final InvalidAlgorithmParameterException e1) {
            throw new RuntimeException(e1);
        } catch (final NoSuchAlgorithmException e1) {
            throw new RuntimeException(e1);
        } catch (final NoSuchProviderException e1) {
            throw new RuntimeException(e1);
        } catch (final CertStoreException e2) {
            throw new RuntimeException(e2);
        } catch (final CMSException e2) {
            throw new RuntimeException(e2);
        }
        CMSSignedData signedData;
        try {
            signedData = generator.generate(new CMSProcessableByteArray(content), assinarAdicionadoConteudo, "BC");
        } catch (final NoSuchAlgorithmException e1) {
            throw new RuntimeException(e1);
        } catch (final NoSuchProviderException e1) {
            throw new RuntimeException(e1);
        } catch (final CMSException e1) {
            throw new RuntimeException(e1);
        }
        try {
            return signedData.getEncoded();
        } catch (final FileNotFoundException e1) {
            throw new RuntimeException(e1);
        } catch (final IOException e1) {
            throw new RuntimeException(e1);
        }
    }

    public InfoCertificadoDigital validarAssinatura(String fileSigned) throws IOException, CMSException,
    NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, CertificateParsingException {
        final InfoCertificadoDigital infoCertificadoDigital = new InfoCertificadoDigital();
        final CMSSignedData signedData = new CMSSignedData(new FileInputStream(new File(fileSigned)));

        X509Certificate cert = null;

        final CertStore certificados = signedData.getCertificatesAndCRLs("Collection", "BC");

        final Collection certCollectionX = certificados.getCertificates(new X509CertSelector());
        final Iterator certIter = certCollectionX.iterator();
        while (certIter.hasNext()) {
            cert = (X509Certificate) certIter.next();
        }
        if (cert == null) {
            return null;
        }

        infoCertificadoDigital.setNomeTitular(cert.getSubjectX500Principal().getName());

        final String frase = cert.getSubjectX500Principal().getName();
        String[] array = frase.split(",");
        if (array != null) {
            for (final String element : array) {
                if (element.startsWith("CN=")) {
                    final String[] arr2 = element.split(":");
                    if (arr2 != null) {
                        infoCertificadoDigital.setNomeTitular(arr2[0].replaceAll("CN=", ""));
                        infoCertificadoDigital.setCpf(arr2[1]);
                    }
                }
            }
        }
        infoCertificadoDigital.setInstituicoes(new ArrayList<>());
        infoCertificadoDigital.setNomeEmissor(cert.getIssuerX500Principal().getName());
        String fraseEmissor = cert.getIssuerX500Principal().getName();
        array = fraseEmissor.split(",");
        if (array != null) {
            for (final String element : array) {
                if (element.startsWith("CN=")) {
                    infoCertificadoDigital.setNomeEmissor(element.replaceAll("CN=", ""));
                } else if (element.startsWith("O=")) {
                    infoCertificadoDigital.setRaiz(element.replaceAll("O=", ""));
                } else if (element.startsWith("C=")) {
                    infoCertificadoDigital.setPais(element.replaceAll("C=", ""));
                }
            }
        }
        infoCertificadoDigital.setDataInicioValidade(cert.getNotBefore());
        infoCertificadoDigital.setDataFimValidade(cert.getNotAfter());
        infoCertificadoDigital.setAlgoritimo(cert.getSigAlgName());
        infoCertificadoDigital.setVersao(cert.getVersion());
        infoCertificadoDigital.setTipo(cert.getType());

        final byte[] conteudo = (byte[]) signedData.getSignedContent().getContent();
        infoCertificadoDigital.setConteudoOriginal(conteudo);

        fraseEmissor = cert.getSubjectDN().getName();
        array = fraseEmissor.split(",");
        if (array != null) {
            for (final String element : array) {
                if (element.startsWith("OU=")) {
                    infoCertificadoDigital.getInstituicoes().add(element.replaceAll("OU=", ""));
                }
            }
        }
        return infoCertificadoDigital;
    }

    public InfoCertificadoDigital getInformacaoCertificado(String fileCertificado) throws CertificateException,
    FileNotFoundException, NoSuchProviderException {
        final InputStream inStream = new FileInputStream(fileCertificado);
        final CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
        final X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);

        return getInformacaoCertificado(cert);
    }

    public InfoCertificadoDigital getInformacaoCertificado(X509Certificate cert) throws CertificateException,
    FileNotFoundException, NoSuchProviderException {
        final InfoCertificadoDigital infoCertificadoDigital = new InfoCertificadoDigital();

        infoCertificadoDigital.setKeyUsageDigitalSignature(cert.getKeyUsage()[0]);
        infoCertificadoDigital.setKeyUsageNonRepudiation(cert.getKeyUsage()[1]);
        infoCertificadoDigital.setKeyUsageKeyEncipherment(cert.getKeyUsage()[2]);
        infoCertificadoDigital.setKeyUsageDataEncipherment(cert.getKeyUsage()[3]);
        infoCertificadoDigital.setKeyUsageKeyAgreement(cert.getKeyUsage()[4]);
        infoCertificadoDigital.setKeyUsageKeyCertSign(cert.getKeyUsage()[5]);
        infoCertificadoDigital.setKeyUsageCRLSign(cert.getKeyUsage()[6]);
        infoCertificadoDigital.setKeyUsageEncipherOnly(cert.getKeyUsage()[7]);
        infoCertificadoDigital.setKeyUsageDecipherOnly(cert.getKeyUsage()[8]);

        infoCertificadoDigital.setNomeTitular(cert.getSubjectX500Principal().getName());

        final String frase = cert.getSubjectX500Principal().getName();
        String[] array = frase.split(",");
        if (array != null) {
            for (final String element : array) {
                if (element.startsWith("CN=")) {
                    final String[] arr2 = element.split(":");
                    if (arr2 != null) {
                        infoCertificadoDigital.setNomeTitular(arr2[0].replaceAll("CN=", ""));
                        infoCertificadoDigital.setCpf(arr2[1]);
                    }
                }
            }
        }
        infoCertificadoDigital.setInstituicoes(new ArrayList<>());
        infoCertificadoDigital.setNomeEmissor(cert.getIssuerX500Principal().getName());
        String fraseEmissor = cert.getIssuerX500Principal().getName();
        array = fraseEmissor.split(",");
        if (array != null) {
            for (final String element : array) {
                if (element.startsWith("CN=")) {
                    infoCertificadoDigital.setNomeEmissor(element.replaceAll("CN=", ""));
                } else if (element.startsWith("O=")) {
                    infoCertificadoDigital.setRaiz(element.replaceAll("O=", ""));
                } else if (element.startsWith("C=")) {
                    infoCertificadoDigital.setPais(element.replaceAll("C=", ""));
                }
            }
        }
        infoCertificadoDigital.setDataInicioValidade(cert.getNotBefore());
        infoCertificadoDigital.setDataFimValidade(cert.getNotAfter());
        infoCertificadoDigital.setAlgoritimo(cert.getSigAlgName());
        infoCertificadoDigital.setVersao(cert.getVersion());
        infoCertificadoDigital.setTipo(cert.getType());

        infoCertificadoDigital.setConteudoOriginal(null);

        fraseEmissor = cert.getSubjectDN().getName();
        array = fraseEmissor.split(",");
        if (array != null) {
            for (final String element : array) {
                if (element.startsWith("OU=")) {
                    infoCertificadoDigital.getInstituicoes().add(element.replaceAll("OU=", ""));
                }
            }
        }
        return infoCertificadoDigital;
    }

    private static byte[] getBytesFromFile(File file) {
        try {
            final InputStream is = new FileInputStream(file);
            final long length = file.length();
            if (length > 2147483647L) {
                System.out.println("Arquivo muito grande >>> " + file.getName());
            }
            final byte[] bytes = new byte[(int) length];

            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                System.out.println("Não foi possível ler o arquivo completamente >>> " + file.getName());
            }
            is.close();
            return bytes;
        } catch (final IOException e) {
            System.out.println("Não foi possível ler o arquivo.");
        }
        return null;
    }

    public static void main(String[] args) throws CertificateException, NoSuchAlgorithmException, IOException,
    NoSuchProviderException, KeyStoreException, CMSException, CertStoreException {
        final CITAssinadorDigital d = new CITAssinadorDigital();
        d.inicializar();

        final InputStream inStream = new FileInputStream("C:\\Emauri\\eToken\\emauri.cer");
        final CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
        final X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
        d.assinar("02448189676", "C:\\CITQuestionario-DB2.pdm", "C:\\CITQuestionario-DB2.assinado", cert, false);
    }

}
