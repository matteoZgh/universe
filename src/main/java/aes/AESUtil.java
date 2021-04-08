package aes;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;

/**
 * Created by lkrush on 2018/10/30.
 */
public class AESUtil {
    private static final String ALGORITHM = "AES";
    private static final String KEY_VALUE = "Eeth@1iVPnz78#aL";
    public static String IV = "91129048100F0494";
    private static String SECRETKEY = "76AAA1C88B7F8D1E";

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    public static String encrypt(String data) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encVal);
        } catch (Exception e) {
        }
        return data;
    }
    public static String encrypt(String strKey, String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(strIn.getBytes());
        return parseByte2HexStr(encrypted);
    }

    public static String encryptHexStr(String value) {
        try {
            SecretKeySpec skeySpec = getKey(SECRETKEY);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return parseByte2HexStr(encrypted);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            return new String(decValue);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean hasPhoneEncrypted(String phone) {
        return StringUtils.isNotBlank(phone) && !StringUtils.isNumeric(phone);
    }

    /**
     * 带偏移量的加密
     * @param strKey
     * @param strIn
     * @return
     * @throws Exception
     */
    public static String decrypt(String strKey, String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = parseHexStr2Byte(strIn);

        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "UTF-8");
        if (originalString.equals("-1")) {
            throw new RuntimeException("decrypt return -1");
        }
        return originalString;
    }

    public static String decryptHexStr(String value) {
        try {
            SecretKeySpec skeySpec = getKey(SECRETKEY);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = parseHexStr2Byte(value);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "UTF-8");
        }  catch (Exception e) {
            return null;
        }
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);

            result[i] = ((byte) (high * 16 + low));
        }

        return result;
    }
    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() throws Exception {
        return new SecretKeySpec(KEY_VALUE.getBytes(Charset.forName("utf-8")), ALGORITHM);
    }
    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes("UTF-8");
        byte[] arrB = new byte[16];

        for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
        return skeySpec;
    }
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }



    public static void main(String[] args) throws Exception {
        System.out.println(encrypt("18946004622"));
    }

}
