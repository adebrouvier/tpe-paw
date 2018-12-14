'use strict';
define(['tpePaw', 'services/titleService', 'services/apiService'], function (tpePaw) {

  tpePaw.controller('HomeCtrl', function ($scope, titleService, apiService) {
    titleService.setTitle("Versus");

    $scope.featuredTournaments = {};
    $scope.featuredRankings = {};
    $scope.mostFollowed = {};
    $scope.topWinners = {};
    $scope.popularRankings = {};

    apiService.getFeaturedTournaments()
      .then(function successCallback(response) {
        $scope.featuredTournaments = response.data;
      });

    apiService.getFeaturedRankings()
      .then(function successCallback(response) {
        $scope.featuredRankings = response.data;
      });

    apiService.getTopWinners()
      .then(function successCallback(response) {
        $scope.topWinners = response.data;
      });

    apiService.getPopularRankings()
      .then(function successCallback(response) {
        $scope.popularRankings = response.data;
      });

    apiService.getMostFollowed()
      .then(function successCallback(response) {
        $scope.mostFollowed = response.data;
      });
  });
});
