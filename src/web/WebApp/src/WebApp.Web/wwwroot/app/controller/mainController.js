(function () {
    'use strict';

    angular
        .module('app')
        .controller('mainController', mainController);

    mainController.$inject = ['$location'];

    function mainController($location, $scope, $http) {
        /* jshint validthis:true */
        var vm = this;
        vm.title = 'mainController';

        $http.get("welcome.htm")
            .then(function (response) {
            $scope.data = function() {
                
            response.data;}
        });


        activate();

        function activate() { }
    }
})();