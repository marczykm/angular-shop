var app = angular.module('hello', ['ngRoute']);

app.controller('MainCtrl', function($scope, $route, $routeParams, $location) {
    $scope.$route = $route;
    $scope.$location = $location;
    $scope.$routeParams = $routeParams;

    console.log($route);

    $scope.greeting = {id: 'xxx', content: 'Hello World!'};
})