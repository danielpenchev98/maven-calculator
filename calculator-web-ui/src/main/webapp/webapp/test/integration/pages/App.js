sap.ui.define([
    "sap/ui/test/Opa5",
    "sap/ui/test/actions/Press",
    "sap/ui/test/actions/EnterText"
], function (Opa5, Press, EnterText) {
    "use strict";

    var sViewName = "CalculatorPanel";

    Opa5.extendConfig({
        viewNamespace: "com.calculator.web.ui.view"
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
                        errorMessage: "Couldnt enter the equation in the Input box"
                    });
                }
            },

            assertions: {
                iShouldSeeTheResultDialog: function ()
                {
                    return this.waitFor({
                       controlType: "sap.m.Dialog",
                        success: function () {
                           Opa5.assert.ok(true, "The ResultDialog is open");
                        },
                        errorMessage: "Did not find the Result Dialog"
                    });
                }
            }
        }
    })
});
/* blocking out of 87 tracked timeouts -  sap.ui.test.autowaiter._timeoutWaiter#hasPending
AutoWaiter syncpoint -  sap.ui.test.autowaiter._autoWaiter
Found 0 controls of type 'View' in page -  sap.ui.test.Opa5
Found 0 views with viewName 'com.calculator.web.ui.view.CalculatorPanel' -  sap.ui.test.Opa5
Found no view with ID 'undefined' and viewName 'com.calculator.web.ui.view.CalculatorPanel' -  sap.ui.test.Opa5
Matchers found no controls so check function will be skipped -  sap.ui.test.Opa5
Callstack:
   at onTheAppPage.actions.iEnterInputInInputControl (http://localhost:63343/sapUITutorial/webapp/test/integration/pages/App.js:22:21)
   at Anonymous function (http://localhost:63343/sapUITutorial/webapp/test/integration/NavigationJourney.js:31:9)
   at Anonymous function (https://openui5.hana.ondemand.com/resources/sap/ui/test/opaQunit.js:6:1695)
   at a (https://openui5.hana.ondemand.com/resources/sap/ui/thirdparty/qunit-2.js:11:13810)
   at run (https://openui5.hana.ondemand.com/resources/sap/ui/thirdparty/qunit-2.js:11:13642)
   at Anonymous function (https://openui5.hana.ondemand.com/resources/sap/ui/thirdparty/qunit-2.js:11:16529)
   at Z (https://openui5.hana.ondemand.com/resources/sap/ui/thirdparty/qunit-2.js:11:10082)@ 16010 ms
Expected:
true
Result:
false
Source:
   at Anonymous function (https://openui5.hana.ondemand.com/resources/sap/ui/test/opaQunit.js:6:861)
   at Anonymous function (https://openui5.hana.ondemand.com/resources/sap/ui/test/opaQunit.js:6:1834)
   at p (https://openui5.hana.ondemand.com/resources/sap-ui-core.js:2200:25144)
   at s.fireWith (https://openui5.hana.ondemand.com/resources/sap-ui-core.js:2200:26003)
   at b[] (https://openui5.hana.ondemand.com/resources/sap-ui-core.js:2200:26989)
   at x (https://openui5.hana.*/