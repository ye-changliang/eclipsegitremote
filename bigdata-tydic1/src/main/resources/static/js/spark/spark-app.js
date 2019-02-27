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
        $('#tb_spark').bootstrapTable({
            url:EventInit.getCtx()+ '/spark-app/findSparkByIpAndConfigNameAndAppNameIsNotNull',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
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
            showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            responseHandler:oTableInit.responseData,
            columns: [{
                checkbox: true
            }, {
                field: 'sparkId',
                visible:false,
                title: 'ID'
            }, {
                field: 'hostServeId',
                visible:false,
                title: '服务Id'
            }, {
                field: 'ip',
                title: '主机Ip'
            }, {
                field: 'rent',
                title: '租户'
            }
            , {
                field: 'appName',
                title: '应用名'
            }, {
                field: 'sparkConfig',
                title: '配置文件名称'
            }, {
                field: 'sparkConfigPath',
                title: '配置文件路径'
            }, {
                field: 'sparkConfigDesc',
                title: '配置文件描述'
            }, {
                field: 'updateDate',
                title: '更新时间'
            }, {
                field: 'update4aCode',
                title: '修改人工号'
            }, ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.limit,   //页面大小
            page: params.offset/params.limit,  //页码
            ip: $("#ip").val(),
            configType:"spark",
            sparkConfig:$("#sparkConfigName").val(),
            appName:$("#sparkAppName").val()
        };
        return temp;
    };
    oTableInit.responseData = function (res) {
        return {
            total:res.totalElements,
            rows:res.content,
        };
    };
    return oTableInit;
};

$("#sparkConfig").bind("onblur",function () {
    var fileName=$("#sparkConfig").val();
    if(fileName=='')
        return
    $.ajax({
        type: "POST",
        url:EventInit.getCtx()+ "/spark/checksparksByConfigName",
        data: {configName:fileName},
        success: function (data) {
            if (data) {
                toastr.error('已有该文件名，请重新命名！');
            } else {
                toastr.error('文件名有效！');
            }
        },
        error: function () {
            toastr.error('服务器异常！');
        }
    });
})

var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        $('#btn_query').on('click',function () {
            $('#tb_spark').bootstrapTable('refresh', {query: TableInit.queryParams});
        });

    };
    //新增事件
    $('#btn_add').on('click', function () {
        setModalValue(0);
    });
    //模态框弹出事件
    $('#myModal').on('show.bs.modal', function (event) {
        var $button = $(event.relatedTarget);
        var value = $button.data("links");
        value && setModalValue(value);

    });

    function setModalValue(value) {
        if (value) {
            var $modal = $('#myModal');
            $modal.find("#sparkId").val(value.sparkId);
            $modal.find("#hostIp").val(value.ip);
            $modal.find("#appName").val(value.appName);
            $modal.find("#sparkConfig").val(value.sparkConfig);
            $modal.find("#sparkConfigDesc").val(value.sparkConfigDesc);
            $modal.find("#sparkConfigContent").val(value.sparkConfigContent);
            $modal.find("#myModalLabel").html("修改spark配置信息")
        } else {
            var $modal = $('#myModal');
            $modal.find("#sparkId").val("");
            $modal.find("#hostIp").val("");
            $modal.find("#appName").val("");
            $modal.find("#sparkConfig").val("");
            $modal.find("#sparkConfigDesc").val("");
            $modal.find("#sparkConfigContent").val("");
            $modal.find("#myModalLabel").html("新增spark配置信息")
        }
        $.ajax({
            type: "POST",
            url:EventInit.getCtx()+ "/host-serve/findAllHostServeListByServeName",
            data: {hostServeName:"spark"},
            success: function (data) {
                if (data) {
                    $("#hostServeList").find("option").not(":first").remove()
                    for(var i=0;i<data.length;i++){
                        if(value!=undefined && data[i]["hostServeId"]==value.hostServeId){
                            $("#hostServeList").append("<option selected value='"+data[i]["hostServeId"]+"'>"+data[i]["hostServeName"]+"（"+data[i]["host"]+"【"+data[i]["rent"]+"】）</option>");
                        }else {
                            $("#hostServeList").append("<option  value='"+data[i]["hostServeId"]+"'>"+data[i]["hostServeName"]+"（"+data[i]["host"]+"【"+data[i]["rent"]+"】）</option>");
                        }
                    }
                } else {
                    toastr.error('服务器异常！保存失败');
                }
            },
            error: function () {
                toastr.error('服务器异常！');
            }
        });
    }

    //保存事件
    $('#myModal').find("#save").on('click', function () {
        $from = $('#myModal').find('#sparkInfo');
        $('#loadingModal').modal('show');
        $.ajax({
            type: "POST",
            url:EventInit.getCtx()+ "/spark-app/sparkAppSave",
            data: $from.serialize(),
            success: function (data) {
                if (data) {
                    toastr.success('保存成功！');
                } else {
                    toastr.error('服务器异常！保存失败');
                }
                $('#myModal').modal('hide');
                $('#tb_spark').bootstrapTable('refresh');
                $('#loadingModal').modal('hide');
            },
            error: function () {
                toastr.error('服务器异常！');
                $('#loadingModal').modal('hide');
            }
        });
    });

    //服务器连接事件
    $('#myModal').find("#testHostConnet").on('click', function () {
        $.ajax({
            type: "POST",
            url:EventInit.getCtx()+ "/host-serve/testHostConnet",
            data: {hostServeId: $('#myModal').find('#hostServeList').val()},
            success: function (data) {
                if (data) {
                    toastr.success('连接成功！');
                } else {
                    toastr.error('服务器连接失败！');
                }
            },
            error: function () {
                toastr.error('服务器异常！');
            }
        });
    });
    //修改事件 需要选中table
    $('#btn_update').on('click', function () {
        var rows = $("#tb_spark").bootstrapTable('getSelections');
        if (rows.length == 0) {
            toastr.info("请选择一条记录");
            return false;
        }
        if (rows.length > 1) {
            toastr.info("选中超过一行");
            return false;
        }
        var item = rows[0];
        setModalValue(item);
    });
    //确认删除事件
    $('#btn_delete').on('click', function () {
        var rows = $("#tb_spark").bootstrapTable('getSelections');
        if (rows.length == 0) {
            toastr.info("请选择一条记录");
            return false;
        }
    });
    $('#delete-confirm').find('#delete-sure').on('click', function () {
        var rows = $("#tb_spark").bootstrapTable('getSelections');
        $(rows).each(function (index, item, array) {
            delete item["updateDate"];
        });
        $('#loadingModal').modal('show');
        $.ajax({
            type: "DELETE",
            url:EventInit.getCtx()+ "/spark-app/batchDeleteSparks",
            dataType:"json",
            contentType:"application/json", // 指定这个协议很重要
            data: JSON.stringify(rows),
            success: function (data) {
                if (data) {
                    toastr.success("刪除成功");
                    $('#tb_spark').bootstrapTable('refresh');
                } else {
                    toastr.error('删除失败！請檢查該主機是否已被配置');
                }
                $('#delete-confirm').modal('hide');
                $('#loadingModal').modal('hide');
            },
            error: function () {
                toastr.error('服务器异常！');
                $('#loadingModal').modal('hide');
            }
        });
    });

    //确认删除事件
    $('#btn_deleteData').on('click', function () {
        var rows = $("#tb_spark").bootstrapTable('getSelections');
        if (rows.length == 0) {
            toastr.info("请选择一条记录");
            return false;
        }
    });
    $('#myDeletModal').find('#delete').on('click', function () {
        var rows = $("#tb_spark").bootstrapTable('getSelections');
        $(rows).each(function (index, item, array) {
            delete item["updateDate"];
        });
        $('#loadingModal').modal('show');
        $.ajax({
            type: "DELETE",
            url:EventInit.getCtx()+ "/spark-app/batchDeleteSparksData",
            dataType:"json",
            contentType:"application/json", // 指定这个协议很重要
            data: JSON.stringify(rows),
            success: function (data) {
                if (data) {
                    toastr.success("刪除成功");
                    $('#tb_spark').bootstrapTable('refresh');
                } else {
                    toastr.error('删除失败！請檢查該主機是否已被配置');
                }
                $('#myDeletModal').modal('hide');
                $('#loadingModal').modal('hide');
            },
            error: function () {
                toastr.error('服务器异常！');
                $('#loadingModal').modal('hide');
            }
        });
    });
    ///////////////////////////////////////////////////////////////////////////
    //备份事件 需要选中table
    $('#btn_back').on('click', function () {
        setValue("back");
    });
    //重启事件 需要选中table
    $('#btn_reset').on('click', function () {
        setValue("reset");
    });

    //停止事件 需要选中table
    $('#btn_stop').on('click', function () {
        setValue("stop")
    });

    function setValue(v) {
        if(!v)return
        var doWhat;
        switch(v)
        {
            case "back":
                doWhat="备份";
                break;
        }
        var rows = $("#tb_spark").bootstrapTable('getSelections');
        if (rows.length == 0) {
            toastr.info("请选择一条记录");
            return false;
        }
        if (rows.length > 1) {
            toastr.info("选中超过一行");
            return false;
        }
        var $modal = $('#sparkModal');
        $modal.find("#title").html(doWhat+"确认框");
        $modal.find("#content").html("确定要"+doWhat+"吗？");
        $modal.find("#value").val(v);
    }

    ///////////////停止、重启、备份确定事件//////////////////////////////////////////
    $('#sparkModal').find('#sure').on('click', function () {
        var $modal = $('#sparkModal');
        var value=$modal.find("#value").val()
        doMian(value)
    });

    function doMian(value) {
        if(!value)return
        var rows = $("#tb_spark").bootstrapTable('getSelections');
        var sparkId=rows[0].sparkId;
        var url="";
        var doWhat;
        switch(value)
        {
            case "back":
                url=EventInit.getCtx()+"/spark-app/backSparks";
                doWhat="备份";
                break;
        }
        $('#loadingModal').modal('show');
        $.ajax({
            type: "POST",
            url: url,
            data: {sparkId:sparkId},
            success: function (data) {
                if (data) {
                    toastr.success(doWhat+'成功！');
                } else {
                    toastr.error('服务器异常！'+doWhat+'失败');
                }
                $('#sparkModal').modal('hide');
                $('#tb_spark').bootstrapTable('refresh');
                $('#loadingModal').modal('hide');
            },
            error: function () {
                toastr.error('服务器异常！');
                $('#loadingModal').modal('hide');
            }
        });
    }
    return oInit;
};
