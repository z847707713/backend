package cn.lovehao.backend.entity;

    import java.util.Date;
    import java.io.Serializable;

    import com.baomidou.mybatisplus.annotation.FieldFill;
    import com.baomidou.mybatisplus.annotation.TableField;
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

    private String id;

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

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
