/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.query.functionscore;

import com.google.common.collect.ImmutableMap;

import org.elasticsearch.common.collect.MapBuilder;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.index.query.QueryParseContext;
import org.elasticsearch.index.query.QueryParsingException;

import java.util.Set;

public class ScoreFunctionParserMapper {

    protected ImmutableMap<String, ScoreFunctionParser> functionParsers;

    @Inject
    public ScoreFunctionParserMapper(Set<ScoreFunctionParser> parsers) {
        MapBuilder<String, ScoreFunctionParser> builder = MapBuilder.newMapBuilder();
        for (ScoreFunctionParser scoreFunctionParser : parsers) {
            for (String name : scoreFunctionParser.getNames()) {
                builder.put(name, scoreFunctionParser);
            }
        }
        this.functionParsers = builder.immutableMap();
    }

    public ScoreFunctionParser get(QueryParseContext parseContext, String parserName) {
        ScoreFunctionParser functionParser = get(parserName);
        if (functionParser == null) {
            throw new QueryParsingException(parseContext, "No function with the name [" + parserName + "] is registered.", null);
        }
        return functionParser;
    }

    private ScoreFunctionParser get(String parserName) {
        return functionParsers.get(parserName);
    }

}
