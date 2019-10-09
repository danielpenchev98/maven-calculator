/*global QUnit*/
sap.ui.define([
    "com/calculator/web/ui/model/UrlFormatter",
], function (UrlFormatter) {
    "use strict";

    QUnit.test("Should return url encoded +", function (assert) {
        assert.strictEqual(UrlFormatter.encodeExpresion("1+1"), "1%2B1", "Correct encoding");
    });
});