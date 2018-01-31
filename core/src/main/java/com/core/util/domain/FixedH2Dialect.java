package com.core.util.domain;

import org.hibernate.dialect.H2Dialect;

import java.sql.Types;

/**
 * Created by himanshu.virmani on 08/12/15.
 */
public class FixedH2Dialect extends H2Dialect {
    public FixedH2Dialect() {
        super();
        registerColumnType(Types.FLOAT, "real");
    }
}
