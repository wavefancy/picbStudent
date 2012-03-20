package cn.ac.picb.young;

import java.security.*;

public class MD5 {

    public String getMD5(String s) {
        byte[] byteOfMessage = s.getBytes();
        String str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digested = md.digest(byteOfMessage);

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; ++i) {
                sb.append(Integer.toHexString((digested[i] & 0xFF) | 0x100).substring(1, 3));
            }
            str = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
        return str;
    }
}