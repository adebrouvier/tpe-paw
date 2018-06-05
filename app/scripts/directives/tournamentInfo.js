'use strict';
define(['tpePaw'], function (tpePaw) {

    tpePaw.directive('tournamentInfo', function () {
        return {
            restrict: 'E',
            templateUrl: 'views/tournament-info.html'
        };
    });
});