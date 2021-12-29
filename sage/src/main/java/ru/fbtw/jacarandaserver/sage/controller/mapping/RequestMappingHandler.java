package ru.fbtw.jacarandaserver.sage.controller.mapping;

import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.controller.hook.CombinedHook;
import ru.fbtw.jacarandaserver.sage.controller.hook.Hook;
import ru.fbtw.util.pathtree.PathTree;

import java.util.HashMap;
import java.util.Map;


@Component
public class RequestMappingHandler {
	private final PathTree<Hook> dynamicHookPathTree;
	private final Map<String, Hook> staticHooks;

	public RequestMappingHandler() {
		dynamicHookPathTree = new PathTree<>();
		staticHooks = new HashMap<>();
	}

	public void addStaticHook(String path, Hook hook) {
		Hook oldHook = staticHooks.get(path);
		if (oldHook == null) {
			staticHooks.put(path, hook);
		} else {
			CombinedHook combinedHook = new CombinedHook();
			combinedHook.addHook(oldHook);
			combinedHook.addHook(hook);
			staticHooks.put(path, combinedHook);
		}
	}

	public void addDynamicHook(String path, Hook hook) {
		Hook oldHook = dynamicHookPathTree.get(path);
		if (oldHook == null) {
			dynamicHookPathTree.put(path, hook);
		} else {
			CombinedHook combinedHook = new CombinedHook();
			combinedHook.addHook(oldHook);
			combinedHook.addHook(hook);
			dynamicHookPathTree.put(path, combinedHook);
		}
	}

	public Hook resolve(String path) {
		Hook hook = staticHooks.get(path);
		if (hook != null) {
			return hook;
		}
		return dynamicHookPathTree.get(path);
	}
}
