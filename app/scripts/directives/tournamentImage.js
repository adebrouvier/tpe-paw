'use strict';
define(['tpePaw'], function (tpePaw) {

  tpePaw.directive('tournamentImage', function () {
    return {
      restrict: 'E',
      templateUrl: 'views/tournament/tournament-image.html'
    };
  });
});
