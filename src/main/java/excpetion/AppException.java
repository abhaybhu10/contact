package excpetion;

import lombok.Getter;

import javax.ws.rs.core.Response;

/**
 * Created by kuabhay on 11/02/19
 */
@Getter
public class AppException extends RuntimeException {
    private Response.Status status;
    private String msg;
    public AppException(Response.Status status,String msg){
        super(msg);
        this.status = status;
        this.msg = msg;
    }
}
