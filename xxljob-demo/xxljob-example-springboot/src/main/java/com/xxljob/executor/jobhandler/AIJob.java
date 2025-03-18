package com.xxljob.executor.jobhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yaohui
 * @create 2025/3/17 上午11:19
 **/
@Component
public class AIJob {

	@Value("${xxl.job.ai-job.api-key}")
	private String apiKey;

	@Value("${xxl.job.ai-job.webhook}")
	private String webhook;

	@XxlJob(value = "sinaNews")
	public ReturnT<String> sinaNews() throws Exception {
		String url = "https://finance.sina.com.cn/topnews/";
		String model = "deepseek-r1";
		ReturnT<String> rtn = ReturnT.SUCCESS;
		Document document = Jsoup.connect(url).get();
		// 定位到“汇总榜”部分
		Element summaryBlock = document.selectFirst("div.lbti:has(h2:containsOwn(汇总榜))");
		if (summaryBlock != null) {
			// 找到该块中的<script>标签
			Element scriptTag = summaryBlock.parent().selectFirst("script[src]");
			if (scriptTag != null) {
				String jobParam = XxlJobContext.getXxlJobContext().getJobParam();
				// 提取src属性值
				String srcValue = scriptTag.attr("src");
				HttpResponse<String> httpResponse = Unirest.get(srcValue).asString();
				String news = httpResponse.getBody();
				Map<String, Object> bodyMap = new HashMap<>();
				bodyMap.put("model", model);
				List<Map<String, String>> messagesList = new ArrayList<>();
				Map<String, String> message = new HashMap<>();
				message.put("role", "system");
				message.put("content", jobParam);
				messagesList.add(message);

				message = new HashMap<>();
				message.put("role", "user");
				message.put("content", "这是今天的财经新闻，帮我按规则解析：" + news );
				messagesList.add(message);
				bodyMap.put("messages", messagesList);
				String bodyJson = new Gson().toJson(bodyMap);
				Unirest.setTimeouts(120000, 120000); //加大timeout，防止超时退出
				HttpResponse<JsonNode> jsonHttpResponse =
						Unirest.post("https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions")
								.header("Authorization", "Bearer " + apiKey)
								.header("Content-Type", "application/json")
								.body(bodyJson)
								.asJson();
				OpenAIResponse openAIResponse =
						new Gson().fromJson(jsonHttpResponse.getBody().getObject().toString(), OpenAIResponse.class);
				rtn.setContent(openAIResponse.getChoices().get(0).getMessage().getContent());

				sendMessage(openAIResponse);

				XxlJobHelper.log(openAIResponse.getChoices().get(0).getMessage().getContent());
			} else {
				rtn.setCode(ReturnT.FAIL_CODE);
				rtn.setMsg("未找到汇总榜的<script>标签");
			}
		} else {
			rtn.setCode(ReturnT.FAIL_CODE);
			rtn.setMsg("未找到汇总榜部分");
		}
		return rtn;
	}


	private ReturnT<String> sendMessage(OpenAIResponse openAIResponse) throws UnirestException {
		ReturnT<String> rtn = ReturnT.SUCCESS;
		Map<String, Object> map = new HashMap<>();
		map.put("msgtype", "markdown");
		Map<String, String> content = new HashMap<>();
		content.put("title", "今日热点");
		content.put("text", openAIResponse.getChoices().get(0).getMessage().getContent());
		map.put("markdown", content);
		HttpResponse<JsonNode> response = Unirest.post(webhook)
				.header("Content-Type", "application/json")
				.body(new Gson().toJson(map))
				.asJson();

		if (response.getStatus() != 200) {
			rtn.setCode(ReturnT.FAIL_CODE);
			rtn.setMsg("发送失败！");
			return rtn;
		}
		return rtn;
	}
}
