'use strict';
define(['routes',
	'services/dependencyResolverFor',
	'i18n/i18nLoader!',
	'angular',
	'angular-route',
	'bootstrap',
	'angular-translate',
    'infinite-scroll',
	'typeahead',
	'bloodhound',
	'angular-loading-bar',
	'angular-animate',
	'angular-messages',
  'angular-ui-sortable'],
	function(config, dependencyResolverFor, i18n) {
		var tpePaw = angular.module('tpePaw', [
			'ngRoute',
			'pascalprecht.translate',
            'infinite-scroll',
			'angular-loading-bar',
			'ngAnimate',
			'ngMessages',
      'ui.sortable'
		]);
		tpePaw
			.config(
				['$routeProvider',
				'$controllerProvider',
				'$compileProvider',
				'$filterProvider',
				'$provide',
				'$translateProvider',
				'cfpLoadingBarProvider',
				function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $translateProvider, cfpLoadingBarProvider) {

					tpePaw.controller = $controllerProvider.register;
					tpePaw.directive = $compileProvider.directive;
					tpePaw.filter = $filterProvider.register;
					tpePaw.factory = $provide.factory;
					tpePaw.service = $provide.service;

					if (config.routes !== undefined) {
						angular.forEach(config.routes, function(route, path) {
							$routeProvider.when(path, {templateUrl: route.templateUrl, resolve: dependencyResolverFor(['controllers/' + route.controller]), controller: route.controller, gaPageTitle: route.gaPageTitle});
						});
					}
					if (config.defaultRoutePath !== undefined) {
						$routeProvider.otherwise({redirectTo: config.defaultRoutePath});
					}

					$translateProvider.translations('preferredLanguage', i18n);
					$translateProvider.preferredLanguage('preferredLanguage');

					cfpLoadingBarProvider.includeSpinner = false;
				}])
      .value('apiUrl', 'http://localhost:8080');
		return tpePaw;
	}
);
