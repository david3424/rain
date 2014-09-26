package org.david.rain.dao;

import org.david.rain.model.RoleGameDetail;

import java.util.List;


public interface RoleGameMappingDao {

	List<RoleGameDetail> getRoleGameListByRoleId(Integer roleId);

	int countGameList();

	List<RoleGameDetail> getRoleGameListByRoleId(Integer roleId, int from,
                                                 int length, String sidx, String sord);

	List<String> getGameListByRoleCode(String roleCode);

	int countByRoleIdAndGameid(Integer roleId, Integer gameId);

	int insertRoleGameMapping(Integer roleId, Integer gameId);

	int deleteRoleGameMapping(Integer roleId, Integer gameId);

	int deleteRolePermissionMappingByRoleIdAndGameId(Integer roleId,
                                                     Integer gameId);

}
