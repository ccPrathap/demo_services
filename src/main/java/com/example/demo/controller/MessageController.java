package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MessageController {
	@Autowired
	private MessageRepository messageRepository;

	@GetMapping("/messages")
	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}

	@GetMapping("/messages/{id}")
	public ResponseEntity<Message> getMessageById(@PathVariable(value = "id") Long messageId) throws Exception {
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new Exception("Message not found for this id : " + messageId));
		return ResponseEntity.ok().body(message);
	}

	@PostMapping("/messages")
	public Message createMessage(@Valid @RequestBody Message message) {
		return messageRepository.save(message);
	}

}