'use strict';
define(['tpePaw'], function(tpePaw) {

    tpePaw.controller('RegisterCtrl', function($scope, $http) {

    	$scope.registerForm = {};

    	$scope.processForm = function () {
        $http.post('http://localhost:8080/users', $scope.registerForm)
            .then(function successCallback(response) {
                console.log('Successfully registered user');
            }, function errorCallback(response) {
                console.log('Register ERROR');
            });
    	};    	
        
    });   

});

