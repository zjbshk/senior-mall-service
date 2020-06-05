package cn.infomany.common.constant;

/**
 * 对于用户访问的唯一标识
 * 我们分为ip和唯一用户
 *
 * @author zjb
 */
public enum UniquelyIdentifiesEnum {

    /**
     * 根据ip来判断
     */
    IP,

    /**
     * 根据用户判断
     */
    USER,


    /**
     * 根据ip和用户判断
     */
    IP_USER,

    /**
     * 根据token判断
     */
    TOKEN


}
