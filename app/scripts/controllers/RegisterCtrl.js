'use strict';
define(['tpePaw', 'services/titleService', 'services/apiService', 'services/authService', 'directives/availableUsername', 'directives/fieldMatch'], function (tpePaw) {

  tpePaw.controller('RegisterCtrl', function ($scope, $filter, $location, titleService, apiService, AuthService) {

    titleService.setTitle($filter('translate')('REGISTER_TITLE') + ' - Versus');

    $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;

    if ($scope.loggedUser !== undefined) {
      $location.path('/');
    }

    $scope.registerForm = {};

    $scope.processForm = function () {

      if ($scope.registerForm.$valid) {
        apiService.register($scope.registerForm)
          .then(function successCallback(response) {

            var loginForm = {
              username: $scope.registerForm.username,
              password: $scope.registerForm.password
            };

            AuthService.login(loginForm);
          }, function errorCallback(response) {
            console.log('Register ERROR');
          });
      }
    };
  });
});

