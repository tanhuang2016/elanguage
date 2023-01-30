package com.util;

import com.annotation.EColumn;
import com.efile.ETable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {

    public static <E>List<E> parseBean(ETable eTable, Class<E> eClass) throws IllegalAccessException, InstantiationException {
        boolean annotationPresent = eClass.isAnnotationPresent(com.annotation.ETable.class);
        String eTableValue = eClass.getSimpleName();
        if(annotationPresent){
            com.annotation.ETable tableAnnotation = eClass.getAnnotation(com.annotation.ETable.class);
            eTableValue = tableAnnotation.value();
        }

        String tableName = eTable.getTableName();
        if(!eTableValue.equals(tableName)){
            StringBuilder str = new StringBuilder();
            str.append(eTableValue);
            str.append("与");
            str.append(tableName);
            str.append("不一致");
            throw new RuntimeException(str.toString());
        }
        Field[] fields = eClass.getDeclaredFields();
        List<Object[]> datas = eTable.getDatas();
        String[] columnNames = eTable.getColumnNames();
        List<ColumnRecord> columnRecords = new ArrayList<>();
        for (Field field : fields) {
            boolean columnAnnotationPresent = field.isAnnotationPresent(EColumn.class) ;
            if(columnAnnotationPresent){
                EColumn eColumnAnnotation = field.getAnnotation(EColumn.class);
                String value = eColumnAnnotation.value();
                for (int i = 0; i < columnNames.length; i++) {
                    if(value.equals(columnNames[i])){
                        ColumnRecord c = new ColumnRecord();
                        c.setField(field);
                        c.setIndex(i);
                        columnRecords.add(c);
                    }
                }


            }
        }

        List<E> es = new ArrayList<>();

        for (Object[] data : datas) {
            E e = eClass.newInstance();
            for (ColumnRecord columnRecord : columnRecords) {
                Field field = columnRecord.getField();
                field.setAccessible(true);
                field.set(e,data[columnRecord.getIndex()]);
            }
            es.add(e);

        }



        return es;
    }
}
