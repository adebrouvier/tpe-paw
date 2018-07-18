'use strict';
define(['tpePaw'], function (tpePaw) {

    tpePaw.directive('rankingInfo', function () {
        return {
            restrict: 'E',
            templateUrl: 'views/ranking-info.html'
        };
    });
});