sap.ui.define([
    "sap/ui/test/opaQunit",
    "./pages/App"
], function () {
    "use strict";

    let startUiComponent = function (Given) {
        Given.iStartMyUIComponent({
            componentConfig: {
                name: "com.calculator.web.ui",
                async: true
            },
        });
    };

    QUnit.module("History");

    opaTest("Should see new record in the history table", function (Given, When, Then) {

        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("1+1").and.iPressTheCalculateButton();

        //Then.onTheAppPage.iShouldSeePendingCalculationRecordInTable("1+1","Pending calculation");

        //Then.onTheAppPage.iShouldSeeCompletedCalculationRecordInTable("1+1","2.0");

        Then.onTheAppPage.iTeardownMyApp();

    });


});