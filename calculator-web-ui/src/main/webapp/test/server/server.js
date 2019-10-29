sap.ui.define(
    ["sap/ui/thirdparty/sinon"],
    function (sinon) {
        "use strict";

        let respondToCalculationRequest = function (server,typeOfRequest,url,responseCode,headers,responseBody){
            return server.respondWith(typeOfRequest, url, function(request){
                let requestBody = request.requestBody;
                let id = getIdCorrespondingToTheRequest(requestBody.equation);
                request.respond(responseCode,headers,responseBody);
            });
        };

        let respondToGetCalculationResult = function (server,typeOfRequest,url,responseCode,headers,responseBody){
          return server.respondWith(typeOfRequest,url,[responseCode,headers,responseBody]);
        };

        let correctEquation = "1+1";
        let emptyEquation = "";
        let missingOpeningBracket = "1+)";
        let sequentialComponentsOfTheSameType = "1++2";
        let missingOperator = "2(-1)";
        let emptyBrackets = "2+()";
        let equationBeginningWithOperator ="+2+1";
        let divisionByZeroEvent = "1/0";
        let unsupportedComponent = "1#0";

        let getIdCorrespondingToTheRequest = function(equation){
           switch(String(equation)){
               case correctEquation : return 1;
               case emptyEquation : return 2;
               case missingOpeningBracket : return 3;
               case sequentialComponentsOfTheSameType : return 4;
               case missingOperator : return 5;
               case emptyBrackets : return 6;
               case equationBeginningWithOperator : return 7;
               case divisionByZeroEvent : return 8;
               case unsupportedComponent : return 9;
           }
        };

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

        let contentTypeHeader = {"Content-Type": "application/json"};
        let badRequestCode = 400;
        let okCode = 200;
        let acceptedCode = 202;

        //change the baseUrl in case the domain changes
        let baseUrl="https://calcwebservicei515142trial.hanatrial.ondemand.com/calculator-web-service";
        let resourceUrl="/api/v1/calculator";
        let sendCalculationRequestUrl = baseUrl + resourceUrl +"/calculate";
        let getCalculationResultUrl = function(id){ return baseUrl + resourceUrl + "/calculations/"+id;};

        let correctEquationResponseBody = createCalculationResult("2.0");
        let emptyEquationResponseBody = createCalculationError(badRequestCode,"Empty equation");
        let missingOpeningBracketResponseBody = createCalculationError(badRequestCode,"Missing or misplaced bracket");
        let sequentialComponentsOfTheSameTypeResponseBody = createCalculationError(badRequestCode,"Sequential components of the same type");
        let missingOperatorResponseBody = createCalculationError(badRequestCode,"Missing operator between a number and an opening bracket or a closing bracket and a number");
        let emptyBracketsResponseBody = createCalculationError(badRequestCode,"Empty brackets");
        let equationBeginningWithOperatorResponseBody = createCalculationError(badRequestCode,"Scope of equation ending or beginning with an operator");
        let divisionByZeroEventResponseBody = createCalculationError(badRequestCode,"Division by zero");
        let unsupportedComponentResponseBody = createCalculationError(badRequestCode,"Unsupported component :#");


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
                    return !url.match(baseUrl+resourceUrl+"/*");
                });

                respondToCalculationRequest(this.server,"POST",sendCalculationRequestUrl, acceptedCode , contentTypeHeader,
                    createRequestId());

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(1),okCode,contentTypeHeader,
                    correctEquationResponseBody);

                respondToGetCalculationResult(this.server, "GET",getCalculationResultUrl(2), badRequestCode, contentTypeHeader,
                    emptyEquationResponseBody);

                respondToGetCalculationResult(this.server, "GET",getCalculationResultUrl(3), badRequestCode, contentTypeHeader,
                    missingOpeningBracketResponseBody);

                respondToGetCalculationResult(this.server,"GET", getCalculationResultUrl(4), badRequestCode, contentTypeHeader,
                    sequentialComponentsOfTheSameTypeResponseBody);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(5), badRequestCode,contentTypeHeader,
                    missingOperatorResponseBody);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(6), badRequestCode,contentTypeHeader,
                    emptyBracketsResponseBody);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(7), badRequestCode,contentTypeHeader,
                    equationBeginningWithOperatorResponseBody);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(8), badRequestCode,contentTypeHeader,
                    divisionByZeroEventResponseBody);

                respondToGetCalculationResult(this.server,"GET",getCalculationResultUrl(9), badRequestCode,contentTypeHeader,
                    unsupportedComponentResponseBody);
            }
        };

    }
);

