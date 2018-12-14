'use strict';
define(['tpePaw', 'services/apiService'], function(tpePaw) {

  tpePaw.directive('existingUser', function($q, apiService) {
    return {
      require: 'ngModel',
      restrict: 'A',
      link: function(scope, elem, attrs, ngModel) {

        ngModel.$asyncValidators.existingUser = function(modelValue, viewValue) {

            if (ngModel.$isEmpty(viewValue)) {
              var defer = $q.defer();
              defer.resolve();
              return defer.promise;
            }

            return apiService.availableUsername(viewValue)
              .then(
                function (response) {
                  var defer = $q.defer();

                  if (!response.data.valid) {
                    defer.resolve();
                  } else {
                    defer.reject();
                  }
                  return defer.promise;
                }
              );
        };
      }
    };
  });
});
