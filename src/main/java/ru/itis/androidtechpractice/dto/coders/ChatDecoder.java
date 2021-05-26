package ru.itis.androidtechpractice.dto.coders;

import com.google.gson.Gson;
import ru.itis.androidtechpractice.dto.ChatDto;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatDecoder implements Decoder.Text<ChatDto> {
    private Gson gson = new Gson();

    @Override
    public ChatDto decode(String s) throws DecodeException {
        return gson.fromJson(s, ChatDto.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
