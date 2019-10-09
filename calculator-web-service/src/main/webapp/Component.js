sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/model/json/JSONModel",
	"./controller/ErrorDialog"
], function (UIComponent, JSONModel,ErrorDialog) {
	"use strict";

	return UIComponent.extend("com.calculator.web.ui.Component", {

		metadata : {
			manifest: "json"
		},

		init : function () {
			// call the init function of the parent
			UIComponent.prototype.init.apply(this, arguments);

			// set dialog
			this._errorDialog = new ErrorDialog(this.getRootControl());


			let oData = { result : "0" };
			let oResultModel= new JSONModel(oData);

			this.setModel(oResultModel);
		},

		exit : function () {
			this._errorDialog.destroy();
			delete this._errorDialog;

		},

		openErrorDialog : function (oModel) {
			this._errorDialog.open(oModel);
		},

		showResult : function (oModel) {
			let result = oModel.getProperty("/result");
			this.getModel().setProperty("/result",result);
		}

	});

});