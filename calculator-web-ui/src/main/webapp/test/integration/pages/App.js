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
                         errorMessage: "Couldn't find the calculateButton on the CalculatorPanel view"
                     });
                },
                iEnterInputInInputControl: function (equation) {
                    return this.waitFor({
                        id: "equation",
                        viewName: sViewName,
                        actions: new EnterText({text: equation}),
                        errorMessage: "Couldn't enter the equation in the input box"
                    });
                },
                iPressTheCloseDialogButton: function() {
                    return this.waitFor({
                        searchOpenDialogs: true,
                        controlType: "sap.m.Button",
                        actions: new Press(),
                        errorMessage: "Couldn't close the dialog"
                    })
                }
            },

            assertions: {

                iShouldSeeTheCalculationResult: function(result)
                {
                    return this.waitFor({
                        viewName : sViewName,
                        controlType: "sap.m.Input",
                        matchers: new Properties({
                            value : result
                        }),
                        success: function () {
                            Opa5.assert.ok(true, "the right result value");
                        },
                        errorMessage: "the wrong result value"
                    });
                },

                iShouldSeeTheErrorMessage: function (message)
                {
                    return this.waitFor({
                        searchOpenDialogs: true,
                        controlType: "sap.m.Text",
                        matchers: new Properties({
                            text : message
                        }),
                        success: function () {
                           Opa5.assert.ok(true, "the right error message");
                        },
                        errorMessage: "the wrong error message"
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
                        matchers: new Properties({
                            text : text
                        }),
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
                        errorMessage: "The View is not to be seen"
                    })
                },
                /*iShouldSeePendingCalculationRecordInTable:function(){
                    return this.waitFor({
                        id: "calculationsTable",
                        viewName : "CalculationsTable",
                        matchers : new AggregationLengthEquals({
                            name: "items",
                            length: 1
                        }),
                        success: function () {
                            Opa5.assert.ok(true, "The table has 1 items");
                        },
                        errorMessage: "Table does not have all entries."
                        })
                }*/
            }
        }
    })
});
