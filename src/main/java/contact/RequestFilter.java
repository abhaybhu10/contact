package contact;

import constants.ContactConstants;
import excpetion.AppException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.List;

/**
 * Created by kuabhay on 16/02/19
 */
@Provider
public class RequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
        List<String> value = headers.get(ContactConstants.AUTH_HEADER_KEY);
        if(value == null || value.isEmpty() || value.get(0).compareTo(ContactConstants.AUTH_VALUE) != 0){
            throw new AppException(Response.Status.UNAUTHORIZED, "Invalid auth");
        }
    }
}
