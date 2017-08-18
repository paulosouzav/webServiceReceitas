package br.edu.ifsul.ws.restful.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="contato")
public class Contato {

    private int id;
    private String nome;
    private String telefone;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
