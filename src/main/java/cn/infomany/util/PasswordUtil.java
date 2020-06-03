package cn.infomany.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;

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

    public static String createSalt() {
        return createRandomStr(15);
    }

    public static String createRandomStr(int count) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[count];
        random.nextBytes(bytes);
        return Base64.encodeBase64String(bytes);
    }
}
