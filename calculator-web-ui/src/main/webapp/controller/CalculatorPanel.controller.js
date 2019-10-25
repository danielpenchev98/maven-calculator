sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
	"../model/UrlFormatter"
], function (Controller, JSONModel) {
	"use strict";

	return Controller.extend("com.calculator.web.ui.controller.CalculatorPanel", {

		onCalculation: function () {

			let equation = this.getView().byId("equation").getValue();

			let ownerComponent = this.getOwnerComponent();
			let oServiceConfig = ownerComponent.getManifestEntry("/sap.app/dataSources/calculationService");

			let oCalculationResult;
			try {
				let oId = this.sendCalculationRequest(oServiceConfig, equation);
				oCalculationResult = this.getCalculationResult(oId);
				this.showCalculationResult(oCalculationResult);
			}
			catch(error) {
			    oCalculationResult.setJSON(error.responseText);
			    ownerComponent.openErrorDialog(oCalculationResult);
			}
		},

		sendCalculationRequest : function (oServiceConfig,equation) {
			let postRequestUrl = oServiceConfig.baseUrl + oServiceConfig.serviceUrl;

			let postRequestBody = {
				equation : equation
			};

			let oId = new JSONModel();
			oId.loadData(postRequestUrl,postRequestBody,true,"POST").then(function(){
				return oId;
			}).catch(function (error){
			    throw error;
			});
		},

		getCalculationResult : function (oId) {
			let getRequestUrl = postRequestUrl+"\\"+oId.id;

			let oCalculationResult = new JSONModel();
			oCalculationResult.loadData(getRequestUrl).then(function () {
				return oCalculationResult;
			}).catch(function (error) {
				throw error;
			});
		},

		showCalculationResult : function (oCalculationResult) {
			ownerComponent.showResult(oCalculationResult);
		}
	});
});