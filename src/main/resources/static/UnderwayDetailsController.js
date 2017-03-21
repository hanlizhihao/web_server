app.controller('UnderwayDetailsController', function ($rootScope, $stateParams, $scope, $http, $state) {
    var id = Number($stateParams.id);
    var indents = $rootScope.indents;
    for (var i = 0; i < indents.length; i++) {
        if (indents[i].id == id) {
            $scope.indent = indents[i];
            $scope.reserve = indents[i].reserve;//取出菜单赋值给scope
            break;
        }
    }
    /**
     * 用于查找菜单，返回订单菜单的价格,id为被更改的id
     */
    $scope.getPrice = function getPrice(id) {
        var value = Number(document.getElementById(id).value);
        for (var z = 0; z < $scope.reserve.length; z++) {
            if (id == $scope.reserve[z].id) {
                $scope.reserve[z].count = value;
                break;
            }
        }
        var price = Number(0);
        for (var i = 0; i < $scope.reserve.length; i++) {
            for (var y = 0; y < $rootScope.findMenu.length; y++) {
                if ($scope.reserve[i].name == $rootScope.findMenu[y].name) {
                    //通过count统计订单的价格
                    price = price + $scope.reserve[i].count * $rootScope.findMenu[y].price;
                    break;
                }
            }
        }
        $scope.indent.price = price;
    };
    //若web端更改上菜，判断是否是第一次上菜，若是，则更改firstTime
    function updateFirstTime() {
        var copyReserve = [];
        copyReserve = $scope.reserve;
        for (var i = 0; i < $scope.reserve.length; i++) {
            var value = Number(document.getElementById($scope.reserve[i].id).value);
            if ($scope.reserve[i].number != 0) {
                return;
            }
            if ($scope.reserve[i].id == id) {
                $scope.reserve[i].number = value;
            }
            if (i == $scope.reserve.length - 1) {
                //若已经到了最后一个，则更改第一次上菜时间
                $scope.indent.firstTime = new Date().getTime();
            }
        }
    }
    //直接使用scope中的值传递
    $scope.update = function () {
        var names = [];
        var counts = [];
        var numbers = [];
        for (var i = 0; i < $scope.indent.reserve.length; i++) {
            var name = String($scope.reserve[i].name);
            var count = String($scope.reserve[i].count);
            var number = String($scope.reserve[i].number);
            names.push(name);
            counts.push(count);
            numbers.push(number);
        }
        updateFirstTime();
        var param = {"id": $scope.indent.id, "name": names, "count": counts, "number": numbers, "table": $scope.indent.tableId,
            "reminderNumber": $scope.indent.reminderNumber, "price": $scope.indent.price, "time": $scope.indent.firstTime};
        $http.post('/indent/update', $.param(param), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
                .success(function (data) {
                    if (data == "success") {
                        $state.go('/underway');
                        alert("修改成功");
                    } else {
                        $state.go('/underway');
                        alert("修改失败");
                    }
                });
    };
    //利用id不同来实现用jquery控制spinner，reserve中对象的属性有count,number,name,id
});

