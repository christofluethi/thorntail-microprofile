package ch.shaped.microprofile.demo.app.info;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@Path("/info")
public class InfoResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(monotonic = true, name = "info-count", absolute = true)
    @Timed(name = "info-time", absolute = true)
    public String get() throws UnknownHostException {
        return "Hello from "+ InetAddress.getLocalHost().getHostName()+" at "+ InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * This way works well when there are multiple network interfaces.
     * It always returns the preferred outbound IP. The destination 8.8.8.8 is not needed to be reachable.
     * does not work on osx
     */
    private String getIP() throws UnknownHostException, SocketException {
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        }
    }
}