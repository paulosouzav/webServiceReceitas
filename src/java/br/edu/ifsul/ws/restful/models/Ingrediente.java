package br.edu.ifsul.ws.restful.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulosouza
 */

@XmlRootElement(name="ingrediente")
public class Ingrediente {
    private String nome;
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
