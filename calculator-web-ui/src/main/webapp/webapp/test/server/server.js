sap.ui.define(
    ["sap/ui/thirdparty/sinon"],
    function (sinon) {
        "use strict";

        function serverRespondWith(server,typeOfRequest,url,requestConfiguration){
            return server.respondWith(typeOfRequest,url,requestConfiguration);
        }

        return {
            /**
             * Initializes a fake server for testing purposes.
             * @public
             */

            init : function () {
                // create a Sinon.JS fake server that responds automatically after 1s
                this.server = sinon.fakeServer.create();
                this.server.autoRespond = true;
                this.server.autoRespondAfter = 100;

                // that responds only to a specific request
                sinon.fakeServer.xhr.useFilters = true;
                this.server.xhr.addFilter(function(method, url) {
                    // whenever this returns true the request will not faked
                    return !url.match("/api/v1/calculate");
                });

                serverRespondWith(this.server,"GET","/api/v1/calculate?equation=1%2B1",
                    [200,{"Content-Type": "application/json" },
                        '{"result":"2.0"}']);

                serverRespondWith(this.server, "GET","/api/v1/calculate?equation=",
                    [404,{"Content-Type": "application/json" },
                        '{"errorCode" : "404", "message" : "Empty equation"}']);

                serverRespondWith(this.server, "GET","/api/v1/calculate?equation=1%2B)",
                    [404,{"Content-Type": "application/json" },
                        '{"errorCode" : "404", "message" : "Missing or misplaced bracket"}']);

                serverRespondWith(this.server,"GET","/api/v1/calculate?equation=1%2B%2B2",
                    [404,{"Content-Type": "application/json" },
                        '{"errorCode" : "404", "message" : "Sequential components of the same type"}']);

                serverRespondWith(this.server,"GET","/api/v1/calculate?equation=2(-1)",
                    [404,{"Content-Type": "application/json" },
                        '{"errorCode" : "404", "message" : "Missing operator between a number and an opening bracket or a closing bracket and a number"}']);

                serverRespondWith(this.server,"GET","/api/v1/calculate?equation=2%2B()",
                    [404,{"Content-Type": "application/json" },
                        '{"errorCode" : "404", "message" : "Empty brackets"}']);

                serverRespondWith(this.server,"GET","/api/v1/calculate?equation=%2B2%2B1",
                    [404,{"Content-Type": "application/json" },
                        '{"errorCode" : "404", "message" : "Scope of equation ending or beginning with an operator"}']);

                serverRespondWith(this.server,"GET","/api/v1/calculate?equation=1%2F0",
                    [404,{"Content-Type": "application/json" },
                        '{"errorCode" : "404", "message" : "Division by zero"}']);

                serverRespondWith(this.server,"GET","/api/v1/calculate?equation=1%230",
                    [404,{"Content-Type": "application/json" },
                        '{"errorCode" : "404", "message" : "Unsupported component :#"}']);
            }
        };

    }
);

