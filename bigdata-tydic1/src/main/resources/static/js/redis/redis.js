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
        $('#tb_redis').bootstrapTable({
            url:EventInit.getCtx()+ '/redis/findRedisByIp',         //请求后台的URL（*）
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
                field: 'redisId',
                visible:false,
                title: 'ID'
            }, {
                field: 'ip',
                title: '主机Ip'
            }, {
                field: 'redisConfig',
                title: '配置文件名称'
            }, {
                field: 'redisConfigPath',
                title: '配置文件路径'
            }, {
                field: 'redisConfigDesc',
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
            configType:"redis",
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


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        $('#btn_query').on('click',function () {
            $('#tb_redis').bootstrapTable('refresh', {query: TableInit.queryParams});
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
            $modal.find("#redisId").val(value.redisId);
            $modal.find("#hostIp").val(value.ip);
            $modal.find("#appName").val(value.appName);
            $modal.find("#redisConfig").val(value.redisConfig);
            $modal.find("#redisConfigDesc").val(value.redisConfigDesc);
            $modal.find("#redisConfigContent").val(value.redisConfigContent);
            $modal.find("#myModalLabel").html("修改redis配置信息")
        } else {
            var $modal = $('#myModal');
            $modal.find("#redisId").val("");
            $modal.find("#hostIp").val("");
            $modal.find("#appName").val("");
            $modal.find("#redisConfig").val("");
            $modal.find("#redisConfigDesc").val("");
            $modal.find("#redisConfigContent").val("");
            $modal.find("#myModalLabel").html("新增redis配置信息")
        }
        $.ajax({
            type: "POST",
            url:EventInit.getCtx()+ "/host-serve/findAllHostServeListByServeName",
            data: {hostServeName:"redis"},
            success: function (data) {
                if (data) {
                    $("#hostServeList").find("option").not(":first").remove()
                    for(var i=0;i<data.length;i++){
                        if(value!=undefined && data[i]["hostServeId"]==value.hostServeId){
                            $("#hostServeList").append("<option selected value='"+data[i]["hostServeId"]+"'>"+data[i]["hostServeName"]+"（"+data[i]["host"]+"）</option>");
                        }else {
                            $("#hostServeList").append("<option  value='"+data[i]["hostServeId"]+"'>"+data[i]["hostServeName"]+"（"+data[i]["host"]+"）</option>");
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
        debugger
        $from = $('#myModal').find('#redisInfo');
        $('#loadingModal').modal('show');
        $.ajax({
            type: "POST",
            url:EventInit.getCtx()+ "/redis/redisSave",
            data: $from.serialize(),
            success: function (data) {
                if (data) {
                    toastr.success('保存成功！');
                } else {
                    toastr.error('服务器异常！保存失败');
                }
                $('#myModal').modal('hide');
                $('#tb_redis').bootstrapTable('refresh');
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
            url:EventInit.getCtx()+ "/redis/testHostConnet",
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
        var rows = $("#tb_redis").bootstrapTable('getSelections');
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
        var rows = $("#tb_redis").bootstrapTable('getSelections');
        if (rows.length == 0) {
            toastr.info("请选择一条记录");
            return false;
        }
    });
    $('#delete-confirm').find('#delete-sure').on('click', function () {
        var rows = $("#tb_redis").bootstrapTable('getSelections');
        $(rows).each(function (index, item, array) {
            delete item["updateDate"];
        });
        $('#loadingModal').modal('show');
        $.ajax({
            type: "DELETE",
            url: EventInit.getCtx()+"/redis/batchDeleteRedis",
            dataType:"json",
            contentType:"application/json", // 指定这个协议很重要
            data: JSON.stringify(rows),
            success: function (data) {
                if (data) {
                    toastr.success("刪除成功");
                    $('#tb_redis').bootstrapTable('refresh');
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
        var rows = $("#tb_redis").bootstrapTable('getSelections');
        if (rows.length == 0) {
            toastr.info("请选择一条记录");
            return false;
        }
    });
    $('#myDeletModal').find('#delete').on('click', function () {
        var rows = $("#tb_redis").bootstrapTable('getSelections');
        $(rows).each(function (index, item, array) {
            delete item["updateDate"];
        });
        $('#loadingModal').modal('show');
        $.ajax({
            type: "DELETE",
            url: EventInit.getCtx()+"/redis/batchDeleteRedisData",
            dataType:"json",
            contentType:"application/json", // 指定这个协议很重要
            data: JSON.stringify(rows),
            success: function (data) {
                if (data) {
                    toastr.success("刪除成功");
                    $('#tb_redis').bootstrapTable('refresh');
                } else {
                    toastr.error('删除失败');
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
            case "reset":
                doWhat="重启";
                break;
            case "stop":
                doWhat="停止";
                break;
        }
        var rows = $("#tb_redis").bootstrapTable('getSelections');
        if (rows.length == 0) {
            toastr.info("请选择一条记录");
            return false;
        }
        if (rows.length > 1) {
            toastr.info("选中超过一行");
            return false;
        }
        var $modal = $('#redisModal');
        $modal.find("#title").html(doWhat+"确认框");
        $modal.find("#content").html("确定要"+doWhat+"吗？");
        $modal.find("#value").val(v);
    }

    ///////////////停止、重启、备份确定事件//////////////////////////////////////////
    $('#redisModal').find('#sure').on('click', function () {
        var $modal = $('#redisModal');
        var value=$modal.find("#value").val()
        doMian(value)
    });

    function doMian(value) {
        if(!value)return
        var rows = $("#tb_redis").bootstrapTable('getSelections');
        var redisId=rows[0].redisId;
        var url="";
        var doWhat;
        switch(value)
        {
            case "back":
                url=EventInit.getCtx()+"/redis/backrediss";
                doWhat="备份";
                break;
            case "reset":
                url=EventInit.getCtx()+"/redis/resetrediss";
                doWhat="重启";
                break;
            case "stop":
                url=EventInit.getCtx()+"/redis/stoprediss";
                doWhat="停止";
                break;
        }
        $('#loadingModal').modal('show');
        $.ajax({
            type: "POST",
            url: url,
            data: {redisId:redisId},
            success: function (data) {
                if (data) {
                    toastr.success(doWhat+'成功！');
                } else {
                    toastr.error('服务器异常！'+doWhat+'失败');
                }
                $('#redisModal').modal('hide');
                $('#tb_redis').bootstrapTable('refresh');
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
