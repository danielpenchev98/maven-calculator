sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
	"../model/UrlFormatter"
], function (Controller, JSONModel,UrlFormatter) {
	"use strict";

	let serviceNotFoundResponseBody="{\"message\": \"Service unavailable\"}";

	return Controller.extend("com.calculator.web.ui.controller.CalculatorPanel", {

		onCalculation: function () {

			let equation = this.getView().byId("equation").getValue();
			let encodedEquation = UrlFormatter.encodeExpresion(equation);

			let ownerComponent = this.getOwnerComponent();
			let serviceConfig = ownerComponent.getManifestEntry("/sap.app/dataSources/calculationService");
			let url=serviceConfig.baseUrl + serviceConfig.serviceUrl +'?' + serviceConfig.queryParam +'='+ encodedEquation;

			let oModel = new JSONModel();
			oModel.loadData(url).then(function () {
				ownerComponent.showResult(oModel);
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