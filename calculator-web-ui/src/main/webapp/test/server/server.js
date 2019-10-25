sap.ui.define(
    ["sap/ui/thirdparty/sinon"],
    function (sinon) {
        "use strict";

        let serverRespondWith = function (server,typeOfRequest,url,responseCode,headers,responseBody){
            return server.respondWith(typeOfRequest, url, [ responseCode, headers, responseBody ] );
        };

        let contentTypeHeader = {"Content-Type": "application/json"};
        let badRequestCode = 400;
        let okCode = 200;
        let acceptedCode = 202;

        //change the baseUrl in case the domain changes
        let baseUrl="https://calcwebservicei515142trial.hanatrial.ondemand.com/calculator-web-service";
        let resourceUrl="/api/v1/calculator";
        let sendCalculationRequestUrl = baseUrl + serviceUrl +"/calculate";
        let getCalculationResult = baseUrl + serviceUrl + "/calculations";

        let correctEquation = "1+1";
        let emptyEquation = "";
        let missingOpeningBracket = "1+)";
        let sequentialComponentsOfTheSameType = "1++2";
        let missingOperator = "2(-1)";
        let emptyBrackets = "2+()";
        let equationBeginningWithOperator ="+2+1";
        let divisionOnZeroEvent = "1/0";
        let unsupportedComponent = "1#0";

        let acceptedRequestResponseBody = '{"id":"1"}';

        let correctEquationResponseBody = '{"result":"2.0"}';
        let emptyEquationResponseBody = '{"errorCode" : \"'+badRequestCode+'\", "message" : "Empty equation"}';
        let missingOpeningBracketResponseBody = '{"errorCode" : \"'+badRequestCode+'\", "message" : "Missing or misplaced bracket"}';
        let sequentialComponentsOfTheSameTypeResponseBody = '{"errorCode" : \"'+badRequestCode+'\", "message" : "Sequential components of the same type"}';
        let missingOperatorResponseBody = '{"errorCode" : \"'+badRequestCode+'\", "message" : "Missing operator between a number and an opening bracket or a closing bracket and a number"}';
        let emptyBracketsResponseBody = '{"errorCode" : \"'+badRequestCode+'\", "message" : "Empty brackets"}';
        let equationBeginningWithOperatorResponseBody = '{"errorCode" : \"'+badRequestCode+'\", "message" : "Scope of equation ending or beginning with an operator"}';
        let divisionByZeroEventResponseBody = '{"errorCode" : \"'+badRequestCode+'\", "message" : "Division by zero"}';
        let unsupportedComponentResponseBody = '{"errorCode" : \"'+badRequestCode+'\", "message" : "Unsupported component :#"}';


        return {
            /**
             * Initializes a fake server for testing purposes.
             * @public
             */
            init : function () {

                this.server = sinon.fakeServer.create();
                this.server.autoRespond = true;
                this.server.autoRespondAfter = 100;

                sinon.fakeServer.xhr.useFilters = true;
                this.server.xhr.addFilter(function(method, url) {
                    return !url.match(baseUrl+serviceUrl);
                });

                serverRespondWith(this.server,"POST",sendCalculationRequestUrl, acceptedCode , contentTypeHeader,
                    acceptedRequestResponseBody);

                serverRespondWith(this.server,"GET",getCalculationResult,okCode)

                serverRespondWith(this.server, typeOfRequest,urlWithEmptyEquation, badRequestCode, contentTypeHeader,
                    emptyEquationResponseBody);

                serverRespondWith(this.server, typeOfRequest,urlWithMissingOpeningBracket, badRequestCode, contentTypeHeader,
                    missingOpeningBracketResponseBody);

                serverRespondWith(this.server,typeOfRequest, urlWithSequentialComponentsOfTheSameType, badRequestCode, contentTypeHeader,
                    sequentialComponentsOfTheSameTypeResponseBody);

                serverRespondWith(this.server,typeOfRequest,urlWithMissingOperator, badRequestCode,contentTypeHeader,
                    missingOperatorResponseBody);

                serverRespondWith(this.server,typeOfRequest,urlWithEmptyBrackets, badRequestCode,contentTypeHeader,
                    emptyBracketsResponseBody);

                serverRespondWith(this.server,typeOfRequest,urlWithEquationBeginningWithOperator, badRequestCode,contentTypeHeader,
                    equationBeginningWithOperatorResponseBody);

                serverRespondWith(this.server,typeOfRequest,urlWithDivisionOnZeroEvent, badRequestCode,contentTypeHeader,
                    divisionByZeroEventResponseBody);

                serverRespondWith(this.server,typeOfRequest,urlWithUnsupportedComponent, badRequestCode,contentTypeHeader,
                    unsupportedComponentResponseBody);
            }
        };

    }
);

