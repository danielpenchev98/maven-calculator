sap.ui.define(
    ["sap/ui/thirdparty/sinon"],
    function (sinon) {
        "use strict";

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

                // and sends back a title string for the page
                this.server.respondWith("GET","/api/v1/calculate?equation=1%2B1",
                    [200,{"Content-Type": "application/json" },
                        '{"result":"2.0"}']);

                this.server.respondWith("GET","/api/v1/calculate?equation=",
                    [404,{"Content-Type": "application/json" },
                        '{"errorCode" : "404", "message" : "Empty equation"}']);

            }
        };

    }
);

