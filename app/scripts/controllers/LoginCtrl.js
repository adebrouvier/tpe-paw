'use strict';
define(['tpePaw', 'services/titleService', 'services/authService'], function (tpePaw) {

  tpePaw.controller('LoginCtrl', function ($scope, $filter, titleService, AuthService) {

    titleService.setTitle($filter('translate')('LOGIN_TITLE') + ' - Versus');

    $scope.loginForm = {};

    $scope.login = function () {
      AuthService.login($scope.loginForm)
        .catch(function (error) {
          if (error.status === 401) {
            $scope.invalidCredentials = true;
          }
        });
    };
  });
});
