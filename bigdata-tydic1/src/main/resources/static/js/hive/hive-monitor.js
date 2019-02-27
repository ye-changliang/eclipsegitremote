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
        $('#tb_hivemonitor').bootstrapTable({
            url:EventInit.getCtx()+'/hive/hivemonitorList',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#',                //工具按钮用哪个容器
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
            },{
                field: 'id',
                visible:false,
                title: 'ID'
               }
           
              ,{
                 field: 'clusterName',
                 title: '集群分类'
                }
            , {
                field: 'hiveName',
                title: '连接分类'
            }, {
                field: 'totalNum',
                title: 'Hive最大连接数'
            }, {
                field: 'nowNum',
                title: '当前连接数'
            }, {
                field: 'idleNum',
                title: '可用连接数'
            }, 
//            {
//                formatter: function (value, row, index) {
//                    return  "<a href='' data-links='" + JSON.stringify(row) + "' data-toggle=\"modal\" data-target=\"#myModal\">修改&nbsp;&nbsp;</a>"
//                            +"<a href='' data-links='" + JSON.stringify(row) + "' data-toggle=\"modal\" data-target=\"#myDeletModal\">&nbsp;&nbsp;删除</a>"
//                        ;
//                },
//                title: '操作'
//            }, 
            ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.limit,   //页面大小
            page: params.offset/params.limit,  //页码
            clusterName: $("#clusterName").val(),
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
            $('#tb_hivemonitor').bootstrapTable('refresh', {query: TableInit.queryParams});
        });

    };

    //新增事件
    $('#btn_add').on('click', function () {
        setModalValue(0);
    });
    function setModalValue(value) {
        if (value) {
            var $modal = $('#myModal');
            $modal.find("#clusterNames").val(value.clusterName);
            $modal.find("#hiveNames").val(value.hiveName);
            $modal.find("#totalNums").val(value.totalNum);
            $modal.find("#nowNums").val(value.nowNum);
            $modal.find("#idleNums").val(value.idleNum);
            $modal.find("#ids").val(value.id)
        } else {
            var $modal = $('#myModal');
            $modal.find("#clusterNames").val('');
            $modal.find("#hiveNames").val('');
            $modal.find("#totalNums").val('');
            $modal.find("#nowNums").val('');
            $modal.find("#idleNums").val('');
            $modal.find("#ids").val('');
        }
    }
    //模态框弹出事件
    $('#myModal').on('show.bs.modal', function (event) {
        var $button = $(event.relatedTarget);
        var value = $button.data("links");
        value && setModalValue(value);
    });

    //模态框删除弹出事件
    $('#myDeletModal').on('show.bs.modal', function (event) {
        var $button = $(event.relatedTarget);
        var value = $button.data("links");
        if (value) {
            var $modal = $(this);
            $modal.find("#idsx").val(value.clusterName);
        }
    });
    //保存事件
    $('#myModal').find("#save").on('click', function () {
        debugger
        $from = $('#myModal').find("#hivemonitorsave");
        $.ajax({
            type: "POST",
            url: EventInit.getCtx()+"/hive/HiveMonitorSave",
            data: $from.serialize(),
            success: function (data) {
                if (data) {
                    toastr.success('保存成功！');
                } else {
                    toastr.error('服务器异常！保存失败');
                }
                $('#myModal').modal('hide');
                $('#tb_hivemonitor').bootstrapTable('refresh');
            },
            error: function () {
                toastr.error('服务器异常！');
            }
        });
    });
    //删除事件
    $('#myDeletModal').find("#delete").on('click', function () {
        var rows = $("#tb_hivemonitor").bootstrapTable('getSelections');
        var items ={ids:[]} ;
        $(rows).each(function (index, item, array) {
            items.ids.push(item.id);
        });
       var value=$("#idsx").val();
        if(!value)
            return;
        var id=value;
        $.ajax({
            type: "POST",
            url: EventInit.getCtx()+"/hive/deleteHiveMonitor",
            dataType:"json",
            contentType:"application/json", // 指定这个协议很重要
            data:JSON.stringify(rows),
            success: function (data) {
                if (data) {
                    if(data=="删除失败"){
                        toastr.error(data);
                    }else if(data=="删除成功"){
                        toastr.success(data);
                    }else{
                        toastr.error(data);
                    }
                    $('#tb_hivemonitor').bootstrapTable('refresh');
                } else {
                    toastr.error('服务器异常！保存失败');
                }
                $('#myDeletModal').modal('hide');
                $('#tb_hivemonitor').bootstrapTable('refresh');
            },
            error: function () {
                toastr.error('服务器异常！');
            }
        });
    });

    //修改事件 需要选中table
    $('#btn_update').on('click', function () {
        var rows = $("#tb_hivemonitor").bootstrapTable('getSelections');
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
        var rows = $("#tb_hivemonitor").bootstrapTable('getSelections');
        if (rows.length == 0) {
            toastr.info("请选择一条记录");
            return false;
        }
    });
    $('#delete-confirm').find('#delete-sure').on('click', function () {
        var rows = $("#tb_hivemonitor").bootstrapTable('getSelections');
        var items ={ids:[]} ;
        $(rows).each(function (index, item, array) {
            items.ids.push(item.id);
        });
        $.ajax({
            type: "DELETE",
            url: EventInit.getCtx()+"/hive/batchDeleteHiveMonitor",
            dataType:"json",
            contentType:"application/json", // 指定这个协议很重要
            data: JSON.stringify(rows),
            success: function (data) {
                if (data) {
                    toastr.success("刪除成功");
                    $('#tb_hivemonitor').bootstrapTable('refresh');
                } else {
                    toastr.error('删除失败！請檢查該主機是否已被配置');
                }
                $('#delete-confirm').modal('hide');
            },
            error: function () {
                toastr.error('服务器异常！');
            }
        });
    });

    return oInit;
};
