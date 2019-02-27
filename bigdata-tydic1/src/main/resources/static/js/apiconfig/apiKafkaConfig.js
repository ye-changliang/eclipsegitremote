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
        $('#tb_apis_kafka').bootstrapTable({
            url: EventInit.getCtx() + '/api-kafka-config/kafkas',         //请求后台的URL（*）
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
                field: 'kafkaCode',
                title: '生产者编号'
            }, {
                field: 'key',
                title: '属性值'
            }, {
                field: 'value',
                title: '实际值'
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.limit,   //页面大小
            page: params.offset / params.limit,  //页码
            kafkaCode: $("#kafkaCode").val(),
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

    oInit.Init = function () {
        //查询按钮
        $('#btn_query').on('click', function () {
            $('#tb_apis_kafka').bootstrapTable('refresh', {query: TableInit.queryParams});
        });

        //新增事件
        $('#btn_add').on('click', function () {
            $from = $('#myModal').find('#formApi');
            $from[0].reset();
            $from.find('.selectpicker').selectpicker('refresh');
        });

        //修改事件 需要选中table
        $('#btn_update').on('click', function () {
            var rows = $("#tb_apis_kafka").bootstrapTable('getSelections');
            if (rows.length == 0) {
                toastr.info("请选择一条记录");
                return false;
            }
            if (rows.length > 1) {
                toastr.info("选中超过一行");
                return false;
            }
            var item = rows[0];
            var postData = $('#myModal').find('#formApi').serialize();
            for (var index in postData.split("&")) {
                var name = postData.split("&")[index];
                if ($('#myModal').find('#formApi').find('[name="' + name.replace(/=.*/g, "") + '"]').prop("tagName").toLowerCase() == "select") {
                    $('#myModal').find('#formApi').find('[name="' + name.replace(/=.*/g, "") + '"]').selectpicker("val", item[name.replace(/=.*/g, "")]);
                } else {
                    $('#myModal').find('#formApi').find('[name="' + name.replace(/=.*/g, "") + '"]').val(item[name.replace(/=.*/g, "")]);
                }
            }

        });

        //保存事件
        $('#myModal').find("#save").on('click', function () {
            $from = $('#myModal').find('#formApi');
            EventInit.showLoading("保存中。。。");
            $.ajax({
                type: "POST",
                url: EventInit.getCtx() + "/api-kafka-config/save",
                data: $from.serialize(),
                success: function (data) {
                    if (data) {
                        toastr.success('保存成功！');
                        $('#myModal').modal('hide');
                        $('#tb_apis_kafka').bootstrapTable('refresh');
                    } else {
                        toastr.error('保存失败!');
                    }
                    EventInit.hideLoading();
                },
                error: function () {
                    toastr.error('服务器异常！');
                    EventInit.hideLoading();
                }
            });
        });


        //确认删除事件
        $('#btn_delete').on('click', function () {
            var rows = $("#tb_apis_kafka").bootstrapTable('getSelections');
            if (rows.length == 0) {
                toastr.info("请选择一条记录");
                return false;
            }
        });
        $('#delete-confirm').find('#delete-sure').on('click', function () {
            var rows = $("#tb_apis_kafka").bootstrapTable('getSelections');
            var items = {ids: []};
            $(rows).each(function (index, item, array) {
                items.ids.push(item.id);
            });
            $.ajax({
                type: "DELETE",
                url: EventInit.getCtx() + "/api-kafka-config/delete",
                dataType: "json",
                contentType: "application/json", // 指定这个协议很重要
                data: JSON.stringify(items.ids),
                success: function (data) {
                    if (data) {
                        toastr.success('删除成功！');
                        $('#tb_apis_kafka').bootstrapTable('refresh');
                    } else {
                        toastr.error('删除失败！');
                    }
                    $('#delete-confirm').modal('hide');
                },
                error: function () {
                    toastr.error('服务器异常！');
                }
            });
        });
    };

    return oInit;
};