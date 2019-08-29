sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
	"../model/UrlFormatter"
], function (Controller, JSONModel,UrlFormatter) {
	"use strict";

	let baseUrl="http://localhost:7777";
	let resourceUrl="/api/v1/calculate";
	let queryParameter="equation";
	let serviceNotFoundResponseBody="{\"message\": \"Service unavailable\"}";

	return Controller.extend("com.calculator.web.ui.controller.CalculatorPanel", {

		onCalculation: function () {

			let equation = this.getView().byId("equation").getValue();
			let encodedEquation = UrlFormatter.encodeExpresion(equation);
			let url=baseUrl + resourceUrl +'?' + queryParameter +'='+ encodedEquation;
			let ownerComponent = this.getOwnerComponent();

			let oModel = new JSONModel();
			oModel.loadData(url).then(function () {
				ownerComponent.openResultDialog(oModel);
			}).catch(function (error) {
				if (error.statusCode === 500 || error.statusCode === 400 ) {
					oModel.setJSON(error.responseText);
				} else {
					oModel.setJSON(serviceNotFoundResponseBody);
				}
				ownerComponent.openErrorDialog(oModel);
			});
		}
	});
});