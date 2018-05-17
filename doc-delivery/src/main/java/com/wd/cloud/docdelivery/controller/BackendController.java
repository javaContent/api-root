package com.wd.cloud.docdelivery.controller;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.config.DeliveryConfig;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.enums.ChannelEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.enums.ProcessTypeEnum;
import com.wd.cloud.docdelivery.service.BackendService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.mail.MailUtil;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@RestController
@RequestMapping("/backend")
public class BackendController {

	private static String title = "[文献互助]——%s";

	private static final String TITLE_CRS = "[crscholar文献传递]——%s";

	private static final String TITLE_CRS_SUCCESS = "[crscholar文献传递•成功]——%s";

	private static String title_success = "[文献互助•成功]——%s";

	private static String title_other = "[文献互助•疑难文献]——%s";

	private static String template = "您好！您查找的文件  %s 已应助成功。";

	// 文献传递改为超链接形式传递文件
	private static String templateName = "您好！您求助的文献  %s 已应助成功。";
	private static String templateUrl = "<br>请点击以下链接下载全文  %s <br>注意：该链接有效期为15天（  %s 止），请及时下载。<br><br>欢迎您使用Spischolar学术资源在线<br><a href='http://www.spischolar.com/' target='blank'>http://www.spischolar.com/</a>";

	private static String templateNull = "您好！非常抱歉，您求助文献 %s 失败，超过7天无有效应助。<br><br>欢迎您使用Spischolar学术资源在线<br><a href='http://www.spischolar.com/' target='blank'>http://www.spischolar.com/</a>";

	private static String templateOther = "您好！您求助的文献 %s 为疑难文献，我们已为您邮件通知更多用户应助该文献，并将在7天内通过邮件通知您应助结果。<br><br>欢迎您使用Spischolar学术资源在线<br><a href='http://www.spischolar.com/' target='blank'>http://www.spischolar.com/</a>";

	private static String templateBook = "您好！非常抱歉，您求助的文献 %s 应助失败。<br><br>欢迎您使用Spischolar学术资源在线<br><a href='http://www.spischolar.com/' target='blank'>http://www.spischolar.com/</a>";

	@Autowired
	private BackendService backendService;

	@Autowired
	DeliveryConfig deliveryConfig;
	/**
	 * 文献互助列表
	 * 
	 * @return
	 */
	@GetMapping("/help/list/{pageNum}/{pageSize}")
	public ResponseModel helpList(@PathVariable int pageNum, @PathVariable int pageSize, @RequestParam short type,
			@RequestParam String shool, @RequestParam String keyword, @RequestParam String beginTime,
			@RequestParam String endTime) {
		
		
		
		backendService.getHelpList(pageNum, pageSize);
		return ResponseModel.success();
	}

	/**
	 * 直接处理，上传文件
	 * 
	 * @return
	 */
	@PostMapping("/upload/{id}")
	public ResponseModel upload(@PathVariable Long id, @RequestParam int processId, HttpServletRequest request, MultipartFile file) {
		HelpRecord helpRecord = backendService.get(id);
		helpRecord.setProcessType(ProcessTypeEnum.PASS.getCode());
		helpRecord.setGmtModified(new Date());
		// 当后台管理员直接处理时，GiveUserId和ProcessId一致
		helpRecord.setGiveUserId(processId);
		helpRecord.setProcessId(processId);
		try {
			if (file != null) {// 查找到文件
				// 创建文件目录
				File dir = FileUtil.mkdir(deliveryConfig.getSavePath());
				// 文件MD5值
				String md5File = DigestUtil.md5Hex(file.getInputStream());
				// 文件后缀
				String extName = FileTypeUtil.getType(file.getInputStream());
				// 组装成新的文件名
				String md5Filename = md5File + "." + extName;
				if (FileUtil.exist(new File(dir, md5Filename))) {
					return ResponseModel.fail("请不要重复上传");
				}
				// 创建一个新文件
				File attachFile = FileUtil.touch(dir, md5Filename);
				// 将文件流写入文件中
				FileUtil.writeFromStream(file.getInputStream(), attachFile);
				helpRecord.setDocFilename(md5Filename);
				String subject = "";
				if (helpRecord.getHelpChannel() == ChannelEnum.CRS.getCode()) {
					subject = String.format(TITLE_CRS_SUCCESS, helpRecord.getDocTitle());
				} else {
					subject = String.format(title_success, helpRecord.getDocTitle());
				}
				String url = request.getRequestURL().toString().replace("/backend/upload", "/front/download") + "/"
						+ md5Filename;
				// 到期时间
				String time = DateUtil.format(DateUtil.offsetDay(new Date(), 15), "yyyy-MM-dd");
				String content = String.format(templateName, helpRecord.getDocTitle())
						+ String.format(templateUrl, "<a href='" + url + "' target='blank'>" + url + "</a>", time);
				try {
					MailUtil.sendHtml(helpRecord.getEmail(), subject, content);
				} catch (Exception e) {
					return ResponseModel.fail("邮件发送失败");
				}
				backendService.update(helpRecord);
			}
		} catch (Exception e) {
			return ResponseModel.fail("文件上传失败");
		}
		return ResponseModel.success("文件上传成功");
	}

	/**
	 * 其他处理方式（第三方、无结果、无结果图书）
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PostMapping("/process/{id}")
	public ResponseModel process(@PathVariable Long id, @RequestParam int processId, 
			@RequestParam Integer processType) {
		HelpRecord helpRecord = backendService.get(id);
		helpRecord.setProcessType(processType);
		helpRecord.setGmtModified(new Date());
		// 当后台管理员直接处理时，GiveUserId和ProcessId一致
		helpRecord.setGiveUserId(processId);
		helpRecord.setProcessId(processId);
		String subject = "", content = "";
		if (processType == 2 || processType == 4) {// 提交第三方处理
			if (helpRecord.getHelpChannel() == ChannelEnum.CRS.getCode()) {
				subject = String.format(TITLE_CRS, helpRecord.getDocTitle());
			} else {
				subject = String.format(title_other, helpRecord.getDocTitle());
			}
			content = String.format(templateOther, helpRecord.getDocTitle());
		} else if (processType == 3) {// 没有找到结果
			if (helpRecord.getHelpChannel() == ChannelEnum.CRS.getCode()) {
				subject = String.format(TITLE_CRS, helpRecord.getDocTitle());
			} else {
				subject = String.format(title, helpRecord.getDocTitle());
			}
			content = String.format(templateNull, helpRecord.getDocTitle());
		} else if (processType == 5) {// 图书
			if (helpRecord.getHelpChannel() == ChannelEnum.CRS.getCode()) {
				subject = String.format(TITLE_CRS, helpRecord.getDocTitle());
			} else {
				subject = String.format(title, helpRecord.getDocTitle());
			}
			content = String.format(templateBook, helpRecord.getDocTitle());
		}
		try {
			if (processType != 6 && processType != 7) {
				MailUtil.sendHtml(helpRecord.getEmail(), subject, content);
			}
		} catch (Exception e) {
			return ResponseModel.fail("邮件发送失败");
		}
		backendService.update(helpRecord);
		return ResponseModel.success("处理成功");
	}

	/**
	 * 审核通过
	 * 
	 * @return
	 */
	@PatchMapping("/audit/pass/{id}")
	public ResponseModel auditPass(@PathVariable Long id, @RequestParam int processId,HttpServletRequest request) {
		HelpRecord helpRecord = backendService.getAudit(id);
		if (helpRecord == null) {
			return ResponseModel.fail();
		}
		helpRecord.setProcessType(ProcessTypeEnum.PASS.getCode());
		helpRecord.setGmtModified(new Date());
		helpRecord.setProcessId(processId);
		helpRecord.setStatus(HelpStatusEnum.PASS.getCode());
		String subject = "";
		if (helpRecord.getHelpChannel() == ChannelEnum.CRS.getCode()) {
			subject = String.format(TITLE_CRS_SUCCESS, helpRecord.getDocTitle());
		} else {
			subject = String.format(title_success, helpRecord.getDocTitle());
		}
		String url = request.getRequestURL().toString().replace("/backend/upload", "/front/download") + "/"
				+ helpRecord.getDocFilename();
		String time = DateUtil.format(DateUtil.offsetDay(new Date(), 15), "yyyy-MM-dd");
		String content = String.format(templateName, helpRecord.getDocTitle())
				+ String.format(templateUrl, "<a href='" + url + "' target='blank'>" + url + "</a>", time);
		MailUtil.sendHtml(helpRecord.getEmail(), subject, content);
		backendService.update(helpRecord);
		return ResponseModel.success();
	}

	/**
	 * 审核不通过
	 * 
	 * @return
	 */
	@PatchMapping("/audit/nopass/{id}")
	public ResponseModel auditNoPass(@PathVariable Long id, @RequestParam int processId) {
		HelpRecord helpRecord = backendService.getAudit(id);
		if (helpRecord == null) {
			return ResponseModel.fail();
		}
		helpRecord.setGmtModified(new Date());
		helpRecord.setProcessId(processId);
		helpRecord.setStatus(HelpStatusEnum.NOPASS.getCode());
		backendService.update(helpRecord);
		return ResponseModel.success();
	}

}
