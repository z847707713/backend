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
    public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private String permissionId;

    private String roleId;

    private Date createTime;


}
