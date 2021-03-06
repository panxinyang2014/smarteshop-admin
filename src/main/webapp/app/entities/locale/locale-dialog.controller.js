(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('LocaleDialogController', LocaleDialogController);

    LocaleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Locale'];

    function LocaleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Locale) {
        var vm = this;

        vm.locale = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.locale.id !== null) {
                Locale.update(vm.locale, onSaveSuccess, onSaveError);
            } else {
                Locale.save(vm.locale, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:localeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
