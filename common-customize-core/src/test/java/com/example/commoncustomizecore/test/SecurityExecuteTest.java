package com.example.commoncustomizecore.test;

import com.example.commoncustomizecore.api.encryption.SecurityExecute;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

public class SecurityExecuteTest
{
    private static String str = "川建国没戏了ssss";

    private static String key = "0123456123456121";

    @Test
    public void testDes()
    {
        SecurityExecute execute = new SecurityExecute();
        String encrypt = execute.desEncrypt(str, Base64.getEncoder().encodeToString(key.getBytes()));
        System.out.println(encrypt);
        String decrypt = execute.desDecrypt(encrypt, Base64.getEncoder().encodeToString(key.getBytes()));
        System.out.println(decrypt);

        Map<String, String> map = execute.desEncrypt(str);

        map.forEach((key, value) -> {
            System.out.println(key + ":" + value);

        });
        String noKey = execute.desDecrypt("5E6mp/1+8cwNN2dMcl+KZnOPoiJybl48", "yOA43MeRHCA=");
        System.out.println(noKey);
    }

    @Test
    public void testAes()
    {
        SecurityExecute execute = new SecurityExecute();
        System.out.println(key.length());
        String encrypt = execute.aesEncrypt(str, Base64.getEncoder().encodeToString(key.getBytes()));
        System.out.println(encrypt);
        System.out.println(execute.aesDecrypt(encrypt, Base64.getEncoder().encodeToString(key.getBytes())));

        Map map = execute.aesEncrypt(str);
        map.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
        System.out.println(execute.aesDecrypt("4PFiAD09Wt9NTN04mp02la+alPWyZ5n+7obtqFSNNXo=", "BuJkx+rNbh3OkmEkQEM3qg=="));
    }

    @Test
    public void testRsaSign()
    {
        SecurityExecute execute = new SecurityExecute();
        try
        {
//            String bastPath = SecurityExecuteTest.class.getClassLoader().getResource("").toString();
            URL url = SecurityExecuteTest.class.getClassLoader().getResource("rsa_private.pem");
            String privateKey = FileUtils.readFileToString(new File(url.getFile()), "UTF-8");
            privateKey = privateKey.split("-----")[2].replace("\t\n", "");
            String sign = execute.rsaSignByPrivate("_input_charset=川建国没戏了ssss&ke=khgs", privateKey);
            System.out.println(sign);

            System.out.println("========验签========");
            URL urlP = SecurityExecuteTest.class.getClassLoader().getResource("rsa_public.pem");
            String publicKey = FileUtils.readFileToString(new File(urlP.getFile()), "UTF-8");
            publicKey = publicKey.split("-----")[2].replace("\t\n", "");
            System.out.println(execute.rsaVerifySignByPublic("_input_charset=川建国没戏了ssss&ke=khgs", sign, publicKey));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testRsaSecret()
    {
        try
        {
            SecurityExecute execute = new SecurityExecute();
            URL url = SecurityExecuteTest.class.getClassLoader().getResource("rsa_public.pem");
            String privateKey = FileUtils.readFileToString(new File(url.getFile()), "UTF-8");
            privateKey = privateKey.split("-----")[2].replace("\t\n", "");
            String encrypt = execute.rsaEncryptByPublic(str, privateKey);
            System.out.println(encrypt);
            System.out.println("========解密==========");
            URL url1 = SecurityExecuteTest.class.getClassLoader().getResource("rsa_private.pem");
            String publicKey = FileUtils.readFileToString(new File(url1.getFile()), "UTF-8");
            publicKey = publicKey.split("-----")[2].replace("\t\n", "");
            System.out.println(execute.rsaDecryptByPrivate(encrypt, publicKey));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}