define(['tpePaw', 'services/titleService', 'directives/tournamentInfo'], function (tpePaw) {

    'use strict';
    tpePaw.controller('TournamentStandingsCtrl', function ($scope, $routeParams, titleService) {

        titleService.setTitle("Versus");

        $scope.tournamentId = $routeParams.id;
        $scope.tournament = {};
    });
});