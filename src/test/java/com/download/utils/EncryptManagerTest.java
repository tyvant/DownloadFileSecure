package com.download.utils;

import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.assertj.core.api.Assertions.assertThat;

public class EncryptManagerTest {

    @Test
    public void testEncryptData() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        // given
        byte [] data = "Encrypt data ".getBytes();
         //when
        byte[] encryptData = EncryptManager.encryptData(data);
        //then
        assertThat(encryptData).isNotEqualTo(data);
    }
    @Test

    public void testDecryptData() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        // given
        byte [] data = "Encrypt data".getBytes();
        byte[] encryptData = EncryptManager.encryptData(data);
        //when
        byte[] decryptData = EncryptManager.decryptData(encryptData);
        //
        assertThat(decryptData).isEqualTo(data);
    }
}
