package org.david.rain.dao;

import org.david.rain.model.GameBean;

import java.util.List;


public interface GameDao {

	List<GameBean> getGameList();

	List<GameBean> getGameById(Integer gameId);

}
