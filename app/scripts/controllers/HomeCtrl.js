'use strict';
define(['tpePaw', 'services/titleService'], function(tpePaw) {

	tpePaw.controller('HomeCtrl', function($scope, titleService) {
		titleService.setTitle("Versus");

	});
});
