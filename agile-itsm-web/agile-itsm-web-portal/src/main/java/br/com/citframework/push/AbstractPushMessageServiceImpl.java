package br.com.citframework.push;

public abstract class AbstractPushMessageServiceImpl<E extends MessageRequest<?>, R extends MessageResponse> implements PushMessageService<E, R> {

    private ConfigPushService config;

    protected ConfigPushService getConfig() {
        return config;
    }

    @Override
    public void configPushService(final ConfigPushService config) {
        this.config = config;
    }

}
