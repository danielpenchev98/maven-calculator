sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
], function (Controller, JSONModel) {
	"use strict";

	return Controller.extend("com.calculator.web.ui.controller.CalculatorPanel", {

		onOpenDialog : function () {

			let url="http://localhost:8080/api/v1/calculate?equation=2%2a2";
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