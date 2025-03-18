package com.xxljob.executor.jobhandler;

import java.util.List;

/**
 * @author yaohui
 * @create 2025/3/17 上午11:42
 **/
public class OpenAIResponse {
	private List<Choice> choices;
	private String object;
	private Usage usage;
	private long created;
	private String systemFingerprint;
	private String model;
	private String id;

	// Getters and Setters
	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public String getSystemFingerprint() {
		return systemFingerprint;
	}

	public void setSystemFingerprint(String systemFingerprint) {
		this.systemFingerprint = systemFingerprint;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

class Choice {
	private Message message;
	private String finishReason;
	private int index;
	private Object logprobs;

	// Getters and Setters
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getFinishReason() {
		return finishReason;
	}

	public void setFinishReason(String finishReason) {
		this.finishReason = finishReason;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Object getLogprobs() {
		return logprobs;
	}

	public void setLogprobs(Object logprobs) {
		this.logprobs = logprobs;
	}
}

class Message {
	private String content;
	private String reasoningContent;
	private String role;

	// Getters and Setters
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReasoningContent() {
		return reasoningContent;
	}

	public void setReasoningContent(String reasoningContent) {
		this.reasoningContent = reasoningContent;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}

class Usage {
	private int promptTokens;
	private int completionTokens;
	private int totalTokens;

	// Getters and Setters
	public int getPromptTokens() {
		return promptTokens;
	}

	public void setPromptTokens(int promptTokens) {
		this.promptTokens = promptTokens;
	}

	public int getCompletionTokens() {
		return completionTokens;
	}

	public void setCompletionTokens(int completionTokens) {
		this.completionTokens = completionTokens;
	}

	public int getTotalTokens() {
		return totalTokens;
	}

	public void setTotalTokens(int totalTokens) {
		this.totalTokens = totalTokens;
	}
}