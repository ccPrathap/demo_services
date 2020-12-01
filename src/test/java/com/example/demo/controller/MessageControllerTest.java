package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;

@WebMvcTest(value = MessageController.class)
public class MessageControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MessageRepository messageRepository;

	@Test
	public void getAllMessages() throws Exception {
		List<Message> messages = new ArrayList<>();
		Message message = new Message("Hello World");
		message.setId(1);
		messages.add(message);

		Mockito.when(messageRepository.findAll()).thenReturn(messages);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/messages").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[{\"id\":1,\"message\":\"Hello World\"}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void getExistingMessageById() throws Exception {
		Message message = new Message("Hello World");
		message.setId(1);

		Mockito.when(messageRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(message));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/messages/1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"id\":1,\"message\":\"Hello World\"}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void getNonExistingMessageById() throws Exception {
		Mockito.when(messageRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/messages/1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "Message not found for this id: 1";
		assertEquals(expected, result.getResponse().getContentAsString());
	}

	@Test
	public void createMessage() throws Exception {
		Message message = new Message("Hello World");
		message.setId(1);
		String inputJson = "{\"message\":\"Hello World\"}";

		Mockito.when(messageRepository.save(Mockito.any())).thenReturn(message);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/messages").accept(MediaType.APPLICATION_JSON)
				.content(inputJson).contentType(MediaType.APPLICATION_JSON);
		;

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"id\":1,\"message\":\"Hello World\"}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
