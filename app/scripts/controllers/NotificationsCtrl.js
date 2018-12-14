'use strict';
define(['tpePaw', 'services/titleService', 'services/authService', 'services/apiService'], function(tpePaw) {

  tpePaw.controller('NotificationsCtrl', function($scope, $routeParams, $filter, $location, titleService, AuthService, apiService) {
    titleService.setTitle($filter('translate')('NOTIFICATION_TITTLE') + ' - Versus');

    $scope.hasNotificationsData = false;
    $scope.busy = false;
    $scope.notificationsPage = 0;
    $scope.notifications = [];
    $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;

    if ($scope.loggedUser === undefined) {
      $location.path('/403');
    }

    $scope.goToUserPage = function (username) {
      $location.path('/users/' + username);
    };
    $scope.goToTournamentPlayers = function (tournamentId) {
      $location.path('/tournament/' + tournamentId + '/players');
    };

    $scope.goToTournamentBrackets = function (tournamentId) {
      $location.path('/tournament/' + tournamentId);
    };
    $scope.goToTournamentComment = function (tournamentId,commentId) {
      $location.path('/tournament/' + tournamentId + '/comments');
    };
    $scope.goToRanking = function (rankingId) {
      $location.path('/ranking/' + rankingId);
    };


    $scope.loadNotifications = function () {
      $scope.busy = true;
      apiService.getNotifications($scope.notificationsPage)
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
