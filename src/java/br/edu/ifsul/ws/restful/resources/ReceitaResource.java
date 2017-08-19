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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/receitas")
public class ReceitaResource {

    static private Map<Integer, Receita> receitasMap;

    static {
        
        Ingrediente i1 = new Ingrediente();
        i1.setNome("Carne");
        i1.setDescricao("Beef is the culinary name for meat from cattle, particularly skeletal muscle.");
        
        List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        ingredientes.add(i1);
        
        receitasMap = new HashMap<Integer, Receita>();
        
        Receita r1 = new Receita();
        r1.setId(1);
        r1.setNome("Lasanha");
        r1.setDescricao("Lasagne are wide, flat-shaped pasta, and possibly one of the oldest types of pasta.");        
        r1.setIngredientes( ingredientes );
        
        receitasMap.put(r1.getId(), r1);
        
    } 

    // GET
    
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

    @GET
    @Path("/xml/{id}")
    @Produces(MediaType.TEXT_XML)
    public Receita getReceitasXML(@PathParam("id") int id) {
        return receitasMap.get(id);
    }

    @GET
    @Path("/json/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReceitasJSON(@PathParam("id") int id) {
        return Response.status(Response.Status.OK).entity(receitasMap.get(id)).build();
    }
    
    @GET
    @Path("/gson/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReceitasComGSON(@PathParam("id") int id) {
        Gson gson = new Gson();
        return Response.status(Response.Status.OK).entity(gson.toJson(receitasMap.get(id))).build();
    }
    
    // GET ESPECIFICO
    @GET
    @Path("/json/{id}/ingredientes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredientesComJSON(@PathParam("id") int id) {
        Gson gson = new Gson();
        return Response.status(Response.Status.OK).entity(gson.toJson(receitasMap.get(id).getIngredientes())).build();
    }
    
    @GET
    @Path("/gson/{id}/ingredientes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredientesComGSON(@PathParam("id") int id) {
        Gson gson = new Gson();
        return Response.status(Response.Status.OK).entity(gson.toJson(receitasMap.get(id).getIngredientes())).build();
    }

    @GET
    @Path("/xml/{id}/ingredientes")
    @Produces(MediaType.TEXT_XML)
    public Response getIngredientesComXML(@PathParam("id") int id) {
        return Response.status(Response.Status.OK).entity(receitasMap.get(id).getIngredientes()).build();
    }
    
    // GET CONVERTER
    @POST
    @Path("/xml-to-json")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response convertReceitaParaGSON(Receita receita) {
        Gson gson = new Gson();
        return Response.status(Response.Status.OK).entity(gson.toJson(receita)).build();
    }
    
    @POST
    @Path("/json-to-xml")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public Response convertReceitaParaXML(Receita receita) {
        System.out.println(receita.getNome());
        return Response.status(Response.Status.OK).entity(receita).build();
    }

    // POST
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response adicionaReceita(Receita receita) {
        int id = receitasMap.get(receitasMap.size()).getId() + 1;
        receita.setId(id);
        receitasMap.put(receita.getId(), receita);        
        return Response.status(Response.Status.CREATED).entity("Receita " + receita.getNome() + " cadastrada com sucesso!").build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response adicionaReceitaJSON(Receita receita) {
        int id = receitasMap.get(receitasMap.size()).getId() + 1;
        receita.setId(id);
        receitasMap.put(receita.getId(), receita);        
        return Response.status(Response.Status.CREATED).entity("Receita " + receita.getNome() + " cadastrada com sucesso!").build();
    }
    
    // PUT
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
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
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response atualizaReceitaComJSON(Receita receita, @PathParam("id") int id) {
        Receita atual = receitasMap.get(id);
        if (atual != null) { 
            atual.setNome(receita.getNome());
            atual.setDescricao(receita.getDescricao());
            return Response.status(Response.Status.OK).entity("Receita " + receita.getId() + " atualizada com sucesso!").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Receita " + receita.getId() + " não encontrada!").build();
        }
    }
    
    // PUT ESPECIFICO
    @PUT
    @Path("{id}/novo-ingrediente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response atualizaIngredientesComJSON(Ingrediente ingrediente, @PathParam("id") int id) {
        Receita atual = receitasMap.get(id);
        if (atual != null) { 
            atual.addIngrediente(ingrediente);
            return Response.status(Response.Status.OK).entity("Ingrediente " + ingrediente.getNome() + " inserido na receita " + id).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Receita " + id + " não encontrada!").build();
        }
    }
    
    @PUT
    @Path("{id}/novo-ingrediente")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response atualizaIngredientesComXML(Ingrediente ingrediente, @PathParam("id") int id) {
        Receita atual = receitasMap.get(id);
        if (atual != null) { 
            atual.addIngrediente(ingrediente);
            return Response.status(Response.Status.OK).entity("Ingrediente " + ingrediente.getNome() + " inserido na receita " + id).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Receita " + id + " não encontrada!").build();
        }
    }
    
    @PUT
    @Path("{id}/novo-ingrediente")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response atualizaIngredientesViaQuery(@PathParam("id") int id, @QueryParam("nome") String nome, @QueryParam("descricao") String descricao) {
        
        Receita atual = receitasMap.get(id);
        if (atual != null) {
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setNome(nome);
            ingrediente.setDescricao(descricao);
            
            atual.addIngrediente(ingrediente);
            
            return Response.status(Response.Status.OK).entity("Ingrediente " + ingrediente.getNome() + " inserido na receita " + id).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Receita " + id + " não encontrada!").build();
        }
    }

    // DELETE
    
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