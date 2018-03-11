define(['tpePaw', 'services/titleService'], function(tpePaw) {

    'use strict';
    tpePaw.controller('LoginCtrl', function($scope, $http, $window, $location, titleService) {

        titleService.setTitle("Login - Versus");

    	$scope.loginForm = {};

    	$scope.login = function () {

            $http.post('http://localhost:8080/login', $scope.loginForm)
                    .then(function successCallback(response) {

                        if (response.headers('Authorization')) {

                            var currentUser = {
                                username: $scope.loginForm.username,
                                token: response.headers('Authorization')
                            };

                            $window.localStorage['currentUser'] = angular.toJson(currentUser);
                            $scope.loggedIn = true;
                            $location.path('/');
                            console.log('loggedIn');
                        }
                    }, function errorCallback(response) {
                        console.log('Login ERROR');
            });
        };
    });
});