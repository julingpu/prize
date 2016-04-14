// Generated on 2015-11-06 using
// generator-webapp 1.1.0
'use strict';

// # Globbing
// for performance reasons we're only matching one level down:
// 'test/spec/{,*/}*.js'
// If you want to recursively match all subfolders, use:
// 'test/spec/**/*.js'

module.exports = function (grunt) {

  // Time how long tasks take. Can help when optimizing build times
  require('time-grunt')(grunt);

  // Automatically load required grunt tasks
  require('jit-grunt')(grunt, {
    useminPrepare: 'grunt-usemin'
  });

  // Configurable paths
  var config = {
    app: 'app',
    dist: 'dist'
  };

  // Define the configuration for all the tasks
  grunt.initConfig({

    // read settings from package.json file

    // Project settings
    config: config,
    
    watch: {
      allFiles: {
        files: [ 
          '<%= config.app %>/*.html',
          '<%= config.app %>/styles/main.css',
          '<%= config.app %>/scripts/main.js',
          'personal-homepage/index.html',
          'personal-homepage/style.css'          
        ],
        options: {
          livereload: true
        }
      }
    }
  
  });
    
};
