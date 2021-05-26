package ru.itis.androidtechpractice.dto.coders;

import com.google.gson.Gson;
import ru.itis.androidtechpractice.dto.MessageDto;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<MessageDto> {

    private Gson gson = new Gson();

    @Override
    public String encode(MessageDto message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}