package cn.infomany.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

    private final static String IP_SEPARATOR = ",";

    /**
     * 获取客户端Ip
     *
     * @param request
     */
    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Real-IP");

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        } else {
            return ip;
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } else {
            // 当有多级反向代理时，x-forwarded-for值为多个时取第一个ip地址
            if (ip.contains(IP_SEPARATOR)) {
                ip = ip.substring(0, ip.indexOf(IP_SEPARATOR));
            }
            return ip;
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        } else {
            return ip;
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            return ip;
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = "";
        }
        return ip;
    }
}
