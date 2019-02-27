$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();


});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_tasks').bootstrapTable({
            url: EventInit.getCtx() + '/task-audit/applies',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 530,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            responseHandler: oTableInit.responseData,
            columns: [{
                checkbox: true
            }, {
                field: 'name',
                title: '任务名称'
            }, {
                field: 'jobStatus.STATUS',
                title: '运行状态'
            }, {
                field: 'jobStatus.STARTDATE',
                title: '开始时间'
            }, {
                field: 'jobStatus.ENDDATE',
                title: '结束时间'
            }, {
                field: 'applyUser',
                title: '申请人'
            }, {
                field: 'applyTime',
                title: '申请时间'
            }, {
                field: 'applyStatus',
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return "待审核"
                    }
                    if (value == 1) {
                        return "已通过"
                    }
                    if (value == 2) {
                        return "已撤回"
                    }
                },
                title: '审核状态'
            }, {
                field: 'jobStatus',
                formatter: function (value, row, index) {
                    if (!value) {
                        return "不通过"
                    }
                    if (value.STATUS == 'end' && value.ERRORS == 0) {
                        return "通过"
                    } else {
                        return "不通过"
                    }

                },
                title: '稽核状态'
            }]
        });
        $('#tb_jobDataSource').bootstrapTable({
            url: EventInit.getCtx() + '/task-apply/findTaskDataSource',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: false,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: function (params) {
                return {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    jobId: params.taskId,
                };
            },//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: false,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            // height: 530,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'ID_JOBENTRY',
                title: '步骤Id'
            }, {
                field: 'NAME',
                title: '步骤名称'
            }, {
                field: 'PARENTID',
                title: '父步骤ID'
            }, {
                field: 'DATABASE_NAME',
                title: '服务名'
            }, {
                field: 'HOST_NAME',
                title: '主机名称(数据源)'
            }, {
                field: 'DBNAME',
                title: '数据库名'
            }, {
                field: 'PORT',
                title: '端口'
            }, {
                field: 'USERNAME',
                title: '用户'
            }],
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.limit,   //页面大小
            page: params.offset / params.limit,  //页码
            name: $("#taskName").val(),
            applyStatus: $("#applyStatus").val(),
            sort: "applyTime,desc",
        };
        return temp;
    };
    oTableInit.responseData = function (res) {
        return {
            total: res.totalElements,
            rows: res.content,
        };
    };
    return oTableInit;
};


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //查询按钮
        $('#btn_query').on('click', function () {
            $('#tb_tasks').bootstrapTable('refresh', {query: TableInit.queryParams});
        });
        //模态框弹出事件
        $('#myModal').on('show.bs.modal', function (event) {
            var $button = $(event.relatedTarget);
            var value = $button.data("message");
            var type = $button.data("type");
            $('#myModal').find('#pass_value').html(value);
            $('#myModal').find('#sure-audit').attr('type', type);
        });
        //审核
        $('#btn_audit_pass').on('click', function () {
            var rows = selectItem();
            if (!rows) {
                return false;
            }
            var item = rows[0];
            setTaskValue(item);
            $('#tb_jobDataSource').bootstrapTable('refresh', {query: {jobId: item.taskId, type: item.TYPE}});
        });

        $('#btn_check').on('click', function () {
            var rows = selectItem();
            if (!rows) {
                return false;
            }
            var item = rows[0];
            EventInit.showLoading("稽核中...");
            $.ajax({
                type: "POST",
                url: EventInit.getCtx() + "/task-audit/check",
                data: {taskId: item.taskId, taskType: item.taskType,id:item.id},
                success: function (data) {
                    var tds = $('#tb_tasks tr.selected').children();
                    if (data.jobStatus) {
                        $(tds[2]).html(data.jobStatus.STATUS);
                        $(tds[3]).html(data.jobStatus.STARTDATE);
                        $(tds[4]).html(data.jobStatus.ENDDATE);
                        if (data.jobStatus.STATUS != 'end' || data.jobStatus.ERRORS != 0) {
                            $(tds[6]).html("不通过");
                            toastr.error('稽核不通过！运行出错或者未运行结束的任务');
                        } else {
                            $(tds[8]).html("通过");
                            toastr.success("稽核通过！");
                        }
                        item.jobStatus = data.jobStatus;

                    } else {
                        $(tds[6]).html("不通过");
                        toastr.error('稽核不通过');
                    }
                    EventInit.hideLoading();
                },
                error: function () {
                    toastr.error('服务器异常！');
                    EventInit.hideLoading();
                }
            });
        });

        //撤回
        $('#but-audit-back').on('click', function () {
            audit(2);
        });
        //通过
        $('#but-audit-pass').on('click', function () {
            audit(1);
        });

        function setTaskValue(data) {
            $("#taskId").val(data.ID || data.taskId);
            $("#taskNameRel").val(data.NAME || data.name);
            $("#username").val(data.USERNAME || data.developUser);
            $("#createDate").val(data.CREATEDATE || data.createDate);
            data.TYPE || (data.TYPE = data.taskType);
            switch (data.TYPE) {
                case 13 :
                    $("#taskType").val("转换");
                    break;
                case 41 :
                    $("#taskType").val("任务");
                    break;
                default:
                    $("#taskType").val('');
                    break;
            }
            $("#taskTypeI").val(data.TYPE);
        }

        function selectItem() {
            var rows = $("#tb_tasks").bootstrapTable('getSelections');
            if (rows.length == 0) {
                toastr.info("请选择一条记录");
                return false;
            }
            if (rows.length > 1) {
                toastr.info("选中超过一行");
                return false;
            }
            return rows;
        }

        function audit(type) {
            var rows = selectItem();
            if (!rows) {
                return false;
            }
            var item = rows[0];

            if (item.applyStatus != 0) {
                toastr.success('已审核的申请！');
                return false;
            }

            var value = item.jobStatus;
            // if (type == 1) {
            if (!value) {
                toastr.success('稽核不通过！');
                return;
            }
            if (value.STATUS != 'end' || value.ERRORS != 0) {
                toastr.success('稽核不通过！运行出错或者未运行结束的任务');
                return;
            }
            // }
            EventInit.showLoading("保存中...");
            $.ajax({
                type: "POST",
                url: EventInit.getCtx() + "/task-audit/audit",
                data: {id: item.id, type: type},
                success: function (data) {
                    if (data) {
                        toastr.success('保存成功！');
                        $('#myModal').modal('hide');
                        $('#tb_tasks').bootstrapTable('refresh');
                    } else {
                        toastr.error('服务器异常！保存失败');
                    }
                    EventInit.hideLoading();
                },
                error: function () {
                    toastr.error('服务器异常！');
                    EventInit.hideLoading();
                }
            });

        }

    };


    return oInit;
};