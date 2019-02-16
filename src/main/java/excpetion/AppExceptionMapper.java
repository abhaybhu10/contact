package excpetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.zookeeper.proto.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by kuabhay on 11/02/19
 */
public class AppExceptionMapper implements ExceptionMapper<AppException> {
    @Override
    public Response toResponse(AppException e) {

        return Response.status(e.getStatus()).entity(new ErrorResponse(e.getMsg())).build();
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ErrorResponse{
        private String msg;
    }
}
