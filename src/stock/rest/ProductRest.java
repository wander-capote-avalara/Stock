package stock.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import stock.services.ProductService;


@Path("products")
public class ProductRest extends UtilRest {
	public ProductRest() {
	}
	
	ProductService ps = new ProductService();
	
	@GET
	@Path("/getProducts")
	@Produces("text/plain")
	public Response getProducts() {
		try {
			return this.buildResponse(ps.getProducts());
		} catch (Exception e) {
			return this.buildErrorResponse("Error!");
		}
	}
}
