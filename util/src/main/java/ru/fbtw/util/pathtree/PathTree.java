package ru.fbtw.util.pathtree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class PathTree<V> {
	public static final String DEFAULT_SEPARATOR = "/";

	private final String separators;
	private final PathTreeNode root;

	public PathTree() {
		this(DEFAULT_SEPARATOR);
	}

	public PathTree(String separators) {
		this.separators = separators;
		root = new PathTreeNode(null);
	}

	public void put(String path, V value) {
		put(root, asIterator(path), value);
	}

	public V get(String path) {
		PathTreeNode nearestNode = getNearestNode(root, asIterator(path));
		return (nearestNode == null) ? null : nearestNode.value;
	}

	private Iterator<String> asIterator(String path) {
		String[] nodes = path.split(separators);

		if (nodes.length == 0) {
			nodes = new String[]{""};
		}

		return new ArrayIterator<>(0, nodes);
	}

	private PathTreeNode getNearestNode(PathTreeNode node, Iterator<String> iterator) {
		if (!iterator.hasNext()) {
			return node;
		}

		String targetNode = iterator.next();
		PathTreeNode nextNode = node.children.get(targetNode);

		if (nextNode == null) {
			return node;
		}

		return getNearestNode(nextNode, iterator);
	}

	private PathTreeNode put(PathTreeNode parent, Iterator<String> iterator, V value) {
		if (!iterator.hasNext()) {
			parent.value = value;
			return parent;
		}

		String targetNodeName = iterator.next();
		PathTreeNode nextNode = parent.children.get(targetNodeName);

		if (nextNode == null) {
			if (iterator.hasNext()) {
				nextNode = new PathTreeNode(null);
				parent.children.put(targetNodeName, nextNode);
			} else {
				nextNode = new PathTreeNode(value);
				parent.children.put(targetNodeName, nextNode);
				return nextNode;
			}
		}

		return put(nextNode, iterator, value);
	}

	class PathTreeNode {

		private V value;
		private final Map<String, PathTreeNode> children;

		public PathTreeNode(V value) {
			this.value = value;
			this.children = new HashMap<>();
		}
	}
}