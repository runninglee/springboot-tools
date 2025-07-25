package com.julan.tools.util.rule.isUnique;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule.isUnique
 * @ClassName : IsUniqueValidation
 * @Description : 唯一性验证器
 * @Author : Hui Lee
 * @CreateTime : 2025/7/17 16:01
 * @Version : V1.0.0
 */
@Log4j2
public class IsUniqueValidation implements ConstraintValidator<IsUnique, Object> {

    private String table;
    private String[] columns;
    private String id;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void initialize(IsUnique annotation) {
        this.table = annotation.table();
        this.columns = annotation.columns();
        this.id = annotation.id();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) return true;
        try {
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ").append(table).append(" WHERE ");
            List<Object> params = new ArrayList<>();
            for (int i = 0; i < columns.length; i++) {
                String field = columns[i];
                String fieldName = toCamel(field);
                Field declaredField = obj.getClass().getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                Object value = declaredField.get(obj);
                sql.append(field).append(" = ?");
                params.add(value);
                if (i < columns.length - 1) {
                    sql.append(" AND ");
                }
            }
            // 排除自己（更新场景）
            if (!id.isEmpty()) {
                Field idField = obj.getClass().getDeclaredField(id);
                idField.setAccessible(true);
                Object idValue = idField.get(obj);
                if (idValue != null) {
                    sql.append(" AND ").append(id).append(" <> ?");
                    params.add(idValue);
                }
            }

            Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, params.toArray());
            return count == 0;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 下划线转驼峰，如 hr_account -> hrAccount
     */
    private String toCamel(String column) {
        StringBuilder sb = new StringBuilder();
        boolean upper = false;
        for (char c : column.toCharArray()) {
            if (c == '_') {
                upper = true;
            } else {
                sb.append(upper ? Character.toUpperCase(c) : c);
                upper = false;
            }
        }
        return sb.toString();
    }
}
