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

        When.onTheAppPage.iEnterInputInInputControl("1+1").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheDialog("Result").and.iShouldSeeTheResponseValue("2.0").and
            .iShouldSeeTheCloseButton("Ok");

        When.onTheAppPage.iPressTheCloseDialogButton();

        Then.onTheAppPage.iShouldSeeTheView().and.iTeardownMyApp();

    });

   opaTest("Should open error dialog with Empty equation message", function(Given,When,Then) {

       Given.iStartMyUIComponent({
           componentConfig: {
               name: "com.calculator.web.ui",
               async: true
           },
       });

        When.onTheAppPage.iEnterInputInInputControl("").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheDialog("Error").iShouldSeeTheResponseValue("Empty equation").and
            .iShouldSeeTheCloseButton("Ok");

        When.onTheAppPage.iPressTheCloseDialogButton();

        Then.onTheAppPage.iShouldSeeTheView().and.iTeardownMyApp();

    });
});