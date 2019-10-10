package com.rafaelbenz.sgsc.sgscapi;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SgscApiApplicationTests {

    @Value("${local.server.port}")
    protected int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

}

