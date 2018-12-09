define(['tpePaw', 'services/titleService', 'services/apiService', 'services/authService', 'directives/gameAutocomplete'], function (tpePaw) {

  'use strict';
  tpePaw.controller('RankingCtrl', function ($scope, $location, titleService, apiService, AuthService) {

    titleService.setTitle("Versus - Ranking");

    $scope.rankingForm = {};
    $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;

    if ($scope.loggedUser === undefined) {
      $location.path('/login');
    }

    $scope.processForm = function () {

      if ($scope.rankingForm.$valid) {

        apiService.createRanking($scope.rankingForm)
          .then(function successCallback(response) {
            console.log('Successfully created ranking');
            console.log(response);
            $location.path('/ranking/' + response.data.id);
          }, function errorCallback(response) {
            console.log(response);
            console.log('Ranking creation error');
          });
      }
    };
  });
});
