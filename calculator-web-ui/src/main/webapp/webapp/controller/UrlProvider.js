sap.ui.define([
    "../model/UrlFormatter"
], function (UrlFormatter) {
    "use strict";

    return {
        getUrl : function(equation){
            return "http://localhost:7777/api/v1/calculate?equation=" + UrlFormatter.encodeExpresion(equation);
        }
    }
});