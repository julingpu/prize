define([], function( ) {
	var exports = {};
	exports.config = require.config ({
        baseUrl: '../bower_components',
		paths: {        
			jquery : 'jquery/dist/jquery.min',
			foundation: 'foundation/js/foundation',
			foundation_tab: 'foundation/js/foundation/foundation.tab',
			modernizr: 'modernizr/modernizr',
			user: '../app/scripts',
			runtime: 'jade/runtime.js',
			templates: 'jade/'			
		},
		shim: {
			'foundation': {
				deps: ['jquery'],
				exports: 'Foundation'
			},
			'foundation_tab': {
				deps: ['jquery', 'foundation'],
				exports: 'Foundation.lib'
			}
		}
	});
	
	return exports;
});