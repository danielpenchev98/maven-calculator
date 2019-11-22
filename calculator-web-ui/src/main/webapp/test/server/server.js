sap.ui.define(
    ["sap/ui/thirdparty/sinon"],
    function (sinon) {
        "use strict";

        //change the baseUrl in case the domain changes
        let baseUrl="https://calcwebservicei515142trial.hanatrial.ondemand.com/calculator-web-service";
        let resourceUrl="/api/v1/calculator";
        let sendCalculationRequestUrl = baseUrl + resourceUrl +"/calculate";
        let getCalculationResultUrl = function(id){
            return baseUrl + resourceUrl + "/calculations/"+id;
        };

        let correctEquation = "1+1";
        let emptyEquation = "";
        let missingOpeningBracket = "1+)";
        let sequentialComponentsOfTheSameType = "1++2";
        let missingOperator = "2(-1)";
        let emptyBrackets = "2+()";
        let expressionBeginningWithOperator ="+2+1";
        let divisionByZeroEvent = "1/0";
        let unsupportedComponent = "1#0";

        let responseHeaders = [{"Content-Type": "application/json"}];
        let badRequestCode = 400;
        let okCode = 200;
        let acceptedCode = 202;

        let createCalculationResult = function(result){
            return {
                "result" : result
            };
        };

        let createCalculationError = function(errorCode,message){
            return {
                "errorCode" : errorCode,
                "message" : message
            };
        };

        let createRequestId = function(id){
            return {
                "id" : id
            };
        };

        let correctEquationResponseBody = createCalculationResult("2.0");
        let emptyEquationResponseBody = createCalculationError(badRequestCode,"Empty expression");
        let missingOpeningBracketResponseBody = createCalculationError(badRequestCode,"Missing or misplaced bracket");
        let sequentialComponentsOfTheSameTypeResponseBody = createCalculationError(badRequestCode,"Sequential components of the same type");
        let missingOperatorResponseBody = createCalculationError(badRequestCode,"Missing operator between a number and an opening bracket or a closing bracket and a number");
        let emptyBracketsResponseBody = createCalculationError(badRequestCode,"Empty brackets");
        let expressionBeginningWithOperatorResponseBody = createCalculationError(badRequestCode,"Scope of expression ending or beginning with an operator");
        let divisionByZeroEventResponseBody = createCalculationError(badRequestCode,"Division by zero");
        let unsupportedComponentResponseBody = createCalculationError(badRequestCode,"Unsupported component :#");

        let getIdCorrespondingToTheRequest = function(expression){
           switch(String(expression)){
               case correctEquation : return 1;
               case emptyEquation : return 2;
               case missingOpeningBracket : return 3;
               case sequentialComponentsOfTheSameType : return 4;
               case missingOperator : return 5;
               case emptyBrackets : return 6;
               case expressionBeginningWithOperator : return 7;
               case divisionByZeroEvent : return 8;
               case unsupportedComponent : return 9;
           }
        };

        let respondToCalculationRequest = function (server,typeOfRequest,url,responseCode,headers){
            return server.respondWith(typeOfRequest, url, function(request){
                let oCalculationRequest = JSON.parse(request.requestBody);
                let id = getIdCorrespondingToTheRequest(oCalculationRequest.expression);
                let oRequestId = createRequestId(id);
                request.respond(responseCode,headers,JSON.stringify(oRequestId));
            });
        };

        let respondToGetCalculationResult = function (server,typeOfRequest,url,responseCode,headers,responseBody){
            return server.respondWith(typeOfRequest,url,[responseCode,headers,JSON.stringify(responseBody)]);
        };

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
                    return !url.match(baseUrl+resourceUrl);
                });

                respondToCalculationRequest(this.server,"POST",sendCalculationRequestUrl, acceptedCode , responseHeaders);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(1),okCode,responseHeaders,
                    correctEquationResponseBody);

                respondToGetCalculationResult(this.server, "GET",getCalculationResultUrl(2), badRequestCode, responseHeaders,
                    emptyEquationResponseBody);

                respondToGetCalculationResult(this.server, "GET",getCalculationResultUrl(3), badRequestCode, responseHeaders,
                    missingOpeningBracketResponseBody);

                respondToGetCalculationResult(this.server,"GET", getCalculationResultUrl(4), badRequestCode, responseHeaders,
                    sequentialComponentsOfTheSameTypeResponseBody);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(5), badRequestCode,responseHeaders,
                    missingOperatorResponseBody);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(6), badRequestCode,responseHeaders,
                    emptyBracketsResponseBody);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(7), badRequestCode,responseHeaders,
                    expressionBeginningWithOperatorResponseBody);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(8), badRequestCode,responseHeaders,
                    divisionByZeroEventResponseBody);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(9), badRequestCode,responseHeaders,
                    unsupportedComponentResponseBody);
            }
        };
    }
);

