sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
	"../model/UrlFormatter"
], function (Controller, JSONModel,UrlFormatter) {
	"use strict";

	return Controller.extend("com.calculator.web.ui.controller.CalculatorPanel", {

		onCalculation: function () {

			let equation = this.getView().byId("equation").getValue();
			let encodedEquation = UrlFormatter.encodeExpresion(equation);
			let url="/api/v1/calculate?equation=" + encodedEquation;
			let ownerComponent = this.getOwnerComponent();

			let oModel = new JSONModel();
			oModel.loadData(url).then(function () {
				ownerComponent.openResultDialog(oModel);
			}).catch(function (error) {

				//if (error.statusCode === 0) {
				//	oModel.setJSON("{\"message\": \"Service unavailable\"}");
			//	} else {
					oModel.setJSON(error.responseText);
			//	}
				ownerComponent.openErrorDialog(oModel);
			});
		}
	});

});