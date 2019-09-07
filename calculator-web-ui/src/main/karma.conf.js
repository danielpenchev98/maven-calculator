module.exports = function(config) {
    config.set({

        frameworks: ["ui5"],
        ui5: {
            type: "application",
            paths: {
                webapp: "webapp"
            },
            url: "https://localhost:9000",
            testpage: "webapp/test/testsuite.qunit.html"
        },
        port:9000,
        browsers: ["Firefox"],
        singleRun: true,
        plugins: [
            'karma-firefox-launcher',
            'karma-ui5'
        ]


    });
};
