package br.edu.ifsul.ws.restful.resources;

import br.edu.ifsul.ws.restful.models.Ingrediente;
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

import br.edu.ifsul.ws.restful.models.Receita;
import com.google.gson.Gson;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/receitas")
public class ReceitaResource {

    static private Map<Integer, Receita> receitasMap;

    static {
        
        Ingrediente i1 = new Ingrediente();
        i1.setId(0);
        i1.setNome("Carne");
        
        List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        ingredientes.add(i1);
        
        receitasMap = new HashMap<Integer, Receita>();
        
        Receita r1 = new Receita();
        r1.setId(0);
        r1.setNome("Lasanha");
        r1.setDescricao("Lasagne are wide, flat-shaped pasta, and possibly one of the oldest types of pasta.");        
        r1.setIngredientes( ingredientes );
        
        receitasMap.put(r1.getId(), r1);
        
    } 

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("xml")
    public List<Receita> getReceitasXML() {
        return new ArrayList<>(receitasMap.values());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("json")
    public Response getReceitasJSON() {
        List<Receita> lista = new ArrayList<>(receitasMap.values());
        GenericEntity<List<Receita>> entidade = new GenericEntity<List<Receita>>(lista) {};
        return Response.ok(entidade).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gson")
    public String getReceitasJSONcomGson() {
        List<Receita> lista = new ArrayList<>(receitasMap.values());
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @Path("/xml/{id}")
    @GET
    @Produces(MediaType.TEXT_XML)
    public Receita getReceitasXML(@PathParam("id") int id) {
        return receitasMap.get(id);
    }

    @Path("/json/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReceitasJSON(@PathParam("id") int id) {
        return Response.status(Response.Status.OK).entity(receitasMap.get(id)).build();
    }

    @POST
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response adicionaReceita(Receita receita) {
        int id = receitasMap.get(receitasMap.size()).getId() + 1;
        receita.setId(id);
        receitasMap.put(receita.getId(), receita);        
        return Response.status(Response.Status.CREATED).entity("Receita " + receita.getNome() + " cadastrada com sucesso!").build();
    }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response atualizaReceita(Receita receita, @PathParam("id") int id) {
        Receita atual = receitasMap.get(id);
        if (atual != null) { 
            atual.setNome(receita.getNome());
            atual.setDescricao(receita.getDescricao());
            return Response.status(Response.Status.OK).entity("Receita " + receita.getId() + " atualizada com sucesso!").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Receita " + receita.getId() + " não encontrada!").build();
        }
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeReceita(@PathParam("id") int id) {
        if (receitasMap.containsKey(id)) { 
            receitasMap.remove(id);
            return Response.status(Response.Status.OK).entity("Receita " + id + " removida com sucesso!").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Receita " + id + " não encontrada!").build();
        }
    }
}