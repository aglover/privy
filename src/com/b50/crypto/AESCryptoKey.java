package com.b50.crypto;

import java.security.Key;

/**
 *
 */
public class AESCryptoKey implements CryptoKeyable{

    private Key key;

    public AESCryptoKey(Key key){
        this.key = key;
    }

    public Key getKey() {
        return this.key;
    }
}
