sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/model/json/JSONModel",
	"./UrlProvider"
], function (Controller, JSONModel,UrlProvider) {
	"use strict";

	return Controller.extend("com.calculator.web.ui.controller.CalculatorPanel", {

		onOpenDialog: function () {

			let equation = this.getView().byId("equation").getValue();
			let url=UrlProvider.getUrl(equation);
			
			let oModel = new JSONModel();
			let ownerComponent = this.getOwnerComponent();

			oModel.loadData(url).then(function () {
				ownerComponent.openResultDialog(oModel);
			}).catch(function (error) {

				//if (error.statusCode === 0) {
				//	oModel.setJSON("{\"message\": \"Service unavailable\"}");
				//} else {
				oModel.setJSON(error.responseText);
				//}
				ownerComponent.openErrorDialog(oModel);
			});
		}
	});

});