package org.david.rain.security.tool;

import org.david.rain.model.GameBean;
import org.david.rain.model.Menu;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SessionUser implements Serializable {

	private static final long serialVersionUID = -3250660435051228657L;

	public static final String SESSION_ROOT_KEY = SessionUser.class.getName()
			+ ".yytj.session.root.key";

	public static final String SESSION_CURRENT_GAME_SHORT = SessionUser.class
			.getName() + ".yytj.session.current.game.short";

	private Map<String, GameBean> gameMap;

	private Map<String, Set<String>> authorityMap;

	private Map<Integer, Map<String, List<Menu>>> userMenuMap;

	private List<String> authorityGameList;

	public Map<String, GameBean> getGameMap() {
		return gameMap;
	}

	public void setGameMap(Map<String, GameBean> gameMap) {
		this.gameMap = gameMap;
	}

	public Map<String, Set<String>> getAuthorityMap() {
		return authorityMap;
	}

	public void setAuthorityMap(Map<String, Set<String>> authorityMap) {
		this.authorityMap = authorityMap;
	}

	public Map<Integer, Map<String, List<Menu>>> getUserMenuMap() {
		return userMenuMap;
	}

	public void setUserMenuMap(Map<Integer, Map<String, List<Menu>>> userMenuMap) {
		this.userMenuMap = userMenuMap;
	}

	public List<String> getAuthorityGameList() {
		return authorityGameList;
	}

	public void setAuthorityGameList(List<String> authorityGameList) {
		this.authorityGameList = authorityGameList;
	}

}
