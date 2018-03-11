'use strict';
define(['tpePaw'], function(tpePaw) {

	tpePaw.controller('IndexCtrl', function($scope, $window) {

		/* TODO: get user from token */
		$scope.currentUser = {};
		$scope.currentUser = angular.fromJson($window.localStorage['currentUser']);

		if ($window.localStorage['currentUser'] != null){
			$scope.loggedIn = true;
		}else{
			$scope.loggedIn = false;
		}
	});
});
