'use strict';
define(['tpePaw', 'services/authService'], function(tpePaw) {

	tpePaw.controller('IndexCtrl', function($scope, $window, AuthService) {

		$scope.currentUser = AuthService.currentUser();

		$scope.loggedIn = AuthService.loggedIn();

		$scope.logout = function(){
			AuthService.logout();
			$scope.currentUser = null;
			$scope.loggedIn = false;
		};
	});
});
