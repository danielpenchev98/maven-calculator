sap.ui.define([
    "sap/ui/test/Opa5",
    "sap/ui/test/actions/Press",
    "sap/ui/test/actions/EnterText",
    "sap/ui/test/matchers/Properties"
], function (Opa5, Press, EnterText,Properties) {
    "use strict";

    var sViewName = "CalculatorPanel";

    Opa5.extendConfig({
        viewNamespace: "com.calculator.web.ui.view",
        autoWait: true
    });


    Opa5.createPageObjects({
        onTheAppPage: {
            actions: {
                iPressTheCalculateButton: function () {
                     return this.waitFor({
                         id: "calculateButton",
                         viewName: sViewName,
                         actions: new Press(),
                         errorMessage: "Couldnt find the calculateButton on the CalculatorPanel view"
                     });
                },
                iEnterInputInInputControl: function (equation) {
                    return this.waitFor({
                        id: "equation",
                        viewName: sViewName,
                        actions: new EnterText({text: equation}),
                        errorMessage: "Couldnt enter the equation in the input box"
                    });
                },
                iPressTheCloseDialogButton: function() {
                    return this.waitFor({
                        searchOpenDialogs: true,
                        controlType: "sap.m.Button",
                        actions: new Press(),
                        errorMessage: "Couldnt close the dialog"
                    })
                }
            },

            assertions: {
                iShouldSeeTheResponseValue: function (result)
                {
                    return this.waitFor({
                       controlType: "sap.m.Text",
                        matchers: new Properties({
                            text : result
                        }),
                        success: function () {
                           Opa5.assert.ok(true, "the right response value");
                        },
                        errorMessage: "the wrong response value"
                    });
                },

                iShouldSeeTheDialog: function(title)
                {
                    return this.waitFor({
                        controlType: "sap.m.Dialog",
                        matchers: new Properties({
                            title: title
                        }),
                        success: function () {
                            Opa5.assert.ok(true, "The Dialog is open");
                        },
                        errorMessage: "Did not find the Dialog"
                    });
                },

                iShouldSeeTheCloseButton: function(text)
                {
                    return this.waitFor({
                       controlType: "sap.m.Button",
                       searchOpenDialogs: true,
                        success: function () {
                            Opa5.assert.ok(true, "The close button is available");
                        },
                        errorMessage: "Did not find the close button"
                    })
                },
                iShouldSeeTheView: function () {
                    return this.waitFor({
                        controlType: "sap.ui.core.mvc.XMLView",
                        success: function () {
                            Opa5.assert.ok(true, "The View is to be seen");
                        },
                        errorMessage: "The View is to be seen"
                    })
                }
            }
        }
    })
});
