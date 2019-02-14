package com.jcq.frame.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcq.frame.client.JcqClient;
import com.jcq.frame.utils.CommonUtils;
import com.jcq.frame.utils.XyfJsonResult;

/**
 * webapi 相关的api编写 通过调用api可以实现转发相关信息到群里或者私聊等功能
 * @author reborn
 */
@RestController
@RequestMapping("/api")
public class WebApiController {
	/**
	 * 
	 * @param fromQQ
	 * @param message
	 * @return
	 */
	@RequestMapping("/sendPrivateMessage")
	public XyfJsonResult privateMessage(String fromQQ, String message) {
		if (CommonUtils.isEmail(fromQQ) || CommonUtils.isEmpty(message)) {
			return XyfJsonResult.errorMsg("send error param error");
		}
		// 客户端需要对from QQ字段进行预处理防止 提交给服务器时候出现错误
		if (!CommonUtils.check(fromQQ)) {
			return XyfJsonResult.errorMsg("param error");
		}
		JcqClient sc = new JcqClient();
		sc.getInstance();
		sc.sendPrivateMessage(message, fromQQ);
		sc.closeClinet();
		return XyfJsonResult.ok("send private msg ok ...");
	}

	/**
	 * 
	 * @param fromGroup
	 * @param message
	 * @param fromQQ
	 * @return
	 */
	@RequestMapping("/sendGroupMessage")
	public XyfJsonResult groupMessage(String fromGroup, String message, String fromQQ) {
		if (CommonUtils.isEmail(fromQQ) || CommonUtils.isEmpty(message) || CommonUtils.isEmpty(fromGroup)) {
			return XyfJsonResult.errorMsg("send msg error param error");
		}
		if (!CommonUtils.check(fromQQ)||!CommonUtils.check(fromGroup)) {
			return XyfJsonResult.errorMsg("param error");
		}
		JcqClient sc = new JcqClient();
		sc.getInstance();
		sc.sendGroupMessage(message, fromQQ, fromGroup);
		sc.closeClinet();
		return XyfJsonResult.ok("send group msg ok ...");
	}

}
