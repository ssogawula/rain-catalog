package za.co.rain.exeptions;

public class MessageDetail {

	private final String errorMessage;
	private final String callerUrl;
	
	public MessageDetail(String errorMessage, String callerUrl) {
		this.errorMessage = errorMessage;
		this.callerUrl = callerUrl;
	}
}
