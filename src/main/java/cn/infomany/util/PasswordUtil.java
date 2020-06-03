package cn.infomany.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;

/**
 * 密码用户类（包括加密）
 *
 * @author zjb
 * @date 2020/6/2
 */
public class PasswordUtil {

    private final static int SALT_POSITION = 1;
    private final static int DIGEST_COUNT = 5;

    public static String encryptedPassword(String password, String salt) {
        String sha256Password = SecureUtil.sha256(password);
        MD5 md5 = new MD5(salt.getBytes(), SALT_POSITION, DIGEST_COUNT);
        return md5.digestHex16(sha256Password);
    }
}
