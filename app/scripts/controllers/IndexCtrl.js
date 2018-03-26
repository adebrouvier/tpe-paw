'use strict';
define(['tpePaw'], function(tpePaw) {

	tpePaw.controller('IndexCtrl', function($scope, $window) {

		/* TODO: get user from token */
		$scope.currentUser = {};

		if ($window.localStorage['currentUser'] != null){
			$scope.loggedIn = true;
			$scope.currentUser = angular.fromJson($window.localStorage['currentUser']);
		}

		if ($window.sessionStorage['currentUser'] != null){
			$scope.loggedIn = true;
			$scope.currentUser = angular.fromJson($window.sessionStorage['currentUser']);
		}
		else{
			$scope.loggedIn = false;
		}

		$scope.logout = function(){
			$window.localStorage.removeItem('currentUser');
			$window.sessionStorage.removeItem('currentUser');
			$scope.loggedIn = false;
		};
	});
});
