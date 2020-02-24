$(function () {

    var url = "/admin/users/list";

    $('#search-form').on('submit',function(event){
        var field = $('#search-field').val()
        var value = $('#keywork-input').val()
        var reloadUrl = url + "?" + field + "=" + value;
        pageload(reloadUrl)
        return false;
    })

    pageload(url);

});

function forbid(_this,id){
    Confirm.alert('是否更改该用户状态',function () {
        var data = {
            id:id
        };
        Ajax.get("/admin/users/forbid",data,function(result){
            lightyear.loading("hide");
            if(result.status){
                _this.prop("checked",!_this.prop("checked"))
            } else {
                Confirm.error(result.msg);
            }
        })
    })
    return false;
}

function add(){
    var jc = $.confirm({
        title: '新增用户',
        useBootstrap: false,
        boxWidth: '60%',
        content: '<div class="col-md-12"><div class="card-body"><form id="confirm-form" class="form-horizontal" action="#" method="post" enctype="multipart/form-data" onsubmit="return false;">\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="example-email-input">用户名</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="username-input" name="username" placeholder="用户名..">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="password-input">密码</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="password" id="password-input" name="password" placeholder="密码..">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="nickname-input">昵称</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="nickname-input" name="nickName" placeholder="昵称..">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="phone-input">手机号</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="phone-input" name="phone" placeholder="手机号..">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="email-input">邮箱</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="email-input" name="email" placeholder="邮箱..">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                </form></div></div>',
        buttons: {
            confirm: {
                text: '确认',
                btnClass: 'btn-primary',
                action: function(){
                    var formData = $('#confirm-form').serializeObject();
                    if(verify('add',formData)){
                        Ajax.post("/admin/user",formData,function(result){
                            lightyear.loading("hide");
                            if(result.status){
                                Notify.success("用户添加成功");
                                jc.close()
                            } else { //添加失败
                                Notify.error(result.msg)
                            }
                        })
                    }
                    return false;
                }
            },
            cancel: {
                text: '取消'
            }
        }
    });
    return false;
}

function edit(id) {
    Ajax.get("/admin/user",{id:id},function (result) {
        lightyear.loading("hide");
        editView(result.data)
    })
}

function deleteById(id) {
    Confirm.alert("是否删除该用户",function(){
        Ajax.deleteByIdDiyFunction("/admin/user",id,function (result) {
            lightyear.loading("hide")
            if(result.status){
                //刷新页面
                Pagination.deleteAndFlush($('#pagination-container'))
                Notify.success("删除成功");
            } else {
                Notify.error(result.msg);
            }
        })
    })
}



function pageload(url){
    $('#pagination-container').pagination({
        dataSource: url, //请求的url
        locator: 'data.records', //返回值data的位置
        totalNumberLocator: function (res) { //返回值总数量的位置
            return Math.floor(res.data.total);
        },
        pageNumber : 1,
        pageSize: 10,
        pageRange: 1,
        className: '',
        ulClassName: 'pagination',
        prevText: '<&nbsp;上一页',
        nextText: '下一页&nbsp;>',
        showGoInput: false,
        showGoButton: false,
        alias: {
            pageNumber: 'current',
            pageSize: 'size'
        },
        callback: function (data, pagination) {   //回掉函数
            //取消loading
            lightyear.loading('hide');
            //取消全选
            $("#my-check-all").prop("checked",false);
            //渲染表格
            $('#data-container').html(template(data));

            //绑定事件
            $('.checkbox-forbid').change(function(){
                //获取更改的id
                var id = $(this).attr('data-id');
                // 模拟禁用checkbox 功能
                $(this).prop("checked",!$(this).prop("checked"))
                // 具体禁用逻辑
                forbid($(this),id);
                return false;
            })

        },beforeRender:function(){
            lightyear.loading('show');
        }
    });

}

function editView(data){
    var confirm = $.confirm({
        title: '编辑用户',
        useBootstrap: false,
        boxWidth: '60%',
        content: '<div class="col-md-12"><div class="card-body"><form id="confirm-form-update" class="form-horizontal" action="#" method="post" enctype="multipart/form-data" onsubmit="return false;">\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="example-email-input">用户名</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="hidden" id="username-id" name="id" placeholder="用户名.." value="' + data.id + '">\n' +
            '                      <input class="form-control" type="text" id="username-input" name="username" placeholder="用户名.." value="' + data.username + '">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="password-input">密码</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="password" id="password-input" name="password" placeholder="密码.." >\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="nickname-input">昵称</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="nickname-input" name="nickName" placeholder="昵称.." value="' + data.nickName + '">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="phone-input">手机号</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="phone-input" name="phone" placeholder="手机号.." value="'+ data.phone +'">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="email-input">邮箱</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="email-input" name="email" placeholder="邮箱.." value="' + data.email + '">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                </form></div></div>',
        buttons: {
            confirm: {
                text: '更新',
                btnClass: 'btn-primary',
                action: function(){
                    var formData = $('#confirm-form-update').serializeObject();
                    if(verify('update',formData)){
                        Ajax.put("/admin/user",formData,function(result){
                            lightyear.loading("hide");
                            if(result.status){
                                Notify.success("用户修改成功");
                                confirm.close();
                                Pagination.flush($('#pagination-container'))
                            } else {
                                Notify.error(result.msg)
                            }
                        })
                    }
                    return false;
                }
            },
            cancel: {
                text: '取消'
            }
        }
    });
    return false;
}


function verify(type,data){
    //用户名为空
    if(Verify.isEmpty(data.username)){
        Notify.error('用户名不能为空')
        return false;
    }
    //密码为空
    if(Verify.isEmpty(data.password) && type=='add' ){
        Notify.error('密码不能为空')
        return false;
    }
    //昵称为空
    if(Verify.isEmpty(data.nickName)){
        Notify.error('昵称不能为空')
        return false;
    }
    //手机为空
    if(Verify.isEmpty(data.phone)){
        Notify.error('手机号不能为空')
        return false;
    }
    //手机为空
    if(!Verify.isPhone(data.phone)){
        Notify.error('手机号不正确')
        return false;
    }
    //邮箱为空
    if(Verify.isEmpty(data.email)){
        Notify.error('昵称不能为空')
        return false;
    }
    //邮箱错误
    if(!Verify.isEmail(data.email)){
        Notify.error('邮箱不正确')
        return false;
    }
    return true;
}

function template(data) {
    var html = "";

    if(data.length <= 0){
        html+='<tr><td colspan="8" style="text-align: center">没有数据</td></tr>'
    }

    for (var i = 0; i < data.length; i++) {
        html += "<tr>" +
            "                                            <td>" +
            "                                                <label class=\"lyear-checkbox checkbox-primary\">" +
            "                                                    <input type=\"checkbox\" class='check-all' name=\"ids[]\" value=\"" + data[i].id + "\"><span></span>" +
            "                                                </label>" +
            "                                            </td>" +
            "                                            <td>" + data[i].id + "</td>\n" +
            "                                            <td>" + data[i].username + "</td>\n" +
            "                                            <td>" + data[i].nickName + "</td>\n" +
            "                                            <td>" + data[i].email +  "</td>\n";

        if(data[i].isForbid){
            html+= "<td><label class=\"lyear-switch switch-solid switch-primary\">" +
                "   <input class='checkbox-forbid' type=\"checkbox\" data-id='"+data[i].id+"'>" +
                "  <span></span>" +
                "</label></td>";
        } else {
            html+= "<td><label class=\"lyear-switch switch-solid switch-primary\">" +
                "   <input class='checkbox-forbid' type=\"checkbox\" checked=\"true\" data-id='"+data[i].id+"'>" +
                "  <span></span>" +
                "</label></td>";
        }

        html +=
            "                                            <td>"+ data[i].createTime +"</td>\n" +
            "                                            <td>\n" +
            "                                                <div class=\"btn-group\">\n" +
            "                                                    <a class=\"btn btn-xs btn-default\" href=\"#!\" onclick=\"edit('"+ data[i].id +"')\" title=\"\"\n" +
            "                                                       data-toggle=\"tooltip\" data-original-title=\"编辑\"><i\n" +
            "                                                            class=\"mdi mdi-pencil\"></i></a>\n" +
            "                                                    <a class=\"btn btn-xs btn-default\" href=\"#!\" onclick=\"deleteById('"+ data[i].id +"')\" title=\"\"\n" +
            "                                                       data-toggle=\"tooltip\" data-original-title=\"删除\"><i\n" +
            "                                                            class=\"mdi mdi-window-close\"></i></a>\n" +
            "                                                </div>\n" +
            "                                            </td>\n" +
            "                                        </tr>";
    }

    return html;
}

// 数据库字段 isFrobid ,是否禁用
//type 为 false 时 是 开启
//type 为true 时 是禁用
function batchOpenOrForbid(type) {
    var ids = new Array();
    $('input[name="ids[]"]').each(function () {
        if($(this).prop('checked')){
            ids.push($(this).val())
        }
    })
    console.log(ids);
    if(ids.length <= 0){
        Confirm.error("请选择需要操作的用户");
        return false;
    }
    var data = {
        ids:ids,
        type:type
    }
    console.log(data);
    Ajax.put("/admin/user/batchForbid",data,function(result){
        lightyear.loading("hide")
        if(result.status){
            Notify.success("更新成功");
            //刷新表格
            Pagination.flush($('#pagination-container'));
        } else {
            Notify.error(result.msg);
        }
    })
}


// 批量删除
function batchDelete() {
    var ids = new Array();
    $('input[name="ids[]"]').each(function () {
        if($(this).prop('checked')){
            ids.push($(this).val())
        }
    })
    if(ids.length <= 0){
        Confirm.error("请选择要删除的用户")
        return false;
    }
    var data = {
        ids:ids
    }
    Ajax.delete("/admin/users",data,function(result){
        lightyear.loading("hide")
        if(result.status){
            //分页刷新
            Pagination.deleteAndFlush($('#pagination-container'),ids.length);
            Notify.success("删除成功")
        } else{
            Notify.error("删除失败")
        }
    })
}