import java.io.Serializable;


public class NetworkObject implements Serializable {

	public Member Originator;
	public Member Recipient;
	
	public Object data;
	public reason objectReason;
	
	public int messageNumber; //FIFO??? nice for debug?
	
	enum reason 
	{joinRequest,
	 joinRequestResponse,
	 leaveNotification,
		keepAlivePing, 
	 keepAliveResponse,
	 event, //Any canvasEvent, transfer ownership
	 dataState,  //What objects are drawn on canvas.
	 networkState,
	 chat,
	 mousePress,
	 mouseDrag,
	 mouseRelease};
	 
	 
	 
	 public NetworkObject(
		 Member Originator,
		 Member Recipient,
		 Object data,
		 reason objectReason,
		 int messageNumber)
	 {
		 this.Originator = Originator;
		 this.Recipient = Recipient;
		 this.data = data;
		 this.objectReason = objectReason;
		 this.messageNumber = messageNumber;
	 }
}
