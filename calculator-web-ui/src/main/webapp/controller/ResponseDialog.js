sap.ui.define([
    "sap/ui/base/ManagedObject",
    "sap/ui/core/Fragment",
], function (ManagedObject, Fragment) {
    "use strict";

    return ManagedObject.extend("com.calculator.web.ui.controller.ResponseDialog", {
        open : function (oView,oModel,id,nameOfFragment) {
            oView.setModel(oModel);

            if (!oView.byId(id)) {
                 var oFragmentController = {
                     onCloseDialog : function () {
                        oView.byId(id).close();
                     }
                 };
                Fragment.load({
                    id: oView.getId(),
                    name: nameOfFragment,
                    controller: oFragmentController
                }).then(function (oDialog) {
                    oView.addDependent(oDialog);
                    oDialog.open();
                });
            } else {
                oView.byId(id).open();
            }
        }

    });

});