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

    let assertAppearanceOfDialog = function (Then, title, content, textOfCloseButton) {
        Then.onTheAppPage.iShouldSeeTheDialog(title).and
            .iShouldSeeTheErrorMessage(content).and
            .iShouldSeeTheCloseButton(textOfCloseButton);
    };

    QUnit.module("Calculation");

    opaTest("Should open result dialog with the result", function (Given, When, Then) {

        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("1+1").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheCalculationResult("2.0");

        Then.onTheAppPage.iTeardownMyUIComponent();
    });

    opaTest("Should open error dialog with \"Empty equation\" message", function (Given, When, Then) {

        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("").and.iPressTheCalculateButton();

        assertAppearanceOfDialog(Then, "Error", "Empty equation", "Ok");

        Then.onTheAppPage.iTeardownMyUIComponent();

    });

    opaTest("Should open error dialog with \"Missing or misplaced brackets\" message", function (Given, When, Then) {

        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("1+)").and.iPressTheCalculateButton();

        assertAppearanceOfDialog(Then, "Error", "Missing or misplaced bracket", "Ok");

        Then.onTheAppPage.iTeardownMyUIComponent();
    });


    opaTest("Should open error dialog with \"Sequential components of the same type\" message", function (Given, When, Then) {

        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("1++2").and.iPressTheCalculateButton();

        assertAppearanceOfDialog(Then, "Error", "Sequential components of the same type", "Ok");

        Then.onTheAppPage.iTeardownMyUIComponent();
    });

    opaTest("Should open error dialog with \"Missing operator between a number and an opening bracket or a closing bracket and a number\" message", function (Given, When, Then) {

        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("2(-1)").and.iPressTheCalculateButton();

        assertAppearanceOfDialog(Then, "Error", "Missing operator between a number and an opening bracket or a closing bracket and a number", "Ok");

        Then.onTheAppPage.iTeardownMyUIComponent();
    });

    opaTest("Should open error dialog with \"Empty brackets\" message", function (Given, When, Then) {

        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("2+()").and.iPressTheCalculateButton();

        assertAppearanceOfDialog(Then, "Error", "Empty brackets", "Ok");

        Then.onTheAppPage.iTeardownMyUIComponent();
    });

    opaTest("Should open error dialog with \"Scope of equation ending or beginning with an operator\" message", function (Given, When, Then) {

        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("+2+1").and.iPressTheCalculateButton();

        assertAppearanceOfDialog(Then, "Error", "Scope of equation ending or beginning with an operator", "Ok");

        Then.onTheAppPage.iTeardownMyUIComponent();
    });

    opaTest("Should open error dialog with \"Division by zero\" message", function (Given, When, Then) {
        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("1/0").and.iPressTheCalculateButton();

        assertAppearanceOfDialog(Then, "Error", "Division by zero", "Ok");

        Then.onTheAppPage.iTeardownMyUIComponent();
    });

    opaTest("Should open error dialog with \"Unsupported component :#\" message", function (Given, When, Then) {
        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("1#0").and.iPressTheCalculateButton();

        assertAppearanceOfDialog(Then, "Error", "Unsupported component :#", "Ok");

        Then.onTheAppPage.iTeardownMyUIComponent();
    });

    opaTest("Should open ErrorDialog and close it", function(Given, When, Then) {
        startUiComponent(Given);

        When.onTheAppPage.iEnterInputInInputControl("").and.iPressTheCalculateButton();

        Then.onTheAppPage.iShouldSeeTheDialog("Error");

        When.onTheAppPage.iPressTheCloseDialogButton();

        Then.onTheAppPage.iTeardownMyUIComponent();
    });


});