package br.com.fiap3espv.challenge.controller;

import br.com.fiap3espv.challenge.dto.cliente.ClienteCadastroDTO;
import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoCadastroDTO;
import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoCadastroResponseDTO;
import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoDetalhamentoDTO;
import br.com.fiap3espv.challenge.dto.veiculo.VeiculoDTO;
import br.com.fiap3espv.challenge.errors.GlobalExceptionHandler;
import br.com.fiap3espv.challenge.exceptions.RecursoNaoEncontradoException;
import br.com.fiap3espv.challenge.model.Cliente;
import br.com.fiap3espv.challenge.model.OrdemServico;
import br.com.fiap3espv.challenge.service.OrdemServicoService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrdemServicoControllerTest {

    @InjectMocks
    private OrdemServicoController controller;

    @Mock
    private OrdemServicoService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void deveriaRetornarStatusCode201ParaCadastrarOrdemServico() throws Exception {
        String json = """
                {
                  "clienteId": 1,
                  "concessionariaId": "001",
                  "dataServico": "2026-09-20",
                  "tipoServico": "Revisão",
                  "veiculo": {
                    "veiculoModelo": "Onix",
                    "veiculoAno": 2022,
                    "veiculoKm": 35000
                  },
                  "categoriaServico": null,
                  "tipoFalha": null,
                  "descricaoProblema": "Barulho no motor",
                  "valorTotal": 450.0
                }
                """;

        Cliente cliente = criarClienteMock();

        OrdemServicoCadastroDTO dto = new OrdemServicoCadastroDTO(
                1L, "001", LocalDate.of(2026, 9, 20), "Revisão",
                new VeiculoDTO("Onix", 2022, 35000),
                null, null, "Barulho no motor", 450.0
        );
        OrdemServico ordemServico = new OrdemServico(dto);
        ReflectionTestUtils.setField(ordemServico, "id", 1L);
        ordemServico.setCliente(cliente);

        OrdemServicoCadastroResponseDTO responseMock = new OrdemServicoCadastroResponseDTO(ordemServico);

        Mockito.when(service.cadastrarOrdemDeServico(Mockito.any(OrdemServicoCadastroDTO.class)))
                .thenReturn(responseMock);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ordens-servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(service).cadastrarOrdemDeServico(Mockito.any(OrdemServicoCadastroDTO.class));
    }

    // --- validação (não chega no service) ---
    @Test
    void deveriaRetornarStatusCode400QuandoVeiculoNaoInformado() throws Exception {
        String json = """
                {
                  "clienteId": 1,
                  "concessionariaId": "001",
                  "dataServico": "2026-09-20",
                  "tipoServico": "Revisão"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ordens-servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verifyNoInteractions(service);
    }

    // --- listagem ---
    @Test
    void deveriaRetornarStatusCode200ParaListarOrdensDeServico() throws Exception {
        Mockito.when(service.listarOrdensDeServico(Mockito.any()))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ordens-servicos"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // --- exibir por id ---
    @Test
    void deveriaRetornarStatusCode200ParaExibirOrdemServicoPorId() throws Exception {
        Cliente cliente = criarClienteMock();

        OrdemServicoCadastroDTO dto = new OrdemServicoCadastroDTO(
                1L, "001", LocalDate.of(2026, 9, 20), "Revisão",
                new VeiculoDTO("Onix", 2022, 35000),
                null, null, "Barulho no motor", 450.0
        );
        OrdemServico ordemServico = new OrdemServico(dto);
        ReflectionTestUtils.setField(ordemServico, "id", 1L);
        ordemServico.setCliente(cliente);

        Mockito.when(service.exibirOrdemDeServico(1L))
                .thenReturn(new OrdemServicoDetalhamentoDTO(ordemServico));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ordens-servicos/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deveriaRetornarStatusCode404QuandoOrdemServicoNaoExiste() throws Exception {
        Mockito.when(service.exibirOrdemDeServico(99L))
                .thenThrow(new RecursoNaoEncontradoException("Ordem de serviço não encontrada"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ordens-servicos/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deveriaRetornarStatusCode200ParaAtualizarOrdemServico() throws Exception {
        String json = """
            {
              "tipoServico": "Troca de óleo",
              "veiculo": {
                "veiculoModelo": "Onix",
                "veiculoAno": 2022,
                "veiculoKm": 35000
              },
              "categoriaServico": "MOTOR",
              "tipoFalha": "VAZAMENTO",
              "descricaoProblema": "Vazamento identificado",
              "valorTotal": 450.0
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/ordens-servicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service).atualizarOrdemDeServico(Mockito.any(), Mockito.eq(1L));
    }

    @Test
    void deveriaRetornarStatusCode204ParaAtivarOrdemServico() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/ordens-servicos/ativacao/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(service).ativarOrdemDeServico(1L);
    }

    @Test
    void deveriaRetornarStatusCode204ParaRemoverOrdemServico() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/ordens-servicos/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(service).removerOrdemDeServico(1L);
    }

    private Cliente criarClienteMock() {
        ClienteCadastroDTO clienteCadastroDTO = new ClienteCadastroDTO(
                "João da Silva", "12345678900", "11999998888",
                "joao.silva@email.com", "São Paulo", "SP"
        );
        Cliente cliente = new Cliente(clienteCadastroDTO);
        ReflectionTestUtils.setField(cliente, "id", 1L);
        return cliente;
    }
}
