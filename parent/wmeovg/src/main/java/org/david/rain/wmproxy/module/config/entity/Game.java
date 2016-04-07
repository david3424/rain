package org.david.rain.wmproxy.module.config.entity;


import org.david.rain.wmproxy.module.config.entity.base.BaseGame;

public class Game extends BaseGame {
	private static final long serialVersionUID = 1L;

	public Game() {
		super();
		initialize();
	}

	public Game(Integer id) {
		super(id);
		initialize();
	}

	public Game(Integer id,  String name, String serverName,
			Integer aid) {
		super(id, name, serverName, aid);
		initialize();
	}

	@Override
	protected void initialize() {
	}
}
