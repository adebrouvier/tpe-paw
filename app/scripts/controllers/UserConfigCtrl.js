'use strict';
define(['tpePaw', 'directives/gameAutocomplete', 'services/sessionService', 'services/titleService', 'services/authService', 'services/apiService'], function (tpePaw) {


  tpePaw.controller('UserConfigCtrl', function ($scope, $location, $routeParams, titleService, AuthService, apiService, sessionService, apiUrl) {


    $scope.youtubeRegexp = /^(?:https:\/\/)?(?:www\.)?(?:youtube\.com\/)(?:[\w-]+)/i;
    $scope.twitchRegexp = /^(?:https:\/\/)?(?:www\.)?(?:twitch\.tv\/)(?:[\w-]+)/i;
    $scope.twitterRegexp = /^(?:https:\/\/)?(?:www\.)?(?:twitter\.com\/)(?:[\w-]+)/i;
    $scope.username = $routeParams.username;
    $scope.user = {};
    $scope.imageSrc = apiUrl + "/users/profile-image/" + $scope.username;
    $scope.maxImageLength = 5 * 1024 * 1024;
    $scope.imageInvalidSize = false;
    $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;

    $scope.$on("fileProgress", function (e, progress) {
      $scope.progress = progress.loaded / progress.total;
    });

    /**
     *
     * User Update function
     *
     **/

    $scope.updateUser = function () {
      var userData;
      var imageLength = ($scope.imageSrc.length) * 3 / 4;
      if (imageLength > $scope.maxImageLength) {
        $scope.imageInvalidSize = true;
      }
      if ($scope.form.$valid && imageLength < $scope.maxImageLength) {
        if ($scope.user.favoriteGame == null || $scope.user.favoriteGame.name == null) {
          userData = {
            description: $scope.user.description,
            twitterUrl: $scope.user.twitterUrl,
            twitchUrl: $scope.user.twitchUrl,
            youtubeUrl: $scope.user.youtubeUrl,
            game: ""
          };

        } else {
          userData = {
            description: $scope.user.description,
            twitterUrl: $scope.user.twitterUrl,
            twitchUrl: $scope.user.twitchUrl,
            youtubeUrl: $scope.user.youtubeUrl,
            game: $scope.user.favoriteGame.name
          };
        }
        var image = $scope.imageSrc;
        var formData = new FormData();

        if ($scope.hasNewImage) {
          formData.append('image', $scope.dataURItoBlob(image));
        }
        formData.append('user', new Blob([JSON.stringify(userData)], {type: "application/json"}));

        var metadata = {
          transformRequest: angular.identity,
          headers: {
            'Content-Type': undefined,
            'Authorization': sessionService.currentUser().token
          }
        };


        return apiService.updateUser($scope.username, formData, metadata)
          .then(function (response) {
            $location.path('/users/' + $scope.username);
          })
          .catch(function (response) {
            $location.path('/users/' + $scope.username);
          });
      }

    };

    $scope.newImage = function () {
      $scope.hasNewImage = true;
    };

    $scope.dataURItoBlob = function (dataURI) {
      var byteString = atob(dataURI.split(',')[1]);

      var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

      var ab = new ArrayBuffer(byteString.length);

      var ia = new Uint8Array(ab);

      for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }

      var blob = new Blob([ab], {type: mimeString});
      return blob;

    };

    apiService.get('/users/' + $scope.username)
      .then(function successCallback(response) {
        $scope.user = response.data;
        titleService.setTitle($scope.user.username + ' - Versus');
      }, function errorCallback(response) {
        $location.path('#/404');
      });

    function readURL(input) {
      if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
          $('#user-preview').attr('src', e.target.result);
        };

        reader.readAsDataURL(input.files[0]);
      }
    }
  });


  tpePaw.directive("ngFileSelect", function (fileReader, $timeout) {
    return {
      scope: {
        ngModel: '='
      },
      link: function ($scope, el) {
        function getFile(file) {
          fileReader.readAsDataUrl(file, $scope)
            .then(function (result) {
              $timeout(function () {
                $scope.ngModel = result;
              });
            });
        }

        el.bind("change", function (e) {
          var file = (e.srcElement || e.target).files[0];
          getFile(file);
        });
      }
    };
  });

  tpePaw.factory("fileReader", function ($q, $log) {
    var onLoad = function (reader, deferred, scope) {
      return function () {
        scope.$apply(function () {
          deferred.resolve(reader.result);
        });
      };
    };

    var onError = function (reader, deferred, scope) {
      return function () {
        scope.$apply(function () {
          deferred.reject(reader.result);
        });
      };
    };

    var onProgress = function (reader, scope) {
      return function (event) {
        scope.$broadcast("fileProgress", {
          total: event.total,
          loaded: event.loaded
        });
      };
    };

    var getReader = function (deferred, scope) {
      var reader = new FileReader();
      reader.onload = onLoad(reader, deferred, scope);
      reader.onerror = onError(reader, deferred, scope);
      reader.onprogress = onProgress(reader, scope);
      return reader;
    };

    var readAsDataURL = function (file, scope) {
      var deferred = $q.defer();

      var reader = getReader(deferred, scope);
      reader.readAsDataURL(file);

      return deferred.promise;
    };

    return {
      readAsDataUrl: readAsDataURL
    };
  });


});


