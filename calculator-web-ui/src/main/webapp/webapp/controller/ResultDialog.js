sap.ui.define([
	"sap/ui/base/ManagedObject",
	"sap/ui/core/Fragment"
], function (ManagedObject, Fragment) {
	"use strict";

	return ManagedObject.extend("com.calculator.web.ui.controller.ResultDialog", {

		constructor : function (oView) {
			this._oView = oView;
		},

		exit : function () {
			delete this._oView;
		},

		open : function (oModel) {
			let oView = this._oView;
            oView.setModel(oModel);

			if (!oView.byId("resultDialog")) {
				let oFragmentController = {
					onCloseDialog : function () {
						oView.byId("resultDialog").close();
					}
				};
				Fragment.load({
					id: oView.getId(),
					name: "com.calculator.web.ui.view.ResultDialog",
					controller: oFragmentController
				}).then(function (oDialog) {
					oView.addDependent(oDialog);
					oDialog.open();
				});
			} else {
				oView.byId("resultDialog").open();
			}
		}

	});

});