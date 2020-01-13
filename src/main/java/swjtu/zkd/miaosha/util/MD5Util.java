package swjtu.zkd.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String SALT = "1a2b3c4d";

    public static String MD5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFormPas(String inputPass) {
        StringBuilder sb = new StringBuilder();
        sb.append(SALT.charAt(0)).append(SALT.charAt(2)).append(inputPass).append(SALT.charAt(5)).append(SALT.charAt(4));
        return MD5(sb.toString());
    }

    public static String formPassToDBPass(String formPass, String saltDB) {
        StringBuilder sb = new StringBuilder();
        sb.append(saltDB.charAt(0)).append(saltDB.charAt(2)).append(formPass).append(saltDB.charAt(5)).append(saltDB.charAt(4));
        return MD5(sb.toString());
    }

    public static String inputPassToDBPass(String inputPass, String saltDB) {
        return formPassToDBPass(inputPassToFormPas(inputPass), saltDB);
    }
}
