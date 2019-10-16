module.exports = function(config) {
    config.set({
        basePath: "../../../main",
        frameworks: ["ui5"],
        ui5: {
            type: "application",
            //to revert back to https if karma does not work
            url: "https://localhost:9000",
            testpage: "webapp/test/testsuite.qunit.html"
        },
        port:9000,
        browsers: ["ChromeHeadless"],
        singleRun: true,
        plugins: [
            'karma-chrome-launcher',
            'karma-ui5'
        ]


    });
};
