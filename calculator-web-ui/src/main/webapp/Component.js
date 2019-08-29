sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/model/json/JSONModel",
	"./controller/ResultDialog",
	"./controller/ErrorDialog"
], function (UIComponent, JSONModel, ResultDialog,ErrorDialog) {
	"use strict";

	return UIComponent.extend("com.calculator.web.ui.Component", {

		metadata : {
			manifest: "json"
		},

		init : function () {
			// call the init function of the parent
			UIComponent.prototype.init.apply(this, arguments);

			// set dialog
			this._errorDialog = new ErrorDialog();
			this._resultDialog = new ResultDialog();
		},

		exit : function () {
			this._errorDialog.destroy();
			this._resultDialog.destroy();
			delete this._errorDialog;
			delete this._resultDialog;

		},

		openResultDialog : function (oModel) {
			this._resultDialog.open(this.getRootControl(),oModel,"resultDialog","com.calculator.web.ui.view.ResultDialog");
		},

		openErrorDialog : function (oModel) {
			this._errorDialog.open(this.getRootControl(),oModel,"errorDialog","com.calculator.web.ui.view.ErrorDialog");
		}

	});

});