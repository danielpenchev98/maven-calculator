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
        captureTimeout: 200000,
        browserNoActivityTimeout: 500000,
        browsers: ["Chrome"],
        singleRun: true,
        plugins: [
            'karma-chrome-launcher',
            'karma-ui5'
        ]


    });
};
