define(['tpePaw', 'services/titleService', 'services/apiService', 'directives/rankingInfo'], function (tpePaw) {

    'use strict';
    tpePaw.controller('RankingPageCtrl', function ($scope, $window, $location, $filter, $routeParams, titleService, apiService) {

        titleService.setTitle("Versus");

        $scope.rankingId = $routeParams.id;
        $scope.ranking = {};

        apiService.getRanking($scope.rankingId)
            .then(function successCallback(response) {
                $scope.ranking = response.data;
                titleService.setTitle($scope.ranking.name + " - Versus");
            }, function errorCallback(response) {
                $location.url('/404');
            });
    });
});
