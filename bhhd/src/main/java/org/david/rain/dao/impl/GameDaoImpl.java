package org.david.rain.dao.impl;

import org.david.rain.dao.GameDao;
import org.david.rain.model.GameBean;
import org.david.rain.model.GameBeanMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@Repository("gameDao")
public class GameDaoImpl extends JdbcDaoSupport implements GameDao {

	private static final String TABLE_NAME = "T_GAME_DICT";

	@Resource(name = "dataSource")
	private DataSource dataSource;

	@PostConstruct
	public void injectDataSource() {
		super.setDataSource(dataSource);
	}

	@Override
	public List<GameBean> getGameList() {
		final String sql = "select t1.game_id, t1.game_name, t1.game_ab, t1.client from "
				+ TABLE_NAME + " t1 where t1.game_type = 2 order by t1.game_id ";

		List<GameBean> list = getJdbcTemplate()
				.query(sql, new GameBeanMapper());

		return list;
	}

	@Override
	public List<GameBean> getGameById(Integer gameId) {
		//添加是否分端
		final String sql = "select t1.game_id, t1.game_name, t1.game_ab, t1.client from "
				+ TABLE_NAME + " t1 where t1.game_id = ? ";

		Object[] args = { gameId };
		List<GameBean> list = getJdbcTemplate().query(sql, args,
				new GameBeanMapper());

		return list;
	}

}
