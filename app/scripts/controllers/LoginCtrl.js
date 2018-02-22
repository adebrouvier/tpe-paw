define(['tpePaw'], function(tpePaw) {

    'use strict';
    tpePaw.controller('LoginCtrl', function($scope, $http) {

    	$scope.loginForm = {};

    	$scope.login = function () {
        $http.post('http://localhost:8080/login', $scope.loginForm)
            .then(function successCallback(response) {
                console.log('loggedIn');
                $scope.loggedIn = true;
                $location.path("#/");
            }, function errorCallback(response) {
                console.log('Login ERROR');
            });
    	};
        
    });

});