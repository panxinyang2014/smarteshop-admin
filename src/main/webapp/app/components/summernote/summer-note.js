(function() {
	'use strict';
	angular
	.module('smarteshopApp')
	.controller('CallbacksCtrl', CallbacksCtrl);
	CallbacksCtrl.$inject = ['$scope','summernote','uibPaginationConfig'];
	function CallbacksCtrl($scope){
		$scope.options = {
				height: 300,
				focus: true,
				airMode: true,
				toolbar: [
				          ['edit',['undo','redo']],
				          ['headline', ['style']],
				          ['style', ['bold', 'italic', 'underline', 'superscript', 'subscript', 'strikethrough', 'clear']],
				          ['fontface', ['fontname']],
				          ['textsize', ['fontsize']],
				          ['fontclr', ['color']],
				          ['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
				          ['height', ['height']],
				          ['table', ['table']],
				          ['insert', ['link','picture','video','hr']],
				          ['view', ['fullscreen', 'codeview']],
				          ['help', ['help']]
				          ]
		};
		$scope.init = function() {
			console.log('Summernote is launched');
		};
		$scope.enter = function() {
			console.log('Enter/Return key pressed');
		};
		$scope.focus = function(e) {
			console.log('Editable area is focused');
		};
		$scope.blur = function(e) {
			console.log('Editable area loses focus');
		};
		$scope.paste = function(e) {
			console.log('Called event paste: '
					+ e.originalEvent.clipboardData.getData('text'));
		};
		$scope.change = function(contents) {
			console.log('contents are changed:', contents,
					$scope.editable);
		};
		$scope.keyup = function(e) {
			console.log('Key is released:', e.keyCode);
		};
		$scope.keydown = function(e) {
			console.log('Key is pressed:', e.keyCode);
		};
		$scope.imageUpload = function(files) {
			console.log('image upload:', files);
			console.log('image upload\'s editor:', $scope.editor);
			console.log('image upload\'s editable:', $scope.editable);
		};
	};
})();