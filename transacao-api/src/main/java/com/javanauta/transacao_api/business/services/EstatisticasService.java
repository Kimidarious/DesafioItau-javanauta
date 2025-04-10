package com.javanauta.transacao_api.business.services;

import com.javanauta.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.javanauta.transacao_api.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcularEstasticasTransacoes(Integer intervaloBusca){

        log.info("Iniciada busca de estastisticas de transações pelo período de tempo " + intervaloBusca);

        long start = System.currentTimeMillis();
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloBusca);

        if (transacoes.isEmpty()) {
            return new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);
        }
        DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream()
                .mapToDouble(TransacaoRequestDTO::valor).summaryStatistics();

        log.info("Estatisticas retornada com sucesso");

        long finish = System.currentTimeMillis();
        long tempoRequisicao = finish - start;
        System.out.println("Tempo de requisição: " + tempoRequisicao + " ms");
        return new EstatisticasResponseDTO(estatisticasTransacoes.getCount(),
                estatisticasTransacoes.getSum(),
                estatisticasTransacoes.getAverage(),
                estatisticasTransacoes.getMin(),
                estatisticasTransacoes.getMax());
    }

}
