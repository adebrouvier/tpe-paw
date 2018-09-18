define(['tpePaw', 'services/titleService', 'directives/tournamentInfo', 'services/apiService'], function (tpePaw) {

    'use strict';
    tpePaw.controller('TournamentPageCtrl', function ($scope, $location, $routeParams, titleService, apiService) {

        titleService.setTitle("Versus");

        $scope.tournamentId = $routeParams.id;
        $scope.tournament = {};

        apiService.get("/tournaments/" + $scope.tournamentId)
            .then(function successCallback(response) {
                $scope.tournament = response.data;
                titleService.setTitle($scope.tournament.name + " - Versus");
            }, function errorCallback(response) {
                $location.url('/404');
            });
    });
});