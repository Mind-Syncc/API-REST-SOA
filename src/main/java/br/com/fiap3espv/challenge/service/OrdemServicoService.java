package br.com.fiap3espv.challenge.service;

import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoCadastroDTO;
import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoCadastroResponseDTO;
import br.com.fiap3espv.challenge.model.Cliente;
import br.com.fiap3espv.challenge.model.OrdemServico;
import br.com.fiap3espv.challenge.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdemServicoService {
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteService clienteService;

    public OrdemServicoCadastroResponseDTO cadastrarOrdemDeServico(OrdemServicoCadastroDTO ordemServicoCadastroDTO) {
        Cliente cliente = clienteService.procurarClientePorId(ordemServicoCadastroDTO.clienteId());

        OrdemServico ordemServico = new OrdemServico(ordemServicoCadastroDTO);
        ordemServico.setCliente(cliente);
        ordemServicoRepository.save(ordemServico);

        return new OrdemServicoCadastroResponseDTO(ordemServico);
    }
}
