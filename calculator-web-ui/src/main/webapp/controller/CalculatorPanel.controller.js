sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel"
], function (Controller, JSONModel) {
	"use strict";

	return Controller.extend("com.calculator.web.ui.controller.CalculatorPanel", {

		onCalculation: function () {

			let equation = this.getView().byId("equation").getValue();

			let ownerComponent = this.getOwnerComponent();
			let oServiceConfig =  ownerComponent.getManifestEntry("/sap.app/dataSources/calculationService");

			this.sendCalculationRequest(oServiceConfig, equation);
		},

		sendCalculationRequest : async function (oServiceConfig,equation) {
			let postRequestUrl = oServiceConfig.baseUrl + oServiceConfig.sendCalculationRequestUrl;

			let postRequestBody = {
				equation : equation
			};

            let settings = {
                "async": true,
                "crossDomain": true,
                "url": postRequestUrl,
                "method": "POST",
                "headers": {
                    "Content-Type": "application/json",
                    "Accept": "*/*",
                    "Cache-Control": "no-cache",
                    "cache-control": "no-cache"
                },
				"data": JSON.stringify(postRequestBody)
			};

			$.ajax(settings).done(function(data){
				console.log("id object ->"+data);
				const processId=setInterval(()=> {
					this.getCalculationResult(oServiceConfig, data).then(completed => {
						console.log("check if finished ->"+completed);
						if(completed) {
							console.log("stop the interval");
							clearInterval(processId);
						}
					});
				},4000);
			}.bind(this))
				.fail(function(jqXhr){
					     this.showCalculationError(jqXhr.responseText);
				      }.bind(this)
				);
		},

		getCalculationResult : async function (oServiceConfig,oId) {
			let getRequestUrl = oServiceConfig.baseUrl + oServiceConfig.getCalculationResultUrl + "/" + oId.id;

			let settings = {
				"async": true,
				"crossDomain": true,
				"url": getRequestUrl,
				"method": "GET",
				"headers": {
					"Content-Type": "application/json",
					"cache-control": "no-cache",
					"Accept": "*/*"
				}
			};

			let hasFinished = false;

			//try {
			await $.ajax(settings).done(function (data, textStatus, jQxhr) {
				if (jQxhr.status === 200) {
					console.log("result");
					this.showCalculationResult(data);
					hasFinished = true;
					console.log("result showed");
				}
			}.bind(this)).fail(function (jqXHR) {
				console.log("error " + jqXHR.responseJSON);
				//this.showCalculationError(jqXHR.responseJSON);
				console.log("error showed");
				hasFinished = true;
			}.bind(this));
		//}
		//	catch(exception){
		//		console.log(exception);
		//	}
			console.log("returning the flag");
			return hasFinished;
		},

		showCalculationResult : function (CalculationResult) {
			let mCalculationResult = new JSONModel();
			mCalculationResult.setJSON(JSON.stringify(CalculationResult));
			this.getOwnerComponent().showResult(mCalculationResult);
		},

		showCalculationError : function (errorMessage) {
			let mError = new JSONModel();
			mError.setJSON(errorMessage);
			this.getOwnerComponent().openErrorDialog(mError);
		}
	});
});