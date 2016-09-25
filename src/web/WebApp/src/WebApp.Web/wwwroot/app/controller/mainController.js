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

        activate();

        function activate() { }
    }
})();