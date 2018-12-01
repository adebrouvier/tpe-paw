'use strict';
define(['tpePaw', 'services/titleService', 'services/authService', 'services/apiService'], function(tpePaw) {

  tpePaw.controller('NotificationsCtrl', function($scope, $routeParams, $filter, titleService, AuthService, apiService) {
    titleService.setTitle($filter('translate')('NOTIFICATION_TITTLE') + ' - Versus');

    $scope.hasNotificationsData = false;
    $scope.busy = false;
    $scope.username = $routeParams.username;
    $scope.notificationsPage = 0;
    $scope.notifications = [];
    $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;

    if ($scope.loggedUser != $scope.username) {
      window.location = '/#/403/';
    }

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
