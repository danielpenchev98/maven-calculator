sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel"
], function (Controller,JSONModel) {
    "use strict";

    return Controller.extend("com.calculator.web.ui.controller.CalculationsTable", {

        calculationHistoryKeyInSessionStorage : "calculationHistory",

        calculationHistory : {},

        onInit: function () {
            let eventBus = sap.ui.getCore().getEventBus();
            eventBus.subscribe("updateChannel", "completedCalculation", this.onCompletedCalculation, this);
            eventBus.subscribe("updateChannel", "pendingCalculation", this.onNewRecord, this);
        },

        onNewRecord : function(ChannelId, EventId, data){
            this.updateAppData(this.createNewPendingCalculationInHistory.bind(this),data);
        },

        onCompletedCalculation : function(ChannelId, EventId, data){
            this.updateAppData(this.updateCalculationResult.bind(this),data);
        },

        updateAppData : function(action,data){
            this.updateCalculationHistoryFromSessionStorage();
            action(data);
            this.updateTable();
            this.updateSessionStorage();
        },

        updateTable : function(){
            let oModel = new JSONModel();
            oModel.setData(this.calculationHistory);
            this.getView().setModel(oModel);
        },

        createNewPendingCalculationInHistory : function(data){
            this.calculationHistory[data.id]= { equation : data.equation, result : "Pending calculation"};
        },

        updateCalculationResult : function(data){
            this.calculationHistory[data.id].result=data.result;
        },

        updateCalculationHistoryFromSessionStorage:function() {
            let calculationHistoryJSONString = sessionStorage.getItem(this.calculationHistoryKeyInSessionStorage);
            this.calculationHistory = calculationHistoryJSONString !== null ? JSON.parse(calculationHistoryJSONString) : {};
        },

        updateSessionStorage:function(){
            sessionStorage.setItem(this.calculationHistoryKeyInSessionStorage,JSON.stringify(this.calculationHistory));
        }
    });

});