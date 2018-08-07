'use strict';
define(['tpePaw', 'services/titleService', 'services/authService'], function(tpePaw) {

    tpePaw.controller('LoginCtrl', function($scope, $http, $window, $location, $filter, titleService, AuthService) {

        titleService.setTitle($filter('translate')('LOGIN_TITLE') + ' - Versus');

    	$scope.loginForm = {};

        $scope.login = function () {
            AuthService.login($scope.loginForm);
        };
    });
});