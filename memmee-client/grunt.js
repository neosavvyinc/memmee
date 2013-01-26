module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        clean: {
            folder: "target/"
        },
        copy: {
            target: {
                options: {
                    cwd: 'src/main/resources/**/*'
                },
                files: {
                    'target/memmee/': [
                        'src/main/resources/*.html',
                        'src/main/resources/*.ico',
                        'src/main/resources/**/*.html',
                        'src/main/resources/lib/**/*',
                        'src/main/resources/**/*.json'
                    ]
                }
            }
        },
        concat: {
            dist: {
                src: ['src/main/resources/js/**/*.js'],
                dest: 'target/memmee/memmee.js'
            }
        },
        min: {
            dist: {
                src: ['target/memmee/memmee.js'],
                dest: 'target/memmee/memmee.min.js'
            }
        },
        htmlrefs: {
            dist: {

                src: 'target/memmee/index.html',
                dest: 'target/memmee/',
                options: {
                    buildNumber: 1
                }
            }
        },
        testacular: {
            endToEnd: {
                configFile: 'src/test/resources/testacular-e2e-config.js',
                singleRun: true,
                browsers: ['Chrome']
            }
        },
        zip: {
            'target/memmee.zip': ['target/memmee/**/*']
        }
    });

    grunt.loadNpmTasks('gruntacular');
    grunt.loadNpmTasks('grunt-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-htmlrefs');
    grunt.loadNpmTasks('grunt-zip');

    // Default task.
    grunt.registerTask('default', 'clean copy concat min htmlrefs:dist testacular:endToEnd zip');


};