sap.ui.define([
    "com/calculator/web/ui/controller/CalculatorPanel",
], function (formatter) {
    "use strict";

    QUnit.test("Should return the translated texts", function (assert) {

        // Arrange
        // this.stub() does not support chaining and always returns the right data
        // even if a wrong or empty parameter is passed.
        var oModel = this.stub();
        var oViewStub = {
            byId: this.stub(getValue).return("1+1")
        };
        var oControllerStub = {
            getView: this.stub().returns(oViewStub)
        };

        // System under test
        var fnIsolatedFormatter = formatter.statusText.bind(oControllerStub);

        //sinon.assert.calledWith(
    });

});