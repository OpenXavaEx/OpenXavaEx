package org.openxava.ex.model.pqgrid;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import org.openxava.ex.annotation.query.FieldProp;
import org.openxava.ex.annotation.query.FieldTmpl;
import org.openxava.ex.annotation.query.FieldTmpls;
import org.openxava.ex.model.pqgrid.PQGridClientModel.ColModelDetail;

/**
 * The defaule {@link FieldTmpls} for PQGrid
 * @author root
 *
 */
@FieldTmpls({
	// The default properties - Object
	@FieldTmpl(fieldClass=Object.class, value={
			@FieldProp(name=PQGridClientModel.ALIGN, value=ColModelDetail.DEFAULT_ALIGN),
			@FieldProp(name=PQGridClientModel.DATATYPE, value=ColModelDetail.DEFAULT_DATATYPE),
			@FieldProp(name=PQGridClientModel.WIDTH, value=ColModelDetail.DEFAULT_WIDTH),
			@FieldProp(name=PQGridClientModel.PROTOTYPE, value=ColModelDetail.DEFAULT_PROTOTYPE)
	}),
	// String
	@FieldTmpl(fieldClass=String.class, value={
		//The same as default
	}),
	// Number
	@FieldTmpl(fieldClass=Number.class, value={
			@FieldProp(name=PQGridClientModel.ALIGN, value=ColModelDetail.ALIGN_right),
			@FieldProp(name=PQGridClientModel.DATATYPE, value=ColModelDetail.DATATYPE_integer),
			@FieldProp(name=PQGridClientModel.PROTOTYPE, value=ColModelDetail.PROTOTYPE_number),
			@FieldProp(name=PQGridClientModel.NUMBER_PRECISION, value="0")
	}),
	// Integer
	@FieldTmpl(fieldClass=Integer.class, value={
		//The same as Number
	}),
	// Long
	@FieldTmpl(fieldClass=Long.class, value={
		//The same as Number
	}),
	// Short
	@FieldTmpl(fieldClass=Short.class, value={
		//The same as Number
	}),
	// Double
	@FieldTmpl(fieldClass=Double.class, value={
			@FieldProp(name=PQGridClientModel.DATATYPE, value=ColModelDetail.DATATYPE_float),
			@FieldProp(name=PQGridClientModel.NUMBER_PRECISION, value=ColModelDetail.DEFAULT_NUMBER_PRECISION)
	}),
	// Float
	@FieldTmpl(fieldClass=Float.class, value={
			@FieldProp(name=PQGridClientModel.DATATYPE, value=ColModelDetail.DATATYPE_float),
			@FieldProp(name=PQGridClientModel.NUMBER_PRECISION, value=ColModelDetail.DEFAULT_NUMBER_PRECISION)
	}),
	// BigDecimal
	@FieldTmpl(fieldClass=BigDecimal.class, value={
			@FieldProp(name=PQGridClientModel.DATATYPE, value=ColModelDetail.DATATYPE_float),
			@FieldProp(name=PQGridClientModel.NUMBER_PRECISION, value=ColModelDetail.DEFAULT_NUMBER_PRECISION)
	}),
	// Date
	@FieldTmpl(fieldClass=Date.class, value={
			@FieldProp(name=PQGridClientModel.ALIGN, value=ColModelDetail.ALIGN_center),
			@FieldProp(name=PQGridClientModel.DATATYPE, value=ColModelDetail.DATATYPE_string),
			@FieldProp(name=PQGridClientModel.PROTOTYPE, value=ColModelDetail.PROTOTYPE_date)
	}),
	// java.sql.Date
	@FieldTmpl(fieldClass=java.sql.Date.class, value={
		//The same as Date
	}),
	// Time
	@FieldTmpl(fieldClass=Time.class, value={
			@FieldProp(name=PQGridClientModel.PROTOTYPE, value=ColModelDetail.PROTOTYPE_datetime)
	}),
	// Timestamp
	@FieldTmpl(fieldClass=Timestamp.class, value={
			@FieldProp(name=PQGridClientModel.PROTOTYPE, value=ColModelDetail.PROTOTYPE_datetime)
	})
})
public class PQGridFieldsTmpl {
	//Nothing
}
