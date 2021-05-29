/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.db.dal.dynamic;
import net.hasor.db.dal.fxquery.DefaultFxQuery;
import net.hasor.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * <bind> 标签
 * @author 赵永春 (zyc@byshell.org)
 * @version : 2021-05-24
 */
public class BindSqlNode extends SqlNode {
    private final static Logger  logger = LoggerFactory.getLogger(BindSqlNode.class);
    private final        String  name;      // 名字
    private final        String  valueExpr; // 值
    private final        boolean overwrite; // 遇到冲突 Key 是否覆盖

    public BindSqlNode(String name, String valueExpr, boolean overwrite) {
        this.name = name;
        this.valueExpr = valueExpr;
        this.overwrite = overwrite;
    }

    @Override
    public void buildSql(StringBuilder queryString, Map<String, Object> contextMap) {
        if (StringUtils.isNotBlank(this.name)) {
            if (contextMap.containsKey(this.name)) {
                if (!this.overwrite) {
                    throw new IllegalArgumentException("duplicate key '" + this.name + "'");
                } else {
                    logger.warn("tag bind overwrite param key " + this.name);
                }
            }
            Object testExprResult = DefaultFxQuery.evalOgnl(this.valueExpr, contextMap);
            contextMap.put(this.name, testExprResult);
        }
    }
}