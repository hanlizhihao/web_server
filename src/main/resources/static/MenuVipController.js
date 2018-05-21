/* global app */
app.controller('MenuController', function ($state, $scope, $http, $rootScope) {
    var menus = $rootScope.showMenu;
    var version = $rootScope.version;
    //用于添加菜品的参数
    $scope.addname = String();
    $scope.addprice = Number();
    $http.get('/version', {}).success(function (data) {
        $rootScope.version = String(data);
    });
    //比对版本，若一致则不向去读取数据
    if (version != $rootScope.version) {
        $http.get('/menus', {
        }).success(function (data) {
            var greens = [];
            //从数据中抽取出用于判断菜品价格的对象数组
            for (var i = 0; i < data.length; i++) {
                var green = {};
                green.name = data[i].greensName;
                green.price = data[i].price;
                greens.push(green);
            }
            $rootScope.findMenu = greens;//菜单
            $rootScope.showMenu = data;//用于显示的菜单
            $scope.menus = data;
        });
    } else {
        $http.get('/menus', {
        }).success(function (data) {
            var greens = [];
            //从数据中抽取出用于判断菜品价格的对象数组
            for (var i = 0; i < data.length; i++) {
                var green = {};
                green.name = data[i].greensName;
                green.price = data[i].price;
                greens.push(green);
            }
            $rootScope.findMenu = greens;//菜单
            $rootScope.showMenu = data;//用于显示的菜单
            $scope.menus = data;
        });
    }
    $scope.addMenu = function () {
        if ($scope.addname == null || $scope.addprice == null || $scope.addname == "") {
            $scope.addname = String(document.getElementById("name").value);
            $scope.addprice = String(document.getElementById("price").value);
        }
        var menuModel = {"name": $scope.addname, "price": $scope.addprice};
        $http.post('/menu/add', $.param(menuModel), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
                .success(function (data) {
                    if (data == "success") {
                        $http.get('/menus', {
                        }).success(function (data) {
                            var greens = [];
                            //从数据中抽取出用于判断菜品价格的对象数组
                            for (var i = 0; i < data.length; i++) {
                                var green = {};
                                green.name = data[i].greensName;
                                green.price = data[i].price;
                                greens.push(green);
                            }
                            $rootScope.findMenu = greens;//菜单
                            $rootScope.showMenu = data;//用于显示的菜单
                            $scope.menus = data;
                        });
                    }
                });
        $("#modal-demo").modal("hide");
    };
    $scope.deleteMenu = function (id) {
        var param = String(id);
        param = "/menu/delete/" + param;
        $http.get(param, {}).success(function (data) {
            if ("success" == data) {
                for (var i = 0; i < $rootScope.showMenu.length; i++) {
                    if ($rootScope.showMenu[i].id == id) {
                        $rootScope.showMenu.splice(i, 1);
                        $rootScope.findMenu.splice(i, 1);
                        $scope.menus = $rootScope.showMenu;
                        break;
                    }
                }
            } else {
                alert("删除失败");
            }
        }).error(function (a) {
            alert("服务器发生错误");
        });
    };
    stompUpdateMenu($scope, $http, $rootScope);
});
app.controller('MenuDetailsController', function ($rootScope, $scope, $http, $stateParams, $state) {
    var id = Number($stateParams.id);
    var menus = $rootScope.showMenu;
    for (var i = 0; i < menus.length; i++) {
        if (menus[i].id == id) {
            $scope.menu = menus[i];
            break;
        }
    }
    $scope.greensName = String();
    $scope.price = String();
    //修改菜单
    $scope.updateMenu = function () {
        $scope.greensName = String(document.getElementById("name").value);
        $scope.price = String(document.getElementById("price").value);
        var menuModel = {"id": $scope.menu.id, "name": $scope.greensName, "price": $scope.price};
        $http.post('/menu/update', $.param(menuModel), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
                .success(function (data) {
                    if ("success" == data) {
                        var greens = [];
                        //从数据中抽取出用于判断菜品价格的对象数组
                        for (var i = 0; i < data.length; i++) {
                            var green = {};
                            green.name = data[i].greensName;
                            green.price = data[i].price;
                            greens.push(green);
                        }
                        $rootScope.findMenu = greens;//菜单
                        $rootScope.showMenu = data;//用于显示的菜单
                        $state.go('menu');
                        alert("修改成功");
                    } else {
                        $state.go('menu');
                    }
                });
        $state.go('menu');
    };
});
app.controller('VipController', function ($scope, $rootScope, $http, $state) {
    $scope.vips = [];
    $scope.addTelephone = String();
    $scope.addNumber = String();
    $scope.addSum = String();
    $http.get('/vips/1', {}).success(function (data) {
        if (data[0].phoneNumber == 404) {
            alert("服务器获取数据失败");
        } else {
            $scope.vips = data;
            $rootScope.vips=data;
        }
    }).error(function () {
        alert("获取数据失败");
    });
    $scope.addVip = function () {
        if ($scope.addTelephone == "" || $scope.addTelephone === null) {
            alert("手机号不能为空");
            return;
        }
        var vipModel={};
        if ($scope.addSum === null || $scope.addNumber === null) {
            vipModel = {"phoneNumber": $scope.addTelephone, "consumeNumber": "0", "totalConsume": "0"};
        } else {
            vipModel = {"phoneNumber": $scope.addTelephone, "consumeNumber": $scope.addNumber, "totalConsume": $scope.addSum};
        }
        $http.post('/vip/add', $.param(vipModel), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
                .success(function (data) {
                    if (data == "success") {
                        $http.get('/vips/1', {}).success(function (data) {
                            if (data[0].phoneNumber == 404) {
                                alert("服务器获取数据失败");
                            } else {
                                $scope.vips = data;
                                $rootScope.vips=data;
                            }
                        }).error(function () {
                            alert("获取数据失败");
                        });
                    } else {
                        alert("添加会员信息失败，服务器存储数据异常");
                    }
                }).error(function () {
            alert("添加会员信息失败，网络连接异常");
        });
        $("#modal-demo").modal("hide");
    };
    $scope.deleteVip = function (id) {
        var param = String(id);
        param = "/vip/delete/" + param;
        $http.get(param, {}).success(function (data) {
            if ("success" == data) {
                for (var i = 0; i < $scope.vips.length; i++) {
                    if ($scope.vips[i].id == id) {
                        $rootScope.vips.splice(i,1);
                        $scope.vips=$rootScope.vips;
                        break;
                    }
                }
            } else {
                alert("删除失败");
            }
        }).error(function (a) {
            alert("服务器发生错误");
        });
    };
    stompUpdateVip( $scope, $rootScope, $http);
});
app.controller('VipDetailsController', function ($state, $scope, $rootScope, $http, $stateParams) {
    var id = Number($stateParams.id);
    var vips = $rootScope.vips;
    for (var i = 0; i < vips.length; i++) {
        if (vips[i].id == id) {
            $scope.vip = vips[i];
            break;
        }
    }
    $scope.phoneNumber=String($scope.vip.phoneNumber);
    $scope.consumeNumber=Number($scope.vip.consumeNumber);
    $scope.totalConsume=Number($scope.vip.totalConsume);
    $scope.joinTime=String($scope.vip.joinTime);
    if($scope.vip.consumeNumber==null&&$scope.vip.totalConsume==null){
        $scope.vip.consumeNumber=Number(0);
        $scope.vip.totalConsume=Number(0);
    }
    //只用scope绑定
    //用于数据绑定的模型
    $scope.updateVip = function () {
        var phone = new String($scope.phoneNumber);
        if (phone.length == 11 || phone.length == 10 || phone.length == 6) {
            var vipModel = {"id": id,"phoneNumber": $scope.phoneNumber,"consumeNumber": $scope.consumeNumber,
                "totalConsume": $scope.totalConsume,"joinTime": $scope.joinTime
            };
            console.log(vipModel);
            $http.post('/vip/update', $.param(vipModel), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
                    .success(function (data) {
                        if (data == "success") {
                            $state.go('vip');
                            alert("更新会员信息成功");
                        } else {
                            $state.go('vip');
                            alert("更新会员信息失败，服务器存储数据异常");
                        }
                    }).error(function () {
                $state.go('vip');
                alert("更新会员信息失败，网络连接异常");
            });
            $state.go('vip');
        } else {
            alert("号码格式不正确");
        }
    };
});