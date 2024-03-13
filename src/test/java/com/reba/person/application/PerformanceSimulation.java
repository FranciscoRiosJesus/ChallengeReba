package com.reba.person.application;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class PerformanceSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://190.51.31.42:8080")
                    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .acceptLanguageHeader("en-US,en;q=0.5")
                    .acceptEncodingHeader("gzip, deflate")
                    .userAgentHeader(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0"
                    );

    ScenarioBuilder scn = scenario("Test URL scenario").exec(
            http("Home").get("/persons")
    );

    {
        setUp(
                scn.injectOpen(rampUsersPerSec(3).to(12).during(120))
        ).protocols(httpProtocol);
    }
}