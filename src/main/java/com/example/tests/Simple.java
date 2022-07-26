package com.example.tests;

import org.apache.commons.codec.digest.DigestUtils;

public class Simple {
    public static void main(String[] args) {

        String sha256hex = DigestUtils.sha256Hex("secret");
        System.out.println(String.format("secret value after hashing:\n%s", sha256hex));

    }
}
