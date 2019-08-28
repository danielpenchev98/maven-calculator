module.exports = function(config) {
    config.set({

        frameworks: ["ui5"],
        ui5: {
            type: "application",
            paths: {
                webapp: "webapp"
            },
            url: "https://localhost:9000",
            testpage: "webapp/test/integration/opaTests.qunit.html"
        },
        port:9000,
        browsers: ["Chrome"],
        plugins: [
            'karma-chrome-launcher',
            'karma-ui5'
        ]

    });
};