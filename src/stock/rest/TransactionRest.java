package stock.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import stock.objects.Transaction;
import stock.services.TransactionService;

@Path("transaction")
public class TransactionRest extends UtilRest {

	public TransactionRest() {
	}
	
	TransactionService ts = new TransactionService();
	
	@POST
	@Path("/add")
	@Consumes("application/*")
	@Produces("text/plain")
	public Response addCategoria(String params) {
		try {
			Transaction transaction = new ObjectMapper().readValue(params, Transaction.class);
			ts.addTransaction(transaction);
			return this.buildResponse("Done!");
		} catch (Exception e) {
			return this.buildErrorResponse("Error!");
		}
	}
	
	@GET
	@Path("/getTransactions")
	@Produces("text/plain")
	public Response getTransactions(@QueryParam("isEntry") int isEntry) {
		try {
			return this.buildResponse(ts.getTransactions(isEntry));
		} catch (Exception e) {
			return this.buildErrorResponse("Error!");
		}
	}
	
	@GET
	@Path("/getStock")
	@Produces("text/plain")
	public Response getStock(@QueryParam("id") int id) {
		try {
			return this.buildResponse(ts.getStock(id));
		} catch (Exception e) {
			return this.buildErrorResponse("Error!");
		}
	}
}
