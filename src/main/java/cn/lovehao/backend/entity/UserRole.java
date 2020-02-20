package cn.lovehao.backend.entity;

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
    public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String roleId;


}
