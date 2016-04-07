/*
*/
package org.david.rain.common.components.util;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * Date: 2008-5-28
 * Time: 11:58:44
 * To change this template use File | Settings | File Templates.
 */
public class Field {
    private ArrayList FieldNames;
      private ArrayList Params;
      private ArrayList ParamValues;
      private String table;
      private String KeyField = "id";
      private Object KeyFieldValue;
      private int[] detailid;
      private String scope;
      private Vector orderby;

      public Field() {
      }

      public void setFieldNames(ArrayList FieldNames) {
            this.FieldNames = FieldNames;
      }

      public void setTable(String table) {
            this.table = table;
      }

      public void setDetailid(int[] detailid) {
            this.detailid = detailid;
      }

      public void setScope(String scope) {
            this.scope = scope;
      }

      public void setKeyFieldValue(Object KeyFieldValue) {
            this.KeyFieldValue = KeyFieldValue;
      }

      public void setParamValues(ArrayList ParamValues) {
            this.ParamValues = ParamValues;
      }

      public void setParams(ArrayList Params) {
            this.Params = Params;
      }

      public void setKeyField(String KeyField) {
            this.KeyField = KeyField;
      }

      public ArrayList getFieldNames() {
            return FieldNames;
      }

      public String getTable() {
            return table;
      }

      public int[] getDetailid() {
            return detailid;
      }

      public String getScope() {
            return scope;
      }

      public Object getKeyFieldValue() {
            return KeyFieldValue;
      }

      public ArrayList getParamValues() {
            return ParamValues;
      }

      public ArrayList getParams() {
            return Params;
      }

      public String getKeyField() {
            return KeyField;
      }

      public String getInsertSql() {
            StringBuffer sql = new StringBuffer("INSERT INTO "+table+"(");
            StringBuffer prepared = new StringBuffer();
            for(int i=0;Params!=null&&i<Params.size();i++){
                  if(prepared.length()==0){
                        sql.append(Params.get(i));
                        prepared.append("?");
                  }else{
                        sql.append(",").append(Params.get(i));
                        prepared.append(",?");
                  }
            }
            sql.append(") VALUES (").append(prepared).append(")");
            return sql.toString();
      }

      public String getUpdateSql() {
            StringBuffer sql = new StringBuffer("UPDATE "+table+" SET ");
            for(int i=0;Params!=null&&i<Params.size();i++){
                  if(!Params.get(i).equals(KeyField)){
                        if (i == Params.size() - 1) {
                              sql.append(Params.get(i)).append(" = ? ");
                        } else {
                              sql.append(Params.get(i)).append(" = ?, ");
                        }
                  }else{
                        Params.remove(i);
                  }
            }

            sql.append("WHERE ").append(KeyField).append(" = ").append(KeyFieldValue);

            return sql.toString();
      }

}
