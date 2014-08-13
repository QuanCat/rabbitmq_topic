package rpc.topic.message;

import java.util.ArrayList;
import java.util.List;

public class MsgDirectory {
	
	private List<Message> msgDirectory;
	
	public MsgDirectory() {
		msgDirectory = new ArrayList<Message>();
	}

	public List<Message> getMsgDirectory() {
		return msgDirectory;
	}
	
	public void addMesg(Message msg) {
		msgDirectory.add(msg);
	}
	

}
