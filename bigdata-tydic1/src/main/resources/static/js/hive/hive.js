
$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
    
    //3.初始化时间格式
     $("#startDate").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
      }).on("click",function(){
    	  var startTime= $("#startDate").val();
    	  console.log("jhs"+startTime);
      });
      
     $("#startDates").datetimepicker({
         format: 'yyyy-mm-dd',
         minView:'month',
         language: 'zh-CN',
         autoclose:true,
       }).on("click",function(){
       });
     
     //设置开始生效的时间
      $("#endDate").datetimepicker({
    	    format: 'yyyy-mm-dd',
    	    minView:'month',
    	    language: 'zh-CN',
    	    autoclose:true,
    	  }).on("click",function(){
    	  });
      
      //设置结束时间
      $("#endDates").datetimepicker({
  	    format: 'yyyy-mm-dd',
  	    minView:'month',
  	    language: 'zh-CN',
  	    autoclose:true,
  	  }).on("click",function(){
  	  });
    
    //4.组件配置提交保存
    $("#addMessage").on("submit", function() {
    	   $.ajax({
               type: "POST",
               url: EventInit.getCtx()+ "/hive/hiveAdd",
               data: $("#addMessage").serialize(),
               success: function (data) {
                   if (data) {
                       toastr.success('保存成功！');
                       $('#tb_hivereg').bootstrapTable('refresh', {query: TableInit.queryParams});
                       $('#addMessage')[0].reset();
                   } else {
                       toastr.error('服务器异常！保存失败');
                   }
               },
               error: function () {
                   toastr.error('服务器异常！');
               }
           });
    	   return false;
    	});
    
    //5.select动态查询事件
    //获取集群列表
    getJqList();
    
});
//修改——转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
   if (cellval != null) {
	   var date2 = new Date(cellval);
	   var year = date2.getFullYear();
	   var month = date2.getMonth()+1;//js从0开始取 
	   var date = date2.getDate(); 
	   if(month<10){
		   month = "0" + month;
		  }
		  if(date<10){
		   date = "0" + date;
		  }
		  var time = year+"-"+month+"-"+date; //2009-06-12 17:18:05
        return time;
    }
}
function selectApar1(){
	var deNamess=$("#clusterNamess").val();
	var myid;
	$.ajax({
        type: "POST",
        url: EventInit.getCtx()+'/hiveselect/allHivejq',
        data: {},
        success: function (data) {
            if (data) {
            	 $("#userNamess").find("option").not(":first").remove();
                for(var i=0;i<data.length;i++){
                	if(data[i].type==1&&data[i].deName==deNamess){
                	   myid=data[i].sid;
                	}
                }
                for(var i=0;i<data.length;i++){
        			if(data[i].pid==myid&&data[i].type==3){
            			$("#userNamess").append("<option selected value='"+data[i]["deName"]+"'>"+data[i]["deName"]+"</option>");
            		}
        		}
            } else {
                toastr.error('服务器异常！');
            }
        },
        error: function () {
            toastr.error('服务器异常！');
        }
    });
}
//集群更换触发事件
function selectApar(){
	var deNamess=$("#clusterNames").val();
	$("#userNames").find("option").not(":first").remove();
	var myid;//定义存储父id
	$.ajax({
        type: "POST",
        url: EventInit.getCtx()+'/hiveselect/allHivejq',
        data: {},
        success: function (data) {
            if (data) {
            	 
                for(var i=0;i<data.length;i++){
                	if(data[i].type==1&&data[i].deName==deNamess){
                	   myid=data[i].sid;
                	}
                }
                for(var i=0;i<data.length;i++){
        			if(data[i].pid==myid&&data[i].type==3){
            			$("#userNames").append("<option selected value='"+data[i]["deName"]+"'>"+data[i]["deName"]+"</option>");
            			//$('#userNames').selectpicker('refresh');//刷新渲染 主要作用select值发生改变后的情况下在进行重新渲染的效果
            		}
        		}
                $('#userNames').selectpicker('refresh');
            } else {
                toastr.error('服务器异常！');
            }
        },
        error: function () {
            toastr.error('服务器异常！');
        }
    });
}
function getJqList(){
	$.ajax({
        type: "POST",
        url: EventInit.getCtx()+'/hiveselect/allHivejq',
        data: {},
        success: function (data) {
            if (data) {
                $("#clusterNames").find("option").not(":first").remove()
                for(var i=0;i<data.length;i++){
                	if(data[i].type==1){
                		$("#clusterNames").append("<option selected value='"+data[i]["deName"]+"'>"+data[i]["deName"]+"</option>");
                	}
                }
                $('#clusterNames').selectpicker('refresh')
            } else {
                toastr.error('服务器异常!');
            }
        },
        error: function () {
            toastr.error('服务器异常！');
        }
    });
}
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_hivereg').bootstrapTable({
            url: EventInit.getCtx()+'/hive/hiveregList',         //请求后台的URL（*）
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
            },{
                field: 'id',
                visible:false,
                title: 'ID'
               }
              ,{
                 field: 'loginId',
                 title: '4A工号'
                }
            , {
                field: 'clusterName',
                title: '集群分类'
            }, {
                field: 'userName',
                title: '租户名称'
            }, 
            {
            	field: 'userKey',
                title: '密钥'
            }, {
            	field: 'regTime',
                title: '注册时间',
                formatter: function (value, row, index) {
                	console.dir(arguments);
                    return changeDateFormat(value)
                }

            }
            , {
            	field: 'startDate',
                title: '生效时间',
                formatter: function (value, row, index) {
                    return changeDateFormat(value)
                }

            }
            , {
            	field: 'endDate',
                title: '失效时间',
                formatter: function (value, row, index) {
                    return changeDateFormat(value)
                }

            },{
                formatter: function (value, row, index) {
                    return  "<a href='' data-links='" + JSON.stringify(row) + "' data-toggle=\"modal\" data-target=\"#myModal\">修改&nbsp;&nbsp;</a>"
                            +"<a href='' data-links='" + JSON.stringify(row) + "' data-toggle=\"modal\" data-target=\"#myDeletModal\">&nbsp;&nbsp;删除</a>"
                        ;
                },
                title: '操作'
            },]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.limit,   //页面大小
            page: params.offset/params.limit,  //页码
            host: $("#hostName").val(),
            sort:"regTime,desc"
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
            $('#tb_hivereg').bootstrapTable('refresh', {query: TableInit.queryParams});
        });

    };

    //新增事件
    $('#btn_add').on('click', function () {
        setModalValue(0);
    });
    function setModalValue(value) {
        if (value) {
            var $modal = $('#myModal');
            $modal.find("#loginId").val(value.loginId);
            $modal.find("#id").val(value.id);
            $modal.find("#clusterNamess").val(value.clusterName); 
            $modal.find("#userNamess").val(value.userName);
            $modal.find("#userKeys").val(value.userKey);
            //将这三个时间进行转化处理一下
            var regstime=changeDateFormat(value.regTime);
            $modal.find("#regTimes").val(regstime);
            var startDate=changeDateFormat(value.startDate);
            $modal.find("#startDates").val(startDate);
            var endDate=changeDateFormat(value.endDate);
            $modal.find("#endDates").val(endDate);
        } else {
            var $modal = $('#myModal');
            $modal.find("#loginId").val('');
            $modal.find("#clusterNamess").val(''); 
            $modal.find("#id").val('');
            $modal.find("#userNamess").val('');
            $modal.find("#userKeys").val('');
            $modal.find("#regTimes").val('');
            $modal.find("#startDates").val('');
            $modal.find("#endDates").val('');
        }
        $.ajax({
            type: "POST",
            url: EventInit.getCtx()+'/hiveselect/allHivejq',
            data: {},
            success: function (data) {
            	//console.log(JSON.stringify(data));
            	//console.log(data[1].deName);
                if (data) {
                    $("#clusterNamess").find("option").not(":first").remove()
                    for(var i=0;i<data.length;i++){
                    	if(data[i].type==1){
                    		if(value!=undefined && data[i]["deName"]==value.clusterName){
                                $("#clusterNamess").append("<option selected value='"+data[i]["deName"]+"'>"+data[i]["deName"]+"</option>");
                            }else {
                                $("#clusterNamess").append("<option  value='"+data[i]["deName"]+"'>"+data[i]["deName"]+"</option>");
                            }
                    	}
                    }
                    
                    //租户的同步
                    var getPassword=$("#clusterNamess").val();
                    $('#clusterNamess').selectpicker('refresh');
                    var myids;
                    for(var i=0;i<data.length;i++){
                    	if(data[i].deName==getPassword){
                    		myids=data[i].sid;
                    	}
                    }
                    for(var i=0;i<data.length;i++){
              			if(data[i].pid==myids&&data[i].type==3){
              				if(value!=undefined && data[i]["deName"]==value.userName){
                                $("#userNamess").append("<option selected value='"+data[i]["deName"]+"'>"+data[i]["deName"]+"</option>");
                            }else {
                                $("#userNamess").append("<option  value='"+data[i]["deName"]+"'>"+data[i]["deName"]+"</option>");
                            }
                  		}
              		}
                    $('#userNamess').selectpicker('refresh');
                } else {
                    toastr.error('服务器异常！');
                }
            },
            error: function () {
                toastr.error('服务器异常！');
            }
        });
       
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
            $modal.find("#ids").val(value.id);
        }
    });
    //保存事件
    $('#myModal').find("#save").on('click', function () {
        debugger
        $from = $('#myModal').find('#hiveregsave');
        $.ajax({
            type: "POST",
            url: EventInit.getCtx()+ "/hive/hiveUpdate",
            data: $from.serialize(),
            success: function (data) {
                if (data) {
                    toastr.success('保存成功！');
                } else {
                    toastr.error('服务器异常！保存失败');
                }
                $('#myModal').modal('hide');
                $('#tb_hivereg').bootstrapTable('refresh');
            },
            error: function () {
                toastr.error('服务器异常！');
            }
        });
    });
    //删除事件
    $('#myDeletModal').find("#delete").on('click', function () {
       var value=$("#ids").val();
        if(!value)
            return;
        var id=value;
        $.ajax({
            type: "POST",
            url: EventInit.getCtx()+"/hive/deleteHivereg",
            data:{id:id},
            success: function (data) {
                if (data) {
                       toastr.success("刪除成功");
                       $('#tb_hivereg').bootstrapTable('refresh');
                 } else {
                       toastr.error('删除失败！請检查!');
                }
                $('#myDeletModal').modal('hide');
                $('#tb_hivereg').bootstrapTable('refresh');
            },
            error: function () {
                toastr.error('服务器异常！');
            }
        });
    });

    //修改事件 需要选中table
    $('#btn_update').on('click', function () {
        var rows = $("#tb_hivereg").bootstrapTable('getSelections');
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
        var rows = $("#tb_hivereg").bootstrapTable('getSelections');
        if (rows.length == 0) {
            toastr.info("请选择一条记录");
            return false;
        }
    });
    $('#delete-confirm').find('#delete-sure').on('click', function () {
        var rows = $("#tb_hivereg").bootstrapTable('getSelections');
        var items =[] ;
        $(rows).each(function (index, item, array) {
            items.push({'id':item.id});
        });
        $.ajax({
            type: "DELETE",
            url:EventInit.getCtx()+"/hive/batchDeleteHive",
            dataType:"json",
            contentType:"application/json", // 指定这个协议很重要
            data: JSON.stringify(items),
            success: function (data) {
                if (data) {
                    toastr.success("刪除成功");
                    $('#tb_hivereg').bootstrapTable('refresh');
                } else {
                    toastr.error('删除失败！請檢查!');
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
