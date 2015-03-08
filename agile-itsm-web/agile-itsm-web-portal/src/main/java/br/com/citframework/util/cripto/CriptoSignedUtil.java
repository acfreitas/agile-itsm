package br.com.citframework.util.cripto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;

public class CriptoSignedUtil {

    private static final String hexDigits = "0123456789abcdef";

    public static SignedInfo generateStringToSend(final String pathJKSPK, final String pathJKSSK, final String txt) throws Exception {
        final File cert = new File(pathJKSPK);
        final String alias = "citsmart";
        final String pwd = "c3ntr@lit";

        final File certSK = new File(pathJKSSK);
        final String aliasSK = "citsmartcripto";
        final String pwdSK = "c3ntr@lit123";

        final PrivateKey privateKey = getPrivateKeyFromFile(cert, alias, pwd);
        final PublicKey publicKey = getPublicKeyFromFile(cert, alias, pwd);

        final SecretKey secretkey = getSecretKeyFromFile(certSK, aliasSK, pwdSK);

        final byte[] txtAssinado = createSignature(privateKey, txt.getBytes());

        final byte[] txtCripto = cript(publicKey, secretkey, txt.getBytes());
        final String hexStrMsg = byteArrayToHexString(txtCripto);

        final SignedInfo signedInfo = new SignedInfo();
        signedInfo.setStrCripto(hexStrMsg);
        signedInfo.setStrSigned(byteArrayToHexString(txtAssinado));
        return signedInfo;
    }

    public static String translateStringReceive(final String pathJKSPK, final String pathJKSSK, final String txtCriptoAndSigned, final String txtAssinado) throws Exception {
        final File cert = new File(pathJKSPK);
        final String alias = "citsmart";
        final String pwd = "c3ntr@lit";

        final File certSK = new File(pathJKSSK);
        final String aliasSK = "citsmartcripto";
        final String pwdSK = "c3ntr@lit123";

        final PrivateKey privateKey = getPrivateKeyFromFile(cert, alias, pwd);
        final PublicKey publicKey = getPublicKeyFromFile(cert, alias, pwd);

        final SecretKey secretkey = getSecretKeyFromFile(certSK, aliasSK, pwdSK);

        final byte[] txtDecripto = decript(publicKey, privateKey, secretkey, hexStringToByteArray(txtCriptoAndSigned));

        if (verifySignature(publicKey, txtDecripto, hexStringToByteArray(txtAssinado))) {
            return new String(txtDecripto);
        } else {
            return "FAIL - ASSINATURA INVALIDA!";
        }
    }

    public static PrivateKey getPrivateKeyFromFile(final File cert, final String alias, final String password) throws Exception {
        final KeyStore ks = KeyStore.getInstance("JKS");
        final char[] pwd = password.toCharArray();
        final InputStream is = new FileInputStream(cert);
        ks.load(is, pwd);
        is.close();
        final Key key = ks.getKey(alias, pwd);
        if (key instanceof PrivateKey) {
            return (PrivateKey) key;
        }
        return null;
    }

    /**
     * Extrai a chave pública do arquivo.
     */
    public static PublicKey getPublicKeyFromFile(final File cert, final String alias, final String password) throws Exception {
        final KeyStore ks = KeyStore.getInstance("JKS");
        final char[] pwd = password.toCharArray();
        final InputStream is = new FileInputStream(cert);
        ks.load(is, pwd);
        ks.getKey(alias, pwd);
        final Certificate c = ks.getCertificate(alias);
        final PublicKey p = c.getPublicKey();
        return p;
    }

    /**
     * Extrai a chave pública do arquivo.
     */
    public static SecretKey getSecretKeyFromFile(final File cert, final String alias, final String password) throws Exception {
        final KeyStore ks = KeyStore.getInstance("JCEKS");
        final char[] pwd = password.toCharArray();
        final InputStream is = new FileInputStream(cert);
        ks.load(is, pwd);
        final Key key = ks.getKey(alias, pwd);
        if (key instanceof SecretKey) {
            return (SecretKey) key;
        }
        return null;
    }

    /**
     * Retorna a assinatura para o buffer de bytes, usando a chave privada.
     *
     * @param key
     *            PrivateKey
     * @param buffer
     *            Array de bytes a ser assinado.
     */
    public static byte[] createSignature(final PrivateKey key, final byte[] buffer) throws Exception {
        final Signature sig = Signature.getInstance("MD5withRSA");
        sig.initSign(key);
        sig.update(buffer, 0, buffer.length);
        return sig.sign();
    }

    /**
     * Verifica a assinatura para o buffer de bytes, usando a chave pública.
     *
     * @param key
     *            PublicKey
     * @param buffer
     *            Array de bytes a ser verficado.
     * @param sgined
     *            Array de bytes assinado (encriptado) a ser verficado.
     */
    public static boolean verifySignature(final PublicKey key, final byte[] buffer, final byte[] signed) throws Exception {
        final Signature sig = Signature.getInstance("MD5withRSA");
        sig.initVerify(key);
        sig.update(buffer, 0, buffer.length);
        return sig.verify(signed);
    }

    /**
     * Converte um array de byte em uma representação, em String, de seus
     * hexadecimais.
     */
    public static String txt2Hexa(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        final String hexDigits = "0123456789abcdef";
        final StringBuilder sbuffer = new StringBuilder();
        for (final byte b : bytes) {
            final int j = b & 0xFF;
            sbuffer.append(hexDigits.charAt(j / 16));
            sbuffer.append(hexDigits.charAt(j % 16));
        }
        return sbuffer.toString();
    }

    public static byte[] generateWrapKey(final PublicKey publicKey, final Cipher cipher, final SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException {
        final byte[] wrappedKey = cipher.wrap(key);
        return wrappedKey;
    }

    public static byte[] cript(final PublicKey publicKey, final SecretKey secretkey, final byte[] msg) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, ShortBufferException, BadPaddingException {
        final Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretkey);

        final int blockSize = cipher.getBlockSize();
        final int outputSize = cipher.getOutputSize(blockSize);
        byte[] outBytes = new byte[outputSize];;
        outBytes = cipher.doFinal(msg, 0, msg.length);
        return outBytes;
    }

    public static byte[] decript(final PublicKey publicKey, final PrivateKey privateKey, final SecretKey secretkey, final byte[] msgCripto) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, ShortBufferException, BadPaddingException {
        final Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretkey);

        final int blockSize = cipher.getBlockSize();
        final int outputSize = cipher.getOutputSize(blockSize);
        byte[] outBytes = new byte[outputSize];;
        outBytes = cipher.doFinal(msgCripto, 0, msgCripto.length);
        return outBytes;
    }

    /**
     * Converte o array de bytes em uma representação hexadecimal.
     *
     * @param input
     *            - O array de bytes a ser convertido.
     * @return Uma String com a representação hexa do array
     */
    public static String byteArrayToHexString(final byte[] b) {
        final StringBuilder buf = new StringBuilder();

        for (final byte element : b) {
            final int j = element & 0xFF;
            buf.append(hexDigits.charAt(j / 16));
            buf.append(hexDigits.charAt(j % 16));
        }

        return buf.toString();
    }

    /**
     * Converte uma String hexa no array de bytes correspondente.
     *
     * @param hexa
     *            - A String hexa
     * @return O vetor de bytes
     * @throws IllegalArgumentException
     *             - Caso a String não sej auma representação haxadecimal válida
     */
    public static byte[] hexStringToByteArray(final String hexa) throws IllegalArgumentException {

        // verifica se a String possui uma quantidade par de elementos
        if (hexa.length() % 2 != 0) {
            throw new IllegalArgumentException("String hexa inválida");
        }

        final byte[] b = new byte[hexa.length() / 2];

        for (int i = 0; i < hexa.length(); i += 2) {
            b[i / 2] = (byte) (hexDigits.indexOf(hexa.charAt(i)) << 4 | hexDigits.indexOf(hexa.charAt(i + 1)));
        }
        return b;
    }
}
