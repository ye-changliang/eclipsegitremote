var EventInit = function () {
    var eventInit = new Object();
    eventInit.Init = function () {
        // $($('#navbar-li li a[href="' + EventInit.getPathName() + '"]').parent().parent().parent().children()[0]).addClass('active')
        $($('#navbar-li li a[href="' + EventInit.getPathName() + '"]').parent()).addClass('active');
        $('#navbar-li li a[href="' + EventInit.getPathName() + '"]').parent().parent().prev().trigger('click')
    };
    eventInit.getCtx = function () {
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： /uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/uimcardprj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return (localhostPaht + projectName);
    };
    eventInit.getPathName = function () {
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： /uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        return pathName;
    };
    eventInit.showLoading = function (msg) {
        $('#loadingModal').modal({id: 'loadingModal-backdrop', keyboard: false});
        $("#loadingModal").modal('show');
        $("#loadingModal-backdrop").css('z-index', '19999')
        if (msg) {
            $("#loadingModal h5").html(msg);
        } else {
            $("#loadingModal h5").html("正在加载中......");
        }
    };
    eventInit.hideLoading = function (msg) {
        $("#loadingModal").modal('hide');
    };
    return eventInit;
};

var EventInit = new EventInit();
