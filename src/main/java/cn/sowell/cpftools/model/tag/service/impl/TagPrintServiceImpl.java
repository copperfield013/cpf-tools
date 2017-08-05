package cn.sowell.cpftools.model.tag.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.utils.TextUtils;
import cn.sowell.cpftools.model.tag.service.TagPrintService;

import com.sowell.tools.util.StringUtils;

@Service
public class TagPrintServiceImpl implements TagPrintService{

	@Override
	public String generateExtTagCode(String extTypeCode) {
		long result = ChronoUnit.DAYS.between((new Date(0l)).toInstant(), Instant.now());
		StringBuffer buffer = new StringBuffer();
		buffer.append("HS");
		int codeLenth = extTypeCode.length();
		while(codeLenth++ < 2){
			buffer.append("0");
		}
		buffer.append(extTypeCode);
		buffer.append("00");
		buffer.append(TextUtils.convert(result, 62, 3));	
		buffer.append(StringUtils.uuid(4, 62).toLowerCase());
		return buffer.toString();
	}

}
