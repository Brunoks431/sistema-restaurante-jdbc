package model;

public class Mesa {

    private Long id;
    private Integer numero;
    private Integer capacidade;
    private String status;

    public Mesa() {
    }

    public Mesa(Long id, Integer numero,
                Integer capacidade, String status) {
        this.id = id;
        this.numero = numero;
        this.capacidade = capacidade;
        this.status = status;
    }

    public boolean estaLivre() {
        return status.equalsIgnoreCase("LIVRE");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
