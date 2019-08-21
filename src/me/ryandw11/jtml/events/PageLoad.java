package me.ryandw11.jtml.events;

import javafx.scene.web.WebView;
import me.ryandw11.jtml.JTML;

@SuppressWarnings("restriction")
public class PageLoad {
	
	private WebView wv;
	private JTML jtml;
	public PageLoad(WebView wv, JTML jtml) {
		this.wv = wv;
		this.jtml = jtml;
	}
	
	/**
	 * Considered LAPI. Should not be used.
	 * @deprecated
	 * @return Web View
	 */
	public WebView getWebView() {
		return wv;
	}
	
	/**
	 * Get the JTML instance.
	 * @return JTML instance.
	 */
	public JTML getJTML() {
		return jtml;
	}

}
