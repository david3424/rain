package org.david.rain.wmproxy.module.sys.action.tree;

/**
 * 辅助类  菜单节点
 * @author gameuser
 *
 */
public class Node implements Comparable<Node> {
	public Integer id;
	public Integer sort;
	public String text;
	public Boolean leaf;
	public String url;
	public String target;

	public Node(Integer id,Integer sort, String text, Boolean leaf, String url, String target) {
		super();
		this.id = id;
		this.sort = sort;
		this.text = text;
		this.leaf = leaf;
		this.url = url;
		this.target = target;
	}
	
	public int compareTo(Node o) {
		if((this.leaf == o.leaf) && (this.id == o.id))
			return 0;
		else if(this.leaf && ! o.leaf)
			return 1;
		else if(!this.leaf && o.leaf)
			return -1;
		else if(this.sort - o.sort==0)
			return (this.text.compareTo(o.text));
		else
			return this.sort - o.sort;
	}
}
