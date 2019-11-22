sap.ui.define([
    "sap/ui/test/Opa5",
    "sap/ui/test/actions/Press",
    "sap/ui/test/actions/EnterText",
    "sap/ui/test/matchers/Properties",
    "sap/ui/test/matchers/AggregationLengthEquals"
], function (Opa5, Press, EnterText,Properties,AggregationLengthEquals) {
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
                iEnterInputInInputControl: function (expression) {
                    return this.waitFor({
                        id: "expression",
                        viewName: sViewName,
                        actions: new EnterText({text: expression}),
                        errorMessage: "Couldn't enter the expression in the input box"
                    });
                },
                iPressTheCloseDialogButton: function() {
                    return this.waitFor({
                        searchOpenDialogs: true,
                        controlType: "sap.m.Button",
                        actions: new Press(),
                        errorMessage: "Couldn't close the dialog"
                    })
                },
                iClearSessionStorage : function(){
                    return sessionStorage.clear();
                }
            },

            assertions: {

                iShouldSeeTheCalculationResult: function(result)
                {
                    return this.waitFor({
                        id: "expression",
                        viewName : sViewName,
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
                iShouldSeeCalculationRecordInTable:function(id,expression,result){
                    return this.waitFor({
                        id:"calculationsTable",
                        viewName : "CalculationHistory",
                        matchers:  new AggregationLengthEquals({
                            name: "items",
                            length: 1
                        }),
                        check : function(oTable){
                            let historyRecordsJSON=JSON.parse(oTable.getModel().getJSON());
                            return historyRecordsJSON[id].expression===expression&&historyRecordsJSON[id].result==result;
                        },
                        success: function () {
                            Opa5.assert.ok(true, "The table has the expected 1 item");
                        },
                        errorMessage: "Table does not have entries or the entry wasn't with the expected values"
                        })
                },
                iShouldSeeTable:function(){
                    return this.waitFor({
                        id:"calculationsTable",
                        viewName : "CalculationHistory",
                        success: function () {
                            Opa5.assert.ok(true, "The table is there");
                        },
                        errorMessage: "The table is not there."
                    })
                }
            }
        }
    })
});
