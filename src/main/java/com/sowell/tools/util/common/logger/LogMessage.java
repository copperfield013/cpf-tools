package com.sowell.tools.util.common.logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LogMessage {
	private Document document;
	private LogElement logElement;
	public LogMessage(String html, LogElement logElement) {
		this.logElement = logElement;
		this.document = Jsoup.parse(html);
	}
	
	
	private Document dealClonedDocument(){
		if(this.logElement != null && this.document != null){
			Document temp = this.document.clone();
			if(temp != null){
				if(this.logElement.hasTag(LogTag.WARNING)){
					temp.prependText("【警告】");
				}
				if(this.logElement.hasTag(LogTag.SUC)){
					temp.appendText("【成功】");
				}
				if(this.logElement.hasTag(LogTag.CHECK_MODE)){
					temp.prepend("【检测】");
				}
			}
			return temp;
		}
		return null;
	}
	
	@Override
	public String toString() {
		Document temp = this.dealClonedDocument();
		return temp == null ? null : temp.text();
	}
	
	public String toText(){
		String ret = this.toString();
		return ret == null ? "" : ret;
	}
	
	public String toHTML(){
		Document temp = this.dealClonedDocument();
		return temp == null ? "" : temp.html();
	}
	
	public LogElement getLogElement() {
		return logElement;
	}
}
