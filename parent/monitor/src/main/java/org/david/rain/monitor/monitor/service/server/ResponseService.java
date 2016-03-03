package org.david.rain.monitor.monitor.service.server;

import org.david.rain.monitor.monitor.domain.ReturnType;
import org.david.rain.monitor.monitor.domain.TypeSetting;
import org.david.rain.monitor.monitor.persistence.ReturnTypeMapper;
import org.david.rain.monitor.monitor.persistence.TypeSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by czw on 13-12-11.
 */
@Service
public class ResponseService {

    @Autowired
    ReturnTypeMapper typeMapper;

    @Autowired
    TypeSettingMapper typeSettingMapper;

    public void insertServerReturnType(String typeCode) {
        typeMapper.addReturnType(typeCode);
    }

    public List<ReturnType> getReturnTypeList() {
        return typeMapper.getAllTypes();
    }

    public List<TypeSetting> getTypeSettings(ReturnType returnType) {
        return typeSettingMapper.getTypeSettings(returnType);
    }

    public void addTypeSetting(TypeSetting typeSetting) {
        typeSettingMapper.addTypeSetting(typeSetting);
    }

    public int updateTypeSetting(TypeSetting typeSetting) {
        return typeSettingMapper.updateTypeSetting(typeSetting);
    }

    public int deleteTypeSetting(Integer id) {
        return typeSettingMapper.deleteTypeSettingById(id);
    }

    @Transactional
    public int deleteType(String codeType) {
        int re1 = typeMapper.deleteReturnTypeByCode(codeType);
        typeSettingMapper.deleteTypeByTypeCode(codeType);
        return re1;
    }
}
