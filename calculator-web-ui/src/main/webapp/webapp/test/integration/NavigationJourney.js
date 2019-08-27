/*global QUnit, opaTest*/
sap.ui.define([
    "sap/ui/test/opaQunit",
    "./pages/App"
], function(){
    "use strict";

    QUnit.module("Navigation");

    opaTest("Should open result dialog with the result", function(Given,When,Then) {

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