sap.ui.define([
    "sap/ui/core/mvc/Controller",
], function (Controller) {
    "use strict";

    return Controller.extend("com.calculator.web.ui.controller.CalculationsTable", {


        onInit: function () {

            let ownerComponent = this.getOwnerComponent();
            let oServiceConfig = ownerComponent.getManifestEntry("/sap.app/dataSources/calculationService");

            let getHistoryUrl = oServiceConfig.baseUrl + oServiceConfig.getCalculationHistoryUrl;

            setInterval(() => {
                console.log(sessionStorage.getItem("calculationHistory"));
                let xhr = new XMLHttpRequest();
                let onloadEventListener = function () {
                    this.onCalculationHistoryReceived(xhr);
                }.bind(this);
                this.doXMLHttpRequest(xhr, "GET", getHistoryUrl, onloadEventListener, null);
            }, 3000);
        },

        onCalculationHistoryReceived: function (xhr) {
            let historyTable = this.getView().byId("calculationsTable");
            let oModel = new sap.ui.model.json.JSONModel();

            let history = JSON.parse(xhr.responseText);

            history.forEach((calculation) => {
                if(this.isPendingCalculation(calculation)) {
                    calculation.errorMsg="Pending calculation";
                }

                if (this.hasCalculationError(calculation)){
                    calculation.calculationResult=null;
                }
            });

            oModel.setData(history);
            historyTable.setModel(oModel);
        },

        /* onInit: function () {

            let ownerComponent = this.getOwnerComponent();
            let oServiceConfig = ownerComponent.getManifestEntry("/sap.app/dataSources/calculationService");

            let getResultUrl = oServiceConfig.baseUrl + oServiceConfig.getCalculationResultUrl;

            let historyTable = this.getView().byId("calculationsTable");

            setInterval(() => {
                let calculationHistory = sessionStorage.getItem("calculationHistory");
                calculationHistory.forEach(function(calculationRecord){
                    let xhr = new XMLHttpRequest();
                    let onloadEventListener = function () {
                        this.onCalculationHistoryReceived(xhr);
                    }.bind(this);
                    this.doXMLHttpRequest(xhr, "GET",getResultUrl+"/"+calculationRecord.id, onloadEventListener, null);
                });
                let oModel = new sap.ui.model.json.JSONModel();
                oModel.set(this.calculationHistory);
                historyTable.setModel(oModel);
            }, 3000);
        },

        onCalculationHistoryReceived: function (xhr) {

            if(xhr.status==202){

            }
            let calculationRecord = JSON.parse(xhr.responseText);

            if(calculationRecord.)
                if(this.isPendingCalculation(calculation)) {
                    calculation.errorMsg="Pending calculation";
                }

                if (this.hasCalculationError(calculation)){
                    calculation.calculationResult=null;
                }

        },*/

        isPendingCalculation:function(calculation){
          return  calculation.statusCode === 0
        },


        hasCalculationError : function(calculation){
            return calculation.errorMsg!==null;
        },

        doXMLHttpRequest:function(xhr,method,url,onloadEventListener,data){
            xhr.open(method,url);
            xhr.setRequestHeader("Content-Type","application/json");
            xhr.setRequestHeader("cache-control","no-cache");
            xhr.addEventListener("load", onloadEventListener);
            xhr.send(data);
        },
    });

});