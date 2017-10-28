package helloworld;

public class StandartOutMessageRenderer implements MessageRenderer {
    private MessageProvider messageProvider = null;

    public void render(){
        if(messageProvider == null){
            throw new RuntimeException("Ti pidor!");
        }
        System.out.println(messageProvider.getMessage());
    }
    public void setMessageProvider(MessageProvider provider){
        this.messageProvider = provider;
    }

    public MessageProvider getMessageProvider() {
        return messageProvider;
    }
}
