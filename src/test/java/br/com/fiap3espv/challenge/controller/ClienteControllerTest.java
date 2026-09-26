package br.com.fiap3espv.challenge.controller;

import br.com.fiap3espv.challenge.dto.cliente.ClienteCadastroDTO;
import br.com.fiap3espv.challenge.dto.cliente.ClienteCadastroResponseDTO;
import br.com.fiap3espv.challenge.dto.cliente.ClienteDetalhesDTO;
import br.com.fiap3espv.challenge.errors.GlobalExceptionHandler;
import br.com.fiap3espv.challenge.exceptions.RecursoNaoEncontradoException;
import br.com.fiap3espv.challenge.model.Cliente;
import br.com.fiap3espv.challenge.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @InjectMocks
    private ClienteController controller;

    @Mock
    private ClienteService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void deveriaRetornarStatusCode201ParaSalvarCliente() throws Exception {
        String json = """
                {
                  "nome": "João da Silva",
                  "cpf": "12345678900",
                  "telefone": "11999998888",
                  "email": "joao.silva@email.com",
                  "cidade": "São Paulo",
                  "estado": "SP"
                }
                """;

        ClienteCadastroDTO clienteCadastroDTO = new ClienteCadastroDTO(
                "João da Silva", "12345678900", "11999998888",
                "joao.silva@email.com", "São Paulo", "SP"
        );
        Cliente cliente = new Cliente(clienteCadastroDTO);
        ReflectionTestUtils.setField(cliente, "id", 1L);

        ClienteCadastroResponseDTO responseMock = new ClienteCadastroResponseDTO(cliente);

        Mockito.when(service.cadastrarCliente(Mockito.any(ClienteCadastroDTO.class)))
                .thenReturn(responseMock);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(service).cadastrarCliente(Mockito.any(ClienteCadastroDTO.class));
    }

    @Test
    void deveriaRetornarStatusCode400ParaCpfInvalido() throws Exception {
        String json = """
            {
              "nome": "João da Silva",
              "cpf": "123",
              "telefone": "11999998888",
              "email": "joao.silva@email.com",
              "cidade": "São Paulo",
              "estado": "SP"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verifyNoInteractions(service);
    }

    @Test
    void deveriaRetornarStatusCode404QuandoClienteNaoExiste() throws Exception {
        Mockito.when(service.exibirDetalhesCliente(99L))
                .thenThrow(new RecursoNaoEncontradoException("Cliente não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deveriaRetornarStatusCode200ParaListarClientesAtivos() throws Exception {
        Mockito.when(service.listarClientesAtivos(Mockito.any()))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deveriaRetornarStatusCode200ParaListarClientesDesativados() throws Exception {
        Mockito.when(service.listarClientesNaoAtivos(Mockito.any()))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/desativados"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deveriaRetornarStatusCode200ParaExibirDetalhesCliente() throws Exception {
        ClienteCadastroDTO dto = new ClienteCadastroDTO(
                "João da Silva", "12345678900", "11999998888",
                "joao.silva@email.com", "São Paulo", "SP"
        );
        Cliente cliente = new Cliente(dto);
        ReflectionTestUtils.setField(cliente, "id", 1L);

        Mockito.when(service.exibirDetalhesCliente(1L))
                .thenReturn(new ClienteDetalhesDTO(cliente)); // ajuste se o construtor for diferente

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deveriaRetornarStatusCode404ParaExibirDetalhesClienteInexistente() throws Exception {
        Mockito.when(service.exibirDetalhesCliente(99L))
                .thenThrow(new RecursoNaoEncontradoException("Cliente não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deveriaRetornarStatusCode200ParaAtualizarCliente() throws Exception {
        String json = """
            {
              "nome": "João da Silva Atualizado",
              "cpf": "12345678900",
              "telefone": "11999998888",
              "email": "joao.silva@email.com",
              "cidade": "São Paulo",
              "estado": "SP"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service).atualizarDadosCliente(Mockito.any(), Mockito.eq(1L));
    }

    @Test
    void deveriaRetornarStatusCode204ParaAtivarCliente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/clientes/ativacao/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(service).ativarCliente(1L);
    }

    @Test
    void deveriaRetornarStatusCode204ParaRemoverCliente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/clientes/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(service).removerCliente(1L);
    }
}