define(['tpePaw', 'services/titleService', 'services/apiService', 'directives/gameAutocomplete'], function (tpePaw) {

    'use strict';
    tpePaw.controller('RankingCtrl', function ($scope, titleService, apiService) {

        titleService.setTitle("Versus - Ranking");

        $scope.rankingForm = {};

        $scope.processForm = function () {

          if ($scope.rankingForm.$valid){

          apiService.createRanking($scope.rankingForm)
              .then(function successCallback(response) {
                  console.log('Successfully created ranking');
                  $location.path('/ranking/' + response.data.id);
              }, function errorCallback(response) {
                  console.log('Ranking creation error');
              });
          }
        };
    });
});
