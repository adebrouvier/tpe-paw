define(['tpePaw', 'services/titleService', 'directives/tournamentInfo'], function (tpePaw) {

    'use strict';
    tpePaw.controller('TournamentPageCtrl', function ($scope, $http, $window, $location, $filter, $routeParams, titleService) {

        titleService.setTitle("Versus");

        $scope.tournamentId = $routeParams.id;
        $scope.tournament = {};

        $http.get("http://localhost:8080/tournaments/" + $scope.tournamentId)
            .then(function successCallback(response) {
                $scope.tournament = response.data;
                titleService.setTitle($scope.tournament.name + " - Versus");
            }, function errorCallback(response) {
                $location.url('/404');
            });
    });
});