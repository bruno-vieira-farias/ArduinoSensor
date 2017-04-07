package br.com.onurbasoft.core;

import java.time.Instant;

/**
 * Classe que representa as medições de temperatura realizadas.
 */
public class MedicaoTemperatura {
    private Integer id;
    private double temperatura;
    private Instant instante;

    public MedicaoTemperatura(Double grauCelsius) {
        this.temperatura = grauCelsius;
    }

    public MedicaoTemperatura(Integer id, Double temperatura, Instant instante) {
        this.id = id;
        this.temperatura = temperatura;
        this.instante = instante;
    }

    public Double getGrauCelsius() {
        return temperatura;
    }

    public Integer getId() {
        return id;
    }

    public Instant getInstante() {
        return instante;
    }
}