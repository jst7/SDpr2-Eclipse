package rmistation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
 
public class encripta {
 
        private final String characterEncoding = "UTF-8";
        private final String cipherTransformation = "AES/CBC/PKCS5Padding";
        private final String aesEncryptionAlgorithm = "AES";
 
        
        public static void main(String[] args) throws Exception {
                encripta utilidades = new encripta();
                String encriptado = utilidades.encrypt("Esto es una prueba", "prueba");
                System.out.println(encriptado);
                String desencriptado = utilidades.decrypt(encriptado, "prueba");
                System.out.println(desencriptado);
        }
 
        public byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector)
                        throws NoSuchAlgorithmException, NoSuchPaddingException,
                        InvalidKeyException, InvalidAlgorithmParameterException,
                        IllegalBlockSizeException, BadPaddingException {
                Cipher cipher = Cipher.getInstance(cipherTransformation);
                SecretKeySpec secretKeySpecy = new SecretKeySpec(key,
                                aesEncryptionAlgorithm);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
                cipherText = cipher.doFinal(cipherText);
                return cipherText;
        }
 
        public byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector)
                        throws NoSuchAlgorithmException, NoSuchPaddingException,
                        InvalidKeyException, InvalidAlgorithmParameterException,
                        IllegalBlockSizeException, BadPaddingException {
                Cipher cipher = Cipher.getInstance(cipherTransformation);
                SecretKeySpec secretKeySpec = new SecretKeySpec(key,
                                aesEncryptionAlgorithm);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
                plainText = cipher.doFinal(plainText);
                return plainText;
        }
 
        private byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
                byte[] keyBytes = new byte[16];
                byte[] parameterKeyBytes = key.getBytes(characterEncoding);
                System.arraycopy(parameterKeyBytes, 0, keyBytes, 0,
                                Math.min(parameterKeyBytes.length, keyBytes.length));
                return keyBytes;
        }
 
        public String encrypt(String plainText, String key)
                        throws UnsupportedEncodingException, InvalidKeyException,
                        NoSuchAlgorithmException, NoSuchPaddingException,
                        InvalidAlgorithmParameterException, IllegalBlockSizeException,
                        BadPaddingException {
                String base64EncryptedString = "";
                byte[] plainTextbytes = plainText.getBytes(characterEncoding);
                byte[] keyBytes = getKeyBytes(key);
                byte[] base64Bytes = Base64.encodeBase64(encrypt(plainTextbytes,
                                keyBytes, keyBytes));
                base64EncryptedString = new String(base64Bytes);
                return base64EncryptedString;
        }
 
        public String decrypt(String encryptedText, String key)
                        throws KeyException, GeneralSecurityException,
                        GeneralSecurityException, InvalidAlgorithmParameterException,
                        IllegalBlockSizeException, BadPaddingException, IOException {
                byte[] cipheredBytes = Base64.decodeBase64(encryptedText
                                .getBytes("utf-8"));
                byte[] keyBytes = getKeyBytes(key);
                return new String(decrypt(cipheredBytes, keyBytes, keyBytes),
                                characterEncoding);
        }
}