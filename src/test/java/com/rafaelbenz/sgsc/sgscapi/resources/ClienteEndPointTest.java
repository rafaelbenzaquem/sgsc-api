package com.rafaelbenz.sgsc.sgscapi.resources;

import com.rafaelbenz.sgsc.sgscapi.SgscApiApplicationTests;
import com.rafaelbenz.sgsc.sgscapi.dto.ClienteDTO;
import com.rafaelbenz.sgsc.sgscapi.dto.ClienteNewDTO;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class ClienteEndPointTest extends SgscApiApplicationTests {


    @Test
    public void criarCliente() {

        ClienteNewDTO cliente = new ClienteNewDTO();
        cliente.setNome("João da Silva");
        cliente.setEmail("joao@gmail.com");
        cliente.setCpfOuCnpj("77643070253");
        cliente.setTipo(1);
        cliente.setTelefone1("9335228523");
        cliente.setTelefone2("93981166350");
        cliente.setLogradouro("Rua das Acácias");
        cliente.setNumero("2284");
        cliente.setComplemento("Apto 302");
        cliente.setBairro("Aldeia");
        cliente.setCep("68040010");
        cliente.setCidade("Santarém");


        given().
                contentType("application/json").
                body(cliente).
                when().
                post("/clientes").
                then().
                statusCode(201)
                .header("Location", "http://localhost:" + port + "/clientes/2");
    }

    @Test
    public void buscarUmClienteComIdInvalido() {
        int id = 3;
        given()
                //.param("id", "1") requisição sem parâmetros
                .when()
                .get("/clientes/" + id)
                .then()
                .log().body().and()
                .statusCode(404) // O status http retornado foi 404\
                .contentType(ContentType.JSON) // O response foi retornado no formato JSON
                .body("status", equalTo(404)); //A chave "nome" contém o valor "maria silva"
    }


    @Test
    public void buscarUmClientePorID() {
        int id = 1;
        given()
                //.param("id", "1") requisição sem parâmetros
                .when()
                .get("/clientes/" + id)
                .then()
                .log().body().and()
                .statusCode(200) // O status http retornado foi 200
                .contentType(ContentType.JSON) // O response foi retornado no formato JSON
                .body("id", equalTo(1))
                .body("nome", containsString("maria silva")); //A chave "nome" contém o valor "maria silva"
    }

    @Test
    public void listarTodosOsClientes() {
        criarCliente();
        List<ClienteDTO> clientes = new ArrayList<>();
        clientes.add(new ClienteDTO(1, "maria silva", "maria@gmail.com"));
        clientes.add(new ClienteDTO(2, "João da Silva", "joao@gmail.com"));


        List<LinkedHashMap> expected = given().get("/clientes").as(List.class);
        assertArrayEquals(listaClienteDTOParaLinkedHashMap(clientes),expected.toArray());

    }

    private LinkedHashMap[] listaClienteDTOParaLinkedHashMap(List<ClienteDTO> clientes) {
        LinkedHashMap[] maps = new LinkedHashMap[clientes.size()];
        int count = 0;
        for (ClienteDTO cliente : clientes) {
            LinkedHashMap atributosCliente = new LinkedHashMap();
            atributosCliente.put("id", cliente.getId());
            atributosCliente.put("nome", cliente.getNome());
            atributosCliente.put("email", cliente.getEmail());
            maps[count] = atributosCliente;
            count++;
        }
        return maps;
    }
}
