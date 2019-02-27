$(function () {
    //2.初始化Button的点击事件
    var oButtonInit1 = new ButtonInit1();
    oButtonInit1.Init();
});

var OnlineTableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_sparkOnline').bootstrapTable({
            url:EventInit.getCtx()+'/spark-serve/querySparkOnlineFile',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
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
            columns:  [{
                checkbox: true
            },  {
                field: 'ip',
                title: '主机Ip'
            }, {
                field: 'rent',
                title: '租户'
            }
              , {
                    field: 'sparkConfig',
                    title: '配置文件名称'
                }, {
                    field: 'sparkConfigPath',
                    title: '配置文件路径'
                }, {
                    field: 'sparkConfigDesc',
                    title: '配置文件描述'
                } ]
        });
    };
    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var hostServeId=$("#hostServeList1").val();
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            hostServeId:hostServeId,
        };
        return temp;
    };
    oTableInit.responseData = function (res) {

    };
    return oTableInit;
};


var ButtonInit1 = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {

    };

    //模态框弹出事件
    $('#sparkOnline').on('show.bs.modal', function (event) {
        $.ajax({
            type: "POST",
            url: EventInit.getCtx()+"/host-serve/findAllHostServeListByServeName",
            data: {hostServeName:"spark"},
            success: function (data) {
                if (data) {
                    $("#hostServeList1").find("option").not(":first").remove()
                    for(var i=0;i<data.length;i++){
                        if(value!=undefined && data[i]["hostServeId"]==value.hostServeId){
                            $("#hostServeList1").append("<option selected value='"+data[i]["hostServeId"]+"'>"+data[i]["hostServeName"]+"（"+data[i]["host"]+"【"+data[i]["rent"]+"】）</option>");
                        }else {
                            $("#hostServeList1").append("<option  value='"+data[i]["hostServeId"]+"'>"+data[i]["hostServeName"]+"（"+data[i]["host"]+"【"+data[i]["rent"]+"】）</option>");
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

    });

    $('#sparkOnline').find("#hostServeList1").on('change', function () {
        //1.初始化Table
        var onlineOTable = new OnlineTableInit();
        onlineOTable.Init();
        $('#tb_sparkOnline').bootstrapTable('refresh');
    })

    //跟新到数据库事件
    $('#sparkOnline').find("#save-import").on('click', function () {
        var rows = $("#tb_sparkOnline").bootstrapTable('getSelections');
        if (rows.length == 0) {
            toastr.info("请选择一条记录");
            return false;
        }
        $('#loadingModal').modal('show');
        $.ajax({
            type: "POST",
            url: EventInit.getCtx()+"/spark-serve/sparkOnlineImport",
            dataType:"json",
            contentType:"application/json", // 指定这个协议很重要
            data: JSON.stringify(rows),
            success: function (data) {
                if (data) {
                    toastr.success("导入成功");
                    $('#tb_sparkOnline').bootstrapTable('refresh');
                    $('#tb_spark').bootstrapTable('refresh');
                } else {
                    toastr.error('导入失败');
                }
                $('#sparkOnline').modal('hide');
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
