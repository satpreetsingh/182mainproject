
public class NetworkObject {

	private Member Originator;
	private Member Recipient;
	
	private Object data;
	private reason objectReason;
	
	private int messageNumber; //FIFO??? nice for debug?
	
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
	 toolTransfer};
}
