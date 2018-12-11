'use strict';
define(['tpePaw', 'services/apiService', 'services/authService'], function(tpePaw) {

	tpePaw.controller('IndexCtrl', function($scope, $window, apiService, AuthService) {

		$scope.currentUser = AuthService.currentUser();
    $scope.unreadNotifications = 0;

		$scope.loggedIn = AuthService.loggedIn();

    if ($scope.currentUser != null) {
      if ($window.location.hash == '#/notifications/' + $scope.currentUser.username) {
        $scope.unreadNotifications = 0;
      } else {
        apiService.get('/notifications/' + $scope.currentUser.username + '/unread-notifications')
          .then(function successCallback(response) {
            $scope.unreadNotifications = response.data.unreadNotifications;
          },function errorCallback(response) {});
      }

    }



    $scope.goToNotifications = function () {
      window.location = '/#/notifications/' + $scope.currentUser.username;
    };

		$scope.logout = function() {
			AuthService.logout();
      $scope.unreadNotifications = 0;
			$scope.currentUser = null;
			$scope.loggedIn = false;
		};
	});
});
