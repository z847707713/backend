$(function () {

    var url = "/admin/permissions/list";
    pageload(url);

    $('#search-form').on('submit', function (event) {
        var field = $('#search-field').val()
        var value = $('#keywork-input').val()
        var reloadUrl = url + "?" + field + "=" + value;
        pageload(reloadUrl)
        return false;
    })

})

function pageload(url) {
    $('#pagination-container').pagination({
        dataSource: url, //请求的url
        locator: 'data.records', //返回值data的位置
        totalNumberLocator: function (res) { //返回值总数量的位置
            return Math.floor(res.data.total);
        },
        pageNumber: 1,
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
            $("#my-check-all").prop("checked", false);
            //渲染表格
            $('#data-container').html(template(data));


            // $('.checkbox-forbid').change(function () {
            //     //获取更改的id
            //     var id = $(this).attr('data-id');
            //     // 模拟禁用checkbox 功能
            //     $(this).prop("checked", !$(this).prop("checked"))
            //     // 具体禁用逻辑
            //     forbid($(this), id);
            //     return false;
            // })

        }, beforeRender: function () {
            lightyear.loading('show');
        }
    });

}


var baseUrl = "/admin/permission";

function template(data) {
    var html = '';

    if (data.length <= 0) {
        html += '<tr><td colspan="13" style="text-align: center">没有数据</td></tr>'
        return html;
    }

    for (var i = 0; i < data.length; i++) {
        html += "<tr>" +
            "                                            <td>" + data[i].id + "</td>\n" +
            "                                            <td>" + data[i].permission + "</td>\n" +
            "                                            <td>" + data[i].name + "</td>\n" +
            "                                            <td>" + Table.textFormate(data[i].pDesc) + "</td>\n" +
            "                                            <td>" + data[i].url + "</td>\n";

        if (data[i].type) {
            html += '<td><i class="mdi mdi-adjust"></i></td>'
        } else {
            html += '<td><icon class="mdi mdi-library-books "></icon></td>'
        }
        html +=
            "                                            <td>" + Table.textFormate(data[i].parent) + "</td>\n" +
            "                                            <td>" + data[i].createBy + "</td>\n" +
            "                                            <td>" + data[i].createTime + "</td>\n" +
            "                                            <td>" + data[i].updateBy + "</td>\n" +
            "                                            <td>" + data[i].updateTime + "</td>\n" +
            "                                            <td>\n" +
            "                                                <div class=\"btn-group\">\n" +
            "                                                    <a class=\"btn btn-xs btn-default\" href=\"#!\" onclick=\"edit('" + data[i].id + "')\" title=\"\"\n" +
            "                                                       data-toggle=\"tooltip\" data-original-title=\"编辑\"><i\n" +
            "                                                            class=\"mdi mdi-pencil\"></i></a>\n" +
            "                                                    <a class=\"btn btn-xs btn-default\" href=\"#!\" onclick=\"deleteById('" + data[i].id + "')\" title=\"\"\n" +
            "                                                       data-toggle=\"tooltip\" data-original-title=\"删除\"><i\n" +
            "                                                            class=\"mdi mdi-window-close\"></i></a>\n" +
            "                                                </div>\n" +
            "                                            </td>\n" +
            "                                        </tr>";
    }
    return html;

}


function add() {
    var jc = $.confirm({
        title: '新增用户',
        useBootstrap: false,
        boxWidth: '60%',
        content: '<div class="col-md-12"><div class="card-body"><form id="confirm-form" class="form-horizontal" action="#" method="post" enctype="multipart/form-data" onsubmit="return false;">\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="example-email-input">角色CODE</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="role-input" name="role" placeholder="角色CODE..">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="password-input">角色名</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="roleName-input" name="roleName" placeholder="角色名..">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="nickname-input">角色描述</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="roleDesc-input" name="roleDesc" placeholder="角色描述..">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                </form></div></div>',
        buttons: {
            confirm: {
                text: '确认',
                btnClass: 'btn-primary',
                action: function () {
                    var formData = $('#confirm-form').serializeObject();
                    if (verify('add', formData)) {
                        Ajax.post(baseUrl, formData, function (result) {
                            lightyear.loading("hide");
                            if (result.status) {
                                Notify.success("角色添加成功");
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


//编辑按钮点击事件
function edit(id) {
    Ajax.get(baseUrl, {id: id}, function (result) {
        lightyear.loading("hide");
        editView(result.data)
    })
}

function editView(data) {
    console.log(data);
    var confirm = $.confirm({
        title: '编辑用户',
        useBootstrap: false,
        boxWidth: '60%',
        content: '<div class="col-md-12"><div class="card-body"><form id="confirm-form-update" class="form-horizontal" action="#" method="post" enctype="multipart/form-data" onsubmit="return false;">\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="example-email-input">角色CODE</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="hidden" id="role-id" name="id"  value="' + data.id + '">\n' +
            '                      <input class="form-control" type="text" id="role-input" name="role" placeholder="角色CODE.." value="' + data.role + '">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="password-input">角色名</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="roleName-input" name="roleName" placeholder="角色名.." value="' + data.roleName + '">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                  <div class="form-group">\n' +
            '                    <label class="col-xs-12" for="nickname-input">角色描述</label>\n' +
            '                    <div class="col-xs-12">\n' +
            '                      <input class="form-control" type="text" id="roleDesc-input" name="roleDesc" placeholder="角色描述.." value="' + data.roleDesc + '">\n' +
            '                    </div>\n' +
            '                  </div>\n' +
            '                </form></div></div>',
        buttons: {
            confirm: {
                text: '更新',
                btnClass: 'btn-primary',
                action: function () {
                    var formData = $('#confirm-form-update').serializeObject();
                    if (verify('update', formData)) {
                        Ajax.put(baseUrl, formData, function (result) {
                            lightyear.loading("hide");
                            if (result.status) {
                                Notify.success("角色修改成功");
                                confirm.close();
                                Pagination.flush($('#pagination-container'));
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

function deleteById(id) {
    Confirm.alert("是否删除该角色", function () {
        Ajax.deleteByIdDiyFunction(baseUrl, id, function (result) {
            lightyear.loading('hide');
            if (result.status) {
                //刷新页面
                Pagination.deleteAndFlush($('#pagination-container'))
                Notify.success("删除成功");
            } else {
                Notify.error(result.msg);
            }
        })
    })
}

function verify(type, data) {
    if (Verify.isEmpty(data.role)) {
        Notify.error("角色编号不能为空");
        return;
    }
    if (Verify.isEmpty(data.roleName)) {
        Notify.error("角色名不能为空");
        return;
    }
    return true;
}




