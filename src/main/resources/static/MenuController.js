/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        $scope.menus = menus;
    }
    $scope.addMenu = function () {
        if($scope.addname==null||$scope.addprice==null||$scope.addname==""){
            $scope.addname=String(document.getElementById("name").value);
            $scope.addprice=String(document.getElementById("price").value);
        }
        var menuModel = {"name":$scope.addname,"price":$scope.addprice};
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
                        $http.get('/version', {}).success(function (data) {
                            $rootScope.version = String(data);
                        });
                    }
                });
    };
    $scope.deleteMenu = function (id) {
        var param = String(id);
        param = "/menu/delete/" + param;
        $.get(param, {}).success(function (data) {
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
        });
    };
});
app.controller('MenuDetailsController', function ($rootScope, $scope, $http, $stateParams,$state) {
    var id = Number($stateParams.id);
    console.log(id);
    var menus = $rootScope.showMenu;
    for (var i = 0; i < menus.length; i++) {
        if (menus[i].id == id) {
            $scope.menu = menus[i];
            break;
        }
    }
    //修改菜单
    $scope.updateMenu = function () {
        var menuModel = {"id": $scope.menu.id, "name": $scope.menu.greensName, "price": $scope.menu.price};
        $.post('/menu/update', $.param(menuModel), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
                .success(function (data) {
                    if ("success" == data) {
                        $state.go('menu');
                        alert("修改成功");
                    } else {
                        $state.go('menu');
                    }
                });
        $state.go('menu');
    };
});

