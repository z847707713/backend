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
    public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 角色
            */
    private String role;

            /**
            * 角色名称
            */
    private String roleName;

            /**
            * 描述
            */
    private String roleDesc;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;


}
