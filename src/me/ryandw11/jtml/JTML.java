package me.ryandw11.jtml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
import me.ryandw11.jtml.defaults.JtmlJs;
import me.ryandw11.jtml.events.PageLoad;
import me.ryandw11.jtml.exceptions.MethodNotFoundException;
import netscape.javascript.JSObject;

@SuppressWarnings("restriction")
public class JTML {
	
	private WebView wv;
	private List<Object> eventList;
	
	/**
	 * Add a webpage view. The JPanel version should be used correctly.
	 * @param fr
	 * @param view The string path.
	 * @param jstojava The map containing the java to javascript functions.
	 */
	public JTML(JFrame fr, String view, Map<String, Object> jstojava) {
		eventList = new ArrayList<>();
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
                    bridge.setMember("jtml", new JtmlJs());
                    Iterator<Entry<String, Object>> it = jstojava.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
                        bridge.setMember(pair.getKey(), pair.getValue());
                        it.remove();
                    }
                }
            });
		    JTML currentInst = this;
		    webView.getEngine().getLoadWorker().stateProperty().addListener(
		            new ChangeListener<State>() {
		                public void changed(ObservableValue ov, State oldState, State newState) {
		                    if (newState == State.SUCCEEDED) {
		                        fireEvent(PageLoad.class, new PageLoad(webView, currentInst));
		                    }
		                }
		            });
			webView.getEngine().load(view);
			wv = webView;
		});
	}
	
	/**
	 * Add a webpage view. The JPanel version should be used correctly.
	 * @param fr
	 * @param view The string path.
	 * @param jstojava The map containing the java to javascript functions.
	 * @param inst The instance of the object.
	 */
	public JTML(JFrame fr, String view, Map<String, Object> jstojava, Object inst) {
		eventList = new ArrayList<>();
		this.registerEvent(inst);
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
                    bridge.setMember("jtml", new JtmlJs());
                    Iterator<Entry<String, Object>> it = jstojava.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
                        bridge.setMember(pair.getKey(), pair.getValue());
                        it.remove();
                    }
                }
            });
		    JTML currentInst = this;
		    webView.getEngine().getLoadWorker().stateProperty().addListener(
		            new ChangeListener<State>() {
		                public void changed(ObservableValue ov, State oldState, State newState) {
		                    if (newState == State.SUCCEEDED) {
		                        fireEvent(PageLoad.class, new PageLoad(webView, currentInst));
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
		eventList = new ArrayList<>();
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
                    bridge.setMember("jtml", new JtmlJs());
                    Iterator<Entry<String, Object>> it = jstojava.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
                        bridge.setMember(pair.getKey(), pair.getValue());
                        it.remove();
                    }
                }
            });
		    JTML currentInst = this;
		    webView.getEngine().getLoadWorker().stateProperty().addListener(
		            new ChangeListener<State>() {
		                public void changed(ObservableValue ov, State oldState, State newState) {
		                    if (newState == State.SUCCEEDED) {
		                        fireEvent(PageLoad.class, new PageLoad(webView, currentInst));
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
	 * @param inst The instance of the object.
	 */
	public JTML(JPanel fr, String view, Map<String, Object> jstojava, Object inst) {
		eventList = new ArrayList<>();
		this.registerEvent(inst);
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
                    bridge.setMember("jtml", new JtmlJs());
                    Iterator<Entry<String, Object>> it = jstojava.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
                        bridge.setMember(pair.getKey(), pair.getValue());
                        it.remove();
                    }
                }
            });
		    JTML currentInst = this;
		    webView.getEngine().getLoadWorker().stateProperty().addListener(
		            new ChangeListener<State>() {
		                public void changed(ObservableValue ov, State oldState, State newState) {
		                    if (newState == State.SUCCEEDED) {
		                        fireEvent(PageLoad.class, new PageLoad(webView, currentInst));
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
	
	/**
	 * Call a javascript function that returns a value back to java.
	 * @param function The function in javascript
	 * @param returnclazz The instance of the return class
	 * @param returnFunction The name of the method that handles the data.
	 */
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
	
	/**
	 * Registers a listener.
	 * @param obj The instance of the class that contains the method.
	 */
	public void registerEvent(Object obj) {
		eventList.add(obj);
	}
	
	private void fireEvent(Class<?> clazz, Object construct) {
		for(Object obj : eventList) {
			for(Method mtd : obj.getClass().getMethods()) {
				if(!mtd.isAnnotationPresent(JTMLEvent.class)) {
					continue;
				}
				if(mtd.getParameterCount() != 1) continue;
				if(mtd.getParameters()[0].getClass() == clazz) {
					try {
						mtd.invoke(obj, construct);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
