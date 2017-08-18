package br.edu.ifsul.ws.restful.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.edu.ifsul.ws.restful.models.Contato;
import com.google.gson.Gson;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contatos")
public class ContatoResource {

    static private Map<Integer, Contato> contatosMap;

    static {
        contatosMap = new HashMap<Integer, Contato>();
        
        Contato c1 = new Contato();
        c1.setId(1);
        c1.setNome("A");
        c1.setTelefone("1");
        Contato c2 = new Contato();
        c2.setId(2);
        c2.setNome("B");
        c2.setTelefone("2");
        Contato c3 = new Contato();
        c3.setId(3);
        c3.setNome("C");
        c3.setTelefone("3");
        Contato c4 = new Contato();
        c4.setId(4);
        c4.setNome("D");
        c4.setTelefone("4");
        contatosMap.put(c1.getId(), c1);
        contatosMap.put(c2.getId(), c2);
        contatosMap.put(c3.getId(), c3);
        contatosMap.put(c4.getId(), c4);
    } 

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("xml")
    public List<Contato> getContatosXML() {
        return new ArrayList<>(contatosMap.values());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("json")
    public Response getContatosJSON() {
        List<Contato> lista = new ArrayList<>(contatosMap.values());
        GenericEntity<List<Contato>> entidade = new GenericEntity<List<Contato>>(lista) {};
        return Response.ok(entidade).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gson")
    public String getContatosJSONcomGson() {
        List<Contato> lista = new ArrayList<>(contatosMap.values());
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @Path("/xml/{id}")
    @GET
    @Produces(MediaType.TEXT_XML)
    public Contato getContatoXML(@PathParam("id") int id) {
        return contatosMap.get(id);
    }

    @Path("/json/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContatoJSON(@PathParam("id") int id) {
        return Response.status(Response.Status.OK).entity(contatosMap.get(id)).build();
    }

    @POST
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response adicionaContato(Contato contato) {
        int id = contatosMap.get(contatosMap.size()).getId() + 1;
        System.out.println(id);
        contato.setId(id);
        contatosMap.put(contato.getId(), contato);        
        return Response.status(Response.Status.CREATED).entity("Contato " + contato.getNome() + " cadastrado com sucesso!").build();
    }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response atualizaContato(Contato contato, @PathParam("id") int id) {
        Contato atual = contatosMap.get(id);
        if (atual != null) { 
            atual.setNome(contato.getNome());
            atual.setTelefone(contato.getTelefone());
            return Response.status(Response.Status.OK).entity("Contato " + contato.getId() + " atualizado com sucesso!").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Contato " + contato.getId() + " não encontrado!").build();
        }
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeContato(@PathParam("id") int id) {
        if (contatosMap.containsKey(id)) { 
            contatosMap.remove(id);
            return Response.status(Response.Status.OK).entity("Contato " + id + " removido com sucesso!").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Contato " + id + " não encontrado!").build();
        }
    }
}