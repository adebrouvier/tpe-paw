'use strict';
define(['tpePaw', 'services/apiService', 'services/authService'], function(tpePaw) {

	tpePaw.controller('IndexCtrl', function($scope, $rootScope, $window, $location, apiService, AuthService) {

		$scope.currentUser = AuthService.currentUser();
    $scope.unreadNotifications = 0;

		$scope.loggedIn = AuthService.loggedIn();

    if ($scope.currentUser != null) {
      if ($window.location.hash == '#/notifications') {
        $scope.unreadNotifications = 0;
      } else {
        apiService.getUnreadNotificationsCount()
          .then(function successCallback(response) {
            $scope.unreadNotifications = response.data.unreadNotifications;
          },function errorCallback(response) {});
      }

    }

    $rootScope.$on('loggedIn', function () {
      $scope.loggedIn = AuthService.loggedIn();
      $scope.currentUser = AuthService.currentUser();
    });

		$scope.logout = function() {
			AuthService.logout();
      $scope.unreadNotifications = 0;
			$scope.currentUser = null;
			$scope.loggedIn = false;
      $location.path('/');
		};
	});
});
