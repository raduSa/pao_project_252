package Entitati;

import Utile.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static Utile.ClientUtils.idCurent;

public class Comanda {
    private final int idComanda;
    private List<Produs> produse;
    private LocalDateTime data;
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }


    public Status getStatus() {
        return status;
    }

    public Comanda() {
        idComanda = idCurent;
        idCurent++;
        produse = new ArrayList<>();
        status = Status.NEPROCESAT;
        data = LocalDateTime.now();
    }

    public Comanda(Integer id, LocalDateTime date, Status stat) {
        idComanda = id;
        produse = new ArrayList<>();
        data = date;
        status = stat;
    }

    public void addProdus(Produs produs) {
        produse.add(produs);
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder("Comanda{" +
                "idComanda=" + idComanda +
                ", data=" + data +
                ", status=" + status +
                "}, Produse: \n");
        for (Produs p : produse) {
            st.append("\t").append(p.toString()).append("\n");
        }
        return String.valueOf(st);
    }

    public double getSumaTotala() {
        double sumaTotala = 0;
        for (Produs produs : this.produse) {
            sumaTotala += produs.getPret();

        }
        return sumaTotala;
    }

    public LocalDateTime getData() {
        return data;
    }

    public List<Produs> getProduse() {
        return produse;
    }
}