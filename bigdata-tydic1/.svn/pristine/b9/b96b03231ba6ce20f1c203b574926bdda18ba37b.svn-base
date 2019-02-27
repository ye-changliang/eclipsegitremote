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
            url:EventInit.getCtx()+ '/redis/findAllRedisMonitorGroupBy',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: false,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
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
                field: 'status',
                formatter: function (value, row, index) {
                    return value==1?"正常":"未启动";
                },
                title: '状态'
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

    /*
        //服务器连接事件
        $('#myModal').find("#testHostConnet").on('click', function () {
            $.ajax({
                type: "POST",
                url: "/redis/testHostConnet",
                data: {hostId: $('#myModal').find('#hostList').val()},
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
        });*/


    //重启事件 需要选中table
    $('#btn_reset').on('click', function () {
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
    });

    //停止事件 需要选中table
    $('#btn_stop').on('click', function () {
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

    });

    //更新事件 需要选中table
    $('#btn_refresh').on('click', function () {
        $('#loadingModal').modal('show');
        $.ajax({
            type: "POST",
            url: EventInit.getCtx()+"/redis/refreshRediss",
            data:{},
            success: function (data) {
                if (data) {
                    toastr.success('更新成功！');
                    $('#tb_redis').bootstrapTable('refresh');
                } else {
                    toastr.error('更新失败！');
                }
                $('#loadingModal').modal('hide');
            },
            error: function () {
                toastr.error('服务器异常！');
                $('#loadingModal').modal('hide');
            }
        });
    });

    //确定停止
    $('#stop-confirm').find('#stop-sure').on('click', function () {
        var rows = $("#tb_redis").bootstrapTable('getSelections');
        var items ={ids:[]} ;
        var redisId=rows[0].redisId;
        $('#loadingModal').modal('show');
        $.ajax({
            type: "POST",
            url: EventInit.getCtx()+"/redis/stopRediss",
            data: {redisId:redisId},
            success: function (data) {
                if (data) {
                    toastr.success("停止成功");
                    $('#tb_redis').bootstrapTable('refresh');
                } else {
                    toastr.error('停止失败');
                }
                $('#stop-confirm').modal('hide');
                $('#loadingModal').modal('hide');
            },
            error: function () {
                toastr.error('服务器异常！');
                $('#loadingModal').modal('hide');
            }
        });
    });

    //确定重启
    $('#repeat-confirm').find('#repeat-sure').on('click', function () {
        var rows = $("#tb_redis").bootstrapTable('getSelections');
        var items ={ids:[]} ;
        var redisId=rows[0].redisId;
        $('#loadingModal').modal('show');
        $.ajax({
            type: "POST",
            url:EventInit.getCtx()+ "/redis/resetRediss",
            data: {redisId:redisId},
            success: function (data) {
                if (data) {
                    toastr.success("重启成功");
                    $('#tb_redis').bootstrapTable('refresh');
                } else {
                    toastr.error('重启失败');
                }
                $('#repeat-confirm').modal('hide');
                $('#loadingModal').modal('hide');
            },
            error: function () {
                toastr.error('服务器异常！');
                $('#loadingModal').modal('hide');
            }
        });
    });

    return oInit;
};
