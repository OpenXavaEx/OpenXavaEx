package org.openxava.ex.model.base;

import org.openxava.ex.annotation.query.Condition;
import org.openxava.ex.annotation.query.Sql;
import org.openxava.ex.model.utils.ReportQueryUtils;

/**
 * The base class for Report Query View Model, using {@link Sql} and {@link Condition} annotation
 * @author root
 *
 */
public class BaseReportSqlQuery extends BaseReportQuery {

	protected QueryResult doQuery(_ReportQueryAction action) {
		return ReportQueryUtils.runSqlQuery(action.getConnectionProvider(), this);
	}

}
