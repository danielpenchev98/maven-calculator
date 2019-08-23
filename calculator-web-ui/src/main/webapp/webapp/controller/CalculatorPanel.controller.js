sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
	"../model/UrlFormatter"
], function (Controller, JSONModel,UrlFormatter) {
	"use strict";

	return Controller.extend("com.calculator.web.ui.controller.CalculatorPanel", {

		onOpenDialog : function () {

			let equation=this.getView().byId("equation").getValue();
			let url="http://localhost:8080/api/v1/calculate?equation="+UrlFormatter.encodeExpresion(equation);

			let oModel=new JSONModel();
			let ownerComponent=this.getOwnerComponent();

			oModel.loadData(url).then(function(){
				ownerComponent.openResultDialog(oModel);
			}).catch(function(error){
				oModel.setJSON(error.responseText);
				ownerComponent.openErrorDialog(oModel);
			});
		}
	});

});