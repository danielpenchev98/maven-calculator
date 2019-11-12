sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
], function (Controller, JSONModel) {
	"use strict";

	return Controller.extend("com.calculator.web.ui.controller.CalculatorPanel", {

		onInit: function(){
			let oData = { result : "0" };
			this.showResultInInputBox(oData);
		},

		onCalculation: function () {
			let equation = this.getView().byId("equation").getValue();
			let oServiceConfig = this.getRequiredUrls();

			this.sendCalculationRequest(oServiceConfig, equation);
		},

		getRequiredUrls : function() {
		    return this.getOwnerComponent().getManifestEntry("/sap.app/dataSources/calculationService");
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

            this.notifyOtherControllers("updateChannel","pendingCalculation", {id:oId.id,equation:equation});

			let processId = setInterval(() => {
				this.getCalculationResult(oServiceConfig, oId, processId)
			}, 1000);
		},

		getCalculationResult : function (oServiceConfig,oId,processId) {
			let getCalculationResultUrl = oServiceConfig.baseUrl + oServiceConfig.getCalculationResultUrl + "/" + oId.id;

			let xhr = new XMLHttpRequest();
			let onloadEventListener = function() {
				this.onCalculationResultCompleted(xhr, processId,oId);
			}.bind(this);
			this.doXMLHttpRequest(xhr,"GET",getCalculationResultUrl,onloadEventListener,null);

		},

		onCalculationResultCompleted : function(xhr,processId,oId) {
			if (this.isCompletedCalculation(xhr.status)) {
				let oResponse = JSON.parse(xhr.responseText);
				this.isSuccessfulCalculation(xhr.status) ? this.showCalculationResult(oResponse,oId) : this.showCalculationError(oResponse,oId);
				clearInterval(processId);
			}
		},

		isCompletedCalculation : function(statusCode){
			return statusCode !== 202;
		},

		isSuccessfulCalculation : function(statusCode) {
			return statusCode === 200;
		},

		showCalculationResult : function (oCalculationResult,oId) {
		    this.notifyOtherControllers("updateChannel","completedCalculation",{id:oId.id,result:oCalculationResult.result});
			this.showResultInInputBox(oCalculationResult);
		},

		showCalculationError : function (errorMessage,oId) {
			this.notifyOtherControllers("updateChannel","completedCalculation",{id:oId.id,result:errorMessage.message});
			let mError = new JSONModel();
			mError.setJSON(JSON.stringify(errorMessage));
			this.getOwnerComponent().openErrorDialog(mError);
		},

		notifyOtherControllers: function (channelName,idEvent,data) {
			sap.ui.getCore().getEventBus().publish(
				channelName,
				idEvent,
				data
			);
		},

		showResultInInputBox : function (oCalculationResult) {
			let oResultModel= new JSONModel(oCalculationResult);
			this.getView().setModel(oResultModel);
		}
	});
});