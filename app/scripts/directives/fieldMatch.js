'use strict';
define(['tpePaw', 'services/apiService'], function (tpePaw) {

  tpePaw.directive('fieldMatch', function () {
    return {
      require: 'ngModel',
      restrict: 'A',
      scope: {
        field: '=fieldMatch'
      },
      link: function (scope, elem, attrs, ngModel) {

        ngModel.$validators.fieldMatch = function (modelValue, viewValue) {
          return scope.field.$viewValue === viewValue;
        };
      }
    };
  });
});
