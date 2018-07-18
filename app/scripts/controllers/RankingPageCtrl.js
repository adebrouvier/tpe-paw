define(['tpePaw', 'services/titleService', 'directives/rankingInfo'], function (tpePaw) {

    'use strict';
    tpePaw.controller('RankingPageCtrl', function ($scope, $http, $window, $location, $filter, $routeParams, titleService) {

        titleService.setTitle("Versus");

        $scope.rankingId = $routeParams.id;
        $scope.ranking = {};

        $http.get("http://localhost:8080/rankings/" + $scope.rankingId)
            .then(function successCallback(response) {
                $scope.ranking = response.data;
                titleService.setTitle($scope.ranking.name + " - Versus");
            }, function errorCallback(response) {
                $location.url('/404');
            });
    });
});