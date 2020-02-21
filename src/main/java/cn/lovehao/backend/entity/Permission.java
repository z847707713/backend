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
    public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 权限code
            */
    private String permission;

            /**
            * 权限名称
            */
    private String name;

            /**
            * 权限描述
            */
    private String pDesc;

            /**
            * ur
            */
    private String url;

            /**
            * 0，菜单；1，按钮
            */
    private Boolean type;

            /**
            * 父级权限
            */
    private String parent;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
