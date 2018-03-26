define(['tpePaw', 'services/titleService'], function(tpePaw) {

    'use strict';
    tpePaw.controller('LoginCtrl', function($scope, $http, $window, $location, $filter, titleService) {

        titleService.setTitle($filter('translate')("LOGIN_TITLE") + " - Versus");

    	$scope.loginForm = {};

    	$scope.login = function () {

            if ($scope.loginForm.$valid){
                $http.post('http://localhost:8080/login', $scope.loginForm)
                        .then(function successCallback(response) {

                            if (response.headers('Authorization')) {

                                var currentUser = {
                                    username: $scope.loginForm.username,
                                    token: response.headers('Authorization')
                                };

                                if ($scope.loginForm.rememberMe){
                                    $window.localStorage['currentUser'] = angular.toJson(currentUser);
                                }else{
                                    $window.sessionStorage['currentUser'] = angular.toJson(currentUser);
                                }
                                $scope.loggedIn = true;
                                $location.path('/');
                                console.log('loggedIn');
                            }
                        }, function errorCallback(response) {
                            console.log('Login ERROR');
                });
            }
        };
    });
});