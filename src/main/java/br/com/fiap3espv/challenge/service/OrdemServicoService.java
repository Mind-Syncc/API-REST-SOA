package br.com.fiap3espv.challenge.service;

import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoCadastroDTO;
import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoCadastroResponseDTO;
import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoDetalhamentoDTO;
import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoListagemDTO;
import br.com.fiap3espv.challenge.model.Cliente;
import br.com.fiap3espv.challenge.model.OrdemServico;
import br.com.fiap3espv.challenge.repository.OrdemServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<OrdemServicoListagemDTO> listarOrdensDeServico(Pageable pageable) {
        return ordemServicoRepository.findAll(pageable).map(OrdemServicoListagemDTO::new);
    }

    public OrdemServicoDetalhamentoDTO exibirOrdemDeServico(Long id) {
        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada"));
        return new OrdemServicoDetalhamentoDTO(ordemServico);
    }

    public void ativarOrdemDeServico(Long id) {
        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada"));
        ordemServico.ativarOrdemDeServico();
        ordemServicoRepository.save(ordemServico);
    }

    public void removerOrdemDeServico(Long id) {
        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada"));
        ordemServico.desativarOrdemDeServico();
        ordemServicoRepository.save(ordemServico);
    }
}
