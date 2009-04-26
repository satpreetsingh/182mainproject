package ChatPeer;
public class ChatMessageObject {

	messageType messageType;
	Object data;
//	int messageNumber;

	enum messageType {
		chatString, 
		chatPeerList
	};

	public ChatMessageObject(messageType messageType, Object data) {
		this.messageType = messageType;
		this.data = data;
//   	this.messageNumber = messageNumber;
	}
}
