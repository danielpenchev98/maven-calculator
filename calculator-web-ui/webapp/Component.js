sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/model/json/JSONModel",
	"./controller/ResultDialog"
], function (UIComponent, JSONModel, ResultDialog) {
	"use strict";

	return UIComponent.extend("com.calculator.web.ui.Component", {

		metadata : {
			manifest: "json"
		},

		init : function () {
			// call the init function of the parent
			UIComponent.prototype.init.apply(this, arguments);

			// set data model
			var oResultData = {
					result : "0.0"
			};

			var oErrorData = {
				    errorCode : 400,
			        message : "Default"
			};
			var oResultModel = new JSONModel(oResultData);
			var oErrorModel=new JSONModel(oErrorData);

			this.setModel(oResultModel);
			this.setModel(oErrorModel,"errorModel");


			// set dialog
			this._resultDialog = new ResultDialog(this.getRootControl());
		},

		exit : function () {
			this._resultDialog.destroy();
			delete this._resultDialog;
		},

		openResultDialog : function () {
			this._resultDialog.open();
		}

	});

});