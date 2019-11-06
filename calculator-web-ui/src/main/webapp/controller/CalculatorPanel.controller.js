sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
], function (Controller, JSONModel) {
	"use strict";

	return Controller.extend("com.calculator.web.ui.controller.CalculatorPanel", {

		onCalculation: function () {

			let equation = this.getView().byId("equation").getValue();

			let ownerComponent = this.getOwnerComponent();
			let oServiceConfig =  ownerComponent.getManifestEntry("/sap.app/dataSources/calculationService");

			this.sendCalculationRequest(oServiceConfig, equation);
		},


		doXMLHttpRequest:function(xhr,method,url,onloadEventListener,data){
			xhr.open(method,url);
			xhr.setRequestHeader("Content-Type","application/json");
			xhr.setRequestHeader("cache-control","no-cache");
			xhr.addEventListener("load", onloadEventListener);
			xhr.send(data);
		},

		sendCalculationRequest : function (oServiceConfig,equation) {
			let postRequestUrl = oServiceConfig.baseUrl + oServiceConfig.sendCalculationRequestUrl;

			let postRequestBody = {
				equation : equation
			};

			let xhr = new XMLHttpRequest();
			let onloadEventListener = function(){
				this.onCalculationRequestQueued(xhr,oServiceConfig,equation);
			}.bind(this);
			this.doXMLHttpRequest(xhr,"POST",postRequestUrl,onloadEventListener,JSON.stringify(postRequestBody));
		},

		onCalculationRequestQueued : function(xhr,oServiceConfig,equation){
			let oId = JSON.parse(xhr.responseText);

			let calculationHistoryJSONString = sessionStorage.getItem("calculationHistory");
			let calculationHistory = null;
			if(calculationHistoryJSONString === null){
				calculationHistory = [{ id:oId.id, equation : equation}]
			} else{
			    calculationHistory = JSON.parse(calculationHistoryJSONString);
				calculationHistory.push({ id:oId.id, equation : equation})
			}
			sessionStorage.setItem("calculationHistory",JSON.stringify(calculationHistory));

			let processId = setInterval(() => {
				this.getCalculationResult(oServiceConfig, oId, processId)
			}, 1000);
		},


		getCalculationResult : function (oServiceConfig,oId,processId) {
			let getCalculationResultUrl = oServiceConfig.baseUrl + oServiceConfig.getCalculationResultUrl + "/" + oId.id;

			let xhr = new XMLHttpRequest();
			let onloadEventListener = function() {
				this.onCalculationResultCompleted(xhr, processId);
			}.bind(this);
			this.doXMLHttpRequest(xhr,"GET",getCalculationResultUrl,onloadEventListener,null);

		},

		onCalculationResultCompleted : function(xhr,processId) {
		   if(this.isCompletedCalculation(xhr.status)){
		   	    let oResponse = JSON.parse(xhr.responseText);
		   	    this.isSuccessfulCalculation(xhr.status) ? this.showCalculationResult(oResponse) : this.showCalculationError(oResponse);
			    clearInterval(processId);
		   }
		},

		isCompletedCalculation : function(statusCode){
			return statusCode !== 202;
		},

		isSuccessfulCalculation : function(statusCode) {
			return statusCode === 200;
		},

		showCalculationResult : function (CalculationResult) {
			let mCalculationResult = new JSONModel();
			mCalculationResult.setJSON(JSON.stringify(CalculationResult));
			this.getOwnerComponent().showResult(mCalculationResult);
		},

		showCalculationError : function (errorMessage) {
			let mError = new JSONModel();
			mError.setJSON(JSON.stringify(errorMessage));
			this.getOwnerComponent().openErrorDialog(mError);
		}
	});
});