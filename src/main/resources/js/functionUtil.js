/* global $scope, $rootScope, app */

//参数为等待时间的秒数,进行取整
function timeToString(wait) {
    var waitTime = new String();
    if (wait >= 60 && wait < 3600) {
        var minute = round(wait/60);
        var second = wait%60;;
        waitTime = minute + "分钟" + second + "秒";
    } else if (wait < 60) {
        waitTime = wait + "秒";
    } else if (wait >= 3600) {
        var hour = round(wait / 3600);
        var minute = round(wait % 3600/60);
        var second = wait % 3600 % 60;
        waitTime = hour + "小时" + minute + "分钟" + second + "秒";
    }
    return waitTime;
}
//由于Math.round函数是四舍五入的，所以不符合需求，需要向下取整的方法
function round(number){
    var num=Number(number);
    var numRound=Math.round(num);
    if(numRound>num){
        return numRound-1;
    }else{
        return numRound;
    }
}
//连接stomp，接受add主题下的indent
function stomp() {
    var stompClient=null;
    var socket=new SockJS('/server');
    stompClient=Stomp.over(socket);
    stompClient.connect({},function (frame) {
        stompClient.subscribe('/topic/update',function (response) {//订阅消息
            var indent={};
            //这个indent与rootScope之前传过来的indent，属性不相同
            indent=JSON.parse(response.body);
            for (var i=0;i<$rootScope.indents;i++){
                //找到指定的rootScope中的indent，更新
                if ($rootScope.indents[i].id==indent.id){
                    var indentNew={};
                    indentNew.id=indent.id;
                    indentNew.tableId=indent.table;
                    indentNew.reserve=indent.reserve;
                    indentNew.fulfill=indent.fulfill;
                    indentNew.reserveNumber=indent.reserve.length;
                    indentNew.fulfillNumber=indent.fulfill.length;
                    var beginTimeS=new Date();
                    var beginTime=beginTimeS.toLocaleString();
                    indentNew.beginTime=beginTime;
                    indentNew.reminderNumber=0;
                    indentNew.firstTime="";
                    indentNew.waitTimeS=0;
                    indentNew.waitTime=timeToString(indentNew.waitTimeS);
                    indentNew.price=indent.price;
                    indentNew.firstTime=indent.time;//第一次上菜时间，毫秒
                    $rootScope.indents[i]=indentNew;
                    $scope.indents[i]=indentNew;
                    break;
                }
            }
        });
        stompClient.subscribe('/topic/add',function (response) {//订阅消息
            var indent={};
            indent=JSON.parse(response.body);
            $rootScope.indents.push(indent);
            $scope.indents.push(indent);
        });
        stompClient.subscribe('/topic/style',function (response) {//订阅消息
            var indent={};
            indent=JSON.parse(response.body);
            var id=indent.id;
            //获取id并删除指定的indent
            for (var i=0;i<$rootScope.indents.length;i++){
                if (id==$rootScope.indents.id){
                    $rootScope.indents.splice(i,1);
                    $scope.indents.splice(i,1);
                    break;
                }
            }
        });
    });
}