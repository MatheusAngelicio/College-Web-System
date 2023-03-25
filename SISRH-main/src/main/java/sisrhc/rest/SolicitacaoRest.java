package sisrh.rest;



import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import sisrh.banco.Banco;
import sisrh.dto.Solicitacao;

@Api
@Path("/solicitacao")
public class SolicitacaoRest {
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarSolicitacoes() throws Exception {
		List<Solicitacao> lista = Banco.listarSolicitacoes();
		GenericEntity<List<Solicitacao>> entity = new GenericEntity<List<Solicitacao>>(lista) {
			
		};
		return Response.ok().entity(entity).build();
		}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response incluirSolicitacao(Solicitacao solicitacao) {
		try {
			Solicitacao emp = Banco.incluirSolicitacao(solicitacao);
			return Response.ok().entity(emp).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na inclusao da Solicitação!\" , \"detalhe\" :  \""
							+ e.getMessage() + "\"  }")
					.build();
		}
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterarSolicitacao(@PathParam("id") Integer id, Solicitacao solicitacao) {
		try {
			if (Banco.buscarSolicitacaoPorId((Integer) id) == null) {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Solicitação nao encontrada!\" }")
						.build();
			}
			Solicitacao sol = Banco.alterarSolicitacao((Integer) id, solicitacao);
			return Response.ok().entity(sol).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na alteracao da solicitação!\" , \"detalhe\" :  \""
							+ e.getMessage() + "\"  }")
					.build();
		}

	}


	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirSolicitacao(@PathParam("id") Integer id) throws Exception {
		try {
			if (Banco.buscarSolicitacaoPorId((Integer) id) == null) {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Solicitação nao encontrada!\" }")
						.build();
			}
			Banco.excluirSolicitacao((Integer)id); 
			return Response.ok().entity("{ \"mensagem\" : \"Solicitação " + (Integer) id + " excluida!\" }").build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na exclusao da solicitação!\" , \"detalhe\" :  \"" + e.getMessage()
							+ "\"  }")
					.build();
		}
	}

}
