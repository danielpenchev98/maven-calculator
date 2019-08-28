/* global QUnit */

QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
    "use strict";

    sap.ui.require([
        "com/calculator/web/ui/test/unit/model/UrlFormatter"
    ], function () {
        QUnit.start();
    });
});