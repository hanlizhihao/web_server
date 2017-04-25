/**
 * 配置ui-router
 * */
app.run(function ($state) {
    $state.go('/underway');
});
app.config(['$stateProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider
                .state('/underway', {//正在进行的路由s
                    url: '/underway',
                    controller: 'UnderwayController',
                    controllerAs: 'underway',
                    templateUrl: 'ongoing.html'
                })
                .state('/underway/carry', {//结账
                    url: '/underwayCarry/:id',
                    controller: 'UnderwayCarryController',
                    controllerAs: 'underwayCarry',
                    templateUrl: 'carry.html'
                })

                .state('/underway/details', {//正在进行的详情
                    controller: 'UnderwayDetailsController',
                    controllerAs: 'underwayDetails',
                    url: '/underwayDetails/:id',
                    templateUrl: "underwayDetails.html"})
                .state('/finished', {//已完成
                    controller: 'FinishedController',
                    controllerAs: 'finished',
                    url: '/finished',
                    templateUrl: "finished.html"
                })
                .state('/finished/details', {//已完成的详情
                    controller: 'FinishedDetailsController',
                    controllerAs: 'finishedDetails',
                    url: '/finishedDetails/:id',
                    templateUrl: "finishedDetails.html"
                })
                .state('canceled', {//已取消
                    controller: 'CanceledController',
                    controllerAs: 'canceled',
                    url: '/canceled',
                    templateUrl: "canceled.html"})
                .state('canceled/details', {//已取消的详情
                    controller: 'CanceledDetailsController',
                    controllerAs: 'canceledDetails',
                    url: '/canceledDetails/:id',
                    templateUrl: "canceledDetails.html"})
                .state('menu', {//菜单管理
                    controller: 'MenuController',
                    controllerAs: 'menu',
                    url: '/menu',
                    templateUrl: "menu.html"})
                .state('menu/details', {//菜单管理详情
                    controller: 'MenuDetailsController',
                    controllerAs: 'menuDetails',
                    url: '/menuDetails/:id',
                    templateUrl: "menuDetails.html"})
                .state('vip', {//vip管理
                    controller: 'VipController',
                    controllerAs: 'vip',
                    url: '/vip',
                    templateUrl: "Vip.html"})
                .state('vip/details', {//vip管理详情
                    controller: 'VipDetailsController',
                    controllerAs: 'vipDetails',
                    url: '/vipDetails/:id',
                    templateUrl: "VipDetails.html"})
                .state('user', {//用户管理
                    controller: 'UserController',
                    controllerAs: 'user',
                    url: '/user',
                    templateUrl: "user.html"})
                .state('user/details', {//用户管理详情
                    controller: 'UserDetailsController',
                    controllerAs: 'userDetails',
                    url: '/userDetails/:id',
                    templateUrl: "userDetails.html"})
                .state('sales', {//销售分析
                    controller: 'SalesController',
                    controllerAs: 'sales',
                    url: '/sales',
                    templateUrl: "sales.html"})
                .state('bill', {//账单管理
                    controller: 'BillController',
                    controllerAs: 'bill',
                    url: '/bill',
                    templateUrl: "bill.html"})
                .state('income', {//损益表
                    controller: 'IncomeController',
                    controllerAs: 'income',
                    url: '/income',
                    templateUrl: "income.html"});
    }]);
//常用服务
//StringService将reserve和fulfill字符串处理并返回一个对象数组
//数组中的对象将包含单个订单的：菜名name，订菜数量count，上菜数量number
app.service('stringService', function () {
    this.convertString = function (reserve, fulfill) {//接受一个字符串，输出一个对象数组
        var reserve1 = String(reserve);
        var fulfills = String(fulfill);
        var result = [];
        if (fulfills != "") {
            var book = String(reserve1.substring(0, reserve1.length - 1));//去掉e
            var fulfil = String(fulfills.substring(0, fulfills.length - 1));
            var fulfillArray = [];
            var reserves = [];
            reserves = book.split("e");//每个字符串有a连接
            fulfillArray = fulfil.split("e");
            var fulfillResult = [];
            for (var i = 0; i < fulfillArray.length; i++) {
                var fulfillObject = {};
                var fulfillshuzu = fulfillArray[i].split("a");
                fulfillObject.name = fulfillshuzu[0];
                fulfillObject.number = fulfillshuzu[1];
                fulfillResult[i] = fulfillObject;
            }
            //以上代码对fulfill也做出格式化，并且存储到一个对象数组中
            for (var x = 0; x < reserves.length; x++) {
                var shuzu = reserves[x].split("a");
                var greens = {};
                greens.id = x + 1;
                greens.name = shuzu[0];
                greens.count = shuzu[1];
                //给菜单赋值，使其既有菜品名字，又有上菜和订菜的数量
                for (var y = 0; y < fulfillResult.length; y++) {
                    if (greens.name == fulfillResult[y].name) {
                        greens.number = fulfillResult[y].number;
                        break;
                    }
                }
                if(greens.number==null){
                    greens.number=new Number(0);
                }
                result.push(greens);
            }
        } else {
            var book1 = String(reserve1.substring(0, reserve1.length - 1));//去掉e
            var reserves1 = [];
            reserves1 = book1.split("e");//每个字符串有a连接
            for (var z = 0; z < reserves1.length; z++) {
                var grenns1 = {};
                var shuzu1 = [];
                shuzu1 = reserves1[z].split("a");
                grenns1.id = z + 1;
                grenns1.name = shuzu1[0];
                grenns1.count = shuzu1[1];
                grenns1.number = 0;
                result.push(grenns1);
            }
        }
        return result;
    };
});
/**
 * 控制器
 * */
app.controller('MainController', function ($rootScope, $http, $scope) {
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
    });
    $http.get('/version', {}).success(function (data) {
        $rootScope.version = String(data);
    });
});