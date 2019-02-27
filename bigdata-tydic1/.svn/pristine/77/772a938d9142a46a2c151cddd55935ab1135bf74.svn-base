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
            url: EventInit.getCtx() + '/task-apply/applies',         //请求后台的URL（*）
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
                field: 'taskId',
                title: '任务ID'
            }, {
                field: 'developUser',
                title: '开发人员'
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
                field: 'auditTime',
                title: '审核时间'
            }, {
                field: 'releaseStatus',
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return "待发布"
                    }
                    if (value == 1) {
                        return "已发布"
                    }
                },
                title: '发布状态'
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
        $('#tb_jobDataSource_release').bootstrapTable({
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
            showRefresh: true,                  //是否显示刷新按钮
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
                formatter: function (value, row, index) {
                    if (!value) {
                        return "";
                    } else {
                        var html = "<select  class='selectpicker form-control release-task' data-live-search='true'>" +
                            "<option data-msg='" + JSON.stringify(row) + "' value='" + row.ID_DATABASE + "'>" + value + "</option>" +
                            "</select>";
                        return html;
                    }
                },
                title: '服务名'
            }, {
                field: 'HOST_NAME',
                title: '主机名称'
            }, {
                field: 'DBNAME',
                title: '数据库名'
            }, {
                field: 'PORT',
                title: '端口'
            }, {
                field: 'USERNAME',
                title: '用户名'
            }, {
                field: 'HOST_NAME',
                formatter: function (value, row, index) {
                    if (!value) {
                        return ""
                    }
                    return "<button class='btn btn-primary save-release'  data-msg='" + JSON.stringify(row) + "'>保存</button>";
                },
                title: '操作'
            }],
            //表格渲染完成后
            onPostBody: function () {
                $.ajax({
                    type: "GET",
                    datatype: "json",
                    url: EventInit.getCtx() + '/task-apply/findDataSources',
                    success: function (data) {
                        for (var i = 0; i < data.length; i++) {
                            $('.release-task').append("<option data-msg='" + JSON.stringify(data[i]) + "' value='" + data[i].ID_DATABASE + "'>" + data[i].DATABASE_NAME + "</option>");
                        }
                        $('.release-task').selectpicker('refresh');
                        $('#tb_jobDataSource_release').parent().css('overflow-x','visible');
                        $('#tb_jobDataSource_release').parent().css('overflow-y','visible');
                        $('.release-task').on('change', function (event) {
                            var id = $(event.target).val();
                            var $option = $(event.target).find('option[value=' + id + ']');
                            var data = $option.data("msg");
                            var tr = $(event.target).parent().parent().parent().children();
                            $(tr[5]).html(data.HOST_NAME);
                            $(tr[6]).html(data.DBNAME);
                            $(tr[7]).html(data.PORT);
                            $(tr[8]).html(data.USERNAME);
                        });

                        //保存事件
                        $('.save-release').on('click', function () {
                            var rows = $("#tb_tasks").bootstrapTable('getSelections');
                            var item = rows[0];
                            var data = $(this).data("msg");
                            var newDataBaseId = $($(this).parent().parent().children()[4]).find("select").val();
                            EventInit.showLoading("保存中...");
                            $.ajax({
                                type: "GET",
                                datatype: "json",
                                url: EventInit.getCtx() + '/task-apply/release',
                                data: {
                                    jobId: data.ID_JOB,
                                    type: data.TYPE,
                                    dataBaseId: newDataBaseId,
                                    jobEntryId: data.ID_JOBENTRY,
                                    id: item.id
                                },
                                success: function () {
                                    if (data) {
                                        toastr.success('保存成功');
                                    } else {
                                        toastr.error('保存失败');
                                    }
                                    EventInit.hideLoading();
                                },
                                error: function () {
                                    toastr.error('服务器异常');
                                    EventInit.hideLoading();
                                }
                            });
                        });

                    }
                });
            }
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.limit,   //页面大小
            page: params.offset / params.limit,  //页码
            name: $("#taskName").val(),
            applyStatus: $("#applyStatus").val(),
            releaseStatus: $("#releaseStatus").val(),
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

        //新增操作
        $('#addModel').find("#save").on('click', function () {
            $from = $('#addModel').find('#formTaskApply');
            if (!$("#username").val()) {
                toastr.error('数据不能为空！');
                return;
            }
            $.ajax({
                type: "POST",
                url: EventInit.getCtx() + "/task-apply/check",
                data: $from.serialize(),
                success: function (data) {
                    if (data) {
                        $.ajax({
                            type: "POST",
                            url: EventInit.getCtx() + "/task-apply/save",
                            data: $from.serialize(),
                            success: function (data) {
                                if (data) {
                                    toastr.success('保存成功！');
                                    $('#addModel').modal('hide');
                                    $('#tb_tasks').bootstrapTable('refresh');
                                } else {
                                    toastr.error('服务器异常！保存失败');
                                }
                            },
                            error: function () {
                                toastr.error('服务器异常！');
                            }
                        });
                    }else{
                        toastr.error('已申请的任务！');
                    }
                },
                error: function () {
                    toastr.error('服务器异常！');
                }
            });
        });

        // //查询任务
        // $('#addModel').find("#search-task-id").on('click', getTask);
        // //查询任务
        // $('#addModel').find("#search-task-name").on('click', getTask);

        //新增按钮
        $('#btn_add').on('click', function () {
            setTaskValue({});
            $('#tb_jobDataSource').bootstrapTable('refresh');
            $('#addModel').find("#save").show();
        });
        //查看事件
        $('#btn_detail').on('click', function () {
            var rows = $("#tb_tasks").bootstrapTable('getSelections');
            if (rows.length == 0) {
                toastr.info("请选择一条记录");
                return false;
            }
            if (rows.length > 1) {
                toastr.info("选中超过一行");
                return false;
            }
            var item = rows[0];
            setTaskValue(item);
            $('#tb_jobDataSource').bootstrapTable('refresh', {query: {jobId: item.taskId, type: item.TYPE}});
            $('#addModel').find("#save").hide();
        });
        //发布操作
        $('#btn_release').on('click', function () {
            var rows = $("#tb_tasks").bootstrapTable('getSelections');
            if (rows.length == 0) {
                toastr.info("请选择一条记录");
                return false;
            }
            if (rows.length > 1) {
                toastr.info("选中超过一行");
                return false;
            }
            var item = rows[0];
            if (item.applyStatus != 1) {
                toastr.info("未通过审核的申请");
                return false;
            }
            setTaskValueRelease(item);
            $('#tb_jobDataSource_release').bootstrapTable('refresh', {query: {jobId: item.taskId, type: item.TYPE}});

        });

        $('#releaseModel button[name="refresh"]').off('click').on('click', function () {
            var rows = $("#tb_tasks").bootstrapTable('getSelections');
            var item = rows[0];
            $('#tb_jobDataSource_release').bootstrapTable('refresh', {query: {jobId: item.taskId, type: item.TYPE}});
        });


        var $input = $("#taskId");
        $input.typeahead({
            updater: function (item) {
                setTaskValue(item);
                $('#tb_jobDataSource').bootstrapTable('refresh', {query: {jobId: item.ID, type: item.TYPE}});
                return item.ID;
            },
            source: function (query, process) {
                if(isNaN(query)){return []}
                return $.ajax({
                    url: EventInit.getCtx() + "/task-apply/findTask",
                    type: 'get',
                    data: {taskId: query, name: ''},
                    success: function (result) {
                        var items = []
                        $(result).each(function (index, item) {
                            item.name = "ID:" + item.ID + "--名称：" + item.NAME;
                            item.id = item.ID;
                            items.push(item);
                        });
                        return process(items);
                    }
                });
            },
            items: 20
        });

        var $taskNameRel = $("#taskNameRel");
        $taskNameRel.typeahead({
            updater: function (item) {
                setTaskValue(item);
                $('#tb_jobDataSource').bootstrapTable('refresh', {query: {jobId: item.ID, type: item.TYPE}});
                return item.NAME;
            },
            source: function (query, process) {
                return $.ajax({
                    url: EventInit.getCtx() + "/task-apply/findTask",
                    type: 'get',
                    data: {taskId: '', name: query},
                    success: function (result) {
                        var items = []
                        $(result).each(function (index, item) {
                            item.name = "ID:" + item.ID + "--名称：" + item.NAME;
                            item.id = item.ID;
                            items.push(item);
                        });
                        return process(items);
                    }
                });
            },
            items: 20
        });

    };

    function getTask() {
        $.ajax({
            type: "get",
            url: EventInit.getCtx() + "/task-apply/findTask",
            data: {taskId: $("#taskId").val(), name: $("#taskNameRel").val()},
            success: function (data) {

            },
            error: function () {
            }
        });
    }

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

    function setTaskValueRelease(data) {
        $("#release-taskId").val(data.ID || data.taskId);
        $("#release-taskNameRel").val(data.NAME || data.name);
        $("#release-username").val(data.USERNAME || data.developUser);
        $("#release-createDate").val(data.CREATEDATE || data.createDate);
        data.TYPE || (data.TYPE = data.taskType);
        switch (data.TYPE) {
            case 13 :
                $("#release-taskType").val("转换");
                break;
            case 41 :
                $("#release-taskType").val("任务");
                break;
            default:
                $("#release-taskType").val('');
                break;
        }
        $("#release-taskTypeI").val(data.TYPE);
    }

    return oInit;
};