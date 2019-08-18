package me.ryandw11.jtml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import me.ryandw11.jtml.exceptions.MethodNotFoundException;
import netscape.javascript.JSObject;

@SuppressWarnings("restriction")
public class JTML {
	
	private WebView wv;
	
	/**
	 * Add a webpage view. The JPanel version should be used correctly.
	 * @param fr
	 * @param view The string path.
	 * @param jstojava The map containing the java to javascript functions.
	 */
	public JTML(JFrame fr, String view, Map<String, Object> jstojava) {
		JFXPanel jfxPanel = new JFXPanel();
		fr.add(jfxPanel);
		Platform.runLater(() -> {
		    WebView webView = new WebView();
		    jfxPanel.setScene(new Scene(webView));
		    webView.getEngine().getLoadWorker()
            .stateProperty()
            .addListener((obs, old, neww) ->
            {
                if (neww == Worker.State.SUCCEEDED)
                {
                    JSObject bridge = (JSObject) webView.getEngine()
                            .executeScript("window");
                    Iterator<Entry<String, Object>> it = jstojava.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
                        bridge.setMember(pair.getKey(), pair.getValue());
                        it.remove();
                    }
                }
            });
			webView.getEngine().load(view);
			wv = webView;
		});
	}
	
	/**
	 * Add a webpage view.
	 * @param fr The JPanel you want it to be added to.
	 * @param view The URL file path.
	 * @param jstojava A map conatining the java to javascript functions.
	 */
	public JTML(JPanel fr, String view, Map<String, Object> jstojava) {
		JFXPanel jfxPanel = new JFXPanel();
		fr.add(jfxPanel);
		Platform.runLater(() -> {
		    WebView webView = new WebView();
		    jfxPanel.setScene(new Scene(webView));
		    webView.getEngine().getLoadWorker()
            .stateProperty()
            .addListener((obs, old, neww) ->
            {
                if (neww == Worker.State.SUCCEEDED)
                {
                    JSObject bridge = (JSObject) webView.getEngine()
                            .executeScript("window");
                    Iterator<Entry<String, Object>> it = jstojava.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
                        bridge.setMember(pair.getKey(), pair.getValue());
                        it.remove();
                    }
                }
            });
			webView.getEngine().load(view);
			wv = webView;
		});
	}
	
	/**
	 * Add a webpage view.
	 * @param fr The JPanel you want it to be added to.
	 * @param view The URL file path.
	 * @param jstojava A map conatining the java to javascript functions.
	 */
	public JTML(JPanel fr, String view, Map<String, Object> jstojava, Object callbackClass, String callbackMethod) {
		JFXPanel jfxPanel = new JFXPanel();
		fr.add(jfxPanel);
		Platform.runLater(() -> {
		    WebView webView = new WebView();
		    jfxPanel.setScene(new Scene(webView));
		    webView.getEngine().getLoadWorker()
            .stateProperty()
            .addListener((obs, old, neww) ->
            {
                if (neww == Worker.State.SUCCEEDED)
                {
                    JSObject bridge = (JSObject) webView.getEngine()
                            .executeScript("window");
                    Iterator<Entry<String, Object>> it = jstojava.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
                        bridge.setMember(pair.getKey(), pair.getValue());
                        it.remove();
                    }
                }
            });
		    webView.getEngine().getLoadWorker().stateProperty().addListener(
		            new ChangeListener<State>() {
		                public void changed(ObservableValue ov, State oldState, State newState) {
		                    if (newState == State.SUCCEEDED) {
		                        try {
									Method m = callbackClass.getClass().getMethod(callbackMethod, null);
									m.invoke(callbackClass, null);
								} catch (NoSuchMethodException | SecurityException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		                    }
		                }
		            });
			webView.getEngine().load(view);
			wv = webView;
		});
	}
	
	/**
	 * Call a javascript function (Or line of code)
	 * @param function
	 */
	public void executeJavaScript(String function) {
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        	wv.getEngine().executeScript(function);
	        }
	   });
	}
	
	public void executeJavaScript(String function, Object returnclazz, String returnFunction) {
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        	System.out.println(returnclazz.getClass().getMethods()[0].getName());
	        	Optional<Method> mtd = Arrays.stream(returnclazz.getClass().getMethods()).filter(x -> x.getName().equals(returnFunction)).findFirst();
	        	if(!mtd.isPresent()) {
					try {
						throw new MethodNotFoundException(mtd.get().getName() + " is not found!");
					} catch (MethodNotFoundException e1) {
						e1.printStackTrace();
					}
	        	}
	        	Method m = mtd.get();
	        	try {
					m.invoke(returnclazz, Arrays.asList(wv.getEngine().executeScript(function)));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
	        }
	   });
	}

}
