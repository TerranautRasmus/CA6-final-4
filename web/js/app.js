var myApp = angular.module('DemoApp', ['ngRoute']);

// Route provider start
myApp.config(function ($routeProvider) {
    $routeProvider
            .when("/AllCars", {
                templateUrl: "views/allCars.html",
                controller: "CarController"
            })
            .when("/AddCar", {
                templateUrl: "views/addCar.html",
                controller: "CarController"
            })
            .when("/AddCar/:id", {
                templateUrl: "views/addCar.html",
                controller: "CarController"
            })
            .otherwise({
                redirectTo: '/AllCars'
            });
});
// Route provider end

myApp.factory('CarFactory', function ($http) {
    var self = this;
    var cars = [];
    self.cars = cars;
    // Get Cars
    var getCars = function () {
        return $http({
            method: 'GET',
            url: 'api/cars'
        });
    };
    
    // Delete Car
    var deleteCar = function (id) {
        $http.delete("api/cars/" + id);
    };
    
    // Add Edit Car
    var addEditCar = function (newcar) {
        if(newcar.id == null) {
            $http.post("api/cars", newcar);
        } else {
            $http.put('api/cars/' + newcar.id, newcar);
        }
    };
    // Return
    return {
        getCars: getCars,
        deleteCar: deleteCar,
        addEditCar: addEditCar
    };
});

myApp.controller('MainController', [function () {
        var mainSelf = this;
        mainSelf.title = "Cars Demo App";
    }]);
myApp.controller('CarViewController', ["CarFactory", function (CarFactory) {
        var self = this;
        self.title = "Cars Demo App";
        self.cars = getCars();
        function getCars() {
            CarFactory.getCars().then(function(res){
                self.cars = res.data;
            }, function(res){});
        }
        self.predicate = "year";
        self.reverse = false;
        
        // Delete car
        self.delete = function (id) {
            console.log(id);
            CarFactory.deleteCar(id);
        };
    }]);
myApp.controller('CarController', ["CarFactory", "$routeParams", function (CarFactory, $routeParams, $location) {
        var self = this;
        
        // Add car
        self.add = function () {
            CarFactory.addEditCar(self.newCar);
            self.newCar = {};
        };
        
        // Edit car
        self.newCar = {};
        if (angular.isDefined($routeParams.id)) {
            CarFactory.getCars().then(function(res) {
                var cars = res.data;
            
                for (var i in cars) {
                    if (cars[i].id == $routeParams.id) {
                        self.newCar = angular.copy(cars[i]);
                    }
                }
            }, function(res){});
        }
    }]);

