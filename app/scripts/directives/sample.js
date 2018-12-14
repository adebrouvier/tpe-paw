'use strict';
define(['tpePaw'], function (tpePaw) {

  tpePaw.directive('sample', function () {
    return {
      restrict: 'E',
      template: '<span>Sample</span>'
    };
  });
});
