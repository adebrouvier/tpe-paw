'use strict';
define(['tpePaw', 'services/titleService'], function(tpePaw) {

	tpePaw.controller('404Ctrl', function($scope, $filter, titleService) {
		titleService.setTitle($filter('translate')('NOTFOUND_TITLE'));

	});
});