sap.ui.define([
    "sap/ui/base/ManagedObject",
    "sap/ui/core/Fragment",
], function (ManagedObject, Fragment) {
    "use strict";

    return ManagedObject.extend("com.calculator.web.ui.controller.ErrorDialog", {

        constructor : function (oView) {
            this._oView = oView;
        },

        exit : function () {
            delete this._oView;
        },

        open : function (oModel) {
            var oView = this._oView;
            oView.setModel(oModel);

            if (!oView.byId("errorDialog")) {
                 var oFragmentController = {
                     onCloseDialog : function () {
                        oView.byId("errorDialog").close();
                     }
                 };
                Fragment.load({
                    id: oView.getId(),
                    name: "com.calculator.web.ui.view.ErrorDialog",
                    controller: oFragmentController
                }).then(function (oDialog) {
                    oView.addDependent(oDialog);
                    oDialog.open();
                });
            } else {
                oView.byId("errorDialog").open();
            }
        }

    });

});