import java.awt.Color;
import java.io.Serializable;


/**
 * A network object, this is the only item sent over the network.
 * Any object put in here MUST implement Serializable.
 * @author ben
 *
 */
public class NetworkObject implements Serializable {

	public Member Originator;
	public Member Recipient;
	
	public Object data;
	public reason objectReason;
	
	
	enum reason 
	{joinSessionRequest,
	 joinPeerRequest,
	 joinRequestResponse,
	 peerListUpdate,
	 leaveNotification,
	 keepAlivePing, 
	 keepAliveResponse,
	 networkState,
	 dataState,
	 chat,
	 mousePress,
	 mouseDrag,
	 mouseRelease,
	 keyTyped,
	 keyReleased,
	 keyPressed,
	 clearCanvas,
	 clearSelection,
	 selectShape,
	 deleteShape,
	 setNewTool,
	 setShapeColor,
	 setShapeFill,
	 canvasOwnershipRequest,
	 canvasOwnershipConfirm,
	 canvasOwnershipDeny,
	 canvasOwnershipRebel,
	 canvasOwnershipSupportRebellion,
	 quit
	 };
	 
	 

	 
	 /**
	  * Create a new NetworkObject.
	  * @param Originator Person who created the object.
	  * @param Recipient Person who should get the object.
	  * @param data Actual data.
	  * @param objectReason Reason the object was made.  
	  * 	Helps for deconstructing object, 
	  * 	as data is packed a standard way for each event.
	  */
	 public NetworkObject(
		Member Originator,
		Member Recipient,
		 Object data,
		 reason objectReason)
	 {
		 this.Originator = Originator;
		 this.Recipient = Recipient;
		 this.data = data;
		 this.objectReason = objectReason;
	 }
}
