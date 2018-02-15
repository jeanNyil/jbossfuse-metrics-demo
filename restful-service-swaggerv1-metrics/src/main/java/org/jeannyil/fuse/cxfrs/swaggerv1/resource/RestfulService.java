package org.jeannyil.fuse.cxfrs.swaggerv1.resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/restfulservice")
@Api(value = "/restfulservice", description = "RESTful service that is compliant with the swagger v1 spec")
public class RestfulService {

	@GET
	@Path("/generatenumbers")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(httpMethod = "GET",
			value = "Returns a list of randomly generated numbers according to the input count and range query parameters",
			notes = "The returned list associate each generated number with a even/odd boolean flag<br>" +
					"The following required query parameters must be supplied: count, range<br>" +
					"Request URI sample: /restfulservice/generatenumbers?count=2&range=50<br>" +
					"Corresponding JSON response:<br/>" +
					"{<br>" +
					"  \"count\": 2,<br>" +
					"  \"range\": 50,<br>" +
					"  \"randomlyGeneratedNumbers\": [<br>" +
					"    {<br>" +
					"      \"number\": 20,<br>" +
					"      \"isEven\": true<br>" +
					"    },<br>" +
					"    {<br>" +
					"      \"number\": 42,<br>" +
					"      \"isEven\": true<br>" +
					"    }<br>" +
					"  ]<br>" +
					"}",
			response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid input parameters"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public Response getRandomlyGeneratedNumbers(@ApiParam(value = "Number of random integers to generate", required = true) @QueryParam("count") @DefaultValue("0") int count,
                                                @ApiParam(value = "The maximum range for the generated number", required = true) @QueryParam("range") @DefaultValue("0") int range) {
		return null;
	}

}
