'use strict';
define(['tpePaw', 'services/titleService', 'services/apiService'], function(tpePaw) {

  tpePaw.controller('NotificationsCtrl', function($scope, $routeParams, $filter, titleService, apiService) {
    // titleService.setTitle($filter('translate')('SEARCH_TITLE') + ' - Versus');

    $scope.hasNotificationsData = false;
    $scope.busy = false;
    $scope.username = $routeParams.username;
    $scope.notificationsPage = 0;
    $scope.notifications = [];

    $scope.goToUserPage = function (username) {
      window.location = '/#/users/' + username;
    };
    $scope.goToTournamentPlayers = function (tournamentId) {
      window.location = '/#/tournament/' + tournamentId + '/players';
    };

    $scope.goToTournamentBrackets = function (tournamentId) {
      window.location = '/#/tournament/' + tournamentId;
    };
    $scope.goToTournamentComment = function (tournamentId,commentId) {
      window.location = '/#/tournament/' + tournamentId + '/comments#comment-' + commentId;
    };
    $scope.goToRanking = function (rankingId) {
      window.location = '/#/ranking/' + rankingId;
    };


    $scope.loadNotifications = function () {
      $scope.busy = true;
      apiService.get('/notifications/' + $scope.username + '?page=' + $scope.notificationsPage)
        .then(function successCallback(response) {
          console.log(response.data);
          if (response.data != null || response.data == {}) {
            var nLength = response.data.length;
            for (var i = 0; i < nLength; i++) {
              $scope.notifications.push(response.data[i]);
            }
            $scope.hasNotificationsData = true;
          } else {
            $scope.hasNotificationsData = false;
          }
        }, function errorCallback(response) {
          $scope.busy = false;
        });
      if ($scope.hasNotificationsData) {
        $scope.notificationsPage += 1;
      }

    };

    $scope.loadNotifications();
  });
});
