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
			UIComponent.prototype.init.apply(this, arguments);
			this._errorDialog = new ErrorDialog(this.getRootControl());
		},

		exit : function () {
			this._errorDialog.destroy();
			delete this._errorDialog;

		},

		openErrorDialog : function (oModel) {
			this._errorDialog.open(oModel);
		}

	});

});