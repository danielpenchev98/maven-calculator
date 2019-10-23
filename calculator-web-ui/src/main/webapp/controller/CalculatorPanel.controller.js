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

			let ownerComponent = this.getOwnerComponent();
			let serviceConfig = ownerComponent.getManifestEntry("/sap.app/dataSources/calculationService");
			let postRequestUrl = serviceConfig.baseUrl + serviceConfig.serviceUrl;

			let postRequestBody = {
				equation : equation
			};

			let oId = new JSONModel();
			oId.loadData(postRequestUrl,postRequestUrl,true,)

			let getRequestUrl = postRequestUrl+"\";

			let oCalculationResult = new JSONModel();
			oCalculationResult.loadData(getRequestUrl).then(function () {
				ownerComponent.showResult(oCalculationResult);
			}).catch(function (error) {
				oCalculationResult.setJSON(error.responseText);
				ownerComponent.openErrorDialog(oCalculationResult);
			});
		}
	});
});