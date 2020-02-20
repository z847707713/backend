package cn.lovehao.backend.entity;

    import java.util.Date;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author zh
* @since 2020-02-18
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

            /**
            * 用户名
            */
    private String username;

            /**
            * 密码
            */
    private String password;

            /**
            * 昵称
            */
    private String nickName;

            /**
            * 昵称首字母
            */
    private String initials;

            /**
            * 头像url
            */
    private String headImg;

            /**
            * 个性签名
            */
    private String signature;

            /**
            * 手机号
            */
    private String phone;

            /**
            * 电子邮箱
            */
    private String email;

            /**
            * 出生日期
            */
    private Date birthDate;

            /**
            * 是否禁用
            */
    private Boolean isForbid;

            /**
            * 是否删除
            */
    private Boolean isDelete;

            /**
            * 创建者
            */
    private String createBy;

            /**
            * 创建时间
            */
    private Date createTime;

            /**
            * 更新者
            */
    private String updateBy;

            /**
            * 更新时间
            */
    private Date updateTime;


}
