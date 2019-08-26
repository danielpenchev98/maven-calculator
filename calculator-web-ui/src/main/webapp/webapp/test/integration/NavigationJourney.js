/*global QUnit, opaTest*/
sap.ui.define([
    "sap/ui/test/opaQunit",
    "./pages/App"
], function(){
    "use strict";

    QUnit.module("Navigation",{
        beforeEach: function () {
            this.server = sinon.fakeServer.create();
            this.server.autoRespond=true;
            this.server.autpRespondAfter=50;
        },
        afterEach: function () {
            this.server.restore();
        }
    });

    opaTest("Should open result dialog with the result", function(Given,When,Then) {

        this.server.respondWith("GET","/api/v1/calculate?equation=1%2b1",
            [200,{"Content-Type": "application/json" },
                '[{"result":"2.0"}]']);

        Given.iStartMyUIComponent({
            componentConfig: {
                name: "com.calculator.web.ui",
                async: true
            },
        });

        When.onTheAppPage.iEnterInputInInputControl("1+1");
        When.onTheAppPage.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheResultDialog();

        Then.iTeardownMyApp();

    });
});