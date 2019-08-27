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

        Then.onTheAppPage.iShouldSeeTheDialog("Result").and
            .iShouldSeeTheResponseValue("2.0").and
            .iShouldSeeTheCloseButton("Ok");

        When.onTheAppPage.iPressTheCloseDialogButton();
        Then.onTheAppPage.iShouldSeeTheView().iTeardownMyApp();

    });

   opaTest("Should open error dialog with \"Empty equation\" message", function(Given,When,Then) {


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

    opaTest("Should open error dialog with \"Missing or misplaced brackets\" message", function(Given,When,Then)
    {
        Given.iStartMyUIComponent({
            componentConfig: {
                name: "com.calculator.web.ui",
                async: true
            },
        });

        When.onTheAppPage.iEnterInputInInputControl("1+)").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheDialog("Error").and
            .iShouldSeeTheResponseValue("Missing or misplaced bracket").and
            .iShouldSeeTheCloseButton("Ok");

        When.onTheAppPage.iPressTheCloseDialogButton();
        Then.onTheAppPage.iShouldSeeTheView().and.iTeardownMyApp();
    });


    opaTest("Should open error dialog with \"Sequential components of the same type\" message", function(Given,When,Then)
    {
        Given.iStartMyUIComponent({
            componentConfig: {
                name: "com.calculator.web.ui",
                async: true
            },
        });

        When.onTheAppPage.iEnterInputInInputControl("1++2").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheDialog("Error").and
            .iShouldSeeTheResponseValue("Sequential components of the same type").and
            .iShouldSeeTheCloseButton("Ok");

        When.onTheAppPage.iPressTheCloseDialogButton();
        Then.onTheAppPage.iShouldSeeTheView().and.iTeardownMyApp();
    });

    opaTest("Should open error dialog with \"Missing operator between a number and an opening bracket or a closing bracket and a number\" message", function(Given,When,Then)
    {
        Given.iStartMyUIComponent({
            componentConfig: {
                name: "com.calculator.web.ui",
                async: true
            },
        });

        When.onTheAppPage.iEnterInputInInputControl("2(-1)").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheDialog("Error").and
            .iShouldSeeTheResponseValue("Missing operator between a number and an opening bracket or a closing bracket and a number").and
            .iShouldSeeTheCloseButton("Ok");

        When.onTheAppPage.iPressTheCloseDialogButton();
        Then.onTheAppPage.iShouldSeeTheView().and.iTeardownMyApp();
    });

    opaTest("Should open error dialog with \"Empty brackets\" message", function(Given,When,Then)
    {
        Given.iStartMyUIComponent({
            componentConfig: {
                name: "com.calculator.web.ui",
                async: true
            },
        });

        When.onTheAppPage.iEnterInputInInputControl("2+()").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheDialog("Error").and
            .iShouldSeeTheResponseValue("Empty brackets").and
            .iShouldSeeTheCloseButton("Ok");

        When.onTheAppPage.iPressTheCloseDialogButton();
        Then.onTheAppPage.iShouldSeeTheView().and.iTeardownMyApp();
    });

    opaTest("Should open error dialog with \"Scope of equation ending or beginning with an operator\" message", function(Given,When,Then)
    {
        Given.iStartMyUIComponent({
            componentConfig: {
                name: "com.calculator.web.ui",
                async: true
            },
        });

        When.onTheAppPage.iEnterInputInInputControl("+2+1").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheDialog("Error").and
            .iShouldSeeTheResponseValue("Scope of equation ending or beginning with an operator").and
            .iShouldSeeTheCloseButton("Ok");

        When.onTheAppPage.iPressTheCloseDialogButton();
        Then.onTheAppPage.iShouldSeeTheView().and.iTeardownMyApp();
    });

    opaTest("Should open error dialog with \"Division by zero\" message", function(Given,When,Then) {
        Given.iStartMyUIComponent({
            componentConfig: {
                name: "com.calculator.web.ui",
                async: true
            },
        });

        When.onTheAppPage.iEnterInputInInputControl("1/0").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheDialog("Error").and
            .iShouldSeeTheResponseValue("Division by zero").and
            .iShouldSeeTheCloseButton("Ok");

        When.onTheAppPage.iPressTheCloseDialogButton();
        Then.onTheAppPage.iShouldSeeTheView().and.iTeardownMyApp();
    });

    opaTest("Should open error dialog with \"Unsupported component :#\" message", function(Given,When,Then) {
        Given.iStartMyUIComponent({
            componentConfig: {
                name: "com.calculator.web.ui",
                async: true
            },
        });

        When.onTheAppPage.iEnterInputInInputControl("1#0").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheDialog("Error").and
            .iShouldSeeTheResponseValue("Unsupported component :#").and
            .iShouldSeeTheCloseButton("Ok");

        When.onTheAppPage.iPressTheCloseDialogButton();
        Then.onTheAppPage.iShouldSeeTheView().and.iTeardownMyApp();
    });
});