/* global $scope, $rootScope, app, $http, stringService */

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
//
function stomp($http,$scope,$rootScope,stringService,$interval) {
    var stompClient=null;
    var socket=new SockJS('/server');
    stompClient=Stomp.over(socket);
    stompClient.connect({},function (frame) {
        stompClient.subscribe('/topic/update',function (response) {//订阅消息
            isPush($http,$scope,$rootScope,stringService,$interval);
//            var indent={};
//            //这个indent与rootScope之前传过来的indent，属性不相同
//            indent=JSON.parse(response.body);
//            for (var i=0;i<$rootScope.indents;i++){
//                //找到指定的rootScope中的indent，更新
//                if ($rootScope.indents[i].id==indent.id){
//                    var indentNew={};
//                    indentNew.id=indent.id;
//                    indentNew.tableId=indent.table;
//                    indentNew.reserve=indent.reserve;
//                    indentNew.fulfill=indent.fulfill;
//                    indentNew.reserveNumber=indent.reserve.length;
//                    indentNew.fulfillNumber=indent.fulfill.length;
//                    var beginTimeS=new Date();
//                    var beginTime=beginTimeS.toLocaleString();
//                    indentNew.beginTime=beginTime;
//                    indentNew.reminderNumber=0;
//                    indentNew.firstTime="";
//                    indentNew.waitTimeS=0;
//                    indentNew.waitTime=timeToString(indentNew.waitTimeS);
//                    indentNew.price=indent.price;
//                    indentNew.firstTime=indent.time;//第一次上菜时间，毫秒
//                    $rootScope.indents[i]=indentNew;
//                    $scope.indents[i]=indentNew;
//                    break;
//                }
//            }
        });
        stompClient.subscribe('/topic/add',function (response) {//订阅消息
//            var indent={};
            isPush($http,$scope,$rootScope,stringService,$interval);
//            indent=JSON.parse(response.body);
//            $rootScope.indents.push(indent);
//            $scope.indents.push(indent);
        });
        stompClient.subscribe('/topic/style',function (response) {//订阅消息
            isPush($http,$scope,$rootScope,stringService,$interval);
//            var indent={};
//            indent=JSON.parse(response.body);
//            var id=indent.id;
//            //获取id并删除指定的indent
//            for (var i=0;i<$rootScope.indents.length;i++){
//                if (id==$rootScope.indents.id){
//                    $rootScope.indents.splice(i,1);
//                    $scope.indents.splice(i,1);
//                    break;
//                }
//            }
        });
    });
}
//用于将用户的数字类型转换为文字描述
function convertStyle(styleNumber) {
    var style = Number(styleNumber);
    if (style == "0") {
        return "管理员";
    } else if (style == "1") {
        return "服务员";
    } else {
        return "异常";
    }
}
function isPush($http,$scope,$rootScope,stringService,$interval){
    $http.get('underway', {
        //参数
    }).success(function (data) {
        for (var i = 0; i < data.length; i++) {
            if (data[i].firstTime == null) {//如果还没上菜则取现在时间减去开始时间，格式化为字符串
                var now = new Date();
                var nowMS = now.getTime();
                var waitTimeMS = nowMS - data[i].beginTime;//现在与开始时间之差是等待时间
                var waitTimeS = round(waitTimeMS / 1000);//转为秒数
                data[i].waitTimeS = Number(waitTimeS);
                data[i].waitTime = timeToString(waitTimeS);
            } else {
                var first = Number(data[i].firstTime);
                var begin = Number(data[i].beginTime);
                var waitTime = round((first - begin) / 1000);
                data[i].waitTime = timeToString(waitTime);
                data[i].waitTimeS=waitTime;
            }
            //将表示点菜的一个字符串转化为对象数组
            var reserve = stringService.convertString(data[i].reserve, data[i].fulfill);
            data[i].reserve = reserve;//预定的菜品数,将字符串转为对象数组
            var beginDate = new Date();//将开始时间的毫秒数转为字符串
            var beginTime = Number(data[i].beginTime);
            beginDate.setTime(beginTime);
            data[i].beginTime = beginDate.toLocaleString();
        }
        $rootScope.indents = data;
        $scope.indents = data;
        $interval(function () {
            for (var i = 0; i < $scope.indents.length; i++) {
                //有上菜且，上菜数量为0时，会发生某种问题
                if ($scope.indents[i].firstTime == null || $scope.indents[i].fulfillNumber == 0) {
                    var waitTimeS = Number($scope.indents[i].waitTimeS);
                    waitTimeS = waitTimeS + 1;
                    $scope.indents[i].waitTimeS = Number(waitTimeS);
                    $scope.indents[i].waitTime = timeToString(waitTimeS);
                }
            }
        }, 1000);
    });
}